package cn.blinfra.boot.starter.security;

import cn.blinfra.boot.starter.web.WebProperties;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import java.util.List;
import java.util.Map;
import java.util.Set;

@AutoConfiguration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class BlWebSecurityConfigurerAdapter {

  @Resource
  private WebProperties webProperties;

  @Resource
  private SecurityProperties securityProperties;

  @Resource
  private AuthenticationEntryPoint authenticationEntryPoint;

  @Resource
  private AccessDeniedHandler accessDeniedHandler;

  @Resource
  private TokenAuthenticationFilter authenticationTokenFilter;

  @Resource
  private List<AuthorizeRequestsCustomizer> authorizeRequestsCustomizers;

  @Resource
  private ApplicationContext applicationContext;

  @Bean
  public AuthenticationManager authenticationManagerBean(
    AuthenticationConfiguration authenticationConfiguration
  ) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }


  protected SecurityFilterChain filterChain(HttpSecurity httpSecurity)
  throws Exception {
    httpSecurity
      // cors
      .cors()
      .and()
      // 禁用 csrf，不使用session
      .csrf()
      .disable()
      // 基于token，不需要session
      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and()
      // 不需要frame
      .headers()
      .frameOptions()
      .disable()
      .and()
      //
      .exceptionHandling()
      // 默认访问点
      .authenticationEntryPoint(authenticationEntryPoint)
      // 访问拒绝处理器
      .accessDeniedHandler(accessDeniedHandler);

    Multimap<HttpMethod, String> permitAllUrls = getPermitAllUrlsFromAnnotations();

    httpSecurity
      // 1. 全局共享规则
      .authorizeRequests()
      // 1.1 全局共享资源可以匿名访问
      .antMatchers(HttpMethod.GET, "/*.html", "/**/*.html", "/**/*/.css", "/**/*.js")
      .permitAll()
      // 1.2 设置@PermitAll 无须验证
      .antMatchers(
        HttpMethod.GET,
        permitAllUrls.get(HttpMethod.GET).toArray(new String[0])
      )
      .permitAll()
      .antMatchers(
        HttpMethod.POST,
        permitAllUrls.get(HttpMethod.POST).toArray(new String[0])
      )
      .permitAll()
      .antMatchers(
        HttpMethod.PUT,
        permitAllUrls.get(HttpMethod.PUT).toArray(new String[0])
      )
      .permitAll()
      .antMatchers(
        HttpMethod.DELETE,
        permitAllUrls.get(HttpMethod.DELETE).toArray(new String[0])
      )
      .permitAll()
      // 1.3 基于 bl.security.permit-all-urls 无需验证
      .antMatchers(securityProperties.getPermitAllUrls().toArray(new String[0]))
      .permitAll()
      // 1.4 设置 App API 无需认证
      .antMatchers(buildAppApi("/**"))
      .permitAll()
      // 1.5 验证码captcha 允许匿名访问
      .antMatchers("/captcha/get", "/captcha/check")
      .permitAll()
      // 1.6 websocket 允许匿名访问
      .antMatchers("/websocket/message")
      .permitAll()
      .and()
      // 2 每个项目自定义
      .authorizeRequests(registry -> authorizeRequestsCustomizers.forEach(customizer -> customizer.customize(
        registry)))
      // 3 兜底, 必须认证
      .authorizeRequests()
      .anyRequest()
      .authenticated();


    httpSecurity.addFilterBefore(
      authenticationTokenFilter,
      UsernamePasswordAuthenticationFilter.class
    );
    return httpSecurity.build();
  }

  private String buildAppApi(String url) {
    return webProperties.getAppApi().getPrefix() + url;
  }

  private Multimap<HttpMethod, String> getPermitAllUrlsFromAnnotations() {
    Multimap<HttpMethod, String> result = HashMultimap.create();

    RequestMappingHandlerMapping requestMappingHandlerMapping =
      (RequestMappingHandlerMapping) applicationContext.getBean(
        "requestMappingHandlerMapping");

    Map<RequestMappingInfo, HandlerMethod> handlerMethodMap =
      requestMappingHandlerMapping.getHandlerMethods();

    for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethodMap.entrySet()) {

      HandlerMethod handlerMethod = entry.getValue();
      // 获得有@PermitAll 注解的接口
      if (!handlerMethod.hasMethodAnnotation(PermitAll.class)) {
        continue;
      }
      if (entry.getKey().getPatternsCondition() == null) {
        continue;
      }
      Set<String> urls = entry.getKey().getPatternsCondition().getPatterns();

      Set<RequestMethod> methods = entry.getKey().getMethodsCondition().getMethods();

      for (final RequestMethod requestMethod : methods) {
        if (requestMethod == RequestMethod.GET) {
          result.putAll(HttpMethod.GET, urls);
        }
        else if (requestMethod == RequestMethod.POST) {
          result.putAll(HttpMethod.POST, urls);
        }
        else if (requestMethod == RequestMethod.PUT) {
          result.putAll(HttpMethod.PUT, urls);
        }
        else if (requestMethod == RequestMethod.DELETE) {
          result.putAll(HttpMethod.DELETE, urls);
        }
      }
    }

    return result;
  }


}

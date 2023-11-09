package cn.blinfra.boot.starter.security;

import cn.blinfra.boot.starter.web.GlobalExceptionHandler;
import cn.blinfra.boot.system.api.oauth2.Oauth2TokenApi;
import cn.blinfra.boot.system.api.permission.PermissionApi;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.annotation.Resource;

@AutoConfiguration
@EnableConfigurationProperties(SecurityProperties.class)
public class BlSecurityAutoConfiguration {

  @Resource
  private SecurityProperties securityProperties;

  @Bean
  public PreAuthenticatedAspect preAuthenticatedAspect() {
    return new PreAuthenticatedAspect();
  }

  @Bean
  public AuthenticationEntryPoint authenticationEntryPoint() {
    return new AuthenticationEntryPointImpl();
  }

  @Bean
  public AccessDeniedHandler accessDeniedHandler() {
    return new AccessDeniedHandlerImp();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(securityProperties.getPasswordEncoderLength());
  }

  @Bean
  public TokenAuthenticationFilter authenticationFilter(
    GlobalExceptionHandler globalExceptionHandler,
    Oauth2TokenApi oauth2TokenApi
  ) {
    return new TokenAuthenticationFilter(
      securityProperties,
      globalExceptionHandler,
      oauth2TokenApi
    );
  }

  @Bean("ss")
  public SecurityFrameworkService securityFrameworkService(PermissionApi permissionApi) {
    return new SecurityFrameworkServiceImpl(permissionApi);
  }

  /**
   * 设置使用
   * {@link TransmittableThreadLocalSecurityContextHolderStrategy}
   * 作为Security上下文
   *
   * @return
   */
  @Bean
  public MethodInvokingFactoryBean securityContextHolderMethodInvokingFactoryBean() {
    MethodInvokingFactoryBean methodInvokingFactoryBean =
      new MethodInvokingFactoryBean();
    methodInvokingFactoryBean.setTargetClass(SecurityContextHolder.class);
    methodInvokingFactoryBean.setTargetMethod("setStrategyName");
    methodInvokingFactoryBean.setArguments(
      TransmittableThreadLocalSecurityContextHolderStrategy.class.getName());
    return methodInvokingFactoryBean;
  }


}

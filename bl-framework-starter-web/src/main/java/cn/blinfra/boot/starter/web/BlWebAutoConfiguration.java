package cn.blinfra.boot.starter.web;

import cn.blinfra.boot.common.enums.WebFilterOrderEnum;
import cn.blinfra.boot.starter.apilog.service.ApiErrorLogFrameworkService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import javax.servlet.Filter;
import java.util.function.Predicate;

@AutoConfiguration
@EnableConfigurationProperties(WebProperties.class)
public class BlWebAutoConfiguration implements WebMvcConfigurer {

  public static <T extends Filter> FilterRegistrationBean<T> createFilterBean(
    T filter, Integer order
  ) {
    FilterRegistrationBean<T> bean = new FilterRegistrationBean<>(filter);
    bean.setOrder(order);
    return bean;
  }

  @Resource
  private WebProperties webProperties;

  @Value("${spring.application.name}")
  private String applicationName;

  @Override
  public void configurePathMatch(PathMatchConfigurer configurer) {
    configurePathMatch(configurer, webProperties.getAdminApi());
    configurePathMatch(configurer, webProperties.getAppApi());
  }

  @Bean
  public GlobalExceptionHandler globalExceptionHandler(
    ApiErrorLogFrameworkService apiErrorLogFrameworkService
  ) {
    return new GlobalExceptionHandler(applicationName, apiErrorLogFrameworkService);
  }

  @Bean
  public GlobalResponseBodyHandler globalResponseBodyHandler() {
    return new GlobalResponseBodyHandler();
  }

  @Bean
  @SuppressWarnings("InstantiationOfUtilityClass")
  public WebFrameworkUtils webFrameworkUtils(WebProperties webProperties) {
    return new WebFrameworkUtils(webProperties);
  }

  @Bean
  public FilterRegistrationBean<CorsFilter> corsFilterBean() {
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    config.addAllowedOriginPattern("*");
    config.addAllowedHeader("*");
    config.addAllowedMethod("*");

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);
    return createFilterBean(new CorsFilter(source), WebFilterOrderEnum.CORS_FILTER);
  }

  @Bean
  public FilterRegistrationBean<CacheRequestBodyFilter> requestBodyCacheFilter() {
    return createFilterBean(
      new CacheRequestBodyFilter(),
      WebFilterOrderEnum.REQUEST_BODY_CACHE_FILTER
    );
  }


  private void configurePathMatch(
    PathMatchConfigurer configurer,
    WebProperties.Api api
  ) {
    AntPathMatcher antPathMatcher = new AntPathMatcher(".");
    configurer
      .addPathPrefix(
        api.getPrefix(),
        createPathPredicate(antPathMatcher, api)
      );
  }


  private Predicate<Class<?>> createPathPredicate(
    AntPathMatcher antPathMatcher,
    WebProperties.Api api
  ) {
    return clazz -> {
      if (!clazz.isAnnotationPresent(RestController.class)) {
        return false;
      }
      return antPathMatcher.match(api.getController(), clazz.getPackage().getName());
    };
  }

}

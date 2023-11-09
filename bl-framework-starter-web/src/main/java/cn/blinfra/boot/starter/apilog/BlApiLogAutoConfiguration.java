package cn.blinfra.boot.starter.apilog;

import cn.blinfra.boot.common.enums.WebFilterOrderEnum;
import cn.blinfra.boot.infra.api.logger.ApiAccessLogApi;
import cn.blinfra.boot.infra.api.logger.ApiErrorLogApi;
import cn.blinfra.boot.starter.apilog.service.ApiAccessLogFrameworkService;
import cn.blinfra.boot.starter.apilog.service.ApiAccessLogFrameworkServiceImpl;
import cn.blinfra.boot.starter.apilog.service.ApiErrorLogFrameworkService;
import cn.blinfra.boot.starter.apilog.service.ApiErrorLogFrameworkServiceImpl;
import cn.blinfra.boot.starter.web.BlWebAutoConfiguration;
import cn.blinfra.boot.starter.web.WebProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@AutoConfiguration(after = BlWebAutoConfiguration.class)
public class BlApiLogAutoConfiguration {

  @Bean
  public ApiAccessLogFrameworkService apiAccessLogFrameworkService(
    ApiAccessLogApi apiAccessLogApi
  ) {
    return new ApiAccessLogFrameworkServiceImpl(apiAccessLogApi);
  }

  @Bean
  public ApiErrorLogFrameworkService apiErrorLogFrameworkService(
    ApiErrorLogApi apiErrorLogApi
  ) {
    return new ApiErrorLogFrameworkServiceImpl(apiErrorLogApi);
  }

  @Bean
  @ConditionalOnProperty(
    prefix = "bl.access-log",
    value = "enable",
    matchIfMissing = true
  )
  public FilterRegistrationBean<ApiAccessLogFilter> apiAccessLogFilterFilter(
    WebProperties webProperties,
    @Value("${spring.application.name}")
    String applicationName,
    ApiAccessLogFrameworkService apiAccessLogFrameworkService
  ) {
    ApiAccessLogFilter filter = new ApiAccessLogFilter(
      webProperties,
      applicationName,
      apiAccessLogFrameworkService
    );
    return BlWebAutoConfiguration.createFilterBean(
      filter,
      WebFilterOrderEnum.API_ACCESS_LOG_FILTER
    );
  }
}

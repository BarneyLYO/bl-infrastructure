package cn.blinfra.boot.starter.xss;

import cn.blinfra.boot.common.enums.WebFilterOrderEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.util.PathMatcher;

import static cn.blinfra.boot.starter.web.BlWebAutoConfiguration.createFilterBean;

@AutoConfiguration
@EnableConfigurationProperties(XssProperties.class)
public class BlXssAutoConfiguration {
  /**
   * Xss 清理者
   *
   * @return XssCleaner
   */
  @Bean
  @ConditionalOnMissingBean(XssCleaner.class)
  public XssCleaner xssCleaner() {
    return new JsoupXssCleaner();
  }

  /**
   * 注册 Jackson 的序列化器，用于处理 json 类型参数的 xss 过滤
   *
   * @return Jackson2ObjectMapperBuilderCustomizer
   */
  @Bean
  @ConditionalOnMissingBean(name = "xssJacksonCustomizer")
  @ConditionalOnBean(ObjectMapper.class)
  @ConditionalOnProperty(value = "bl.xss.enable", havingValue = "true")
  public Jackson2ObjectMapperBuilderCustomizer xssJacksonCustomizer(XssCleaner xssCleaner) {
    // 在反序列化时进行 xss 过滤，可以替换使用 XssStringJsonSerializer，在序列化时进行处理
    return builder -> builder.deserializerByType(String.class, new XssStringJsonDeserializer(xssCleaner));
  }

  /**
   * 创建 XssFilter Bean，解决 Xss 安全问题
   */
  @Bean
  @ConditionalOnBean(XssCleaner.class)
  public FilterRegistrationBean<XssFilter> xssFilter(XssProperties properties, PathMatcher pathMatcher, XssCleaner xssCleaner) {
    return createFilterBean(new XssFilter(properties, pathMatcher, xssCleaner), WebFilterOrderEnum.XSS_FILTER);
  }
}

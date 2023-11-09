package cn.blinfra.boot.starter.security;

import cn.blinfra.boot.starter.web.WebProperties;
import org.springframework.core.Ordered;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

import javax.annotation.Resource;

/**
 * 自定义的URL安全配置
 * 每个Maven Module可以自定义规则
 */
public abstract class AuthorizeRequestsCustomizer
  implements Customizer<
                ExpressionUrlAuthorizationConfigurer<HttpSecurity>.
                  ExpressionInterceptUrlRegistry>,
             Ordered {

  @Resource
  private WebProperties webProperties;

  protected String buildAdminApi(String url) {
    return webProperties.getAdminApi().getPrefix() + url;
  }

  protected String buildAppApi(String url) {
    return webProperties.getAppApi().getPrefix() + url;
  }

  @Override
  public int getOrder() {
    return 0;
  }
}

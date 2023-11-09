package cn.blinfra.boot.starter.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;

@ConfigurationProperties(prefix = "bl.security")
@Validated
@Data
public class SecurityProperties {

  /**
   * HTTP 请求时，访问令牌请求的Header
   */
  @NotEmpty(message = "Token Header 不能为空")
  private String tokenHeader = "Authorization";

  /**
   * mock模式的开关
   */
  @NotNull(message = "mock模式的开关不能为空")
  private Boolean mockEnable = false;

  /**
   * mock模式的密钥
   * 保证安全性
   */
  @NotEmpty(message = "mock 模式的密钥不能为空")
  private String mockSecret = "test";

  /**
   * 免登录的URL列表
   */
  private List<String> permitAllUrls = Collections.emptyList();

  /**
   * PasswordEncoder 加密复杂度，越高开销越大
   */
  private Integer passwordEncoderLength = 4;
}

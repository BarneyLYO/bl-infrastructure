package cn.blinfra.boot.starter.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@ConfigurationProperties(prefix = "bl.web")
@Validated
@Data
public class WebProperties {

  @NotNull(message = "APP API 不能为空")
  private Api appApi = new Api("/app-api", "**.controller.app.**");

  @NotNull(message = "Admin API 不能为空")
  private Api adminApi = new Api("/admin-api", "**.controller.admin.**");

  @NotNull(message = "Admin UI 不能为空")
  private Ui adminUi;


  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @Valid
  public static final class Api {
    /**
     * API 前缀，实现所有Controller提供的RESTFul API的统一前缀
     * 通过该前缀，避免Swagger/Actuator意外通过Nginx暴露出来给外部带来安全性问题
     * Nginx需要配置转发到 /api/*的所有接口即可
     */
    @NotEmpty(message = "API 前缀不能为空")
    private String prefix;

    @NotEmpty(message = "Controller所在包不能为空")
    private String controller;
  }

  @Data
  @Valid
  public static final class Ui {
    private String url;
  }
}

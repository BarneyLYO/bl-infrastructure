package cn.blinfra.boot.starter.apilog.service;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * API 访问日志
 */
@Data
public class ApiAccessLog {

  private String traceId;

  private Long userId;

  private Integer userType;

  @NotNull(message = "应用名不能为空")
  private String applicationName;

  @NotNull(message = "http 请求方法不能为空")
  private String requestMethod;

  @NotNull(message = "请求参数不能为空")
  private String requestParams;

  @NotNull(message = "访问地址不能为空")
  private String requestUrl;

  @NotNull(message = "IP 不能为空")
  private String userIp;

  @NotNull(message = "User-Agent 不能为空")
  private String userAgent;

  @NotNull(message = "开始请求时间不能为空")
  private LocalDateTime beginTime;

  @NotNull(message = "结束请求时间不能为空")
  private LocalDateTime endTime;

  @NotNull(message = "执行时长不能为空")
  private Integer duration;

  @NotNull(message = "错误码不能为空")
  private Integer resultCode;

  private String resultMsg;
}

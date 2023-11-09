package cn.blinfra.boot.infra.api.logger.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class ApiErrorLogCreateReqDTO {
  /**
   * 链路追踪编号
   */
  private String traceId;

  /**
   * 用户编号
   */
  private Long userId;

  /**
   * 用户编号
   */
  private Integer userType;

  /**
   * 应用名
   */
  @NotNull(message = "应用名不能为空")
  private String applicationName;

  @NotNull(message = "请求方法不能为空")
  private String requestMethod;

  @NotNull(message = "访问地址不能为空")
  private String requestUrl;

  @NotNull(message = "请求参数")
  private String requestParams;

  @NotNull(message = "IP 不能为空")
  private String userIp;

  @NotNull(message = "User-Agent 不能为空")
  private String userAgent;

  @NotNull(message = "异常时间不能为空")
  private LocalDateTime exceptionTime;

  @NotNull(message = "异常名不能为空")
  private String exceptionName;

  @NotNull(message = "异常类不能为空")
  private String exceptionClassName;

  @NotNull(message = "异常发生的类文件名不能为空")
  private String exceptionFileName;

  @NotNull(message = "异常发生的方法行数不能为空")
  private String exceptionMethodName;

  @NotNull(message = "异常发生的方法所在行不能为空")
  private Integer exceptionLineNumber;

  @NotNull(message = "异常的堆栈信息不能为空")
  private String exceptionStackTrace;

  @NotNull(message = "异常导致的根消息不能为空")
  private String exceptionRootCauseMessage;

  @NotNull(message = "异常消息不能为空")
  private String exceptionMessage;
}

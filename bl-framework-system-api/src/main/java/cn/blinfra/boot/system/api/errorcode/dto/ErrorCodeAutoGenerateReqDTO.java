package cn.blinfra.boot.system.api.errorcode.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
public class ErrorCodeAutoGenerateReqDTO {

  @NotNull(message = "应用名不能为空")
  private String applicationName;

  @NotNull(message = "错误编码不能为空")
  private Integer code;

  @NotEmpty(message = "错误码错误提示不能为空")
  private String message;
}

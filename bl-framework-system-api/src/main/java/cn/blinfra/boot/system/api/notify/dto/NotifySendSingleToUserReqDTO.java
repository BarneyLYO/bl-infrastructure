package cn.blinfra.boot.system.api.notify.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
public class NotifySendSingleToUserReqDTO {

  @NotNull(message = "用户编号不能为空")
  private Long userId;

  @NotEmpty(message = "站内信模版编号不能为空")
  private String templateCode;

  private Map<String, Object> templateParams;
}

package cn.blinfra.boot.system.api.oauth2.dto;

import cn.blinfra.boot.common.enums.UserTypeEnum;
import cn.blinfra.boot.common.validation.InEnum;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class Oauth2AccessTokenCreateReqDTO implements Serializable {

  @NotNull(message = "用户编号不能为空")
  private Long userId;

  @NotNull(message = "用户类型不能为空")
  @InEnum(value = UserTypeEnum.class, message = "用户必须为指定范围")
  private Integer userType;

  @NotNull(message = "客户编号不能为空")
  private String clientId;

  /**
   * 范围
   */
  private List<String> scopes;
}

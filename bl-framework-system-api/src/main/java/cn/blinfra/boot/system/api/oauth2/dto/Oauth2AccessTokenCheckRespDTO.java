package cn.blinfra.boot.system.api.oauth2.dto;

import lombok.Data;

import java.util.List;

@Data
public class Oauth2AccessTokenCheckRespDTO {

  /**
   * 用户编号
   */
  private Long userId;

  /**
   * 用户类型
   */
  private Integer userType;

  /**
   * 租户编号
   */
  private Long tenantId;

  /**
   * 授权范围数组
   */
  private List<String> scope;
}

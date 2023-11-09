package cn.blinfra.boot.system.api.user.dto;

import lombok.Data;

import java.util.Set;

@Data
public class AdminUserRespDTO {

  private Long id;

  private String nickname;

  private Integer status;

  private Long deptId;

  private Set<Long> postIds;

  private String mobile;

}

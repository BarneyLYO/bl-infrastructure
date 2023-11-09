package cn.blinfra.boot.system.api.dept.dto;

import lombok.Data;

/**
 * 部门ResponseDTO
 */
@Data
public class DeptRespDTO {

  /**
   * 部门编号
   */
  private Long id;

  /**
   * 部门名称
   */
  private String name;

  /**
   * 父部门编号
   */
  private Long parentId;

  /**
   * 负责人编号
   */
  private Long leaderUserId;

  /**
   * 部门状态
   * 枚举 {@link cn.blinfra.boot.common.enums.CommonStatusEnum}
   */
  private Integer status;
}

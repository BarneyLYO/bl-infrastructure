package cn.blinfra.boot.system.api.permission;

import java.util.Collection;

/**
 * 角色API 接口
 */
public interface RoleApi {

  /**
   * 校验角色们是否有效，
   * 无效
   *  - 角色编号不存在
   *  - 角色被禁用
   * @param ids
   */
  void validRoleList(Collection<Long> ids);
}

package cn.blinfra.boot.system.api.permission;

import cn.blinfra.boot.system.api.permission.dto.DeptDataPermissionRespDTO;

import java.util.Collection;
import java.util.Set;

/**
 * 权限API
 */
public interface PermissionApi {

  Set<Long> getUserRoleIdListByRoleIds(Collection<Long> roleIds);

  boolean hasAnyPermissions(Long userId, String... permissions);

  /**
   * 判断是否有任意一个角色
   * @param userId 用户编号
   * @param roles 角色数组
   * @return t/f
   */
  boolean hasAnyRoles(Long userId, String... roles);

  /**
   * 获得登录用户的部门数据权限
   * @param userId 用户编号
   * @return 部门数据权限
   */
  DeptDataPermissionRespDTO getDeptDataPermission(Long userId);
}

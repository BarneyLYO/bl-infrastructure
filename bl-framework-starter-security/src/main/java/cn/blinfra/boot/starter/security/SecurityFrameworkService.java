package cn.blinfra.boot.starter.security;

public interface SecurityFrameworkService {

  /**
   * 判断是否有权限
   *
   * @param permission
   * @return
   */
  boolean hasPermission(String permission);

  /**
   * 判断是否有任意一个权限
   *
   * @param permission
   * @return
   */
  boolean hasAnyPermissions(String... permission);

  /**
   * 判断是否有角色
   * 角色使用的是 SysRoleDO的标识
   *
   * @param role
   * @return
   */
  boolean hasRole(String role);

  /**
   * 判断是否有任意一个角色
   *
   * @param roles
   * @return
   */
  boolean hasAnyRoles(String... roles);

  /**
   * 判断是否有授权
   *
   * @param scope
   * @return
   */
  boolean hasScope(String scope);

  /**
   * 判断是否有任意一个授权
   *
   * @param scope
   * @return
   */
  boolean hasAnyScopes(String... scope);
}

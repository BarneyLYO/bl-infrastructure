package cn.blinfra.boot.starter.security;

import cn.blinfra.boot.system.api.permission.PermissionApi;
import cn.hutool.core.collection.CollUtil;
import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public class SecurityFrameworkServiceImpl implements SecurityFrameworkService {

  private final PermissionApi permissionApi;

  @Override
  public boolean hasPermission(String permission) {
    return hasAnyPermissions(permission);
  }

  @Override
  public boolean hasAnyPermissions(String... permission) {
    return permissionApi.hasAnyPermissions(
      SecurityFrameworkUtils.getLoginUserId(),
      permission
    );
  }

  @Override
  public boolean hasRole(String role) {
    return hasAnyRoles(role);
  }

  @Override
  public boolean hasAnyRoles(String... roles) {
    return permissionApi.hasAnyRoles(SecurityFrameworkUtils.getLoginUserId(), roles);
  }

  @Override
  public boolean hasScope(String scope) {
    return hasAnyScopes(scope);
  }

  @Override
  public boolean hasAnyScopes(String... scope) {
    LoginUser user = SecurityFrameworkUtils.getLoginUser();
    if (user == null) {
      return false;
    }
    return CollUtil.containsAny(user.getScopes(), Arrays.asList(scope));
  }
}

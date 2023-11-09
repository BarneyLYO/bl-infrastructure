package cn.blinfra.boot.system.enums;

import cn.blinfra.boot.common.util.ObjectUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleCodeEnum {
  SUPER_ADMIN("super_admin", "超级管理员"),
  TENANT_ADMIN("tenant_admin", "租户管理员");
  private final String code;
  private final String name;

  public static boolean isSuperAdmin(String code) {
    return ObjectUtils.equalsAny(code, SUPER_ADMIN.getCode());
  }
}

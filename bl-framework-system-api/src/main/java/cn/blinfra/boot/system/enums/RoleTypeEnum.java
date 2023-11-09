package cn.blinfra.boot.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleTypeEnum {
  SYSTEM(1),
  CUSTOM(2);
  private final Integer type;
}

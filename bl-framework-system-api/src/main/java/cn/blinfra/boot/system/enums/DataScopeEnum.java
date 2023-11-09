package cn.blinfra.boot.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DataScopeEnum {
  ALL(1),
  DEPT_CUSTOM(2),
  DEPT_ONLY(3),
  DEPT_AND_CHILD(4),
  SELF(5)
  ;
  private final Integer scope;
}

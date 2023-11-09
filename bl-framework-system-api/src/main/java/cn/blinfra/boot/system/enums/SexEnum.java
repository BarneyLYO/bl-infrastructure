package cn.blinfra.boot.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SexEnum {
  MALE(1),
  FEMALE(2),
  UNKNOWN(3)
  ;

  private final Integer sex;
}

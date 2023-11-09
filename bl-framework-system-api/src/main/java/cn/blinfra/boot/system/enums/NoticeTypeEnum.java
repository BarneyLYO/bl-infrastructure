package cn.blinfra.boot.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NoticeTypeEnum {
  NOTICE(1),
  ANNOUNCEMENT(2)
  ;
  private final Integer type;
}

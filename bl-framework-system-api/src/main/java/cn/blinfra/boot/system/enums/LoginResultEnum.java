package cn.blinfra.boot.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LoginResultEnum {
  SUCCESS(0),
  BAD_CREDENTIALS(10),
  USER_DISABLED(20),
  CAPTCHA_NOT_FOUND(30),
  CAPTCHA_CODE_ERROR(31);
  private final Integer result;
}

package cn.blinfra.boot.system.enums;

import cn.hutool.core.util.ArrayUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Oauth2GrantTypeEnum {
  PASSWORD("password"),
  AUTHORIZATION_CODE("authorization_code"),
  IMPLICIT("implicit"),
  CLIENT_CREDENTIALS("client_credentials"),
  REFRESH_TOKEN("refresh_token");

  private final String grantType;

  public static Oauth2GrantTypeEnum getByGrantType(String grantType) {
    return ArrayUtil.firstMatch(o -> o.getGrantType().equals(grantType), values());
  }
}

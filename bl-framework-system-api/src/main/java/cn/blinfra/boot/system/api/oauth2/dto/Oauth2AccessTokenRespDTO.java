package cn.blinfra.boot.system.api.oauth2.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class Oauth2AccessTokenRespDTO implements Serializable {

  private String accessToken;

  private String refreshToken;

  private Long userId;

  private Integer userType;

  private LocalDateTime expiresTime;
}

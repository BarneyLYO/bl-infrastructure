package cn.blinfra.boot.system.api.oauth2;

import cn.blinfra.boot.system.api.oauth2.dto.Oauth2AccessTokenCheckRespDTO;
import cn.blinfra.boot.system.api.oauth2.dto.Oauth2AccessTokenCreateReqDTO;
import cn.blinfra.boot.system.api.oauth2.dto.Oauth2AccessTokenRespDTO;

import javax.validation.Valid;

/**
 * OAuth 2.0 token api 接口
 */
public interface Oauth2TokenApi {

  Oauth2AccessTokenRespDTO createAccessToken(@Valid Oauth2AccessTokenCreateReqDTO reqDTO);

  Oauth2AccessTokenCheckRespDTO checkAccessToken(String accessToken);

  Oauth2AccessTokenRespDTO removeAccessToken(String accessToken);

  Oauth2AccessTokenRespDTO refreshAccessToken(String refreshToken, String clientId);
}

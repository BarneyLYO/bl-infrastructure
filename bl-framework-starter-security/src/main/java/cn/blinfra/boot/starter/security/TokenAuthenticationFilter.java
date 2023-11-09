package cn.blinfra.boot.starter.security;

import cn.blinfra.boot.common.exception.ServiceException;
import cn.blinfra.boot.common.pojo.CommonResult;
import cn.blinfra.boot.common.util.ServletUtils;
import cn.blinfra.boot.starter.web.GlobalExceptionHandler;
import cn.blinfra.boot.starter.web.WebFrameworkUtils;
import cn.blinfra.boot.system.api.oauth2.Oauth2TokenApi;
import cn.blinfra.boot.system.api.oauth2.dto.Oauth2AccessTokenCheckRespDTO;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

  private final SecurityProperties securityProperties;

  private final GlobalExceptionHandler globalExceptionHandler;

  private final Oauth2TokenApi oauth2TokenApi;

  @Override
  @SuppressWarnings("NullableProblems")
  protected void doFilterInternal(
    HttpServletRequest request,
    HttpServletResponse response,
    FilterChain filterChain
  ) throws ServletException, IOException {
    String token = SecurityFrameworkUtils.obtainAuthorization(
      request,
      securityProperties.getTokenHeader()
    );

    if (StrUtil.isNotEmpty(token)) {
      Integer userType = WebFrameworkUtils.getLoginUserType(request);
      try {
        LoginUser loginUser = buildLoginUserByToken(token, userType);
        if (loginUser == null) {
          loginUser = mockLoginUser(request, token, userType);
        }

        if (loginUser != null) {
          SecurityFrameworkUtils.setLoginUser(loginUser, request);
        }
      }
      catch (Throwable ex) {
        CommonResult<?> result =
          globalExceptionHandler.allExceptionHandler(request, ex);
        ServletUtils.writeJSON(response, result);
        return;
      }
    }
    filterChain.doFilter(request, response);
  }

  private LoginUser buildLoginUserByToken(String token, Integer userType) {
    try {
      Oauth2AccessTokenCheckRespDTO
        accessTokenRespDTO = oauth2TokenApi.checkAccessToken(token);
      if (accessTokenRespDTO == null) {
        return null;
      }
      if (ObjectUtil.notEqual(accessTokenRespDTO.getUserType(), userType)) {
        throw new AccessDeniedException("错误用户类型");
      }
      return new LoginUser().setId(accessTokenRespDTO.getUserId())
                            .setUserType(accessTokenRespDTO.getUserType())
                            .setTenantId(accessTokenRespDTO.getTenantId())
                            .setScopes(accessTokenRespDTO.getScope());
    }
    catch (ServiceException serviceException) {
      return null;
    }
  }

  /**
   * 模拟登录用户，方便日常开发调试
   *
   * @param request
   * @param token    {@link SecurityProperties#getMockSecret()} + 用户编号
   * @param userType
   * @return 模拟的LoginUser
   */
  private LoginUser mockLoginUser(
    HttpServletRequest request,
    String token,
    Integer userType
  ) {
    if (securityProperties.getMockEnable()) {
      return null;
    }

    if (!token.startsWith(securityProperties.getMockSecret())) {
      return null;
    }

    Long userId =
      Long.valueOf(token.substring(securityProperties.getMockSecret().length()));

    return new LoginUser()
      .setId(userId)
      .setUserType(userType)
      .setTenantId(WebFrameworkUtils.getTenantId(request));
  }

}

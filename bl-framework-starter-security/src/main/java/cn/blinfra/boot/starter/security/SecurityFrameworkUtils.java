package cn.blinfra.boot.starter.security;

import cn.blinfra.boot.starter.web.WebFrameworkUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

public class SecurityFrameworkUtils {
  public static final String AUTHORIZATION_BEARER = "Bearer";

  /**
   * 请求中获得认证Token
   *
   * @param request 请求
   * @param header  认证Token 对应的Header
   * @return 认证Token
   */
  public static String obtainAuthorization(
    HttpServletRequest request,
    String header
  ) {
    String authorization = request.getHeader(header);
    if (!StringUtils.hasText(authorization)) {
      return null;
    }
    int index = authorization.indexOf(AUTHORIZATION_BEARER + " ");
    if (index == -1) {
      return null;
    }
    return authorization.substring(index + 7).trim();
  }

  public static Authentication getAuthentication() {
    SecurityContext context = SecurityContextHolder.getContext();
    if (context == null) {
      return null;
    }
    return context.getAuthentication();
  }

  @Nullable
  public static LoginUser getLoginUser() {
    Authentication authentication = getAuthentication();
    if (authentication == null) {
      return null;
    }
    Object principal = authentication.getPrincipal();
    if (principal instanceof LoginUser) {
      return (LoginUser) principal;
    }
    return null;
  }


  /**
   * 获取当前用户
   * @return 当前用户
   */
  @Nullable
  public static Long getLoginUserId() {
    LoginUser loginUser = getLoginUser();
    if (loginUser != null) {
      return loginUser.getId();
    }
    return null;
  }

  /**
   * 设置当前用户
   *
   * @param loginUser 登录用户
   * @param request   请求
   */
  public static void setLoginUser(LoginUser loginUser, HttpServletRequest request) {
    // 创建 Authentication， 并设置到security context中
    Authentication authentication = buildAuthentication(loginUser, request);
    SecurityContextHolder.getContext().setAuthentication(authentication);

    WebFrameworkUtils.setLoginUserId(request, loginUser.getId());
    WebFrameworkUtils.setLoginUserType(request, loginUser.getUserType());
  }

  private static Authentication buildAuthentication(
    LoginUser loginUser,
    HttpServletRequest request
  ) {
    UsernamePasswordAuthenticationToken authenticationToken =
      new UsernamePasswordAuthenticationToken(
        loginUser, null, Collections.emptyList()
      );

    authenticationToken.setDetails(
      new WebAuthenticationDetailsSource().buildDetails(request)
    );

    return authenticationToken;
  }

}

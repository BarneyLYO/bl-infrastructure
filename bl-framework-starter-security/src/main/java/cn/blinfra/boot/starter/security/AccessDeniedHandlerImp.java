package cn.blinfra.boot.starter.security;

import cn.blinfra.boot.common.constants.GlobalErrorCodeConstants;
import cn.blinfra.boot.common.pojo.CommonResult;
import cn.blinfra.boot.common.util.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 访问一个需要认证的URL资源，已经认证登录但是没有权限的情况下，返回
 * {@link cn.blinfra.boot.common.constants.GlobalErrorCodeConstants#FORBIDDEN}
 * 错误码
 * Spring Security 通过ExceptionTranslationFilter handleAccessDeniedException方法处理
 */
@Slf4j
@SuppressWarnings("JavadocReference")
public class AccessDeniedHandlerImp implements AccessDeniedHandler {
  @Override
  public void handle(
    HttpServletRequest request,
    HttpServletResponse response,
    AccessDeniedException accessDeniedException
  ) throws IOException, ServletException {
    log.warn(
      "[commence][访问({})]时,用户({})权限不够",
      request.getRequestURI(),
      SecurityFrameworkUtils.getLoginUserId(),
      accessDeniedException
    );

    ServletUtils.writeJSON(
      response,
      CommonResult.error(GlobalErrorCodeConstants.FORBIDDEN)
    );
  }
}

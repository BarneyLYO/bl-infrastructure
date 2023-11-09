package cn.blinfra.boot.starter.security;

import cn.blinfra.boot.common.constants.GlobalErrorCodeConstants;
import cn.blinfra.boot.common.pojo.CommonResult;
import cn.blinfra.boot.common.util.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 访问一个需要认证的URL资源，但是此时自己尚未认证（登录）的情况下，
 * 返回{@link cn.blinfra.boot.common.constants.GlobalErrorCodeConstants#UNAUTHORIZED}
 * 错误码，从而使前端能感知，并重定向到登录页
 */
@Slf4j
@SuppressWarnings("JavadocReference")
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
  @Override
  public void commence(
    HttpServletRequest request,
    HttpServletResponse response,
    AuthenticationException authException
  ) throws IOException, ServletException {
    log.debug(
      "[commence][访问URL({})时，没有登录]",
      request.getRequestURI(),
      authException
    );

    ServletUtils.writeJSON(
      response,
      CommonResult.error(GlobalErrorCodeConstants.UNAUTHORIZED)
    );
  }
}

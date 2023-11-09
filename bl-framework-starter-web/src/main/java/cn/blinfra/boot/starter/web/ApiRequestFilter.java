package cn.blinfra.boot.starter.web;

import cn.blinfra.boot.starter.web.WebProperties;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * 所有的request必须以当前controller配置的prefix开头，
 * 否则拒绝请求
 */
@RequiredArgsConstructor
public abstract class ApiRequestFilter extends OncePerRequestFilter {
  protected final WebProperties webProperties;

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request)
  throws ServletException {
    final boolean matched = StrUtil.startWithAny(
      request.getRequestURI(),
      webProperties.getAdminApi().getPrefix(),
      webProperties.getAppApi().getPrefix()
    );

    return !matched;
  }
}

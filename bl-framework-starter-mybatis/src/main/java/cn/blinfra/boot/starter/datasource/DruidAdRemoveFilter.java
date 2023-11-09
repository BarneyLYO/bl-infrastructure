package cn.blinfra.boot.starter.datasource;


import com.alibaba.druid.util.Utils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DruidAdRemoveFilter extends OncePerRequestFilter {
  private static final String COMMON_JS_ILE_PATH =
    "support/http/resources/js/common.js";

  @Override
  protected void doFilterInternal(
    HttpServletRequest request,
    HttpServletResponse response,
    FilterChain filterChain
  ) throws ServletException, IOException {
    filterChain.doFilter(request, response);
    /* reset the buffer area, response header will not be reset */
    response.resetBuffer();
    String text = Utils.readFromResource(COMMON_JS_ILE_PATH);
    text = text.replaceAll("<a.*?banner\"></a><br />", "");
    text = text.replaceAll("powered.*?shrek.wang</a>", "");
    response.getWriter().write(text);
  }
}

package cn.blinfra.boot.starter.web;

import cn.blinfra.boot.common.pojo.CommonResult;
import cn.blinfra.boot.starter.web.WebFrameworkUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 全局响应的处理器（ResponseBody）
 */
@ControllerAdvice
public class GlobalResponseBodyHandler implements ResponseBodyAdvice {

  @Override
  public boolean supports(MethodParameter returnType, Class converterType) {
    if (returnType.getMethod() == null) {
      return false;
    }
    /* 只拦截返回结果为CommonResult的类型 */
    return returnType.getMethod().getReturnType() == CommonResult.class;
  }

  @Override
  public Object beforeBodyWrite(
    Object body,
    MethodParameter returnType,
    MediaType selectedContentType,
    Class selectedConverterType,
    ServerHttpRequest request,
    ServerHttpResponse response
  ) {
    /*将body的内容缓存到attribute map中*/
    WebFrameworkUtils.setCommonResult(
      ((ServletServerHttpRequest) request).getServletRequest(),
      (CommonResult<?>) body
    );
    return body;
  }
}

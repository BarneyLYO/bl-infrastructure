package cn.blinfra.boot.starter.apilog;

import cn.blinfra.boot.common.constants.GlobalErrorCodeConstants;
import cn.blinfra.boot.common.pojo.CommonResult;
import cn.blinfra.boot.common.util.JsonUtils;
import cn.blinfra.boot.common.util.ServletUtils;
import cn.blinfra.boot.common.util.TraceUtils;
import cn.blinfra.boot.starter.apilog.service.ApiAccessLog;
import cn.blinfra.boot.starter.apilog.service.ApiAccessLogFrameworkService;
import cn.blinfra.boot.starter.web.WebProperties;
import cn.blinfra.boot.starter.web.WebFrameworkUtils;
import cn.blinfra.boot.starter.web.ApiRequestFilter;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.extra.servlet.ServletUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@Slf4j
public class ApiAccessLogFilter extends ApiRequestFilter {
  private final String applicationName;

  private final ApiAccessLogFrameworkService apiAccessLogFrameworkService;

  public ApiAccessLogFilter(
    WebProperties webProperties,
    String applicationName,
    ApiAccessLogFrameworkService apiAccessLogFrameworkService
  ) {
    super(webProperties);
    this.applicationName = applicationName;
    this.apiAccessLogFrameworkService = apiAccessLogFrameworkService;
  }

  @Override
  protected void doFilterInternal(
    HttpServletRequest request,
    HttpServletResponse response,
    FilterChain filterChain
  ) throws ServletException, IOException {
    LocalDateTime beginTime = LocalDateTime.now();
    Map<String, String> queryString = ServletUtil.getParamMap(request);
    String requestBody =
      ServletUtils.isJsonRequest(request)
      ? ServletUtil.getBody(request)
      : null;

    try {
      filterChain.doFilter(request, response);
      createApiAccessLog(request, beginTime, queryString, requestBody, null);
    }
    catch (Exception ex) {
      createApiAccessLog(request, beginTime, queryString, requestBody, ex);
      throw ex;
    }
  }

  private void createApiAccessLog(
    HttpServletRequest request,
    LocalDateTime beginTime,
    Map<String, String> queryString,
    String requestBody,
    Exception ex
  ) {
    ApiAccessLog accessLog = new ApiAccessLog();
    try {
      buildApiAccessLogDTO(
        accessLog,
        request,
        beginTime,
        queryString,
        requestBody,
        ex
      );
      apiAccessLogFrameworkService.createApiAccessLog(accessLog);
    }
    catch (Throwable th) {
      log.error(
        "[createApiAccessLog][url({}) log({}) 发生异常]",
        request.getRequestURI(),
        JsonUtils.toJsonString(accessLog),
        th
      );
    }
  }

  private void buildApiAccessLogDTO(
    ApiAccessLog accessLog,
    HttpServletRequest request,
    LocalDateTime beginTime,
    Map<String, String> queryString,
    String requestBody,
    Exception ex
  ) {
    accessLog.setUserId(WebFrameworkUtils.getLoginUserId());
    accessLog.setUserType(WebFrameworkUtils.getLoginUserType());
    CommonResult<?> result = WebFrameworkUtils.getCommonResult(request);

    if (result != null) {
      accessLog.setResultCode(result.getCode());
      accessLog.setResultMsg(result.getMsg());
    }
    else if (ex != null) {
      accessLog.setResultCode(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR.getCode());
      accessLog.setResultMsg(ExceptionUtil.getRootCauseMessage(ex));
    }
    else {
      accessLog.setResultCode(0);
      accessLog.setResultMsg("");
    }

    accessLog.setTraceId(TraceUtils.getTraceId());
    accessLog.setApplicationName(applicationName);
    accessLog.setRequestUrl(request.getRequestURI());
    Map<String, Object> requestParams = MapUtil.<String, Object>builder()
                                               .put("query", queryString)
                                               .put("body", requestBody)
                                               .build();

    accessLog.setRequestParams(JsonUtils.toJsonString(requestParams));
    accessLog.setRequestMethod(request.getMethod());
    accessLog.setUserAgent(ServletUtils.getUserAgent(request));
    accessLog.setUserIp(ServletUtil.getClientIP(request));
    accessLog.setBeginTime(beginTime);
    accessLog.setEndTime(LocalDateTime.now());
    accessLog.setDuration(
      (int) LocalDateTimeUtil.between(
        accessLog.getBeginTime(),
        accessLog.getEndTime(),
        ChronoUnit.SECONDS
      )
    );
  }


}

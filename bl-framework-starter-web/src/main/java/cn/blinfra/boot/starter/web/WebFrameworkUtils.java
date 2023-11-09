package cn.blinfra.boot.starter.web;

import cn.blinfra.boot.common.enums.UserTypeEnum;
import cn.blinfra.boot.common.pojo.CommonResult;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

public class WebFrameworkUtils {
  private static final String REQUEST_ATTRIBUTE_LOGIN_USER_ID = "login_user_id";
  private static final String REQUEST_ATTRIBUTE_LOGIN_USER_TYPE = "login_user_type";
  private static final String REQUEST_ATTRIBUTE_COMMON_RESULT = "common_result";

  public static final String HEADER_TENANT_ID = "tenant-id";

  private static WebProperties properties;

  public WebFrameworkUtils(WebProperties webProperties) {
    WebFrameworkUtils.properties = webProperties;
  }

  /**
   * 从request当中抽取TenantID
   *
   * @param request
   * @return TenantID
   */
  public static Long getTenantId(HttpServletRequest request) {
    String tenantId = request.getHeader(HEADER_TENANT_ID);
    return StrUtil.isNotEmpty(tenantId) ? Long.valueOf(tenantId) : null;
  }

  public static void setLoginUserId(ServletRequest request, Long userId) {
    request.setAttribute(REQUEST_ATTRIBUTE_LOGIN_USER_ID, userId);
  }

  public static void setLoginUserType(HttpServletRequest request, Integer userType) {
    request.setAttribute(REQUEST_ATTRIBUTE_LOGIN_USER_TYPE, userType);
  }

  public static Long getLoginUserId(HttpServletRequest request) {
    if (ObjectUtil.isNull(request))
      return null;
    return (Long) request.getAttribute(REQUEST_ATTRIBUTE_LOGIN_USER_ID);
  }

  public static Integer getLoginUserType(HttpServletRequest request) {
    if (ObjectUtil.isNull(request))
      return null;
    Integer userType =
      (Integer) request.getAttribute(REQUEST_ATTRIBUTE_LOGIN_USER_TYPE);
    if (request.getRequestURI().startsWith(properties.getAdminApi().getPrefix())) {
      return UserTypeEnum.ADMIN.getValue();
    }
    if (request.getRequestURI().startsWith(properties.getAppApi().getPrefix())) {
      return UserTypeEnum.MEMBER.getValue();
    }
    return null;
  }

  public static HttpServletRequest getRequest() {
    RequestAttributes requestAttributes =
      RequestContextHolder.getRequestAttributes();

    if (requestAttributes instanceof ServletRequestAttributes) {
      ServletRequestAttributes servletRequestAttributes =
        (ServletRequestAttributes) requestAttributes;

      return servletRequestAttributes.getRequest();
    }

    return null;
  }

  public static Integer getLoginUserType() {
    return getLoginUserType(getRequest());
  }

  public static Long getLoginUserId() {
    return getLoginUserId(getRequest());
  }

  public static void setCommonResult(
    ServletRequest request,
    CommonResult<?> result
  ) {
    request.setAttribute(REQUEST_ATTRIBUTE_COMMON_RESULT, result);
  }

  public static CommonResult<?> getCommonResult(ServletRequest request) {
    return (CommonResult<?>) request.getAttribute(REQUEST_ATTRIBUTE_COMMON_RESULT);
  }

}

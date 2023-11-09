package cn.blinfra.boot.starter.security;

import cn.blinfra.boot.common.constants.GlobalErrorCodeConstants;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import static cn.blinfra.boot.common.util.ServiceExceptionUtils.exception;

@Aspect
@Slf4j
public class PreAuthenticatedAspect {

  @Around("@annotation(preAuthenticated)")
  public Object around(
    ProceedingJoinPoint joinPoint,
    PreAuthenticated preAuthenticated
  )
  throws Throwable {
    if (SecurityFrameworkUtils.getLoginUser() == null) {
      throw exception(GlobalErrorCodeConstants.UNAUTHORIZED);
    }
    return joinPoint.proceed();
  }
}

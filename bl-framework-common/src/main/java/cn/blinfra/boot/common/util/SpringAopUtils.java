package cn.blinfra.boot.common.util;

import cn.hutool.core.bean.BeanUtil;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.support.AopUtils;

public class SpringAopUtils {
  public static Object getTarget(Object proxy) throws Exception {
    if (!AopUtils.isAopProxy(proxy)) {
      return proxy;
    }

    if (AopUtils.isJdkDynamicProxy(proxy)) {
      return getJdkDynamicProxyTargetObject(proxy);
    }

    return getCglibProxyTargetObject(proxy);

  }

  private static Object getCglibProxyTargetObject(Object proxy) throws Exception {
    Object dynamicAdvisedInterceptor
      = BeanUtil.getFieldValue(proxy, "CGLIB$CALLBACK_0");

    AdvisedSupport advisedSupport =
      (AdvisedSupport) BeanUtil.getFieldValue(
        dynamicAdvisedInterceptor,
        "advised"
      );

    return advisedSupport.getTargetSource().getTarget();
  }

  private static Object getJdkDynamicProxyTargetObject(Object proxy)
  throws Exception {
    AopProxy aopProxy = (AopProxy) BeanUtil.getFieldValue(proxy, "h");

    AdvisedSupport advisedSupport =
      (AdvisedSupport) BeanUtil.getFieldValue(aopProxy, "advised");

    return advisedSupport.getTargetSource().getTarget();
  }

}

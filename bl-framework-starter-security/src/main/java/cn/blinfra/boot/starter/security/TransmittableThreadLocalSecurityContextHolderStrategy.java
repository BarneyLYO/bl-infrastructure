package cn.blinfra.boot.starter.security;

import cn.hutool.core.lang.Assert;
import com.alibaba.ttl.TransmittableThreadLocal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.context.SecurityContextImpl;

public class TransmittableThreadLocalSecurityContextHolderStrategy
  implements SecurityContextHolderStrategy {

  private static final ThreadLocal<SecurityContext> CONTEXT_HOLDER
    = new TransmittableThreadLocal<>();


  @Override
  public void clearContext() {
    CONTEXT_HOLDER.remove();
  }

  @Override
  public SecurityContext getContext() {
    SecurityContext ctx = CONTEXT_HOLDER.get();
    if (ctx == null) {
      ctx = createEmptyContext();
      CONTEXT_HOLDER.set(ctx);
    }
    return ctx;
  }

  @Override
  public void setContext(SecurityContext context) {
    Assert
      .notNull(
        context,
        "Only non-null SecurityContext instances are permitted"
      );
    CONTEXT_HOLDER.set(context);
  }

  @Override
  public SecurityContext createEmptyContext() {
    return new SecurityContextImpl();
  }
}

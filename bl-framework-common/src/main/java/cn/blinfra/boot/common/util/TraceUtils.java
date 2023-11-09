package cn.blinfra.boot.common.util;

import org.apache.skywalking.apm.toolkit.trace.TraceContext;

public class TraceUtils {
  public static String getTraceId() {
    return TraceContext.traceId();
  }
}

package cn.blinfra.boot.common.util;

import cn.blinfra.boot.common.exception.ServiceException;
import cn.blinfra.boot.common.model.ErrorCode;
import com.google.common.annotations.VisibleForTesting;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
abstract public class ServiceExceptionUtils {
  private static final ConcurrentHashMap<Integer, String> MESSAGES
    = new ConcurrentHashMap<>();

  public static void putAll(Map<Integer, String> messages) {
    ServiceExceptionUtils.MESSAGES.putAll(messages);
  }

  public static void put(Integer code, String message) {
    ServiceExceptionUtils.MESSAGES.put(code, message);
  }

  public static void delete(Integer code, String message) {
    ServiceExceptionUtils.MESSAGES.remove(code, message);
  }

  public static ServiceException exception(ErrorCode errorCode) {
    String messagePatern =
      MESSAGES.getOrDefault(errorCode.getCode(), errorCode.getMsg());
    return exception0(errorCode.getCode(), messagePatern);
  }

  public static ServiceException exception(ErrorCode errorCode, Object... params) {
    String messagePattern =
      MESSAGES.getOrDefault(errorCode.getCode(), errorCode.getMsg());
    return exception0(errorCode.getCode(), messagePattern, params);
  }

  public static ServiceException exception(Integer code) {
    return exception0(code, MESSAGES.get(code));
  }

  public static ServiceException exception(Integer code, Object... params) {
    return exception0(code, MESSAGES.get(code), params);
  }

  public static ServiceException exception0(
    Integer code, String messagePattern, Object... params
  ) {
    String message = doFormat(code, messagePattern, params);
    return new ServiceException(code, message);
  }

  @VisibleForTesting
  public static String doFormat(int code, String messagePattern, Object... params) {
    StringBuilder sbuf = new StringBuilder(messagePattern.length() + 50);
    int i = 0;
    int j;
    int l;
    for (l = 0; l < params.length; l++) {
      j = messagePattern.indexOf("{}", i);
      if (j != -1) {
        // end j == -1
        sbuf.append(messagePattern, i, j);
        sbuf.append(params[l]);
        i = j + 2;
        continue;
      }
      log.error(
        "[doFormat][参数过多:错误码({})|错误内容({})|参数({})]",
        code,
        messagePattern,
        params
      );
      if (i == 0) {
        return messagePattern;
      }
      sbuf.append(messagePattern.substring(i));
      return sbuf.toString();
    }

    if (messagePattern.indexOf("{}", i) != -1) {
      log.error(
        "[doFormat][参数过少:错误码({})|错误内容({})|参数({})]",
        code,
        messagePattern,
        params
      );
    }
    sbuf.append(messagePattern.substring(i));
    return sbuf.toString();
  }

  private ServiceExceptionUtils() {}

}

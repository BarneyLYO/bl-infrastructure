package cn.blinfra.boot.common.constants;

import cn.blinfra.boot.common.model.ErrorCode;

public interface GlobalErrorCodeConstants {
  ErrorCode SUCCESS = new ErrorCode(0, "成功");

  ErrorCode BAD_REQUEST = new ErrorCode(400, "请求参数不正确");
  ErrorCode UNAUTHORIZED = new ErrorCode(401, "账号为登陆");
  ErrorCode FORBIDDEN = new ErrorCode(403, "没有操作权限");
  ErrorCode NOT_FOUND = new ErrorCode(404, "请求未找到");
  ErrorCode METHOD_NOT_ALLOW = new ErrorCode(405, "请求方法不正确");
  ErrorCode LOCKED = new ErrorCode(423, "请求失败，请稍后重试");
  ErrorCode TOO_MANY_REQUESTS = new ErrorCode(429, "请求过于平凡，请稍后重试");

  ErrorCode INTERNAL_SERVER_ERROR = new ErrorCode(500, "系统异常");
  ErrorCode NOT_IMPLEMENTED = new ErrorCode(501, "功能未开启");

  ErrorCode REPEATED_REQUESTS = new ErrorCode(900, "重复请求，请稍后重试");
  ErrorCode UNKOWN = new ErrorCode(999, "未知错误");

  static boolean isServerErrorCode (Integer code) {
    return (
      code != null
      && code >= INTERNAL_SERVER_ERROR.getCode()
      && code <= INTERNAL_SERVER_ERROR.getCode() + 99
      );
  }


}

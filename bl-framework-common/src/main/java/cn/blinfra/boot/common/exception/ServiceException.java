package cn.blinfra.boot.common.exception;

import cn.blinfra.boot.common.model.ErrorCode;

public class ServiceException extends BackendException {

  public ServiceException() {
    super();
  }

  public ServiceException(ErrorCode code) {
    super(code.getCode(), code.getMsg());
  }

  public ServiceException(Integer code, String message) {
    super(code, message);
  }
}

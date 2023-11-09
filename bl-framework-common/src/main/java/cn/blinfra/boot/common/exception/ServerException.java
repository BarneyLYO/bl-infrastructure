package cn.blinfra.boot.common.exception;

import cn.blinfra.boot.common.model.ErrorCode;

public class ServerException extends BackendException{

  public ServerException() {
    super();
  }

  public ServerException(ErrorCode code) {
    super(code.getCode(), code.getMsg());
  }

  public ServerException(Integer code, String message) {
    super(code, message);
  }
}

package cn.blinfra.boot.common.pojo;

import cn.blinfra.boot.common.constants.GlobalErrorCodeConstants;
import cn.blinfra.boot.common.exception.ServerException;
import cn.blinfra.boot.common.exception.ServiceException;
import cn.blinfra.boot.common.model.ErrorCode;
import cn.hutool.core.lang.Assert;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
public class CommonResult<T> implements Serializable {

  /**
   * @see ErrorCode#getCode()
   */
  private Integer code;

  /**
   * return data
   */
  private T data;

  /**
   * @see ErrorCode#getMsg()
   */
  private String msg;

  public static <T> CommonResult<T> error(Integer code, String message) {
    Assert.isTrue(
      !GlobalErrorCodeConstants.SUCCESS.getCode().equals(code),
      "code must be wrong"
    );

    CommonResult<T> result = new CommonResult<>();
    result.code = code;
    result.msg = message;
    return result;
  }

  public static <T> CommonResult<T> error(CommonResult<?> result) {
    return error(result.getCode(), result.getMsg());
  }

  public static <T> CommonResult<T> error(ErrorCode errorCode) {
    return error(errorCode.getCode(), errorCode.getMsg());
  }

  public static <T> CommonResult<T> error(ServiceException serviceException) {
    return error(serviceException.getCode(), serviceException.getMessage());
  }

  public static <T> CommonResult<T> success(T data) {
    CommonResult<T> result = new CommonResult<>();
    result.code = GlobalErrorCodeConstants.SUCCESS.getCode();
    result.data = data;
    result.msg = "";
    return result;
  }

  public static boolean isSuccess(Integer code) {
    return Objects.equals(code, GlobalErrorCodeConstants.SUCCESS.getCode());
  }

  @JsonIgnore // use JsonIgnore to tell jackson this is not property
  public boolean isSuccess() {
    return isSuccess(code);
  }

  @JsonIgnore
  public boolean isError() {
    return !isSuccess();
  }

  public void checkError() throws ServiceException {
    if (isSuccess())
      return;

    if (GlobalErrorCodeConstants.isServerErrorCode(code)) {
      throw new ServerException(code, msg);
    }
    throw new ServiceException(code, msg);
  }

  @JsonIgnore
  public T getCheckedData() {
    checkError();
    return data;
  }

}

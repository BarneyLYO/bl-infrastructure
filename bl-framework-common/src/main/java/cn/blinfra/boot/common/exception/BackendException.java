package cn.blinfra.boot.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
abstract public class BackendException extends RuntimeException {

  private Integer code;
  private String message;

  @Override
  public String getMessage() {
    return message;
  }
}

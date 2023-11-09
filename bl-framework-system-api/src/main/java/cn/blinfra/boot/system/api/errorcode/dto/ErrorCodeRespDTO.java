package cn.blinfra.boot.system.api.errorcode.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorCodeRespDTO {

  /**
   * 错误码编码
   */
  private Integer code;

  /**
   * 错误码错误提示
   */
  private String message;

  /**
   * 更新时间
   */
  private LocalDateTime updateTime;
}

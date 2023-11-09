package cn.blinfra.boot.system.api.dict.dto;

import lombok.Data;

@Data
public class DictDataRespDTO {

  /**
   * 字典标签
   */
  private String label;

  /**
   * 字典值
   */
  private String value;

  /**
   * 字典类型
   */
  private String dictType;

  /**
   * 状态
   * 枚举 {@link cn.blinfra.boot.common.enums.CommonStatusEnum}
   */
  private Integer Status;
}

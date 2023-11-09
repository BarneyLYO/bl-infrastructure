package cn.blinfra.boot.system.enums;

import cn.blinfra.boot.common.model.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ErrorCodeTypeEnum implements IntArrayValuable {
  AUTO_GENERATION(1),
  MANUAL_OPERATION(2);
  public static final int[] ARRAYS =
    Arrays.stream(values()).mapToInt(ErrorCodeTypeEnum::getType).toArray();

  private final Integer type;

  @Override
  public int[] array() {
    return ARRAYS;
  }
}

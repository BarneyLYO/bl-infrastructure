package cn.blinfra.boot.common.enums;

import cn.blinfra.boot.common.model.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum CommonStatusEnum implements IntArrayValuable {
  ENABLE(0, "开启"),
  DISABLE(1, "关闭");

  public static final int[] ARRAY =
    Arrays.stream(values())
          .mapToInt(CommonStatusEnum::getStatus)
          .toArray();

  private final Integer status;

  private final String name;

  @Override
  public int[] array() {
    return ARRAY;
  }
}

package cn.blinfra.boot.common.enums;

import cn.blinfra.boot.common.model.IntArrayValuable;
import cn.hutool.core.util.ArrayUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum UserTypeEnum implements IntArrayValuable {
  MEMBER(1, "用户"),
  ADMIN(2, "管理员");

  private final Integer value;
  private final String name;

  public static final int[] ARRAY =
    Arrays.stream(values())
          .mapToInt(UserTypeEnum::getValue)
          .toArray();

  public static UserTypeEnum valueOf(Integer value) {
    return ArrayUtil.firstMatch(
      userTypeEnum -> userTypeEnum.getValue()
                                  .equals(value),
      UserTypeEnum.values()
    );
  }


  @Override
  public int[] array() {
    return ARRAY;
  }
}

package cn.blinfra.boot.common.util;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class StrUtils {
  /**
   * 限制字符串长度，如果超过指定长度，截取指定长度并在末尾加"..."
   *
   * @param str
   * @param maxLength
   * @return
   */
  public static String maxLength(CharSequence str, int maxLength) {
    return StrUtil.maxLength(str, maxLength - 3);
  }

  /**
   * check if a string is start by any item in the prefixes collections
   *
   * @param str      target string
   * @param prefixes prefix collection
   */
  public static boolean startWithAny(String str, Collection<String> prefixes) {
    if (StrUtil.isEmpty(str) || ArrayUtil.isEmpty(prefixes)) {
      return false;
    }

    for (CharSequence p : prefixes) {
      if (StrUtil.startWith(str, p, false)) {
        return true;
      }
    }
    return false;
  }

  public static List<Long> splitToLong(String value, CharSequence separator) {
    long[] longs = StrUtil.splitToLong(value, separator);
    return Arrays.stream(longs).boxed().collect(Collectors.toList());
  }
}

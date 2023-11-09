package cn.blinfra.boot.common.util;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;

import java.io.InputStream;

public class IoUtils {
  public static String readUtf8(InputStream in, boolean isClose) {
    return StrUtil.utf8Str(IoUtil.read(in, isClose));
  }
}

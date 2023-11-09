package cn.blinfra.boot.common.util;

import cn.hutool.core.util.StrUtil;

public class NumberUtils {
  public static Long parseLong(String str) {
    return StrUtil.isNotEmpty(str) ? Long.valueOf(str) : null;
  }

  public static double getDistance(double lat1, double lng1, double lat2, double lng2) {
    double radLat1 = lat1 * Math.PI / 180;
    double radLat2 = lat2 * Math.PI / 180;

    double a = radLat1 - radLat2;
    double b = lng1 * Math.PI / 180 - lng2 * Math.PI / 180;
    final double part1 = Math.pow(Math.sin(a / 2), 2);
    final double part2 =
      Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2);

    double distance = 2 * Math.asin(Math.sqrt(part1 + part2));
    distance = distance * 6378.137;
    distance = Math.round(distance * 10000D) / 10000D;
    return distance;
  }
}

package cn.blinfra.boot.common.util;

import cn.blinfra.boot.common.pojo.PageParam;

public class PageUtils {
  public static int getStart(PageParam param) {
    return (param.getPageNo() - 1) * param.getPageSize();
  }
}

package cn.blinfra.boot.starter.xss;

public interface XssCleaner {

  /**
   * 清理有Xss的文本
   * @param html
   * @return html after clean
   */
  String clean(String html);
}

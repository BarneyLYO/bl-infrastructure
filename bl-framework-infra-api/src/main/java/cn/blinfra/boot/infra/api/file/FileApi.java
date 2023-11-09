package cn.blinfra.boot.infra.api.file;

public interface FileApi {

  default String createFile(byte[] content) {
    return createFile(null, null, content);
  }

  default String createFile(String path, byte[] content) {
    return createFile(null, path, content);
  }

  /**
   * 保存文件，并且返回文件的访问路径
   *
   * @param name    文件名
   * @param path    文件路径
   * @param content 文件内容
   * @return 文件路径
   */
  String createFile(String name, String path, byte[] content);
}

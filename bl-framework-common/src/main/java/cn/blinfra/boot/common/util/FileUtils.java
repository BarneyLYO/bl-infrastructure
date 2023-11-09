package cn.blinfra.boot.common.util;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import lombok.SneakyThrows;

import java.io.ByteArrayInputStream;
import java.io.File;

public class FileUtils {

  @SneakyThrows
  public static File createTempFile() {
    File file = File.createTempFile(IdUtil.simpleUUID(), null);
    file.deleteOnExit();
    return file;
  }

  public static File createTempFile(byte[] data) {
    File file = createTempFile();
    FileUtil.writeBytes(data, file);
    return file;
  }

  public static File createTempFile(String data) {
    File file = createTempFile();
    FileUtil.writeUtf8String(data, file);
    return file;
  }

  public static String generatePath(byte[] content, String originalName) {
    String sha256Hex = DigestUtil.sha256Hex(content);
    if (StrUtil.isNotBlank(originalName)) {
      String extName = FileNameUtil.extName(originalName);
      return StrUtil.isBlank(extName) ? sha256Hex : sha256Hex + "." + extName;
    }
    return sha256Hex + '.' + FileTypeUtil.getType(new ByteArrayInputStream(content));
  }
}

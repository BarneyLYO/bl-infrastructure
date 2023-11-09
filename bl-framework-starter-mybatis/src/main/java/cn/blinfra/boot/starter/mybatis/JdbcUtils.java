package cn.blinfra.boot.starter.mybatis;


import com.baomidou.mybatisplus.annotation.DbType;

import java.sql.Connection;
import java.sql.DriverManager;

public class JdbcUtils {

  public static boolean isConnectionOK(
    String url,
    String username,
    String password
  ) {
    try (Connection ignored = DriverManager.getConnection(url, username, password)) {
      return true;
    }
    catch (Exception ex) {
      return false;
    }
  }

  public static DbType getDbType(String url) {
    String name = com.alibaba.druid.util.JdbcUtils.getDbType(url, null);
    return DbType.getDbType(name);
  }
}

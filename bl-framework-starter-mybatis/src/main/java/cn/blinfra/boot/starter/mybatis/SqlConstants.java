package cn.blinfra.boot.starter.mybatis;


import com.baomidou.mybatisplus.annotation.DbType;

public class SqlConstants {

  public static DbType DB_TYPE;

  public static void init(DbType dbType) {
    DB_TYPE = dbType;
  }
}

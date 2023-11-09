package cn.blinfra.boot.starter.mybatis;

import cn.blinfra.boot.common.util.SetUtils;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Objects;
import java.util.Set;

@Slf4j
public class IdTypeEnvironmentPostProcessor implements EnvironmentPostProcessor {

  private static final String ID_TYPE_KEY
    = "mybatis-plus.global-config.db-config.id-type";

  private static final String DATASOURCE_DYNAMIC_KEY
    = "spring.datasource.dynamic";

  private static final String QUARTZ_JOB_STORE_DRIVER_KEY
    = "spring.quartz.properties.org.quartz.jobStore.driverDelegateClass";

  private static final Set<DbType> INPUT_ID_TYPES = SetUtils.asSet(
    DbType.ORACLE,
    DbType.ORACLE_12C,
    DbType.POSTGRE_SQL,
    DbType.KINGBASE_ES,
    DbType.DB2,
    DbType.H2
  );

  @Override
  public void postProcessEnvironment(
    ConfigurableEnvironment environment,
    SpringApplication application
  ) {
    DbType dbType = getDbType(environment);
    if(Objects.isNull(dbType)) return;

    setJobStoreDriverIfPresent(environment, dbType);

    SqlConstants.init(dbType);

    IdType idType = getIdType(environment);
    if(idType != IdType.NONE) return;

    // Oracle, PostgreSQL, Kingbase, DB2, H2, 用户输入ID
    if(INPUT_ID_TYPES.contains(dbType)) {
      setIdType(environment, IdType.INPUT);
      return;
    }

    // Mysql 自增ID
    setIdType(environment, IdType.AUTO);
  }

  public IdType getIdType(ConfigurableEnvironment environment) {
    return environment.getProperty(ID_TYPE_KEY, IdType.class);
  }

  public void setIdType(ConfigurableEnvironment environment, IdType idType) {
    environment.getSystemProperties().put(ID_TYPE_KEY, idType);
    log.info("[setIdType][修改 MyBatis + 的idType 为 ({})]", idType);
  }

  public void setJobStoreDriverIfPresent(
    ConfigurableEnvironment environment,
    DbType dbType
  ) {
    String driverClass = environment.getProperty(QUARTZ_JOB_STORE_DRIVER_KEY);
    if (StrUtil.isNotEmpty(driverClass)) {
      return;
    }

    if (dbType == DbType.POSTGRE_SQL) {
      driverClass = "org.quartz.impl.jdbcjobstore.PostgreSQLDelegate";
    }
    else if (dbType == DbType.ORACLE || dbType == DbType.ORACLE_12C) {
      driverClass = "org.quartz.impl.jdbcjobstore.oracle.OracleDelegate";
    }
    else if (dbType == DbType.SQL_SERVER || dbType == DbType.SQL_SERVER2005) {
      driverClass = "org.quarz.impl.jdbcjobstore.MSSQLDelegate";
    }

    if (StrUtil.isNotEmpty(driverClass)) {
      environment.getSystemProperties()
                 .put(QUARTZ_JOB_STORE_DRIVER_KEY, driverClass);
    }
  }

  public static DbType getDbType(ConfigurableEnvironment environment) {
    String primary =
      environment.getProperty(DATASOURCE_DYNAMIC_KEY + "." + "primary");
    if (StrUtil.isEmpty(primary)) {
      return null;
    }

    String url = environment.getProperty(
      DATASOURCE_DYNAMIC_KEY + ".datasource." + primary + ".url");

    if (StrUtil.isEmpty(url)) {
      return null;
    }

    return JdbcUtils.getDbType(url);
  }
}

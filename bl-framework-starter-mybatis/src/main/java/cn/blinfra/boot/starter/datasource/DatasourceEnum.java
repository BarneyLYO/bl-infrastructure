package cn.blinfra.boot.starter.datasource;

/**
 * 多数据源中不同数据源配置
 * 通过在方法上使用{@link com.baomidou.dynamic.datasource.annotation.DS} 注解,设置使用的数据源
 *
 */
public class DatasourceEnum {

  String MASTER = "master";

  String SLAVE = "slave";
}

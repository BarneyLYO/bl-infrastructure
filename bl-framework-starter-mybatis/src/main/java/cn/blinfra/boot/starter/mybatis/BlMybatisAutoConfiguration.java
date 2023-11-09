package cn.blinfra.boot.starter.mybatis;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.incrementer.IKeyGenerator;
import com.baomidou.mybatisplus.extension.incrementer.H2KeyGenerator;
import com.baomidou.mybatisplus.extension.incrementer.KingbaseKeyGenerator;
import com.baomidou.mybatisplus.extension.incrementer.OracleKeyGenerator;
import com.baomidou.mybatisplus.extension.incrementer.PostgreKeyGenerator;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;

@AutoConfiguration
@MapperScan(
  value = "${bl.info.base-package}",
  annotationClass = Mapper.class,
  lazyInitialization = "${mybatis.lazy-initialization:false}"
)
public class BlMybatisAutoConfiguration {

  @Bean
  public MybatisPlusInterceptor mybatisPlusInterceptor() {
    MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
    mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
    return mybatisPlusInterceptor;
  }

  @Bean
  public MetaObjectHandler defaultMetaObjectHandler() {
    return new DefaultDBFieldHandler();
  }

  @Bean
  @ConditionalOnProperty(
    prefix = "mybatis-plus.global-config.db-config",
    name = "id-type",
    havingValue = "INPUT"
  )
  public IKeyGenerator keyGenerator(ConfigurableEnvironment environment) {
    DbType dbType = IdTypeEnvironmentPostProcessor.getDbType(environment);
    if (dbType == null) {
      throw new IllegalArgumentException(StrUtil.format(
        "DbType{} 找不到合适的IKeyGenerator实现类"));
    }

    if (dbType == DbType.POSTGRE_SQL) {
      return new PostgreKeyGenerator();
    }

    if (dbType == DbType.ORACLE || dbType == DbType.ORACLE_12C) {
      return new OracleKeyGenerator();
    }

    if (dbType == DbType.H2) {
      return new H2KeyGenerator();
    }

    if (dbType == DbType.KINGBASE_ES) {
      return new KingbaseKeyGenerator();
    }

    throw new IllegalArgumentException(StrUtil.format(
      "DbType{} 找不到合适的IKeyGenerator实现类",
      dbType
    ));

  }


}

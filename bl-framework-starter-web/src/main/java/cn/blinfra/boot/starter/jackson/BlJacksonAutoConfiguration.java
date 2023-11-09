package cn.blinfra.boot.starter.jackson;

import cn.blinfra.boot.common.util.JsonUtils;
import cn.blinfra.boot.starter.jackson.LocalDateTimeDeserializer;
import cn.blinfra.boot.starter.jackson.LocalDateTimeSerializer;
import cn.blinfra.boot.starter.jackson.NumberSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@AutoConfiguration
@Slf4j
public class BlJacksonAutoConfiguration {

  @Bean
  public BeanPostProcessor objectMapperBeanPostProcessor() {
    return new BlJacksonBeanPostProcessor();
  }

  public static final class BlJacksonBeanPostProcessor
    implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
    throws BeansException {

      if (bean instanceof ObjectMapper) {
        ObjectMapper objectMapper = (ObjectMapper) bean;
        SimpleModule simpleModule = new SimpleModule();

        simpleModule
          .addSerializer(Long.class, NumberSerializer.INSTANCE)
          .addSerializer(Long.TYPE, NumberSerializer.INSTANCE)
          .addSerializer(LocalDateTime.class, LocalDateTimeSerializer.INSTANCE)
          .addDeserializer(LocalDateTime.class, LocalDateTimeDeserializer.INSTANCE);

        objectMapper.registerModules(simpleModule);
        JsonUtils.init(objectMapper);
        log.info("Jackson 自动化配置结束");
        return bean;
      }

      return bean;
    }
  }

}

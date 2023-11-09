package cn.blinfra.boot.starter.mybatis;

import cn.blinfra.boot.common.util.JsonUtils;
import com.baomidou.mybatisplus.extension.handlers.AbstractJsonTypeHandler;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Set;

public class JsonLongSetTypeHandler extends AbstractJsonTypeHandler<Object> {
  private static final
  TypeReference<Set<Long>> TYPE_REFERENCE = new TypeReference<Set<Long>>() {};

  @Override
  protected Object parse(String json) {
    return JsonUtils.parseObject(json, TYPE_REFERENCE);
  }

  @Override
  protected String toJson(Object obj) {
    return JsonUtils.toJsonString(obj);
  }
}

package cn.blinfra.boot.starter.mybatis;

import cn.hutool.core.util.StrUtil;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.util.List;
import java.util.Optional;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.COMMA;

@MappedJdbcTypes(JdbcType.VARCHAR)
@MappedTypes(List.class)
public class StringListTypeHandler extends ListTypeHandler<String> {

  @Override
  protected List<String> getResult(String value) {
    return Optional.ofNullable(value)
                   .map(s -> StrUtil.splitTrim(s, COMMA))
                   .orElse(null);
  }
}

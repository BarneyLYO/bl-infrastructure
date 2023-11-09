package cn.blinfra.boot.starter.mybatis;

import cn.blinfra.boot.common.util.StrUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.util.List;
import java.util.Optional;

@MappedJdbcTypes(JdbcType.VARCHAR)
@MappedTypes(List.class)
public class LongListTypeHandler extends ListTypeHandler<Long> {
  @Override
  protected List<Long> getResult(String val) {
    return Optional.ofNullable(val)
                   .map(v -> StrUtils.splitToLong(v, StringPool.COMMA))
                   .orElse(null);
  }
}

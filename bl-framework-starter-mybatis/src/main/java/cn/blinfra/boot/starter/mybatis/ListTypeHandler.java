package cn.blinfra.boot.starter.mybatis;

import cn.hutool.core.collection.CollUtil;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.COMMA;

public abstract class ListTypeHandler<T> implements TypeHandler<List<T>> {
  @Override
  public void setParameter(
    PreparedStatement preparedStatement,
    int i,
    List<T> strings,
    JdbcType jdbcType
  ) throws SQLException {
    preparedStatement.setString(i, CollUtil.join(strings, COMMA));
  }

  @Override
  public List<T> getResult(ResultSet resultSet, String s) throws SQLException {
    String value = resultSet.getString(s);
    return getResult(value);
  }

  @Override
  public List<T> getResult(ResultSet resultSet, int i) throws SQLException {
    String value = resultSet.getString(i);
    return getResult(value);
  }

  @Override
  public List<T> getResult(CallableStatement callableStatement, int i)
  throws SQLException {
    String value = callableStatement.getString(i);
    return getResult(value);
  }

  protected abstract List<T> getResult(String val);
}

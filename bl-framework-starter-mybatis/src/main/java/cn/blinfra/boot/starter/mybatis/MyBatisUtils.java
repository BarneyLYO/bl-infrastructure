package cn.blinfra.boot.starter.mybatis;

import cn.blinfra.boot.common.pojo.PageParam;
import cn.blinfra.boot.common.pojo.SortingField;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MyBatisUtils {

  private static final OrderItem mapSortingField(SortingField sortingField) {
    if (SortingField.ORDER_ASC.equals(sortingField.getOrder())) {
      return OrderItem.asc(sortingField.getField());
    }
    return OrderItem.desc(sortingField.getField());
  }

  private static final String MYSQL_ESCAPE_CHARACTER = "`";

  public static <T> Page<T> buildPage(PageParam pageParam) {
    return buildPage(pageParam, null);
  }

  private static <T> Page<T> buildPage(
    PageParam pageParam,
    Collection<SortingField> sortingFields
  ) {
    Page<T> page = new Page<>(pageParam.getPageNo(), pageParam.getPageSize());
    if (CollectionUtil.isEmpty(sortingFields)) {
      return page;
    }

    page.addOrder(
      sortingFields.stream()
                   .map(MyBatisUtils::mapSortingField)
                   .collect(Collectors.toList())
    );
    return page;
  }

  /**
   * 将拦截器加在mybatis链中
   *
   * @param interceptor
   * @param inner
   * @param index
   */
  public static void addInterceptor(
    MybatisPlusInterceptor interceptor,
    InnerInterceptor inner,
    int index
  ) {
    List<InnerInterceptor> inners = new ArrayList<>(interceptor.getInterceptors());
    inners.add(index, inner);
    interceptor.setInterceptors(inners);
  }

  public static String getTableName(Table table) {
    String tableName = table.getName();
    if (tableName.startsWith(MYSQL_ESCAPE_CHARACTER) &&
        tableName.endsWith(MYSQL_ESCAPE_CHARACTER)) {
      tableName = tableName.substring(1, tableName.length() - 1);
    }
    return tableName;
  }

  public static Column buildColumn(
    String tableName,
    Alias tableAlias,
    String column
  ) {
    if (tableAlias != null) {
      tableName = tableAlias.getName();
    }
    return new Column(tableName + StringPool.DOT + column);
  }
}

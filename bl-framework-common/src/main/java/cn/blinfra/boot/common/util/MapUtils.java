package cn.blinfra.boot.common.util;

import cn.blinfra.boot.common.model.KeyValue;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class MapUtils {

  public static <K, V> List<V> getList(Multimap<K, V> multimap, Collection<K> keys) {
    List<V> results = new ArrayList<>();
    keys.forEach(k -> {
      Collection<V> values = multimap.get(k);
      if (CollectionUtil.isEmpty(values)) {
        return;
      }
      results.addAll(values);
    });
    return results;
  }

  public static <K, V> void findAndThen(Map<K, V> map, K key, Consumer<V> consumer) {
    if (CollUtil.isEmpty(map)) {
      return;
    }

    V value = map.get(key);
    if (value == null) {
      return;
    }

    consumer.accept(value);
  }

  public static <K, V> Map<K, V> convertMap(List<KeyValue<K, V>> keyValues) {
    Map<K, V> map = Maps.newLinkedHashMapWithExpectedSize(keyValues.size());
    keyValues.forEach(keyValue -> map.put(keyValue.getKey(), keyValue.getValue()));
    return map;
  }

}

package cn.blinfra.boot.common.util;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.function.Consumer;

public class ObjectUtils {
  public static <T> T cloneIgnoreID(T object) {
    T result = ObjectUtil.clone(object);
    Field field = ReflectUtil.getField(object.getClass(), "id");
    if (field != null) {
      ReflectUtil.setFieldValue(result, field, null);
    }
    return result;
  }

  public static <T> T cloneIgnoreID(T object, Consumer<T> consumer) {
    final T result = cloneIgnoreID(object);
    consumer.accept(result);
    return result;
  }

  public static <T extends Comparable<T>> T max(T obj1, T obj2) {
    if (obj1 == null) {
      return obj2;
    }
    if (obj2 == null) {
      return obj1;
    }
    return obj1.compareTo(obj2) > 0 ? obj1 : obj2;
  }

  @SafeVarargs
  public static <T> T defaultIfNull(T... arr) {
    for (T item : arr) {
      if (item != null) {
        return item;
      }
    }
    return null;
  }

  @SafeVarargs
  public static <T> boolean equalsAny(T obj, T... arr) {
    return Arrays.asList(arr).contains(obj);
  }

}

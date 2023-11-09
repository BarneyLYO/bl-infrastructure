package cn.blinfra.boot.common.util;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.time.Duration;
import java.util.concurrent.Executors;

public class CacheUtils {

  public static <K, V> LoadingCache<K, V> buildAsyncReloadingCache(
    Duration duration,
    CacheLoader<K, V> loader
  ) {
    CacheLoader<K, V> cacheLoader =
      CacheLoader.asyncReloading(
        loader,
        Executors.newCachedThreadPool()
      );

    return CacheBuilder.newBuilder()
                       .refreshAfterWrite(duration)
                       .build(cacheLoader);
  }

}

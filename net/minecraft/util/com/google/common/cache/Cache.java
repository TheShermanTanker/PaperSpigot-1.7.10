package net.minecraft.util.com.google.common.cache;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import javax.annotation.Nullable;
import net.minecraft.util.com.google.common.annotations.Beta;
import net.minecraft.util.com.google.common.annotations.GwtCompatible;
import net.minecraft.util.com.google.common.collect.ImmutableMap;

@Beta
@GwtCompatible
public interface Cache<K, V> {
  @Nullable
  V getIfPresent(Object paramObject);
  
  V get(K paramK, Callable<? extends V> paramCallable) throws ExecutionException;
  
  ImmutableMap<K, V> getAllPresent(Iterable<?> paramIterable);
  
  void put(K paramK, V paramV);
  
  void putAll(Map<? extends K, ? extends V> paramMap);
  
  void invalidate(Object paramObject);
  
  void invalidateAll(Iterable<?> paramIterable);
  
  void invalidateAll();
  
  long size();
  
  CacheStats stats();
  
  ConcurrentMap<K, V> asMap();
  
  void cleanUp();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\cache\Cache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
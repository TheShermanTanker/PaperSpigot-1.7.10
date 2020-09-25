package net.minecraft.util.com.google.common.cache;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import net.minecraft.util.com.google.common.annotations.Beta;
import net.minecraft.util.com.google.common.annotations.GwtCompatible;
import net.minecraft.util.com.google.common.base.Function;
import net.minecraft.util.com.google.common.collect.ImmutableMap;

@Beta
@GwtCompatible
public interface LoadingCache<K, V> extends Cache<K, V>, Function<K, V> {
  V get(K paramK) throws ExecutionException;
  
  V getUnchecked(K paramK);
  
  ImmutableMap<K, V> getAll(Iterable<? extends K> paramIterable) throws ExecutionException;
  
  @Deprecated
  V apply(K paramK);
  
  void refresh(K paramK);
  
  ConcurrentMap<K, V> asMap();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\cache\LoadingCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
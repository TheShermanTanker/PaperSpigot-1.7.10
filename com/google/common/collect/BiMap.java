package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible
public interface BiMap<K, V> extends Map<K, V> {
  V put(@Nullable K paramK, @Nullable V paramV);
  
  V forcePut(@Nullable K paramK, @Nullable V paramV);
  
  void putAll(Map<? extends K, ? extends V> paramMap);
  
  Set<V> values();
  
  BiMap<V, K> inverse();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\BiMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
package com.google.common.collect;

import com.google.common.annotations.Beta;
import javax.annotation.Nullable;

@Deprecated
@Beta
public interface MapEvictionListener<K, V> {
  void onEviction(@Nullable K paramK, @Nullable V paramV);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\MapEvictionListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import java.util.SortedMap;

@Beta
@GwtCompatible
public interface SortedMapDifference<K, V> extends MapDifference<K, V> {
  SortedMap<K, V> entriesOnlyOnLeft();
  
  SortedMap<K, V> entriesOnlyOnRight();
  
  SortedMap<K, V> entriesInCommon();
  
  SortedMap<K, MapDifference.ValueDifference<V>> entriesDiffering();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\SortedMapDifference.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
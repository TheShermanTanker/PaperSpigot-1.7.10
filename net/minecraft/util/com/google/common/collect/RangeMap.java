package net.minecraft.util.com.google.common.collect;

import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.util.com.google.common.annotations.Beta;

@Beta
public interface RangeMap<K extends Comparable, V> {
  @Nullable
  V get(K paramK);
  
  @Nullable
  Map.Entry<Range<K>, V> getEntry(K paramK);
  
  Range<K> span();
  
  void put(Range<K> paramRange, V paramV);
  
  void putAll(RangeMap<K, V> paramRangeMap);
  
  void clear();
  
  void remove(Range<K> paramRange);
  
  Map<Range<K>, V> asMapOfRanges();
  
  RangeMap<K, V> subRangeMap(Range<K> paramRange);
  
  boolean equals(@Nullable Object paramObject);
  
  int hashCode();
  
  String toString();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\collect\RangeMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
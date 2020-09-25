package net.minecraft.util.com.google.common.collect;

import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.util.com.google.common.annotations.Beta;

@Beta
public interface RangeSet<C extends Comparable> {
  boolean contains(C paramC);
  
  Range<C> rangeContaining(C paramC);
  
  boolean encloses(Range<C> paramRange);
  
  boolean enclosesAll(RangeSet<C> paramRangeSet);
  
  boolean isEmpty();
  
  Range<C> span();
  
  Set<Range<C>> asRanges();
  
  RangeSet<C> complement();
  
  RangeSet<C> subRangeSet(Range<C> paramRange);
  
  void add(Range<C> paramRange);
  
  void remove(Range<C> paramRange);
  
  void clear();
  
  void addAll(RangeSet<C> paramRangeSet);
  
  void removeAll(RangeSet<C> paramRangeSet);
  
  boolean equals(@Nullable Object paramObject);
  
  int hashCode();
  
  String toString();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\collect\RangeSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
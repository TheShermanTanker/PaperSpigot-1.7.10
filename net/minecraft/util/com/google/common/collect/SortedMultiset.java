package net.minecraft.util.com.google.common.collect;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import net.minecraft.util.com.google.common.annotations.Beta;
import net.minecraft.util.com.google.common.annotations.GwtCompatible;

@Beta
@GwtCompatible(emulated = true)
public interface SortedMultiset<E> extends SortedMultisetBridge<E>, SortedIterable<E> {
  Comparator<? super E> comparator();
  
  Multiset.Entry<E> firstEntry();
  
  Multiset.Entry<E> lastEntry();
  
  Multiset.Entry<E> pollFirstEntry();
  
  Multiset.Entry<E> pollLastEntry();
  
  NavigableSet<E> elementSet();
  
  Iterator<E> iterator();
  
  SortedMultiset<E> descendingMultiset();
  
  SortedMultiset<E> headMultiset(E paramE, BoundType paramBoundType);
  
  SortedMultiset<E> subMultiset(E paramE1, BoundType paramBoundType1, E paramE2, BoundType paramBoundType2);
  
  SortedMultiset<E> tailMultiset(E paramE, BoundType paramBoundType);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\collect\SortedMultiset.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
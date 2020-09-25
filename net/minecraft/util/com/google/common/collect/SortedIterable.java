package net.minecraft.util.com.google.common.collect;

import java.util.Comparator;
import java.util.Iterator;
import net.minecraft.util.com.google.common.annotations.GwtCompatible;

@GwtCompatible
interface SortedIterable<T> extends Iterable<T> {
  Comparator<? super T> comparator();
  
  Iterator<T> iterator();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\collect\SortedIterable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
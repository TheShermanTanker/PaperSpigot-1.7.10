package net.minecraft.util.com.google.common.collect;

import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import net.minecraft.util.com.google.common.annotations.Beta;
import net.minecraft.util.com.google.common.annotations.GwtCompatible;

@GwtCompatible
@Beta
public interface RowSortedTable<R, C, V> extends Table<R, C, V> {
  SortedSet<R> rowKeySet();
  
  SortedMap<R, Map<C, V>> rowMap();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\collect\RowSortedTable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
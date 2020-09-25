package net.minecraft.util.gnu.trove.map;

import java.util.Map;
import java.util.Set;
import net.minecraft.util.gnu.trove.TLongCollection;
import net.minecraft.util.gnu.trove.function.TLongFunction;
import net.minecraft.util.gnu.trove.iterator.TObjectLongIterator;
import net.minecraft.util.gnu.trove.procedure.TLongProcedure;
import net.minecraft.util.gnu.trove.procedure.TObjectLongProcedure;
import net.minecraft.util.gnu.trove.procedure.TObjectProcedure;

public interface TObjectLongMap<K> {
  long getNoEntryValue();
  
  int size();
  
  boolean isEmpty();
  
  boolean containsKey(Object paramObject);
  
  boolean containsValue(long paramLong);
  
  long get(Object paramObject);
  
  long put(K paramK, long paramLong);
  
  long putIfAbsent(K paramK, long paramLong);
  
  long remove(Object paramObject);
  
  void putAll(Map<? extends K, ? extends Long> paramMap);
  
  void putAll(TObjectLongMap<? extends K> paramTObjectLongMap);
  
  void clear();
  
  Set<K> keySet();
  
  Object[] keys();
  
  K[] keys(K[] paramArrayOfK);
  
  TLongCollection valueCollection();
  
  long[] values();
  
  long[] values(long[] paramArrayOflong);
  
  TObjectLongIterator<K> iterator();
  
  boolean increment(K paramK);
  
  boolean adjustValue(K paramK, long paramLong);
  
  long adjustOrPutValue(K paramK, long paramLong1, long paramLong2);
  
  boolean forEachKey(TObjectProcedure<? super K> paramTObjectProcedure);
  
  boolean forEachValue(TLongProcedure paramTLongProcedure);
  
  boolean forEachEntry(TObjectLongProcedure<? super K> paramTObjectLongProcedure);
  
  void transformValues(TLongFunction paramTLongFunction);
  
  boolean retainEntries(TObjectLongProcedure<? super K> paramTObjectLongProcedure);
  
  boolean equals(Object paramObject);
  
  int hashCode();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\map\TObjectLongMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
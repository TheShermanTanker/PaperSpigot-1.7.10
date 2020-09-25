package net.minecraft.util.gnu.trove.map;

import java.util.Collection;
import java.util.Map;
import net.minecraft.util.gnu.trove.function.TObjectFunction;
import net.minecraft.util.gnu.trove.iterator.TLongObjectIterator;
import net.minecraft.util.gnu.trove.procedure.TLongObjectProcedure;
import net.minecraft.util.gnu.trove.procedure.TLongProcedure;
import net.minecraft.util.gnu.trove.procedure.TObjectProcedure;
import net.minecraft.util.gnu.trove.set.TLongSet;

public interface TLongObjectMap<V> {
  long getNoEntryKey();
  
  int size();
  
  boolean isEmpty();
  
  boolean containsKey(long paramLong);
  
  boolean containsValue(Object paramObject);
  
  V get(long paramLong);
  
  V put(long paramLong, V paramV);
  
  V putIfAbsent(long paramLong, V paramV);
  
  V remove(long paramLong);
  
  void putAll(Map<? extends Long, ? extends V> paramMap);
  
  void putAll(TLongObjectMap<? extends V> paramTLongObjectMap);
  
  void clear();
  
  TLongSet keySet();
  
  long[] keys();
  
  long[] keys(long[] paramArrayOflong);
  
  Collection<V> valueCollection();
  
  Object[] values();
  
  V[] values(V[] paramArrayOfV);
  
  TLongObjectIterator<V> iterator();
  
  boolean forEachKey(TLongProcedure paramTLongProcedure);
  
  boolean forEachValue(TObjectProcedure<? super V> paramTObjectProcedure);
  
  boolean forEachEntry(TLongObjectProcedure<? super V> paramTLongObjectProcedure);
  
  void transformValues(TObjectFunction<V, V> paramTObjectFunction);
  
  boolean retainEntries(TLongObjectProcedure<? super V> paramTLongObjectProcedure);
  
  boolean equals(Object paramObject);
  
  int hashCode();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\map\TLongObjectMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
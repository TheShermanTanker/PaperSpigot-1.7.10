package net.minecraft.util.gnu.trove.map;

import java.util.Collection;
import java.util.Map;
import net.minecraft.util.gnu.trove.function.TObjectFunction;
import net.minecraft.util.gnu.trove.iterator.TIntObjectIterator;
import net.minecraft.util.gnu.trove.procedure.TIntObjectProcedure;
import net.minecraft.util.gnu.trove.procedure.TIntProcedure;
import net.minecraft.util.gnu.trove.procedure.TObjectProcedure;
import net.minecraft.util.gnu.trove.set.TIntSet;

public interface TIntObjectMap<V> {
  int getNoEntryKey();
  
  int size();
  
  boolean isEmpty();
  
  boolean containsKey(int paramInt);
  
  boolean containsValue(Object paramObject);
  
  V get(int paramInt);
  
  V put(int paramInt, V paramV);
  
  V putIfAbsent(int paramInt, V paramV);
  
  V remove(int paramInt);
  
  void putAll(Map<? extends Integer, ? extends V> paramMap);
  
  void putAll(TIntObjectMap<? extends V> paramTIntObjectMap);
  
  void clear();
  
  TIntSet keySet();
  
  int[] keys();
  
  int[] keys(int[] paramArrayOfint);
  
  Collection<V> valueCollection();
  
  Object[] values();
  
  V[] values(V[] paramArrayOfV);
  
  TIntObjectIterator<V> iterator();
  
  boolean forEachKey(TIntProcedure paramTIntProcedure);
  
  boolean forEachValue(TObjectProcedure<? super V> paramTObjectProcedure);
  
  boolean forEachEntry(TIntObjectProcedure<? super V> paramTIntObjectProcedure);
  
  void transformValues(TObjectFunction<V, V> paramTObjectFunction);
  
  boolean retainEntries(TIntObjectProcedure<? super V> paramTIntObjectProcedure);
  
  boolean equals(Object paramObject);
  
  int hashCode();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\map\TIntObjectMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
package net.minecraft.util.gnu.trove.map;

import java.util.Collection;
import java.util.Map;
import net.minecraft.util.gnu.trove.function.TObjectFunction;
import net.minecraft.util.gnu.trove.iterator.TShortObjectIterator;
import net.minecraft.util.gnu.trove.procedure.TObjectProcedure;
import net.minecraft.util.gnu.trove.procedure.TShortObjectProcedure;
import net.minecraft.util.gnu.trove.procedure.TShortProcedure;
import net.minecraft.util.gnu.trove.set.TShortSet;

public interface TShortObjectMap<V> {
  short getNoEntryKey();
  
  int size();
  
  boolean isEmpty();
  
  boolean containsKey(short paramShort);
  
  boolean containsValue(Object paramObject);
  
  V get(short paramShort);
  
  V put(short paramShort, V paramV);
  
  V putIfAbsent(short paramShort, V paramV);
  
  V remove(short paramShort);
  
  void putAll(Map<? extends Short, ? extends V> paramMap);
  
  void putAll(TShortObjectMap<? extends V> paramTShortObjectMap);
  
  void clear();
  
  TShortSet keySet();
  
  short[] keys();
  
  short[] keys(short[] paramArrayOfshort);
  
  Collection<V> valueCollection();
  
  Object[] values();
  
  V[] values(V[] paramArrayOfV);
  
  TShortObjectIterator<V> iterator();
  
  boolean forEachKey(TShortProcedure paramTShortProcedure);
  
  boolean forEachValue(TObjectProcedure<? super V> paramTObjectProcedure);
  
  boolean forEachEntry(TShortObjectProcedure<? super V> paramTShortObjectProcedure);
  
  void transformValues(TObjectFunction<V, V> paramTObjectFunction);
  
  boolean retainEntries(TShortObjectProcedure<? super V> paramTShortObjectProcedure);
  
  boolean equals(Object paramObject);
  
  int hashCode();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\map\TShortObjectMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
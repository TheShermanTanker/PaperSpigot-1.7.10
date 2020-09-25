package net.minecraft.util.gnu.trove.map;

import java.util.Collection;
import java.util.Map;
import net.minecraft.util.gnu.trove.function.TObjectFunction;
import net.minecraft.util.gnu.trove.iterator.TFloatObjectIterator;
import net.minecraft.util.gnu.trove.procedure.TFloatObjectProcedure;
import net.minecraft.util.gnu.trove.procedure.TFloatProcedure;
import net.minecraft.util.gnu.trove.procedure.TObjectProcedure;
import net.minecraft.util.gnu.trove.set.TFloatSet;

public interface TFloatObjectMap<V> {
  float getNoEntryKey();
  
  int size();
  
  boolean isEmpty();
  
  boolean containsKey(float paramFloat);
  
  boolean containsValue(Object paramObject);
  
  V get(float paramFloat);
  
  V put(float paramFloat, V paramV);
  
  V putIfAbsent(float paramFloat, V paramV);
  
  V remove(float paramFloat);
  
  void putAll(Map<? extends Float, ? extends V> paramMap);
  
  void putAll(TFloatObjectMap<? extends V> paramTFloatObjectMap);
  
  void clear();
  
  TFloatSet keySet();
  
  float[] keys();
  
  float[] keys(float[] paramArrayOffloat);
  
  Collection<V> valueCollection();
  
  Object[] values();
  
  V[] values(V[] paramArrayOfV);
  
  TFloatObjectIterator<V> iterator();
  
  boolean forEachKey(TFloatProcedure paramTFloatProcedure);
  
  boolean forEachValue(TObjectProcedure<? super V> paramTObjectProcedure);
  
  boolean forEachEntry(TFloatObjectProcedure<? super V> paramTFloatObjectProcedure);
  
  void transformValues(TObjectFunction<V, V> paramTObjectFunction);
  
  boolean retainEntries(TFloatObjectProcedure<? super V> paramTFloatObjectProcedure);
  
  boolean equals(Object paramObject);
  
  int hashCode();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\map\TFloatObjectMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
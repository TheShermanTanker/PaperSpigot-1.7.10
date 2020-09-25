package net.minecraft.util.gnu.trove.map;

import java.util.Map;
import java.util.Set;
import net.minecraft.util.gnu.trove.TFloatCollection;
import net.minecraft.util.gnu.trove.function.TFloatFunction;
import net.minecraft.util.gnu.trove.iterator.TObjectFloatIterator;
import net.minecraft.util.gnu.trove.procedure.TFloatProcedure;
import net.minecraft.util.gnu.trove.procedure.TObjectFloatProcedure;
import net.minecraft.util.gnu.trove.procedure.TObjectProcedure;

public interface TObjectFloatMap<K> {
  float getNoEntryValue();
  
  int size();
  
  boolean isEmpty();
  
  boolean containsKey(Object paramObject);
  
  boolean containsValue(float paramFloat);
  
  float get(Object paramObject);
  
  float put(K paramK, float paramFloat);
  
  float putIfAbsent(K paramK, float paramFloat);
  
  float remove(Object paramObject);
  
  void putAll(Map<? extends K, ? extends Float> paramMap);
  
  void putAll(TObjectFloatMap<? extends K> paramTObjectFloatMap);
  
  void clear();
  
  Set<K> keySet();
  
  Object[] keys();
  
  K[] keys(K[] paramArrayOfK);
  
  TFloatCollection valueCollection();
  
  float[] values();
  
  float[] values(float[] paramArrayOffloat);
  
  TObjectFloatIterator<K> iterator();
  
  boolean increment(K paramK);
  
  boolean adjustValue(K paramK, float paramFloat);
  
  float adjustOrPutValue(K paramK, float paramFloat1, float paramFloat2);
  
  boolean forEachKey(TObjectProcedure<? super K> paramTObjectProcedure);
  
  boolean forEachValue(TFloatProcedure paramTFloatProcedure);
  
  boolean forEachEntry(TObjectFloatProcedure<? super K> paramTObjectFloatProcedure);
  
  void transformValues(TFloatFunction paramTFloatFunction);
  
  boolean retainEntries(TObjectFloatProcedure<? super K> paramTObjectFloatProcedure);
  
  boolean equals(Object paramObject);
  
  int hashCode();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\map\TObjectFloatMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
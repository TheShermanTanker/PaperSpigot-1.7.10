package net.minecraft.util.gnu.trove.map;

import java.util.Map;
import net.minecraft.util.gnu.trove.TFloatCollection;
import net.minecraft.util.gnu.trove.function.TFloatFunction;
import net.minecraft.util.gnu.trove.iterator.TFloatFloatIterator;
import net.minecraft.util.gnu.trove.procedure.TFloatFloatProcedure;
import net.minecraft.util.gnu.trove.procedure.TFloatProcedure;
import net.minecraft.util.gnu.trove.set.TFloatSet;

public interface TFloatFloatMap {
  float getNoEntryKey();
  
  float getNoEntryValue();
  
  float put(float paramFloat1, float paramFloat2);
  
  float putIfAbsent(float paramFloat1, float paramFloat2);
  
  void putAll(Map<? extends Float, ? extends Float> paramMap);
  
  void putAll(TFloatFloatMap paramTFloatFloatMap);
  
  float get(float paramFloat);
  
  void clear();
  
  boolean isEmpty();
  
  float remove(float paramFloat);
  
  int size();
  
  TFloatSet keySet();
  
  float[] keys();
  
  float[] keys(float[] paramArrayOffloat);
  
  TFloatCollection valueCollection();
  
  float[] values();
  
  float[] values(float[] paramArrayOffloat);
  
  boolean containsValue(float paramFloat);
  
  boolean containsKey(float paramFloat);
  
  TFloatFloatIterator iterator();
  
  boolean forEachKey(TFloatProcedure paramTFloatProcedure);
  
  boolean forEachValue(TFloatProcedure paramTFloatProcedure);
  
  boolean forEachEntry(TFloatFloatProcedure paramTFloatFloatProcedure);
  
  void transformValues(TFloatFunction paramTFloatFunction);
  
  boolean retainEntries(TFloatFloatProcedure paramTFloatFloatProcedure);
  
  boolean increment(float paramFloat);
  
  boolean adjustValue(float paramFloat1, float paramFloat2);
  
  float adjustOrPutValue(float paramFloat1, float paramFloat2, float paramFloat3);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\map\TFloatFloatMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
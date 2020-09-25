package net.minecraft.util.gnu.trove.map;

import java.util.Map;
import net.minecraft.util.gnu.trove.TFloatCollection;
import net.minecraft.util.gnu.trove.function.TFloatFunction;
import net.minecraft.util.gnu.trove.iterator.TLongFloatIterator;
import net.minecraft.util.gnu.trove.procedure.TFloatProcedure;
import net.minecraft.util.gnu.trove.procedure.TLongFloatProcedure;
import net.minecraft.util.gnu.trove.procedure.TLongProcedure;
import net.minecraft.util.gnu.trove.set.TLongSet;

public interface TLongFloatMap {
  long getNoEntryKey();
  
  float getNoEntryValue();
  
  float put(long paramLong, float paramFloat);
  
  float putIfAbsent(long paramLong, float paramFloat);
  
  void putAll(Map<? extends Long, ? extends Float> paramMap);
  
  void putAll(TLongFloatMap paramTLongFloatMap);
  
  float get(long paramLong);
  
  void clear();
  
  boolean isEmpty();
  
  float remove(long paramLong);
  
  int size();
  
  TLongSet keySet();
  
  long[] keys();
  
  long[] keys(long[] paramArrayOflong);
  
  TFloatCollection valueCollection();
  
  float[] values();
  
  float[] values(float[] paramArrayOffloat);
  
  boolean containsValue(float paramFloat);
  
  boolean containsKey(long paramLong);
  
  TLongFloatIterator iterator();
  
  boolean forEachKey(TLongProcedure paramTLongProcedure);
  
  boolean forEachValue(TFloatProcedure paramTFloatProcedure);
  
  boolean forEachEntry(TLongFloatProcedure paramTLongFloatProcedure);
  
  void transformValues(TFloatFunction paramTFloatFunction);
  
  boolean retainEntries(TLongFloatProcedure paramTLongFloatProcedure);
  
  boolean increment(long paramLong);
  
  boolean adjustValue(long paramLong, float paramFloat);
  
  float adjustOrPutValue(long paramLong, float paramFloat1, float paramFloat2);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\map\TLongFloatMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
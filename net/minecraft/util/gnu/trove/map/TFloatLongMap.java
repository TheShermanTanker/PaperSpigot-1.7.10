package net.minecraft.util.gnu.trove.map;

import java.util.Map;
import net.minecraft.util.gnu.trove.TLongCollection;
import net.minecraft.util.gnu.trove.function.TLongFunction;
import net.minecraft.util.gnu.trove.iterator.TFloatLongIterator;
import net.minecraft.util.gnu.trove.procedure.TFloatLongProcedure;
import net.minecraft.util.gnu.trove.procedure.TFloatProcedure;
import net.minecraft.util.gnu.trove.procedure.TLongProcedure;
import net.minecraft.util.gnu.trove.set.TFloatSet;

public interface TFloatLongMap {
  float getNoEntryKey();
  
  long getNoEntryValue();
  
  long put(float paramFloat, long paramLong);
  
  long putIfAbsent(float paramFloat, long paramLong);
  
  void putAll(Map<? extends Float, ? extends Long> paramMap);
  
  void putAll(TFloatLongMap paramTFloatLongMap);
  
  long get(float paramFloat);
  
  void clear();
  
  boolean isEmpty();
  
  long remove(float paramFloat);
  
  int size();
  
  TFloatSet keySet();
  
  float[] keys();
  
  float[] keys(float[] paramArrayOffloat);
  
  TLongCollection valueCollection();
  
  long[] values();
  
  long[] values(long[] paramArrayOflong);
  
  boolean containsValue(long paramLong);
  
  boolean containsKey(float paramFloat);
  
  TFloatLongIterator iterator();
  
  boolean forEachKey(TFloatProcedure paramTFloatProcedure);
  
  boolean forEachValue(TLongProcedure paramTLongProcedure);
  
  boolean forEachEntry(TFloatLongProcedure paramTFloatLongProcedure);
  
  void transformValues(TLongFunction paramTLongFunction);
  
  boolean retainEntries(TFloatLongProcedure paramTFloatLongProcedure);
  
  boolean increment(float paramFloat);
  
  boolean adjustValue(float paramFloat, long paramLong);
  
  long adjustOrPutValue(float paramFloat, long paramLong1, long paramLong2);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\map\TFloatLongMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
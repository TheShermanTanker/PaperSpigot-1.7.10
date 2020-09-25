package net.minecraft.util.gnu.trove.map;

import java.util.Map;
import net.minecraft.util.gnu.trove.TIntCollection;
import net.minecraft.util.gnu.trove.function.TIntFunction;
import net.minecraft.util.gnu.trove.iterator.TFloatIntIterator;
import net.minecraft.util.gnu.trove.procedure.TFloatIntProcedure;
import net.minecraft.util.gnu.trove.procedure.TFloatProcedure;
import net.minecraft.util.gnu.trove.procedure.TIntProcedure;
import net.minecraft.util.gnu.trove.set.TFloatSet;

public interface TFloatIntMap {
  float getNoEntryKey();
  
  int getNoEntryValue();
  
  int put(float paramFloat, int paramInt);
  
  int putIfAbsent(float paramFloat, int paramInt);
  
  void putAll(Map<? extends Float, ? extends Integer> paramMap);
  
  void putAll(TFloatIntMap paramTFloatIntMap);
  
  int get(float paramFloat);
  
  void clear();
  
  boolean isEmpty();
  
  int remove(float paramFloat);
  
  int size();
  
  TFloatSet keySet();
  
  float[] keys();
  
  float[] keys(float[] paramArrayOffloat);
  
  TIntCollection valueCollection();
  
  int[] values();
  
  int[] values(int[] paramArrayOfint);
  
  boolean containsValue(int paramInt);
  
  boolean containsKey(float paramFloat);
  
  TFloatIntIterator iterator();
  
  boolean forEachKey(TFloatProcedure paramTFloatProcedure);
  
  boolean forEachValue(TIntProcedure paramTIntProcedure);
  
  boolean forEachEntry(TFloatIntProcedure paramTFloatIntProcedure);
  
  void transformValues(TIntFunction paramTIntFunction);
  
  boolean retainEntries(TFloatIntProcedure paramTFloatIntProcedure);
  
  boolean increment(float paramFloat);
  
  boolean adjustValue(float paramFloat, int paramInt);
  
  int adjustOrPutValue(float paramFloat, int paramInt1, int paramInt2);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\map\TFloatIntMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
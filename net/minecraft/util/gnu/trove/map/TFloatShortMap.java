package net.minecraft.util.gnu.trove.map;

import java.util.Map;
import net.minecraft.util.gnu.trove.TShortCollection;
import net.minecraft.util.gnu.trove.function.TShortFunction;
import net.minecraft.util.gnu.trove.iterator.TFloatShortIterator;
import net.minecraft.util.gnu.trove.procedure.TFloatProcedure;
import net.minecraft.util.gnu.trove.procedure.TFloatShortProcedure;
import net.minecraft.util.gnu.trove.procedure.TShortProcedure;
import net.minecraft.util.gnu.trove.set.TFloatSet;

public interface TFloatShortMap {
  float getNoEntryKey();
  
  short getNoEntryValue();
  
  short put(float paramFloat, short paramShort);
  
  short putIfAbsent(float paramFloat, short paramShort);
  
  void putAll(Map<? extends Float, ? extends Short> paramMap);
  
  void putAll(TFloatShortMap paramTFloatShortMap);
  
  short get(float paramFloat);
  
  void clear();
  
  boolean isEmpty();
  
  short remove(float paramFloat);
  
  int size();
  
  TFloatSet keySet();
  
  float[] keys();
  
  float[] keys(float[] paramArrayOffloat);
  
  TShortCollection valueCollection();
  
  short[] values();
  
  short[] values(short[] paramArrayOfshort);
  
  boolean containsValue(short paramShort);
  
  boolean containsKey(float paramFloat);
  
  TFloatShortIterator iterator();
  
  boolean forEachKey(TFloatProcedure paramTFloatProcedure);
  
  boolean forEachValue(TShortProcedure paramTShortProcedure);
  
  boolean forEachEntry(TFloatShortProcedure paramTFloatShortProcedure);
  
  void transformValues(TShortFunction paramTShortFunction);
  
  boolean retainEntries(TFloatShortProcedure paramTFloatShortProcedure);
  
  boolean increment(float paramFloat);
  
  boolean adjustValue(float paramFloat, short paramShort);
  
  short adjustOrPutValue(float paramFloat, short paramShort1, short paramShort2);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\map\TFloatShortMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
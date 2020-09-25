package net.minecraft.util.gnu.trove.map;

import java.util.Map;
import net.minecraft.util.gnu.trove.TFloatCollection;
import net.minecraft.util.gnu.trove.function.TFloatFunction;
import net.minecraft.util.gnu.trove.iterator.TCharFloatIterator;
import net.minecraft.util.gnu.trove.procedure.TCharFloatProcedure;
import net.minecraft.util.gnu.trove.procedure.TCharProcedure;
import net.minecraft.util.gnu.trove.procedure.TFloatProcedure;
import net.minecraft.util.gnu.trove.set.TCharSet;

public interface TCharFloatMap {
  char getNoEntryKey();
  
  float getNoEntryValue();
  
  float put(char paramChar, float paramFloat);
  
  float putIfAbsent(char paramChar, float paramFloat);
  
  void putAll(Map<? extends Character, ? extends Float> paramMap);
  
  void putAll(TCharFloatMap paramTCharFloatMap);
  
  float get(char paramChar);
  
  void clear();
  
  boolean isEmpty();
  
  float remove(char paramChar);
  
  int size();
  
  TCharSet keySet();
  
  char[] keys();
  
  char[] keys(char[] paramArrayOfchar);
  
  TFloatCollection valueCollection();
  
  float[] values();
  
  float[] values(float[] paramArrayOffloat);
  
  boolean containsValue(float paramFloat);
  
  boolean containsKey(char paramChar);
  
  TCharFloatIterator iterator();
  
  boolean forEachKey(TCharProcedure paramTCharProcedure);
  
  boolean forEachValue(TFloatProcedure paramTFloatProcedure);
  
  boolean forEachEntry(TCharFloatProcedure paramTCharFloatProcedure);
  
  void transformValues(TFloatFunction paramTFloatFunction);
  
  boolean retainEntries(TCharFloatProcedure paramTCharFloatProcedure);
  
  boolean increment(char paramChar);
  
  boolean adjustValue(char paramChar, float paramFloat);
  
  float adjustOrPutValue(char paramChar, float paramFloat1, float paramFloat2);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\map\TCharFloatMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
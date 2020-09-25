package net.minecraft.util.gnu.trove.map;

import java.util.Map;
import net.minecraft.util.gnu.trove.TCharCollection;
import net.minecraft.util.gnu.trove.function.TCharFunction;
import net.minecraft.util.gnu.trove.iterator.TFloatCharIterator;
import net.minecraft.util.gnu.trove.procedure.TCharProcedure;
import net.minecraft.util.gnu.trove.procedure.TFloatCharProcedure;
import net.minecraft.util.gnu.trove.procedure.TFloatProcedure;
import net.minecraft.util.gnu.trove.set.TFloatSet;

public interface TFloatCharMap {
  float getNoEntryKey();
  
  char getNoEntryValue();
  
  char put(float paramFloat, char paramChar);
  
  char putIfAbsent(float paramFloat, char paramChar);
  
  void putAll(Map<? extends Float, ? extends Character> paramMap);
  
  void putAll(TFloatCharMap paramTFloatCharMap);
  
  char get(float paramFloat);
  
  void clear();
  
  boolean isEmpty();
  
  char remove(float paramFloat);
  
  int size();
  
  TFloatSet keySet();
  
  float[] keys();
  
  float[] keys(float[] paramArrayOffloat);
  
  TCharCollection valueCollection();
  
  char[] values();
  
  char[] values(char[] paramArrayOfchar);
  
  boolean containsValue(char paramChar);
  
  boolean containsKey(float paramFloat);
  
  TFloatCharIterator iterator();
  
  boolean forEachKey(TFloatProcedure paramTFloatProcedure);
  
  boolean forEachValue(TCharProcedure paramTCharProcedure);
  
  boolean forEachEntry(TFloatCharProcedure paramTFloatCharProcedure);
  
  void transformValues(TCharFunction paramTCharFunction);
  
  boolean retainEntries(TFloatCharProcedure paramTFloatCharProcedure);
  
  boolean increment(float paramFloat);
  
  boolean adjustValue(float paramFloat, char paramChar);
  
  char adjustOrPutValue(float paramFloat, char paramChar1, char paramChar2);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\map\TFloatCharMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
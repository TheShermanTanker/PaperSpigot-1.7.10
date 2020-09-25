package net.minecraft.util.gnu.trove.map;

import java.util.Map;
import net.minecraft.util.gnu.trove.TShortCollection;
import net.minecraft.util.gnu.trove.function.TShortFunction;
import net.minecraft.util.gnu.trove.iterator.TCharShortIterator;
import net.minecraft.util.gnu.trove.procedure.TCharProcedure;
import net.minecraft.util.gnu.trove.procedure.TCharShortProcedure;
import net.minecraft.util.gnu.trove.procedure.TShortProcedure;
import net.minecraft.util.gnu.trove.set.TCharSet;

public interface TCharShortMap {
  char getNoEntryKey();
  
  short getNoEntryValue();
  
  short put(char paramChar, short paramShort);
  
  short putIfAbsent(char paramChar, short paramShort);
  
  void putAll(Map<? extends Character, ? extends Short> paramMap);
  
  void putAll(TCharShortMap paramTCharShortMap);
  
  short get(char paramChar);
  
  void clear();
  
  boolean isEmpty();
  
  short remove(char paramChar);
  
  int size();
  
  TCharSet keySet();
  
  char[] keys();
  
  char[] keys(char[] paramArrayOfchar);
  
  TShortCollection valueCollection();
  
  short[] values();
  
  short[] values(short[] paramArrayOfshort);
  
  boolean containsValue(short paramShort);
  
  boolean containsKey(char paramChar);
  
  TCharShortIterator iterator();
  
  boolean forEachKey(TCharProcedure paramTCharProcedure);
  
  boolean forEachValue(TShortProcedure paramTShortProcedure);
  
  boolean forEachEntry(TCharShortProcedure paramTCharShortProcedure);
  
  void transformValues(TShortFunction paramTShortFunction);
  
  boolean retainEntries(TCharShortProcedure paramTCharShortProcedure);
  
  boolean increment(char paramChar);
  
  boolean adjustValue(char paramChar, short paramShort);
  
  short adjustOrPutValue(char paramChar, short paramShort1, short paramShort2);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\map\TCharShortMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
package net.minecraft.util.gnu.trove.map;

import java.util.Map;
import net.minecraft.util.gnu.trove.TCharCollection;
import net.minecraft.util.gnu.trove.function.TCharFunction;
import net.minecraft.util.gnu.trove.iterator.TShortCharIterator;
import net.minecraft.util.gnu.trove.procedure.TCharProcedure;
import net.minecraft.util.gnu.trove.procedure.TShortCharProcedure;
import net.minecraft.util.gnu.trove.procedure.TShortProcedure;
import net.minecraft.util.gnu.trove.set.TShortSet;

public interface TShortCharMap {
  short getNoEntryKey();
  
  char getNoEntryValue();
  
  char put(short paramShort, char paramChar);
  
  char putIfAbsent(short paramShort, char paramChar);
  
  void putAll(Map<? extends Short, ? extends Character> paramMap);
  
  void putAll(TShortCharMap paramTShortCharMap);
  
  char get(short paramShort);
  
  void clear();
  
  boolean isEmpty();
  
  char remove(short paramShort);
  
  int size();
  
  TShortSet keySet();
  
  short[] keys();
  
  short[] keys(short[] paramArrayOfshort);
  
  TCharCollection valueCollection();
  
  char[] values();
  
  char[] values(char[] paramArrayOfchar);
  
  boolean containsValue(char paramChar);
  
  boolean containsKey(short paramShort);
  
  TShortCharIterator iterator();
  
  boolean forEachKey(TShortProcedure paramTShortProcedure);
  
  boolean forEachValue(TCharProcedure paramTCharProcedure);
  
  boolean forEachEntry(TShortCharProcedure paramTShortCharProcedure);
  
  void transformValues(TCharFunction paramTCharFunction);
  
  boolean retainEntries(TShortCharProcedure paramTShortCharProcedure);
  
  boolean increment(short paramShort);
  
  boolean adjustValue(short paramShort, char paramChar);
  
  char adjustOrPutValue(short paramShort, char paramChar1, char paramChar2);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\map\TShortCharMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
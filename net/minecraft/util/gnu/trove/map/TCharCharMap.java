package net.minecraft.util.gnu.trove.map;

import java.util.Map;
import net.minecraft.util.gnu.trove.TCharCollection;
import net.minecraft.util.gnu.trove.function.TCharFunction;
import net.minecraft.util.gnu.trove.iterator.TCharCharIterator;
import net.minecraft.util.gnu.trove.procedure.TCharCharProcedure;
import net.minecraft.util.gnu.trove.procedure.TCharProcedure;
import net.minecraft.util.gnu.trove.set.TCharSet;

public interface TCharCharMap {
  char getNoEntryKey();
  
  char getNoEntryValue();
  
  char put(char paramChar1, char paramChar2);
  
  char putIfAbsent(char paramChar1, char paramChar2);
  
  void putAll(Map<? extends Character, ? extends Character> paramMap);
  
  void putAll(TCharCharMap paramTCharCharMap);
  
  char get(char paramChar);
  
  void clear();
  
  boolean isEmpty();
  
  char remove(char paramChar);
  
  int size();
  
  TCharSet keySet();
  
  char[] keys();
  
  char[] keys(char[] paramArrayOfchar);
  
  TCharCollection valueCollection();
  
  char[] values();
  
  char[] values(char[] paramArrayOfchar);
  
  boolean containsValue(char paramChar);
  
  boolean containsKey(char paramChar);
  
  TCharCharIterator iterator();
  
  boolean forEachKey(TCharProcedure paramTCharProcedure);
  
  boolean forEachValue(TCharProcedure paramTCharProcedure);
  
  boolean forEachEntry(TCharCharProcedure paramTCharCharProcedure);
  
  void transformValues(TCharFunction paramTCharFunction);
  
  boolean retainEntries(TCharCharProcedure paramTCharCharProcedure);
  
  boolean increment(char paramChar);
  
  boolean adjustValue(char paramChar1, char paramChar2);
  
  char adjustOrPutValue(char paramChar1, char paramChar2, char paramChar3);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\map\TCharCharMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
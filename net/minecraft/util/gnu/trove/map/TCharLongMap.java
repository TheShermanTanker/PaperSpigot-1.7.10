package net.minecraft.util.gnu.trove.map;

import java.util.Map;
import net.minecraft.util.gnu.trove.TLongCollection;
import net.minecraft.util.gnu.trove.function.TLongFunction;
import net.minecraft.util.gnu.trove.iterator.TCharLongIterator;
import net.minecraft.util.gnu.trove.procedure.TCharLongProcedure;
import net.minecraft.util.gnu.trove.procedure.TCharProcedure;
import net.minecraft.util.gnu.trove.procedure.TLongProcedure;
import net.minecraft.util.gnu.trove.set.TCharSet;

public interface TCharLongMap {
  char getNoEntryKey();
  
  long getNoEntryValue();
  
  long put(char paramChar, long paramLong);
  
  long putIfAbsent(char paramChar, long paramLong);
  
  void putAll(Map<? extends Character, ? extends Long> paramMap);
  
  void putAll(TCharLongMap paramTCharLongMap);
  
  long get(char paramChar);
  
  void clear();
  
  boolean isEmpty();
  
  long remove(char paramChar);
  
  int size();
  
  TCharSet keySet();
  
  char[] keys();
  
  char[] keys(char[] paramArrayOfchar);
  
  TLongCollection valueCollection();
  
  long[] values();
  
  long[] values(long[] paramArrayOflong);
  
  boolean containsValue(long paramLong);
  
  boolean containsKey(char paramChar);
  
  TCharLongIterator iterator();
  
  boolean forEachKey(TCharProcedure paramTCharProcedure);
  
  boolean forEachValue(TLongProcedure paramTLongProcedure);
  
  boolean forEachEntry(TCharLongProcedure paramTCharLongProcedure);
  
  void transformValues(TLongFunction paramTLongFunction);
  
  boolean retainEntries(TCharLongProcedure paramTCharLongProcedure);
  
  boolean increment(char paramChar);
  
  boolean adjustValue(char paramChar, long paramLong);
  
  long adjustOrPutValue(char paramChar, long paramLong1, long paramLong2);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\map\TCharLongMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
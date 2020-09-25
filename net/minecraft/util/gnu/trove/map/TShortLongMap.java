package net.minecraft.util.gnu.trove.map;

import java.util.Map;
import net.minecraft.util.gnu.trove.TLongCollection;
import net.minecraft.util.gnu.trove.function.TLongFunction;
import net.minecraft.util.gnu.trove.iterator.TShortLongIterator;
import net.minecraft.util.gnu.trove.procedure.TLongProcedure;
import net.minecraft.util.gnu.trove.procedure.TShortLongProcedure;
import net.minecraft.util.gnu.trove.procedure.TShortProcedure;
import net.minecraft.util.gnu.trove.set.TShortSet;

public interface TShortLongMap {
  short getNoEntryKey();
  
  long getNoEntryValue();
  
  long put(short paramShort, long paramLong);
  
  long putIfAbsent(short paramShort, long paramLong);
  
  void putAll(Map<? extends Short, ? extends Long> paramMap);
  
  void putAll(TShortLongMap paramTShortLongMap);
  
  long get(short paramShort);
  
  void clear();
  
  boolean isEmpty();
  
  long remove(short paramShort);
  
  int size();
  
  TShortSet keySet();
  
  short[] keys();
  
  short[] keys(short[] paramArrayOfshort);
  
  TLongCollection valueCollection();
  
  long[] values();
  
  long[] values(long[] paramArrayOflong);
  
  boolean containsValue(long paramLong);
  
  boolean containsKey(short paramShort);
  
  TShortLongIterator iterator();
  
  boolean forEachKey(TShortProcedure paramTShortProcedure);
  
  boolean forEachValue(TLongProcedure paramTLongProcedure);
  
  boolean forEachEntry(TShortLongProcedure paramTShortLongProcedure);
  
  void transformValues(TLongFunction paramTLongFunction);
  
  boolean retainEntries(TShortLongProcedure paramTShortLongProcedure);
  
  boolean increment(short paramShort);
  
  boolean adjustValue(short paramShort, long paramLong);
  
  long adjustOrPutValue(short paramShort, long paramLong1, long paramLong2);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\map\TShortLongMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
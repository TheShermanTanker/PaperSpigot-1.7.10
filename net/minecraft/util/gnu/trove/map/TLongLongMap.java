package net.minecraft.util.gnu.trove.map;

import java.util.Map;
import net.minecraft.util.gnu.trove.TLongCollection;
import net.minecraft.util.gnu.trove.function.TLongFunction;
import net.minecraft.util.gnu.trove.iterator.TLongLongIterator;
import net.minecraft.util.gnu.trove.procedure.TLongLongProcedure;
import net.minecraft.util.gnu.trove.procedure.TLongProcedure;
import net.minecraft.util.gnu.trove.set.TLongSet;

public interface TLongLongMap {
  long getNoEntryKey();
  
  long getNoEntryValue();
  
  long put(long paramLong1, long paramLong2);
  
  long putIfAbsent(long paramLong1, long paramLong2);
  
  void putAll(Map<? extends Long, ? extends Long> paramMap);
  
  void putAll(TLongLongMap paramTLongLongMap);
  
  long get(long paramLong);
  
  void clear();
  
  boolean isEmpty();
  
  long remove(long paramLong);
  
  int size();
  
  TLongSet keySet();
  
  long[] keys();
  
  long[] keys(long[] paramArrayOflong);
  
  TLongCollection valueCollection();
  
  long[] values();
  
  long[] values(long[] paramArrayOflong);
  
  boolean containsValue(long paramLong);
  
  boolean containsKey(long paramLong);
  
  TLongLongIterator iterator();
  
  boolean forEachKey(TLongProcedure paramTLongProcedure);
  
  boolean forEachValue(TLongProcedure paramTLongProcedure);
  
  boolean forEachEntry(TLongLongProcedure paramTLongLongProcedure);
  
  void transformValues(TLongFunction paramTLongFunction);
  
  boolean retainEntries(TLongLongProcedure paramTLongLongProcedure);
  
  boolean increment(long paramLong);
  
  boolean adjustValue(long paramLong1, long paramLong2);
  
  long adjustOrPutValue(long paramLong1, long paramLong2, long paramLong3);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\map\TLongLongMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
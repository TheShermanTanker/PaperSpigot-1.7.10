package net.minecraft.util.gnu.trove.map;

import java.util.Map;
import net.minecraft.util.gnu.trove.TLongCollection;
import net.minecraft.util.gnu.trove.function.TLongFunction;
import net.minecraft.util.gnu.trove.iterator.TIntLongIterator;
import net.minecraft.util.gnu.trove.procedure.TIntLongProcedure;
import net.minecraft.util.gnu.trove.procedure.TIntProcedure;
import net.minecraft.util.gnu.trove.procedure.TLongProcedure;
import net.minecraft.util.gnu.trove.set.TIntSet;

public interface TIntLongMap {
  int getNoEntryKey();
  
  long getNoEntryValue();
  
  long put(int paramInt, long paramLong);
  
  long putIfAbsent(int paramInt, long paramLong);
  
  void putAll(Map<? extends Integer, ? extends Long> paramMap);
  
  void putAll(TIntLongMap paramTIntLongMap);
  
  long get(int paramInt);
  
  void clear();
  
  boolean isEmpty();
  
  long remove(int paramInt);
  
  int size();
  
  TIntSet keySet();
  
  int[] keys();
  
  int[] keys(int[] paramArrayOfint);
  
  TLongCollection valueCollection();
  
  long[] values();
  
  long[] values(long[] paramArrayOflong);
  
  boolean containsValue(long paramLong);
  
  boolean containsKey(int paramInt);
  
  TIntLongIterator iterator();
  
  boolean forEachKey(TIntProcedure paramTIntProcedure);
  
  boolean forEachValue(TLongProcedure paramTLongProcedure);
  
  boolean forEachEntry(TIntLongProcedure paramTIntLongProcedure);
  
  void transformValues(TLongFunction paramTLongFunction);
  
  boolean retainEntries(TIntLongProcedure paramTIntLongProcedure);
  
  boolean increment(int paramInt);
  
  boolean adjustValue(int paramInt, long paramLong);
  
  long adjustOrPutValue(int paramInt, long paramLong1, long paramLong2);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\map\TIntLongMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
package net.minecraft.util.gnu.trove.map;

import java.util.Map;
import net.minecraft.util.gnu.trove.TIntCollection;
import net.minecraft.util.gnu.trove.function.TIntFunction;
import net.minecraft.util.gnu.trove.iterator.TLongIntIterator;
import net.minecraft.util.gnu.trove.procedure.TIntProcedure;
import net.minecraft.util.gnu.trove.procedure.TLongIntProcedure;
import net.minecraft.util.gnu.trove.procedure.TLongProcedure;
import net.minecraft.util.gnu.trove.set.TLongSet;

public interface TLongIntMap {
  long getNoEntryKey();
  
  int getNoEntryValue();
  
  int put(long paramLong, int paramInt);
  
  int putIfAbsent(long paramLong, int paramInt);
  
  void putAll(Map<? extends Long, ? extends Integer> paramMap);
  
  void putAll(TLongIntMap paramTLongIntMap);
  
  int get(long paramLong);
  
  void clear();
  
  boolean isEmpty();
  
  int remove(long paramLong);
  
  int size();
  
  TLongSet keySet();
  
  long[] keys();
  
  long[] keys(long[] paramArrayOflong);
  
  TIntCollection valueCollection();
  
  int[] values();
  
  int[] values(int[] paramArrayOfint);
  
  boolean containsValue(int paramInt);
  
  boolean containsKey(long paramLong);
  
  TLongIntIterator iterator();
  
  boolean forEachKey(TLongProcedure paramTLongProcedure);
  
  boolean forEachValue(TIntProcedure paramTIntProcedure);
  
  boolean forEachEntry(TLongIntProcedure paramTLongIntProcedure);
  
  void transformValues(TIntFunction paramTIntFunction);
  
  boolean retainEntries(TLongIntProcedure paramTLongIntProcedure);
  
  boolean increment(long paramLong);
  
  boolean adjustValue(long paramLong, int paramInt);
  
  int adjustOrPutValue(long paramLong, int paramInt1, int paramInt2);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\map\TLongIntMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
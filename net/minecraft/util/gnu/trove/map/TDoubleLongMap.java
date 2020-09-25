package net.minecraft.util.gnu.trove.map;

import java.util.Map;
import net.minecraft.util.gnu.trove.TLongCollection;
import net.minecraft.util.gnu.trove.function.TLongFunction;
import net.minecraft.util.gnu.trove.iterator.TDoubleLongIterator;
import net.minecraft.util.gnu.trove.procedure.TDoubleLongProcedure;
import net.minecraft.util.gnu.trove.procedure.TDoubleProcedure;
import net.minecraft.util.gnu.trove.procedure.TLongProcedure;
import net.minecraft.util.gnu.trove.set.TDoubleSet;

public interface TDoubleLongMap {
  double getNoEntryKey();
  
  long getNoEntryValue();
  
  long put(double paramDouble, long paramLong);
  
  long putIfAbsent(double paramDouble, long paramLong);
  
  void putAll(Map<? extends Double, ? extends Long> paramMap);
  
  void putAll(TDoubleLongMap paramTDoubleLongMap);
  
  long get(double paramDouble);
  
  void clear();
  
  boolean isEmpty();
  
  long remove(double paramDouble);
  
  int size();
  
  TDoubleSet keySet();
  
  double[] keys();
  
  double[] keys(double[] paramArrayOfdouble);
  
  TLongCollection valueCollection();
  
  long[] values();
  
  long[] values(long[] paramArrayOflong);
  
  boolean containsValue(long paramLong);
  
  boolean containsKey(double paramDouble);
  
  TDoubleLongIterator iterator();
  
  boolean forEachKey(TDoubleProcedure paramTDoubleProcedure);
  
  boolean forEachValue(TLongProcedure paramTLongProcedure);
  
  boolean forEachEntry(TDoubleLongProcedure paramTDoubleLongProcedure);
  
  void transformValues(TLongFunction paramTLongFunction);
  
  boolean retainEntries(TDoubleLongProcedure paramTDoubleLongProcedure);
  
  boolean increment(double paramDouble);
  
  boolean adjustValue(double paramDouble, long paramLong);
  
  long adjustOrPutValue(double paramDouble, long paramLong1, long paramLong2);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\map\TDoubleLongMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
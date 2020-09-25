package net.minecraft.util.gnu.trove.map;

import java.util.Map;
import net.minecraft.util.gnu.trove.TDoubleCollection;
import net.minecraft.util.gnu.trove.function.TDoubleFunction;
import net.minecraft.util.gnu.trove.iterator.TLongDoubleIterator;
import net.minecraft.util.gnu.trove.procedure.TDoubleProcedure;
import net.minecraft.util.gnu.trove.procedure.TLongDoubleProcedure;
import net.minecraft.util.gnu.trove.procedure.TLongProcedure;
import net.minecraft.util.gnu.trove.set.TLongSet;

public interface TLongDoubleMap {
  long getNoEntryKey();
  
  double getNoEntryValue();
  
  double put(long paramLong, double paramDouble);
  
  double putIfAbsent(long paramLong, double paramDouble);
  
  void putAll(Map<? extends Long, ? extends Double> paramMap);
  
  void putAll(TLongDoubleMap paramTLongDoubleMap);
  
  double get(long paramLong);
  
  void clear();
  
  boolean isEmpty();
  
  double remove(long paramLong);
  
  int size();
  
  TLongSet keySet();
  
  long[] keys();
  
  long[] keys(long[] paramArrayOflong);
  
  TDoubleCollection valueCollection();
  
  double[] values();
  
  double[] values(double[] paramArrayOfdouble);
  
  boolean containsValue(double paramDouble);
  
  boolean containsKey(long paramLong);
  
  TLongDoubleIterator iterator();
  
  boolean forEachKey(TLongProcedure paramTLongProcedure);
  
  boolean forEachValue(TDoubleProcedure paramTDoubleProcedure);
  
  boolean forEachEntry(TLongDoubleProcedure paramTLongDoubleProcedure);
  
  void transformValues(TDoubleFunction paramTDoubleFunction);
  
  boolean retainEntries(TLongDoubleProcedure paramTLongDoubleProcedure);
  
  boolean increment(long paramLong);
  
  boolean adjustValue(long paramLong, double paramDouble);
  
  double adjustOrPutValue(long paramLong, double paramDouble1, double paramDouble2);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\map\TLongDoubleMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
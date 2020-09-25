package net.minecraft.util.gnu.trove.map;

import java.util.Map;
import net.minecraft.util.gnu.trove.TDoubleCollection;
import net.minecraft.util.gnu.trove.function.TDoubleFunction;
import net.minecraft.util.gnu.trove.iterator.TIntDoubleIterator;
import net.minecraft.util.gnu.trove.procedure.TDoubleProcedure;
import net.minecraft.util.gnu.trove.procedure.TIntDoubleProcedure;
import net.minecraft.util.gnu.trove.procedure.TIntProcedure;
import net.minecraft.util.gnu.trove.set.TIntSet;

public interface TIntDoubleMap {
  int getNoEntryKey();
  
  double getNoEntryValue();
  
  double put(int paramInt, double paramDouble);
  
  double putIfAbsent(int paramInt, double paramDouble);
  
  void putAll(Map<? extends Integer, ? extends Double> paramMap);
  
  void putAll(TIntDoubleMap paramTIntDoubleMap);
  
  double get(int paramInt);
  
  void clear();
  
  boolean isEmpty();
  
  double remove(int paramInt);
  
  int size();
  
  TIntSet keySet();
  
  int[] keys();
  
  int[] keys(int[] paramArrayOfint);
  
  TDoubleCollection valueCollection();
  
  double[] values();
  
  double[] values(double[] paramArrayOfdouble);
  
  boolean containsValue(double paramDouble);
  
  boolean containsKey(int paramInt);
  
  TIntDoubleIterator iterator();
  
  boolean forEachKey(TIntProcedure paramTIntProcedure);
  
  boolean forEachValue(TDoubleProcedure paramTDoubleProcedure);
  
  boolean forEachEntry(TIntDoubleProcedure paramTIntDoubleProcedure);
  
  void transformValues(TDoubleFunction paramTDoubleFunction);
  
  boolean retainEntries(TIntDoubleProcedure paramTIntDoubleProcedure);
  
  boolean increment(int paramInt);
  
  boolean adjustValue(int paramInt, double paramDouble);
  
  double adjustOrPutValue(int paramInt, double paramDouble1, double paramDouble2);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\map\TIntDoubleMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
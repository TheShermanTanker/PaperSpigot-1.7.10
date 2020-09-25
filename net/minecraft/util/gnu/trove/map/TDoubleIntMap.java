package net.minecraft.util.gnu.trove.map;

import java.util.Map;
import net.minecraft.util.gnu.trove.TIntCollection;
import net.minecraft.util.gnu.trove.function.TIntFunction;
import net.minecraft.util.gnu.trove.iterator.TDoubleIntIterator;
import net.minecraft.util.gnu.trove.procedure.TDoubleIntProcedure;
import net.minecraft.util.gnu.trove.procedure.TDoubleProcedure;
import net.minecraft.util.gnu.trove.procedure.TIntProcedure;
import net.minecraft.util.gnu.trove.set.TDoubleSet;

public interface TDoubleIntMap {
  double getNoEntryKey();
  
  int getNoEntryValue();
  
  int put(double paramDouble, int paramInt);
  
  int putIfAbsent(double paramDouble, int paramInt);
  
  void putAll(Map<? extends Double, ? extends Integer> paramMap);
  
  void putAll(TDoubleIntMap paramTDoubleIntMap);
  
  int get(double paramDouble);
  
  void clear();
  
  boolean isEmpty();
  
  int remove(double paramDouble);
  
  int size();
  
  TDoubleSet keySet();
  
  double[] keys();
  
  double[] keys(double[] paramArrayOfdouble);
  
  TIntCollection valueCollection();
  
  int[] values();
  
  int[] values(int[] paramArrayOfint);
  
  boolean containsValue(int paramInt);
  
  boolean containsKey(double paramDouble);
  
  TDoubleIntIterator iterator();
  
  boolean forEachKey(TDoubleProcedure paramTDoubleProcedure);
  
  boolean forEachValue(TIntProcedure paramTIntProcedure);
  
  boolean forEachEntry(TDoubleIntProcedure paramTDoubleIntProcedure);
  
  void transformValues(TIntFunction paramTIntFunction);
  
  boolean retainEntries(TDoubleIntProcedure paramTDoubleIntProcedure);
  
  boolean increment(double paramDouble);
  
  boolean adjustValue(double paramDouble, int paramInt);
  
  int adjustOrPutValue(double paramDouble, int paramInt1, int paramInt2);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\map\TDoubleIntMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
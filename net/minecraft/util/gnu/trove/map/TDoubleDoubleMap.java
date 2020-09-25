package net.minecraft.util.gnu.trove.map;

import java.util.Map;
import net.minecraft.util.gnu.trove.TDoubleCollection;
import net.minecraft.util.gnu.trove.function.TDoubleFunction;
import net.minecraft.util.gnu.trove.iterator.TDoubleDoubleIterator;
import net.minecraft.util.gnu.trove.procedure.TDoubleDoubleProcedure;
import net.minecraft.util.gnu.trove.procedure.TDoubleProcedure;
import net.minecraft.util.gnu.trove.set.TDoubleSet;

public interface TDoubleDoubleMap {
  double getNoEntryKey();
  
  double getNoEntryValue();
  
  double put(double paramDouble1, double paramDouble2);
  
  double putIfAbsent(double paramDouble1, double paramDouble2);
  
  void putAll(Map<? extends Double, ? extends Double> paramMap);
  
  void putAll(TDoubleDoubleMap paramTDoubleDoubleMap);
  
  double get(double paramDouble);
  
  void clear();
  
  boolean isEmpty();
  
  double remove(double paramDouble);
  
  int size();
  
  TDoubleSet keySet();
  
  double[] keys();
  
  double[] keys(double[] paramArrayOfdouble);
  
  TDoubleCollection valueCollection();
  
  double[] values();
  
  double[] values(double[] paramArrayOfdouble);
  
  boolean containsValue(double paramDouble);
  
  boolean containsKey(double paramDouble);
  
  TDoubleDoubleIterator iterator();
  
  boolean forEachKey(TDoubleProcedure paramTDoubleProcedure);
  
  boolean forEachValue(TDoubleProcedure paramTDoubleProcedure);
  
  boolean forEachEntry(TDoubleDoubleProcedure paramTDoubleDoubleProcedure);
  
  void transformValues(TDoubleFunction paramTDoubleFunction);
  
  boolean retainEntries(TDoubleDoubleProcedure paramTDoubleDoubleProcedure);
  
  boolean increment(double paramDouble);
  
  boolean adjustValue(double paramDouble1, double paramDouble2);
  
  double adjustOrPutValue(double paramDouble1, double paramDouble2, double paramDouble3);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\map\TDoubleDoubleMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
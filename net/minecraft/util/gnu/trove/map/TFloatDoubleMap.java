package net.minecraft.util.gnu.trove.map;

import java.util.Map;
import net.minecraft.util.gnu.trove.TDoubleCollection;
import net.minecraft.util.gnu.trove.function.TDoubleFunction;
import net.minecraft.util.gnu.trove.iterator.TFloatDoubleIterator;
import net.minecraft.util.gnu.trove.procedure.TDoubleProcedure;
import net.minecraft.util.gnu.trove.procedure.TFloatDoubleProcedure;
import net.minecraft.util.gnu.trove.procedure.TFloatProcedure;
import net.minecraft.util.gnu.trove.set.TFloatSet;

public interface TFloatDoubleMap {
  float getNoEntryKey();
  
  double getNoEntryValue();
  
  double put(float paramFloat, double paramDouble);
  
  double putIfAbsent(float paramFloat, double paramDouble);
  
  void putAll(Map<? extends Float, ? extends Double> paramMap);
  
  void putAll(TFloatDoubleMap paramTFloatDoubleMap);
  
  double get(float paramFloat);
  
  void clear();
  
  boolean isEmpty();
  
  double remove(float paramFloat);
  
  int size();
  
  TFloatSet keySet();
  
  float[] keys();
  
  float[] keys(float[] paramArrayOffloat);
  
  TDoubleCollection valueCollection();
  
  double[] values();
  
  double[] values(double[] paramArrayOfdouble);
  
  boolean containsValue(double paramDouble);
  
  boolean containsKey(float paramFloat);
  
  TFloatDoubleIterator iterator();
  
  boolean forEachKey(TFloatProcedure paramTFloatProcedure);
  
  boolean forEachValue(TDoubleProcedure paramTDoubleProcedure);
  
  boolean forEachEntry(TFloatDoubleProcedure paramTFloatDoubleProcedure);
  
  void transformValues(TDoubleFunction paramTDoubleFunction);
  
  boolean retainEntries(TFloatDoubleProcedure paramTFloatDoubleProcedure);
  
  boolean increment(float paramFloat);
  
  boolean adjustValue(float paramFloat, double paramDouble);
  
  double adjustOrPutValue(float paramFloat, double paramDouble1, double paramDouble2);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\map\TFloatDoubleMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
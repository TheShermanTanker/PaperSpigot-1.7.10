package net.minecraft.util.gnu.trove.map;

import java.util.Map;
import net.minecraft.util.gnu.trove.TFloatCollection;
import net.minecraft.util.gnu.trove.function.TFloatFunction;
import net.minecraft.util.gnu.trove.iterator.TDoubleFloatIterator;
import net.minecraft.util.gnu.trove.procedure.TDoubleFloatProcedure;
import net.minecraft.util.gnu.trove.procedure.TDoubleProcedure;
import net.minecraft.util.gnu.trove.procedure.TFloatProcedure;
import net.minecraft.util.gnu.trove.set.TDoubleSet;

public interface TDoubleFloatMap {
  double getNoEntryKey();
  
  float getNoEntryValue();
  
  float put(double paramDouble, float paramFloat);
  
  float putIfAbsent(double paramDouble, float paramFloat);
  
  void putAll(Map<? extends Double, ? extends Float> paramMap);
  
  void putAll(TDoubleFloatMap paramTDoubleFloatMap);
  
  float get(double paramDouble);
  
  void clear();
  
  boolean isEmpty();
  
  float remove(double paramDouble);
  
  int size();
  
  TDoubleSet keySet();
  
  double[] keys();
  
  double[] keys(double[] paramArrayOfdouble);
  
  TFloatCollection valueCollection();
  
  float[] values();
  
  float[] values(float[] paramArrayOffloat);
  
  boolean containsValue(float paramFloat);
  
  boolean containsKey(double paramDouble);
  
  TDoubleFloatIterator iterator();
  
  boolean forEachKey(TDoubleProcedure paramTDoubleProcedure);
  
  boolean forEachValue(TFloatProcedure paramTFloatProcedure);
  
  boolean forEachEntry(TDoubleFloatProcedure paramTDoubleFloatProcedure);
  
  void transformValues(TFloatFunction paramTFloatFunction);
  
  boolean retainEntries(TDoubleFloatProcedure paramTDoubleFloatProcedure);
  
  boolean increment(double paramDouble);
  
  boolean adjustValue(double paramDouble, float paramFloat);
  
  float adjustOrPutValue(double paramDouble, float paramFloat1, float paramFloat2);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\map\TDoubleFloatMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
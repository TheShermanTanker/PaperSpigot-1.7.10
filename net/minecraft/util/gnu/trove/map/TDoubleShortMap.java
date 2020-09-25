package net.minecraft.util.gnu.trove.map;

import java.util.Map;
import net.minecraft.util.gnu.trove.TShortCollection;
import net.minecraft.util.gnu.trove.function.TShortFunction;
import net.minecraft.util.gnu.trove.iterator.TDoubleShortIterator;
import net.minecraft.util.gnu.trove.procedure.TDoubleProcedure;
import net.minecraft.util.gnu.trove.procedure.TDoubleShortProcedure;
import net.minecraft.util.gnu.trove.procedure.TShortProcedure;
import net.minecraft.util.gnu.trove.set.TDoubleSet;

public interface TDoubleShortMap {
  double getNoEntryKey();
  
  short getNoEntryValue();
  
  short put(double paramDouble, short paramShort);
  
  short putIfAbsent(double paramDouble, short paramShort);
  
  void putAll(Map<? extends Double, ? extends Short> paramMap);
  
  void putAll(TDoubleShortMap paramTDoubleShortMap);
  
  short get(double paramDouble);
  
  void clear();
  
  boolean isEmpty();
  
  short remove(double paramDouble);
  
  int size();
  
  TDoubleSet keySet();
  
  double[] keys();
  
  double[] keys(double[] paramArrayOfdouble);
  
  TShortCollection valueCollection();
  
  short[] values();
  
  short[] values(short[] paramArrayOfshort);
  
  boolean containsValue(short paramShort);
  
  boolean containsKey(double paramDouble);
  
  TDoubleShortIterator iterator();
  
  boolean forEachKey(TDoubleProcedure paramTDoubleProcedure);
  
  boolean forEachValue(TShortProcedure paramTShortProcedure);
  
  boolean forEachEntry(TDoubleShortProcedure paramTDoubleShortProcedure);
  
  void transformValues(TShortFunction paramTShortFunction);
  
  boolean retainEntries(TDoubleShortProcedure paramTDoubleShortProcedure);
  
  boolean increment(double paramDouble);
  
  boolean adjustValue(double paramDouble, short paramShort);
  
  short adjustOrPutValue(double paramDouble, short paramShort1, short paramShort2);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\map\TDoubleShortMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
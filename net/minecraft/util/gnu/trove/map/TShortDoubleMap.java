package net.minecraft.util.gnu.trove.map;

import java.util.Map;
import net.minecraft.util.gnu.trove.TDoubleCollection;
import net.minecraft.util.gnu.trove.function.TDoubleFunction;
import net.minecraft.util.gnu.trove.iterator.TShortDoubleIterator;
import net.minecraft.util.gnu.trove.procedure.TDoubleProcedure;
import net.minecraft.util.gnu.trove.procedure.TShortDoubleProcedure;
import net.minecraft.util.gnu.trove.procedure.TShortProcedure;
import net.minecraft.util.gnu.trove.set.TShortSet;

public interface TShortDoubleMap {
  short getNoEntryKey();
  
  double getNoEntryValue();
  
  double put(short paramShort, double paramDouble);
  
  double putIfAbsent(short paramShort, double paramDouble);
  
  void putAll(Map<? extends Short, ? extends Double> paramMap);
  
  void putAll(TShortDoubleMap paramTShortDoubleMap);
  
  double get(short paramShort);
  
  void clear();
  
  boolean isEmpty();
  
  double remove(short paramShort);
  
  int size();
  
  TShortSet keySet();
  
  short[] keys();
  
  short[] keys(short[] paramArrayOfshort);
  
  TDoubleCollection valueCollection();
  
  double[] values();
  
  double[] values(double[] paramArrayOfdouble);
  
  boolean containsValue(double paramDouble);
  
  boolean containsKey(short paramShort);
  
  TShortDoubleIterator iterator();
  
  boolean forEachKey(TShortProcedure paramTShortProcedure);
  
  boolean forEachValue(TDoubleProcedure paramTDoubleProcedure);
  
  boolean forEachEntry(TShortDoubleProcedure paramTShortDoubleProcedure);
  
  void transformValues(TDoubleFunction paramTDoubleFunction);
  
  boolean retainEntries(TShortDoubleProcedure paramTShortDoubleProcedure);
  
  boolean increment(short paramShort);
  
  boolean adjustValue(short paramShort, double paramDouble);
  
  double adjustOrPutValue(short paramShort, double paramDouble1, double paramDouble2);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\map\TShortDoubleMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
package net.minecraft.util.gnu.trove.map;

import java.util.Map;
import net.minecraft.util.gnu.trove.TDoubleCollection;
import net.minecraft.util.gnu.trove.function.TDoubleFunction;
import net.minecraft.util.gnu.trove.iterator.TCharDoubleIterator;
import net.minecraft.util.gnu.trove.procedure.TCharDoubleProcedure;
import net.minecraft.util.gnu.trove.procedure.TCharProcedure;
import net.minecraft.util.gnu.trove.procedure.TDoubleProcedure;
import net.minecraft.util.gnu.trove.set.TCharSet;

public interface TCharDoubleMap {
  char getNoEntryKey();
  
  double getNoEntryValue();
  
  double put(char paramChar, double paramDouble);
  
  double putIfAbsent(char paramChar, double paramDouble);
  
  void putAll(Map<? extends Character, ? extends Double> paramMap);
  
  void putAll(TCharDoubleMap paramTCharDoubleMap);
  
  double get(char paramChar);
  
  void clear();
  
  boolean isEmpty();
  
  double remove(char paramChar);
  
  int size();
  
  TCharSet keySet();
  
  char[] keys();
  
  char[] keys(char[] paramArrayOfchar);
  
  TDoubleCollection valueCollection();
  
  double[] values();
  
  double[] values(double[] paramArrayOfdouble);
  
  boolean containsValue(double paramDouble);
  
  boolean containsKey(char paramChar);
  
  TCharDoubleIterator iterator();
  
  boolean forEachKey(TCharProcedure paramTCharProcedure);
  
  boolean forEachValue(TDoubleProcedure paramTDoubleProcedure);
  
  boolean forEachEntry(TCharDoubleProcedure paramTCharDoubleProcedure);
  
  void transformValues(TDoubleFunction paramTDoubleFunction);
  
  boolean retainEntries(TCharDoubleProcedure paramTCharDoubleProcedure);
  
  boolean increment(char paramChar);
  
  boolean adjustValue(char paramChar, double paramDouble);
  
  double adjustOrPutValue(char paramChar, double paramDouble1, double paramDouble2);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\map\TCharDoubleMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
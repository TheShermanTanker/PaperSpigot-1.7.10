package net.minecraft.util.gnu.trove.map;

import java.util.Map;
import net.minecraft.util.gnu.trove.TCharCollection;
import net.minecraft.util.gnu.trove.function.TCharFunction;
import net.minecraft.util.gnu.trove.iterator.TDoubleCharIterator;
import net.minecraft.util.gnu.trove.procedure.TCharProcedure;
import net.minecraft.util.gnu.trove.procedure.TDoubleCharProcedure;
import net.minecraft.util.gnu.trove.procedure.TDoubleProcedure;
import net.minecraft.util.gnu.trove.set.TDoubleSet;

public interface TDoubleCharMap {
  double getNoEntryKey();
  
  char getNoEntryValue();
  
  char put(double paramDouble, char paramChar);
  
  char putIfAbsent(double paramDouble, char paramChar);
  
  void putAll(Map<? extends Double, ? extends Character> paramMap);
  
  void putAll(TDoubleCharMap paramTDoubleCharMap);
  
  char get(double paramDouble);
  
  void clear();
  
  boolean isEmpty();
  
  char remove(double paramDouble);
  
  int size();
  
  TDoubleSet keySet();
  
  double[] keys();
  
  double[] keys(double[] paramArrayOfdouble);
  
  TCharCollection valueCollection();
  
  char[] values();
  
  char[] values(char[] paramArrayOfchar);
  
  boolean containsValue(char paramChar);
  
  boolean containsKey(double paramDouble);
  
  TDoubleCharIterator iterator();
  
  boolean forEachKey(TDoubleProcedure paramTDoubleProcedure);
  
  boolean forEachValue(TCharProcedure paramTCharProcedure);
  
  boolean forEachEntry(TDoubleCharProcedure paramTDoubleCharProcedure);
  
  void transformValues(TCharFunction paramTCharFunction);
  
  boolean retainEntries(TDoubleCharProcedure paramTDoubleCharProcedure);
  
  boolean increment(double paramDouble);
  
  boolean adjustValue(double paramDouble, char paramChar);
  
  char adjustOrPutValue(double paramDouble, char paramChar1, char paramChar2);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\map\TDoubleCharMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
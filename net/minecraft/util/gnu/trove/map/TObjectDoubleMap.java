package net.minecraft.util.gnu.trove.map;

import java.util.Map;
import java.util.Set;
import net.minecraft.util.gnu.trove.TDoubleCollection;
import net.minecraft.util.gnu.trove.function.TDoubleFunction;
import net.minecraft.util.gnu.trove.iterator.TObjectDoubleIterator;
import net.minecraft.util.gnu.trove.procedure.TDoubleProcedure;
import net.minecraft.util.gnu.trove.procedure.TObjectDoubleProcedure;
import net.minecraft.util.gnu.trove.procedure.TObjectProcedure;

public interface TObjectDoubleMap<K> {
  double getNoEntryValue();
  
  int size();
  
  boolean isEmpty();
  
  boolean containsKey(Object paramObject);
  
  boolean containsValue(double paramDouble);
  
  double get(Object paramObject);
  
  double put(K paramK, double paramDouble);
  
  double putIfAbsent(K paramK, double paramDouble);
  
  double remove(Object paramObject);
  
  void putAll(Map<? extends K, ? extends Double> paramMap);
  
  void putAll(TObjectDoubleMap<? extends K> paramTObjectDoubleMap);
  
  void clear();
  
  Set<K> keySet();
  
  Object[] keys();
  
  K[] keys(K[] paramArrayOfK);
  
  TDoubleCollection valueCollection();
  
  double[] values();
  
  double[] values(double[] paramArrayOfdouble);
  
  TObjectDoubleIterator<K> iterator();
  
  boolean increment(K paramK);
  
  boolean adjustValue(K paramK, double paramDouble);
  
  double adjustOrPutValue(K paramK, double paramDouble1, double paramDouble2);
  
  boolean forEachKey(TObjectProcedure<? super K> paramTObjectProcedure);
  
  boolean forEachValue(TDoubleProcedure paramTDoubleProcedure);
  
  boolean forEachEntry(TObjectDoubleProcedure<? super K> paramTObjectDoubleProcedure);
  
  void transformValues(TDoubleFunction paramTDoubleFunction);
  
  boolean retainEntries(TObjectDoubleProcedure<? super K> paramTObjectDoubleProcedure);
  
  boolean equals(Object paramObject);
  
  int hashCode();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\map\TObjectDoubleMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
package net.minecraft.util.gnu.trove.map;

import java.util.Map;
import java.util.Set;
import net.minecraft.util.gnu.trove.TCharCollection;
import net.minecraft.util.gnu.trove.function.TCharFunction;
import net.minecraft.util.gnu.trove.iterator.TObjectCharIterator;
import net.minecraft.util.gnu.trove.procedure.TCharProcedure;
import net.minecraft.util.gnu.trove.procedure.TObjectCharProcedure;
import net.minecraft.util.gnu.trove.procedure.TObjectProcedure;

public interface TObjectCharMap<K> {
  char getNoEntryValue();
  
  int size();
  
  boolean isEmpty();
  
  boolean containsKey(Object paramObject);
  
  boolean containsValue(char paramChar);
  
  char get(Object paramObject);
  
  char put(K paramK, char paramChar);
  
  char putIfAbsent(K paramK, char paramChar);
  
  char remove(Object paramObject);
  
  void putAll(Map<? extends K, ? extends Character> paramMap);
  
  void putAll(TObjectCharMap<? extends K> paramTObjectCharMap);
  
  void clear();
  
  Set<K> keySet();
  
  Object[] keys();
  
  K[] keys(K[] paramArrayOfK);
  
  TCharCollection valueCollection();
  
  char[] values();
  
  char[] values(char[] paramArrayOfchar);
  
  TObjectCharIterator<K> iterator();
  
  boolean increment(K paramK);
  
  boolean adjustValue(K paramK, char paramChar);
  
  char adjustOrPutValue(K paramK, char paramChar1, char paramChar2);
  
  boolean forEachKey(TObjectProcedure<? super K> paramTObjectProcedure);
  
  boolean forEachValue(TCharProcedure paramTCharProcedure);
  
  boolean forEachEntry(TObjectCharProcedure<? super K> paramTObjectCharProcedure);
  
  void transformValues(TCharFunction paramTCharFunction);
  
  boolean retainEntries(TObjectCharProcedure<? super K> paramTObjectCharProcedure);
  
  boolean equals(Object paramObject);
  
  int hashCode();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\map\TObjectCharMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
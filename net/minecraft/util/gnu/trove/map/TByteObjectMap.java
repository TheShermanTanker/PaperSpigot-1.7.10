package net.minecraft.util.gnu.trove.map;

import java.util.Collection;
import java.util.Map;
import net.minecraft.util.gnu.trove.function.TObjectFunction;
import net.minecraft.util.gnu.trove.iterator.TByteObjectIterator;
import net.minecraft.util.gnu.trove.procedure.TByteObjectProcedure;
import net.minecraft.util.gnu.trove.procedure.TByteProcedure;
import net.minecraft.util.gnu.trove.procedure.TObjectProcedure;
import net.minecraft.util.gnu.trove.set.TByteSet;

public interface TByteObjectMap<V> {
  byte getNoEntryKey();
  
  int size();
  
  boolean isEmpty();
  
  boolean containsKey(byte paramByte);
  
  boolean containsValue(Object paramObject);
  
  V get(byte paramByte);
  
  V put(byte paramByte, V paramV);
  
  V putIfAbsent(byte paramByte, V paramV);
  
  V remove(byte paramByte);
  
  void putAll(Map<? extends Byte, ? extends V> paramMap);
  
  void putAll(TByteObjectMap<? extends V> paramTByteObjectMap);
  
  void clear();
  
  TByteSet keySet();
  
  byte[] keys();
  
  byte[] keys(byte[] paramArrayOfbyte);
  
  Collection<V> valueCollection();
  
  Object[] values();
  
  V[] values(V[] paramArrayOfV);
  
  TByteObjectIterator<V> iterator();
  
  boolean forEachKey(TByteProcedure paramTByteProcedure);
  
  boolean forEachValue(TObjectProcedure<? super V> paramTObjectProcedure);
  
  boolean forEachEntry(TByteObjectProcedure<? super V> paramTByteObjectProcedure);
  
  void transformValues(TObjectFunction<V, V> paramTObjectFunction);
  
  boolean retainEntries(TByteObjectProcedure<? super V> paramTByteObjectProcedure);
  
  boolean equals(Object paramObject);
  
  int hashCode();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\map\TByteObjectMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
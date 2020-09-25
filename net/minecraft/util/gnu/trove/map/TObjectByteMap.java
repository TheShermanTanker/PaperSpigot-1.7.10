package net.minecraft.util.gnu.trove.map;

import java.util.Map;
import java.util.Set;
import net.minecraft.util.gnu.trove.TByteCollection;
import net.minecraft.util.gnu.trove.function.TByteFunction;
import net.minecraft.util.gnu.trove.iterator.TObjectByteIterator;
import net.minecraft.util.gnu.trove.procedure.TByteProcedure;
import net.minecraft.util.gnu.trove.procedure.TObjectByteProcedure;
import net.minecraft.util.gnu.trove.procedure.TObjectProcedure;

public interface TObjectByteMap<K> {
  byte getNoEntryValue();
  
  int size();
  
  boolean isEmpty();
  
  boolean containsKey(Object paramObject);
  
  boolean containsValue(byte paramByte);
  
  byte get(Object paramObject);
  
  byte put(K paramK, byte paramByte);
  
  byte putIfAbsent(K paramK, byte paramByte);
  
  byte remove(Object paramObject);
  
  void putAll(Map<? extends K, ? extends Byte> paramMap);
  
  void putAll(TObjectByteMap<? extends K> paramTObjectByteMap);
  
  void clear();
  
  Set<K> keySet();
  
  Object[] keys();
  
  K[] keys(K[] paramArrayOfK);
  
  TByteCollection valueCollection();
  
  byte[] values();
  
  byte[] values(byte[] paramArrayOfbyte);
  
  TObjectByteIterator<K> iterator();
  
  boolean increment(K paramK);
  
  boolean adjustValue(K paramK, byte paramByte);
  
  byte adjustOrPutValue(K paramK, byte paramByte1, byte paramByte2);
  
  boolean forEachKey(TObjectProcedure<? super K> paramTObjectProcedure);
  
  boolean forEachValue(TByteProcedure paramTByteProcedure);
  
  boolean forEachEntry(TObjectByteProcedure<? super K> paramTObjectByteProcedure);
  
  void transformValues(TByteFunction paramTByteFunction);
  
  boolean retainEntries(TObjectByteProcedure<? super K> paramTObjectByteProcedure);
  
  boolean equals(Object paramObject);
  
  int hashCode();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\map\TObjectByteMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
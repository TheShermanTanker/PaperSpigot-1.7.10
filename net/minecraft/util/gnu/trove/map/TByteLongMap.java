package net.minecraft.util.gnu.trove.map;

import java.util.Map;
import net.minecraft.util.gnu.trove.TLongCollection;
import net.minecraft.util.gnu.trove.function.TLongFunction;
import net.minecraft.util.gnu.trove.iterator.TByteLongIterator;
import net.minecraft.util.gnu.trove.procedure.TByteLongProcedure;
import net.minecraft.util.gnu.trove.procedure.TByteProcedure;
import net.minecraft.util.gnu.trove.procedure.TLongProcedure;
import net.minecraft.util.gnu.trove.set.TByteSet;

public interface TByteLongMap {
  byte getNoEntryKey();
  
  long getNoEntryValue();
  
  long put(byte paramByte, long paramLong);
  
  long putIfAbsent(byte paramByte, long paramLong);
  
  void putAll(Map<? extends Byte, ? extends Long> paramMap);
  
  void putAll(TByteLongMap paramTByteLongMap);
  
  long get(byte paramByte);
  
  void clear();
  
  boolean isEmpty();
  
  long remove(byte paramByte);
  
  int size();
  
  TByteSet keySet();
  
  byte[] keys();
  
  byte[] keys(byte[] paramArrayOfbyte);
  
  TLongCollection valueCollection();
  
  long[] values();
  
  long[] values(long[] paramArrayOflong);
  
  boolean containsValue(long paramLong);
  
  boolean containsKey(byte paramByte);
  
  TByteLongIterator iterator();
  
  boolean forEachKey(TByteProcedure paramTByteProcedure);
  
  boolean forEachValue(TLongProcedure paramTLongProcedure);
  
  boolean forEachEntry(TByteLongProcedure paramTByteLongProcedure);
  
  void transformValues(TLongFunction paramTLongFunction);
  
  boolean retainEntries(TByteLongProcedure paramTByteLongProcedure);
  
  boolean increment(byte paramByte);
  
  boolean adjustValue(byte paramByte, long paramLong);
  
  long adjustOrPutValue(byte paramByte, long paramLong1, long paramLong2);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\map\TByteLongMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
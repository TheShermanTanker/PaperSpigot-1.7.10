package net.minecraft.util.gnu.trove.map;

import java.util.Map;
import net.minecraft.util.gnu.trove.TByteCollection;
import net.minecraft.util.gnu.trove.function.TByteFunction;
import net.minecraft.util.gnu.trove.iterator.TLongByteIterator;
import net.minecraft.util.gnu.trove.procedure.TByteProcedure;
import net.minecraft.util.gnu.trove.procedure.TLongByteProcedure;
import net.minecraft.util.gnu.trove.procedure.TLongProcedure;
import net.minecraft.util.gnu.trove.set.TLongSet;

public interface TLongByteMap {
  long getNoEntryKey();
  
  byte getNoEntryValue();
  
  byte put(long paramLong, byte paramByte);
  
  byte putIfAbsent(long paramLong, byte paramByte);
  
  void putAll(Map<? extends Long, ? extends Byte> paramMap);
  
  void putAll(TLongByteMap paramTLongByteMap);
  
  byte get(long paramLong);
  
  void clear();
  
  boolean isEmpty();
  
  byte remove(long paramLong);
  
  int size();
  
  TLongSet keySet();
  
  long[] keys();
  
  long[] keys(long[] paramArrayOflong);
  
  TByteCollection valueCollection();
  
  byte[] values();
  
  byte[] values(byte[] paramArrayOfbyte);
  
  boolean containsValue(byte paramByte);
  
  boolean containsKey(long paramLong);
  
  TLongByteIterator iterator();
  
  boolean forEachKey(TLongProcedure paramTLongProcedure);
  
  boolean forEachValue(TByteProcedure paramTByteProcedure);
  
  boolean forEachEntry(TLongByteProcedure paramTLongByteProcedure);
  
  void transformValues(TByteFunction paramTByteFunction);
  
  boolean retainEntries(TLongByteProcedure paramTLongByteProcedure);
  
  boolean increment(long paramLong);
  
  boolean adjustValue(long paramLong, byte paramByte);
  
  byte adjustOrPutValue(long paramLong, byte paramByte1, byte paramByte2);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\map\TLongByteMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
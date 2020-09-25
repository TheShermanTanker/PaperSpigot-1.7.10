package net.minecraft.util.gnu.trove.map;

import java.util.Map;
import net.minecraft.util.gnu.trove.TByteCollection;
import net.minecraft.util.gnu.trove.function.TByteFunction;
import net.minecraft.util.gnu.trove.iterator.TByteByteIterator;
import net.minecraft.util.gnu.trove.procedure.TByteByteProcedure;
import net.minecraft.util.gnu.trove.procedure.TByteProcedure;
import net.minecraft.util.gnu.trove.set.TByteSet;

public interface TByteByteMap {
  byte getNoEntryKey();
  
  byte getNoEntryValue();
  
  byte put(byte paramByte1, byte paramByte2);
  
  byte putIfAbsent(byte paramByte1, byte paramByte2);
  
  void putAll(Map<? extends Byte, ? extends Byte> paramMap);
  
  void putAll(TByteByteMap paramTByteByteMap);
  
  byte get(byte paramByte);
  
  void clear();
  
  boolean isEmpty();
  
  byte remove(byte paramByte);
  
  int size();
  
  TByteSet keySet();
  
  byte[] keys();
  
  byte[] keys(byte[] paramArrayOfbyte);
  
  TByteCollection valueCollection();
  
  byte[] values();
  
  byte[] values(byte[] paramArrayOfbyte);
  
  boolean containsValue(byte paramByte);
  
  boolean containsKey(byte paramByte);
  
  TByteByteIterator iterator();
  
  boolean forEachKey(TByteProcedure paramTByteProcedure);
  
  boolean forEachValue(TByteProcedure paramTByteProcedure);
  
  boolean forEachEntry(TByteByteProcedure paramTByteByteProcedure);
  
  void transformValues(TByteFunction paramTByteFunction);
  
  boolean retainEntries(TByteByteProcedure paramTByteByteProcedure);
  
  boolean increment(byte paramByte);
  
  boolean adjustValue(byte paramByte1, byte paramByte2);
  
  byte adjustOrPutValue(byte paramByte1, byte paramByte2, byte paramByte3);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\map\TByteByteMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
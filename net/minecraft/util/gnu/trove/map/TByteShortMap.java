package net.minecraft.util.gnu.trove.map;

import java.util.Map;
import net.minecraft.util.gnu.trove.TShortCollection;
import net.minecraft.util.gnu.trove.function.TShortFunction;
import net.minecraft.util.gnu.trove.iterator.TByteShortIterator;
import net.minecraft.util.gnu.trove.procedure.TByteProcedure;
import net.minecraft.util.gnu.trove.procedure.TByteShortProcedure;
import net.minecraft.util.gnu.trove.procedure.TShortProcedure;
import net.minecraft.util.gnu.trove.set.TByteSet;

public interface TByteShortMap {
  byte getNoEntryKey();
  
  short getNoEntryValue();
  
  short put(byte paramByte, short paramShort);
  
  short putIfAbsent(byte paramByte, short paramShort);
  
  void putAll(Map<? extends Byte, ? extends Short> paramMap);
  
  void putAll(TByteShortMap paramTByteShortMap);
  
  short get(byte paramByte);
  
  void clear();
  
  boolean isEmpty();
  
  short remove(byte paramByte);
  
  int size();
  
  TByteSet keySet();
  
  byte[] keys();
  
  byte[] keys(byte[] paramArrayOfbyte);
  
  TShortCollection valueCollection();
  
  short[] values();
  
  short[] values(short[] paramArrayOfshort);
  
  boolean containsValue(short paramShort);
  
  boolean containsKey(byte paramByte);
  
  TByteShortIterator iterator();
  
  boolean forEachKey(TByteProcedure paramTByteProcedure);
  
  boolean forEachValue(TShortProcedure paramTShortProcedure);
  
  boolean forEachEntry(TByteShortProcedure paramTByteShortProcedure);
  
  void transformValues(TShortFunction paramTShortFunction);
  
  boolean retainEntries(TByteShortProcedure paramTByteShortProcedure);
  
  boolean increment(byte paramByte);
  
  boolean adjustValue(byte paramByte, short paramShort);
  
  short adjustOrPutValue(byte paramByte, short paramShort1, short paramShort2);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\map\TByteShortMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
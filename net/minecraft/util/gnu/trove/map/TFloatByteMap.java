package net.minecraft.util.gnu.trove.map;

import java.util.Map;
import net.minecraft.util.gnu.trove.TByteCollection;
import net.minecraft.util.gnu.trove.function.TByteFunction;
import net.minecraft.util.gnu.trove.iterator.TFloatByteIterator;
import net.minecraft.util.gnu.trove.procedure.TByteProcedure;
import net.minecraft.util.gnu.trove.procedure.TFloatByteProcedure;
import net.minecraft.util.gnu.trove.procedure.TFloatProcedure;
import net.minecraft.util.gnu.trove.set.TFloatSet;

public interface TFloatByteMap {
  float getNoEntryKey();
  
  byte getNoEntryValue();
  
  byte put(float paramFloat, byte paramByte);
  
  byte putIfAbsent(float paramFloat, byte paramByte);
  
  void putAll(Map<? extends Float, ? extends Byte> paramMap);
  
  void putAll(TFloatByteMap paramTFloatByteMap);
  
  byte get(float paramFloat);
  
  void clear();
  
  boolean isEmpty();
  
  byte remove(float paramFloat);
  
  int size();
  
  TFloatSet keySet();
  
  float[] keys();
  
  float[] keys(float[] paramArrayOffloat);
  
  TByteCollection valueCollection();
  
  byte[] values();
  
  byte[] values(byte[] paramArrayOfbyte);
  
  boolean containsValue(byte paramByte);
  
  boolean containsKey(float paramFloat);
  
  TFloatByteIterator iterator();
  
  boolean forEachKey(TFloatProcedure paramTFloatProcedure);
  
  boolean forEachValue(TByteProcedure paramTByteProcedure);
  
  boolean forEachEntry(TFloatByteProcedure paramTFloatByteProcedure);
  
  void transformValues(TByteFunction paramTByteFunction);
  
  boolean retainEntries(TFloatByteProcedure paramTFloatByteProcedure);
  
  boolean increment(float paramFloat);
  
  boolean adjustValue(float paramFloat, byte paramByte);
  
  byte adjustOrPutValue(float paramFloat, byte paramByte1, byte paramByte2);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\map\TFloatByteMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
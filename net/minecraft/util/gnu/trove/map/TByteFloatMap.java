package net.minecraft.util.gnu.trove.map;

import java.util.Map;
import net.minecraft.util.gnu.trove.TFloatCollection;
import net.minecraft.util.gnu.trove.function.TFloatFunction;
import net.minecraft.util.gnu.trove.iterator.TByteFloatIterator;
import net.minecraft.util.gnu.trove.procedure.TByteFloatProcedure;
import net.minecraft.util.gnu.trove.procedure.TByteProcedure;
import net.minecraft.util.gnu.trove.procedure.TFloatProcedure;
import net.minecraft.util.gnu.trove.set.TByteSet;

public interface TByteFloatMap {
  byte getNoEntryKey();
  
  float getNoEntryValue();
  
  float put(byte paramByte, float paramFloat);
  
  float putIfAbsent(byte paramByte, float paramFloat);
  
  void putAll(Map<? extends Byte, ? extends Float> paramMap);
  
  void putAll(TByteFloatMap paramTByteFloatMap);
  
  float get(byte paramByte);
  
  void clear();
  
  boolean isEmpty();
  
  float remove(byte paramByte);
  
  int size();
  
  TByteSet keySet();
  
  byte[] keys();
  
  byte[] keys(byte[] paramArrayOfbyte);
  
  TFloatCollection valueCollection();
  
  float[] values();
  
  float[] values(float[] paramArrayOffloat);
  
  boolean containsValue(float paramFloat);
  
  boolean containsKey(byte paramByte);
  
  TByteFloatIterator iterator();
  
  boolean forEachKey(TByteProcedure paramTByteProcedure);
  
  boolean forEachValue(TFloatProcedure paramTFloatProcedure);
  
  boolean forEachEntry(TByteFloatProcedure paramTByteFloatProcedure);
  
  void transformValues(TFloatFunction paramTFloatFunction);
  
  boolean retainEntries(TByteFloatProcedure paramTByteFloatProcedure);
  
  boolean increment(byte paramByte);
  
  boolean adjustValue(byte paramByte, float paramFloat);
  
  float adjustOrPutValue(byte paramByte, float paramFloat1, float paramFloat2);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\map\TByteFloatMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
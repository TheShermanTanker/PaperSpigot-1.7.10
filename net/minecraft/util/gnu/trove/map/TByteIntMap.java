package net.minecraft.util.gnu.trove.map;

import java.util.Map;
import net.minecraft.util.gnu.trove.TIntCollection;
import net.minecraft.util.gnu.trove.function.TIntFunction;
import net.minecraft.util.gnu.trove.iterator.TByteIntIterator;
import net.minecraft.util.gnu.trove.procedure.TByteIntProcedure;
import net.minecraft.util.gnu.trove.procedure.TByteProcedure;
import net.minecraft.util.gnu.trove.procedure.TIntProcedure;
import net.minecraft.util.gnu.trove.set.TByteSet;

public interface TByteIntMap {
  byte getNoEntryKey();
  
  int getNoEntryValue();
  
  int put(byte paramByte, int paramInt);
  
  int putIfAbsent(byte paramByte, int paramInt);
  
  void putAll(Map<? extends Byte, ? extends Integer> paramMap);
  
  void putAll(TByteIntMap paramTByteIntMap);
  
  int get(byte paramByte);
  
  void clear();
  
  boolean isEmpty();
  
  int remove(byte paramByte);
  
  int size();
  
  TByteSet keySet();
  
  byte[] keys();
  
  byte[] keys(byte[] paramArrayOfbyte);
  
  TIntCollection valueCollection();
  
  int[] values();
  
  int[] values(int[] paramArrayOfint);
  
  boolean containsValue(int paramInt);
  
  boolean containsKey(byte paramByte);
  
  TByteIntIterator iterator();
  
  boolean forEachKey(TByteProcedure paramTByteProcedure);
  
  boolean forEachValue(TIntProcedure paramTIntProcedure);
  
  boolean forEachEntry(TByteIntProcedure paramTByteIntProcedure);
  
  void transformValues(TIntFunction paramTIntFunction);
  
  boolean retainEntries(TByteIntProcedure paramTByteIntProcedure);
  
  boolean increment(byte paramByte);
  
  boolean adjustValue(byte paramByte, int paramInt);
  
  int adjustOrPutValue(byte paramByte, int paramInt1, int paramInt2);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\map\TByteIntMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
package net.minecraft.util.gnu.trove.map;

import java.util.Map;
import net.minecraft.util.gnu.trove.TByteCollection;
import net.minecraft.util.gnu.trove.function.TByteFunction;
import net.minecraft.util.gnu.trove.iterator.TIntByteIterator;
import net.minecraft.util.gnu.trove.procedure.TByteProcedure;
import net.minecraft.util.gnu.trove.procedure.TIntByteProcedure;
import net.minecraft.util.gnu.trove.procedure.TIntProcedure;
import net.minecraft.util.gnu.trove.set.TIntSet;

public interface TIntByteMap {
  int getNoEntryKey();
  
  byte getNoEntryValue();
  
  byte put(int paramInt, byte paramByte);
  
  byte putIfAbsent(int paramInt, byte paramByte);
  
  void putAll(Map<? extends Integer, ? extends Byte> paramMap);
  
  void putAll(TIntByteMap paramTIntByteMap);
  
  byte get(int paramInt);
  
  void clear();
  
  boolean isEmpty();
  
  byte remove(int paramInt);
  
  int size();
  
  TIntSet keySet();
  
  int[] keys();
  
  int[] keys(int[] paramArrayOfint);
  
  TByteCollection valueCollection();
  
  byte[] values();
  
  byte[] values(byte[] paramArrayOfbyte);
  
  boolean containsValue(byte paramByte);
  
  boolean containsKey(int paramInt);
  
  TIntByteIterator iterator();
  
  boolean forEachKey(TIntProcedure paramTIntProcedure);
  
  boolean forEachValue(TByteProcedure paramTByteProcedure);
  
  boolean forEachEntry(TIntByteProcedure paramTIntByteProcedure);
  
  void transformValues(TByteFunction paramTByteFunction);
  
  boolean retainEntries(TIntByteProcedure paramTIntByteProcedure);
  
  boolean increment(int paramInt);
  
  boolean adjustValue(int paramInt, byte paramByte);
  
  byte adjustOrPutValue(int paramInt, byte paramByte1, byte paramByte2);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\map\TIntByteMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
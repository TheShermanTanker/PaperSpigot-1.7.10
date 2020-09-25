package net.minecraft.util.gnu.trove.map;

import java.util.Map;
import net.minecraft.util.gnu.trove.TByteCollection;
import net.minecraft.util.gnu.trove.function.TByteFunction;
import net.minecraft.util.gnu.trove.iterator.TCharByteIterator;
import net.minecraft.util.gnu.trove.procedure.TByteProcedure;
import net.minecraft.util.gnu.trove.procedure.TCharByteProcedure;
import net.minecraft.util.gnu.trove.procedure.TCharProcedure;
import net.minecraft.util.gnu.trove.set.TCharSet;

public interface TCharByteMap {
  char getNoEntryKey();
  
  byte getNoEntryValue();
  
  byte put(char paramChar, byte paramByte);
  
  byte putIfAbsent(char paramChar, byte paramByte);
  
  void putAll(Map<? extends Character, ? extends Byte> paramMap);
  
  void putAll(TCharByteMap paramTCharByteMap);
  
  byte get(char paramChar);
  
  void clear();
  
  boolean isEmpty();
  
  byte remove(char paramChar);
  
  int size();
  
  TCharSet keySet();
  
  char[] keys();
  
  char[] keys(char[] paramArrayOfchar);
  
  TByteCollection valueCollection();
  
  byte[] values();
  
  byte[] values(byte[] paramArrayOfbyte);
  
  boolean containsValue(byte paramByte);
  
  boolean containsKey(char paramChar);
  
  TCharByteIterator iterator();
  
  boolean forEachKey(TCharProcedure paramTCharProcedure);
  
  boolean forEachValue(TByteProcedure paramTByteProcedure);
  
  boolean forEachEntry(TCharByteProcedure paramTCharByteProcedure);
  
  void transformValues(TByteFunction paramTByteFunction);
  
  boolean retainEntries(TCharByteProcedure paramTCharByteProcedure);
  
  boolean increment(char paramChar);
  
  boolean adjustValue(char paramChar, byte paramByte);
  
  byte adjustOrPutValue(char paramChar, byte paramByte1, byte paramByte2);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\map\TCharByteMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
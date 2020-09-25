package net.minecraft.util.gnu.trove.map;

import java.util.Map;
import net.minecraft.util.gnu.trove.TCharCollection;
import net.minecraft.util.gnu.trove.function.TCharFunction;
import net.minecraft.util.gnu.trove.iterator.TByteCharIterator;
import net.minecraft.util.gnu.trove.procedure.TByteCharProcedure;
import net.minecraft.util.gnu.trove.procedure.TByteProcedure;
import net.minecraft.util.gnu.trove.procedure.TCharProcedure;
import net.minecraft.util.gnu.trove.set.TByteSet;

public interface TByteCharMap {
  byte getNoEntryKey();
  
  char getNoEntryValue();
  
  char put(byte paramByte, char paramChar);
  
  char putIfAbsent(byte paramByte, char paramChar);
  
  void putAll(Map<? extends Byte, ? extends Character> paramMap);
  
  void putAll(TByteCharMap paramTByteCharMap);
  
  char get(byte paramByte);
  
  void clear();
  
  boolean isEmpty();
  
  char remove(byte paramByte);
  
  int size();
  
  TByteSet keySet();
  
  byte[] keys();
  
  byte[] keys(byte[] paramArrayOfbyte);
  
  TCharCollection valueCollection();
  
  char[] values();
  
  char[] values(char[] paramArrayOfchar);
  
  boolean containsValue(char paramChar);
  
  boolean containsKey(byte paramByte);
  
  TByteCharIterator iterator();
  
  boolean forEachKey(TByteProcedure paramTByteProcedure);
  
  boolean forEachValue(TCharProcedure paramTCharProcedure);
  
  boolean forEachEntry(TByteCharProcedure paramTByteCharProcedure);
  
  void transformValues(TCharFunction paramTCharFunction);
  
  boolean retainEntries(TByteCharProcedure paramTByteCharProcedure);
  
  boolean increment(byte paramByte);
  
  boolean adjustValue(byte paramByte, char paramChar);
  
  char adjustOrPutValue(byte paramByte, char paramChar1, char paramChar2);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\map\TByteCharMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
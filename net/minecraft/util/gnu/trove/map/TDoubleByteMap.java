package net.minecraft.util.gnu.trove.map;

import java.util.Map;
import net.minecraft.util.gnu.trove.TByteCollection;
import net.minecraft.util.gnu.trove.function.TByteFunction;
import net.minecraft.util.gnu.trove.iterator.TDoubleByteIterator;
import net.minecraft.util.gnu.trove.procedure.TByteProcedure;
import net.minecraft.util.gnu.trove.procedure.TDoubleByteProcedure;
import net.minecraft.util.gnu.trove.procedure.TDoubleProcedure;
import net.minecraft.util.gnu.trove.set.TDoubleSet;

public interface TDoubleByteMap {
  double getNoEntryKey();
  
  byte getNoEntryValue();
  
  byte put(double paramDouble, byte paramByte);
  
  byte putIfAbsent(double paramDouble, byte paramByte);
  
  void putAll(Map<? extends Double, ? extends Byte> paramMap);
  
  void putAll(TDoubleByteMap paramTDoubleByteMap);
  
  byte get(double paramDouble);
  
  void clear();
  
  boolean isEmpty();
  
  byte remove(double paramDouble);
  
  int size();
  
  TDoubleSet keySet();
  
  double[] keys();
  
  double[] keys(double[] paramArrayOfdouble);
  
  TByteCollection valueCollection();
  
  byte[] values();
  
  byte[] values(byte[] paramArrayOfbyte);
  
  boolean containsValue(byte paramByte);
  
  boolean containsKey(double paramDouble);
  
  TDoubleByteIterator iterator();
  
  boolean forEachKey(TDoubleProcedure paramTDoubleProcedure);
  
  boolean forEachValue(TByteProcedure paramTByteProcedure);
  
  boolean forEachEntry(TDoubleByteProcedure paramTDoubleByteProcedure);
  
  void transformValues(TByteFunction paramTByteFunction);
  
  boolean retainEntries(TDoubleByteProcedure paramTDoubleByteProcedure);
  
  boolean increment(double paramDouble);
  
  boolean adjustValue(double paramDouble, byte paramByte);
  
  byte adjustOrPutValue(double paramDouble, byte paramByte1, byte paramByte2);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\map\TDoubleByteMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
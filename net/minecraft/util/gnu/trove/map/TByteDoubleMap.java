package net.minecraft.util.gnu.trove.map;

import java.util.Map;
import net.minecraft.util.gnu.trove.TDoubleCollection;
import net.minecraft.util.gnu.trove.function.TDoubleFunction;
import net.minecraft.util.gnu.trove.iterator.TByteDoubleIterator;
import net.minecraft.util.gnu.trove.procedure.TByteDoubleProcedure;
import net.minecraft.util.gnu.trove.procedure.TByteProcedure;
import net.minecraft.util.gnu.trove.procedure.TDoubleProcedure;
import net.minecraft.util.gnu.trove.set.TByteSet;

public interface TByteDoubleMap {
  byte getNoEntryKey();
  
  double getNoEntryValue();
  
  double put(byte paramByte, double paramDouble);
  
  double putIfAbsent(byte paramByte, double paramDouble);
  
  void putAll(Map<? extends Byte, ? extends Double> paramMap);
  
  void putAll(TByteDoubleMap paramTByteDoubleMap);
  
  double get(byte paramByte);
  
  void clear();
  
  boolean isEmpty();
  
  double remove(byte paramByte);
  
  int size();
  
  TByteSet keySet();
  
  byte[] keys();
  
  byte[] keys(byte[] paramArrayOfbyte);
  
  TDoubleCollection valueCollection();
  
  double[] values();
  
  double[] values(double[] paramArrayOfdouble);
  
  boolean containsValue(double paramDouble);
  
  boolean containsKey(byte paramByte);
  
  TByteDoubleIterator iterator();
  
  boolean forEachKey(TByteProcedure paramTByteProcedure);
  
  boolean forEachValue(TDoubleProcedure paramTDoubleProcedure);
  
  boolean forEachEntry(TByteDoubleProcedure paramTByteDoubleProcedure);
  
  void transformValues(TDoubleFunction paramTDoubleFunction);
  
  boolean retainEntries(TByteDoubleProcedure paramTByteDoubleProcedure);
  
  boolean increment(byte paramByte);
  
  boolean adjustValue(byte paramByte, double paramDouble);
  
  double adjustOrPutValue(byte paramByte, double paramDouble1, double paramDouble2);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\map\TByteDoubleMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
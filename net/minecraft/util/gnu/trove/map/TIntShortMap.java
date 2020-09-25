package net.minecraft.util.gnu.trove.map;

import java.util.Map;
import net.minecraft.util.gnu.trove.TShortCollection;
import net.minecraft.util.gnu.trove.function.TShortFunction;
import net.minecraft.util.gnu.trove.iterator.TIntShortIterator;
import net.minecraft.util.gnu.trove.procedure.TIntProcedure;
import net.minecraft.util.gnu.trove.procedure.TIntShortProcedure;
import net.minecraft.util.gnu.trove.procedure.TShortProcedure;
import net.minecraft.util.gnu.trove.set.TIntSet;

public interface TIntShortMap {
  int getNoEntryKey();
  
  short getNoEntryValue();
  
  short put(int paramInt, short paramShort);
  
  short putIfAbsent(int paramInt, short paramShort);
  
  void putAll(Map<? extends Integer, ? extends Short> paramMap);
  
  void putAll(TIntShortMap paramTIntShortMap);
  
  short get(int paramInt);
  
  void clear();
  
  boolean isEmpty();
  
  short remove(int paramInt);
  
  int size();
  
  TIntSet keySet();
  
  int[] keys();
  
  int[] keys(int[] paramArrayOfint);
  
  TShortCollection valueCollection();
  
  short[] values();
  
  short[] values(short[] paramArrayOfshort);
  
  boolean containsValue(short paramShort);
  
  boolean containsKey(int paramInt);
  
  TIntShortIterator iterator();
  
  boolean forEachKey(TIntProcedure paramTIntProcedure);
  
  boolean forEachValue(TShortProcedure paramTShortProcedure);
  
  boolean forEachEntry(TIntShortProcedure paramTIntShortProcedure);
  
  void transformValues(TShortFunction paramTShortFunction);
  
  boolean retainEntries(TIntShortProcedure paramTIntShortProcedure);
  
  boolean increment(int paramInt);
  
  boolean adjustValue(int paramInt, short paramShort);
  
  short adjustOrPutValue(int paramInt, short paramShort1, short paramShort2);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\map\TIntShortMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
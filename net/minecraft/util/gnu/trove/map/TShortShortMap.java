package net.minecraft.util.gnu.trove.map;

import java.util.Map;
import net.minecraft.util.gnu.trove.TShortCollection;
import net.minecraft.util.gnu.trove.function.TShortFunction;
import net.minecraft.util.gnu.trove.iterator.TShortShortIterator;
import net.minecraft.util.gnu.trove.procedure.TShortProcedure;
import net.minecraft.util.gnu.trove.procedure.TShortShortProcedure;
import net.minecraft.util.gnu.trove.set.TShortSet;

public interface TShortShortMap {
  short getNoEntryKey();
  
  short getNoEntryValue();
  
  short put(short paramShort1, short paramShort2);
  
  short putIfAbsent(short paramShort1, short paramShort2);
  
  void putAll(Map<? extends Short, ? extends Short> paramMap);
  
  void putAll(TShortShortMap paramTShortShortMap);
  
  short get(short paramShort);
  
  void clear();
  
  boolean isEmpty();
  
  short remove(short paramShort);
  
  int size();
  
  TShortSet keySet();
  
  short[] keys();
  
  short[] keys(short[] paramArrayOfshort);
  
  TShortCollection valueCollection();
  
  short[] values();
  
  short[] values(short[] paramArrayOfshort);
  
  boolean containsValue(short paramShort);
  
  boolean containsKey(short paramShort);
  
  TShortShortIterator iterator();
  
  boolean forEachKey(TShortProcedure paramTShortProcedure);
  
  boolean forEachValue(TShortProcedure paramTShortProcedure);
  
  boolean forEachEntry(TShortShortProcedure paramTShortShortProcedure);
  
  void transformValues(TShortFunction paramTShortFunction);
  
  boolean retainEntries(TShortShortProcedure paramTShortShortProcedure);
  
  boolean increment(short paramShort);
  
  boolean adjustValue(short paramShort1, short paramShort2);
  
  short adjustOrPutValue(short paramShort1, short paramShort2, short paramShort3);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\map\TShortShortMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
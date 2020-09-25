package net.minecraft.util.gnu.trove.map;

import java.util.Map;
import net.minecraft.util.gnu.trove.TIntCollection;
import net.minecraft.util.gnu.trove.function.TIntFunction;
import net.minecraft.util.gnu.trove.iterator.TCharIntIterator;
import net.minecraft.util.gnu.trove.procedure.TCharIntProcedure;
import net.minecraft.util.gnu.trove.procedure.TCharProcedure;
import net.minecraft.util.gnu.trove.procedure.TIntProcedure;
import net.minecraft.util.gnu.trove.set.TCharSet;

public interface TCharIntMap {
  char getNoEntryKey();
  
  int getNoEntryValue();
  
  int put(char paramChar, int paramInt);
  
  int putIfAbsent(char paramChar, int paramInt);
  
  void putAll(Map<? extends Character, ? extends Integer> paramMap);
  
  void putAll(TCharIntMap paramTCharIntMap);
  
  int get(char paramChar);
  
  void clear();
  
  boolean isEmpty();
  
  int remove(char paramChar);
  
  int size();
  
  TCharSet keySet();
  
  char[] keys();
  
  char[] keys(char[] paramArrayOfchar);
  
  TIntCollection valueCollection();
  
  int[] values();
  
  int[] values(int[] paramArrayOfint);
  
  boolean containsValue(int paramInt);
  
  boolean containsKey(char paramChar);
  
  TCharIntIterator iterator();
  
  boolean forEachKey(TCharProcedure paramTCharProcedure);
  
  boolean forEachValue(TIntProcedure paramTIntProcedure);
  
  boolean forEachEntry(TCharIntProcedure paramTCharIntProcedure);
  
  void transformValues(TIntFunction paramTIntFunction);
  
  boolean retainEntries(TCharIntProcedure paramTCharIntProcedure);
  
  boolean increment(char paramChar);
  
  boolean adjustValue(char paramChar, int paramInt);
  
  int adjustOrPutValue(char paramChar, int paramInt1, int paramInt2);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\map\TCharIntMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
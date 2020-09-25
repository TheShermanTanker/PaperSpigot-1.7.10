package net.minecraft.util.gnu.trove.map;

import java.util.Map;
import net.minecraft.util.gnu.trove.TIntCollection;
import net.minecraft.util.gnu.trove.function.TIntFunction;
import net.minecraft.util.gnu.trove.iterator.TIntIntIterator;
import net.minecraft.util.gnu.trove.procedure.TIntIntProcedure;
import net.minecraft.util.gnu.trove.procedure.TIntProcedure;
import net.minecraft.util.gnu.trove.set.TIntSet;

public interface TIntIntMap {
  int getNoEntryKey();
  
  int getNoEntryValue();
  
  int put(int paramInt1, int paramInt2);
  
  int putIfAbsent(int paramInt1, int paramInt2);
  
  void putAll(Map<? extends Integer, ? extends Integer> paramMap);
  
  void putAll(TIntIntMap paramTIntIntMap);
  
  int get(int paramInt);
  
  void clear();
  
  boolean isEmpty();
  
  int remove(int paramInt);
  
  int size();
  
  TIntSet keySet();
  
  int[] keys();
  
  int[] keys(int[] paramArrayOfint);
  
  TIntCollection valueCollection();
  
  int[] values();
  
  int[] values(int[] paramArrayOfint);
  
  boolean containsValue(int paramInt);
  
  boolean containsKey(int paramInt);
  
  TIntIntIterator iterator();
  
  boolean forEachKey(TIntProcedure paramTIntProcedure);
  
  boolean forEachValue(TIntProcedure paramTIntProcedure);
  
  boolean forEachEntry(TIntIntProcedure paramTIntIntProcedure);
  
  void transformValues(TIntFunction paramTIntFunction);
  
  boolean retainEntries(TIntIntProcedure paramTIntIntProcedure);
  
  boolean increment(int paramInt);
  
  boolean adjustValue(int paramInt1, int paramInt2);
  
  int adjustOrPutValue(int paramInt1, int paramInt2, int paramInt3);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\map\TIntIntMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
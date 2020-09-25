package net.minecraft.util.gnu.trove.map;

import java.util.Map;
import net.minecraft.util.gnu.trove.TCharCollection;
import net.minecraft.util.gnu.trove.function.TCharFunction;
import net.minecraft.util.gnu.trove.iterator.TIntCharIterator;
import net.minecraft.util.gnu.trove.procedure.TCharProcedure;
import net.minecraft.util.gnu.trove.procedure.TIntCharProcedure;
import net.minecraft.util.gnu.trove.procedure.TIntProcedure;
import net.minecraft.util.gnu.trove.set.TIntSet;

public interface TIntCharMap {
  int getNoEntryKey();
  
  char getNoEntryValue();
  
  char put(int paramInt, char paramChar);
  
  char putIfAbsent(int paramInt, char paramChar);
  
  void putAll(Map<? extends Integer, ? extends Character> paramMap);
  
  void putAll(TIntCharMap paramTIntCharMap);
  
  char get(int paramInt);
  
  void clear();
  
  boolean isEmpty();
  
  char remove(int paramInt);
  
  int size();
  
  TIntSet keySet();
  
  int[] keys();
  
  int[] keys(int[] paramArrayOfint);
  
  TCharCollection valueCollection();
  
  char[] values();
  
  char[] values(char[] paramArrayOfchar);
  
  boolean containsValue(char paramChar);
  
  boolean containsKey(int paramInt);
  
  TIntCharIterator iterator();
  
  boolean forEachKey(TIntProcedure paramTIntProcedure);
  
  boolean forEachValue(TCharProcedure paramTCharProcedure);
  
  boolean forEachEntry(TIntCharProcedure paramTIntCharProcedure);
  
  void transformValues(TCharFunction paramTCharFunction);
  
  boolean retainEntries(TIntCharProcedure paramTIntCharProcedure);
  
  boolean increment(int paramInt);
  
  boolean adjustValue(int paramInt, char paramChar);
  
  char adjustOrPutValue(int paramInt, char paramChar1, char paramChar2);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\map\TIntCharMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
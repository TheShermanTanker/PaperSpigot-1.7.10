package net.minecraft.util.gnu.trove.list;

import java.util.Random;
import net.minecraft.util.gnu.trove.TIntCollection;
import net.minecraft.util.gnu.trove.function.TIntFunction;
import net.minecraft.util.gnu.trove.procedure.TIntProcedure;

public interface TIntList extends TIntCollection {
  int getNoEntryValue();
  
  int size();
  
  boolean isEmpty();
  
  boolean add(int paramInt);
  
  void add(int[] paramArrayOfint);
  
  void add(int[] paramArrayOfint, int paramInt1, int paramInt2);
  
  void insert(int paramInt1, int paramInt2);
  
  void insert(int paramInt, int[] paramArrayOfint);
  
  void insert(int paramInt1, int[] paramArrayOfint, int paramInt2, int paramInt3);
  
  int get(int paramInt);
  
  int set(int paramInt1, int paramInt2);
  
  void set(int paramInt, int[] paramArrayOfint);
  
  void set(int paramInt1, int[] paramArrayOfint, int paramInt2, int paramInt3);
  
  int replace(int paramInt1, int paramInt2);
  
  void clear();
  
  boolean remove(int paramInt);
  
  int removeAt(int paramInt);
  
  void remove(int paramInt1, int paramInt2);
  
  void transformValues(TIntFunction paramTIntFunction);
  
  void reverse();
  
  void reverse(int paramInt1, int paramInt2);
  
  void shuffle(Random paramRandom);
  
  TIntList subList(int paramInt1, int paramInt2);
  
  int[] toArray();
  
  int[] toArray(int paramInt1, int paramInt2);
  
  int[] toArray(int[] paramArrayOfint);
  
  int[] toArray(int[] paramArrayOfint, int paramInt1, int paramInt2);
  
  int[] toArray(int[] paramArrayOfint, int paramInt1, int paramInt2, int paramInt3);
  
  boolean forEach(TIntProcedure paramTIntProcedure);
  
  boolean forEachDescending(TIntProcedure paramTIntProcedure);
  
  void sort();
  
  void sort(int paramInt1, int paramInt2);
  
  void fill(int paramInt);
  
  void fill(int paramInt1, int paramInt2, int paramInt3);
  
  int binarySearch(int paramInt);
  
  int binarySearch(int paramInt1, int paramInt2, int paramInt3);
  
  int indexOf(int paramInt);
  
  int indexOf(int paramInt1, int paramInt2);
  
  int lastIndexOf(int paramInt);
  
  int lastIndexOf(int paramInt1, int paramInt2);
  
  boolean contains(int paramInt);
  
  TIntList grep(TIntProcedure paramTIntProcedure);
  
  TIntList inverseGrep(TIntProcedure paramTIntProcedure);
  
  int max();
  
  int min();
  
  int sum();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\list\TIntList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
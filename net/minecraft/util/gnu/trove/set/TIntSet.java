package net.minecraft.util.gnu.trove.set;

import java.util.Collection;
import net.minecraft.util.gnu.trove.TIntCollection;
import net.minecraft.util.gnu.trove.iterator.TIntIterator;
import net.minecraft.util.gnu.trove.procedure.TIntProcedure;

public interface TIntSet extends TIntCollection {
  int getNoEntryValue();
  
  int size();
  
  boolean isEmpty();
  
  boolean contains(int paramInt);
  
  TIntIterator iterator();
  
  int[] toArray();
  
  int[] toArray(int[] paramArrayOfint);
  
  boolean add(int paramInt);
  
  boolean remove(int paramInt);
  
  boolean containsAll(Collection<?> paramCollection);
  
  boolean containsAll(TIntCollection paramTIntCollection);
  
  boolean containsAll(int[] paramArrayOfint);
  
  boolean addAll(Collection<? extends Integer> paramCollection);
  
  boolean addAll(TIntCollection paramTIntCollection);
  
  boolean addAll(int[] paramArrayOfint);
  
  boolean retainAll(Collection<?> paramCollection);
  
  boolean retainAll(TIntCollection paramTIntCollection);
  
  boolean retainAll(int[] paramArrayOfint);
  
  boolean removeAll(Collection<?> paramCollection);
  
  boolean removeAll(TIntCollection paramTIntCollection);
  
  boolean removeAll(int[] paramArrayOfint);
  
  void clear();
  
  boolean forEach(TIntProcedure paramTIntProcedure);
  
  boolean equals(Object paramObject);
  
  int hashCode();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\set\TIntSet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
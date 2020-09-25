package net.minecraft.util.gnu.trove;

import java.util.Collection;
import net.minecraft.util.gnu.trove.iterator.TLongIterator;
import net.minecraft.util.gnu.trove.procedure.TLongProcedure;

public interface TLongCollection {
  public static final long serialVersionUID = 1L;
  
  long getNoEntryValue();
  
  int size();
  
  boolean isEmpty();
  
  boolean contains(long paramLong);
  
  TLongIterator iterator();
  
  long[] toArray();
  
  long[] toArray(long[] paramArrayOflong);
  
  boolean add(long paramLong);
  
  boolean remove(long paramLong);
  
  boolean containsAll(Collection<?> paramCollection);
  
  boolean containsAll(TLongCollection paramTLongCollection);
  
  boolean containsAll(long[] paramArrayOflong);
  
  boolean addAll(Collection<? extends Long> paramCollection);
  
  boolean addAll(TLongCollection paramTLongCollection);
  
  boolean addAll(long[] paramArrayOflong);
  
  boolean retainAll(Collection<?> paramCollection);
  
  boolean retainAll(TLongCollection paramTLongCollection);
  
  boolean retainAll(long[] paramArrayOflong);
  
  boolean removeAll(Collection<?> paramCollection);
  
  boolean removeAll(TLongCollection paramTLongCollection);
  
  boolean removeAll(long[] paramArrayOflong);
  
  void clear();
  
  boolean forEach(TLongProcedure paramTLongProcedure);
  
  boolean equals(Object paramObject);
  
  int hashCode();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\TLongCollection.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
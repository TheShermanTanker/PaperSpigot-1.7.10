package net.minecraft.util.gnu.trove;

import java.util.Collection;
import net.minecraft.util.gnu.trove.iterator.TCharIterator;
import net.minecraft.util.gnu.trove.procedure.TCharProcedure;

public interface TCharCollection {
  public static final long serialVersionUID = 1L;
  
  char getNoEntryValue();
  
  int size();
  
  boolean isEmpty();
  
  boolean contains(char paramChar);
  
  TCharIterator iterator();
  
  char[] toArray();
  
  char[] toArray(char[] paramArrayOfchar);
  
  boolean add(char paramChar);
  
  boolean remove(char paramChar);
  
  boolean containsAll(Collection<?> paramCollection);
  
  boolean containsAll(TCharCollection paramTCharCollection);
  
  boolean containsAll(char[] paramArrayOfchar);
  
  boolean addAll(Collection<? extends Character> paramCollection);
  
  boolean addAll(TCharCollection paramTCharCollection);
  
  boolean addAll(char[] paramArrayOfchar);
  
  boolean retainAll(Collection<?> paramCollection);
  
  boolean retainAll(TCharCollection paramTCharCollection);
  
  boolean retainAll(char[] paramArrayOfchar);
  
  boolean removeAll(Collection<?> paramCollection);
  
  boolean removeAll(TCharCollection paramTCharCollection);
  
  boolean removeAll(char[] paramArrayOfchar);
  
  void clear();
  
  boolean forEach(TCharProcedure paramTCharProcedure);
  
  boolean equals(Object paramObject);
  
  int hashCode();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\TCharCollection.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
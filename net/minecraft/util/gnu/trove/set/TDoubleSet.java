package net.minecraft.util.gnu.trove.set;

import java.util.Collection;
import net.minecraft.util.gnu.trove.TDoubleCollection;
import net.minecraft.util.gnu.trove.iterator.TDoubleIterator;
import net.minecraft.util.gnu.trove.procedure.TDoubleProcedure;

public interface TDoubleSet extends TDoubleCollection {
  double getNoEntryValue();
  
  int size();
  
  boolean isEmpty();
  
  boolean contains(double paramDouble);
  
  TDoubleIterator iterator();
  
  double[] toArray();
  
  double[] toArray(double[] paramArrayOfdouble);
  
  boolean add(double paramDouble);
  
  boolean remove(double paramDouble);
  
  boolean containsAll(Collection<?> paramCollection);
  
  boolean containsAll(TDoubleCollection paramTDoubleCollection);
  
  boolean containsAll(double[] paramArrayOfdouble);
  
  boolean addAll(Collection<? extends Double> paramCollection);
  
  boolean addAll(TDoubleCollection paramTDoubleCollection);
  
  boolean addAll(double[] paramArrayOfdouble);
  
  boolean retainAll(Collection<?> paramCollection);
  
  boolean retainAll(TDoubleCollection paramTDoubleCollection);
  
  boolean retainAll(double[] paramArrayOfdouble);
  
  boolean removeAll(Collection<?> paramCollection);
  
  boolean removeAll(TDoubleCollection paramTDoubleCollection);
  
  boolean removeAll(double[] paramArrayOfdouble);
  
  void clear();
  
  boolean forEach(TDoubleProcedure paramTDoubleProcedure);
  
  boolean equals(Object paramObject);
  
  int hashCode();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\set\TDoubleSet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
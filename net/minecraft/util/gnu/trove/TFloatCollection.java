package net.minecraft.util.gnu.trove;

import java.util.Collection;
import net.minecraft.util.gnu.trove.iterator.TFloatIterator;
import net.minecraft.util.gnu.trove.procedure.TFloatProcedure;

public interface TFloatCollection {
  public static final long serialVersionUID = 1L;
  
  float getNoEntryValue();
  
  int size();
  
  boolean isEmpty();
  
  boolean contains(float paramFloat);
  
  TFloatIterator iterator();
  
  float[] toArray();
  
  float[] toArray(float[] paramArrayOffloat);
  
  boolean add(float paramFloat);
  
  boolean remove(float paramFloat);
  
  boolean containsAll(Collection<?> paramCollection);
  
  boolean containsAll(TFloatCollection paramTFloatCollection);
  
  boolean containsAll(float[] paramArrayOffloat);
  
  boolean addAll(Collection<? extends Float> paramCollection);
  
  boolean addAll(TFloatCollection paramTFloatCollection);
  
  boolean addAll(float[] paramArrayOffloat);
  
  boolean retainAll(Collection<?> paramCollection);
  
  boolean retainAll(TFloatCollection paramTFloatCollection);
  
  boolean retainAll(float[] paramArrayOffloat);
  
  boolean removeAll(Collection<?> paramCollection);
  
  boolean removeAll(TFloatCollection paramTFloatCollection);
  
  boolean removeAll(float[] paramArrayOffloat);
  
  void clear();
  
  boolean forEach(TFloatProcedure paramTFloatProcedure);
  
  boolean equals(Object paramObject);
  
  int hashCode();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\TFloatCollection.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
package net.minecraft.util.gnu.trove;

import java.util.Collection;
import net.minecraft.util.gnu.trove.iterator.TByteIterator;
import net.minecraft.util.gnu.trove.procedure.TByteProcedure;

public interface TByteCollection {
  public static final long serialVersionUID = 1L;
  
  byte getNoEntryValue();
  
  int size();
  
  boolean isEmpty();
  
  boolean contains(byte paramByte);
  
  TByteIterator iterator();
  
  byte[] toArray();
  
  byte[] toArray(byte[] paramArrayOfbyte);
  
  boolean add(byte paramByte);
  
  boolean remove(byte paramByte);
  
  boolean containsAll(Collection<?> paramCollection);
  
  boolean containsAll(TByteCollection paramTByteCollection);
  
  boolean containsAll(byte[] paramArrayOfbyte);
  
  boolean addAll(Collection<? extends Byte> paramCollection);
  
  boolean addAll(TByteCollection paramTByteCollection);
  
  boolean addAll(byte[] paramArrayOfbyte);
  
  boolean retainAll(Collection<?> paramCollection);
  
  boolean retainAll(TByteCollection paramTByteCollection);
  
  boolean retainAll(byte[] paramArrayOfbyte);
  
  boolean removeAll(Collection<?> paramCollection);
  
  boolean removeAll(TByteCollection paramTByteCollection);
  
  boolean removeAll(byte[] paramArrayOfbyte);
  
  void clear();
  
  boolean forEach(TByteProcedure paramTByteProcedure);
  
  boolean equals(Object paramObject);
  
  int hashCode();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\TByteCollection.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
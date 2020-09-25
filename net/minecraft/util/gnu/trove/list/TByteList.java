package net.minecraft.util.gnu.trove.list;

import java.util.Random;
import net.minecraft.util.gnu.trove.TByteCollection;
import net.minecraft.util.gnu.trove.function.TByteFunction;
import net.minecraft.util.gnu.trove.procedure.TByteProcedure;

public interface TByteList extends TByteCollection {
  byte getNoEntryValue();
  
  int size();
  
  boolean isEmpty();
  
  boolean add(byte paramByte);
  
  void add(byte[] paramArrayOfbyte);
  
  void add(byte[] paramArrayOfbyte, int paramInt1, int paramInt2);
  
  void insert(int paramInt, byte paramByte);
  
  void insert(int paramInt, byte[] paramArrayOfbyte);
  
  void insert(int paramInt1, byte[] paramArrayOfbyte, int paramInt2, int paramInt3);
  
  byte get(int paramInt);
  
  byte set(int paramInt, byte paramByte);
  
  void set(int paramInt, byte[] paramArrayOfbyte);
  
  void set(int paramInt1, byte[] paramArrayOfbyte, int paramInt2, int paramInt3);
  
  byte replace(int paramInt, byte paramByte);
  
  void clear();
  
  boolean remove(byte paramByte);
  
  byte removeAt(int paramInt);
  
  void remove(int paramInt1, int paramInt2);
  
  void transformValues(TByteFunction paramTByteFunction);
  
  void reverse();
  
  void reverse(int paramInt1, int paramInt2);
  
  void shuffle(Random paramRandom);
  
  TByteList subList(int paramInt1, int paramInt2);
  
  byte[] toArray();
  
  byte[] toArray(int paramInt1, int paramInt2);
  
  byte[] toArray(byte[] paramArrayOfbyte);
  
  byte[] toArray(byte[] paramArrayOfbyte, int paramInt1, int paramInt2);
  
  byte[] toArray(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3);
  
  boolean forEach(TByteProcedure paramTByteProcedure);
  
  boolean forEachDescending(TByteProcedure paramTByteProcedure);
  
  void sort();
  
  void sort(int paramInt1, int paramInt2);
  
  void fill(byte paramByte);
  
  void fill(int paramInt1, int paramInt2, byte paramByte);
  
  int binarySearch(byte paramByte);
  
  int binarySearch(byte paramByte, int paramInt1, int paramInt2);
  
  int indexOf(byte paramByte);
  
  int indexOf(int paramInt, byte paramByte);
  
  int lastIndexOf(byte paramByte);
  
  int lastIndexOf(int paramInt, byte paramByte);
  
  boolean contains(byte paramByte);
  
  TByteList grep(TByteProcedure paramTByteProcedure);
  
  TByteList inverseGrep(TByteProcedure paramTByteProcedure);
  
  byte max();
  
  byte min();
  
  byte sum();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\list\TByteList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
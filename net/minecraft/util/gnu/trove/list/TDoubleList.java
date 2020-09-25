package net.minecraft.util.gnu.trove.list;

import java.util.Random;
import net.minecraft.util.gnu.trove.TDoubleCollection;
import net.minecraft.util.gnu.trove.function.TDoubleFunction;
import net.minecraft.util.gnu.trove.procedure.TDoubleProcedure;

public interface TDoubleList extends TDoubleCollection {
  double getNoEntryValue();
  
  int size();
  
  boolean isEmpty();
  
  boolean add(double paramDouble);
  
  void add(double[] paramArrayOfdouble);
  
  void add(double[] paramArrayOfdouble, int paramInt1, int paramInt2);
  
  void insert(int paramInt, double paramDouble);
  
  void insert(int paramInt, double[] paramArrayOfdouble);
  
  void insert(int paramInt1, double[] paramArrayOfdouble, int paramInt2, int paramInt3);
  
  double get(int paramInt);
  
  double set(int paramInt, double paramDouble);
  
  void set(int paramInt, double[] paramArrayOfdouble);
  
  void set(int paramInt1, double[] paramArrayOfdouble, int paramInt2, int paramInt3);
  
  double replace(int paramInt, double paramDouble);
  
  void clear();
  
  boolean remove(double paramDouble);
  
  double removeAt(int paramInt);
  
  void remove(int paramInt1, int paramInt2);
  
  void transformValues(TDoubleFunction paramTDoubleFunction);
  
  void reverse();
  
  void reverse(int paramInt1, int paramInt2);
  
  void shuffle(Random paramRandom);
  
  TDoubleList subList(int paramInt1, int paramInt2);
  
  double[] toArray();
  
  double[] toArray(int paramInt1, int paramInt2);
  
  double[] toArray(double[] paramArrayOfdouble);
  
  double[] toArray(double[] paramArrayOfdouble, int paramInt1, int paramInt2);
  
  double[] toArray(double[] paramArrayOfdouble, int paramInt1, int paramInt2, int paramInt3);
  
  boolean forEach(TDoubleProcedure paramTDoubleProcedure);
  
  boolean forEachDescending(TDoubleProcedure paramTDoubleProcedure);
  
  void sort();
  
  void sort(int paramInt1, int paramInt2);
  
  void fill(double paramDouble);
  
  void fill(int paramInt1, int paramInt2, double paramDouble);
  
  int binarySearch(double paramDouble);
  
  int binarySearch(double paramDouble, int paramInt1, int paramInt2);
  
  int indexOf(double paramDouble);
  
  int indexOf(int paramInt, double paramDouble);
  
  int lastIndexOf(double paramDouble);
  
  int lastIndexOf(int paramInt, double paramDouble);
  
  boolean contains(double paramDouble);
  
  TDoubleList grep(TDoubleProcedure paramTDoubleProcedure);
  
  TDoubleList inverseGrep(TDoubleProcedure paramTDoubleProcedure);
  
  double max();
  
  double min();
  
  double sum();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\list\TDoubleList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
package net.minecraft.util.gnu.trove.iterator;

public interface TDoubleObjectIterator<V> extends TAdvancingIterator {
  double key();
  
  V value();
  
  V setValue(V paramV);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\iterator\TDoubleObjectIterator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
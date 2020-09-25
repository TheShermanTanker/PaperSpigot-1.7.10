package net.minecraft.util.gnu.trove.iterator;

public interface TFloatObjectIterator<V> extends TAdvancingIterator {
  float key();
  
  V value();
  
  V setValue(V paramV);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\iterator\TFloatObjectIterator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
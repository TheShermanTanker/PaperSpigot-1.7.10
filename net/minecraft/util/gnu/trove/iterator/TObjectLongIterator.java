package net.minecraft.util.gnu.trove.iterator;

public interface TObjectLongIterator<K> extends TAdvancingIterator {
  K key();
  
  long value();
  
  long setValue(long paramLong);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\iterator\TObjectLongIterator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
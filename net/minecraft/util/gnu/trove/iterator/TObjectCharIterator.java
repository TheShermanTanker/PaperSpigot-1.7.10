package net.minecraft.util.gnu.trove.iterator;

public interface TObjectCharIterator<K> extends TAdvancingIterator {
  K key();
  
  char value();
  
  char setValue(char paramChar);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\iterator\TObjectCharIterator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
package net.minecraft.util.gnu.trove.queue;

import net.minecraft.util.gnu.trove.TCharCollection;

public interface TCharQueue extends TCharCollection {
  char element();
  
  boolean offer(char paramChar);
  
  char peek();
  
  char poll();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\queue\TCharQueue.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
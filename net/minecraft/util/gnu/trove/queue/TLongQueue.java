package net.minecraft.util.gnu.trove.queue;

import net.minecraft.util.gnu.trove.TLongCollection;

public interface TLongQueue extends TLongCollection {
  long element();
  
  boolean offer(long paramLong);
  
  long peek();
  
  long poll();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\queue\TLongQueue.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
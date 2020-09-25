package net.minecraft.util.gnu.trove.queue;

import net.minecraft.util.gnu.trove.TIntCollection;

public interface TIntQueue extends TIntCollection {
  int element();
  
  boolean offer(int paramInt);
  
  int peek();
  
  int poll();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\queue\TIntQueue.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
package net.minecraft.util.gnu.trove.queue;

import net.minecraft.util.gnu.trove.TShortCollection;

public interface TShortQueue extends TShortCollection {
  short element();
  
  boolean offer(short paramShort);
  
  short peek();
  
  short poll();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\queue\TShortQueue.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
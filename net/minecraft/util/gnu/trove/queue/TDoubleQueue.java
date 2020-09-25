package net.minecraft.util.gnu.trove.queue;

import net.minecraft.util.gnu.trove.TDoubleCollection;

public interface TDoubleQueue extends TDoubleCollection {
  double element();
  
  boolean offer(double paramDouble);
  
  double peek();
  
  double poll();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\queue\TDoubleQueue.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
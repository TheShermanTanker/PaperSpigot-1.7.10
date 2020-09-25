package net.minecraft.util.gnu.trove.queue;

import net.minecraft.util.gnu.trove.TByteCollection;

public interface TByteQueue extends TByteCollection {
  byte element();
  
  boolean offer(byte paramByte);
  
  byte peek();
  
  byte poll();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\queue\TByteQueue.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
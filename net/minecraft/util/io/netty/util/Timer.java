package net.minecraft.util.io.netty.util;

import java.util.Set;
import java.util.concurrent.TimeUnit;

public interface Timer {
  Timeout newTimeout(TimerTask paramTimerTask, long paramLong, TimeUnit paramTimeUnit);
  
  Set<Timeout> stop();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\nett\\util\Timer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
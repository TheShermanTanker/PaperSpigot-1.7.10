package com.avaje.ebean;

import java.util.concurrent.TimeUnit;

public interface BackgroundExecutor {
  void execute(Runnable paramRunnable);
  
  void executePeriodically(Runnable paramRunnable, long paramLong, TimeUnit paramTimeUnit);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\BackgroundExecutor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
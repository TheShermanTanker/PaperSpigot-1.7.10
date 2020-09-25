package net.minecraft.util.io.netty.util.concurrent;

public interface EventExecutor extends EventExecutorGroup {
  EventExecutor next();
  
  EventExecutorGroup parent();
  
  boolean inEventLoop();
  
  boolean inEventLoop(Thread paramThread);
  
  <V> Promise<V> newPromise();
  
  <V> ProgressivePromise<V> newProgressivePromise();
  
  <V> Future<V> newSucceededFuture(V paramV);
  
  <V> Future<V> newFailedFuture(Throwable paramThrowable);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\nett\\util\concurrent\EventExecutor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
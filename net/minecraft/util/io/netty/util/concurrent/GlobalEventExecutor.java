/*     */ package net.minecraft.util.io.netty.util.concurrent;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.PriorityQueue;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.BlockingQueue;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.LinkedBlockingQueue;
/*     */ import java.util.concurrent.ScheduledFuture;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import net.minecraft.util.io.netty.util.internal.logging.InternalLogger;
/*     */ import net.minecraft.util.io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class GlobalEventExecutor
/*     */   extends AbstractEventExecutor
/*     */ {
/*  39 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(GlobalEventExecutor.class);
/*     */   
/*     */   private static final int ST_NOT_STARTED = 1;
/*     */   
/*     */   private static final int ST_STARTED = 2;
/*  44 */   private static final long SCHEDULE_PURGE_INTERVAL = TimeUnit.SECONDS.toNanos(1L);
/*     */   
/*  46 */   public static final GlobalEventExecutor INSTANCE = new GlobalEventExecutor();
/*     */   
/*  48 */   final Queue<Runnable> taskQueue = new LinkedBlockingQueue<Runnable>();
/*  49 */   final Queue<ScheduledFutureTask<?>> delayedTaskQueue = new PriorityQueue<ScheduledFutureTask<?>>();
/*  50 */   final ScheduledFutureTask<Void> purgeTask = new ScheduledFutureTask<Void>(this, this.delayedTaskQueue, Executors.callable(new PurgeTask(), null), ScheduledFutureTask.deadlineNanos(SCHEDULE_PURGE_INTERVAL), -SCHEDULE_PURGE_INTERVAL);
/*     */ 
/*     */ 
/*     */   
/*  54 */   private final ThreadFactory threadFactory = new DefaultThreadFactory(getClass());
/*  55 */   private final TaskRunner taskRunner = new TaskRunner();
/*  56 */   private final Object stateLock = new Object();
/*     */   
/*     */   volatile Thread thread;
/*  59 */   private volatile int state = 1;
/*     */   
/*  61 */   private final Future<?> terminationFuture = new FailedFuture(this, new UnsupportedOperationException());
/*     */   
/*     */   private GlobalEventExecutor() {
/*  64 */     this.delayedTaskQueue.add(this.purgeTask);
/*     */   }
/*     */ 
/*     */   
/*     */   public EventExecutorGroup parent() {
/*  69 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Runnable takeTask() {
/*  78 */     BlockingQueue<Runnable> taskQueue = (BlockingQueue<Runnable>)this.taskQueue; while (true) {
/*     */       Runnable runnable;
/*  80 */       ScheduledFutureTask<?> delayedTask = this.delayedTaskQueue.peek();
/*  81 */       if (delayedTask == null) {
/*  82 */         Runnable task = null;
/*     */         try {
/*  84 */           task = taskQueue.take();
/*  85 */         } catch (InterruptedException e) {}
/*     */ 
/*     */         
/*  88 */         return task;
/*     */       } 
/*  90 */       long delayNanos = delayedTask.delayNanos();
/*     */       
/*  92 */       if (delayNanos > 0L) {
/*     */         try {
/*  94 */           runnable = taskQueue.poll(delayNanos, TimeUnit.NANOSECONDS);
/*  95 */         } catch (InterruptedException e) {
/*  96 */           return null;
/*     */         } 
/*     */       } else {
/*  99 */         runnable = taskQueue.poll();
/*     */       } 
/*     */       
/* 102 */       if (runnable == null) {
/* 103 */         fetchFromDelayedQueue();
/* 104 */         runnable = taskQueue.poll();
/*     */       } 
/*     */       
/* 107 */       if (runnable != null) {
/* 108 */         return runnable;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void fetchFromDelayedQueue() {
/* 115 */     long nanoTime = 0L;
/*     */     while (true) {
/* 117 */       ScheduledFutureTask<?> delayedTask = this.delayedTaskQueue.peek();
/* 118 */       if (delayedTask == null) {
/*     */         break;
/*     */       }
/*     */       
/* 122 */       if (nanoTime == 0L) {
/* 123 */         nanoTime = ScheduledFutureTask.nanoTime();
/*     */       }
/*     */       
/* 126 */       if (delayedTask.deadlineNanos() <= nanoTime) {
/* 127 */         this.delayedTaskQueue.remove();
/* 128 */         this.taskQueue.add(delayedTask);
/*     */         continue;
/*     */       } 
/*     */       break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int pendingTasks() {
/* 142 */     return this.taskQueue.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addTask(Runnable task) {
/* 150 */     if (task == null) {
/* 151 */       throw new NullPointerException("task");
/*     */     }
/* 153 */     this.taskQueue.add(task);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean inEventLoop(Thread thread) {
/* 158 */     return (thread == this.thread);
/*     */   }
/*     */ 
/*     */   
/*     */   public Future<?> shutdownGracefully(long quietPeriod, long timeout, TimeUnit unit) {
/* 163 */     return terminationFuture();
/*     */   }
/*     */ 
/*     */   
/*     */   public Future<?> terminationFuture() {
/* 168 */     return this.terminationFuture;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void shutdown() {
/* 174 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isShuttingDown() {
/* 179 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isShutdown() {
/* 184 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isTerminated() {
/* 189 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean awaitTermination(long timeout, TimeUnit unit) {
/* 194 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void execute(Runnable task) {
/* 199 */     if (task == null) {
/* 200 */       throw new NullPointerException("task");
/*     */     }
/*     */     
/* 203 */     addTask(task);
/* 204 */     if (!inEventLoop()) {
/* 205 */       startThread();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
/* 213 */     if (command == null) {
/* 214 */       throw new NullPointerException("command");
/*     */     }
/* 216 */     if (unit == null) {
/* 217 */       throw new NullPointerException("unit");
/*     */     }
/* 219 */     if (delay < 0L) {
/* 220 */       throw new IllegalArgumentException(String.format("delay: %d (expected: >= 0)", new Object[] { Long.valueOf(delay) }));
/*     */     }
/*     */     
/* 223 */     return schedule(new ScheduledFutureTask(this, this.delayedTaskQueue, command, null, ScheduledFutureTask.deadlineNanos(unit.toNanos(delay))));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
/* 229 */     if (callable == null) {
/* 230 */       throw new NullPointerException("callable");
/*     */     }
/* 232 */     if (unit == null) {
/* 233 */       throw new NullPointerException("unit");
/*     */     }
/* 235 */     if (delay < 0L) {
/* 236 */       throw new IllegalArgumentException(String.format("delay: %d (expected: >= 0)", new Object[] { Long.valueOf(delay) }));
/*     */     }
/*     */     
/* 239 */     return schedule(new ScheduledFutureTask<V>(this, this.delayedTaskQueue, callable, ScheduledFutureTask.deadlineNanos(unit.toNanos(delay))));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
/* 245 */     if (command == null) {
/* 246 */       throw new NullPointerException("command");
/*     */     }
/* 248 */     if (unit == null) {
/* 249 */       throw new NullPointerException("unit");
/*     */     }
/* 251 */     if (initialDelay < 0L) {
/* 252 */       throw new IllegalArgumentException(String.format("initialDelay: %d (expected: >= 0)", new Object[] { Long.valueOf(initialDelay) }));
/*     */     }
/*     */     
/* 255 */     if (period <= 0L) {
/* 256 */       throw new IllegalArgumentException(String.format("period: %d (expected: > 0)", new Object[] { Long.valueOf(period) }));
/*     */     }
/*     */ 
/*     */     
/* 260 */     return schedule(new ScheduledFutureTask(this, this.delayedTaskQueue, Executors.callable(command, null), ScheduledFutureTask.deadlineNanos(unit.toNanos(initialDelay)), unit.toNanos(period)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
/* 267 */     if (command == null) {
/* 268 */       throw new NullPointerException("command");
/*     */     }
/* 270 */     if (unit == null) {
/* 271 */       throw new NullPointerException("unit");
/*     */     }
/* 273 */     if (initialDelay < 0L) {
/* 274 */       throw new IllegalArgumentException(String.format("initialDelay: %d (expected: >= 0)", new Object[] { Long.valueOf(initialDelay) }));
/*     */     }
/*     */     
/* 277 */     if (delay <= 0L) {
/* 278 */       throw new IllegalArgumentException(String.format("delay: %d (expected: > 0)", new Object[] { Long.valueOf(delay) }));
/*     */     }
/*     */ 
/*     */     
/* 282 */     return schedule(new ScheduledFutureTask(this, this.delayedTaskQueue, Executors.callable(command, null), ScheduledFutureTask.deadlineNanos(unit.toNanos(initialDelay)), -unit.toNanos(delay)));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private <V> ScheduledFuture<V> schedule(final ScheduledFutureTask<V> task) {
/* 288 */     if (task == null) {
/* 289 */       throw new NullPointerException("task");
/*     */     }
/*     */     
/* 292 */     if (inEventLoop()) {
/* 293 */       this.delayedTaskQueue.add(task);
/*     */     } else {
/* 295 */       execute(new Runnable()
/*     */           {
/*     */             public void run() {
/* 298 */               GlobalEventExecutor.this.delayedTaskQueue.add(task);
/*     */             }
/*     */           });
/*     */     } 
/*     */     
/* 303 */     return task;
/*     */   }
/*     */   
/*     */   private void startThread() {
/* 307 */     synchronized (this.stateLock) {
/* 308 */       if (this.state == 1) {
/* 309 */         this.state = 2;
/*     */         
/* 311 */         this.thread = this.threadFactory.newThread(this.taskRunner);
/*     */         
/* 313 */         this.thread.start();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   final class TaskRunner
/*     */     implements Runnable {
/*     */     public void run() {
/*     */       while (true) {
/* 322 */         Runnable task = GlobalEventExecutor.this.takeTask();
/* 323 */         if (task != null) {
/*     */           try {
/* 325 */             task.run();
/* 326 */           } catch (Throwable t) {
/* 327 */             GlobalEventExecutor.logger.warn("Unexpected exception from the global event executor: ", t);
/*     */           } 
/*     */           
/* 330 */           if (task != GlobalEventExecutor.this.purgeTask) {
/*     */             continue;
/*     */           }
/*     */         } 
/*     */         
/* 335 */         if (GlobalEventExecutor.this.taskQueue.isEmpty() && GlobalEventExecutor.this.delayedTaskQueue.size() == 1)
/* 336 */           synchronized (GlobalEventExecutor.this.stateLock) {
/*     */             
/* 338 */             if (GlobalEventExecutor.this.taskQueue.isEmpty() && GlobalEventExecutor.this.delayedTaskQueue.size() == 1) {
/* 339 */               GlobalEventExecutor.this.state = 1;
/*     */               break;
/*     */             } 
/*     */           }  
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private final class PurgeTask implements Runnable {
/*     */     private PurgeTask() {}
/*     */     
/*     */     public void run() {
/* 351 */       Iterator<ScheduledFutureTask<?>> i = GlobalEventExecutor.this.delayedTaskQueue.iterator();
/* 352 */       while (i.hasNext()) {
/* 353 */         ScheduledFutureTask<?> task = i.next();
/* 354 */         if (task.isCancelled())
/* 355 */           i.remove(); 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\nett\\util\concurrent\GlobalEventExecutor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
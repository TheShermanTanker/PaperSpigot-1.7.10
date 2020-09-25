/*     */ package net.minecraft.util.io.netty.util.concurrent;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.PriorityQueue;
/*     */ import java.util.Queue;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.BlockingQueue;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.LinkedBlockingQueue;
/*     */ import java.util.concurrent.RejectedExecutionException;
/*     */ import java.util.concurrent.ScheduledFuture;
/*     */ import java.util.concurrent.Semaphore;
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
/*     */ public abstract class SingleThreadEventExecutor
/*     */   extends AbstractEventExecutor
/*     */ {
/*  43 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(SingleThreadEventExecutor.class);
/*     */   
/*     */   private static final int ST_NOT_STARTED = 1;
/*     */   
/*     */   private static final int ST_STARTED = 2;
/*     */   private static final int ST_SHUTTING_DOWN = 3;
/*     */   private static final int ST_SHUTDOWN = 4;
/*     */   private static final int ST_TERMINATED = 5;
/*     */   
/*  52 */   private static final Runnable WAKEUP_TASK = new Runnable()
/*     */     {
/*     */       public void run() {}
/*     */     };
/*     */ 
/*     */   
/*     */   private final EventExecutorGroup parent;
/*     */   
/*     */   private final Queue<Runnable> taskQueue;
/*  61 */   final Queue<ScheduledFutureTask<?>> delayedTaskQueue = new PriorityQueue<ScheduledFutureTask<?>>();
/*     */   
/*     */   private final Thread thread;
/*  64 */   private final Object stateLock = new Object();
/*  65 */   private final Semaphore threadLock = new Semaphore(0);
/*  66 */   private final Set<Runnable> shutdownHooks = new LinkedHashSet<Runnable>();
/*     */   
/*     */   private final boolean addTaskWakesUp;
/*     */   private long lastExecutionTime;
/*  70 */   private volatile int state = 1;
/*     */   
/*     */   private volatile long gracefulShutdownQuietPeriod;
/*     */   private volatile long gracefulShutdownTimeout;
/*     */   private long gracefulShutdownStartTime;
/*  75 */   private final Promise<?> terminationFuture = new DefaultPromise(GlobalEventExecutor.INSTANCE);
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
/*     */   protected SingleThreadEventExecutor(EventExecutorGroup parent, ThreadFactory threadFactory, boolean addTaskWakesUp) {
/*  88 */     if (threadFactory == null) {
/*  89 */       throw new NullPointerException("threadFactory");
/*     */     }
/*     */     
/*  92 */     this.parent = parent;
/*  93 */     this.addTaskWakesUp = addTaskWakesUp;
/*     */     
/*  95 */     this.thread = threadFactory.newThread(new Runnable()
/*     */         {
/*     */           public void run() {
/*  98 */             boolean success = false;
/*  99 */             SingleThreadEventExecutor.this.updateLastExecutionTime();
/*     */             try {
/* 101 */               SingleThreadEventExecutor.this.run();
/* 102 */               success = true;
/* 103 */             } catch (Throwable t) {
/* 104 */               SingleThreadEventExecutor.logger.warn("Unexpected exception from an event executor: ", t);
/*     */             } finally {
/* 106 */               if (SingleThreadEventExecutor.this.state < 3) {
/* 107 */                 SingleThreadEventExecutor.this.state = 3;
/*     */               }
/*     */ 
/*     */               
/* 111 */               if (success && SingleThreadEventExecutor.this.gracefulShutdownStartTime == 0L) {
/* 112 */                 SingleThreadEventExecutor.logger.error("Buggy " + EventExecutor.class.getSimpleName() + " implementation; " + SingleThreadEventExecutor.class.getSimpleName() + ".confirmShutdown() must be called " + "before run() implementation terminates.");
/*     */               }
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*     */               try {
/*     */                 do {
/*     */                 
/* 121 */                 } while (!SingleThreadEventExecutor.this.confirmShutdown());
/*     */               } finally {
/*     */ 
/*     */                 
/*     */                 try {
/*     */                   
/* 127 */                   SingleThreadEventExecutor.this.cleanup();
/*     */                 } finally {
/* 129 */                   synchronized (SingleThreadEventExecutor.this.stateLock) {
/* 130 */                     SingleThreadEventExecutor.this.state = 5;
/*     */                   } 
/* 132 */                   SingleThreadEventExecutor.this.threadLock.release();
/* 133 */                   if (!SingleThreadEventExecutor.this.taskQueue.isEmpty()) {
/* 134 */                     SingleThreadEventExecutor.logger.warn("An event executor terminated with non-empty task queue (" + SingleThreadEventExecutor.this.taskQueue.size() + ')');
/*     */                   }
/*     */ 
/*     */ 
/*     */                   
/* 139 */                   SingleThreadEventExecutor.this.terminationFuture.setSuccess(null);
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           }
/*     */         });
/*     */     
/* 146 */     this.taskQueue = newTaskQueue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Queue<Runnable> newTaskQueue() {
/* 156 */     return new LinkedBlockingQueue<Runnable>();
/*     */   }
/*     */ 
/*     */   
/*     */   public EventExecutorGroup parent() {
/* 161 */     return this.parent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void interruptThread() {
/* 168 */     this.thread.interrupt();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Runnable pollTask() {
/*     */     Runnable task;
/* 175 */     assert inEventLoop();
/*     */     while (true) {
/* 177 */       task = this.taskQueue.poll();
/* 178 */       if (task == WAKEUP_TASK)
/*     */         continue;  break;
/*     */     } 
/* 181 */     return task;
/*     */   }
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
/*     */   protected Runnable takeTask() {
/* 195 */     assert inEventLoop();
/* 196 */     if (!(this.taskQueue instanceof BlockingQueue)) {
/* 197 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/* 200 */     BlockingQueue<Runnable> taskQueue = (BlockingQueue<Runnable>)this.taskQueue;
/*     */     while (true) {
/* 202 */       ScheduledFutureTask<?> delayedTask = this.delayedTaskQueue.peek();
/* 203 */       if (delayedTask == null) {
/* 204 */         Runnable runnable = null;
/*     */         try {
/* 206 */           runnable = taskQueue.take();
/* 207 */           if (runnable == WAKEUP_TASK) {
/* 208 */             runnable = null;
/*     */           }
/* 210 */         } catch (InterruptedException e) {}
/*     */ 
/*     */         
/* 213 */         return runnable;
/*     */       } 
/* 215 */       long delayNanos = delayedTask.delayNanos();
/* 216 */       Runnable task = null;
/* 217 */       if (delayNanos > 0L) {
/*     */         try {
/* 219 */           task = taskQueue.poll(delayNanos, TimeUnit.NANOSECONDS);
/* 220 */         } catch (InterruptedException e) {
/* 221 */           return null;
/*     */         } 
/*     */       }
/* 224 */       if (task == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 229 */         fetchFromDelayedQueue();
/* 230 */         task = taskQueue.poll();
/*     */       } 
/*     */       
/* 233 */       if (task != null) {
/* 234 */         return task;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void fetchFromDelayedQueue() {
/* 241 */     long nanoTime = 0L;
/*     */     while (true) {
/* 243 */       ScheduledFutureTask<?> delayedTask = this.delayedTaskQueue.peek();
/* 244 */       if (delayedTask == null) {
/*     */         break;
/*     */       }
/*     */       
/* 248 */       if (nanoTime == 0L) {
/* 249 */         nanoTime = ScheduledFutureTask.nanoTime();
/*     */       }
/*     */       
/* 252 */       if (delayedTask.deadlineNanos() <= nanoTime) {
/* 253 */         this.delayedTaskQueue.remove();
/* 254 */         this.taskQueue.add(delayedTask);
/*     */         continue;
/*     */       } 
/*     */       break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Runnable peekTask() {
/* 265 */     assert inEventLoop();
/* 266 */     return this.taskQueue.peek();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean hasTasks() {
/* 273 */     assert inEventLoop();
/* 274 */     return !this.taskQueue.isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int pendingTasks() {
/* 284 */     return this.taskQueue.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addTask(Runnable task) {
/* 292 */     if (task == null) {
/* 293 */       throw new NullPointerException("task");
/*     */     }
/* 295 */     if (isShutdown()) {
/* 296 */       reject();
/*     */     }
/* 298 */     this.taskQueue.add(task);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean removeTask(Runnable task) {
/* 305 */     if (task == null) {
/* 306 */       throw new NullPointerException("task");
/*     */     }
/* 308 */     return this.taskQueue.remove(task);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean runAllTasks() {
/* 317 */     fetchFromDelayedQueue();
/* 318 */     Runnable task = pollTask();
/* 319 */     if (task == null) {
/* 320 */       return false;
/*     */     }
/*     */     
/*     */     while (true) {
/*     */       try {
/* 325 */         task.run();
/* 326 */       } catch (Throwable t) {
/* 327 */         logger.warn("A task raised an exception.", t);
/*     */       } 
/*     */       
/* 330 */       task = pollTask();
/* 331 */       if (task == null) {
/* 332 */         this.lastExecutionTime = ScheduledFutureTask.nanoTime();
/* 333 */         return true;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean runAllTasks(long timeoutNanos) {
/*     */     long lastExecutionTime;
/* 343 */     fetchFromDelayedQueue();
/* 344 */     Runnable task = pollTask();
/* 345 */     if (task == null) {
/* 346 */       return false;
/*     */     }
/*     */     
/* 349 */     long deadline = ScheduledFutureTask.nanoTime() + timeoutNanos;
/* 350 */     long runTasks = 0L;
/*     */     
/*     */     while (true) {
/*     */       try {
/* 354 */         task.run();
/* 355 */       } catch (Throwable t) {
/* 356 */         logger.warn("A task raised an exception.", t);
/*     */       } 
/*     */       
/* 359 */       runTasks++;
/*     */ 
/*     */ 
/*     */       
/* 363 */       if ((runTasks & 0x3FL) == 0L) {
/* 364 */         lastExecutionTime = ScheduledFutureTask.nanoTime();
/* 365 */         if (lastExecutionTime >= deadline) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */       
/* 370 */       task = pollTask();
/* 371 */       if (task == null) {
/* 372 */         lastExecutionTime = ScheduledFutureTask.nanoTime();
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 377 */     this.lastExecutionTime = lastExecutionTime;
/* 378 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected long delayNanos(long currentTimeNanos) {
/* 385 */     ScheduledFutureTask<?> delayedTask = this.delayedTaskQueue.peek();
/* 386 */     if (delayedTask == null) {
/* 387 */       return SCHEDULE_PURGE_INTERVAL;
/*     */     }
/*     */     
/* 390 */     return delayedTask.delayNanos(currentTimeNanos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateLastExecutionTime() {
/* 401 */     this.lastExecutionTime = ScheduledFutureTask.nanoTime();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void cleanup() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void wakeup(boolean inEventLoop) {
/* 417 */     if (!inEventLoop || this.state == 3) {
/* 418 */       this.taskQueue.add(WAKEUP_TASK);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean inEventLoop(Thread thread) {
/* 424 */     return (thread == this.thread);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addShutdownHook(final Runnable task) {
/* 431 */     if (inEventLoop()) {
/* 432 */       this.shutdownHooks.add(task);
/*     */     } else {
/* 434 */       execute(new Runnable()
/*     */           {
/*     */             public void run() {
/* 437 */               SingleThreadEventExecutor.this.shutdownHooks.add(task);
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeShutdownHook(final Runnable task) {
/* 447 */     if (inEventLoop()) {
/* 448 */       this.shutdownHooks.remove(task);
/*     */     } else {
/* 450 */       execute(new Runnable()
/*     */           {
/*     */             public void run() {
/* 453 */               SingleThreadEventExecutor.this.shutdownHooks.remove(task);
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean runShutdownHooks() {
/* 460 */     boolean ran = false;
/*     */     
/* 462 */     while (!this.shutdownHooks.isEmpty()) {
/* 463 */       List<Runnable> copy = new ArrayList<Runnable>(this.shutdownHooks);
/* 464 */       this.shutdownHooks.clear();
/* 465 */       for (Runnable task : copy) {
/*     */         try {
/* 467 */           task.run();
/* 468 */         } catch (Throwable t) {
/* 469 */           logger.warn("Shutdown hook raised an exception.", t);
/*     */         } finally {
/* 471 */           ran = true;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 476 */     if (ran) {
/* 477 */       this.lastExecutionTime = ScheduledFutureTask.nanoTime();
/*     */     }
/*     */     
/* 480 */     return ran;
/*     */   }
/*     */ 
/*     */   
/*     */   public Future<?> shutdownGracefully(long quietPeriod, long timeout, TimeUnit unit) {
/* 485 */     if (quietPeriod < 0L) {
/* 486 */       throw new IllegalArgumentException("quietPeriod: " + quietPeriod + " (expected >= 0)");
/*     */     }
/* 488 */     if (timeout < quietPeriod) {
/* 489 */       throw new IllegalArgumentException("timeout: " + timeout + " (expected >= quietPeriod (" + quietPeriod + "))");
/*     */     }
/*     */     
/* 492 */     if (unit == null) {
/* 493 */       throw new NullPointerException("unit");
/*     */     }
/*     */     
/* 496 */     if (isShuttingDown()) {
/* 497 */       return terminationFuture();
/*     */     }
/*     */     
/* 500 */     boolean inEventLoop = inEventLoop();
/* 501 */     boolean wakeup = true;
/*     */     
/* 503 */     synchronized (this.stateLock) {
/* 504 */       if (isShuttingDown()) {
/* 505 */         return terminationFuture();
/*     */       }
/*     */       
/* 508 */       this.gracefulShutdownQuietPeriod = unit.toNanos(quietPeriod);
/* 509 */       this.gracefulShutdownTimeout = unit.toNanos(timeout);
/*     */       
/* 511 */       if (inEventLoop) {
/* 512 */         assert this.state == 2;
/* 513 */         this.state = 3;
/*     */       } else {
/* 515 */         switch (this.state) {
/*     */           case 1:
/* 517 */             this.state = 3;
/* 518 */             this.thread.start();
/*     */             break;
/*     */           case 2:
/* 521 */             this.state = 3;
/*     */             break;
/*     */           default:
/* 524 */             wakeup = false;
/*     */             break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 529 */     if (wakeup) {
/* 530 */       wakeup(inEventLoop);
/*     */     }
/*     */     
/* 533 */     return terminationFuture();
/*     */   }
/*     */ 
/*     */   
/*     */   public Future<?> terminationFuture() {
/* 538 */     return this.terminationFuture;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void shutdown() {
/* 544 */     if (isShutdown()) {
/*     */       return;
/*     */     }
/*     */     
/* 548 */     boolean inEventLoop = inEventLoop();
/* 549 */     boolean wakeup = true;
/*     */     
/* 551 */     synchronized (this.stateLock) {
/* 552 */       if (isShutdown()) {
/*     */         return;
/*     */       }
/*     */       
/* 556 */       if (inEventLoop) {
/* 557 */         assert this.state == 2 || this.state == 3;
/* 558 */         this.state = 4;
/*     */       } else {
/* 560 */         switch (this.state) {
/*     */           case 1:
/* 562 */             this.state = 4;
/* 563 */             this.thread.start();
/*     */             break;
/*     */           case 2:
/*     */           case 3:
/* 567 */             this.state = 4;
/*     */             break;
/*     */           default:
/* 570 */             wakeup = false;
/*     */             break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 575 */     if (wakeup) {
/* 576 */       wakeup(inEventLoop);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isShuttingDown() {
/* 582 */     return (this.state >= 3);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isShutdown() {
/* 587 */     return (this.state >= 4);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isTerminated() {
/* 592 */     return (this.state == 5);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean confirmShutdown() {
/* 599 */     if (!isShuttingDown()) {
/* 600 */       return false;
/*     */     }
/*     */     
/* 603 */     if (!inEventLoop()) {
/* 604 */       throw new IllegalStateException("must be invoked from an event loop");
/*     */     }
/*     */     
/* 607 */     cancelDelayedTasks();
/*     */     
/* 609 */     if (this.gracefulShutdownStartTime == 0L) {
/* 610 */       this.gracefulShutdownStartTime = ScheduledFutureTask.nanoTime();
/*     */     }
/*     */     
/* 613 */     if (runAllTasks() || runShutdownHooks()) {
/* 614 */       if (isShutdown())
/*     */       {
/* 616 */         return true;
/*     */       }
/*     */ 
/*     */       
/* 620 */       wakeup(true);
/* 621 */       return false;
/*     */     } 
/*     */     
/* 624 */     long nanoTime = ScheduledFutureTask.nanoTime();
/*     */     
/* 626 */     if (isShutdown() || nanoTime - this.gracefulShutdownStartTime > this.gracefulShutdownTimeout) {
/* 627 */       return true;
/*     */     }
/*     */     
/* 630 */     if (nanoTime - this.lastExecutionTime <= this.gracefulShutdownQuietPeriod) {
/*     */ 
/*     */       
/* 633 */       wakeup(true);
/*     */       try {
/* 635 */         Thread.sleep(100L);
/* 636 */       } catch (InterruptedException e) {}
/*     */ 
/*     */ 
/*     */       
/* 640 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 645 */     return true;
/*     */   }
/*     */   
/*     */   private void cancelDelayedTasks() {
/* 649 */     if (this.delayedTaskQueue.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 653 */     ScheduledFutureTask[] arrayOfScheduledFutureTask = (ScheduledFutureTask[])this.delayedTaskQueue.toArray((Object[])new ScheduledFutureTask[this.delayedTaskQueue.size()]);
/*     */ 
/*     */     
/* 656 */     for (ScheduledFutureTask<?> task : arrayOfScheduledFutureTask) {
/* 657 */       task.cancel(false);
/*     */     }
/*     */     
/* 660 */     this.delayedTaskQueue.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
/* 665 */     if (unit == null) {
/* 666 */       throw new NullPointerException("unit");
/*     */     }
/*     */     
/* 669 */     if (inEventLoop()) {
/* 670 */       throw new IllegalStateException("cannot await termination of the current thread");
/*     */     }
/*     */     
/* 673 */     if (this.threadLock.tryAcquire(timeout, unit)) {
/* 674 */       this.threadLock.release();
/*     */     }
/*     */     
/* 677 */     return isTerminated();
/*     */   }
/*     */ 
/*     */   
/*     */   public void execute(Runnable task) {
/* 682 */     if (task == null) {
/* 683 */       throw new NullPointerException("task");
/*     */     }
/*     */     
/* 686 */     boolean inEventLoop = inEventLoop();
/* 687 */     if (inEventLoop) {
/* 688 */       addTask(task);
/*     */     } else {
/* 690 */       startThread();
/* 691 */       addTask(task);
/* 692 */       if (isShutdown() && removeTask(task)) {
/* 693 */         reject();
/*     */       }
/*     */     } 
/*     */     
/* 697 */     if (!this.addTaskWakesUp) {
/* 698 */       wakeup(inEventLoop);
/*     */     }
/*     */   }
/*     */   
/*     */   protected static void reject() {
/* 703 */     throw new RejectedExecutionException("event executor terminated");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 708 */   private static final long SCHEDULE_PURGE_INTERVAL = TimeUnit.SECONDS.toNanos(1L);
/*     */ 
/*     */   
/*     */   public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
/* 712 */     if (command == null) {
/* 713 */       throw new NullPointerException("command");
/*     */     }
/* 715 */     if (unit == null) {
/* 716 */       throw new NullPointerException("unit");
/*     */     }
/* 718 */     if (delay < 0L) {
/* 719 */       throw new IllegalArgumentException(String.format("delay: %d (expected: >= 0)", new Object[] { Long.valueOf(delay) }));
/*     */     }
/*     */     
/* 722 */     return schedule(new ScheduledFutureTask(this, this.delayedTaskQueue, command, null, ScheduledFutureTask.deadlineNanos(unit.toNanos(delay))));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
/* 728 */     if (callable == null) {
/* 729 */       throw new NullPointerException("callable");
/*     */     }
/* 731 */     if (unit == null) {
/* 732 */       throw new NullPointerException("unit");
/*     */     }
/* 734 */     if (delay < 0L) {
/* 735 */       throw new IllegalArgumentException(String.format("delay: %d (expected: >= 0)", new Object[] { Long.valueOf(delay) }));
/*     */     }
/*     */     
/* 738 */     return schedule(new ScheduledFutureTask<V>(this, this.delayedTaskQueue, callable, ScheduledFutureTask.deadlineNanos(unit.toNanos(delay))));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
/* 744 */     if (command == null) {
/* 745 */       throw new NullPointerException("command");
/*     */     }
/* 747 */     if (unit == null) {
/* 748 */       throw new NullPointerException("unit");
/*     */     }
/* 750 */     if (initialDelay < 0L) {
/* 751 */       throw new IllegalArgumentException(String.format("initialDelay: %d (expected: >= 0)", new Object[] { Long.valueOf(initialDelay) }));
/*     */     }
/*     */     
/* 754 */     if (period <= 0L) {
/* 755 */       throw new IllegalArgumentException(String.format("period: %d (expected: > 0)", new Object[] { Long.valueOf(period) }));
/*     */     }
/*     */ 
/*     */     
/* 759 */     return schedule(new ScheduledFutureTask(this, this.delayedTaskQueue, Executors.callable(command, null), ScheduledFutureTask.deadlineNanos(unit.toNanos(initialDelay)), unit.toNanos(period)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
/* 766 */     if (command == null) {
/* 767 */       throw new NullPointerException("command");
/*     */     }
/* 769 */     if (unit == null) {
/* 770 */       throw new NullPointerException("unit");
/*     */     }
/* 772 */     if (initialDelay < 0L) {
/* 773 */       throw new IllegalArgumentException(String.format("initialDelay: %d (expected: >= 0)", new Object[] { Long.valueOf(initialDelay) }));
/*     */     }
/*     */     
/* 776 */     if (delay <= 0L) {
/* 777 */       throw new IllegalArgumentException(String.format("delay: %d (expected: > 0)", new Object[] { Long.valueOf(delay) }));
/*     */     }
/*     */ 
/*     */     
/* 781 */     return schedule(new ScheduledFutureTask(this, this.delayedTaskQueue, Executors.callable(command, null), ScheduledFutureTask.deadlineNanos(unit.toNanos(initialDelay)), -unit.toNanos(delay)));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private <V> ScheduledFuture<V> schedule(final ScheduledFutureTask<V> task) {
/* 787 */     if (task == null) {
/* 788 */       throw new NullPointerException("task");
/*     */     }
/*     */     
/* 791 */     if (inEventLoop()) {
/* 792 */       this.delayedTaskQueue.add(task);
/*     */     } else {
/* 794 */       execute(new Runnable()
/*     */           {
/*     */             public void run() {
/* 797 */               SingleThreadEventExecutor.this.delayedTaskQueue.add(task);
/*     */             }
/*     */           });
/*     */     } 
/*     */     
/* 802 */     return task;
/*     */   }
/*     */   
/*     */   private void startThread() {
/* 806 */     synchronized (this.stateLock) {
/* 807 */       if (this.state == 1) {
/* 808 */         this.state = 2;
/* 809 */         this.delayedTaskQueue.add(new ScheduledFutureTask(this, this.delayedTaskQueue, Executors.callable(new PurgeTask(), null), ScheduledFutureTask.deadlineNanos(SCHEDULE_PURGE_INTERVAL), -SCHEDULE_PURGE_INTERVAL));
/*     */ 
/*     */         
/* 812 */         this.thread.start();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected abstract void run();
/*     */   
/*     */   private final class PurgeTask implements Runnable { public void run() {
/* 820 */       Iterator<ScheduledFutureTask<?>> i = SingleThreadEventExecutor.this.delayedTaskQueue.iterator();
/* 821 */       while (i.hasNext()) {
/* 822 */         ScheduledFutureTask<?> task = i.next();
/* 823 */         if (task.isCancelled())
/* 824 */           i.remove(); 
/*     */       } 
/*     */     }
/*     */     
/*     */     private PurgeTask() {} }
/*     */ 
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\nett\\util\concurrent\SingleThreadEventExecutor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
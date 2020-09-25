/*     */ package net.minecraft.util.io.netty.util;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import java.util.concurrent.locks.ReadWriteLock;
/*     */ import java.util.concurrent.locks.ReentrantReadWriteLock;
/*     */ import net.minecraft.util.io.netty.util.internal.PlatformDependent;
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
/*     */ public class HashedWheelTimer
/*     */   implements Timer
/*     */ {
/*  78 */   static final InternalLogger logger = InternalLoggerFactory.getInstance(HashedWheelTimer.class);
/*     */ 
/*     */   
/*  81 */   private static final ResourceLeakDetector<HashedWheelTimer> leakDetector = new ResourceLeakDetector<HashedWheelTimer>(HashedWheelTimer.class, 1, (Runtime.getRuntime().availableProcessors() * 4));
/*     */ 
/*     */   
/*     */   private final ResourceLeak leak;
/*     */   
/*  86 */   private final Worker worker = new Worker();
/*     */   
/*     */   final Thread workerThread;
/*     */   public static final int WORKER_STATE_INIT = 0;
/*     */   public static final int WORKER_STATE_STARTED = 1;
/*     */   public static final int WORKER_STATE_SHUTDOWN = 2;
/*  92 */   final AtomicInteger workerState = new AtomicInteger();
/*     */   
/*     */   final long tickDuration;
/*     */   final Set<HashedWheelTimeout>[] wheel;
/*     */   final int mask;
/*  97 */   final ReadWriteLock lock = new ReentrantReadWriteLock();
/*     */ 
/*     */ 
/*     */   
/*     */   volatile int wheelCursor;
/*     */ 
/*     */ 
/*     */   
/*     */   public HashedWheelTimer() {
/* 106 */     this(Executors.defaultThreadFactory());
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
/*     */   public HashedWheelTimer(long tickDuration, TimeUnit unit) {
/* 120 */     this(Executors.defaultThreadFactory(), tickDuration, unit);
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
/*     */   public HashedWheelTimer(long tickDuration, TimeUnit unit, int ticksPerWheel) {
/* 134 */     this(Executors.defaultThreadFactory(), tickDuration, unit, ticksPerWheel);
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
/*     */   public HashedWheelTimer(ThreadFactory threadFactory) {
/* 147 */     this(threadFactory, 100L, TimeUnit.MILLISECONDS);
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
/*     */ 
/*     */   
/*     */   public HashedWheelTimer(ThreadFactory threadFactory, long tickDuration, TimeUnit unit) {
/* 163 */     this(threadFactory, tickDuration, unit, 512);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HashedWheelTimer(ThreadFactory threadFactory, long tickDuration, TimeUnit unit, int ticksPerWheel) {
/* 182 */     if (threadFactory == null) {
/* 183 */       throw new NullPointerException("threadFactory");
/*     */     }
/* 185 */     if (unit == null) {
/* 186 */       throw new NullPointerException("unit");
/*     */     }
/* 188 */     if (tickDuration <= 0L) {
/* 189 */       throw new IllegalArgumentException("tickDuration must be greater than 0: " + tickDuration);
/*     */     }
/* 191 */     if (ticksPerWheel <= 0) {
/* 192 */       throw new IllegalArgumentException("ticksPerWheel must be greater than 0: " + ticksPerWheel);
/*     */     }
/*     */ 
/*     */     
/* 196 */     this.wheel = createWheel(ticksPerWheel);
/* 197 */     this.mask = this.wheel.length - 1;
/*     */ 
/*     */     
/* 200 */     this.tickDuration = unit.toNanos(tickDuration);
/*     */ 
/*     */     
/* 203 */     if (this.tickDuration >= Long.MAX_VALUE / this.wheel.length) {
/* 204 */       throw new IllegalArgumentException(String.format("tickDuration: %d (expected: 0 < tickDuration in nanos < %d", new Object[] { Long.valueOf(tickDuration), Long.valueOf(Long.MAX_VALUE / this.wheel.length) }));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 209 */     this.workerThread = threadFactory.newThread(this.worker);
/* 210 */     this.leak = leakDetector.open(this);
/*     */   }
/*     */ 
/*     */   
/*     */   private static Set<HashedWheelTimeout>[] createWheel(int ticksPerWheel) {
/* 215 */     if (ticksPerWheel <= 0) {
/* 216 */       throw new IllegalArgumentException("ticksPerWheel must be greater than 0: " + ticksPerWheel);
/*     */     }
/*     */     
/* 219 */     if (ticksPerWheel > 1073741824) {
/* 220 */       throw new IllegalArgumentException("ticksPerWheel may not be greater than 2^30: " + ticksPerWheel);
/*     */     }
/*     */ 
/*     */     
/* 224 */     ticksPerWheel = normalizeTicksPerWheel(ticksPerWheel);
/* 225 */     Set[] arrayOfSet = new Set[ticksPerWheel];
/* 226 */     for (int i = 0; i < arrayOfSet.length; i++) {
/* 227 */       arrayOfSet[i] = Collections.newSetFromMap(PlatformDependent.newConcurrentHashMap());
/*     */     }
/*     */     
/* 230 */     return (Set<HashedWheelTimeout>[])arrayOfSet;
/*     */   }
/*     */   
/*     */   private static int normalizeTicksPerWheel(int ticksPerWheel) {
/* 234 */     int normalizedTicksPerWheel = 1;
/* 235 */     while (normalizedTicksPerWheel < ticksPerWheel) {
/* 236 */       normalizedTicksPerWheel <<= 1;
/*     */     }
/* 238 */     return normalizedTicksPerWheel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void start() {
/* 249 */     switch (this.workerState.get()) {
/*     */       case 0:
/* 251 */         if (this.workerState.compareAndSet(0, 1)) {
/* 252 */           this.workerThread.start();
/*     */         }
/*     */       
/*     */       case 1:
/*     */         return;
/*     */       case 2:
/* 258 */         throw new IllegalStateException("cannot be started once stopped");
/*     */     } 
/* 260 */     throw new Error("Invalid WorkerState");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<Timeout> stop() {
/* 266 */     if (Thread.currentThread() == this.workerThread) {
/* 267 */       throw new IllegalStateException(HashedWheelTimer.class.getSimpleName() + ".stop() cannot be called from " + TimerTask.class.getSimpleName());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 273 */     if (!this.workerState.compareAndSet(1, 2)) {
/*     */       
/* 275 */       this.workerState.set(2);
/*     */       
/* 277 */       if (this.leak != null) {
/* 278 */         this.leak.close();
/*     */       }
/*     */       
/* 281 */       return Collections.emptySet();
/*     */     } 
/*     */     
/* 284 */     boolean interrupted = false;
/* 285 */     while (this.workerThread.isAlive()) {
/* 286 */       this.workerThread.interrupt();
/*     */       try {
/* 288 */         this.workerThread.join(100L);
/* 289 */       } catch (InterruptedException e) {
/* 290 */         interrupted = true;
/*     */       } 
/*     */     } 
/*     */     
/* 294 */     if (interrupted) {
/* 295 */       Thread.currentThread().interrupt();
/*     */     }
/*     */     
/* 298 */     if (this.leak != null) {
/* 299 */       this.leak.close();
/*     */     }
/*     */     
/* 302 */     Set<Timeout> unprocessedTimeouts = new HashSet<Timeout>();
/* 303 */     for (Set<HashedWheelTimeout> bucket : this.wheel) {
/* 304 */       unprocessedTimeouts.addAll((Collection)bucket);
/* 305 */       bucket.clear();
/*     */     } 
/*     */     
/* 308 */     return Collections.unmodifiableSet(unprocessedTimeouts);
/*     */   }
/*     */ 
/*     */   
/*     */   public Timeout newTimeout(TimerTask task, long delay, TimeUnit unit) {
/* 313 */     long currentTime = System.nanoTime();
/*     */     
/* 315 */     if (task == null) {
/* 316 */       throw new NullPointerException("task");
/*     */     }
/* 318 */     if (unit == null) {
/* 319 */       throw new NullPointerException("unit");
/*     */     }
/*     */     
/* 322 */     start();
/*     */     
/* 324 */     long delayInNanos = unit.toNanos(delay);
/* 325 */     HashedWheelTimeout timeout = new HashedWheelTimeout(task, currentTime + delayInNanos);
/* 326 */     scheduleTimeout(timeout, delayInNanos);
/* 327 */     return timeout;
/*     */   }
/*     */ 
/*     */   
/*     */   void scheduleTimeout(HashedWheelTimeout timeout, long delay) {
/* 332 */     long relativeIndex = (delay + this.tickDuration - 1L) / this.tickDuration;
/*     */ 
/*     */     
/* 335 */     if (relativeIndex < 0L) {
/* 336 */       relativeIndex = delay / this.tickDuration;
/*     */     }
/* 338 */     if (relativeIndex == 0L) {
/* 339 */       relativeIndex = 1L;
/*     */     }
/* 341 */     if ((relativeIndex & this.mask) == 0L) {
/* 342 */       relativeIndex--;
/*     */     }
/* 344 */     long remainingRounds = relativeIndex / this.wheel.length;
/*     */ 
/*     */     
/* 347 */     this.lock.readLock().lock();
/*     */     try {
/* 349 */       if (this.workerState.get() == 2) {
/* 350 */         throw new IllegalStateException("Cannot enqueue after shutdown");
/*     */       }
/* 352 */       int stopIndex = (int)(this.wheelCursor + relativeIndex & this.mask);
/* 353 */       timeout.stopIndex = stopIndex;
/* 354 */       timeout.remainingRounds = remainingRounds;
/* 355 */       this.wheel[stopIndex].add(timeout);
/*     */     } finally {
/* 357 */       this.lock.readLock().unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private final class Worker
/*     */     implements Runnable
/*     */   {
/*     */     private long startTime;
/*     */     
/*     */     private long tick;
/*     */ 
/*     */     
/*     */     public void run() {
/* 371 */       List<HashedWheelTimer.HashedWheelTimeout> expiredTimeouts = new ArrayList<HashedWheelTimer.HashedWheelTimeout>();
/*     */ 
/*     */       
/* 374 */       this.startTime = System.nanoTime();
/* 375 */       this.tick = 1L;
/*     */       
/* 377 */       while (HashedWheelTimer.this.workerState.get() == 1) {
/* 378 */         long deadline = waitForNextTick();
/* 379 */         if (deadline > 0L) {
/* 380 */           fetchExpiredTimeouts(expiredTimeouts, deadline);
/* 381 */           notifyExpiredTimeouts(expiredTimeouts);
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void fetchExpiredTimeouts(List<HashedWheelTimer.HashedWheelTimeout> expiredTimeouts, long deadline) {
/* 393 */       HashedWheelTimer.this.lock.writeLock().lock();
/*     */       try {
/* 395 */         int newWheelCursor = HashedWheelTimer.this.wheelCursor = HashedWheelTimer.this.wheelCursor + 1 & HashedWheelTimer.this.mask;
/* 396 */         fetchExpiredTimeouts(expiredTimeouts, HashedWheelTimer.this.wheel[newWheelCursor].iterator(), deadline);
/*     */       } finally {
/* 398 */         HashedWheelTimer.this.lock.writeLock().unlock();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void fetchExpiredTimeouts(List<HashedWheelTimer.HashedWheelTimeout> expiredTimeouts, Iterator<HashedWheelTimer.HashedWheelTimeout> i, long deadline) {
/* 406 */       List<HashedWheelTimer.HashedWheelTimeout> slipped = null;
/* 407 */       while (i.hasNext()) {
/* 408 */         HashedWheelTimer.HashedWheelTimeout timeout = i.next();
/* 409 */         if (timeout.remainingRounds <= 0L) {
/* 410 */           i.remove();
/* 411 */           if (timeout.deadline <= deadline) {
/* 412 */             expiredTimeouts.add(timeout);
/*     */ 
/*     */             
/*     */             continue;
/*     */           } 
/*     */           
/* 418 */           if (slipped == null) {
/* 419 */             slipped = new ArrayList<HashedWheelTimer.HashedWheelTimeout>();
/*     */           }
/* 421 */           slipped.add(timeout);
/*     */           continue;
/*     */         } 
/* 424 */         timeout.remainingRounds--;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 429 */       if (slipped != null) {
/* 430 */         for (HashedWheelTimer.HashedWheelTimeout timeout : slipped) {
/* 431 */           HashedWheelTimer.this.scheduleTimeout(timeout, timeout.deadline - deadline);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void notifyExpiredTimeouts(List<HashedWheelTimer.HashedWheelTimeout> expiredTimeouts) {
/* 439 */       for (int i = expiredTimeouts.size() - 1; i >= 0; i--) {
/* 440 */         ((HashedWheelTimer.HashedWheelTimeout)expiredTimeouts.get(i)).expire();
/*     */       }
/*     */ 
/*     */       
/* 444 */       expiredTimeouts.clear();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private long waitForNextTick() {
/* 454 */       long deadline = this.startTime + HashedWheelTimer.this.tickDuration * this.tick;
/*     */       
/*     */       while (true) {
/* 457 */         long currentTime = System.nanoTime();
/* 458 */         long sleepTimeMs = (deadline - currentTime + 999999L) / 1000000L;
/*     */         
/* 460 */         if (sleepTimeMs <= 0L) {
/* 461 */           this.tick++;
/* 462 */           if (currentTime == Long.MIN_VALUE) {
/* 463 */             return -9223372036854775807L;
/*     */           }
/* 465 */           return currentTime;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 474 */         if (PlatformDependent.isWindows()) {
/* 475 */           sleepTimeMs = sleepTimeMs / 10L * 10L;
/*     */         }
/*     */         
/*     */         try {
/* 479 */           Thread.sleep(sleepTimeMs);
/* 480 */         } catch (InterruptedException e) {
/* 481 */           if (HashedWheelTimer.this.workerState.get() == 2) {
/* 482 */             return Long.MIN_VALUE;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private final class HashedWheelTimeout
/*     */     implements Timeout
/*     */   {
/*     */     private static final int ST_INIT = 0;
/*     */     private static final int ST_CANCELLED = 1;
/*     */     private static final int ST_EXPIRED = 2;
/*     */     private final TimerTask task;
/*     */     final long deadline;
/*     */     volatile int stopIndex;
/*     */     volatile long remainingRounds;
/* 499 */     private final AtomicInteger state = new AtomicInteger(0);
/*     */     
/*     */     HashedWheelTimeout(TimerTask task, long deadline) {
/* 502 */       this.task = task;
/* 503 */       this.deadline = deadline;
/*     */     }
/*     */ 
/*     */     
/*     */     public Timer timer() {
/* 508 */       return HashedWheelTimer.this;
/*     */     }
/*     */ 
/*     */     
/*     */     public TimerTask task() {
/* 513 */       return this.task;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean cancel() {
/* 518 */       if (!this.state.compareAndSet(0, 1)) {
/* 519 */         return false;
/*     */       }
/*     */       
/* 522 */       HashedWheelTimer.this.wheel[this.stopIndex].remove(this);
/* 523 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isCancelled() {
/* 528 */       return (this.state.get() == 1);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isExpired() {
/* 533 */       return (this.state.get() != 0);
/*     */     }
/*     */     
/*     */     public void expire() {
/* 537 */       if (!this.state.compareAndSet(0, 2)) {
/*     */         return;
/*     */       }
/*     */       
/*     */       try {
/* 542 */         this.task.run(this);
/* 543 */       } catch (Throwable t) {
/* 544 */         if (HashedWheelTimer.logger.isWarnEnabled()) {
/* 545 */           HashedWheelTimer.logger.warn("An exception was thrown by " + TimerTask.class.getSimpleName() + '.', t);
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 552 */       long currentTime = System.nanoTime();
/* 553 */       long remaining = this.deadline - currentTime;
/*     */       
/* 555 */       StringBuilder buf = new StringBuilder(192);
/* 556 */       buf.append(getClass().getSimpleName());
/* 557 */       buf.append('(');
/*     */       
/* 559 */       buf.append("deadline: ");
/* 560 */       if (remaining > 0L) {
/* 561 */         buf.append(remaining);
/* 562 */         buf.append(" ms later, ");
/* 563 */       } else if (remaining < 0L) {
/* 564 */         buf.append(-remaining);
/* 565 */         buf.append(" ms ago, ");
/*     */       } else {
/* 567 */         buf.append("now, ");
/*     */       } 
/*     */       
/* 570 */       if (isCancelled()) {
/* 571 */         buf.append(", cancelled");
/*     */       }
/*     */       
/* 574 */       return buf.append(')').toString();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\nett\\util\HashedWheelTimer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
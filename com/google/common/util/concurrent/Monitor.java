/*     */ package com.google.common.util.concurrent;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.base.Throwables;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.ArrayList;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.locks.Condition;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ import javax.annotation.Nullable;
/*     */ import javax.annotation.concurrent.GuardedBy;
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
/*     */ @Beta
/*     */ public final class Monitor
/*     */ {
/*     */   private final ReentrantLock lock;
/*     */   
/*     */   @Beta
/*     */   public static abstract class Guard
/*     */   {
/*     */     final Monitor monitor;
/*     */     final Condition condition;
/*     */     @GuardedBy("monitor.lock")
/* 229 */     int waiterCount = 0;
/*     */ 
/*     */     
/*     */     protected Guard(Monitor monitor) {
/* 233 */       this.monitor = (Monitor)Preconditions.checkNotNull(monitor, "monitor");
/* 234 */       this.condition = monitor.lock.newCondition();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public abstract boolean isSatisfied();
/*     */ 
/*     */ 
/*     */     
/*     */     public final boolean equals(Object other) {
/* 245 */       return (this == other);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public final int hashCode() {
/* 251 */       return super.hashCode();
/*     */     }
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
/* 266 */   private final ArrayList<Guard> activeGuards = Lists.newArrayListWithCapacity(1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Monitor() {
/* 273 */     this(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Monitor(boolean fair) {
/* 283 */     this.lock = new ReentrantLock(fair);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void enter() {
/* 290 */     this.lock.lock();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void enterInterruptibly() throws InterruptedException {
/* 297 */     this.lock.lockInterruptibly();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean enter(long time, TimeUnit unit) {
/* 306 */     ReentrantLock lock = this.lock;
/* 307 */     long startNanos = System.nanoTime();
/* 308 */     long timeoutNanos = unit.toNanos(time);
/* 309 */     long remainingNanos = timeoutNanos;
/* 310 */     boolean interruptIgnored = false;
/*     */     
/*     */     while (true) {
/*     */       try {
/* 314 */         return lock.tryLock(remainingNanos, TimeUnit.NANOSECONDS);
/* 315 */       } catch (InterruptedException ignored) {
/* 316 */         interruptIgnored = true;
/*     */       
/*     */       }
/*     */       finally {
/*     */         
/* 321 */         if (interruptIgnored) {
/* 322 */           Thread.currentThread().interrupt();
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean enterInterruptibly(long time, TimeUnit unit) throws InterruptedException {
/* 333 */     return this.lock.tryLock(time, unit);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean tryEnter() {
/* 344 */     return this.lock.tryLock();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void enterWhen(Guard guard) throws InterruptedException {
/* 351 */     if (guard.monitor != this) {
/* 352 */       throw new IllegalMonitorStateException();
/*     */     }
/* 354 */     ReentrantLock lock = this.lock;
/* 355 */     boolean reentrant = lock.isHeldByCurrentThread();
/* 356 */     lock.lockInterruptibly();
/*     */     try {
/* 358 */       waitInterruptibly(guard, reentrant);
/* 359 */     } catch (Throwable throwable) {
/* 360 */       lock.unlock();
/* 361 */       throw Throwables.propagate(throwable);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void enterWhenUninterruptibly(Guard guard) {
/* 369 */     if (guard.monitor != this) {
/* 370 */       throw new IllegalMonitorStateException();
/*     */     }
/* 372 */     ReentrantLock lock = this.lock;
/* 373 */     boolean reentrant = lock.isHeldByCurrentThread();
/* 374 */     lock.lock();
/*     */     try {
/* 376 */       waitUninterruptibly(guard, reentrant);
/* 377 */     } catch (Throwable throwable) {
/* 378 */       lock.unlock();
/* 379 */       throw Throwables.propagate(throwable);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean enterWhen(Guard guard, long time, TimeUnit unit) throws InterruptedException {
/*     */     boolean satisfied;
/* 391 */     if (guard.monitor != this) {
/* 392 */       throw new IllegalMonitorStateException();
/*     */     }
/* 394 */     ReentrantLock lock = this.lock;
/* 395 */     boolean reentrant = lock.isHeldByCurrentThread();
/* 396 */     long startNanos = System.nanoTime();
/* 397 */     if (!lock.tryLock(time, unit)) {
/* 398 */       return false;
/*     */     }
/*     */     
/*     */     try {
/* 402 */       long remainingNanos = unit.toNanos(time) - System.nanoTime() - startNanos;
/* 403 */       satisfied = waitInterruptibly(guard, remainingNanos, reentrant);
/* 404 */     } catch (Throwable throwable) {
/* 405 */       lock.unlock();
/* 406 */       throw Throwables.propagate(throwable);
/*     */     } 
/* 408 */     if (satisfied) {
/* 409 */       return true;
/*     */     }
/* 411 */     lock.unlock();
/* 412 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean enterWhenUninterruptibly(Guard guard, long time, TimeUnit unit) {
/* 423 */     if (guard.monitor != this) {
/* 424 */       throw new IllegalMonitorStateException();
/*     */     }
/* 426 */     ReentrantLock lock = this.lock;
/* 427 */     boolean reentrant = lock.isHeldByCurrentThread();
/* 428 */     long startNanos = System.nanoTime();
/* 429 */     long timeoutNanos = unit.toNanos(time);
/* 430 */     long remainingNanos = timeoutNanos;
/* 431 */     boolean interruptIgnored = false; 
/*     */     try { boolean satisfied;
/*     */       while (true)
/*     */       { 
/* 435 */         try { if (lock.tryLock(remainingNanos, TimeUnit.NANOSECONDS)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 443 */             remainingNanos = timeoutNanos - System.nanoTime() - startNanos;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             break;
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           satisfied = false;
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 461 */           return satisfied; } catch (InterruptedException ignored) { interruptIgnored = true; } finally { remainingNanos = timeoutNanos - System.nanoTime() - startNanos; }  }  try { satisfied = waitUninterruptibly(guard, remainingNanos, reentrant); } catch (Throwable throwable) { lock.unlock(); throw Throwables.propagate(throwable); }  if (satisfied) return true;  lock.unlock(); return false; } finally { if (interruptIgnored) Thread.currentThread().interrupt();
/*     */        }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean enterIf(Guard guard) {
/*     */     boolean satisfied;
/* 473 */     if (guard.monitor != this) {
/* 474 */       throw new IllegalMonitorStateException();
/*     */     }
/* 476 */     ReentrantLock lock = this.lock;
/* 477 */     lock.lock();
/*     */     
/*     */     try {
/* 480 */       satisfied = guard.isSatisfied();
/* 481 */     } catch (Throwable throwable) {
/* 482 */       lock.unlock();
/* 483 */       throw Throwables.propagate(throwable);
/*     */     } 
/* 485 */     if (satisfied) {
/* 486 */       return true;
/*     */     }
/* 488 */     lock.unlock();
/* 489 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean enterIfInterruptibly(Guard guard) throws InterruptedException {
/*     */     boolean satisfied;
/* 500 */     if (guard.monitor != this) {
/* 501 */       throw new IllegalMonitorStateException();
/*     */     }
/* 503 */     ReentrantLock lock = this.lock;
/* 504 */     lock.lockInterruptibly();
/*     */     
/*     */     try {
/* 507 */       satisfied = guard.isSatisfied();
/* 508 */     } catch (Throwable throwable) {
/* 509 */       lock.unlock();
/* 510 */       throw Throwables.propagate(throwable);
/*     */     } 
/* 512 */     if (satisfied) {
/* 513 */       return true;
/*     */     }
/* 515 */     lock.unlock();
/* 516 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean enterIf(Guard guard, long time, TimeUnit unit) {
/*     */     boolean satisfied;
/* 527 */     if (guard.monitor != this) {
/* 528 */       throw new IllegalMonitorStateException();
/*     */     }
/* 530 */     ReentrantLock lock = this.lock;
/* 531 */     if (!enter(time, unit)) {
/* 532 */       return false;
/*     */     }
/*     */     
/*     */     try {
/* 536 */       satisfied = guard.isSatisfied();
/* 537 */     } catch (Throwable throwable) {
/* 538 */       lock.unlock();
/* 539 */       throw Throwables.propagate(throwable);
/*     */     } 
/* 541 */     if (satisfied) {
/* 542 */       return true;
/*     */     }
/* 544 */     lock.unlock();
/* 545 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean enterIfInterruptibly(Guard guard, long time, TimeUnit unit) throws InterruptedException {
/*     */     boolean satisfied;
/* 557 */     if (guard.monitor != this) {
/* 558 */       throw new IllegalMonitorStateException();
/*     */     }
/* 560 */     ReentrantLock lock = this.lock;
/* 561 */     if (!lock.tryLock(time, unit)) {
/* 562 */       return false;
/*     */     }
/*     */     
/*     */     try {
/* 566 */       satisfied = guard.isSatisfied();
/* 567 */     } catch (Throwable throwable) {
/* 568 */       lock.unlock();
/* 569 */       throw Throwables.propagate(throwable);
/*     */     } 
/* 571 */     if (satisfied) {
/* 572 */       return true;
/*     */     }
/* 574 */     lock.unlock();
/* 575 */     return false;
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
/*     */   public boolean tryEnterIf(Guard guard) {
/*     */     boolean satisfied;
/* 588 */     if (guard.monitor != this) {
/* 589 */       throw new IllegalMonitorStateException();
/*     */     }
/* 591 */     ReentrantLock lock = this.lock;
/* 592 */     if (!lock.tryLock()) {
/* 593 */       return false;
/*     */     }
/*     */     
/*     */     try {
/* 597 */       satisfied = guard.isSatisfied();
/* 598 */     } catch (Throwable throwable) {
/* 599 */       lock.unlock();
/* 600 */       throw Throwables.propagate(throwable);
/*     */     } 
/* 602 */     if (satisfied) {
/* 603 */       return true;
/*     */     }
/* 605 */     lock.unlock();
/* 606 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @GuardedBy("lock")
/*     */   public void waitFor(Guard guard) throws InterruptedException {
/* 616 */     if (guard.monitor != this) {
/* 617 */       throw new IllegalMonitorStateException();
/*     */     }
/* 619 */     if (!this.lock.isHeldByCurrentThread()) {
/* 620 */       throw new IllegalMonitorStateException();
/*     */     }
/* 622 */     waitInterruptibly(guard, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @GuardedBy("lock")
/*     */   public void waitForUninterruptibly(Guard guard) {
/* 631 */     if (guard.monitor != this) {
/* 632 */       throw new IllegalMonitorStateException();
/*     */     }
/* 634 */     if (!this.lock.isHeldByCurrentThread()) {
/* 635 */       throw new IllegalMonitorStateException();
/*     */     }
/* 637 */     waitUninterruptibly(guard, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @GuardedBy("lock")
/*     */   public boolean waitFor(Guard guard, long time, TimeUnit unit) throws InterruptedException {
/* 648 */     if (guard.monitor != this) {
/* 649 */       throw new IllegalMonitorStateException();
/*     */     }
/* 651 */     if (!this.lock.isHeldByCurrentThread()) {
/* 652 */       throw new IllegalMonitorStateException();
/*     */     }
/* 654 */     return waitInterruptibly(guard, unit.toNanos(time), true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @GuardedBy("lock")
/*     */   public boolean waitForUninterruptibly(Guard guard, long time, TimeUnit unit) {
/* 665 */     if (guard.monitor != this) {
/* 666 */       throw new IllegalMonitorStateException();
/*     */     }
/* 668 */     if (!this.lock.isHeldByCurrentThread()) {
/* 669 */       throw new IllegalMonitorStateException();
/*     */     }
/* 671 */     return waitUninterruptibly(guard, unit.toNanos(time), true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @GuardedBy("lock")
/*     */   public void leave() {
/* 679 */     ReentrantLock lock = this.lock;
/* 680 */     if (!lock.isHeldByCurrentThread()) {
/* 681 */       throw new IllegalMonitorStateException();
/*     */     }
/*     */     try {
/* 684 */       signalConditionsOfSatisfiedGuards(null);
/*     */     } finally {
/* 686 */       lock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reevaluateGuards() {
/* 698 */     ReentrantLock lock = this.lock;
/* 699 */     lock.lock();
/*     */     try {
/* 701 */       signalConditionsOfSatisfiedGuards(null);
/*     */     } finally {
/* 703 */       lock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFair() {
/* 711 */     return this.lock.isFair();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOccupied() {
/* 719 */     return this.lock.isLocked();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOccupiedByCurrentThread() {
/* 727 */     return this.lock.isHeldByCurrentThread();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOccupiedDepth() {
/* 735 */     return this.lock.getHoldCount();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getQueueLength() {
/* 745 */     return this.lock.getQueueLength();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasQueuedThreads() {
/* 755 */     return this.lock.hasQueuedThreads();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasQueuedThread(Thread thread) {
/* 765 */     return this.lock.hasQueuedThread(thread);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasWaiters(Guard guard) {
/* 775 */     if (guard.monitor != this) {
/* 776 */       throw new IllegalMonitorStateException();
/*     */     }
/* 778 */     this.lock.lock();
/*     */     try {
/* 780 */       return (guard.waiterCount > 0);
/*     */     } finally {
/* 782 */       this.lock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWaitQueueLength(Guard guard) {
/* 793 */     if (guard.monitor != this) {
/* 794 */       throw new IllegalMonitorStateException();
/*     */     }
/* 796 */     this.lock.lock();
/*     */     try {
/* 798 */       return guard.waiterCount;
/*     */     } finally {
/* 800 */       this.lock.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   @GuardedBy("lock")
/*     */   private void signalConditionsOfSatisfiedGuards(@Nullable Guard interruptedGuard) {
/* 806 */     ArrayList<Guard> guards = this.activeGuards;
/* 807 */     int guardCount = guards.size();
/*     */     try {
/* 809 */       for (int i = 0; i < guardCount; i++) {
/* 810 */         Guard guard = guards.get(i);
/* 811 */         if (guard != interruptedGuard || guard.waiterCount != 1)
/*     */         {
/*     */ 
/*     */ 
/*     */           
/* 816 */           if (guard.isSatisfied()) {
/* 817 */             guard.condition.signal();
/*     */             return;
/*     */           }  } 
/*     */       } 
/* 821 */     } catch (Throwable throwable) {
/* 822 */       for (int i = 0; i < guardCount; i++) {
/* 823 */         Guard guard = guards.get(i);
/* 824 */         guard.condition.signalAll();
/*     */       } 
/* 826 */       throw Throwables.propagate(throwable);
/*     */     } 
/*     */   }
/*     */   
/*     */   @GuardedBy("lock")
/*     */   private void incrementWaiters(Guard guard) {
/* 832 */     int waiters = guard.waiterCount++;
/* 833 */     if (waiters == 0) {
/* 834 */       this.activeGuards.add(guard);
/*     */     }
/*     */   }
/*     */   
/*     */   @GuardedBy("lock")
/*     */   private void decrementWaiters(Guard guard) {
/* 840 */     int waiters = --guard.waiterCount;
/* 841 */     if (waiters == 0) {
/* 842 */       this.activeGuards.remove(guard);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @GuardedBy("lock")
/*     */   private void waitInterruptibly(Guard guard, boolean signalBeforeWaiting) throws InterruptedException {
/* 849 */     if (!guard.isSatisfied()) {
/* 850 */       if (signalBeforeWaiting) {
/* 851 */         signalConditionsOfSatisfiedGuards(null);
/*     */       }
/* 853 */       incrementWaiters(guard);
/*     */       try {
/* 855 */         Condition condition = guard.condition;
/*     */         do {
/*     */           try {
/* 858 */             condition.await();
/* 859 */           } catch (InterruptedException interrupt) {
/*     */             try {
/* 861 */               signalConditionsOfSatisfiedGuards(guard);
/* 862 */             } catch (Throwable throwable) {
/* 863 */               Thread.currentThread().interrupt();
/* 864 */               throw Throwables.propagate(throwable);
/*     */             } 
/* 866 */             throw interrupt;
/*     */           } 
/* 868 */         } while (!guard.isSatisfied());
/*     */       } finally {
/* 870 */         decrementWaiters(guard);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @GuardedBy("lock")
/*     */   private void waitUninterruptibly(Guard guard, boolean signalBeforeWaiting) {
/* 877 */     if (!guard.isSatisfied()) {
/* 878 */       if (signalBeforeWaiting) {
/* 879 */         signalConditionsOfSatisfiedGuards(null);
/*     */       }
/* 881 */       incrementWaiters(guard);
/*     */       try {
/* 883 */         Condition condition = guard.condition;
/*     */         do {
/* 885 */           condition.awaitUninterruptibly();
/* 886 */         } while (!guard.isSatisfied());
/*     */       } finally {
/* 888 */         decrementWaiters(guard);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @GuardedBy("lock")
/*     */   private boolean waitInterruptibly(Guard guard, long remainingNanos, boolean signalBeforeWaiting) throws InterruptedException {
/* 896 */     if (!guard.isSatisfied()) {
/* 897 */       if (signalBeforeWaiting) {
/* 898 */         signalConditionsOfSatisfiedGuards(null);
/*     */       }
/* 900 */       incrementWaiters(guard);
/*     */       try {
/* 902 */         Condition condition = guard.condition;
/*     */         do {
/* 904 */           if (remainingNanos <= 0L) {
/* 905 */             return false;
/*     */           }
/*     */           try {
/* 908 */             remainingNanos = condition.awaitNanos(remainingNanos);
/* 909 */           } catch (InterruptedException interrupt) {
/*     */             try {
/* 911 */               signalConditionsOfSatisfiedGuards(guard);
/* 912 */             } catch (Throwable throwable) {
/* 913 */               Thread.currentThread().interrupt();
/* 914 */               throw Throwables.propagate(throwable);
/*     */             } 
/* 916 */             throw interrupt;
/*     */           } 
/* 918 */         } while (!guard.isSatisfied());
/*     */       } finally {
/* 920 */         decrementWaiters(guard);
/*     */       } 
/*     */     } 
/* 923 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   @GuardedBy("lock")
/*     */   private boolean waitUninterruptibly(Guard guard, long timeoutNanos, boolean signalBeforeWaiting) {
/* 929 */     if (!guard.isSatisfied()) {
/* 930 */       long startNanos = System.nanoTime();
/* 931 */       if (signalBeforeWaiting) {
/* 932 */         signalConditionsOfSatisfiedGuards(null);
/*     */       }
/* 934 */       boolean interruptIgnored = false;
/*     */       try {
/* 936 */         incrementWaiters(guard);
/*     */         try {
/* 938 */           Condition condition = guard.condition;
/* 939 */           long remainingNanos = timeoutNanos;
/*     */           do {
/* 941 */             if (remainingNanos <= 0L) {
/* 942 */               return false;
/*     */             }
/*     */             try {
/* 945 */               remainingNanos = condition.awaitNanos(remainingNanos);
/* 946 */             } catch (InterruptedException ignored) {
/*     */               try {
/* 948 */                 signalConditionsOfSatisfiedGuards(guard);
/* 949 */               } catch (Throwable throwable) {
/* 950 */                 Thread.currentThread().interrupt();
/* 951 */                 throw Throwables.propagate(throwable);
/*     */               } 
/* 953 */               interruptIgnored = true;
/* 954 */               remainingNanos = timeoutNanos - System.nanoTime() - startNanos;
/*     */             } 
/* 956 */           } while (!guard.isSatisfied());
/*     */         } finally {
/* 958 */           decrementWaiters(guard);
/*     */         } 
/*     */       } finally {
/* 961 */         if (interruptIgnored) {
/* 962 */           Thread.currentThread().interrupt();
/*     */         }
/*     */       } 
/*     */     } 
/* 966 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\commo\\util\concurrent\Monitor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
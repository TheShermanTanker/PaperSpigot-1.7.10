/*     */ package net.minecraft.util.io.netty.util.concurrent;
/*     */ 
/*     */ import java.util.concurrent.CancellationException;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import net.minecraft.util.io.netty.util.Signal;
/*     */ import net.minecraft.util.io.netty.util.internal.PlatformDependent;
/*     */ import net.minecraft.util.io.netty.util.internal.StringUtil;
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
/*     */ public class DefaultPromise<V>
/*     */   extends AbstractFuture<V>
/*     */   implements Promise<V>
/*     */ {
/*  32 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(DefaultPromise.class);
/*     */   
/*     */   private static final int MAX_LISTENER_STACK_DEPTH = 8;
/*     */   
/*  36 */   private static final ThreadLocal<Integer> LISTENER_STACK_DEPTH = new ThreadLocal<Integer>()
/*     */     {
/*     */       protected Integer initialValue() {
/*  39 */         return Integer.valueOf(0);
/*     */       }
/*     */     };
/*  42 */   private static final Signal SUCCESS = new Signal(DefaultPromise.class.getName() + ".SUCCESS");
/*  43 */   private static final Signal UNCANCELLABLE = new Signal(DefaultPromise.class.getName() + ".UNCANCELLABLE");
/*     */ 
/*     */   
/*     */   private final EventExecutor executor;
/*     */ 
/*     */   
/*     */   private volatile Object result;
/*     */ 
/*     */   
/*     */   private Object listeners;
/*     */ 
/*     */   
/*     */   private short waiters;
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultPromise(EventExecutor executor) {
/*  60 */     if (executor == null) {
/*  61 */       throw new NullPointerException("executor");
/*     */     }
/*  63 */     this.executor = executor;
/*     */   }
/*     */ 
/*     */   
/*     */   protected DefaultPromise() {
/*  68 */     this.executor = null;
/*     */   }
/*     */   
/*     */   protected EventExecutor executor() {
/*  72 */     return this.executor;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCancelled() {
/*  77 */     return isCancelled0(this.result);
/*     */   }
/*     */   
/*     */   private static boolean isCancelled0(Object result) {
/*  81 */     return (result instanceof CauseHolder && ((CauseHolder)result).cause instanceof CancellationException);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCancellable() {
/*  86 */     return (this.result == null);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDone() {
/*  91 */     return isDone0(this.result);
/*     */   }
/*     */   
/*     */   private static boolean isDone0(Object result) {
/*  95 */     return (result != null && result != UNCANCELLABLE);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSuccess() {
/* 100 */     Object result = this.result;
/* 101 */     if (result == null || result == UNCANCELLABLE) {
/* 102 */       return false;
/*     */     }
/* 104 */     return !(result instanceof CauseHolder);
/*     */   }
/*     */ 
/*     */   
/*     */   public Throwable cause() {
/* 109 */     Object result = this.result;
/* 110 */     if (result instanceof CauseHolder) {
/* 111 */       return ((CauseHolder)result).cause;
/*     */     }
/* 113 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Promise<V> addListener(GenericFutureListener<? extends Future<? super V>> listener) {
/* 118 */     if (listener == null) {
/* 119 */       throw new NullPointerException("listener");
/*     */     }
/*     */     
/* 122 */     if (isDone()) {
/* 123 */       notifyListener(executor(), this, listener);
/* 124 */       return this;
/*     */     } 
/*     */     
/* 127 */     synchronized (this) {
/* 128 */       if (!isDone()) {
/* 129 */         if (this.listeners == null) {
/* 130 */           this.listeners = listener;
/*     */         }
/* 132 */         else if (this.listeners instanceof DefaultFutureListeners) {
/* 133 */           ((DefaultFutureListeners)this.listeners).add(listener);
/*     */         } else {
/*     */           
/* 136 */           GenericFutureListener<? extends Future<V>> firstListener = (GenericFutureListener<? extends Future<V>>)this.listeners;
/*     */           
/* 138 */           this.listeners = new DefaultFutureListeners(firstListener, listener);
/*     */         } 
/*     */         
/* 141 */         return this;
/*     */       } 
/*     */     } 
/*     */     
/* 145 */     notifyListener(executor(), this, listener);
/* 146 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Promise<V> addListeners(GenericFutureListener<? extends Future<? super V>>... listeners) {
/* 151 */     if (listeners == null) {
/* 152 */       throw new NullPointerException("listeners");
/*     */     }
/*     */     
/* 155 */     for (GenericFutureListener<? extends Future<? super V>> l : listeners) {
/* 156 */       if (l == null) {
/*     */         break;
/*     */       }
/* 159 */       addListener(l);
/*     */     } 
/* 161 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Promise<V> removeListener(GenericFutureListener<? extends Future<? super V>> listener) {
/* 166 */     if (listener == null) {
/* 167 */       throw new NullPointerException("listener");
/*     */     }
/*     */     
/* 170 */     if (isDone()) {
/* 171 */       return this;
/*     */     }
/*     */     
/* 174 */     synchronized (this) {
/* 175 */       if (!isDone()) {
/* 176 */         if (this.listeners instanceof DefaultFutureListeners) {
/* 177 */           ((DefaultFutureListeners)this.listeners).remove(listener);
/* 178 */         } else if (this.listeners == listener) {
/* 179 */           this.listeners = null;
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 184 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Promise<V> removeListeners(GenericFutureListener<? extends Future<? super V>>... listeners) {
/* 189 */     if (listeners == null) {
/* 190 */       throw new NullPointerException("listeners");
/*     */     }
/*     */     
/* 193 */     for (GenericFutureListener<? extends Future<? super V>> l : listeners) {
/* 194 */       if (l == null) {
/*     */         break;
/*     */       }
/* 197 */       removeListener(l);
/*     */     } 
/* 199 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Promise<V> sync() throws InterruptedException {
/* 204 */     await();
/* 205 */     rethrowIfFailed();
/* 206 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Promise<V> syncUninterruptibly() {
/* 211 */     awaitUninterruptibly();
/* 212 */     rethrowIfFailed();
/* 213 */     return this;
/*     */   }
/*     */   
/*     */   private void rethrowIfFailed() {
/* 217 */     Throwable cause = cause();
/* 218 */     if (cause == null) {
/*     */       return;
/*     */     }
/*     */     
/* 222 */     PlatformDependent.throwException(cause);
/*     */   }
/*     */ 
/*     */   
/*     */   public Promise<V> await() throws InterruptedException {
/* 227 */     if (isDone()) {
/* 228 */       return this;
/*     */     }
/*     */     
/* 231 */     if (Thread.interrupted()) {
/* 232 */       throw new InterruptedException(toString());
/*     */     }
/*     */     
/* 235 */     synchronized (this) {
/* 236 */       while (!isDone()) {
/* 237 */         checkDeadLock();
/* 238 */         incWaiters();
/*     */         try {
/* 240 */           wait();
/*     */         } finally {
/* 242 */           decWaiters();
/*     */         } 
/*     */       } 
/*     */     } 
/* 246 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean await(long timeout, TimeUnit unit) throws InterruptedException {
/* 252 */     return await0(unit.toNanos(timeout), true);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean await(long timeoutMillis) throws InterruptedException {
/* 257 */     return await0(TimeUnit.MILLISECONDS.toNanos(timeoutMillis), true);
/*     */   }
/*     */ 
/*     */   
/*     */   public Promise<V> awaitUninterruptibly() {
/* 262 */     if (isDone()) {
/* 263 */       return this;
/*     */     }
/*     */     
/* 266 */     boolean interrupted = false;
/* 267 */     synchronized (this) {
/* 268 */       while (!isDone()) {
/* 269 */         checkDeadLock();
/* 270 */         incWaiters();
/*     */         try {
/* 272 */           wait();
/* 273 */         } catch (InterruptedException e) {
/* 274 */           interrupted = true;
/*     */         } finally {
/* 276 */           decWaiters();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 281 */     if (interrupted) {
/* 282 */       Thread.currentThread().interrupt();
/*     */     }
/*     */     
/* 285 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean awaitUninterruptibly(long timeout, TimeUnit unit) {
/*     */     try {
/* 291 */       return await0(unit.toNanos(timeout), false);
/* 292 */     } catch (InterruptedException e) {
/* 293 */       throw new InternalError();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean awaitUninterruptibly(long timeoutMillis) {
/*     */     try {
/* 300 */       return await0(TimeUnit.MILLISECONDS.toNanos(timeoutMillis), false);
/* 301 */     } catch (InterruptedException e) {
/* 302 */       throw new InternalError();
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean await0(long timeoutNanos, boolean interruptable) throws InterruptedException {
/* 307 */     if (isDone()) {
/* 308 */       return true;
/*     */     }
/*     */     
/* 311 */     if (timeoutNanos <= 0L) {
/* 312 */       return isDone();
/*     */     }
/*     */     
/* 315 */     if (interruptable && Thread.interrupted()) {
/* 316 */       throw new InterruptedException(toString());
/*     */     }
/*     */     
/* 319 */     long startTime = (timeoutNanos <= 0L) ? 0L : System.nanoTime();
/* 320 */     long waitTime = timeoutNanos;
/* 321 */     boolean interrupted = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*     */     
/*     */     } finally {
/* 361 */       if (interrupted) {
/* 362 */         Thread.currentThread().interrupt();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkDeadLock() {
/* 371 */     EventExecutor e = executor();
/* 372 */     if (e != null && e.inEventLoop()) {
/* 373 */       throw new BlockingOperationException(toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Promise<V> setSuccess(V result) {
/* 379 */     if (setSuccess0(result)) {
/* 380 */       notifyListeners();
/* 381 */       return this;
/*     */     } 
/* 383 */     throw new IllegalStateException("complete already: " + this);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean trySuccess(V result) {
/* 388 */     if (setSuccess0(result)) {
/* 389 */       notifyListeners();
/* 390 */       return true;
/*     */     } 
/* 392 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public Promise<V> setFailure(Throwable cause) {
/* 397 */     if (setFailure0(cause)) {
/* 398 */       notifyListeners();
/* 399 */       return this;
/*     */     } 
/* 401 */     throw new IllegalStateException("complete already: " + this, cause);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean tryFailure(Throwable cause) {
/* 406 */     if (setFailure0(cause)) {
/* 407 */       notifyListeners();
/* 408 */       return true;
/*     */     } 
/* 410 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean cancel(boolean mayInterruptIfRunning) {
/* 415 */     Object result = this.result;
/* 416 */     if (isDone0(result) || result == UNCANCELLABLE) {
/* 417 */       return false;
/*     */     }
/*     */     
/* 420 */     synchronized (this) {
/*     */       
/* 422 */       result = this.result;
/* 423 */       if (isDone0(result) || result == UNCANCELLABLE) {
/* 424 */         return false;
/*     */       }
/*     */       
/* 427 */       this.result = new CauseHolder(new CancellationException());
/* 428 */       if (hasWaiters()) {
/* 429 */         notifyAll();
/*     */       }
/*     */     } 
/*     */     
/* 433 */     notifyListeners();
/* 434 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean setUncancellable() {
/* 439 */     Object result = this.result;
/* 440 */     if (isDone0(result)) {
/* 441 */       return false;
/*     */     }
/*     */     
/* 444 */     synchronized (this) {
/*     */       
/* 446 */       result = this.result;
/* 447 */       if (isDone0(result)) {
/* 448 */         return false;
/*     */       }
/*     */       
/* 451 */       this.result = UNCANCELLABLE;
/*     */     } 
/* 453 */     return true;
/*     */   }
/*     */   
/*     */   private boolean setFailure0(Throwable cause) {
/* 457 */     if (isDone()) {
/* 458 */       return false;
/*     */     }
/*     */     
/* 461 */     synchronized (this) {
/*     */       
/* 463 */       if (isDone()) {
/* 464 */         return false;
/*     */       }
/*     */       
/* 467 */       this.result = new CauseHolder(cause);
/* 468 */       if (hasWaiters()) {
/* 469 */         notifyAll();
/*     */       }
/*     */     } 
/* 472 */     return true;
/*     */   }
/*     */   
/*     */   private boolean setSuccess0(V result) {
/* 476 */     if (isDone()) {
/* 477 */       return false;
/*     */     }
/*     */     
/* 480 */     synchronized (this) {
/*     */       
/* 482 */       if (isDone()) {
/* 483 */         return false;
/*     */       }
/* 485 */       if (result == null) {
/* 486 */         this.result = SUCCESS;
/*     */       } else {
/* 488 */         this.result = result;
/*     */       } 
/* 490 */       if (hasWaiters()) {
/* 491 */         notifyAll();
/*     */       }
/*     */     } 
/* 494 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public V getNow() {
/* 500 */     Object result = this.result;
/* 501 */     if (result instanceof CauseHolder || result == SUCCESS) {
/* 502 */       return null;
/*     */     }
/* 504 */     return (V)result;
/*     */   }
/*     */   
/*     */   private boolean hasWaiters() {
/* 508 */     return (this.waiters > 0);
/*     */   }
/*     */   
/*     */   private void incWaiters() {
/* 512 */     if (this.waiters == Short.MAX_VALUE) {
/* 513 */       throw new IllegalStateException("too many waiters: " + this);
/*     */     }
/* 515 */     this.waiters = (short)(this.waiters + 1);
/*     */   }
/*     */   
/*     */   private void decWaiters() {
/* 519 */     this.waiters = (short)(this.waiters - 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void notifyListeners() {
/* 529 */     Object listeners = this.listeners;
/* 530 */     if (listeners == null) {
/*     */       return;
/*     */     }
/*     */     
/* 534 */     this.listeners = null;
/*     */     
/* 536 */     EventExecutor executor = executor();
/* 537 */     if (executor.inEventLoop()) {
/* 538 */       Integer stackDepth = LISTENER_STACK_DEPTH.get();
/* 539 */       if (stackDepth.intValue() < 8) {
/* 540 */         LISTENER_STACK_DEPTH.set(Integer.valueOf(stackDepth.intValue() + 1));
/*     */         try {
/* 542 */           if (listeners instanceof DefaultFutureListeners) {
/* 543 */             notifyListeners0(this, (DefaultFutureListeners)listeners);
/*     */           } else {
/*     */             
/* 546 */             final GenericFutureListener<? extends Future<V>> l = (GenericFutureListener<? extends Future<V>>)listeners;
/*     */             
/* 548 */             notifyListener0(this, l);
/*     */           } 
/*     */         } finally {
/* 551 */           LISTENER_STACK_DEPTH.set(stackDepth);
/*     */         } 
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*     */     try {
/* 558 */       if (listeners instanceof DefaultFutureListeners) {
/* 559 */         final DefaultFutureListeners dfl = (DefaultFutureListeners)listeners;
/* 560 */         executor.execute(new Runnable()
/*     */             {
/*     */               public void run() {
/* 563 */                 DefaultPromise.notifyListeners0(DefaultPromise.this, dfl);
/*     */               }
/*     */             });
/*     */       } else {
/*     */         
/* 568 */         final GenericFutureListener<? extends Future<V>> l = (GenericFutureListener<? extends Future<V>>)listeners;
/*     */         
/* 570 */         executor.execute(new Runnable()
/*     */             {
/*     */               public void run() {
/* 573 */                 DefaultPromise.notifyListener0(DefaultPromise.this, l);
/*     */               }
/*     */             });
/*     */       } 
/* 577 */     } catch (Throwable t) {
/* 578 */       logger.error("Failed to notify listener(s). Event loop shut down?", t);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void notifyListeners0(Future<?> future, DefaultFutureListeners listeners) {
/* 583 */     GenericFutureListener[] arrayOfGenericFutureListener = (GenericFutureListener[])listeners.listeners();
/* 584 */     int size = listeners.size();
/* 585 */     for (int i = 0; i < size; i++) {
/* 586 */       notifyListener0(future, arrayOfGenericFutureListener[i]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void notifyListener(EventExecutor eventExecutor, final Future<?> future, final GenericFutureListener<?> l) {
/* 593 */     if (eventExecutor.inEventLoop()) {
/* 594 */       Integer stackDepth = LISTENER_STACK_DEPTH.get();
/* 595 */       if (stackDepth.intValue() < 8) {
/* 596 */         LISTENER_STACK_DEPTH.set(Integer.valueOf(stackDepth.intValue() + 1));
/*     */         try {
/* 598 */           notifyListener0(future, l);
/*     */         } finally {
/* 600 */           LISTENER_STACK_DEPTH.set(stackDepth);
/*     */         } 
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*     */     try {
/* 607 */       eventExecutor.execute(new Runnable()
/*     */           {
/*     */             public void run() {
/* 610 */               DefaultPromise.notifyListener0(future, l);
/*     */             }
/*     */           });
/* 613 */     } catch (Throwable t) {
/* 614 */       logger.error("Failed to notify a listener. Event loop shut down?", t);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static void notifyListener0(Future future, GenericFutureListener<Future> l) {
/*     */     try {
/* 621 */       l.operationComplete(future);
/* 622 */     } catch (Throwable t) {
/* 623 */       if (logger.isWarnEnabled()) {
/* 624 */         logger.warn("An exception was thrown by " + l.getClass().getName() + ".operationComplete()", t);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized Object progressiveListeners() {
/* 634 */     Object listeners = this.listeners;
/* 635 */     if (listeners == null)
/*     */     {
/* 637 */       return null;
/*     */     }
/*     */     
/* 640 */     if (listeners instanceof DefaultFutureListeners) {
/*     */       
/* 642 */       DefaultFutureListeners dfl = (DefaultFutureListeners)listeners;
/* 643 */       int progressiveSize = dfl.progressiveSize();
/* 644 */       switch (progressiveSize) {
/*     */         case 0:
/* 646 */           return null;
/*     */         case 1:
/* 648 */           for (GenericFutureListener<?> l : dfl.listeners()) {
/* 649 */             if (l instanceof GenericProgressiveFutureListener) {
/* 650 */               return l;
/*     */             }
/*     */           } 
/* 653 */           return null;
/*     */       } 
/*     */       
/* 656 */       GenericFutureListener[] arrayOfGenericFutureListener = (GenericFutureListener[])dfl.listeners();
/* 657 */       GenericProgressiveFutureListener[] arrayOfGenericProgressiveFutureListener = new GenericProgressiveFutureListener[progressiveSize];
/* 658 */       for (int i = 0, j = 0; j < progressiveSize; i++) {
/* 659 */         GenericFutureListener<?> l = arrayOfGenericFutureListener[i];
/* 660 */         if (l instanceof GenericProgressiveFutureListener) {
/* 661 */           arrayOfGenericProgressiveFutureListener[j++] = (GenericProgressiveFutureListener)l;
/*     */         }
/*     */       } 
/*     */       
/* 665 */       return arrayOfGenericProgressiveFutureListener;
/* 666 */     }  if (listeners instanceof GenericProgressiveFutureListener) {
/* 667 */       return listeners;
/*     */     }
/*     */     
/* 670 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void notifyProgressiveListeners(final long progress, final long total) {
/* 676 */     Object listeners = progressiveListeners();
/* 677 */     if (listeners == null) {
/*     */       return;
/*     */     }
/*     */     
/* 681 */     final ProgressiveFuture<V> self = (ProgressiveFuture<V>)this;
/*     */     
/* 683 */     EventExecutor executor = executor();
/* 684 */     if (executor.inEventLoop()) {
/* 685 */       if (listeners instanceof GenericProgressiveFutureListener[]) {
/* 686 */         notifyProgressiveListeners0(self, (GenericProgressiveFutureListener<?>[])listeners, progress, total);
/*     */       } else {
/*     */         
/* 689 */         notifyProgressiveListener0(self, (GenericProgressiveFutureListener)listeners, progress, total);
/*     */       } 
/*     */     } else {
/*     */       
/*     */       try {
/* 694 */         if (listeners instanceof GenericProgressiveFutureListener[]) {
/* 695 */           final GenericProgressiveFutureListener[] array = (GenericProgressiveFutureListener[])listeners;
/*     */           
/* 697 */           executor.execute(new Runnable()
/*     */               {
/*     */                 public void run() {
/* 700 */                   DefaultPromise.notifyProgressiveListeners0(self, (GenericProgressiveFutureListener<?>[])array, progress, total);
/*     */                 }
/*     */               });
/*     */         } else {
/* 704 */           final GenericProgressiveFutureListener<ProgressiveFuture<V>> l = (GenericProgressiveFutureListener<ProgressiveFuture<V>>)listeners;
/*     */           
/* 706 */           executor.execute(new Runnable()
/*     */               {
/*     */                 public void run() {
/* 709 */                   DefaultPromise.notifyProgressiveListener0(self, l, progress, total);
/*     */                 }
/*     */               });
/*     */         } 
/* 713 */       } catch (Throwable t) {
/* 714 */         logger.error("Failed to notify listener(s). Event loop shut down?", t);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void notifyProgressiveListeners0(ProgressiveFuture<?> future, GenericProgressiveFutureListener<?>[] listeners, long progress, long total) {
/* 721 */     for (GenericProgressiveFutureListener<?> l : listeners) {
/* 722 */       if (l == null) {
/*     */         break;
/*     */       }
/* 725 */       notifyProgressiveListener0(future, l, progress, total);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void notifyProgressiveListener0(ProgressiveFuture future, GenericProgressiveFutureListener<ProgressiveFuture> l, long progress, long total) {
/*     */     try {
/* 733 */       l.operationProgressed(future, progress, total);
/* 734 */     } catch (Throwable t) {
/* 735 */       if (logger.isWarnEnabled())
/* 736 */         logger.warn("An exception was thrown by " + l.getClass().getName() + ".operationProgressed()", t); 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static final class CauseHolder {
/*     */     final Throwable cause;
/*     */     
/*     */     private CauseHolder(Throwable cause) {
/* 744 */       this.cause = cause;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 750 */     return toStringBuilder().toString();
/*     */   }
/*     */   
/*     */   protected StringBuilder toStringBuilder() {
/* 754 */     StringBuilder buf = new StringBuilder(64);
/* 755 */     buf.append(StringUtil.simpleClassName(this));
/* 756 */     buf.append('@');
/* 757 */     buf.append(Integer.toHexString(hashCode()));
/*     */     
/* 759 */     Object result = this.result;
/* 760 */     if (result == SUCCESS) {
/* 761 */       buf.append("(success)");
/* 762 */     } else if (result == UNCANCELLABLE) {
/* 763 */       buf.append("(uncancellable)");
/* 764 */     } else if (result instanceof CauseHolder) {
/* 765 */       buf.append("(failure(");
/* 766 */       buf.append(((CauseHolder)result).cause);
/* 767 */       buf.append(')');
/*     */     } else {
/* 769 */       buf.append("(incomplete)");
/*     */     } 
/* 771 */     return buf;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\nett\\util\concurrent\DefaultPromise.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
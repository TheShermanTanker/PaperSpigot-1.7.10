/*     */ package com.google.common.util.concurrent;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.util.concurrent.CancellationException;
/*     */ import java.util.concurrent.ExecutionException;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.TimeoutException;
/*     */ import java.util.concurrent.locks.AbstractQueuedSynchronizer;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractFuture<V>
/*     */   implements ListenableFuture<V>
/*     */ {
/*  70 */   private final Sync<V> sync = new Sync<V>();
/*     */ 
/*     */   
/*  73 */   private final ExecutionList executionList = new ExecutionList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public V get(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException, ExecutionException {
/*  93 */     return this.sync.get(unit.toNanos(timeout));
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
/*     */   
/*     */   public V get() throws InterruptedException, ExecutionException {
/* 113 */     return this.sync.get();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDone() {
/* 118 */     return this.sync.isDone();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCancelled() {
/* 123 */     return this.sync.isCancelled();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean cancel(boolean mayInterruptIfRunning) {
/* 128 */     if (!this.sync.cancel()) {
/* 129 */       return false;
/*     */     }
/* 131 */     done();
/* 132 */     if (mayInterruptIfRunning) {
/* 133 */       interruptTask();
/*     */     }
/* 135 */     return true;
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
/*     */   protected void interruptTask() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addListener(Runnable listener, Executor exec) {
/* 157 */     this.executionList.add(listener, exec);
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
/*     */   protected boolean set(@Nullable V value) {
/* 170 */     boolean result = this.sync.set(value);
/* 171 */     if (result) {
/* 172 */       done();
/*     */     }
/* 174 */     return result;
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
/*     */   protected boolean setException(Throwable throwable) {
/* 188 */     boolean result = this.sync.setException((Throwable)Preconditions.checkNotNull(throwable));
/* 189 */     if (result) {
/* 190 */       done();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 195 */     if (throwable instanceof Error) {
/* 196 */       throw (Error)throwable;
/*     */     }
/* 198 */     return result;
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
/*     */   @Deprecated
/*     */   @Beta
/*     */   protected final boolean cancel() {
/* 217 */     boolean result = this.sync.cancel();
/* 218 */     if (result) {
/* 219 */       done();
/*     */     }
/* 221 */     return result;
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
/*     */   @Deprecated
/*     */   @Beta
/*     */   protected void done() {
/* 235 */     this.executionList.execute();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final class Sync<V>
/*     */     extends AbstractQueuedSynchronizer
/*     */   {
/*     */     private static final long serialVersionUID = 0L;
/*     */ 
/*     */ 
/*     */     
/*     */     static final int RUNNING = 0;
/*     */ 
/*     */ 
/*     */     
/*     */     static final int COMPLETING = 1;
/*     */ 
/*     */ 
/*     */     
/*     */     static final int COMPLETED = 2;
/*     */ 
/*     */ 
/*     */     
/*     */     static final int CANCELLED = 4;
/*     */ 
/*     */ 
/*     */     
/*     */     private V value;
/*     */ 
/*     */     
/*     */     private Throwable exception;
/*     */ 
/*     */ 
/*     */     
/*     */     protected int tryAcquireShared(int ignored) {
/* 272 */       if (isDone()) {
/* 273 */         return 1;
/*     */       }
/* 275 */       return -1;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected boolean tryReleaseShared(int finalState) {
/* 284 */       setState(finalState);
/* 285 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     V get(long nanos) throws TimeoutException, CancellationException, ExecutionException, InterruptedException {
/* 297 */       if (!tryAcquireSharedNanos(-1, nanos)) {
/* 298 */         throw new TimeoutException("Timeout waiting for task.");
/*     */       }
/*     */       
/* 301 */       return getValue();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     V get() throws CancellationException, ExecutionException, InterruptedException {
/* 314 */       acquireSharedInterruptibly(-1);
/* 315 */       return getValue();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private V getValue() throws CancellationException, ExecutionException {
/* 324 */       int state = getState();
/* 325 */       switch (state) {
/*     */         case 2:
/* 327 */           if (this.exception != null) {
/* 328 */             throw new ExecutionException(this.exception);
/*     */           }
/* 330 */           return this.value;
/*     */ 
/*     */         
/*     */         case 4:
/* 334 */           throw new CancellationException("Task was cancelled.");
/*     */       } 
/*     */       
/* 337 */       throw new IllegalStateException("Error, synchronizer in invalid state: " + state);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     boolean isDone() {
/* 346 */       return ((getState() & 0x6) != 0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     boolean isCancelled() {
/* 353 */       return (getState() == 4);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     boolean set(@Nullable V v) {
/* 360 */       return complete(v, (Throwable)null, 2);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     boolean setException(Throwable t) {
/* 367 */       return complete((V)null, t, 2);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     boolean cancel() {
/* 374 */       return complete((V)null, (Throwable)null, 4);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean complete(@Nullable V v, Throwable t, int finalState) {
/* 388 */       if (compareAndSetState(0, 1)) {
/* 389 */         this.value = v;
/* 390 */         this.exception = t;
/* 391 */         releaseShared(finalState);
/* 392 */         return true;
/*     */       } 
/*     */ 
/*     */       
/* 396 */       return false;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\commo\\util\concurrent\AbstractFuture.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
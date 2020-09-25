/*     */ package com.google.common.util.concurrent;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public final class JdkFutureAdapters
/*     */ {
/*     */   public static <V> ListenableFuture<V> listenInPoolThread(Future<V> future) {
/*  60 */     if (future instanceof ListenableFuture) {
/*  61 */       return (ListenableFuture<V>)future;
/*     */     }
/*  63 */     return new ListenableFutureAdapter<V>(future);
/*     */   }
/*     */ 
/*     */   
/*     */   @VisibleForTesting
/*     */   static <V> ListenableFuture<V> listenInPoolThread(Future<V> future, Executor executor) {
/*  69 */     Preconditions.checkNotNull(executor);
/*  70 */     if (future instanceof ListenableFuture) {
/*  71 */       return (ListenableFuture<V>)future;
/*     */     }
/*  73 */     return new ListenableFutureAdapter<V>(future, executor);
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
/*     */   private static class ListenableFutureAdapter<V>
/*     */     extends ForwardingFuture<V>
/*     */     implements ListenableFuture<V>
/*     */   {
/*  89 */     private static final ThreadFactory threadFactory = (new ThreadFactoryBuilder()).setNameFormat("ListenableFutureAdapter-thread-%d").build();
/*     */ 
/*     */ 
/*     */     
/*  93 */     private static final Executor defaultAdapterExecutor = Executors.newCachedThreadPool(threadFactory);
/*     */ 
/*     */     
/*     */     private final Executor adapterExecutor;
/*     */ 
/*     */     
/*  99 */     private final ExecutionList executionList = new ExecutionList();
/*     */ 
/*     */ 
/*     */     
/* 103 */     private final AtomicBoolean hasListeners = new AtomicBoolean(false);
/*     */     
/*     */     private final Future<V> delegate;
/*     */ 
/*     */     
/*     */     ListenableFutureAdapter(Future<V> delegate) {
/* 109 */       this(delegate, defaultAdapterExecutor);
/*     */     }
/*     */     
/*     */     ListenableFutureAdapter(Future<V> delegate, Executor adapterExecutor) {
/* 113 */       this.delegate = (Future<V>)Preconditions.checkNotNull(delegate);
/* 114 */       this.adapterExecutor = (Executor)Preconditions.checkNotNull(adapterExecutor);
/*     */     }
/*     */ 
/*     */     
/*     */     protected Future<V> delegate() {
/* 119 */       return this.delegate;
/*     */     }
/*     */ 
/*     */     
/*     */     public void addListener(Runnable listener, Executor exec) {
/* 124 */       this.executionList.add(listener, exec);
/*     */ 
/*     */ 
/*     */       
/* 128 */       if (this.hasListeners.compareAndSet(false, true)) {
/* 129 */         if (this.delegate.isDone()) {
/*     */ 
/*     */           
/* 132 */           this.executionList.execute();
/*     */           
/*     */           return;
/*     */         } 
/* 136 */         this.adapterExecutor.execute(new Runnable()
/*     */             {
/*     */               public void run() {
/*     */                 try {
/* 140 */                   JdkFutureAdapters.ListenableFutureAdapter.this.delegate.get();
/* 141 */                 } catch (Error e) {
/* 142 */                   throw e;
/* 143 */                 } catch (InterruptedException e) {
/* 144 */                   Thread.currentThread().interrupt();
/*     */                   
/* 146 */                   throw new AssertionError(e);
/* 147 */                 } catch (Throwable e) {}
/*     */ 
/*     */ 
/*     */                 
/* 151 */                 JdkFutureAdapters.ListenableFutureAdapter.this.executionList.execute();
/*     */               }
/*     */             });
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\commo\\util\concurrent\JdkFutureAdapters.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
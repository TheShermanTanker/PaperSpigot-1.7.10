/*     */ package net.minecraft.util.com.google.common.util.concurrent;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.ExecutionException;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.TimeoutException;
/*     */ import net.minecraft.util.com.google.common.base.Preconditions;
/*     */ import net.minecraft.util.com.google.common.base.Throwables;
/*     */ import net.minecraft.util.com.google.common.collect.ImmutableList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class WrappingExecutorService
/*     */   implements ExecutorService
/*     */ {
/*     */   private final ExecutorService delegate;
/*     */   
/*     */   protected WrappingExecutorService(ExecutorService delegate) {
/*  50 */     this.delegate = (ExecutorService)Preconditions.checkNotNull(delegate);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract <T> Callable<T> wrapTask(Callable<T> paramCallable);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Runnable wrapTask(Runnable command) {
/*  67 */     final Callable<Object> wrapped = wrapTask(Executors.callable(command, null));
/*     */     
/*  69 */     return new Runnable() {
/*     */         public void run() {
/*     */           try {
/*  72 */             wrapped.call();
/*  73 */           } catch (Exception e) {
/*  74 */             Throwables.propagate(e);
/*     */           } 
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final <T> ImmutableList<Callable<T>> wrapTasks(Collection<? extends Callable<T>> tasks) {
/*  87 */     ImmutableList.Builder<Callable<T>> builder = ImmutableList.builder();
/*  88 */     for (Callable<T> task : tasks) {
/*  89 */       builder.add(wrapTask(task));
/*     */     }
/*  91 */     return builder.build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void execute(Runnable command) {
/*  97 */     this.delegate.execute(wrapTask(command));
/*     */   }
/*     */ 
/*     */   
/*     */   public final <T> Future<T> submit(Callable<T> task) {
/* 102 */     return this.delegate.submit(wrapTask((Callable<T>)Preconditions.checkNotNull(task)));
/*     */   }
/*     */ 
/*     */   
/*     */   public final Future<?> submit(Runnable task) {
/* 107 */     return this.delegate.submit(wrapTask(task));
/*     */   }
/*     */ 
/*     */   
/*     */   public final <T> Future<T> submit(Runnable task, T result) {
/* 112 */     return this.delegate.submit(wrapTask(task), result);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
/* 118 */     return this.delegate.invokeAll((Collection<? extends Callable<T>>)wrapTasks(tasks));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
/* 125 */     return this.delegate.invokeAll((Collection<? extends Callable<T>>)wrapTasks(tasks), timeout, unit);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
/* 131 */     return this.delegate.invokeAny((Collection<? extends Callable<T>>)wrapTasks(tasks));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
/* 138 */     return this.delegate.invokeAny((Collection<? extends Callable<T>>)wrapTasks(tasks), timeout, unit);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void shutdown() {
/* 145 */     this.delegate.shutdown();
/*     */   }
/*     */ 
/*     */   
/*     */   public final List<Runnable> shutdownNow() {
/* 150 */     return this.delegate.shutdownNow();
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean isShutdown() {
/* 155 */     return this.delegate.isShutdown();
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean isTerminated() {
/* 160 */     return this.delegate.isTerminated();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
/* 166 */     return this.delegate.awaitTermination(timeout, unit);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\commo\\util\concurrent\WrappingExecutorService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
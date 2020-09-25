/*     */ package com.google.common.util.concurrent;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.FutureTask;
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
/*     */ public final class ListenableFutureTask<V>
/*     */   extends FutureTask<V>
/*     */   implements ListenableFuture<V>
/*     */ {
/*  40 */   private final ExecutionList executionList = new ExecutionList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <V> ListenableFutureTask<V> create(Callable<V> callable) {
/*  50 */     return new ListenableFutureTask<V>(callable);
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
/*     */   public static <V> ListenableFutureTask<V> create(Runnable runnable, @Nullable V result) {
/*  67 */     return new ListenableFutureTask<V>(runnable, result);
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
/*     */   public ListenableFutureTask(Callable<V> callable) {
/*  81 */     super(callable);
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
/*     */   @Deprecated
/*     */   @Beta
/*     */   public ListenableFutureTask(Runnable runnable, @Nullable V result) {
/* 101 */     super(runnable, result);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addListener(Runnable listener, Executor exec) {
/* 106 */     this.executionList.add(listener, exec);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void done() {
/* 111 */     this.executionList.execute();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\commo\\util\concurrent\ListenableFutureTask.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.google.common.util.concurrent;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ExecutionList
/*     */ {
/*  49 */   private static final Logger log = Logger.getLogger(ExecutionList.class.getName());
/*     */ 
/*     */ 
/*     */   
/*  53 */   private final Queue<RunnableExecutorPair> runnables = Lists.newLinkedList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean executed = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(Runnable runnable, Executor executor) {
/*  84 */     Preconditions.checkNotNull(runnable, "Runnable was null.");
/*  85 */     Preconditions.checkNotNull(executor, "Executor was null.");
/*     */     
/*  87 */     boolean executeImmediate = false;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  92 */     synchronized (this.runnables) {
/*  93 */       if (!this.executed) {
/*  94 */         this.runnables.add(new RunnableExecutorPair(runnable, executor));
/*     */       } else {
/*  96 */         executeImmediate = true;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 104 */     if (executeImmediate) {
/* 105 */       (new RunnableExecutorPair(runnable, executor)).execute();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   @Beta
/*     */   public void run() {
/* 118 */     execute();
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
/*     */   public void execute() {
/* 136 */     synchronized (this.runnables) {
/* 137 */       if (this.executed) {
/*     */         return;
/*     */       }
/* 140 */       this.executed = true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 145 */     while (!this.runnables.isEmpty())
/* 146 */       ((RunnableExecutorPair)this.runnables.poll()).execute(); 
/*     */   }
/*     */   
/*     */   private static class RunnableExecutorPair
/*     */   {
/*     */     final Runnable runnable;
/*     */     final Executor executor;
/*     */     
/*     */     RunnableExecutorPair(Runnable runnable, Executor executor) {
/* 155 */       this.runnable = runnable;
/* 156 */       this.executor = executor;
/*     */     }
/*     */     
/*     */     void execute() {
/*     */       try {
/* 161 */         this.executor.execute(this.runnable);
/* 162 */       } catch (RuntimeException e) {
/*     */ 
/*     */ 
/*     */         
/* 166 */         ExecutionList.log.log(Level.SEVERE, "RuntimeException while executing runnable " + this.runnable + " with executor " + this.executor, e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\commo\\util\concurrent\ExecutionList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
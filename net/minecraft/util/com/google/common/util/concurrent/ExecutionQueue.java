/*     */ package net.minecraft.util.com.google.common.util.concurrent;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.concurrent.ConcurrentLinkedQueue;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.annotation.concurrent.GuardedBy;
/*     */ import javax.annotation.concurrent.ThreadSafe;
/*     */ import net.minecraft.util.com.google.common.base.Preconditions;
/*     */ import net.minecraft.util.com.google.common.collect.Queues;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @ThreadSafe
/*     */ final class ExecutionQueue
/*     */ {
/*  55 */   private static final Logger logger = Logger.getLogger(ExecutionQueue.class.getName());
/*     */ 
/*     */   
/*  58 */   private final ConcurrentLinkedQueue<RunnableExecutorPair> queuedListeners = Queues.newConcurrentLinkedQueue();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  64 */   private final ReentrantLock lock = new ReentrantLock();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void add(Runnable runnable, Executor executor) {
/*  71 */     this.queuedListeners.add(new RunnableExecutorPair(runnable, executor));
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
/*     */   void execute() {
/*  88 */     Iterator<RunnableExecutorPair> iterator = this.queuedListeners.iterator();
/*  89 */     while (iterator.hasNext()) {
/*  90 */       ((RunnableExecutorPair)iterator.next()).submit();
/*  91 */       iterator.remove();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final class RunnableExecutorPair
/*     */     implements Runnable
/*     */   {
/*     */     private final Executor executor;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final Runnable runnable;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @GuardedBy("lock")
/*     */     private boolean hasBeenExecuted = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     RunnableExecutorPair(Runnable runnable, Executor executor) {
/* 120 */       this.runnable = (Runnable)Preconditions.checkNotNull(runnable);
/* 121 */       this.executor = (Executor)Preconditions.checkNotNull(executor);
/*     */     }
/*     */ 
/*     */     
/*     */     private void submit() {
/* 126 */       ExecutionQueue.this.lock.lock();
/*     */       try {
/* 128 */         if (!this.hasBeenExecuted) {
/*     */           try {
/* 130 */             this.executor.execute(this);
/* 131 */           } catch (Exception e) {
/* 132 */             ExecutionQueue.logger.log(Level.SEVERE, "Exception while executing listener " + this.runnable + " with executor " + this.executor, e);
/*     */           }
/*     */         
/*     */         }
/*     */       }
/*     */       finally {
/*     */         
/* 139 */         if (ExecutionQueue.this.lock.isHeldByCurrentThread()) {
/* 140 */           this.hasBeenExecuted = true;
/* 141 */           ExecutionQueue.this.lock.unlock();
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final void run() {
/* 150 */       if (ExecutionQueue.this.lock.isHeldByCurrentThread()) {
/* 151 */         this.hasBeenExecuted = true;
/* 152 */         ExecutionQueue.this.lock.unlock();
/*     */       } 
/* 154 */       this.runnable.run();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\commo\\util\concurrent\ExecutionQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
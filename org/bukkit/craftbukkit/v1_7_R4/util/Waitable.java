/*    */ package org.bukkit.craftbukkit.v1_7_R4.util;
/*    */ 
/*    */ import java.util.concurrent.ExecutionException;
/*    */ 
/*    */ public abstract class Waitable<T>
/*    */   implements Runnable {
/*    */   private enum Status {
/*  8 */     WAITING,
/*  9 */     RUNNING,
/* 10 */     FINISHED;
/*    */   }
/* 12 */   Throwable t = null;
/* 13 */   T value = null;
/* 14 */   Status status = Status.WAITING;
/*    */   
/*    */   public final void run() {
/* 17 */     synchronized (this) {
/* 18 */       if (this.status != Status.WAITING) {
/* 19 */         throw new IllegalStateException("Invalid state " + this.status);
/*    */       }
/* 21 */       this.status = Status.RUNNING;
/*    */     } 
/*    */     try {
/* 24 */       this.value = evaluate();
/* 25 */     } catch (Throwable t) {
/* 26 */       this.t = t;
/*    */     } finally {
/* 28 */       synchronized (this) {
/* 29 */         this.status = Status.FINISHED;
/* 30 */         notifyAll();
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   protected abstract T evaluate();
/*    */   
/*    */   public synchronized T get() throws InterruptedException, ExecutionException {
/* 38 */     while (this.status != Status.FINISHED) {
/* 39 */       wait();
/*    */     }
/* 41 */     if (this.t != null) {
/* 42 */       throw new ExecutionException(this.t);
/*    */     }
/* 44 */     return this.value;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R\\util\Waitable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
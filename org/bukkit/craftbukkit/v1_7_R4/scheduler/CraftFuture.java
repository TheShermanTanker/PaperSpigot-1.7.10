/*     */ package org.bukkit.craftbukkit.v1_7_R4.scheduler;
/*     */ 
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.CancellationException;
/*     */ import java.util.concurrent.ExecutionException;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.TimeoutException;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ class CraftFuture<T>
/*     */   extends CraftTask
/*     */   implements Future<T> {
/*     */   private final Callable<T> callable;
/*     */   private T value;
/*  16 */   private Exception exception = null;
/*     */   
/*     */   CraftFuture(Callable<T> callable, Plugin plugin, int id) {
/*  19 */     super(plugin, null, id, -1L);
/*  20 */     this.callable = callable;
/*     */   }
/*     */   
/*     */   public synchronized boolean cancel(boolean mayInterruptIfRunning) {
/*  24 */     if (getPeriod() != -1L) {
/*  25 */       return false;
/*     */     }
/*  27 */     setPeriod(-2L);
/*  28 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isCancelled() {
/*  32 */     return (getPeriod() == -2L);
/*     */   }
/*     */   
/*     */   public boolean isDone() {
/*  36 */     long period = getPeriod();
/*  37 */     return (period != -1L && period != -3L);
/*     */   }
/*     */   
/*     */   public T get() throws CancellationException, InterruptedException, ExecutionException {
/*     */     try {
/*  42 */       return get(0L, TimeUnit.MILLISECONDS);
/*  43 */     } catch (TimeoutException e) {
/*  44 */       throw new Error(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public synchronized T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
/*  49 */     timeout = unit.toMillis(timeout);
/*  50 */     long period = getPeriod();
/*  51 */     long timestamp = (timeout > 0L) ? System.currentTimeMillis() : 0L;
/*     */     
/*  53 */     while (period == -1L || period == -3L) {
/*  54 */       wait(timeout);
/*  55 */       period = getPeriod();
/*  56 */       if (period == -1L || period == -3L) {
/*  57 */         if (timeout == 0L) {
/*     */           continue;
/*     */         }
/*  60 */         timeout += timestamp - (timestamp = System.currentTimeMillis());
/*  61 */         if (timeout > 0L) {
/*     */           continue;
/*     */         }
/*  64 */         throw new TimeoutException();
/*     */       } 
/*     */     } 
/*  67 */     if (period == -2L) {
/*  68 */       throw new CancellationException();
/*     */     }
/*  70 */     if (period == -4L) {
/*  71 */       if (this.exception == null) {
/*  72 */         return this.value;
/*     */       }
/*  74 */       throw new ExecutionException(this.exception);
/*     */     } 
/*  76 */     throw new IllegalStateException("Expected -1 to -4, got " + period);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() {
/*  82 */     synchronized (this) {
/*  83 */       if (getPeriod() == -2L) {
/*     */         return;
/*     */       }
/*  86 */       setPeriod(-3L);
/*     */     } 
/*     */     try {
/*  89 */       this.value = this.callable.call();
/*  90 */     } catch (Exception e) {
/*  91 */       this.exception = e;
/*     */     } finally {
/*  93 */       synchronized (this) {
/*  94 */         setPeriod(-4L);
/*  95 */         notifyAll();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   synchronized boolean cancel0() {
/* 101 */     if (getPeriod() != -1L) {
/* 102 */       return false;
/*     */     }
/* 104 */     setPeriod(-2L);
/* 105 */     notifyAll();
/* 106 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\scheduler\CraftFuture.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
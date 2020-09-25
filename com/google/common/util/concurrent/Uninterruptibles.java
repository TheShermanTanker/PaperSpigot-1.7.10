/*     */ package com.google.common.util.concurrent;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.util.concurrent.BlockingQueue;
/*     */ import java.util.concurrent.CountDownLatch;
/*     */ import java.util.concurrent.ExecutionException;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.TimeoutException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public final class Uninterruptibles
/*     */ {
/*     */   public static void awaitUninterruptibly(CountDownLatch latch) {
/*  51 */     boolean interrupted = false;
/*     */     
/*     */     while (true) {
/*     */       try {
/*  55 */         latch.await();
/*     */         return;
/*  57 */       } catch (InterruptedException e) {
/*     */ 
/*     */       
/*     */       } finally {
/*     */         
/*  62 */         if (interrupted) {
/*  63 */           Thread.currentThread().interrupt();
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean awaitUninterruptibly(CountDownLatch latch, long timeout, TimeUnit unit) {
/*  75 */     boolean interrupted = false;
/*     */     try {
/*  77 */       long remainingNanos = unit.toNanos(timeout);
/*  78 */       long end = System.nanoTime() + remainingNanos;
/*     */ 
/*     */       
/*     */       while (true) {
/*     */         try {
/*  83 */           return latch.await(remainingNanos, TimeUnit.NANOSECONDS);
/*  84 */         } catch (InterruptedException e) {
/*  85 */           interrupted = true;
/*  86 */           remainingNanos = end - System.nanoTime();
/*     */         } 
/*     */       } 
/*     */     } finally {
/*  90 */       if (interrupted) {
/*  91 */         Thread.currentThread().interrupt();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void joinUninterruptibly(Thread toJoin) {
/* 100 */     boolean interrupted = false;
/*     */     
/*     */     while (true) {
/*     */       try {
/* 104 */         toJoin.join();
/*     */         return;
/* 106 */       } catch (InterruptedException e) {
/*     */ 
/*     */       
/*     */       } finally {
/*     */         
/* 111 */         if (interrupted) {
/* 112 */           Thread.currentThread().interrupt();
/*     */         }
/*     */       } 
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
/*     */   public static <V> V getUninterruptibly(Future<V> future) throws ExecutionException {
/* 128 */     boolean interrupted = false;
/*     */     
/*     */     while (true) {
/*     */       try {
/* 132 */         return future.get();
/* 133 */       } catch (InterruptedException e) {
/*     */ 
/*     */       
/*     */       } finally {
/*     */         
/* 138 */         if (interrupted) {
/* 139 */           Thread.currentThread().interrupt();
/*     */         }
/*     */       } 
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
/*     */   public static <V> V getUninterruptibly(Future<V> future, long timeout, TimeUnit unit) throws ExecutionException, TimeoutException {
/* 156 */     boolean interrupted = false;
/*     */     try {
/* 158 */       long remainingNanos = unit.toNanos(timeout);
/* 159 */       long end = System.nanoTime() + remainingNanos;
/*     */ 
/*     */       
/*     */       while (true) {
/*     */         try {
/* 164 */           return future.get(remainingNanos, TimeUnit.NANOSECONDS);
/* 165 */         } catch (InterruptedException e) {
/* 166 */           interrupted = true;
/* 167 */           remainingNanos = end - System.nanoTime();
/*     */         } 
/*     */       } 
/*     */     } finally {
/* 171 */       if (interrupted) {
/* 172 */         Thread.currentThread().interrupt();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void joinUninterruptibly(Thread toJoin, long timeout, TimeUnit unit) {
/* 184 */     Preconditions.checkNotNull(toJoin);
/* 185 */     boolean interrupted = false;
/*     */     try {
/* 187 */       long remainingNanos = unit.toNanos(timeout);
/* 188 */       long end = System.nanoTime() + remainingNanos;
/*     */       
/*     */       while (true) {
/*     */         try {
/* 192 */           TimeUnit.NANOSECONDS.timedJoin(toJoin, remainingNanos);
/*     */           return;
/* 194 */         } catch (InterruptedException e) {
/* 195 */           interrupted = true;
/* 196 */           remainingNanos = end - System.nanoTime();
/*     */         } 
/*     */       } 
/*     */     } finally {
/* 200 */       if (interrupted) {
/* 201 */         Thread.currentThread().interrupt();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E> E takeUninterruptibly(BlockingQueue<E> queue) {
/* 210 */     boolean interrupted = false;
/*     */     
/*     */     while (true) {
/*     */       try {
/* 214 */         return queue.take();
/* 215 */       } catch (InterruptedException e) {
/*     */ 
/*     */       
/*     */       } finally {
/*     */         
/* 220 */         if (interrupted) {
/* 221 */           Thread.currentThread().interrupt();
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E> void putUninterruptibly(BlockingQueue<E> queue, E element) {
/* 231 */     boolean interrupted = false;
/*     */     
/*     */     while (true) {
/*     */       try {
/* 235 */         queue.put(element);
/*     */         return;
/* 237 */       } catch (InterruptedException e) {
/*     */ 
/*     */       
/*     */       } finally {
/*     */         
/* 242 */         if (interrupted) {
/* 243 */           Thread.currentThread().interrupt();
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void sleepUninterruptibly(long sleepFor, TimeUnit unit) {
/* 254 */     boolean interrupted = false;
/*     */     try {
/* 256 */       long remainingNanos = unit.toNanos(sleepFor);
/* 257 */       long end = System.nanoTime() + remainingNanos;
/*     */       
/*     */       while (true) {
/*     */         try {
/* 261 */           TimeUnit.NANOSECONDS.sleep(remainingNanos);
/*     */           return;
/* 263 */         } catch (InterruptedException e) {
/* 264 */           interrupted = true;
/* 265 */           remainingNanos = end - System.nanoTime();
/*     */         } 
/*     */       } 
/*     */     } finally {
/* 269 */       if (interrupted)
/* 270 */         Thread.currentThread().interrupt(); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\commo\\util\concurrent\Uninterruptibles.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
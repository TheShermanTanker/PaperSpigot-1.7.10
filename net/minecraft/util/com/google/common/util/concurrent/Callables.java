/*     */ package net.minecraft.util.com.google.common.util.concurrent;
/*     */ 
/*     */ import java.util.concurrent.Callable;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.util.com.google.common.base.Preconditions;
/*     */ import net.minecraft.util.com.google.common.base.Supplier;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Callables
/*     */ {
/*     */   public static <T> Callable<T> returning(@Nullable final T value) {
/*  41 */     return new Callable<T>() {
/*     */         public T call() {
/*  43 */           return (T)value;
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
/*     */ 
/*     */ 
/*     */   
/*     */   static <T> Callable<T> threadRenaming(final Callable<T> callable, final Supplier<String> nameSupplier) {
/*  58 */     Preconditions.checkNotNull(nameSupplier);
/*  59 */     Preconditions.checkNotNull(callable);
/*  60 */     return new Callable<T>() {
/*     */         public T call() throws Exception {
/*  62 */           Thread currentThread = Thread.currentThread();
/*  63 */           String oldName = currentThread.getName();
/*  64 */           boolean restoreName = Callables.trySetName((String)nameSupplier.get(), currentThread);
/*     */           try {
/*  66 */             return (T)callable.call();
/*     */           } finally {
/*  68 */             if (restoreName) {
/*  69 */               Callables.trySetName(oldName, currentThread);
/*     */             }
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
/*     */ 
/*     */   
/*     */   static Runnable threadRenaming(final Runnable task, final Supplier<String> nameSupplier) {
/*  85 */     Preconditions.checkNotNull(nameSupplier);
/*  86 */     Preconditions.checkNotNull(task);
/*  87 */     return new Runnable() {
/*     */         public void run() {
/*  89 */           Thread currentThread = Thread.currentThread();
/*  90 */           String oldName = currentThread.getName();
/*  91 */           boolean restoreName = Callables.trySetName((String)nameSupplier.get(), currentThread);
/*     */           try {
/*  93 */             task.run();
/*     */           } finally {
/*  95 */             if (restoreName) {
/*  96 */               Callables.trySetName(oldName, currentThread);
/*     */             }
/*     */           } 
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean trySetName(String threadName, Thread currentThread) {
/*     */     try {
/* 109 */       currentThread.setName(threadName);
/* 110 */       return true;
/* 111 */     } catch (SecurityException e) {
/* 112 */       return false;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\commo\\util\concurrent\Callables.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
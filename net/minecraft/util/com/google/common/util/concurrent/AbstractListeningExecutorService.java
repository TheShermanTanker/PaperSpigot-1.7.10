/*    */ package net.minecraft.util.com.google.common.util.concurrent;
/*    */ 
/*    */ import java.util.concurrent.AbstractExecutorService;
/*    */ import java.util.concurrent.Callable;
/*    */ import java.util.concurrent.Future;
/*    */ import java.util.concurrent.RunnableFuture;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.util.com.google.common.annotations.Beta;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Beta
/*    */ public abstract class AbstractListeningExecutorService
/*    */   extends AbstractExecutorService
/*    */   implements ListeningExecutorService
/*    */ {
/*    */   protected final <T> ListenableFutureTask<T> newTaskFor(Runnable runnable, T value) {
/* 42 */     return ListenableFutureTask.create(runnable, value);
/*    */   }
/*    */   
/*    */   protected final <T> ListenableFutureTask<T> newTaskFor(Callable<T> callable) {
/* 46 */     return ListenableFutureTask.create(callable);
/*    */   }
/*    */   
/*    */   public ListenableFuture<?> submit(Runnable task) {
/* 50 */     return (ListenableFuture)super.submit(task);
/*    */   }
/*    */   
/*    */   public <T> ListenableFuture<T> submit(Runnable task, @Nullable T result) {
/* 54 */     return (ListenableFuture<T>)super.<T>submit(task, result);
/*    */   }
/*    */   
/*    */   public <T> ListenableFuture<T> submit(Callable<T> task) {
/* 58 */     return (ListenableFuture<T>)super.<T>submit(task);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\commo\\util\concurrent\AbstractListeningExecutorService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
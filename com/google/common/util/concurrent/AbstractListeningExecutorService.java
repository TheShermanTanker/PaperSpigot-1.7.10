/*     */ package com.google.common.util.concurrent;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.CancellationException;
/*     */ import java.util.concurrent.ExecutionException;
/*     */ import java.util.concurrent.ExecutorCompletionService;
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
/*     */ abstract class AbstractListeningExecutorService
/*     */   implements ListeningExecutorService
/*     */ {
/*     */   public ListenableFuture<?> submit(Runnable task) {
/*  42 */     ListenableFutureTask<Void> ftask = ListenableFutureTask.create(task, null);
/*  43 */     execute(ftask);
/*  44 */     return ftask;
/*     */   }
/*     */   
/*     */   public <T> ListenableFuture<T> submit(Runnable task, T result) {
/*  48 */     ListenableFutureTask<T> ftask = ListenableFutureTask.create(task, result);
/*  49 */     execute(ftask);
/*  50 */     return ftask;
/*     */   }
/*     */   
/*     */   public <T> ListenableFuture<T> submit(Callable<T> task) {
/*  54 */     ListenableFutureTask<T> ftask = ListenableFutureTask.create(task);
/*  55 */     execute(ftask);
/*  56 */     return ftask;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private <T> T doInvokeAny(Collection<? extends Callable<T>> tasks, boolean timed, long nanos) throws InterruptedException, ExecutionException, TimeoutException {
/*  64 */     int ntasks = tasks.size();
/*  65 */     Preconditions.checkArgument((ntasks > 0));
/*  66 */     List<Future<T>> futures = new ArrayList<Future<T>>(ntasks);
/*  67 */     ExecutorCompletionService<T> ecs = new ExecutorCompletionService<T>(this);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
/*     */     try {
/* 134 */       return doInvokeAny(tasks, false, 0L);
/* 135 */     } catch (TimeoutException cannotHappen) {
/* 136 */       throw new AssertionError();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
/* 143 */     return doInvokeAny(tasks, true, unit.toNanos(timeout));
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
/* 148 */     if (tasks == null) {
/* 149 */       throw new NullPointerException();
/*     */     }
/* 151 */     List<Future<T>> futures = new ArrayList<Future<T>>(tasks.size());
/* 152 */     boolean done = false;
/*     */     try {
/* 154 */       for (Callable<T> t : tasks) {
/* 155 */         ListenableFutureTask<T> f = ListenableFutureTask.create(t);
/* 156 */         futures.add(f);
/* 157 */         execute(f);
/*     */       } 
/* 159 */       for (Future<T> f : futures) {
/* 160 */         if (!f.isDone()) {
/*     */           
/* 162 */           try { f.get(); }
/* 163 */           catch (CancellationException ignore) {  }
/* 164 */           catch (ExecutionException ignore) {}
/*     */         }
/*     */       } 
/*     */       
/* 168 */       done = true;
/* 169 */       return futures;
/*     */     } finally {
/* 171 */       if (!done) {
/* 172 */         for (Future<T> f : futures) {
/* 173 */           f.cancel(true);
/*     */         }
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
/* 182 */     if (tasks == null || unit == null) {
/* 183 */       throw new NullPointerException();
/*     */     }
/* 185 */     long nanos = unit.toNanos(timeout);
/* 186 */     List<Future<T>> futures = new ArrayList<Future<T>>(tasks.size());
/* 187 */     boolean done = false;
/*     */     try {
/* 189 */       for (Callable<T> t : tasks) {
/* 190 */         futures.add(ListenableFutureTask.create(t));
/*     */       }
/*     */       
/* 193 */       long lastTime = System.nanoTime();
/*     */ 
/*     */ 
/*     */       
/* 197 */       Iterator<Future<T>> it = futures.iterator();
/* 198 */       while (it.hasNext()) {
/* 199 */         execute((Runnable)it.next());
/* 200 */         long now = System.nanoTime();
/* 201 */         nanos -= now - lastTime;
/* 202 */         lastTime = now;
/* 203 */         if (nanos <= 0L) {
/* 204 */           return futures;
/*     */         }
/*     */       } 
/*     */       
/* 208 */       for (Future<T> f : futures) {
/* 209 */         if (!f.isDone()) {
/* 210 */           if (nanos <= 0L) {
/* 211 */             return futures;
/*     */           }
/*     */           
/* 214 */           try { f.get(nanos, TimeUnit.NANOSECONDS); }
/* 215 */           catch (CancellationException ignore) {  }
/* 216 */           catch (ExecutionException ignore) {  }
/* 217 */           catch (TimeoutException toe)
/* 218 */           { return futures; }
/*     */           
/* 220 */           long now = System.nanoTime();
/* 221 */           nanos -= now - lastTime;
/* 222 */           lastTime = now;
/*     */         } 
/*     */       } 
/* 225 */       done = true;
/* 226 */       return futures;
/*     */     } finally {
/* 228 */       if (!done)
/* 229 */         for (Future<T> f : futures)
/* 230 */           f.cancel(true);  
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\commo\\util\concurrent\AbstractListeningExecutorService.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
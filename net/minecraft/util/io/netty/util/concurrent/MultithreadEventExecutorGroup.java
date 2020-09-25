/*     */ package net.minecraft.util.io.netty.util.concurrent;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
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
/*     */ public abstract class MultithreadEventExecutorGroup
/*     */   extends AbstractEventExecutorGroup
/*     */ {
/*     */   private final EventExecutor[] children;
/*  33 */   private final AtomicInteger childIndex = new AtomicInteger();
/*  34 */   private final AtomicInteger terminatedChildren = new AtomicInteger();
/*  35 */   private final Promise<?> terminationFuture = new DefaultPromise(GlobalEventExecutor.INSTANCE);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected MultithreadEventExecutorGroup(int nThreads, ThreadFactory threadFactory, Object... args) {
/*  45 */     if (nThreads <= 0) {
/*  46 */       throw new IllegalArgumentException(String.format("nThreads: %d (expected: > 0)", new Object[] { Integer.valueOf(nThreads) }));
/*     */     }
/*     */     
/*  49 */     if (threadFactory == null) {
/*  50 */       threadFactory = newDefaultThreadFactory();
/*     */     }
/*     */     
/*  53 */     this.children = (EventExecutor[])new SingleThreadEventExecutor[nThreads];
/*  54 */     for (int i = 0; i < nThreads; i++) {
/*  55 */       boolean success = false;
/*     */     }
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
/*  83 */     FutureListener<Object> terminationListener = new FutureListener()
/*     */       {
/*     */         public void operationComplete(Future<Object> future) throws Exception {
/*  86 */           if (MultithreadEventExecutorGroup.this.terminatedChildren.incrementAndGet() == MultithreadEventExecutorGroup.this.children.length) {
/*  87 */             MultithreadEventExecutorGroup.this.terminationFuture.setSuccess(null);
/*     */           }
/*     */         }
/*     */       };
/*     */     
/*  92 */     for (EventExecutor e : this.children) {
/*  93 */       e.terminationFuture().addListener(terminationListener);
/*     */     }
/*     */   }
/*     */   
/*     */   protected ThreadFactory newDefaultThreadFactory() {
/*  98 */     return new DefaultThreadFactory(getClass());
/*     */   }
/*     */ 
/*     */   
/*     */   public EventExecutor next() {
/* 103 */     return this.children[Math.abs(this.childIndex.getAndIncrement() % this.children.length)];
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<EventExecutor> iterator() {
/* 108 */     return children().iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int executorCount() {
/* 116 */     return this.children.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Set<EventExecutor> children() {
/* 123 */     Set<EventExecutor> children = Collections.newSetFromMap(new LinkedHashMap<EventExecutor, Boolean>());
/* 124 */     Collections.addAll(children, this.children);
/* 125 */     return children;
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
/*     */   public Future<?> shutdownGracefully(long quietPeriod, long timeout, TimeUnit unit) {
/* 138 */     for (EventExecutor l : this.children) {
/* 139 */       l.shutdownGracefully(quietPeriod, timeout, unit);
/*     */     }
/* 141 */     return terminationFuture();
/*     */   }
/*     */ 
/*     */   
/*     */   public Future<?> terminationFuture() {
/* 146 */     return this.terminationFuture;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void shutdown() {
/* 152 */     for (EventExecutor l : this.children) {
/* 153 */       l.shutdown();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isShuttingDown() {
/* 159 */     for (EventExecutor l : this.children) {
/* 160 */       if (!l.isShuttingDown()) {
/* 161 */         return false;
/*     */       }
/*     */     } 
/* 164 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isShutdown() {
/* 169 */     for (EventExecutor l : this.children) {
/* 170 */       if (!l.isShutdown()) {
/* 171 */         return false;
/*     */       }
/*     */     } 
/* 174 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isTerminated() {
/* 179 */     for (EventExecutor l : this.children) {
/* 180 */       if (!l.isTerminated()) {
/* 181 */         return false;
/*     */       }
/*     */     } 
/* 184 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
/* 190 */     long deadline = System.nanoTime() + unit.toNanos(timeout); EventExecutor[] arr$; int len$, i$;
/* 191 */     for (arr$ = this.children, len$ = arr$.length, i$ = 0; i$ < len$; ) { EventExecutor l = arr$[i$];
/*     */       while (true) {
/* 193 */         long timeLeft = deadline - System.nanoTime();
/* 194 */         if (timeLeft <= 0L) {
/*     */           break;
/*     */         }
/* 197 */         if (l.awaitTermination(timeLeft, TimeUnit.NANOSECONDS)) {
/*     */           i$++;
/*     */         }
/*     */       }  }
/*     */     
/* 202 */     return isTerminated();
/*     */   }
/*     */   
/*     */   protected abstract EventExecutor newChild(ThreadFactory paramThreadFactory, Object... paramVarArgs) throws Exception;
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\nett\\util\concurrent\MultithreadEventExecutorGroup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
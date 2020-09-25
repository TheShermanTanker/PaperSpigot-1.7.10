/*     */ package org.bukkit.craftbukkit.v1_7_R4.util;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.ConcurrentLinkedQueue;
/*     */ import java.util.concurrent.LinkedBlockingQueue;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.ThreadPoolExecutor;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
/*     */ import org.apache.commons.lang.Validate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class AsynchronousExecutor<P, T, C, E extends Throwable>
/*     */ {
/*  60 */   static final AtomicIntegerFieldUpdater STATE_FIELD = AtomicIntegerFieldUpdater.newUpdater(Task.class, "state");
/*     */   final CallBackProvider<P, T, C, E> provider;
/*     */   
/*     */   private static boolean set(Task $this, int expected, int value) {
/*  64 */     return STATE_FIELD.compareAndSet($this, expected, value);
/*     */   }
/*     */   
/*     */   class Task
/*     */     implements Runnable {
/*     */     static final int PENDING = 0;
/*     */     static final int STAGE_1_ASYNC = 1;
/*     */     static final int STAGE_1_SYNC = 2;
/*     */     static final int STAGE_1_COMPLETE = 3;
/*     */     static final int FINISHED = 4;
/*  74 */     volatile int state = 0;
/*     */     final P parameter;
/*     */     T object;
/*  77 */     final List<C> callbacks = new LinkedList<C>();
/*  78 */     E t = null;
/*     */     
/*     */     Task(P parameter) {
/*  81 */       this.parameter = parameter;
/*     */     }
/*     */ 
/*     */     
/*     */     public void run() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: invokevirtual initAsync : ()Z
/*     */       //   4: ifeq -> 21
/*     */       //   7: aload_0
/*     */       //   8: getfield this$0 : Lorg/bukkit/craftbukkit/v1_7_R4/util/AsynchronousExecutor;
/*     */       //   11: getfield finished : Ljava/util/Queue;
/*     */       //   14: aload_0
/*     */       //   15: invokeinterface add : (Ljava/lang/Object;)Z
/*     */       //   20: pop
/*     */       //   21: return
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #85	-> 0
/*     */       //   #86	-> 7
/*     */       //   #88	-> 21
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	22	0	this	Lorg/bukkit/craftbukkit/v1_7_R4/util/AsynchronousExecutor$Task;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*     */       //   0	22	0	this	Lorg/bukkit/craftbukkit/v1_7_R4/util/AsynchronousExecutor<TP;TT;TC;TE;>.Task;
/*     */     }
/*     */ 
/*     */     
/*     */     boolean initAsync() {
/*  91 */       if (AsynchronousExecutor.set(this, 0, 1)) {
/*  92 */         boolean ret = true;
/*     */         
/*     */         try {
/*  95 */           init();
/*     */         } finally {
/*  97 */           if (!AsynchronousExecutor.set(this, 1, 3)) {
/*     */ 
/*     */ 
/*     */             
/* 101 */             synchronized (this) {
/* 102 */               if (this.state != 2)
/*     */               {
/* 104 */                 notifyAll();
/*     */               }
/*     */ 
/*     */               
/* 108 */               this.state = 3;
/*     */             } 
/*     */             
/* 111 */             ret = false;
/*     */           } 
/*     */         } 
/*     */         
/* 115 */         return ret;
/*     */       } 
/* 117 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     void initSync() {
/* 122 */       if (AsynchronousExecutor.set(this, 0, 3)) {
/*     */         
/* 124 */         init();
/* 125 */       } else if (AsynchronousExecutor.set(this, 1, 2)) {
/*     */         
/* 127 */         synchronized (this) {
/* 128 */           if (AsynchronousExecutor.set(this, 2, 0))
/*     */           {
/* 130 */             while (this.state != 3) {
/*     */               try {
/* 132 */                 wait();
/* 133 */               } catch (InterruptedException e) {
/* 134 */                 Thread.currentThread().interrupt();
/* 135 */                 throw new RuntimeException("Unable to handle interruption on " + this.parameter, e);
/*     */               } 
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void init() {
/*     */       try {
/* 150 */         this.object = (T)AsynchronousExecutor.this.provider.callStage1(this.parameter);
/* 151 */       } catch (Throwable t) {
/* 152 */         this.t = (E)t;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     T get() throws E {
/* 158 */       initSync();
/* 159 */       if (this.callbacks.isEmpty())
/*     */       {
/*     */         
/* 162 */         this.callbacks.add((C)this);
/*     */       }
/* 164 */       finish();
/* 165 */       return this.object;
/*     */     }
/*     */     
/*     */     void finish() throws E {
/* 169 */       switch (this.state) {
/*     */ 
/*     */ 
/*     */         
/*     */         default:
/* 174 */           throw (E)new IllegalStateException("Attempting to finish unprepared(" + this.state + ") task(" + this.parameter + ")");
/*     */         case 3:
/*     */           try {
/* 177 */             if (this.t != null) {
/* 178 */               throw this.t;
/*     */             }
/* 180 */             if (this.callbacks.isEmpty()) {
/*     */               return;
/*     */             }
/*     */             
/* 184 */             AsynchronousExecutor.CallBackProvider<P, T, C, E> provider = AsynchronousExecutor.this.provider;
/* 185 */             P parameter = this.parameter;
/* 186 */             T object = this.object;
/*     */             
/* 188 */             provider.callStage2(parameter, object);
/* 189 */             for (C callback : this.callbacks) {
/* 190 */               if (callback == this) {
/*     */                 continue;
/*     */               }
/*     */ 
/*     */               
/* 195 */               provider.callStage3(parameter, object, callback);
/*     */             } 
/*     */           } finally {
/* 198 */             AsynchronousExecutor.this.tasks.remove(this.parameter);
/* 199 */             this.state = 4;
/*     */           } 
/*     */           break;
/*     */         case 4:
/*     */           break;
/*     */       } 
/*     */     } boolean drop() {
/* 206 */       if (AsynchronousExecutor.set(this, 0, 4)) {
/*     */         
/* 208 */         AsynchronousExecutor.this.tasks.remove(this.parameter);
/* 209 */         return true;
/*     */       } 
/*     */       
/* 212 */       return false;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 218 */   final Queue<Task> finished = new ConcurrentLinkedQueue<Task>();
/* 219 */   final Map<P, Task> tasks = new HashMap<P, Task>();
/*     */ 
/*     */   
/*     */   final ThreadPoolExecutor pool;
/*     */ 
/*     */ 
/*     */   
/*     */   public AsynchronousExecutor(CallBackProvider<P, T, C, E> provider, int coreSize) {
/* 227 */     Validate.notNull(provider, "Provider cannot be null");
/* 228 */     this.provider = provider;
/*     */ 
/*     */     
/* 231 */     this.pool = new ThreadPoolExecutor(coreSize, 2147483647, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), provider);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(P parameter, C callback) {
/* 240 */     Task task = this.tasks.get(parameter);
/* 241 */     if (task == null) {
/* 242 */       this.tasks.put(parameter, task = new Task(parameter));
/* 243 */       this.pool.execute(task);
/*     */     } 
/* 245 */     task.callbacks.add(callback);
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
/*     */   public boolean drop(P parameter, C callback) throws IllegalStateException {
/* 264 */     Task task = this.tasks.get(parameter);
/* 265 */     if (task == null) {
/* 266 */       throw new IllegalStateException("Unknown " + parameter);
/*     */     }
/* 268 */     if (!task.callbacks.remove(callback)) {
/* 269 */       throw new IllegalStateException("Unknown " + callback + " for " + parameter);
/*     */     }
/* 271 */     if (task.callbacks.isEmpty()) {
/* 272 */       return task.drop();
/*     */     }
/* 274 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T get(P parameter) throws E, IllegalStateException {
/* 284 */     Task task = this.tasks.get(parameter);
/* 285 */     if (task == null) {
/* 286 */       throw new IllegalStateException("Unknown " + parameter);
/*     */     }
/* 288 */     return task.get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T getSkipQueue(P parameter) throws E {
/* 295 */     return skipQueue(parameter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T getSkipQueue(P parameter, C callback) throws E {
/* 302 */     T object = skipQueue(parameter);
/* 303 */     this.provider.callStage3(parameter, object, callback);
/* 304 */     return object;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T getSkipQueue(P parameter, C... callbacks) throws E {
/* 311 */     CallBackProvider<P, T, C, E> provider = this.provider;
/* 312 */     T object = skipQueue(parameter);
/* 313 */     for (C callback : callbacks) {
/* 314 */       provider.callStage3(parameter, object, callback);
/*     */     }
/* 316 */     return object;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T getSkipQueue(P parameter, Iterable<C> callbacks) throws E {
/* 323 */     CallBackProvider<P, T, C, E> provider = this.provider;
/* 324 */     T object = skipQueue(parameter);
/* 325 */     for (C callback : callbacks) {
/* 326 */       provider.callStage3(parameter, object, callback);
/*     */     }
/* 328 */     return object;
/*     */   }
/*     */   
/*     */   private T skipQueue(P parameter) throws E {
/* 332 */     Task task = this.tasks.get(parameter);
/* 333 */     if (task != null) {
/* 334 */       return task.get();
/*     */     }
/* 336 */     T object = this.provider.callStage1(parameter);
/* 337 */     this.provider.callStage2(parameter, object);
/* 338 */     return object;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void finishActive() throws E {
/* 345 */     Queue<Task> finished = this.finished;
/* 346 */     while (!finished.isEmpty()) {
/* 347 */       ((Task)finished.poll()).finish();
/*     */     }
/*     */   }
/*     */   
/*     */   public void setActiveThreads(int coreSize) {
/* 352 */     this.pool.setCorePoolSize(coreSize);
/*     */   }
/*     */   
/*     */   public static interface CallBackProvider<P, T, C, E extends Throwable> extends ThreadFactory {
/*     */     T callStage1(P param1P) throws E;
/*     */     
/*     */     void callStage2(P param1P, T param1T) throws E;
/*     */     
/*     */     void callStage3(P param1P, T param1T, C param1C) throws E;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R\\util\AsynchronousExecutor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.google.common.util.concurrent;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.base.Throwables;
/*     */ import java.util.concurrent.Executor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public abstract class AbstractExecutionThreadService
/*     */   implements Service
/*     */ {
/*  37 */   private final Service delegate = new AbstractService() {
/*     */       protected final void doStart() {
/*  39 */         AbstractExecutionThreadService.this.executor().execute(new Runnable()
/*     */             {
/*     */               public void run() {
/*     */                 try {
/*  43 */                   AbstractExecutionThreadService.this.startUp();
/*  44 */                   AbstractExecutionThreadService.null.this.notifyStarted();
/*     */                   
/*  46 */                   if (AbstractExecutionThreadService.null.this.isRunning()) {
/*     */                     try {
/*  48 */                       AbstractExecutionThreadService.this.run();
/*  49 */                     } catch (Throwable t) {
/*     */                       try {
/*  51 */                         AbstractExecutionThreadService.this.shutDown();
/*  52 */                       } catch (Exception ignored) {}
/*  53 */                       throw t;
/*     */                     } 
/*     */                   }
/*     */                   
/*  57 */                   AbstractExecutionThreadService.this.shutDown();
/*  58 */                   AbstractExecutionThreadService.null.this.notifyStopped();
/*  59 */                 } catch (Throwable t) {
/*  60 */                   AbstractExecutionThreadService.null.this.notifyFailed(t);
/*  61 */                   throw Throwables.propagate(t);
/*     */                 } 
/*     */               }
/*     */             });
/*     */       }
/*     */       
/*     */       protected void doStop() {
/*  68 */         AbstractExecutionThreadService.this.triggerShutdown();
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void startUp() throws Exception {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void run() throws Exception;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void shutDown() throws Exception {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void triggerShutdown() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Executor executor() {
/* 113 */     return new Executor()
/*     */       {
/*     */         public void execute(Runnable command) {
/* 116 */           (new Thread(command, AbstractExecutionThreadService.this.getServiceName())).start();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public String toString() {
/* 122 */     return getServiceName() + " [" + state() + "]";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final ListenableFuture<Service.State> start() {
/* 128 */     return this.delegate.start();
/*     */   }
/*     */   
/*     */   public final Service.State startAndWait() {
/* 132 */     return this.delegate.startAndWait();
/*     */   }
/*     */   
/*     */   public final boolean isRunning() {
/* 136 */     return this.delegate.isRunning();
/*     */   }
/*     */   
/*     */   public final Service.State state() {
/* 140 */     return this.delegate.state();
/*     */   }
/*     */   
/*     */   public final ListenableFuture<Service.State> stop() {
/* 144 */     return this.delegate.stop();
/*     */   }
/*     */   
/*     */   public final Service.State stopAndWait() {
/* 148 */     return this.delegate.stopAndWait();
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
/*     */   protected String getServiceName() {
/* 160 */     return getClass().getSimpleName();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\commo\\util\concurrent\AbstractExecutionThreadService.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
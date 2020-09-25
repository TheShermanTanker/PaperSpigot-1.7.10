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
/*     */ 
/*     */ @Beta
/*     */ public abstract class AbstractIdleService
/*     */   implements Service
/*     */ {
/*  38 */   private final Service delegate = new AbstractService() {
/*     */       protected final void doStart() {
/*  40 */         AbstractIdleService.this.executor(Service.State.STARTING).execute(new Runnable() {
/*     */               public void run() {
/*     */                 try {
/*  43 */                   AbstractIdleService.this.startUp();
/*  44 */                   AbstractIdleService.null.this.notifyStarted();
/*  45 */                 } catch (Throwable t) {
/*  46 */                   AbstractIdleService.null.this.notifyFailed(t);
/*  47 */                   throw Throwables.propagate(t);
/*     */                 } 
/*     */               }
/*     */             });
/*     */       }
/*     */       
/*     */       protected final void doStop() {
/*  54 */         AbstractIdleService.this.executor(Service.State.STOPPING).execute(new Runnable() {
/*     */               public void run() {
/*     */                 try {
/*  57 */                   AbstractIdleService.this.shutDown();
/*  58 */                   AbstractIdleService.null.this.notifyStopped();
/*  59 */                 } catch (Throwable t) {
/*  60 */                   AbstractIdleService.null.this.notifyFailed(t);
/*  61 */                   throw Throwables.propagate(t);
/*     */                 } 
/*     */               }
/*     */             });
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
/*     */   protected Executor executor(final Service.State state) {
/*  87 */     return new Executor()
/*     */       {
/*     */         public void execute(Runnable command) {
/*  90 */           (new Thread(command, AbstractIdleService.this.getServiceName() + " " + state)).start();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public String toString() {
/*  96 */     return getServiceName() + " [" + state() + "]";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final ListenableFuture<Service.State> start() {
/* 102 */     return this.delegate.start();
/*     */   }
/*     */   
/*     */   public final Service.State startAndWait() {
/* 106 */     return this.delegate.startAndWait();
/*     */   }
/*     */   
/*     */   public final boolean isRunning() {
/* 110 */     return this.delegate.isRunning();
/*     */   }
/*     */   
/*     */   public final Service.State state() {
/* 114 */     return this.delegate.state();
/*     */   }
/*     */   
/*     */   public final ListenableFuture<Service.State> stop() {
/* 118 */     return this.delegate.stop();
/*     */   }
/*     */   
/*     */   public final Service.State stopAndWait() {
/* 122 */     return this.delegate.stopAndWait();
/*     */   }
/*     */   
/*     */   private String getServiceName() {
/* 126 */     return getClass().getSimpleName();
/*     */   }
/*     */   
/*     */   protected abstract void startUp() throws Exception;
/*     */   
/*     */   protected abstract void shutDown() throws Exception;
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\commo\\util\concurrent\AbstractIdleService.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
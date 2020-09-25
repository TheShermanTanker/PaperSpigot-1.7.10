/*     */ package net.minecraft.util.com.google.common.util.concurrent;
/*     */ 
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.TimeoutException;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import net.minecraft.util.com.google.common.annotations.Beta;
/*     */ import net.minecraft.util.com.google.common.base.Supplier;
/*     */ import net.minecraft.util.com.google.common.base.Throwables;
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
/*  40 */   private static final Logger logger = Logger.getLogger(AbstractExecutionThreadService.class.getName());
/*     */ 
/*     */ 
/*     */   
/*  44 */   private final Service delegate = new AbstractService() {
/*     */       protected final void doStart() {
/*  46 */         Executor executor = MoreExecutors.renamingDecorator(AbstractExecutionThreadService.this.executor(), new Supplier<String>() {
/*     */               public String get() {
/*  48 */                 return AbstractExecutionThreadService.this.serviceName();
/*     */               }
/*     */             });
/*  51 */         executor.execute(new Runnable()
/*     */             {
/*     */               public void run() {
/*     */                 try {
/*  55 */                   AbstractExecutionThreadService.this.startUp();
/*  56 */                   AbstractExecutionThreadService.null.this.notifyStarted();
/*     */                   
/*  58 */                   if (AbstractExecutionThreadService.null.this.isRunning()) {
/*     */                     try {
/*  60 */                       AbstractExecutionThreadService.this.run();
/*  61 */                     } catch (Throwable t) {
/*     */                       try {
/*  63 */                         AbstractExecutionThreadService.this.shutDown();
/*  64 */                       } catch (Exception ignored) {
/*  65 */                         AbstractExecutionThreadService.logger.log(Level.WARNING, "Error while attempting to shut down the service after failure.", ignored);
/*     */                       } 
/*     */ 
/*     */                       
/*  69 */                       throw t;
/*     */                     } 
/*     */                   }
/*     */                   
/*  73 */                   AbstractExecutionThreadService.this.shutDown();
/*  74 */                   AbstractExecutionThreadService.null.this.notifyStopped();
/*  75 */                 } catch (Throwable t) {
/*  76 */                   AbstractExecutionThreadService.null.this.notifyFailed(t);
/*  77 */                   throw Throwables.propagate(t);
/*     */                 } 
/*     */               }
/*     */             });
/*     */       }
/*     */       
/*     */       protected void doStop() {
/*  84 */         AbstractExecutionThreadService.this.triggerShutdown();
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
/*     */   protected void startUp() throws Exception {}
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
/*     */   protected void shutDown() throws Exception {}
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
/*     */   protected void triggerShutdown() {}
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
/*     */   protected Executor executor() {
/* 143 */     return new Executor()
/*     */       {
/*     */         public void execute(Runnable command) {
/* 146 */           MoreExecutors.newThread(AbstractExecutionThreadService.this.serviceName(), command).start();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public String toString() {
/* 152 */     return serviceName() + " [" + state() + "]";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public final ListenableFuture<Service.State> start() {
/* 160 */     return this.delegate.start();
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public final Service.State startAndWait() {
/* 166 */     return this.delegate.startAndWait();
/*     */   }
/*     */   
/*     */   public final boolean isRunning() {
/* 170 */     return this.delegate.isRunning();
/*     */   }
/*     */   
/*     */   public final Service.State state() {
/* 174 */     return this.delegate.state();
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public final ListenableFuture<Service.State> stop() {
/* 180 */     return this.delegate.stop();
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public final Service.State stopAndWait() {
/* 186 */     return this.delegate.stopAndWait();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void addListener(Service.Listener listener, Executor executor) {
/* 193 */     this.delegate.addListener(listener, executor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Throwable failureCause() {
/* 200 */     return this.delegate.failureCause();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Service startAsync() {
/* 207 */     this.delegate.startAsync();
/* 208 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Service stopAsync() {
/* 215 */     this.delegate.stopAsync();
/* 216 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void awaitRunning() {
/* 223 */     this.delegate.awaitRunning();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void awaitRunning(long timeout, TimeUnit unit) throws TimeoutException {
/* 230 */     this.delegate.awaitRunning(timeout, unit);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void awaitTerminated() {
/* 237 */     this.delegate.awaitTerminated();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void awaitTerminated(long timeout, TimeUnit unit) throws TimeoutException {
/* 244 */     this.delegate.awaitTerminated(timeout, unit);
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
/*     */   protected String serviceName() {
/* 256 */     return getClass().getSimpleName();
/*     */   }
/*     */   
/*     */   protected abstract void run() throws Exception;
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\commo\\util\concurrent\AbstractExecutionThreadService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
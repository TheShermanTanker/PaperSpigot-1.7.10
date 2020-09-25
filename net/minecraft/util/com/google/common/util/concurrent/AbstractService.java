/*     */ package net.minecraft.util.com.google.common.util.concurrent;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.concurrent.ExecutionException;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.TimeoutException;
/*     */ import javax.annotation.Nullable;
/*     */ import javax.annotation.concurrent.GuardedBy;
/*     */ import javax.annotation.concurrent.Immutable;
/*     */ import net.minecraft.util.com.google.common.annotations.Beta;
/*     */ import net.minecraft.util.com.google.common.base.Preconditions;
/*     */ import net.minecraft.util.com.google.common.collect.Lists;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public abstract class AbstractService
/*     */   implements Service
/*     */ {
/*  55 */   private final Monitor monitor = new Monitor();
/*     */   
/*  57 */   private final Transition startup = new Transition();
/*  58 */   private final Transition shutdown = new Transition();
/*     */   
/*  60 */   private final Monitor.Guard isStartable = new Monitor.Guard(this.monitor) {
/*     */       public boolean isSatisfied() {
/*  62 */         return (AbstractService.this.state() == Service.State.NEW);
/*     */       }
/*     */     };
/*     */   
/*  66 */   private final Monitor.Guard isStoppable = new Monitor.Guard(this.monitor) {
/*     */       public boolean isSatisfied() {
/*  68 */         return (AbstractService.this.state().compareTo(Service.State.RUNNING) <= 0);
/*     */       }
/*     */     };
/*     */   
/*  72 */   private final Monitor.Guard hasReachedRunning = new Monitor.Guard(this.monitor) {
/*     */       public boolean isSatisfied() {
/*  74 */         return (AbstractService.this.state().compareTo(Service.State.RUNNING) >= 0);
/*     */       }
/*     */     };
/*     */   
/*  78 */   private final Monitor.Guard isStopped = new Monitor.Guard(this.monitor) {
/*     */       public boolean isSatisfied() {
/*  80 */         return AbstractService.this.state().isTerminal();
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */   
/*     */   @GuardedBy("monitor")
/*  87 */   private final List<ListenerExecutorPair> listeners = Lists.newArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  96 */   private final ExecutionQueue queuedListeners = new ExecutionQueue();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @GuardedBy("monitor")
/* 107 */   private volatile StateSnapshot snapshot = new StateSnapshot(Service.State.NEW);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractService() {
/* 114 */     addListener(new Service.Listener()
/*     */         {
/*     */           public void running() {
/* 117 */             AbstractService.this.startup.set(Service.State.RUNNING);
/*     */           }
/*     */           
/*     */           public void stopping(Service.State from) {
/* 121 */             if (from == Service.State.STARTING) {
/* 122 */               AbstractService.this.startup.set(Service.State.STOPPING);
/*     */             }
/*     */           }
/*     */           
/*     */           public void terminated(Service.State from) {
/* 127 */             if (from == Service.State.NEW) {
/* 128 */               AbstractService.this.startup.set(Service.State.TERMINATED);
/*     */             }
/* 130 */             AbstractService.this.shutdown.set(Service.State.TERMINATED);
/*     */           }
/*     */           
/*     */           public void failed(Service.State from, Throwable failure) {
/* 134 */             switch (from) {
/*     */               case STARTING:
/* 136 */                 AbstractService.this.startup.setException(failure);
/* 137 */                 AbstractService.this.shutdown.setException(new Exception("Service failed to start.", failure));
/*     */                 return;
/*     */               case RUNNING:
/* 140 */                 AbstractService.this.shutdown.setException(new Exception("Service failed while running", failure));
/*     */                 return;
/*     */               case STOPPING:
/* 143 */                 AbstractService.this.shutdown.setException(failure);
/*     */                 return;
/*     */             } 
/*     */ 
/*     */ 
/*     */             
/* 149 */             throw new AssertionError("Unexpected from state: " + from);
/*     */           }
/*     */         }MoreExecutors.sameThreadExecutor());
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
/*     */   public final Service startAsync() {
/* 181 */     if (this.monitor.enterIf(this.isStartable)) {
/*     */       try {
/* 183 */         this.snapshot = new StateSnapshot(Service.State.STARTING);
/* 184 */         starting();
/* 185 */         doStart();
/*     */       }
/* 187 */       catch (Throwable startupFailure) {
/* 188 */         notifyFailed(startupFailure);
/*     */       } finally {
/* 190 */         this.monitor.leave();
/* 191 */         executeListeners();
/*     */       } 
/*     */     } else {
/* 194 */       throw new IllegalStateException("Service " + this + " has already been started");
/*     */     } 
/* 196 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public final ListenableFuture<Service.State> start() {
/* 202 */     if (this.monitor.enterIf(this.isStartable)) {
/*     */       try {
/* 204 */         this.snapshot = new StateSnapshot(Service.State.STARTING);
/* 205 */         starting();
/* 206 */         doStart();
/* 207 */       } catch (Throwable startupFailure) {
/* 208 */         notifyFailed(startupFailure);
/*     */       } finally {
/* 210 */         this.monitor.leave();
/* 211 */         executeListeners();
/*     */       } 
/*     */     }
/* 214 */     return this.startup;
/*     */   }
/*     */   
/*     */   public final Service stopAsync() {
/* 218 */     stop();
/* 219 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public final ListenableFuture<Service.State> stop() {
/* 225 */     if (this.monitor.enterIf(this.isStoppable)) {
/*     */       try {
/* 227 */         Service.State previous = state();
/* 228 */         switch (previous) {
/*     */           case NEW:
/* 230 */             this.snapshot = new StateSnapshot(Service.State.TERMINATED);
/* 231 */             terminated(Service.State.NEW);
/*     */             break;
/*     */           case STARTING:
/* 234 */             this.snapshot = new StateSnapshot(Service.State.STARTING, true, null);
/* 235 */             stopping(Service.State.STARTING);
/*     */             break;
/*     */           case RUNNING:
/* 238 */             this.snapshot = new StateSnapshot(Service.State.STOPPING);
/* 239 */             stopping(Service.State.RUNNING);
/* 240 */             doStop();
/*     */             break;
/*     */           
/*     */           case STOPPING:
/*     */           case TERMINATED:
/*     */           case FAILED:
/* 246 */             throw new AssertionError("isStoppable is incorrectly implemented, saw: " + previous);
/*     */           default:
/* 248 */             throw new AssertionError("Unexpected state: " + previous);
/*     */         } 
/*     */ 
/*     */       
/* 252 */       } catch (Throwable shutdownFailure) {
/* 253 */         notifyFailed(shutdownFailure);
/*     */       } finally {
/* 255 */         this.monitor.leave();
/* 256 */         executeListeners();
/*     */       } 
/*     */     }
/* 259 */     return this.shutdown;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Service.State startAndWait() {
/* 265 */     return Futures.<Service.State>getUnchecked(start());
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Service.State stopAndWait() {
/* 271 */     return Futures.<Service.State>getUnchecked(stop());
/*     */   }
/*     */   
/*     */   public final void awaitRunning() {
/* 275 */     this.monitor.enterWhenUninterruptibly(this.hasReachedRunning);
/*     */     try {
/* 277 */       checkCurrentState(Service.State.RUNNING);
/*     */     } finally {
/* 279 */       this.monitor.leave();
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void awaitRunning(long timeout, TimeUnit unit) throws TimeoutException {
/* 284 */     if (this.monitor.enterWhenUninterruptibly(this.hasReachedRunning, timeout, unit)) {
/*     */       try {
/* 286 */         checkCurrentState(Service.State.RUNNING);
/*     */       } finally {
/* 288 */         this.monitor.leave();
/*     */       
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 295 */       throw new TimeoutException("Timed out waiting for " + this + " to reach the RUNNING state. " + "Current state: " + state());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final void awaitTerminated() {
/* 301 */     this.monitor.enterWhenUninterruptibly(this.isStopped);
/*     */     try {
/* 303 */       checkCurrentState(Service.State.TERMINATED);
/*     */     } finally {
/* 305 */       this.monitor.leave();
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void awaitTerminated(long timeout, TimeUnit unit) throws TimeoutException {
/* 310 */     if (this.monitor.enterWhenUninterruptibly(this.isStopped, timeout, unit)) {
/*     */       try {
/* 312 */         Service.State state = state();
/* 313 */         checkCurrentState(Service.State.TERMINATED);
/*     */       } finally {
/* 315 */         this.monitor.leave();
/*     */       
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 322 */       throw new TimeoutException("Timed out waiting for " + this + " to reach a terminal state. " + "Current state: " + state());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @GuardedBy("monitor")
/*     */   private void checkCurrentState(Service.State expected) {
/* 330 */     Service.State actual = state();
/* 331 */     if (actual != expected) {
/* 332 */       if (actual == Service.State.FAILED)
/*     */       {
/* 334 */         throw new IllegalStateException("Expected the service to be " + expected + ", but the service has FAILED", failureCause());
/*     */       }
/*     */       
/* 337 */       throw new IllegalStateException("Expected the service to be " + expected + ", but was " + actual);
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
/*     */   protected final void notifyStarted() {
/* 349 */     this.monitor.enter();
/*     */ 
/*     */     
/*     */     try {
/* 353 */       if (this.snapshot.state != Service.State.STARTING) {
/* 354 */         IllegalStateException failure = new IllegalStateException("Cannot notifyStarted() when the service is " + this.snapshot.state);
/*     */         
/* 356 */         notifyFailed(failure);
/* 357 */         throw failure;
/*     */       } 
/*     */       
/* 360 */       if (this.snapshot.shutdownWhenStartupFinishes) {
/* 361 */         this.snapshot = new StateSnapshot(Service.State.STOPPING);
/*     */ 
/*     */         
/* 364 */         doStop();
/*     */       } else {
/* 366 */         this.snapshot = new StateSnapshot(Service.State.RUNNING);
/* 367 */         running();
/*     */       } 
/*     */     } finally {
/* 370 */       this.monitor.leave();
/* 371 */       executeListeners();
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
/*     */   protected final void notifyStopped() {
/* 383 */     this.monitor.enter();
/*     */ 
/*     */     
/*     */     try {
/* 387 */       Service.State previous = this.snapshot.state;
/* 388 */       if (previous != Service.State.STOPPING && previous != Service.State.RUNNING) {
/* 389 */         IllegalStateException failure = new IllegalStateException("Cannot notifyStopped() when the service is " + previous);
/*     */         
/* 391 */         notifyFailed(failure);
/* 392 */         throw failure;
/*     */       } 
/* 394 */       this.snapshot = new StateSnapshot(Service.State.TERMINATED);
/* 395 */       terminated(previous);
/*     */     } finally {
/* 397 */       this.monitor.leave();
/* 398 */       executeListeners();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void notifyFailed(Throwable cause) {
/* 408 */     Preconditions.checkNotNull(cause);
/*     */     
/* 410 */     this.monitor.enter();
/*     */     try {
/* 412 */       Service.State previous = state();
/* 413 */       switch (previous) {
/*     */         case TERMINATED:
/*     */         case NEW:
/* 416 */           throw new IllegalStateException("Failed while in state:" + previous, cause);
/*     */         case STARTING:
/*     */         case RUNNING:
/*     */         case STOPPING:
/* 420 */           this.snapshot = new StateSnapshot(Service.State.FAILED, false, cause);
/* 421 */           failed(previous, cause);
/*     */           break;
/*     */         
/*     */         case FAILED:
/*     */           break;
/*     */         default:
/* 427 */           throw new AssertionError("Unexpected state: " + previous);
/*     */       } 
/*     */     } finally {
/* 430 */       this.monitor.leave();
/* 431 */       executeListeners();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean isRunning() {
/* 437 */     return (state() == Service.State.RUNNING);
/*     */   }
/*     */ 
/*     */   
/*     */   public final Service.State state() {
/* 442 */     return this.snapshot.externalState();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Throwable failureCause() {
/* 450 */     return this.snapshot.failureCause();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void addListener(Service.Listener listener, Executor executor) {
/* 458 */     Preconditions.checkNotNull(listener, "listener");
/* 459 */     Preconditions.checkNotNull(executor, "executor");
/* 460 */     this.monitor.enter();
/*     */     try {
/* 462 */       Service.State currentState = state();
/* 463 */       if (currentState != Service.State.TERMINATED && currentState != Service.State.FAILED) {
/* 464 */         this.listeners.add(new ListenerExecutorPair(listener, executor));
/*     */       }
/*     */     } finally {
/* 467 */       this.monitor.leave();
/*     */     } 
/*     */   }
/*     */   
/*     */   public String toString() {
/* 472 */     return getClass().getSimpleName() + " [" + state() + "]";
/*     */   }
/*     */ 
/*     */   
/*     */   private class Transition
/*     */     extends AbstractFuture<Service.State>
/*     */   {
/*     */     private Transition() {}
/*     */     
/*     */     public Service.State get(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException, ExecutionException {
/*     */       try {
/* 483 */         return super.get(timeout, unit);
/* 484 */       } catch (TimeoutException e) {
/* 485 */         throw new TimeoutException(AbstractService.this.toString());
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void executeListeners() {
/* 495 */     if (!this.monitor.isOccupiedByCurrentThread()) {
/* 496 */       this.queuedListeners.execute();
/*     */     }
/*     */   }
/*     */   
/*     */   @GuardedBy("monitor")
/*     */   private void starting() {
/* 502 */     for (ListenerExecutorPair pair : this.listeners) {
/* 503 */       this.queuedListeners.add(new Runnable() {
/*     */             public void run() {
/* 505 */               pair.listener.starting();
/*     */             }
/*     */           },  pair.executor);
/*     */     } 
/*     */   }
/*     */   
/*     */   @GuardedBy("monitor")
/*     */   private void running() {
/* 513 */     for (ListenerExecutorPair pair : this.listeners) {
/* 514 */       this.queuedListeners.add(new Runnable() {
/*     */             public void run() {
/* 516 */               pair.listener.running();
/*     */             }
/*     */           },  pair.executor);
/*     */     } 
/*     */   }
/*     */   
/*     */   @GuardedBy("monitor")
/*     */   private void stopping(final Service.State from) {
/* 524 */     for (ListenerExecutorPair pair : this.listeners) {
/* 525 */       this.queuedListeners.add(new Runnable() {
/*     */             public void run() {
/* 527 */               pair.listener.stopping(from);
/*     */             }
/*     */           },  pair.executor);
/*     */     } 
/*     */   }
/*     */   
/*     */   @GuardedBy("monitor")
/*     */   private void terminated(final Service.State from) {
/* 535 */     for (ListenerExecutorPair pair : this.listeners) {
/* 536 */       this.queuedListeners.add(new Runnable() {
/*     */             public void run() {
/* 538 */               pair.listener.terminated(from);
/*     */             }
/*     */           },  pair.executor);
/*     */     } 
/*     */     
/* 543 */     this.listeners.clear();
/*     */   }
/*     */   
/*     */   @GuardedBy("monitor")
/*     */   private void failed(final Service.State from, final Throwable cause) {
/* 548 */     for (ListenerExecutorPair pair : this.listeners) {
/* 549 */       this.queuedListeners.add(new Runnable() {
/*     */             public void run() {
/* 551 */               pair.listener.failed(from, cause);
/*     */             }
/*     */           }pair.executor);
/*     */     } 
/*     */     
/* 556 */     this.listeners.clear();
/*     */   }
/*     */   
/*     */   protected abstract void doStart();
/*     */   
/*     */   protected abstract void doStop();
/*     */   
/*     */   private static class ListenerExecutorPair {
/*     */     ListenerExecutorPair(Service.Listener listener, Executor executor) {
/* 565 */       this.listener = listener;
/* 566 */       this.executor = executor;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     final Service.Listener listener;
/*     */ 
/*     */ 
/*     */     
/*     */     final Executor executor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Immutable
/*     */   private static final class StateSnapshot
/*     */   {
/*     */     final Service.State state;
/*     */ 
/*     */     
/*     */     final boolean shutdownWhenStartupFinishes;
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     final Throwable failure;
/*     */ 
/*     */ 
/*     */     
/*     */     StateSnapshot(Service.State internalState) {
/* 597 */       this(internalState, false, null);
/*     */     }
/*     */ 
/*     */     
/*     */     StateSnapshot(Service.State internalState, boolean shutdownWhenStartupFinishes, @Nullable Throwable failure) {
/* 602 */       Preconditions.checkArgument((!shutdownWhenStartupFinishes || internalState == Service.State.STARTING), "shudownWhenStartupFinishes can only be set if state is STARTING. Got %s instead.", new Object[] { internalState });
/*     */ 
/*     */       
/* 605 */       Preconditions.checkArgument(((((failure != null) ? 1 : 0) ^ ((internalState == Service.State.FAILED) ? 1 : 0)) == 0), "A failure cause should be set if and only if the state is failed.  Got %s and %s instead.", new Object[] { internalState, failure });
/*     */ 
/*     */       
/* 608 */       this.state = internalState;
/* 609 */       this.shutdownWhenStartupFinishes = shutdownWhenStartupFinishes;
/* 610 */       this.failure = failure;
/*     */     }
/*     */ 
/*     */     
/*     */     Service.State externalState() {
/* 615 */       if (this.shutdownWhenStartupFinishes && this.state == Service.State.STARTING) {
/* 616 */         return Service.State.STOPPING;
/*     */       }
/* 618 */       return this.state;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     Throwable failureCause() {
/* 624 */       Preconditions.checkState((this.state == Service.State.FAILED), "failureCause() is only valid if the service has failed, service is %s", new Object[] { this.state });
/*     */       
/* 626 */       return this.failure;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\commo\\util\concurrent\AbstractService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
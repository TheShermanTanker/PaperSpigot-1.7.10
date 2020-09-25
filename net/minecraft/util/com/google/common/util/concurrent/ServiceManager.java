/*     */ package net.minecraft.util.com.google.common.util.concurrent;
/*     */ 
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.EnumMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.TimeoutException;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.annotation.concurrent.GuardedBy;
/*     */ import javax.annotation.concurrent.Immutable;
/*     */ import net.minecraft.util.com.google.common.annotations.Beta;
/*     */ import net.minecraft.util.com.google.common.base.Function;
/*     */ import net.minecraft.util.com.google.common.base.Objects;
/*     */ import net.minecraft.util.com.google.common.base.Preconditions;
/*     */ import net.minecraft.util.com.google.common.base.Predicates;
/*     */ import net.minecraft.util.com.google.common.base.Stopwatch;
/*     */ import net.minecraft.util.com.google.common.base.Supplier;
/*     */ import net.minecraft.util.com.google.common.collect.Collections2;
/*     */ import net.minecraft.util.com.google.common.collect.ImmutableCollection;
/*     */ import net.minecraft.util.com.google.common.collect.ImmutableList;
/*     */ import net.minecraft.util.com.google.common.collect.ImmutableMap;
/*     */ import net.minecraft.util.com.google.common.collect.ImmutableMultimap;
/*     */ import net.minecraft.util.com.google.common.collect.ImmutableSet;
/*     */ import net.minecraft.util.com.google.common.collect.ImmutableSetMultimap;
/*     */ import net.minecraft.util.com.google.common.collect.Lists;
/*     */ import net.minecraft.util.com.google.common.collect.Maps;
/*     */ import net.minecraft.util.com.google.common.collect.Multimaps;
/*     */ import net.minecraft.util.com.google.common.collect.Multiset;
/*     */ import net.minecraft.util.com.google.common.collect.Ordering;
/*     */ import net.minecraft.util.com.google.common.collect.SetMultimap;
/*     */ import net.minecraft.util.com.google.common.collect.Sets;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public final class ServiceManager
/*     */ {
/* 124 */   private static final Logger logger = Logger.getLogger(ServiceManager.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final ServiceManagerState state;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final ImmutableList<Service> services;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Beta
/*     */   public static abstract class Listener
/*     */   {
/*     */     public void healthy() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void stopped() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void failure(Service service) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ServiceManager(Iterable<? extends Service> services) {
/* 180 */     ImmutableList<Service> copy = ImmutableList.copyOf(services);
/* 181 */     if (copy.isEmpty()) {
/*     */ 
/*     */       
/* 184 */       logger.log(Level.WARNING, "ServiceManager configured with no services.  Is your application configured properly?", new EmptyServiceManagerWarning());
/*     */ 
/*     */       
/* 187 */       copy = ImmutableList.of(new NoOpService());
/*     */     } 
/* 189 */     this.state = new ServiceManagerState((ImmutableCollection<Service>)copy);
/* 190 */     this.services = copy;
/* 191 */     WeakReference<ServiceManagerState> stateReference = new WeakReference<ServiceManagerState>(this.state);
/*     */     
/* 193 */     for (Service service : copy) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 202 */       service.addListener(new ServiceListener(service, stateReference), new SynchronizedExecutor());
/*     */ 
/*     */       
/* 205 */       Preconditions.checkArgument((service.state() == Service.State.NEW), "Can only manage NEW services, %s", new Object[] { service });
/*     */     } 
/*     */ 
/*     */     
/* 209 */     this.state.markReady();
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
/*     */   public void addListener(Listener listener, Executor executor) {
/* 233 */     this.state.addListener(listener, executor);
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
/*     */   public void addListener(Listener listener) {
/* 249 */     this.state.addListener(listener, MoreExecutors.sameThreadExecutor());
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
/*     */   public ServiceManager startAsync() {
/* 261 */     for (Service service : this.services) {
/* 262 */       Service.State state = service.state();
/* 263 */       Preconditions.checkState((state == Service.State.NEW), "Service %s is %s, cannot start it.", new Object[] { service, state });
/*     */     } 
/* 265 */     for (Service service : this.services) {
/*     */       try {
/* 267 */         service.startAsync();
/* 268 */       } catch (IllegalStateException e) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 273 */         logger.log(Level.WARNING, "Unable to start Service " + service, e);
/*     */       } 
/*     */     } 
/* 276 */     return this;
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
/*     */   public void awaitHealthy() {
/* 288 */     this.state.awaitHealthy();
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
/*     */   public void awaitHealthy(long timeout, TimeUnit unit) throws TimeoutException {
/* 303 */     this.state.awaitHealthy(timeout, unit);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ServiceManager stopAsync() {
/* 313 */     for (Service service : this.services) {
/* 314 */       service.stopAsync();
/*     */     }
/* 316 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void awaitStopped() {
/* 325 */     this.state.awaitStopped();
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
/*     */   public void awaitStopped(long timeout, TimeUnit unit) throws TimeoutException {
/* 338 */     this.state.awaitStopped(timeout, unit);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isHealthy() {
/* 348 */     for (Service service : this.services) {
/* 349 */       if (!service.isRunning()) {
/* 350 */         return false;
/*     */       }
/*     */     } 
/* 353 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImmutableMultimap<Service.State, Service> servicesByState() {
/* 363 */     return this.state.servicesByState();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImmutableMap<Service, Long> startupTimes() {
/* 374 */     return this.state.startupTimes();
/*     */   }
/*     */   
/*     */   public String toString() {
/* 378 */     return Objects.toStringHelper(ServiceManager.class).add("services", Collections2.filter((Collection)this.services, Predicates.not(Predicates.instanceOf(NoOpService.class)))).toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class ServiceManagerState
/*     */   {
/* 388 */     final Monitor monitor = new Monitor();
/*     */     @GuardedBy("monitor")
/* 390 */     final SetMultimap<Service.State, Service> servicesByState = Multimaps.newSetMultimap(new EnumMap<Service.State, Object>(Service.State.class), new Supplier<Set<Service>>()
/*     */         {
/*     */           
/*     */           public Set<Service> get()
/*     */           {
/* 395 */             return Sets.newLinkedHashSet();
/*     */           }
/*     */         });
/*     */     @GuardedBy("monitor")
/* 399 */     final Multiset<Service.State> states = this.servicesByState.keys();
/*     */     
/*     */     @GuardedBy("monitor")
/* 402 */     final Map<Service, Stopwatch> startupTimers = Maps.newIdentityHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @GuardedBy("monitor")
/*     */     boolean ready;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @GuardedBy("monitor")
/*     */     boolean transitioned;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     final int numberOfServices;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 428 */     final Monitor.Guard awaitHealthGuard = new Monitor.Guard(this.monitor)
/*     */       {
/*     */         public boolean isSatisfied() {
/* 431 */           return (ServiceManager.ServiceManagerState.this.states.count(Service.State.RUNNING) == ServiceManager.ServiceManagerState.this.numberOfServices || ServiceManager.ServiceManagerState.this.states.contains(Service.State.STOPPING) || ServiceManager.ServiceManagerState.this.states.contains(Service.State.TERMINATED) || ServiceManager.ServiceManagerState.this.states.contains(Service.State.FAILED));
/*     */         }
/*     */       };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 441 */     final Monitor.Guard stoppedGuard = new Monitor.Guard(this.monitor) {
/*     */         public boolean isSatisfied() {
/* 443 */           return (ServiceManager.ServiceManagerState.this.states.count(Service.State.TERMINATED) + ServiceManager.ServiceManagerState.this.states.count(Service.State.FAILED) == ServiceManager.ServiceManagerState.this.numberOfServices);
/*     */         }
/*     */       };
/*     */     
/*     */     @GuardedBy("monitor")
/* 448 */     final List<ServiceManager.ListenerExecutorPair> listeners = Lists.newArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @GuardedBy("monitor")
/* 459 */     final ExecutionQueue queuedListeners = new ExecutionQueue();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     ServiceManagerState(ImmutableCollection<Service> services) {
/* 469 */       this.numberOfServices = services.size();
/* 470 */       this.servicesByState.putAll(Service.State.NEW, (Iterable)services);
/* 471 */       for (Service service : services) {
/* 472 */         this.startupTimers.put(service, Stopwatch.createUnstarted());
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void markReady() {
/* 481 */       this.monitor.enter();
/*     */       try {
/* 483 */         if (!this.transitioned) {
/*     */           
/* 485 */           this.ready = true;
/*     */         } else {
/*     */           
/* 488 */           List<Service> servicesInBadStates = Lists.newArrayList();
/* 489 */           for (Service service : servicesByState().values()) {
/* 490 */             if (service.state() != Service.State.NEW) {
/* 491 */               servicesInBadStates.add(service);
/*     */             }
/*     */           } 
/* 494 */           throw new IllegalArgumentException("Services started transitioning asynchronously before the ServiceManager was constructed: " + servicesInBadStates);
/*     */         } 
/*     */       } finally {
/*     */         
/* 498 */         this.monitor.leave();
/*     */       } 
/*     */     }
/*     */     
/*     */     void addListener(ServiceManager.Listener listener, Executor executor) {
/* 503 */       Preconditions.checkNotNull(listener, "listener");
/* 504 */       Preconditions.checkNotNull(executor, "executor");
/* 505 */       this.monitor.enter();
/*     */       
/*     */       try {
/* 508 */         if (!this.stoppedGuard.isSatisfied()) {
/* 509 */           this.listeners.add(new ServiceManager.ListenerExecutorPair(listener, executor));
/*     */         }
/*     */       } finally {
/* 512 */         this.monitor.leave();
/*     */       } 
/*     */     }
/*     */     
/*     */     void awaitHealthy() {
/* 517 */       this.monitor.enterWhenUninterruptibly(this.awaitHealthGuard);
/*     */       try {
/* 519 */         checkHealthy();
/*     */       } finally {
/* 521 */         this.monitor.leave();
/*     */       } 
/*     */     }
/*     */     
/*     */     void awaitHealthy(long timeout, TimeUnit unit) throws TimeoutException {
/* 526 */       this.monitor.enter();
/*     */       try {
/* 528 */         if (!this.monitor.waitForUninterruptibly(this.awaitHealthGuard, timeout, unit)) {
/* 529 */           throw new TimeoutException("Timeout waiting for the services to become healthy. The following services have not started: " + Multimaps.filterKeys(this.servicesByState, Predicates.in(ImmutableSet.of(Service.State.NEW, Service.State.STARTING))));
/*     */         }
/*     */ 
/*     */         
/* 533 */         checkHealthy();
/*     */       } finally {
/* 535 */         this.monitor.leave();
/*     */       } 
/*     */     }
/*     */     
/*     */     void awaitStopped() {
/* 540 */       this.monitor.enterWhenUninterruptibly(this.stoppedGuard);
/* 541 */       this.monitor.leave();
/*     */     }
/*     */     
/*     */     void awaitStopped(long timeout, TimeUnit unit) throws TimeoutException {
/* 545 */       this.monitor.enter();
/*     */       try {
/* 547 */         if (!this.monitor.waitForUninterruptibly(this.stoppedGuard, timeout, unit)) {
/* 548 */           throw new TimeoutException("Timeout waiting for the services to stop. The following services have not stopped: " + Multimaps.filterKeys(this.servicesByState, Predicates.not(Predicates.in(ImmutableSet.of(Service.State.TERMINATED, Service.State.FAILED)))));
/*     */         
/*     */         }
/*     */       }
/*     */       finally {
/*     */         
/* 554 */         this.monitor.leave();
/*     */       } 
/*     */     }
/*     */     
/*     */     ImmutableMultimap<Service.State, Service> servicesByState() {
/* 559 */       ImmutableSetMultimap.Builder<Service.State, Service> builder = ImmutableSetMultimap.builder();
/* 560 */       this.monitor.enter();
/*     */       try {
/* 562 */         for (Map.Entry<Service.State, Service> entry : (Iterable<Map.Entry<Service.State, Service>>)this.servicesByState.entries()) {
/* 563 */           if (!(entry.getValue() instanceof ServiceManager.NoOpService)) {
/* 564 */             builder.put(entry.getKey(), entry.getValue());
/*     */           }
/*     */         } 
/*     */       } finally {
/* 568 */         this.monitor.leave();
/*     */       } 
/* 570 */       return (ImmutableMultimap<Service.State, Service>)builder.build();
/*     */     }
/*     */     
/*     */     ImmutableMap<Service, Long> startupTimes() {
/*     */       List<Map.Entry<Service, Long>> loadTimes;
/* 575 */       this.monitor.enter();
/*     */       try {
/* 577 */         loadTimes = Lists.newArrayListWithCapacity(this.states.size() - this.states.count(Service.State.NEW) + this.states.count(Service.State.STARTING));
/*     */         
/* 579 */         for (Map.Entry<Service, Stopwatch> entry : this.startupTimers.entrySet()) {
/* 580 */           Service service = entry.getKey();
/* 581 */           Stopwatch stopWatch = entry.getValue();
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 586 */           if (!stopWatch.isRunning() && !this.servicesByState.containsEntry(Service.State.NEW, service) && !(service instanceof ServiceManager.NoOpService))
/*     */           {
/* 588 */             loadTimes.add(Maps.immutableEntry(service, Long.valueOf(stopWatch.elapsed(TimeUnit.MILLISECONDS))));
/*     */           }
/*     */         } 
/*     */       } finally {
/* 592 */         this.monitor.leave();
/*     */       } 
/* 594 */       Collections.sort(loadTimes, (Comparator<? super Map.Entry<Service, Long>>)Ordering.natural().onResultOf(new Function<Map.Entry<Service, Long>, Long>()
/*     */             {
/*     */               public Long apply(Map.Entry<Service, Long> input) {
/* 597 */                 return input.getValue();
/*     */               }
/*     */             }));
/* 600 */       ImmutableMap.Builder<Service, Long> builder = ImmutableMap.builder();
/* 601 */       for (Map.Entry<Service, Long> entry : loadTimes) {
/* 602 */         builder.put(entry);
/*     */       }
/* 604 */       return builder.build();
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
/*     */     void transitionService(Service service, Service.State from, Service.State to) {
/* 619 */       Preconditions.checkNotNull(service);
/* 620 */       Preconditions.checkArgument((from != to));
/* 621 */       this.monitor.enter();
/*     */       try {
/* 623 */         this.transitioned = true;
/* 624 */         if (!this.ready) {
/*     */           return;
/*     */         }
/*     */         
/* 628 */         Preconditions.checkState(this.servicesByState.remove(from, service), "Service %s not at the expected location in the state map %s", new Object[] { service, from });
/*     */         
/* 630 */         Preconditions.checkState(this.servicesByState.put(to, service), "Service %s in the state map unexpectedly at %s", new Object[] { service, to });
/*     */ 
/*     */         
/* 633 */         Stopwatch stopwatch = this.startupTimers.get(service);
/* 634 */         if (from == Service.State.NEW) {
/* 635 */           stopwatch.start();
/*     */         }
/* 637 */         if (to.compareTo(Service.State.RUNNING) >= 0 && stopwatch.isRunning()) {
/*     */           
/* 639 */           stopwatch.stop();
/* 640 */           if (!(service instanceof ServiceManager.NoOpService)) {
/* 641 */             ServiceManager.logger.log(Level.FINE, "Started {0} in {1}.", new Object[] { service, stopwatch });
/*     */           }
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 648 */         if (to == Service.State.FAILED) {
/* 649 */           fireFailedListeners(service);
/*     */         }
/*     */         
/* 652 */         if (this.states.count(Service.State.RUNNING) == this.numberOfServices) {
/*     */ 
/*     */           
/* 655 */           fireHealthyListeners();
/* 656 */         } else if (this.states.count(Service.State.TERMINATED) + this.states.count(Service.State.FAILED) == this.numberOfServices) {
/* 657 */           fireStoppedListeners();
/*     */           
/* 659 */           this.listeners.clear();
/*     */         } 
/*     */       } finally {
/* 662 */         this.monitor.leave();
/*     */         
/* 664 */         executeListeners();
/*     */       } 
/*     */     }
/*     */     
/*     */     @GuardedBy("monitor")
/*     */     void fireStoppedListeners() {
/* 670 */       for (ServiceManager.ListenerExecutorPair pair : this.listeners) {
/* 671 */         this.queuedListeners.add(new Runnable() {
/*     */               public void run() {
/* 673 */                 pair.listener.stopped();
/*     */               }
/*     */             },  pair.executor);
/*     */       } 
/*     */     }
/*     */     
/*     */     @GuardedBy("monitor")
/*     */     void fireHealthyListeners() {
/* 681 */       for (ServiceManager.ListenerExecutorPair pair : this.listeners) {
/* 682 */         this.queuedListeners.add(new Runnable() {
/*     */               public void run() {
/* 684 */                 pair.listener.healthy();
/*     */               }
/*     */             },  pair.executor);
/*     */       } 
/*     */     }
/*     */     
/*     */     @GuardedBy("monitor")
/*     */     void fireFailedListeners(final Service service) {
/* 692 */       for (ServiceManager.ListenerExecutorPair pair : this.listeners) {
/* 693 */         this.queuedListeners.add(new Runnable() {
/*     */               public void run() {
/* 695 */                 pair.listener.failure(service);
/*     */               }
/*     */             },  pair.executor);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     void executeListeners() {
/* 703 */       Preconditions.checkState(!this.monitor.isOccupiedByCurrentThread(), "It is incorrect to execute listeners with the monitor held.");
/*     */       
/* 705 */       this.queuedListeners.execute();
/*     */     }
/*     */     
/*     */     @GuardedBy("monitor")
/*     */     void checkHealthy() {
/* 710 */       if (this.states.count(Service.State.RUNNING) != this.numberOfServices) {
/* 711 */         throw new IllegalStateException("Expected to be healthy after starting. The following services are not running: " + Multimaps.filterKeys(this.servicesByState, Predicates.not(Predicates.equalTo(Service.State.RUNNING))));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class ServiceListener
/*     */     extends Service.Listener
/*     */   {
/*     */     final Service service;
/*     */ 
/*     */     
/*     */     final WeakReference<ServiceManager.ServiceManagerState> state;
/*     */ 
/*     */ 
/*     */     
/*     */     ServiceListener(Service service, WeakReference<ServiceManager.ServiceManagerState> state) {
/* 730 */       this.service = service;
/* 731 */       this.state = state;
/*     */     }
/*     */     
/*     */     public void starting() {
/* 735 */       ServiceManager.ServiceManagerState state = this.state.get();
/* 736 */       if (state != null) {
/* 737 */         state.transitionService(this.service, Service.State.NEW, Service.State.STARTING);
/* 738 */         if (!(this.service instanceof ServiceManager.NoOpService)) {
/* 739 */           ServiceManager.logger.log(Level.FINE, "Starting {0}.", this.service);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*     */     public void running() {
/* 745 */       ServiceManager.ServiceManagerState state = this.state.get();
/* 746 */       if (state != null) {
/* 747 */         state.transitionService(this.service, Service.State.STARTING, Service.State.RUNNING);
/*     */       }
/*     */     }
/*     */     
/*     */     public void stopping(Service.State from) {
/* 752 */       ServiceManager.ServiceManagerState state = this.state.get();
/* 753 */       if (state != null) {
/* 754 */         state.transitionService(this.service, from, Service.State.STOPPING);
/*     */       }
/*     */     }
/*     */     
/*     */     public void terminated(Service.State from) {
/* 759 */       ServiceManager.ServiceManagerState state = this.state.get();
/* 760 */       if (state != null) {
/* 761 */         if (!(this.service instanceof ServiceManager.NoOpService)) {
/* 762 */           ServiceManager.logger.log(Level.FINE, "Service {0} has terminated. Previous state was: {1}", new Object[] { this.service, from });
/*     */         }
/*     */         
/* 765 */         state.transitionService(this.service, from, Service.State.TERMINATED);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void failed(Service.State from, Throwable failure) {
/* 770 */       ServiceManager.ServiceManagerState state = this.state.get();
/* 771 */       if (state != null) {
/*     */ 
/*     */         
/* 774 */         if (!(this.service instanceof ServiceManager.NoOpService)) {
/* 775 */           ServiceManager.logger.log(Level.SEVERE, "Service " + this.service + " has failed in the " + from + " state.", failure);
/*     */         }
/*     */         
/* 778 */         state.transitionService(this.service, from, Service.State.FAILED);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   @Immutable
/*     */   private static final class ListenerExecutorPair {
/*     */     final ServiceManager.Listener listener;
/*     */     final Executor executor;
/*     */     
/*     */     ListenerExecutorPair(ServiceManager.Listener listener, Executor executor) {
/* 789 */       this.listener = listener;
/* 790 */       this.executor = executor;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class NoOpService
/*     */     extends AbstractService
/*     */   {
/*     */     private NoOpService() {}
/*     */ 
/*     */     
/*     */     protected void doStart() {
/* 803 */       notifyStarted(); } protected void doStop() {
/* 804 */       notifyStopped();
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class EmptyServiceManagerWarning
/*     */     extends Throwable {
/*     */     private EmptyServiceManagerWarning() {}
/*     */   }
/*     */   
/*     */   private static final class SynchronizedExecutor
/*     */     implements Executor {
/*     */     private SynchronizedExecutor() {}
/*     */     
/*     */     public synchronized void execute(Runnable command) {
/* 818 */       command.run();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\commo\\util\concurrent\ServiceManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
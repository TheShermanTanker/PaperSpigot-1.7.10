/*     */ package com.google.common.eventbus;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.google.common.base.Supplier;
/*     */ import com.google.common.base.Throwables;
/*     */ import com.google.common.cache.Cache;
/*     */ import com.google.common.cache.CacheBuilder;
/*     */ import com.google.common.cache.CacheLoader;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Multimap;
/*     */ import com.google.common.collect.Multimaps;
/*     */ import com.google.common.collect.SetMultimap;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentLinkedQueue;
/*     */ import java.util.concurrent.CopyOnWriteArraySet;
/*     */ import java.util.concurrent.ExecutionException;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public class EventBus
/*     */ {
/* 114 */   private final SetMultimap<Class<?>, EventHandler> handlersByType = Multimaps.newSetMultimap(new ConcurrentHashMap<Object, Object>(), new Supplier<Set<EventHandler>>()
/*     */       {
/*     */         
/*     */         public Set<EventHandler> get()
/*     */         {
/* 119 */           return new CopyOnWriteArraySet<EventHandler>();
/*     */         }
/*     */       });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Logger logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 134 */   private final HandlerFindingStrategy finder = new AnnotatedHandlerFinder();
/*     */ 
/*     */   
/* 137 */   private final ThreadLocal<ConcurrentLinkedQueue<EventWithHandler>> eventsToDispatch = new ThreadLocal<ConcurrentLinkedQueue<EventWithHandler>>()
/*     */     {
/*     */       protected ConcurrentLinkedQueue<EventBus.EventWithHandler> initialValue()
/*     */       {
/* 141 */         return new ConcurrentLinkedQueue<EventBus.EventWithHandler>();
/*     */       }
/*     */     };
/*     */ 
/*     */   
/* 146 */   private final ThreadLocal<Boolean> isDispatching = new ThreadLocal<Boolean>()
/*     */     {
/*     */       protected Boolean initialValue() {
/* 149 */         return Boolean.valueOf(false);
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 156 */   private Cache<Class<?>, Set<Class<?>>> flattenHierarchyCache = CacheBuilder.newBuilder().weakKeys().build(new CacheLoader<Class<?>, Set<Class<?>>>()
/*     */       {
/*     */ 
/*     */         
/*     */         public Set<Class<?>> load(Class<?> concreteClass) throws Exception
/*     */         {
/* 162 */           List<Class<?>> parents = Lists.newLinkedList();
/* 163 */           Set<Class<?>> classes = Sets.newHashSet();
/*     */           
/* 165 */           parents.add(concreteClass);
/*     */           
/* 167 */           while (!parents.isEmpty()) {
/* 168 */             Class<?> clazz = parents.remove(0);
/* 169 */             classes.add(clazz);
/*     */             
/* 171 */             Class<?> parent = clazz.getSuperclass();
/* 172 */             if (parent != null) {
/* 173 */               parents.add(parent);
/*     */             }
/*     */             
/* 176 */             for (Class<?> iface : clazz.getInterfaces()) {
/* 177 */               parents.add(iface);
/*     */             }
/*     */           } 
/*     */           
/* 181 */           return classes;
/*     */         }
/*     */       });
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EventBus() {
/* 189 */     this("default");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EventBus(String identifier) {
/* 199 */     this.logger = Logger.getLogger(EventBus.class.getName() + "." + identifier);
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
/*     */   public void register(Object object) {
/* 211 */     this.handlersByType.putAll(this.finder.findAllHandlers(object));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unregister(Object object) {
/* 221 */     Multimap<Class<?>, EventHandler> methodsInListener = this.finder.findAllHandlers(object);
/* 222 */     for (Map.Entry<Class<?>, Collection<EventHandler>> entry : (Iterable<Map.Entry<Class<?>, Collection<EventHandler>>>)methodsInListener.asMap().entrySet()) {
/* 223 */       Set<EventHandler> currentHandlers = getHandlersForEventType(entry.getKey());
/* 224 */       Collection<EventHandler> eventMethodsInListener = entry.getValue();
/*     */       
/* 226 */       if (currentHandlers == null || !currentHandlers.containsAll(entry.getValue())) {
/* 227 */         throw new IllegalArgumentException("missing event handler for an annotated method. Is " + object + " registered?");
/*     */       }
/*     */       
/* 230 */       currentHandlers.removeAll(eventMethodsInListener);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void post(Object event) {
/* 246 */     Set<Class<?>> dispatchTypes = flattenHierarchy(event.getClass());
/*     */     
/* 248 */     boolean dispatched = false;
/* 249 */     for (Class<?> eventType : dispatchTypes) {
/* 250 */       Set<EventHandler> wrappers = getHandlersForEventType(eventType);
/*     */       
/* 252 */       if (wrappers != null && !wrappers.isEmpty()) {
/* 253 */         dispatched = true;
/* 254 */         for (EventHandler wrapper : wrappers) {
/* 255 */           enqueueEvent(event, wrapper);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 260 */     if (!dispatched && !(event instanceof DeadEvent)) {
/* 261 */       post(new DeadEvent(this, event));
/*     */     }
/*     */     
/* 264 */     dispatchQueuedEvents();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void enqueueEvent(Object event, EventHandler handler) {
/* 273 */     ((ConcurrentLinkedQueue<EventWithHandler>)this.eventsToDispatch.get()).offer(new EventWithHandler(event, handler));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dispatchQueuedEvents() {
/* 284 */     if (((Boolean)this.isDispatching.get()).booleanValue()) {
/*     */       return;
/*     */     }
/*     */     
/* 288 */     this.isDispatching.set(Boolean.valueOf(true));
/*     */     try {
/*     */       while (true) {
/* 291 */         EventWithHandler eventWithHandler = ((ConcurrentLinkedQueue<EventWithHandler>)this.eventsToDispatch.get()).poll();
/* 292 */         if (eventWithHandler == null) {
/*     */           break;
/*     */         }
/*     */         
/* 296 */         dispatch(eventWithHandler.event, eventWithHandler.handler);
/*     */       } 
/*     */     } finally {
/* 299 */       this.isDispatching.set(Boolean.valueOf(false));
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
/*     */   
/*     */   protected void dispatch(Object event, EventHandler wrapper) {
/*     */     try {
/* 313 */       wrapper.handleEvent(event);
/* 314 */     } catch (InvocationTargetException e) {
/* 315 */       this.logger.log(Level.SEVERE, "Could not dispatch event: " + event + " to handler " + wrapper, e);
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
/*     */ 
/*     */   
/*     */   Set<EventHandler> getHandlersForEventType(Class<?> type) {
/* 329 */     return this.handlersByType.get(type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Set<EventHandler> newHandlerSet() {
/* 340 */     return new CopyOnWriteArraySet<EventHandler>();
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
/*     */   @VisibleForTesting
/*     */   Set<Class<?>> flattenHierarchy(Class<?> concreteClass) {
/*     */     try {
/* 354 */       return (Set<Class<?>>)this.flattenHierarchyCache.get(concreteClass);
/* 355 */     } catch (ExecutionException e) {
/* 356 */       throw Throwables.propagate(e.getCause());
/*     */     } 
/*     */   }
/*     */   
/*     */   static class EventWithHandler {
/*     */     final Object event;
/*     */     final EventHandler handler;
/*     */     
/*     */     public EventWithHandler(Object event, EventHandler handler) {
/* 365 */       this.event = event;
/* 366 */       this.handler = handler;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\eventbus\EventBus.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
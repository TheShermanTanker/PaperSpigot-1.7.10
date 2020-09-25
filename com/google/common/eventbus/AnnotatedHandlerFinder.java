/*    */ package com.google.common.eventbus;
/*    */ 
/*    */ import com.google.common.collect.HashMultimap;
/*    */ import com.google.common.collect.Multimap;
/*    */ import java.lang.reflect.Method;
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
/*    */ 
/*    */ 
/*    */ class AnnotatedHandlerFinder
/*    */   implements HandlerFindingStrategy
/*    */ {
/*    */   public Multimap<Class<?>, EventHandler> findAllHandlers(Object listener) {
/* 39 */     HashMultimap hashMultimap = HashMultimap.create();
/*    */     
/* 41 */     Class<?> clazz = listener.getClass();
/* 42 */     while (clazz != null) {
/* 43 */       for (Method method : clazz.getMethods()) {
/* 44 */         Subscribe annotation = method.<Subscribe>getAnnotation(Subscribe.class);
/*    */         
/* 46 */         if (annotation != null) {
/* 47 */           Class<?>[] parameterTypes = method.getParameterTypes();
/* 48 */           if (parameterTypes.length != 1) {
/* 49 */             throw new IllegalArgumentException("Method " + method + " has @Subscribe annotation, but requires " + parameterTypes.length + " arguments.  Event handler methods " + "must require a single argument.");
/*    */           }
/*    */ 
/*    */ 
/*    */           
/* 54 */           Class<?> eventType = parameterTypes[0];
/* 55 */           EventHandler handler = makeHandler(listener, method);
/*    */           
/* 57 */           hashMultimap.put(eventType, handler);
/*    */         } 
/*    */       } 
/* 60 */       clazz = clazz.getSuperclass();
/*    */     } 
/* 62 */     return (Multimap<Class<?>, EventHandler>)hashMultimap;
/*    */   }
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
/*    */   private static EventHandler makeHandler(Object listener, Method method) {
/*    */     EventHandler wrapper;
/* 78 */     if (methodIsDeclaredThreadSafe(method)) {
/* 79 */       wrapper = new EventHandler(listener, method);
/*    */     } else {
/* 81 */       wrapper = new SynchronizedEventHandler(listener, method);
/*    */     } 
/* 83 */     return wrapper;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static boolean methodIsDeclaredThreadSafe(Method method) {
/* 95 */     return (method.getAnnotation(AllowConcurrentEvents.class) != null);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\eventbus\AnnotatedHandlerFinder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
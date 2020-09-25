/*     */ package net.minecraft.util.org.apache.commons.lang3.event;
/*     */ 
/*     */ import java.lang.reflect.InvocationHandler;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import net.minecraft.util.org.apache.commons.lang3.reflect.MethodUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EventUtils
/*     */ {
/*     */   public static <L> void addEventListener(Object eventSource, Class<L> listenerType, L listener) {
/*     */     try {
/*  50 */       MethodUtils.invokeMethod(eventSource, "add" + listenerType.getSimpleName(), new Object[] { listener });
/*  51 */     } catch (NoSuchMethodException e) {
/*  52 */       throw new IllegalArgumentException("Class " + eventSource.getClass().getName() + " does not have a public add" + listenerType.getSimpleName() + " method which takes a parameter of type " + listenerType.getName() + ".");
/*     */     
/*     */     }
/*  55 */     catch (IllegalAccessException e) {
/*  56 */       throw new IllegalArgumentException("Class " + eventSource.getClass().getName() + " does not have an accessible add" + listenerType.getSimpleName() + " method which takes a parameter of type " + listenerType.getName() + ".");
/*     */     
/*     */     }
/*  59 */     catch (InvocationTargetException e) {
/*  60 */       throw new RuntimeException("Unable to add listener.", e.getCause());
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
/*     */   
/*     */   public static <L> void bindEventsToMethod(Object target, String methodName, Object eventSource, Class<L> listenerType, String... eventTypes) {
/*  77 */     L listener = listenerType.cast(Proxy.newProxyInstance(target.getClass().getClassLoader(), new Class[] { listenerType }, new EventBindingInvocationHandler(target, methodName, eventTypes)));
/*     */     
/*  79 */     addEventListener(eventSource, listenerType, listener);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class EventBindingInvocationHandler
/*     */     implements InvocationHandler
/*     */   {
/*     */     private final Object target;
/*     */     
/*     */     private final String methodName;
/*     */     
/*     */     private final Set<String> eventTypes;
/*     */ 
/*     */     
/*     */     EventBindingInvocationHandler(Object target, String methodName, String[] eventTypes) {
/*  95 */       this.target = target;
/*  96 */       this.methodName = methodName;
/*  97 */       this.eventTypes = new HashSet<String>(Arrays.asList(eventTypes));
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
/*     */     public Object invoke(Object proxy, Method method, Object[] parameters) throws Throwable {
/* 111 */       if (this.eventTypes.isEmpty() || this.eventTypes.contains(method.getName())) {
/* 112 */         if (hasMatchingParametersMethod(method)) {
/* 113 */           return MethodUtils.invokeMethod(this.target, this.methodName, parameters);
/*     */         }
/* 115 */         return MethodUtils.invokeMethod(this.target, this.methodName, new Object[0]);
/*     */       } 
/*     */       
/* 118 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean hasMatchingParametersMethod(Method method) {
/* 128 */       return (MethodUtils.getAccessibleMethod(this.target.getClass(), this.methodName, method.getParameterTypes()) != null);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\org\apache\commons\lang3\event\EventUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
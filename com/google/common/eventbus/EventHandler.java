/*     */ package com.google.common.eventbus;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class EventHandler
/*     */ {
/*     */   private final Object target;
/*     */   private final Method method;
/*     */   
/*     */   EventHandler(Object target, Method method) {
/*  49 */     Preconditions.checkNotNull(target, "EventHandler target cannot be null.");
/*     */     
/*  51 */     Preconditions.checkNotNull(method, "EventHandler method cannot be null.");
/*     */     
/*  53 */     this.target = target;
/*  54 */     this.method = method;
/*  55 */     method.setAccessible(true);
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
/*     */   public void handleEvent(Object event) throws InvocationTargetException {
/*     */     try {
/*  68 */       this.method.invoke(this.target, new Object[] { event });
/*  69 */     } catch (IllegalArgumentException e) {
/*  70 */       throw new Error("Method rejected target/argument: " + event, e);
/*  71 */     } catch (IllegalAccessException e) {
/*  72 */       throw new Error("Method became inaccessible: " + event, e);
/*  73 */     } catch (InvocationTargetException e) {
/*  74 */       if (e.getCause() instanceof Error) {
/*  75 */         throw (Error)e.getCause();
/*     */       }
/*  77 */       throw e;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String toString() {
/*  82 */     return "[wrapper " + this.method + "]";
/*     */   }
/*     */   
/*     */   public int hashCode() {
/*  86 */     int PRIME = 31;
/*  87 */     return (31 + this.method.hashCode()) * 31 + this.target.hashCode();
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj) {
/*  91 */     if (this == obj) {
/*  92 */       return true;
/*     */     }
/*     */     
/*  95 */     if (obj == null) {
/*  96 */       return false;
/*     */     }
/*     */     
/*  99 */     if (getClass() != obj.getClass()) {
/* 100 */       return false;
/*     */     }
/*     */     
/* 103 */     EventHandler other = (EventHandler)obj;
/*     */     
/* 105 */     return (this.method.equals(other.method) && this.target == other.target);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\eventbus\EventHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package org.bukkit.plugin;
/*    */ 
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.EventException;
/*    */ import org.bukkit.event.EventPriority;
/*    */ import org.bukkit.event.Listener;
/*    */ 
/*    */ 
/*    */ public class TimedRegisteredListener
/*    */   extends RegisteredListener
/*    */ {
/*    */   private int count;
/*    */   private long totalTime;
/*    */   private Class<? extends Event> eventClass;
/*    */   private boolean multiple = false;
/*    */   
/*    */   public TimedRegisteredListener(Listener pluginListener, EventExecutor eventExecutor, EventPriority eventPriority, Plugin registeredPlugin, boolean listenCancelled) {
/* 18 */     super(pluginListener, eventExecutor, eventPriority, registeredPlugin, listenCancelled);
/*    */   }
/*    */ 
/*    */   
/*    */   public void callEvent(Event event) throws EventException {
/* 23 */     if (event.isAsynchronous()) {
/* 24 */       super.callEvent(event);
/*    */       return;
/*    */     } 
/* 27 */     this.count++;
/* 28 */     Class<? extends Event> newEventClass = (Class)event.getClass();
/* 29 */     if (this.eventClass == null) {
/* 30 */       this.eventClass = newEventClass;
/* 31 */     } else if (!this.eventClass.equals(newEventClass)) {
/* 32 */       this.multiple = true;
/* 33 */       this.eventClass = getCommonSuperclass(newEventClass, this.eventClass).asSubclass(Event.class);
/*    */     } 
/* 35 */     long start = System.nanoTime();
/* 36 */     super.callEvent(event);
/* 37 */     this.totalTime += System.nanoTime() - start;
/*    */   }
/*    */   
/*    */   private static Class<?> getCommonSuperclass(Class<?> class1, Class<?> class2) {
/* 41 */     while (!class1.isAssignableFrom(class2)) {
/* 42 */       class1 = class1.getSuperclass();
/*    */     }
/* 44 */     return class1;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void reset() {
/* 51 */     this.count = 0;
/* 52 */     this.totalTime = 0L;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getCount() {
/* 61 */     return this.count;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getTotalTime() {
/* 70 */     return this.totalTime;
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
/*    */   public Class<? extends Event> getEventClass() {
/* 85 */     return this.eventClass;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean hasMultiple() {
/* 95 */     return this.multiple;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\plugin\TimedRegisteredListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
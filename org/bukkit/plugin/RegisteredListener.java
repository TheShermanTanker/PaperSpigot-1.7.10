/*    */ package org.bukkit.plugin;
/*    */ 
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.EventException;
/*    */ import org.bukkit.event.EventPriority;
/*    */ import org.bukkit.event.Listener;
/*    */ 
/*    */ 
/*    */ public class RegisteredListener
/*    */ {
/*    */   private final Listener listener;
/*    */   private final EventPriority priority;
/*    */   
/*    */   public RegisteredListener(Listener listener, EventExecutor executor, EventPriority priority, Plugin plugin, boolean ignoreCancelled) {
/* 16 */     this.listener = listener;
/* 17 */     this.priority = priority;
/* 18 */     this.plugin = plugin;
/* 19 */     this.executor = executor;
/* 20 */     this.ignoreCancelled = ignoreCancelled;
/*    */   }
/*    */ 
/*    */   
/*    */   private final Plugin plugin;
/*    */   private final EventExecutor executor;
/*    */   private final boolean ignoreCancelled;
/*    */   
/*    */   public Listener getListener() {
/* 29 */     return this.listener;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Plugin getPlugin() {
/* 38 */     return this.plugin;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EventPriority getPriority() {
/* 47 */     return this.priority;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void callEvent(Event event) throws EventException {
/* 57 */     if (event instanceof Cancellable && (
/* 58 */       (Cancellable)event).isCancelled() && isIgnoringCancelled()) {
/*    */       return;
/*    */     }
/*    */     
/* 62 */     this.executor.execute(this.listener, event);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isIgnoringCancelled() {
/* 71 */     return this.ignoreCancelled;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\plugin\RegisteredListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package org.bukkit.event.server;
/*    */ 
/*    */ import org.bukkit.event.HandlerList;
/*    */ import org.bukkit.plugin.RegisteredServiceProvider;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ServiceUnregisterEvent
/*    */   extends ServiceEvent
/*    */ {
/* 13 */   private static final HandlerList handlers = new HandlerList();
/*    */   
/*    */   public ServiceUnregisterEvent(RegisteredServiceProvider<?> serviceProvider) {
/* 16 */     super(serviceProvider);
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 21 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 25 */     return handlers;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\server\ServiceUnregisterEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
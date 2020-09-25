/*    */ package org.bukkit.event.server;
/*    */ 
/*    */ import org.bukkit.plugin.RegisteredServiceProvider;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ServiceEvent
/*    */   extends ServerEvent
/*    */ {
/*    */   private final RegisteredServiceProvider<?> provider;
/*    */   
/*    */   public ServiceEvent(RegisteredServiceProvider<?> provider) {
/* 13 */     this.provider = provider;
/*    */   }
/*    */   
/*    */   public RegisteredServiceProvider<?> getProvider() {
/* 17 */     return this.provider;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\server\ServiceEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
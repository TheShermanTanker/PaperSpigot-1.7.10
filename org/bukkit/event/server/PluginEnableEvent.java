/*    */ package org.bukkit.event.server;
/*    */ 
/*    */ import org.bukkit.event.HandlerList;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ 
/*    */ public class PluginEnableEvent
/*    */   extends PluginEvent
/*    */ {
/* 10 */   private static final HandlerList handlers = new HandlerList();
/*    */   
/*    */   public PluginEnableEvent(Plugin plugin) {
/* 13 */     super(plugin);
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 18 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 22 */     return handlers;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\server\PluginEnableEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package org.bukkit.event.server;
/*    */ 
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RemoteServerCommandEvent
/*    */   extends ServerCommandEvent
/*    */ {
/* 11 */   private static final HandlerList handlers = new HandlerList();
/*    */   
/*    */   public RemoteServerCommandEvent(CommandSender sender, String command) {
/* 14 */     super(sender, command);
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 19 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 23 */     return handlers;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\server\RemoteServerCommandEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
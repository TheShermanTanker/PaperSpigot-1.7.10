/*    */ package org.bukkit.event.server;
/*    */ 
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.event.HandlerList;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ServerCommandEvent
/*    */   extends ServerEvent
/*    */ {
/* 41 */   private static final HandlerList handlers = new HandlerList();
/*    */   private String command;
/*    */   private final CommandSender sender;
/*    */   
/*    */   public ServerCommandEvent(CommandSender sender, String command) {
/* 46 */     this.command = command;
/* 47 */     this.sender = sender;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommand() {
/* 57 */     return this.command;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setCommand(String message) {
/* 66 */     this.command = message;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CommandSender getSender() {
/* 75 */     return this.sender;
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 80 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 84 */     return handlers;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\server\ServerCommandEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
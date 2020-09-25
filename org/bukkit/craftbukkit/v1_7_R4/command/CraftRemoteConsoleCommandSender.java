/*    */ package org.bukkit.craftbukkit.v1_7_R4.command;
/*    */ 
/*    */ import net.minecraft.server.v1_7_R4.RemoteControlCommandListener;
/*    */ import org.bukkit.command.RemoteConsoleCommandSender;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CraftRemoteConsoleCommandSender
/*    */   extends ServerCommandSender
/*    */   implements RemoteConsoleCommandSender
/*    */ {
/*    */   public void sendMessage(String message) {
/* 13 */     RemoteControlCommandListener.instance.sendMessage(message + "\n");
/*    */   }
/*    */ 
/*    */   
/*    */   public void sendMessage(String[] messages) {
/* 18 */     for (String message : messages) {
/* 19 */       sendMessage(message);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 25 */     return "Rcon";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isOp() {
/* 30 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setOp(boolean value) {
/* 35 */     throw new UnsupportedOperationException("Cannot change operator status of remote controller.");
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\command\CraftRemoteConsoleCommandSender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
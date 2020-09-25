/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandSay
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand() {
/* 15 */     return "say";
/*    */   }
/*    */ 
/*    */   
/*    */   public int a() {
/* 20 */     return 1;
/*    */   }
/*    */ 
/*    */   
/*    */   public String c(ICommandListener paramICommandListener) {
/* 25 */     return "commands.say.usage";
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 30 */     if (paramArrayOfString.length > 0 && paramArrayOfString[0].length() > 0) {
/* 31 */       IChatBaseComponent iChatBaseComponent = a(paramICommandListener, paramArrayOfString, 0, true);
/* 32 */       MinecraftServer.getServer().getPlayerList().sendMessage(new ChatMessage("chat.type.announcement", new Object[] { paramICommandListener.getName(), iChatBaseComponent }));
/*    */       
/*    */       return;
/*    */     } 
/* 36 */     throw new ExceptionUsage("commands.say.usage", new Object[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 41 */     if (paramArrayOfString.length >= 1) {
/* 42 */       return a(paramArrayOfString, MinecraftServer.getServer().getPlayers());
/*    */     }
/*    */     
/* 45 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\CommandSay.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
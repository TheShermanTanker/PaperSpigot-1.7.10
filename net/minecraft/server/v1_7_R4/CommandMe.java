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
/*    */ public class CommandMe
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand() {
/* 15 */     return "me";
/*    */   }
/*    */ 
/*    */   
/*    */   public int a() {
/* 20 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String c(ICommandListener paramICommandListener) {
/* 26 */     return "commands.me.usage";
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 31 */     if (paramArrayOfString.length > 0) {
/* 32 */       IChatBaseComponent iChatBaseComponent = a(paramICommandListener, paramArrayOfString, 0, paramICommandListener.a(1, "me"));
/* 33 */       MinecraftServer.getServer().getPlayerList().sendMessage(new ChatMessage("chat.type.emote", new Object[] { paramICommandListener.getScoreboardDisplayName(), iChatBaseComponent }));
/*    */       
/*    */       return;
/*    */     } 
/* 37 */     throw new ExceptionUsage("commands.me.usage", new Object[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 42 */     return a(paramArrayOfString, MinecraftServer.getServer().getPlayers());
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\CommandMe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
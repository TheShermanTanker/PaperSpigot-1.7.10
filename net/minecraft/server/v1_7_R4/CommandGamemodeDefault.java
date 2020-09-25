/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandGamemodeDefault
/*    */   extends CommandGamemode
/*    */ {
/*    */   public String getCommand() {
/* 13 */     return "defaultgamemode";
/*    */   }
/*    */ 
/*    */   
/*    */   public String c(ICommandListener paramICommandListener) {
/* 18 */     return "commands.defaultgamemode.usage";
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 23 */     if (paramArrayOfString.length > 0) {
/* 24 */       EnumGamemode enumGamemode = h(paramICommandListener, paramArrayOfString[0]);
/* 25 */       a(enumGamemode);
/*    */       
/* 27 */       a(paramICommandListener, this, "commands.defaultgamemode.success", new Object[] { new ChatMessage("gameMode." + enumGamemode.b(), new Object[0]) });
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 32 */     throw new ExceptionUsage("commands.defaultgamemode.usage", new Object[0]);
/*    */   }
/*    */   
/*    */   protected void a(EnumGamemode paramEnumGamemode) {
/* 36 */     MinecraftServer minecraftServer = MinecraftServer.getServer();
/* 37 */     minecraftServer.a(paramEnumGamemode);
/*    */     
/* 39 */     if (minecraftServer.getForceGamemode())
/* 40 */       for (EntityPlayer entityPlayer : (MinecraftServer.getServer().getPlayerList()).players) {
/* 41 */         entityPlayer.a(paramEnumGamemode);
/* 42 */         entityPlayer.fallDistance = 0.0F;
/*    */       }  
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\CommandGamemodeDefault.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
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
/*    */ 
/*    */ 
/*    */ public class CommandGamemode
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand() {
/* 17 */     return "gamemode";
/*    */   }
/*    */ 
/*    */   
/*    */   public int a() {
/* 22 */     return 2;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String c(ICommandListener paramICommandListener) {
/* 28 */     return "commands.gamemode.usage";
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 33 */     if (paramArrayOfString.length > 0) {
/* 34 */       EnumGamemode enumGamemode = h(paramICommandListener, paramArrayOfString[0]);
/* 35 */       EntityPlayer entityPlayer = (paramArrayOfString.length >= 2) ? d(paramICommandListener, paramArrayOfString[1]) : b(paramICommandListener);
/*    */       
/* 37 */       entityPlayer.a(enumGamemode);
/* 38 */       entityPlayer.fallDistance = 0.0F;
/*    */       
/* 40 */       ChatMessage chatMessage = new ChatMessage("gameMode." + enumGamemode.b(), new Object[0]);
/*    */       
/* 42 */       if (entityPlayer != paramICommandListener) {
/* 43 */         a(paramICommandListener, this, 1, "commands.gamemode.success.other", new Object[] { entityPlayer.getName(), chatMessage });
/*    */       } else {
/* 45 */         a(paramICommandListener, this, 1, "commands.gamemode.success.self", new Object[] { chatMessage });
/*    */       } 
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 51 */     throw new ExceptionUsage("commands.gamemode.usage", new Object[0]);
/*    */   }
/*    */   
/*    */   protected EnumGamemode h(ICommandListener paramICommandListener, String paramString) {
/* 55 */     if (paramString.equalsIgnoreCase(EnumGamemode.SURVIVAL.b()) || paramString.equalsIgnoreCase("s"))
/* 56 */       return EnumGamemode.SURVIVAL; 
/* 57 */     if (paramString.equalsIgnoreCase(EnumGamemode.CREATIVE.b()) || paramString.equalsIgnoreCase("c"))
/* 58 */       return EnumGamemode.CREATIVE; 
/* 59 */     if (paramString.equalsIgnoreCase(EnumGamemode.ADVENTURE.b()) || paramString.equalsIgnoreCase("a")) {
/* 60 */       return EnumGamemode.ADVENTURE;
/*    */     }
/* 62 */     return WorldSettings.a(a(paramICommandListener, paramString, 0, (EnumGamemode.values()).length - 2));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 68 */     if (paramArrayOfString.length == 1)
/* 69 */       return a(paramArrayOfString, new String[] { "survival", "creative", "adventure" }); 
/* 70 */     if (paramArrayOfString.length == 2) {
/* 71 */       return a(paramArrayOfString, d());
/*    */     }
/*    */     
/* 74 */     return null;
/*    */   }
/*    */   
/*    */   protected String[] d() {
/* 78 */     return MinecraftServer.getServer().getPlayers();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isListStart(String[] paramArrayOfString, int paramInt) {
/* 83 */     return (paramInt == 1);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\CommandGamemode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
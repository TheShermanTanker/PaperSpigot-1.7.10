/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandSaveAll
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand() {
/* 12 */     return "save-all";
/*    */   }
/*    */ 
/*    */   
/*    */   public String c(ICommandListener paramICommandListener) {
/* 17 */     return "commands.save.usage";
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 22 */     MinecraftServer minecraftServer = MinecraftServer.getServer();
/*    */     
/* 24 */     paramICommandListener.sendMessage(new ChatMessage("commands.save.start", new Object[0]));
/*    */     
/* 26 */     if (minecraftServer.getPlayerList() != null) {
/* 27 */       minecraftServer.getPlayerList().savePlayers();
/*    */     }
/*    */     try {
/*    */       byte b;
/* 31 */       for (b = 0; b < minecraftServer.worldServer.length; b++) {
/* 32 */         if (minecraftServer.worldServer[b] != null) {
/* 33 */           WorldServer worldServer = minecraftServer.worldServer[b];
/* 34 */           boolean bool = worldServer.savingDisabled;
/* 35 */           worldServer.savingDisabled = false;
/* 36 */           worldServer.save(true, null);
/* 37 */           worldServer.savingDisabled = bool;
/*    */         } 
/*    */       } 
/* 40 */       if (paramArrayOfString.length > 0 && "flush".equals(paramArrayOfString[0])) {
/* 41 */         paramICommandListener.sendMessage(new ChatMessage("commands.save.flushStart", new Object[0]));
/* 42 */         for (b = 0; b < minecraftServer.worldServer.length; b++) {
/* 43 */           if (minecraftServer.worldServer[b] != null) {
/* 44 */             WorldServer worldServer = minecraftServer.worldServer[b];
/* 45 */             boolean bool = worldServer.savingDisabled;
/* 46 */             worldServer.savingDisabled = false;
/* 47 */             worldServer.flushSave();
/* 48 */             worldServer.savingDisabled = bool;
/*    */           } 
/*    */         } 
/* 51 */         paramICommandListener.sendMessage(new ChatMessage("commands.save.flushEnd", new Object[0]));
/*    */       } 
/* 53 */     } catch (ExceptionWorldConflict exceptionWorldConflict) {
/* 54 */       a(paramICommandListener, this, "commands.save.failed", new Object[] { exceptionWorldConflict.getMessage() });
/*    */       
/*    */       return;
/*    */     } 
/* 58 */     a(paramICommandListener, this, "commands.save.success", new Object[0]);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\CommandSaveAll.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
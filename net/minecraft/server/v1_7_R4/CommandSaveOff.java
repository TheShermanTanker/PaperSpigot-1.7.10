/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandSaveOff
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand() {
/* 11 */     return "save-off";
/*    */   }
/*    */ 
/*    */   
/*    */   public String c(ICommandListener paramICommandListener) {
/* 16 */     return "commands.save-off.usage";
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 21 */     MinecraftServer minecraftServer = MinecraftServer.getServer();
/* 22 */     boolean bool = false;
/*    */     
/* 24 */     for (byte b = 0; b < minecraftServer.worldServer.length; b++) {
/* 25 */       if (minecraftServer.worldServer[b] != null) {
/* 26 */         WorldServer worldServer = minecraftServer.worldServer[b];
/* 27 */         if (!worldServer.savingDisabled) {
/* 28 */           worldServer.savingDisabled = true;
/* 29 */           bool = true;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 34 */     if (bool) {
/* 35 */       a(paramICommandListener, this, "commands.save.disabled", new Object[0]);
/*    */     } else {
/* 37 */       throw new CommandException("commands.save-off.alreadyOff", new Object[0]);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\CommandSaveOff.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
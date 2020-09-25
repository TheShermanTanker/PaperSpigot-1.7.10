/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandPublish
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand() {
/* 10 */     return "publish";
/*    */   }
/*    */ 
/*    */   
/*    */   public String c(ICommandListener paramICommandListener) {
/* 15 */     return "commands.publish.usage";
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 20 */     String str = MinecraftServer.getServer().a(EnumGamemode.SURVIVAL, false);
/*    */     
/* 22 */     if (str != null) {
/* 23 */       a(paramICommandListener, this, "commands.publish.started", new Object[] { str });
/*    */     } else {
/* 25 */       a(paramICommandListener, this, "commands.publish.failed", new Object[0]);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\CommandPublish.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
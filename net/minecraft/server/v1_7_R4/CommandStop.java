/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandStop
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand() {
/*  9 */     return "stop";
/*    */   }
/*    */ 
/*    */   
/*    */   public String c(ICommandListener paramICommandListener) {
/* 14 */     return "commands.stop.usage";
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 19 */     if ((MinecraftServer.getServer()).worldServer != null) {
/* 20 */       a(paramICommandListener, this, "commands.stop.start", new Object[0]);
/*    */     }
/* 22 */     MinecraftServer.getServer().safeShutdown();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\CommandStop.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
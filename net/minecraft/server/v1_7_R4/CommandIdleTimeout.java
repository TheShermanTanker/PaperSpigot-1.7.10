/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandIdleTimeout
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand() {
/* 11 */     return "setidletimeout";
/*    */   }
/*    */ 
/*    */   
/*    */   public int a() {
/* 16 */     return 3;
/*    */   }
/*    */ 
/*    */   
/*    */   public String c(ICommandListener paramICommandListener) {
/* 21 */     return "commands.setidletimeout.usage";
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 26 */     if (paramArrayOfString.length == 1) {
/* 27 */       int i = a(paramICommandListener, paramArrayOfString[0], 0);
/* 28 */       MinecraftServer.getServer().setIdleTimeout(i);
/* 29 */       a(paramICommandListener, this, "commands.setidletimeout.success", new Object[] { Integer.valueOf(i) });
/*    */       
/*    */       return;
/*    */     } 
/* 33 */     throw new ExceptionUsage("commands.setidletimeout.usage", new Object[0]);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\CommandIdleTimeout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
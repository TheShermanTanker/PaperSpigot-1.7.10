/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandToggleDownfall
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand() {
/* 10 */     return "toggledownfall";
/*    */   }
/*    */ 
/*    */   
/*    */   public int a() {
/* 15 */     return 2;
/*    */   }
/*    */ 
/*    */   
/*    */   public String c(ICommandListener paramICommandListener) {
/* 20 */     return "commands.downfall.usage";
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 25 */     d();
/* 26 */     a(paramICommandListener, this, "commands.downfall.success", new Object[0]);
/*    */   }
/*    */   
/*    */   protected void d() {
/* 30 */     WorldData worldData = (MinecraftServer.getServer()).worldServer[0].getWorldData();
/*    */     
/* 32 */     worldData.setStorm(!worldData.hasStorm());
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\CommandToggleDownfall.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
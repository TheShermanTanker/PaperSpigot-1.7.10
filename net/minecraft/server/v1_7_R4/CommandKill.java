/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandKill
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand() {
/* 12 */     return "kill";
/*    */   }
/*    */ 
/*    */   
/*    */   public int a() {
/* 17 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public String c(ICommandListener paramICommandListener) {
/* 22 */     return "commands.kill.usage";
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 27 */     EntityPlayer entityPlayer = b(paramICommandListener);
/*    */     
/* 29 */     entityPlayer.damageEntity(DamageSource.OUT_OF_WORLD, Float.MAX_VALUE);
/*    */     
/* 31 */     paramICommandListener.sendMessage(new ChatMessage("commands.kill.success", new Object[0]));
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\CommandKill.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
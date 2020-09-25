/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandSeed
/*    */   extends CommandAbstract
/*    */ {
/*    */   public boolean canUse(ICommandListener paramICommandListener) {
/* 12 */     return (MinecraftServer.getServer().N() || super.canUse(paramICommandListener));
/*    */   }
/*    */ 
/*    */   
/*    */   public String getCommand() {
/* 17 */     return "seed";
/*    */   }
/*    */ 
/*    */   
/*    */   public int a() {
/* 22 */     return 2;
/*    */   }
/*    */ 
/*    */   
/*    */   public String c(ICommandListener paramICommandListener) {
/* 27 */     return "commands.seed.usage";
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 32 */     World world = (paramICommandListener instanceof EntityHuman) ? ((EntityHuman)paramICommandListener).world : MinecraftServer.getServer().getWorldServer(0);
/* 33 */     paramICommandListener.sendMessage(new ChatMessage("commands.seed.success", new Object[] { Long.valueOf(world.getSeed()) }));
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\CommandSeed.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
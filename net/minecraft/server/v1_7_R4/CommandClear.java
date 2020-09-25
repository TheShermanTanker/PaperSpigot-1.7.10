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
/*    */ public class CommandClear
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand() {
/* 15 */     return "clear";
/*    */   }
/*    */ 
/*    */   
/*    */   public String c(ICommandListener paramICommandListener) {
/* 20 */     return "commands.clear.usage";
/*    */   }
/*    */ 
/*    */   
/*    */   public int a() {
/* 25 */     return 2;
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 30 */     EntityPlayer entityPlayer = (paramArrayOfString.length == 0) ? b(paramICommandListener) : d(paramICommandListener, paramArrayOfString[0]);
/*    */     
/* 32 */     Item item = (paramArrayOfString.length >= 2) ? f(paramICommandListener, paramArrayOfString[1]) : null;
/* 33 */     boolean bool = (paramArrayOfString.length >= 3) ? a(paramICommandListener, paramArrayOfString[2], 0) : true;
/*    */     
/* 35 */     if (paramArrayOfString.length >= 2 && item == null) {
/* 36 */       throw new CommandException("commands.clear.failure", new Object[] { entityPlayer.getName() });
/*    */     }
/*    */     
/* 39 */     int i = entityPlayer.inventory.a(item, bool);
/* 40 */     entityPlayer.defaultContainer.b();
/* 41 */     if (!entityPlayer.abilities.canInstantlyBuild) entityPlayer.broadcastCarriedItem();
/*    */     
/* 43 */     if (i == 0) {
/* 44 */       throw new CommandException("commands.clear.failure", new Object[] { entityPlayer.getName() });
/*    */     }
/*    */     
/* 47 */     a(paramICommandListener, this, "commands.clear.success", new Object[] { entityPlayer.getName(), Integer.valueOf(i) });
/*    */   }
/*    */ 
/*    */   
/*    */   public List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 52 */     if (paramArrayOfString.length == 1) {
/* 53 */       return a(paramArrayOfString, d());
/*    */     }
/* 55 */     if (paramArrayOfString.length == 2) {
/* 56 */       return a(paramArrayOfString, Item.REGISTRY.keySet());
/*    */     }
/*    */     
/* 59 */     return null;
/*    */   }
/*    */   
/*    */   protected String[] d() {
/* 63 */     return MinecraftServer.getServer().getPlayers();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isListStart(String[] paramArrayOfString, int paramInt) {
/* 68 */     return (paramInt == 0);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\CommandClear.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
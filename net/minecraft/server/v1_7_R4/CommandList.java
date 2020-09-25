/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandList
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand() {
/* 11 */     return "list";
/*    */   }
/*    */ 
/*    */   
/*    */   public int a() {
/* 16 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public String c(ICommandListener paramICommandListener) {
/* 21 */     return "commands.players.usage";
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 26 */     paramICommandListener.sendMessage(new ChatMessage("commands.players.list", new Object[] { Integer.valueOf(MinecraftServer.getServer().C()), Integer.valueOf(MinecraftServer.getServer().D()) }));
/* 27 */     paramICommandListener.sendMessage(new ChatComponentText(MinecraftServer.getServer().getPlayerList().b((paramArrayOfString.length > 0 && "uuids".equalsIgnoreCase(paramArrayOfString[0])))));
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\CommandList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
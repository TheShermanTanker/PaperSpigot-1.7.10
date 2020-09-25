/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandBanList
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand() {
/* 13 */     return "banlist";
/*    */   }
/*    */ 
/*    */   
/*    */   public int a() {
/* 18 */     return 3;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canUse(ICommandListener paramICommandListener) {
/* 24 */     return ((MinecraftServer.getServer().getPlayerList().getIPBans().isEnabled() || MinecraftServer.getServer().getPlayerList().getProfileBans().isEnabled()) && super.canUse(paramICommandListener));
/*    */   }
/*    */ 
/*    */   
/*    */   public String c(ICommandListener paramICommandListener) {
/* 29 */     return "commands.banlist.usage";
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 34 */     if (paramArrayOfString.length >= 1 && paramArrayOfString[0].equalsIgnoreCase("ips")) {
/* 35 */       paramICommandListener.sendMessage(new ChatMessage("commands.banlist.ips", new Object[] { Integer.valueOf((MinecraftServer.getServer().getPlayerList().getIPBans().getEntries()).length) }));
/* 36 */       paramICommandListener.sendMessage(new ChatComponentText(a((Object[])MinecraftServer.getServer().getPlayerList().getIPBans().getEntries())));
/*    */     } else {
/* 38 */       paramICommandListener.sendMessage(new ChatMessage("commands.banlist.players", new Object[] { Integer.valueOf((MinecraftServer.getServer().getPlayerList().getProfileBans().getEntries()).length) }));
/* 39 */       paramICommandListener.sendMessage(new ChatComponentText(a((Object[])MinecraftServer.getServer().getPlayerList().getProfileBans().getEntries())));
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 45 */     if (paramArrayOfString.length == 1) {
/* 46 */       return a(paramArrayOfString, new String[] { "players", "ips" });
/*    */     }
/*    */     
/* 49 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\CommandBanList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
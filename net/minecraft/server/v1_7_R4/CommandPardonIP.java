/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.regex.Matcher;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandPardonIP
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand() {
/* 14 */     return "pardon-ip";
/*    */   }
/*    */ 
/*    */   
/*    */   public int a() {
/* 19 */     return 3;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canUse(ICommandListener paramICommandListener) {
/* 25 */     return (MinecraftServer.getServer().getPlayerList().getIPBans().isEnabled() && super.canUse(paramICommandListener));
/*    */   }
/*    */ 
/*    */   
/*    */   public String c(ICommandListener paramICommandListener) {
/* 30 */     return "commands.unbanip.usage";
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 35 */     if (paramArrayOfString.length == 1 && paramArrayOfString[0].length() > 1) {
/* 36 */       Matcher matcher = CommandBanIp.a.matcher(paramArrayOfString[0]);
/*    */       
/* 38 */       if (matcher.matches()) {
/* 39 */         MinecraftServer.getServer().getPlayerList().getIPBans().remove(paramArrayOfString[0]);
/* 40 */         a(paramICommandListener, this, "commands.unbanip.success", new Object[] { paramArrayOfString[0] });
/*    */         return;
/*    */       } 
/* 43 */       throw new ExceptionInvalidSyntax("commands.unbanip.invalid", new Object[0]);
/*    */     } 
/*    */ 
/*    */     
/* 47 */     throw new ExceptionUsage("commands.unbanip.usage", new Object[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 52 */     if (paramArrayOfString.length == 1) {
/* 53 */       return a(paramArrayOfString, MinecraftServer.getServer().getPlayerList().getIPBans().getEntries());
/*    */     }
/*    */     
/* 56 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\CommandPardonIP.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
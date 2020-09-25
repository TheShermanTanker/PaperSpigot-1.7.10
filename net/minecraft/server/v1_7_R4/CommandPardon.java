/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.util.com.mojang.authlib.GameProfile;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandPardon
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand() {
/* 15 */     return "pardon";
/*    */   }
/*    */ 
/*    */   
/*    */   public int a() {
/* 20 */     return 3;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String c(ICommandListener paramICommandListener) {
/* 26 */     return "commands.unban.usage";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canUse(ICommandListener paramICommandListener) {
/* 31 */     return (MinecraftServer.getServer().getPlayerList().getProfileBans().isEnabled() && super.canUse(paramICommandListener));
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 36 */     if (paramArrayOfString.length == 1 && paramArrayOfString[0].length() > 0) {
/* 37 */       MinecraftServer minecraftServer = MinecraftServer.getServer();
/* 38 */       GameProfile gameProfile = minecraftServer.getPlayerList().getProfileBans().a(paramArrayOfString[0]);
/* 39 */       if (gameProfile == null) {
/* 40 */         throw new CommandException("commands.unban.failed", new Object[] { paramArrayOfString[0] });
/*    */       }
/*    */       
/* 43 */       minecraftServer.getPlayerList().getProfileBans().remove(gameProfile);
/* 44 */       a(paramICommandListener, this, "commands.unban.success", new Object[] { paramArrayOfString[0] });
/*    */       
/*    */       return;
/*    */     } 
/* 48 */     throw new ExceptionUsage("commands.unban.usage", new Object[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 53 */     if (paramArrayOfString.length == 1) {
/* 54 */       return a(paramArrayOfString, MinecraftServer.getServer().getPlayerList().getProfileBans().getEntries());
/*    */     }
/*    */     
/* 57 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\CommandPardon.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
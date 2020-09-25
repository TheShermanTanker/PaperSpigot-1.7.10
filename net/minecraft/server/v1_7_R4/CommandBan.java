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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandBan
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand() {
/* 19 */     return "ban";
/*    */   }
/*    */ 
/*    */   
/*    */   public int a() {
/* 24 */     return 3;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String c(ICommandListener paramICommandListener) {
/* 30 */     return "commands.ban.usage";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canUse(ICommandListener paramICommandListener) {
/* 35 */     return (MinecraftServer.getServer().getPlayerList().getProfileBans().isEnabled() && super.canUse(paramICommandListener));
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 40 */     if (paramArrayOfString.length >= 1 && paramArrayOfString[0].length() > 0) {
/* 41 */       MinecraftServer minecraftServer = MinecraftServer.getServer();
/* 42 */       GameProfile gameProfile = minecraftServer.getUserCache().getProfile(paramArrayOfString[0]);
/* 43 */       if (gameProfile == null) {
/* 44 */         throw new CommandException("commands.ban.failed", new Object[] { paramArrayOfString[0] });
/*    */       }
/*    */       
/* 47 */       String str = null;
/* 48 */       if (paramArrayOfString.length >= 2) {
/* 49 */         str = a(paramICommandListener, paramArrayOfString, 1).c();
/*    */       }
/*    */       
/* 52 */       GameProfileBanEntry gameProfileBanEntry = new GameProfileBanEntry(gameProfile, null, paramICommandListener.getName(), null, str);
/* 53 */       minecraftServer.getPlayerList().getProfileBans().add(gameProfileBanEntry);
/*    */       
/* 55 */       EntityPlayer entityPlayer = minecraftServer.getPlayerList().getPlayer(paramArrayOfString[0]);
/* 56 */       if (entityPlayer != null) {
/* 57 */         entityPlayer.playerConnection.disconnect("You are banned from this server.");
/*    */       }
/*    */       
/* 60 */       a(paramICommandListener, this, "commands.ban.success", new Object[] { paramArrayOfString[0] });
/*    */       
/*    */       return;
/*    */     } 
/* 64 */     throw new ExceptionUsage("commands.ban.usage", new Object[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 69 */     if (paramArrayOfString.length >= 1) {
/* 70 */       return a(paramArrayOfString, MinecraftServer.getServer().getPlayers());
/*    */     }
/*    */     
/* 73 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\CommandBan.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
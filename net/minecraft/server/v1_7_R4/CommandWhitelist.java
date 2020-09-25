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
/*    */ public class CommandWhitelist
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand() {
/* 16 */     return "whitelist";
/*    */   }
/*    */ 
/*    */   
/*    */   public int a() {
/* 21 */     return 3;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String c(ICommandListener paramICommandListener) {
/* 27 */     return "commands.whitelist.usage";
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 32 */     if (paramArrayOfString.length >= 1) {
/* 33 */       MinecraftServer minecraftServer = MinecraftServer.getServer();
/* 34 */       if (paramArrayOfString[0].equals("on")) {
/* 35 */         minecraftServer.getPlayerList().setHasWhitelist(true);
/* 36 */         a(paramICommandListener, this, "commands.whitelist.enabled", new Object[0]); return;
/*    */       } 
/* 38 */       if (paramArrayOfString[0].equals("off")) {
/* 39 */         minecraftServer.getPlayerList().setHasWhitelist(false);
/* 40 */         a(paramICommandListener, this, "commands.whitelist.disabled", new Object[0]); return;
/*    */       } 
/* 42 */       if (paramArrayOfString[0].equals("list")) {
/* 43 */         paramICommandListener.sendMessage(new ChatMessage("commands.whitelist.list", new Object[] { Integer.valueOf((minecraftServer.getPlayerList().getWhitelisted()).length), Integer.valueOf((minecraftServer.getPlayerList().getSeenPlayers()).length) }));
/*    */         
/* 45 */         String[] arrayOfString = minecraftServer.getPlayerList().getWhitelisted();
/* 46 */         paramICommandListener.sendMessage(new ChatComponentText(a((Object[])arrayOfString))); return;
/*    */       } 
/* 48 */       if (paramArrayOfString[0].equals("add")) {
/* 49 */         if (paramArrayOfString.length < 2) {
/* 50 */           throw new ExceptionUsage("commands.whitelist.add.usage", new Object[0]);
/*    */         }
/*    */         
/* 53 */         GameProfile gameProfile = minecraftServer.getUserCache().getProfile(paramArrayOfString[1]);
/* 54 */         if (gameProfile == null) {
/* 55 */           throw new CommandException("commands.whitelist.add.failed", new Object[] { paramArrayOfString[1] });
/*    */         }
/* 57 */         minecraftServer.getPlayerList().addWhitelist(gameProfile);
/* 58 */         a(paramICommandListener, this, "commands.whitelist.add.success", new Object[] { paramArrayOfString[1] }); return;
/*    */       } 
/* 60 */       if (paramArrayOfString[0].equals("remove")) {
/* 61 */         if (paramArrayOfString.length < 2) {
/* 62 */           throw new ExceptionUsage("commands.whitelist.remove.usage", new Object[0]);
/*    */         }
/*    */         
/* 65 */         GameProfile gameProfile = minecraftServer.getPlayerList().getWhitelist().a(paramArrayOfString[1]);
/* 66 */         if (gameProfile == null) {
/* 67 */           throw new CommandException("commands.whitelist.remove.failed", new Object[] { paramArrayOfString[1] });
/*    */         }
/* 69 */         minecraftServer.getPlayerList().removeWhitelist(gameProfile);
/* 70 */         a(paramICommandListener, this, "commands.whitelist.remove.success", new Object[] { paramArrayOfString[1] }); return;
/*    */       } 
/* 72 */       if (paramArrayOfString[0].equals("reload")) {
/* 73 */         minecraftServer.getPlayerList().reloadWhitelist();
/* 74 */         a(paramICommandListener, this, "commands.whitelist.reloaded", new Object[0]);
/*    */         
/*    */         return;
/*    */       } 
/*    */     } 
/* 79 */     throw new ExceptionUsage("commands.whitelist.usage", new Object[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 84 */     if (paramArrayOfString.length == 1) {
/* 85 */       return a(paramArrayOfString, new String[] { "on", "off", "list", "add", "remove", "reload" });
/*    */     }
/*    */     
/* 88 */     if (paramArrayOfString.length == 2) {
/* 89 */       if (paramArrayOfString[0].equals("remove"))
/* 90 */         return a(paramArrayOfString, MinecraftServer.getServer().getPlayerList().getWhitelisted()); 
/* 91 */       if (paramArrayOfString[0].equals("add")) {
/* 92 */         return a(paramArrayOfString, MinecraftServer.getServer().getUserCache().a());
/*    */       }
/*    */     } 
/*    */     
/* 96 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\CommandWhitelist.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
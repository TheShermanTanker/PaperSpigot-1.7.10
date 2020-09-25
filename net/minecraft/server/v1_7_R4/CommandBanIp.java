/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandBanIp
/*    */   extends CommandAbstract
/*    */ {
/* 18 */   public static final Pattern a = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommand() {
/* 23 */     return "ban-ip";
/*    */   }
/*    */ 
/*    */   
/*    */   public int a() {
/* 28 */     return 3;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canUse(ICommandListener paramICommandListener) {
/* 33 */     return (MinecraftServer.getServer().getPlayerList().getIPBans().isEnabled() && super.canUse(paramICommandListener));
/*    */   }
/*    */ 
/*    */   
/*    */   public String c(ICommandListener paramICommandListener) {
/* 38 */     return "commands.banip.usage";
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 43 */     if (paramArrayOfString.length >= 1 && paramArrayOfString[0].length() > 1) {
/* 44 */       Matcher matcher = a.matcher(paramArrayOfString[0]);
/* 45 */       IChatBaseComponent iChatBaseComponent = null;
/*    */       
/* 47 */       if (paramArrayOfString.length >= 2) {
/* 48 */         iChatBaseComponent = a(paramICommandListener, paramArrayOfString, 1);
/*    */       }
/*    */       
/* 51 */       if (matcher.matches()) {
/* 52 */         a(paramICommandListener, paramArrayOfString[0], (iChatBaseComponent == null) ? null : iChatBaseComponent.c());
/*    */       } else {
/* 54 */         EntityPlayer entityPlayer = MinecraftServer.getServer().getPlayerList().getPlayer(paramArrayOfString[0]);
/*    */         
/* 56 */         if (entityPlayer == null) {
/* 57 */           throw new ExceptionPlayerNotFound("commands.banip.invalid", new Object[0]);
/*    */         }
/*    */         
/* 60 */         a(paramICommandListener, entityPlayer.s(), (iChatBaseComponent == null) ? null : iChatBaseComponent.c());
/*    */       } 
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 66 */     throw new ExceptionUsage("commands.banip.usage", new Object[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 71 */     if (paramArrayOfString.length == 1) {
/* 72 */       return a(paramArrayOfString, MinecraftServer.getServer().getPlayers());
/*    */     }
/*    */     
/* 75 */     return null;
/*    */   }
/*    */   
/*    */   protected void a(ICommandListener paramICommandListener, String paramString1, String paramString2) {
/* 79 */     IpBanEntry ipBanEntry = new IpBanEntry(paramString1, null, paramICommandListener.getName(), null, paramString2);
/* 80 */     MinecraftServer.getServer().getPlayerList().getIPBans().add(ipBanEntry);
/*    */     
/* 82 */     List list = MinecraftServer.getServer().getPlayerList().b(paramString1);
/* 83 */     String[] arrayOfString = new String[list.size()];
/* 84 */     byte b = 0;
/*    */     
/* 86 */     for (EntityPlayer entityPlayer : list) {
/* 87 */       entityPlayer.playerConnection.disconnect("You have been IP banned.");
/* 88 */       arrayOfString[b++] = entityPlayer.getName();
/*    */     } 
/*    */     
/* 91 */     if (list.isEmpty()) {
/* 92 */       a(paramICommandListener, this, "commands.banip.success", new Object[] { paramString1 });
/*    */     } else {
/* 94 */       a(paramICommandListener, this, "commands.banip.success.players", new Object[] { paramString1, a((Object[])arrayOfString) });
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\CommandBanIp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
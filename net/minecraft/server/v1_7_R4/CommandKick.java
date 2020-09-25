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
/*    */ public class CommandKick
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand() {
/* 15 */     return "kick";
/*    */   }
/*    */ 
/*    */   
/*    */   public int a() {
/* 20 */     return 2;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String c(ICommandListener paramICommandListener) {
/* 26 */     return "commands.kick.usage";
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 31 */     if (paramArrayOfString.length > 0 && paramArrayOfString[0].length() > 1) {
/* 32 */       EntityPlayer entityPlayer = MinecraftServer.getServer().getPlayerList().getPlayer(paramArrayOfString[0]);
/* 33 */       String str = "Kicked by an operator.";
/* 34 */       boolean bool = false;
/*    */       
/* 36 */       if (entityPlayer == null) {
/* 37 */         throw new ExceptionPlayerNotFound();
/*    */       }
/*    */       
/* 40 */       if (paramArrayOfString.length >= 2) {
/* 41 */         str = a(paramICommandListener, paramArrayOfString, 1).c();
/* 42 */         bool = true;
/*    */       } 
/*    */       
/* 45 */       entityPlayer.playerConnection.disconnect(str);
/*    */       
/* 47 */       if (bool) {
/* 48 */         a(paramICommandListener, this, "commands.kick.success.reason", new Object[] { entityPlayer.getName(), str });
/*    */       } else {
/* 50 */         a(paramICommandListener, this, "commands.kick.success", new Object[] { entityPlayer.getName() });
/*    */       } 
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 56 */     throw new ExceptionUsage("commands.kick.usage", new Object[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 61 */     if (paramArrayOfString.length >= 1) {
/* 62 */       return a(paramArrayOfString, MinecraftServer.getServer().getPlayers());
/*    */     }
/*    */     
/* 65 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\CommandKick.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
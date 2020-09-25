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
/*    */ public class CommandDeop
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand() {
/* 15 */     return "deop";
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
/* 26 */     return "commands.deop.usage";
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 31 */     if (paramArrayOfString.length == 1 && paramArrayOfString[0].length() > 0) {
/* 32 */       MinecraftServer minecraftServer = MinecraftServer.getServer();
/* 33 */       GameProfile gameProfile = minecraftServer.getPlayerList().getOPs().a(paramArrayOfString[0]);
/* 34 */       if (gameProfile == null) {
/* 35 */         throw new CommandException("commands.deop.failed", new Object[] { paramArrayOfString[0] });
/*    */       }
/*    */       
/* 38 */       minecraftServer.getPlayerList().removeOp(gameProfile);
/* 39 */       a(paramICommandListener, this, "commands.deop.success", new Object[] { paramArrayOfString[0] });
/*    */       
/*    */       return;
/*    */     } 
/* 43 */     throw new ExceptionUsage("commands.deop.usage", new Object[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 48 */     if (paramArrayOfString.length == 1) {
/* 49 */       return a(paramArrayOfString, MinecraftServer.getServer().getPlayerList().n());
/*    */     }
/*    */     
/* 52 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\CommandDeop.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
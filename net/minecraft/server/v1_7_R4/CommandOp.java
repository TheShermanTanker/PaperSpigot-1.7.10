/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.util.com.mojang.authlib.GameProfile;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandOp
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand() {
/* 15 */     return "op";
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
/* 26 */     return "commands.op.usage";
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 31 */     if (paramArrayOfString.length == 1 && paramArrayOfString[0].length() > 0) {
/* 32 */       MinecraftServer minecraftServer = MinecraftServer.getServer();
/* 33 */       GameProfile gameProfile = minecraftServer.getUserCache().getProfile(paramArrayOfString[0]);
/* 34 */       if (gameProfile == null) {
/* 35 */         throw new CommandException("commands.op.failed", new Object[] { paramArrayOfString[0] });
/*    */       }
/*    */       
/* 38 */       minecraftServer.getPlayerList().addOp(gameProfile);
/* 39 */       a(paramICommandListener, this, "commands.op.success", new Object[] { paramArrayOfString[0] });
/*    */       
/*    */       return;
/*    */     } 
/* 43 */     throw new ExceptionUsage("commands.op.usage", new Object[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 48 */     if (paramArrayOfString.length == 1) {
/* 49 */       String str = paramArrayOfString[paramArrayOfString.length - 1];
/* 50 */       ArrayList<String> arrayList = new ArrayList();
/*    */       
/* 52 */       for (GameProfile gameProfile : MinecraftServer.getServer().F()) {
/* 53 */         if (!MinecraftServer.getServer().getPlayerList().isOp(gameProfile) && a(str, gameProfile.getName())) {
/* 54 */           arrayList.add(gameProfile.getName());
/*    */         }
/*    */       } 
/*    */       
/* 58 */       return arrayList;
/*    */     } 
/*    */     
/* 61 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\CommandOp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
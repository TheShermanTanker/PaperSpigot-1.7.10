/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandXp
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand() {
/* 14 */     return "xp";
/*    */   }
/*    */ 
/*    */   
/*    */   public int a() {
/* 19 */     return 2;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String c(ICommandListener paramICommandListener) {
/* 25 */     return "commands.xp.usage";
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 30 */     if (paramArrayOfString.length > 0) {
/*    */       EntityPlayer entityPlayer;
/* 32 */       String str = paramArrayOfString[0];
/*    */       
/* 34 */       boolean bool1 = (str.endsWith("l") || str.endsWith("L")) ? true : false;
/* 35 */       if (bool1 && str.length() > 1) str = str.substring(0, str.length() - 1);
/*    */       
/* 37 */       int i = a(paramICommandListener, str);
/* 38 */       boolean bool2 = (i < 0) ? true : false;
/*    */       
/* 40 */       if (bool2) i *= -1;
/*    */       
/* 42 */       if (paramArrayOfString.length > 1) {
/* 43 */         entityPlayer = d(paramICommandListener, paramArrayOfString[1]);
/*    */       } else {
/* 45 */         entityPlayer = b(paramICommandListener);
/*    */       } 
/*    */       
/* 48 */       if (bool1) {
/* 49 */         if (bool2) {
/* 50 */           entityPlayer.levelDown(-i);
/* 51 */           a(paramICommandListener, this, "commands.xp.success.negative.levels", new Object[] { Integer.valueOf(i), entityPlayer.getName() });
/*    */         } else {
/* 53 */           entityPlayer.levelDown(i);
/* 54 */           a(paramICommandListener, this, "commands.xp.success.levels", new Object[] { Integer.valueOf(i), entityPlayer.getName() });
/*    */         } 
/*    */       } else {
/* 57 */         if (bool2) {
/* 58 */           throw new ExceptionUsage("commands.xp.failure.widthdrawXp", new Object[0]);
/*    */         }
/* 60 */         entityPlayer.giveExp(i);
/* 61 */         a(paramICommandListener, this, "commands.xp.success", new Object[] { Integer.valueOf(i), entityPlayer.getName() });
/*    */       } 
/*    */ 
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 68 */     throw new ExceptionUsage("commands.xp.usage", new Object[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 73 */     if (paramArrayOfString.length == 2) {
/* 74 */       return a(paramArrayOfString, d());
/*    */     }
/*    */     
/* 77 */     return null;
/*    */   }
/*    */   
/*    */   protected String[] d() {
/* 81 */     return MinecraftServer.getServer().getPlayers();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isListStart(String[] paramArrayOfString, int paramInt) {
/* 86 */     return (paramInt == 1);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\CommandXp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
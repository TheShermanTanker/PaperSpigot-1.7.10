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
/*    */ public class CommandGamerule
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand() {
/* 15 */     return "gamerule";
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
/* 26 */     return "commands.gamerule.usage";
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 31 */     if (paramArrayOfString.length == 2) {
/* 32 */       String str1 = paramArrayOfString[0];
/* 33 */       String str2 = paramArrayOfString[1];
/*    */       
/* 35 */       GameRules gameRules = d();
/*    */       
/* 37 */       if (gameRules.contains(str1)) {
/* 38 */         gameRules.set(str1, str2);
/* 39 */         a(paramICommandListener, this, "commands.gamerule.success", new Object[0]);
/*    */       } else {
/* 41 */         a(paramICommandListener, this, "commands.gamerule.norule", new Object[] { str1 });
/*    */       } 
/*    */       return;
/*    */     } 
/* 45 */     if (paramArrayOfString.length == 1) {
/* 46 */       String str = paramArrayOfString[0];
/* 47 */       GameRules gameRules = d();
/*    */       
/* 49 */       if (gameRules.contains(str)) {
/* 50 */         String str1 = gameRules.get(str);
/* 51 */         paramICommandListener.sendMessage((new ChatComponentText(str)).a(" = ").a(str1));
/*    */       } else {
/* 53 */         a(paramICommandListener, this, "commands.gamerule.norule", new Object[] { str });
/*    */       } 
/*    */       return;
/*    */     } 
/* 57 */     if (paramArrayOfString.length == 0) {
/* 58 */       GameRules gameRules = d();
/* 59 */       paramICommandListener.sendMessage(new ChatComponentText(a((Object[])gameRules.getGameRules())));
/*    */       
/*    */       return;
/*    */     } 
/* 63 */     throw new ExceptionUsage("commands.gamerule.usage", new Object[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 68 */     if (paramArrayOfString.length == 1)
/* 69 */       return a(paramArrayOfString, d().getGameRules()); 
/* 70 */     if (paramArrayOfString.length == 2) {
/* 71 */       return a(paramArrayOfString, new String[] { "true", "false" });
/*    */     }
/*    */     
/* 74 */     return null;
/*    */   }
/*    */   
/*    */   private GameRules d() {
/* 78 */     return MinecraftServer.getServer().getWorldServer(0).getGameRules();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\CommandGamerule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
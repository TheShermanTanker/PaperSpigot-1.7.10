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
/*    */ public class CommandDifficulty
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand() {
/* 15 */     return "difficulty";
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
/* 26 */     return "commands.difficulty.usage";
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 31 */     if (paramArrayOfString.length > 0) {
/* 32 */       EnumDifficulty enumDifficulty = h(paramICommandListener, paramArrayOfString[0]);
/*    */       
/* 34 */       MinecraftServer.getServer().a(enumDifficulty);
/*    */       
/* 36 */       a(paramICommandListener, this, "commands.difficulty.success", new Object[] { new ChatMessage(enumDifficulty.b(), new Object[0]) });
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 41 */     throw new ExceptionUsage("commands.difficulty.usage", new Object[0]);
/*    */   }
/*    */   
/*    */   protected EnumDifficulty h(ICommandListener paramICommandListener, String paramString) {
/* 45 */     if (paramString.equalsIgnoreCase("peaceful") || paramString.equalsIgnoreCase("p"))
/* 46 */       return EnumDifficulty.PEACEFUL; 
/* 47 */     if (paramString.equalsIgnoreCase("easy") || paramString.equalsIgnoreCase("e"))
/* 48 */       return EnumDifficulty.EASY; 
/* 49 */     if (paramString.equalsIgnoreCase("normal") || paramString.equalsIgnoreCase("n"))
/* 50 */       return EnumDifficulty.NORMAL; 
/* 51 */     if (paramString.equalsIgnoreCase("hard") || paramString.equalsIgnoreCase("h")) {
/* 52 */       return EnumDifficulty.HARD;
/*    */     }
/* 54 */     return EnumDifficulty.getById(a(paramICommandListener, paramString, 0, 3));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 60 */     if (paramArrayOfString.length == 1) {
/* 61 */       return a(paramArrayOfString, new String[] { "peaceful", "easy", "normal", "hard" });
/*    */     }
/*    */     
/* 64 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\CommandDifficulty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
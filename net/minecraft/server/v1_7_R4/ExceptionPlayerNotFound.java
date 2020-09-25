/*   */ package net.minecraft.server.v1_7_R4;
/*   */ 
/*   */ public class ExceptionPlayerNotFound extends CommandException {
/*   */   public ExceptionPlayerNotFound() {
/* 5 */     this("commands.generic.player.notFound", new Object[0]);
/*   */   }
/*   */   
/*   */   public ExceptionPlayerNotFound(String paramString, Object... paramVarArgs) {
/* 9 */     super(paramString, paramVarArgs);
/*   */   }
/*   */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ExceptionPlayerNotFound.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
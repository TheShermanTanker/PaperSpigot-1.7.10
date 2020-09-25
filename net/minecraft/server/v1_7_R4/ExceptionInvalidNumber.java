/*   */ package net.minecraft.server.v1_7_R4;
/*   */ 
/*   */ public class ExceptionInvalidNumber extends CommandException {
/*   */   public ExceptionInvalidNumber() {
/* 5 */     this("commands.generic.num.invalid", new Object[0]);
/*   */   }
/*   */   
/*   */   public ExceptionInvalidNumber(String paramString, Object... paramVarArgs) {
/* 9 */     super(paramString, paramVarArgs);
/*   */   }
/*   */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ExceptionInvalidNumber.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*   */ package net.minecraft.server.v1_7_R4;
/*   */ 
/*   */ public class ExceptionUnknownCommand extends CommandException {
/*   */   public ExceptionUnknownCommand() {
/* 5 */     this("commands.generic.notFound", new Object[0]);
/*   */   }
/*   */   
/*   */   public ExceptionUnknownCommand(String paramString, Object... paramVarArgs) {
/* 9 */     super(paramString, paramVarArgs);
/*   */   }
/*   */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ExceptionUnknownCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
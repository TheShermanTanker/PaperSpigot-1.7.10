/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class CommandException extends RuntimeException {
/*    */   private Object[] a;
/*    */   
/*    */   public CommandException(String paramString, Object... paramVarArgs) {
/*  7 */     super(paramString);
/*    */     
/*  9 */     this.a = paramVarArgs;
/*    */   }
/*    */   
/*    */   public Object[] getArgs() {
/* 13 */     return this.a;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\CommandException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
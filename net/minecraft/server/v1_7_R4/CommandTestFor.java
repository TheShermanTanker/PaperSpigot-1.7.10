/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandTestFor
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand() {
/* 13 */     return "testfor";
/*    */   }
/*    */ 
/*    */   
/*    */   public int a() {
/* 18 */     return 2;
/*    */   }
/*    */ 
/*    */   
/*    */   public String c(ICommandListener paramICommandListener) {
/* 23 */     return "commands.testfor.usage";
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 28 */     if (paramArrayOfString.length != 1) throw new ExceptionUsage("commands.testfor.usage", new Object[0]); 
/* 29 */     if (!(paramICommandListener instanceof CommandBlockListenerAbstract)) throw new CommandException("commands.testfor.failed", new Object[0]); 
/* 30 */     d(paramICommandListener, paramArrayOfString[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isListStart(String[] paramArrayOfString, int paramInt) {
/* 35 */     return (paramInt == 0);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\CommandTestFor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
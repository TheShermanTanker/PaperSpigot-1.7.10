/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.util.com.google.gson.JsonParseException;
/*    */ import net.minecraft.util.org.apache.commons.lang3.exception.ExceptionUtils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandTellRaw
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand() {
/* 18 */     return "tellraw";
/*    */   }
/*    */ 
/*    */   
/*    */   public int a() {
/* 23 */     return 2;
/*    */   }
/*    */ 
/*    */   
/*    */   public String c(ICommandListener paramICommandListener) {
/* 28 */     return "commands.tellraw.usage";
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 33 */     if (paramArrayOfString.length < 2) throw new ExceptionUsage("commands.tellraw.usage", new Object[0]);
/*    */     
/* 35 */     EntityPlayer entityPlayer = d(paramICommandListener, paramArrayOfString[0]);
/* 36 */     String str = b(paramICommandListener, paramArrayOfString, 1);
/*    */     
/*    */     try {
/* 39 */       IChatBaseComponent iChatBaseComponent = ChatSerializer.a(str);
/* 40 */       entityPlayer.sendMessage(iChatBaseComponent);
/* 41 */     } catch (JsonParseException jsonParseException) {
/* 42 */       Throwable throwable = ExceptionUtils.getRootCause((Throwable)jsonParseException);
/* 43 */       throw new ExceptionInvalidSyntax("commands.tellraw.jsonException", new Object[] { (throwable == null) ? "" : throwable.getMessage() });
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 49 */     if (paramArrayOfString.length == 1) {
/* 50 */       return a(paramArrayOfString, MinecraftServer.getServer().getPlayers());
/*    */     }
/*    */     
/* 53 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isListStart(String[] paramArrayOfString, int paramInt) {
/* 58 */     return (paramInt == 0);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\CommandTellRaw.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
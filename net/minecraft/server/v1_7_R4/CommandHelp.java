/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandHelp
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand() {
/* 17 */     return "help";
/*    */   }
/*    */ 
/*    */   
/*    */   public int a() {
/* 22 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String c(ICommandListener paramICommandListener) {
/* 28 */     return "commands.help.usage";
/*    */   }
/*    */ 
/*    */   
/*    */   public List b() {
/* 33 */     return Arrays.asList(new String[] { "?" });
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 38 */     List<ICommand> list = d(paramICommandListener);
/* 39 */     byte b1 = 7;
/* 40 */     int i = (list.size() - 1) / b1;
/* 41 */     byte b2 = 0;
/*    */     
/*    */     try {
/* 44 */       b2 = (paramArrayOfString.length == 0) ? 0 : (a(paramICommandListener, paramArrayOfString[0], 1, i + 1) - 1);
/* 45 */     } catch (ExceptionInvalidNumber exceptionInvalidNumber) {
/*    */       
/* 47 */       Map map = d();
/* 48 */       ICommand iCommand = (ICommand)map.get(paramArrayOfString[0]);
/*    */       
/* 50 */       if (iCommand != null)
/*    */       {
/* 52 */         throw new ExceptionUsage(iCommand.c(paramICommandListener), new Object[0]); } 
/* 53 */       if (MathHelper.a(paramArrayOfString[0], -1) != -1) {
/* 54 */         throw exceptionInvalidNumber;
/*    */       }
/* 56 */       throw new ExceptionUnknownCommand();
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 61 */     int j = Math.min((b2 + 1) * b1, list.size());
/*    */     
/* 63 */     ChatMessage chatMessage = new ChatMessage("commands.help.header", new Object[] { Integer.valueOf(b2 + 1), Integer.valueOf(i + 1) });
/* 64 */     chatMessage.getChatModifier().setColor(EnumChatFormat.DARK_GREEN);
/* 65 */     paramICommandListener.sendMessage(chatMessage);
/*    */     
/* 67 */     for (int k = b2 * b1; k < j; k++) {
/* 68 */       ICommand iCommand = list.get(k);
/*    */       
/* 70 */       ChatMessage chatMessage1 = new ChatMessage(iCommand.c(paramICommandListener), new Object[0]);
/* 71 */       chatMessage1.getChatModifier().setChatClickable(new ChatClickable(EnumClickAction.SUGGEST_COMMAND, "/" + iCommand.getCommand() + " "));
/* 72 */       paramICommandListener.sendMessage(chatMessage1);
/*    */     } 
/*    */     
/* 75 */     if (b2 == 0 && paramICommandListener instanceof EntityHuman) {
/* 76 */       ChatMessage chatMessage1 = new ChatMessage("commands.help.footer", new Object[0]);
/* 77 */       chatMessage1.getChatModifier().setColor(EnumChatFormat.GREEN);
/* 78 */       paramICommandListener.sendMessage(chatMessage1);
/*    */     } 
/*    */   }
/*    */   
/*    */   protected List d(ICommandListener paramICommandListener) {
/* 83 */     List<Comparable> list = MinecraftServer.getServer().getCommandHandler().a(paramICommandListener);
/* 84 */     Collections.sort(list);
/* 85 */     return list;
/*    */   }
/*    */   
/*    */   protected Map d() {
/* 89 */     return MinecraftServer.getServer().getCommandHandler().a();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\CommandHelp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandTell
/*    */   extends CommandAbstract
/*    */ {
/*    */   public List b() {
/* 19 */     return Arrays.asList(new String[] { "w", "msg" });
/*    */   }
/*    */ 
/*    */   
/*    */   public String getCommand() {
/* 24 */     return "tell";
/*    */   }
/*    */ 
/*    */   
/*    */   public int a() {
/* 29 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public String c(ICommandListener paramICommandListener) {
/* 34 */     return "commands.message.usage";
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 39 */     if (paramArrayOfString.length < 2) throw new ExceptionUsage("commands.message.usage", new Object[0]);
/*    */     
/* 41 */     EntityPlayer entityPlayer = d(paramICommandListener, paramArrayOfString[0]);
/*    */     
/* 43 */     if (entityPlayer == null) throw new ExceptionPlayerNotFound(); 
/* 44 */     if (entityPlayer == paramICommandListener) throw new ExceptionPlayerNotFound("commands.message.sameTarget", new Object[0]);
/*    */     
/* 46 */     IChatBaseComponent iChatBaseComponent = a(paramICommandListener, paramArrayOfString, 1, !(paramICommandListener instanceof EntityHuman));
/* 47 */     ChatMessage chatMessage1 = new ChatMessage("commands.message.display.incoming", new Object[] { paramICommandListener.getScoreboardDisplayName(), iChatBaseComponent.f() });
/* 48 */     ChatMessage chatMessage2 = new ChatMessage("commands.message.display.outgoing", new Object[] { entityPlayer.getScoreboardDisplayName(), iChatBaseComponent.f() });
/* 49 */     chatMessage1.getChatModifier().setColor(EnumChatFormat.GRAY).setItalic(Boolean.valueOf(true));
/* 50 */     chatMessage2.getChatModifier().setColor(EnumChatFormat.GRAY).setItalic(Boolean.valueOf(true));
/* 51 */     entityPlayer.sendMessage(chatMessage1);
/* 52 */     paramICommandListener.sendMessage(chatMessage2);
/*    */   }
/*    */ 
/*    */   
/*    */   public List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString) {
/* 57 */     return a(paramArrayOfString, MinecraftServer.getServer().getPlayers());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isListStart(String[] paramArrayOfString, int paramInt) {
/* 62 */     return (paramInt == 0);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\CommandTell.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class ChatMessageException extends IllegalArgumentException {
/*    */   public ChatMessageException(ChatMessage paramChatMessage, String paramString) {
/*  5 */     super(String.format("Error parsing: %s: %s", new Object[] { paramChatMessage, paramString }));
/*    */   }
/*    */   
/*    */   public ChatMessageException(ChatMessage paramChatMessage, int paramInt) {
/*  9 */     super(String.format("Invalid index %d requested for %s", new Object[] { Integer.valueOf(paramInt), paramChatMessage }));
/*    */   }
/*    */   
/*    */   public ChatMessageException(ChatMessage paramChatMessage, Throwable paramThrowable) {
/* 13 */     super(String.format("Error while parsing: %s", new Object[] { paramChatMessage }), paramThrowable);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ChatMessageException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
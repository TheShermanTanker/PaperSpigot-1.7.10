/*    */ package org.bukkit.craftbukkit.v1_7_R4.command;
/*    */ 
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.ConsoleCommandSender;
/*    */ import org.bukkit.conversations.Conversation;
/*    */ import org.bukkit.conversations.ConversationAbandonedEvent;
/*    */ import org.bukkit.conversations.ConversationCanceller;
/*    */ import org.bukkit.conversations.ManuallyAbandonedConversationCanceller;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.conversations.ConversationTracker;
/*    */ 
/*    */ public class CraftConsoleCommandSender
/*    */   extends ServerCommandSender
/*    */   implements ConsoleCommandSender
/*    */ {
/* 15 */   protected final ConversationTracker conversationTracker = new ConversationTracker();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void sendMessage(String message) {
/* 22 */     sendRawMessage(message);
/*    */   }
/*    */   
/*    */   public void sendRawMessage(String message) {
/* 26 */     System.out.println(ChatColor.stripColor(message));
/*    */   }
/*    */   
/*    */   public void sendMessage(String[] messages) {
/* 30 */     for (String message : messages) {
/* 31 */       sendMessage(message);
/*    */     }
/*    */   }
/*    */   
/*    */   public String getName() {
/* 36 */     return "CONSOLE";
/*    */   }
/*    */   
/*    */   public boolean isOp() {
/* 40 */     return true;
/*    */   }
/*    */   
/*    */   public void setOp(boolean value) {
/* 44 */     throw new UnsupportedOperationException("Cannot change operator status of server console");
/*    */   }
/*    */   
/*    */   public boolean beginConversation(Conversation conversation) {
/* 48 */     return this.conversationTracker.beginConversation(conversation);
/*    */   }
/*    */   
/*    */   public void abandonConversation(Conversation conversation) {
/* 52 */     this.conversationTracker.abandonConversation(conversation, new ConversationAbandonedEvent(conversation, (ConversationCanceller)new ManuallyAbandonedConversationCanceller()));
/*    */   }
/*    */   
/*    */   public void abandonConversation(Conversation conversation, ConversationAbandonedEvent details) {
/* 56 */     this.conversationTracker.abandonConversation(conversation, details);
/*    */   }
/*    */   
/*    */   public void acceptConversationInput(String input) {
/* 60 */     this.conversationTracker.acceptConversationInput(input);
/*    */   }
/*    */   
/*    */   public boolean isConversing() {
/* 64 */     return this.conversationTracker.isConversing();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\command\CraftConsoleCommandSender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
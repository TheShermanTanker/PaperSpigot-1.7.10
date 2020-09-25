/*    */ package org.bukkit.craftbukkit.v1_7_R4.conversations;
/*    */ 
/*    */ import java.util.LinkedList;
/*    */ import java.util.logging.Level;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.conversations.Conversation;
/*    */ import org.bukkit.conversations.ConversationAbandonedEvent;
/*    */ import org.bukkit.conversations.ConversationCanceller;
/*    */ import org.bukkit.conversations.ManuallyAbandonedConversationCanceller;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ConversationTracker
/*    */ {
/* 15 */   private LinkedList<Conversation> conversationQueue = new LinkedList<Conversation>();
/*    */   
/*    */   public synchronized boolean beginConversation(Conversation conversation) {
/* 18 */     if (!this.conversationQueue.contains(conversation)) {
/* 19 */       this.conversationQueue.addLast(conversation);
/* 20 */       if (this.conversationQueue.getFirst() == conversation) {
/* 21 */         conversation.begin();
/* 22 */         conversation.outputNextPrompt();
/* 23 */         return true;
/*    */       } 
/*    */     } 
/* 26 */     return true;
/*    */   }
/*    */   
/*    */   public synchronized void abandonConversation(Conversation conversation, ConversationAbandonedEvent details) {
/* 30 */     if (!this.conversationQueue.isEmpty()) {
/* 31 */       if (this.conversationQueue.getFirst() == conversation) {
/* 32 */         conversation.abandon(details);
/*    */       }
/* 34 */       if (this.conversationQueue.contains(conversation)) {
/* 35 */         this.conversationQueue.remove(conversation);
/*    */       }
/* 37 */       if (!this.conversationQueue.isEmpty()) {
/* 38 */         ((Conversation)this.conversationQueue.getFirst()).outputNextPrompt();
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public synchronized void abandonAllConversations() {
/* 45 */     LinkedList<Conversation> oldQueue = this.conversationQueue;
/* 46 */     this.conversationQueue = new LinkedList<Conversation>();
/* 47 */     for (Conversation conversation : oldQueue) {
/*    */       try {
/* 49 */         conversation.abandon(new ConversationAbandonedEvent(conversation, (ConversationCanceller)new ManuallyAbandonedConversationCanceller()));
/* 50 */       } catch (Throwable t) {
/* 51 */         Bukkit.getLogger().log(Level.SEVERE, "Unexpected exception while abandoning a conversation", t);
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public synchronized void acceptConversationInput(String input) {
/* 57 */     if (isConversing()) {
/* 58 */       ((Conversation)this.conversationQueue.getFirst()).acceptInput(input);
/*    */     }
/*    */   }
/*    */   
/*    */   public synchronized boolean isConversing() {
/* 63 */     return !this.conversationQueue.isEmpty();
/*    */   }
/*    */   
/*    */   public synchronized boolean isConversingModaly() {
/* 67 */     return (isConversing() && ((Conversation)this.conversationQueue.getFirst()).isModal());
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\conversations\ConversationTracker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
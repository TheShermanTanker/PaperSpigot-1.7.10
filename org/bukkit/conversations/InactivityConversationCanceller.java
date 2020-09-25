/*    */ package org.bukkit.conversations;
/*    */ 
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InactivityConversationCanceller
/*    */   implements ConversationCanceller
/*    */ {
/*    */   protected Plugin plugin;
/*    */   protected int timeoutSeconds;
/*    */   protected Conversation conversation;
/* 14 */   private int taskId = -1;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public InactivityConversationCanceller(Plugin plugin, int timeoutSeconds) {
/* 23 */     this.plugin = plugin;
/* 24 */     this.timeoutSeconds = timeoutSeconds;
/*    */   }
/*    */   
/*    */   public void setConversation(Conversation conversation) {
/* 28 */     this.conversation = conversation;
/* 29 */     startTimer();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean cancelBasedOnInput(ConversationContext context, String input) {
/* 34 */     stopTimer();
/* 35 */     startTimer();
/* 36 */     return false;
/*    */   }
/*    */   
/*    */   public ConversationCanceller clone() {
/* 40 */     return new InactivityConversationCanceller(this.plugin, this.timeoutSeconds);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private void startTimer() {
/* 47 */     this.taskId = this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable() {
/*    */           public void run() {
/* 49 */             if (InactivityConversationCanceller.this.conversation.getState() == Conversation.ConversationState.UNSTARTED) {
/* 50 */               InactivityConversationCanceller.this.startTimer();
/* 51 */             } else if (InactivityConversationCanceller.this.conversation.getState() == Conversation.ConversationState.STARTED) {
/* 52 */               InactivityConversationCanceller.this.cancelling(InactivityConversationCanceller.this.conversation);
/* 53 */               InactivityConversationCanceller.this.conversation.abandon(new ConversationAbandonedEvent(InactivityConversationCanceller.this.conversation, InactivityConversationCanceller.this));
/*    */             } 
/*    */           }
/*    */         }(this.timeoutSeconds * 20));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private void stopTimer() {
/* 63 */     if (this.taskId != -1) {
/* 64 */       this.plugin.getServer().getScheduler().cancelTask(this.taskId);
/* 65 */       this.taskId = -1;
/*    */     } 
/*    */   }
/*    */   
/*    */   protected void cancelling(Conversation conversation) {}
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\conversations\InactivityConversationCanceller.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
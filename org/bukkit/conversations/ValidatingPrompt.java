/*    */ package org.bukkit.conversations;
/*    */ 
/*    */ import org.bukkit.ChatColor;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ValidatingPrompt
/*    */   implements Prompt
/*    */ {
/*    */   public Prompt acceptInput(ConversationContext context, String input) {
/* 25 */     if (isInputValid(context, input)) {
/* 26 */       return acceptValidatedInput(context, input);
/*    */     }
/* 28 */     String failPrompt = getFailedValidationText(context, input);
/* 29 */     if (failPrompt != null) {
/* 30 */       context.getForWhom().sendRawMessage(ChatColor.RED + failPrompt);
/*    */     }
/*    */     
/* 33 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean blocksForInput(ConversationContext context) {
/* 44 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected abstract boolean isInputValid(ConversationContext paramConversationContext, String paramString);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected abstract Prompt acceptValidatedInput(ConversationContext paramConversationContext, String paramString);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getFailedValidationText(ConversationContext context, String invalidInput) {
/* 76 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\conversations\ValidatingPrompt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
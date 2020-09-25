/*    */ package org.bukkit.conversations;
/*    */ 
/*    */ import org.apache.commons.lang.ArrayUtils;
/*    */ import org.apache.commons.lang.BooleanUtils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class BooleanPrompt
/*    */   extends ValidatingPrompt
/*    */ {
/*    */   protected boolean isInputValid(ConversationContext context, String input) {
/* 18 */     String[] accepted = { "true", "false", "on", "off", "yes", "no", "y", "n", "1", "0", "right", "wrong", "correct", "incorrect", "valid", "invalid" };
/* 19 */     return ArrayUtils.contains((Object[])accepted, input.toLowerCase());
/*    */   }
/*    */ 
/*    */   
/*    */   protected Prompt acceptValidatedInput(ConversationContext context, String input) {
/* 24 */     if (input.equalsIgnoreCase("y") || input.equals("1") || input.equalsIgnoreCase("right") || input.equalsIgnoreCase("correct") || input.equalsIgnoreCase("valid")) input = "true"; 
/* 25 */     return acceptValidatedInput(context, BooleanUtils.toBoolean(input));
/*    */   }
/*    */   
/*    */   protected abstract Prompt acceptValidatedInput(ConversationContext paramConversationContext, boolean paramBoolean);
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\conversations\BooleanPrompt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
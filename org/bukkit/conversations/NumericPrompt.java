/*    */ package org.bukkit.conversations;
/*    */ 
/*    */ import org.apache.commons.lang.math.NumberUtils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class NumericPrompt
/*    */   extends ValidatingPrompt
/*    */ {
/*    */   protected boolean isInputValid(ConversationContext context, String input) {
/* 16 */     return (NumberUtils.isNumber(input) && isNumberValid(context, NumberUtils.createNumber(input)));
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
/*    */   protected boolean isNumberValid(ConversationContext context, Number input) {
/* 28 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected Prompt acceptValidatedInput(ConversationContext context, String input) {
/*    */     try {
/* 35 */       return acceptValidatedInput(context, NumberUtils.createNumber(input));
/* 36 */     } catch (NumberFormatException e) {
/* 37 */       return acceptValidatedInput(context, NumberUtils.INTEGER_ZERO);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected abstract Prompt acceptValidatedInput(ConversationContext paramConversationContext, Number paramNumber);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getFailedValidationText(ConversationContext context, String invalidInput) {
/* 53 */     if (NumberUtils.isNumber(invalidInput)) {
/* 54 */       return getFailedValidationText(context, NumberUtils.createNumber(invalidInput));
/*    */     }
/* 56 */     return getInputNotNumericText(context, invalidInput);
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
/*    */   
/*    */   protected String getInputNotNumericText(ConversationContext context, String invalidInput) {
/* 69 */     return null;
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
/*    */   protected String getFailedValidationText(ConversationContext context, Number invalidInput) {
/* 81 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\conversations\NumericPrompt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
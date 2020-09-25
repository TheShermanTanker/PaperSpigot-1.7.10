/*    */ package org.bukkit.conversations;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang.StringUtils;
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
/*    */ public abstract class FixedSetPrompt
/*    */   extends ValidatingPrompt
/*    */ {
/*    */   protected List<String> fixedSet;
/*    */   
/*    */   public FixedSetPrompt(String... fixedSet) {
/* 26 */     this.fixedSet = Arrays.asList(fixedSet);
/*    */   }
/*    */ 
/*    */   
/*    */   private FixedSetPrompt() {}
/*    */   
/*    */   protected boolean isInputValid(ConversationContext context, String input) {
/* 33 */     return this.fixedSet.contains(input);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected String formatFixedSet() {
/* 44 */     return "[" + StringUtils.join(this.fixedSet, ", ") + "]";
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\conversations\FixedSetPrompt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
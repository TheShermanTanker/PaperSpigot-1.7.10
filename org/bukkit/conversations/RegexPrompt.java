/*    */ package org.bukkit.conversations;
/*    */ 
/*    */ import java.util.regex.Pattern;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class RegexPrompt
/*    */   extends ValidatingPrompt
/*    */ {
/*    */   private Pattern pattern;
/*    */   
/*    */   public RegexPrompt(String regex) {
/* 14 */     this(Pattern.compile(regex));
/*    */   }
/*    */ 
/*    */   
/*    */   public RegexPrompt(Pattern pattern) {
/* 19 */     this.pattern = pattern;
/*    */   }
/*    */ 
/*    */   
/*    */   private RegexPrompt() {}
/*    */   
/*    */   protected boolean isInputValid(ConversationContext context, String input) {
/* 26 */     return this.pattern.matcher(input).matches();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\conversations\RegexPrompt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
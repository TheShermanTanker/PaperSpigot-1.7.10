/*    */ package org.bukkit.craftbukkit.libs.joptsimple;
/*    */ 
/*    */ import java.util.Collection;
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
/*    */ final class ParserRules
/*    */ {
/*    */   static final char HYPHEN_CHAR = '-';
/* 39 */   static final String HYPHEN = String.valueOf('-');
/*    */   static final String DOUBLE_HYPHEN = "--";
/*    */   static final String OPTION_TERMINATOR = "--";
/*    */   static final String RESERVED_FOR_EXTENSIONS = "W";
/*    */   
/*    */   static {
/* 45 */     new ParserRules();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static boolean isShortOptionToken(String argument) {
/* 53 */     return (argument.startsWith(HYPHEN) && !HYPHEN.equals(argument) && !isLongOptionToken(argument));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   static boolean isLongOptionToken(String argument) {
/* 59 */     return (argument.startsWith("--") && !isOptionTerminator(argument));
/*    */   }
/*    */   
/*    */   static boolean isOptionTerminator(String argument) {
/* 63 */     return "--".equals(argument);
/*    */   }
/*    */   
/*    */   static void ensureLegalOption(String option) {
/* 67 */     if (option.startsWith(HYPHEN)) {
/* 68 */       throw new IllegalOptionSpecificationException(String.valueOf(option));
/*    */     }
/* 70 */     for (int i = 0; i < option.length(); i++)
/* 71 */       ensureLegalOptionCharacter(option.charAt(i)); 
/*    */   }
/*    */   
/*    */   static void ensureLegalOptions(Collection<String> options) {
/* 75 */     for (String each : options)
/* 76 */       ensureLegalOption(each); 
/*    */   }
/*    */   
/*    */   private static void ensureLegalOptionCharacter(char option) {
/* 80 */     if (!Character.isLetterOrDigit(option) && !isAllowedPunctuation(option))
/* 81 */       throw new IllegalOptionSpecificationException(String.valueOf(option)); 
/*    */   }
/*    */   
/*    */   private static boolean isAllowedPunctuation(char option) {
/* 85 */     String allowedPunctuation = "?.-";
/* 86 */     return (allowedPunctuation.indexOf(option) != -1);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\joptsimple\ParserRules.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
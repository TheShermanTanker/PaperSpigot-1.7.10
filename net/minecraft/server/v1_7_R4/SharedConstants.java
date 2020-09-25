/*    */ package net.minecraft.server.v1_7_R4;
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
/*    */ public class SharedConstants
/*    */ {
/*    */   public static boolean isAllowedChatCharacter(char paramChar) {
/* 34 */     return (paramChar != '§' && paramChar >= ' ' && paramChar != '');
/*    */   }
/*    */   
/* 37 */   public static final char[] allowedCharacters = new char[] { '/', '\n', '\r', '\t', Character.MIN_VALUE, '\f', '`', '?', '*', '\\', '<', '>', '|', '"', ':' };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String a(String paramString) {
/* 46 */     StringBuilder stringBuilder = new StringBuilder();
/*    */     
/* 48 */     for (char c : paramString.toCharArray()) {
/* 49 */       if (isAllowedChatCharacter(c)) {
/* 50 */         stringBuilder.append(c);
/*    */       }
/*    */     } 
/*    */     
/* 54 */     return stringBuilder.toString();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\SharedConstants.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
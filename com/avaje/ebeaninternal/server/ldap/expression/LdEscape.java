/*    */ package com.avaje.ebeaninternal.server.ldap.expression;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LdEscape
/*    */ {
/*    */   public static String forLike(String source) {
/* 10 */     StringBuilder sb = new StringBuilder(source.length() + 5);
/*    */     
/* 12 */     int len = source.length();
/* 13 */     for (int i = 0; i < len; i++) {
/*    */       
/* 15 */       char ch = source.charAt(i);
/* 16 */       switch (ch) {
/*    */         case '(':
/* 18 */           sb.append("\\\\28"); break;
/*    */         case ')':
/* 20 */           sb.append("\\\\29"); break;
/*    */         case '\\':
/* 22 */           sb.append("\\\\5c"); break;
/*    */         case '/':
/* 24 */           sb.append("\\\\2f"); break;
/*    */         case '\000':
/* 26 */           sb.append("\\\\0"); break;
/*    */         default:
/* 28 */           sb.append(ch); break;
/*    */       } 
/*    */     } 
/* 31 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\ldap\expression\LdEscape.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
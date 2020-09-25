/*    */ package org.apache.logging.log4j.core.helpers;
/*    */ 
/*    */ import java.security.MessageDigest;
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
/*    */ public final class NameUtil
/*    */ {
/*    */   private static final int MASK = 255;
/*    */   
/*    */   public static String getSubName(String name) {
/* 32 */     if (name.isEmpty()) {
/* 33 */       return null;
/*    */     }
/* 35 */     int i = name.lastIndexOf('.');
/* 36 */     return (i > 0) ? name.substring(0, i) : "";
/*    */   }
/*    */   
/*    */   public static String md5(String string) {
/*    */     try {
/* 41 */       MessageDigest digest = MessageDigest.getInstance("MD5");
/* 42 */       digest.update(string.getBytes());
/* 43 */       byte[] bytes = digest.digest();
/* 44 */       StringBuilder md5 = new StringBuilder();
/* 45 */       for (byte b : bytes) {
/* 46 */         String hex = Integer.toHexString(0xFF & b);
/* 47 */         if (hex.length() == 1) {
/* 48 */           md5.append('0');
/*    */         }
/* 50 */         md5.append(hex);
/*    */       } 
/* 52 */       return md5.toString();
/* 53 */     } catch (Exception ex) {
/* 54 */       return string;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\helpers\NameUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
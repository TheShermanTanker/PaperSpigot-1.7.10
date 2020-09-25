/*    */ package com.avaje.ebeaninternal.server.lib.util;
/*    */ 
/*    */ import java.util.MissingResourceException;
/*    */ import java.util.ResourceBundle;
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
/*    */ public class MimeTypeHelper
/*    */ {
/*    */   public static String getMimeType(String filePath) {
/* 38 */     int lastPeriod = filePath.lastIndexOf(".");
/* 39 */     if (lastPeriod > -1) {
/* 40 */       filePath = filePath.substring(lastPeriod + 1);
/*    */     }
/*    */     
/*    */     try {
/* 44 */       return resources.getString(filePath.toLowerCase());
/*    */     }
/* 46 */     catch (MissingResourceException e) {
/* 47 */       return null;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 54 */   private static ResourceBundle resources = ResourceBundle.getBundle("com.avaje.lib.util.mimetypes");
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\li\\util\MimeTypeHelper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
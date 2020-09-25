/*    */ package com.avaje.ebeaninternal.server.type;
/*    */ 
/*    */ import java.util.Locale;
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
/*    */ public class ScalarTypeLocale
/*    */   extends ScalarTypeBaseVarchar<Locale>
/*    */ {
/*    */   public ScalarTypeLocale() {
/* 31 */     super(Locale.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getLength() {
/* 36 */     return 20;
/*    */   }
/*    */ 
/*    */   
/*    */   public Locale convertFromDbString(String dbValue) {
/* 41 */     return parse(dbValue);
/*    */   }
/*    */ 
/*    */   
/*    */   public String convertToDbString(Locale beanValue) {
/* 46 */     return beanValue.toString();
/*    */   }
/*    */   
/*    */   public String formatValue(Locale t) {
/* 50 */     return t.toString();
/*    */   }
/*    */ 
/*    */   
/*    */   public Locale parse(String value) {
/* 55 */     int pos1 = -1;
/* 56 */     int pos2 = -1;
/*    */     
/* 58 */     for (int i = 0; i < value.length(); i++) {
/* 59 */       char c = value.charAt(i);
/* 60 */       if (c == '_') {
/* 61 */         if (pos1 > -1) {
/* 62 */           pos2 = i;
/*    */           break;
/*    */         } 
/* 65 */         pos1 = i;
/*    */       } 
/*    */     } 
/*    */     
/* 69 */     if (pos1 == -1) {
/* 70 */       return new Locale(value);
/*    */     }
/* 72 */     String language = value.substring(0, pos1);
/* 73 */     if (pos2 == -1) {
/* 74 */       String str = value.substring(pos1 + 1);
/* 75 */       return new Locale(language, str);
/*    */     } 
/* 77 */     String country = value.substring(pos1 + 1, pos2);
/* 78 */     String variant = value.substring(pos2 + 1);
/* 79 */     return new Locale(language, country, variant);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeLocale.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
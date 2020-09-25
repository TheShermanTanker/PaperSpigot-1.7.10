/*    */ package com.avaje.ebeaninternal.server.type;
/*    */ 
/*    */ import java.util.Currency;
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
/*    */ public class ScalarTypeCurrency
/*    */   extends ScalarTypeBaseVarchar<Currency>
/*    */ {
/*    */   public ScalarTypeCurrency() {
/* 30 */     super(Currency.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getLength() {
/* 35 */     return 3;
/*    */   }
/*    */ 
/*    */   
/*    */   public Currency convertFromDbString(String dbValue) {
/* 40 */     return Currency.getInstance(dbValue);
/*    */   }
/*    */ 
/*    */   
/*    */   public String convertToDbString(Currency beanValue) {
/* 45 */     return beanValue.getCurrencyCode();
/*    */   }
/*    */   
/*    */   public String formatValue(Currency v) {
/* 49 */     return v.toString();
/*    */   }
/*    */   
/*    */   public Currency parse(String value) {
/* 53 */     return Currency.getInstance(value);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeCurrency.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package com.avaje.ebeaninternal.server.type;
/*    */ 
/*    */ import java.util.TimeZone;
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
/*    */ public class ScalarTypeTimeZone
/*    */   extends ScalarTypeBaseVarchar<TimeZone>
/*    */ {
/*    */   public ScalarTypeTimeZone() {
/* 30 */     super(TimeZone.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getLength() {
/* 35 */     return 20;
/*    */   }
/*    */ 
/*    */   
/*    */   public TimeZone convertFromDbString(String dbValue) {
/* 40 */     return TimeZone.getTimeZone(dbValue);
/*    */   }
/*    */ 
/*    */   
/*    */   public String convertToDbString(TimeZone beanValue) {
/* 45 */     return beanValue.getID();
/*    */   }
/*    */   
/*    */   public String formatValue(TimeZone v) {
/* 49 */     return v.toString();
/*    */   }
/*    */   
/*    */   public TimeZone parse(String value) {
/* 53 */     return TimeZone.getTimeZone(value);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeTimeZone.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
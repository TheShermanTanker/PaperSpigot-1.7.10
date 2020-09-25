/*    */ package com.avaje.ebeaninternal.server.type;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
/*    */ import java.sql.Date;
/*    */ import java.sql.SQLException;
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
/*    */ public class ScalarTypeDate
/*    */   extends ScalarTypeBaseDate<Date>
/*    */ {
/*    */   public ScalarTypeDate() {
/* 34 */     super(Date.class, true, 91);
/*    */   }
/*    */ 
/*    */   
/*    */   public Date convertFromDate(Date date) {
/* 39 */     return date;
/*    */   }
/*    */ 
/*    */   
/*    */   public Date convertToDate(Date t) {
/* 44 */     return t;
/*    */   }
/*    */   
/*    */   public void bind(DataBind b, Date value) throws SQLException {
/* 48 */     if (value == null) {
/* 49 */       b.setNull(91);
/*    */     } else {
/* 51 */       b.setDate(value);
/*    */     } 
/*    */   }
/*    */   
/*    */   public Date read(DataReader dataReader) throws SQLException {
/* 56 */     return dataReader.getDate();
/*    */   }
/*    */   
/*    */   public Object toJdbcType(Object value) {
/* 60 */     return BasicTypeConverter.toDate(value);
/*    */   }
/*    */   
/*    */   public Date toBeanType(Object value) {
/* 64 */     return BasicTypeConverter.toDate(value);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeDate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
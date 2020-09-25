/*    */ package com.avaje.ebeaninternal.server.type;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
/*    */ import java.sql.SQLException;
/*    */ import java.sql.Timestamp;
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
/*    */ public class ScalarTypeTimestamp
/*    */   extends ScalarTypeBaseDateTime<Timestamp>
/*    */ {
/*    */   public ScalarTypeTimestamp() {
/* 34 */     super(Timestamp.class, true, 93);
/*    */   }
/*    */ 
/*    */   
/*    */   public Timestamp convertFromTimestamp(Timestamp ts) {
/* 39 */     return ts;
/*    */   }
/*    */ 
/*    */   
/*    */   public Timestamp convertToTimestamp(Timestamp t) {
/* 44 */     return t;
/*    */   }
/*    */   
/*    */   public void bind(DataBind b, Timestamp value) throws SQLException {
/* 48 */     if (value == null) {
/* 49 */       b.setNull(93);
/*    */     } else {
/* 51 */       b.setTimestamp(value);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Timestamp read(DataReader dataReader) throws SQLException {
/* 57 */     return dataReader.getTimestamp();
/*    */   }
/*    */   
/*    */   public Object toJdbcType(Object value) {
/* 61 */     return BasicTypeConverter.toTimestamp(value);
/*    */   }
/*    */   
/*    */   public Timestamp toBeanType(Object value) {
/* 65 */     return BasicTypeConverter.toTimestamp(value);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeTimestamp.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
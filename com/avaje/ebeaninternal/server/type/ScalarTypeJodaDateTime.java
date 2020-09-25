/*    */ package com.avaje.ebeaninternal.server.type;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
/*    */ import java.sql.Timestamp;
/*    */ import java.util.Date;
/*    */ import org.joda.time.DateTime;
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
/*    */ public class ScalarTypeJodaDateTime
/*    */   extends ScalarTypeBaseDateTime<DateTime>
/*    */ {
/*    */   public ScalarTypeJodaDateTime() {
/* 35 */     super(DateTime.class, false, 93);
/*    */   }
/*    */ 
/*    */   
/*    */   public DateTime convertFromTimestamp(Timestamp ts) {
/* 40 */     return new DateTime(ts.getTime());
/*    */   }
/*    */ 
/*    */   
/*    */   public Timestamp convertToTimestamp(DateTime t) {
/* 45 */     return new Timestamp(t.getMillis());
/*    */   }
/*    */   
/*    */   public Object toJdbcType(Object value) {
/* 49 */     if (value instanceof DateTime) {
/* 50 */       return new Timestamp(((DateTime)value).getMillis());
/*    */     }
/* 52 */     return BasicTypeConverter.toTimestamp(value);
/*    */   }
/*    */   
/*    */   public DateTime toBeanType(Object value) {
/* 56 */     if (value instanceof Date) {
/* 57 */       return new DateTime(((Date)value).getTime());
/*    */     }
/* 59 */     return (DateTime)value;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeJodaDateTime.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
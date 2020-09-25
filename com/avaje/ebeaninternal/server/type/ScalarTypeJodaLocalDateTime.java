/*    */ package com.avaje.ebeaninternal.server.type;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
/*    */ import java.sql.Timestamp;
/*    */ import java.util.Date;
/*    */ import org.joda.time.LocalDateTime;
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
/*    */ public class ScalarTypeJodaLocalDateTime
/*    */   extends ScalarTypeBaseDateTime<LocalDateTime>
/*    */ {
/*    */   public ScalarTypeJodaLocalDateTime() {
/* 35 */     super(LocalDateTime.class, false, 93);
/*    */   }
/*    */ 
/*    */   
/*    */   public LocalDateTime convertFromTimestamp(Timestamp ts) {
/* 40 */     return new LocalDateTime(ts.getTime());
/*    */   }
/*    */ 
/*    */   
/*    */   public Timestamp convertToTimestamp(LocalDateTime t) {
/* 45 */     return new Timestamp(t.toDateTime().getMillis());
/*    */   }
/*    */   
/*    */   public Object toJdbcType(Object value) {
/* 49 */     if (value instanceof LocalDateTime) {
/* 50 */       return new Timestamp(((LocalDateTime)value).toDateTime().getMillis());
/*    */     }
/* 52 */     return BasicTypeConverter.toTimestamp(value);
/*    */   }
/*    */   
/*    */   public LocalDateTime toBeanType(Object value) {
/* 56 */     if (value instanceof Date) {
/* 57 */       return new LocalDateTime(((Date)value).getTime());
/*    */     }
/* 59 */     return (LocalDateTime)value;
/*    */   }
/*    */   
/*    */   public LocalDateTime parseDateTime(long systemTimeMillis) {
/* 63 */     return new LocalDateTime(systemTimeMillis);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeJodaLocalDateTime.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
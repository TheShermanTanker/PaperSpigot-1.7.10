/*    */ package com.avaje.ebeaninternal.server.type;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
/*    */ import java.sql.Date;
/*    */ import java.util.Date;
/*    */ import org.joda.time.LocalDate;
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
/*    */ public class ScalarTypeJodaLocalDate
/*    */   extends ScalarTypeBaseDate<LocalDate>
/*    */ {
/*    */   public ScalarTypeJodaLocalDate() {
/* 35 */     super(LocalDate.class, false, 91);
/*    */   }
/*    */ 
/*    */   
/*    */   public LocalDate convertFromDate(Date ts) {
/* 40 */     return new LocalDate(ts.getTime());
/*    */   }
/*    */ 
/*    */   
/*    */   public Date convertToDate(LocalDate t) {
/* 45 */     return new Date(t.toDateMidnight().getMillis());
/*    */   }
/*    */   
/*    */   public Object toJdbcType(Object value) {
/* 49 */     if (value instanceof LocalDate) {
/* 50 */       return new Date(((LocalDate)value).toDateMidnight().getMillis());
/*    */     }
/* 52 */     return BasicTypeConverter.toDate(value);
/*    */   }
/*    */   
/*    */   public LocalDate toBeanType(Object value) {
/* 56 */     if (value instanceof Date) {
/* 57 */       return new LocalDate(((Date)value).getTime());
/*    */     }
/* 59 */     return (LocalDate)value;
/*    */   }
/*    */   
/*    */   public LocalDate parseDateTime(long systemTimeMillis) {
/* 63 */     return new LocalDate(systemTimeMillis);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeJodaLocalDate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
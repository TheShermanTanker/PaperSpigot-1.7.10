/*    */ package com.avaje.ebeaninternal.server.type;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
/*    */ import java.sql.Date;
/*    */ import java.util.Date;
/*    */ import org.joda.time.DateMidnight;
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
/*    */ public class ScalarTypeJodaDateMidnight
/*    */   extends ScalarTypeBaseDate<DateMidnight>
/*    */ {
/*    */   public ScalarTypeJodaDateMidnight() {
/* 38 */     super(DateMidnight.class, false, 91);
/*    */   }
/*    */ 
/*    */   
/*    */   public DateMidnight convertFromDate(Date ts) {
/* 43 */     return new DateMidnight(ts.getTime());
/*    */   }
/*    */ 
/*    */   
/*    */   public Date convertToDate(DateMidnight t) {
/* 48 */     return new Date(t.getMillis());
/*    */   }
/*    */   
/*    */   public Object toJdbcType(Object value) {
/* 52 */     if (value instanceof DateMidnight) {
/* 53 */       return new Date(((DateMidnight)value).getMillis());
/*    */     }
/* 55 */     return BasicTypeConverter.toDate(value);
/*    */   }
/*    */   
/*    */   public DateMidnight toBeanType(Object value) {
/* 59 */     if (value instanceof Date) {
/* 60 */       return new DateMidnight(((Date)value).getTime());
/*    */     }
/* 62 */     return (DateMidnight)value;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeJodaDateMidnight.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
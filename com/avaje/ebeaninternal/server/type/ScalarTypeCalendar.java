/*    */ package com.avaje.ebeaninternal.server.type;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
/*    */ import java.sql.Date;
/*    */ import java.sql.SQLException;
/*    */ import java.sql.Timestamp;
/*    */ import java.util.Calendar;
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
/*    */ public class ScalarTypeCalendar
/*    */   extends ScalarTypeBaseDateTime<Calendar>
/*    */ {
/*    */   public ScalarTypeCalendar(int jdbcType) {
/* 36 */     super(Calendar.class, false, jdbcType);
/*    */   }
/*    */   
/*    */   public void bind(DataBind b, Calendar value) throws SQLException {
/* 40 */     if (value == null) {
/* 41 */       b.setNull(93);
/*    */     } else {
/* 43 */       Calendar date = value;
/* 44 */       if (this.jdbcType == 93) {
/* 45 */         Timestamp timestamp = new Timestamp(date.getTimeInMillis());
/* 46 */         b.setTimestamp(timestamp);
/*    */       } else {
/* 48 */         Date d = new Date(date.getTimeInMillis());
/* 49 */         b.setDate(d);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Calendar convertFromTimestamp(Timestamp ts) {
/* 56 */     Calendar calendar = Calendar.getInstance();
/* 57 */     calendar.setTimeInMillis(ts.getTime());
/* 58 */     return calendar;
/*    */   }
/*    */ 
/*    */   
/*    */   public Timestamp convertToTimestamp(Calendar t) {
/* 63 */     return new Timestamp(t.getTimeInMillis());
/*    */   }
/*    */   
/*    */   public Object toJdbcType(Object value) {
/* 67 */     return BasicTypeConverter.convert(value, this.jdbcType);
/*    */   }
/*    */   
/*    */   public Calendar toBeanType(Object value) {
/* 71 */     return BasicTypeConverter.toCalendar(value);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeCalendar.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.avaje.ebeaninternal.server.type;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
/*     */ import java.sql.Date;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.Date;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ScalarTypeUtilDate
/*     */ {
/*     */   public static class TimestampType
/*     */     extends ScalarTypeBaseDateTime<Date>
/*     */   {
/*     */     public TimestampType() {
/*  37 */       super(Date.class, false, 93);
/*     */     }
/*     */     
/*     */     public Date read(DataReader dataReader) throws SQLException {
/*  41 */       Timestamp timestamp = dataReader.getTimestamp();
/*  42 */       if (timestamp == null) {
/*  43 */         return null;
/*     */       }
/*  45 */       return new Date(timestamp.getTime());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void bind(DataBind b, Date value) throws SQLException {
/*  51 */       if (value == null) {
/*  52 */         b.setNull(93);
/*     */       } else {
/*     */         
/*  55 */         Timestamp timestamp = new Timestamp(value.getTime());
/*  56 */         b.setTimestamp(timestamp);
/*     */       } 
/*     */     }
/*     */     
/*     */     public Object toJdbcType(Object value) {
/*  61 */       return BasicTypeConverter.toTimestamp(value);
/*     */     }
/*     */     
/*     */     public Date toBeanType(Object value) {
/*  65 */       return BasicTypeConverter.toUtilDate(value);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Date convertFromTimestamp(Timestamp ts) {
/*  72 */       return new Date(ts.getTime());
/*     */     }
/*     */ 
/*     */     
/*     */     public Timestamp convertToTimestamp(Date t) {
/*  77 */       return new Timestamp(t.getTime());
/*     */     }
/*     */     
/*     */     public Date parseDateTime(long systemTimeMillis) {
/*  81 */       return new Date(systemTimeMillis);
/*     */     }
/*     */     
/*     */     public Object luceneFromIndexValue(Object value) {
/*  85 */       Long l = (Long)value;
/*  86 */       return new Date(l.longValue());
/*     */     }
/*     */     
/*     */     public Object luceneToIndexValue(Object value) {
/*  90 */       return Long.valueOf(((Date)value).getTime());
/*     */     }
/*     */   }
/*     */   
/*     */   public static class DateType
/*     */     extends ScalarTypeBaseDate<Date> {
/*     */     public DateType() {
/*  97 */       super(Date.class, false, 91);
/*     */     }
/*     */ 
/*     */     
/*     */     public Date convertFromDate(Date ts) {
/* 102 */       return new Date(ts.getTime());
/*     */     }
/*     */ 
/*     */     
/*     */     public Date convertToDate(Date t) {
/* 107 */       return new Date(t.getTime());
/*     */     }
/*     */     
/*     */     public Object toJdbcType(Object value) {
/* 111 */       return BasicTypeConverter.toDate(value);
/*     */     }
/*     */     
/*     */     public Date toBeanType(Object value) {
/* 115 */       return BasicTypeConverter.toUtilDate(value);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeUtilDate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
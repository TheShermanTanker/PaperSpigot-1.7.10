/*    */ package org.bukkit.craftbukkit.libs.joptsimple.util;
/*    */ 
/*    */ import java.text.DateFormat;
/*    */ import java.text.ParsePosition;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
/*    */ import org.bukkit.craftbukkit.libs.joptsimple.ValueConversionException;
/*    */ import org.bukkit.craftbukkit.libs.joptsimple.ValueConverter;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DateConverter
/*    */   implements ValueConverter<Date>
/*    */ {
/*    */   private final DateFormat formatter;
/*    */   
/*    */   public DateConverter(DateFormat formatter) {
/* 51 */     if (formatter == null) {
/* 52 */       throw new NullPointerException("illegal null formatter");
/*    */     }
/* 54 */     this.formatter = formatter;
/*    */   }
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
/*    */   public static DateConverter datePattern(String pattern) {
/* 68 */     SimpleDateFormat formatter = new SimpleDateFormat(pattern);
/* 69 */     formatter.setLenient(false);
/*    */     
/* 71 */     return new DateConverter(formatter);
/*    */   }
/*    */   
/*    */   public Date convert(String value) {
/* 75 */     ParsePosition position = new ParsePosition(0);
/*    */     
/* 77 */     Date date = this.formatter.parse(value, position);
/* 78 */     if (position.getIndex() != value.length()) {
/* 79 */       throw new ValueConversionException(message(value));
/*    */     }
/* 81 */     return date;
/*    */   }
/*    */   
/*    */   public Class<Date> valueType() {
/* 85 */     return Date.class;
/*    */   }
/*    */   
/*    */   public String valuePattern() {
/* 89 */     return (this.formatter instanceof SimpleDateFormat) ? ((SimpleDateFormat)this.formatter).toLocalizedPattern() : "";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private String message(String value) {
/* 95 */     String message = "Value [" + value + "] does not match date/time pattern";
/* 96 */     if (this.formatter instanceof SimpleDateFormat) {
/* 97 */       message = message + " [" + ((SimpleDateFormat)this.formatter).toLocalizedPattern() + ']';
/*    */     }
/* 99 */     return message;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\joptsimpl\\util\DateConverter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
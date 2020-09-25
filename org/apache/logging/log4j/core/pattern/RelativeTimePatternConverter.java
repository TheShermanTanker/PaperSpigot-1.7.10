/*    */ package org.apache.logging.log4j.core.pattern;
/*    */ 
/*    */ import java.lang.management.ManagementFactory;
/*    */ import org.apache.logging.log4j.core.LogEvent;
/*    */ import org.apache.logging.log4j.core.config.plugins.Plugin;
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
/*    */ @Plugin(name = "RelativeTimePatternConverter", category = "Converter")
/*    */ @ConverterKeys({"r", "relative"})
/*    */ public class RelativeTimePatternConverter
/*    */   extends LogEventPatternConverter
/*    */ {
/* 33 */   private long lastTimestamp = Long.MIN_VALUE;
/* 34 */   private final long startTime = ManagementFactory.getRuntimeMXBean().getStartTime();
/*    */ 
/*    */   
/*    */   private String relative;
/*    */ 
/*    */   
/*    */   public RelativeTimePatternConverter() {
/* 41 */     super("Time", "time");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static RelativeTimePatternConverter newInstance(String[] options) {
/* 52 */     return new RelativeTimePatternConverter();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void format(LogEvent event, StringBuilder toAppendTo) {
/* 60 */     long timestamp = event.getMillis();
/*    */     
/* 62 */     synchronized (this) {
/* 63 */       if (timestamp != this.lastTimestamp) {
/* 64 */         this.lastTimestamp = timestamp;
/* 65 */         this.relative = Long.toString(timestamp - this.startTime);
/*    */       } 
/*    */     } 
/* 68 */     toAppendTo.append(this.relative);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\pattern\RelativeTimePatternConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
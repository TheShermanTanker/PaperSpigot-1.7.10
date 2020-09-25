/*    */ package org.apache.logging.log4j.core.pattern;
/*    */ 
/*    */ import org.apache.logging.log4j.core.LogEvent;
/*    */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*    */ import org.apache.logging.log4j.core.helpers.Constants;
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
/*    */ @Plugin(name = "LineSeparatorPatternConverter", category = "Converter")
/*    */ @ConverterKeys({"n"})
/*    */ public final class LineSeparatorPatternConverter
/*    */   extends LogEventPatternConverter
/*    */ {
/* 33 */   private static final LineSeparatorPatternConverter INSTANCE = new LineSeparatorPatternConverter();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private final String lineSep;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private LineSeparatorPatternConverter() {
/* 45 */     super("Line Sep", "lineSep");
/* 46 */     this.lineSep = Constants.LINE_SEP;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static LineSeparatorPatternConverter newInstance(String[] options) {
/* 55 */     return INSTANCE;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void format(LogEvent event, StringBuilder toAppendTo) {
/* 63 */     toAppendTo.append(this.lineSep);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\pattern\LineSeparatorPatternConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package org.apache.logging.log4j.core.pattern;
/*    */ 
/*    */ import java.util.Date;
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
/*    */ 
/*    */ @Plugin(name = "IntegerPatternConverter", category = "FileConverter")
/*    */ @ConverterKeys({"i", "index"})
/*    */ public final class IntegerPatternConverter
/*    */   extends AbstractPatternConverter
/*    */   implements ArrayPatternConverter
/*    */ {
/* 34 */   private static final IntegerPatternConverter INSTANCE = new IntegerPatternConverter();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private IntegerPatternConverter() {
/* 40 */     super("Integer", "integer");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static IntegerPatternConverter newInstance(String[] options) {
/* 51 */     return INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   public void format(StringBuilder toAppendTo, Object... objects) {
/* 56 */     for (Object obj : objects) {
/* 57 */       if (obj instanceof Integer) {
/* 58 */         format(obj, toAppendTo);
/*    */         break;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void format(Object obj, StringBuilder toAppendTo) {
/* 69 */     if (obj instanceof Integer) {
/* 70 */       toAppendTo.append(obj.toString());
/*    */     }
/*    */     
/* 73 */     if (obj instanceof Date)
/* 74 */       toAppendTo.append(Long.toString(((Date)obj).getTime())); 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\pattern\IntegerPatternConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
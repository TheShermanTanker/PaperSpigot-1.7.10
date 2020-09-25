/*    */ package org.apache.logging.log4j.core.pattern;
/*    */ 
/*    */ import org.apache.logging.log4j.core.LogEvent;
/*    */ import org.apache.logging.log4j.core.config.Configuration;
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
/*    */ public final class LiteralPatternConverter
/*    */   extends LogEventPatternConverter
/*    */   implements ArrayPatternConverter
/*    */ {
/*    */   private final String literal;
/*    */   private final Configuration config;
/*    */   private final boolean substitute;
/*    */   
/*    */   public LiteralPatternConverter(Configuration config, String literal) {
/* 43 */     super("Literal", "literal");
/* 44 */     this.literal = literal;
/* 45 */     this.config = config;
/* 46 */     this.substitute = (config != null && literal.contains("${"));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void format(LogEvent event, StringBuilder toAppendTo) {
/* 54 */     toAppendTo.append(this.substitute ? this.config.getStrSubstitutor().replace(event, this.literal) : this.literal);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void format(Object obj, StringBuilder output) {
/* 61 */     output.append(this.substitute ? this.config.getStrSubstitutor().replace(this.literal) : this.literal);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void format(StringBuilder output, Object... objects) {
/* 69 */     output.append(this.substitute ? this.config.getStrSubstitutor().replace(this.literal) : this.literal);
/*    */   }
/*    */   
/*    */   public String getLiteral() {
/* 73 */     return this.literal;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\pattern\LiteralPatternConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package org.apache.logging.log4j.core.pattern;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.config.Configuration;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.layout.PatternLayout;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ @Plugin(name = "style", category = "Converter")
/*     */ @ConverterKeys({"style"})
/*     */ public final class StyleConverter
/*     */   extends LogEventPatternConverter
/*     */ {
/*     */   private final List<PatternFormatter> patternFormatters;
/*     */   private final String style;
/*     */   
/*     */   private StyleConverter(List<PatternFormatter> patternFormatters, String style) {
/*  43 */     super("style", "style");
/*  44 */     this.patternFormatters = patternFormatters;
/*  45 */     this.style = style;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static StyleConverter newInstance(Configuration config, String[] options) {
/*  57 */     if (options.length < 1) {
/*  58 */       LOGGER.error("Incorrect number of options on style. Expected at least 1, received " + options.length);
/*  59 */       return null;
/*     */     } 
/*  61 */     if (options[0] == null) {
/*  62 */       LOGGER.error("No pattern supplied on style");
/*  63 */       return null;
/*     */     } 
/*  65 */     if (options[1] == null) {
/*  66 */       LOGGER.error("No style attributes provided");
/*  67 */       return null;
/*     */     } 
/*     */     
/*  70 */     PatternParser parser = PatternLayout.createPatternParser(config);
/*  71 */     List<PatternFormatter> formatters = parser.parse(options[0]);
/*  72 */     String style = AnsiEscape.createSequence(options[1].split("\\s*,\\s*"));
/*  73 */     return new StyleConverter(formatters, style);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void format(LogEvent event, StringBuilder toAppendTo) {
/*  82 */     StringBuilder buf = new StringBuilder();
/*  83 */     for (PatternFormatter formatter : this.patternFormatters) {
/*  84 */       formatter.format(event, buf);
/*     */     }
/*     */     
/*  87 */     if (buf.length() > 0) {
/*  88 */       toAppendTo.append(this.style).append(buf.toString()).append(AnsiEscape.getDefaultStyle());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean handlesThrowable() {
/*  94 */     for (PatternFormatter formatter : this.patternFormatters) {
/*  95 */       if (formatter.handlesThrowable()) {
/*  96 */         return true;
/*     */       }
/*     */     } 
/*  99 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 109 */     StringBuilder sb = new StringBuilder();
/* 110 */     sb.append(super.toString());
/* 111 */     sb.append("[style=");
/* 112 */     sb.append(this.style);
/* 113 */     sb.append(", patternFormatters=");
/* 114 */     sb.append(this.patternFormatters);
/* 115 */     sb.append("]");
/* 116 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\pattern\StyleConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
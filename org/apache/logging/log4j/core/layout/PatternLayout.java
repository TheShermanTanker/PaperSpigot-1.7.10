/*     */ package org.apache.logging.log4j.core.layout;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.config.Configuration;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginElement;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*     */ import org.apache.logging.log4j.core.helpers.Booleans;
/*     */ import org.apache.logging.log4j.core.helpers.Charsets;
/*     */ import org.apache.logging.log4j.core.helpers.OptionConverter;
/*     */ import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
/*     */ import org.apache.logging.log4j.core.pattern.PatternFormatter;
/*     */ import org.apache.logging.log4j.core.pattern.PatternParser;
/*     */ import org.apache.logging.log4j.core.pattern.RegexReplacement;
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
/*     */ @Plugin(name = "PatternLayout", category = "Core", elementType = "layout", printObject = true)
/*     */ public final class PatternLayout
/*     */   extends AbstractStringLayout
/*     */ {
/*     */   public static final String DEFAULT_CONVERSION_PATTERN = "%m%n";
/*     */   public static final String TTCC_CONVERSION_PATTERN = "%r [%t] %p %c %x - %m%n";
/*     */   public static final String SIMPLE_CONVERSION_PATTERN = "%d [%t] %p %c - %m%n";
/*     */   public static final String KEY = "Converter";
/*     */   private List<PatternFormatter> formatters;
/*     */   private final String conversionPattern;
/*     */   private final Configuration config;
/*     */   private final RegexReplacement replace;
/*     */   private final boolean alwaysWriteExceptions;
/*     */   
/*     */   private PatternLayout(Configuration config, RegexReplacement replace, String pattern, Charset charset, boolean alwaysWriteExceptions) {
/* 110 */     super(charset);
/* 111 */     this.replace = replace;
/* 112 */     this.conversionPattern = pattern;
/* 113 */     this.config = config;
/* 114 */     this.alwaysWriteExceptions = alwaysWriteExceptions;
/* 115 */     PatternParser parser = createPatternParser(config);
/* 116 */     this.formatters = parser.parse((pattern == null) ? "%m%n" : pattern, this.alwaysWriteExceptions);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setConversionPattern(String conversionPattern) {
/* 127 */     String pattern = OptionConverter.convertSpecialChars(conversionPattern);
/* 128 */     if (pattern == null) {
/*     */       return;
/*     */     }
/* 131 */     PatternParser parser = createPatternParser(this.config);
/* 132 */     this.formatters = parser.parse(pattern, this.alwaysWriteExceptions);
/*     */   }
/*     */   
/*     */   public String getConversionPattern() {
/* 136 */     return this.conversionPattern;
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
/*     */   
/*     */   public Map<String, String> getContentFormat() {
/* 149 */     Map<String, String> result = new HashMap<String, String>();
/* 150 */     result.put("structured", "false");
/* 151 */     result.put("formatType", "conversion");
/* 152 */     result.put("format", this.conversionPattern);
/* 153 */     return result;
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
/*     */   public String toSerializable(LogEvent event) {
/* 165 */     StringBuilder buf = new StringBuilder();
/* 166 */     for (PatternFormatter formatter : this.formatters) {
/* 167 */       formatter.format(event, buf);
/*     */     }
/* 169 */     String str = buf.toString();
/* 170 */     if (this.replace != null) {
/* 171 */       str = this.replace.format(str);
/*     */     }
/* 173 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PatternParser createPatternParser(Configuration config) {
/* 182 */     if (config == null) {
/* 183 */       return new PatternParser(config, "Converter", LogEventPatternConverter.class);
/*     */     }
/* 185 */     PatternParser parser = (PatternParser)config.getComponent("Converter");
/* 186 */     if (parser == null) {
/* 187 */       parser = new PatternParser(config, "Converter", LogEventPatternConverter.class);
/* 188 */       config.addComponent("Converter", parser);
/* 189 */       parser = (PatternParser)config.getComponent("Converter");
/*     */     } 
/* 191 */     return parser;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 196 */     return this.conversionPattern;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @PluginFactory
/*     */   public static PatternLayout createLayout(@PluginAttribute("pattern") String pattern, @PluginConfiguration Configuration config, @PluginElement("Replace") RegexReplacement replace, @PluginAttribute("charset") String charsetName, @PluginAttribute("alwaysWriteExceptions") String always) {
/* 217 */     Charset charset = Charsets.getSupportedCharset(charsetName);
/* 218 */     boolean alwaysWriteExceptions = Booleans.parseBoolean(always, true);
/* 219 */     return new PatternLayout(config, replace, (pattern == null) ? "%m%n" : pattern, charset, alwaysWriteExceptions);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\layout\PatternLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
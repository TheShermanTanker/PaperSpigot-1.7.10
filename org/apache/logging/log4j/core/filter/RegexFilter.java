/*     */ package org.apache.logging.log4j.core.filter;
/*     */ 
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.Marker;
/*     */ import org.apache.logging.log4j.core.Filter;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.Logger;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*     */ import org.apache.logging.log4j.message.Message;
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
/*     */ @Plugin(name = "RegexFilter", category = "Core", elementType = "filter", printObject = true)
/*     */ public final class RegexFilter
/*     */   extends AbstractFilter
/*     */ {
/*     */   private final Pattern pattern;
/*     */   private final boolean useRawMessage;
/*     */   
/*     */   private RegexFilter(boolean raw, Pattern pattern, Filter.Result onMatch, Filter.Result onMismatch) {
/*  46 */     super(onMatch, onMismatch);
/*  47 */     this.pattern = pattern;
/*  48 */     this.useRawMessage = raw;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object... params) {
/*  54 */     return filter(msg);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Filter.Result filter(Logger logger, Level level, Marker marker, Object msg, Throwable t) {
/*  60 */     if (msg == null) {
/*  61 */       return this.onMismatch;
/*     */     }
/*  63 */     return filter(msg.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Filter.Result filter(Logger logger, Level level, Marker marker, Message msg, Throwable t) {
/*  69 */     if (msg == null) {
/*  70 */       return this.onMismatch;
/*     */     }
/*  72 */     String text = this.useRawMessage ? msg.getFormat() : msg.getFormattedMessage();
/*  73 */     return filter(text);
/*     */   }
/*     */ 
/*     */   
/*     */   public Filter.Result filter(LogEvent event) {
/*  78 */     String text = this.useRawMessage ? event.getMessage().getFormat() : event.getMessage().getFormattedMessage();
/*  79 */     return filter(text);
/*     */   }
/*     */   
/*     */   private Filter.Result filter(String msg) {
/*  83 */     if (msg == null) {
/*  84 */       return this.onMismatch;
/*     */     }
/*  86 */     Matcher m = this.pattern.matcher(msg);
/*  87 */     return m.matches() ? this.onMatch : this.onMismatch;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  92 */     StringBuilder sb = new StringBuilder();
/*  93 */     sb.append("useRaw=").append(this.useRawMessage);
/*  94 */     sb.append(", pattern=").append(this.pattern.toString());
/*  95 */     return sb.toString();
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
/*     */   @PluginFactory
/*     */   public static RegexFilter createFilter(@PluginAttribute("regex") String regex, @PluginAttribute("useRawMsg") String useRawMsg, @PluginAttribute("onMatch") String match, @PluginAttribute("onMismatch") String mismatch) {
/*     */     Pattern pattern;
/* 113 */     if (regex == null) {
/* 114 */       LOGGER.error("A regular expression must be provided for RegexFilter");
/* 115 */       return null;
/*     */     } 
/* 117 */     boolean raw = Boolean.parseBoolean(useRawMsg);
/*     */     
/*     */     try {
/* 120 */       pattern = Pattern.compile(regex);
/* 121 */     } catch (Exception ex) {
/* 122 */       LOGGER.error("RegexFilter caught exception compiling pattern: " + regex + " cause: " + ex.getMessage());
/* 123 */       return null;
/*     */     } 
/* 125 */     Filter.Result onMatch = Filter.Result.toResult(match);
/* 126 */     Filter.Result onMismatch = Filter.Result.toResult(mismatch);
/*     */     
/* 128 */     return new RegexFilter(raw, pattern, onMatch, onMismatch);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\filter\RegexFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
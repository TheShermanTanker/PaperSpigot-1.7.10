/*    */ package org.apache.logging.log4j.core.filter;
/*    */ 
/*    */ import org.apache.logging.log4j.Level;
/*    */ import org.apache.logging.log4j.Marker;
/*    */ import org.apache.logging.log4j.core.Filter;
/*    */ import org.apache.logging.log4j.core.LogEvent;
/*    */ import org.apache.logging.log4j.core.Logger;
/*    */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*    */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*    */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*    */ import org.apache.logging.log4j.message.Message;
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
/*    */ @Plugin(name = "ThresholdFilter", category = "Core", elementType = "filter", printObject = true)
/*    */ public final class ThresholdFilter
/*    */   extends AbstractFilter
/*    */ {
/*    */   private final Level level;
/*    */   
/*    */   private ThresholdFilter(Level level, Filter.Result onMatch, Filter.Result onMismatch) {
/* 42 */     super(onMatch, onMismatch);
/* 43 */     this.level = level;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object... params) {
/* 49 */     return filter(level);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Filter.Result filter(Logger logger, Level level, Marker marker, Object msg, Throwable t) {
/* 55 */     return filter(level);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Filter.Result filter(Logger logger, Level level, Marker marker, Message msg, Throwable t) {
/* 61 */     return filter(level);
/*    */   }
/*    */ 
/*    */   
/*    */   public Filter.Result filter(LogEvent event) {
/* 66 */     return filter(event.getLevel());
/*    */   }
/*    */   
/*    */   private Filter.Result filter(Level level) {
/* 70 */     return level.isAtLeastAsSpecificAs(this.level) ? this.onMatch : this.onMismatch;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 75 */     return this.level.toString();
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
/*    */   @PluginFactory
/*    */   public static ThresholdFilter createFilter(@PluginAttribute("level") String levelName, @PluginAttribute("onMatch") String match, @PluginAttribute("onMismatch") String mismatch) {
/* 90 */     Level level = Level.toLevel(levelName, Level.ERROR);
/* 91 */     Filter.Result onMatch = Filter.Result.toResult(match, Filter.Result.NEUTRAL);
/* 92 */     Filter.Result onMismatch = Filter.Result.toResult(mismatch, Filter.Result.DENY);
/* 93 */     return new ThresholdFilter(level, onMatch, onMismatch);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\filter\ThresholdFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
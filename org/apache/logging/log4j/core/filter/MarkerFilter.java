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
/*    */ @Plugin(name = "MarkerFilter", category = "Core", elementType = "filter", printObject = true)
/*    */ public final class MarkerFilter
/*    */   extends AbstractFilter
/*    */ {
/*    */   private final String name;
/*    */   
/*    */   private MarkerFilter(String name, Filter.Result onMatch, Filter.Result onMismatch) {
/* 39 */     super(onMatch, onMismatch);
/* 40 */     this.name = name;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object... params) {
/* 46 */     return filter(marker);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Filter.Result filter(Logger logger, Level level, Marker marker, Object msg, Throwable t) {
/* 52 */     return filter(marker);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Filter.Result filter(Logger logger, Level level, Marker marker, Message msg, Throwable t) {
/* 58 */     return filter(marker);
/*    */   }
/*    */ 
/*    */   
/*    */   public Filter.Result filter(LogEvent event) {
/* 63 */     return filter(event.getMarker());
/*    */   }
/*    */   
/*    */   private Filter.Result filter(Marker marker) {
/* 67 */     return (marker != null && marker.isInstanceOf(this.name)) ? this.onMatch : this.onMismatch;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 72 */     return this.name;
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
/*    */   
/*    */   @PluginFactory
/*    */   public static MarkerFilter createFilter(@PluginAttribute("marker") String marker, @PluginAttribute("onMatch") String match, @PluginAttribute("onMismatch") String mismatch) {
/* 88 */     if (marker == null) {
/* 89 */       LOGGER.error("A marker must be provided for MarkerFilter");
/* 90 */       return null;
/*    */     } 
/* 92 */     Filter.Result onMatch = Filter.Result.toResult(match);
/* 93 */     Filter.Result onMismatch = Filter.Result.toResult(mismatch);
/* 94 */     return new MarkerFilter(marker, onMatch, onMismatch);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\filter\MarkerFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
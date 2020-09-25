/*    */ package org.apache.logging.log4j.core.config.plugins;
/*    */ 
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ import java.util.concurrent.ConcurrentMap;
/*    */ import org.apache.logging.log4j.core.Appender;
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
/*    */ @Plugin(name = "appenders", category = "Core")
/*    */ public final class AppendersPlugin
/*    */ {
/*    */   @PluginFactory
/*    */   public static ConcurrentMap<String, Appender> createAppenders(@PluginElement("Appenders") Appender[] appenders) {
/* 42 */     ConcurrentMap<String, Appender> map = new ConcurrentHashMap<String, Appender>();
/*    */ 
/*    */     
/* 45 */     for (Appender appender : appenders) {
/* 46 */       map.put(appender.getName(), appender);
/*    */     }
/*    */     
/* 49 */     return map;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\config\plugins\AppendersPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
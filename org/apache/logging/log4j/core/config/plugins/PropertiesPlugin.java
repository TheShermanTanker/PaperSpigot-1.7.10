/*    */ package org.apache.logging.log4j.core.config.plugins;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import org.apache.logging.log4j.core.config.Configuration;
/*    */ import org.apache.logging.log4j.core.config.Property;
/*    */ import org.apache.logging.log4j.core.lookup.Interpolator;
/*    */ import org.apache.logging.log4j.core.lookup.MapLookup;
/*    */ import org.apache.logging.log4j.core.lookup.StrLookup;
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
/*    */ @Plugin(name = "properties", category = "Core", printObject = true)
/*    */ public final class PropertiesPlugin
/*    */ {
/*    */   @PluginFactory
/*    */   public static StrLookup configureSubstitutor(@PluginElement("Properties") Property[] properties, @PluginConfiguration Configuration config) {
/* 46 */     if (properties == null) {
/* 47 */       return (StrLookup)new Interpolator(null);
/*    */     }
/* 49 */     Map<String, String> map = new HashMap<String, String>(config.getProperties());
/*    */     
/* 51 */     for (Property prop : properties) {
/* 52 */       map.put(prop.getName(), prop.getValue());
/*    */     }
/*    */     
/* 55 */     return (StrLookup)new Interpolator((StrLookup)new MapLookup(map));
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\config\plugins\PropertiesPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
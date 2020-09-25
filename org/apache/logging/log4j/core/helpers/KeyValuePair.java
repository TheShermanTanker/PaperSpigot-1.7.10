/*    */ package org.apache.logging.log4j.core.helpers;
/*    */ 
/*    */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*    */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*    */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
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
/*    */ @Plugin(name = "KeyValuePair", category = "Core", printObject = true)
/*    */ public class KeyValuePair
/*    */ {
/*    */   private final String key;
/*    */   private final String value;
/*    */   
/*    */   public KeyValuePair(String key, String value) {
/* 38 */     this.key = key;
/* 39 */     this.value = value;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getKey() {
/* 47 */     return this.key;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getValue() {
/* 55 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 60 */     return this.key + "=" + this.value;
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
/*    */   @PluginFactory
/*    */   public static KeyValuePair createPair(@PluginAttribute("key") String key, @PluginAttribute("value") String value) {
/* 74 */     return new KeyValuePair(key, value);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\helpers\KeyValuePair.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
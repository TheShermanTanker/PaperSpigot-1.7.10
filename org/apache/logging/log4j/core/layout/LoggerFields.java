/*    */ package org.apache.logging.log4j.core.layout;
/*    */ 
/*    */ import java.util.Collections;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*    */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*    */ import org.apache.logging.log4j.core.config.plugins.PluginElement;
/*    */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*    */ import org.apache.logging.log4j.core.helpers.Booleans;
/*    */ import org.apache.logging.log4j.core.helpers.KeyValuePair;
/*    */ import org.apache.logging.log4j.message.StructuredDataId;
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
/*    */ @Plugin(name = "LoggerFields", category = "Core", printObject = true)
/*    */ public final class LoggerFields
/*    */ {
/*    */   private final Map<String, String> map;
/*    */   private final String sdId;
/*    */   private final String enterpriseId;
/*    */   private final boolean discardIfAllFieldsAreEmpty;
/*    */   
/*    */   private LoggerFields(Map<String, String> map, String sdId, String enterpriseId, boolean discardIfAllFieldsAreEmpty) {
/* 44 */     this.sdId = sdId;
/* 45 */     this.enterpriseId = enterpriseId;
/* 46 */     this.map = Collections.unmodifiableMap(map);
/* 47 */     this.discardIfAllFieldsAreEmpty = discardIfAllFieldsAreEmpty;
/*    */   }
/*    */   
/*    */   public Map<String, String> getMap() {
/* 51 */     return this.map;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 56 */     return this.map.toString();
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
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @PluginFactory
/*    */   public static LoggerFields createLoggerFields(@PluginElement("LoggerFields") KeyValuePair[] keyValuePairs, @PluginAttribute("sdId") String sdId, @PluginAttribute("enterpriseId") String enterpriseId, @PluginAttribute("discardIfAllFieldsAreEmpty") String discardIfAllFieldsAreEmpty) {
/* 76 */     Map<String, String> map = new HashMap<String, String>();
/*    */     
/* 78 */     for (KeyValuePair keyValuePair : keyValuePairs) {
/* 79 */       map.put(keyValuePair.getKey(), keyValuePair.getValue());
/*    */     }
/*    */     
/* 82 */     boolean discardIfEmpty = Booleans.parseBoolean(discardIfAllFieldsAreEmpty, false);
/* 83 */     return new LoggerFields(map, sdId, enterpriseId, discardIfEmpty);
/*    */   }
/*    */   
/*    */   public StructuredDataId getSdId() {
/* 87 */     if (this.enterpriseId == null || this.sdId == null) {
/* 88 */       return null;
/*    */     }
/* 90 */     int eId = Integer.parseInt(this.enterpriseId);
/* 91 */     return new StructuredDataId(this.sdId, eId, null, null);
/*    */   }
/*    */   
/*    */   public boolean getDiscardIfAllFieldsAreEmpty() {
/* 95 */     return this.discardIfAllFieldsAreEmpty;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\layout\LoggerFields.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
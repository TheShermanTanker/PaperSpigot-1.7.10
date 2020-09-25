/*    */ package org.apache.logging.log4j.core.config;
/*    */ 
/*    */ import java.io.File;
/*    */ import org.apache.logging.log4j.core.config.plugins.Plugin;
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
/*    */ @Plugin(name = "JSONConfigurationFactory", category = "ConfigurationFactory")
/*    */ @Order(6)
/*    */ public class JSONConfigurationFactory
/*    */   extends ConfigurationFactory
/*    */ {
/* 33 */   public static final String[] SUFFIXES = new String[] { ".json", ".jsn" };
/*    */   
/* 35 */   private static String[] dependencies = new String[] { "com.fasterxml.jackson.databind.ObjectMapper", "com.fasterxml.jackson.databind.JsonNode", "com.fasterxml.jackson.core.JsonParser" };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 41 */   private final File configFile = null;
/*    */   
/*    */   private boolean isActive;
/*    */   
/*    */   public JSONConfigurationFactory() {
/*    */     try {
/* 47 */       for (String item : dependencies) {
/* 48 */         Class.forName(item);
/*    */       }
/* 50 */     } catch (ClassNotFoundException ex) {
/* 51 */       LOGGER.debug("Missing dependencies for Json support");
/* 52 */       this.isActive = false;
/*    */       return;
/*    */     } 
/* 55 */     this.isActive = true;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isActive() {
/* 60 */     return this.isActive;
/*    */   }
/*    */ 
/*    */   
/*    */   public Configuration getConfiguration(ConfigurationFactory.ConfigurationSource source) {
/* 65 */     if (!this.isActive) {
/* 66 */       return null;
/*    */     }
/* 68 */     return new JSONConfiguration(source);
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getSupportedTypes() {
/* 73 */     return SUFFIXES;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\config\JSONConfigurationFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
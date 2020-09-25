/*    */ package org.bukkit.configuration;
/*    */ 
/*    */ import java.util.Map;
/*    */ import org.apache.commons.lang.Validate;
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
/*    */ public class MemoryConfiguration
/*    */   extends MemorySection
/*    */   implements Configuration
/*    */ {
/*    */   protected Configuration defaults;
/*    */   protected MemoryConfigurationOptions options;
/*    */   
/*    */   public MemoryConfiguration() {}
/*    */   
/*    */   public MemoryConfiguration(Configuration defaults) {
/* 29 */     this.defaults = defaults;
/*    */   }
/*    */ 
/*    */   
/*    */   public void addDefault(String path, Object value) {
/* 34 */     Validate.notNull(path, "Path may not be null");
/*    */     
/* 36 */     if (this.defaults == null) {
/* 37 */       this.defaults = new MemoryConfiguration();
/*    */     }
/*    */     
/* 40 */     this.defaults.set(path, value);
/*    */   }
/*    */   
/*    */   public void addDefaults(Map<String, Object> defaults) {
/* 44 */     Validate.notNull(defaults, "Defaults may not be null");
/*    */     
/* 46 */     for (Map.Entry<String, Object> entry : defaults.entrySet()) {
/* 47 */       addDefault(entry.getKey(), entry.getValue());
/*    */     }
/*    */   }
/*    */   
/*    */   public void addDefaults(Configuration defaults) {
/* 52 */     Validate.notNull(defaults, "Defaults may not be null");
/*    */     
/* 54 */     addDefaults(defaults.getValues(true));
/*    */   }
/*    */   
/*    */   public void setDefaults(Configuration defaults) {
/* 58 */     Validate.notNull(defaults, "Defaults may not be null");
/*    */     
/* 60 */     this.defaults = defaults;
/*    */   }
/*    */   
/*    */   public Configuration getDefaults() {
/* 64 */     return this.defaults;
/*    */   }
/*    */ 
/*    */   
/*    */   public ConfigurationSection getParent() {
/* 69 */     return null;
/*    */   }
/*    */   
/*    */   public MemoryConfigurationOptions options() {
/* 73 */     if (this.options == null) {
/* 74 */       this.options = new MemoryConfigurationOptions(this);
/*    */     }
/*    */     
/* 77 */     return this.options;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\configuration\MemoryConfiguration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
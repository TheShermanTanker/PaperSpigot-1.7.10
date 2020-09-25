/*    */ package org.bukkit.configuration.file;
/*    */ 
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.configuration.Configuration;
/*    */ import org.bukkit.configuration.ConfigurationOptions;
/*    */ import org.bukkit.configuration.MemoryConfiguration;
/*    */ import org.bukkit.configuration.MemoryConfigurationOptions;
/*    */ 
/*    */ public class YamlConfigurationOptions extends FileConfigurationOptions {
/* 10 */   private int indent = 2;
/*    */   
/*    */   protected YamlConfigurationOptions(YamlConfiguration configuration) {
/* 13 */     super(configuration);
/*    */   }
/*    */ 
/*    */   
/*    */   public YamlConfiguration configuration() {
/* 18 */     return (YamlConfiguration)super.configuration();
/*    */   }
/*    */ 
/*    */   
/*    */   public YamlConfigurationOptions copyDefaults(boolean value) {
/* 23 */     super.copyDefaults(value);
/* 24 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public YamlConfigurationOptions pathSeparator(char value) {
/* 29 */     super.pathSeparator(value);
/* 30 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public YamlConfigurationOptions header(String value) {
/* 35 */     super.header(value);
/* 36 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public YamlConfigurationOptions copyHeader(boolean value) {
/* 41 */     super.copyHeader(value);
/* 42 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int indent() {
/* 53 */     return this.indent;
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
/*    */   public YamlConfigurationOptions indent(int value) {
/* 65 */     Validate.isTrue((value >= 2), "Indent must be at least 2 characters");
/* 66 */     Validate.isTrue((value <= 9), "Indent cannot be greater than 9 characters");
/*    */     
/* 68 */     this.indent = value;
/* 69 */     return this;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\configuration\file\YamlConfigurationOptions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
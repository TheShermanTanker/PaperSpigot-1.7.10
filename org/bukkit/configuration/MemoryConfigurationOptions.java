/*    */ package org.bukkit.configuration;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MemoryConfigurationOptions
/*    */   extends ConfigurationOptions
/*    */ {
/*    */   protected MemoryConfigurationOptions(MemoryConfiguration configuration) {
/*  9 */     super(configuration);
/*    */   }
/*    */ 
/*    */   
/*    */   public MemoryConfiguration configuration() {
/* 14 */     return (MemoryConfiguration)super.configuration();
/*    */   }
/*    */ 
/*    */   
/*    */   public MemoryConfigurationOptions copyDefaults(boolean value) {
/* 19 */     super.copyDefaults(value);
/* 20 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public MemoryConfigurationOptions pathSeparator(char value) {
/* 25 */     super.pathSeparator(value);
/* 26 */     return this;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\configuration\MemoryConfigurationOptions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
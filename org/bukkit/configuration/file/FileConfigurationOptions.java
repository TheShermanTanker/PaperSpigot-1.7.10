/*     */ package org.bukkit.configuration.file;
/*     */ 
/*     */ import org.bukkit.configuration.Configuration;
/*     */ import org.bukkit.configuration.ConfigurationOptions;
/*     */ import org.bukkit.configuration.MemoryConfiguration;
/*     */ import org.bukkit.configuration.MemoryConfigurationOptions;
/*     */ 
/*     */ public class FileConfigurationOptions
/*     */   extends MemoryConfigurationOptions {
/*  10 */   private String header = null;
/*     */   private boolean copyHeader = true;
/*     */   
/*     */   protected FileConfigurationOptions(MemoryConfiguration configuration) {
/*  14 */     super(configuration);
/*     */   }
/*     */ 
/*     */   
/*     */   public FileConfiguration configuration() {
/*  19 */     return (FileConfiguration)super.configuration();
/*     */   }
/*     */ 
/*     */   
/*     */   public FileConfigurationOptions copyDefaults(boolean value) {
/*  24 */     super.copyDefaults(value);
/*  25 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public FileConfigurationOptions pathSeparator(char value) {
/*  30 */     super.pathSeparator(value);
/*  31 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String header() {
/*  49 */     return this.header;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FileConfigurationOptions header(String value) {
/*  68 */     this.header = value;
/*  69 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean copyHeader() {
/*  91 */     return this.copyHeader;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FileConfigurationOptions copyHeader(boolean value) {
/* 114 */     this.copyHeader = value;
/*     */     
/* 116 */     return this;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\configuration\file\FileConfigurationOptions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
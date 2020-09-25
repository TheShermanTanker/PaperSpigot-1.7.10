/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.Properties;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.bukkit.craftbukkit.libs.joptsimple.OptionSet;
/*     */ 
/*     */ 
/*     */ public class PropertyManager
/*     */ {
/*  15 */   private static final Logger loggingAgent = LogManager.getLogger();
/*  16 */   public final Properties properties = new Properties();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final File c;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private OptionSet options;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropertyManager(File file1) {
/*  46 */     this.options = null; this.c = file1; if (file1.exists()) { FileInputStream fileinputstream = null; try { fileinputstream = new FileInputStream(file1); this.properties.load(fileinputstream); } catch (Exception exception) { loggingAgent.warn("Failed to load " + file1, exception); a(); } finally { if (fileinputstream != null)
/*     */           try { fileinputstream.close(); } catch (IOException ioexception) {}  }  }
/*     */     else { loggingAgent.warn(file1 + " does not exist"); a(); }
/*  49 */      } public PropertyManager(OptionSet options) { this((File)options.valueOf("config"));
/*     */     
/*  51 */     this.options = options; }
/*     */ 
/*     */   
/*     */   private <T> T getOverride(String name, T value) {
/*  55 */     if (this.options != null && this.options.has(name) && !name.equals("online-mode")) {
/*  56 */       return (T)this.options.valueOf(name);
/*     */     }
/*     */     
/*  59 */     return value;
/*     */   }
/*     */ 
/*     */   
/*     */   public void a() {
/*  64 */     loggingAgent.info("Generating new properties file");
/*  65 */     savePropertiesFile();
/*     */   }
/*     */   
/*     */   public void savePropertiesFile() {
/*  69 */     FileOutputStream fileoutputstream = null;
/*     */ 
/*     */     
/*     */     try {
/*  73 */       if (this.c.exists() && !this.c.canWrite()) {
/*     */         return;
/*     */       }
/*     */       
/*  77 */       fileoutputstream = new FileOutputStream(this.c);
/*  78 */       this.properties.store(fileoutputstream, "Minecraft server properties");
/*  79 */     } catch (Exception exception) {
/*  80 */       loggingAgent.warn("Failed to save " + this.c, exception);
/*  81 */       a();
/*     */     } finally {
/*  83 */       if (fileoutputstream != null) {
/*     */         try {
/*  85 */           fileoutputstream.close();
/*  86 */         } catch (IOException ioexception) {}
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public File c() {
/*  94 */     return this.c;
/*     */   }
/*     */   
/*     */   public String getString(String s, String s1) {
/*  98 */     if (!this.properties.containsKey(s)) {
/*  99 */       this.properties.setProperty(s, s1);
/* 100 */       savePropertiesFile();
/* 101 */       savePropertiesFile();
/*     */     } 
/*     */     
/* 104 */     return getOverride(s, this.properties.getProperty(s, s1));
/*     */   }
/*     */   
/*     */   public int getInt(String s, int i) {
/*     */     try {
/* 109 */       return ((Integer)getOverride(s, Integer.valueOf(Integer.parseInt(getString(s, "" + i))))).intValue();
/* 110 */     } catch (Exception exception) {
/* 111 */       this.properties.setProperty(s, "" + i);
/* 112 */       savePropertiesFile();
/* 113 */       return ((Integer)getOverride(s, Integer.valueOf(i))).intValue();
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean getBoolean(String s, boolean flag) {
/*     */     try {
/* 119 */       return ((Boolean)getOverride(s, Boolean.valueOf(Boolean.parseBoolean(getString(s, "" + flag))))).booleanValue();
/* 120 */     } catch (Exception exception) {
/* 121 */       this.properties.setProperty(s, "" + flag);
/* 122 */       savePropertiesFile();
/* 123 */       return ((Boolean)getOverride(s, Boolean.valueOf(flag))).booleanValue();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setProperty(String s, Object object) {
/* 128 */     this.properties.setProperty(s, "" + object);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PropertyManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
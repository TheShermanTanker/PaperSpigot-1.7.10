/*     */ package org.apache.logging.log4j.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.Properties;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.status.StatusLogger;
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
/*     */ 
/*     */ public class PropertiesUtil
/*     */ {
/*  31 */   private static final PropertiesUtil LOG4J_PROPERTIES = new PropertiesUtil("log4j2.component.properties");
/*     */   
/*  33 */   private static final Logger LOGGER = (Logger)StatusLogger.getLogger();
/*     */   
/*     */   private final Properties props;
/*     */   
/*     */   public PropertiesUtil(Properties props) {
/*  38 */     this.props = props;
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
/*     */   static Properties loadClose(InputStream in, Object source) {
/*  53 */     Properties props = new Properties();
/*  54 */     if (null != in) {
/*     */       try {
/*  56 */         props.load(in);
/*  57 */       } catch (IOException e) {
/*  58 */         LOGGER.error("Unable to read " + source, e);
/*     */       } finally {
/*     */         try {
/*  61 */           in.close();
/*  62 */         } catch (IOException e) {
/*  63 */           LOGGER.error("Unable to close " + source, e);
/*     */         } 
/*     */       } 
/*     */     }
/*  67 */     return props;
/*     */   }
/*     */   
/*     */   public PropertiesUtil(String propsLocn) {
/*  71 */     ClassLoader loader = ProviderUtil.findClassLoader();
/*  72 */     InputStream in = loader.getResourceAsStream(propsLocn);
/*  73 */     this.props = loadClose(in, propsLocn);
/*     */   }
/*     */   
/*     */   public static PropertiesUtil getProperties() {
/*  77 */     return LOG4J_PROPERTIES;
/*     */   }
/*     */   
/*     */   public String getStringProperty(String name) {
/*  81 */     String prop = null;
/*     */     try {
/*  83 */       prop = System.getProperty(name);
/*  84 */     } catch (SecurityException e) {}
/*     */ 
/*     */     
/*  87 */     return (prop == null) ? this.props.getProperty(name) : prop;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getIntegerProperty(String name, int defaultValue) {
/*  92 */     String prop = null;
/*     */     try {
/*  94 */       prop = System.getProperty(name);
/*  95 */     } catch (SecurityException e) {}
/*     */ 
/*     */     
/*  98 */     if (prop == null) {
/*  99 */       prop = this.props.getProperty(name);
/*     */     }
/* 101 */     if (prop != null) {
/*     */       try {
/* 103 */         return Integer.parseInt(prop);
/* 104 */       } catch (Exception ex) {
/* 105 */         return defaultValue;
/*     */       } 
/*     */     }
/* 108 */     return defaultValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public long getLongProperty(String name, long defaultValue) {
/* 113 */     String prop = null;
/*     */     try {
/* 115 */       prop = System.getProperty(name);
/* 116 */     } catch (SecurityException e) {}
/*     */ 
/*     */     
/* 119 */     if (prop == null) {
/* 120 */       prop = this.props.getProperty(name);
/*     */     }
/* 122 */     if (prop != null) {
/*     */       try {
/* 124 */         return Long.parseLong(prop);
/* 125 */       } catch (Exception ex) {
/* 126 */         return defaultValue;
/*     */       } 
/*     */     }
/* 129 */     return defaultValue;
/*     */   }
/*     */   
/*     */   public String getStringProperty(String name, String defaultValue) {
/* 133 */     String prop = getStringProperty(name);
/* 134 */     return (prop == null) ? defaultValue : prop;
/*     */   }
/*     */   
/*     */   public boolean getBooleanProperty(String name) {
/* 138 */     return getBooleanProperty(name, false);
/*     */   }
/*     */   
/*     */   public boolean getBooleanProperty(String name, boolean defaultValue) {
/* 142 */     String prop = getStringProperty(name);
/* 143 */     return (prop == null) ? defaultValue : "true".equalsIgnoreCase(prop);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Properties getSystemProperties() {
/*     */     try {
/* 152 */       return new Properties(System.getProperties());
/* 153 */     } catch (SecurityException ex) {
/* 154 */       StatusLogger.getLogger().error("Unable to access system properties.");
/*     */       
/* 156 */       return new Properties();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4\\util\PropertiesUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
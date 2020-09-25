/*     */ package com.avaje.ebean.config;
/*     */ 
/*     */ import com.avaje.ebeaninternal.api.ClassUtil;
/*     */ import java.util.Map;
/*     */ import javax.servlet.ServletContext;
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
/*     */ public final class GlobalProperties
/*     */ {
/*     */   private static volatile PropertyMap globalMap;
/*     */   private static boolean skipPrimaryServer;
/*     */   
/*     */   public static synchronized void setSkipPrimaryServer(boolean skip) {
/*  23 */     skipPrimaryServer = skip;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized boolean isSkipPrimaryServer() {
/*  30 */     return skipPrimaryServer;
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
/*     */   public static String evaluateExpressions(String val) {
/*  45 */     return getPropertyMap().eval(val);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized void evaluateExpressions() {
/*  53 */     getPropertyMap().evaluateProperties();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized void setServletContext(ServletContext servletContext) {
/*  62 */     PropertyMapLoader.setServletContext(servletContext);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized ServletContext getServletContext() {
/*  70 */     return PropertyMapLoader.getServletContext();
/*     */   }
/*     */ 
/*     */   
/*     */   private static void initPropertyMap() {
/*  75 */     String fileName = System.getenv("EBEAN_PROPS_FILE");
/*  76 */     if (fileName == null) {
/*  77 */       fileName = System.getProperty("ebean.props.file");
/*  78 */       if (fileName == null) {
/*  79 */         fileName = "ebean.properties";
/*     */       }
/*     */     } 
/*     */     
/*  83 */     globalMap = PropertyMapLoader.load((PropertyMap)null, fileName);
/*  84 */     if (globalMap == null)
/*     */     {
/*     */       
/*  87 */       globalMap = new PropertyMap();
/*     */     }
/*     */     
/*  90 */     String loaderCn = globalMap.get("ebean.properties.loader");
/*  91 */     if (loaderCn != null) {
/*     */       
/*     */       try {
/*     */         
/*  95 */         Runnable r = (Runnable)ClassUtil.newInstance(loaderCn);
/*  96 */         r.run();
/*  97 */       } catch (Exception e) {
/*  98 */         String m = "Error creating or running properties loader " + loaderCn;
/*  99 */         throw new RuntimeException(m, e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static synchronized PropertyMap getPropertyMap() {
/* 109 */     if (globalMap == null) {
/* 110 */       initPropertyMap();
/*     */     }
/*     */     
/* 113 */     return globalMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized String get(String key, String defaultValue) {
/* 120 */     return getPropertyMap().get(key, defaultValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized int getInt(String key, int defaultValue) {
/* 127 */     return getPropertyMap().getInt(key, defaultValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized boolean getBoolean(String key, boolean defaultValue) {
/* 134 */     return getPropertyMap().getBoolean(key, defaultValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized String put(String key, String value) {
/* 142 */     return getPropertyMap().putEval(key, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized void putAll(Map<String, String> keyValueMap) {
/* 149 */     for (Map.Entry<String, String> e : keyValueMap.entrySet()) {
/* 150 */       getPropertyMap().putEval(e.getKey(), e.getValue());
/*     */     }
/*     */   }
/*     */   
/*     */   public static PropertySource getPropertySource(String name) {
/* 155 */     return new ConfigPropertyMap(name);
/*     */   }
/*     */   
/*     */   public static interface PropertySource {
/*     */     String getServerName();
/*     */     
/*     */     String get(String param1String1, String param1String2);
/*     */     
/*     */     int getInt(String param1String, int param1Int);
/*     */     
/*     */     boolean getBoolean(String param1String, boolean param1Boolean);
/*     */     
/*     */     <T extends Enum<T>> T getEnum(Class<T> param1Class, String param1String, T param1T);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\config\GlobalProperties.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
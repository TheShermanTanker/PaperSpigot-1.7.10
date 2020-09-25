/*     */ package org.apache.logging.log4j.core.lookup;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginManager;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginType;
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
/*     */ public class Interpolator
/*     */   implements StrLookup
/*     */ {
/*  33 */   private static final Logger LOGGER = (Logger)StatusLogger.getLogger();
/*     */ 
/*     */   
/*     */   private static final char PREFIX_SEPARATOR = ':';
/*     */   
/*  38 */   private final Map<String, StrLookup> lookups = new HashMap<String, StrLookup>();
/*     */   
/*     */   private final StrLookup defaultLookup;
/*     */   
/*     */   public Interpolator(StrLookup defaultLookup) {
/*  43 */     this.defaultLookup = (defaultLookup == null) ? new MapLookup(new HashMap<String, String>()) : defaultLookup;
/*  44 */     PluginManager manager = new PluginManager("Lookup");
/*  45 */     manager.collectPlugins();
/*  46 */     Map<String, PluginType<?>> plugins = manager.getPlugins();
/*     */     
/*  48 */     for (Map.Entry<String, PluginType<?>> entry : plugins.entrySet()) {
/*     */       
/*  50 */       Class<? extends StrLookup> clazz = ((PluginType)entry.getValue()).getPluginClass();
/*     */       try {
/*  52 */         this.lookups.put(entry.getKey(), clazz.newInstance());
/*  53 */       } catch (Exception ex) {
/*  54 */         LOGGER.error("Unable to create Lookup for " + (String)entry.getKey(), ex);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Interpolator() {
/*  63 */     this.defaultLookup = new MapLookup(new HashMap<String, String>());
/*  64 */     this.lookups.put("sys", new SystemPropertiesLookup());
/*  65 */     this.lookups.put("env", new EnvironmentLookup());
/*  66 */     this.lookups.put("jndi", new JndiLookup());
/*     */     try {
/*  68 */       if (Class.forName("javax.servlet.ServletContext") != null) {
/*  69 */         this.lookups.put("web", new WebLookup());
/*     */       }
/*  71 */     } catch (ClassNotFoundException ex) {
/*  72 */       LOGGER.debug("ServletContext not present - WebLookup not added");
/*  73 */     } catch (Exception ex) {
/*  74 */       LOGGER.error("Unable to locate ServletContext", ex);
/*     */     } 
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
/*     */   public String lookup(String var) {
/*  92 */     return lookup(null, var);
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
/*     */   public String lookup(LogEvent event, String var) {
/* 110 */     if (var == null) {
/* 111 */       return null;
/*     */     }
/*     */     
/* 114 */     int prefixPos = var.indexOf(':');
/* 115 */     if (prefixPos >= 0) {
/* 116 */       String prefix = var.substring(0, prefixPos);
/* 117 */       String name = var.substring(prefixPos + 1);
/* 118 */       StrLookup lookup = this.lookups.get(prefix);
/* 119 */       String value = null;
/* 120 */       if (lookup != null) {
/* 121 */         value = (event == null) ? lookup.lookup(name) : lookup.lookup(event, name);
/*     */       }
/*     */       
/* 124 */       if (value != null) {
/* 125 */         return value;
/*     */       }
/* 127 */       var = var.substring(prefixPos + 1);
/*     */     } 
/* 129 */     if (this.defaultLookup != null) {
/* 130 */       return (event == null) ? this.defaultLookup.lookup(var) : this.defaultLookup.lookup(event, var);
/*     */     }
/* 132 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 137 */     StringBuilder sb = new StringBuilder();
/* 138 */     for (String name : this.lookups.keySet()) {
/* 139 */       if (sb.length() == 0) {
/* 140 */         sb.append("{");
/*     */       } else {
/* 142 */         sb.append(", ");
/*     */       } 
/*     */       
/* 145 */       sb.append(name);
/*     */     } 
/* 147 */     if (sb.length() > 0) {
/* 148 */       sb.append("}");
/*     */     }
/* 150 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\lookup\Interpolator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
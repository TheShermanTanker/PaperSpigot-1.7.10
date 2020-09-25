/*     */ package org.apache.logging.log4j.spi;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ public class DefaultThreadContextMap
/*     */   implements ThreadContextMap
/*     */ {
/*     */   private final boolean useMap;
/*     */   
/*  33 */   private final ThreadLocal<Map<String, String>> localMap = new InheritableThreadLocal<Map<String, String>>()
/*     */     {
/*     */       protected Map<String, String> childValue(Map<String, String> parentValue)
/*     */       {
/*  37 */         return (parentValue == null || !DefaultThreadContextMap.this.useMap) ? null : Collections.<String, String>unmodifiableMap(new HashMap<String, String>(parentValue));
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   public DefaultThreadContextMap(boolean useMap) {
/*  43 */     this.useMap = useMap;
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
/*     */   public void put(String key, String value) {
/*  58 */     if (!this.useMap) {
/*     */       return;
/*     */     }
/*  61 */     Map<String, String> map = this.localMap.get();
/*  62 */     map = (map == null) ? new HashMap<String, String>() : new HashMap<String, String>(map);
/*  63 */     map.put(key, value);
/*  64 */     this.localMap.set(Collections.unmodifiableMap(map));
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
/*     */   public String get(String key) {
/*  76 */     Map<String, String> map = this.localMap.get();
/*  77 */     return (map == null) ? null : map.get(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove(String key) {
/*  87 */     Map<String, String> map = this.localMap.get();
/*  88 */     if (map != null) {
/*  89 */       Map<String, String> copy = new HashMap<String, String>(map);
/*  90 */       copy.remove(key);
/*  91 */       this.localMap.set(Collections.unmodifiableMap(copy));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 100 */     this.localMap.remove();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsKey(String key) {
/* 110 */     Map<String, String> map = this.localMap.get();
/* 111 */     return (map != null && map.containsKey(key));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, String> getCopy() {
/* 120 */     Map<String, String> map = this.localMap.get();
/* 121 */     return (map == null) ? new HashMap<String, String>() : new HashMap<String, String>(map);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, String> getImmutableMapOrNull() {
/* 130 */     return this.localMap.get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 139 */     Map<String, String> map = this.localMap.get();
/* 140 */     return (map == null || map.size() == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 145 */     Map<String, String> map = this.localMap.get();
/* 146 */     return (map == null) ? "{}" : map.toString();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\spi\DefaultThreadContextMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
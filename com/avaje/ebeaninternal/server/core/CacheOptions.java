/*    */ package com.avaje.ebeaninternal.server.core;
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
/*    */ public class CacheOptions
/*    */ {
/*    */   private boolean useCache;
/*    */   private boolean readOnly;
/*    */   private String naturalKey;
/*    */   private String warmingQuery;
/*    */   
/*    */   public boolean isUseCache() {
/* 26 */     return this.useCache;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setUseCache(boolean useCache) {
/* 33 */     this.useCache = useCache;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isReadOnly() {
/* 40 */     return this.readOnly;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setReadOnly(boolean readOnly) {
/* 47 */     this.readOnly = readOnly;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getWarmingQuery() {
/* 54 */     return this.warmingQuery;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setWarmingQuery(String warmingQuery) {
/* 61 */     this.warmingQuery = warmingQuery;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isUseNaturalKeyCache() {
/* 69 */     return (this.naturalKey != null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getNaturalKey() {
/* 76 */     return this.naturalKey;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setNaturalKey(String naturalKey) {
/* 83 */     if (naturalKey == null || naturalKey.length() == 0) {
/* 84 */       naturalKey = null;
/*    */     } else {
/* 86 */       this.naturalKey = naturalKey.trim();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\core\CacheOptions.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
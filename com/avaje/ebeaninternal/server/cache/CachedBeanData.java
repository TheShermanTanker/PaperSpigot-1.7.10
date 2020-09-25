/*    */ package com.avaje.ebeaninternal.server.cache;
/*    */ 
/*    */ import java.util.Set;
/*    */ 
/*    */ public class CachedBeanData
/*    */ {
/*    */   private final Object sharableBean;
/*    */   private final Set<String> loadedProperties;
/*    */   private final Object[] data;
/*    */   private final int naturalKeyUpdate;
/*    */   
/*    */   public CachedBeanData(Object sharableBean, Set<String> loadedProperties, Object[] data, int naturalKeyUpdate) {
/* 13 */     this.sharableBean = sharableBean;
/* 14 */     this.loadedProperties = loadedProperties;
/* 15 */     this.data = data;
/* 16 */     this.naturalKeyUpdate = naturalKeyUpdate;
/*    */   }
/*    */   
/*    */   public Object getSharableBean() {
/* 20 */     return this.sharableBean;
/*    */   }
/*    */   
/*    */   public boolean isNaturalKeyUpdate() {
/* 24 */     return (this.naturalKeyUpdate > -1);
/*    */   }
/*    */   
/*    */   public Object getNaturalKey() {
/* 28 */     return this.data[this.naturalKeyUpdate];
/*    */   }
/*    */   
/*    */   public boolean containsProperty(String propName) {
/* 32 */     return (this.loadedProperties == null || this.loadedProperties.contains(propName));
/*    */   }
/*    */   
/*    */   public Object getData(int i) {
/* 36 */     return this.data[i];
/*    */   }
/*    */   
/*    */   public Set<String> getLoadedProperties() {
/* 40 */     return this.loadedProperties;
/*    */   }
/*    */   
/*    */   public Object[] copyData() {
/* 44 */     Object[] dest = new Object[this.data.length];
/* 45 */     System.arraycopy(this.data, 0, dest, 0, this.data.length);
/* 46 */     return dest;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\cache\CachedBeanData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
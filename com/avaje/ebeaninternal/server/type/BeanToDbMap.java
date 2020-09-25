/*     */ package com.avaje.ebeaninternal.server.type;
/*     */ 
/*     */ import java.util.HashMap;
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
/*     */ public class BeanToDbMap<B, D>
/*     */ {
/*     */   final HashMap<B, D> keyMap;
/*     */   final HashMap<D, B> valueMap;
/*     */   final boolean allowNulls;
/*     */   
/*     */   public BeanToDbMap() {
/*  48 */     this(false);
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
/*     */   public BeanToDbMap(boolean allowNulls) {
/*  60 */     this.allowNulls = allowNulls;
/*  61 */     this.keyMap = new HashMap<B, D>();
/*  62 */     this.valueMap = new HashMap<D, B>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanToDbMap<B, D> add(B beanValue, D dbValue) {
/*  69 */     this.keyMap.put(beanValue, dbValue);
/*  70 */     this.valueMap.put(dbValue, beanValue);
/*  71 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public D getDbValue(B beanValue) {
/*  78 */     if (beanValue == null) {
/*  79 */       return null;
/*     */     }
/*  81 */     D dbValue = this.keyMap.get(beanValue);
/*  82 */     if (dbValue == null && !this.allowNulls) {
/*  83 */       String msg = "DB value for " + beanValue + " not found in " + this.valueMap;
/*  84 */       throw new IllegalArgumentException(msg);
/*     */     } 
/*  86 */     return dbValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public B getBeanValue(D dbValue) {
/*  93 */     if (dbValue == null) {
/*  94 */       return null;
/*     */     }
/*  96 */     B beanValue = this.valueMap.get(dbValue);
/*  97 */     if (beanValue == null && !this.allowNulls) {
/*  98 */       String msg = "Bean value for " + dbValue + " not found in " + this.valueMap;
/*  99 */       throw new IllegalArgumentException(msg);
/*     */     } 
/* 101 */     return beanValue;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\BeanToDbMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
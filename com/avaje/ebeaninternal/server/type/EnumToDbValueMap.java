/*     */ package com.avaje.ebeaninternal.server.type;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
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
/*     */ public abstract class EnumToDbValueMap<T>
/*     */ {
/*     */   final LinkedHashMap<Object, T> keyMap;
/*     */   final LinkedHashMap<T, Object> valueMap;
/*     */   final boolean allowNulls;
/*     */   final boolean isIntegerType;
/*     */   
/*     */   public static EnumToDbValueMap<?> create(boolean integerType) {
/*  36 */     return integerType ? new EnumToDbIntegerMap() : new EnumToDbStringMap();
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
/*     */   public EnumToDbValueMap() {
/*  51 */     this(false, false);
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
/*     */   public EnumToDbValueMap(boolean allowNulls, boolean isIntegerType) {
/*  63 */     this.allowNulls = allowNulls;
/*  64 */     this.isIntegerType = isIntegerType;
/*  65 */     this.keyMap = new LinkedHashMap<Object, T>();
/*  66 */     this.valueMap = new LinkedHashMap<T, Object>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isIntegerType() {
/*  74 */     return this.isIntegerType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<T> dbValues() {
/*  81 */     return this.valueMap.keySet().iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<Object> beanValues() {
/*  88 */     return this.valueMap.values().iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void bind(DataBind paramDataBind, Object paramObject) throws SQLException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Object read(DataReader paramDataReader) throws SQLException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int getDbType();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract EnumToDbValueMap<T> add(Object paramObject, String paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addInternal(Object beanValue, T dbValue) {
/* 120 */     this.keyMap.put(beanValue, dbValue);
/* 121 */     this.valueMap.put(dbValue, beanValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T getDbValue(Object beanValue) {
/* 128 */     if (beanValue == null) {
/* 129 */       return null;
/*     */     }
/* 131 */     T dbValue = this.keyMap.get(beanValue);
/* 132 */     if (dbValue == null && !this.allowNulls) {
/* 133 */       String msg = "DB value for " + beanValue + " not found in " + this.valueMap;
/* 134 */       throw new IllegalArgumentException(msg);
/*     */     } 
/* 136 */     return dbValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getBeanValue(T dbValue) {
/* 143 */     if (dbValue == null) {
/* 144 */       return null;
/*     */     }
/* 146 */     Object beanValue = this.valueMap.get(dbValue);
/* 147 */     if (beanValue == null && !this.allowNulls) {
/* 148 */       String msg = "Bean value for " + dbValue + " not found in " + this.valueMap;
/* 149 */       throw new IllegalArgumentException(msg);
/*     */     } 
/* 151 */     return beanValue;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\EnumToDbValueMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.avaje.ebeaninternal.server.query;
/*     */ 
/*     */ import com.avaje.ebean.bean.BeanCollection;
/*     */ import com.avaje.ebeaninternal.api.SpiQuery;
/*     */ import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
/*     */ import com.avaje.ebeaninternal.server.core.RelationalQueryRequest;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
/*     */ import com.avaje.ebeaninternal.server.util.BeanCollectionFactory;
/*     */ import com.avaje.ebeaninternal.server.util.BeanCollectionParams;
/*     */ import java.util.Collection;
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
/*     */ public final class BeanCollectionWrapper
/*     */ {
/*     */   private final boolean isMap;
/*     */   private final SpiQuery.Type queryType;
/*     */   private final String mapKey;
/*     */   private final BeanCollection<?> beanCollection;
/*     */   private final Collection<Object> collection;
/*     */   private final Map<Object, Object> map;
/*     */   private final BeanDescriptor<?> desc;
/*     */   private int rowCount;
/*     */   
/*     */   public BeanCollectionWrapper(RelationalQueryRequest request) {
/*  84 */     this.desc = null;
/*  85 */     this.queryType = request.getQueryType();
/*  86 */     this.mapKey = request.getQuery().getMapKey();
/*  87 */     this.isMap = SpiQuery.Type.MAP.equals(this.queryType);
/*     */     
/*  89 */     this.beanCollection = createBeanCollection(this.queryType);
/*  90 */     this.collection = getCollection(this.isMap);
/*  91 */     this.map = getMap(this.isMap);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanCollectionWrapper(OrmQueryRequest<?> request) {
/*  99 */     this.desc = request.getBeanDescriptor();
/* 100 */     this.queryType = request.getQueryType();
/* 101 */     this.mapKey = request.getQuery().getMapKey();
/* 102 */     this.isMap = SpiQuery.Type.MAP.equals(this.queryType);
/*     */     
/* 104 */     this.beanCollection = createBeanCollection(this.queryType);
/* 105 */     this.collection = getCollection(this.isMap);
/* 106 */     this.map = getMap(this.isMap);
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
/*     */   public BeanCollectionWrapper(BeanPropertyAssocMany<?> manyProp) {
/* 119 */     this.queryType = manyProp.getManyType().getQueryType();
/* 120 */     this.mapKey = manyProp.getMapKey();
/* 121 */     this.desc = manyProp.getTargetDescriptor();
/* 122 */     this.isMap = SpiQuery.Type.MAP.equals(this.queryType);
/*     */     
/* 124 */     this.beanCollection = createBeanCollection(this.queryType);
/* 125 */     this.collection = getCollection(this.isMap);
/* 126 */     this.map = getMap(this.isMap);
/*     */   }
/*     */ 
/*     */   
/*     */   private Map<Object, Object> getMap(boolean isMap) {
/* 131 */     return isMap ? (Map)this.beanCollection : null;
/*     */   }
/*     */ 
/*     */   
/*     */   private Collection<Object> getCollection(boolean isMap) {
/* 136 */     return isMap ? null : (Collection)this.beanCollection;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanCollection<?> getBeanCollection() {
/* 143 */     return this.beanCollection;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BeanCollection<?> createBeanCollection(SpiQuery.Type manyType) {
/* 150 */     BeanCollectionParams p = new BeanCollectionParams(manyType);
/* 151 */     return BeanCollectionFactory.create(p);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMap() {
/* 158 */     return this.isMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 165 */     return this.rowCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(Object bean) {
/* 172 */     add(bean, this.beanCollection);
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
/*     */   public void add(Object bean, Object collection) {
/* 185 */     if (bean == null) {
/*     */       return;
/*     */     }
/* 188 */     this.rowCount++;
/* 189 */     if (this.isMap) {
/* 190 */       Object keyValue = null;
/* 191 */       if (this.mapKey != null) {
/*     */         
/* 193 */         keyValue = this.desc.getValue(bean, this.mapKey);
/*     */       } else {
/*     */         
/* 196 */         keyValue = this.desc.getId(bean);
/*     */       } 
/*     */       
/* 199 */       Map<Object, Object> mapColl = (Map)collection;
/* 200 */       mapColl.put(keyValue, bean);
/*     */     } else {
/* 202 */       ((Collection<Object>)collection).add(bean);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addToCollection(Object bean) {
/* 210 */     this.collection.add(bean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addToMap(Object bean, Object key) {
/* 217 */     this.map.put(key, bean);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\query\BeanCollectionWrapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.avaje.ebeaninternal.server.deploy;
/*     */ 
/*     */ import com.avaje.ebean.EbeanServer;
/*     */ import com.avaje.ebean.InvalidValue;
/*     */ import com.avaje.ebean.Query;
/*     */ import com.avaje.ebean.Transaction;
/*     */ import com.avaje.ebean.bean.BeanCollection;
/*     */ import com.avaje.ebean.bean.BeanCollectionAdd;
/*     */ import com.avaje.ebean.bean.BeanCollectionLoader;
/*     */ import com.avaje.ebean.common.BeanMap;
/*     */ import com.avaje.ebeaninternal.server.text.json.WriteJsonContext;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
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
/*     */ public final class BeanMapHelp<T>
/*     */   implements BeanCollectionHelp<T>
/*     */ {
/*     */   private final BeanPropertyAssocMany<T> many;
/*     */   private final BeanDescriptor<T> targetDescriptor;
/*     */   private final BeanProperty beanProperty;
/*     */   private BeanCollectionLoader loader;
/*     */   
/*     */   public BeanMapHelp(BeanDescriptor<T> targetDescriptor, String mapKey) {
/*  34 */     this(null, targetDescriptor, mapKey);
/*     */   }
/*     */   
/*     */   public BeanMapHelp(BeanPropertyAssocMany<T> many) {
/*  38 */     this(many, many.getTargetDescriptor(), many.getMapKey());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BeanMapHelp(BeanPropertyAssocMany<T> many, BeanDescriptor<T> targetDescriptor, String mapKey) {
/*  45 */     this.many = many;
/*  46 */     this.targetDescriptor = targetDescriptor;
/*     */     
/*  48 */     this.beanProperty = targetDescriptor.getBeanProperty(mapKey);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<?> getIterator(Object collection) {
/*  55 */     return ((Map)collection).values().iterator();
/*     */   }
/*     */   
/*     */   public void setLoader(BeanCollectionLoader loader) {
/*  59 */     this.loader = loader;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanCollectionAdd getBeanCollectionAdd(Object bc, String mapKey) {
/*  65 */     if (mapKey == null) {
/*  66 */       mapKey = this.many.getMapKey();
/*     */     }
/*  68 */     BeanProperty beanProp = this.targetDescriptor.getBeanProperty(mapKey);
/*     */     
/*  70 */     if (bc instanceof BeanMap) {
/*  71 */       BeanMap<Object, Object> bm = (BeanMap<Object, Object>)bc;
/*  72 */       Map<Object, Object> actualMap = bm.getActualMap();
/*  73 */       if (actualMap == null) {
/*  74 */         actualMap = new LinkedHashMap<Object, Object>();
/*  75 */         bm.setActualMap(actualMap);
/*     */       } 
/*  77 */       return new Adder(beanProp, actualMap);
/*     */     } 
/*  79 */     if (bc instanceof Map) {
/*  80 */       return new Adder(beanProp, (Map<Object, Object>)bc);
/*     */     }
/*     */     
/*  83 */     throw new RuntimeException("Unhandled type " + bc);
/*     */   }
/*     */ 
/*     */   
/*     */   static class Adder
/*     */     implements BeanCollectionAdd
/*     */   {
/*     */     private final BeanProperty beanProperty;
/*     */     private final Map<Object, Object> map;
/*     */     
/*     */     Adder(BeanProperty beanProperty, Map<Object, Object> map) {
/*  94 */       this.beanProperty = beanProperty;
/*  95 */       this.map = map;
/*     */     }
/*     */     
/*     */     public void addBean(Object bean) {
/*  99 */       Object keyValue = this.beanProperty.getValue(bean);
/* 100 */       this.map.put(keyValue, bean);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Object createEmpty(boolean vanilla) {
/* 106 */     return vanilla ? new LinkedHashMap<Object, Object>() : new BeanMap();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(BeanCollection<?> collection, Object bean) {
/* 112 */     Object keyValue = this.beanProperty.getValueIntercept(bean);
/*     */     
/* 114 */     Map<Object, Object> map = (Map)collection;
/* 115 */     map.put(keyValue, bean);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanCollection<T> createReference(Object parentBean, String propertyName) {
/* 121 */     return (BeanCollection<T>)new BeanMap(this.loader, parentBean, propertyName);
/*     */   }
/*     */ 
/*     */   
/*     */   public ArrayList<InvalidValue> validate(Object manyValue) {
/* 126 */     ArrayList<InvalidValue> errs = null;
/*     */     
/* 128 */     Map<?, ?> m = (Map<?, ?>)manyValue;
/* 129 */     Iterator<?> it = m.values().iterator();
/* 130 */     while (it.hasNext()) {
/* 131 */       Object detailBean = it.next();
/* 132 */       InvalidValue invalid = this.targetDescriptor.validate(true, detailBean);
/* 133 */       if (invalid != null) {
/* 134 */         if (errs == null) {
/* 135 */           errs = new ArrayList<InvalidValue>();
/*     */         }
/* 137 */         errs.add(invalid);
/*     */       } 
/*     */     } 
/*     */     
/* 141 */     return errs;
/*     */   }
/*     */   
/*     */   public void refresh(EbeanServer server, Query<?> query, Transaction t, Object parentBean) {
/* 145 */     BeanMap<?, ?> newBeanMap = (BeanMap<?, ?>)server.findMap(query, t);
/* 146 */     refresh((BeanCollection<?>)newBeanMap, parentBean);
/*     */   }
/*     */ 
/*     */   
/*     */   public void refresh(BeanCollection<?> bc, Object parentBean) {
/* 151 */     BeanMap<?, ?> newBeanMap = (BeanMap)bc;
/* 152 */     Map<?, ?> current = (Map<?, ?>)this.many.getValueUnderlying(parentBean);
/*     */     
/* 154 */     newBeanMap.setModifyListening(this.many.getModifyListenMode());
/* 155 */     if (current == null) {
/*     */       
/* 157 */       this.many.setValue(parentBean, newBeanMap);
/*     */     }
/* 159 */     else if (current instanceof BeanMap) {
/*     */       
/* 161 */       BeanMap<?, ?> currentBeanMap = (BeanMap<?, ?>)current;
/* 162 */       currentBeanMap.setActualMap(newBeanMap.getActualMap());
/* 163 */       currentBeanMap.setModifyListening(this.many.getModifyListenMode());
/*     */     }
/*     */     else {
/*     */       
/* 167 */       this.many.setValue(parentBean, newBeanMap);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void jsonWrite(WriteJsonContext ctx, String name, Object collection, boolean explicitInclude) {
/*     */     Map<?, ?> map;
/* 174 */     if (collection instanceof BeanCollection) {
/* 175 */       BeanMap<?, ?> bc = (BeanMap<?, ?>)collection;
/* 176 */       if (!bc.isPopulated()) {
/* 177 */         if (explicitInclude) {
/*     */ 
/*     */           
/* 180 */           bc.size();
/*     */         } else {
/*     */           return;
/*     */         } 
/*     */       }
/* 185 */       map = bc.getActualMap();
/*     */     } else {
/* 187 */       map = (Map<?, ?>)collection;
/*     */     } 
/*     */     
/* 190 */     int count = 0;
/* 191 */     ctx.beginAssocMany(name);
/* 192 */     Iterator<?> it = map.entrySet().iterator();
/* 193 */     while (it.hasNext()) {
/* 194 */       Map.Entry<?, ?> entry = (Map.Entry<?, ?>)it.next();
/* 195 */       if (count++ > 0) {
/* 196 */         ctx.appendComma();
/*     */       }
/*     */       
/* 199 */       Object detailBean = entry.getValue();
/* 200 */       this.targetDescriptor.jsonWrite(ctx, detailBean);
/*     */     } 
/* 202 */     ctx.endAssocMany();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\BeanMapHelp.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
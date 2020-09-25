/*     */ package com.avaje.ebeaninternal.server.cache;
/*     */ 
/*     */ import com.avaje.ebean.bean.EntityBean;
/*     */ import com.avaje.ebean.bean.EntityBeanIntercept;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CachedBeanDataFromBean
/*     */ {
/*     */   private final BeanDescriptor<?> desc;
/*     */   private final Object bean;
/*     */   private final EntityBeanIntercept ebi;
/*     */   private final Set<String> loadedProps;
/*     */   private final Set<String> extractProps;
/*     */   
/*     */   public static CachedBeanData extract(BeanDescriptor<?> desc, Object bean) {
/*  21 */     if (bean instanceof EntityBean) {
/*  22 */       return (new CachedBeanDataFromBean(desc, bean, ((EntityBean)bean)._ebean_getIntercept())).extract();
/*     */     }
/*     */     
/*  25 */     return (new CachedBeanDataFromBean(desc, bean, null)).extract();
/*     */   }
/*     */ 
/*     */   
/*     */   public static CachedBeanData extract(BeanDescriptor<?> desc, Object bean, EntityBeanIntercept ebi) {
/*  30 */     return (new CachedBeanDataFromBean(desc, bean, ebi)).extract();
/*     */   }
/*     */   
/*     */   private CachedBeanDataFromBean(BeanDescriptor<?> desc, Object bean, EntityBeanIntercept ebi) {
/*  34 */     this.desc = desc;
/*  35 */     this.bean = bean;
/*  36 */     this.ebi = ebi;
/*  37 */     if (ebi != null) {
/*  38 */       this.loadedProps = ebi.getLoadedProps();
/*  39 */       this.extractProps = (this.loadedProps == null) ? null : new HashSet<String>();
/*     */     } else {
/*  41 */       this.extractProps = new HashSet<String>();
/*  42 */       this.loadedProps = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private CachedBeanData extract() {
/*  48 */     BeanProperty[] props = this.desc.propertiesNonMany();
/*     */     
/*  50 */     Object[] data = new Object[props.length];
/*     */     
/*  52 */     int naturalKeyUpdate = -1;
/*  53 */     for (int i = 0; i < props.length; i++) {
/*  54 */       BeanProperty prop = props[i];
/*  55 */       if (includeNonManyProperty(prop.getName())) {
/*     */         
/*  57 */         data[i] = prop.getCacheDataValue(this.bean);
/*  58 */         if (prop.isNaturalKey()) {
/*  59 */           naturalKeyUpdate = i;
/*     */         }
/*  61 */         if (this.ebi != null) {
/*  62 */           if (this.extractProps != null) {
/*  63 */             this.extractProps.add(prop.getName());
/*     */           }
/*  65 */         } else if (data[i] != null && 
/*  66 */           this.extractProps != null) {
/*  67 */           this.extractProps.add(prop.getName());
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  73 */     Object sharableBean = null;
/*  74 */     if (this.desc.isCacheSharableBeans() && this.ebi != null && this.loadedProps == null) {
/*  75 */       if (this.ebi.isReadOnly()) {
/*  76 */         sharableBean = this.bean;
/*     */       } else {
/*     */         
/*  79 */         sharableBean = this.desc.createBean(false);
/*  80 */         BeanProperty[] propertiesId = this.desc.propertiesId();
/*  81 */         for (int j = 0; j < propertiesId.length; j++) {
/*  82 */           Object v = propertiesId[j].getValue(this.bean);
/*  83 */           propertiesId[j].setValue(sharableBean, v);
/*     */         } 
/*  85 */         BeanProperty[] propertiesNonTransient = this.desc.propertiesNonTransient();
/*  86 */         for (int k = 0; k < propertiesNonTransient.length; k++) {
/*  87 */           Object v = propertiesNonTransient[k].getValue(this.bean);
/*  88 */           propertiesNonTransient[k].setValue(sharableBean, v);
/*     */         } 
/*  90 */         EntityBeanIntercept ebi = ((EntityBean)sharableBean)._ebean_intercept();
/*  91 */         ebi.setReadOnly(true);
/*  92 */         ebi.setLoaded();
/*     */       } 
/*     */     }
/*     */     
/*  96 */     return new CachedBeanData(sharableBean, this.extractProps, data, naturalKeyUpdate);
/*     */   }
/*     */   
/*     */   private boolean includeNonManyProperty(String name) {
/* 100 */     return (this.loadedProps == null || this.loadedProps.contains(name));
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\cache\CachedBeanDataFromBean.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
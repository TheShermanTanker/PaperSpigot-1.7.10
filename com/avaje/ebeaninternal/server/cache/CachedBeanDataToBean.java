/*     */ package com.avaje.ebeaninternal.server.cache;
/*     */ 
/*     */ import com.avaje.ebean.bean.EntityBean;
/*     */ import com.avaje.ebean.bean.EntityBeanIntercept;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CachedBeanDataToBean
/*     */ {
/*     */   private final BeanDescriptor<?> desc;
/*     */   private final Object bean;
/*     */   private final EntityBeanIntercept ebi;
/*     */   private final CachedBeanData cacheBeandata;
/*     */   private final Set<String> cacheLoadedProperties;
/*     */   private final Set<String> loadedProps;
/*     */   private final Set<String> excludeProps;
/*     */   private final Object oldValuesBean;
/*     */   private final boolean readOnly;
/*     */   
/*     */   public static void load(BeanDescriptor<?> desc, Object bean, CachedBeanData cacheBeandata) {
/*  26 */     if (bean instanceof EntityBean) {
/*  27 */       load(desc, bean, ((EntityBean)bean)._ebean_getIntercept(), cacheBeandata);
/*     */     } else {
/*  29 */       load(desc, bean, null, cacheBeandata);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void load(BeanDescriptor<?> desc, Object bean, EntityBeanIntercept ebi, CachedBeanData cacheBeandata) {
/*  34 */     (new CachedBeanDataToBean(desc, bean, ebi, cacheBeandata)).load();
/*     */   }
/*     */   
/*     */   private CachedBeanDataToBean(BeanDescriptor<?> desc, Object bean, EntityBeanIntercept ebi, CachedBeanData cacheBeandata) {
/*  38 */     this.desc = desc;
/*  39 */     this.bean = bean;
/*  40 */     this.ebi = ebi;
/*  41 */     this.cacheBeandata = cacheBeandata;
/*  42 */     this.cacheLoadedProperties = cacheBeandata.getLoadedProperties();
/*  43 */     this.loadedProps = (this.cacheLoadedProperties == null) ? null : new HashSet<String>();
/*     */     
/*  45 */     if (ebi != null) {
/*  46 */       this.excludeProps = ebi.getLoadedProps();
/*  47 */       this.oldValuesBean = ebi.getOldValues();
/*  48 */       this.readOnly = ebi.isReadOnly();
/*     */     } else {
/*  50 */       this.excludeProps = null;
/*  51 */       this.oldValuesBean = null;
/*  52 */       this.readOnly = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean load() {
/*  58 */     BeanProperty[] propertiesNonTransient = this.desc.propertiesNonMany();
/*  59 */     for (int i = 0; i < propertiesNonTransient.length; i++) {
/*  60 */       BeanProperty prop = propertiesNonTransient[i];
/*  61 */       if (includeNonManyProperty(prop.getName())) {
/*  62 */         Object data = this.cacheBeandata.getData(i);
/*  63 */         prop.setCacheDataValue(this.bean, data, this.oldValuesBean, this.readOnly);
/*     */       } 
/*     */     } 
/*  66 */     BeanPropertyAssocMany[] arrayOfBeanPropertyAssocMany = this.desc.propertiesMany();
/*  67 */     for (int j = 0; j < arrayOfBeanPropertyAssocMany.length; j++) {
/*  68 */       BeanPropertyAssocMany<?> prop = arrayOfBeanPropertyAssocMany[j];
/*  69 */       if (includeManyProperty(prop.getName()))
/*     */       {
/*  71 */         prop.createReference(this.bean);
/*     */       }
/*     */     } 
/*     */     
/*  75 */     if (this.ebi != null) {
/*  76 */       if (this.loadedProps == null) {
/*  77 */         this.ebi.setLoadedProps(null);
/*     */       } else {
/*  79 */         HashSet<String> mergeProps = new HashSet<String>();
/*  80 */         if (this.excludeProps != null) {
/*  81 */           mergeProps.addAll(this.excludeProps);
/*     */         }
/*  83 */         mergeProps.addAll(this.loadedProps);
/*  84 */         this.ebi.setLoadedProps(mergeProps);
/*     */       } 
/*  86 */       this.ebi.setLoadedLazy();
/*     */     } 
/*     */     
/*  89 */     return true;
/*     */   }
/*     */   
/*     */   private boolean includeManyProperty(String name) {
/*  93 */     if (this.excludeProps != null && this.excludeProps.contains(name))
/*     */     {
/*  95 */       return false;
/*     */     }
/*  97 */     if (this.loadedProps != null) {
/*  98 */       this.loadedProps.add(name);
/*     */     }
/* 100 */     return true;
/*     */   }
/*     */   
/*     */   private boolean includeNonManyProperty(String name) {
/* 104 */     if (this.excludeProps != null && this.excludeProps.contains(name))
/*     */     {
/* 106 */       return false;
/*     */     }
/* 108 */     if (this.cacheLoadedProperties != null && !this.cacheLoadedProperties.contains(name)) {
/* 109 */       return false;
/*     */     }
/* 111 */     if (this.loadedProps != null) {
/* 112 */       this.loadedProps.add(name);
/*     */     }
/* 114 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\cache\CachedBeanDataToBean.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
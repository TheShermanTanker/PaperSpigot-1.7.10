/*     */ package com.avaje.ebeaninternal.server.deploy;
/*     */ 
/*     */ import com.avaje.ebean.config.ScalarTypeConverter;
/*     */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanProperty;
/*     */ import com.avaje.ebeaninternal.server.type.CtCompoundProperty;
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
/*     */ public class BeanPropertyCompoundScalar
/*     */   extends BeanProperty
/*     */ {
/*     */   private final BeanPropertyCompoundRoot rootProperty;
/*     */   private final CtCompoundProperty ctProperty;
/*     */   private final ScalarTypeConverter typeConverter;
/*     */   
/*     */   public BeanPropertyCompoundScalar(BeanPropertyCompoundRoot rootProperty, DeployBeanProperty scalarDeploy, CtCompoundProperty ctProperty, ScalarTypeConverter<?, ?> typeConverter) {
/*  44 */     super(scalarDeploy);
/*  45 */     this.rootProperty = rootProperty;
/*  46 */     this.ctProperty = ctProperty;
/*  47 */     this.typeConverter = typeConverter;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getValue(Object valueObject) {
/*  53 */     if (this.typeConverter != null) {
/*  54 */       valueObject = this.typeConverter.unwrapValue(valueObject);
/*     */     }
/*  56 */     return this.ctProperty.getValue(valueObject);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setValue(Object bean, Object value) {
/*  61 */     setValueInCompound(bean, value, false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValueInCompound(Object bean, Object value, boolean intercept) {
/*  67 */     Object compoundValue = this.ctProperty.setValue(bean, value);
/*     */     
/*  69 */     if (compoundValue != null) {
/*  70 */       if (this.typeConverter != null) {
/*  71 */         compoundValue = this.typeConverter.wrapValue(compoundValue);
/*     */       }
/*     */ 
/*     */       
/*  75 */       if (intercept) {
/*  76 */         this.rootProperty.setRootValueIntercept(bean, compoundValue);
/*     */       } else {
/*  78 */         this.rootProperty.setRootValue(bean, compoundValue);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValueIntercept(Object bean, Object value) {
/*  88 */     setValueInCompound(bean, value, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getValueIntercept(Object bean) {
/*  96 */     return getValue(bean);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object elGetReference(Object bean) {
/* 101 */     return getValue(bean);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object elGetValue(Object bean) {
/* 106 */     return getValue(bean);
/*     */   }
/*     */ 
/*     */   
/*     */   public void elSetReference(Object bean) {
/* 111 */     super.elSetReference(bean);
/*     */   }
/*     */ 
/*     */   
/*     */   public void elSetValue(Object bean, Object value, boolean populate, boolean reference) {
/* 116 */     super.elSetValue(bean, value, populate, reference);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\BeanPropertyCompoundScalar.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
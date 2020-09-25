/*     */ package com.avaje.ebeaninternal.server.deploy.meta;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptorMap;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
/*     */ import java.util.List;
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
/*     */ public class DeployBeanTable
/*     */ {
/*     */   private final Class<?> beanType;
/*     */   private String baseTable;
/*     */   private List<DeployBeanProperty> idProperties;
/*     */   
/*     */   public DeployBeanTable(Class<?> beanType) {
/*  53 */     this.beanType = beanType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBaseTable() {
/*  62 */     return this.baseTable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBaseTable(String baseTable) {
/*  69 */     this.baseTable = baseTable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanProperty[] createIdProperties(BeanDescriptorMap owner) {
/*  76 */     BeanProperty[] props = new BeanProperty[this.idProperties.size()];
/*  77 */     for (int i = 0; i < this.idProperties.size(); i++) {
/*  78 */       props[i] = createProperty(owner, this.idProperties.get(i));
/*     */     }
/*  80 */     return props;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private BeanProperty createProperty(BeanDescriptorMap owner, DeployBeanProperty prop) {
/*  86 */     if (prop instanceof DeployBeanPropertyAssocOne) {
/*  87 */       return (BeanProperty)new BeanPropertyAssocOne(owner, (DeployBeanPropertyAssocOne)prop);
/*     */     }
/*     */     
/*  90 */     return new BeanProperty(prop);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIdProperties(List<DeployBeanProperty> idProperties) {
/*  99 */     this.idProperties = idProperties;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<?> getBeanType() {
/* 106 */     return this.beanType;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\meta\DeployBeanTable.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
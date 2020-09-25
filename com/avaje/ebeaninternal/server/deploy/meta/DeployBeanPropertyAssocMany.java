/*     */ package com.avaje.ebeaninternal.server.deploy.meta;
/*     */ 
/*     */ import com.avaje.ebean.bean.BeanCollection;
/*     */ import com.avaje.ebeaninternal.server.deploy.ManyType;
/*     */ import com.avaje.ebeaninternal.server.deploy.TableJoin;
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
/*     */ public class DeployBeanPropertyAssocMany<T>
/*     */   extends DeployBeanPropertyAssoc<T>
/*     */ {
/*  31 */   BeanCollection.ModifyListenMode modifyListenMode = BeanCollection.ModifyListenMode.NONE;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean manyToMany;
/*     */ 
/*     */ 
/*     */   
/*     */   boolean unidirectional;
/*     */ 
/*     */ 
/*     */   
/*     */   DeployTableJoin intersectionJoin;
/*     */ 
/*     */ 
/*     */   
/*     */   DeployTableJoin inverseJoin;
/*     */ 
/*     */ 
/*     */   
/*     */   String fetchOrderBy;
/*     */ 
/*     */ 
/*     */   
/*     */   String mapKey;
/*     */ 
/*     */ 
/*     */   
/*     */   ManyType manyType;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DeployBeanPropertyAssocMany(DeployBeanDescriptor<?> desc, Class<T> targetType, ManyType manyType) {
/*  66 */     super(desc, targetType);
/*  67 */     this.manyType = manyType;
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
/*     */   public void setTargetType(Class<?> cls) {
/*  79 */     this.targetType = (Class)cls;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ManyType getManyType() {
/*  87 */     return this.manyType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isManyToMany() {
/*  94 */     return this.manyToMany;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setManyToMany(boolean isManyToMany) {
/* 101 */     this.manyToMany = isManyToMany;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanCollection.ModifyListenMode getModifyListenMode() {
/* 108 */     return this.modifyListenMode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setModifyListenMode(BeanCollection.ModifyListenMode modifyListenMode) {
/* 115 */     this.modifyListenMode = modifyListenMode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUnidirectional() {
/* 122 */     return this.unidirectional;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUnidirectional(boolean unidirectional) {
/* 129 */     this.unidirectional = unidirectional;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TableJoin createIntersectionTableJoin() {
/* 136 */     if (this.intersectionJoin != null) {
/* 137 */       return new TableJoin(this.intersectionJoin, null);
/*     */     }
/* 139 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TableJoin createInverseTableJoin() {
/* 147 */     if (this.inverseJoin != null) {
/* 148 */       return new TableJoin(this.inverseJoin, null);
/*     */     }
/* 150 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DeployTableJoin getIntersectionJoin() {
/* 158 */     return this.intersectionJoin;
/*     */   }
/*     */   
/*     */   public DeployTableJoin getInverseJoin() {
/* 162 */     return this.inverseJoin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIntersectionJoin(DeployTableJoin intersectionJoin) {
/* 169 */     this.intersectionJoin = intersectionJoin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInverseJoin(DeployTableJoin inverseJoin) {
/* 176 */     this.inverseJoin = inverseJoin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFetchOrderBy() {
/* 184 */     return this.fetchOrderBy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMapKey() {
/* 191 */     return this.mapKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMapKey(String mapKey) {
/* 198 */     if (mapKey != null && mapKey.length() > 0) {
/* 199 */       this.mapKey = mapKey;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFetchOrderBy(String orderBy) {
/* 208 */     if (orderBy != null && orderBy.length() > 0)
/* 209 */       this.fetchOrderBy = orderBy; 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\meta\DeployBeanPropertyAssocMany.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
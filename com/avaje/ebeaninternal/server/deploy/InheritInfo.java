/*     */ package com.avaje.ebeaninternal.server.deploy;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.core.InternString;
/*     */ import com.avaje.ebeaninternal.server.deploy.id.IdBinder;
/*     */ import com.avaje.ebeaninternal.server.deploy.parse.DeployInheritInfo;
/*     */ import com.avaje.ebeaninternal.server.query.SqlTreeProperties;
/*     */ import com.avaje.ebeaninternal.server.subclass.SubClassUtil;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import javax.persistence.PersistenceException;
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
/*     */ public class InheritInfo
/*     */ {
/*     */   private final String discriminatorStringValue;
/*     */   private final Object discriminatorValue;
/*     */   private final String discriminatorColumn;
/*     */   private final int discriminatorType;
/*     */   private final int discriminatorLength;
/*     */   private final String where;
/*     */   private final Class<?> type;
/*  53 */   private final ArrayList<InheritInfo> children = new ArrayList<InheritInfo>();
/*     */ 
/*     */   
/*     */   private final HashMap<String, InheritInfo> discMap;
/*     */ 
/*     */   
/*     */   private final HashMap<String, InheritInfo> typeMap;
/*     */ 
/*     */   
/*     */   private final InheritInfo parent;
/*     */ 
/*     */   
/*     */   private final InheritInfo root;
/*     */ 
/*     */   
/*     */   private BeanDescriptor<?> descriptor;
/*     */ 
/*     */ 
/*     */   
/*     */   public InheritInfo(InheritInfo r, InheritInfo parent, DeployInheritInfo deploy) {
/*  73 */     this.parent = parent;
/*  74 */     this.type = deploy.getType();
/*  75 */     this.discriminatorColumn = InternString.intern(deploy.getDiscriminatorColumn(parent));
/*  76 */     this.discriminatorValue = deploy.getDiscriminatorObjectValue();
/*  77 */     this.discriminatorStringValue = deploy.getDiscriminatorStringValue();
/*     */     
/*  79 */     this.discriminatorType = deploy.getDiscriminatorType(parent);
/*  80 */     this.discriminatorLength = deploy.getDiscriminatorLength(parent);
/*  81 */     this.where = InternString.intern(deploy.getWhere());
/*     */     
/*  83 */     if (r == null) {
/*     */       
/*  85 */       this.root = this;
/*  86 */       this.discMap = new HashMap<String, InheritInfo>();
/*  87 */       this.typeMap = new HashMap<String, InheritInfo>();
/*  88 */       registerWithRoot(this);
/*     */     } else {
/*     */       
/*  91 */       this.root = r;
/*     */       
/*  93 */       this.discMap = null;
/*  94 */       this.typeMap = null;
/*  95 */       this.root.registerWithRoot(this);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitChildren(InheritInfoVisitor visitor) {
/* 104 */     for (int i = 0; i < this.children.size(); i++) {
/* 105 */       InheritInfo child = this.children.get(i);
/* 106 */       visitor.visit(child);
/* 107 */       child.visitChildren(visitor);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSaveRecurseSkippable() {
/* 116 */     return this.root.isNodeSaveRecurseSkippable();
/*     */   }
/*     */   
/*     */   private boolean isNodeSaveRecurseSkippable() {
/* 120 */     if (!this.descriptor.isSaveRecurseSkippable()) {
/* 121 */       return false;
/*     */     }
/* 123 */     for (int i = 0; i < this.children.size(); i++) {
/* 124 */       InheritInfo child = this.children.get(i);
/* 125 */       if (!child.isNodeSaveRecurseSkippable()) {
/* 126 */         return false;
/*     */       }
/*     */     } 
/* 129 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDeleteRecurseSkippable() {
/* 137 */     return this.root.isNodeDeleteRecurseSkippable();
/*     */   }
/*     */   
/*     */   private boolean isNodeDeleteRecurseSkippable() {
/* 141 */     if (!this.descriptor.isDeleteRecurseSkippable()) {
/* 142 */       return false;
/*     */     }
/* 144 */     for (int i = 0; i < this.children.size(); i++) {
/* 145 */       InheritInfo child = this.children.get(i);
/* 146 */       if (!child.isNodeDeleteRecurseSkippable()) {
/* 147 */         return false;
/*     */       }
/*     */     } 
/* 150 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDescriptor(BeanDescriptor<?> descriptor) {
/* 158 */     this.descriptor = descriptor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanDescriptor<?> getBeanDescriptor() {
/* 165 */     return this.descriptor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanProperty findSubTypeProperty(String propertyName) {
/* 173 */     BeanProperty prop = null;
/*     */     
/* 175 */     for (int i = 0, x = this.children.size(); i < x; i++) {
/* 176 */       InheritInfo childInfo = this.children.get(i);
/*     */ 
/*     */       
/* 179 */       prop = childInfo.getBeanDescriptor().findBeanProperty(propertyName);
/*     */       
/* 181 */       if (prop != null) {
/* 182 */         return prop;
/*     */       }
/*     */     } 
/*     */     
/* 186 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addChildrenProperties(SqlTreeProperties selectProps) {
/* 194 */     for (int i = 0, x = this.children.size(); i < x; i++) {
/* 195 */       InheritInfo childInfo = this.children.get(i);
/* 196 */       selectProps.add(childInfo.descriptor.propertiesLocal());
/*     */       
/* 198 */       childInfo.addChildrenProperties(selectProps);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InheritInfo readType(DbReadContext ctx) throws SQLException {
/* 207 */     String discValue = ctx.getDataReader().getString();
/* 208 */     return readType(discValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InheritInfo readType(String discValue) {
/* 216 */     if (discValue == null) {
/* 217 */       return null;
/*     */     }
/*     */     
/* 220 */     InheritInfo typeInfo = this.root.getType(discValue);
/* 221 */     if (typeInfo == null) {
/* 222 */       String m = "Inheritance type for discriminator value [" + discValue + "] was not found?";
/* 223 */       throw new PersistenceException(m);
/*     */     } 
/*     */     
/* 226 */     return typeInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InheritInfo readType(Class<?> beanType) {
/* 234 */     InheritInfo typeInfo = this.root.getTypeByClass(beanType);
/* 235 */     if (typeInfo == null) {
/* 236 */       String m = "Inheritance type for bean type [" + beanType.getName() + "] was not found?";
/* 237 */       throw new PersistenceException(m);
/*     */     } 
/*     */     
/* 240 */     return typeInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object createBean(boolean vanillaMode) {
/* 247 */     return this.descriptor.createBean(vanillaMode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IdBinder getIdBinder() {
/* 254 */     return this.descriptor.getIdBinder();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<?> getType() {
/* 261 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InheritInfo getRoot() {
/* 271 */     return this.root;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InheritInfo getParent() {
/* 278 */     return this.parent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAbstract() {
/* 285 */     return (this.discriminatorValue == null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRoot() {
/* 292 */     return (this.parent == null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InheritInfo getType(String discValue) {
/* 299 */     return this.discMap.get(discValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private InheritInfo getTypeByClass(Class<?> beanType) {
/* 306 */     String clsName = SubClassUtil.getSuperClassName(beanType.getName());
/* 307 */     return this.typeMap.get(clsName);
/*     */   }
/*     */   
/*     */   private void registerWithRoot(InheritInfo info) {
/* 311 */     if (info.getDiscriminatorStringValue() != null) {
/* 312 */       String stringDiscValue = info.getDiscriminatorStringValue();
/* 313 */       this.discMap.put(stringDiscValue, info);
/*     */     } 
/* 315 */     String clsName = SubClassUtil.getSuperClassName(info.getType().getName());
/* 316 */     this.typeMap.put(clsName, info);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addChild(InheritInfo childInfo) {
/* 323 */     this.children.add(childInfo);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getWhere() {
/* 331 */     return this.where;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDiscriminatorColumn() {
/* 338 */     return this.discriminatorColumn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDiscriminatorType() {
/* 345 */     return this.discriminatorType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDiscriminatorLength() {
/* 353 */     return this.discriminatorLength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDiscriminatorStringValue() {
/* 360 */     return this.discriminatorStringValue;
/*     */   }
/*     */   
/*     */   public Object getDiscriminatorValue() {
/* 364 */     return this.discriminatorValue;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 368 */     return "InheritInfo[" + this.type.getName() + "] disc[" + this.discriminatorStringValue + "]";
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\InheritInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
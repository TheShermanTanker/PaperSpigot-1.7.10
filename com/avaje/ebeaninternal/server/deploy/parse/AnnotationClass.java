/*     */ package com.avaje.ebeaninternal.server.deploy.parse;
/*     */ 
/*     */ import com.avaje.ebean.Query;
/*     */ import com.avaje.ebean.annotation.CacheStrategy;
/*     */ import com.avaje.ebean.annotation.LdapDomain;
/*     */ import com.avaje.ebean.annotation.NamedUpdate;
/*     */ import com.avaje.ebean.annotation.NamedUpdates;
/*     */ import com.avaje.ebean.annotation.UpdateMode;
/*     */ import com.avaje.ebean.config.TableName;
/*     */ import com.avaje.ebeaninternal.server.core.CacheOptions;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.CompoundUniqueContraint;
/*     */ import com.avaje.ebeaninternal.server.deploy.DeployNamedQuery;
/*     */ import com.avaje.ebeaninternal.server.deploy.DeployNamedUpdate;
/*     */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanProperty;
/*     */ import javax.persistence.Embeddable;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.NamedQueries;
/*     */ import javax.persistence.NamedQuery;
/*     */ import javax.persistence.Table;
/*     */ import javax.persistence.UniqueConstraint;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
/*     */ import javax.xml.bind.annotation.XmlType;
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
/*     */ public class AnnotationClass
/*     */   extends AnnotationParser
/*     */ {
/*     */   public AnnotationClass(DeployBeanInfo<?> info) {
/*  51 */     super(info);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void parse() {
/*  58 */     read(this.descriptor.getBeanType());
/*  59 */     setTableName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setTableName() {
/*  67 */     if (this.descriptor.isBaseTableType()) {
/*     */ 
/*     */       
/*  70 */       TableName tableName = this.namingConvention.getTableName(this.descriptor.getBeanType());
/*     */       
/*  72 */       this.descriptor.setBaseTable(tableName);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private String[] parseLdapObjectclasses(String objectclasses) {
/*  78 */     if (objectclasses == null || objectclasses.length() == 0) {
/*  79 */       return null;
/*     */     }
/*  81 */     return objectclasses.split(",");
/*     */   }
/*     */   
/*     */   private boolean isXmlElement(Class<?> cls) {
/*  85 */     XmlRootElement rootElement = cls.<XmlRootElement>getAnnotation(XmlRootElement.class);
/*  86 */     if (rootElement != null) {
/*  87 */       return true;
/*     */     }
/*  89 */     XmlType xmlType = cls.<XmlType>getAnnotation(XmlType.class);
/*  90 */     if (xmlType != null) {
/*  91 */       return true;
/*     */     }
/*  93 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private void read(Class<?> cls) {
/*  98 */     LdapDomain ldapDomain = cls.<LdapDomain>getAnnotation(LdapDomain.class);
/*  99 */     if (ldapDomain != null) {
/* 100 */       this.descriptor.setName(cls.getSimpleName());
/* 101 */       this.descriptor.setEntityType(BeanDescriptor.EntityType.LDAP);
/* 102 */       this.descriptor.setLdapBaseDn(ldapDomain.baseDn());
/* 103 */       this.descriptor.setLdapObjectclasses(parseLdapObjectclasses(ldapDomain.objectclass()));
/*     */     } 
/*     */     
/* 106 */     Entity entity = cls.<Entity>getAnnotation(Entity.class);
/* 107 */     if (entity != null) {
/*     */       
/* 109 */       if (entity.name().equals("")) {
/* 110 */         this.descriptor.setName(cls.getSimpleName());
/*     */       } else {
/*     */         
/* 113 */         this.descriptor.setName(entity.name());
/*     */       } 
/* 115 */     } else if (isXmlElement(cls)) {
/* 116 */       this.descriptor.setName(cls.getSimpleName());
/* 117 */       this.descriptor.setEntityType(BeanDescriptor.EntityType.XMLELEMENT);
/*     */     } 
/*     */     
/* 120 */     Embeddable embeddable = cls.<Embeddable>getAnnotation(Embeddable.class);
/* 121 */     if (embeddable != null) {
/* 122 */       this.descriptor.setEntityType(BeanDescriptor.EntityType.EMBEDDED);
/* 123 */       this.descriptor.setName("Embeddable:" + cls.getSimpleName());
/*     */     } 
/*     */     
/* 126 */     UniqueConstraint uc = cls.<UniqueConstraint>getAnnotation(UniqueConstraint.class);
/* 127 */     if (uc != null) {
/* 128 */       this.descriptor.addCompoundUniqueConstraint(new CompoundUniqueContraint(uc.columnNames()));
/*     */     }
/*     */     
/* 131 */     Table table = cls.<Table>getAnnotation(Table.class);
/* 132 */     if (table != null) {
/* 133 */       UniqueConstraint[] uniqueConstraints = table.uniqueConstraints();
/* 134 */       if (uniqueConstraints != null) {
/* 135 */         for (UniqueConstraint c : uniqueConstraints) {
/* 136 */           this.descriptor.addCompoundUniqueConstraint(new CompoundUniqueContraint(c.columnNames()));
/*     */         }
/*     */       }
/*     */     } 
/*     */     
/* 141 */     UpdateMode updateMode = cls.<UpdateMode>getAnnotation(UpdateMode.class);
/* 142 */     if (updateMode != null) {
/* 143 */       this.descriptor.setUpdateChangesOnly(updateMode.updateChangesOnly());
/*     */     }
/*     */     
/* 146 */     NamedQueries namedQueries = cls.<NamedQueries>getAnnotation(NamedQueries.class);
/* 147 */     if (namedQueries != null) {
/* 148 */       readNamedQueries(namedQueries);
/*     */     }
/* 150 */     NamedQuery namedQuery = cls.<NamedQuery>getAnnotation(NamedQuery.class);
/* 151 */     if (namedQuery != null) {
/* 152 */       readNamedQuery(namedQuery);
/*     */     }
/*     */     
/* 155 */     NamedUpdates namedUpdates = cls.<NamedUpdates>getAnnotation(NamedUpdates.class);
/* 156 */     if (namedUpdates != null) {
/* 157 */       readNamedUpdates(namedUpdates);
/*     */     }
/*     */     
/* 160 */     NamedUpdate namedUpdate = cls.<NamedUpdate>getAnnotation(NamedUpdate.class);
/* 161 */     if (namedUpdate != null) {
/* 162 */       readNamedUpdate(namedUpdate);
/*     */     }
/*     */     
/* 165 */     CacheStrategy cacheStrategy = cls.<CacheStrategy>getAnnotation(CacheStrategy.class);
/* 166 */     if (cacheStrategy != null) {
/* 167 */       readCacheStrategy(cacheStrategy);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void readCacheStrategy(CacheStrategy cacheStrategy) {
/* 173 */     CacheOptions cacheOptions = this.descriptor.getCacheOptions();
/* 174 */     cacheOptions.setUseCache(cacheStrategy.useBeanCache());
/* 175 */     cacheOptions.setReadOnly(cacheStrategy.readOnly());
/* 176 */     cacheOptions.setWarmingQuery(cacheStrategy.warmingQuery());
/* 177 */     if (cacheStrategy.naturalKey().length() > 0) {
/* 178 */       String propName = cacheStrategy.naturalKey().trim();
/* 179 */       DeployBeanProperty beanProperty = this.descriptor.getBeanProperty(propName);
/* 180 */       if (beanProperty != null) {
/* 181 */         beanProperty.setNaturalKey(true);
/* 182 */         cacheOptions.setNaturalKey(propName);
/*     */       } 
/*     */     } 
/*     */     
/* 186 */     if (!Query.UseIndex.DEFAULT.equals(cacheStrategy.useIndex()))
/*     */     {
/* 188 */       this.descriptor.setUseIndex(cacheStrategy.useIndex());
/*     */     }
/*     */   }
/*     */   
/*     */   private void readNamedQueries(NamedQueries namedQueries) {
/* 193 */     NamedQuery[] queries = namedQueries.value();
/* 194 */     for (int i = 0; i < queries.length; i++) {
/* 195 */       readNamedQuery(queries[i]);
/*     */     }
/*     */   }
/*     */   
/*     */   private void readNamedQuery(NamedQuery namedQuery) {
/* 200 */     DeployNamedQuery q = new DeployNamedQuery(namedQuery);
/* 201 */     this.descriptor.add(q);
/*     */   }
/*     */   
/*     */   private void readNamedUpdates(NamedUpdates updates) {
/* 205 */     NamedUpdate[] updateArray = updates.value();
/* 206 */     for (int i = 0; i < updateArray.length; i++) {
/* 207 */       readNamedUpdate(updateArray[i]);
/*     */     }
/*     */   }
/*     */   
/*     */   private void readNamedUpdate(NamedUpdate update) {
/* 212 */     DeployNamedUpdate upd = new DeployNamedUpdate(update);
/* 213 */     this.descriptor.add(upd);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\parse\AnnotationClass.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
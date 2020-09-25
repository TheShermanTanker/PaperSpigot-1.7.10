/*     */ package com.avaje.ebeaninternal.server.deploy.id;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssoc;
/*     */ import com.avaje.ebeaninternal.server.deploy.DbSqlContext;
/*     */ import com.avaje.ebeaninternal.server.deploy.IntersectionRow;
/*     */ import com.avaje.ebeaninternal.server.persist.dml.GenerateDmlRequest;
/*     */ import com.avaje.ebeaninternal.server.persist.dmlbind.BindableRequest;
/*     */ import com.avaje.ebeaninternal.util.ValueUtil;
/*     */ import java.sql.SQLException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ImportedIdMultiple
/*     */   implements ImportedId
/*     */ {
/*     */   final BeanPropertyAssoc<?> owner;
/*     */   final ImportedIdSimple[] imported;
/*     */   
/*     */   public ImportedIdMultiple(BeanPropertyAssoc<?> owner, ImportedIdSimple[] imported) {
/*  23 */     this.owner = owner;
/*  24 */     this.imported = imported;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addFkeys(String name) {}
/*     */ 
/*     */   
/*     */   public String getLogicalName() {
/*  33 */     return null;
/*     */   }
/*     */   
/*     */   public boolean isScalar() {
/*  37 */     return false;
/*     */   }
/*     */   
/*     */   public String getDbColumn() {
/*  41 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void sqlAppend(DbSqlContext ctx) {
/*  46 */     for (int i = 0; i < this.imported.length; i++) {
/*  47 */       ctx.appendColumn((this.imported[i]).localDbColumn);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void dmlAppend(GenerateDmlRequest request) {
/*  53 */     for (int i = 0; i < this.imported.length; i++) {
/*  54 */       request.appendColumn((this.imported[i]).localDbColumn);
/*     */     }
/*     */   }
/*     */   
/*     */   public void dmlWhere(GenerateDmlRequest request, Object bean) {
/*  59 */     if (bean == null) {
/*  60 */       for (int i = 0; i < this.imported.length; i++) {
/*  61 */         request.appendColumnIsNull((this.imported[i]).localDbColumn);
/*     */       }
/*     */     } else {
/*  64 */       for (int i = 0; i < this.imported.length; i++) {
/*  65 */         Object value = (this.imported[i]).foreignProperty.getValue(bean);
/*  66 */         if (value == null) {
/*  67 */           request.appendColumnIsNull((this.imported[i]).localDbColumn);
/*     */         } else {
/*  69 */           request.appendColumn((this.imported[i]).localDbColumn);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasChanged(Object bean, Object oldValues) {
/*  77 */     for (int i = 0; i < this.imported.length; i++) {
/*  78 */       Object id = (this.imported[i]).foreignProperty.getValue(bean);
/*  79 */       Object oldId = (this.imported[i]).foreignProperty.getValue(oldValues);
/*  80 */       if (!ValueUtil.areEqual(id, oldId)) {
/*  81 */         return true;
/*     */       }
/*     */     } 
/*  84 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object bind(BindableRequest request, Object bean, boolean bindNull) throws SQLException {
/*  90 */     for (int i = 0; i < this.imported.length; i++) {
/*  91 */       if ((this.imported[i]).owner.isUpdateable()) {
/*  92 */         Object scalarValue = (this.imported[i]).foreignProperty.getValue(bean);
/*  93 */         request.bind(scalarValue, (this.imported[i]).foreignProperty, (this.imported[i]).localDbColumn, true);
/*     */       } 
/*     */     } 
/*     */     
/*  97 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void buildImport(IntersectionRow row, Object other) {
/* 102 */     for (int i = 0; i < this.imported.length; i++) {
/* 103 */       Object scalarValue = (this.imported[i]).foreignProperty.getValue(other);
/* 104 */       row.put((this.imported[i]).localDbColumn, scalarValue);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanProperty findMatchImport(String matchDbColumn) {
/* 113 */     BeanProperty p = null;
/* 114 */     for (int i = 0; i < this.imported.length; i++) {
/* 115 */       p = this.imported[i].findMatchImport(matchDbColumn);
/* 116 */       if (p != null) {
/* 117 */         return p;
/*     */       }
/*     */     } 
/*     */     
/* 121 */     return p;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\id\ImportedIdMultiple.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
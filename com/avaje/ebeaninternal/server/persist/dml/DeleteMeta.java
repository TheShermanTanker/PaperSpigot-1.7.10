/*     */ package com.avaje.ebeaninternal.server.persist.dml;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.core.ConcurrencyMode;
/*     */ import com.avaje.ebeaninternal.server.core.PersistRequestBean;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.persist.dmlbind.Bindable;
/*     */ import com.avaje.ebeaninternal.server.persist.dmlbind.BindableId;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Set;
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
/*     */ public final class DeleteMeta
/*     */ {
/*     */   private final String sqlVersion;
/*     */   private final String sqlNone;
/*     */   private final BindableId id;
/*     */   private final Bindable version;
/*     */   private final Bindable all;
/*     */   private final String tableName;
/*     */   private final boolean emptyStringAsNull;
/*     */   
/*     */   public DeleteMeta(boolean emptyStringAsNull, BeanDescriptor<?> desc, BindableId id, Bindable version, Bindable all) {
/*  52 */     this.emptyStringAsNull = emptyStringAsNull;
/*  53 */     this.tableName = desc.getBaseTable();
/*  54 */     this.id = id;
/*  55 */     this.version = version;
/*  56 */     this.all = all;
/*     */     
/*  58 */     this.sqlNone = genSql(ConcurrencyMode.NONE);
/*  59 */     this.sqlVersion = genSql(ConcurrencyMode.VERSION);
/*     */   }
/*     */   
/*     */   public boolean isEmptyStringAsNull() {
/*  63 */     return this.emptyStringAsNull;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTableName() {
/*  70 */     return this.tableName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void bind(PersistRequestBean<?> persist, DmlHandler bind) throws SQLException {
/*  78 */     Object oldBean, bean = persist.getBean();
/*     */     
/*  80 */     this.id.dmlBind(bind, false, bean);
/*     */     
/*  82 */     switch (persist.getConcurrencyMode()) {
/*     */       case VERSION:
/*  84 */         this.version.dmlBind(bind, false, bean);
/*     */         break;
/*     */       
/*     */       case ALL:
/*  88 */         oldBean = persist.getOldValues();
/*  89 */         this.all.dmlBindWhere(bind, true, oldBean);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSql(PersistRequestBean<?> request) throws SQLException {
/* 102 */     if (this.id.isEmpty()) {
/* 103 */       throw new IllegalStateException("Can not deleteById on " + request.getFullName() + " as no @Id property");
/*     */     }
/*     */     
/* 106 */     switch (request.determineConcurrencyMode()) {
/*     */       case NONE:
/* 108 */         return this.sqlNone;
/*     */       
/*     */       case VERSION:
/* 111 */         return this.sqlVersion;
/*     */       
/*     */       case ALL:
/* 114 */         return genDynamicWhere(request.getLoadedProperties(), request.getOldValues());
/*     */     } 
/*     */     
/* 117 */     throw new RuntimeException("Invalid mode " + request.determineConcurrencyMode());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String genSql(ConcurrencyMode conMode) {
/* 125 */     GenerateDmlRequest request = new GenerateDmlRequest(this.emptyStringAsNull);
/*     */     
/* 127 */     request.append("delete from ").append(this.tableName);
/* 128 */     request.append(" where ");
/*     */     
/* 130 */     request.setWhereIdMode();
/* 131 */     this.id.dmlAppend(request, false);
/*     */     
/* 133 */     if (ConcurrencyMode.VERSION.equals(conMode)) {
/* 134 */       if (this.version == null) {
/* 135 */         return null;
/*     */       }
/* 137 */       this.version.dmlAppend(request, false);
/*     */     }
/* 139 */     else if (ConcurrencyMode.ALL.equals(conMode)) {
/* 140 */       throw new RuntimeException("Never called for ConcurrencyMode.ALL");
/*     */     } 
/*     */     
/* 143 */     return request.toString();
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
/*     */   private String genDynamicWhere(Set<String> includedProps, Object oldBean) throws SQLException {
/* 155 */     GenerateDmlRequest request = new GenerateDmlRequest(this.emptyStringAsNull, includedProps, oldBean);
/*     */     
/* 157 */     request.append(this.sqlNone);
/*     */     
/* 159 */     request.setWhereMode();
/* 160 */     this.all.dmlWhere(request, true, oldBean);
/*     */     
/* 162 */     return request.toString();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\persist\dml\DeleteMeta.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
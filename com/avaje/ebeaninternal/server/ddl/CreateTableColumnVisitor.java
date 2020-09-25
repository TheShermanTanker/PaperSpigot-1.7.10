/*     */ package com.avaje.ebeaninternal.server.ddl;
/*     */ 
/*     */ import com.avaje.ebean.config.dbplatform.DbDdlSyntax;
/*     */ import com.avaje.ebean.config.dbplatform.IdType;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyCompound;
/*     */ import com.avaje.ebeaninternal.server.deploy.TableJoin;
/*     */ import com.avaje.ebeaninternal.server.deploy.TableJoinColumn;
/*     */ import com.avaje.ebeaninternal.server.deploy.id.ImportedId;
/*     */ import com.avaje.ebeaninternal.server.lib.util.StringHelper;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CreateTableColumnVisitor
/*     */   extends BaseTablePropertyVisitor
/*     */ {
/*  23 */   private static final Logger logger = Logger.getLogger(CreateTableColumnVisitor.class.getName());
/*     */   
/*     */   private final DdlGenContext ctx;
/*     */   
/*     */   private final DbDdlSyntax ddl;
/*     */   
/*     */   private final CreateTableVisitor parent;
/*     */   
/*     */   public CreateTableColumnVisitor(CreateTableVisitor parent, DdlGenContext ctx) {
/*  32 */     this.parent = parent;
/*  33 */     this.ctx = ctx;
/*  34 */     this.ddl = ctx.getDdlSyntax();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitMany(BeanPropertyAssocMany<?> p) {
/*  40 */     if (p.isManyToMany() && 
/*  41 */       p.getMappedBy() == null) {
/*     */ 
/*     */ 
/*     */       
/*  45 */       TableJoin intersectionTableJoin = p.getIntersectionTableJoin();
/*     */ 
/*     */       
/*  48 */       String intTable = intersectionTableJoin.getTable();
/*  49 */       if (this.ctx.isProcessIntersectionTable(intTable))
/*     */       {
/*     */ 
/*     */         
/*  53 */         (new CreateIntersectionTable(this.ctx, p)).build();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitCompoundScalar(BeanPropertyCompound compound, BeanProperty p) {
/*  61 */     visitScalar(p);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitCompound(BeanPropertyCompound p) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitEmbeddedScalar(BeanProperty p, BeanPropertyAssocOne<?> embedded) {
/*  73 */     visitScalar(p);
/*     */   }
/*     */ 
/*     */   
/*     */   private StringBuilder createUniqueConstraintBuffer(String table, String column) {
/*  78 */     String uqConstraintName = "uq_" + table + "_" + column;
/*     */     
/*  80 */     if (uqConstraintName.length() > this.ddl.getMaxConstraintNameLength()) {
/*  81 */       uqConstraintName = uqConstraintName.substring(0, this.ddl.getMaxConstraintNameLength());
/*     */     }
/*     */     
/*  84 */     uqConstraintName = this.ctx.removeQuotes(uqConstraintName);
/*  85 */     uqConstraintName = StringHelper.replaceString(uqConstraintName, " ", "_");
/*     */     
/*  87 */     StringBuilder constraintExpr = new StringBuilder();
/*  88 */     constraintExpr.append("constraint ").append(uqConstraintName).append(" unique (");
/*     */ 
/*     */ 
/*     */     
/*  92 */     return constraintExpr;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitOneImported(BeanPropertyAssocOne<?> p) {
/*  98 */     ImportedId importedId = p.getImportedId();
/*     */     
/* 100 */     TableJoinColumn[] columns = p.getTableJoin().columns();
/* 101 */     if (columns.length == 0) {
/* 102 */       String msg = "No join columns for " + p.getFullBeanName();
/* 103 */       throw new RuntimeException(msg);
/*     */     } 
/*     */     
/* 106 */     StringBuilder constraintExpr = createUniqueConstraintBuffer(p.getBeanDescriptor().getBaseTable(), columns[0].getLocalDbColumn());
/*     */     
/* 108 */     for (int i = 0; i < columns.length; i++) {
/*     */       
/* 110 */       String dbCol = columns[i].getLocalDbColumn();
/*     */       
/* 112 */       if (i > 0) {
/* 113 */         constraintExpr.append(", ");
/*     */       }
/* 115 */       constraintExpr.append(dbCol);
/*     */       
/* 117 */       if (!this.parent.isDbColumnWritten(dbCol)) {
/*     */ 
/*     */ 
/*     */         
/* 121 */         this.parent.writeColumnName(dbCol, (BeanProperty)p);
/*     */         
/* 123 */         BeanProperty importedProperty = importedId.findMatchImport(dbCol);
/* 124 */         if (importedProperty != null) {
/*     */           
/* 126 */           String columnDefn = this.ctx.getColumnDefn(importedProperty);
/* 127 */           this.ctx.write(columnDefn);
/*     */         } else {
/*     */           
/* 130 */           throw new RuntimeException("Imported BeanProperty not found?");
/*     */         } 
/*     */         
/* 133 */         if (!p.isNullable()) {
/* 134 */           this.ctx.write(" not null");
/*     */         }
/* 136 */         this.ctx.write(",").writeNewLine();
/*     */       } 
/* 138 */     }  constraintExpr.append(")");
/*     */     
/* 140 */     if (p.isOneToOne() && 
/* 141 */       this.ddl.isAddOneToOneUniqueContraint()) {
/* 142 */       this.parent.addUniqueConstraint(constraintExpr.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitScalar(BeanProperty p) {
/* 151 */     if (p.isSecondaryTable()) {
/*     */       return;
/*     */     }
/*     */     
/* 155 */     if (this.parent.isDbColumnWritten(p.getDbColumn())) {
/*     */       return;
/*     */     }
/*     */     
/* 159 */     this.parent.writeColumnName(p.getDbColumn(), p);
/*     */     
/* 161 */     String columnDefn = this.ctx.getColumnDefn(p);
/* 162 */     this.ctx.write(columnDefn);
/*     */     
/* 164 */     if (isIdentity(p)) {
/* 165 */       writeIdentity();
/*     */     }
/*     */     
/* 168 */     if (p.isId() && this.ddl.isInlinePrimaryKeyConstraint()) {
/* 169 */       this.ctx.write(" primary key");
/*     */     }
/* 171 */     else if (!p.isNullable() || p.isDDLNotNull()) {
/* 172 */       this.ctx.write(" not null");
/*     */     } 
/*     */     
/* 175 */     if (p.isUnique() && !p.isId()) {
/* 176 */       this.parent.addUniqueConstraint(createUniqueConstraint(p));
/*     */     }
/*     */     
/* 179 */     this.parent.addCheckConstraint(p);
/*     */     
/* 181 */     this.ctx.write(",").writeNewLine();
/*     */   }
/*     */ 
/*     */   
/*     */   private String createUniqueConstraint(BeanProperty p) {
/* 186 */     StringBuilder expr = createUniqueConstraintBuffer(p.getBeanDescriptor().getBaseTable(), p.getDbColumn());
/*     */     
/* 188 */     expr.append(p.getDbColumn()).append(")");
/*     */     
/* 190 */     return expr.toString();
/*     */   }
/*     */   
/*     */   protected void writeIdentity() {
/* 194 */     String identity = this.ddl.getIdentity();
/* 195 */     if (identity != null && identity.length() > 0) {
/* 196 */       this.ctx.write(" ").write(identity);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isIdentity(BeanProperty p) {
/* 202 */     if (p.isId()) {
/*     */       try {
/* 204 */         IdType idType = p.getBeanDescriptor().getIdType();
/*     */         
/* 206 */         if (idType.equals(IdType.IDENTITY)) {
/*     */           
/* 208 */           int jdbcType = p.getScalarType().getJdbcType();
/* 209 */           if (jdbcType == 4 || jdbcType == -5 || jdbcType == 5)
/*     */           {
/* 211 */             return true;
/*     */           }
/*     */         } 
/* 214 */       } catch (Exception e) {
/* 215 */         String msg = "Error determining identity on property " + p.getFullBeanName();
/* 216 */         logger.log(Level.SEVERE, msg, e);
/*     */       } 
/*     */     }
/* 219 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\ddl\CreateTableColumnVisitor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
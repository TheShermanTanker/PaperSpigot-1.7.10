/*     */ package com.avaje.ebeaninternal.server.deploy;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.core.InternString;
/*     */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanProperty;
/*     */ import com.avaje.ebeaninternal.server.deploy.meta.DeployTableJoin;
/*     */ import com.avaje.ebeaninternal.server.deploy.meta.DeployTableJoinColumn;
/*     */ import com.avaje.ebeaninternal.server.query.SplitName;
/*     */ import com.avaje.ebeaninternal.server.query.SqlBeanLoad;
/*     */ import java.sql.SQLException;
/*     */ import java.util.LinkedHashMap;
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
/*     */ public final class TableJoin
/*     */ {
/*     */   public static final String NEW_LINE = "\n";
/*     */   public static final String LEFT_OUTER = "left outer join";
/*     */   public static final String JOIN = "join";
/*     */   private final boolean importedPrimaryKey;
/*     */   private final String table;
/*     */   private final String type;
/*     */   private final BeanCascadeInfo cascadeInfo;
/*     */   private final BeanProperty[] properties;
/*     */   private final TableJoinColumn[] columns;
/*     */   
/*     */   public TableJoin(DeployTableJoin deploy, LinkedHashMap<String, BeanProperty> propMap) {
/*  80 */     this.importedPrimaryKey = deploy.isImportedPrimaryKey();
/*  81 */     this.table = InternString.intern(deploy.getTable());
/*  82 */     this.type = InternString.intern(deploy.getType());
/*  83 */     this.cascadeInfo = deploy.getCascadeInfo();
/*     */     
/*  85 */     DeployTableJoinColumn[] deployCols = deploy.columns();
/*  86 */     this.columns = new TableJoinColumn[deployCols.length];
/*  87 */     for (int i = 0; i < deployCols.length; i++) {
/*  88 */       this.columns[i] = new TableJoinColumn(deployCols[i]);
/*     */     }
/*     */     
/*  91 */     DeployBeanProperty[] deployProps = deploy.properties();
/*  92 */     if (deployProps.length > 0 && propMap == null) {
/*  93 */       throw new NullPointerException("propMap is null?");
/*     */     }
/*     */     
/*  96 */     this.properties = new BeanProperty[deployProps.length];
/*  97 */     for (int j = 0; j < deployProps.length; j++) {
/*  98 */       BeanProperty prop = propMap.get(deployProps[j].getName());
/*  99 */       this.properties[j] = prop;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TableJoin createWithAlias(String localAlias, String foreignAlias) {
/* 109 */     return new TableJoin(this, localAlias, foreignAlias);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private TableJoin(TableJoin join, String localAlias, String foreignAlias) {
/* 118 */     this.importedPrimaryKey = join.importedPrimaryKey;
/* 119 */     this.table = join.table;
/* 120 */     this.type = join.type;
/* 121 */     this.cascadeInfo = join.cascadeInfo;
/* 122 */     this.properties = join.properties;
/* 123 */     this.columns = join.columns;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 128 */     StringBuilder sb = new StringBuilder(30);
/* 129 */     sb.append(this.type).append(" ").append(this.table).append(" ");
/* 130 */     for (int i = 0; i < this.columns.length; i++) {
/* 131 */       sb.append(this.columns[i]).append(" ");
/*     */     }
/* 133 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public void appendSelect(DbSqlContext ctx, boolean subQuery) {
/* 137 */     for (int i = 0, x = this.properties.length; i < x; i++) {
/* 138 */       this.properties[i].appendSelect(ctx, subQuery);
/*     */     }
/*     */   }
/*     */   
/*     */   public void load(SqlBeanLoad sqlBeanLoad) throws SQLException {
/* 143 */     for (int i = 0, x = this.properties.length; i < x; i++) {
/* 144 */       this.properties[i].load(sqlBeanLoad);
/*     */     }
/*     */   }
/*     */   
/*     */   public Object readSet(DbReadContext ctx, Object bean, Class<?> type) throws SQLException {
/* 149 */     for (int i = 0, x = this.properties.length; i < x; i++) {
/* 150 */       this.properties[i].readSet(ctx, bean, type);
/*     */     }
/* 152 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isImportedPrimaryKey() {
/* 159 */     return this.importedPrimaryKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanCascadeInfo getCascadeInfo() {
/* 166 */     return this.cascadeInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TableJoinColumn[] columns() {
/* 173 */     return this.columns;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanProperty[] properties() {
/* 181 */     return this.properties;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTable() {
/* 188 */     return this.table;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getType() {
/* 195 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOuterJoin() {
/* 202 */     return this.type.equals("left outer join");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addJoin(boolean forceOuterJoin, String prefix, DbSqlContext ctx) {
/* 207 */     String[] names = SplitName.split(prefix);
/* 208 */     String a1 = ctx.getTableAlias(names[0]);
/* 209 */     String a2 = ctx.getTableAlias(prefix);
/*     */     
/* 211 */     return addJoin(forceOuterJoin, a1, a2, ctx);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addJoin(boolean forceOuterJoin, String a1, String a2, DbSqlContext ctx) {
/* 216 */     ctx.addJoin(forceOuterJoin ? "left outer join" : this.type, this.table, columns(), a1, a2);
/*     */     
/* 218 */     return (forceOuterJoin || "left outer join".equals(this.type));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addInnerJoin(String a1, String a2, DbSqlContext ctx) {
/* 225 */     ctx.addJoin("join", this.table, columns(), a1, a2);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\TableJoin.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
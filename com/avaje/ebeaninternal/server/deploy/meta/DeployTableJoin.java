/*     */ package com.avaje.ebeaninternal.server.deploy.meta;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.core.Message;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanCascadeInfo;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanTable;
/*     */ import java.util.ArrayList;
/*     */ import javax.persistence.JoinColumn;
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
/*     */ public class DeployTableJoin
/*     */ {
/*     */   private boolean importedPrimaryKey;
/*     */   private String table;
/*  53 */   private String type = "join";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  58 */   private ArrayList<DeployBeanProperty> properties = new ArrayList<DeployBeanProperty>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  63 */   private ArrayList<DeployTableJoinColumn> columns = new ArrayList<DeployTableJoinColumn>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   private BeanCascadeInfo cascadeInfo = new BeanCascadeInfo();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  78 */     return this.type + " " + this.table + " " + this.columns;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isImportedPrimaryKey() {
/*  85 */     return this.importedPrimaryKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setImportedPrimaryKey(boolean importedPrimaryKey) {
/*  93 */     this.importedPrimaryKey = importedPrimaryKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasJoinColumns() {
/* 100 */     return (this.columns.size() > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanCascadeInfo getCascadeInfo() {
/* 107 */     return this.cascadeInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setColumns(DeployTableJoinColumn[] cols, boolean reverse) {
/* 115 */     this.columns = new ArrayList<DeployTableJoinColumn>();
/* 116 */     for (int i = 0; i < cols.length; i++) {
/* 117 */       addJoinColumn(cols[i].copy(reverse));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addJoinColumn(DeployTableJoinColumn pair) {
/* 125 */     this.columns.add(pair);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addJoinColumn(boolean order, JoinColumn jc, BeanTable beanTable) {
/* 135 */     if (!"".equals(jc.table())) {
/* 136 */       setTable(jc.table());
/*     */     }
/* 138 */     addJoinColumn(new DeployTableJoinColumn(order, jc, beanTable));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addJoinColumn(boolean order, JoinColumn[] jcArray, BeanTable beanTable) {
/* 145 */     for (int i = 0; i < jcArray.length; i++) {
/* 146 */       addJoinColumn(order, jcArray[i], beanTable);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DeployTableJoinColumn[] columns() {
/* 154 */     return this.columns.<DeployTableJoinColumn>toArray(new DeployTableJoinColumn[this.columns.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DeployBeanProperty[] properties() {
/* 162 */     return this.properties.<DeployBeanProperty>toArray(new DeployBeanProperty[this.properties.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTable() {
/* 169 */     return this.table;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTable(String table) {
/* 176 */     this.table = table;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getType() {
/* 183 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOuterJoin() {
/* 190 */     return this.type.equals("left outer join");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setType(String joinType) {
/* 197 */     joinType = joinType.toUpperCase();
/* 198 */     if (joinType.equalsIgnoreCase("join")) {
/* 199 */       this.type = "join";
/* 200 */     } else if (joinType.indexOf("LEFT") > -1) {
/* 201 */       this.type = "left outer join";
/* 202 */     } else if (joinType.indexOf("OUTER") > -1) {
/* 203 */       this.type = "left outer join";
/* 204 */     } else if (joinType.indexOf("INNER") > -1) {
/* 205 */       this.type = "join";
/*     */     } else {
/* 207 */       throw new RuntimeException(Message.msg("join.type.unknown", joinType));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public DeployTableJoin createInverse(String tableName) {
/* 213 */     DeployTableJoin inverse = new DeployTableJoin();
/*     */     
/* 215 */     return copyTo(inverse, true, tableName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public DeployTableJoin copyTo(DeployTableJoin destJoin, boolean reverse, String tableName) {
/* 221 */     destJoin.setTable(tableName);
/* 222 */     destJoin.setType(this.type);
/* 223 */     destJoin.setColumns(columns(), reverse);
/*     */     
/* 225 */     return destJoin;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\meta\DeployTableJoin.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
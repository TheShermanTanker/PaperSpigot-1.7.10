/*     */ package com.avaje.ebeaninternal.server.query;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.deploy.DbSqlContext;
/*     */ import com.avaje.ebeaninternal.server.deploy.TableJoinColumn;
/*     */ import com.avaje.ebeaninternal.server.lib.util.StringHelper;
/*     */ import com.avaje.ebeaninternal.server.util.ArrayStack;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultDbSqlContext
/*     */   implements DbSqlContext
/*     */ {
/*     */   private static final String NEW_LINE = "\n";
/*     */   private static final String COMMA = ", ";
/*     */   private static final String PERIOD = ".";
/*     */   private final String tableAliasPlaceHolder;
/*     */   private final String columnAliasPrefix;
/*  24 */   private final ArrayStack<String> tableAliasStack = new ArrayStack();
/*     */   
/*  26 */   private final ArrayStack<String> joinStack = new ArrayStack();
/*     */   
/*  28 */   private final ArrayStack<String> prefixStack = new ArrayStack();
/*     */   
/*     */   private final boolean useColumnAlias;
/*     */   
/*     */   private int columnIndex;
/*     */   
/*  34 */   private StringBuilder sb = new StringBuilder(140);
/*     */ 
/*     */   
/*     */   private HashSet<String> formulaJoins;
/*     */ 
/*     */   
/*     */   private HashSet<String> tableJoins;
/*     */ 
/*     */   
/*     */   private SqlTreeAlias alias;
/*     */ 
/*     */   
/*     */   private String currentPrefix;
/*     */ 
/*     */   
/*     */   private ArrayList<BeanProperty> encryptedProps;
/*     */ 
/*     */   
/*     */   public DefaultDbSqlContext(SqlTreeAlias alias, String tableAliasPlaceHolder) {
/*  53 */     this.tableAliasPlaceHolder = tableAliasPlaceHolder;
/*  54 */     this.columnAliasPrefix = null;
/*  55 */     this.useColumnAlias = false;
/*  56 */     this.alias = alias;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultDbSqlContext(SqlTreeAlias alias, String tableAliasPlaceHolder, String columnAliasPrefix, boolean alwaysUseColumnAlias) {
/*  64 */     this.alias = alias;
/*  65 */     this.tableAliasPlaceHolder = tableAliasPlaceHolder;
/*  66 */     this.columnAliasPrefix = columnAliasPrefix;
/*  67 */     this.useColumnAlias = alwaysUseColumnAlias;
/*     */   }
/*     */   
/*     */   public void addEncryptedProp(BeanProperty p) {
/*  71 */     if (this.encryptedProps == null) {
/*  72 */       this.encryptedProps = new ArrayList<BeanProperty>();
/*     */     }
/*  74 */     this.encryptedProps.add(p);
/*     */   }
/*     */   
/*     */   public BeanProperty[] getEncryptedProps() {
/*  78 */     if (this.encryptedProps == null) {
/*  79 */       return null;
/*     */     }
/*     */     
/*  82 */     return this.encryptedProps.<BeanProperty>toArray(new BeanProperty[this.encryptedProps.size()]);
/*     */   }
/*     */   
/*     */   public String peekJoin() {
/*  86 */     return (String)this.joinStack.peek();
/*     */   }
/*     */   
/*     */   public void popJoin() {
/*  90 */     this.joinStack.pop();
/*     */   }
/*     */   
/*     */   public void pushJoin(String node) {
/*  94 */     this.joinStack.push(node);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addJoin(String type, String table, TableJoinColumn[] cols, String a1, String a2) {
/*  99 */     if (this.tableJoins == null) {
/* 100 */       this.tableJoins = new HashSet<String>();
/*     */     }
/*     */     
/* 103 */     String joinKey = table + "-" + a1 + "-" + a2;
/* 104 */     if (this.tableJoins.contains(joinKey)) {
/*     */       return;
/*     */     }
/*     */     
/* 108 */     this.tableJoins.add(joinKey);
/*     */     
/* 110 */     this.sb.append("\n");
/* 111 */     this.sb.append(type);
/*     */     
/* 113 */     this.sb.append(" ").append(table).append(" ");
/* 114 */     this.sb.append(a2);
/*     */     
/* 116 */     this.sb.append(" on ");
/*     */     
/* 118 */     for (int i = 0; i < cols.length; i++) {
/* 119 */       TableJoinColumn pair = cols[i];
/* 120 */       if (i > 0) {
/* 121 */         this.sb.append(" and ");
/*     */       }
/*     */       
/* 124 */       this.sb.append(a2);
/* 125 */       this.sb.append(".").append(pair.getForeignDbColumn());
/* 126 */       this.sb.append(" = ");
/* 127 */       this.sb.append(a1);
/* 128 */       this.sb.append(".").append(pair.getLocalDbColumn());
/*     */     } 
/*     */     
/* 131 */     this.sb.append(" ");
/*     */   }
/*     */   
/*     */   public String getTableAlias(String prefix) {
/* 135 */     return this.alias.getTableAlias(prefix);
/*     */   }
/*     */   
/*     */   public String getTableAliasManyWhere(String prefix) {
/* 139 */     return this.alias.getTableAliasManyWhere(prefix);
/*     */   }
/*     */   
/*     */   public void pushSecondaryTableAlias(String alias) {
/* 143 */     this.tableAliasStack.push(alias);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getRelativePrefix(String propName) {
/* 148 */     return (this.currentPrefix == null) ? propName : (this.currentPrefix + "." + propName);
/*     */   }
/*     */ 
/*     */   
/*     */   public void pushTableAlias(String prefix) {
/* 153 */     this.prefixStack.push(this.currentPrefix);
/* 154 */     this.currentPrefix = prefix;
/* 155 */     this.tableAliasStack.push(getTableAlias(prefix));
/*     */   }
/*     */   
/*     */   public void popTableAlias() {
/* 159 */     this.tableAliasStack.pop();
/*     */     
/* 161 */     this.currentPrefix = (String)this.prefixStack.pop();
/*     */   }
/*     */   
/*     */   public StringBuilder getBuffer() {
/* 165 */     return this.sb;
/*     */   }
/*     */   
/*     */   public DefaultDbSqlContext append(String s) {
/* 169 */     this.sb.append(s);
/* 170 */     return this;
/*     */   }
/*     */   
/*     */   public DefaultDbSqlContext append(char s) {
/* 174 */     this.sb.append(s);
/* 175 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void appendFormulaJoin(String sqlFormulaJoin, boolean forceOuterJoin) {
/* 181 */     String tableAlias = (String)this.tableAliasStack.peek();
/* 182 */     String converted = StringHelper.replaceString(sqlFormulaJoin, this.tableAliasPlaceHolder, tableAlias);
/*     */     
/* 184 */     if (this.formulaJoins == null) {
/* 185 */       this.formulaJoins = new HashSet<String>();
/*     */     }
/* 187 */     else if (this.formulaJoins.contains(converted)) {
/*     */       return;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 194 */     this.formulaJoins.add(converted);
/*     */     
/* 196 */     this.sb.append("\n");
/*     */     
/* 198 */     if (forceOuterJoin && 
/* 199 */       "join".equals(sqlFormulaJoin.substring(0, 4).toLowerCase()))
/*     */     {
/* 201 */       append(" left outer ");
/*     */     }
/*     */ 
/*     */     
/* 205 */     this.sb.append(converted);
/* 206 */     this.sb.append(" ");
/*     */   }
/*     */ 
/*     */   
/*     */   public void appendFormulaSelect(String sqlFormulaSelect) {
/* 211 */     String tableAlias = (String)this.tableAliasStack.peek();
/* 212 */     String converted = StringHelper.replaceString(sqlFormulaSelect, this.tableAliasPlaceHolder, tableAlias);
/*     */     
/* 214 */     this.sb.append(", ");
/* 215 */     this.sb.append(converted);
/*     */   }
/*     */   
/*     */   public void appendColumn(String column) {
/* 219 */     appendColumn((String)this.tableAliasStack.peek(), column);
/*     */   }
/*     */   
/*     */   public void appendColumn(String tableAlias, String column) {
/* 223 */     this.sb.append(", ");
/*     */     
/* 225 */     if (column.indexOf("${}") > -1) {
/*     */ 
/*     */       
/* 228 */       String x = StringHelper.replaceString(column, "${}", tableAlias);
/* 229 */       this.sb.append(x);
/*     */     } else {
/* 231 */       this.sb.append(tableAlias);
/* 232 */       this.sb.append(".");
/* 233 */       this.sb.append(column);
/*     */     } 
/* 235 */     if (this.useColumnAlias) {
/* 236 */       this.sb.append(" ");
/* 237 */       this.sb.append(this.columnAliasPrefix);
/* 238 */       this.sb.append(this.columnIndex);
/*     */     } 
/* 240 */     this.columnIndex++;
/*     */   }
/*     */   
/*     */   public String peekTableAlias() {
/* 244 */     return (String)this.tableAliasStack.peek();
/*     */   }
/*     */   
/*     */   public void appendRawColumn(String rawcolumnWithTableAlias) {
/* 248 */     this.sb.append(", ");
/* 249 */     this.sb.append(rawcolumnWithTableAlias);
/*     */     
/* 251 */     if (this.useColumnAlias) {
/* 252 */       this.sb.append(" ");
/* 253 */       this.sb.append(this.columnAliasPrefix);
/* 254 */       this.sb.append(this.columnIndex);
/*     */     } 
/* 256 */     this.columnIndex++;
/*     */   }
/*     */   
/*     */   public int length() {
/* 260 */     return this.sb.length();
/*     */   }
/*     */   
/*     */   public String getContent() {
/* 264 */     String s = this.sb.toString();
/* 265 */     this.sb = new StringBuilder();
/* 266 */     return s;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 270 */     return "DefaultDbSqlContext: " + this.sb.toString();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\query\DefaultDbSqlContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
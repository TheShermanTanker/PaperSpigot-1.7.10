/*     */ package com.avaje.ebeaninternal.server.querydefn;
/*     */ 
/*     */ import com.avaje.ebean.EbeanServer;
/*     */ import com.avaje.ebean.Update;
/*     */ import com.avaje.ebeaninternal.api.BindParams;
/*     */ import com.avaje.ebeaninternal.api.SpiUpdate;
/*     */ import com.avaje.ebeaninternal.server.deploy.DeployNamedUpdate;
/*     */ import java.io.Serializable;
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
/*     */ public final class DefaultOrmUpdate<T>
/*     */   implements SpiUpdate<T>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -8791423602246515438L;
/*     */   private final transient EbeanServer server;
/*     */   private final Class<?> beanType;
/*     */   private final String name;
/*  49 */   private final BindParams bindParams = new BindParams();
/*     */ 
/*     */ 
/*     */   
/*     */   private final String updateStatement;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean notifyCache = true;
/*     */ 
/*     */ 
/*     */   
/*     */   private int timeout;
/*     */ 
/*     */ 
/*     */   
/*     */   private String generatedSql;
/*     */ 
/*     */   
/*     */   private final String baseTable;
/*     */ 
/*     */   
/*     */   private final SpiUpdate.OrmUpdateType type;
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultOrmUpdate(Class<?> beanType, EbeanServer server, String baseTable, String updateStatement) {
/*  76 */     this.beanType = beanType;
/*  77 */     this.server = server;
/*  78 */     this.baseTable = baseTable;
/*  79 */     this.name = "";
/*  80 */     this.updateStatement = updateStatement;
/*  81 */     this.type = deriveType(updateStatement);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultOrmUpdate(Class<?> beanType, EbeanServer server, String baseTable, DeployNamedUpdate namedUpdate) {
/*  87 */     this.beanType = beanType;
/*  88 */     this.server = server;
/*  89 */     this.baseTable = baseTable;
/*  90 */     this.name = namedUpdate.getName();
/*  91 */     this.notifyCache = namedUpdate.isNotifyCache();
/*     */ 
/*     */ 
/*     */     
/*  95 */     this.updateStatement = namedUpdate.getSqlUpdateStatement();
/*  96 */     this.type = deriveType(this.updateStatement);
/*     */   }
/*     */   
/*     */   public DefaultOrmUpdate<T> setTimeout(int secs) {
/* 100 */     this.timeout = secs;
/* 101 */     return this;
/*     */   }
/*     */   
/*     */   public Class<?> getBeanType() {
/* 105 */     return this.beanType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTimeout() {
/* 112 */     return this.timeout;
/*     */   }
/*     */ 
/*     */   
/*     */   private SpiUpdate.OrmUpdateType deriveType(String updateStatement) {
/* 117 */     updateStatement = updateStatement.trim();
/* 118 */     int spacepos = updateStatement.indexOf(' ');
/* 119 */     if (spacepos == -1) {
/* 120 */       return SpiUpdate.OrmUpdateType.UNKNOWN;
/*     */     }
/*     */     
/* 123 */     String firstWord = updateStatement.substring(0, spacepos);
/* 124 */     if (firstWord.equalsIgnoreCase("update")) {
/* 125 */       return SpiUpdate.OrmUpdateType.UPDATE;
/*     */     }
/* 127 */     if (firstWord.equalsIgnoreCase("insert")) {
/* 128 */       return SpiUpdate.OrmUpdateType.INSERT;
/*     */     }
/* 130 */     if (firstWord.equalsIgnoreCase("delete")) {
/* 131 */       return SpiUpdate.OrmUpdateType.DELETE;
/*     */     }
/* 133 */     return SpiUpdate.OrmUpdateType.UNKNOWN;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int execute() {
/* 139 */     return this.server.execute((Update)this);
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
/*     */   
/*     */   public DefaultOrmUpdate<T> setNotifyCache(boolean notifyCache) {
/* 152 */     this.notifyCache = notifyCache;
/* 153 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNotifyCache() {
/* 161 */     return this.notifyCache;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 165 */     return this.name;
/*     */   }
/*     */   
/*     */   public String getUpdateStatement() {
/* 169 */     return this.updateStatement;
/*     */   }
/*     */   
/*     */   public DefaultOrmUpdate<T> set(int position, Object value) {
/* 173 */     this.bindParams.setParameter(position, value);
/* 174 */     return this;
/*     */   }
/*     */   
/*     */   public DefaultOrmUpdate<T> setParameter(int position, Object value) {
/* 178 */     this.bindParams.setParameter(position, value);
/* 179 */     return this;
/*     */   }
/*     */   
/*     */   public DefaultOrmUpdate<T> setNull(int position, int jdbcType) {
/* 183 */     this.bindParams.setNullParameter(position, jdbcType);
/* 184 */     return this;
/*     */   }
/*     */   
/*     */   public DefaultOrmUpdate<T> setNullParameter(int position, int jdbcType) {
/* 188 */     this.bindParams.setNullParameter(position, jdbcType);
/* 189 */     return this;
/*     */   }
/*     */   
/*     */   public DefaultOrmUpdate<T> set(String name, Object value) {
/* 193 */     this.bindParams.setParameter(name, value);
/* 194 */     return this;
/*     */   }
/*     */   
/*     */   public DefaultOrmUpdate<T> setParameter(String name, Object param) {
/* 198 */     this.bindParams.setParameter(name, param);
/* 199 */     return this;
/*     */   }
/*     */   
/*     */   public DefaultOrmUpdate<T> setNull(String name, int jdbcType) {
/* 203 */     this.bindParams.setNullParameter(name, jdbcType);
/* 204 */     return this;
/*     */   }
/*     */   
/*     */   public DefaultOrmUpdate<T> setNullParameter(String name, int jdbcType) {
/* 208 */     this.bindParams.setNullParameter(name, jdbcType);
/* 209 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BindParams getBindParams() {
/* 216 */     return this.bindParams;
/*     */   }
/*     */   
/*     */   public String getGeneratedSql() {
/* 220 */     return this.generatedSql;
/*     */   }
/*     */   
/*     */   public void setGeneratedSql(String generatedSql) {
/* 224 */     this.generatedSql = generatedSql;
/*     */   }
/*     */   
/*     */   public String getBaseTable() {
/* 228 */     return this.baseTable;
/*     */   }
/*     */   
/*     */   public SpiUpdate.OrmUpdateType getOrmUpdateType() {
/* 232 */     return this.type;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\querydefn\DefaultOrmUpdate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
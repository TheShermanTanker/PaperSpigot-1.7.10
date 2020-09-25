/*     */ package com.avaje.ebeaninternal.server.core;
/*     */ 
/*     */ import com.avaje.ebean.Ebean;
/*     */ import com.avaje.ebean.EbeanServer;
/*     */ import com.avaje.ebean.SqlUpdate;
/*     */ import com.avaje.ebeaninternal.api.BindParams;
/*     */ import com.avaje.ebeaninternal.api.SpiSqlUpdate;
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
/*     */ public final class DefaultSqlUpdate
/*     */   implements Serializable, SpiSqlUpdate
/*     */ {
/*     */   private static final long serialVersionUID = -6493829438421253102L;
/*     */   private final transient EbeanServer server;
/*     */   private final BindParams bindParams;
/*     */   private final String sql;
/* 118 */   private String label = "";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int timeout;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isAutoTableMod = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int addPos;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultSqlUpdate(EbeanServer server, String sql, BindParams bindParams) {
/* 145 */     this.server = server;
/* 146 */     this.sql = sql;
/* 147 */     this.bindParams = bindParams;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultSqlUpdate(EbeanServer server, String sql) {
/* 155 */     this(server, sql, new BindParams());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultSqlUpdate(String sql) {
/* 162 */     this(null, sql, new BindParams());
/*     */   }
/*     */   
/*     */   public int execute() {
/* 166 */     if (this.server != null) {
/* 167 */       return this.server.execute((SqlUpdate)this);
/*     */     }
/*     */     
/* 170 */     return Ebean.execute((SqlUpdate)this);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAutoTableMod() {
/* 175 */     return this.isAutoTableMod;
/*     */   }
/*     */   
/*     */   public SqlUpdate setAutoTableMod(boolean isAutoTableMod) {
/* 179 */     this.isAutoTableMod = isAutoTableMod;
/* 180 */     return (SqlUpdate)this;
/*     */   }
/*     */   
/*     */   public String getLabel() {
/* 184 */     return this.label;
/*     */   }
/*     */   
/*     */   public SqlUpdate setLabel(String label) {
/* 188 */     this.label = label;
/* 189 */     return (SqlUpdate)this;
/*     */   }
/*     */   
/*     */   public String getSql() {
/* 193 */     return this.sql;
/*     */   }
/*     */   
/*     */   public int getTimeout() {
/* 197 */     return this.timeout;
/*     */   }
/*     */   
/*     */   public SqlUpdate setTimeout(int secs) {
/* 201 */     this.timeout = secs;
/* 202 */     return (SqlUpdate)this;
/*     */   }
/*     */   
/*     */   public SqlUpdate addParameter(Object value) {
/* 206 */     return setParameter(++this.addPos, value);
/*     */   }
/*     */   
/*     */   public SqlUpdate setParameter(int position, Object value) {
/* 210 */     this.bindParams.setParameter(position, value);
/* 211 */     return (SqlUpdate)this;
/*     */   }
/*     */   
/*     */   public SqlUpdate setNull(int position, int jdbcType) {
/* 215 */     this.bindParams.setNullParameter(position, jdbcType);
/* 216 */     return (SqlUpdate)this;
/*     */   }
/*     */   
/*     */   public SqlUpdate setNullParameter(int position, int jdbcType) {
/* 220 */     this.bindParams.setNullParameter(position, jdbcType);
/* 221 */     return (SqlUpdate)this;
/*     */   }
/*     */   
/*     */   public SqlUpdate setParameter(String name, Object param) {
/* 225 */     this.bindParams.setParameter(name, param);
/* 226 */     return (SqlUpdate)this;
/*     */   }
/*     */   
/*     */   public SqlUpdate setNull(String name, int jdbcType) {
/* 230 */     this.bindParams.setNullParameter(name, jdbcType);
/* 231 */     return (SqlUpdate)this;
/*     */   }
/*     */   
/*     */   public SqlUpdate setNullParameter(String name, int jdbcType) {
/* 235 */     this.bindParams.setNullParameter(name, jdbcType);
/* 236 */     return (SqlUpdate)this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BindParams getBindParams() {
/* 243 */     return this.bindParams;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\core\DefaultSqlUpdate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
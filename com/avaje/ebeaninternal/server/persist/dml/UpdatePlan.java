/*     */ package com.avaje.ebeaninternal.server.persist.dml;
/*     */ 
/*     */ import com.avaje.ebeaninternal.api.SpiUpdatePlan;
/*     */ import com.avaje.ebeaninternal.server.core.ConcurrencyMode;
/*     */ import com.avaje.ebeaninternal.server.persist.dmlbind.Bindable;
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
/*     */ public class UpdatePlan
/*     */   implements SpiUpdatePlan
/*     */ {
/*  42 */   public static final UpdatePlan EMPTY_SET_CLAUSE = new UpdatePlan();
/*     */ 
/*     */   
/*     */   private final Integer key;
/*     */ 
/*     */   
/*     */   private final ConcurrencyMode mode;
/*     */ 
/*     */   
/*     */   private final String sql;
/*     */   
/*     */   private final Bindable set;
/*     */   
/*     */   private final Set<String> properties;
/*     */   
/*     */   private final boolean checkIncludes;
/*     */   
/*     */   private final long timeCreated;
/*     */   
/*     */   private final boolean emptySetClause;
/*     */   
/*     */   private Long timeLastUsed;
/*     */ 
/*     */   
/*     */   public UpdatePlan(ConcurrencyMode mode, String sql, Bindable set) {
/*  67 */     this(null, mode, sql, set, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UpdatePlan(Integer key, ConcurrencyMode mode, String sql, Bindable set, Set<String> properties) {
/*  76 */     this.emptySetClause = false;
/*  77 */     this.key = key;
/*  78 */     this.mode = mode;
/*  79 */     this.sql = sql;
/*  80 */     this.set = set;
/*  81 */     this.properties = properties;
/*  82 */     this.checkIncludes = (properties != null);
/*  83 */     this.timeCreated = System.currentTimeMillis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private UpdatePlan() {
/*  90 */     this.emptySetClause = true;
/*  91 */     this.key = Integer.valueOf(0);
/*  92 */     this.mode = ConcurrencyMode.NONE;
/*  93 */     this.sql = null;
/*  94 */     this.set = null;
/*  95 */     this.properties = null;
/*  96 */     this.checkIncludes = false;
/*  97 */     this.timeCreated = 0L;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmptySetClause() {
/* 102 */     return this.emptySetClause;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void bindSet(DmlHandler bind, Object bean) throws SQLException {
/* 110 */     this.set.dmlBind(bind, this.checkIncludes, bean);
/*     */ 
/*     */     
/* 113 */     Long touched = Long.valueOf(System.currentTimeMillis());
/* 114 */     this.timeLastUsed = touched;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTimeCreated() {
/* 121 */     return this.timeCreated;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Long getTimeLastUsed() {
/* 130 */     return this.timeLastUsed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getKey() {
/* 137 */     return this.key;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ConcurrencyMode getMode() {
/* 144 */     return this.mode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSql() {
/* 151 */     return this.sql;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Bindable getSet() {
/* 158 */     return this.set;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getProperties() {
/* 169 */     return this.properties;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\persist\dml\UpdatePlan.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
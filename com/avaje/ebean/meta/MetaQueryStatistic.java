/*   1 */ package com.avaje.ebean.meta;@Entity public class MetaQueryStatistic implements Serializable, EntityBean { private static final long serialVersionUID = -8746524372894472583L; boolean autofetchTuned; String beanType; int origQueryPlanHash; int finalQueryPlanHash; String sql; int executionCount; int totalLoadedBeans; int totalTimeMicros; long collectionStart; long lastQueryTime; int avgTimeMicros; int avgLoadedBeans; private static String _EBEAN_MARKER = "com.avaje.ebean.meta.MetaQueryStatistic"; protected EntityBeanIntercept _ebean_intercept; protected transient Object _ebean_identity; public String _ebean_getMarker() { return _EBEAN_MARKER; } public EntityBeanIntercept _ebean_getIntercept() { return this._ebean_intercept; } public EntityBeanIntercept _ebean_intercept() { if (this._ebean_intercept == null)
/*   2 */       this._ebean_intercept = new EntityBeanIntercept(this); 
/*   3 */     return this._ebean_intercept; } public void addPropertyChangeListener(PropertyChangeListener listener) { this._ebean_intercept.addPropertyChangeListener(listener); } public void addPropertyChangeListener(String name, PropertyChangeListener listener) { this._ebean_intercept.addPropertyChangeListener(name, listener); } public void removePropertyChangeListener(PropertyChangeListener listener) { this._ebean_intercept.removePropertyChangeListener(listener); } public void removePropertyChangeListener(String name, PropertyChangeListener listener) { this._ebean_intercept.removePropertyChangeListener(name, listener); } protected void _ebean_set_autofetchTuned(boolean newValue) { PropertyChangeEvent evt = this._ebean_intercept.preSetter(true, "autofetchTuned", _ebean_get_autofetchTuned(), newValue); this.autofetchTuned = newValue; this._ebean_intercept.postSetter(evt); } protected boolean _ebean_getni_autofetchTuned() { return this.autofetchTuned; } protected void _ebean_setni_autofetchTuned(boolean _newValue) { this.autofetchTuned = _newValue; } protected void _ebean_set_beanType(String newValue) { PropertyChangeEvent evt = this._ebean_intercept.preSetter(true, "beanType", _ebean_get_beanType(), newValue); this.beanType = newValue; this._ebean_intercept.postSetter(evt); } protected String _ebean_getni_beanType() { return this.beanType; } protected void _ebean_setni_beanType(String _newValue) { this.beanType = _newValue; } protected void _ebean_set_origQueryPlanHash(int newValue) { PropertyChangeEvent evt = this._ebean_intercept.preSetter(true, "origQueryPlanHash", _ebean_get_origQueryPlanHash(), newValue); this.origQueryPlanHash = newValue; this._ebean_intercept.postSetter(evt); } protected int _ebean_getni_origQueryPlanHash() { return this.origQueryPlanHash; } protected void _ebean_setni_origQueryPlanHash(int _newValue) { this.origQueryPlanHash = _newValue; } protected void _ebean_set_finalQueryPlanHash(int newValue) { PropertyChangeEvent evt = this._ebean_intercept.preSetter(true, "finalQueryPlanHash", _ebean_get_finalQueryPlanHash(), newValue); this.finalQueryPlanHash = newValue; this._ebean_intercept.postSetter(evt); } protected int _ebean_getni_finalQueryPlanHash() { return this.finalQueryPlanHash; } protected void _ebean_setni_finalQueryPlanHash(int _newValue) { this.finalQueryPlanHash = _newValue; } protected void _ebean_set_sql(String newValue) { PropertyChangeEvent evt = this._ebean_intercept.preSetter(true, "sql", _ebean_get_sql(), newValue); this.sql = newValue; this._ebean_intercept.postSetter(evt); } protected String _ebean_getni_sql() { return this.sql; } protected void _ebean_setni_sql(String _newValue) { this.sql = _newValue; } protected void _ebean_set_executionCount(int newValue) { PropertyChangeEvent evt = this._ebean_intercept.preSetter(true, "executionCount", _ebean_get_executionCount(), newValue); this.executionCount = newValue; this._ebean_intercept.postSetter(evt); } protected int _ebean_getni_executionCount() { return this.executionCount; } protected void _ebean_setni_executionCount(int _newValue) { this.executionCount = _newValue; } protected void _ebean_set_totalLoadedBeans(int newValue) { PropertyChangeEvent evt = this._ebean_intercept.preSetter(true, "totalLoadedBeans", _ebean_get_totalLoadedBeans(), newValue); this.totalLoadedBeans = newValue; this._ebean_intercept.postSetter(evt); } protected int _ebean_getni_totalLoadedBeans() { return this.totalLoadedBeans; } protected void _ebean_setni_totalLoadedBeans(int _newValue) { this.totalLoadedBeans = _newValue; } protected void _ebean_set_totalTimeMicros(int newValue) { PropertyChangeEvent evt = this._ebean_intercept.preSetter(true, "totalTimeMicros", _ebean_get_totalTimeMicros(), newValue); this.totalTimeMicros = newValue; this._ebean_intercept.postSetter(evt); } protected int _ebean_getni_totalTimeMicros() { return this.totalTimeMicros; } protected void _ebean_setni_totalTimeMicros(int _newValue) { this.totalTimeMicros = _newValue; } protected void _ebean_set_collectionStart(long newValue) { PropertyChangeEvent propertyChangeEvent = this._ebean_intercept.preSetter(true, "collectionStart", _ebean_get_collectionStart(), newValue); this.collectionStart = newValue; this._ebean_intercept.postSetter(propertyChangeEvent); } protected long _ebean_getni_collectionStart() { return this.collectionStart; } protected void _ebean_setni_collectionStart(long _newValue) { this.collectionStart = _newValue; } protected void _ebean_set_lastQueryTime(long newValue) { PropertyChangeEvent propertyChangeEvent = this._ebean_intercept.preSetter(true, "lastQueryTime", _ebean_get_lastQueryTime(), newValue); this.lastQueryTime = newValue; this._ebean_intercept.postSetter(propertyChangeEvent); } protected long _ebean_getni_lastQueryTime() { return this.lastQueryTime; } protected void _ebean_setni_lastQueryTime(long _newValue) { this.lastQueryTime = _newValue; } protected void _ebean_set_avgTimeMicros(int newValue) { PropertyChangeEvent evt = this._ebean_intercept.preSetter(true, "avgTimeMicros", _ebean_get_avgTimeMicros(), newValue); this.avgTimeMicros = newValue; this._ebean_intercept.postSetter(evt); } protected int _ebean_getni_avgTimeMicros() { return this.avgTimeMicros; } protected void _ebean_setni_avgTimeMicros(int _newValue) { this.avgTimeMicros = _newValue; } protected void _ebean_set_avgLoadedBeans(int newValue) { PropertyChangeEvent evt = this._ebean_intercept.preSetter(true, "avgLoadedBeans", _ebean_get_avgLoadedBeans(), newValue); this.avgLoadedBeans = newValue; this._ebean_intercept.postSetter(evt); }
/*   4 */   protected int _ebean_getni_avgLoadedBeans() { return this.avgLoadedBeans; } protected void _ebean_setni_avgLoadedBeans(int _newValue) { this.avgLoadedBeans = _newValue; } public Object _ebean_createCopy() { MetaQueryStatistic p = new MetaQueryStatistic(); p.autofetchTuned = this.autofetchTuned; p.beanType = this.beanType; p.origQueryPlanHash = this.origQueryPlanHash; p.finalQueryPlanHash = this.finalQueryPlanHash; p.sql = this.sql; p.executionCount = this.executionCount; p.totalLoadedBeans = this.totalLoadedBeans; p.totalTimeMicros = this.totalTimeMicros; p.collectionStart = this.collectionStart; p.lastQueryTime = this.lastQueryTime; p.avgTimeMicros = this.avgTimeMicros; p.avgLoadedBeans = this.avgLoadedBeans; return p; } public Object _ebean_getField(int index, Object o) { MetaQueryStatistic p = (MetaQueryStatistic)o; switch (index) { case 0: return Boolean.valueOf(p.autofetchTuned);case 1: return p.beanType;case 2: return Integer.valueOf(p.origQueryPlanHash);case 3: return Integer.valueOf(p.finalQueryPlanHash);case 4: return p.sql;case 5: return Integer.valueOf(p.executionCount);case 6: return Integer.valueOf(p.totalLoadedBeans);case 7: return Integer.valueOf(p.totalTimeMicros);case 8: return Long.valueOf(p.collectionStart);case 9: return Long.valueOf(p.lastQueryTime);case 10: return Integer.valueOf(p.avgTimeMicros);case 11: return Integer.valueOf(p.avgLoadedBeans); }  throw new RuntimeException("Invalid index " + index); } public Object _ebean_getFieldIntercept(int index, Object o) { MetaQueryStatistic p = (MetaQueryStatistic)o; switch (index) { case 0: return Boolean.valueOf(p._ebean_get_autofetchTuned());case 1: return p._ebean_get_beanType();case 2: return Integer.valueOf(p._ebean_get_origQueryPlanHash());case 3: return Integer.valueOf(p._ebean_get_finalQueryPlanHash());case 4: return p._ebean_get_sql();case 5: return Integer.valueOf(p._ebean_get_executionCount());case 6: return Integer.valueOf(p._ebean_get_totalLoadedBeans());case 7: return Integer.valueOf(p._ebean_get_totalTimeMicros());case 8: return Long.valueOf(p._ebean_get_collectionStart());case 9: return Long.valueOf(p._ebean_get_lastQueryTime());case 10: return Integer.valueOf(p._ebean_get_avgTimeMicros());case 11: return Integer.valueOf(p._ebean_get_avgLoadedBeans()); }  throw new RuntimeException("Invalid index " + index); } protected boolean _ebean_get_autofetchTuned() { this._ebean_intercept.preGetter("autofetchTuned");
/*   5 */     return this.autofetchTuned; } public void _ebean_setField(int index, Object o, Object arg) { MetaQueryStatistic p = (MetaQueryStatistic)o; switch (index) { case 0: p.autofetchTuned = ((Boolean)arg).booleanValue(); return;case 1: p.beanType = (String)arg; return;case 2: p.origQueryPlanHash = ((Integer)arg).intValue(); return;case 3: p.finalQueryPlanHash = ((Integer)arg).intValue(); return;case 4: p.sql = (String)arg; return;case 5: p.executionCount = ((Integer)arg).intValue(); return;case 6: p.totalLoadedBeans = ((Integer)arg).intValue(); return;case 7: p.totalTimeMicros = ((Integer)arg).intValue(); return;case 8: p.collectionStart = ((Long)arg).longValue(); return;case 9: p.lastQueryTime = ((Long)arg).longValue(); return;case 10: p.avgTimeMicros = ((Integer)arg).intValue(); return;case 11: p.avgLoadedBeans = ((Integer)arg).intValue(); return; }  throw new RuntimeException("Invalid index " + index); } public void _ebean_setFieldIntercept(int index, Object o, Object arg) { MetaQueryStatistic p = (MetaQueryStatistic)o; switch (index) { case 0: p._ebean_set_autofetchTuned(((Boolean)arg).booleanValue()); return;case 1: p._ebean_set_beanType((String)arg); return;case 2: p._ebean_set_origQueryPlanHash(((Integer)arg).intValue()); return;case 3: p._ebean_set_finalQueryPlanHash(((Integer)arg).intValue()); return;case 4: p._ebean_set_sql((String)arg); return;case 5: p._ebean_set_executionCount(((Integer)arg).intValue()); return;case 6: p._ebean_set_totalLoadedBeans(((Integer)arg).intValue()); return;case 7: p._ebean_set_totalTimeMicros(((Integer)arg).intValue()); return;case 8: p._ebean_set_collectionStart(((Long)arg).longValue()); return;case 9: p._ebean_set_lastQueryTime(((Long)arg).longValue()); return;case 10: p._ebean_set_avgTimeMicros(((Integer)arg).intValue()); return;case 11: p._ebean_set_avgLoadedBeans(((Integer)arg).intValue()); return; }  throw new RuntimeException("Invalid index " + index); } public String[] _ebean_getFieldNames() { return new String[] { "autofetchTuned", "beanType", "origQueryPlanHash", "finalQueryPlanHash", "sql", "executionCount", "totalLoadedBeans", "totalTimeMicros", "collectionStart", "lastQueryTime", "avgTimeMicros", "avgLoadedBeans" }; } public void _ebean_setEmbeddedLoaded() {} public boolean _ebean_isEmbeddedNewOrDirty() { return false; } protected String _ebean_get_beanType() { this._ebean_intercept.preGetter("beanType"); return this.beanType; } protected int _ebean_get_origQueryPlanHash() { this._ebean_intercept.preGetter("origQueryPlanHash"); return this.origQueryPlanHash; } protected int _ebean_get_finalQueryPlanHash() { this._ebean_intercept.preGetter("finalQueryPlanHash"); return this.finalQueryPlanHash; } protected String _ebean_get_sql() { this._ebean_intercept.preGetter("sql"); return this.sql; } protected int _ebean_get_executionCount() { this._ebean_intercept.preGetter("executionCount"); return this.executionCount; } protected int _ebean_get_totalLoadedBeans() { this._ebean_intercept.preGetter("totalLoadedBeans"); return this.totalLoadedBeans; } protected int _ebean_get_totalTimeMicros() { this._ebean_intercept.preGetter("totalTimeMicros"); return this.totalTimeMicros; } protected long _ebean_get_collectionStart() { this._ebean_intercept.preGetter("collectionStart"); return this.collectionStart; } protected long _ebean_get_lastQueryTime() { this._ebean_intercept.preGetter("lastQueryTime"); return this.lastQueryTime; } protected int _ebean_get_avgTimeMicros() { this._ebean_intercept.preGetter("avgTimeMicros"); return this.avgTimeMicros; } protected int _ebean_get_avgLoadedBeans() { this._ebean_intercept.preGetter("avgLoadedBeans"); return this.avgLoadedBeans; }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object _ebean_newInstance() {
/*  10 */     return new MetaQueryStatistic();
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
/*     */   public MetaQueryStatistic() {
/*  45 */     this._ebean_intercept = new EntityBeanIntercept(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MetaQueryStatistic(boolean autofetchTuned, String beanType, int plan, String sql, int executionCount, int totalLoadedBeans, int totalTimeMicros, long collectionStart, long lastQueryTime) {
/*  53 */     this._ebean_intercept = new EntityBeanIntercept(this);
/*     */     
/*  55 */     this.autofetchTuned = autofetchTuned;
/*  56 */     this.beanType = beanType;
/*  57 */     this.finalQueryPlanHash = plan;
/*  58 */     this.sql = sql;
/*  59 */     this.executionCount = executionCount;
/*  60 */     this.totalLoadedBeans = totalLoadedBeans;
/*  61 */     this.totalTimeMicros = totalTimeMicros;
/*  62 */     this.collectionStart = collectionStart;
/*     */     
/*  64 */     this.lastQueryTime = lastQueryTime;
/*  65 */     this.avgTimeMicros = (executionCount == 0) ? 0 : (totalTimeMicros / executionCount);
/*  66 */     this.avgLoadedBeans = (executionCount == 0) ? 0 : (totalLoadedBeans / executionCount);
/*     */   }
/*     */   
/*     */   public String toString() {
/*  70 */     return "type=" + this.beanType + " tuned:" + this.autofetchTuned + " origHash=" + this.origQueryPlanHash + " count=" + this.executionCount + " avgMicros=" + getAvgTimeMicros();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAutofetchTuned() {
/*  77 */     return _ebean_get_autofetchTuned();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOrigQueryPlanHash() {
/*  87 */     return _ebean_get_origQueryPlanHash();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFinalQueryPlanHash() {
/*  94 */     return _ebean_get_finalQueryPlanHash();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBeanType() {
/* 101 */     return _ebean_get_beanType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSql() {
/* 108 */     return _ebean_get_sql();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getExecutionCount() {
/* 115 */     return _ebean_get_executionCount();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTotalLoadedBeans() {
/* 125 */     return _ebean_get_totalLoadedBeans();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTotalTimeMicros() {
/* 132 */     return _ebean_get_totalTimeMicros();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getCollectionStart() {
/* 139 */     return _ebean_get_collectionStart();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getLastQueryTime() {
/* 146 */     return _ebean_get_lastQueryTime();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAvgTimeMicros() {
/* 156 */     return _ebean_get_avgTimeMicros();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAvgLoadedBeans() {
/* 166 */     return _ebean_get_avgLoadedBeans();
/*     */   } }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\meta\MetaQueryStatistic.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
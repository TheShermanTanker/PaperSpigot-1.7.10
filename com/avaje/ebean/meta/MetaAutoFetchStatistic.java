/*   1 */ package com.avaje.ebean.meta;@Entity public class MetaAutoFetchStatistic implements Serializable, EntityBean { private static final long serialVersionUID = -6640406753257176803L; @Id private String id; private ObjectGraphOrigin origin; private String beanType; private int counter; @Transient private List<QueryStats> queryStats; @Transient private List<NodeUsageStats> nodeUsageStats; private static String _EBEAN_MARKER = "com.avaje.ebean.meta.MetaAutoFetchStatistic"; protected EntityBeanIntercept _ebean_intercept; protected transient Object _ebean_identity; public String _ebean_getMarker() { return _EBEAN_MARKER; } public EntityBeanIntercept _ebean_getIntercept() { return this._ebean_intercept; } public EntityBeanIntercept _ebean_intercept() { if (this._ebean_intercept == null)
/*   2 */       this._ebean_intercept = new EntityBeanIntercept(this); 
/*   3 */     return this._ebean_intercept; } public void addPropertyChangeListener(PropertyChangeListener listener) { this._ebean_intercept.addPropertyChangeListener(listener); } public void addPropertyChangeListener(String name, PropertyChangeListener listener) { this._ebean_intercept.addPropertyChangeListener(name, listener); } public void removePropertyChangeListener(PropertyChangeListener listener) { this._ebean_intercept.removePropertyChangeListener(listener); } public void removePropertyChangeListener(String name, PropertyChangeListener listener) { this._ebean_intercept.removePropertyChangeListener(name, listener); } protected void _ebean_set_id(String newValue) { PropertyChangeEvent evt = this._ebean_intercept.preSetter(false, "id", _ebean_get_id(), newValue); this.id = newValue; this._ebean_intercept.postSetter(evt); } protected String _ebean_getni_id() { return this.id; } protected void _ebean_setni_id(String _newValue) { this.id = _newValue; } protected void _ebean_set_origin(ObjectGraphOrigin newValue) { PropertyChangeEvent evt = this._ebean_intercept.preSetter(true, "origin", _ebean_get_origin(), newValue); this.origin = newValue; this._ebean_intercept.postSetter(evt); } protected ObjectGraphOrigin _ebean_getni_origin() { return this.origin; } protected void _ebean_setni_origin(ObjectGraphOrigin _newValue) { this.origin = _newValue; } protected void _ebean_set_beanType(String newValue) { PropertyChangeEvent evt = this._ebean_intercept.preSetter(true, "beanType", _ebean_get_beanType(), newValue); this.beanType = newValue; this._ebean_intercept.postSetter(evt); } protected String _ebean_getni_beanType() { return this.beanType; } protected void _ebean_setni_beanType(String _newValue) { this.beanType = _newValue; } protected void _ebean_set_counter(int newValue) { PropertyChangeEvent evt = this._ebean_intercept.preSetter(true, "counter", _ebean_get_counter(), newValue); this.counter = newValue; this._ebean_intercept.postSetter(evt); } protected int _ebean_getni_counter() { return this.counter; } protected void _ebean_setni_counter(int _newValue) { this.counter = _newValue; } protected void _ebean_set_queryStats(List<QueryStats> newValue) { PropertyChangeEvent evt = this._ebean_intercept.preSetter(false, "queryStats", _ebean_get_queryStats(), newValue); this.queryStats = newValue; this._ebean_intercept.postSetter(evt); } protected List _ebean_getni_queryStats() { return this.queryStats; } protected void _ebean_setni_queryStats(List<QueryStats> _newValue) { this.queryStats = _newValue; } protected List _ebean_getni_nodeUsageStats() { return this.nodeUsageStats; } protected void _ebean_setni_nodeUsageStats(List<NodeUsageStats> _newValue) { this.nodeUsageStats = _newValue; } public Object _ebean_createCopy() { MetaAutoFetchStatistic p = new MetaAutoFetchStatistic(); p.id = this.id; p.origin = this.origin; p.beanType = this.beanType; p.counter = this.counter; return p; } public Object _ebean_getField(int index, Object o) { MetaAutoFetchStatistic p = (MetaAutoFetchStatistic)o; switch (index) { case 0: return p.id;case 1: return p.origin;case 2: return p.beanType;case 3: return Integer.valueOf(p.counter);case 4: return p.queryStats;case 5: return p.nodeUsageStats; }  throw new RuntimeException("Invalid index " + index); } public Object _ebean_getFieldIntercept(int index, Object o) { MetaAutoFetchStatistic p = (MetaAutoFetchStatistic)o; switch (index) { case 0: return p._ebean_get_id();case 1: return p._ebean_get_origin();case 2: return p._ebean_get_beanType();case 3: return Integer.valueOf(p._ebean_get_counter());case 4: return p._ebean_get_queryStats();case 5: return p._ebean_get_nodeUsageStats(); }  throw new RuntimeException("Invalid index " + index); } protected void _ebean_set_nodeUsageStats(List<NodeUsageStats> newValue) { PropertyChangeEvent evt = this._ebean_intercept.preSetter(false, "nodeUsageStats", _ebean_get_nodeUsageStats(), newValue); this.nodeUsageStats = newValue; this._ebean_intercept.postSetter(evt); } public void _ebean_setField(int index, Object o, Object arg) { MetaAutoFetchStatistic p = (MetaAutoFetchStatistic)o; switch (index) { case 0: p.id = (String)arg; return;case 1: p.origin = (ObjectGraphOrigin)arg; return;case 2: p.beanType = (String)arg; return;case 3: p.counter = ((Integer)arg).intValue(); return;case 4: p.queryStats = (List<QueryStats>)arg; return;case 5: p.nodeUsageStats = (List<NodeUsageStats>)arg; return; }  throw new RuntimeException("Invalid index " + index); } public void _ebean_setFieldIntercept(int index, Object o, Object arg) { MetaAutoFetchStatistic p = (MetaAutoFetchStatistic)o; switch (index) { case 0: p._ebean_set_id((String)arg); return;case 1: p._ebean_set_origin((ObjectGraphOrigin)arg); return;case 2: p._ebean_set_beanType((String)arg); return;case 3: p._ebean_set_counter(((Integer)arg).intValue()); return;case 4: p._ebean_set_queryStats((List)arg); return;case 5: p._ebean_set_nodeUsageStats((List)arg); return; }  throw new RuntimeException("Invalid index " + index); } public String[] _ebean_getFieldNames() { return new String[] { "id", "origin", "beanType", "counter", "queryStats", "nodeUsageStats" }; } private Object _ebean_getIdentity() { synchronized (this) { if (this._ebean_identity != null) return this._ebean_identity;  Object tmpId = _ebean_getField(0, this); if (tmpId != null) { this._ebean_identity = tmpId; } else { this._ebean_identity = new Object(); }  return this._ebean_identity; }  } public boolean equals(Object obj) { if (obj == null) return false;  if (!getClass().equals(obj.getClass()))
/*   4 */       return false; 
/*   5 */     if (obj == this)
/*   6 */       return true; 
/*   7 */     return _ebean_getIdentity().equals(((MetaAutoFetchStatistic)obj)._ebean_getIdentity()); } public int hashCode() { return _ebean_getIdentity().hashCode(); } public void _ebean_setEmbeddedLoaded() {} public boolean _ebean_isEmbeddedNewOrDirty() { return false; } protected ObjectGraphOrigin _ebean_get_origin() { this._ebean_intercept.preGetter("origin"); return this.origin; } protected String _ebean_get_beanType() { this._ebean_intercept.preGetter("beanType"); return this.beanType; } protected int _ebean_get_counter() { this._ebean_intercept.preGetter("counter"); return this.counter; } protected String _ebean_get_id() { return this.id; }
/*     */   protected List _ebean_get_queryStats() { return this.queryStats; }
/*     */   protected List _ebean_get_nodeUsageStats() { return this.nodeUsageStats; }
/*  10 */   public Object _ebean_newInstance() { return new MetaAutoFetchStatistic(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MetaAutoFetchStatistic() {
/*  38 */     this._ebean_intercept = new EntityBeanIntercept(this);
/*     */   }
/*     */   public MetaAutoFetchStatistic(ObjectGraphOrigin origin, int counter, List<QueryStats> queryStats, List<NodeUsageStats> nodeUsageStats) {
/*  41 */     this._ebean_intercept = new EntityBeanIntercept(this);
/*     */     
/*  43 */     this.origin = origin;
/*  44 */     this.beanType = (origin == null) ? null : origin.getBeanType();
/*  45 */     this.id = (origin == null) ? null : origin.getKey();
/*  46 */     this.counter = counter;
/*  47 */     this.queryStats = queryStats;
/*  48 */     this.nodeUsageStats = nodeUsageStats;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getId() {
/*  55 */     return _ebean_get_id();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBeanType() {
/*  62 */     return _ebean_get_beanType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectGraphOrigin getOrigin() {
/*  69 */     return _ebean_get_origin();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCounter() {
/*  76 */     return _ebean_get_counter();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<QueryStats> getQueryStats() {
/*  83 */     return this.queryStats;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<NodeUsageStats> getNodeUsageStats() {
/*  90 */     return this.nodeUsageStats;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class QueryStats
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = -5517935732867671387L;
/*     */     
/*     */     private final String path;
/*     */     
/*     */     private final int exeCount;
/*     */     
/*     */     private final int totalBeanLoaded;
/*     */     
/*     */     private final int totalMicros;
/*     */ 
/*     */     
/*     */     public QueryStats(String path, int exeCount, int totalBeanLoaded, int totalMicros) {
/* 109 */       this.path = path;
/* 110 */       this.exeCount = exeCount;
/* 111 */       this.totalBeanLoaded = totalBeanLoaded;
/* 112 */       this.totalMicros = totalMicros;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getPath() {
/* 120 */       return this.path;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getExeCount() {
/* 127 */       return this.exeCount;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getTotalBeanLoaded() {
/* 134 */       return this.totalBeanLoaded;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getTotalMicros() {
/* 141 */       return this.totalMicros;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 145 */       long avgMicros = (this.exeCount == 0) ? 0L : (this.totalMicros / this.exeCount);
/*     */       
/* 147 */       return "queryExe path[" + this.path + "] count[" + this.exeCount + "] totalBeansLoaded[" + this.totalBeanLoaded + "] avgMicros[" + avgMicros + "] totalMicros[" + this.totalMicros + "]";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class NodeUsageStats
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1786787832374844739L;
/*     */ 
/*     */     
/*     */     private final String path;
/*     */     
/*     */     private final int profileCount;
/*     */     
/*     */     private final int profileUsedCount;
/*     */     
/*     */     private final String[] usedProperties;
/*     */ 
/*     */     
/*     */     public NodeUsageStats(String path, int profileCount, int profileUsedCount, String[] usedProperties) {
/* 169 */       this.path = (path == null) ? "" : path;
/* 170 */       this.profileCount = profileCount;
/* 171 */       this.profileUsedCount = profileUsedCount;
/* 172 */       this.usedProperties = usedProperties;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getPath() {
/* 180 */       return this.path;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getProfileCount() {
/* 187 */       return this.profileCount;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getProfileUsedCount() {
/* 199 */       return this.profileUsedCount;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String[] getUsedProperties() {
/* 206 */       return this.usedProperties;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Set<String> getUsedPropertiesSet() {
/* 213 */       LinkedHashSet<String> s = new LinkedHashSet<String>();
/* 214 */       for (int i = 0; i < this.usedProperties.length; i++) {
/* 215 */         s.add(this.usedProperties[i]);
/*     */       }
/* 217 */       return s;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 221 */       return "path[" + this.path + "] profileCount[" + this.profileCount + "] used[" + this.profileUsedCount + "] props" + Arrays.toString((Object[])this.usedProperties);
/*     */     }
/*     */   } }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\meta\MetaAutoFetchStatistic.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
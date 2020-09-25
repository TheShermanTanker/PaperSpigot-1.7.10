/*   1 */ package com.avaje.ebean.meta;@Entity public class MetaAutoFetchTunedQueryInfo implements Serializable, EntityBean { private static final long serialVersionUID = 3119991928889170215L; @Id private String id; private String beanType; private ObjectGraphOrigin origin; private String tunedDetail; private int profileCount; private int tunedCount; private long lastTuneTime; private static String _EBEAN_MARKER = "com.avaje.ebean.meta.MetaAutoFetchTunedQueryInfo"; protected EntityBeanIntercept _ebean_intercept; protected transient Object _ebean_identity; public String _ebean_getMarker() { return _EBEAN_MARKER; } public EntityBeanIntercept _ebean_getIntercept() { return this._ebean_intercept; } public EntityBeanIntercept _ebean_intercept() { if (this._ebean_intercept == null)
/*   2 */       this._ebean_intercept = new EntityBeanIntercept(this); 
/*   3 */     return this._ebean_intercept; } public void addPropertyChangeListener(PropertyChangeListener listener) { this._ebean_intercept.addPropertyChangeListener(listener); } public void addPropertyChangeListener(String name, PropertyChangeListener listener) { this._ebean_intercept.addPropertyChangeListener(name, listener); } public void removePropertyChangeListener(PropertyChangeListener listener) { this._ebean_intercept.removePropertyChangeListener(listener); } public void removePropertyChangeListener(String name, PropertyChangeListener listener) { this._ebean_intercept.removePropertyChangeListener(name, listener); } protected void _ebean_set_id(String newValue) { PropertyChangeEvent evt = this._ebean_intercept.preSetter(false, "id", _ebean_get_id(), newValue); this.id = newValue; this._ebean_intercept.postSetter(evt); } protected String _ebean_getni_id() { return this.id; } protected void _ebean_setni_id(String _newValue) { this.id = _newValue; } protected void _ebean_set_beanType(String newValue) { PropertyChangeEvent evt = this._ebean_intercept.preSetter(true, "beanType", _ebean_get_beanType(), newValue); this.beanType = newValue; this._ebean_intercept.postSetter(evt); } protected String _ebean_getni_beanType() { return this.beanType; } protected void _ebean_setni_beanType(String _newValue) { this.beanType = _newValue; } protected void _ebean_set_origin(ObjectGraphOrigin newValue) { PropertyChangeEvent evt = this._ebean_intercept.preSetter(true, "origin", _ebean_get_origin(), newValue); this.origin = newValue; this._ebean_intercept.postSetter(evt); } protected ObjectGraphOrigin _ebean_getni_origin() { return this.origin; } protected void _ebean_setni_origin(ObjectGraphOrigin _newValue) { this.origin = _newValue; } protected void _ebean_set_tunedDetail(String newValue) { PropertyChangeEvent evt = this._ebean_intercept.preSetter(true, "tunedDetail", _ebean_get_tunedDetail(), newValue); this.tunedDetail = newValue; this._ebean_intercept.postSetter(evt); } protected String _ebean_getni_tunedDetail() { return this.tunedDetail; } protected void _ebean_setni_tunedDetail(String _newValue) { this.tunedDetail = _newValue; } protected void _ebean_set_profileCount(int newValue) { PropertyChangeEvent evt = this._ebean_intercept.preSetter(true, "profileCount", _ebean_get_profileCount(), newValue); this.profileCount = newValue; this._ebean_intercept.postSetter(evt); } protected int _ebean_getni_profileCount() { return this.profileCount; } protected void _ebean_setni_profileCount(int _newValue) { this.profileCount = _newValue; } protected void _ebean_set_tunedCount(int newValue) { PropertyChangeEvent evt = this._ebean_intercept.preSetter(true, "tunedCount", _ebean_get_tunedCount(), newValue); this.tunedCount = newValue; this._ebean_intercept.postSetter(evt); } protected int _ebean_getni_tunedCount() { return this.tunedCount; } protected void _ebean_setni_tunedCount(int _newValue) { this.tunedCount = _newValue; } protected long _ebean_getni_lastTuneTime() { return this.lastTuneTime; } protected void _ebean_setni_lastTuneTime(long _newValue) { this.lastTuneTime = _newValue; } public Object _ebean_createCopy() { MetaAutoFetchTunedQueryInfo p = new MetaAutoFetchTunedQueryInfo(); p.id = this.id; p.beanType = this.beanType; p.origin = this.origin; p.tunedDetail = this.tunedDetail; p.profileCount = this.profileCount; p.tunedCount = this.tunedCount; p.lastTuneTime = this.lastTuneTime; return p; } public Object _ebean_getField(int index, Object o) { MetaAutoFetchTunedQueryInfo p = (MetaAutoFetchTunedQueryInfo)o; switch (index) { case 0: return p.id;case 1: return p.beanType;case 2: return p.origin;case 3: return p.tunedDetail;case 4: return Integer.valueOf(p.profileCount);case 5: return Integer.valueOf(p.tunedCount);case 6: return Long.valueOf(p.lastTuneTime); }  throw new RuntimeException("Invalid index " + index); } public Object _ebean_getFieldIntercept(int index, Object o) { MetaAutoFetchTunedQueryInfo p = (MetaAutoFetchTunedQueryInfo)o; switch (index) { case 0: return p._ebean_get_id();case 1: return p._ebean_get_beanType();case 2: return p._ebean_get_origin();case 3: return p._ebean_get_tunedDetail();case 4: return Integer.valueOf(p._ebean_get_profileCount());case 5: return Integer.valueOf(p._ebean_get_tunedCount());case 6: return Long.valueOf(p._ebean_get_lastTuneTime()); }  throw new RuntimeException("Invalid index " + index); } protected void _ebean_set_lastTuneTime(long newValue) { PropertyChangeEvent propertyChangeEvent = this._ebean_intercept.preSetter(true, "lastTuneTime", _ebean_get_lastTuneTime(), newValue); this.lastTuneTime = newValue; this._ebean_intercept.postSetter(propertyChangeEvent); } public void _ebean_setField(int index, Object o, Object arg) { MetaAutoFetchTunedQueryInfo p = (MetaAutoFetchTunedQueryInfo)o; switch (index) { case 0: p.id = (String)arg; return;case 1: p.beanType = (String)arg; return;case 2: p.origin = (ObjectGraphOrigin)arg; return;case 3: p.tunedDetail = (String)arg; return;case 4: p.profileCount = ((Integer)arg).intValue(); return;case 5: p.tunedCount = ((Integer)arg).intValue(); return;case 6: p.lastTuneTime = ((Long)arg).longValue(); return; }  throw new RuntimeException("Invalid index " + index); } public void _ebean_setFieldIntercept(int index, Object o, Object arg) { MetaAutoFetchTunedQueryInfo p = (MetaAutoFetchTunedQueryInfo)o; switch (index) { case 0: p._ebean_set_id((String)arg); return;case 1: p._ebean_set_beanType((String)arg); return;case 2: p._ebean_set_origin((ObjectGraphOrigin)arg); return;case 3: p._ebean_set_tunedDetail((String)arg); return;case 4: p._ebean_set_profileCount(((Integer)arg).intValue()); return;case 5: p._ebean_set_tunedCount(((Integer)arg).intValue()); return;case 6: p._ebean_set_lastTuneTime(((Long)arg).longValue()); return; }  throw new RuntimeException("Invalid index " + index); } public String[] _ebean_getFieldNames() { return new String[] { "id", "beanType", "origin", "tunedDetail", "profileCount", "tunedCount", "lastTuneTime" }; } private Object _ebean_getIdentity() { synchronized (this) { if (this._ebean_identity != null) return this._ebean_identity;  Object tmpId = _ebean_getField(0, this); if (tmpId != null) { this._ebean_identity = tmpId; } else { this._ebean_identity = new Object(); }  return this._ebean_identity; }  } public boolean equals(Object obj) { if (obj == null) return false;  if (!getClass().equals(obj.getClass()))
/*   4 */       return false; 
/*   5 */     if (obj == this)
/*   6 */       return true; 
/*   7 */     return _ebean_getIdentity().equals(((MetaAutoFetchTunedQueryInfo)obj)._ebean_getIdentity()); } public int hashCode() { return _ebean_getIdentity().hashCode(); } public void _ebean_setEmbeddedLoaded() {} public boolean _ebean_isEmbeddedNewOrDirty() { return false; } protected String _ebean_get_beanType() { this._ebean_intercept.preGetter("beanType"); return this.beanType; } protected ObjectGraphOrigin _ebean_get_origin() { this._ebean_intercept.preGetter("origin"); return this.origin; } protected String _ebean_get_tunedDetail() { this._ebean_intercept.preGetter("tunedDetail"); return this.tunedDetail; } protected int _ebean_get_profileCount() { this._ebean_intercept.preGetter("profileCount"); return this.profileCount; } protected int _ebean_get_tunedCount() { this._ebean_intercept.preGetter("tunedCount"); return this.tunedCount; }
/*     */   protected long _ebean_get_lastTuneTime() { this._ebean_intercept.preGetter("lastTuneTime"); return this.lastTuneTime; }
/*     */   protected String _ebean_get_id() { return this.id; }
/*  10 */   public Object _ebean_newInstance() { return new MetaAutoFetchTunedQueryInfo(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MetaAutoFetchTunedQueryInfo() {
/*  51 */     this._ebean_intercept = new EntityBeanIntercept(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public MetaAutoFetchTunedQueryInfo(ObjectGraphOrigin origin, String tunedDetail, int profileCount, int tunedCount, long lastTuneTime) {
/*  56 */     this._ebean_intercept = new EntityBeanIntercept(this);
/*     */     
/*  58 */     this.origin = origin;
/*  59 */     this.beanType = (origin == null) ? null : origin.getBeanType();
/*  60 */     this.id = (origin == null) ? null : origin.getKey();
/*  61 */     this.tunedDetail = tunedDetail;
/*  62 */     this.profileCount = profileCount;
/*  63 */     this.tunedCount = tunedCount;
/*  64 */     this.lastTuneTime = lastTuneTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getId() {
/*  71 */     return _ebean_get_id();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBeanType() {
/*  78 */     return _ebean_get_beanType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectGraphOrigin getOrigin() {
/*  85 */     return _ebean_get_origin();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTunedDetail() {
/*  92 */     return _ebean_get_tunedDetail();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getProfileCount() {
/*  99 */     return _ebean_get_profileCount();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTunedCount() {
/* 106 */     return _ebean_get_tunedCount();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getLastTuneTime() {
/* 113 */     return _ebean_get_lastTuneTime();
/*     */   }
/*     */   
/*     */   public String toString() {
/* 117 */     return "origin[" + this.origin + "] query[" + this.tunedDetail + "] profileCount[" + this.profileCount + "]";
/*     */   } }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\meta\MetaAutoFetchTunedQueryInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
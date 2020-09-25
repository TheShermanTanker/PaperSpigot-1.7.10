/*     */ package com.avaje.ebeaninternal.server.query;
/*     */ 
/*     */ import com.avaje.ebean.SqlRow;
/*     */ import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
/*     */ import java.math.BigDecimal;
/*     */ import java.sql.Date;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultSqlRow
/*     */   implements SqlRow
/*     */ {
/*     */   static final long serialVersionUID = -3120927797041336242L;
/*     */   private final String dbTrueValue;
/*     */   Map<String, Object> map;
/*     */   
/*     */   public DefaultSqlRow(Map<String, Object> map, String dbTrueValue) {
/*  69 */     this.map = map;
/*  70 */     this.dbTrueValue = dbTrueValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultSqlRow(String dbTrueValue) {
/*  78 */     this.map = new LinkedHashMap<String, Object>();
/*  79 */     this.dbTrueValue = dbTrueValue;
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
/*     */   public DefaultSqlRow(int initialCapacity, float loadFactor, String dbTrueValue) {
/*  93 */     this.map = new LinkedHashMap<String, Object>(initialCapacity, loadFactor);
/*  94 */     this.dbTrueValue = dbTrueValue;
/*     */   }
/*     */   
/*     */   public Iterator<String> keys() {
/*  98 */     return this.map.keySet().iterator();
/*     */   }
/*     */   
/*     */   public Object remove(Object name) {
/* 102 */     name = ((String)name).toLowerCase();
/* 103 */     return this.map.remove(name);
/*     */   }
/*     */   
/*     */   public Object get(Object name) {
/* 107 */     name = ((String)name).toLowerCase();
/* 108 */     return this.map.get(name);
/*     */   }
/*     */   
/*     */   public Object put(String name, Object value) {
/* 112 */     return setInternal(name, value);
/*     */   }
/*     */   
/*     */   public Object set(String name, Object value) {
/* 116 */     return setInternal(name, value);
/*     */   }
/*     */ 
/*     */   
/*     */   private Object setInternal(String name, Object newValue) {
/* 121 */     name = name.toLowerCase();
/*     */ 
/*     */     
/* 124 */     return this.map.put(name, newValue);
/*     */   }
/*     */   
/*     */   public UUID getUUID(String name) {
/* 128 */     Object val = get(name);
/* 129 */     return BasicTypeConverter.toUUID(val);
/*     */   }
/*     */   
/*     */   public Boolean getBoolean(String name) {
/* 133 */     Object val = get(name);
/* 134 */     return BasicTypeConverter.toBoolean(val, this.dbTrueValue);
/*     */   }
/*     */   
/*     */   public Integer getInteger(String name) {
/* 138 */     Object val = get(name);
/* 139 */     return BasicTypeConverter.toInteger(val);
/*     */   }
/*     */   
/*     */   public BigDecimal getBigDecimal(String name) {
/* 143 */     Object val = get(name);
/* 144 */     return BasicTypeConverter.toBigDecimal(val);
/*     */   }
/*     */   
/*     */   public Long getLong(String name) {
/* 148 */     Object val = get(name);
/* 149 */     return BasicTypeConverter.toLong(val);
/*     */   }
/*     */   
/*     */   public Double getDouble(String name) {
/* 153 */     Object val = get(name);
/* 154 */     return BasicTypeConverter.toDouble(val);
/*     */   }
/*     */   
/*     */   public Float getFloat(String name) {
/* 158 */     Object val = get(name);
/* 159 */     return BasicTypeConverter.toFloat(val);
/*     */   }
/*     */   
/*     */   public String getString(String name) {
/* 163 */     Object val = get(name);
/* 164 */     return BasicTypeConverter.toString(val);
/*     */   }
/*     */   
/*     */   public Date getUtilDate(String name) {
/* 168 */     Object val = get(name);
/* 169 */     return BasicTypeConverter.toUtilDate(val);
/*     */   }
/*     */   
/*     */   public Date getDate(String name) {
/* 173 */     Object val = get(name);
/* 174 */     return BasicTypeConverter.toDate(val);
/*     */   }
/*     */   
/*     */   public Timestamp getTimestamp(String name) {
/* 178 */     Object val = get(name);
/* 179 */     return BasicTypeConverter.toTimestamp(val);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 183 */     return this.map.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 190 */     this.map.clear();
/*     */   }
/*     */   
/*     */   public boolean containsKey(Object key) {
/* 194 */     key = ((String)key).toLowerCase();
/* 195 */     return this.map.containsKey(key);
/*     */   }
/*     */   
/*     */   public boolean containsValue(Object value) {
/* 199 */     return this.map.containsValue(value);
/*     */   }
/*     */   
/*     */   public Set<Map.Entry<String, Object>> entrySet() {
/* 203 */     return this.map.entrySet();
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 207 */     return this.map.isEmpty();
/*     */   }
/*     */   
/*     */   public Set<String> keySet() {
/* 211 */     return this.map.keySet();
/*     */   }
/*     */   
/*     */   public void putAll(Map<? extends String, ? extends Object> t) {
/* 215 */     this.map.putAll(t);
/*     */   }
/*     */   
/*     */   public int size() {
/* 219 */     return this.map.size();
/*     */   }
/*     */   
/*     */   public Collection<Object> values() {
/* 223 */     return this.map.values();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\query\DefaultSqlRow.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
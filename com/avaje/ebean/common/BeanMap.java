/*     */ package com.avaje.ebean.common;
/*     */ 
/*     */ import com.avaje.ebean.bean.BeanCollection;
/*     */ import com.avaje.ebean.bean.BeanCollectionLoader;
/*     */ import com.avaje.ebean.bean.SerializeControl;
/*     */ import java.io.ObjectStreamException;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
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
/*     */ public final class BeanMap<K, E>
/*     */   extends AbstractBeanCollection<E>
/*     */   implements Map<K, E>
/*     */ {
/*     */   private Map<K, E> map;
/*     */   
/*     */   public BeanMap(Map<K, E> map) {
/*  47 */     this.map = map;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeanMap() {
/*  54 */     this(new LinkedHashMap<K, E>());
/*     */   }
/*     */   
/*     */   public BeanMap(BeanCollectionLoader ebeanServer, Object ownerBean, String propertyName) {
/*  58 */     super(ebeanServer, ownerBean, propertyName);
/*     */   }
/*     */   
/*     */   Object readResolve() throws ObjectStreamException {
/*  62 */     if (SerializeControl.isVanillaCollections()) {
/*  63 */       return this.map;
/*     */     }
/*  65 */     return this;
/*     */   }
/*     */   
/*     */   Object writeReplace() throws ObjectStreamException {
/*  69 */     if (SerializeControl.isVanillaCollections()) {
/*  70 */       return this.map;
/*     */     }
/*  72 */     return this;
/*     */   }
/*     */   
/*     */   public void internalAdd(Object bean) {
/*  76 */     throw new RuntimeException("Not allowed for map");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPopulated() {
/*  84 */     return (this.map != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isReference() {
/*  92 */     return (this.map == null);
/*     */   }
/*     */   
/*     */   public boolean checkEmptyLazyLoad() {
/*  96 */     if (this.map == null) {
/*  97 */       this.map = new LinkedHashMap<K, E>();
/*  98 */       return true;
/*     */     } 
/* 100 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private void initClear() {
/* 105 */     synchronized (this) {
/* 106 */       if (this.map == null) {
/* 107 */         if (this.modifyListening) {
/* 108 */           lazyLoadCollection(true);
/*     */         } else {
/* 110 */           this.map = new LinkedHashMap<K, E>();
/*     */         } 
/*     */       }
/* 113 */       touched();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void init() {
/* 118 */     synchronized (this) {
/* 119 */       if (this.map == null) {
/* 120 */         lazyLoadCollection(false);
/*     */       }
/* 122 */       touched();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setActualMap(Map<?, ?> map) {
/* 131 */     this.map = (Map)map;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<K, E> getActualMap() {
/* 138 */     return this.map;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<E> getActualDetails() {
/* 149 */     return this.map.values();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getActualCollection() {
/* 156 */     return this.map;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 160 */     StringBuffer sb = new StringBuffer();
/* 161 */     sb.append("BeanMap ");
/* 162 */     if (isReadOnly()) {
/* 163 */       sb.append("readOnly ");
/*     */     }
/* 165 */     if (this.map == null) {
/* 166 */       sb.append("deferred ");
/*     */     } else {
/*     */       
/* 169 */       sb.append("size[").append(this.map.size()).append("]");
/* 170 */       sb.append(" hasMoreRows[").append(this.hasMoreRows).append("]");
/* 171 */       sb.append(" map").append(this.map);
/*     */     } 
/* 173 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 180 */     init();
/* 181 */     return this.map.equals(obj);
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 185 */     init();
/* 186 */     return this.map.hashCode();
/*     */   }
/*     */   
/*     */   public void clear() {
/* 190 */     checkReadOnly();
/* 191 */     initClear();
/* 192 */     if (this.modifyRemoveListening) {
/* 193 */       for (K key : this.map.keySet()) {
/* 194 */         E o = this.map.remove(key);
/* 195 */         modifyRemoval(o);
/*     */       } 
/*     */     }
/* 198 */     this.map.clear();
/*     */   }
/*     */   
/*     */   public boolean containsKey(Object key) {
/* 202 */     init();
/* 203 */     return this.map.containsKey(key);
/*     */   }
/*     */   
/*     */   public boolean containsValue(Object value) {
/* 207 */     init();
/* 208 */     return this.map.containsValue(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<Map.Entry<K, E>> entrySet() {
/* 213 */     init();
/* 214 */     if (isReadOnly()) {
/* 215 */       return Collections.unmodifiableSet(this.map.entrySet());
/*     */     }
/* 217 */     if (this.modifyListening) {
/* 218 */       Set<Map.Entry<K, E>> s = this.map.entrySet();
/* 219 */       return new ModifySet<Map.Entry<K, E>>((BeanCollection)this, s);
/*     */     } 
/* 221 */     return this.map.entrySet();
/*     */   }
/*     */   
/*     */   public E get(Object key) {
/* 225 */     init();
/* 226 */     return this.map.get(key);
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 230 */     init();
/* 231 */     return this.map.isEmpty();
/*     */   }
/*     */   
/*     */   public Set<K> keySet() {
/* 235 */     init();
/* 236 */     if (isReadOnly()) {
/* 237 */       return Collections.unmodifiableSet(this.map.keySet());
/*     */     }
/*     */     
/* 240 */     return this.map.keySet();
/*     */   }
/*     */   
/*     */   public E put(K key, E value) {
/* 244 */     checkReadOnly();
/* 245 */     init();
/* 246 */     if (this.modifyListening) {
/* 247 */       Object o = this.map.put(key, value);
/* 248 */       modifyAddition(value);
/* 249 */       modifyRemoval(o);
/*     */     } 
/* 251 */     return this.map.put(key, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void putAll(Map<? extends K, ? extends E> t) {
/* 256 */     checkReadOnly();
/* 257 */     init();
/* 258 */     if (this.modifyListening) {
/* 259 */       Iterator<Map.Entry> it = t.entrySet().iterator();
/* 260 */       while (it.hasNext()) {
/* 261 */         Map.Entry entry = it.next();
/* 262 */         Object o = this.map.put((K)entry.getKey(), (E)entry.getValue());
/* 263 */         modifyAddition((E)entry.getValue());
/* 264 */         modifyRemoval(o);
/*     */       } 
/*     */     } 
/* 267 */     this.map.putAll(t);
/*     */   }
/*     */   
/*     */   public E remove(Object key) {
/* 271 */     checkReadOnly();
/* 272 */     init();
/* 273 */     if (this.modifyRemoveListening) {
/* 274 */       E o = this.map.remove(key);
/* 275 */       modifyRemoval(o);
/* 276 */       return o;
/*     */     } 
/* 278 */     return this.map.remove(key);
/*     */   }
/*     */   
/*     */   public int size() {
/* 282 */     init();
/* 283 */     return this.map.size();
/*     */   }
/*     */   
/*     */   public Collection<E> values() {
/* 287 */     init();
/* 288 */     if (isReadOnly()) {
/* 289 */       return Collections.unmodifiableCollection(this.map.values());
/*     */     }
/* 291 */     if (this.modifyListening) {
/* 292 */       Collection<E> c = this.map.values();
/* 293 */       return new ModifyCollection<E>(this, c);
/*     */     } 
/* 295 */     return this.map.values();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\common\BeanMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
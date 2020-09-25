/*     */ package com.avaje.ebeaninternal.server.transaction;
/*     */ 
/*     */ import com.avaje.ebean.bean.PersistenceContext;
/*     */ import com.avaje.ebeaninternal.api.Monitor;
/*     */ import com.avaje.ebeaninternal.server.subclass.SubClassUtil;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DefaultPersistenceContext
/*     */   implements PersistenceContext
/*     */ {
/*  52 */   private final HashMap<String, ClassContext> typeCache = new HashMap<String, ClassContext>();
/*     */   
/*  54 */   private final Monitor monitor = new Monitor();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void put(Object id, Object bean) {
/*  66 */     synchronized (this.monitor) {
/*  67 */       getClassContext(bean.getClass()).put(id, bean);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Object putIfAbsent(Object id, Object bean) {
/*  72 */     synchronized (this.monitor) {
/*  73 */       return getClassContext(bean.getClass()).putIfAbsent(id, bean);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object get(Class<?> beanType, Object id) {
/*  83 */     synchronized (this.monitor) {
/*  84 */       return getClassContext(beanType).get(id);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size(Class<?> beanType) {
/*  92 */     synchronized (this.monitor) {
/*  93 */       ClassContext classMap = this.typeCache.get(beanType.getName());
/*  94 */       return (classMap == null) ? 0 : classMap.size();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 102 */     synchronized (this.monitor) {
/* 103 */       this.typeCache.clear();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void clear(Class<?> beanType) {
/* 108 */     synchronized (this.monitor) {
/* 109 */       ClassContext classMap = this.typeCache.get(beanType.getName());
/* 110 */       if (classMap != null) {
/* 111 */         classMap.clear();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void clear(Class<?> beanType, Object id) {
/* 117 */     synchronized (this.monitor) {
/* 118 */       ClassContext classMap = this.typeCache.get(beanType.getName());
/* 119 */       if (classMap != null && id != null)
/*     */       {
/* 121 */         classMap.remove(id);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public String toString() {
/* 127 */     synchronized (this.monitor) {
/* 128 */       StringBuilder sb = new StringBuilder();
/* 129 */       Iterator<Map.Entry<String, ClassContext>> it = this.typeCache.entrySet().iterator();
/* 130 */       while (it.hasNext()) {
/* 131 */         Map.Entry<String, ClassContext> entry = it.next();
/* 132 */         if (((ClassContext)entry.getValue()).size() > 0) {
/* 133 */           sb.append((String)entry.getKey() + ":" + ((ClassContext)entry.getValue()).size() + "; ");
/*     */         }
/*     */       } 
/* 136 */       return sb.toString();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private ClassContext getClassContext(Class<?> beanType) {
/* 143 */     String clsName = SubClassUtil.getSuperClassName(beanType.getName());
/*     */     
/* 145 */     ClassContext classMap = this.typeCache.get(clsName);
/* 146 */     if (classMap == null) {
/* 147 */       classMap = new ClassContext();
/* 148 */       this.typeCache.put(clsName, classMap);
/*     */     } 
/* 150 */     return classMap;
/*     */   }
/*     */   
/*     */   private static class ClassContext
/*     */   {
/* 155 */     private final WeakValueMap<Object, Object> map = new WeakValueMap<Object, Object>();
/*     */     
/*     */     private Object get(Object id) {
/* 158 */       return this.map.get(id);
/*     */     }
/*     */ 
/*     */     
/*     */     private Object putIfAbsent(Object id, Object bean) {
/* 163 */       return this.map.putIfAbsent(id, bean);
/*     */     }
/*     */     
/*     */     private void put(Object id, Object b) {
/* 167 */       this.map.put(id, b);
/*     */     }
/*     */     
/*     */     private int size() {
/* 171 */       return this.map.size();
/*     */     }
/*     */     
/*     */     private void clear() {
/* 175 */       this.map.clear();
/*     */     }
/*     */     
/*     */     private Object remove(Object id) {
/* 179 */       return this.map.remove(id);
/*     */     }
/*     */     
/*     */     private ClassContext() {}
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\transaction\DefaultPersistenceContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
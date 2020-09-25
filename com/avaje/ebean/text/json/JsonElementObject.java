/*     */ package com.avaje.ebean.text.json;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JsonElementObject
/*     */   implements JsonElement
/*     */ {
/*  40 */   private final Map<String, JsonElement> map = new LinkedHashMap<String, JsonElement>();
/*     */   
/*     */   public void put(String key, JsonElement value) {
/*  43 */     this.map.put(key, value);
/*     */   }
/*     */   
/*     */   private String[] split(String exp) {
/*  47 */     int pos = exp.indexOf('.');
/*  48 */     if (pos == -1) {
/*  49 */       return new String[] { exp, null };
/*     */     }
/*  51 */     String exp0 = exp.substring(0, pos);
/*  52 */     String exp1 = exp.substring(pos + 1);
/*  53 */     return new String[] { exp0, exp1 };
/*     */   }
/*     */   
/*     */   public Object eval(String exp) {
/*  57 */     String[] e = split(exp);
/*  58 */     return eval(e[0], e[1]);
/*     */   }
/*     */   
/*     */   public int evalInt(String exp) {
/*  62 */     String[] e = split(exp);
/*  63 */     return evalInt(e[0], e[1]);
/*     */   }
/*     */   
/*     */   public String evalString(String exp) {
/*  67 */     if (exp == null) {
/*  68 */       return this.map.toString();
/*     */     }
/*  70 */     String[] e = split(exp);
/*  71 */     return evalString(e[0], e[1]);
/*     */   }
/*     */   
/*     */   public boolean evalBoolean(String exp) {
/*  75 */     String[] e = split(exp);
/*  76 */     return evalBoolean(e[0], e[1]);
/*     */   }
/*     */   
/*     */   private Object eval(String exp0, String exp1) {
/*  80 */     JsonElement e = this.map.get(exp0);
/*  81 */     return (e == null) ? null : e.eval(exp1);
/*     */   }
/*     */   
/*     */   private int evalInt(String exp0, String exp1) {
/*  85 */     JsonElement e = this.map.get(exp0);
/*  86 */     return (e == null) ? 0 : e.evalInt(exp1);
/*     */   }
/*     */   
/*     */   private String evalString(String exp0, String exp1) {
/*  90 */     JsonElement e = this.map.get(exp0);
/*  91 */     return (e == null) ? "" : e.evalString(exp1);
/*     */   }
/*     */   
/*     */   private boolean evalBoolean(String exp0, String exp1) {
/*  95 */     JsonElement e = this.map.get(exp0);
/*  96 */     return (e == null) ? false : e.evalBoolean(exp1);
/*     */   }
/*     */   
/*     */   public JsonElement get(String key) {
/* 100 */     return this.map.get(key);
/*     */   }
/*     */   
/*     */   public JsonElement getValue(String key) {
/* 104 */     return this.map.get(key);
/*     */   }
/*     */   
/*     */   public Set<String> keySet() {
/* 108 */     return this.map.keySet();
/*     */   }
/*     */   
/*     */   public Set<Map.Entry<String, JsonElement>> entrySet() {
/* 112 */     return this.map.entrySet();
/*     */   }
/*     */   
/*     */   public String toString() {
/* 116 */     return this.map.toString();
/*     */   }
/*     */   
/*     */   public boolean isPrimitive() {
/* 120 */     return false;
/*     */   }
/*     */   
/*     */   public String toPrimitiveString() {
/* 124 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\text\json\JsonElementObject.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
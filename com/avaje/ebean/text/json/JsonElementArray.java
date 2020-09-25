/*     */ package com.avaje.ebean.text.json;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JsonElementArray
/*     */   implements JsonElement
/*     */ {
/*  39 */   private final List<JsonElement> values = new ArrayList<JsonElement>();
/*     */   
/*     */   public List<JsonElement> getValues() {
/*  42 */     return this.values;
/*     */   }
/*     */   
/*     */   public void add(JsonElement value) {
/*  46 */     this.values.add(value);
/*     */   }
/*     */   
/*     */   public String toString() {
/*  50 */     return this.values.toString();
/*     */   }
/*     */   
/*     */   public boolean isPrimitive() {
/*  54 */     return false;
/*     */   }
/*     */   
/*     */   public String toPrimitiveString() {
/*  58 */     return null;
/*     */   }
/*     */   
/*     */   private String[] split(String exp) {
/*  62 */     int pos = exp.indexOf('.');
/*  63 */     if (pos == -1) {
/*  64 */       return new String[] { exp, null };
/*     */     }
/*  66 */     String exp0 = exp.substring(0, pos);
/*  67 */     String exp1 = exp.substring(pos + 1);
/*  68 */     return new String[] { exp0, exp1 };
/*     */   }
/*     */   
/*     */   public Object eval(String exp) {
/*  72 */     String[] e = split(exp);
/*  73 */     return eval(e[0], e[1]);
/*     */   }
/*     */   
/*     */   public int evalInt(String exp) {
/*  77 */     String[] e = split(exp);
/*  78 */     return evalInt(e[0], e[1]);
/*     */   }
/*     */   
/*     */   public String evalString(String exp) {
/*  82 */     String[] e = split(exp);
/*  83 */     return evalString(e[0], e[1]);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean evalBoolean(String exp) {
/*  88 */     return false;
/*     */   }
/*     */   
/*     */   private Object eval(String exp0, String exp1) {
/*  92 */     if ("size".equals(exp0)) {
/*  93 */       return Integer.valueOf(this.values.size());
/*     */     }
/*  95 */     if ("isEmpty".equals(exp0)) {
/*  96 */       return Boolean.valueOf(this.values.isEmpty());
/*     */     }
/*  98 */     int idx = Integer.parseInt(exp0);
/*  99 */     JsonElement element = this.values.get(idx);
/* 100 */     return element.eval(exp1);
/*     */   }
/*     */   
/*     */   private int evalInt(String exp0, String exp1) {
/* 104 */     if ("size".equals(exp0)) {
/* 105 */       return this.values.size();
/*     */     }
/* 107 */     if ("isEmpty".equals(exp0)) {
/* 108 */       return this.values.isEmpty() ? 1 : 0;
/*     */     }
/* 110 */     int idx = Integer.parseInt(exp0);
/* 111 */     JsonElement element = this.values.get(idx);
/* 112 */     return element.evalInt(exp1);
/*     */   }
/*     */   
/*     */   private String evalString(String exp0, String exp1) {
/* 116 */     if ("size".equals(exp0)) {
/* 117 */       return String.valueOf(this.values.size());
/*     */     }
/* 119 */     if ("isEmpty".equals(exp0)) {
/* 120 */       return String.valueOf(this.values.isEmpty());
/*     */     }
/* 122 */     int idx = Integer.parseInt(exp0);
/* 123 */     JsonElement element = this.values.get(idx);
/* 124 */     return element.evalString(exp1);
/*     */   }
/*     */   
/*     */   public String getString() {
/* 128 */     return toString();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\text\json\JsonElementArray.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
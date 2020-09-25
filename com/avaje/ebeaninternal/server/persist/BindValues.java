/*     */ package com.avaje.ebeaninternal.server.persist;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BindValues
/*     */ {
/*     */   int commentCount;
/*  31 */   final ArrayList<Value> list = new ArrayList<Value>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/*  43 */     return this.list.size() - this.commentCount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(Object value, int dbType, String name) {
/*  53 */     this.list.add(new Value(value, dbType, name));
/*     */   }
/*     */   
/*     */   public void addComment(String comment) {
/*  57 */     this.commentCount++;
/*  58 */     this.list.add(new Value(comment));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList<Value> values() {
/*  65 */     return this.list;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Value
/*     */   {
/*     */     private final Object value;
/*     */ 
/*     */     
/*     */     private final int dbType;
/*     */ 
/*     */     
/*     */     private final String name;
/*     */ 
/*     */     
/*     */     private final boolean isComment;
/*     */ 
/*     */ 
/*     */     
/*     */     public Value(String comment) {
/*  86 */       this.name = comment;
/*  87 */       this.isComment = true;
/*  88 */       this.value = null;
/*  89 */       this.dbType = 0;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Value(Object value, int dbType, String name) {
/*  97 */       this.isComment = false;
/*  98 */       this.value = value;
/*  99 */       this.dbType = dbType;
/* 100 */       this.name = name;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isComment() {
/* 107 */       return this.isComment;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getDbType() {
/* 114 */       return this.dbType;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Object getValue() {
/* 121 */       return this.value;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getName() {
/* 128 */       return this.name;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 132 */       return "" + this.value;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\persist\BindValues.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
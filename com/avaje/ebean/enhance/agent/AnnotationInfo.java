/*    */ package com.avaje.ebean.enhance.agent;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AnnotationInfo
/*    */ {
/* 11 */   final HashMap<String, Object> valueMap = new HashMap<String, Object>();
/*    */ 
/*    */ 
/*    */   
/*    */   AnnotationInfo parent;
/*    */ 
/*    */ 
/*    */   
/*    */   public AnnotationInfo(AnnotationInfo parent) {
/* 20 */     this.parent = parent;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 24 */     return this.valueMap.toString();
/*    */   }
/*    */ 
/*    */   
/*    */   public AnnotationInfo getParent() {
/* 29 */     return this.parent;
/*    */   }
/*    */   
/*    */   public void setParent(AnnotationInfo parent) {
/* 33 */     this.parent = parent;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void add(String prefix, String name, Object value) {
/* 41 */     if (name == null) {
/*    */       
/* 43 */       ArrayList<Object> list = (ArrayList<Object>)this.valueMap.get(prefix);
/* 44 */       if (list == null) {
/* 45 */         list = new ArrayList();
/* 46 */         this.valueMap.put(prefix, list);
/*    */       } 
/*    */       
/* 49 */       list.add(value);
/*    */     } else {
/*    */       
/* 52 */       String key = getKey(prefix, name);
/*    */       
/* 54 */       this.valueMap.put(key, value);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addEnum(String prefix, String name, String desc, String value) {
/* 63 */     add(prefix, name, value);
/*    */   }
/*    */   
/*    */   private String getKey(String prefix, String name) {
/* 67 */     if (prefix == null) {
/* 68 */       return name;
/*    */     }
/* 70 */     return prefix + "." + name;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object getValue(String key) {
/* 78 */     Object o = this.valueMap.get(key);
/* 79 */     if (o == null && this.parent != null)
/*    */     {
/* 81 */       o = this.parent.getValue(key);
/*    */     }
/* 83 */     return o;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\enhance\agent\AnnotationInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
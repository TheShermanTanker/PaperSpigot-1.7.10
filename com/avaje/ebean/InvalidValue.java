/*     */ package com.avaje.ebean;
/*     */ 
/*     */ import java.io.Serializable;
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
/*     */ public class InvalidValue
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 2408556605456324913L;
/*  56 */   private static final Object[] EMPTY = new Object[0];
/*     */   
/*     */   private final String beanType;
/*     */   
/*     */   private final String propertyName;
/*     */   
/*     */   private final String validatorKey;
/*     */   
/*     */   private final Object value;
/*     */   
/*     */   private final InvalidValue[] children;
/*     */   
/*     */   private final Object[] validatorAttributes;
/*     */   
/*     */   private String message;
/*     */   
/*     */   public InvalidValue(String validatorKey, String beanType, Object bean, InvalidValue[] children) {
/*  73 */     this.validatorKey = validatorKey;
/*  74 */     this.validatorAttributes = EMPTY;
/*  75 */     this.beanType = beanType;
/*  76 */     this.propertyName = null;
/*  77 */     this.value = bean;
/*  78 */     this.children = children;
/*     */   }
/*     */   
/*     */   public InvalidValue(String validatorKey, Object[] validatorAttributes, String beanType, String propertyName, Object value) {
/*  82 */     this.validatorKey = validatorKey;
/*  83 */     this.validatorAttributes = validatorAttributes;
/*  84 */     this.beanType = beanType;
/*  85 */     this.propertyName = propertyName;
/*  86 */     this.value = value;
/*  87 */     this.children = null;
/*     */   }
/*     */   
/*     */   public String getBeanType() {
/*  91 */     return this.beanType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPropertyName() {
/*  98 */     return this.propertyName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getValidatorKey() {
/* 105 */     return this.validatorKey;
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
/*     */   public Object[] getValidatorAttributes() {
/* 121 */     return this.validatorAttributes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getValue() {
/* 128 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InvalidValue[] getChildren() {
/* 135 */     return this.children;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMessage() {
/* 142 */     return this.message;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMessage(String message) {
/* 149 */     this.message = message;
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
/*     */   public boolean isBean() {
/* 161 */     return !isError();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isError() {
/* 168 */     return (this.children == null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InvalidValue[] getErrors() {
/* 176 */     ArrayList<InvalidValue> list = new ArrayList<InvalidValue>();
/* 177 */     buildList(list);
/*     */     
/* 179 */     return toArray(list);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void buildList(List<InvalidValue> list) {
/* 186 */     if (isError()) {
/* 187 */       list.add(this);
/*     */     } else {
/* 189 */       for (int i = 0; i < this.children.length; i++) {
/* 190 */         this.children[i].buildList(list);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 197 */     if (isError()) {
/* 198 */       return "errorKey=" + this.validatorKey + " type=" + this.beanType + " property=" + this.propertyName + " value=" + this.value;
/*     */     }
/*     */     
/* 201 */     if (this.children.length == 1) {
/* 202 */       return this.children[0].toString();
/*     */     }
/*     */ 
/*     */     
/* 206 */     StringBuilder sb = new StringBuilder(50);
/* 207 */     sb.append("\r\nCHILDREN[").append(this.children.length).append("]");
/* 208 */     for (int i = 0; i < this.children.length; i++) {
/* 209 */       sb.append(this.children[i].toString()).append(", ");
/*     */     }
/* 211 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static InvalidValue[] toArray(List<InvalidValue> list) {
/* 218 */     return list.<InvalidValue>toArray(new InvalidValue[list.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<InvalidValue> toList(InvalidValue invalid) {
/* 225 */     ArrayList<InvalidValue> list = new ArrayList<InvalidValue>();
/* 226 */     list.add(invalid);
/* 227 */     return list;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\InvalidValue.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
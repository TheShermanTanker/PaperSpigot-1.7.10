/*     */ package com.avaje.ebeaninternal.server.deploy.parse;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.deploy.InheritInfo;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
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
/*     */ public class DeployInheritInfo
/*     */ {
/*     */   private static final String JPA_DEFAULT_DISCRIM_COLUMN = "dtype";
/*     */   private int discriminatorLength;
/*     */   private int discriminatorType;
/*     */   private String discriminatorStringValue;
/*     */   private Object discriminatorObjectValue;
/*     */   private String discriminatorColumn;
/*     */   private String discriminatorWhere;
/*     */   private Class<?> type;
/*     */   private Class<?> parent;
/*  55 */   private ArrayList<DeployInheritInfo> children = new ArrayList<DeployInheritInfo>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DeployInheritInfo(Class<?> type) {
/*  61 */     this.type = type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<?> getType() {
/*  68 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<?> getParent() {
/*  75 */     return this.parent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setParent(Class<?> parent) {
/*  82 */     this.parent = parent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAbstract() {
/*  89 */     return (this.discriminatorObjectValue == null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRoot() {
/*  96 */     return (this.parent == null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<DeployInheritInfo> children() {
/* 103 */     return this.children.iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addChild(DeployInheritInfo childInfo) {
/* 110 */     this.children.add(childInfo);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDiscriminatorWhere() {
/* 117 */     return this.discriminatorWhere;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDiscriminatorWhere(String discriminatorWhere) {
/* 124 */     this.discriminatorWhere = discriminatorWhere;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDiscriminatorColumn(InheritInfo parent) {
/* 131 */     if (this.discriminatorColumn == null) {
/* 132 */       if (parent == null) {
/* 133 */         this.discriminatorColumn = "dtype";
/*     */       } else {
/* 135 */         this.discriminatorColumn = parent.getDiscriminatorColumn();
/*     */       } 
/*     */     }
/* 138 */     return this.discriminatorColumn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDiscriminatorColumn(String discriminatorColumn) {
/* 145 */     this.discriminatorColumn = discriminatorColumn;
/*     */   }
/*     */   
/*     */   public int getDiscriminatorLength(InheritInfo parent) {
/* 149 */     if (this.discriminatorLength == 0) {
/* 150 */       if (parent == null) {
/* 151 */         this.discriminatorLength = 10;
/*     */       } else {
/* 153 */         this.discriminatorLength = parent.getDiscriminatorLength();
/*     */       } 
/*     */     }
/* 156 */     return this.discriminatorLength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDiscriminatorType(InheritInfo parent) {
/* 163 */     if (this.discriminatorType == 0) {
/* 164 */       if (parent == null) {
/* 165 */         this.discriminatorType = 12;
/*     */       } else {
/* 167 */         this.discriminatorType = parent.getDiscriminatorType();
/*     */       } 
/*     */     }
/* 170 */     return this.discriminatorType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDiscriminatorType(int discriminatorType) {
/* 177 */     this.discriminatorType = discriminatorType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDiscriminatorLength() {
/* 184 */     return this.discriminatorLength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDiscriminatorLength(int discriminatorLength) {
/* 191 */     this.discriminatorLength = discriminatorLength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getDiscriminatorObjectValue() {
/* 198 */     return this.discriminatorObjectValue;
/*     */   }
/*     */   
/*     */   public String getDiscriminatorStringValue() {
/* 202 */     return this.discriminatorStringValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDiscriminatorValue(String value) {
/* 209 */     if (value != null) {
/* 210 */       value = value.trim();
/* 211 */       if (value.length() == 0) {
/* 212 */         value = null;
/*     */       } else {
/* 214 */         this.discriminatorStringValue = value;
/*     */         
/* 216 */         if (this.discriminatorType == 4) {
/* 217 */           this.discriminatorObjectValue = Integer.valueOf(value.toString());
/*     */         } else {
/* 219 */           this.discriminatorObjectValue = value;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getWhere() {
/* 227 */     List<Object> discList = new ArrayList();
/*     */     
/* 229 */     appendDiscriminator(discList);
/*     */     
/* 231 */     return buildWhereLiteral(discList);
/*     */   }
/*     */   
/*     */   private void appendDiscriminator(List<Object> list) {
/* 235 */     if (this.discriminatorObjectValue != null) {
/* 236 */       list.add(this.discriminatorObjectValue);
/*     */     }
/* 238 */     for (DeployInheritInfo child : this.children) {
/* 239 */       child.appendDiscriminator(list);
/*     */     }
/*     */   }
/*     */   
/*     */   private String buildWhereLiteral(List<Object> discList) {
/* 244 */     int size = discList.size();
/* 245 */     if (size == 0) {
/* 246 */       return "";
/*     */     }
/* 248 */     StringBuilder sb = new StringBuilder();
/* 249 */     sb.append(this.discriminatorColumn);
/* 250 */     if (size == 1) {
/* 251 */       sb.append(" = ");
/*     */     } else {
/* 253 */       sb.append(" in (");
/*     */     } 
/* 255 */     for (int i = 0; i < discList.size(); i++) {
/* 256 */       appendSqlLiteralValue(i, discList.get(i), sb);
/*     */     }
/* 258 */     if (size > 1) {
/* 259 */       sb.append(")");
/*     */     }
/* 261 */     return sb.toString();
/*     */   }
/*     */   
/*     */   private void appendSqlLiteralValue(int count, Object value, StringBuilder sb) {
/* 265 */     if (count > 0) {
/* 266 */       sb.append(",");
/*     */     }
/* 268 */     if (value instanceof String) {
/* 269 */       sb.append("'").append(value).append("'");
/*     */     } else {
/* 271 */       sb.append(value);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String toString() {
/* 276 */     StringBuilder sb = new StringBuilder();
/* 277 */     sb.append("InheritInfo[").append(this.type.getName()).append("]");
/* 278 */     sb.append(" root[").append(this.parent.getName()).append("]");
/* 279 */     sb.append(" disValue[").append(this.discriminatorStringValue).append("]");
/* 280 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\parse\DeployInheritInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
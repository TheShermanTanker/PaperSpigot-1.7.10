/*     */ package com.avaje.ebean.bean;
/*     */ 
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.HashSet;
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
/*     */ public final class NodeUsageCollector
/*     */ {
/*     */   private final ObjectGraphNode node;
/*     */   private final WeakReference<NodeUsageListener> managerRef;
/*  51 */   private final HashSet<String> used = new HashSet<String>();
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean modified;
/*     */ 
/*     */ 
/*     */   
/*     */   private String loadProperty;
/*     */ 
/*     */ 
/*     */   
/*     */   public NodeUsageCollector(ObjectGraphNode node, WeakReference<NodeUsageListener> managerRef) {
/*  64 */     this.node = node;
/*     */     
/*  66 */     this.managerRef = managerRef;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setModified() {
/*  73 */     this.modified = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addUsed(String property) {
/*  80 */     this.used.add(property);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLoadProperty(String loadProperty) {
/*  87 */     this.loadProperty = loadProperty;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void publishUsageInfo() {
/*  94 */     NodeUsageListener manager = this.managerRef.get();
/*  95 */     if (manager != null) {
/*  96 */       manager.collectNodeUsage(this);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void finalize() throws Throwable {
/* 105 */     publishUsageInfo();
/* 106 */     super.finalize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectGraphNode getNode() {
/* 113 */     return this.node;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 120 */     return this.used.isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HashSet<String> getUsed() {
/* 127 */     return this.used;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isModified() {
/* 134 */     return this.modified;
/*     */   }
/*     */   
/*     */   public String getLoadProperty() {
/* 138 */     return this.loadProperty;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 142 */     return this.node + " read:" + this.used + " modified:" + this.modified;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\bean\NodeUsageCollector.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
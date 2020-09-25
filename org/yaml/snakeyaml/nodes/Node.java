/*     */ package org.yaml.snakeyaml.nodes;
/*     */ 
/*     */ import org.yaml.snakeyaml.error.Mark;
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
/*     */ public abstract class Node
/*     */ {
/*     */   private Tag tag;
/*     */   private Mark startMark;
/*     */   protected Mark endMark;
/*     */   private Class<? extends Object> type;
/*     */   private boolean twoStepsConstruction;
/*     */   protected boolean resolved;
/*     */   protected Boolean useClassConstructor;
/*     */   
/*     */   public Node(Tag tag, Mark startMark, Mark endMark) {
/*  46 */     setTag(tag);
/*  47 */     this.startMark = startMark;
/*  48 */     this.endMark = endMark;
/*  49 */     this.type = Object.class;
/*  50 */     this.twoStepsConstruction = false;
/*  51 */     this.resolved = true;
/*  52 */     this.useClassConstructor = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tag getTag() {
/*  63 */     return this.tag;
/*     */   }
/*     */   
/*     */   public Mark getEndMark() {
/*  67 */     return this.endMark;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract NodeId getNodeId();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Mark getStartMark() {
/*  79 */     return this.startMark;
/*     */   }
/*     */   
/*     */   public void setTag(Tag tag) {
/*  83 */     if (tag == null) {
/*  84 */       throw new NullPointerException("tag in a Node is required.");
/*     */     }
/*  86 */     this.tag = tag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean equals(Object obj) {
/*  94 */     return super.equals(obj);
/*     */   }
/*     */   
/*     */   public Class<? extends Object> getType() {
/*  98 */     return this.type;
/*     */   }
/*     */   
/*     */   public void setType(Class<? extends Object> type) {
/* 102 */     if (!type.isAssignableFrom(this.type)) {
/* 103 */       this.type = type;
/*     */     }
/*     */   }
/*     */   
/*     */   public void setTwoStepsConstruction(boolean twoStepsConstruction) {
/* 108 */     this.twoStepsConstruction = twoStepsConstruction;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTwoStepsConstruction() {
/* 129 */     return this.twoStepsConstruction;
/*     */   }
/*     */ 
/*     */   
/*     */   public final int hashCode() {
/* 134 */     return super.hashCode();
/*     */   }
/*     */   
/*     */   public boolean useClassConstructor() {
/* 138 */     if (this.useClassConstructor == null) {
/* 139 */       if (isResolved() && !Object.class.equals(this.type) && !this.tag.equals(Tag.NULL))
/* 140 */         return true; 
/* 141 */       if (this.tag.isCompatible(getType()))
/*     */       {
/*     */         
/* 144 */         return true;
/*     */       }
/* 146 */       return false;
/*     */     } 
/*     */     
/* 149 */     return this.useClassConstructor.booleanValue();
/*     */   }
/*     */   
/*     */   public void setUseClassConstructor(Boolean useClassConstructor) {
/* 153 */     this.useClassConstructor = useClassConstructor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isResolved() {
/* 163 */     return this.resolved;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\yaml\snakeyaml\nodes\Node.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
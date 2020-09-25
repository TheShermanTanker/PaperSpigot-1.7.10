/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.util.Comparator;
/*     */ import javax.annotation.Nullable;
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
/*     */ 
/*     */ @GwtCompatible
/*     */ class BstNode<K, N extends BstNode<K, N>>
/*     */ {
/*     */   private final K key;
/*     */   @Nullable
/*     */   private final N left;
/*     */   @Nullable
/*     */   private final N right;
/*     */   
/*     */   BstNode(K key, @Nullable N left, @Nullable N right) {
/*  64 */     this.key = (K)Preconditions.checkNotNull(key);
/*  65 */     this.left = left;
/*  66 */     this.right = right;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final K getKey() {
/*  73 */     return this.key;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public final N childOrNull(BstSide side) {
/*  81 */     switch (side) {
/*     */       case LEFT:
/*  83 */         return this.left;
/*     */       case RIGHT:
/*  85 */         return this.right;
/*     */     } 
/*  87 */     throw new AssertionError();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean hasChild(BstSide side) {
/*  95 */     return (childOrNull(side) != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final N getChild(BstSide side) {
/* 104 */     N child = childOrNull(side);
/* 105 */     Preconditions.checkState((child != null));
/* 106 */     return child;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final boolean orderingInvariantHolds(Comparator<? super K> comparator) {
/*     */     int i;
/* 114 */     Preconditions.checkNotNull(comparator);
/* 115 */     boolean result = true;
/* 116 */     if (hasChild(BstSide.LEFT)) {
/* 117 */       i = result & ((comparator.compare((K)getChild(BstSide.LEFT).getKey(), this.key) < 0) ? 1 : 0);
/*     */     }
/* 119 */     if (hasChild(BstSide.RIGHT)) {
/* 120 */       i &= (comparator.compare((K)getChild(BstSide.RIGHT).getKey(), this.key) > 0) ? 1 : 0;
/*     */     }
/* 122 */     return i;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\BstNode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
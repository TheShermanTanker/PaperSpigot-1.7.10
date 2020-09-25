/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.base.Preconditions;
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
/*     */ @GwtCompatible
/*     */ final class BstRangeOps
/*     */ {
/*     */   public static <K, N extends BstNode<K, N>> int totalInRange(BstAggregate<? super N> aggregate, GeneralRange<K> range, @Nullable N root) {
/*  39 */     Preconditions.checkNotNull(aggregate);
/*  40 */     Preconditions.checkNotNull(range);
/*  41 */     if (root == null || range.isEmpty()) {
/*  42 */       return 0;
/*     */     }
/*  44 */     int total = aggregate.treeValue(root);
/*  45 */     if (range.hasLowerBound()) {
/*  46 */       total -= totalBeyondRangeToSide(aggregate, range, BstSide.LEFT, root);
/*     */     }
/*  48 */     if (range.hasUpperBound()) {
/*  49 */       total -= totalBeyondRangeToSide(aggregate, range, BstSide.RIGHT, root);
/*     */     }
/*  51 */     return total;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static <K, N extends BstNode<K, N>> int totalBeyondRangeToSide(BstAggregate<? super N> aggregate, GeneralRange<K> range, BstSide side, @Nullable N root) {
/*  57 */     int accum = 0;
/*  58 */     while (root != null) {
/*  59 */       if (beyond(range, (K)root.getKey(), side)) {
/*  60 */         accum += aggregate.entryValue(root);
/*  61 */         accum += aggregate.treeValue((N)root.childOrNull(side));
/*  62 */         root = (N)root.childOrNull(side.other()); continue;
/*     */       } 
/*  64 */       root = (N)root.childOrNull(side);
/*     */     } 
/*     */     
/*  67 */     return accum;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static <K, N extends BstNode<K, N>> N minusRange(GeneralRange<K> range, BstBalancePolicy<N> balancePolicy, BstNodeFactory<N> nodeFactory, @Nullable N root) {
/*  78 */     Preconditions.checkNotNull(range);
/*  79 */     Preconditions.checkNotNull(balancePolicy);
/*  80 */     Preconditions.checkNotNull(nodeFactory);
/*  81 */     N higher = range.hasUpperBound() ? subTreeBeyondRangeToSide(range, balancePolicy, nodeFactory, BstSide.RIGHT, root) : null;
/*     */ 
/*     */     
/*  84 */     N lower = range.hasLowerBound() ? subTreeBeyondRangeToSide(range, balancePolicy, nodeFactory, BstSide.LEFT, root) : null;
/*     */ 
/*     */     
/*  87 */     return balancePolicy.combine(nodeFactory, lower, higher);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private static <K, N extends BstNode<K, N>> N subTreeBeyondRangeToSide(GeneralRange<K> range, BstBalancePolicy<N> balancePolicy, BstNodeFactory<N> nodeFactory, BstSide side, @Nullable N root) {
/*  98 */     if (root == null) {
/*  99 */       return null;
/*     */     }
/* 101 */     if (beyond(range, (K)root.getKey(), side)) {
/* 102 */       N left = (N)root.childOrNull(BstSide.LEFT);
/* 103 */       N right = (N)root.childOrNull(BstSide.RIGHT);
/* 104 */       switch (side) {
/*     */         case LEFT:
/* 106 */           right = subTreeBeyondRangeToSide(range, balancePolicy, nodeFactory, BstSide.LEFT, right);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 114 */           return balancePolicy.balance(nodeFactory, root, left, right);case RIGHT: left = subTreeBeyondRangeToSide(range, balancePolicy, nodeFactory, BstSide.RIGHT, left); return balancePolicy.balance(nodeFactory, root, left, right);
/*     */       }  throw new AssertionError();
/* 116 */     }  return subTreeBeyondRangeToSide(range, balancePolicy, nodeFactory, side, (N)root.childOrNull(side));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static <K, N extends BstNode<K, N>, P extends BstPath<N, P>> P furthestPath(GeneralRange<K> range, BstSide side, BstPathFactory<N, P> pathFactory, @Nullable N root) {
/* 128 */     Preconditions.checkNotNull(range);
/* 129 */     Preconditions.checkNotNull(pathFactory);
/* 130 */     Preconditions.checkNotNull(side);
/* 131 */     if (root == null) {
/* 132 */       return null;
/*     */     }
/* 134 */     P path = pathFactory.initialPath(root);
/* 135 */     return furthestPath(range, side, pathFactory, path);
/*     */   }
/*     */ 
/*     */   
/*     */   private static <K, N extends BstNode<K, N>, P extends BstPath<N, P>> P furthestPath(GeneralRange<K> range, BstSide side, BstPathFactory<N, P> pathFactory, P currentPath) {
/* 140 */     N tip = (N)currentPath.getTip();
/* 141 */     K tipKey = (K)tip.getKey();
/* 142 */     if (beyond(range, tipKey, side)) {
/* 143 */       if (tip.hasChild(side.other())) {
/* 144 */         currentPath = pathFactory.extension(currentPath, side.other());
/* 145 */         return furthestPath(range, side, pathFactory, currentPath);
/*     */       } 
/* 147 */       return null;
/*     */     } 
/* 149 */     if (tip.hasChild(side)) {
/* 150 */       P alphaPath = pathFactory.extension(currentPath, side);
/* 151 */       alphaPath = furthestPath(range, side, pathFactory, alphaPath);
/* 152 */       if (alphaPath != null) {
/* 153 */         return alphaPath;
/*     */       }
/*     */     } 
/* 156 */     return beyond(range, tipKey, side.other()) ? null : currentPath;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> boolean beyond(GeneralRange<K> range, K key, BstSide side) {
/* 163 */     Preconditions.checkNotNull(range);
/* 164 */     Preconditions.checkNotNull(key);
/* 165 */     switch (side) {
/*     */       case LEFT:
/* 167 */         return range.tooLow(key);
/*     */       case RIGHT:
/* 169 */         return range.tooHigh(key);
/*     */     } 
/* 171 */     throw new AssertionError();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\BstRangeOps.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
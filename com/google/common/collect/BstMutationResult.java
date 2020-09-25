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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @GwtCompatible
/*     */ final class BstMutationResult<K, N extends BstNode<K, N>>
/*     */ {
/*     */   private final K targetKey;
/*     */   @Nullable
/*     */   private N originalRoot;
/*     */   @Nullable
/*     */   private N changedRoot;
/*     */   private final BstModificationResult<N> modificationResult;
/*     */   
/*     */   public static <K, N extends BstNode<K, N>> BstMutationResult<K, N> mutationResult(K targetKey, @Nullable N originalRoot, @Nullable N changedRoot, BstModificationResult<N> modificationResult) {
/*  50 */     return new BstMutationResult<K, N>(targetKey, originalRoot, changedRoot, modificationResult);
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
/*     */   private BstMutationResult(K targetKey, @Nullable N originalRoot, @Nullable N changedRoot, BstModificationResult<N> modificationResult) {
/*  65 */     this.targetKey = (K)Preconditions.checkNotNull(targetKey);
/*  66 */     this.originalRoot = originalRoot;
/*  67 */     this.changedRoot = changedRoot;
/*  68 */     this.modificationResult = (BstModificationResult<N>)Preconditions.checkNotNull(modificationResult);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public K getTargetKey() {
/*  75 */     return this.targetKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public N getOriginalRoot() {
/*  83 */     return this.originalRoot;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public N getChangedRoot() {
/*  91 */     return this.changedRoot;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public N getOriginalTarget() {
/* 101 */     return this.modificationResult.getOriginalTarget();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public N getChangedTarget() {
/* 111 */     return this.modificationResult.getChangedTarget();
/*     */   }
/*     */   
/*     */   BstModificationResult.ModificationType modificationType() {
/* 115 */     return this.modificationResult.getType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BstMutationResult<K, N> lift(N liftOriginalRoot, BstSide side, BstNodeFactory<N> nodeFactory, BstBalancePolicy<N> balancePolicy) {
/*     */     N resultLeft;
/*     */     N resultRight;
/*     */     assert false;
/* 126 */     switch (modificationType()) {
/*     */       case IDENTITY:
/* 128 */         this.originalRoot = this.changedRoot = liftOriginalRoot;
/* 129 */         return this;
/*     */       case REBUILDING_CHANGE:
/*     */       case REBALANCING_CHANGE:
/* 132 */         this.originalRoot = liftOriginalRoot;
/* 133 */         resultLeft = (N)liftOriginalRoot.childOrNull(BstSide.LEFT);
/* 134 */         resultRight = (N)liftOriginalRoot.childOrNull(BstSide.RIGHT);
/* 135 */         switch (side) {
/*     */           case IDENTITY:
/* 137 */             resultLeft = this.changedRoot;
/*     */             break;
/*     */           case REBUILDING_CHANGE:
/* 140 */             resultRight = this.changedRoot;
/*     */             break;
/*     */           default:
/* 143 */             throw new AssertionError();
/*     */         } 
/* 145 */         if (modificationType() == BstModificationResult.ModificationType.REBUILDING_CHANGE) {
/* 146 */           this.changedRoot = nodeFactory.createNode(liftOriginalRoot, resultLeft, resultRight);
/*     */         } else {
/* 148 */           this.changedRoot = balancePolicy.balance(nodeFactory, liftOriginalRoot, resultLeft, resultRight);
/*     */         } 
/*     */         
/* 151 */         return this;
/*     */     } 
/* 153 */     throw new AssertionError();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\BstMutationResult.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
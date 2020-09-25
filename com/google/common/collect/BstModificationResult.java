/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.annotations.GwtCompatible;
/*    */ import com.google.common.base.Preconditions;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @GwtCompatible
/*    */ final class BstModificationResult<N extends BstNode<?, N>>
/*    */ {
/*    */   @Nullable
/*    */   private final N originalTarget;
/*    */   @Nullable
/*    */   private final N changedTarget;
/*    */   private final ModificationType type;
/*    */   
/*    */   enum ModificationType
/*    */   {
/* 31 */     IDENTITY, REBUILDING_CHANGE, REBALANCING_CHANGE;
/*    */   }
/*    */   
/*    */   static <N extends BstNode<?, N>> BstModificationResult<N> identity(@Nullable N target) {
/* 35 */     return new BstModificationResult<N>(target, target, ModificationType.IDENTITY);
/*    */   }
/*    */ 
/*    */   
/*    */   static <N extends BstNode<?, N>> BstModificationResult<N> rebuildingChange(@Nullable N originalTarget, @Nullable N changedTarget) {
/* 40 */     return new BstModificationResult<N>(originalTarget, changedTarget, ModificationType.REBUILDING_CHANGE);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   static <N extends BstNode<?, N>> BstModificationResult<N> rebalancingChange(@Nullable N originalTarget, @Nullable N changedTarget) {
/* 46 */     return new BstModificationResult<N>(originalTarget, changedTarget, ModificationType.REBALANCING_CHANGE);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private BstModificationResult(@Nullable N originalTarget, @Nullable N changedTarget, ModificationType type) {
/* 56 */     this.originalTarget = originalTarget;
/* 57 */     this.changedTarget = changedTarget;
/* 58 */     this.type = (ModificationType)Preconditions.checkNotNull(type);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   N getOriginalTarget() {
/* 63 */     return this.originalTarget;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   N getChangedTarget() {
/* 68 */     return this.changedTarget;
/*    */   }
/*    */   
/*    */   ModificationType getType() {
/* 72 */     return this.type;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\BstModificationResult.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
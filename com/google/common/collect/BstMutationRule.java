/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.annotations.GwtCompatible;
/*    */ import com.google.common.base.Preconditions;
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
/*    */ final class BstMutationRule<K, N extends BstNode<K, N>>
/*    */ {
/*    */   private final BstModifier<K, N> modifier;
/*    */   private final BstBalancePolicy<N> balancePolicy;
/*    */   private final BstNodeFactory<N> nodeFactory;
/*    */   
/*    */   public static <K, N extends BstNode<K, N>> BstMutationRule<K, N> createRule(BstModifier<K, N> modifier, BstBalancePolicy<N> balancePolicy, BstNodeFactory<N> nodeFactory) {
/* 40 */     return new BstMutationRule<K, N>(modifier, balancePolicy, nodeFactory);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private BstMutationRule(BstModifier<K, N> modifier, BstBalancePolicy<N> balancePolicy, BstNodeFactory<N> nodeFactory) {
/* 49 */     this.balancePolicy = (BstBalancePolicy<N>)Preconditions.checkNotNull(balancePolicy);
/* 50 */     this.nodeFactory = (BstNodeFactory<N>)Preconditions.checkNotNull(nodeFactory);
/* 51 */     this.modifier = (BstModifier<K, N>)Preconditions.checkNotNull(modifier);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BstModifier<K, N> getModifier() {
/* 59 */     return this.modifier;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BstBalancePolicy<N> getBalancePolicy() {
/* 67 */     return this.balancePolicy;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BstNodeFactory<N> getNodeFactory() {
/* 75 */     return this.nodeFactory;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\BstMutationRule.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
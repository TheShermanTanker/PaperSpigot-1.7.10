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
/*     */ @GwtCompatible
/*     */ final class BstOperations
/*     */ {
/*     */   @Nullable
/*     */   public static <K, N extends BstNode<K, N>> N seek(Comparator<? super K> comparator, @Nullable N tree, K key) {
/*  42 */     Preconditions.checkNotNull(comparator);
/*  43 */     if (tree == null) {
/*  44 */       return null;
/*     */     }
/*  46 */     int cmp = comparator.compare(key, (K)tree.getKey());
/*  47 */     if (cmp == 0) {
/*  48 */       return tree;
/*     */     }
/*  50 */     BstSide side = (cmp < 0) ? BstSide.LEFT : BstSide.RIGHT;
/*  51 */     return seek(comparator, (N)tree.childOrNull(side), key);
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
/*     */   public static <K, N extends BstNode<K, N>> BstMutationResult<K, N> mutate(Comparator<? super K> comparator, BstMutationRule<K, N> mutationRule, @Nullable N tree, K key) {
/*  71 */     Preconditions.checkNotNull(comparator);
/*  72 */     Preconditions.checkNotNull(mutationRule);
/*  73 */     Preconditions.checkNotNull(key);
/*     */     
/*  75 */     if (tree != null) {
/*  76 */       int cmp = comparator.compare(key, (K)tree.getKey());
/*  77 */       if (cmp != 0) {
/*  78 */         BstSide side = (cmp < 0) ? BstSide.LEFT : BstSide.RIGHT;
/*  79 */         BstMutationResult<K, N> mutation = mutate(comparator, mutationRule, (N)tree.childOrNull(side), key);
/*     */         
/*  81 */         return mutation.lift(tree, side, mutationRule.getNodeFactory(), mutationRule.getBalancePolicy());
/*     */       } 
/*     */     } 
/*     */     
/*  85 */     return modify(tree, key, mutationRule);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, N extends BstNode<K, N>> BstMutationResult<K, N> mutate(BstInOrderPath<N> path, BstMutationRule<K, N> mutationRule) {
/*  93 */     Preconditions.checkNotNull(path);
/*  94 */     Preconditions.checkNotNull(mutationRule);
/*  95 */     BstBalancePolicy<N> balancePolicy = mutationRule.getBalancePolicy();
/*  96 */     BstNodeFactory<N> nodeFactory = mutationRule.getNodeFactory();
/*  97 */     BstModifier<K, N> modifier = mutationRule.getModifier();
/*     */     
/*  99 */     N target = path.getTip();
/* 100 */     K key = (K)target.getKey();
/* 101 */     BstMutationResult<K, N> result = modify(target, key, mutationRule);
/* 102 */     while (path.hasPrefix()) {
/* 103 */       BstInOrderPath<N> prefix = path.getPrefix();
/* 104 */       result = result.lift(prefix.getTip(), path.getSideOfExtension(), nodeFactory, balancePolicy);
/* 105 */       path = prefix;
/*     */     } 
/* 107 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static <K, N extends BstNode<K, N>> BstMutationResult<K, N> modify(@Nullable N tree, K key, BstMutationRule<K, N> mutationRule) {
/*     */     N changedRoot;
/* 115 */     BstBalancePolicy<N> rebalancePolicy = mutationRule.getBalancePolicy();
/* 116 */     BstNodeFactory<N> nodeFactory = mutationRule.getNodeFactory();
/* 117 */     BstModifier<K, N> modifier = mutationRule.getModifier();
/*     */     
/* 119 */     N originalRoot = tree;
/*     */     
/* 121 */     N originalTarget = (tree == null) ? null : nodeFactory.createLeaf(tree);
/* 122 */     BstModificationResult<N> modResult = modifier.modify(key, originalTarget);
/* 123 */     N originalLeft = null;
/* 124 */     N originalRight = null;
/* 125 */     if (tree != null) {
/* 126 */       originalLeft = (N)tree.childOrNull(BstSide.LEFT);
/* 127 */       originalRight = (N)tree.childOrNull(BstSide.RIGHT);
/*     */     } 
/* 129 */     switch (modResult.getType()) {
/*     */       case IDENTITY:
/* 131 */         changedRoot = tree;
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
/* 157 */         return BstMutationResult.mutationResult(key, originalRoot, changedRoot, modResult);case REBUILDING_CHANGE: if (modResult.getChangedTarget() != null) { changedRoot = nodeFactory.createNode(modResult.getChangedTarget(), originalLeft, originalRight); } else if (tree == null) { changedRoot = null; } else { throw new AssertionError("Modification result is a REBUILDING_CHANGE, but rebalancing required"); }  return BstMutationResult.mutationResult(key, originalRoot, changedRoot, modResult);case REBALANCING_CHANGE: if (modResult.getChangedTarget() != null) { changedRoot = rebalancePolicy.balance(nodeFactory, modResult.getChangedTarget(), originalLeft, originalRight); } else if (tree != null) { changedRoot = rebalancePolicy.combine(nodeFactory, originalLeft, originalRight); } else { changedRoot = null; }  return BstMutationResult.mutationResult(key, originalRoot, changedRoot, modResult);
/*     */     } 
/*     */     throw new AssertionError();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, N extends BstNode<K, N>> BstMutationResult<K, N> extractMin(N root, BstNodeFactory<N> nodeFactory, BstBalancePolicy<N> balancePolicy) {
/* 165 */     Preconditions.checkNotNull(root);
/* 166 */     Preconditions.checkNotNull(nodeFactory);
/* 167 */     Preconditions.checkNotNull(balancePolicy);
/* 168 */     if (root.hasChild(BstSide.LEFT)) {
/* 169 */       BstMutationResult<K, N> subResult = extractMin((N)root.getChild(BstSide.LEFT), nodeFactory, balancePolicy);
/*     */       
/* 171 */       return subResult.lift(root, BstSide.LEFT, nodeFactory, balancePolicy);
/*     */     } 
/* 173 */     return BstMutationResult.mutationResult((K)root.getKey(), root, (N)root.childOrNull(BstSide.RIGHT), (BstModificationResult)BstModificationResult.rebalancingChange((BstNode)root, null));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, N extends BstNode<K, N>> BstMutationResult<K, N> extractMax(N root, BstNodeFactory<N> nodeFactory, BstBalancePolicy<N> balancePolicy) {
/* 183 */     Preconditions.checkNotNull(root);
/* 184 */     Preconditions.checkNotNull(nodeFactory);
/* 185 */     Preconditions.checkNotNull(balancePolicy);
/* 186 */     if (root.hasChild(BstSide.RIGHT)) {
/* 187 */       BstMutationResult<K, N> subResult = extractMax((N)root.getChild(BstSide.RIGHT), nodeFactory, balancePolicy);
/*     */       
/* 189 */       return subResult.lift(root, BstSide.RIGHT, nodeFactory, balancePolicy);
/*     */     } 
/* 191 */     return BstMutationResult.mutationResult((K)root.getKey(), root, (N)root.childOrNull(BstSide.LEFT), (BstModificationResult)BstModificationResult.rebalancingChange((BstNode)root, null));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <N extends BstNode<?, N>> N insertMin(@Nullable N root, N entry, BstNodeFactory<N> nodeFactory, BstBalancePolicy<N> balancePolicy) {
/* 201 */     Preconditions.checkNotNull(entry);
/* 202 */     Preconditions.checkNotNull(nodeFactory);
/* 203 */     Preconditions.checkNotNull(balancePolicy);
/* 204 */     if (root == null) {
/* 205 */       return nodeFactory.createLeaf(entry);
/*     */     }
/* 207 */     return balancePolicy.balance(nodeFactory, root, insertMin((N)root.childOrNull(BstSide.LEFT), entry, nodeFactory, balancePolicy), (N)root.childOrNull(BstSide.RIGHT));
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
/*     */   public static <N extends BstNode<?, N>> N insertMax(@Nullable N root, N entry, BstNodeFactory<N> nodeFactory, BstBalancePolicy<N> balancePolicy) {
/* 219 */     Preconditions.checkNotNull(entry);
/* 220 */     Preconditions.checkNotNull(nodeFactory);
/* 221 */     Preconditions.checkNotNull(balancePolicy);
/* 222 */     if (root == null) {
/* 223 */       return nodeFactory.createLeaf(entry);
/*     */     }
/* 225 */     return balancePolicy.balance(nodeFactory, root, (N)root.childOrNull(BstSide.LEFT), insertMax((N)root.childOrNull(BstSide.RIGHT), entry, nodeFactory, balancePolicy));
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\BstOperations.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
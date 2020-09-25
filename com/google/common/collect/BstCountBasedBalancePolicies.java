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
/*     */ 
/*     */ 
/*     */ @GwtCompatible
/*     */ final class BstCountBasedBalancePolicies
/*     */ {
/*     */   private static final int SINGLE_ROTATE_RATIO = 4;
/*     */   private static final int SECOND_ROTATE_RATIO = 2;
/*     */   
/*     */   public static <N extends BstNode<?, N>> BstBalancePolicy<N> noRebalancePolicy(final BstAggregate<N> countAggregate) {
/*  48 */     Preconditions.checkNotNull(countAggregate);
/*  49 */     return new BstBalancePolicy<N>()
/*     */       {
/*     */         public N balance(BstNodeFactory<N> nodeFactory, N source, @Nullable N left, @Nullable N right)
/*     */         {
/*  53 */           return (N)((BstNodeFactory)Preconditions.checkNotNull(nodeFactory)).createNode(source, left, right);
/*     */         }
/*     */ 
/*     */         
/*     */         @Nullable
/*     */         public N combine(BstNodeFactory<N> nodeFactory, @Nullable N left, @Nullable N right) {
/*  59 */           if (left == null)
/*  60 */             return right; 
/*  61 */           if (right == null)
/*  62 */             return left; 
/*  63 */           if (countAggregate.treeValue(left) > countAggregate.treeValue(right)) {
/*  64 */             return nodeFactory.createNode(left, (N)left.childOrNull(BstSide.LEFT), combine(nodeFactory, (N)left.childOrNull(BstSide.RIGHT), right));
/*     */           }
/*     */           
/*  67 */           return nodeFactory.createNode(right, combine(nodeFactory, left, (N)right.childOrNull(BstSide.LEFT)), (N)right.childOrNull(BstSide.RIGHT));
/*     */         }
/*     */       };
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
/*     */   public static <K, N extends BstNode<K, N>> BstBalancePolicy<N> singleRebalancePolicy(final BstAggregate<N> countAggregate) {
/*  81 */     Preconditions.checkNotNull(countAggregate);
/*  82 */     return new BstBalancePolicy<N>()
/*     */       {
/*     */         public N balance(BstNodeFactory<N> nodeFactory, N source, @Nullable N left, @Nullable N right)
/*     */         {
/*  86 */           int countL = countAggregate.treeValue(left);
/*  87 */           int countR = countAggregate.treeValue(right);
/*  88 */           if (countL + countR > 1) {
/*  89 */             if (countR >= 4 * countL)
/*  90 */               return rotateL(nodeFactory, source, left, right); 
/*  91 */             if (countL >= 4 * countR) {
/*  92 */               return rotateR(nodeFactory, source, left, right);
/*     */             }
/*     */           } 
/*  95 */           return nodeFactory.createNode(source, left, right);
/*     */         }
/*     */         
/*     */         private N rotateL(BstNodeFactory<N> nodeFactory, N source, @Nullable N left, N right) {
/*  99 */           Preconditions.checkNotNull(right);
/* 100 */           N rl = (N)right.childOrNull(BstSide.LEFT);
/* 101 */           N rr = (N)right.childOrNull(BstSide.RIGHT);
/* 102 */           if (countAggregate.treeValue(rl) >= 2 * countAggregate.treeValue(rr)) {
/* 103 */             right = singleR(nodeFactory, right, rl, rr);
/*     */           }
/* 105 */           return singleL(nodeFactory, source, left, right);
/*     */         }
/*     */         
/*     */         private N rotateR(BstNodeFactory<N> nodeFactory, N source, N left, @Nullable N right) {
/* 109 */           Preconditions.checkNotNull(left);
/* 110 */           N lr = (N)left.childOrNull(BstSide.RIGHT);
/* 111 */           N ll = (N)left.childOrNull(BstSide.LEFT);
/* 112 */           if (countAggregate.treeValue(lr) >= 2 * countAggregate.treeValue(ll)) {
/* 113 */             left = singleL(nodeFactory, left, ll, lr);
/*     */           }
/* 115 */           return singleR(nodeFactory, source, left, right);
/*     */         }
/*     */         
/*     */         private N singleL(BstNodeFactory<N> nodeFactory, N source, @Nullable N left, N right) {
/* 119 */           Preconditions.checkNotNull(right);
/* 120 */           return nodeFactory.createNode(right, nodeFactory.createNode(source, left, (N)right.childOrNull(BstSide.LEFT)), (N)right.childOrNull(BstSide.RIGHT));
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         private N singleR(BstNodeFactory<N> nodeFactory, N source, N left, @Nullable N right) {
/* 126 */           Preconditions.checkNotNull(left);
/* 127 */           return nodeFactory.createNode(left, (N)left.childOrNull(BstSide.LEFT), nodeFactory.createNode(source, (N)left.childOrNull(BstSide.RIGHT), right));
/*     */         }
/*     */ 
/*     */         
/*     */         @Nullable
/*     */         public N combine(BstNodeFactory<N> nodeFactory, @Nullable N left, @Nullable N right) {
/*     */           N newRootSource;
/* 134 */           if (left == null)
/* 135 */             return right; 
/* 136 */           if (right == null) {
/* 137 */             return left;
/*     */           }
/*     */           
/* 140 */           if (countAggregate.treeValue(left) > countAggregate.treeValue(right)) {
/* 141 */             BstMutationResult<K, N> extractLeftMax = (BstMutationResult)BstOperations.extractMax((BstNode)left, (BstNodeFactory)nodeFactory, this);
/* 142 */             newRootSource = extractLeftMax.getOriginalTarget();
/* 143 */             left = extractLeftMax.getChangedRoot();
/*     */           } else {
/* 145 */             BstMutationResult<K, N> extractRightMin = (BstMutationResult)BstOperations.extractMin((BstNode)right, (BstNodeFactory)nodeFactory, this);
/* 146 */             newRootSource = extractRightMin.getOriginalTarget();
/* 147 */             right = extractRightMin.getChangedRoot();
/*     */           } 
/* 149 */           return nodeFactory.createNode(newRootSource, left, right);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, N extends BstNode<K, N>> BstBalancePolicy<N> fullRebalancePolicy(final BstAggregate<N> countAggregate) {
/* 161 */     Preconditions.checkNotNull(countAggregate);
/* 162 */     final BstBalancePolicy<N> singleBalancePolicy = singleRebalancePolicy(countAggregate);
/*     */     
/* 164 */     return new BstBalancePolicy<N>()
/*     */       {
/*     */         public N balance(BstNodeFactory<N> nodeFactory, N source, @Nullable N left, @Nullable N right)
/*     */         {
/* 168 */           if (left == null)
/* 169 */             return (N)BstOperations.insertMin((BstNode)right, (BstNode)source, (BstNodeFactory)nodeFactory, singleBalancePolicy); 
/* 170 */           if (right == null) {
/* 171 */             return (N)BstOperations.insertMax((BstNode)left, (BstNode)source, (BstNodeFactory)nodeFactory, singleBalancePolicy);
/*     */           }
/* 173 */           int countL = countAggregate.treeValue(left);
/* 174 */           int countR = countAggregate.treeValue(right);
/* 175 */           if (4 * countL <= countR) {
/* 176 */             N resultLeft = balance(nodeFactory, source, left, (N)right.childOrNull(BstSide.LEFT));
/* 177 */             return (N)singleBalancePolicy.balance(nodeFactory, right, resultLeft, right.childOrNull(BstSide.RIGHT));
/*     */           } 
/* 179 */           if (4 * countR <= countL) {
/* 180 */             N resultRight = balance(nodeFactory, source, (N)left.childOrNull(BstSide.RIGHT), right);
/* 181 */             return (N)singleBalancePolicy.balance(nodeFactory, left, left.childOrNull(BstSide.LEFT), resultRight);
/*     */           } 
/*     */           
/* 184 */           return nodeFactory.createNode(source, left, right);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         @Nullable
/*     */         public N combine(BstNodeFactory<N> nodeFactory, @Nullable N left, @Nullable N right) {
/* 191 */           if (left == null)
/* 192 */             return right; 
/* 193 */           if (right == null) {
/* 194 */             return left;
/*     */           }
/* 196 */           int countL = countAggregate.treeValue(left);
/* 197 */           int countR = countAggregate.treeValue(right);
/* 198 */           if (4 * countL <= countR) {
/* 199 */             N resultLeft = combine(nodeFactory, left, (N)right.childOrNull(BstSide.LEFT));
/* 200 */             return (N)singleBalancePolicy.balance(nodeFactory, right, resultLeft, right.childOrNull(BstSide.RIGHT));
/*     */           } 
/* 202 */           if (4 * countR <= countL) {
/* 203 */             N resultRight = combine(nodeFactory, (N)left.childOrNull(BstSide.RIGHT), right);
/* 204 */             return (N)singleBalancePolicy.balance(nodeFactory, left, left.childOrNull(BstSide.LEFT), resultRight);
/*     */           } 
/*     */           
/* 207 */           return (N)singleBalancePolicy.combine(nodeFactory, left, right);
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\BstCountBasedBalancePolicies.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
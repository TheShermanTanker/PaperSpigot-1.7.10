/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.base.Optional;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.util.NoSuchElementException;
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
/*     */ @GwtCompatible
/*     */ final class BstInOrderPath<N extends BstNode<?, N>>
/*     */   extends BstPath<N, BstInOrderPath<N>>
/*     */ {
/*     */   private final BstSide sideExtension;
/*     */   private transient Optional<BstInOrderPath<N>> prevInOrder;
/*     */   private transient Optional<BstInOrderPath<N>> nextInOrder;
/*     */   
/*     */   public static <N extends BstNode<?, N>> BstPathFactory<N, BstInOrderPath<N>> inOrderFactory() {
/*  37 */     return new BstPathFactory<N, BstInOrderPath<N>>()
/*     */       {
/*     */         public BstInOrderPath<N> extension(BstInOrderPath<N> path, BstSide side) {
/*  40 */           return BstInOrderPath.extension(path, side);
/*     */         }
/*     */ 
/*     */         
/*     */         public BstInOrderPath<N> initialPath(N root) {
/*  45 */           return (BstInOrderPath)new BstInOrderPath<BstNode>((BstNode)root, null, null);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   private static <N extends BstNode<?, N>> BstInOrderPath<N> extension(BstInOrderPath<N> path, BstSide side) {
/*  52 */     Preconditions.checkNotNull(path);
/*  53 */     N tip = path.getTip();
/*  54 */     return new BstInOrderPath<N>((N)tip.getChild(side), side, path);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BstInOrderPath(N tip, @Nullable BstSide sideExtension, @Nullable BstInOrderPath<N> tail) {
/*  63 */     super(tip, tail);
/*  64 */     this.sideExtension = sideExtension;
/*     */     assert false;
/*     */   }
/*     */   
/*     */   private Optional<BstInOrderPath<N>> computeNextInOrder(BstSide side) {
/*  69 */     if (getTip().hasChild(side)) {
/*  70 */       BstInOrderPath<N> path = extension(this, side);
/*  71 */       BstSide otherSide = side.other();
/*  72 */       while (path.getTip().hasChild(otherSide)) {
/*  73 */         path = extension(path, otherSide);
/*     */       }
/*  75 */       return Optional.of(path);
/*     */     } 
/*  77 */     BstInOrderPath<N> current = this;
/*  78 */     while (current.sideExtension == side) {
/*  79 */       current = current.getPrefix();
/*     */     }
/*  81 */     current = current.prefixOrNull();
/*  82 */     return Optional.fromNullable(current);
/*     */   }
/*     */ 
/*     */   
/*     */   private Optional<BstInOrderPath<N>> nextInOrder(BstSide side) {
/*     */     Optional<BstInOrderPath<N>> result;
/*  88 */     switch (side) {
/*     */       case LEFT:
/*  90 */         result = this.prevInOrder;
/*  91 */         return (result == null) ? (this.prevInOrder = computeNextInOrder(side)) : result;
/*     */       case RIGHT:
/*  93 */         result = this.nextInOrder;
/*  94 */         return (result == null) ? (this.nextInOrder = computeNextInOrder(side)) : result;
/*     */     } 
/*  96 */     throw new AssertionError();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasNext(BstSide side) {
/* 104 */     return nextInOrder(side).isPresent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BstInOrderPath<N> next(BstSide side) {
/* 113 */     if (!hasNext(side)) {
/* 114 */       throw new NoSuchElementException();
/*     */     }
/* 116 */     return (BstInOrderPath<N>)nextInOrder(side).get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BstSide getSideOfExtension() {
/* 124 */     return this.sideExtension;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\BstInOrderPath.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
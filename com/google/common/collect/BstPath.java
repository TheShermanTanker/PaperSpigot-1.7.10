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
/*    */ abstract class BstPath<N extends BstNode<?, N>, P extends BstPath<N, P>>
/*    */ {
/*    */   private final N tip;
/*    */   @Nullable
/*    */   private final P prefix;
/*    */   
/*    */   BstPath(N tip, @Nullable P prefix) {
/* 38 */     this.tip = (N)Preconditions.checkNotNull(tip);
/* 39 */     this.prefix = prefix;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final N getTip() {
/* 46 */     return this.tip;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final boolean hasPrefix() {
/* 53 */     return (this.prefix != null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public final P prefixOrNull() {
/* 62 */     return this.prefix;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final P getPrefix() {
/* 71 */     Preconditions.checkState(hasPrefix());
/* 72 */     return this.prefix;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\BstPath.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
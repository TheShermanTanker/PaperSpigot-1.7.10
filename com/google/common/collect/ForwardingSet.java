/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.annotations.Beta;
/*    */ import com.google.common.annotations.GwtCompatible;
/*    */ import java.util.Collection;
/*    */ import java.util.Set;
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
/*    */ public abstract class ForwardingSet<E>
/*    */   extends ForwardingCollection<E>
/*    */   implements Set<E>
/*    */ {
/*    */   public boolean equals(@Nullable Object object) {
/* 57 */     return (object == this || delegate().equals(object));
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 61 */     return delegate().hashCode();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Beta
/*    */   protected boolean standardEquals(@Nullable Object object) {
/* 72 */     return Sets.equalsImpl(this, object);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Beta
/*    */   protected int standardHashCode() {
/* 83 */     return Sets.hashCodeImpl(this);
/*    */   }
/*    */   
/*    */   protected abstract Set<E> delegate();
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\ForwardingSet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
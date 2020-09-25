/*    */ package net.minecraft.util.com.google.common.collect;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.util.com.google.common.annotations.GwtCompatible;
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
/*    */ @GwtCompatible(serializable = true)
/*    */ final class AllEqualOrdering
/*    */   extends Ordering<Object>
/*    */   implements Serializable
/*    */ {
/* 33 */   static final AllEqualOrdering INSTANCE = new AllEqualOrdering();
/*    */   private static final long serialVersionUID = 0L;
/*    */   
/*    */   public int compare(@Nullable Object left, @Nullable Object right) {
/* 37 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public <E> List<E> sortedCopy(Iterable<E> iterable) {
/* 42 */     return Lists.newArrayList(iterable);
/*    */   }
/*    */ 
/*    */   
/*    */   public <E> ImmutableList<E> immutableSortedCopy(Iterable<E> iterable) {
/* 47 */     return ImmutableList.copyOf(iterable);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public <S> Ordering<S> reverse() {
/* 53 */     return this;
/*    */   }
/*    */   
/*    */   private Object readResolve() {
/* 57 */     return INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 62 */     return "Ordering.allEqual()";
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\collect\AllEqualOrdering.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
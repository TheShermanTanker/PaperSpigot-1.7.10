/*    */ package net.minecraft.util.com.google.common.collect;
/*    */ 
/*    */ import java.util.ListIterator;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ @GwtCompatible
/*    */ abstract class TransformedListIterator<F, T>
/*    */   extends TransformedIterator<F, T>
/*    */   implements ListIterator<T>
/*    */ {
/*    */   TransformedListIterator(ListIterator<? extends F> backingIterator) {
/* 35 */     super(backingIterator);
/*    */   }
/*    */   
/*    */   private ListIterator<? extends F> backingIterator() {
/* 39 */     return Iterators.cast(this.backingIterator);
/*    */   }
/*    */ 
/*    */   
/*    */   public final boolean hasPrevious() {
/* 44 */     return backingIterator().hasPrevious();
/*    */   }
/*    */ 
/*    */   
/*    */   public final T previous() {
/* 49 */     return transform(backingIterator().previous());
/*    */   }
/*    */ 
/*    */   
/*    */   public final int nextIndex() {
/* 54 */     return backingIterator().nextIndex();
/*    */   }
/*    */ 
/*    */   
/*    */   public final int previousIndex() {
/* 59 */     return backingIterator().previousIndex();
/*    */   }
/*    */ 
/*    */   
/*    */   public void set(T element) {
/* 64 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ 
/*    */   
/*    */   public void add(T element) {
/* 69 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\collect\TransformedListIterator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package net.minecraft.util.com.google.common.collect;
/*    */ 
/*    */ import java.util.ListIterator;
/*    */ import net.minecraft.util.com.google.common.annotations.GwtCompatible;
/*    */ import net.minecraft.util.com.google.common.annotations.GwtIncompatible;
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
/*    */ @GwtCompatible(emulated = true)
/*    */ class RegularImmutableAsList<E>
/*    */   extends ImmutableAsList<E>
/*    */ {
/*    */   private final ImmutableCollection<E> delegate;
/*    */   private final ImmutableList<? extends E> delegateList;
/*    */   
/*    */   RegularImmutableAsList(ImmutableCollection<E> delegate, ImmutableList<? extends E> delegateList) {
/* 35 */     this.delegate = delegate;
/* 36 */     this.delegateList = delegateList;
/*    */   }
/*    */   
/*    */   RegularImmutableAsList(ImmutableCollection<E> delegate, Object[] array) {
/* 40 */     this(delegate, ImmutableList.asImmutableList(array));
/*    */   }
/*    */ 
/*    */   
/*    */   ImmutableCollection<E> delegateCollection() {
/* 45 */     return this.delegate;
/*    */   }
/*    */   
/*    */   ImmutableList<? extends E> delegateList() {
/* 49 */     return this.delegateList;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public UnmodifiableListIterator<E> listIterator(int index) {
/* 55 */     return (UnmodifiableListIterator)this.delegateList.listIterator(index);
/*    */   }
/*    */ 
/*    */   
/*    */   @GwtIncompatible("not present in emulated superclass")
/*    */   int copyIntoArray(Object[] dst, int offset) {
/* 61 */     return this.delegateList.copyIntoArray(dst, offset);
/*    */   }
/*    */ 
/*    */   
/*    */   public E get(int index) {
/* 66 */     return this.delegateList.get(index);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\collect\RegularImmutableAsList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
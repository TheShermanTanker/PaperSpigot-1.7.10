/*    */ package net.minecraft.util.com.google.common.collect;
/*    */ 
/*    */ import java.io.InvalidObjectException;
/*    */ import java.io.ObjectInputStream;
/*    */ import java.io.Serializable;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @GwtCompatible(serializable = true, emulated = true)
/*    */ abstract class ImmutableAsList<E>
/*    */   extends ImmutableList<E>
/*    */ {
/*    */   abstract ImmutableCollection<E> delegateCollection();
/*    */   
/*    */   public boolean contains(Object target) {
/* 41 */     return delegateCollection().contains(target);
/*    */   }
/*    */ 
/*    */   
/*    */   public int size() {
/* 46 */     return delegateCollection().size();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isEmpty() {
/* 51 */     return delegateCollection().isEmpty();
/*    */   }
/*    */ 
/*    */   
/*    */   boolean isPartialView() {
/* 56 */     return delegateCollection().isPartialView();
/*    */   }
/*    */   
/*    */   @GwtIncompatible("serialization")
/*    */   static class SerializedForm
/*    */     implements Serializable {
/*    */     final ImmutableCollection<?> collection;
/*    */     private static final long serialVersionUID = 0L;
/*    */     
/*    */     SerializedForm(ImmutableCollection<?> collection) {
/* 66 */       this.collection = collection;
/*    */     }
/*    */     Object readResolve() {
/* 69 */       return this.collection.asList();
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @GwtIncompatible("serialization")
/*    */   private void readObject(ObjectInputStream stream) throws InvalidObjectException {
/* 77 */     throw new InvalidObjectException("Use SerializedForm");
/*    */   }
/*    */   
/*    */   @GwtIncompatible("serialization")
/*    */   Object writeReplace() {
/* 82 */     return new SerializedForm(delegateCollection());
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\collect\ImmutableAsList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
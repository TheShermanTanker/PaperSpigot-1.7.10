/*    */ package net.minecraft.util.com.google.common.collect;
/*    */ 
/*    */ import java.util.Iterator;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @GwtCompatible
/*    */ public abstract class ForwardingIterator<T>
/*    */   extends ForwardingObject
/*    */   implements Iterator<T>
/*    */ {
/*    */   public boolean hasNext() {
/* 43 */     return delegate().hasNext();
/*    */   }
/*    */ 
/*    */   
/*    */   public T next() {
/* 48 */     return delegate().next();
/*    */   }
/*    */ 
/*    */   
/*    */   public void remove() {
/* 53 */     delegate().remove();
/*    */   }
/*    */   
/*    */   protected abstract Iterator<T> delegate();
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\collect\ForwardingIterator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
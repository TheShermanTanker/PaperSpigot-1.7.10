/*    */ package net.minecraft.util.com.google.common.collect;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.Comparator;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.util.com.google.common.annotations.GwtCompatible;
/*    */ import net.minecraft.util.com.google.common.base.Preconditions;
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
/*    */ final class ComparatorOrdering<T>
/*    */   extends Ordering<T>
/*    */   implements Serializable
/*    */ {
/*    */   final Comparator<T> comparator;
/*    */   private static final long serialVersionUID = 0L;
/*    */   
/*    */   ComparatorOrdering(Comparator<T> comparator) {
/* 34 */     this.comparator = (Comparator<T>)Preconditions.checkNotNull(comparator);
/*    */   }
/*    */   
/*    */   public int compare(T a, T b) {
/* 38 */     return this.comparator.compare(a, b);
/*    */   }
/*    */   
/*    */   public boolean equals(@Nullable Object object) {
/* 42 */     if (object == this) {
/* 43 */       return true;
/*    */     }
/* 45 */     if (object instanceof ComparatorOrdering) {
/* 46 */       ComparatorOrdering<?> that = (ComparatorOrdering)object;
/* 47 */       return this.comparator.equals(that.comparator);
/*    */     } 
/* 49 */     return false;
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 53 */     return this.comparator.hashCode();
/*    */   }
/*    */   
/*    */   public String toString() {
/* 57 */     return this.comparator.toString();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\collect\ComparatorOrdering.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package net.minecraft.util.com.google.common.collect;
/*    */ 
/*    */ import java.io.Serializable;
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
/*    */ 
/*    */ 
/*    */ @GwtCompatible(serializable = true)
/*    */ final class NaturalOrdering
/*    */   extends Ordering<Comparable>
/*    */   implements Serializable
/*    */ {
/* 30 */   static final NaturalOrdering INSTANCE = new NaturalOrdering();
/*    */   
/*    */   public int compare(Comparable<Comparable> left, Comparable right) {
/* 33 */     Preconditions.checkNotNull(left);
/* 34 */     Preconditions.checkNotNull(right);
/* 35 */     return left.compareTo(right);
/*    */   }
/*    */   private static final long serialVersionUID = 0L;
/*    */   public <S extends Comparable> Ordering<S> reverse() {
/* 39 */     return ReverseNaturalOrdering.INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   private Object readResolve() {
/* 44 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 48 */     return "Ordering.natural()";
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\collect\NaturalOrdering.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
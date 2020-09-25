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
/*    */ @GwtCompatible(serializable = true)
/*    */ final class ExplicitOrdering<T>
/*    */   extends Ordering<T>
/*    */   implements Serializable
/*    */ {
/*    */   final ImmutableMap<T, Integer> rankMap;
/*    */   private static final long serialVersionUID = 0L;
/*    */   
/*    */   ExplicitOrdering(List<T> valuesInOrder) {
/* 32 */     this(buildRankMap(valuesInOrder));
/*    */   }
/*    */   
/*    */   ExplicitOrdering(ImmutableMap<T, Integer> rankMap) {
/* 36 */     this.rankMap = rankMap;
/*    */   }
/*    */   
/*    */   public int compare(T left, T right) {
/* 40 */     return rank(left) - rank(right);
/*    */   }
/*    */   
/*    */   private int rank(T value) {
/* 44 */     Integer rank = this.rankMap.get(value);
/* 45 */     if (rank == null) {
/* 46 */       throw new Ordering.IncomparableValueException(value);
/*    */     }
/* 48 */     return rank.intValue();
/*    */   }
/*    */ 
/*    */   
/*    */   private static <T> ImmutableMap<T, Integer> buildRankMap(List<T> valuesInOrder) {
/* 53 */     ImmutableMap.Builder<T, Integer> builder = ImmutableMap.builder();
/* 54 */     int rank = 0;
/* 55 */     for (T value : valuesInOrder) {
/* 56 */       builder.put(value, Integer.valueOf(rank++));
/*    */     }
/* 58 */     return builder.build();
/*    */   }
/*    */   
/*    */   public boolean equals(@Nullable Object object) {
/* 62 */     if (object instanceof ExplicitOrdering) {
/* 63 */       ExplicitOrdering<?> that = (ExplicitOrdering)object;
/* 64 */       return this.rankMap.equals(that.rankMap);
/*    */     } 
/* 66 */     return false;
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 70 */     return this.rankMap.hashCode();
/*    */   }
/*    */   
/*    */   public String toString() {
/* 74 */     return "Ordering.explicit(" + this.rankMap.keySet() + ")";
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\collect\ExplicitOrdering.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package com.avaje.ebeaninternal.server.el;
/*    */ 
/*    */ import java.util.Comparator;
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
/*    */ public final class ElComparatorProperty<T>
/*    */   implements Comparator<T>, ElComparator<T>
/*    */ {
/*    */   private final ElPropertyValue elGetValue;
/*    */   private final int nullOrder;
/*    */   private final int asc;
/*    */   
/*    */   public ElComparatorProperty(ElPropertyValue elGetValue, boolean ascending, boolean nullsHigh) {
/* 36 */     this.elGetValue = elGetValue;
/* 37 */     this.asc = ascending ? 1 : -1;
/* 38 */     this.nullOrder = this.asc * (nullsHigh ? 1 : -1);
/*    */   }
/*    */ 
/*    */   
/*    */   public int compare(T o1, T o2) {
/* 43 */     Object val1 = this.elGetValue.elGetValue(o1);
/* 44 */     Object val2 = this.elGetValue.elGetValue(o2);
/*    */     
/* 46 */     return compareValues(val1, val2);
/*    */   }
/*    */ 
/*    */   
/*    */   public int compareValue(Object value, T o2) {
/* 51 */     Object val2 = this.elGetValue.elGetValue(o2);
/*    */     
/* 53 */     return compareValues(value, val2);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int compareValues(Object val1, Object val2) {
/* 59 */     if (val1 == null) {
/* 60 */       return (val2 == null) ? 0 : this.nullOrder;
/*    */     }
/* 62 */     if (val2 == null) {
/* 63 */       return -1 * this.nullOrder;
/*    */     }
/* 65 */     Comparable<Object> c = (Comparable)val1;
/* 66 */     return this.asc * c.compareTo(val2);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\el\ElComparatorProperty.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
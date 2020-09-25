/*     */ package net.minecraft.util.org.apache.commons.lang3.tuple;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import net.minecraft.util.org.apache.commons.lang3.ObjectUtils;
/*     */ import net.minecraft.util.org.apache.commons.lang3.builder.CompareToBuilder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Triple<L, M, R>
/*     */   implements Comparable<Triple<L, M, R>>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   
/*     */   public static <L, M, R> Triple<L, M, R> of(L left, M middle, R right) {
/*  61 */     return new ImmutableTriple<L, M, R>(left, middle, right);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int compareTo(Triple<L, M, R> other) {
/*  97 */     return (new CompareToBuilder()).append(getLeft(), other.getLeft()).append(getMiddle(), other.getMiddle()).append(getRight(), other.getRight()).toComparison();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 110 */     if (obj == this) {
/* 111 */       return true;
/*     */     }
/* 113 */     if (obj instanceof Triple) {
/* 114 */       Triple<?, ?, ?> other = (Triple<?, ?, ?>)obj;
/* 115 */       return (ObjectUtils.equals(getLeft(), other.getLeft()) && ObjectUtils.equals(getMiddle(), other.getMiddle()) && ObjectUtils.equals(getRight(), other.getRight()));
/*     */     } 
/*     */ 
/*     */     
/* 119 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 129 */     return ((getLeft() == null) ? 0 : getLeft().hashCode()) ^ ((getMiddle() == null) ? 0 : getMiddle().hashCode()) ^ ((getRight() == null) ? 0 : getRight().hashCode());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 141 */     return '(' + getLeft() + ',' + getMiddle() + ',' + getRight() + ')';
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString(String format) {
/* 157 */     return String.format(format, new Object[] { getLeft(), getMiddle(), getRight() });
/*     */   }
/*     */   
/*     */   public abstract L getLeft();
/*     */   
/*     */   public abstract M getMiddle();
/*     */   
/*     */   public abstract R getRight();
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\org\apache\commons\lang3\tuple\Triple.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
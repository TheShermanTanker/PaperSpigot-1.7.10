/*    */ package net.minecraft.util.gnu.trove.impl.unmodifiable;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import net.minecraft.util.gnu.trove.TCharCollection;
/*    */ import net.minecraft.util.gnu.trove.set.TCharSet;
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
/*    */ public class TUnmodifiableCharSet
/*    */   extends TUnmodifiableCharCollection
/*    */   implements TCharSet, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -9215047833775013803L;
/*    */   
/*    */   public TUnmodifiableCharSet(TCharSet s) {
/* 57 */     super((TCharCollection)s);
/* 58 */   } public boolean equals(Object o) { return (o == this || this.c.equals(o)); } public int hashCode() {
/* 59 */     return this.c.hashCode();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\imp\\unmodifiable\TUnmodifiableCharSet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
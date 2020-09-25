/*    */ package net.minecraft.util.gnu.trove.impl.unmodifiable;
/*    */ 
/*    */ import java.util.RandomAccess;
/*    */ import net.minecraft.util.gnu.trove.list.TCharList;
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
/*    */ 
/*    */ 
/*    */ public class TUnmodifiableRandomAccessCharList
/*    */   extends TUnmodifiableCharList
/*    */   implements RandomAccess
/*    */ {
/*    */   private static final long serialVersionUID = -2542308836966382001L;
/*    */   
/*    */   public TUnmodifiableRandomAccessCharList(TCharList list) {
/* 58 */     super(list);
/*    */   }
/*    */   
/*    */   public TCharList subList(int fromIndex, int toIndex) {
/* 62 */     return new TUnmodifiableRandomAccessCharList(this.list.subList(fromIndex, toIndex));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private Object writeReplace() {
/* 72 */     return new TUnmodifiableCharList(this.list);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\imp\\unmodifiable\TUnmodifiableRandomAccessCharList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
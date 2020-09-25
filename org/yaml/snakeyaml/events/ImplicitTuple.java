/*    */ package org.yaml.snakeyaml.events;
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
/*    */ public class ImplicitTuple
/*    */ {
/*    */   private final boolean plain;
/*    */   private final boolean nonPlain;
/*    */   
/*    */   public ImplicitTuple(boolean plain, boolean nonplain) {
/* 30 */     this.plain = plain;
/* 31 */     this.nonPlain = nonplain;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canOmitTagInPlainScalar() {
/* 39 */     return this.plain;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canOmitTagInNonPlainScalar() {
/* 47 */     return this.nonPlain;
/*    */   }
/*    */   
/*    */   public boolean bothFalse() {
/* 51 */     return (!this.plain && !this.nonPlain);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 56 */     return "implicit=[" + this.plain + ", " + this.nonPlain + "]";
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\yaml\snakeyaml\events\ImplicitTuple.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
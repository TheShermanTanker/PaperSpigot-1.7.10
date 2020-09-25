/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FurnaceAndDispenser
/*    */   extends DirectionalContainer
/*    */ {
/*    */   @Deprecated
/*    */   public FurnaceAndDispenser(int type) {
/* 16 */     super(type);
/*    */   }
/*    */   
/*    */   public FurnaceAndDispenser(Material type) {
/* 20 */     super(type);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public FurnaceAndDispenser(int type, byte data) {
/* 29 */     super(type, data);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public FurnaceAndDispenser(Material type, byte data) {
/* 38 */     super(type, data);
/*    */   }
/*    */ 
/*    */   
/*    */   public FurnaceAndDispenser clone() {
/* 43 */     return (FurnaceAndDispenser)super.clone();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\FurnaceAndDispenser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
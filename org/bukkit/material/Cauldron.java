/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ 
/*    */ 
/*    */ public class Cauldron
/*    */   extends MaterialData
/*    */ {
/*    */   private static final int CAULDRON_FULL = 3;
/*    */   private static final int CAULDRON_EMPTY = 0;
/*    */   
/*    */   public Cauldron() {
/* 13 */     super(Material.CAULDRON);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public Cauldron(int type, byte data) {
/* 22 */     super(type, data);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public Cauldron(byte data) {
/* 31 */     super(Material.CAULDRON, data);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isFull() {
/* 40 */     return (getData() >= 3);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isEmpty() {
/* 49 */     return (getData() <= 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 54 */     return (isEmpty() ? "EMPTY" : (isFull() ? "FULL" : (getData() + "/3 FULL"))) + " CAULDRON";
/*    */   }
/*    */ 
/*    */   
/*    */   public Cauldron clone() {
/* 59 */     return (Cauldron)super.clone();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\Cauldron.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ 
/*    */ public class Cake extends MaterialData {
/*    */   public Cake() {
/*  7 */     super(Material.CAKE_BLOCK);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public Cake(int type) {
/* 16 */     super(type);
/*    */   }
/*    */   
/*    */   public Cake(Material type) {
/* 20 */     super(type);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public Cake(int type, byte data) {
/* 29 */     super(type, data);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public Cake(Material type, byte data) {
/* 38 */     super(type, data);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getSlicesEaten() {
/* 47 */     return getData();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getSlicesRemaining() {
/* 56 */     return 6 - getData();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setSlicesEaten(int n) {
/* 65 */     if (n < 6) {
/* 66 */       setData((byte)n);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setSlicesRemaining(int n) {
/* 76 */     if (n > 6) {
/* 77 */       n = 6;
/*    */     }
/* 79 */     setData((byte)(6 - n));
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 84 */     return super.toString() + " " + getSlicesEaten() + "/" + getSlicesRemaining() + " slices eaten/remaining";
/*    */   }
/*    */ 
/*    */   
/*    */   public Cake clone() {
/* 89 */     return (Cake)super.clone();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\Cake.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
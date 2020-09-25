/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ 
/*    */ public class PressurePlate
/*    */   extends MaterialData
/*    */   implements PressureSensor
/*    */ {
/*    */   public PressurePlate() {
/* 10 */     super(Material.WOOD_PLATE);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public PressurePlate(int type) {
/* 19 */     super(type);
/*    */   }
/*    */   
/*    */   public PressurePlate(Material type) {
/* 23 */     super(type);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public PressurePlate(int type, byte data) {
/* 32 */     super(type, data);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public PressurePlate(Material type, byte data) {
/* 41 */     super(type, data);
/*    */   }
/*    */   
/*    */   public boolean isPressed() {
/* 45 */     return (getData() == 1);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 50 */     return super.toString() + (isPressed() ? " PRESSED" : "");
/*    */   }
/*    */ 
/*    */   
/*    */   public PressurePlate clone() {
/* 55 */     return (PressurePlate)super.clone();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\PressurePlate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
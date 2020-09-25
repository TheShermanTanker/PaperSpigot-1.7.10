/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.CropState;
/*    */ import org.bukkit.Material;
/*    */ 
/*    */ 
/*    */ public class Crops
/*    */   extends MaterialData
/*    */ {
/*    */   public Crops() {
/* 11 */     super(Material.CROPS);
/*    */   }
/*    */   
/*    */   public Crops(CropState state) {
/* 15 */     this();
/* 16 */     setState(state);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public Crops(int type) {
/* 25 */     super(type);
/*    */   }
/*    */   
/*    */   public Crops(Material type) {
/* 29 */     super(type);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public Crops(int type, byte data) {
/* 38 */     super(type, data);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public Crops(Material type, byte data) {
/* 47 */     super(type, data);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CropState getState() {
/* 56 */     return CropState.getByData(getData());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setState(CropState state) {
/* 65 */     setData(state.getData());
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 70 */     return getState() + " " + super.toString();
/*    */   }
/*    */ 
/*    */   
/*    */   public Crops clone() {
/* 75 */     return (Crops)super.clone();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\Crops.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
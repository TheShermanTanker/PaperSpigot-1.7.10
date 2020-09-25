/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.DyeColor;
/*    */ import org.bukkit.Material;
/*    */ 
/*    */ public class Dye
/*    */   extends MaterialData
/*    */   implements Colorable
/*    */ {
/*    */   public Dye() {
/* 11 */     super(Material.INK_SACK);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public Dye(int type) {
/* 20 */     super(type);
/*    */   }
/*    */   
/*    */   public Dye(Material type) {
/* 24 */     super(type);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public Dye(int type, byte data) {
/* 33 */     super(type, data);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public Dye(Material type, byte data) {
/* 42 */     super(type, data);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DyeColor getColor() {
/* 51 */     return DyeColor.getByDyeData(getData());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setColor(DyeColor color) {
/* 60 */     setData(color.getDyeData());
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 65 */     return getColor() + " DYE(" + getData() + ")";
/*    */   }
/*    */ 
/*    */   
/*    */   public Dye clone() {
/* 70 */     return (Dye)super.clone();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\Dye.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
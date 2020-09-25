/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.DyeColor;
/*    */ import org.bukkit.Material;
/*    */ 
/*    */ public class Wool
/*    */   extends MaterialData
/*    */   implements Colorable
/*    */ {
/*    */   public Wool() {
/* 11 */     super(Material.WOOL);
/*    */   }
/*    */   
/*    */   public Wool(DyeColor color) {
/* 15 */     this();
/* 16 */     setColor(color);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public Wool(int type) {
/* 25 */     super(type);
/*    */   }
/*    */   
/*    */   public Wool(Material type) {
/* 29 */     super(type);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public Wool(int type, byte data) {
/* 38 */     super(type, data);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public Wool(Material type, byte data) {
/* 47 */     super(type, data);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DyeColor getColor() {
/* 56 */     return DyeColor.getByWoolData(getData());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setColor(DyeColor color) {
/* 65 */     setData(color.getWoolData());
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 70 */     return getColor() + " " + super.toString();
/*    */   }
/*    */ 
/*    */   
/*    */   public Wool clone() {
/* 75 */     return (Wool)super.clone();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\Wool.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.SandstoneType;
/*    */ 
/*    */ 
/*    */ public class Sandstone
/*    */   extends MaterialData
/*    */ {
/*    */   public Sandstone() {
/* 11 */     super(Material.SANDSTONE);
/*    */   }
/*    */   
/*    */   public Sandstone(SandstoneType type) {
/* 15 */     this();
/* 16 */     setType(type);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public Sandstone(int type) {
/* 25 */     super(type);
/*    */   }
/*    */   
/*    */   public Sandstone(Material type) {
/* 29 */     super(type);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public Sandstone(int type, byte data) {
/* 38 */     super(type, data);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public Sandstone(Material type, byte data) {
/* 47 */     super(type, data);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SandstoneType getType() {
/* 56 */     return SandstoneType.getByData(getData());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setType(SandstoneType type) {
/* 65 */     setData(type.getData());
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 70 */     return getType() + " " + super.toString();
/*    */   }
/*    */ 
/*    */   
/*    */   public Sandstone clone() {
/* 75 */     return (Sandstone)super.clone();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\Sandstone.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
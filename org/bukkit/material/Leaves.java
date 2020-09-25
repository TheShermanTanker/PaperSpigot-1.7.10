/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.TreeSpecies;
/*    */ 
/*    */ 
/*    */ public class Leaves
/*    */   extends MaterialData
/*    */ {
/*    */   public Leaves() {
/* 11 */     super(Material.LEAVES);
/*    */   }
/*    */   
/*    */   public Leaves(TreeSpecies species) {
/* 15 */     this();
/* 16 */     setSpecies(species);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public Leaves(int type) {
/* 25 */     super(type);
/*    */   }
/*    */   
/*    */   public Leaves(Material type) {
/* 29 */     super(type);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public Leaves(int type, byte data) {
/* 38 */     super(type, data);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public Leaves(Material type, byte data) {
/* 47 */     super(type, data);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TreeSpecies getSpecies() {
/* 56 */     return TreeSpecies.getByData((byte)(getData() & 0x3));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setSpecies(TreeSpecies species) {
/* 65 */     setData(species.getData());
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 70 */     return getSpecies() + " " + super.toString();
/*    */   }
/*    */ 
/*    */   
/*    */   public Leaves clone() {
/* 75 */     return (Leaves)super.clone();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\Leaves.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
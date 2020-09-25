/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.GrassSpecies;
/*    */ import org.bukkit.Material;
/*    */ 
/*    */ 
/*    */ public class LongGrass
/*    */   extends MaterialData
/*    */ {
/*    */   public LongGrass() {
/* 11 */     super(Material.LONG_GRASS);
/*    */   }
/*    */   
/*    */   public LongGrass(GrassSpecies species) {
/* 15 */     this();
/* 16 */     setSpecies(species);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public LongGrass(int type) {
/* 25 */     super(type);
/*    */   }
/*    */   
/*    */   public LongGrass(Material type) {
/* 29 */     super(type);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public LongGrass(int type, byte data) {
/* 38 */     super(type, data);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public LongGrass(Material type, byte data) {
/* 47 */     super(type, data);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public GrassSpecies getSpecies() {
/* 56 */     return GrassSpecies.getByData(getData());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setSpecies(GrassSpecies species) {
/* 65 */     setData(species.getData());
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 70 */     return getSpecies() + " " + super.toString();
/*    */   }
/*    */ 
/*    */   
/*    */   public LongGrass clone() {
/* 75 */     return (LongGrass)super.clone();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\LongGrass.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
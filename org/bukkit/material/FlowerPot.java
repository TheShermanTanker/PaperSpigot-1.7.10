/*     */ package org.bukkit.material;
/*     */ 
/*     */ import org.bukkit.GrassSpecies;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.TreeSpecies;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FlowerPot
/*     */   extends MaterialData
/*     */ {
/*     */   public FlowerPot() {
/*  16 */     super(Material.FLOWER_POT);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public FlowerPot(int type) {
/*  25 */     super(type);
/*     */   }
/*     */   
/*     */   public FlowerPot(Material type) {
/*  29 */     super(type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public FlowerPot(int type, byte data) {
/*  38 */     super(type, data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public FlowerPot(Material type, byte data) {
/*  47 */     super(type, data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MaterialData getContents() {
/*  57 */     switch (getData()) {
/*     */       case 1:
/*  59 */         return new MaterialData(Material.RED_ROSE);
/*     */       case 2:
/*  61 */         return new MaterialData(Material.YELLOW_FLOWER);
/*     */       case 3:
/*  63 */         return new Tree(TreeSpecies.GENERIC);
/*     */       case 4:
/*  65 */         return new Tree(TreeSpecies.REDWOOD);
/*     */       case 5:
/*  67 */         return new Tree(TreeSpecies.BIRCH);
/*     */       case 6:
/*  69 */         return new Tree(TreeSpecies.JUNGLE);
/*     */       case 7:
/*  71 */         return new MaterialData(Material.RED_MUSHROOM);
/*     */       case 8:
/*  73 */         return new MaterialData(Material.BROWN_MUSHROOM);
/*     */       case 9:
/*  75 */         return new MaterialData(Material.CACTUS);
/*     */       case 10:
/*  77 */         return new MaterialData(Material.DEAD_BUSH);
/*     */       case 11:
/*  79 */         return new LongGrass(GrassSpecies.FERN_LIKE);
/*     */     } 
/*  81 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setContents(MaterialData materialData) {
/*  91 */     Material mat = materialData.getItemType();
/*     */     
/*  93 */     if (mat == Material.RED_ROSE) {
/*  94 */       setData((byte)1);
/*  95 */     } else if (mat == Material.YELLOW_FLOWER) {
/*  96 */       setData((byte)2);
/*  97 */     } else if (mat == Material.RED_MUSHROOM) {
/*  98 */       setData((byte)7);
/*  99 */     } else if (mat == Material.BROWN_MUSHROOM) {
/* 100 */       setData((byte)8);
/* 101 */     } else if (mat == Material.CACTUS) {
/* 102 */       setData((byte)9);
/* 103 */     } else if (mat == Material.DEAD_BUSH) {
/* 104 */       setData((byte)10);
/* 105 */     } else if (mat == Material.SAPLING) {
/* 106 */       TreeSpecies species = ((Tree)materialData).getSpecies();
/*     */       
/* 108 */       if (species == TreeSpecies.GENERIC) {
/* 109 */         setData((byte)3);
/* 110 */       } else if (species == TreeSpecies.REDWOOD) {
/* 111 */         setData((byte)4);
/* 112 */       } else if (species == TreeSpecies.BIRCH) {
/* 113 */         setData((byte)5);
/*     */       } else {
/* 115 */         setData((byte)6);
/*     */       } 
/* 117 */     } else if (mat == Material.LONG_GRASS) {
/* 118 */       GrassSpecies species = ((LongGrass)materialData).getSpecies();
/*     */       
/* 120 */       if (species == GrassSpecies.FERN_LIKE) {
/* 121 */         setData((byte)11);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 128 */     return super.toString() + " containing " + getContents();
/*     */   }
/*     */ 
/*     */   
/*     */   public FlowerPot clone() {
/* 133 */     return (FlowerPot)super.clone();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\FlowerPot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
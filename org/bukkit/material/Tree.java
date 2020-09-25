/*     */ package org.bukkit.material;
/*     */ 
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.TreeSpecies;
/*     */ import org.bukkit.block.BlockFace;
/*     */ 
/*     */ 
/*     */ public class Tree
/*     */   extends MaterialData
/*     */ {
/*     */   public Tree() {
/*  12 */     super(Material.LOG);
/*     */   }
/*     */   
/*     */   public Tree(TreeSpecies species) {
/*  16 */     this();
/*  17 */     setSpecies(species);
/*     */   }
/*     */   
/*     */   public Tree(TreeSpecies species, BlockFace dir) {
/*  21 */     this();
/*  22 */     setSpecies(species);
/*  23 */     setDirection(dir);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Tree(int type) {
/*  32 */     super(type);
/*     */   }
/*     */   
/*     */   public Tree(Material type) {
/*  36 */     super(type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Tree(int type, byte data) {
/*  45 */     super(type, data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Tree(Material type, byte data) {
/*  54 */     super(type, data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TreeSpecies getSpecies() {
/*  63 */     return TreeSpecies.getByData((byte)(getData() & 0x3));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSpecies(TreeSpecies species) {
/*  72 */     setData((byte)(getData() & 0xC | species.getData()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockFace getDirection() {
/*  87 */     switch (getData() >> 2 & 0x3)
/*     */     
/*     */     { default:
/*  90 */         return BlockFace.UP;
/*     */       case 1:
/*  92 */         return BlockFace.WEST;
/*     */       case 2:
/*  94 */         return BlockFace.NORTH;
/*     */       case 3:
/*  96 */         break; }  return BlockFace.SELF;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDirection(BlockFace dir) {
/*     */     int dat;
/* 106 */     switch (dir) {
/*     */ 
/*     */       
/*     */       default:
/* 110 */         dat = 0;
/*     */         break;
/*     */       case WEST:
/*     */       case EAST:
/* 114 */         dat = 1;
/*     */         break;
/*     */       case NORTH:
/*     */       case SOUTH:
/* 118 */         dat = 2;
/*     */         break;
/*     */       case SELF:
/* 121 */         dat = 3;
/*     */         break;
/*     */     } 
/* 124 */     setData((byte)(getData() & 0x3 | dat << 2));
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 129 */     return getSpecies() + " " + getDirection() + " " + super.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public Tree clone() {
/* 134 */     return (Tree)super.clone();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\Tree.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
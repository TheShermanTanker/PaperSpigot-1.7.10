/*     */ package org.bukkit.material;
/*     */ 
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.BlockFace;
/*     */ 
/*     */ public class Skull
/*     */   extends MaterialData
/*     */   implements Directional
/*     */ {
/*     */   public Skull() {
/*  11 */     super(Material.SKULL);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Skull(BlockFace direction) {
/*  20 */     this();
/*  21 */     setFacingDirection(direction);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Skull(int type) {
/*  30 */     super(type);
/*     */   }
/*     */   
/*     */   public Skull(Material type) {
/*  34 */     super(type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Skull(int type, byte data) {
/*  43 */     super(type, data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Skull(Material type, byte data) {
/*  52 */     super(type, data);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFacingDirection(BlockFace face) {
/*     */     int data;
/*  58 */     switch (face) {
/*     */       
/*     */       default:
/*  61 */         data = 1;
/*     */         break;
/*     */       
/*     */       case NORTH:
/*  65 */         data = 2;
/*     */         break;
/*     */       
/*     */       case EAST:
/*  69 */         data = 4;
/*     */         break;
/*     */       
/*     */       case SOUTH:
/*  73 */         data = 3;
/*     */         break;
/*     */       
/*     */       case WEST:
/*  77 */         data = 5;
/*     */         break;
/*     */     } 
/*  80 */     setData((byte)data);
/*     */   }
/*     */   
/*     */   public BlockFace getFacing() {
/*  84 */     int data = getData();
/*     */     
/*  86 */     switch (data) {
/*     */       
/*     */       default:
/*  89 */         return BlockFace.SELF;
/*     */       
/*     */       case 2:
/*  92 */         return BlockFace.NORTH;
/*     */       
/*     */       case 3:
/*  95 */         return BlockFace.SOUTH;
/*     */       
/*     */       case 4:
/*  98 */         return BlockFace.EAST;
/*     */       case 5:
/*     */         break;
/* 101 */     }  return BlockFace.WEST;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 107 */     return super.toString() + " facing " + getFacing();
/*     */   }
/*     */ 
/*     */   
/*     */   public Skull clone() {
/* 112 */     return (Skull)super.clone();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\Skull.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
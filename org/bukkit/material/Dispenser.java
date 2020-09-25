/*     */ package org.bukkit.material;
/*     */ 
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.BlockFace;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Dispenser
/*     */   extends FurnaceAndDispenser
/*     */ {
/*     */   public Dispenser() {
/*  12 */     super(Material.DISPENSER);
/*     */   }
/*     */   
/*     */   public Dispenser(BlockFace direction) {
/*  16 */     this();
/*  17 */     setFacingDirection(direction);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Dispenser(int type) {
/*  26 */     super(type);
/*     */   }
/*     */   
/*     */   public Dispenser(Material type) {
/*  30 */     super(type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Dispenser(int type, byte data) {
/*  39 */     super(type, data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Dispenser(Material type, byte data) {
/*  48 */     super(type, data);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFacingDirection(BlockFace face) {
/*     */     byte data;
/*  54 */     switch (face) {
/*     */       case DOWN:
/*  56 */         data = 0;
/*     */         break;
/*     */       
/*     */       case UP:
/*  60 */         data = 1;
/*     */         break;
/*     */       
/*     */       case NORTH:
/*  64 */         data = 2;
/*     */         break;
/*     */       
/*     */       case SOUTH:
/*  68 */         data = 3;
/*     */         break;
/*     */       
/*     */       case WEST:
/*  72 */         data = 4;
/*     */         break;
/*     */ 
/*     */       
/*     */       default:
/*  77 */         data = 5;
/*     */         break;
/*     */     } 
/*  80 */     setData(data);
/*     */   }
/*     */   
/*     */   public BlockFace getFacing() {
/*  84 */     int data = getData() & 0x7;
/*     */     
/*  86 */     switch (data) {
/*     */       case 0:
/*  88 */         return BlockFace.DOWN;
/*     */       
/*     */       case 1:
/*  91 */         return BlockFace.UP;
/*     */       
/*     */       case 2:
/*  94 */         return BlockFace.NORTH;
/*     */       
/*     */       case 3:
/*  97 */         return BlockFace.SOUTH;
/*     */       
/*     */       case 4:
/* 100 */         return BlockFace.WEST;
/*     */     } 
/*     */ 
/*     */     
/* 104 */     return BlockFace.EAST;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Dispenser clone() {
/* 110 */     return (Dispenser)super.clone();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\Dispenser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
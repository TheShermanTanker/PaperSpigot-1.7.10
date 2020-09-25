/*     */ package org.bukkit.material;
/*     */ 
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.BlockFace;
/*     */ 
/*     */ 
/*     */ public class Torch
/*     */   extends SimpleAttachableMaterialData
/*     */ {
/*     */   public Torch() {
/*  11 */     super(Material.TORCH);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Torch(int type) {
/*  20 */     super(type);
/*     */   }
/*     */   
/*     */   public Torch(Material type) {
/*  24 */     super(type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Torch(int type, byte data) {
/*  33 */     super(type, data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Torch(Material type, byte data) {
/*  42 */     super(type, data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockFace getAttachedFace() {
/*  51 */     byte data = getData();
/*     */     
/*  53 */     switch (data) {
/*     */       case 1:
/*  55 */         return BlockFace.WEST;
/*     */       
/*     */       case 2:
/*  58 */         return BlockFace.EAST;
/*     */       
/*     */       case 3:
/*  61 */         return BlockFace.NORTH;
/*     */       
/*     */       case 4:
/*  64 */         return BlockFace.SOUTH;
/*     */     } 
/*     */ 
/*     */     
/*  68 */     return BlockFace.DOWN;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFacingDirection(BlockFace face) {
/*     */     byte data;
/*  75 */     switch (face) {
/*     */       case EAST:
/*  77 */         data = 1;
/*     */         break;
/*     */       
/*     */       case WEST:
/*  81 */         data = 2;
/*     */         break;
/*     */       
/*     */       case SOUTH:
/*  85 */         data = 3;
/*     */         break;
/*     */       
/*     */       case NORTH:
/*  89 */         data = 4;
/*     */         break;
/*     */ 
/*     */       
/*     */       default:
/*  94 */         data = 5;
/*     */         break;
/*     */     } 
/*  97 */     setData(data);
/*     */   }
/*     */ 
/*     */   
/*     */   public Torch clone() {
/* 102 */     return (Torch)super.clone();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\Torch.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
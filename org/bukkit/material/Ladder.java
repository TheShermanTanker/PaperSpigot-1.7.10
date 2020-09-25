/*     */ package org.bukkit.material;
/*     */ 
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.BlockFace;
/*     */ 
/*     */ 
/*     */ public class Ladder
/*     */   extends SimpleAttachableMaterialData
/*     */ {
/*     */   public Ladder() {
/*  11 */     super(Material.LADDER);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Ladder(int type) {
/*  20 */     super(type);
/*     */   }
/*     */   
/*     */   public Ladder(Material type) {
/*  24 */     super(type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Ladder(int type, byte data) {
/*  33 */     super(type, data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Ladder(Material type, byte data) {
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
/*     */       case 2:
/*  55 */         return BlockFace.SOUTH;
/*     */       
/*     */       case 3:
/*  58 */         return BlockFace.NORTH;
/*     */       
/*     */       case 4:
/*  61 */         return BlockFace.EAST;
/*     */       
/*     */       case 5:
/*  64 */         return BlockFace.WEST;
/*     */     } 
/*     */     
/*  67 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFacingDirection(BlockFace face) {
/*  74 */     byte data = 0;
/*     */     
/*  76 */     switch (face) {
/*     */       case SOUTH:
/*  78 */         data = 2;
/*     */         break;
/*     */       
/*     */       case NORTH:
/*  82 */         data = 3;
/*     */         break;
/*     */       
/*     */       case EAST:
/*  86 */         data = 4;
/*     */         break;
/*     */       
/*     */       case WEST:
/*  90 */         data = 5;
/*     */         break;
/*     */     } 
/*     */     
/*  94 */     setData(data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Ladder clone() {
/* 100 */     return (Ladder)super.clone();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\Ladder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
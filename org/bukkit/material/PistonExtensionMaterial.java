/*     */ package org.bukkit.material;
/*     */ 
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.BlockFace;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PistonExtensionMaterial
/*     */   extends MaterialData
/*     */   implements Attachable
/*     */ {
/*     */   @Deprecated
/*     */   public PistonExtensionMaterial(int type) {
/*  16 */     super(type);
/*     */   }
/*     */   
/*     */   public PistonExtensionMaterial(Material type) {
/*  20 */     super(type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public PistonExtensionMaterial(int type, byte data) {
/*  29 */     super(type, data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public PistonExtensionMaterial(Material type, byte data) {
/*  38 */     super(type, data);
/*     */   }
/*     */   
/*     */   public void setFacingDirection(BlockFace face) {
/*  42 */     byte data = (byte)(getData() & 0x8);
/*     */     
/*  44 */     switch (face) {
/*     */       case UP:
/*  46 */         data = (byte)(data | 0x1);
/*     */         break;
/*     */       case NORTH:
/*  49 */         data = (byte)(data | 0x2);
/*     */         break;
/*     */       case SOUTH:
/*  52 */         data = (byte)(data | 0x3);
/*     */         break;
/*     */       case WEST:
/*  55 */         data = (byte)(data | 0x4);
/*     */         break;
/*     */       case EAST:
/*  58 */         data = (byte)(data | 0x5);
/*     */         break;
/*     */     } 
/*  61 */     setData(data);
/*     */   }
/*     */   
/*     */   public BlockFace getFacing() {
/*  65 */     byte dir = (byte)(getData() & 0x7);
/*     */     
/*  67 */     switch (dir) {
/*     */       case 0:
/*  69 */         return BlockFace.DOWN;
/*     */       case 1:
/*  71 */         return BlockFace.UP;
/*     */       case 2:
/*  73 */         return BlockFace.NORTH;
/*     */       case 3:
/*  75 */         return BlockFace.SOUTH;
/*     */       case 4:
/*  77 */         return BlockFace.WEST;
/*     */       case 5:
/*  79 */         return BlockFace.EAST;
/*     */     } 
/*  81 */     return BlockFace.SELF;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSticky() {
/*  91 */     return ((getData() & 0x8) == 8);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSticky(boolean sticky) {
/* 100 */     setData((byte)(sticky ? (getData() | 0x8) : (getData() & 0xFFFFFFF7)));
/*     */   }
/*     */   
/*     */   public BlockFace getAttachedFace() {
/* 104 */     return getFacing().getOppositeFace();
/*     */   }
/*     */ 
/*     */   
/*     */   public PistonExtensionMaterial clone() {
/* 109 */     return (PistonExtensionMaterial)super.clone();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\PistonExtensionMaterial.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
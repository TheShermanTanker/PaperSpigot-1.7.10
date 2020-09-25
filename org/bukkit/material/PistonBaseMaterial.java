/*     */ package org.bukkit.material;
/*     */ 
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.BlockFace;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PistonBaseMaterial
/*     */   extends MaterialData
/*     */   implements Directional, Redstone
/*     */ {
/*     */   @Deprecated
/*     */   public PistonBaseMaterial(int type) {
/*  16 */     super(type);
/*     */   }
/*     */   
/*     */   public PistonBaseMaterial(Material type) {
/*  20 */     super(type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public PistonBaseMaterial(int type, byte data) {
/*  29 */     super(type, data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public PistonBaseMaterial(Material type, byte data) {
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
/*     */   public boolean isPowered() {
/*  86 */     return ((getData() & 0x8) == 8);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPowered(boolean powered) {
/*  95 */     setData((byte)(powered ? (getData() | 0x8) : (getData() & 0xFFFFFFF7)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSticky() {
/* 104 */     return (getItemType() == Material.PISTON_STICKY_BASE);
/*     */   }
/*     */ 
/*     */   
/*     */   public PistonBaseMaterial clone() {
/* 109 */     return (PistonBaseMaterial)super.clone();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\PistonBaseMaterial.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
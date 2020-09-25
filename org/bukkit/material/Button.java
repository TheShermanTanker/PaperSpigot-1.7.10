/*     */ package org.bukkit.material;
/*     */ 
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.BlockFace;
/*     */ 
/*     */ public class Button
/*     */   extends SimpleAttachableMaterialData
/*     */   implements Redstone
/*     */ {
/*     */   public Button() {
/*  11 */     super(Material.STONE_BUTTON);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Button(int type) {
/*  20 */     super(type);
/*     */   }
/*     */   
/*     */   public Button(Material type) {
/*  24 */     super(type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Button(int type, byte data) {
/*  33 */     super(type, data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Button(Material type, byte data) {
/*  42 */     super(type, data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPowered() {
/*  52 */     return ((getData() & 0x8) == 8);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPowered(boolean bool) {
/*  62 */     setData((byte)(bool ? (getData() | 0x8) : (getData() & 0xFFFFFFF7)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockFace getAttachedFace() {
/*  71 */     byte data = (byte)(getData() & 0x7);
/*     */     
/*  73 */     switch (data) {
/*     */       case 1:
/*  75 */         return BlockFace.WEST;
/*     */       
/*     */       case 2:
/*  78 */         return BlockFace.EAST;
/*     */       
/*     */       case 3:
/*  81 */         return BlockFace.NORTH;
/*     */       
/*     */       case 4:
/*  84 */         return BlockFace.SOUTH;
/*     */     } 
/*     */     
/*  87 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFacingDirection(BlockFace face) {
/*  94 */     byte data = (byte)(getData() & 0x8);
/*     */     
/*  96 */     switch (face) {
/*     */       case EAST:
/*  98 */         data = (byte)(data | 0x1);
/*     */         break;
/*     */       
/*     */       case WEST:
/* 102 */         data = (byte)(data | 0x2);
/*     */         break;
/*     */       
/*     */       case SOUTH:
/* 106 */         data = (byte)(data | 0x3);
/*     */         break;
/*     */       
/*     */       case NORTH:
/* 110 */         data = (byte)(data | 0x4);
/*     */         break;
/*     */     } 
/*     */     
/* 114 */     setData(data);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 119 */     return super.toString() + " " + (isPowered() ? "" : "NOT ") + "POWERED";
/*     */   }
/*     */ 
/*     */   
/*     */   public Button clone() {
/* 124 */     return (Button)super.clone();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\Button.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
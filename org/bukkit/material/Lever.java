/*     */ package org.bukkit.material;
/*     */ 
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.BlockFace;
/*     */ 
/*     */ public class Lever
/*     */   extends SimpleAttachableMaterialData
/*     */   implements Redstone
/*     */ {
/*     */   public Lever() {
/*  11 */     super(Material.LEVER);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Lever(int type) {
/*  20 */     super(type);
/*     */   }
/*     */   
/*     */   public Lever(Material type) {
/*  24 */     super(type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Lever(int type, byte data) {
/*  33 */     super(type, data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Lever(Material type, byte data) {
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
/*     */   public void setPowered(boolean isPowered) {
/*  61 */     setData((byte)(isPowered ? (getData() | 0x8) : (getData() & 0xFFFFFFF7)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockFace getAttachedFace() {
/*  70 */     byte data = (byte)(getData() & 0x7);
/*     */     
/*  72 */     switch (data) {
/*     */       case 1:
/*  74 */         return BlockFace.WEST;
/*     */       
/*     */       case 2:
/*  77 */         return BlockFace.EAST;
/*     */       
/*     */       case 3:
/*  80 */         return BlockFace.NORTH;
/*     */       
/*     */       case 4:
/*  83 */         return BlockFace.SOUTH;
/*     */       
/*     */       case 5:
/*     */       case 6:
/*  87 */         return BlockFace.DOWN;
/*     */       
/*     */       case 0:
/*     */       case 7:
/*  91 */         return BlockFace.UP;
/*     */     } 
/*     */ 
/*     */     
/*  95 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFacingDirection(BlockFace face) {
/* 102 */     byte data = (byte)(getData() & 0x8);
/* 103 */     BlockFace attach = getAttachedFace();
/*     */     
/* 105 */     if (attach == BlockFace.DOWN) {
/* 106 */       switch (face) {
/*     */         case SOUTH:
/*     */         case NORTH:
/* 109 */           data = (byte)(data | 0x5);
/*     */           break;
/*     */         
/*     */         case EAST:
/*     */         case WEST:
/* 114 */           data = (byte)(data | 0x6);
/*     */           break;
/*     */       } 
/* 117 */     } else if (attach == BlockFace.UP) {
/* 118 */       switch (face) {
/*     */         case SOUTH:
/*     */         case NORTH:
/* 121 */           data = (byte)(data | 0x7);
/*     */           break;
/*     */         
/*     */         case EAST:
/*     */         case WEST:
/* 126 */           data = (byte)(data | 0x0);
/*     */           break;
/*     */       } 
/*     */     } else {
/* 130 */       switch (face) {
/*     */         case EAST:
/* 132 */           data = (byte)(data | 0x1);
/*     */           break;
/*     */         
/*     */         case WEST:
/* 136 */           data = (byte)(data | 0x2);
/*     */           break;
/*     */         
/*     */         case SOUTH:
/* 140 */           data = (byte)(data | 0x3);
/*     */           break;
/*     */         
/*     */         case NORTH:
/* 144 */           data = (byte)(data | 0x4);
/*     */           break;
/*     */       } 
/*     */     } 
/* 148 */     setData(data);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 153 */     return super.toString() + " facing " + getFacing() + " " + (isPowered() ? "" : "NOT ") + "POWERED";
/*     */   }
/*     */ 
/*     */   
/*     */   public Lever clone() {
/* 158 */     return (Lever)super.clone();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\Lever.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
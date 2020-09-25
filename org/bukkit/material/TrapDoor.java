/*     */ package org.bukkit.material;
/*     */ 
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.BlockFace;
/*     */ 
/*     */ public class TrapDoor
/*     */   extends SimpleAttachableMaterialData
/*     */   implements Openable
/*     */ {
/*     */   public TrapDoor() {
/*  11 */     super(Material.TRAP_DOOR);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public TrapDoor(int type) {
/*  20 */     super(type);
/*     */   }
/*     */   
/*     */   public TrapDoor(Material type) {
/*  24 */     super(type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public TrapDoor(int type, byte data) {
/*  33 */     super(type, data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public TrapDoor(Material type, byte data) {
/*  42 */     super(type, data);
/*     */   }
/*     */   
/*     */   public boolean isOpen() {
/*  46 */     return ((getData() & 0x4) == 4);
/*     */   }
/*     */   
/*     */   public void setOpen(boolean isOpen) {
/*  50 */     byte data = getData();
/*     */     
/*  52 */     if (isOpen) {
/*  53 */       data = (byte)(data | 0x4);
/*     */     } else {
/*  55 */       data = (byte)(data & 0xFFFFFFFB);
/*     */     } 
/*     */     
/*  58 */     setData(data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInverted() {
/*  67 */     return ((getData() & 0x8) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInverted(boolean inv) {
/*  76 */     int dat = getData() & 0x7;
/*  77 */     if (inv) {
/*  78 */       dat |= 0x8;
/*     */     }
/*  80 */     setData((byte)dat);
/*     */   }
/*     */   
/*     */   public BlockFace getAttachedFace() {
/*  84 */     byte data = (byte)(getData() & 0x3);
/*     */     
/*  86 */     switch (data) {
/*     */       case 0:
/*  88 */         return BlockFace.SOUTH;
/*     */       
/*     */       case 1:
/*  91 */         return BlockFace.NORTH;
/*     */       
/*     */       case 2:
/*  94 */         return BlockFace.EAST;
/*     */       
/*     */       case 3:
/*  97 */         return BlockFace.WEST;
/*     */     } 
/*     */     
/* 100 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFacingDirection(BlockFace face) {
/* 105 */     byte data = (byte)(getData() & 0xC);
/*     */     
/* 107 */     switch (face) {
/*     */       case SOUTH:
/* 109 */         data = (byte)(data | 0x1);
/*     */         break;
/*     */       case WEST:
/* 112 */         data = (byte)(data | 0x2);
/*     */         break;
/*     */       case EAST:
/* 115 */         data = (byte)(data | 0x3);
/*     */         break;
/*     */     } 
/*     */     
/* 119 */     setData(data);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 124 */     return (isOpen() ? "OPEN " : "CLOSED ") + super.toString() + " with hinges set " + getAttachedFace() + (isInverted() ? " inverted" : "");
/*     */   }
/*     */ 
/*     */   
/*     */   public TrapDoor clone() {
/* 129 */     return (TrapDoor)super.clone();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\TrapDoor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
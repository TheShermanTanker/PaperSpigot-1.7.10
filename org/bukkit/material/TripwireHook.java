/*     */ package org.bukkit.material;
/*     */ 
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.BlockFace;
/*     */ 
/*     */ 
/*     */ public class TripwireHook
/*     */   extends SimpleAttachableMaterialData
/*     */   implements Redstone
/*     */ {
/*     */   public TripwireHook() {
/*  12 */     super(Material.TRIPWIRE_HOOK);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public TripwireHook(int type) {
/*  21 */     super(type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public TripwireHook(int type, byte data) {
/*  30 */     super(type, data);
/*     */   }
/*     */   
/*     */   public TripwireHook(BlockFace dir) {
/*  34 */     this();
/*  35 */     setFacingDirection(dir);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isConnected() {
/*  44 */     return ((getData() & 0x4) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setConnected(boolean connected) {
/*  53 */     int dat = getData() & 0xB;
/*  54 */     if (connected) {
/*  55 */       dat |= 0x4;
/*     */     }
/*  57 */     setData((byte)dat);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isActivated() {
/*  66 */     return ((getData() & 0x8) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setActivated(boolean act) {
/*  75 */     int dat = getData() & 0x7;
/*  76 */     if (act) {
/*  77 */       dat |= 0x8;
/*     */     }
/*  79 */     setData((byte)dat);
/*     */   }
/*     */   
/*     */   public void setFacingDirection(BlockFace face) {
/*  83 */     int dat = getData() & 0xC;
/*  84 */     switch (face) {
/*     */       case WEST:
/*  86 */         dat |= 0x1;
/*     */         break;
/*     */       case NORTH:
/*  89 */         dat |= 0x2;
/*     */         break;
/*     */       case EAST:
/*  92 */         dat |= 0x3;
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  98 */     setData((byte)dat);
/*     */   }
/*     */   
/*     */   public BlockFace getAttachedFace() {
/* 102 */     switch (getData() & 0x3) {
/*     */       case 0:
/* 104 */         return BlockFace.NORTH;
/*     */       case 1:
/* 106 */         return BlockFace.EAST;
/*     */       case 2:
/* 108 */         return BlockFace.SOUTH;
/*     */       case 3:
/* 110 */         return BlockFace.WEST;
/*     */     } 
/* 112 */     return null;
/*     */   }
/*     */   
/*     */   public boolean isPowered() {
/* 116 */     return isActivated();
/*     */   }
/*     */ 
/*     */   
/*     */   public TripwireHook clone() {
/* 121 */     return (TripwireHook)super.clone();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 126 */     return super.toString() + " facing " + getFacing() + (isActivated() ? " Activated" : "") + (isConnected() ? " Connected" : "");
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\TripwireHook.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
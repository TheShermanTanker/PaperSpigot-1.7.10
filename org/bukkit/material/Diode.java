/*     */ package org.bukkit.material;
/*     */ 
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.BlockFace;
/*     */ 
/*     */ public class Diode extends MaterialData implements Directional {
/*     */   public Diode() {
/*   8 */     super(Material.DIODE_BLOCK_ON);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Diode(int type) {
/*  17 */     super(type);
/*     */   }
/*     */   
/*     */   public Diode(Material type) {
/*  21 */     super(type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Diode(int type, byte data) {
/*  30 */     super(type, data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Diode(Material type, byte data) {
/*  39 */     super(type, data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDelay(int delay) {
/*  49 */     if (delay > 4) {
/*  50 */       delay = 4;
/*     */     }
/*  52 */     if (delay < 1) {
/*  53 */       delay = 1;
/*     */     }
/*  55 */     byte newData = (byte)(getData() & 0x3);
/*     */     
/*  57 */     setData((byte)(newData | delay - 1 << 2));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDelay() {
/*  66 */     return (getData() >> 2) + 1;
/*     */   }
/*     */   public void setFacingDirection(BlockFace face) {
/*     */     byte data;
/*  70 */     int delay = getDelay();
/*     */ 
/*     */     
/*  73 */     switch (face) {
/*     */       case EAST:
/*  75 */         data = 1;
/*     */         break;
/*     */       
/*     */       case SOUTH:
/*  79 */         data = 2;
/*     */         break;
/*     */       
/*     */       case WEST:
/*  83 */         data = 3;
/*     */         break;
/*     */ 
/*     */       
/*     */       default:
/*  88 */         data = 0;
/*     */         break;
/*     */     } 
/*  91 */     setData(data);
/*  92 */     setDelay(delay);
/*     */   }
/*     */   
/*     */   public BlockFace getFacing() {
/*  96 */     byte data = (byte)(getData() & 0x3);
/*     */     
/*  98 */     switch (data) {
/*     */       
/*     */       default:
/* 101 */         return BlockFace.NORTH;
/*     */       
/*     */       case 1:
/* 104 */         return BlockFace.EAST;
/*     */       
/*     */       case 2:
/* 107 */         return BlockFace.SOUTH;
/*     */       case 3:
/*     */         break;
/* 110 */     }  return BlockFace.WEST;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 116 */     return super.toString() + " facing " + getFacing() + " with " + getDelay() + " ticks delay";
/*     */   }
/*     */ 
/*     */   
/*     */   public Diode clone() {
/* 121 */     return (Diode)super.clone();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\Diode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
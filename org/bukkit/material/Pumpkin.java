/*     */ package org.bukkit.material;
/*     */ 
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.BlockFace;
/*     */ 
/*     */ 
/*     */ public class Pumpkin
/*     */   extends MaterialData
/*     */   implements Directional
/*     */ {
/*     */   public Pumpkin() {
/*  12 */     super(Material.PUMPKIN);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pumpkin(BlockFace direction) {
/*  21 */     this();
/*  22 */     setFacingDirection(direction);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Pumpkin(int type) {
/*  31 */     super(type);
/*     */   }
/*     */   
/*     */   public Pumpkin(Material type) {
/*  35 */     super(type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Pumpkin(int type, byte data) {
/*  44 */     super(type, data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Pumpkin(Material type, byte data) {
/*  53 */     super(type, data);
/*     */   }
/*     */   
/*     */   public boolean isLit() {
/*  57 */     return (getItemType() == Material.JACK_O_LANTERN);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFacingDirection(BlockFace face) {
/*     */     byte data;
/*  63 */     switch (face) {
/*     */       case NORTH:
/*  65 */         data = 0;
/*     */         break;
/*     */       
/*     */       case EAST:
/*  69 */         data = 1;
/*     */         break;
/*     */       
/*     */       case SOUTH:
/*  73 */         data = 2;
/*     */         break;
/*     */ 
/*     */       
/*     */       default:
/*  78 */         data = 3;
/*     */         break;
/*     */     } 
/*  81 */     setData(data);
/*     */   }
/*     */   
/*     */   public BlockFace getFacing() {
/*  85 */     byte data = getData();
/*     */     
/*  87 */     switch (data) {
/*     */       case 0:
/*  89 */         return BlockFace.NORTH;
/*     */       
/*     */       case 1:
/*  92 */         return BlockFace.EAST;
/*     */       
/*     */       case 2:
/*  95 */         return BlockFace.SOUTH;
/*     */     } 
/*     */ 
/*     */     
/*  99 */     return BlockFace.EAST;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 105 */     return super.toString() + " facing " + getFacing() + " " + (isLit() ? "" : "NOT ") + "LIT";
/*     */   }
/*     */ 
/*     */   
/*     */   public Pumpkin clone() {
/* 110 */     return (Pumpkin)super.clone();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\Pumpkin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
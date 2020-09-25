/*     */ package org.bukkit.material;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.bukkit.Material;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Step
/*     */   extends TexturedMaterial
/*     */ {
/*  12 */   private static final List<Material> textures = new ArrayList<Material>();
/*     */   static {
/*  14 */     textures.add(Material.STONE);
/*  15 */     textures.add(Material.SANDSTONE);
/*  16 */     textures.add(Material.WOOD);
/*  17 */     textures.add(Material.COBBLESTONE);
/*  18 */     textures.add(Material.BRICK);
/*  19 */     textures.add(Material.SMOOTH_BRICK);
/*  20 */     textures.add(Material.NETHER_BRICK);
/*  21 */     textures.add(Material.QUARTZ_BLOCK);
/*     */   }
/*     */   
/*     */   public Step() {
/*  25 */     super(Material.STEP);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Step(int type) {
/*  34 */     super(type);
/*     */   }
/*     */   
/*     */   public Step(Material type) {
/*  38 */     super(textures.contains(type) ? Material.STEP : type);
/*  39 */     if (textures.contains(type)) {
/*  40 */       setMaterial(type);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Step(int type, byte data) {
/*  50 */     super(type, data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Step(Material type, byte data) {
/*  59 */     super(type, data);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Material> getTextures() {
/*  64 */     return textures;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInverted() {
/*  73 */     return ((getData() & 0x8) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInverted(boolean inv) {
/*  83 */     int dat = getData() & 0x7;
/*  84 */     if (inv) {
/*  85 */       dat |= 0x8;
/*     */     }
/*  87 */     setData((byte)dat);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected int getTextureIndex() {
/*  97 */     return getData() & 0x7;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected void setTextureIndex(int idx) {
/* 107 */     setData((byte)(getData() & 0x8 | idx));
/*     */   }
/*     */ 
/*     */   
/*     */   public Step clone() {
/* 112 */     return (Step)super.clone();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 117 */     return super.toString() + (isInverted() ? "inverted" : "");
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\Step.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
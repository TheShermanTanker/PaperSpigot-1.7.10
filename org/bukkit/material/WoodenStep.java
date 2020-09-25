/*     */ package org.bukkit.material;
/*     */ 
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.TreeSpecies;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WoodenStep
/*     */   extends MaterialData
/*     */ {
/*     */   public WoodenStep() {
/*  12 */     super(Material.WOOD_STEP);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public WoodenStep(int type) {
/*  21 */     super(type);
/*     */   }
/*     */   
/*     */   public WoodenStep(TreeSpecies species) {
/*  25 */     this();
/*  26 */     setSpecies(species);
/*     */   }
/*     */   
/*     */   public WoodenStep(TreeSpecies species, boolean inv) {
/*  30 */     this();
/*  31 */     setSpecies(species);
/*  32 */     setInverted(inv);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public WoodenStep(int type, byte data) {
/*  41 */     super(type, data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public WoodenStep(Material type, byte data) {
/*  50 */     super(type, data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TreeSpecies getSpecies() {
/*  59 */     return TreeSpecies.getByData((byte)(getData() & 0x3));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSpecies(TreeSpecies species) {
/*  68 */     setData((byte)(getData() & 0xC | species.getData()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInverted() {
/*  77 */     return ((getData() & 0x8) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInverted(boolean inv) {
/*  87 */     int dat = getData() & 0x7;
/*  88 */     if (inv) {
/*  89 */       dat |= 0x8;
/*     */     }
/*  91 */     setData((byte)dat);
/*     */   }
/*     */ 
/*     */   
/*     */   public WoodenStep clone() {
/*  96 */     return (WoodenStep)super.clone();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 101 */     return super.toString() + " " + getSpecies() + (isInverted() ? " inverted" : "");
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\WoodenStep.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
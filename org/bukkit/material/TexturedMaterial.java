/*     */ package org.bukkit.material;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.bukkit.Material;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class TexturedMaterial
/*     */   extends MaterialData
/*     */ {
/*     */   public TexturedMaterial(Material m) {
/*  13 */     super(m);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public TexturedMaterial(int type) {
/*  22 */     super(type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public TexturedMaterial(int type, byte data) {
/*  31 */     super(type, data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public TexturedMaterial(Material type, byte data) {
/*  40 */     super(type, data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Material getMaterial() {
/*  57 */     int n = getTextureIndex();
/*  58 */     if (n > getTextures().size() - 1) {
/*  59 */       n = 0;
/*     */     }
/*     */     
/*  62 */     return getTextures().get(n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaterial(Material material) {
/*  72 */     if (getTextures().contains(material)) {
/*  73 */       setTextureIndex(getTextures().indexOf(material));
/*     */     } else {
/*  75 */       setTextureIndex(0);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected int getTextureIndex() {
/*  87 */     return getData();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected void setTextureIndex(int idx) {
/*  98 */     setData((byte)idx);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 103 */     return getMaterial() + " " + super.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public TexturedMaterial clone() {
/* 108 */     return (TexturedMaterial)super.clone();
/*     */   }
/*     */   
/*     */   public abstract List<Material> getTextures();
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\TexturedMaterial.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
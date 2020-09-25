/*     */ package net.minecraft.server.v1_7_R4;
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
/*     */ 
/*     */ public enum EnumArmorMaterial
/*     */ {
/*  61 */   CLOTH(5, new int[] { 1, 3, 2, 1 }, 15),
/*     */   
/*  63 */   CHAIN(15, new int[] { 2, 5, 4, 1 }, 12),
/*     */   
/*  65 */   IRON(15, new int[] { 2, 6, 5, 2 }, 9),
/*     */   
/*  67 */   GOLD(7, new int[] { 2, 5, 3, 1 }, 25),
/*     */   
/*  69 */   DIAMOND(33, new int[] { 3, 8, 6, 3 }, 10);
/*     */   
/*     */   private int f;
/*     */   
/*     */   private int[] g;
/*     */   
/*     */   private int h;
/*     */   
/*     */   EnumArmorMaterial(int paramInt1, int[] paramArrayOfint, int paramInt2) {
/*  78 */     this.f = paramInt1;
/*  79 */     this.g = paramArrayOfint;
/*  80 */     this.h = paramInt2;
/*     */   }
/*     */   
/*     */   public int a(int paramInt) {
/*  84 */     return ItemArmor.e()[paramInt] * this.f;
/*     */   }
/*     */   
/*     */   public int b(int paramInt) {
/*  88 */     return this.g[paramInt];
/*     */   }
/*     */   
/*     */   public int a() {
/*  92 */     return this.h;
/*     */   }
/*     */   
/*     */   public Item b() {
/*  96 */     if (this == CLOTH)
/*  97 */       return Items.LEATHER; 
/*  98 */     if (this == CHAIN)
/*  99 */       return Items.IRON_INGOT; 
/* 100 */     if (this == GOLD)
/* 101 */       return Items.GOLD_INGOT; 
/* 102 */     if (this == IRON)
/* 103 */       return Items.IRON_INGOT; 
/* 104 */     if (this == DIAMOND) {
/* 105 */       return Items.DIAMOND;
/*     */     }
/* 107 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EnumArmorMaterial.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
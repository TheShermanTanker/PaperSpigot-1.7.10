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
/*     */ public enum EnumToolMaterial
/*     */ {
/*  72 */   WOOD(0, 59, 2.0F, 0.0F, 15),
/*  73 */   STONE(1, 131, 4.0F, 1.0F, 5),
/*  74 */   IRON(2, 250, 6.0F, 2.0F, 14),
/*  75 */   DIAMOND(3, 1561, 8.0F, 3.0F, 10),
/*  76 */   GOLD(0, 32, 12.0F, 0.0F, 22);
/*     */   
/*     */   private final int f;
/*     */   private final int g;
/*     */   private final float h;
/*     */   private final float i;
/*     */   private final int j;
/*     */   
/*     */   EnumToolMaterial(int paramInt1, int paramInt2, float paramFloat1, float paramFloat2, int paramInt3) {
/*  85 */     this.f = paramInt1;
/*  86 */     this.g = paramInt2;
/*  87 */     this.h = paramFloat1;
/*  88 */     this.i = paramFloat2;
/*  89 */     this.j = paramInt3;
/*     */   }
/*     */   
/*     */   public int a() {
/*  93 */     return this.g;
/*     */   }
/*     */   
/*     */   public float b() {
/*  97 */     return this.h;
/*     */   }
/*     */   
/*     */   public float c() {
/* 101 */     return this.i;
/*     */   }
/*     */   
/*     */   public int d() {
/* 105 */     return this.f;
/*     */   }
/*     */   
/*     */   public int e() {
/* 109 */     return this.j;
/*     */   }
/*     */   
/*     */   public Item f() {
/* 113 */     if (this == WOOD)
/* 114 */       return Item.getItemOf(Blocks.WOOD); 
/* 115 */     if (this == STONE)
/* 116 */       return Item.getItemOf(Blocks.COBBLESTONE); 
/* 117 */     if (this == GOLD)
/* 118 */       return Items.GOLD_INGOT; 
/* 119 */     if (this == IRON)
/* 120 */       return Items.IRON_INGOT; 
/* 121 */     if (this == DIAMOND) {
/* 122 */       return Items.DIAMOND;
/*     */     }
/* 124 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EnumToolMaterial.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
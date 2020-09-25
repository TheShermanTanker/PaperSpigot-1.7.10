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
/*     */ public class BlockCobbleWall
/*     */   extends Block
/*     */ {
/*  22 */   public static final String[] a = new String[] { "normal", "mossy" };
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockCobbleWall(Block paramBlock) {
/*  27 */     super(paramBlock.material);
/*     */     
/*  29 */     c(paramBlock.strength);
/*  30 */     b(paramBlock.durability / 3.0F);
/*  31 */     a(paramBlock.stepSound);
/*  32 */     a(CreativeModeTab.b);
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
/*     */   public int b() {
/*  45 */     return 32;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean d() {
/*  50 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean b(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3) {
/*  55 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean c() {
/*  60 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateShape(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3) {
/*  65 */     boolean bool1 = e(paramIBlockAccess, paramInt1, paramInt2, paramInt3 - 1);
/*  66 */     boolean bool2 = e(paramIBlockAccess, paramInt1, paramInt2, paramInt3 + 1);
/*  67 */     boolean bool3 = e(paramIBlockAccess, paramInt1 - 1, paramInt2, paramInt3);
/*  68 */     boolean bool4 = e(paramIBlockAccess, paramInt1 + 1, paramInt2, paramInt3);
/*     */     
/*  70 */     float f1 = 0.25F;
/*  71 */     float f2 = 0.75F;
/*  72 */     float f3 = 0.25F;
/*  73 */     float f4 = 0.75F;
/*  74 */     float f5 = 1.0F;
/*     */     
/*  76 */     if (bool1) {
/*  77 */       f3 = 0.0F;
/*     */     }
/*  79 */     if (bool2) {
/*  80 */       f4 = 1.0F;
/*     */     }
/*  82 */     if (bool3) {
/*  83 */       f1 = 0.0F;
/*     */     }
/*  85 */     if (bool4) {
/*  86 */       f2 = 1.0F;
/*     */     }
/*     */     
/*  89 */     if (bool1 && bool2 && !bool3 && !bool4) {
/*  90 */       f5 = 0.8125F;
/*  91 */       f1 = 0.3125F;
/*  92 */       f2 = 0.6875F;
/*  93 */     } else if (!bool1 && !bool2 && bool3 && bool4) {
/*  94 */       f5 = 0.8125F;
/*  95 */       f3 = 0.3125F;
/*  96 */       f4 = 0.6875F;
/*     */     } 
/*     */     
/*  99 */     a(f1, 0.0F, f3, f2, f5, f4);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB a(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/* 104 */     updateShape(paramWorld, paramInt1, paramInt2, paramInt3);
/* 105 */     this.maxY = 1.5D;
/* 106 */     return super.a(paramWorld, paramInt1, paramInt2, paramInt3);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean e(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3) {
/* 111 */     Block block = paramIBlockAccess.getType(paramInt1, paramInt2, paramInt3);
/* 112 */     if (block == this || block == Blocks.FENCE_GATE) {
/* 113 */       return true;
/*     */     }
/* 115 */     if (block.material.k() && block.d()) {
/* 116 */       return (block.material != Material.PUMPKIN);
/*     */     }
/* 118 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDropData(int paramInt) {
/* 129 */     return paramInt;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockCobbleWall.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockFence
/*     */   extends Block
/*     */ {
/*     */   private final String a;
/*     */   
/*     */   public BlockFence(String paramString, Material paramMaterial) {
/*  17 */     super(paramMaterial);
/*  18 */     this.a = paramString;
/*  19 */     a(CreativeModeTab.c);
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, AxisAlignedBB paramAxisAlignedBB, List paramList, Entity paramEntity) {
/*  24 */     boolean bool1 = e(paramWorld, paramInt1, paramInt2, paramInt3 - 1);
/*  25 */     boolean bool2 = e(paramWorld, paramInt1, paramInt2, paramInt3 + 1);
/*  26 */     boolean bool3 = e(paramWorld, paramInt1 - 1, paramInt2, paramInt3);
/*  27 */     boolean bool4 = e(paramWorld, paramInt1 + 1, paramInt2, paramInt3);
/*     */     
/*  29 */     float f1 = 0.375F;
/*  30 */     float f2 = 0.625F;
/*  31 */     float f3 = 0.375F;
/*  32 */     float f4 = 0.625F;
/*     */     
/*  34 */     if (bool1) {
/*  35 */       f3 = 0.0F;
/*     */     }
/*  37 */     if (bool2) {
/*  38 */       f4 = 1.0F;
/*     */     }
/*  40 */     if (bool1 || bool2) {
/*  41 */       a(f1, 0.0F, f3, f2, 1.5F, f4);
/*  42 */       super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramAxisAlignedBB, paramList, paramEntity);
/*     */     } 
/*  44 */     f3 = 0.375F;
/*  45 */     f4 = 0.625F;
/*  46 */     if (bool3) {
/*  47 */       f1 = 0.0F;
/*     */     }
/*  49 */     if (bool4) {
/*  50 */       f2 = 1.0F;
/*     */     }
/*  52 */     if (bool3 || bool4 || (!bool1 && !bool2)) {
/*  53 */       a(f1, 0.0F, f3, f2, 1.5F, f4);
/*  54 */       super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramAxisAlignedBB, paramList, paramEntity);
/*     */     } 
/*     */     
/*  57 */     if (bool1) {
/*  58 */       f3 = 0.0F;
/*     */     }
/*  60 */     if (bool2) {
/*  61 */       f4 = 1.0F;
/*     */     }
/*     */     
/*  64 */     a(f1, 0.0F, f3, f2, 1.0F, f4);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateShape(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3) {
/*  69 */     boolean bool1 = e(paramIBlockAccess, paramInt1, paramInt2, paramInt3 - 1);
/*  70 */     boolean bool2 = e(paramIBlockAccess, paramInt1, paramInt2, paramInt3 + 1);
/*  71 */     boolean bool3 = e(paramIBlockAccess, paramInt1 - 1, paramInt2, paramInt3);
/*  72 */     boolean bool4 = e(paramIBlockAccess, paramInt1 + 1, paramInt2, paramInt3);
/*     */     
/*  74 */     float f1 = 0.375F;
/*  75 */     float f2 = 0.625F;
/*  76 */     float f3 = 0.375F;
/*  77 */     float f4 = 0.625F;
/*     */     
/*  79 */     if (bool1) {
/*  80 */       f3 = 0.0F;
/*     */     }
/*  82 */     if (bool2) {
/*  83 */       f4 = 1.0F;
/*     */     }
/*  85 */     if (bool3) {
/*  86 */       f1 = 0.0F;
/*     */     }
/*  88 */     if (bool4) {
/*  89 */       f2 = 1.0F;
/*     */     }
/*     */     
/*  92 */     a(f1, 0.0F, f3, f2, 1.0F, f4);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean c() {
/*  97 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean d() {
/* 102 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean b(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3) {
/* 107 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int b() {
/* 112 */     return 11;
/*     */   }
/*     */   
/*     */   public boolean e(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3) {
/* 116 */     Block block = paramIBlockAccess.getType(paramInt1, paramInt2, paramInt3);
/* 117 */     if (block == this || block == Blocks.FENCE_GATE) {
/* 118 */       return true;
/*     */     }
/* 120 */     if (block.material.k() && block.d()) {
/* 121 */       return (block.material != Material.PUMPKIN);
/*     */     }
/* 123 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean a(Block paramBlock) {
/* 127 */     return (paramBlock == Blocks.FENCE || paramBlock == Blocks.NETHER_FENCE);
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
/*     */   public boolean interact(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityHuman paramEntityHuman, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3) {
/* 142 */     if (paramWorld.isStatic) return true; 
/* 143 */     if (ItemLeash.a(paramEntityHuman, paramWorld, paramInt1, paramInt2, paramInt3)) {
/* 144 */       return true;
/*     */     }
/* 146 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockFence.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
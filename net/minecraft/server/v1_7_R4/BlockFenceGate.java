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
/*     */ public class BlockFenceGate
/*     */   extends BlockDirectional
/*     */ {
/*     */   public BlockFenceGate() {
/*  17 */     super(Material.WOOD);
/*  18 */     a(CreativeModeTab.d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/*  28 */     if (!paramWorld.getType(paramInt1, paramInt2 - 1, paramInt3).getMaterial().isBuildable()) return false; 
/*  29 */     return super.canPlace(paramWorld, paramInt1, paramInt2, paramInt3);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB a(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/*  34 */     int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
/*  35 */     if (b(i)) {
/*  36 */       return null;
/*     */     }
/*  38 */     if (i == 2 || i == 0) {
/*  39 */       return AxisAlignedBB.a(paramInt1, paramInt2, (paramInt3 + 0.375F), (paramInt1 + 1), (paramInt2 + 1.5F), (paramInt3 + 0.625F));
/*     */     }
/*  41 */     return AxisAlignedBB.a((paramInt1 + 0.375F), paramInt2, paramInt3, (paramInt1 + 0.625F), (paramInt2 + 1.5F), (paramInt3 + 1));
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateShape(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3) {
/*  46 */     int i = l(paramIBlockAccess.getData(paramInt1, paramInt2, paramInt3));
/*  47 */     if (i == 2 || i == 0) {
/*  48 */       a(0.0F, 0.0F, 0.375F, 1.0F, 1.0F, 0.625F);
/*     */     } else {
/*  50 */       a(0.375F, 0.0F, 0.0F, 0.625F, 1.0F, 1.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean c() {
/*  60 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean d() {
/*  65 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean b(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3) {
/*  70 */     return b(paramIBlockAccess.getData(paramInt1, paramInt2, paramInt3));
/*     */   }
/*     */ 
/*     */   
/*     */   public int b() {
/*  75 */     return 21;
/*     */   }
/*     */ 
/*     */   
/*     */   public void postPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityLiving paramEntityLiving, ItemStack paramItemStack) {
/*  80 */     int i = (MathHelper.floor((paramEntityLiving.yaw * 4.0F / 360.0F) + 0.5D) & 0x3) % 4;
/*  81 */     paramWorld.setData(paramInt1, paramInt2, paramInt3, i, 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean interact(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityHuman paramEntityHuman, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3) {
/*  86 */     int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
/*  87 */     if (b(i)) {
/*  88 */       paramWorld.setData(paramInt1, paramInt2, paramInt3, i & 0xFFFFFFFB, 2);
/*     */     } else {
/*     */       
/*  91 */       int j = (MathHelper.floor((paramEntityHuman.yaw * 4.0F / 360.0F) + 0.5D) & 0x3) % 4;
/*  92 */       int k = l(i);
/*  93 */       if (k == (j + 2) % 4) {
/*  94 */         i = j;
/*     */       }
/*  96 */       paramWorld.setData(paramInt1, paramInt2, paramInt3, i | 0x4, 2);
/*     */     } 
/*  98 */     paramWorld.a(paramEntityHuman, 1003, paramInt1, paramInt2, paramInt3, 0);
/*  99 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void doPhysics(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Block paramBlock) {
/* 104 */     if (paramWorld.isStatic)
/*     */       return; 
/* 106 */     int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
/*     */     
/* 108 */     boolean bool = paramWorld.isBlockIndirectlyPowered(paramInt1, paramInt2, paramInt3);
/* 109 */     if (bool || paramBlock.isPowerSource()) {
/* 110 */       if (bool && !b(i)) {
/* 111 */         paramWorld.setData(paramInt1, paramInt2, paramInt3, i | 0x4, 2);
/* 112 */         paramWorld.a((EntityHuman)null, 1003, paramInt1, paramInt2, paramInt3, 0);
/* 113 */       } else if (!bool && b(i)) {
/* 114 */         paramWorld.setData(paramInt1, paramInt2, paramInt3, i & 0xFFFFFFFB, 2);
/* 115 */         paramWorld.a((EntityHuman)null, 1003, paramInt1, paramInt2, paramInt3, 0);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static boolean b(int paramInt) {
/* 121 */     return ((paramInt & 0x4) != 0);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockFenceGate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
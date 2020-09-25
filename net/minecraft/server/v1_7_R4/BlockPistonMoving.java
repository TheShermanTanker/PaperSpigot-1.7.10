/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Random;
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
/*     */ public class BlockPistonMoving
/*     */   extends BlockContainer
/*     */ {
/*     */   public BlockPistonMoving() {
/*  18 */     super(Material.PISTON);
/*  19 */     c(-1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public TileEntity a(World paramWorld, int paramInt) {
/*  24 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {}
/*     */ 
/*     */   
/*     */   public void remove(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Block paramBlock, int paramInt4) {
/*  33 */     TileEntity tileEntity = paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
/*  34 */     if (tileEntity instanceof TileEntityPiston) {
/*  35 */       ((TileEntityPiston)tileEntity).f();
/*     */     } else {
/*  37 */       super.remove(paramWorld, paramInt1, paramInt2, paramInt3, paramBlock, paramInt4);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/*  43 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  48 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int b() {
/*  53 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean c() {
/*  58 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean d() {
/*  63 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean interact(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityHuman paramEntityHuman, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3) {
/*  69 */     if (!paramWorld.isStatic && paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3) == null) {
/*     */       
/*  71 */       paramWorld.setAir(paramInt1, paramInt2, paramInt3);
/*  72 */       return true;
/*     */     } 
/*  74 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public Item getDropType(int paramInt1, Random paramRandom, int paramInt2) {
/*  79 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void dropNaturally(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat, int paramInt5) {
/*  84 */     if (paramWorld.isStatic)
/*     */       return; 
/*  86 */     TileEntityPiston tileEntityPiston = e(paramWorld, paramInt1, paramInt2, paramInt3);
/*  87 */     if (tileEntityPiston == null) {
/*     */       return;
/*     */     }
/*     */     
/*  91 */     tileEntityPiston.a().b(paramWorld, paramInt1, paramInt2, paramInt3, tileEntityPiston.p(), 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void doPhysics(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Block paramBlock) {
/*  96 */     if (!paramWorld.isStatic) {
/*  97 */       paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
/*     */     }
/*     */   }
/*     */   
/*     */   public static TileEntity a(Block paramBlock, int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2) {
/* 102 */     return new TileEntityPiston(paramBlock, paramInt1, paramInt2, paramBoolean1, paramBoolean2);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB a(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/* 107 */     TileEntityPiston tileEntityPiston = e(paramWorld, paramInt1, paramInt2, paramInt3);
/* 108 */     if (tileEntityPiston == null) {
/* 109 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 113 */     float f = tileEntityPiston.a(0.0F);
/* 114 */     if (tileEntityPiston.b()) {
/* 115 */       f = 1.0F - f;
/*     */     }
/* 117 */     return a(paramWorld, paramInt1, paramInt2, paramInt3, tileEntityPiston.a(), f, tileEntityPiston.c());
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateShape(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3) {
/* 122 */     TileEntityPiston tileEntityPiston = e(paramIBlockAccess, paramInt1, paramInt2, paramInt3);
/* 123 */     if (tileEntityPiston != null) {
/* 124 */       Block block = tileEntityPiston.a();
/* 125 */       if (block == this || block.getMaterial() == Material.AIR) {
/*     */         return;
/*     */       }
/* 128 */       block.updateShape(paramIBlockAccess, paramInt1, paramInt2, paramInt3);
/*     */       
/* 130 */       float f = tileEntityPiston.a(0.0F);
/* 131 */       if (tileEntityPiston.b()) {
/* 132 */         f = 1.0F - f;
/*     */       }
/* 134 */       int i = tileEntityPiston.c();
/* 135 */       this.minX = block.x() - (Facing.b[i] * f);
/* 136 */       this.minY = block.z() - (Facing.c[i] * f);
/* 137 */       this.minZ = block.B() - (Facing.d[i] * f);
/* 138 */       this.maxX = block.y() - (Facing.b[i] * f);
/* 139 */       this.maxY = block.A() - (Facing.c[i] * f);
/* 140 */       this.maxZ = block.C() - (Facing.d[i] * f);
/*     */     } 
/*     */   }
/*     */   
/*     */   public AxisAlignedBB a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Block paramBlock, float paramFloat, int paramInt4) {
/* 145 */     if (paramBlock == this || paramBlock.getMaterial() == Material.AIR) {
/* 146 */       return null;
/*     */     }
/* 148 */     AxisAlignedBB axisAlignedBB = paramBlock.a(paramWorld, paramInt1, paramInt2, paramInt3);
/*     */     
/* 150 */     if (axisAlignedBB == null) {
/* 151 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 155 */     if (Facing.b[paramInt4] < 0) {
/* 156 */       axisAlignedBB.a -= (Facing.b[paramInt4] * paramFloat);
/*     */     } else {
/* 158 */       axisAlignedBB.d -= (Facing.b[paramInt4] * paramFloat);
/*     */     } 
/* 160 */     if (Facing.c[paramInt4] < 0) {
/* 161 */       axisAlignedBB.b -= (Facing.c[paramInt4] * paramFloat);
/*     */     } else {
/* 163 */       axisAlignedBB.e -= (Facing.c[paramInt4] * paramFloat);
/*     */     } 
/* 165 */     if (Facing.d[paramInt4] < 0) {
/* 166 */       axisAlignedBB.c -= (Facing.d[paramInt4] * paramFloat);
/*     */     } else {
/* 168 */       axisAlignedBB.f -= (Facing.d[paramInt4] * paramFloat);
/*     */     } 
/* 170 */     return axisAlignedBB;
/*     */   }
/*     */   
/*     */   private TileEntityPiston e(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3) {
/* 174 */     TileEntity tileEntity = paramIBlockAccess.getTileEntity(paramInt1, paramInt2, paramInt3);
/* 175 */     if (tileEntity instanceof TileEntityPiston) {
/* 176 */       return (TileEntityPiston)tileEntity;
/*     */     }
/* 178 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockPistonMoving.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
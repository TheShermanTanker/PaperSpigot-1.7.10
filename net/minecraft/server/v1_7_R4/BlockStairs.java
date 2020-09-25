/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.List;
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
/*     */ 
/*     */ public class BlockStairs
/*     */   extends Block
/*     */ {
/*  19 */   private static final int[][] a = new int[][] { { 2, 6 }, { 3, 7 }, { 2, 3 }, { 6, 7 }, { 0, 4 }, { 1, 5 }, { 0, 1 }, { 4, 5 } };
/*     */ 
/*     */ 
/*     */   
/*     */   private final Block b;
/*     */ 
/*     */ 
/*     */   
/*     */   private final int M;
/*     */ 
/*     */   
/*     */   private boolean N;
/*     */ 
/*     */   
/*     */   private int O;
/*     */ 
/*     */ 
/*     */   
/*     */   protected BlockStairs(Block paramBlock, int paramInt) {
/*  38 */     super(paramBlock.material);
/*  39 */     this.b = paramBlock;
/*  40 */     this.M = paramInt;
/*  41 */     c(paramBlock.strength);
/*  42 */     b(paramBlock.durability / 3.0F);
/*  43 */     a(paramBlock.stepSound);
/*  44 */     g(255);
/*  45 */     a(CreativeModeTab.b);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateShape(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3) {
/*  50 */     if (this.N) {
/*  51 */       a(0.5F * (this.O % 2), 0.5F * (this.O / 2 % 2), 0.5F * (this.O / 4 % 2), 0.5F + 0.5F * (this.O % 2), 0.5F + 0.5F * (this.O / 2 % 2), 0.5F + 0.5F * (this.O / 4 % 2));
/*     */     } else {
/*  53 */       a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean c() {
/*  59 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean d() {
/*  64 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int b() {
/*  69 */     return 10;
/*     */   }
/*     */   
/*     */   public void e(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3) {
/*  73 */     int i = paramIBlockAccess.getData(paramInt1, paramInt2, paramInt3);
/*     */     
/*  75 */     if ((i & 0x4) != 0) {
/*  76 */       a(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */     } else {
/*  78 */       a(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean a(Block paramBlock) {
/*  83 */     return paramBlock instanceof BlockStairs;
/*     */   }
/*     */   
/*     */   private boolean f(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  87 */     Block block = paramIBlockAccess.getType(paramInt1, paramInt2, paramInt3);
/*  88 */     if (a(block) && paramIBlockAccess.getData(paramInt1, paramInt2, paramInt3) == paramInt4) {
/*  89 */       return true;
/*     */     }
/*     */     
/*  92 */     return false;
/*     */   }
/*     */   
/*     */   public boolean f(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3) {
/*  96 */     int i = paramIBlockAccess.getData(paramInt1, paramInt2, paramInt3);
/*  97 */     int j = i & 0x3;
/*     */     
/*  99 */     float f1 = 0.5F;
/* 100 */     float f2 = 1.0F;
/*     */     
/* 102 */     if ((i & 0x4) != 0) {
/* 103 */       f1 = 0.0F;
/* 104 */       f2 = 0.5F;
/*     */     } 
/*     */     
/* 107 */     float f3 = 0.0F;
/* 108 */     float f4 = 1.0F;
/* 109 */     float f5 = 0.0F;
/* 110 */     float f6 = 0.5F;
/*     */     
/* 112 */     boolean bool = true;
/*     */     
/* 114 */     if (j == 0) {
/* 115 */       f3 = 0.5F;
/* 116 */       f6 = 1.0F;
/*     */       
/* 118 */       Block block = paramIBlockAccess.getType(paramInt1 + 1, paramInt2, paramInt3);
/* 119 */       int k = paramIBlockAccess.getData(paramInt1 + 1, paramInt2, paramInt3);
/* 120 */       if (a(block) && (i & 0x4) == (k & 0x4)) {
/* 121 */         int m = k & 0x3;
/* 122 */         if (m == 3 && !f(paramIBlockAccess, paramInt1, paramInt2, paramInt3 + 1, i)) {
/* 123 */           f6 = 0.5F;
/* 124 */           bool = false;
/* 125 */         } else if (m == 2 && !f(paramIBlockAccess, paramInt1, paramInt2, paramInt3 - 1, i)) {
/* 126 */           f5 = 0.5F;
/* 127 */           bool = false;
/*     */         } 
/*     */       } 
/* 130 */     } else if (j == 1) {
/* 131 */       f4 = 0.5F;
/* 132 */       f6 = 1.0F;
/*     */       
/* 134 */       Block block = paramIBlockAccess.getType(paramInt1 - 1, paramInt2, paramInt3);
/* 135 */       int k = paramIBlockAccess.getData(paramInt1 - 1, paramInt2, paramInt3);
/* 136 */       if (a(block) && (i & 0x4) == (k & 0x4)) {
/* 137 */         int m = k & 0x3;
/* 138 */         if (m == 3 && !f(paramIBlockAccess, paramInt1, paramInt2, paramInt3 + 1, i)) {
/* 139 */           f6 = 0.5F;
/* 140 */           bool = false;
/* 141 */         } else if (m == 2 && !f(paramIBlockAccess, paramInt1, paramInt2, paramInt3 - 1, i)) {
/* 142 */           f5 = 0.5F;
/* 143 */           bool = false;
/*     */         } 
/*     */       } 
/* 146 */     } else if (j == 2) {
/* 147 */       f5 = 0.5F;
/* 148 */       f6 = 1.0F;
/*     */       
/* 150 */       Block block = paramIBlockAccess.getType(paramInt1, paramInt2, paramInt3 + 1);
/* 151 */       int k = paramIBlockAccess.getData(paramInt1, paramInt2, paramInt3 + 1);
/* 152 */       if (a(block) && (i & 0x4) == (k & 0x4)) {
/* 153 */         int m = k & 0x3;
/* 154 */         if (m == 1 && !f(paramIBlockAccess, paramInt1 + 1, paramInt2, paramInt3, i)) {
/* 155 */           f4 = 0.5F;
/* 156 */           bool = false;
/* 157 */         } else if (m == 0 && !f(paramIBlockAccess, paramInt1 - 1, paramInt2, paramInt3, i)) {
/* 158 */           f3 = 0.5F;
/* 159 */           bool = false;
/*     */         } 
/*     */       } 
/* 162 */     } else if (j == 3) {
/* 163 */       Block block = paramIBlockAccess.getType(paramInt1, paramInt2, paramInt3 - 1);
/* 164 */       int k = paramIBlockAccess.getData(paramInt1, paramInt2, paramInt3 - 1);
/* 165 */       if (a(block) && (i & 0x4) == (k & 0x4)) {
/* 166 */         int m = k & 0x3;
/* 167 */         if (m == 1 && !f(paramIBlockAccess, paramInt1 + 1, paramInt2, paramInt3, i)) {
/* 168 */           f4 = 0.5F;
/* 169 */           bool = false;
/* 170 */         } else if (m == 0 && !f(paramIBlockAccess, paramInt1 - 1, paramInt2, paramInt3, i)) {
/* 171 */           f3 = 0.5F;
/* 172 */           bool = false;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 177 */     a(f3, f1, f5, f4, f2, f6);
/* 178 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean g(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3) {
/* 186 */     int i = paramIBlockAccess.getData(paramInt1, paramInt2, paramInt3);
/* 187 */     int j = i & 0x3;
/*     */     
/* 189 */     float f1 = 0.5F;
/* 190 */     float f2 = 1.0F;
/*     */     
/* 192 */     if ((i & 0x4) != 0) {
/* 193 */       f1 = 0.0F;
/* 194 */       f2 = 0.5F;
/*     */     } 
/*     */     
/* 197 */     float f3 = 0.0F;
/* 198 */     float f4 = 0.5F;
/* 199 */     float f5 = 0.5F;
/* 200 */     float f6 = 1.0F;
/*     */     
/* 202 */     boolean bool = false;
/*     */     
/* 204 */     if (j == 0) {
/* 205 */       Block block = paramIBlockAccess.getType(paramInt1 - 1, paramInt2, paramInt3);
/* 206 */       int k = paramIBlockAccess.getData(paramInt1 - 1, paramInt2, paramInt3);
/* 207 */       if (a(block) && (i & 0x4) == (k & 0x4)) {
/* 208 */         int m = k & 0x3;
/* 209 */         if (m == 3 && !f(paramIBlockAccess, paramInt1, paramInt2, paramInt3 - 1, i)) {
/* 210 */           f5 = 0.0F;
/* 211 */           f6 = 0.5F;
/* 212 */           bool = true;
/* 213 */         } else if (m == 2 && !f(paramIBlockAccess, paramInt1, paramInt2, paramInt3 + 1, i)) {
/* 214 */           f5 = 0.5F;
/* 215 */           f6 = 1.0F;
/* 216 */           bool = true;
/*     */         } 
/*     */       } 
/* 219 */     } else if (j == 1) {
/* 220 */       Block block = paramIBlockAccess.getType(paramInt1 + 1, paramInt2, paramInt3);
/* 221 */       int k = paramIBlockAccess.getData(paramInt1 + 1, paramInt2, paramInt3);
/* 222 */       if (a(block) && (i & 0x4) == (k & 0x4)) {
/* 223 */         f3 = 0.5F;
/* 224 */         f4 = 1.0F;
/* 225 */         int m = k & 0x3;
/* 226 */         if (m == 3 && !f(paramIBlockAccess, paramInt1, paramInt2, paramInt3 - 1, i)) {
/* 227 */           f5 = 0.0F;
/* 228 */           f6 = 0.5F;
/* 229 */           bool = true;
/* 230 */         } else if (m == 2 && !f(paramIBlockAccess, paramInt1, paramInt2, paramInt3 + 1, i)) {
/* 231 */           f5 = 0.5F;
/* 232 */           f6 = 1.0F;
/* 233 */           bool = true;
/*     */         } 
/*     */       } 
/* 236 */     } else if (j == 2) {
/* 237 */       Block block = paramIBlockAccess.getType(paramInt1, paramInt2, paramInt3 - 1);
/* 238 */       int k = paramIBlockAccess.getData(paramInt1, paramInt2, paramInt3 - 1);
/* 239 */       if (a(block) && (i & 0x4) == (k & 0x4)) {
/* 240 */         f5 = 0.0F;
/* 241 */         f6 = 0.5F;
/*     */         
/* 243 */         int m = k & 0x3;
/* 244 */         if (m == 1 && !f(paramIBlockAccess, paramInt1 - 1, paramInt2, paramInt3, i)) {
/* 245 */           bool = true;
/* 246 */         } else if (m == 0 && !f(paramIBlockAccess, paramInt1 + 1, paramInt2, paramInt3, i)) {
/* 247 */           f3 = 0.5F;
/* 248 */           f4 = 1.0F;
/* 249 */           bool = true;
/*     */         } 
/*     */       } 
/* 252 */     } else if (j == 3) {
/* 253 */       Block block = paramIBlockAccess.getType(paramInt1, paramInt2, paramInt3 + 1);
/* 254 */       int k = paramIBlockAccess.getData(paramInt1, paramInt2, paramInt3 + 1);
/* 255 */       if (a(block) && (i & 0x4) == (k & 0x4)) {
/* 256 */         int m = k & 0x3;
/* 257 */         if (m == 1 && !f(paramIBlockAccess, paramInt1 - 1, paramInt2, paramInt3, i)) {
/* 258 */           bool = true;
/* 259 */         } else if (m == 0 && !f(paramIBlockAccess, paramInt1 + 1, paramInt2, paramInt3, i)) {
/* 260 */           f3 = 0.5F;
/* 261 */           f4 = 1.0F;
/* 262 */           bool = true;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 267 */     if (bool) {
/* 268 */       a(f3, f1, f5, f4, f2, f6);
/*     */     }
/* 270 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, AxisAlignedBB paramAxisAlignedBB, List paramList, Entity paramEntity) {
/* 276 */     e(paramWorld, paramInt1, paramInt2, paramInt3);
/* 277 */     super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramAxisAlignedBB, paramList, paramEntity);
/*     */     
/* 279 */     boolean bool = f(paramWorld, paramInt1, paramInt2, paramInt3);
/* 280 */     super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramAxisAlignedBB, paramList, paramEntity);
/*     */     
/* 282 */     if (bool && 
/* 283 */       g(paramWorld, paramInt1, paramInt2, paramInt3)) {
/* 284 */       super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramAxisAlignedBB, paramList, paramEntity);
/*     */     }
/*     */ 
/*     */     
/* 288 */     a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
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
/*     */   
/*     */   public void attack(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityHuman paramEntityHuman) {
/* 306 */     this.b.attack(paramWorld, paramInt1, paramInt2, paramInt3, paramEntityHuman);
/*     */   }
/*     */ 
/*     */   
/*     */   public void postBreak(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 311 */     this.b.postBreak(paramWorld, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float a(Entity paramEntity) {
/* 321 */     return this.b.a(paramEntity);
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
/*     */   public int a(World paramWorld) {
/* 336 */     return this.b.a(paramWorld);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Entity paramEntity, Vec3D paramVec3D) {
/* 346 */     this.b.a(paramWorld, paramInt1, paramInt2, paramInt3, paramEntity, paramVec3D);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean v() {
/* 351 */     return this.b.v();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean a(int paramInt, boolean paramBoolean) {
/* 356 */     return this.b.a(paramInt, paramBoolean);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/* 361 */     return this.b.canPlace(paramWorld, paramInt1, paramInt2, paramInt3);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/* 366 */     doPhysics(paramWorld, paramInt1, paramInt2, paramInt3, Blocks.AIR);
/* 367 */     this.b.onPlace(paramWorld, paramInt1, paramInt2, paramInt3);
/*     */   }
/*     */ 
/*     */   
/*     */   public void remove(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Block paramBlock, int paramInt4) {
/* 372 */     this.b.remove(paramWorld, paramInt1, paramInt2, paramInt3, paramBlock, paramInt4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void b(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Entity paramEntity) {
/* 382 */     this.b.b(paramWorld, paramInt1, paramInt2, paramInt3, paramEntity);
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Random paramRandom) {
/* 387 */     this.b.a(paramWorld, paramInt1, paramInt2, paramInt3, paramRandom);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean interact(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityHuman paramEntityHuman, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3) {
/* 392 */     return this.b.interact(paramWorld, paramInt1, paramInt2, paramInt3, paramEntityHuman, 0, 0.0F, 0.0F, 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void wasExploded(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Explosion paramExplosion) {
/* 397 */     this.b.wasExploded(paramWorld, paramInt1, paramInt2, paramInt3, paramExplosion);
/*     */   }
/*     */ 
/*     */   
/*     */   public MaterialMapColor f(int paramInt) {
/* 402 */     return this.b.f(this.M);
/*     */   }
/*     */ 
/*     */   
/*     */   public void postPlace(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityLiving paramEntityLiving, ItemStack paramItemStack) {
/* 407 */     int i = MathHelper.floor((paramEntityLiving.yaw * 4.0F / 360.0F) + 0.5D) & 0x3;
/* 408 */     int j = paramWorld.getData(paramInt1, paramInt2, paramInt3) & 0x4;
/*     */     
/* 410 */     if (i == 0) paramWorld.setData(paramInt1, paramInt2, paramInt3, 0x2 | j, 2); 
/* 411 */     if (i == 1) paramWorld.setData(paramInt1, paramInt2, paramInt3, 0x1 | j, 2); 
/* 412 */     if (i == 2) paramWorld.setData(paramInt1, paramInt2, paramInt3, 0x3 | j, 2); 
/* 413 */     if (i == 3) paramWorld.setData(paramInt1, paramInt2, paramInt3, 0x0 | j, 2);
/*     */   
/*     */   }
/*     */   
/*     */   public int getPlacedData(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3, int paramInt5) {
/* 418 */     if (paramInt4 == 0 || (paramInt4 != 1 && paramFloat2 > 0.5D)) {
/* 419 */       return paramInt5 | 0x4;
/*     */     }
/* 421 */     return paramInt5;
/*     */   }
/*     */ 
/*     */   
/*     */   public MovingObjectPosition a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Vec3D paramVec3D1, Vec3D paramVec3D2) {
/* 426 */     MovingObjectPosition[] arrayOfMovingObjectPosition = new MovingObjectPosition[8];
/* 427 */     int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
/* 428 */     int j = i & 0x3;
/* 429 */     boolean bool = ((i & 0x4) == 4) ? true : false;
/* 430 */     int[] arrayOfInt = a[j + (bool ? 4 : 0)];
/*     */     
/* 432 */     this.N = true;
/* 433 */     for (byte b = 0; b < 8; b++) {
/* 434 */       this.O = b;
/*     */       
/* 436 */       for (int k : arrayOfInt) {
/* 437 */         if (k == b);
/*     */       } 
/*     */       
/* 440 */       arrayOfMovingObjectPosition[b] = super.a(paramWorld, paramInt1, paramInt2, paramInt3, paramVec3D1, paramVec3D2);
/*     */     } 
/*     */     
/* 443 */     for (int k : arrayOfInt) {
/* 444 */       arrayOfMovingObjectPosition[k] = null;
/*     */     }
/*     */     
/* 447 */     MovingObjectPosition movingObjectPosition = null;
/* 448 */     double d = 0.0D;
/*     */     
/* 450 */     for (MovingObjectPosition movingObjectPosition1 : arrayOfMovingObjectPosition) {
/* 451 */       if (movingObjectPosition1 != null) {
/* 452 */         double d1 = movingObjectPosition1.pos.distanceSquared(paramVec3D2);
/*     */         
/* 454 */         if (d1 > d) {
/* 455 */           movingObjectPosition = movingObjectPosition1;
/* 456 */           d = d1;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 461 */     return movingObjectPosition;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockStairs.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
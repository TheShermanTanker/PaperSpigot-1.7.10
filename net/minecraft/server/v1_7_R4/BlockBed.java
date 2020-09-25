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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockBed
/*     */   extends BlockDirectional
/*     */ {
/*  24 */   public static final int[][] a = new int[][] { { 0, 1 }, { -1, 0 }, { 0, -1 }, { 1, 0 } };
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
/*     */   public BlockBed() {
/*  41 */     super(Material.CLOTH);
/*     */     
/*  43 */     e();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean interact(World paramWorld, int paramInt1, int paramInt2, int paramInt3, EntityHuman paramEntityHuman, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3) {
/*  48 */     if (paramWorld.isStatic) return true;
/*     */     
/*  50 */     int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
/*     */     
/*  52 */     if (!b(i)) {
/*     */       
/*  54 */       int j = l(i);
/*  55 */       paramInt1 += a[j][0];
/*  56 */       paramInt3 += a[j][1];
/*  57 */       if (paramWorld.getType(paramInt1, paramInt2, paramInt3) != this) {
/*  58 */         return true;
/*     */       }
/*  60 */       i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
/*     */     } 
/*     */     
/*  63 */     if (!paramWorld.worldProvider.e() || paramWorld.getBiome(paramInt1, paramInt3) == BiomeBase.HELL) {
/*  64 */       double d1 = paramInt1 + 0.5D;
/*  65 */       double d2 = paramInt2 + 0.5D;
/*  66 */       double d3 = paramInt3 + 0.5D;
/*  67 */       paramWorld.setAir(paramInt1, paramInt2, paramInt3);
/*  68 */       int j = l(i);
/*  69 */       paramInt1 += a[j][0];
/*  70 */       paramInt3 += a[j][1];
/*  71 */       if (paramWorld.getType(paramInt1, paramInt2, paramInt3) == this) {
/*  72 */         paramWorld.setAir(paramInt1, paramInt2, paramInt3);
/*  73 */         d1 = (d1 + paramInt1 + 0.5D) / 2.0D;
/*  74 */         d2 = (d2 + paramInt2 + 0.5D) / 2.0D;
/*  75 */         d3 = (d3 + paramInt3 + 0.5D) / 2.0D;
/*     */       } 
/*  77 */       paramWorld.createExplosion(null, (paramInt1 + 0.5F), (paramInt2 + 0.5F), (paramInt3 + 0.5F), 5.0F, true, true);
/*  78 */       return true;
/*     */     } 
/*     */     
/*  81 */     if (c(i)) {
/*  82 */       EntityHuman entityHuman = null;
/*  83 */       for (EntityHuman entityHuman1 : paramWorld.players) {
/*  84 */         if (entityHuman1.isSleeping()) {
/*  85 */           ChunkCoordinates chunkCoordinates = entityHuman1.bB;
/*  86 */           if (chunkCoordinates.x == paramInt1 && chunkCoordinates.y == paramInt2 && chunkCoordinates.z == paramInt3) {
/*  87 */             entityHuman = entityHuman1;
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/*  92 */       if (entityHuman == null) {
/*  93 */         a(paramWorld, paramInt1, paramInt2, paramInt3, false);
/*     */       } else {
/*  95 */         paramEntityHuman.b(new ChatMessage("tile.bed.occupied", new Object[0]));
/*  96 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 100 */     EnumBedResult enumBedResult = paramEntityHuman.a(paramInt1, paramInt2, paramInt3);
/* 101 */     if (enumBedResult == EnumBedResult.OK) {
/* 102 */       a(paramWorld, paramInt1, paramInt2, paramInt3, true);
/* 103 */       return true;
/*     */     } 
/*     */     
/* 106 */     if (enumBedResult == EnumBedResult.NOT_POSSIBLE_NOW) {
/* 107 */       paramEntityHuman.b(new ChatMessage("tile.bed.noSleep", new Object[0]));
/* 108 */     } else if (enumBedResult == EnumBedResult.NOT_SAFE) {
/* 109 */       paramEntityHuman.b(new ChatMessage("tile.bed.notSafe", new Object[0]));
/*     */     } 
/* 111 */     return true;
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
/*     */   public int b() {
/* 142 */     return 14;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean d() {
/* 147 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean c() {
/* 152 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateShape(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3) {
/* 157 */     e();
/*     */   }
/*     */ 
/*     */   
/*     */   public void doPhysics(World paramWorld, int paramInt1, int paramInt2, int paramInt3, Block paramBlock) {
/* 162 */     int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
/* 163 */     int j = l(i);
/*     */     
/* 165 */     if (b(i)) {
/* 166 */       if (paramWorld.getType(paramInt1 - a[j][0], paramInt2, paramInt3 - a[j][1]) != this) {
/* 167 */         paramWorld.setAir(paramInt1, paramInt2, paramInt3);
/*     */       }
/*     */     }
/* 170 */     else if (paramWorld.getType(paramInt1 + a[j][0], paramInt2, paramInt3 + a[j][1]) != this) {
/* 171 */       paramWorld.setAir(paramInt1, paramInt2, paramInt3);
/* 172 */       if (!paramWorld.isStatic) {
/* 173 */         b(paramWorld, paramInt1, paramInt2, paramInt3, i, 0);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getDropType(int paramInt1, Random paramRandom, int paramInt2) {
/* 181 */     if (b(paramInt1)) {
/* 182 */       return Item.getById(0);
/*     */     }
/* 184 */     return Items.BED;
/*     */   }
/*     */   
/*     */   private void e() {
/* 188 */     a(0.0F, 0.0F, 0.0F, 1.0F, 0.5625F, 1.0F);
/*     */   }
/*     */   
/*     */   public static boolean b(int paramInt) {
/* 192 */     return ((paramInt & 0x8) != 0);
/*     */   }
/*     */   
/*     */   public static boolean c(int paramInt) {
/* 196 */     return ((paramInt & 0x4) != 0);
/*     */   }
/*     */   
/*     */   public static void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean) {
/* 200 */     int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
/* 201 */     if (paramBoolean) {
/* 202 */       i |= 0x4;
/*     */     } else {
/* 204 */       i &= 0xFFFFFFFB;
/*     */     } 
/* 206 */     paramWorld.setData(paramInt1, paramInt2, paramInt3, i, 4);
/*     */   }
/*     */   
/*     */   public static ChunkCoordinates a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 210 */     int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
/* 211 */     int j = BlockDirectional.l(i);
/*     */ 
/*     */     
/* 214 */     for (byte b = 0; b <= 1; b++) {
/* 215 */       int k = paramInt1 - a[j][0] * b - 1;
/* 216 */       int m = paramInt3 - a[j][1] * b - 1;
/* 217 */       int n = k + 2;
/* 218 */       int i1 = m + 2;
/*     */       
/* 220 */       for (int i2 = k; i2 <= n; i2++) {
/* 221 */         for (int i3 = m; i3 <= i1; i3++) {
/* 222 */           if (World.a(paramWorld, i2, paramInt2 - 1, i3) && !paramWorld.getType(i2, paramInt2, i3).getMaterial().k() && !paramWorld.getType(i2, paramInt2 + 1, i3).getMaterial().k()) {
/* 223 */             if (paramInt4 > 0) {
/* 224 */               paramInt4--;
/*     */             } else {
/*     */               
/* 227 */               return new ChunkCoordinates(i2, paramInt2, i3);
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 233 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void dropNaturally(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat, int paramInt5) {
/* 238 */     if (!b(paramInt4)) {
/* 239 */       super.dropNaturally(paramWorld, paramInt1, paramInt2, paramInt3, paramInt4, paramFloat, 0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int h() {
/* 245 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, EntityHuman paramEntityHuman) {
/* 255 */     if (paramEntityHuman.abilities.canInstantlyBuild && 
/* 256 */       b(paramInt4)) {
/* 257 */       int i = l(paramInt4);
/* 258 */       paramInt1 -= a[i][0];
/* 259 */       paramInt3 -= a[i][1];
/* 260 */       if (paramWorld.getType(paramInt1, paramInt2, paramInt3) == this)
/* 261 */         paramWorld.setAir(paramInt1, paramInt2, paramInt3); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockBed.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
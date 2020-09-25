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
/*     */ public abstract class WorldProvider
/*     */ {
/*  15 */   public static final float[] a = new float[] { 1.0F, 0.75F, 0.5F, 0.25F, 0.0F, 0.25F, 0.5F, 0.75F };
/*     */   
/*     */   public World b;
/*     */   
/*     */   public WorldType type;
/*     */   public String d;
/*     */   public WorldChunkManager e;
/*     */   public boolean f;
/*     */   public boolean g;
/*  24 */   public float[] h = new float[16];
/*     */   public int dimension;
/*     */   
/*     */   public final void a(World paramWorld) {
/*  28 */     this.b = paramWorld;
/*  29 */     this.type = paramWorld.getWorldData().getType();
/*  30 */     this.d = paramWorld.getWorldData().getGeneratorOptions();
/*  31 */     b();
/*  32 */     a();
/*     */   }
/*     */   
/*     */   protected void a() {
/*  36 */     float f = 0.0F;
/*  37 */     for (byte b = 0; b <= 15; b++) {
/*  38 */       float f1 = 1.0F - b / 15.0F;
/*  39 */       this.h[b] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * (1.0F - f) + f;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void b() {
/*  44 */     if (this.b.getWorldData().getType() == WorldType.FLAT) {
/*  45 */       WorldGenFlatInfo worldGenFlatInfo = WorldGenFlatInfo.a(this.b.getWorldData().getGeneratorOptions());
/*  46 */       this.e = new WorldChunkManagerHell(BiomeBase.getBiome(worldGenFlatInfo.a()), 0.5F);
/*     */     } else {
/*  48 */       this.e = new WorldChunkManager(this.b);
/*     */     } 
/*     */   }
/*     */   
/*     */   public IChunkProvider getChunkProvider() {
/*  53 */     if (this.type == WorldType.FLAT)
/*     */     {
/*  55 */       return new ChunkProviderFlat(this.b, this.b.getSeed(), this.b.getWorldData().shouldGenerateMapFeatures(), this.d);
/*     */     }
/*  57 */     return new ChunkProviderGenerate(this.b, this.b.getSeed(), this.b.getWorldData().shouldGenerateMapFeatures());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canSpawn(int paramInt1, int paramInt2) {
/*  62 */     return (this.b.b(paramInt1, paramInt2) == Blocks.GRASS);
/*     */   }
/*     */   
/*     */   public float a(long paramLong, float paramFloat) {
/*  66 */     int i = (int)(paramLong % 24000L);
/*  67 */     float f1 = (i + paramFloat) / 24000.0F - 0.25F;
/*  68 */     if (f1 < 0.0F) f1++; 
/*  69 */     if (f1 > 1.0F) f1--; 
/*  70 */     float f2 = f1;
/*  71 */     f1 = 1.0F - (float)((Math.cos(f1 * Math.PI) + 1.0D) / 2.0D);
/*  72 */     f1 = f2 + (f1 - f2) / 3.0F;
/*  73 */     return f1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int a(long paramLong) {
/*  78 */     return (int)(paramLong / 24000L % 8L + 8L) % 8;
/*     */   }
/*     */   
/*     */   public boolean d() {
/*  82 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  87 */   private float[] j = new float[4];
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
/*     */   public boolean e() {
/* 124 */     return true;
/*     */   }
/*     */   
/*     */   public static WorldProvider byDimension(int paramInt) {
/* 128 */     if (paramInt == -1) return new WorldProviderHell(); 
/* 129 */     if (paramInt == 0) return new WorldProviderNormal(); 
/* 130 */     if (paramInt == 1) return new WorldProviderTheEnd(); 
/* 131 */     return null;
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
/*     */   public ChunkCoordinates h() {
/* 143 */     return null;
/*     */   }
/*     */   
/*     */   public int getSeaLevel() {
/* 147 */     if (this.type == WorldType.FLAT) {
/* 148 */       return 4;
/*     */     }
/* 150 */     return 64;
/*     */   }
/*     */   
/*     */   public abstract String getName();
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
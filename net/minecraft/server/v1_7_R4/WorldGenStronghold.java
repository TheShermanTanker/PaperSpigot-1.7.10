/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldGenStronghold
/*     */   extends StructureGenerator
/*     */ {
/*     */   private List e;
/*     */   private boolean f;
/*  18 */   private ChunkCoordIntPair[] g = new ChunkCoordIntPair[3];
/*  19 */   private double h = 32.0D;
/*  20 */   private int i = 3;
/*     */ 
/*     */   
/*     */   public WorldGenStronghold() {
/*  24 */     this.e = new ArrayList();
/*  25 */     for (BiomeBase biomeBase : BiomeBase.getBiomes()) {
/*  26 */       if (biomeBase != null && biomeBase.am > 0.0F) {
/*  27 */         this.e.add(biomeBase);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public WorldGenStronghold(Map paramMap) {
/*  33 */     this();
/*  34 */     for (Map.Entry entry : paramMap.entrySet()) {
/*  35 */       if (((String)entry.getKey()).equals("distance")) {
/*  36 */         this.h = MathHelper.a((String)entry.getValue(), this.h, 1.0D); continue;
/*  37 */       }  if (((String)entry.getKey()).equals("count")) {
/*  38 */         this.g = new ChunkCoordIntPair[MathHelper.a((String)entry.getValue(), this.g.length, 1)]; continue;
/*  39 */       }  if (((String)entry.getKey()).equals("spread")) {
/*  40 */         this.i = MathHelper.a((String)entry.getValue(), this.i, 1);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String a() {
/*  47 */     return "Stronghold";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean a(int paramInt1, int paramInt2) {
/*  53 */     if (!this.f) {
/*  54 */       Random random = new Random();
/*     */       
/*  56 */       random.setSeed(this.c.getSeed());
/*     */       
/*  58 */       double d = random.nextDouble() * Math.PI * 2.0D;
/*  59 */       int i = 1;
/*     */       
/*  61 */       for (byte b = 0; b < this.g.length; b++) {
/*  62 */         double d1 = (1.25D * i + random.nextDouble()) * this.h * i;
/*  63 */         int j = (int)Math.round(Math.cos(d) * d1);
/*  64 */         int k = (int)Math.round(Math.sin(d) * d1);
/*     */         
/*  66 */         ChunkPosition chunkPosition = this.c.getWorldChunkManager().a((j << 4) + 8, (k << 4) + 8, 112, this.e, random);
/*  67 */         if (chunkPosition != null) {
/*  68 */           j = chunkPosition.x >> 4;
/*  69 */           k = chunkPosition.z >> 4;
/*     */         } 
/*     */         
/*  72 */         this.g[b] = new ChunkCoordIntPair(j, k);
/*     */         
/*  74 */         d += 6.283185307179586D * i / this.i;
/*     */         
/*  76 */         if (b == this.i) {
/*  77 */           i += 2 + random.nextInt(5);
/*  78 */           this.i += 1 + random.nextInt(2);
/*     */         } 
/*     */       } 
/*     */       
/*  82 */       this.f = true;
/*     */     } 
/*  84 */     for (ChunkCoordIntPair chunkCoordIntPair : this.g) {
/*  85 */       if (paramInt1 == chunkCoordIntPair.x && paramInt2 == chunkCoordIntPair.z) {
/*  86 */         return true;
/*     */       }
/*     */     } 
/*  89 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected List o_() {
/*  94 */     ArrayList<ChunkPosition> arrayList = new ArrayList();
/*  95 */     for (ChunkCoordIntPair chunkCoordIntPair : this.g) {
/*  96 */       if (chunkCoordIntPair != null) {
/*  97 */         arrayList.add(chunkCoordIntPair.a(64));
/*     */       }
/*     */     } 
/* 100 */     return arrayList;
/*     */   }
/*     */ 
/*     */   
/*     */   protected StructureStart b(int paramInt1, int paramInt2) {
/* 105 */     WorldGenStronghold2Start worldGenStronghold2Start = new WorldGenStronghold2Start(this.c, this.b, paramInt1, paramInt2);
/*     */     
/* 107 */     while (worldGenStronghold2Start.b().isEmpty() || ((WorldGenStrongholdStart)worldGenStronghold2Start.b().get(0)).b == null)
/*     */     {
/* 109 */       worldGenStronghold2Start = new WorldGenStronghold2Start(this.c, this.b, paramInt1, paramInt2);
/*     */     }
/*     */     
/* 112 */     return worldGenStronghold2Start;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenStronghold.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
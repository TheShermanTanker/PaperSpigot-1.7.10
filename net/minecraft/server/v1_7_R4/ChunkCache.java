/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChunkCache
/*     */   implements IBlockAccess
/*     */ {
/*     */   private int a;
/*     */   private int b;
/*     */   private Chunk[][] c;
/*     */   private boolean d;
/*     */   private World e;
/*     */   
/*     */   public ChunkCache(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7) {
/*  18 */     this.e = paramWorld;
/*     */     
/*  20 */     this.a = paramInt1 - paramInt7 >> 4;
/*  21 */     this.b = paramInt3 - paramInt7 >> 4;
/*  22 */     int i = paramInt4 + paramInt7 >> 4;
/*  23 */     int j = paramInt6 + paramInt7 >> 4;
/*     */     
/*  25 */     this.c = new Chunk[i - this.a + 1][j - this.b + 1];
/*     */     
/*  27 */     this.d = true; int k;
/*  28 */     for (k = this.a; k <= i; k++) {
/*  29 */       for (int m = this.b; m <= j; m++) {
/*  30 */         Chunk chunk = paramWorld.getChunkAt(k, m);
/*  31 */         if (chunk != null) {
/*  32 */           this.c[k - this.a][m - this.b] = chunk;
/*     */         }
/*     */       } 
/*     */     } 
/*  36 */     for (k = paramInt1 >> 4; k <= paramInt4 >> 4; k++) {
/*  37 */       for (int m = paramInt3 >> 4; m <= paramInt6 >> 4; m++) {
/*  38 */         Chunk chunk = this.c[k - this.a][m - this.b];
/*  39 */         if (chunk != null && 
/*  40 */           !chunk.c(paramInt2, paramInt5)) {
/*  41 */           this.d = false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Block getType(int paramInt1, int paramInt2, int paramInt3) {
/*  55 */     Block block = Blocks.AIR;
/*  56 */     if (paramInt2 >= 0 && 
/*  57 */       paramInt2 < 256) {
/*     */       
/*  59 */       int i = (paramInt1 >> 4) - this.a;
/*  60 */       int j = (paramInt3 >> 4) - this.b;
/*     */       
/*  62 */       if (i >= 0 && i < this.c.length && j >= 0 && j < (this.c[i]).length) {
/*     */         
/*  64 */         Chunk chunk = this.c[i][j];
/*  65 */         if (chunk != null) {
/*  66 */           block = chunk.getType(paramInt1 & 0xF, paramInt2, paramInt3 & 0xF);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  71 */     return block;
/*     */   }
/*     */ 
/*     */   
/*     */   public TileEntity getTileEntity(int paramInt1, int paramInt2, int paramInt3) {
/*  76 */     int i = (paramInt1 >> 4) - this.a;
/*  77 */     int j = (paramInt3 >> 4) - this.b;
/*     */     
/*  79 */     return this.c[i][j].e(paramInt1 & 0xF, paramInt2, paramInt3 & 0xF);
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
/*     */   public int getData(int paramInt1, int paramInt2, int paramInt3) {
/* 142 */     if (paramInt2 < 0) return 0; 
/* 143 */     if (paramInt2 >= 256) return 0; 
/* 144 */     int i = (paramInt1 >> 4) - this.a;
/* 145 */     int j = (paramInt3 >> 4) - this.b;
/*     */     
/* 147 */     return this.c[i][j].getData(paramInt1 & 0xF, paramInt2, paramInt3 & 0xF);
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
/*     */   public int getBlockPower(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 214 */     return getType(paramInt1, paramInt2, paramInt3).c(this, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ChunkCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
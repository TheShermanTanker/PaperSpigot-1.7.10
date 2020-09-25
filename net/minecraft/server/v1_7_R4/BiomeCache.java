/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BiomeCache
/*    */ {
/*    */   private final WorldChunkManager a;
/*    */   private long b;
/* 39 */   private LongHashMap c = new LongHashMap();
/* 40 */   private List d = new ArrayList();
/*    */   
/*    */   public BiomeCache(WorldChunkManager paramWorldChunkManager) {
/* 43 */     this.a = paramWorldChunkManager;
/*    */   }
/*    */   
/*    */   public BiomeCacheBlock a(int paramInt1, int paramInt2) {
/* 47 */     paramInt1 >>= 4;
/* 48 */     paramInt2 >>= 4;
/* 49 */     long l = paramInt1 & 0xFFFFFFFFL | (paramInt2 & 0xFFFFFFFFL) << 32L;
/* 50 */     BiomeCacheBlock biomeCacheBlock = (BiomeCacheBlock)this.c.getEntry(l);
/* 51 */     if (biomeCacheBlock == null) {
/* 52 */       biomeCacheBlock = new BiomeCacheBlock(this, paramInt1, paramInt2);
/* 53 */       this.c.put(l, biomeCacheBlock);
/* 54 */       this.d.add(biomeCacheBlock);
/*    */     } 
/* 56 */     biomeCacheBlock.e = MinecraftServer.ar();
/* 57 */     return biomeCacheBlock;
/*    */   }
/*    */   
/*    */   public BiomeBase b(int paramInt1, int paramInt2) {
/* 61 */     return a(paramInt1, paramInt2).a(paramInt1, paramInt2);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void a() {
/* 69 */     long l1 = MinecraftServer.ar();
/* 70 */     long l2 = l1 - this.b;
/* 71 */     if (l2 > 7500L || l2 < 0L) {
/* 72 */       this.b = l1;
/*    */       
/* 74 */       for (byte b = 0; b < this.d.size(); b++) {
/* 75 */         BiomeCacheBlock biomeCacheBlock = this.d.get(b);
/* 76 */         long l = l1 - biomeCacheBlock.e;
/* 77 */         if (l > 30000L || l < 0L) {
/* 78 */           this.d.remove(b--);
/* 79 */           long l3 = biomeCacheBlock.c & 0xFFFFFFFFL | (biomeCacheBlock.d & 0xFFFFFFFFL) << 32L;
/* 80 */           this.c.remove(l3);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public BiomeBase[] d(int paramInt1, int paramInt2) {
/* 87 */     return (a(paramInt1, paramInt2)).b;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BiomeCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
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
/*     */ public class WorldGenStronghold2Start
/*     */   extends StructureStart
/*     */ {
/*     */   public WorldGenStronghold2Start() {}
/*     */   
/*     */   public WorldGenStronghold2Start(World paramWorld, Random paramRandom, int paramInt1, int paramInt2) {
/* 122 */     super(paramInt1, paramInt2);
/*     */     
/* 124 */     WorldGenStrongholdPieces.b();
/*     */     
/* 126 */     WorldGenStrongholdStart worldGenStrongholdStart = new WorldGenStrongholdStart(0, paramRandom, (paramInt1 << 4) + 2, (paramInt2 << 4) + 2);
/* 127 */     this.a.add(worldGenStrongholdStart);
/* 128 */     worldGenStrongholdStart.a(worldGenStrongholdStart, this.a, paramRandom);
/*     */     
/* 130 */     List<StructurePiece> list = worldGenStrongholdStart.c;
/* 131 */     while (!list.isEmpty()) {
/* 132 */       int i = paramRandom.nextInt(list.size());
/* 133 */       StructurePiece structurePiece = list.remove(i);
/* 134 */       structurePiece.a(worldGenStrongholdStart, this.a, paramRandom);
/*     */     } 
/*     */     
/* 137 */     c();
/* 138 */     a(paramWorld, paramRandom, 10);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenStronghold2Start.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
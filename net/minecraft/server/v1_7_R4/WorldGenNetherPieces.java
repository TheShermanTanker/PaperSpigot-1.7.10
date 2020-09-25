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
/*     */ public class WorldGenNetherPieces
/*     */ {
/*     */   public static void a() {
/*  21 */     WorldGenFactory.a(WorldGenNetherPiece1.class, "NeBCr");
/*  22 */     WorldGenFactory.a(WorldGenNetherPiece2.class, "NeBEF");
/*  23 */     WorldGenFactory.a(WorldGenNetherPiece3.class, "NeBS");
/*  24 */     WorldGenFactory.a(WorldGenNetherPiece4.class, "NeCCS");
/*  25 */     WorldGenFactory.a(WorldGenNetherPiece5.class, "NeCTB");
/*  26 */     WorldGenFactory.a(WorldGenNetherPiece6.class, "NeCE");
/*  27 */     WorldGenFactory.a(WorldGenNetherPiece7.class, "NeSCSC");
/*  28 */     WorldGenFactory.a(WorldGenNetherPiece8.class, "NeSCLT");
/*  29 */     WorldGenFactory.a(WorldGenNetherPiece9.class, "NeSC");
/*  30 */     WorldGenFactory.a(WorldGenNetherPiece10.class, "NeSCRT");
/*  31 */     WorldGenFactory.a(WorldGenNetherPiece11.class, "NeCSR");
/*  32 */     WorldGenFactory.a(WorldGenNetherPiece12.class, "NeMT");
/*  33 */     WorldGenFactory.a(WorldGenNetherPiece13.class, "NeRC");
/*  34 */     WorldGenFactory.a(WorldGenNetherPiece16.class, "NeSR");
/*  35 */     WorldGenFactory.a(WorldGenNetherPiece15.class, "NeStart");
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
/*  66 */   private static final WorldGenNetherPieceWeight[] a = new WorldGenNetherPieceWeight[] { new WorldGenNetherPieceWeight(WorldGenNetherPiece3.class, 30, 0, true), new WorldGenNetherPieceWeight(WorldGenNetherPiece1.class, 10, 4), new WorldGenNetherPieceWeight(WorldGenNetherPiece13.class, 10, 4), new WorldGenNetherPieceWeight(WorldGenNetherPiece16.class, 10, 3), new WorldGenNetherPieceWeight(WorldGenNetherPiece12.class, 5, 2), new WorldGenNetherPieceWeight(WorldGenNetherPiece6.class, 5, 1) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  74 */   private static final WorldGenNetherPieceWeight[] b = new WorldGenNetherPieceWeight[] { new WorldGenNetherPieceWeight(WorldGenNetherPiece9.class, 25, 0, true), new WorldGenNetherPieceWeight(WorldGenNetherPiece7.class, 15, 5), new WorldGenNetherPieceWeight(WorldGenNetherPiece10.class, 5, 10), new WorldGenNetherPieceWeight(WorldGenNetherPiece8.class, 5, 10), new WorldGenNetherPieceWeight(WorldGenNetherPiece4.class, 10, 3, true), new WorldGenNetherPieceWeight(WorldGenNetherPiece5.class, 7, 2), new WorldGenNetherPieceWeight(WorldGenNetherPiece11.class, 5, 2) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static WorldGenNetherPiece b(WorldGenNetherPieceWeight paramWorldGenNetherPieceWeight, List paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/*     */     WorldGenNetherPiece11 worldGenNetherPiece11;
/*  87 */     Class<WorldGenNetherPiece3> clazz = paramWorldGenNetherPieceWeight.a;
/*  88 */     WorldGenNetherPiece3 worldGenNetherPiece3 = null;
/*     */     
/*  90 */     if (clazz == WorldGenNetherPiece3.class) {
/*  91 */       worldGenNetherPiece3 = WorldGenNetherPiece3.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
/*  92 */     } else if (clazz == WorldGenNetherPiece1.class) {
/*  93 */       WorldGenNetherPiece1 worldGenNetherPiece1 = WorldGenNetherPiece1.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
/*  94 */     } else if (clazz == WorldGenNetherPiece13.class) {
/*  95 */       WorldGenNetherPiece13 worldGenNetherPiece13 = WorldGenNetherPiece13.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
/*  96 */     } else if (clazz == WorldGenNetherPiece16.class) {
/*  97 */       WorldGenNetherPiece16 worldGenNetherPiece16 = WorldGenNetherPiece16.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
/*  98 */     } else if (clazz == WorldGenNetherPiece12.class) {
/*  99 */       WorldGenNetherPiece12 worldGenNetherPiece12 = WorldGenNetherPiece12.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
/* 100 */     } else if (clazz == WorldGenNetherPiece6.class) {
/* 101 */       WorldGenNetherPiece6 worldGenNetherPiece6 = WorldGenNetherPiece6.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
/* 102 */     } else if (clazz == WorldGenNetherPiece9.class) {
/* 103 */       WorldGenNetherPiece9 worldGenNetherPiece9 = WorldGenNetherPiece9.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
/* 104 */     } else if (clazz == WorldGenNetherPiece10.class) {
/* 105 */       WorldGenNetherPiece10 worldGenNetherPiece10 = WorldGenNetherPiece10.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
/* 106 */     } else if (clazz == WorldGenNetherPiece8.class) {
/* 107 */       WorldGenNetherPiece8 worldGenNetherPiece8 = WorldGenNetherPiece8.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
/* 108 */     } else if (clazz == WorldGenNetherPiece4.class) {
/* 109 */       WorldGenNetherPiece4 worldGenNetherPiece4 = WorldGenNetherPiece4.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
/* 110 */     } else if (clazz == WorldGenNetherPiece5.class) {
/* 111 */       WorldGenNetherPiece5 worldGenNetherPiece5 = WorldGenNetherPiece5.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
/* 112 */     } else if (clazz == WorldGenNetherPiece7.class) {
/* 113 */       WorldGenNetherPiece7 worldGenNetherPiece7 = WorldGenNetherPiece7.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
/* 114 */     } else if (clazz == WorldGenNetherPiece11.class) {
/* 115 */       worldGenNetherPiece11 = WorldGenNetherPiece11.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
/*     */     } 
/* 117 */     return worldGenNetherPiece11;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenNetherPieces.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
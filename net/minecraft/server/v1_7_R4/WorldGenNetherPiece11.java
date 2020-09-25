/*      */ package net.minecraft.server.v1_7_R4;
/*      */ 
/*      */ import java.util.List;
/*      */ import java.util.Random;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class WorldGenNetherPiece11
/*      */   extends WorldGenNetherPiece
/*      */ {
/*      */   public WorldGenNetherPiece11() {}
/*      */   
/*      */   public WorldGenNetherPiece11(int paramInt1, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, int paramInt2) {
/*  999 */     super(paramInt1);
/*      */     
/* 1001 */     this.g = paramInt2;
/* 1002 */     this.f = paramStructureBoundingBox;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void a(StructurePiece paramStructurePiece, List paramList, Random paramRandom) {
/* 1009 */     a((WorldGenNetherPiece15)paramStructurePiece, paramList, paramRandom, 5, 3, true);
/* 1010 */     a((WorldGenNetherPiece15)paramStructurePiece, paramList, paramRandom, 5, 11, true);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static WorldGenNetherPiece11 a(List paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/* 1016 */     StructureBoundingBox structureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, -5, -3, 0, 13, 14, 13, paramInt4);
/*      */     
/* 1018 */     if (!a(structureBoundingBox) || StructurePiece.a(paramList, structureBoundingBox) != null) {
/* 1019 */       return null;
/*      */     }
/*      */     
/* 1022 */     return new WorldGenNetherPiece11(paramInt5, paramRandom, structureBoundingBox, paramInt4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox) {
/* 1029 */     a(paramWorld, paramStructureBoundingBox, 0, 3, 0, 12, 4, 12, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
/*      */     
/* 1031 */     a(paramWorld, paramStructureBoundingBox, 0, 5, 0, 12, 13, 12, Blocks.AIR, Blocks.AIR, false);
/*      */ 
/*      */     
/* 1034 */     a(paramWorld, paramStructureBoundingBox, 0, 5, 0, 1, 12, 12, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
/* 1035 */     a(paramWorld, paramStructureBoundingBox, 11, 5, 0, 12, 12, 12, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
/* 1036 */     a(paramWorld, paramStructureBoundingBox, 2, 5, 11, 4, 12, 12, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
/* 1037 */     a(paramWorld, paramStructureBoundingBox, 8, 5, 11, 10, 12, 12, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
/* 1038 */     a(paramWorld, paramStructureBoundingBox, 5, 9, 11, 7, 12, 12, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
/* 1039 */     a(paramWorld, paramStructureBoundingBox, 2, 5, 0, 4, 12, 1, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
/* 1040 */     a(paramWorld, paramStructureBoundingBox, 8, 5, 0, 10, 12, 1, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
/* 1041 */     a(paramWorld, paramStructureBoundingBox, 5, 9, 0, 7, 12, 1, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
/*      */ 
/*      */     
/* 1044 */     a(paramWorld, paramStructureBoundingBox, 2, 11, 2, 10, 12, 10, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
/*      */     
/*      */     int i;
/* 1047 */     for (i = 1; i <= 11; i += 2) {
/* 1048 */       a(paramWorld, paramStructureBoundingBox, i, 10, 0, i, 11, 0, Blocks.NETHER_FENCE, Blocks.NETHER_FENCE, false);
/* 1049 */       a(paramWorld, paramStructureBoundingBox, i, 10, 12, i, 11, 12, Blocks.NETHER_FENCE, Blocks.NETHER_FENCE, false);
/* 1050 */       a(paramWorld, paramStructureBoundingBox, 0, 10, i, 0, 11, i, Blocks.NETHER_FENCE, Blocks.NETHER_FENCE, false);
/* 1051 */       a(paramWorld, paramStructureBoundingBox, 12, 10, i, 12, 11, i, Blocks.NETHER_FENCE, Blocks.NETHER_FENCE, false);
/* 1052 */       a(paramWorld, Blocks.NETHER_BRICK, 0, i, 13, 0, paramStructureBoundingBox);
/* 1053 */       a(paramWorld, Blocks.NETHER_BRICK, 0, i, 13, 12, paramStructureBoundingBox);
/* 1054 */       a(paramWorld, Blocks.NETHER_BRICK, 0, 0, 13, i, paramStructureBoundingBox);
/* 1055 */       a(paramWorld, Blocks.NETHER_BRICK, 0, 12, 13, i, paramStructureBoundingBox);
/* 1056 */       a(paramWorld, Blocks.NETHER_FENCE, 0, i + 1, 13, 0, paramStructureBoundingBox);
/* 1057 */       a(paramWorld, Blocks.NETHER_FENCE, 0, i + 1, 13, 12, paramStructureBoundingBox);
/* 1058 */       a(paramWorld, Blocks.NETHER_FENCE, 0, 0, 13, i + 1, paramStructureBoundingBox);
/* 1059 */       a(paramWorld, Blocks.NETHER_FENCE, 0, 12, 13, i + 1, paramStructureBoundingBox);
/*      */     } 
/* 1061 */     a(paramWorld, Blocks.NETHER_FENCE, 0, 0, 13, 0, paramStructureBoundingBox);
/* 1062 */     a(paramWorld, Blocks.NETHER_FENCE, 0, 0, 13, 12, paramStructureBoundingBox);
/* 1063 */     a(paramWorld, Blocks.NETHER_FENCE, 0, 0, 13, 0, paramStructureBoundingBox);
/* 1064 */     a(paramWorld, Blocks.NETHER_FENCE, 0, 12, 13, 0, paramStructureBoundingBox);
/*      */ 
/*      */     
/* 1067 */     for (i = 3; i <= 9; i += 2) {
/* 1068 */       a(paramWorld, paramStructureBoundingBox, 1, 7, i, 1, 8, i, Blocks.NETHER_FENCE, Blocks.NETHER_FENCE, false);
/* 1069 */       a(paramWorld, paramStructureBoundingBox, 11, 7, i, 11, 8, i, Blocks.NETHER_FENCE, Blocks.NETHER_FENCE, false);
/*      */     } 
/*      */ 
/*      */     
/* 1073 */     i = a(Blocks.NETHER_BRICK_STAIRS, 3); int j;
/* 1074 */     for (j = 0; j <= 6; j++) {
/* 1075 */       int m = j + 4;
/* 1076 */       for (byte b1 = 5; b1 <= 7; b1++) {
/* 1077 */         a(paramWorld, Blocks.NETHER_BRICK_STAIRS, i, b1, 5 + j, m, paramStructureBoundingBox);
/*      */       }
/* 1079 */       if (m >= 5 && m <= 8) {
/* 1080 */         a(paramWorld, paramStructureBoundingBox, 5, 5, m, 7, j + 4, m, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
/* 1081 */       } else if (m >= 9 && m <= 10) {
/* 1082 */         a(paramWorld, paramStructureBoundingBox, 5, 8, m, 7, j + 4, m, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
/*      */       } 
/* 1084 */       if (j >= 1) {
/* 1085 */         a(paramWorld, paramStructureBoundingBox, 5, 6 + j, m, 7, 9 + j, m, Blocks.AIR, Blocks.AIR, false);
/*      */       }
/*      */     } 
/* 1088 */     for (j = 5; j <= 7; j++) {
/* 1089 */       a(paramWorld, Blocks.NETHER_BRICK_STAIRS, i, j, 12, 11, paramStructureBoundingBox);
/*      */     }
/* 1091 */     a(paramWorld, paramStructureBoundingBox, 5, 6, 7, 5, 7, 7, Blocks.NETHER_FENCE, Blocks.NETHER_FENCE, false);
/* 1092 */     a(paramWorld, paramStructureBoundingBox, 7, 6, 7, 7, 7, 7, Blocks.NETHER_FENCE, Blocks.NETHER_FENCE, false);
/* 1093 */     a(paramWorld, paramStructureBoundingBox, 5, 13, 12, 7, 13, 12, Blocks.AIR, Blocks.AIR, false);
/*      */ 
/*      */     
/* 1096 */     a(paramWorld, paramStructureBoundingBox, 2, 5, 2, 3, 5, 3, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
/* 1097 */     a(paramWorld, paramStructureBoundingBox, 2, 5, 9, 3, 5, 10, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
/* 1098 */     a(paramWorld, paramStructureBoundingBox, 2, 5, 4, 2, 5, 8, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
/* 1099 */     a(paramWorld, paramStructureBoundingBox, 9, 5, 2, 10, 5, 3, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
/* 1100 */     a(paramWorld, paramStructureBoundingBox, 9, 5, 9, 10, 5, 10, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
/* 1101 */     a(paramWorld, paramStructureBoundingBox, 10, 5, 4, 10, 5, 8, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
/* 1102 */     j = a(Blocks.NETHER_BRICK_STAIRS, 0);
/* 1103 */     int k = a(Blocks.NETHER_BRICK_STAIRS, 1);
/* 1104 */     a(paramWorld, Blocks.NETHER_BRICK_STAIRS, k, 4, 5, 2, paramStructureBoundingBox);
/* 1105 */     a(paramWorld, Blocks.NETHER_BRICK_STAIRS, k, 4, 5, 3, paramStructureBoundingBox);
/* 1106 */     a(paramWorld, Blocks.NETHER_BRICK_STAIRS, k, 4, 5, 9, paramStructureBoundingBox);
/* 1107 */     a(paramWorld, Blocks.NETHER_BRICK_STAIRS, k, 4, 5, 10, paramStructureBoundingBox);
/* 1108 */     a(paramWorld, Blocks.NETHER_BRICK_STAIRS, j, 8, 5, 2, paramStructureBoundingBox);
/* 1109 */     a(paramWorld, Blocks.NETHER_BRICK_STAIRS, j, 8, 5, 3, paramStructureBoundingBox);
/* 1110 */     a(paramWorld, Blocks.NETHER_BRICK_STAIRS, j, 8, 5, 9, paramStructureBoundingBox);
/* 1111 */     a(paramWorld, Blocks.NETHER_BRICK_STAIRS, j, 8, 5, 10, paramStructureBoundingBox);
/*      */ 
/*      */     
/* 1114 */     a(paramWorld, paramStructureBoundingBox, 3, 4, 4, 4, 4, 8, Blocks.SOUL_SAND, Blocks.SOUL_SAND, false);
/* 1115 */     a(paramWorld, paramStructureBoundingBox, 8, 4, 4, 9, 4, 8, Blocks.SOUL_SAND, Blocks.SOUL_SAND, false);
/* 1116 */     a(paramWorld, paramStructureBoundingBox, 3, 5, 4, 4, 5, 8, Blocks.NETHER_WART, Blocks.NETHER_WART, false);
/* 1117 */     a(paramWorld, paramStructureBoundingBox, 8, 5, 4, 9, 5, 8, Blocks.NETHER_WART, Blocks.NETHER_WART, false);
/*      */ 
/*      */     
/* 1120 */     a(paramWorld, paramStructureBoundingBox, 4, 2, 0, 8, 2, 12, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
/* 1121 */     a(paramWorld, paramStructureBoundingBox, 0, 2, 4, 12, 2, 8, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
/*      */     
/* 1123 */     a(paramWorld, paramStructureBoundingBox, 4, 0, 0, 8, 1, 3, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
/* 1124 */     a(paramWorld, paramStructureBoundingBox, 4, 0, 9, 8, 1, 12, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
/* 1125 */     a(paramWorld, paramStructureBoundingBox, 0, 0, 4, 3, 1, 8, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
/* 1126 */     a(paramWorld, paramStructureBoundingBox, 9, 0, 4, 12, 1, 8, Blocks.NETHER_BRICK, Blocks.NETHER_BRICK, false);
/*      */     byte b;
/* 1128 */     for (b = 4; b <= 8; b++) {
/* 1129 */       for (byte b1 = 0; b1 <= 2; b1++) {
/* 1130 */         b(paramWorld, Blocks.NETHER_BRICK, 0, b, -1, b1, paramStructureBoundingBox);
/* 1131 */         b(paramWorld, Blocks.NETHER_BRICK, 0, b, -1, 12 - b1, paramStructureBoundingBox);
/*      */       } 
/*      */     } 
/* 1134 */     for (b = 0; b <= 2; b++) {
/* 1135 */       for (byte b1 = 4; b1 <= 8; b1++) {
/* 1136 */         b(paramWorld, Blocks.NETHER_BRICK, 0, b, -1, b1, paramStructureBoundingBox);
/* 1137 */         b(paramWorld, Blocks.NETHER_BRICK, 0, 12 - b, -1, b1, paramStructureBoundingBox);
/*      */       } 
/*      */     } 
/*      */     
/* 1141 */     return true;
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenNetherPiece11.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
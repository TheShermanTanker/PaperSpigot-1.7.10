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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class WorldGenVillageButcher
/*      */   extends WorldGenVillagePiece
/*      */ {
/*      */   public WorldGenVillageButcher() {}
/*      */   
/*      */   public WorldGenVillageButcher(WorldGenVillageStartPiece paramWorldGenVillageStartPiece, int paramInt1, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, int paramInt2) {
/* 1208 */     super(paramWorldGenVillageStartPiece, paramInt1);
/*      */     
/* 1210 */     this.g = paramInt2;
/* 1211 */     this.f = paramStructureBoundingBox;
/*      */   }
/*      */ 
/*      */   
/*      */   public static WorldGenVillageButcher a(WorldGenVillageStartPiece paramWorldGenVillageStartPiece, List paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/* 1216 */     StructureBoundingBox structureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, 0, 0, 0, 9, 7, 11, paramInt4);
/*      */     
/* 1218 */     if (!a(structureBoundingBox) || StructurePiece.a(paramList, structureBoundingBox) != null) {
/* 1219 */       return null;
/*      */     }
/*      */     
/* 1222 */     return new WorldGenVillageButcher(paramWorldGenVillageStartPiece, paramInt5, paramRandom, structureBoundingBox, paramInt4);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox) {
/* 1228 */     if (this.k < 0) {
/* 1229 */       this.k = b(paramWorld, paramStructureBoundingBox);
/* 1230 */       if (this.k < 0) {
/* 1231 */         return true;
/*      */       }
/* 1233 */       this.f.a(0, this.k - this.f.e + 7 - 1, 0);
/*      */     } 
/*      */ 
/*      */     
/* 1237 */     a(paramWorld, paramStructureBoundingBox, 1, 1, 1, 7, 4, 4, Blocks.AIR, Blocks.AIR, false);
/* 1238 */     a(paramWorld, paramStructureBoundingBox, 2, 1, 6, 8, 4, 10, Blocks.AIR, Blocks.AIR, false);
/*      */ 
/*      */     
/* 1241 */     a(paramWorld, paramStructureBoundingBox, 2, 0, 6, 8, 0, 10, Blocks.DIRT, Blocks.DIRT, false);
/* 1242 */     a(paramWorld, Blocks.COBBLESTONE, 0, 6, 0, 6, paramStructureBoundingBox);
/*      */     
/* 1244 */     a(paramWorld, paramStructureBoundingBox, 2, 1, 6, 2, 1, 10, Blocks.FENCE, Blocks.FENCE, false);
/* 1245 */     a(paramWorld, paramStructureBoundingBox, 8, 1, 6, 8, 1, 10, Blocks.FENCE, Blocks.FENCE, false);
/* 1246 */     a(paramWorld, paramStructureBoundingBox, 3, 1, 10, 7, 1, 10, Blocks.FENCE, Blocks.FENCE, false);
/*      */ 
/*      */     
/* 1249 */     a(paramWorld, paramStructureBoundingBox, 1, 0, 1, 7, 0, 4, Blocks.WOOD, Blocks.WOOD, false);
/* 1250 */     a(paramWorld, paramStructureBoundingBox, 0, 0, 0, 0, 3, 5, Blocks.COBBLESTONE, Blocks.COBBLESTONE, false);
/* 1251 */     a(paramWorld, paramStructureBoundingBox, 8, 0, 0, 8, 3, 5, Blocks.COBBLESTONE, Blocks.COBBLESTONE, false);
/* 1252 */     a(paramWorld, paramStructureBoundingBox, 1, 0, 0, 7, 1, 0, Blocks.COBBLESTONE, Blocks.COBBLESTONE, false);
/* 1253 */     a(paramWorld, paramStructureBoundingBox, 1, 0, 5, 7, 1, 5, Blocks.COBBLESTONE, Blocks.COBBLESTONE, false);
/*      */ 
/*      */     
/* 1256 */     a(paramWorld, paramStructureBoundingBox, 1, 2, 0, 7, 3, 0, Blocks.WOOD, Blocks.WOOD, false);
/* 1257 */     a(paramWorld, paramStructureBoundingBox, 1, 2, 5, 7, 3, 5, Blocks.WOOD, Blocks.WOOD, false);
/* 1258 */     a(paramWorld, paramStructureBoundingBox, 0, 4, 1, 8, 4, 1, Blocks.WOOD, Blocks.WOOD, false);
/* 1259 */     a(paramWorld, paramStructureBoundingBox, 0, 4, 4, 8, 4, 4, Blocks.WOOD, Blocks.WOOD, false);
/* 1260 */     a(paramWorld, paramStructureBoundingBox, 0, 5, 2, 8, 5, 3, Blocks.WOOD, Blocks.WOOD, false);
/* 1261 */     a(paramWorld, Blocks.WOOD, 0, 0, 4, 2, paramStructureBoundingBox);
/* 1262 */     a(paramWorld, Blocks.WOOD, 0, 0, 4, 3, paramStructureBoundingBox);
/* 1263 */     a(paramWorld, Blocks.WOOD, 0, 8, 4, 2, paramStructureBoundingBox);
/* 1264 */     a(paramWorld, Blocks.WOOD, 0, 8, 4, 3, paramStructureBoundingBox);
/*      */     
/* 1266 */     int i = a(Blocks.WOOD_STAIRS, 3);
/* 1267 */     int j = a(Blocks.WOOD_STAIRS, 2); byte b;
/* 1268 */     for (b = -1; b <= 2; b++) {
/* 1269 */       for (byte b1 = 0; b1 <= 8; b1++) {
/* 1270 */         a(paramWorld, Blocks.WOOD_STAIRS, i, b1, 4 + b, b, paramStructureBoundingBox);
/* 1271 */         a(paramWorld, Blocks.WOOD_STAIRS, j, b1, 4 + b, 5 - b, paramStructureBoundingBox);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1276 */     a(paramWorld, Blocks.LOG, 0, 0, 2, 1, paramStructureBoundingBox);
/* 1277 */     a(paramWorld, Blocks.LOG, 0, 0, 2, 4, paramStructureBoundingBox);
/* 1278 */     a(paramWorld, Blocks.LOG, 0, 8, 2, 1, paramStructureBoundingBox);
/* 1279 */     a(paramWorld, Blocks.LOG, 0, 8, 2, 4, paramStructureBoundingBox);
/* 1280 */     a(paramWorld, Blocks.THIN_GLASS, 0, 0, 2, 2, paramStructureBoundingBox);
/* 1281 */     a(paramWorld, Blocks.THIN_GLASS, 0, 0, 2, 3, paramStructureBoundingBox);
/* 1282 */     a(paramWorld, Blocks.THIN_GLASS, 0, 8, 2, 2, paramStructureBoundingBox);
/* 1283 */     a(paramWorld, Blocks.THIN_GLASS, 0, 8, 2, 3, paramStructureBoundingBox);
/* 1284 */     a(paramWorld, Blocks.THIN_GLASS, 0, 2, 2, 5, paramStructureBoundingBox);
/* 1285 */     a(paramWorld, Blocks.THIN_GLASS, 0, 3, 2, 5, paramStructureBoundingBox);
/* 1286 */     a(paramWorld, Blocks.THIN_GLASS, 0, 5, 2, 0, paramStructureBoundingBox);
/* 1287 */     a(paramWorld, Blocks.THIN_GLASS, 0, 6, 2, 5, paramStructureBoundingBox);
/*      */ 
/*      */     
/* 1290 */     a(paramWorld, Blocks.FENCE, 0, 2, 1, 3, paramStructureBoundingBox);
/* 1291 */     a(paramWorld, Blocks.WOOD_PLATE, 0, 2, 2, 3, paramStructureBoundingBox);
/* 1292 */     a(paramWorld, Blocks.WOOD, 0, 1, 1, 4, paramStructureBoundingBox);
/* 1293 */     a(paramWorld, Blocks.WOOD_STAIRS, a(Blocks.WOOD_STAIRS, 3), 2, 1, 4, paramStructureBoundingBox);
/* 1294 */     a(paramWorld, Blocks.WOOD_STAIRS, a(Blocks.WOOD_STAIRS, 1), 1, 1, 3, paramStructureBoundingBox);
/*      */ 
/*      */     
/* 1297 */     a(paramWorld, paramStructureBoundingBox, 5, 0, 1, 7, 0, 3, Blocks.DOUBLE_STEP, Blocks.DOUBLE_STEP, false);
/* 1298 */     a(paramWorld, Blocks.DOUBLE_STEP, 0, 6, 1, 1, paramStructureBoundingBox);
/* 1299 */     a(paramWorld, Blocks.DOUBLE_STEP, 0, 6, 1, 2, paramStructureBoundingBox);
/*      */ 
/*      */     
/* 1302 */     a(paramWorld, Blocks.AIR, 0, 2, 1, 0, paramStructureBoundingBox);
/* 1303 */     a(paramWorld, Blocks.AIR, 0, 2, 2, 0, paramStructureBoundingBox);
/* 1304 */     a(paramWorld, Blocks.TORCH, 0, 2, 3, 1, paramStructureBoundingBox);
/* 1305 */     a(paramWorld, paramStructureBoundingBox, paramRandom, 2, 1, 0, a(Blocks.WOODEN_DOOR, 1));
/* 1306 */     if (a(paramWorld, 2, 0, -1, paramStructureBoundingBox).getMaterial() == Material.AIR && a(paramWorld, 2, -1, -1, paramStructureBoundingBox).getMaterial() != Material.AIR) {
/* 1307 */       a(paramWorld, Blocks.COBBLESTONE_STAIRS, a(Blocks.COBBLESTONE_STAIRS, 3), 2, 0, -1, paramStructureBoundingBox);
/*      */     }
/*      */ 
/*      */     
/* 1311 */     a(paramWorld, Blocks.AIR, 0, 6, 1, 5, paramStructureBoundingBox);
/* 1312 */     a(paramWorld, Blocks.AIR, 0, 6, 2, 5, paramStructureBoundingBox);
/* 1313 */     a(paramWorld, Blocks.TORCH, 0, 6, 3, 4, paramStructureBoundingBox);
/* 1314 */     a(paramWorld, paramStructureBoundingBox, paramRandom, 6, 1, 5, a(Blocks.WOODEN_DOOR, 1));
/*      */     
/* 1316 */     for (b = 0; b < 5; b++) {
/* 1317 */       for (byte b1 = 0; b1 < 9; b1++) {
/* 1318 */         b(paramWorld, b1, 7, b, paramStructureBoundingBox);
/* 1319 */         b(paramWorld, Blocks.COBBLESTONE, 0, b1, -1, b, paramStructureBoundingBox);
/*      */       } 
/*      */     } 
/*      */     
/* 1323 */     a(paramWorld, paramStructureBoundingBox, 4, 1, 2, 2);
/*      */     
/* 1325 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   protected int b(int paramInt) {
/* 1330 */     if (paramInt == 0) {
/* 1331 */       return 4;
/*      */     }
/* 1333 */     return 0;
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenVillageButcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
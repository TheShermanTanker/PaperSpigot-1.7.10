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
/*      */ public class WorldGenStrongholdLibrary
/*      */   extends WorldGenStrongholdPiece
/*      */ {
/* 1181 */   private static final StructurePieceTreasure[] a = new StructurePieceTreasure[] { new StructurePieceTreasure(Items.BOOK, 0, 1, 3, 20), new StructurePieceTreasure(Items.PAPER, 0, 2, 7, 20), new StructurePieceTreasure(Items.MAP_EMPTY, 0, 1, 1, 1), new StructurePieceTreasure(Items.COMPASS, 0, 1, 1, 1) };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean b;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public WorldGenStrongholdLibrary() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public WorldGenStrongholdLibrary(int paramInt1, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, int paramInt2) {
/* 1201 */     super(paramInt1);
/*      */     
/* 1203 */     this.g = paramInt2;
/* 1204 */     this.d = a(paramRandom);
/* 1205 */     this.f = paramStructureBoundingBox;
/* 1206 */     this.b = (paramStructureBoundingBox.c() > 6);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void a(NBTTagCompound paramNBTTagCompound) {
/* 1211 */     super.a(paramNBTTagCompound);
/* 1212 */     paramNBTTagCompound.setBoolean("Tall", this.b);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void b(NBTTagCompound paramNBTTagCompound) {
/* 1217 */     super.b(paramNBTTagCompound);
/* 1218 */     this.b = paramNBTTagCompound.getBoolean("Tall");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static WorldGenStrongholdLibrary a(List paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/* 1224 */     StructureBoundingBox structureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, -4, -1, 0, 14, 11, 15, paramInt4);
/*      */     
/* 1226 */     if (!a(structureBoundingBox) || StructurePiece.a(paramList, structureBoundingBox) != null) {
/*      */       
/* 1228 */       structureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, -4, -1, 0, 14, 6, 15, paramInt4);
/*      */       
/* 1230 */       if (!a(structureBoundingBox) || StructurePiece.a(paramList, structureBoundingBox) != null) {
/* 1231 */         return null;
/*      */       }
/*      */     } 
/*      */     
/* 1235 */     return new WorldGenStrongholdLibrary(paramInt5, paramRandom, structureBoundingBox, paramInt4);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox) {
/* 1240 */     if (a(paramWorld, paramStructureBoundingBox)) {
/* 1241 */       return false;
/*      */     }
/*      */     
/* 1244 */     byte b1 = 11;
/* 1245 */     if (!this.b) {
/* 1246 */       b1 = 6;
/*      */     }
/*      */ 
/*      */     
/* 1250 */     a(paramWorld, paramStructureBoundingBox, 0, 0, 0, 13, b1 - 1, 14, true, paramRandom, WorldGenStrongholdPieces.c());
/*      */     
/* 1252 */     a(paramWorld, paramRandom, paramStructureBoundingBox, this.d, 4, 1, 0);
/*      */ 
/*      */     
/* 1255 */     a(paramWorld, paramStructureBoundingBox, paramRandom, 0.07F, 2, 1, 1, 11, 4, 13, Blocks.WEB, Blocks.WEB, false);
/*      */     
/* 1257 */     boolean bool = true;
/* 1258 */     byte b2 = 12;
/*      */     
/*      */     int i;
/* 1261 */     for (i = 1; i <= 13; i++) {
/* 1262 */       if ((i - 1) % 4 == 0) {
/* 1263 */         a(paramWorld, paramStructureBoundingBox, 1, 1, i, 1, 4, i, Blocks.WOOD, Blocks.WOOD, false);
/* 1264 */         a(paramWorld, paramStructureBoundingBox, 12, 1, i, 12, 4, i, Blocks.WOOD, Blocks.WOOD, false);
/*      */         
/* 1266 */         a(paramWorld, Blocks.TORCH, 0, 2, 3, i, paramStructureBoundingBox);
/* 1267 */         a(paramWorld, Blocks.TORCH, 0, 11, 3, i, paramStructureBoundingBox);
/*      */         
/* 1269 */         if (this.b) {
/* 1270 */           a(paramWorld, paramStructureBoundingBox, 1, 6, i, 1, 9, i, Blocks.WOOD, Blocks.WOOD, false);
/* 1271 */           a(paramWorld, paramStructureBoundingBox, 12, 6, i, 12, 9, i, Blocks.WOOD, Blocks.WOOD, false);
/*      */         } 
/*      */       } else {
/*      */         
/* 1275 */         a(paramWorld, paramStructureBoundingBox, 1, 1, i, 1, 4, i, Blocks.BOOKSHELF, Blocks.BOOKSHELF, false);
/* 1276 */         a(paramWorld, paramStructureBoundingBox, 12, 1, i, 12, 4, i, Blocks.BOOKSHELF, Blocks.BOOKSHELF, false);
/*      */         
/* 1278 */         if (this.b) {
/* 1279 */           a(paramWorld, paramStructureBoundingBox, 1, 6, i, 1, 9, i, Blocks.BOOKSHELF, Blocks.BOOKSHELF, false);
/* 1280 */           a(paramWorld, paramStructureBoundingBox, 12, 6, i, 12, 9, i, Blocks.BOOKSHELF, Blocks.BOOKSHELF, false);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1286 */     for (i = 3; i < 12; i += 2) {
/* 1287 */       a(paramWorld, paramStructureBoundingBox, 3, 1, i, 4, 3, i, Blocks.BOOKSHELF, Blocks.BOOKSHELF, false);
/* 1288 */       a(paramWorld, paramStructureBoundingBox, 6, 1, i, 7, 3, i, Blocks.BOOKSHELF, Blocks.BOOKSHELF, false);
/* 1289 */       a(paramWorld, paramStructureBoundingBox, 9, 1, i, 10, 3, i, Blocks.BOOKSHELF, Blocks.BOOKSHELF, false);
/*      */     } 
/*      */     
/* 1292 */     if (this.b) {
/*      */       
/* 1294 */       a(paramWorld, paramStructureBoundingBox, 1, 5, 1, 3, 5, 13, Blocks.WOOD, Blocks.WOOD, false);
/* 1295 */       a(paramWorld, paramStructureBoundingBox, 10, 5, 1, 12, 5, 13, Blocks.WOOD, Blocks.WOOD, false);
/* 1296 */       a(paramWorld, paramStructureBoundingBox, 4, 5, 1, 9, 5, 2, Blocks.WOOD, Blocks.WOOD, false);
/* 1297 */       a(paramWorld, paramStructureBoundingBox, 4, 5, 12, 9, 5, 13, Blocks.WOOD, Blocks.WOOD, false);
/*      */       
/* 1299 */       a(paramWorld, Blocks.WOOD, 0, 9, 5, 11, paramStructureBoundingBox);
/* 1300 */       a(paramWorld, Blocks.WOOD, 0, 8, 5, 11, paramStructureBoundingBox);
/* 1301 */       a(paramWorld, Blocks.WOOD, 0, 9, 5, 10, paramStructureBoundingBox);
/*      */ 
/*      */       
/* 1304 */       a(paramWorld, paramStructureBoundingBox, 3, 6, 2, 3, 6, 12, Blocks.FENCE, Blocks.FENCE, false);
/* 1305 */       a(paramWorld, paramStructureBoundingBox, 10, 6, 2, 10, 6, 10, Blocks.FENCE, Blocks.FENCE, false);
/* 1306 */       a(paramWorld, paramStructureBoundingBox, 4, 6, 2, 9, 6, 2, Blocks.FENCE, Blocks.FENCE, false);
/* 1307 */       a(paramWorld, paramStructureBoundingBox, 4, 6, 12, 8, 6, 12, Blocks.FENCE, Blocks.FENCE, false);
/* 1308 */       a(paramWorld, Blocks.FENCE, 0, 9, 6, 11, paramStructureBoundingBox);
/* 1309 */       a(paramWorld, Blocks.FENCE, 0, 8, 6, 11, paramStructureBoundingBox);
/* 1310 */       a(paramWorld, Blocks.FENCE, 0, 9, 6, 10, paramStructureBoundingBox);
/*      */ 
/*      */       
/* 1313 */       i = a(Blocks.LADDER, 3);
/* 1314 */       a(paramWorld, Blocks.LADDER, i, 10, 1, 13, paramStructureBoundingBox);
/* 1315 */       a(paramWorld, Blocks.LADDER, i, 10, 2, 13, paramStructureBoundingBox);
/* 1316 */       a(paramWorld, Blocks.LADDER, i, 10, 3, 13, paramStructureBoundingBox);
/* 1317 */       a(paramWorld, Blocks.LADDER, i, 10, 4, 13, paramStructureBoundingBox);
/* 1318 */       a(paramWorld, Blocks.LADDER, i, 10, 5, 13, paramStructureBoundingBox);
/* 1319 */       a(paramWorld, Blocks.LADDER, i, 10, 6, 13, paramStructureBoundingBox);
/* 1320 */       a(paramWorld, Blocks.LADDER, i, 10, 7, 13, paramStructureBoundingBox);
/*      */ 
/*      */       
/* 1323 */       byte b3 = 7;
/* 1324 */       byte b4 = 7;
/* 1325 */       a(paramWorld, Blocks.FENCE, 0, b3 - 1, 9, b4, paramStructureBoundingBox);
/* 1326 */       a(paramWorld, Blocks.FENCE, 0, b3, 9, b4, paramStructureBoundingBox);
/* 1327 */       a(paramWorld, Blocks.FENCE, 0, b3 - 1, 8, b4, paramStructureBoundingBox);
/* 1328 */       a(paramWorld, Blocks.FENCE, 0, b3, 8, b4, paramStructureBoundingBox);
/* 1329 */       a(paramWorld, Blocks.FENCE, 0, b3 - 1, 7, b4, paramStructureBoundingBox);
/* 1330 */       a(paramWorld, Blocks.FENCE, 0, b3, 7, b4, paramStructureBoundingBox);
/*      */       
/* 1332 */       a(paramWorld, Blocks.FENCE, 0, b3 - 2, 7, b4, paramStructureBoundingBox);
/* 1333 */       a(paramWorld, Blocks.FENCE, 0, b3 + 1, 7, b4, paramStructureBoundingBox);
/* 1334 */       a(paramWorld, Blocks.FENCE, 0, b3 - 1, 7, b4 - 1, paramStructureBoundingBox);
/* 1335 */       a(paramWorld, Blocks.FENCE, 0, b3 - 1, 7, b4 + 1, paramStructureBoundingBox);
/* 1336 */       a(paramWorld, Blocks.FENCE, 0, b3, 7, b4 - 1, paramStructureBoundingBox);
/* 1337 */       a(paramWorld, Blocks.FENCE, 0, b3, 7, b4 + 1, paramStructureBoundingBox);
/*      */       
/* 1339 */       a(paramWorld, Blocks.TORCH, 0, b3 - 2, 8, b4, paramStructureBoundingBox);
/* 1340 */       a(paramWorld, Blocks.TORCH, 0, b3 + 1, 8, b4, paramStructureBoundingBox);
/* 1341 */       a(paramWorld, Blocks.TORCH, 0, b3 - 1, 8, b4 - 1, paramStructureBoundingBox);
/* 1342 */       a(paramWorld, Blocks.TORCH, 0, b3 - 1, 8, b4 + 1, paramStructureBoundingBox);
/* 1343 */       a(paramWorld, Blocks.TORCH, 0, b3, 8, b4 - 1, paramStructureBoundingBox);
/* 1344 */       a(paramWorld, Blocks.TORCH, 0, b3, 8, b4 + 1, paramStructureBoundingBox);
/*      */     } 
/*      */ 
/*      */     
/* 1348 */     a(paramWorld, paramStructureBoundingBox, paramRandom, 3, 3, 5, StructurePieceTreasure.a(a, new StructurePieceTreasure[] { Items.ENCHANTED_BOOK.a(paramRandom, 1, 5, 2) }), 1 + paramRandom.nextInt(4));
/* 1349 */     if (this.b) {
/* 1350 */       a(paramWorld, Blocks.AIR, 0, 12, 9, 1, paramStructureBoundingBox);
/* 1351 */       a(paramWorld, paramStructureBoundingBox, paramRandom, 12, 8, 1, StructurePieceTreasure.a(a, new StructurePieceTreasure[] { Items.ENCHANTED_BOOK.a(paramRandom, 1, 5, 2) }), 1 + paramRandom.nextInt(4));
/*      */     } 
/*      */ 
/*      */     
/* 1355 */     return true;
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenStrongholdLibrary.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
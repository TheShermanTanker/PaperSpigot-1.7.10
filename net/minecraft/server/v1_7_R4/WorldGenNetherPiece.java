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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class WorldGenNetherPiece
/*     */   extends StructurePiece
/*     */ {
/* 123 */   protected static final StructurePieceTreasure[] a = new StructurePieceTreasure[] { new StructurePieceTreasure(Items.DIAMOND, 0, 1, 3, 5), new StructurePieceTreasure(Items.IRON_INGOT, 0, 1, 5, 5), new StructurePieceTreasure(Items.GOLD_INGOT, 0, 1, 3, 15), new StructurePieceTreasure(Items.GOLD_SWORD, 0, 1, 1, 5), new StructurePieceTreasure(Items.GOLD_CHESTPLATE, 0, 1, 1, 5), new StructurePieceTreasure(Items.FLINT_AND_STEEL, 0, 1, 1, 5), new StructurePieceTreasure(Items.NETHER_STALK, 0, 3, 7, 5), new StructurePieceTreasure(Items.SADDLE, 0, 1, 1, 10), new StructurePieceTreasure(Items.HORSE_ARMOR_GOLD, 0, 1, 1, 8), new StructurePieceTreasure(Items.HORSE_ARMOR_IRON, 0, 1, 1, 5), new StructurePieceTreasure(Items.HORSE_ARMOR_DIAMOND, 0, 1, 1, 3) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldGenNetherPiece() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected WorldGenNetherPiece(int paramInt) {
/* 143 */     super(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void b(NBTTagCompound paramNBTTagCompound) {}
/*     */ 
/*     */   
/*     */   protected void a(NBTTagCompound paramNBTTagCompound) {}
/*     */ 
/*     */   
/*     */   private int a(List paramList) {
/* 155 */     boolean bool = false;
/* 156 */     int i = 0;
/* 157 */     for (WorldGenNetherPieceWeight worldGenNetherPieceWeight : paramList) {
/* 158 */       if (worldGenNetherPieceWeight.d > 0 && worldGenNetherPieceWeight.c < worldGenNetherPieceWeight.d) {
/* 159 */         bool = true;
/*     */       }
/* 161 */       i += worldGenNetherPieceWeight.b;
/*     */     } 
/* 163 */     return bool ? i : -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private WorldGenNetherPiece a(WorldGenNetherPiece15 paramWorldGenNetherPiece15, List paramList1, List paramList2, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/* 169 */     int i = a(paramList1);
/* 170 */     boolean bool = (i > 0 && paramInt5 <= 30) ? true : false;
/*     */     
/* 172 */     byte b = 0;
/* 173 */     while (b < 5 && bool) {
/* 174 */       b++;
/*     */       
/* 176 */       int j = paramRandom.nextInt(i);
/* 177 */       for (WorldGenNetherPieceWeight worldGenNetherPieceWeight : paramList1) {
/* 178 */         j -= worldGenNetherPieceWeight.b;
/* 179 */         if (j < 0) {
/*     */           
/* 181 */           if (!worldGenNetherPieceWeight.a(paramInt5) || (worldGenNetherPieceWeight == paramWorldGenNetherPiece15.b && !worldGenNetherPieceWeight.e)) {
/*     */             break;
/*     */           }
/*     */           
/* 185 */           WorldGenNetherPiece worldGenNetherPiece = WorldGenNetherPieces.a(worldGenNetherPieceWeight, paramList2, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
/* 186 */           if (worldGenNetherPiece != null) {
/* 187 */             worldGenNetherPieceWeight.c++;
/* 188 */             paramWorldGenNetherPiece15.b = worldGenNetherPieceWeight;
/*     */             
/* 190 */             if (!worldGenNetherPieceWeight.a()) {
/* 191 */               paramList1.remove(worldGenNetherPieceWeight);
/*     */             }
/* 193 */             return worldGenNetherPiece;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 198 */     return WorldGenNetherPiece2.a(paramList2, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
/*     */   }
/*     */   
/*     */   private StructurePiece a(WorldGenNetherPiece15 paramWorldGenNetherPiece15, List<WorldGenNetherPiece> paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, boolean paramBoolean) {
/* 202 */     if (Math.abs(paramInt1 - (paramWorldGenNetherPiece15.c()).a) > 112 || Math.abs(paramInt3 - (paramWorldGenNetherPiece15.c()).c) > 112) {
/* 203 */       return WorldGenNetherPiece2.a(paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
/*     */     }
/* 205 */     List list = paramWorldGenNetherPiece15.c;
/* 206 */     if (paramBoolean) {
/* 207 */       list = paramWorldGenNetherPiece15.d;
/*     */     }
/* 209 */     WorldGenNetherPiece worldGenNetherPiece = a(paramWorldGenNetherPiece15, list, paramList, paramRandom, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5 + 1);
/* 210 */     if (worldGenNetherPiece != null) {
/* 211 */       paramList.add(worldGenNetherPiece);
/* 212 */       paramWorldGenNetherPiece15.e.add(worldGenNetherPiece);
/*     */     } 
/* 214 */     return worldGenNetherPiece;
/*     */   }
/*     */   
/*     */   protected StructurePiece a(WorldGenNetherPiece15 paramWorldGenNetherPiece15, List paramList, Random paramRandom, int paramInt1, int paramInt2, boolean paramBoolean) {
/* 218 */     switch (this.g) {
/*     */       case 2:
/* 220 */         return a(paramWorldGenNetherPiece15, paramList, paramRandom, this.f.a + paramInt1, this.f.b + paramInt2, this.f.c - 1, this.g, d(), paramBoolean);
/*     */       case 0:
/* 222 */         return a(paramWorldGenNetherPiece15, paramList, paramRandom, this.f.a + paramInt1, this.f.b + paramInt2, this.f.f + 1, this.g, d(), paramBoolean);
/*     */       case 1:
/* 224 */         return a(paramWorldGenNetherPiece15, paramList, paramRandom, this.f.a - 1, this.f.b + paramInt2, this.f.c + paramInt1, this.g, d(), paramBoolean);
/*     */       case 3:
/* 226 */         return a(paramWorldGenNetherPiece15, paramList, paramRandom, this.f.d + 1, this.f.b + paramInt2, this.f.c + paramInt1, this.g, d(), paramBoolean);
/*     */     } 
/* 228 */     return null;
/*     */   }
/*     */   
/*     */   protected StructurePiece b(WorldGenNetherPiece15 paramWorldGenNetherPiece15, List paramList, Random paramRandom, int paramInt1, int paramInt2, boolean paramBoolean) {
/* 232 */     switch (this.g) {
/*     */       case 2:
/* 234 */         return a(paramWorldGenNetherPiece15, paramList, paramRandom, this.f.a - 1, this.f.b + paramInt1, this.f.c + paramInt2, 1, d(), paramBoolean);
/*     */       case 0:
/* 236 */         return a(paramWorldGenNetherPiece15, paramList, paramRandom, this.f.a - 1, this.f.b + paramInt1, this.f.c + paramInt2, 1, d(), paramBoolean);
/*     */       case 1:
/* 238 */         return a(paramWorldGenNetherPiece15, paramList, paramRandom, this.f.a + paramInt2, this.f.b + paramInt1, this.f.c - 1, 2, d(), paramBoolean);
/*     */       case 3:
/* 240 */         return a(paramWorldGenNetherPiece15, paramList, paramRandom, this.f.a + paramInt2, this.f.b + paramInt1, this.f.c - 1, 2, d(), paramBoolean);
/*     */     } 
/* 242 */     return null;
/*     */   }
/*     */   
/*     */   protected StructurePiece c(WorldGenNetherPiece15 paramWorldGenNetherPiece15, List paramList, Random paramRandom, int paramInt1, int paramInt2, boolean paramBoolean) {
/* 246 */     switch (this.g) {
/*     */       case 2:
/* 248 */         return a(paramWorldGenNetherPiece15, paramList, paramRandom, this.f.d + 1, this.f.b + paramInt1, this.f.c + paramInt2, 3, d(), paramBoolean);
/*     */       case 0:
/* 250 */         return a(paramWorldGenNetherPiece15, paramList, paramRandom, this.f.d + 1, this.f.b + paramInt1, this.f.c + paramInt2, 3, d(), paramBoolean);
/*     */       case 1:
/* 252 */         return a(paramWorldGenNetherPiece15, paramList, paramRandom, this.f.a + paramInt2, this.f.b + paramInt1, this.f.f + 1, 0, d(), paramBoolean);
/*     */       case 3:
/* 254 */         return a(paramWorldGenNetherPiece15, paramList, paramRandom, this.f.a + paramInt2, this.f.b + paramInt1, this.f.f + 1, 0, d(), paramBoolean);
/*     */     } 
/* 256 */     return null;
/*     */   }
/*     */   
/*     */   protected static boolean a(StructureBoundingBox paramStructureBoundingBox) {
/* 260 */     return (paramStructureBoundingBox != null && paramStructureBoundingBox.b > 10);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenNetherPiece.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
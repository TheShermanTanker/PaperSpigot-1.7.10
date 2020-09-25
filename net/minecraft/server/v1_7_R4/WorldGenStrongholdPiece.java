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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class WorldGenStrongholdPiece
/*     */   extends StructurePiece
/*     */ {
/* 213 */   protected WorldGenStrongholdDoorType d = WorldGenStrongholdDoorType.a;
/*     */ 
/*     */   
/*     */   public WorldGenStrongholdPiece() {}
/*     */ 
/*     */   
/*     */   protected WorldGenStrongholdPiece(int paramInt) {
/* 220 */     super(paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(NBTTagCompound paramNBTTagCompound) {
/* 229 */     paramNBTTagCompound.setString("EntryDoor", this.d.name());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void b(NBTTagCompound paramNBTTagCompound) {
/* 234 */     this.d = WorldGenStrongholdDoorType.valueOf(paramNBTTagCompound.getString("EntryDoor"));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, WorldGenStrongholdDoorType paramWorldGenStrongholdDoorType, int paramInt1, int paramInt2, int paramInt3) {
/* 239 */     switch (WorldGenStrongholdPieceWeight3.a[paramWorldGenStrongholdDoorType.ordinal()]) {
/*     */       
/*     */       default:
/* 242 */         a(paramWorld, paramStructureBoundingBox, paramInt1, paramInt2, paramInt3, paramInt1 + 3 - 1, paramInt2 + 3 - 1, paramInt3, Blocks.AIR, Blocks.AIR, false);
/*     */         return;
/*     */       case 2:
/* 245 */         a(paramWorld, Blocks.SMOOTH_BRICK, 0, paramInt1, paramInt2, paramInt3, paramStructureBoundingBox);
/* 246 */         a(paramWorld, Blocks.SMOOTH_BRICK, 0, paramInt1, paramInt2 + 1, paramInt3, paramStructureBoundingBox);
/* 247 */         a(paramWorld, Blocks.SMOOTH_BRICK, 0, paramInt1, paramInt2 + 2, paramInt3, paramStructureBoundingBox);
/* 248 */         a(paramWorld, Blocks.SMOOTH_BRICK, 0, paramInt1 + 1, paramInt2 + 2, paramInt3, paramStructureBoundingBox);
/* 249 */         a(paramWorld, Blocks.SMOOTH_BRICK, 0, paramInt1 + 2, paramInt2 + 2, paramInt3, paramStructureBoundingBox);
/* 250 */         a(paramWorld, Blocks.SMOOTH_BRICK, 0, paramInt1 + 2, paramInt2 + 1, paramInt3, paramStructureBoundingBox);
/* 251 */         a(paramWorld, Blocks.SMOOTH_BRICK, 0, paramInt1 + 2, paramInt2, paramInt3, paramStructureBoundingBox);
/* 252 */         a(paramWorld, Blocks.WOODEN_DOOR, 0, paramInt1 + 1, paramInt2, paramInt3, paramStructureBoundingBox);
/* 253 */         a(paramWorld, Blocks.WOODEN_DOOR, 8, paramInt1 + 1, paramInt2 + 1, paramInt3, paramStructureBoundingBox);
/*     */         return;
/*     */       case 3:
/* 256 */         a(paramWorld, Blocks.AIR, 0, paramInt1 + 1, paramInt2, paramInt3, paramStructureBoundingBox);
/* 257 */         a(paramWorld, Blocks.AIR, 0, paramInt1 + 1, paramInt2 + 1, paramInt3, paramStructureBoundingBox);
/* 258 */         a(paramWorld, Blocks.IRON_FENCE, 0, paramInt1, paramInt2, paramInt3, paramStructureBoundingBox);
/* 259 */         a(paramWorld, Blocks.IRON_FENCE, 0, paramInt1, paramInt2 + 1, paramInt3, paramStructureBoundingBox);
/* 260 */         a(paramWorld, Blocks.IRON_FENCE, 0, paramInt1, paramInt2 + 2, paramInt3, paramStructureBoundingBox);
/* 261 */         a(paramWorld, Blocks.IRON_FENCE, 0, paramInt1 + 1, paramInt2 + 2, paramInt3, paramStructureBoundingBox);
/* 262 */         a(paramWorld, Blocks.IRON_FENCE, 0, paramInt1 + 2, paramInt2 + 2, paramInt3, paramStructureBoundingBox);
/* 263 */         a(paramWorld, Blocks.IRON_FENCE, 0, paramInt1 + 2, paramInt2 + 1, paramInt3, paramStructureBoundingBox);
/* 264 */         a(paramWorld, Blocks.IRON_FENCE, 0, paramInt1 + 2, paramInt2, paramInt3, paramStructureBoundingBox); return;
/*     */       case 4:
/*     */         break;
/* 267 */     }  a(paramWorld, Blocks.SMOOTH_BRICK, 0, paramInt1, paramInt2, paramInt3, paramStructureBoundingBox);
/* 268 */     a(paramWorld, Blocks.SMOOTH_BRICK, 0, paramInt1, paramInt2 + 1, paramInt3, paramStructureBoundingBox);
/* 269 */     a(paramWorld, Blocks.SMOOTH_BRICK, 0, paramInt1, paramInt2 + 2, paramInt3, paramStructureBoundingBox);
/* 270 */     a(paramWorld, Blocks.SMOOTH_BRICK, 0, paramInt1 + 1, paramInt2 + 2, paramInt3, paramStructureBoundingBox);
/* 271 */     a(paramWorld, Blocks.SMOOTH_BRICK, 0, paramInt1 + 2, paramInt2 + 2, paramInt3, paramStructureBoundingBox);
/* 272 */     a(paramWorld, Blocks.SMOOTH_BRICK, 0, paramInt1 + 2, paramInt2 + 1, paramInt3, paramStructureBoundingBox);
/* 273 */     a(paramWorld, Blocks.SMOOTH_BRICK, 0, paramInt1 + 2, paramInt2, paramInt3, paramStructureBoundingBox);
/* 274 */     a(paramWorld, Blocks.IRON_DOOR_BLOCK, 0, paramInt1 + 1, paramInt2, paramInt3, paramStructureBoundingBox);
/* 275 */     a(paramWorld, Blocks.IRON_DOOR_BLOCK, 8, paramInt1 + 1, paramInt2 + 1, paramInt3, paramStructureBoundingBox);
/* 276 */     a(paramWorld, Blocks.STONE_BUTTON, a(Blocks.STONE_BUTTON, 4), paramInt1 + 2, paramInt2 + 1, paramInt3 + 1, paramStructureBoundingBox);
/* 277 */     a(paramWorld, Blocks.STONE_BUTTON, a(Blocks.STONE_BUTTON, 3), paramInt1 + 2, paramInt2 + 1, paramInt3 - 1, paramStructureBoundingBox);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected WorldGenStrongholdDoorType a(Random paramRandom) {
/* 283 */     int i = paramRandom.nextInt(5);
/* 284 */     switch (i)
/*     */     
/*     */     { 
/*     */       default:
/* 288 */         return WorldGenStrongholdDoorType.a;
/*     */       case 2:
/* 290 */         return WorldGenStrongholdDoorType.b;
/*     */       case 3:
/* 292 */         return WorldGenStrongholdDoorType.c;
/*     */       case 4:
/* 294 */         break; }  return WorldGenStrongholdDoorType.d;
/*     */   }
/*     */ 
/*     */   
/*     */   protected StructurePiece a(WorldGenStrongholdStart paramWorldGenStrongholdStart, List paramList, Random paramRandom, int paramInt1, int paramInt2) {
/* 299 */     switch (this.g) {
/*     */       case 2:
/* 301 */         return WorldGenStrongholdPieces.a(paramWorldGenStrongholdStart, paramList, paramRandom, this.f.a + paramInt1, this.f.b + paramInt2, this.f.c - 1, this.g, d());
/*     */       case 0:
/* 303 */         return WorldGenStrongholdPieces.a(paramWorldGenStrongholdStart, paramList, paramRandom, this.f.a + paramInt1, this.f.b + paramInt2, this.f.f + 1, this.g, d());
/*     */       case 1:
/* 305 */         return WorldGenStrongholdPieces.a(paramWorldGenStrongholdStart, paramList, paramRandom, this.f.a - 1, this.f.b + paramInt2, this.f.c + paramInt1, this.g, d());
/*     */       case 3:
/* 307 */         return WorldGenStrongholdPieces.a(paramWorldGenStrongholdStart, paramList, paramRandom, this.f.d + 1, this.f.b + paramInt2, this.f.c + paramInt1, this.g, d());
/*     */     } 
/* 309 */     return null;
/*     */   }
/*     */   
/*     */   protected StructurePiece b(WorldGenStrongholdStart paramWorldGenStrongholdStart, List paramList, Random paramRandom, int paramInt1, int paramInt2) {
/* 313 */     switch (this.g) {
/*     */       case 2:
/* 315 */         return WorldGenStrongholdPieces.a(paramWorldGenStrongholdStart, paramList, paramRandom, this.f.a - 1, this.f.b + paramInt1, this.f.c + paramInt2, 1, d());
/*     */       case 0:
/* 317 */         return WorldGenStrongholdPieces.a(paramWorldGenStrongholdStart, paramList, paramRandom, this.f.a - 1, this.f.b + paramInt1, this.f.c + paramInt2, 1, d());
/*     */       case 1:
/* 319 */         return WorldGenStrongholdPieces.a(paramWorldGenStrongholdStart, paramList, paramRandom, this.f.a + paramInt2, this.f.b + paramInt1, this.f.c - 1, 2, d());
/*     */       case 3:
/* 321 */         return WorldGenStrongholdPieces.a(paramWorldGenStrongholdStart, paramList, paramRandom, this.f.a + paramInt2, this.f.b + paramInt1, this.f.c - 1, 2, d());
/*     */     } 
/* 323 */     return null;
/*     */   }
/*     */   
/*     */   protected StructurePiece c(WorldGenStrongholdStart paramWorldGenStrongholdStart, List paramList, Random paramRandom, int paramInt1, int paramInt2) {
/* 327 */     switch (this.g) {
/*     */       case 2:
/* 329 */         return WorldGenStrongholdPieces.a(paramWorldGenStrongholdStart, paramList, paramRandom, this.f.d + 1, this.f.b + paramInt1, this.f.c + paramInt2, 3, d());
/*     */       case 0:
/* 331 */         return WorldGenStrongholdPieces.a(paramWorldGenStrongholdStart, paramList, paramRandom, this.f.d + 1, this.f.b + paramInt1, this.f.c + paramInt2, 3, d());
/*     */       case 1:
/* 333 */         return WorldGenStrongholdPieces.a(paramWorldGenStrongholdStart, paramList, paramRandom, this.f.a + paramInt2, this.f.b + paramInt1, this.f.f + 1, 0, d());
/*     */       case 3:
/* 335 */         return WorldGenStrongholdPieces.a(paramWorldGenStrongholdStart, paramList, paramRandom, this.f.a + paramInt2, this.f.b + paramInt1, this.f.f + 1, 0, d());
/*     */     } 
/* 337 */     return null;
/*     */   }
/*     */   
/*     */   protected static boolean a(StructureBoundingBox paramStructureBoundingBox) {
/* 341 */     return (paramStructureBoundingBox != null && paramStructureBoundingBox.b > 10);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenStrongholdPiece.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
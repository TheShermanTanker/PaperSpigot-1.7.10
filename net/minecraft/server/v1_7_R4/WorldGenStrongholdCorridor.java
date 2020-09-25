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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldGenStrongholdCorridor
/*     */   extends WorldGenStrongholdPiece
/*     */ {
/*     */   private int a;
/*     */   
/*     */   public WorldGenStrongholdCorridor() {}
/*     */   
/*     */   public WorldGenStrongholdCorridor(int paramInt1, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, int paramInt2) {
/* 358 */     super(paramInt1);
/*     */     
/* 360 */     this.g = paramInt2;
/* 361 */     this.f = paramStructureBoundingBox;
/* 362 */     this.a = (paramInt2 == 2 || paramInt2 == 0) ? paramStructureBoundingBox.d() : paramStructureBoundingBox.b();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void a(NBTTagCompound paramNBTTagCompound) {
/* 367 */     super.a(paramNBTTagCompound);
/* 368 */     paramNBTTagCompound.setInt("Steps", this.a);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void b(NBTTagCompound paramNBTTagCompound) {
/* 373 */     super.b(paramNBTTagCompound);
/* 374 */     this.a = paramNBTTagCompound.getInt("Steps");
/*     */   }
/*     */ 
/*     */   
/*     */   public static StructureBoundingBox a(List paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 379 */     byte b = 3;
/*     */     
/* 381 */     StructureBoundingBox structureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, -1, -1, 0, 5, 5, 4, paramInt4);
/*     */     
/* 383 */     StructurePiece structurePiece = StructurePiece.a(paramList, structureBoundingBox);
/* 384 */     if (structurePiece == null)
/*     */     {
/* 386 */       return null;
/*     */     }
/*     */     
/* 389 */     if ((structurePiece.c()).b == structureBoundingBox.b)
/*     */     {
/* 391 */       for (byte b1 = 3; b1 >= 1; b1--) {
/* 392 */         structureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, -1, -1, 0, 5, 5, b1 - 1, paramInt4);
/* 393 */         if (!structurePiece.c().a(structureBoundingBox))
/*     */         {
/*     */           
/* 396 */           return StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, -1, -1, 0, 5, 5, b1, paramInt4);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 401 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox) {
/* 406 */     if (a(paramWorld, paramStructureBoundingBox)) {
/* 407 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 411 */     for (byte b = 0; b < this.a; b++) {
/*     */       
/* 413 */       a(paramWorld, Blocks.SMOOTH_BRICK, 0, 0, 0, b, paramStructureBoundingBox);
/* 414 */       a(paramWorld, Blocks.SMOOTH_BRICK, 0, 1, 0, b, paramStructureBoundingBox);
/* 415 */       a(paramWorld, Blocks.SMOOTH_BRICK, 0, 2, 0, b, paramStructureBoundingBox);
/* 416 */       a(paramWorld, Blocks.SMOOTH_BRICK, 0, 3, 0, b, paramStructureBoundingBox);
/* 417 */       a(paramWorld, Blocks.SMOOTH_BRICK, 0, 4, 0, b, paramStructureBoundingBox);
/*     */       
/* 419 */       for (byte b1 = 1; b1 <= 3; b1++) {
/* 420 */         a(paramWorld, Blocks.SMOOTH_BRICK, 0, 0, b1, b, paramStructureBoundingBox);
/* 421 */         a(paramWorld, Blocks.AIR, 0, 1, b1, b, paramStructureBoundingBox);
/* 422 */         a(paramWorld, Blocks.AIR, 0, 2, b1, b, paramStructureBoundingBox);
/* 423 */         a(paramWorld, Blocks.AIR, 0, 3, b1, b, paramStructureBoundingBox);
/* 424 */         a(paramWorld, Blocks.SMOOTH_BRICK, 0, 4, b1, b, paramStructureBoundingBox);
/*     */       } 
/*     */       
/* 427 */       a(paramWorld, Blocks.SMOOTH_BRICK, 0, 0, 4, b, paramStructureBoundingBox);
/* 428 */       a(paramWorld, Blocks.SMOOTH_BRICK, 0, 1, 4, b, paramStructureBoundingBox);
/* 429 */       a(paramWorld, Blocks.SMOOTH_BRICK, 0, 2, 4, b, paramStructureBoundingBox);
/* 430 */       a(paramWorld, Blocks.SMOOTH_BRICK, 0, 3, 4, b, paramStructureBoundingBox);
/* 431 */       a(paramWorld, Blocks.SMOOTH_BRICK, 0, 4, 4, b, paramStructureBoundingBox);
/*     */     } 
/*     */     
/* 434 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenStrongholdCorridor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
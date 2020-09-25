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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldGenVillageRoad
/*     */   extends WorldGenVillageRoadPiece
/*     */ {
/*     */   private int a;
/*     */   
/*     */   public WorldGenVillageRoad() {}
/*     */   
/*     */   public WorldGenVillageRoad(WorldGenVillageStartPiece paramWorldGenVillageStartPiece, int paramInt1, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, int paramInt2) {
/* 541 */     super(paramWorldGenVillageStartPiece, paramInt1);
/*     */     
/* 543 */     this.g = paramInt2;
/* 544 */     this.f = paramStructureBoundingBox;
/* 545 */     this.a = Math.max(paramStructureBoundingBox.b(), paramStructureBoundingBox.d());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void a(NBTTagCompound paramNBTTagCompound) {
/* 550 */     super.a(paramNBTTagCompound);
/* 551 */     paramNBTTagCompound.setInt("Length", this.a);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void b(NBTTagCompound paramNBTTagCompound) {
/* 556 */     super.b(paramNBTTagCompound);
/* 557 */     this.a = paramNBTTagCompound.getInt("Length");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(StructurePiece paramStructurePiece, List paramList, Random paramRandom) {
/* 563 */     boolean bool = false;
/*     */ 
/*     */     
/* 566 */     int i = paramRandom.nextInt(5);
/* 567 */     while (i < this.a - 8) {
/* 568 */       StructurePiece structurePiece = a((WorldGenVillageStartPiece)paramStructurePiece, paramList, paramRandom, 0, i);
/* 569 */       if (structurePiece != null) {
/* 570 */         i += Math.max(structurePiece.f.b(), structurePiece.f.d());
/* 571 */         bool = true;
/*     */       } 
/* 573 */       i += 2 + paramRandom.nextInt(5);
/*     */     } 
/*     */ 
/*     */     
/* 577 */     i = paramRandom.nextInt(5);
/* 578 */     while (i < this.a - 8) {
/* 579 */       StructurePiece structurePiece = b((WorldGenVillageStartPiece)paramStructurePiece, paramList, paramRandom, 0, i);
/* 580 */       if (structurePiece != null) {
/* 581 */         i += Math.max(structurePiece.f.b(), structurePiece.f.d());
/* 582 */         bool = true;
/*     */       } 
/* 584 */       i += 2 + paramRandom.nextInt(5);
/*     */     } 
/*     */     
/* 587 */     if (bool && paramRandom.nextInt(3) > 0) {
/* 588 */       switch (this.g) {
/*     */         case 2:
/* 590 */           WorldGenVillagePieces.b((WorldGenVillageStartPiece)paramStructurePiece, paramList, paramRandom, this.f.a - 1, this.f.b, this.f.c, 1, d());
/*     */           break;
/*     */         case 0:
/* 593 */           WorldGenVillagePieces.b((WorldGenVillageStartPiece)paramStructurePiece, paramList, paramRandom, this.f.a - 1, this.f.b, this.f.f - 2, 1, d());
/*     */           break;
/*     */         case 3:
/* 596 */           WorldGenVillagePieces.b((WorldGenVillageStartPiece)paramStructurePiece, paramList, paramRandom, this.f.d - 2, this.f.b, this.f.c - 1, 2, d());
/*     */           break;
/*     */         case 1:
/* 599 */           WorldGenVillagePieces.b((WorldGenVillageStartPiece)paramStructurePiece, paramList, paramRandom, this.f.a, this.f.b, this.f.c - 1, 2, d());
/*     */           break;
/*     */       } 
/*     */     }
/* 603 */     if (bool && paramRandom.nextInt(3) > 0) {
/* 604 */       switch (this.g) {
/*     */         case 2:
/* 606 */           WorldGenVillagePieces.b((WorldGenVillageStartPiece)paramStructurePiece, paramList, paramRandom, this.f.d + 1, this.f.b, this.f.c, 3, d());
/*     */           break;
/*     */         case 0:
/* 609 */           WorldGenVillagePieces.b((WorldGenVillageStartPiece)paramStructurePiece, paramList, paramRandom, this.f.d + 1, this.f.b, this.f.f - 2, 3, d());
/*     */           break;
/*     */         case 3:
/* 612 */           WorldGenVillagePieces.b((WorldGenVillageStartPiece)paramStructurePiece, paramList, paramRandom, this.f.d - 2, this.f.b, this.f.f + 1, 0, d());
/*     */           break;
/*     */         case 1:
/* 615 */           WorldGenVillagePieces.b((WorldGenVillageStartPiece)paramStructurePiece, paramList, paramRandom, this.f.a, this.f.b, this.f.f + 1, 0, d());
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static StructureBoundingBox a(WorldGenVillageStartPiece paramWorldGenVillageStartPiece, List paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 624 */     int i = 7 * MathHelper.nextInt(paramRandom, 3, 5);
/*     */     
/* 626 */     while (i >= 7) {
/* 627 */       StructureBoundingBox structureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, 0, 0, 0, 3, 3, i, paramInt4);
/*     */       
/* 629 */       if (StructurePiece.a(paramList, structureBoundingBox) == null) {
/* 630 */         return structureBoundingBox;
/*     */       }
/* 632 */       i -= 7;
/*     */     } 
/*     */     
/* 635 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox) {
/* 640 */     Block block = b(Blocks.GRAVEL, 0);
/* 641 */     for (int i = this.f.a; i <= this.f.d; i++) {
/* 642 */       for (int j = this.f.c; j <= this.f.f; j++) {
/* 643 */         if (paramStructureBoundingBox.b(i, 64, j)) {
/* 644 */           int k = paramWorld.i(i, j) - 1;
/* 645 */           paramWorld.setTypeAndData(i, k, j, block, 0, 2);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 650 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenVillageRoad.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
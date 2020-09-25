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
/*     */ public class WorldGenMineshaftCross
/*     */   extends StructurePiece
/*     */ {
/*     */   private int a;
/*     */   private boolean b;
/*     */   
/*     */   public WorldGenMineshaftCross() {}
/*     */   
/*     */   protected void a(NBTTagCompound paramNBTTagCompound) {
/* 470 */     paramNBTTagCompound.setBoolean("tf", this.b);
/* 471 */     paramNBTTagCompound.setInt("D", this.a);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void b(NBTTagCompound paramNBTTagCompound) {
/* 476 */     this.b = paramNBTTagCompound.getBoolean("tf");
/* 477 */     this.a = paramNBTTagCompound.getInt("D");
/*     */   }
/*     */   
/*     */   public WorldGenMineshaftCross(int paramInt1, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, int paramInt2) {
/* 481 */     super(paramInt1);
/* 482 */     this.a = paramInt2;
/* 483 */     this.f = paramStructureBoundingBox;
/* 484 */     this.b = (paramStructureBoundingBox.c() > 3);
/*     */   }
/*     */ 
/*     */   
/*     */   public static StructureBoundingBox a(List paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 489 */     StructureBoundingBox structureBoundingBox = new StructureBoundingBox(paramInt1, paramInt2, paramInt3, paramInt1, paramInt2 + 2, paramInt3);
/*     */     
/* 491 */     if (paramRandom.nextInt(4) == 0) {
/* 492 */       structureBoundingBox.e += 4;
/*     */     }
/*     */     
/* 495 */     switch (paramInt4) {
/*     */       case 2:
/* 497 */         structureBoundingBox.a = paramInt1 - 1;
/* 498 */         structureBoundingBox.d = paramInt1 + 3;
/* 499 */         structureBoundingBox.c = paramInt3 - 4;
/*     */         break;
/*     */       case 0:
/* 502 */         structureBoundingBox.a = paramInt1 - 1;
/* 503 */         structureBoundingBox.d = paramInt1 + 3;
/* 504 */         structureBoundingBox.f = paramInt3 + 4;
/*     */         break;
/*     */       case 1:
/* 507 */         structureBoundingBox.a = paramInt1 - 4;
/* 508 */         structureBoundingBox.c = paramInt3 - 1;
/* 509 */         structureBoundingBox.f = paramInt3 + 3;
/*     */         break;
/*     */       case 3:
/* 512 */         structureBoundingBox.d = paramInt1 + 4;
/* 513 */         structureBoundingBox.c = paramInt3 - 1;
/* 514 */         structureBoundingBox.f = paramInt3 + 3;
/*     */         break;
/*     */     } 
/*     */     
/* 518 */     if (StructurePiece.a(paramList, structureBoundingBox) != null) {
/* 519 */       return null;
/*     */     }
/*     */     
/* 522 */     return structureBoundingBox;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(StructurePiece paramStructurePiece, List paramList, Random paramRandom) {
/* 528 */     int i = d();
/*     */ 
/*     */     
/* 531 */     switch (this.a) {
/*     */       case 2:
/* 533 */         WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.a + 1, this.f.b, this.f.c - 1, 2, i);
/* 534 */         WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.a - 1, this.f.b, this.f.c + 1, 1, i);
/* 535 */         WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.d + 1, this.f.b, this.f.c + 1, 3, i);
/*     */         break;
/*     */       case 0:
/* 538 */         WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.a + 1, this.f.b, this.f.f + 1, 0, i);
/* 539 */         WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.a - 1, this.f.b, this.f.c + 1, 1, i);
/* 540 */         WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.d + 1, this.f.b, this.f.c + 1, 3, i);
/*     */         break;
/*     */       case 1:
/* 543 */         WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.a + 1, this.f.b, this.f.c - 1, 2, i);
/* 544 */         WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.a + 1, this.f.b, this.f.f + 1, 0, i);
/* 545 */         WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.a - 1, this.f.b, this.f.c + 1, 1, i);
/*     */         break;
/*     */       case 3:
/* 548 */         WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.a + 1, this.f.b, this.f.c - 1, 2, i);
/* 549 */         WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.a + 1, this.f.b, this.f.f + 1, 0, i);
/* 550 */         WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.d + 1, this.f.b, this.f.c + 1, 3, i);
/*     */         break;
/*     */     } 
/*     */     
/* 554 */     if (this.b) {
/* 555 */       if (paramRandom.nextBoolean()) WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.a + 1, this.f.b + 3 + 1, this.f.c - 1, 2, i); 
/* 556 */       if (paramRandom.nextBoolean()) WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.a - 1, this.f.b + 3 + 1, this.f.c + 1, 1, i); 
/* 557 */       if (paramRandom.nextBoolean()) WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.d + 1, this.f.b + 3 + 1, this.f.c + 1, 3, i); 
/* 558 */       if (paramRandom.nextBoolean()) WorldGenMineshaftPieces.a(paramStructurePiece, paramList, paramRandom, this.f.a + 1, this.f.b + 3 + 1, this.f.f + 1, 0, i);
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox) {
/* 565 */     if (a(paramWorld, paramStructureBoundingBox)) {
/* 566 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 570 */     if (this.b) {
/* 571 */       a(paramWorld, paramStructureBoundingBox, this.f.a + 1, this.f.b, this.f.c, this.f.d - 1, this.f.b + 3 - 1, this.f.f, Blocks.AIR, Blocks.AIR, false);
/*     */       
/* 573 */       a(paramWorld, paramStructureBoundingBox, this.f.a, this.f.b, this.f.c + 1, this.f.d, this.f.b + 3 - 1, this.f.f - 1, Blocks.AIR, Blocks.AIR, false);
/*     */       
/* 575 */       a(paramWorld, paramStructureBoundingBox, this.f.a + 1, this.f.e - 2, this.f.c, this.f.d - 1, this.f.e, this.f.f, Blocks.AIR, Blocks.AIR, false);
/*     */       
/* 577 */       a(paramWorld, paramStructureBoundingBox, this.f.a, this.f.e - 2, this.f.c + 1, this.f.d, this.f.e, this.f.f - 1, Blocks.AIR, Blocks.AIR, false);
/*     */       
/* 579 */       a(paramWorld, paramStructureBoundingBox, this.f.a + 1, this.f.b + 3, this.f.c + 1, this.f.d - 1, this.f.b + 3, this.f.f - 1, Blocks.AIR, Blocks.AIR, false);
/*     */     } else {
/*     */       
/* 582 */       a(paramWorld, paramStructureBoundingBox, this.f.a + 1, this.f.b, this.f.c, this.f.d - 1, this.f.e, this.f.f, Blocks.AIR, Blocks.AIR, false);
/* 583 */       a(paramWorld, paramStructureBoundingBox, this.f.a, this.f.b, this.f.c + 1, this.f.d, this.f.e, this.f.f - 1, Blocks.AIR, Blocks.AIR, false);
/*     */     } 
/*     */ 
/*     */     
/* 587 */     a(paramWorld, paramStructureBoundingBox, this.f.a + 1, this.f.b, this.f.c + 1, this.f.a + 1, this.f.e, this.f.c + 1, Blocks.WOOD, Blocks.AIR, false);
/* 588 */     a(paramWorld, paramStructureBoundingBox, this.f.a + 1, this.f.b, this.f.f - 1, this.f.a + 1, this.f.e, this.f.f - 1, Blocks.WOOD, Blocks.AIR, false);
/* 589 */     a(paramWorld, paramStructureBoundingBox, this.f.d - 1, this.f.b, this.f.c + 1, this.f.d - 1, this.f.e, this.f.c + 1, Blocks.WOOD, Blocks.AIR, false);
/* 590 */     a(paramWorld, paramStructureBoundingBox, this.f.d - 1, this.f.b, this.f.f - 1, this.f.d - 1, this.f.e, this.f.f - 1, Blocks.WOOD, Blocks.AIR, false);
/*     */ 
/*     */ 
/*     */     
/* 594 */     for (int i = this.f.a; i <= this.f.d; i++) {
/* 595 */       for (int j = this.f.c; j <= this.f.f; j++) {
/* 596 */         if (a(paramWorld, i, this.f.b - 1, j, paramStructureBoundingBox).getMaterial() == Material.AIR) {
/* 597 */           a(paramWorld, Blocks.WOOD, 0, i, this.f.b - 1, j, paramStructureBoundingBox);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 602 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenMineshaftCross.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
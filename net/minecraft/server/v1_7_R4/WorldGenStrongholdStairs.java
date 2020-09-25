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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldGenStrongholdStairs
/*     */   extends WorldGenStrongholdPiece
/*     */ {
/*     */   private boolean a;
/*     */   private boolean b;
/*     */   
/*     */   public WorldGenStrongholdStairs() {}
/*     */   
/*     */   public WorldGenStrongholdStairs(int paramInt1, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, int paramInt2) {
/* 587 */     super(paramInt1);
/*     */     
/* 589 */     this.g = paramInt2;
/* 590 */     this.d = a(paramRandom);
/* 591 */     this.f = paramStructureBoundingBox;
/*     */     
/* 593 */     this.a = (paramRandom.nextInt(2) == 0);
/* 594 */     this.b = (paramRandom.nextInt(2) == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void a(NBTTagCompound paramNBTTagCompound) {
/* 599 */     super.a(paramNBTTagCompound);
/* 600 */     paramNBTTagCompound.setBoolean("Left", this.a);
/* 601 */     paramNBTTagCompound.setBoolean("Right", this.b);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void b(NBTTagCompound paramNBTTagCompound) {
/* 606 */     super.b(paramNBTTagCompound);
/* 607 */     this.a = paramNBTTagCompound.getBoolean("Left");
/* 608 */     this.b = paramNBTTagCompound.getBoolean("Right");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(StructurePiece paramStructurePiece, List paramList, Random paramRandom) {
/* 614 */     a((WorldGenStrongholdStart)paramStructurePiece, paramList, paramRandom, 1, 1);
/* 615 */     if (this.a) b((WorldGenStrongholdStart)paramStructurePiece, paramList, paramRandom, 1, 2); 
/* 616 */     if (this.b) c((WorldGenStrongholdStart)paramStructurePiece, paramList, paramRandom, 1, 2);
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public static WorldGenStrongholdStairs a(List paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/* 622 */     StructureBoundingBox structureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, -1, -1, 0, 5, 5, 7, paramInt4);
/*     */     
/* 624 */     if (!a(structureBoundingBox) || StructurePiece.a(paramList, structureBoundingBox) != null) {
/* 625 */       return null;
/*     */     }
/*     */     
/* 628 */     return new WorldGenStrongholdStairs(paramInt5, paramRandom, structureBoundingBox, paramInt4);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox) {
/* 633 */     if (a(paramWorld, paramStructureBoundingBox)) {
/* 634 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 638 */     a(paramWorld, paramStructureBoundingBox, 0, 0, 0, 4, 4, 6, true, paramRandom, WorldGenStrongholdPieces.c());
/*     */     
/* 640 */     a(paramWorld, paramRandom, paramStructureBoundingBox, this.d, 1, 1, 0);
/*     */     
/* 642 */     a(paramWorld, paramRandom, paramStructureBoundingBox, WorldGenStrongholdDoorType.a, 1, 1, 6);
/*     */     
/* 644 */     a(paramWorld, paramStructureBoundingBox, paramRandom, 0.1F, 1, 2, 1, Blocks.TORCH, 0);
/* 645 */     a(paramWorld, paramStructureBoundingBox, paramRandom, 0.1F, 3, 2, 1, Blocks.TORCH, 0);
/* 646 */     a(paramWorld, paramStructureBoundingBox, paramRandom, 0.1F, 1, 2, 5, Blocks.TORCH, 0);
/* 647 */     a(paramWorld, paramStructureBoundingBox, paramRandom, 0.1F, 3, 2, 5, Blocks.TORCH, 0);
/*     */     
/* 649 */     if (this.a) {
/* 650 */       a(paramWorld, paramStructureBoundingBox, 0, 1, 2, 0, 3, 4, Blocks.AIR, Blocks.AIR, false);
/*     */     }
/* 652 */     if (this.b) {
/* 653 */       a(paramWorld, paramStructureBoundingBox, 4, 1, 2, 4, 3, 4, Blocks.AIR, Blocks.AIR, false);
/*     */     }
/*     */     
/* 656 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenStrongholdStairs.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
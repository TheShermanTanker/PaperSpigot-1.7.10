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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldGenVillageHouse
/*     */   extends WorldGenVillagePiece
/*     */ {
/*     */   private boolean a;
/*     */   
/*     */   public WorldGenVillageHouse() {}
/*     */   
/*     */   public WorldGenVillageHouse(WorldGenVillageStartPiece paramWorldGenVillageStartPiece, int paramInt1, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, int paramInt2) {
/* 667 */     super(paramWorldGenVillageStartPiece, paramInt1);
/*     */     
/* 669 */     this.g = paramInt2;
/* 670 */     this.f = paramStructureBoundingBox;
/* 671 */     this.a = paramRandom.nextBoolean();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void a(NBTTagCompound paramNBTTagCompound) {
/* 676 */     super.a(paramNBTTagCompound);
/* 677 */     paramNBTTagCompound.setBoolean("Terrace", this.a);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void b(NBTTagCompound paramNBTTagCompound) {
/* 682 */     super.b(paramNBTTagCompound);
/* 683 */     this.a = paramNBTTagCompound.getBoolean("Terrace");
/*     */   }
/*     */ 
/*     */   
/*     */   public static WorldGenVillageHouse a(WorldGenVillageStartPiece paramWorldGenVillageStartPiece, List paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/* 688 */     StructureBoundingBox structureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, 0, 0, 0, 5, 6, 5, paramInt4);
/*     */     
/* 690 */     if (StructurePiece.a(paramList, structureBoundingBox) != null) {
/* 691 */       return null;
/*     */     }
/*     */     
/* 694 */     return new WorldGenVillageHouse(paramWorldGenVillageStartPiece, paramInt5, paramRandom, structureBoundingBox, paramInt4);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox) {
/* 700 */     if (this.k < 0) {
/* 701 */       this.k = b(paramWorld, paramStructureBoundingBox);
/* 702 */       if (this.k < 0) {
/* 703 */         return true;
/*     */       }
/* 705 */       this.f.a(0, this.k - this.f.e + 6 - 1, 0);
/*     */     } 
/*     */ 
/*     */     
/* 709 */     a(paramWorld, paramStructureBoundingBox, 0, 0, 0, 4, 0, 4, Blocks.COBBLESTONE, Blocks.COBBLESTONE, false);
/*     */     
/* 711 */     a(paramWorld, paramStructureBoundingBox, 0, 4, 0, 4, 4, 4, Blocks.LOG, Blocks.LOG, false);
/* 712 */     a(paramWorld, paramStructureBoundingBox, 1, 4, 1, 3, 4, 3, Blocks.WOOD, Blocks.WOOD, false);
/*     */ 
/*     */     
/* 715 */     a(paramWorld, Blocks.COBBLESTONE, 0, 0, 1, 0, paramStructureBoundingBox);
/* 716 */     a(paramWorld, Blocks.COBBLESTONE, 0, 0, 2, 0, paramStructureBoundingBox);
/* 717 */     a(paramWorld, Blocks.COBBLESTONE, 0, 0, 3, 0, paramStructureBoundingBox);
/* 718 */     a(paramWorld, Blocks.COBBLESTONE, 0, 4, 1, 0, paramStructureBoundingBox);
/* 719 */     a(paramWorld, Blocks.COBBLESTONE, 0, 4, 2, 0, paramStructureBoundingBox);
/* 720 */     a(paramWorld, Blocks.COBBLESTONE, 0, 4, 3, 0, paramStructureBoundingBox);
/* 721 */     a(paramWorld, Blocks.COBBLESTONE, 0, 0, 1, 4, paramStructureBoundingBox);
/* 722 */     a(paramWorld, Blocks.COBBLESTONE, 0, 0, 2, 4, paramStructureBoundingBox);
/* 723 */     a(paramWorld, Blocks.COBBLESTONE, 0, 0, 3, 4, paramStructureBoundingBox);
/* 724 */     a(paramWorld, Blocks.COBBLESTONE, 0, 4, 1, 4, paramStructureBoundingBox);
/* 725 */     a(paramWorld, Blocks.COBBLESTONE, 0, 4, 2, 4, paramStructureBoundingBox);
/* 726 */     a(paramWorld, Blocks.COBBLESTONE, 0, 4, 3, 4, paramStructureBoundingBox);
/* 727 */     a(paramWorld, paramStructureBoundingBox, 0, 1, 1, 0, 3, 3, Blocks.WOOD, Blocks.WOOD, false);
/* 728 */     a(paramWorld, paramStructureBoundingBox, 4, 1, 1, 4, 3, 3, Blocks.WOOD, Blocks.WOOD, false);
/* 729 */     a(paramWorld, paramStructureBoundingBox, 1, 1, 4, 3, 3, 4, Blocks.WOOD, Blocks.WOOD, false);
/* 730 */     a(paramWorld, Blocks.THIN_GLASS, 0, 0, 2, 2, paramStructureBoundingBox);
/* 731 */     a(paramWorld, Blocks.THIN_GLASS, 0, 2, 2, 4, paramStructureBoundingBox);
/* 732 */     a(paramWorld, Blocks.THIN_GLASS, 0, 4, 2, 2, paramStructureBoundingBox);
/*     */ 
/*     */     
/* 735 */     a(paramWorld, Blocks.WOOD, 0, 1, 1, 0, paramStructureBoundingBox);
/* 736 */     a(paramWorld, Blocks.WOOD, 0, 1, 2, 0, paramStructureBoundingBox);
/* 737 */     a(paramWorld, Blocks.WOOD, 0, 1, 3, 0, paramStructureBoundingBox);
/* 738 */     a(paramWorld, Blocks.WOOD, 0, 2, 3, 0, paramStructureBoundingBox);
/* 739 */     a(paramWorld, Blocks.WOOD, 0, 3, 3, 0, paramStructureBoundingBox);
/* 740 */     a(paramWorld, Blocks.WOOD, 0, 3, 2, 0, paramStructureBoundingBox);
/* 741 */     a(paramWorld, Blocks.WOOD, 0, 3, 1, 0, paramStructureBoundingBox);
/* 742 */     if (a(paramWorld, 2, 0, -1, paramStructureBoundingBox).getMaterial() == Material.AIR && a(paramWorld, 2, -1, -1, paramStructureBoundingBox).getMaterial() != Material.AIR) {
/* 743 */       a(paramWorld, Blocks.COBBLESTONE_STAIRS, a(Blocks.COBBLESTONE_STAIRS, 3), 2, 0, -1, paramStructureBoundingBox);
/*     */     }
/*     */ 
/*     */     
/* 747 */     a(paramWorld, paramStructureBoundingBox, 1, 1, 1, 3, 3, 3, Blocks.AIR, Blocks.AIR, false);
/*     */ 
/*     */     
/* 750 */     if (this.a) {
/* 751 */       a(paramWorld, Blocks.FENCE, 0, 0, 5, 0, paramStructureBoundingBox);
/* 752 */       a(paramWorld, Blocks.FENCE, 0, 1, 5, 0, paramStructureBoundingBox);
/* 753 */       a(paramWorld, Blocks.FENCE, 0, 2, 5, 0, paramStructureBoundingBox);
/* 754 */       a(paramWorld, Blocks.FENCE, 0, 3, 5, 0, paramStructureBoundingBox);
/* 755 */       a(paramWorld, Blocks.FENCE, 0, 4, 5, 0, paramStructureBoundingBox);
/* 756 */       a(paramWorld, Blocks.FENCE, 0, 0, 5, 4, paramStructureBoundingBox);
/* 757 */       a(paramWorld, Blocks.FENCE, 0, 1, 5, 4, paramStructureBoundingBox);
/* 758 */       a(paramWorld, Blocks.FENCE, 0, 2, 5, 4, paramStructureBoundingBox);
/* 759 */       a(paramWorld, Blocks.FENCE, 0, 3, 5, 4, paramStructureBoundingBox);
/* 760 */       a(paramWorld, Blocks.FENCE, 0, 4, 5, 4, paramStructureBoundingBox);
/* 761 */       a(paramWorld, Blocks.FENCE, 0, 4, 5, 1, paramStructureBoundingBox);
/* 762 */       a(paramWorld, Blocks.FENCE, 0, 4, 5, 2, paramStructureBoundingBox);
/* 763 */       a(paramWorld, Blocks.FENCE, 0, 4, 5, 3, paramStructureBoundingBox);
/* 764 */       a(paramWorld, Blocks.FENCE, 0, 0, 5, 1, paramStructureBoundingBox);
/* 765 */       a(paramWorld, Blocks.FENCE, 0, 0, 5, 2, paramStructureBoundingBox);
/* 766 */       a(paramWorld, Blocks.FENCE, 0, 0, 5, 3, paramStructureBoundingBox);
/*     */     } 
/*     */ 
/*     */     
/* 770 */     if (this.a) {
/* 771 */       int i = a(Blocks.LADDER, 3);
/* 772 */       a(paramWorld, Blocks.LADDER, i, 3, 1, 3, paramStructureBoundingBox);
/* 773 */       a(paramWorld, Blocks.LADDER, i, 3, 2, 3, paramStructureBoundingBox);
/* 774 */       a(paramWorld, Blocks.LADDER, i, 3, 3, 3, paramStructureBoundingBox);
/* 775 */       a(paramWorld, Blocks.LADDER, i, 3, 4, 3, paramStructureBoundingBox);
/*     */     } 
/*     */ 
/*     */     
/* 779 */     a(paramWorld, Blocks.TORCH, 0, 2, 3, 1, paramStructureBoundingBox);
/*     */     
/* 781 */     for (byte b = 0; b < 5; b++) {
/* 782 */       for (byte b1 = 0; b1 < 5; b1++) {
/* 783 */         b(paramWorld, b1, 6, b, paramStructureBoundingBox);
/* 784 */         b(paramWorld, Blocks.COBBLESTONE, 0, b1, -1, b, paramStructureBoundingBox);
/*     */       } 
/*     */     } 
/*     */     
/* 788 */     a(paramWorld, paramStructureBoundingBox, 1, 1, 2, 1);
/*     */     
/* 790 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenVillageHouse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
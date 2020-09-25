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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldGenVillageTemple
/*     */   extends WorldGenVillagePiece
/*     */ {
/*     */   public WorldGenVillageTemple() {}
/*     */   
/*     */   public WorldGenVillageTemple(WorldGenVillageStartPiece paramWorldGenVillageStartPiece, int paramInt1, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, int paramInt2) {
/* 805 */     super(paramWorldGenVillageStartPiece, paramInt1);
/*     */     
/* 807 */     this.g = paramInt2;
/* 808 */     this.f = paramStructureBoundingBox;
/*     */   }
/*     */ 
/*     */   
/*     */   public static WorldGenVillageTemple a(WorldGenVillageStartPiece paramWorldGenVillageStartPiece, List paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/* 813 */     StructureBoundingBox structureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, 0, 0, 0, 5, 12, 9, paramInt4);
/*     */     
/* 815 */     if (!a(structureBoundingBox) || StructurePiece.a(paramList, structureBoundingBox) != null) {
/* 816 */       return null;
/*     */     }
/*     */     
/* 819 */     return new WorldGenVillageTemple(paramWorldGenVillageStartPiece, paramInt5, paramRandom, structureBoundingBox, paramInt4);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox) {
/* 825 */     if (this.k < 0) {
/* 826 */       this.k = b(paramWorld, paramStructureBoundingBox);
/* 827 */       if (this.k < 0) {
/* 828 */         return true;
/*     */       }
/* 830 */       this.f.a(0, this.k - this.f.e + 12 - 1, 0);
/*     */     } 
/*     */ 
/*     */     
/* 834 */     a(paramWorld, paramStructureBoundingBox, 1, 1, 1, 3, 3, 7, Blocks.AIR, Blocks.AIR, false);
/* 835 */     a(paramWorld, paramStructureBoundingBox, 1, 5, 1, 3, 9, 3, Blocks.AIR, Blocks.AIR, false);
/*     */ 
/*     */     
/* 838 */     a(paramWorld, paramStructureBoundingBox, 1, 0, 0, 3, 0, 8, Blocks.COBBLESTONE, Blocks.COBBLESTONE, false);
/*     */ 
/*     */     
/* 841 */     a(paramWorld, paramStructureBoundingBox, 1, 1, 0, 3, 10, 0, Blocks.COBBLESTONE, Blocks.COBBLESTONE, false);
/*     */     
/* 843 */     a(paramWorld, paramStructureBoundingBox, 0, 1, 1, 0, 10, 3, Blocks.COBBLESTONE, Blocks.COBBLESTONE, false);
/*     */     
/* 845 */     a(paramWorld, paramStructureBoundingBox, 4, 1, 1, 4, 10, 3, Blocks.COBBLESTONE, Blocks.COBBLESTONE, false);
/*     */     
/* 847 */     a(paramWorld, paramStructureBoundingBox, 0, 0, 4, 0, 4, 7, Blocks.COBBLESTONE, Blocks.COBBLESTONE, false);
/*     */     
/* 849 */     a(paramWorld, paramStructureBoundingBox, 4, 0, 4, 4, 4, 7, Blocks.COBBLESTONE, Blocks.COBBLESTONE, false);
/*     */     
/* 851 */     a(paramWorld, paramStructureBoundingBox, 1, 1, 8, 3, 4, 8, Blocks.COBBLESTONE, Blocks.COBBLESTONE, false);
/*     */     
/* 853 */     a(paramWorld, paramStructureBoundingBox, 1, 5, 4, 3, 10, 4, Blocks.COBBLESTONE, Blocks.COBBLESTONE, false);
/*     */ 
/*     */     
/* 856 */     a(paramWorld, paramStructureBoundingBox, 1, 5, 5, 3, 5, 7, Blocks.COBBLESTONE, Blocks.COBBLESTONE, false);
/*     */     
/* 858 */     a(paramWorld, paramStructureBoundingBox, 0, 9, 0, 4, 9, 4, Blocks.COBBLESTONE, Blocks.COBBLESTONE, false);
/*     */     
/* 860 */     a(paramWorld, paramStructureBoundingBox, 0, 4, 0, 4, 4, 4, Blocks.COBBLESTONE, Blocks.COBBLESTONE, false);
/* 861 */     a(paramWorld, Blocks.COBBLESTONE, 0, 0, 11, 2, paramStructureBoundingBox);
/* 862 */     a(paramWorld, Blocks.COBBLESTONE, 0, 4, 11, 2, paramStructureBoundingBox);
/* 863 */     a(paramWorld, Blocks.COBBLESTONE, 0, 2, 11, 0, paramStructureBoundingBox);
/* 864 */     a(paramWorld, Blocks.COBBLESTONE, 0, 2, 11, 4, paramStructureBoundingBox);
/*     */ 
/*     */     
/* 867 */     a(paramWorld, Blocks.COBBLESTONE, 0, 1, 1, 6, paramStructureBoundingBox);
/* 868 */     a(paramWorld, Blocks.COBBLESTONE, 0, 1, 1, 7, paramStructureBoundingBox);
/* 869 */     a(paramWorld, Blocks.COBBLESTONE, 0, 2, 1, 7, paramStructureBoundingBox);
/* 870 */     a(paramWorld, Blocks.COBBLESTONE, 0, 3, 1, 6, paramStructureBoundingBox);
/* 871 */     a(paramWorld, Blocks.COBBLESTONE, 0, 3, 1, 7, paramStructureBoundingBox);
/* 872 */     a(paramWorld, Blocks.COBBLESTONE_STAIRS, a(Blocks.COBBLESTONE_STAIRS, 3), 1, 1, 5, paramStructureBoundingBox);
/* 873 */     a(paramWorld, Blocks.COBBLESTONE_STAIRS, a(Blocks.COBBLESTONE_STAIRS, 3), 2, 1, 6, paramStructureBoundingBox);
/* 874 */     a(paramWorld, Blocks.COBBLESTONE_STAIRS, a(Blocks.COBBLESTONE_STAIRS, 3), 3, 1, 5, paramStructureBoundingBox);
/* 875 */     a(paramWorld, Blocks.COBBLESTONE_STAIRS, a(Blocks.COBBLESTONE_STAIRS, 1), 1, 2, 7, paramStructureBoundingBox);
/* 876 */     a(paramWorld, Blocks.COBBLESTONE_STAIRS, a(Blocks.COBBLESTONE_STAIRS, 0), 3, 2, 7, paramStructureBoundingBox);
/*     */ 
/*     */     
/* 879 */     a(paramWorld, Blocks.THIN_GLASS, 0, 0, 2, 2, paramStructureBoundingBox);
/* 880 */     a(paramWorld, Blocks.THIN_GLASS, 0, 0, 3, 2, paramStructureBoundingBox);
/* 881 */     a(paramWorld, Blocks.THIN_GLASS, 0, 4, 2, 2, paramStructureBoundingBox);
/* 882 */     a(paramWorld, Blocks.THIN_GLASS, 0, 4, 3, 2, paramStructureBoundingBox);
/* 883 */     a(paramWorld, Blocks.THIN_GLASS, 0, 0, 6, 2, paramStructureBoundingBox);
/* 884 */     a(paramWorld, Blocks.THIN_GLASS, 0, 0, 7, 2, paramStructureBoundingBox);
/* 885 */     a(paramWorld, Blocks.THIN_GLASS, 0, 4, 6, 2, paramStructureBoundingBox);
/* 886 */     a(paramWorld, Blocks.THIN_GLASS, 0, 4, 7, 2, paramStructureBoundingBox);
/* 887 */     a(paramWorld, Blocks.THIN_GLASS, 0, 2, 6, 0, paramStructureBoundingBox);
/* 888 */     a(paramWorld, Blocks.THIN_GLASS, 0, 2, 7, 0, paramStructureBoundingBox);
/* 889 */     a(paramWorld, Blocks.THIN_GLASS, 0, 2, 6, 4, paramStructureBoundingBox);
/* 890 */     a(paramWorld, Blocks.THIN_GLASS, 0, 2, 7, 4, paramStructureBoundingBox);
/* 891 */     a(paramWorld, Blocks.THIN_GLASS, 0, 0, 3, 6, paramStructureBoundingBox);
/* 892 */     a(paramWorld, Blocks.THIN_GLASS, 0, 4, 3, 6, paramStructureBoundingBox);
/* 893 */     a(paramWorld, Blocks.THIN_GLASS, 0, 2, 3, 8, paramStructureBoundingBox);
/*     */ 
/*     */     
/* 896 */     a(paramWorld, Blocks.TORCH, 0, 2, 4, 7, paramStructureBoundingBox);
/* 897 */     a(paramWorld, Blocks.TORCH, 0, 1, 4, 6, paramStructureBoundingBox);
/* 898 */     a(paramWorld, Blocks.TORCH, 0, 3, 4, 6, paramStructureBoundingBox);
/* 899 */     a(paramWorld, Blocks.TORCH, 0, 2, 4, 5, paramStructureBoundingBox);
/*     */ 
/*     */     
/* 902 */     int i = a(Blocks.LADDER, 4); byte b;
/* 903 */     for (b = 1; b <= 9; b++) {
/* 904 */       a(paramWorld, Blocks.LADDER, i, 3, b, 3, paramStructureBoundingBox);
/*     */     }
/*     */ 
/*     */     
/* 908 */     a(paramWorld, Blocks.AIR, 0, 2, 1, 0, paramStructureBoundingBox);
/* 909 */     a(paramWorld, Blocks.AIR, 0, 2, 2, 0, paramStructureBoundingBox);
/* 910 */     a(paramWorld, paramStructureBoundingBox, paramRandom, 2, 1, 0, a(Blocks.WOODEN_DOOR, 1));
/* 911 */     if (a(paramWorld, 2, 0, -1, paramStructureBoundingBox).getMaterial() == Material.AIR && a(paramWorld, 2, -1, -1, paramStructureBoundingBox).getMaterial() != Material.AIR) {
/* 912 */       a(paramWorld, Blocks.COBBLESTONE_STAIRS, a(Blocks.COBBLESTONE_STAIRS, 3), 2, 0, -1, paramStructureBoundingBox);
/*     */     }
/*     */     
/* 915 */     for (b = 0; b < 9; b++) {
/* 916 */       for (byte b1 = 0; b1 < 5; b1++) {
/* 917 */         b(paramWorld, b1, 12, b, paramStructureBoundingBox);
/* 918 */         b(paramWorld, Blocks.COBBLESTONE, 0, b1, -1, b, paramStructureBoundingBox);
/*     */       } 
/*     */     } 
/*     */     
/* 922 */     a(paramWorld, paramStructureBoundingBox, 2, 1, 2, 1);
/*     */     
/* 924 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int b(int paramInt) {
/* 929 */     return 2;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenVillageTemple.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
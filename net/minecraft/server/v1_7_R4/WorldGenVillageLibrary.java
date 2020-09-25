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
/*      */ public class WorldGenVillageLibrary
/*      */   extends WorldGenVillagePiece
/*      */ {
/*      */   public WorldGenVillageLibrary() {}
/*      */   
/*      */   public WorldGenVillageLibrary(WorldGenVillageStartPiece paramWorldGenVillageStartPiece, int paramInt1, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, int paramInt2) {
/*  944 */     super(paramWorldGenVillageStartPiece, paramInt1);
/*      */     
/*  946 */     this.g = paramInt2;
/*  947 */     this.f = paramStructureBoundingBox;
/*      */   }
/*      */ 
/*      */   
/*      */   public static WorldGenVillageLibrary a(WorldGenVillageStartPiece paramWorldGenVillageStartPiece, List paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/*  952 */     StructureBoundingBox structureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, 0, 0, 0, 9, 9, 6, paramInt4);
/*      */     
/*  954 */     if (!a(structureBoundingBox) || StructurePiece.a(paramList, structureBoundingBox) != null) {
/*  955 */       return null;
/*      */     }
/*      */     
/*  958 */     return new WorldGenVillageLibrary(paramWorldGenVillageStartPiece, paramInt5, paramRandom, structureBoundingBox, paramInt4);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox) {
/*  964 */     if (this.k < 0) {
/*  965 */       this.k = b(paramWorld, paramStructureBoundingBox);
/*  966 */       if (this.k < 0) {
/*  967 */         return true;
/*      */       }
/*  969 */       this.f.a(0, this.k - this.f.e + 9 - 1, 0);
/*      */     } 
/*      */ 
/*      */     
/*  973 */     a(paramWorld, paramStructureBoundingBox, 1, 1, 1, 7, 5, 4, Blocks.AIR, Blocks.AIR, false);
/*      */ 
/*      */     
/*  976 */     a(paramWorld, paramStructureBoundingBox, 0, 0, 0, 8, 0, 5, Blocks.COBBLESTONE, Blocks.COBBLESTONE, false);
/*      */     
/*  978 */     a(paramWorld, paramStructureBoundingBox, 0, 5, 0, 8, 5, 5, Blocks.COBBLESTONE, Blocks.COBBLESTONE, false);
/*  979 */     a(paramWorld, paramStructureBoundingBox, 0, 6, 1, 8, 6, 4, Blocks.COBBLESTONE, Blocks.COBBLESTONE, false);
/*  980 */     a(paramWorld, paramStructureBoundingBox, 0, 7, 2, 8, 7, 3, Blocks.COBBLESTONE, Blocks.COBBLESTONE, false);
/*  981 */     int i = a(Blocks.WOOD_STAIRS, 3);
/*  982 */     int j = a(Blocks.WOOD_STAIRS, 2); int k;
/*  983 */     for (k = -1; k <= 2; k++) {
/*  984 */       for (byte b1 = 0; b1 <= 8; b1++) {
/*  985 */         a(paramWorld, Blocks.WOOD_STAIRS, i, b1, 6 + k, k, paramStructureBoundingBox);
/*  986 */         a(paramWorld, Blocks.WOOD_STAIRS, j, b1, 6 + k, 5 - k, paramStructureBoundingBox);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  991 */     a(paramWorld, paramStructureBoundingBox, 0, 1, 0, 0, 1, 5, Blocks.COBBLESTONE, Blocks.COBBLESTONE, false);
/*  992 */     a(paramWorld, paramStructureBoundingBox, 1, 1, 5, 8, 1, 5, Blocks.COBBLESTONE, Blocks.COBBLESTONE, false);
/*  993 */     a(paramWorld, paramStructureBoundingBox, 8, 1, 0, 8, 1, 4, Blocks.COBBLESTONE, Blocks.COBBLESTONE, false);
/*  994 */     a(paramWorld, paramStructureBoundingBox, 2, 1, 0, 7, 1, 0, Blocks.COBBLESTONE, Blocks.COBBLESTONE, false);
/*  995 */     a(paramWorld, paramStructureBoundingBox, 0, 2, 0, 0, 4, 0, Blocks.COBBLESTONE, Blocks.COBBLESTONE, false);
/*  996 */     a(paramWorld, paramStructureBoundingBox, 0, 2, 5, 0, 4, 5, Blocks.COBBLESTONE, Blocks.COBBLESTONE, false);
/*  997 */     a(paramWorld, paramStructureBoundingBox, 8, 2, 5, 8, 4, 5, Blocks.COBBLESTONE, Blocks.COBBLESTONE, false);
/*  998 */     a(paramWorld, paramStructureBoundingBox, 8, 2, 0, 8, 4, 0, Blocks.COBBLESTONE, Blocks.COBBLESTONE, false);
/*      */ 
/*      */     
/* 1001 */     a(paramWorld, paramStructureBoundingBox, 0, 2, 1, 0, 4, 4, Blocks.WOOD, Blocks.WOOD, false);
/* 1002 */     a(paramWorld, paramStructureBoundingBox, 1, 2, 5, 7, 4, 5, Blocks.WOOD, Blocks.WOOD, false);
/* 1003 */     a(paramWorld, paramStructureBoundingBox, 8, 2, 1, 8, 4, 4, Blocks.WOOD, Blocks.WOOD, false);
/* 1004 */     a(paramWorld, paramStructureBoundingBox, 1, 2, 0, 7, 4, 0, Blocks.WOOD, Blocks.WOOD, false);
/*      */ 
/*      */     
/* 1007 */     a(paramWorld, Blocks.THIN_GLASS, 0, 4, 2, 0, paramStructureBoundingBox);
/* 1008 */     a(paramWorld, Blocks.THIN_GLASS, 0, 5, 2, 0, paramStructureBoundingBox);
/* 1009 */     a(paramWorld, Blocks.THIN_GLASS, 0, 6, 2, 0, paramStructureBoundingBox);
/* 1010 */     a(paramWorld, Blocks.THIN_GLASS, 0, 4, 3, 0, paramStructureBoundingBox);
/* 1011 */     a(paramWorld, Blocks.THIN_GLASS, 0, 5, 3, 0, paramStructureBoundingBox);
/* 1012 */     a(paramWorld, Blocks.THIN_GLASS, 0, 6, 3, 0, paramStructureBoundingBox);
/* 1013 */     a(paramWorld, Blocks.THIN_GLASS, 0, 0, 2, 2, paramStructureBoundingBox);
/* 1014 */     a(paramWorld, Blocks.THIN_GLASS, 0, 0, 2, 3, paramStructureBoundingBox);
/* 1015 */     a(paramWorld, Blocks.THIN_GLASS, 0, 0, 3, 2, paramStructureBoundingBox);
/* 1016 */     a(paramWorld, Blocks.THIN_GLASS, 0, 0, 3, 3, paramStructureBoundingBox);
/* 1017 */     a(paramWorld, Blocks.THIN_GLASS, 0, 8, 2, 2, paramStructureBoundingBox);
/* 1018 */     a(paramWorld, Blocks.THIN_GLASS, 0, 8, 2, 3, paramStructureBoundingBox);
/* 1019 */     a(paramWorld, Blocks.THIN_GLASS, 0, 8, 3, 2, paramStructureBoundingBox);
/* 1020 */     a(paramWorld, Blocks.THIN_GLASS, 0, 8, 3, 3, paramStructureBoundingBox);
/* 1021 */     a(paramWorld, Blocks.THIN_GLASS, 0, 2, 2, 5, paramStructureBoundingBox);
/* 1022 */     a(paramWorld, Blocks.THIN_GLASS, 0, 3, 2, 5, paramStructureBoundingBox);
/* 1023 */     a(paramWorld, Blocks.THIN_GLASS, 0, 5, 2, 5, paramStructureBoundingBox);
/* 1024 */     a(paramWorld, Blocks.THIN_GLASS, 0, 6, 2, 5, paramStructureBoundingBox);
/*      */ 
/*      */     
/* 1027 */     a(paramWorld, paramStructureBoundingBox, 1, 4, 1, 7, 4, 1, Blocks.WOOD, Blocks.WOOD, false);
/* 1028 */     a(paramWorld, paramStructureBoundingBox, 1, 4, 4, 7, 4, 4, Blocks.WOOD, Blocks.WOOD, false);
/* 1029 */     a(paramWorld, paramStructureBoundingBox, 1, 3, 4, 7, 3, 4, Blocks.BOOKSHELF, Blocks.BOOKSHELF, false);
/*      */ 
/*      */     
/* 1032 */     a(paramWorld, Blocks.WOOD, 0, 7, 1, 4, paramStructureBoundingBox);
/* 1033 */     a(paramWorld, Blocks.WOOD_STAIRS, a(Blocks.WOOD_STAIRS, 0), 7, 1, 3, paramStructureBoundingBox);
/* 1034 */     k = a(Blocks.WOOD_STAIRS, 3);
/* 1035 */     a(paramWorld, Blocks.WOOD_STAIRS, k, 6, 1, 4, paramStructureBoundingBox);
/* 1036 */     a(paramWorld, Blocks.WOOD_STAIRS, k, 5, 1, 4, paramStructureBoundingBox);
/* 1037 */     a(paramWorld, Blocks.WOOD_STAIRS, k, 4, 1, 4, paramStructureBoundingBox);
/* 1038 */     a(paramWorld, Blocks.WOOD_STAIRS, k, 3, 1, 4, paramStructureBoundingBox);
/*      */ 
/*      */     
/* 1041 */     a(paramWorld, Blocks.FENCE, 0, 6, 1, 3, paramStructureBoundingBox);
/* 1042 */     a(paramWorld, Blocks.WOOD_PLATE, 0, 6, 2, 3, paramStructureBoundingBox);
/* 1043 */     a(paramWorld, Blocks.FENCE, 0, 4, 1, 3, paramStructureBoundingBox);
/* 1044 */     a(paramWorld, Blocks.WOOD_PLATE, 0, 4, 2, 3, paramStructureBoundingBox);
/* 1045 */     a(paramWorld, Blocks.WORKBENCH, 0, 7, 1, 1, paramStructureBoundingBox);
/*      */ 
/*      */     
/* 1048 */     a(paramWorld, Blocks.AIR, 0, 1, 1, 0, paramStructureBoundingBox);
/* 1049 */     a(paramWorld, Blocks.AIR, 0, 1, 2, 0, paramStructureBoundingBox);
/* 1050 */     a(paramWorld, paramStructureBoundingBox, paramRandom, 1, 1, 0, a(Blocks.WOODEN_DOOR, 1));
/* 1051 */     if (a(paramWorld, 1, 0, -1, paramStructureBoundingBox).getMaterial() == Material.AIR && a(paramWorld, 1, -1, -1, paramStructureBoundingBox).getMaterial() != Material.AIR) {
/* 1052 */       a(paramWorld, Blocks.COBBLESTONE_STAIRS, a(Blocks.COBBLESTONE_STAIRS, 3), 1, 0, -1, paramStructureBoundingBox);
/*      */     }
/*      */     
/* 1055 */     for (byte b = 0; b < 6; b++) {
/* 1056 */       for (byte b1 = 0; b1 < 9; b1++) {
/* 1057 */         b(paramWorld, b1, 9, b, paramStructureBoundingBox);
/* 1058 */         b(paramWorld, Blocks.COBBLESTONE, 0, b1, -1, b, paramStructureBoundingBox);
/*      */       } 
/*      */     } 
/*      */     
/* 1062 */     a(paramWorld, paramStructureBoundingBox, 2, 1, 2, 1);
/*      */     
/* 1064 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   protected int b(int paramInt) {
/* 1069 */     return 1;
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenVillageLibrary.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
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
/*      */ public class WorldGenStrongholdRoomCrossing
/*      */   extends WorldGenStrongholdPiece
/*      */ {
/*  948 */   private static final StructurePieceTreasure[] b = new StructurePieceTreasure[] { new StructurePieceTreasure(Items.IRON_INGOT, 0, 1, 5, 10), new StructurePieceTreasure(Items.GOLD_INGOT, 0, 1, 3, 5), new StructurePieceTreasure(Items.REDSTONE, 0, 4, 9, 5), new StructurePieceTreasure(Items.COAL, 0, 3, 8, 10), new StructurePieceTreasure(Items.BREAD, 0, 1, 3, 15), new StructurePieceTreasure(Items.APPLE, 0, 1, 3, 15), new StructurePieceTreasure(Items.IRON_PICKAXE, 0, 1, 1, 1) };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int a;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public WorldGenStrongholdRoomCrossing() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public WorldGenStrongholdRoomCrossing(int paramInt1, Random paramRandom, StructureBoundingBox paramStructureBoundingBox, int paramInt2) {
/*  970 */     super(paramInt1);
/*      */     
/*  972 */     this.g = paramInt2;
/*  973 */     this.d = a(paramRandom);
/*  974 */     this.f = paramStructureBoundingBox;
/*  975 */     this.a = paramRandom.nextInt(5);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void a(NBTTagCompound paramNBTTagCompound) {
/*  980 */     super.a(paramNBTTagCompound);
/*  981 */     paramNBTTagCompound.setInt("Type", this.a);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void b(NBTTagCompound paramNBTTagCompound) {
/*  986 */     super.b(paramNBTTagCompound);
/*  987 */     this.a = paramNBTTagCompound.getInt("Type");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void a(StructurePiece paramStructurePiece, List paramList, Random paramRandom) {
/*  993 */     a((WorldGenStrongholdStart)paramStructurePiece, paramList, paramRandom, 4, 1);
/*  994 */     b((WorldGenStrongholdStart)paramStructurePiece, paramList, paramRandom, 1, 4);
/*  995 */     c((WorldGenStrongholdStart)paramStructurePiece, paramList, paramRandom, 1, 4);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static WorldGenStrongholdRoomCrossing a(List paramList, Random paramRandom, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/* 1001 */     StructureBoundingBox structureBoundingBox = StructureBoundingBox.a(paramInt1, paramInt2, paramInt3, -4, -1, 0, 11, 7, 11, paramInt4);
/*      */     
/* 1003 */     if (!a(structureBoundingBox) || StructurePiece.a(paramList, structureBoundingBox) != null) {
/* 1004 */       return null;
/*      */     }
/*      */     
/* 1007 */     return new WorldGenStrongholdRoomCrossing(paramInt5, paramRandom, structureBoundingBox, paramInt4);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean a(World paramWorld, Random paramRandom, StructureBoundingBox paramStructureBoundingBox) {
/* 1012 */     if (a(paramWorld, paramStructureBoundingBox)) {
/* 1013 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1017 */     a(paramWorld, paramStructureBoundingBox, 0, 0, 0, 10, 6, 10, true, paramRandom, WorldGenStrongholdPieces.c());
/*      */     
/* 1019 */     a(paramWorld, paramRandom, paramStructureBoundingBox, this.d, 4, 1, 0);
/*      */     
/* 1021 */     a(paramWorld, paramStructureBoundingBox, 4, 1, 10, 6, 3, 10, Blocks.AIR, Blocks.AIR, false);
/* 1022 */     a(paramWorld, paramStructureBoundingBox, 0, 1, 4, 0, 3, 6, Blocks.AIR, Blocks.AIR, false);
/* 1023 */     a(paramWorld, paramStructureBoundingBox, 10, 1, 4, 10, 3, 6, Blocks.AIR, Blocks.AIR, false);
/*      */     
/* 1025 */     switch (this.a) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       default:
/* 1101 */         return true;
/*      */       case 0:
/*      */         a(paramWorld, Blocks.SMOOTH_BRICK, 0, 5, 1, 5, paramStructureBoundingBox);
/*      */         a(paramWorld, Blocks.SMOOTH_BRICK, 0, 5, 2, 5, paramStructureBoundingBox);
/*      */         a(paramWorld, Blocks.SMOOTH_BRICK, 0, 5, 3, 5, paramStructureBoundingBox);
/*      */         a(paramWorld, Blocks.TORCH, 0, 4, 3, 5, paramStructureBoundingBox);
/*      */         a(paramWorld, Blocks.TORCH, 0, 6, 3, 5, paramStructureBoundingBox);
/*      */         a(paramWorld, Blocks.TORCH, 0, 5, 3, 4, paramStructureBoundingBox);
/*      */         a(paramWorld, Blocks.TORCH, 0, 5, 3, 6, paramStructureBoundingBox);
/*      */         a(paramWorld, Blocks.STEP, 0, 4, 1, 4, paramStructureBoundingBox);
/*      */         a(paramWorld, Blocks.STEP, 0, 4, 1, 5, paramStructureBoundingBox);
/*      */         a(paramWorld, Blocks.STEP, 0, 4, 1, 6, paramStructureBoundingBox);
/*      */         a(paramWorld, Blocks.STEP, 0, 6, 1, 4, paramStructureBoundingBox);
/*      */         a(paramWorld, Blocks.STEP, 0, 6, 1, 5, paramStructureBoundingBox);
/*      */         a(paramWorld, Blocks.STEP, 0, 6, 1, 6, paramStructureBoundingBox);
/*      */         a(paramWorld, Blocks.STEP, 0, 5, 1, 4, paramStructureBoundingBox);
/*      */         a(paramWorld, Blocks.STEP, 0, 5, 1, 6, paramStructureBoundingBox);
/*      */       case 1:
/*      */         for (b = 0; b < 5; b++) {
/*      */           a(paramWorld, Blocks.SMOOTH_BRICK, 0, 3, 1, 3 + b, paramStructureBoundingBox);
/*      */           a(paramWorld, Blocks.SMOOTH_BRICK, 0, 7, 1, 3 + b, paramStructureBoundingBox);
/*      */           a(paramWorld, Blocks.SMOOTH_BRICK, 0, 3 + b, 1, 3, paramStructureBoundingBox);
/*      */           a(paramWorld, Blocks.SMOOTH_BRICK, 0, 3 + b, 1, 7, paramStructureBoundingBox);
/*      */         } 
/*      */         a(paramWorld, Blocks.SMOOTH_BRICK, 0, 5, 1, 5, paramStructureBoundingBox);
/*      */         a(paramWorld, Blocks.SMOOTH_BRICK, 0, 5, 2, 5, paramStructureBoundingBox);
/*      */         a(paramWorld, Blocks.SMOOTH_BRICK, 0, 5, 3, 5, paramStructureBoundingBox);
/*      */         a(paramWorld, Blocks.WATER, 0, 5, 4, 5, paramStructureBoundingBox);
/*      */       case 2:
/*      */         break;
/*      */     } 
/*      */     byte b;
/*      */     for (b = 1; b <= 9; b++) {
/*      */       a(paramWorld, Blocks.COBBLESTONE, 0, 1, 3, b, paramStructureBoundingBox);
/*      */       a(paramWorld, Blocks.COBBLESTONE, 0, 9, 3, b, paramStructureBoundingBox);
/*      */     } 
/*      */     for (b = 1; b <= 9; b++) {
/*      */       a(paramWorld, Blocks.COBBLESTONE, 0, b, 3, 1, paramStructureBoundingBox);
/*      */       a(paramWorld, Blocks.COBBLESTONE, 0, b, 3, 9, paramStructureBoundingBox);
/*      */     } 
/*      */     a(paramWorld, Blocks.COBBLESTONE, 0, 5, 1, 4, paramStructureBoundingBox);
/*      */     a(paramWorld, Blocks.COBBLESTONE, 0, 5, 1, 6, paramStructureBoundingBox);
/*      */     a(paramWorld, Blocks.COBBLESTONE, 0, 5, 3, 4, paramStructureBoundingBox);
/*      */     a(paramWorld, Blocks.COBBLESTONE, 0, 5, 3, 6, paramStructureBoundingBox);
/*      */     a(paramWorld, Blocks.COBBLESTONE, 0, 4, 1, 5, paramStructureBoundingBox);
/*      */     a(paramWorld, Blocks.COBBLESTONE, 0, 6, 1, 5, paramStructureBoundingBox);
/*      */     a(paramWorld, Blocks.COBBLESTONE, 0, 4, 3, 5, paramStructureBoundingBox);
/*      */     a(paramWorld, Blocks.COBBLESTONE, 0, 6, 3, 5, paramStructureBoundingBox);
/*      */     for (b = 1; b <= 3; b++) {
/*      */       a(paramWorld, Blocks.COBBLESTONE, 0, 4, b, 4, paramStructureBoundingBox);
/*      */       a(paramWorld, Blocks.COBBLESTONE, 0, 6, b, 4, paramStructureBoundingBox);
/*      */       a(paramWorld, Blocks.COBBLESTONE, 0, 4, b, 6, paramStructureBoundingBox);
/*      */       a(paramWorld, Blocks.COBBLESTONE, 0, 6, b, 6, paramStructureBoundingBox);
/*      */     } 
/*      */     a(paramWorld, Blocks.TORCH, 0, 5, 3, 5, paramStructureBoundingBox);
/*      */     for (b = 2; b <= 8; b++) {
/*      */       a(paramWorld, Blocks.WOOD, 0, 2, 3, b, paramStructureBoundingBox);
/*      */       a(paramWorld, Blocks.WOOD, 0, 3, 3, b, paramStructureBoundingBox);
/*      */       if (b <= 3 || b >= 7) {
/*      */         a(paramWorld, Blocks.WOOD, 0, 4, 3, b, paramStructureBoundingBox);
/*      */         a(paramWorld, Blocks.WOOD, 0, 5, 3, b, paramStructureBoundingBox);
/*      */         a(paramWorld, Blocks.WOOD, 0, 6, 3, b, paramStructureBoundingBox);
/*      */       } 
/*      */       a(paramWorld, Blocks.WOOD, 0, 7, 3, b, paramStructureBoundingBox);
/*      */       a(paramWorld, Blocks.WOOD, 0, 8, 3, b, paramStructureBoundingBox);
/*      */     } 
/*      */     a(paramWorld, Blocks.LADDER, a(Blocks.LADDER, 4), 9, 1, 3, paramStructureBoundingBox);
/*      */     a(paramWorld, Blocks.LADDER, a(Blocks.LADDER, 4), 9, 2, 3, paramStructureBoundingBox);
/*      */     a(paramWorld, Blocks.LADDER, a(Blocks.LADDER, 4), 9, 3, 3, paramStructureBoundingBox);
/*      */     a(paramWorld, paramStructureBoundingBox, paramRandom, 3, 4, 8, StructurePieceTreasure.a(b, new StructurePieceTreasure[] { Items.ENCHANTED_BOOK.b(paramRandom) }), 1 + paramRandom.nextInt(4));
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenStrongholdRoomCrossing.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
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
/*     */ public class WorldGenDungeons
/*     */   extends WorldGenerator
/*     */ {
/*  16 */   private static final StructurePieceTreasure[] a = new StructurePieceTreasure[] { new StructurePieceTreasure(Items.SADDLE, 0, 1, 1, 10), new StructurePieceTreasure(Items.IRON_INGOT, 0, 1, 4, 10), new StructurePieceTreasure(Items.BREAD, 0, 1, 1, 10), new StructurePieceTreasure(Items.WHEAT, 0, 1, 4, 10), new StructurePieceTreasure(Items.SULPHUR, 0, 1, 4, 10), new StructurePieceTreasure(Items.STRING, 0, 1, 4, 10), new StructurePieceTreasure(Items.BUCKET, 0, 1, 1, 10), new StructurePieceTreasure(Items.GOLDEN_APPLE, 0, 1, 1, 1), new StructurePieceTreasure(Items.REDSTONE, 0, 1, 4, 10), new StructurePieceTreasure(Items.RECORD_1, 0, 1, 1, 10), new StructurePieceTreasure(Items.RECORD_2, 0, 1, 1, 10), new StructurePieceTreasure(Items.NAME_TAG, 0, 1, 1, 10), new StructurePieceTreasure(Items.HORSE_ARMOR_GOLD, 0, 1, 1, 2), new StructurePieceTreasure(Items.HORSE_ARMOR_IRON, 0, 1, 1, 5), new StructurePieceTreasure(Items.HORSE_ARMOR_DIAMOND, 0, 1, 1, 1) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean generate(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3) {
/*  37 */     byte b1 = 3;
/*  38 */     int i = paramRandom.nextInt(2) + 2;
/*  39 */     int j = paramRandom.nextInt(2) + 2;
/*     */     
/*  41 */     byte b2 = 0; int k;
/*  42 */     for (k = paramInt1 - i - 1; k <= paramInt1 + i + 1; k++) {
/*  43 */       for (int m = paramInt2 - 1; m <= paramInt2 + b1 + 1; m++) {
/*  44 */         for (int n = paramInt3 - j - 1; n <= paramInt3 + j + 1; n++) {
/*  45 */           Material material = paramWorld.getType(k, m, n).getMaterial();
/*  46 */           if (m == paramInt2 - 1 && !material.isBuildable()) return false; 
/*  47 */           if (m == paramInt2 + b1 + 1 && !material.isBuildable()) return false;
/*     */           
/*  49 */           if ((k == paramInt1 - i - 1 || k == paramInt1 + i + 1 || n == paramInt3 - j - 1 || n == paramInt3 + j + 1) && 
/*  50 */             m == paramInt2 && paramWorld.isEmpty(k, m, n) && paramWorld.isEmpty(k, m + 1, n)) {
/*  51 */             b2++;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  59 */     if (b2 < 1 || b2 > 5) return false;
/*     */     
/*  61 */     for (k = paramInt1 - i - 1; k <= paramInt1 + i + 1; k++) {
/*  62 */       for (int m = paramInt2 + b1; m >= paramInt2 - 1; m--) {
/*  63 */         for (int n = paramInt3 - j - 1; n <= paramInt3 + j + 1; n++) {
/*     */           
/*  65 */           if (k == paramInt1 - i - 1 || m == paramInt2 - 1 || n == paramInt3 - j - 1 || k == paramInt1 + i + 1 || m == paramInt2 + b1 + 1 || n == paramInt3 + j + 1) {
/*  66 */             if (m >= 0 && !paramWorld.getType(k, m - 1, n).getMaterial().isBuildable()) {
/*  67 */               paramWorld.setAir(k, m, n);
/*  68 */             } else if (paramWorld.getType(k, m, n).getMaterial().isBuildable()) {
/*  69 */               if (m == paramInt2 - 1 && paramRandom.nextInt(4) != 0) {
/*  70 */                 paramWorld.setTypeAndData(k, m, n, Blocks.MOSSY_COBBLESTONE, 0, 2);
/*     */               } else {
/*  72 */                 paramWorld.setTypeAndData(k, m, n, Blocks.COBBLESTONE, 0, 2);
/*     */               } 
/*     */             } 
/*     */           } else {
/*  76 */             paramWorld.setAir(k, m, n);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  82 */     for (k = 0; k < 2; k++) {
/*  83 */       for (byte b = 0; b < 3; b++) {
/*  84 */         int m = paramInt1 + paramRandom.nextInt(i * 2 + 1) - i;
/*  85 */         int n = paramInt2;
/*  86 */         int i1 = paramInt3 + paramRandom.nextInt(j * 2 + 1) - j;
/*  87 */         if (paramWorld.isEmpty(m, n, i1)) {
/*     */           
/*  89 */           byte b3 = 0;
/*  90 */           if (paramWorld.getType(m - 1, n, i1).getMaterial().isBuildable()) b3++; 
/*  91 */           if (paramWorld.getType(m + 1, n, i1).getMaterial().isBuildable()) b3++; 
/*  92 */           if (paramWorld.getType(m, n, i1 - 1).getMaterial().isBuildable()) b3++; 
/*  93 */           if (paramWorld.getType(m, n, i1 + 1).getMaterial().isBuildable()) b3++;
/*     */           
/*  95 */           if (b3 == 1) {
/*     */             
/*  97 */             paramWorld.setTypeAndData(m, n, i1, Blocks.CHEST, 0, 2);
/*     */             
/*  99 */             StructurePieceTreasure[] arrayOfStructurePieceTreasure = StructurePieceTreasure.a(a, new StructurePieceTreasure[] { Items.ENCHANTED_BOOK.b(paramRandom) });
/*     */             
/* 101 */             TileEntityChest tileEntityChest = (TileEntityChest)paramWorld.getTileEntity(m, n, i1);
/* 102 */             if (tileEntityChest != null) {
/* 103 */               StructurePieceTreasure.a(paramRandom, arrayOfStructurePieceTreasure, tileEntityChest, 8);
/*     */             }
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 110 */     paramWorld.setTypeAndData(paramInt1, paramInt2, paramInt3, Blocks.MOB_SPAWNER, 0, 2);
/* 111 */     TileEntityMobSpawner tileEntityMobSpawner = (TileEntityMobSpawner)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
/* 112 */     if (tileEntityMobSpawner != null) {
/* 113 */       tileEntityMobSpawner.getSpawner().setMobName(a(paramRandom));
/*     */     } else {
/* 115 */       System.err.println("Failed to fetch mob spawner entity at (" + paramInt1 + ", " + paramInt2 + ", " + paramInt3 + ")");
/*     */     } 
/*     */     
/* 118 */     return true;
/*     */   }
/*     */   
/*     */   private String a(Random paramRandom) {
/* 122 */     int i = paramRandom.nextInt(4);
/* 123 */     if (i == 0) return "Skeleton"; 
/* 124 */     if (i == 1) return "Zombie"; 
/* 125 */     if (i == 2) return "Zombie"; 
/* 126 */     if (i == 3) return "Spider"; 
/* 127 */     return "";
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenDungeons.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
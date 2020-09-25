/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenBonusChest
/*    */   extends WorldGenerator
/*    */ {
/*    */   private final StructurePieceTreasure[] a;
/*    */   private final int b;
/*    */   
/*    */   public WorldGenBonusChest(StructurePieceTreasure[] paramArrayOfStructurePieceTreasure, int paramInt) {
/* 18 */     this.a = paramArrayOfStructurePieceTreasure;
/* 19 */     this.b = paramInt;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean generate(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3) {
/*    */     Block block;
/* 25 */     while (((block = paramWorld.getType(paramInt1, paramInt2, paramInt3)).getMaterial() == Material.AIR || block.getMaterial() == Material.LEAVES) && paramInt2 > 1) {
/* 26 */       paramInt2--;
/*    */     }
/* 28 */     if (paramInt2 < 1) {
/* 29 */       return false;
/*    */     }
/* 31 */     paramInt2++;
/*    */     
/* 33 */     for (byte b = 0; b < 4; b++) {
/* 34 */       int i = paramInt1 + paramRandom.nextInt(4) - paramRandom.nextInt(4);
/* 35 */       int j = paramInt2 + paramRandom.nextInt(3) - paramRandom.nextInt(3);
/* 36 */       int k = paramInt3 + paramRandom.nextInt(4) - paramRandom.nextInt(4);
/* 37 */       if (paramWorld.isEmpty(i, j, k) && World.a(paramWorld, i, j - 1, k)) {
/* 38 */         paramWorld.setTypeAndData(i, j, k, Blocks.CHEST, 0, 2);
/* 39 */         TileEntityChest tileEntityChest = (TileEntityChest)paramWorld.getTileEntity(i, j, k);
/* 40 */         if (tileEntityChest != null && 
/* 41 */           tileEntityChest != null) StructurePieceTreasure.a(paramRandom, this.a, tileEntityChest, this.b);
/*    */         
/* 43 */         if (paramWorld.isEmpty(i - 1, j, k) && World.a(paramWorld, i - 1, j - 1, k)) {
/* 44 */           paramWorld.setTypeAndData(i - 1, j, k, Blocks.TORCH, 0, 2);
/*    */         }
/* 46 */         if (paramWorld.isEmpty(i + 1, j, k) && World.a(paramWorld, i - 1, j - 1, k)) {
/* 47 */           paramWorld.setTypeAndData(i + 1, j, k, Blocks.TORCH, 0, 2);
/*    */         }
/* 49 */         if (paramWorld.isEmpty(i, j, k - 1) && World.a(paramWorld, i - 1, j - 1, k)) {
/* 50 */           paramWorld.setTypeAndData(i, j, k - 1, Blocks.TORCH, 0, 2);
/*    */         }
/* 52 */         if (paramWorld.isEmpty(i, j, k + 1) && World.a(paramWorld, i - 1, j - 1, k)) {
/* 53 */           paramWorld.setTypeAndData(i, j, k + 1, Blocks.TORCH, 0, 2);
/*    */         }
/* 55 */         return true;
/*    */       } 
/*    */     } 
/*    */     
/* 59 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenBonusChest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
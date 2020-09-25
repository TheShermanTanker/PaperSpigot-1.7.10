/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenLiquids
/*    */   extends WorldGenerator
/*    */ {
/*    */   private Block a;
/*    */   
/*    */   public WorldGenLiquids(Block paramBlock) {
/* 14 */     this.a = paramBlock;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean generate(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3) {
/* 19 */     if (paramWorld.getType(paramInt1, paramInt2 + 1, paramInt3) != Blocks.STONE) return false; 
/* 20 */     if (paramWorld.getType(paramInt1, paramInt2 - 1, paramInt3) != Blocks.STONE) return false;
/*    */     
/* 22 */     if (paramWorld.getType(paramInt1, paramInt2, paramInt3).getMaterial() != Material.AIR && paramWorld.getType(paramInt1, paramInt2, paramInt3) != Blocks.STONE) return false;
/*    */     
/* 24 */     byte b1 = 0;
/* 25 */     if (paramWorld.getType(paramInt1 - 1, paramInt2, paramInt3) == Blocks.STONE) b1++; 
/* 26 */     if (paramWorld.getType(paramInt1 + 1, paramInt2, paramInt3) == Blocks.STONE) b1++; 
/* 27 */     if (paramWorld.getType(paramInt1, paramInt2, paramInt3 - 1) == Blocks.STONE) b1++; 
/* 28 */     if (paramWorld.getType(paramInt1, paramInt2, paramInt3 + 1) == Blocks.STONE) b1++;
/*    */     
/* 30 */     byte b2 = 0;
/* 31 */     if (paramWorld.isEmpty(paramInt1 - 1, paramInt2, paramInt3)) b2++; 
/* 32 */     if (paramWorld.isEmpty(paramInt1 + 1, paramInt2, paramInt3)) b2++; 
/* 33 */     if (paramWorld.isEmpty(paramInt1, paramInt2, paramInt3 - 1)) b2++; 
/* 34 */     if (paramWorld.isEmpty(paramInt1, paramInt2, paramInt3 + 1)) b2++;
/*    */     
/* 36 */     if (b1 == 3 && b2 == 1) {
/* 37 */       paramWorld.setTypeAndData(paramInt1, paramInt2, paramInt3, this.a, 0, 2);
/* 38 */       paramWorld.d = true;
/* 39 */       this.a.a(paramWorld, paramInt1, paramInt2, paramInt3, paramRandom);
/* 40 */       paramWorld.d = false;
/*    */     } 
/*    */     
/* 43 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenLiquids.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
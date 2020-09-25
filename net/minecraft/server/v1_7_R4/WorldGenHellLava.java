/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenHellLava
/*    */   extends WorldGenerator
/*    */ {
/*    */   private Block a;
/*    */   private boolean b;
/*    */   
/*    */   public WorldGenHellLava(Block paramBlock, boolean paramBoolean) {
/* 15 */     this.a = paramBlock;
/* 16 */     this.b = paramBoolean;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean generate(World paramWorld, Random paramRandom, int paramInt1, int paramInt2, int paramInt3) {
/* 21 */     if (paramWorld.getType(paramInt1, paramInt2 + 1, paramInt3) != Blocks.NETHERRACK) return false; 
/* 22 */     if (paramWorld.getType(paramInt1, paramInt2, paramInt3).getMaterial() != Material.AIR && paramWorld.getType(paramInt1, paramInt2, paramInt3) != Blocks.NETHERRACK) return false;
/*    */     
/* 24 */     byte b1 = 0;
/* 25 */     if (paramWorld.getType(paramInt1 - 1, paramInt2, paramInt3) == Blocks.NETHERRACK) b1++; 
/* 26 */     if (paramWorld.getType(paramInt1 + 1, paramInt2, paramInt3) == Blocks.NETHERRACK) b1++; 
/* 27 */     if (paramWorld.getType(paramInt1, paramInt2, paramInt3 - 1) == Blocks.NETHERRACK) b1++; 
/* 28 */     if (paramWorld.getType(paramInt1, paramInt2, paramInt3 + 1) == Blocks.NETHERRACK) b1++; 
/* 29 */     if (paramWorld.getType(paramInt1, paramInt2 - 1, paramInt3) == Blocks.NETHERRACK) b1++;
/*    */     
/* 31 */     byte b2 = 0;
/* 32 */     if (paramWorld.isEmpty(paramInt1 - 1, paramInt2, paramInt3)) b2++; 
/* 33 */     if (paramWorld.isEmpty(paramInt1 + 1, paramInt2, paramInt3)) b2++; 
/* 34 */     if (paramWorld.isEmpty(paramInt1, paramInt2, paramInt3 - 1)) b2++; 
/* 35 */     if (paramWorld.isEmpty(paramInt1, paramInt2, paramInt3 + 1)) b2++; 
/* 36 */     if (paramWorld.isEmpty(paramInt1, paramInt2 - 1, paramInt3)) b2++;
/*    */     
/* 38 */     if ((!this.b && b1 == 4 && b2 == 1) || b1 == 5) {
/* 39 */       paramWorld.setTypeAndData(paramInt1, paramInt2, paramInt3, this.a, 0, 2);
/* 40 */       paramWorld.d = true;
/* 41 */       this.a.a(paramWorld, paramInt1, paramInt2, paramInt3, paramRandom);
/* 42 */       paramWorld.d = false;
/*    */     } 
/*    */     
/* 45 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenHellLava.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
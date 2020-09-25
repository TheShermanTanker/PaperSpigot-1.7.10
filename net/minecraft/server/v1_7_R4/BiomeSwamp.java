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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BiomeSwamp
/*    */   extends BiomeBase
/*    */ {
/*    */   protected BiomeSwamp(int paramInt) {
/* 18 */     super(paramInt);
/* 19 */     this.ar.x = 2;
/* 20 */     this.ar.y = 1;
/* 21 */     this.ar.A = 1;
/* 22 */     this.ar.B = 8;
/* 23 */     this.ar.C = 10;
/* 24 */     this.ar.G = 1;
/* 25 */     this.ar.w = 4;
/* 26 */     this.ar.F = 0;
/* 27 */     this.ar.E = 0;
/* 28 */     this.ar.z = 5;
/*    */     
/* 30 */     this.aq = 14745518;
/*    */     
/* 32 */     this.as.add(new BiomeMeta(EntitySlime.class, 1, 1, 1));
/*    */   }
/*    */ 
/*    */   
/*    */   public WorldGenTreeAbstract a(Random paramRandom) {
/* 37 */     return this.aB;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String a(Random paramRandom, int paramInt1, int paramInt2, int paramInt3) {
/* 57 */     return BlockFlowers.a[1];
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(World paramWorld, Random paramRandom, Block[] paramArrayOfBlock, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, double paramDouble) {
/* 62 */     double d = ad.a(paramInt1 * 0.25D, paramInt2 * 0.25D);
/* 63 */     if (d > 0.0D) {
/* 64 */       int i = paramInt1 & 0xF;
/* 65 */       int j = paramInt2 & 0xF;
/* 66 */       int k = paramArrayOfBlock.length / 256;
/* 67 */       for (char c = 'ÿ'; c >= '\000'; c--) {
/* 68 */         int m = (j * 16 + i) * k + c;
/* 69 */         if (paramArrayOfBlock[m] == null || paramArrayOfBlock[m].getMaterial() != Material.AIR) {
/* 70 */           if (c == '>' && paramArrayOfBlock[m] != Blocks.STATIONARY_WATER) {
/* 71 */             paramArrayOfBlock[m] = Blocks.STATIONARY_WATER;
/* 72 */             if (d < 0.12D) {
/* 73 */               paramArrayOfBlock[m + 1] = Blocks.WATER_LILY;
/*    */             }
/*    */           } 
/*    */           
/*    */           break;
/*    */         } 
/*    */       } 
/*    */     } 
/* 81 */     b(paramWorld, paramRandom, paramArrayOfBlock, paramArrayOfbyte, paramInt1, paramInt2, paramDouble);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BiomeSwamp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
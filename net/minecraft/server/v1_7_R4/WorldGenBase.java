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
/*    */ public class WorldGenBase
/*    */ {
/* 13 */   protected int a = 8;
/* 14 */   protected Random b = new Random();
/*    */   protected World c;
/*    */   
/*    */   public void a(IChunkProvider paramIChunkProvider, World paramWorld, int paramInt1, int paramInt2, Block[] paramArrayOfBlock) {
/* 18 */     int i = this.a;
/* 19 */     this.c = paramWorld;
/*    */     
/* 21 */     this.b.setSeed(paramWorld.getSeed());
/* 22 */     long l1 = this.b.nextLong();
/* 23 */     long l2 = this.b.nextLong();
/*    */     
/* 25 */     for (int j = paramInt1 - i; j <= paramInt1 + i; j++) {
/* 26 */       for (int k = paramInt2 - i; k <= paramInt2 + i; k++) {
/* 27 */         long l3 = j * l1;
/* 28 */         long l4 = k * l2;
/* 29 */         this.b.setSeed(l3 ^ l4 ^ paramWorld.getSeed());
/* 30 */         a(paramWorld, j, k, paramInt1, paramInt2, paramArrayOfBlock);
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   protected void a(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Block[] paramArrayOfBlock) {}
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BiomePlains
/*    */   extends BiomeBase
/*    */ {
/*    */   protected boolean aC;
/*    */   
/*    */   protected BiomePlains(int paramInt) {
/* 14 */     super(paramInt);
/*    */     
/* 16 */     a(0.8F, 0.4F);
/* 17 */     a(e);
/*    */     
/* 19 */     this.at.add(new BiomeMeta(EntityHorse.class, 5, 2, 6));
/*    */     
/* 21 */     this.ar.x = -999;
/* 22 */     this.ar.y = 4;
/* 23 */     this.ar.z = 10;
/*    */   }
/*    */ 
/*    */   
/*    */   public String a(Random paramRandom, int paramInt1, int paramInt2, int paramInt3) {
/* 28 */     double d = ad.a(paramInt1 / 200.0D, paramInt3 / 200.0D);
/* 29 */     if (d < -0.8D) {
/* 30 */       int i = paramRandom.nextInt(4);
/* 31 */       return BlockFlowers.a[4 + i];
/*    */     } 
/* 33 */     if (paramRandom.nextInt(3) > 0) {
/* 34 */       int i = paramRandom.nextInt(3);
/* 35 */       if (i == 0)
/* 36 */         return BlockFlowers.a[0]; 
/* 37 */       if (i == 1) {
/* 38 */         return BlockFlowers.a[3];
/*    */       }
/* 40 */       return BlockFlowers.a[8];
/*    */     } 
/*    */     
/* 43 */     return BlockFlowers.b[0];
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void a(World paramWorld, Random paramRandom, int paramInt1, int paramInt2) {
/* 49 */     double d = ad.a((paramInt1 + 8) / 200.0D, (paramInt2 + 8) / 200.0D);
/* 50 */     if (d < -0.8D) {
/* 51 */       this.ar.y = 15;
/* 52 */       this.ar.z = 5;
/*    */     } else {
/* 54 */       this.ar.y = 4;
/* 55 */       this.ar.z = 10;
/*    */       
/* 57 */       ae.a(2);
/* 58 */       for (byte b = 0; b < 7; b++) {
/* 59 */         int i = paramInt1 + paramRandom.nextInt(16) + 8;
/* 60 */         int j = paramInt2 + paramRandom.nextInt(16) + 8;
/* 61 */         int k = paramRandom.nextInt(paramWorld.getHighestBlockYAt(i, j) + 32);
/* 62 */         ae.generate(paramWorld, paramRandom, i, k, j);
/*    */       } 
/*    */     } 
/* 65 */     if (this.aC) {
/* 66 */       ae.a(0);
/* 67 */       for (byte b = 0; b < 10; b++) {
/* 68 */         int i = paramInt1 + paramRandom.nextInt(16) + 8;
/* 69 */         int j = paramInt2 + paramRandom.nextInt(16) + 8;
/* 70 */         int k = paramRandom.nextInt(paramWorld.getHighestBlockYAt(i, j) + 32);
/* 71 */         ae.generate(paramWorld, paramRandom, i, k, j);
/*    */       } 
/*    */     } 
/* 74 */     super.a(paramWorld, paramRandom, paramInt1, paramInt2);
/*    */   }
/*    */ 
/*    */   
/*    */   protected BiomeBase k() {
/* 79 */     BiomePlains biomePlains = new BiomePlains(this.id + 128);
/* 80 */     biomePlains.a("Sunflower Plains");
/* 81 */     biomePlains.aC = true;
/* 82 */     biomePlains.b(9286496);
/* 83 */     biomePlains.ah = 14273354;
/* 84 */     return biomePlains;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BiomePlains.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
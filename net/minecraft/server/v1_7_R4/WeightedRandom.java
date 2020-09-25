/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import java.util.Random;
/*    */ 
/*    */ public class WeightedRandom {
/*    */   public static int a(Collection paramCollection) {
/*  8 */     int i = 0;
/*  9 */     for (WeightedRandomChoice weightedRandomChoice : paramCollection) {
/* 10 */       i += weightedRandomChoice.a;
/*    */     }
/* 12 */     return i;
/*    */   }
/*    */   
/*    */   public static WeightedRandomChoice a(Random paramRandom, Collection paramCollection, int paramInt) {
/* 16 */     if (paramInt <= 0) {
/* 17 */       throw new IllegalArgumentException();
/*    */     }
/*    */     
/* 20 */     int i = paramRandom.nextInt(paramInt);
/* 21 */     for (WeightedRandomChoice weightedRandomChoice : paramCollection) {
/* 22 */       i -= weightedRandomChoice.a;
/* 23 */       if (i < 0) {
/* 24 */         return weightedRandomChoice;
/*    */       }
/*    */     } 
/* 27 */     return null;
/*    */   }
/*    */   
/*    */   public static WeightedRandomChoice a(Random paramRandom, Collection paramCollection) {
/* 31 */     return a(paramRandom, paramCollection, a(paramCollection));
/*    */   }
/*    */   
/*    */   public static int a(WeightedRandomChoice[] paramArrayOfWeightedRandomChoice) {
/* 35 */     int i = 0;
/* 36 */     for (WeightedRandomChoice weightedRandomChoice : paramArrayOfWeightedRandomChoice) {
/* 37 */       i += weightedRandomChoice.a;
/*    */     }
/* 39 */     return i;
/*    */   }
/*    */ 
/*    */   
/*    */   public static WeightedRandomChoice a(Random paramRandom, WeightedRandomChoice[] paramArrayOfWeightedRandomChoice, int paramInt) {
/* 44 */     if (paramInt <= 0) {
/* 45 */       throw new IllegalArgumentException();
/*    */     }
/*    */     
/* 48 */     int i = paramRandom.nextInt(paramInt);
/* 49 */     for (WeightedRandomChoice weightedRandomChoice : paramArrayOfWeightedRandomChoice) {
/* 50 */       i -= weightedRandomChoice.a;
/* 51 */       if (i < 0) {
/* 52 */         return weightedRandomChoice;
/*    */       }
/*    */     } 
/* 55 */     return null;
/*    */   }
/*    */   
/*    */   public static WeightedRandomChoice a(Random paramRandom, WeightedRandomChoice[] paramArrayOfWeightedRandomChoice) {
/* 59 */     return a(paramRandom, paramArrayOfWeightedRandomChoice, a(paramArrayOfWeightedRandomChoice));
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WeightedRandom.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
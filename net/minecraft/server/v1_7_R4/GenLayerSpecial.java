/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GenLayerSpecial
/*     */   extends GenLayer
/*     */ {
/*     */   private final EnumGenLayerSpecial c;
/*     */   
/*     */   public GenLayerSpecial(long paramLong, GenLayer paramGenLayer, EnumGenLayerSpecial paramEnumGenLayerSpecial) {
/*  12 */     super(paramLong);
/*  13 */     this.a = paramGenLayer;
/*  14 */     this.c = paramEnumGenLayerSpecial;
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] a(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  19 */     switch (GenLayerJumpTable.a[this.c.ordinal()])
/*     */     
/*     */     { default:
/*  22 */         return c(paramInt1, paramInt2, paramInt3, paramInt4);
/*     */       case 2:
/*  24 */         return d(paramInt1, paramInt2, paramInt3, paramInt4);
/*     */       case 3:
/*  26 */         break; }  return e(paramInt1, paramInt2, paramInt3, paramInt4);
/*     */   }
/*     */ 
/*     */   
/*     */   private int[] c(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  31 */     int i = paramInt1 - 1;
/*  32 */     int j = paramInt2 - 1;
/*  33 */     int k = 1 + paramInt3 + 1;
/*  34 */     int m = 1 + paramInt4 + 1;
/*  35 */     int[] arrayOfInt1 = this.a.a(i, j, k, m);
/*     */     
/*  37 */     int[] arrayOfInt2 = IntCache.a(paramInt3 * paramInt4);
/*     */     
/*  39 */     for (byte b = 0; b < paramInt4; b++) {
/*  40 */       for (byte b1 = 0; b1 < paramInt3; b1++) {
/*  41 */         a((b1 + paramInt1), (b + paramInt2));
/*     */         
/*  43 */         int n = arrayOfInt1[b1 + 1 + (b + 1) * k];
/*     */         
/*  45 */         if (n == 1) {
/*  46 */           int i1 = arrayOfInt1[b1 + 1 + (b + 1 - 1) * k];
/*  47 */           int i2 = arrayOfInt1[b1 + 1 + 1 + (b + 1) * k];
/*  48 */           int i3 = arrayOfInt1[b1 + 1 - 1 + (b + 1) * k];
/*  49 */           int i4 = arrayOfInt1[b1 + 1 + (b + 1 + 1) * k];
/*     */           
/*  51 */           boolean bool1 = (i1 == 3 || i2 == 3 || i3 == 3 || i4 == 3) ? true : false;
/*  52 */           boolean bool2 = (i1 == 4 || i2 == 4 || i3 == 4 || i4 == 4) ? true : false;
/*  53 */           if (bool1 || bool2) {
/*  54 */             n = 2;
/*     */           }
/*     */         } 
/*     */         
/*  58 */         arrayOfInt2[b1 + b * paramInt3] = n;
/*     */       } 
/*     */     } 
/*     */     
/*  62 */     return arrayOfInt2;
/*     */   }
/*     */   
/*     */   private int[] d(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  66 */     int i = paramInt1 - 1;
/*  67 */     int j = paramInt2 - 1;
/*  68 */     int k = 1 + paramInt3 + 1;
/*  69 */     int m = 1 + paramInt4 + 1;
/*  70 */     int[] arrayOfInt1 = this.a.a(i, j, k, m);
/*     */     
/*  72 */     int[] arrayOfInt2 = IntCache.a(paramInt3 * paramInt4);
/*     */     
/*  74 */     for (byte b = 0; b < paramInt4; b++) {
/*  75 */       for (byte b1 = 0; b1 < paramInt3; b1++) {
/*  76 */         int n = arrayOfInt1[b1 + 1 + (b + 1) * k];
/*     */         
/*  78 */         if (n == 4) {
/*  79 */           int i1 = arrayOfInt1[b1 + 1 + (b + 1 - 1) * k];
/*  80 */           int i2 = arrayOfInt1[b1 + 1 + 1 + (b + 1) * k];
/*  81 */           int i3 = arrayOfInt1[b1 + 1 - 1 + (b + 1) * k];
/*  82 */           int i4 = arrayOfInt1[b1 + 1 + (b + 1 + 1) * k];
/*     */           
/*  84 */           boolean bool1 = (i1 == 2 || i2 == 2 || i3 == 2 || i4 == 2) ? true : false;
/*  85 */           boolean bool2 = (i1 == 1 || i2 == 1 || i3 == 1 || i4 == 1) ? true : false;
/*     */           
/*  87 */           if (bool2 || bool1) {
/*  88 */             n = 3;
/*     */           }
/*     */         } 
/*     */         
/*  92 */         arrayOfInt2[b1 + b * paramInt3] = n;
/*     */       } 
/*     */     } 
/*     */     
/*  96 */     return arrayOfInt2;
/*     */   }
/*     */   
/*     */   private int[] e(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 100 */     int[] arrayOfInt1 = this.a.a(paramInt1, paramInt2, paramInt3, paramInt4);
/* 101 */     int[] arrayOfInt2 = IntCache.a(paramInt3 * paramInt4);
/*     */     
/* 103 */     for (byte b = 0; b < paramInt4; b++) {
/* 104 */       for (byte b1 = 0; b1 < paramInt3; b1++) {
/* 105 */         a((b1 + paramInt1), (b + paramInt2));
/*     */         
/* 107 */         int i = arrayOfInt1[b1 + b * paramInt3];
/*     */         
/* 109 */         if (i != 0 && a(13) == 0) {
/* 110 */           i |= 1 + a(15) << 8 & 0xF00;
/*     */         }
/*     */         
/* 113 */         arrayOfInt2[b1 + b * paramInt3] = i;
/*     */       } 
/*     */     } 
/*     */     
/* 117 */     return arrayOfInt2;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\GenLayerSpecial.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ public class Path
/*     */ {
/*   5 */   private PathPoint[] a = new PathPoint[128];
/*     */   
/*     */   private int b;
/*     */ 
/*     */   
/*     */   public PathPoint a(PathPoint pathpoint) {
/*  11 */     if (pathpoint.d >= 0) {
/*  12 */       throw new IllegalStateException("OW KNOWS!");
/*     */     }
/*  14 */     if (this.b == this.a.length) {
/*  15 */       PathPoint[] apathpoint = new PathPoint[this.b << 1];
/*     */       
/*  17 */       System.arraycopy(this.a, 0, apathpoint, 0, this.b);
/*  18 */       this.a = apathpoint;
/*     */     } 
/*     */     
/*  21 */     this.a[this.b] = pathpoint;
/*  22 */     pathpoint.d = this.b;
/*  23 */     a(this.b++);
/*  24 */     return pathpoint;
/*     */   }
/*     */ 
/*     */   
/*     */   public void a() {
/*  29 */     this.b = 0;
/*     */   }
/*     */   
/*     */   public PathPoint c() {
/*  33 */     PathPoint pathpoint = this.a[0];
/*     */     
/*  35 */     this.a[0] = this.a[--this.b];
/*  36 */     this.a[this.b] = null;
/*  37 */     if (this.b > 0) {
/*  38 */       b(0);
/*     */     }
/*     */     
/*  41 */     pathpoint.d = -1;
/*  42 */     return pathpoint;
/*     */   }
/*     */   
/*     */   public void a(PathPoint pathpoint, float f) {
/*  46 */     float f1 = pathpoint.g;
/*     */     
/*  48 */     pathpoint.g = f;
/*  49 */     if (f < f1) {
/*  50 */       a(pathpoint.d);
/*     */     } else {
/*  52 */       b(pathpoint.d);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void a(int i) {
/*  57 */     PathPoint pathpoint = this.a[i];
/*     */ 
/*     */ 
/*     */     
/*  61 */     for (float f = pathpoint.g; i > 0; i = j) {
/*  62 */       int j = i - 1 >> 1;
/*  63 */       PathPoint pathpoint1 = this.a[j];
/*     */       
/*  65 */       if (f >= pathpoint1.g) {
/*     */         break;
/*     */       }
/*     */       
/*  69 */       this.a[i] = pathpoint1;
/*  70 */       pathpoint1.d = i;
/*     */     } 
/*     */     
/*  73 */     this.a[i] = pathpoint;
/*  74 */     pathpoint.d = i;
/*     */   }
/*     */   
/*     */   private void b(int i) {
/*  78 */     PathPoint pathpoint = this.a[i];
/*  79 */     float f = pathpoint.g; while (true) {
/*     */       PathPoint pathpoint2;
/*     */       float f2;
/*  82 */       int j = 1 + (i << 1);
/*  83 */       int k = j + 1;
/*     */       
/*  85 */       if (j >= this.b) {
/*     */         break;
/*     */       }
/*     */       
/*  89 */       PathPoint pathpoint1 = this.a[j];
/*  90 */       float f1 = pathpoint1.g;
/*     */ 
/*     */ 
/*     */       
/*  94 */       if (k >= this.b) {
/*  95 */         pathpoint2 = null;
/*  96 */         f2 = Float.POSITIVE_INFINITY;
/*     */       } else {
/*  98 */         pathpoint2 = this.a[k];
/*  99 */         f2 = pathpoint2.g;
/*     */       } 
/*     */       
/* 102 */       if (f1 < f2) {
/* 103 */         if (f1 >= f) {
/*     */           break;
/*     */         }
/*     */         
/* 107 */         this.a[i] = pathpoint1;
/* 108 */         pathpoint1.d = i;
/* 109 */         i = j; continue;
/*     */       } 
/* 111 */       if (f2 >= f) {
/*     */         break;
/*     */       }
/*     */       
/* 115 */       this.a[i] = pathpoint2;
/* 116 */       pathpoint2.d = i;
/* 117 */       i = k;
/*     */     } 
/*     */ 
/*     */     
/* 121 */     this.a[i] = pathpoint;
/* 122 */     pathpoint.d = i;
/*     */   }
/*     */   
/*     */   public boolean e() {
/* 126 */     return (this.b == 0);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\Path.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
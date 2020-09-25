/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ public class Vec3D
/*     */ {
/*     */   public double a;
/*     */   
/*     */   public static Vec3D a(double paramDouble1, double paramDouble2, double paramDouble3) {
/*   8 */     return new Vec3D(paramDouble1, paramDouble2, paramDouble3);
/*     */   }
/*     */   public double b;
/*     */   public double c;
/*     */   
/*     */   protected Vec3D(double paramDouble1, double paramDouble2, double paramDouble3) {
/*  14 */     if (paramDouble1 == -0.0D) paramDouble1 = 0.0D; 
/*  15 */     if (paramDouble2 == -0.0D) paramDouble2 = 0.0D; 
/*  16 */     if (paramDouble3 == -0.0D) paramDouble3 = 0.0D; 
/*  17 */     this.a = paramDouble1;
/*  18 */     this.b = paramDouble2;
/*  19 */     this.c = paramDouble3;
/*     */   }
/*     */   
/*     */   protected Vec3D b(double paramDouble1, double paramDouble2, double paramDouble3) {
/*  23 */     this.a = paramDouble1;
/*  24 */     this.b = paramDouble2;
/*  25 */     this.c = paramDouble3;
/*  26 */     return this;
/*     */   }
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
/*     */   public Vec3D a() {
/*  42 */     double d = MathHelper.sqrt(this.a * this.a + this.b * this.b + this.c * this.c);
/*  43 */     if (d < 1.0E-4D) return a(0.0D, 0.0D, 0.0D); 
/*  44 */     return a(this.a / d, this.b / d, this.c / d);
/*     */   }
/*     */   
/*     */   public double b(Vec3D paramVec3D) {
/*  48 */     return this.a * paramVec3D.a + this.b * paramVec3D.b + this.c * paramVec3D.c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vec3D add(double paramDouble1, double paramDouble2, double paramDouble3) {
/*  56 */     return a(this.a + paramDouble1, this.b + paramDouble2, this.c + paramDouble3);
/*     */   }
/*     */   
/*     */   public double d(Vec3D paramVec3D) {
/*  60 */     double d1 = paramVec3D.a - this.a;
/*  61 */     double d2 = paramVec3D.b - this.b;
/*  62 */     double d3 = paramVec3D.c - this.c;
/*  63 */     return MathHelper.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
/*     */   }
/*     */   
/*     */   public double distanceSquared(Vec3D paramVec3D) {
/*  67 */     double d1 = paramVec3D.a - this.a;
/*  68 */     double d2 = paramVec3D.b - this.b;
/*  69 */     double d3 = paramVec3D.c - this.c;
/*  70 */     return d1 * d1 + d2 * d2 + d3 * d3;
/*     */   }
/*     */   
/*     */   public double d(double paramDouble1, double paramDouble2, double paramDouble3) {
/*  74 */     double d1 = paramDouble1 - this.a;
/*  75 */     double d2 = paramDouble2 - this.b;
/*  76 */     double d3 = paramDouble3 - this.c;
/*  77 */     return d1 * d1 + d2 * d2 + d3 * d3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double b() {
/*  85 */     return MathHelper.sqrt(this.a * this.a + this.b * this.b + this.c * this.c);
/*     */   }
/*     */   
/*     */   public Vec3D b(Vec3D paramVec3D, double paramDouble) {
/*  89 */     double d1 = paramVec3D.a - this.a;
/*  90 */     double d2 = paramVec3D.b - this.b;
/*  91 */     double d3 = paramVec3D.c - this.c;
/*     */     
/*  93 */     if (d1 * d1 < 1.0000000116860974E-7D) return null;
/*     */     
/*  95 */     double d4 = (paramDouble - this.a) / d1;
/*  96 */     if (d4 < 0.0D || d4 > 1.0D) return null; 
/*  97 */     return a(this.a + d1 * d4, this.b + d2 * d4, this.c + d3 * d4);
/*     */   }
/*     */   
/*     */   public Vec3D c(Vec3D paramVec3D, double paramDouble) {
/* 101 */     double d1 = paramVec3D.a - this.a;
/* 102 */     double d2 = paramVec3D.b - this.b;
/* 103 */     double d3 = paramVec3D.c - this.c;
/*     */     
/* 105 */     if (d2 * d2 < 1.0000000116860974E-7D) return null;
/*     */     
/* 107 */     double d4 = (paramDouble - this.b) / d2;
/* 108 */     if (d4 < 0.0D || d4 > 1.0D) return null; 
/* 109 */     return a(this.a + d1 * d4, this.b + d2 * d4, this.c + d3 * d4);
/*     */   }
/*     */   
/*     */   public Vec3D d(Vec3D paramVec3D, double paramDouble) {
/* 113 */     double d1 = paramVec3D.a - this.a;
/* 114 */     double d2 = paramVec3D.b - this.b;
/* 115 */     double d3 = paramVec3D.c - this.c;
/*     */     
/* 117 */     if (d3 * d3 < 1.0000000116860974E-7D) return null;
/*     */     
/* 119 */     double d4 = (paramDouble - this.c) / d3;
/* 120 */     if (d4 < 0.0D || d4 > 1.0D) return null; 
/* 121 */     return a(this.a + d1 * d4, this.b + d2 * d4, this.c + d3 * d4);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 125 */     return "(" + this.a + ", " + this.b + ", " + this.c + ")";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(float paramFloat) {
/* 133 */     float f1 = MathHelper.cos(paramFloat);
/* 134 */     float f2 = MathHelper.sin(paramFloat);
/*     */     
/* 136 */     double d1 = this.a;
/* 137 */     double d2 = this.b * f1 + this.c * f2;
/* 138 */     double d3 = this.c * f1 - this.b * f2;
/*     */     
/* 140 */     b(d1, d2, d3);
/*     */   }
/*     */   
/*     */   public void b(float paramFloat) {
/* 144 */     float f1 = MathHelper.cos(paramFloat);
/* 145 */     float f2 = MathHelper.sin(paramFloat);
/*     */     
/* 147 */     double d1 = this.a * f1 + this.c * f2;
/* 148 */     double d2 = this.b;
/* 149 */     double d3 = this.c * f1 - this.a * f2;
/*     */     
/* 151 */     b(d1, d2, d3);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\Vec3D.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
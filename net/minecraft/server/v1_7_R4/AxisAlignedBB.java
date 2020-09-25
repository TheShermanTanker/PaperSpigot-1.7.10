/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ public class AxisAlignedBB {
/*     */   public double a;
/*     */   public double b;
/*     */   public double c;
/*     */   
/*     */   public static AxisAlignedBB a(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6) {
/*   9 */     return new AxisAlignedBB(paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramDouble6);
/*     */   }
/*     */   public double d; public double e; public double f;
/*     */   protected AxisAlignedBB(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6) {
/*  13 */     this.a = paramDouble1;
/*  14 */     this.b = paramDouble2;
/*  15 */     this.c = paramDouble3;
/*  16 */     this.d = paramDouble4;
/*  17 */     this.e = paramDouble5;
/*  18 */     this.f = paramDouble6;
/*     */   }
/*     */   
/*     */   public AxisAlignedBB b(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6) {
/*  22 */     this.a = paramDouble1;
/*  23 */     this.b = paramDouble2;
/*  24 */     this.c = paramDouble3;
/*  25 */     this.d = paramDouble4;
/*  26 */     this.e = paramDouble5;
/*  27 */     this.f = paramDouble6;
/*  28 */     return this;
/*     */   }
/*     */   
/*     */   public AxisAlignedBB a(double paramDouble1, double paramDouble2, double paramDouble3) {
/*  32 */     double d1 = this.a;
/*  33 */     double d2 = this.b;
/*  34 */     double d3 = this.c;
/*  35 */     double d4 = this.d;
/*  36 */     double d5 = this.e;
/*  37 */     double d6 = this.f;
/*     */     
/*  39 */     if (paramDouble1 < 0.0D) d1 += paramDouble1; 
/*  40 */     if (paramDouble1 > 0.0D) d4 += paramDouble1;
/*     */     
/*  42 */     if (paramDouble2 < 0.0D) d2 += paramDouble2; 
/*  43 */     if (paramDouble2 > 0.0D) d5 += paramDouble2;
/*     */     
/*  45 */     if (paramDouble3 < 0.0D) d3 += paramDouble3; 
/*  46 */     if (paramDouble3 > 0.0D) d6 += paramDouble3;
/*     */     
/*  48 */     return a(d1, d2, d3, d4, d5, d6);
/*     */   }
/*     */   
/*     */   public AxisAlignedBB grow(double paramDouble1, double paramDouble2, double paramDouble3) {
/*  52 */     double d1 = this.a - paramDouble1;
/*  53 */     double d2 = this.b - paramDouble2;
/*  54 */     double d3 = this.c - paramDouble3;
/*  55 */     double d4 = this.d + paramDouble1;
/*  56 */     double d5 = this.e + paramDouble2;
/*  57 */     double d6 = this.f + paramDouble3;
/*     */     
/*  59 */     return a(d1, d2, d3, d4, d5, d6);
/*     */   }
/*     */   
/*     */   public AxisAlignedBB a(AxisAlignedBB paramAxisAlignedBB) {
/*  63 */     double d1 = Math.min(this.a, paramAxisAlignedBB.a);
/*  64 */     double d2 = Math.min(this.b, paramAxisAlignedBB.b);
/*  65 */     double d3 = Math.min(this.c, paramAxisAlignedBB.c);
/*  66 */     double d4 = Math.max(this.d, paramAxisAlignedBB.d);
/*  67 */     double d5 = Math.max(this.e, paramAxisAlignedBB.e);
/*  68 */     double d6 = Math.max(this.f, paramAxisAlignedBB.f);
/*     */     
/*  70 */     return a(d1, d2, d3, d4, d5, d6);
/*     */   }
/*     */   
/*     */   public AxisAlignedBB c(double paramDouble1, double paramDouble2, double paramDouble3) {
/*  74 */     return a(this.a + paramDouble1, this.b + paramDouble2, this.c + paramDouble3, this.d + paramDouble1, this.e + paramDouble2, this.f + paramDouble3);
/*     */   }
/*     */   
/*     */   public double a(AxisAlignedBB paramAxisAlignedBB, double paramDouble) {
/*  78 */     if (paramAxisAlignedBB.e <= this.b || paramAxisAlignedBB.b >= this.e) return paramDouble; 
/*  79 */     if (paramAxisAlignedBB.f <= this.c || paramAxisAlignedBB.c >= this.f) return paramDouble;
/*     */     
/*  81 */     if (paramDouble > 0.0D && paramAxisAlignedBB.d <= this.a) {
/*  82 */       double d = this.a - paramAxisAlignedBB.d;
/*  83 */       if (d < paramDouble) paramDouble = d; 
/*     */     } 
/*  85 */     if (paramDouble < 0.0D && paramAxisAlignedBB.a >= this.d) {
/*  86 */       double d = this.d - paramAxisAlignedBB.a;
/*  87 */       if (d > paramDouble) paramDouble = d;
/*     */     
/*     */     } 
/*  90 */     return paramDouble;
/*     */   }
/*     */   
/*     */   public double b(AxisAlignedBB paramAxisAlignedBB, double paramDouble) {
/*  94 */     if (paramAxisAlignedBB.d <= this.a || paramAxisAlignedBB.a >= this.d) return paramDouble; 
/*  95 */     if (paramAxisAlignedBB.f <= this.c || paramAxisAlignedBB.c >= this.f) return paramDouble;
/*     */     
/*  97 */     if (paramDouble > 0.0D && paramAxisAlignedBB.e <= this.b) {
/*  98 */       double d = this.b - paramAxisAlignedBB.e;
/*  99 */       if (d < paramDouble) paramDouble = d; 
/*     */     } 
/* 101 */     if (paramDouble < 0.0D && paramAxisAlignedBB.b >= this.e) {
/* 102 */       double d = this.e - paramAxisAlignedBB.b;
/* 103 */       if (d > paramDouble) paramDouble = d;
/*     */     
/*     */     } 
/* 106 */     return paramDouble;
/*     */   }
/*     */   
/*     */   public double c(AxisAlignedBB paramAxisAlignedBB, double paramDouble) {
/* 110 */     if (paramAxisAlignedBB.d <= this.a || paramAxisAlignedBB.a >= this.d) return paramDouble; 
/* 111 */     if (paramAxisAlignedBB.e <= this.b || paramAxisAlignedBB.b >= this.e) return paramDouble;
/*     */     
/* 113 */     if (paramDouble > 0.0D && paramAxisAlignedBB.f <= this.c) {
/* 114 */       double d = this.c - paramAxisAlignedBB.f;
/* 115 */       if (d < paramDouble) paramDouble = d; 
/*     */     } 
/* 117 */     if (paramDouble < 0.0D && paramAxisAlignedBB.c >= this.f) {
/* 118 */       double d = this.f - paramAxisAlignedBB.c;
/* 119 */       if (d > paramDouble) paramDouble = d;
/*     */     
/*     */     } 
/* 122 */     return paramDouble;
/*     */   }
/*     */   
/*     */   public boolean b(AxisAlignedBB paramAxisAlignedBB) {
/* 126 */     if (paramAxisAlignedBB.d <= this.a || paramAxisAlignedBB.a >= this.d) return false; 
/* 127 */     if (paramAxisAlignedBB.e <= this.b || paramAxisAlignedBB.b >= this.e) return false; 
/* 128 */     if (paramAxisAlignedBB.f <= this.c || paramAxisAlignedBB.c >= this.f) return false; 
/* 129 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB d(double paramDouble1, double paramDouble2, double paramDouble3) {
/* 140 */     this.a += paramDouble1;
/* 141 */     this.b += paramDouble2;
/* 142 */     this.c += paramDouble3;
/* 143 */     this.d += paramDouble1;
/* 144 */     this.e += paramDouble2;
/* 145 */     this.f += paramDouble3;
/* 146 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean a(Vec3D paramVec3D) {
/* 157 */     if (paramVec3D.a <= this.a || paramVec3D.a >= this.d) return false; 
/* 158 */     if (paramVec3D.b <= this.b || paramVec3D.b >= this.e) return false; 
/* 159 */     if (paramVec3D.c <= this.c || paramVec3D.c >= this.f) return false; 
/* 160 */     return true;
/*     */   }
/*     */   
/*     */   public double a() {
/* 164 */     double d1 = this.d - this.a;
/* 165 */     double d2 = this.e - this.b;
/* 166 */     double d3 = this.f - this.c;
/* 167 */     return (d1 + d2 + d3) / 3.0D;
/*     */   }
/*     */   
/*     */   public AxisAlignedBB shrink(double paramDouble1, double paramDouble2, double paramDouble3) {
/* 171 */     double d1 = this.a + paramDouble1;
/* 172 */     double d2 = this.b + paramDouble2;
/* 173 */     double d3 = this.c + paramDouble3;
/* 174 */     double d4 = this.d - paramDouble1;
/* 175 */     double d5 = this.e - paramDouble2;
/* 176 */     double d6 = this.f - paramDouble3;
/*     */     
/* 178 */     return a(d1, d2, d3, d4, d5, d6);
/*     */   }
/*     */   
/*     */   public AxisAlignedBB clone() {
/* 182 */     return a(this.a, this.b, this.c, this.d, this.e, this.f);
/*     */   }
/*     */   
/*     */   public MovingObjectPosition a(Vec3D paramVec3D1, Vec3D paramVec3D2) {
/* 186 */     Vec3D vec3D1 = paramVec3D1.b(paramVec3D2, this.a);
/* 187 */     Vec3D vec3D2 = paramVec3D1.b(paramVec3D2, this.d);
/*     */     
/* 189 */     Vec3D vec3D3 = paramVec3D1.c(paramVec3D2, this.b);
/* 190 */     Vec3D vec3D4 = paramVec3D1.c(paramVec3D2, this.e);
/*     */     
/* 192 */     Vec3D vec3D5 = paramVec3D1.d(paramVec3D2, this.c);
/* 193 */     Vec3D vec3D6 = paramVec3D1.d(paramVec3D2, this.f);
/*     */     
/* 195 */     if (!b(vec3D1)) vec3D1 = null; 
/* 196 */     if (!b(vec3D2)) vec3D2 = null; 
/* 197 */     if (!c(vec3D3)) vec3D3 = null; 
/* 198 */     if (!c(vec3D4)) vec3D4 = null; 
/* 199 */     if (!d(vec3D5)) vec3D5 = null; 
/* 200 */     if (!d(vec3D6)) vec3D6 = null;
/*     */     
/* 202 */     Vec3D vec3D7 = null;
/*     */     
/* 204 */     if (vec3D1 != null && (vec3D7 == null || paramVec3D1.distanceSquared(vec3D1) < paramVec3D1.distanceSquared(vec3D7))) vec3D7 = vec3D1; 
/* 205 */     if (vec3D2 != null && (vec3D7 == null || paramVec3D1.distanceSquared(vec3D2) < paramVec3D1.distanceSquared(vec3D7))) vec3D7 = vec3D2; 
/* 206 */     if (vec3D3 != null && (vec3D7 == null || paramVec3D1.distanceSquared(vec3D3) < paramVec3D1.distanceSquared(vec3D7))) vec3D7 = vec3D3; 
/* 207 */     if (vec3D4 != null && (vec3D7 == null || paramVec3D1.distanceSquared(vec3D4) < paramVec3D1.distanceSquared(vec3D7))) vec3D7 = vec3D4; 
/* 208 */     if (vec3D5 != null && (vec3D7 == null || paramVec3D1.distanceSquared(vec3D5) < paramVec3D1.distanceSquared(vec3D7))) vec3D7 = vec3D5; 
/* 209 */     if (vec3D6 != null && (vec3D7 == null || paramVec3D1.distanceSquared(vec3D6) < paramVec3D1.distanceSquared(vec3D7))) vec3D7 = vec3D6;
/*     */     
/* 211 */     if (vec3D7 == null) return null;
/*     */     
/* 213 */     byte b = -1;
/*     */     
/* 215 */     if (vec3D7 == vec3D1) b = 4; 
/* 216 */     if (vec3D7 == vec3D2) b = 5; 
/* 217 */     if (vec3D7 == vec3D3) b = 0; 
/* 218 */     if (vec3D7 == vec3D4) b = 1; 
/* 219 */     if (vec3D7 == vec3D5) b = 2; 
/* 220 */     if (vec3D7 == vec3D6) b = 3;
/*     */     
/* 222 */     return new MovingObjectPosition(0, 0, 0, b, vec3D7);
/*     */   }
/*     */   
/*     */   private boolean b(Vec3D paramVec3D) {
/* 226 */     if (paramVec3D == null) return false; 
/* 227 */     return (paramVec3D.b >= this.b && paramVec3D.b <= this.e && paramVec3D.c >= this.c && paramVec3D.c <= this.f);
/*     */   }
/*     */   
/*     */   private boolean c(Vec3D paramVec3D) {
/* 231 */     if (paramVec3D == null) return false; 
/* 232 */     return (paramVec3D.a >= this.a && paramVec3D.a <= this.d && paramVec3D.c >= this.c && paramVec3D.c <= this.f);
/*     */   }
/*     */   
/*     */   private boolean d(Vec3D paramVec3D) {
/* 236 */     if (paramVec3D == null) return false; 
/* 237 */     return (paramVec3D.a >= this.a && paramVec3D.a <= this.d && paramVec3D.b >= this.b && paramVec3D.b <= this.e);
/*     */   }
/*     */   
/*     */   public void d(AxisAlignedBB paramAxisAlignedBB) {
/* 241 */     this.a = paramAxisAlignedBB.a;
/* 242 */     this.b = paramAxisAlignedBB.b;
/* 243 */     this.c = paramAxisAlignedBB.c;
/* 244 */     this.d = paramAxisAlignedBB.d;
/* 245 */     this.e = paramAxisAlignedBB.e;
/* 246 */     this.f = paramAxisAlignedBB.f;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 250 */     return "box[" + this.a + ", " + this.b + ", " + this.c + " -> " + this.d + ", " + this.e + ", " + this.f + "]";
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\AxisAlignedBB.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
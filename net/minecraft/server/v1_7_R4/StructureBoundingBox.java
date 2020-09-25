/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ public class StructureBoundingBox
/*     */ {
/*     */   public int a;
/*     */   public int b;
/*     */   public int c;
/*     */   public int d;
/*     */   public int e;
/*     */   public int f;
/*     */   
/*     */   public StructureBoundingBox() {}
/*     */   
/*     */   public StructureBoundingBox(int[] paramArrayOfint) {
/*  15 */     if (paramArrayOfint.length == 6) {
/*  16 */       this.a = paramArrayOfint[0];
/*  17 */       this.b = paramArrayOfint[1];
/*  18 */       this.c = paramArrayOfint[2];
/*  19 */       this.d = paramArrayOfint[3];
/*  20 */       this.e = paramArrayOfint[4];
/*  21 */       this.f = paramArrayOfint[5];
/*     */     } 
/*     */   }
/*     */   
/*     */   public static StructureBoundingBox a() {
/*  26 */     return new StructureBoundingBox(2147483647, 2147483647, 2147483647, -2147483648, -2147483648, -2147483648);
/*     */   }
/*     */   
/*     */   public static StructureBoundingBox a(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10) {
/*  30 */     switch (paramInt10) {
/*     */       default:
/*  32 */         return new StructureBoundingBox(paramInt1 + paramInt4, paramInt2 + paramInt5, paramInt3 + paramInt6, paramInt1 + paramInt7 - 1 + paramInt4, paramInt2 + paramInt8 - 1 + paramInt5, paramInt3 + paramInt9 - 1 + paramInt6);
/*     */       
/*     */       case 2:
/*  35 */         return new StructureBoundingBox(paramInt1 + paramInt4, paramInt2 + paramInt5, paramInt3 - paramInt9 + 1 + paramInt6, paramInt1 + paramInt7 - 1 + paramInt4, paramInt2 + paramInt8 - 1 + paramInt5, paramInt3 + paramInt6);
/*     */       
/*     */       case 0:
/*  38 */         return new StructureBoundingBox(paramInt1 + paramInt4, paramInt2 + paramInt5, paramInt3 + paramInt6, paramInt1 + paramInt7 - 1 + paramInt4, paramInt2 + paramInt8 - 1 + paramInt5, paramInt3 + paramInt9 - 1 + paramInt6);
/*     */       
/*     */       case 1:
/*  41 */         return new StructureBoundingBox(paramInt1 - paramInt9 + 1 + paramInt6, paramInt2 + paramInt5, paramInt3 + paramInt4, paramInt1 + paramInt6, paramInt2 + paramInt8 - 1 + paramInt5, paramInt3 + paramInt7 - 1 + paramInt4);
/*     */       case 3:
/*     */         break;
/*  44 */     }  return new StructureBoundingBox(paramInt1 + paramInt6, paramInt2 + paramInt5, paramInt3 + paramInt4, paramInt1 + paramInt9 - 1 + paramInt6, paramInt2 + paramInt8 - 1 + paramInt5, paramInt3 + paramInt7 - 1 + paramInt4);
/*     */   }
/*     */ 
/*     */   
/*     */   public StructureBoundingBox(StructureBoundingBox paramStructureBoundingBox) {
/*  49 */     this.a = paramStructureBoundingBox.a;
/*  50 */     this.b = paramStructureBoundingBox.b;
/*  51 */     this.c = paramStructureBoundingBox.c;
/*  52 */     this.d = paramStructureBoundingBox.d;
/*  53 */     this.e = paramStructureBoundingBox.e;
/*  54 */     this.f = paramStructureBoundingBox.f;
/*     */   }
/*     */   
/*     */   public StructureBoundingBox(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/*  58 */     this.a = paramInt1;
/*  59 */     this.b = paramInt2;
/*  60 */     this.c = paramInt3;
/*  61 */     this.d = paramInt4;
/*  62 */     this.e = paramInt5;
/*  63 */     this.f = paramInt6;
/*     */   }
/*     */   
/*     */   public StructureBoundingBox(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  67 */     this.a = paramInt1;
/*  68 */     this.c = paramInt2;
/*  69 */     this.d = paramInt3;
/*  70 */     this.f = paramInt4;
/*     */ 
/*     */     
/*  73 */     this.b = 1;
/*  74 */     this.e = 512;
/*     */   }
/*     */   
/*     */   public boolean a(StructureBoundingBox paramStructureBoundingBox) {
/*  78 */     return (this.d >= paramStructureBoundingBox.a && this.a <= paramStructureBoundingBox.d && this.f >= paramStructureBoundingBox.c && this.c <= paramStructureBoundingBox.f && this.e >= paramStructureBoundingBox.b && this.b <= paramStructureBoundingBox.e);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean a(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  86 */     return (this.d >= paramInt1 && this.a <= paramInt3 && this.f >= paramInt2 && this.c <= paramInt4);
/*     */   }
/*     */   
/*     */   public void b(StructureBoundingBox paramStructureBoundingBox) {
/*  90 */     this.a = Math.min(this.a, paramStructureBoundingBox.a);
/*  91 */     this.b = Math.min(this.b, paramStructureBoundingBox.b);
/*  92 */     this.c = Math.min(this.c, paramStructureBoundingBox.c);
/*  93 */     this.d = Math.max(this.d, paramStructureBoundingBox.d);
/*  94 */     this.e = Math.max(this.e, paramStructureBoundingBox.e);
/*  95 */     this.f = Math.max(this.f, paramStructureBoundingBox.f);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(int paramInt1, int paramInt2, int paramInt3) {
/* 114 */     this.a += paramInt1;
/* 115 */     this.b += paramInt2;
/* 116 */     this.c += paramInt3;
/* 117 */     this.d += paramInt1;
/* 118 */     this.e += paramInt2;
/* 119 */     this.f += paramInt3;
/*     */   }
/*     */   
/*     */   public boolean b(int paramInt1, int paramInt2, int paramInt3) {
/* 123 */     return (paramInt1 >= this.a && paramInt1 <= this.d && paramInt3 >= this.c && paramInt3 <= this.f && paramInt2 >= this.b && paramInt2 <= this.e);
/*     */   }
/*     */   
/*     */   public int b() {
/* 127 */     return this.d - this.a + 1;
/*     */   }
/*     */   
/*     */   public int c() {
/* 131 */     return this.e - this.b + 1;
/*     */   }
/*     */   
/*     */   public int d() {
/* 135 */     return this.f - this.c + 1;
/*     */   }
/*     */   
/*     */   public int e() {
/* 139 */     return this.a + (this.d - this.a + 1) / 2;
/*     */   }
/*     */   
/*     */   public int f() {
/* 143 */     return this.b + (this.e - this.b + 1) / 2;
/*     */   }
/*     */   
/*     */   public int g() {
/* 147 */     return this.c + (this.f - this.c + 1) / 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 152 */     return "(" + this.a + ", " + this.b + ", " + this.c + "; " + this.d + ", " + this.e + ", " + this.f + ")";
/*     */   }
/*     */   
/*     */   public NBTTagIntArray h() {
/* 156 */     return new NBTTagIntArray(new int[] { this.a, this.b, this.c, this.d, this.e, this.f });
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\StructureBoundingBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ public class WorldGenBigTree
/*     */   extends WorldGenTreeAbstract {
/*   7 */   static final byte[] a = new byte[] { 2, 0, 0, 1, 2, 1 };
/*   8 */   Random b = new Random();
/*     */   World world;
/*  10 */   int[] d = new int[] { 0, 0, 0 };
/*     */   int e;
/*     */   int f;
/*  13 */   double g = 0.618D;
/*  14 */   double h = 1.0D;
/*  15 */   double i = 0.381D;
/*  16 */   double j = 1.0D;
/*  17 */   double k = 1.0D;
/*  18 */   int l = 1;
/*  19 */   int m = 12;
/*  20 */   int n = 4;
/*     */   int[][] o;
/*     */   
/*     */   public WorldGenBigTree(boolean flag) {
/*  24 */     super(flag);
/*     */   }
/*     */   
/*     */   void a() {
/*  28 */     this.f = (int)(this.e * this.g);
/*  29 */     if (this.f >= this.e) {
/*  30 */       this.f = this.e - 1;
/*     */     }
/*     */     
/*  33 */     int i = (int)(1.382D + Math.pow(this.k * this.e / 13.0D, 2.0D));
/*     */     
/*  35 */     if (i < 1) {
/*  36 */       i = 1;
/*     */     }
/*     */     
/*  39 */     int[][] aint = new int[i * this.e][4];
/*  40 */     int j = this.d[1] + this.e - this.n;
/*  41 */     int k = 1;
/*  42 */     int l = this.d[1] + this.f;
/*  43 */     int i1 = j - this.d[1];
/*     */     
/*  45 */     aint[0][0] = this.d[0];
/*  46 */     aint[0][1] = j;
/*  47 */     aint[0][2] = this.d[2];
/*  48 */     aint[0][3] = l;
/*  49 */     j--;
/*     */     
/*  51 */     while (i1 >= 0) {
/*  52 */       int j1 = 0;
/*  53 */       float f = a(i1);
/*     */       
/*  55 */       if (f < 0.0F) {
/*  56 */         j--;
/*  57 */         i1--; continue;
/*     */       } 
/*  59 */       for (double d0 = 0.5D; j1 < i; j1++) {
/*  60 */         double d1 = this.j * f * (this.b.nextFloat() + 0.328D);
/*  61 */         double d2 = this.b.nextFloat() * 2.0D * 3.14159D;
/*  62 */         int k1 = MathHelper.floor(d1 * Math.sin(d2) + this.d[0] + d0);
/*  63 */         int l1 = MathHelper.floor(d1 * Math.cos(d2) + this.d[2] + d0);
/*  64 */         int[] aint1 = { k1, j, l1 };
/*  65 */         int[] aint2 = { k1, j + this.n, l1 };
/*     */         
/*  67 */         if (a(aint1, aint2) == -1) {
/*  68 */           int[] aint3 = { this.d[0], this.d[1], this.d[2] };
/*  69 */           double d3 = Math.sqrt(Math.pow(Math.abs(this.d[0] - aint1[0]), 2.0D) + Math.pow(Math.abs(this.d[2] - aint1[2]), 2.0D));
/*  70 */           double d4 = d3 * this.i;
/*     */           
/*  72 */           if (aint1[1] - d4 > l) {
/*  73 */             aint3[1] = l;
/*     */           } else {
/*  75 */             aint3[1] = (int)(aint1[1] - d4);
/*     */           } 
/*     */           
/*  78 */           if (a(aint3, aint1) == -1) {
/*  79 */             aint[k][0] = k1;
/*  80 */             aint[k][1] = j;
/*  81 */             aint[k][2] = l1;
/*  82 */             aint[k][3] = aint3[1];
/*  83 */             k++;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  88 */       j--;
/*  89 */       i1--;
/*     */     } 
/*     */ 
/*     */     
/*  93 */     this.o = new int[k][4];
/*  94 */     System.arraycopy(aint, 0, this.o, 0, k);
/*     */   }
/*     */   
/*     */   void a(int i, int j, int k, float f, byte b0, Block block) {
/*  98 */     int l = (int)(f + 0.618D);
/*  99 */     byte b1 = a[b0];
/* 100 */     byte b2 = a[b0 + 3];
/* 101 */     int[] aint = { i, j, k };
/* 102 */     int[] aint1 = { 0, 0, 0 };
/* 103 */     int i1 = -l;
/* 104 */     int j1 = -l;
/*     */     
/* 106 */     for (aint1[b0] = aint[b0]; i1 <= l; i1++) {
/* 107 */       aint1[b1] = aint[b1] + i1;
/* 108 */       j1 = -l;
/*     */       
/* 110 */       while (j1 <= l) {
/* 111 */         double d0 = Math.pow(Math.abs(i1) + 0.5D, 2.0D) + Math.pow(Math.abs(j1) + 0.5D, 2.0D);
/*     */         
/* 113 */         if (d0 > (f * f)) {
/* 114 */           j1++; continue;
/*     */         } 
/* 116 */         aint1[b2] = aint[b2] + j1;
/* 117 */         Block block1 = this.world.getType(aint1[0], aint1[1], aint1[2]);
/*     */         
/* 119 */         if (block1.getMaterial() != Material.AIR && block1.getMaterial() != Material.LEAVES) {
/* 120 */           j1++; continue;
/*     */         } 
/* 122 */         setTypeAndData(this.world, aint1[0], aint1[1], aint1[2], block, 0);
/* 123 */         j1++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   float a(int i) {
/*     */     float f2;
/* 131 */     if (i < this.e * 0.3D) {
/* 132 */       return -1.618F;
/*     */     }
/* 134 */     float f = this.e / 2.0F;
/* 135 */     float f1 = this.e / 2.0F - i;
/*     */ 
/*     */     
/* 138 */     if (f1 == 0.0F) {
/* 139 */       f2 = f;
/* 140 */     } else if (Math.abs(f1) >= f) {
/* 141 */       f2 = 0.0F;
/*     */     } else {
/* 143 */       f2 = (float)Math.sqrt(Math.pow(Math.abs(f), 2.0D) - Math.pow(Math.abs(f1), 2.0D));
/*     */     } 
/*     */     
/* 146 */     f2 *= 0.5F;
/* 147 */     return f2;
/*     */   }
/*     */ 
/*     */   
/*     */   float b(int i) {
/* 152 */     return (i >= 0 && i < this.n) ? ((i != 0 && i != this.n - 1) ? 3.0F : 2.0F) : -1.0F;
/*     */   }
/*     */   
/*     */   void a(int i, int j, int k) {
/* 156 */     int l = j;
/*     */     
/* 158 */     for (int i1 = j + this.n; l < i1; l++) {
/* 159 */       float f = b(l - j);
/*     */       
/* 161 */       a(i, l, k, f, (byte)1, Blocks.LEAVES);
/*     */     } 
/*     */   }
/*     */   
/*     */   void a(int[] aint, int[] aint1, Block block) {
/* 166 */     int[] aint2 = { 0, 0, 0 };
/* 167 */     byte b0 = 0;
/*     */     
/*     */     byte b1;
/*     */     
/* 171 */     for (b1 = 0; b0 < 3; b0 = (byte)(b0 + 1)) {
/* 172 */       aint2[b0] = aint1[b0] - aint[b0];
/* 173 */       if (Math.abs(aint2[b0]) > Math.abs(aint2[b1])) {
/* 174 */         b1 = b0;
/*     */       }
/*     */     } 
/*     */     
/* 178 */     if (aint2[b1] != 0) {
/* 179 */       byte b4, b2 = a[b1];
/* 180 */       byte b3 = a[b1 + 3];
/*     */ 
/*     */       
/* 183 */       if (aint2[b1] > 0) {
/* 184 */         b4 = 1;
/*     */       } else {
/* 186 */         b4 = -1;
/*     */       } 
/*     */       
/* 189 */       double d0 = aint2[b2] / aint2[b1];
/* 190 */       double d1 = aint2[b3] / aint2[b1];
/* 191 */       int[] aint3 = { 0, 0, 0 };
/* 192 */       int i = 0;
/*     */       
/* 194 */       for (int j = aint2[b1] + b4; i != j; i += b4) {
/* 195 */         aint3[b1] = MathHelper.floor((aint[b1] + i) + 0.5D);
/* 196 */         aint3[b2] = MathHelper.floor(aint[b2] + i * d0 + 0.5D);
/* 197 */         aint3[b3] = MathHelper.floor(aint[b3] + i * d1 + 0.5D);
/* 198 */         byte b5 = 0;
/* 199 */         int k = Math.abs(aint3[0] - aint[0]);
/* 200 */         int l = Math.abs(aint3[2] - aint[2]);
/* 201 */         int i1 = Math.max(k, l);
/*     */         
/* 203 */         if (i1 > 0) {
/* 204 */           if (k == i1) {
/* 205 */             b5 = 4;
/* 206 */           } else if (l == i1) {
/* 207 */             b5 = 8;
/*     */           } 
/*     */         }
/*     */         
/* 211 */         setTypeAndData(this.world, aint3[0], aint3[1], aint3[2], block, b5);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   void b() {
/* 217 */     int i = 0;
/*     */     
/* 219 */     for (int j = this.o.length; i < j; i++) {
/* 220 */       int k = this.o[i][0];
/* 221 */       int l = this.o[i][1];
/* 222 */       int i1 = this.o[i][2];
/*     */       
/* 224 */       a(k, l, i1);
/*     */     } 
/*     */   }
/*     */   
/*     */   boolean c(int i) {
/* 229 */     return (i >= this.e * 0.2D);
/*     */   }
/*     */   
/*     */   void c() {
/* 233 */     int i = this.d[0];
/* 234 */     int j = this.d[1];
/* 235 */     int k = this.d[1] + this.f;
/* 236 */     int l = this.d[2];
/* 237 */     int[] aint = { i, j, l };
/* 238 */     int[] aint1 = { i, k, l };
/*     */     
/* 240 */     a(aint, aint1, Blocks.LOG);
/* 241 */     if (this.l == 2) {
/* 242 */       aint[0] = aint[0] + 1;
/* 243 */       aint1[0] = aint1[0] + 1;
/* 244 */       a(aint, aint1, Blocks.LOG);
/* 245 */       aint[2] = aint[2] + 1;
/* 246 */       aint1[2] = aint1[2] + 1;
/* 247 */       a(aint, aint1, Blocks.LOG);
/* 248 */       aint[0] = aint[0] + -1;
/* 249 */       aint1[0] = aint1[0] + -1;
/* 250 */       a(aint, aint1, Blocks.LOG);
/*     */     } 
/*     */   }
/*     */   
/*     */   void d() {
/* 255 */     int i = 0;
/* 256 */     int j = this.o.length;
/*     */     
/* 258 */     for (int[] aint = { this.d[0], this.d[1], this.d[2] }; i < j; i++) {
/* 259 */       int[] aint1 = this.o[i];
/* 260 */       int[] aint2 = { aint1[0], aint1[1], aint1[2] };
/*     */       
/* 262 */       aint[1] = aint1[3];
/* 263 */       int k = aint[1] - this.d[1];
/*     */       
/* 265 */       if (c(k))
/* 266 */         a(aint, aint2, Blocks.LOG); 
/*     */     } 
/*     */   }
/*     */   
/*     */   int a(int[] aint, int[] aint1) {
/*     */     byte b4;
/* 272 */     int[] aint2 = { 0, 0, 0 };
/* 273 */     byte b0 = 0;
/*     */     
/*     */     byte b1;
/*     */     
/* 277 */     for (b1 = 0; b0 < 3; b0 = (byte)(b0 + 1)) {
/* 278 */       aint2[b0] = aint1[b0] - aint[b0];
/* 279 */       if (Math.abs(aint2[b0]) > Math.abs(aint2[b1])) {
/* 280 */         b1 = b0;
/*     */       }
/*     */     } 
/*     */     
/* 284 */     if (aint2[b1] == 0) {
/* 285 */       return -1;
/*     */     }
/* 287 */     byte b2 = a[b1];
/* 288 */     byte b3 = a[b1 + 3];
/*     */ 
/*     */     
/* 291 */     if (aint2[b1] > 0) {
/* 292 */       b4 = 1;
/*     */     } else {
/* 294 */       b4 = -1;
/*     */     } 
/*     */     
/* 297 */     double d0 = aint2[b2] / aint2[b1];
/* 298 */     double d1 = aint2[b3] / aint2[b1];
/* 299 */     int[] aint3 = { 0, 0, 0 };
/* 300 */     int i = 0;
/*     */     
/*     */     int j;
/*     */     
/* 304 */     for (j = aint2[b1] + b4; i != j; i += b4) {
/* 305 */       aint3[b1] = aint[b1] + i;
/* 306 */       aint3[b2] = MathHelper.floor(aint[b2] + i * d0);
/* 307 */       aint3[b3] = MathHelper.floor(aint[b3] + i * d1);
/* 308 */       Block block = this.world.getType(aint3[0], aint3[1], aint3[2]);
/*     */       
/* 310 */       if (!a(block) || aint[1] >= 256) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */     
/* 315 */     return (i == j) ? -1 : Math.abs(i);
/*     */   }
/*     */ 
/*     */   
/*     */   boolean e() {
/* 320 */     int[] aint = { this.d[0], this.d[1], this.d[2] };
/* 321 */     int[] aint1 = { this.d[0], this.d[1] + this.e - 1, this.d[2] };
/* 322 */     Block block = this.world.getType(this.d[0], this.d[1] - 1, this.d[2]);
/*     */     
/* 324 */     if (block != Blocks.DIRT && block != Blocks.GRASS && block != Blocks.SOIL) {
/* 325 */       return false;
/*     */     }
/* 327 */     int i = a(aint, aint1);
/*     */     
/* 329 */     if (i == -1)
/* 330 */       return true; 
/* 331 */     if (i < 6) {
/* 332 */       return false;
/*     */     }
/* 334 */     this.e = i;
/* 335 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(double d0, double d1, double d2) {
/* 341 */     this.m = (int)(d0 * 12.0D);
/* 342 */     if (d0 > 0.5D) {
/* 343 */       this.n = 5;
/*     */     }
/*     */     
/* 346 */     this.j = d1;
/* 347 */     this.k = d2;
/*     */   }
/*     */   
/*     */   public boolean generate(World world, Random random, int i, int j, int k) {
/* 351 */     this.world = world;
/* 352 */     long l = random.nextLong();
/*     */     
/* 354 */     this.b.setSeed(l);
/* 355 */     this.d[0] = i;
/* 356 */     this.d[1] = j;
/* 357 */     this.d[2] = k;
/* 358 */     if (this.e == 0) {
/* 359 */       this.e = 5 + this.b.nextInt(this.m);
/*     */     }
/*     */     
/* 362 */     if (!e()) {
/* 363 */       return false;
/*     */     }
/* 365 */     a();
/* 366 */     b();
/* 367 */     c();
/* 368 */     d();
/* 369 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\WorldGenBigTree.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
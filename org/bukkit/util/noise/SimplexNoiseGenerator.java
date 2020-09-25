/*     */ package org.bukkit.util.noise;
/*     */ 
/*     */ import java.util.Random;
/*     */ import org.bukkit.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SimplexNoiseGenerator
/*     */   extends PerlinNoiseGenerator
/*     */ {
/*  15 */   protected static final double SQRT_3 = Math.sqrt(3.0D);
/*  16 */   protected static final double SQRT_5 = Math.sqrt(5.0D);
/*  17 */   protected static final double F2 = 0.5D * (SQRT_3 - 1.0D);
/*  18 */   protected static final double G2 = (3.0D - SQRT_3) / 6.0D;
/*  19 */   protected static final double G22 = G2 * 2.0D - 1.0D;
/*     */   protected static final double F3 = 0.3333333333333333D;
/*     */   protected static final double G3 = 0.16666666666666666D;
/*  22 */   protected static final double F4 = (SQRT_5 - 1.0D) / 4.0D;
/*  23 */   protected static final double G4 = (5.0D - SQRT_5) / 20.0D;
/*  24 */   protected static final double G42 = G4 * 2.0D;
/*  25 */   protected static final double G43 = G4 * 3.0D;
/*  26 */   protected static final double G44 = G4 * 4.0D - 1.0D;
/*  27 */   protected static final int[][] grad4 = new int[][] { { 0, 1, 1, 1 }, { 0, 1, 1, -1 }, { 0, 1, -1, 1 }, { 0, 1, -1, -1 }, { 0, -1, 1, 1 }, { 0, -1, 1, -1 }, { 0, -1, -1, 1 }, { 0, -1, -1, -1 }, { 1, 0, 1, 1 }, { 1, 0, 1, -1 }, { 1, 0, -1, 1 }, { 1, 0, -1, -1 }, { -1, 0, 1, 1 }, { -1, 0, 1, -1 }, { -1, 0, -1, 1 }, { -1, 0, -1, -1 }, { 1, 1, 0, 1 }, { 1, 1, 0, -1 }, { 1, -1, 0, 1 }, { 1, -1, 0, -1 }, { -1, 1, 0, 1 }, { -1, 1, 0, -1 }, { -1, -1, 0, 1 }, { -1, -1, 0, -1 }, { 1, 1, 1, 0 }, { 1, 1, -1, 0 }, { 1, -1, 1, 0 }, { 1, -1, -1, 0 }, { -1, 1, 1, 0 }, { -1, 1, -1, 0 }, { -1, -1, 1, 0 }, { -1, -1, -1, 0 } };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  35 */   protected static final int[][] simplex = new int[][] { { 0, 1, 2, 3 }, { 0, 1, 3, 2 }, { 0, 0, 0, 0 }, { 0, 2, 3, 1 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 1, 2, 3, 0 }, { 0, 2, 1, 3 }, { 0, 0, 0, 0 }, { 0, 3, 1, 2 }, { 0, 3, 2, 1 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 1, 3, 2, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 1, 2, 0, 3 }, { 0, 0, 0, 0 }, { 1, 3, 0, 2 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 2, 3, 0, 1 }, { 2, 3, 1, 0 }, { 1, 0, 2, 3 }, { 1, 0, 3, 2 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 2, 0, 3, 1 }, { 0, 0, 0, 0 }, { 2, 1, 3, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 2, 0, 1, 3 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 3, 0, 1, 2 }, { 3, 0, 2, 1 }, { 0, 0, 0, 0 }, { 3, 1, 2, 0 }, { 2, 1, 0, 3 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 3, 1, 0, 2 }, { 0, 0, 0, 0 }, { 3, 2, 0, 1 }, { 3, 2, 1, 0 } };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static double offsetW;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  45 */   private static final SimplexNoiseGenerator instance = new SimplexNoiseGenerator();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SimplexNoiseGenerator() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimplexNoiseGenerator(World world) {
/*  57 */     this(new Random(world.getSeed()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimplexNoiseGenerator(long seed) {
/*  66 */     this(new Random(seed));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimplexNoiseGenerator(Random rand) {
/*  75 */     super(rand);
/*  76 */     offsetW = rand.nextDouble() * 256.0D;
/*     */   }
/*     */   
/*     */   protected static double dot(int[] g, double x, double y) {
/*  80 */     return g[0] * x + g[1] * y;
/*     */   }
/*     */   
/*     */   protected static double dot(int[] g, double x, double y, double z) {
/*  84 */     return g[0] * x + g[1] * y + g[2] * z;
/*     */   }
/*     */   
/*     */   protected static double dot(int[] g, double x, double y, double z, double w) {
/*  88 */     return g[0] * x + g[1] * y + g[2] * z + g[3] * w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double getNoise(double xin) {
/*  99 */     return instance.noise(xin);
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
/*     */   public static double getNoise(double xin, double yin) {
/* 111 */     return instance.noise(xin, yin);
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
/*     */   public static double getNoise(double xin, double yin, double zin) {
/* 124 */     return instance.noise(xin, yin, zin);
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
/*     */   public static double getNoise(double x, double y, double z, double w) {
/* 138 */     return instance.noise(x, y, z, w);
/*     */   }
/*     */   public double noise(double xin, double yin, double zin) {
/*     */     int i1, j1, k1, i2, j2, k2;
/*     */     double n0, n1, n2, n3;
/* 143 */     xin += this.offsetX;
/* 144 */     yin += this.offsetY;
/* 145 */     zin += this.offsetZ;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 150 */     double s = (xin + yin + zin) * 0.3333333333333333D;
/* 151 */     int i = floor(xin + s);
/* 152 */     int j = floor(yin + s);
/* 153 */     int k = floor(zin + s);
/* 154 */     double t = (i + j + k) * 0.16666666666666666D;
/* 155 */     double X0 = i - t;
/* 156 */     double Y0 = j - t;
/* 157 */     double Z0 = k - t;
/* 158 */     double x0 = xin - X0;
/* 159 */     double y0 = yin - Y0;
/* 160 */     double z0 = zin - Z0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 167 */     if (x0 >= y0) {
/* 168 */       if (y0 >= z0) {
/* 169 */         i1 = 1;
/* 170 */         j1 = 0;
/* 171 */         k1 = 0;
/* 172 */         i2 = 1;
/* 173 */         j2 = 1;
/* 174 */         k2 = 0;
/*     */       }
/* 176 */       else if (x0 >= z0) {
/* 177 */         i1 = 1;
/* 178 */         j1 = 0;
/* 179 */         k1 = 0;
/* 180 */         i2 = 1;
/* 181 */         j2 = 0;
/* 182 */         k2 = 1;
/*     */       } else {
/*     */         
/* 185 */         i1 = 0;
/* 186 */         j1 = 0;
/* 187 */         k1 = 1;
/* 188 */         i2 = 1;
/* 189 */         j2 = 0;
/* 190 */         k2 = 1;
/*     */       }
/*     */     
/* 193 */     } else if (y0 < z0) {
/* 194 */       i1 = 0;
/* 195 */       j1 = 0;
/* 196 */       k1 = 1;
/* 197 */       i2 = 0;
/* 198 */       j2 = 1;
/* 199 */       k2 = 1;
/*     */     }
/* 201 */     else if (x0 < z0) {
/* 202 */       i1 = 0;
/* 203 */       j1 = 1;
/* 204 */       k1 = 0;
/* 205 */       i2 = 0;
/* 206 */       j2 = 1;
/* 207 */       k2 = 1;
/*     */     } else {
/*     */       
/* 210 */       i1 = 0;
/* 211 */       j1 = 1;
/* 212 */       k1 = 0;
/* 213 */       i2 = 1;
/* 214 */       j2 = 1;
/* 215 */       k2 = 0;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 223 */     double x1 = x0 - i1 + 0.16666666666666666D;
/* 224 */     double y1 = y0 - j1 + 0.16666666666666666D;
/* 225 */     double z1 = z0 - k1 + 0.16666666666666666D;
/* 226 */     double x2 = x0 - i2 + 0.3333333333333333D;
/* 227 */     double y2 = y0 - j2 + 0.3333333333333333D;
/* 228 */     double z2 = z0 - k2 + 0.3333333333333333D;
/* 229 */     double x3 = x0 - 1.0D + 0.5D;
/* 230 */     double y3 = y0 - 1.0D + 0.5D;
/* 231 */     double z3 = z0 - 1.0D + 0.5D;
/*     */ 
/*     */     
/* 234 */     int ii = i & 0xFF;
/* 235 */     int jj = j & 0xFF;
/* 236 */     int kk = k & 0xFF;
/* 237 */     int gi0 = this.perm[ii + this.perm[jj + this.perm[kk]]] % 12;
/* 238 */     int gi1 = this.perm[ii + i1 + this.perm[jj + j1 + this.perm[kk + k1]]] % 12;
/* 239 */     int gi2 = this.perm[ii + i2 + this.perm[jj + j2 + this.perm[kk + k2]]] % 12;
/* 240 */     int gi3 = this.perm[ii + 1 + this.perm[jj + 1 + this.perm[kk + 1]]] % 12;
/*     */ 
/*     */     
/* 243 */     double t0 = 0.6D - x0 * x0 - y0 * y0 - z0 * z0;
/* 244 */     if (t0 < 0.0D) {
/* 245 */       n0 = 0.0D;
/*     */     } else {
/* 247 */       t0 *= t0;
/* 248 */       n0 = t0 * t0 * dot(grad3[gi0], x0, y0, z0);
/*     */     } 
/*     */     
/* 251 */     double t1 = 0.6D - x1 * x1 - y1 * y1 - z1 * z1;
/* 252 */     if (t1 < 0.0D) {
/* 253 */       n1 = 0.0D;
/*     */     } else {
/* 255 */       t1 *= t1;
/* 256 */       n1 = t1 * t1 * dot(grad3[gi1], x1, y1, z1);
/*     */     } 
/*     */     
/* 259 */     double t2 = 0.6D - x2 * x2 - y2 * y2 - z2 * z2;
/* 260 */     if (t2 < 0.0D) {
/* 261 */       n2 = 0.0D;
/*     */     } else {
/* 263 */       t2 *= t2;
/* 264 */       n2 = t2 * t2 * dot(grad3[gi2], x2, y2, z2);
/*     */     } 
/*     */     
/* 267 */     double t3 = 0.6D - x3 * x3 - y3 * y3 - z3 * z3;
/* 268 */     if (t3 < 0.0D) {
/* 269 */       n3 = 0.0D;
/*     */     } else {
/* 271 */       t3 *= t3;
/* 272 */       n3 = t3 * t3 * dot(grad3[gi3], x3, y3, z3);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 277 */     return 32.0D * (n0 + n1 + n2 + n3);
/*     */   }
/*     */   public double noise(double xin, double yin) {
/*     */     int i1, j1;
/*     */     double n0, n1, n2;
/* 282 */     xin += this.offsetX;
/* 283 */     yin += this.offsetY;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 288 */     double s = (xin + yin) * F2;
/* 289 */     int i = floor(xin + s);
/* 290 */     int j = floor(yin + s);
/* 291 */     double t = (i + j) * G2;
/* 292 */     double X0 = i - t;
/* 293 */     double Y0 = j - t;
/* 294 */     double x0 = xin - X0;
/* 295 */     double y0 = yin - Y0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 301 */     if (x0 > y0) {
/* 302 */       i1 = 1;
/* 303 */       j1 = 0;
/*     */     } else {
/*     */       
/* 306 */       i1 = 0;
/* 307 */       j1 = 1;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 314 */     double x1 = x0 - i1 + G2;
/* 315 */     double y1 = y0 - j1 + G2;
/* 316 */     double x2 = x0 + G22;
/* 317 */     double y2 = y0 + G22;
/*     */ 
/*     */     
/* 320 */     int ii = i & 0xFF;
/* 321 */     int jj = j & 0xFF;
/* 322 */     int gi0 = this.perm[ii + this.perm[jj]] % 12;
/* 323 */     int gi1 = this.perm[ii + i1 + this.perm[jj + j1]] % 12;
/* 324 */     int gi2 = this.perm[ii + 1 + this.perm[jj + 1]] % 12;
/*     */ 
/*     */     
/* 327 */     double t0 = 0.5D - x0 * x0 - y0 * y0;
/* 328 */     if (t0 < 0.0D) {
/* 329 */       n0 = 0.0D;
/*     */     } else {
/* 331 */       t0 *= t0;
/* 332 */       n0 = t0 * t0 * dot(grad3[gi0], x0, y0);
/*     */     } 
/*     */     
/* 335 */     double t1 = 0.5D - x1 * x1 - y1 * y1;
/* 336 */     if (t1 < 0.0D) {
/* 337 */       n1 = 0.0D;
/*     */     } else {
/* 339 */       t1 *= t1;
/* 340 */       n1 = t1 * t1 * dot(grad3[gi1], x1, y1);
/*     */     } 
/*     */     
/* 343 */     double t2 = 0.5D - x2 * x2 - y2 * y2;
/* 344 */     if (t2 < 0.0D) {
/* 345 */       n2 = 0.0D;
/*     */     } else {
/* 347 */       t2 *= t2;
/* 348 */       n2 = t2 * t2 * dot(grad3[gi2], x2, y2);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 353 */     return 70.0D * (n0 + n1 + n2);
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
/*     */   public double noise(double x, double y, double z, double w) {
/*     */     double n0, n1, n2, n3, n4;
/* 367 */     x += this.offsetX;
/* 368 */     y += this.offsetY;
/* 369 */     z += this.offsetZ;
/* 370 */     w += offsetW;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 375 */     double s = (x + y + z + w) * F4;
/* 376 */     int i = floor(x + s);
/* 377 */     int j = floor(y + s);
/* 378 */     int k = floor(z + s);
/* 379 */     int l = floor(w + s);
/*     */     
/* 381 */     double t = (i + j + k + l) * G4;
/* 382 */     double X0 = i - t;
/* 383 */     double Y0 = j - t;
/* 384 */     double Z0 = k - t;
/* 385 */     double W0 = l - t;
/* 386 */     double x0 = x - X0;
/* 387 */     double y0 = y - Y0;
/* 388 */     double z0 = z - Z0;
/* 389 */     double w0 = w - W0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 399 */     int c1 = (x0 > y0) ? 32 : 0;
/* 400 */     int c2 = (x0 > z0) ? 16 : 0;
/* 401 */     int c3 = (y0 > z0) ? 8 : 0;
/* 402 */     int c4 = (x0 > w0) ? 4 : 0;
/* 403 */     int c5 = (y0 > w0) ? 2 : 0;
/* 404 */     int c6 = (z0 > w0) ? 1 : 0;
/* 405 */     int c = c1 + c2 + c3 + c4 + c5 + c6;
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
/* 416 */     int i1 = (simplex[c][0] >= 3) ? 1 : 0;
/* 417 */     int j1 = (simplex[c][1] >= 3) ? 1 : 0;
/* 418 */     int k1 = (simplex[c][2] >= 3) ? 1 : 0;
/* 419 */     int l1 = (simplex[c][3] >= 3) ? 1 : 0;
/*     */ 
/*     */     
/* 422 */     int i2 = (simplex[c][0] >= 2) ? 1 : 0;
/* 423 */     int j2 = (simplex[c][1] >= 2) ? 1 : 0;
/* 424 */     int k2 = (simplex[c][2] >= 2) ? 1 : 0;
/* 425 */     int l2 = (simplex[c][3] >= 2) ? 1 : 0;
/*     */ 
/*     */     
/* 428 */     int i3 = (simplex[c][0] >= 1) ? 1 : 0;
/* 429 */     int j3 = (simplex[c][1] >= 1) ? 1 : 0;
/* 430 */     int k3 = (simplex[c][2] >= 1) ? 1 : 0;
/* 431 */     int l3 = (simplex[c][3] >= 1) ? 1 : 0;
/*     */ 
/*     */ 
/*     */     
/* 435 */     double x1 = x0 - i1 + G4;
/* 436 */     double y1 = y0 - j1 + G4;
/* 437 */     double z1 = z0 - k1 + G4;
/* 438 */     double w1 = w0 - l1 + G4;
/*     */     
/* 440 */     double x2 = x0 - i2 + G42;
/* 441 */     double y2 = y0 - j2 + G42;
/* 442 */     double z2 = z0 - k2 + G42;
/* 443 */     double w2 = w0 - l2 + G42;
/*     */     
/* 445 */     double x3 = x0 - i3 + G43;
/* 446 */     double y3 = y0 - j3 + G43;
/* 447 */     double z3 = z0 - k3 + G43;
/* 448 */     double w3 = w0 - l3 + G43;
/*     */     
/* 450 */     double x4 = x0 + G44;
/* 451 */     double y4 = y0 + G44;
/* 452 */     double z4 = z0 + G44;
/* 453 */     double w4 = w0 + G44;
/*     */ 
/*     */     
/* 456 */     int ii = i & 0xFF;
/* 457 */     int jj = j & 0xFF;
/* 458 */     int kk = k & 0xFF;
/* 459 */     int ll = l & 0xFF;
/*     */     
/* 461 */     int gi0 = this.perm[ii + this.perm[jj + this.perm[kk + this.perm[ll]]]] % 32;
/* 462 */     int gi1 = this.perm[ii + i1 + this.perm[jj + j1 + this.perm[kk + k1 + this.perm[ll + l1]]]] % 32;
/* 463 */     int gi2 = this.perm[ii + i2 + this.perm[jj + j2 + this.perm[kk + k2 + this.perm[ll + l2]]]] % 32;
/* 464 */     int gi3 = this.perm[ii + i3 + this.perm[jj + j3 + this.perm[kk + k3 + this.perm[ll + l3]]]] % 32;
/* 465 */     int gi4 = this.perm[ii + 1 + this.perm[jj + 1 + this.perm[kk + 1 + this.perm[ll + 1]]]] % 32;
/*     */ 
/*     */     
/* 468 */     double t0 = 0.6D - x0 * x0 - y0 * y0 - z0 * z0 - w0 * w0;
/* 469 */     if (t0 < 0.0D) {
/* 470 */       n0 = 0.0D;
/*     */     } else {
/* 472 */       t0 *= t0;
/* 473 */       n0 = t0 * t0 * dot(grad4[gi0], x0, y0, z0, w0);
/*     */     } 
/*     */     
/* 476 */     double t1 = 0.6D - x1 * x1 - y1 * y1 - z1 * z1 - w1 * w1;
/* 477 */     if (t1 < 0.0D) {
/* 478 */       n1 = 0.0D;
/*     */     } else {
/* 480 */       t1 *= t1;
/* 481 */       n1 = t1 * t1 * dot(grad4[gi1], x1, y1, z1, w1);
/*     */     } 
/*     */     
/* 484 */     double t2 = 0.6D - x2 * x2 - y2 * y2 - z2 * z2 - w2 * w2;
/* 485 */     if (t2 < 0.0D) {
/* 486 */       n2 = 0.0D;
/*     */     } else {
/* 488 */       t2 *= t2;
/* 489 */       n2 = t2 * t2 * dot(grad4[gi2], x2, y2, z2, w2);
/*     */     } 
/*     */     
/* 492 */     double t3 = 0.6D - x3 * x3 - y3 * y3 - z3 * z3 - w3 * w3;
/* 493 */     if (t3 < 0.0D) {
/* 494 */       n3 = 0.0D;
/*     */     } else {
/* 496 */       t3 *= t3;
/* 497 */       n3 = t3 * t3 * dot(grad4[gi3], x3, y3, z3, w3);
/*     */     } 
/*     */     
/* 500 */     double t4 = 0.6D - x4 * x4 - y4 * y4 - z4 * z4 - w4 * w4;
/* 501 */     if (t4 < 0.0D) {
/* 502 */       n4 = 0.0D;
/*     */     } else {
/* 504 */       t4 *= t4;
/* 505 */       n4 = t4 * t4 * dot(grad4[gi4], x4, y4, z4, w4);
/*     */     } 
/*     */ 
/*     */     
/* 509 */     return 27.0D * (n0 + n1 + n2 + n3 + n4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SimplexNoiseGenerator getInstance() {
/* 518 */     return instance;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukki\\util\noise\SimplexNoiseGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
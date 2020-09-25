/*     */ package net.minecraft.server.v1_7_R4;
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
/*     */ public class Navigation
/*     */ {
/*     */   private EntityInsentient a;
/*     */   private World b;
/*     */   private PathEntity c;
/*     */   private double d;
/*     */   private AttributeInstance e;
/*     */   private boolean f;
/*     */   private int g;
/*     */   private int h;
/*  26 */   private Vec3D i = Vec3D.a(0.0D, 0.0D, 0.0D);
/*     */   
/*     */   private boolean j = true;
/*     */   private boolean k;
/*     */   private boolean l;
/*     */   private boolean m;
/*     */   
/*     */   public Navigation(EntityInsentient paramEntityInsentient, World paramWorld) {
/*  34 */     this.a = paramEntityInsentient;
/*  35 */     this.b = paramWorld;
/*  36 */     this.e = paramEntityInsentient.getAttributeInstance(GenericAttributes.b);
/*     */   }
/*     */   
/*     */   public void a(boolean paramBoolean) {
/*  40 */     this.l = paramBoolean;
/*     */   }
/*     */   
/*     */   public boolean a() {
/*  44 */     return this.l;
/*     */   }
/*     */   
/*     */   public void b(boolean paramBoolean) {
/*  48 */     this.k = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void c(boolean paramBoolean) {
/*  56 */     this.j = paramBoolean;
/*     */   }
/*     */   
/*     */   public boolean c() {
/*  60 */     return this.k;
/*     */   }
/*     */   
/*     */   public void d(boolean paramBoolean) {
/*  64 */     this.f = paramBoolean;
/*     */   }
/*     */   
/*     */   public void a(double paramDouble) {
/*  68 */     this.d = paramDouble;
/*     */   }
/*     */   
/*     */   public void e(boolean paramBoolean) {
/*  72 */     this.m = paramBoolean;
/*     */   }
/*     */   
/*     */   public float d() {
/*  76 */     return (float)this.e.getValue();
/*     */   }
/*     */   
/*     */   public PathEntity a(double paramDouble1, double paramDouble2, double paramDouble3) {
/*  80 */     if (!l()) return null; 
/*  81 */     return this.b.a(this.a, MathHelper.floor(paramDouble1), (int)paramDouble2, MathHelper.floor(paramDouble3), d(), this.j, this.k, this.l, this.m);
/*     */   }
/*     */   
/*     */   public boolean a(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/*  85 */     PathEntity pathEntity = a(MathHelper.floor(paramDouble1), (int)paramDouble2, MathHelper.floor(paramDouble3));
/*  86 */     return a(pathEntity, paramDouble4);
/*     */   }
/*     */   
/*     */   public PathEntity a(Entity paramEntity) {
/*  90 */     if (!l()) return null; 
/*  91 */     return this.b.findPath(this.a, paramEntity, d(), this.j, this.k, this.l, this.m);
/*     */   }
/*     */   
/*     */   public boolean a(Entity paramEntity, double paramDouble) {
/*  95 */     PathEntity pathEntity = a(paramEntity);
/*  96 */     if (pathEntity != null) return a(pathEntity, paramDouble); 
/*  97 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean a(PathEntity paramPathEntity, double paramDouble) {
/* 102 */     if (paramPathEntity == null) {
/* 103 */       this.c = null;
/* 104 */       return false;
/*     */     } 
/* 106 */     if (!paramPathEntity.a(this.c)) this.c = paramPathEntity; 
/* 107 */     if (this.f) n(); 
/* 108 */     if (this.c.d() == 0) return false;
/*     */     
/* 110 */     this.d = paramDouble;
/* 111 */     Vec3D vec3D = j();
/* 112 */     this.h = this.g;
/* 113 */     this.i.a = vec3D.a;
/* 114 */     this.i.b = vec3D.b;
/* 115 */     this.i.c = vec3D.c;
/* 116 */     return true;
/*     */   }
/*     */   
/*     */   public PathEntity e() {
/* 120 */     return this.c;
/*     */   }
/*     */   
/*     */   public void f() {
/* 124 */     this.g++;
/* 125 */     if (g())
/*     */       return; 
/* 127 */     if (l()) i();
/*     */     
/* 129 */     if (g())
/* 130 */       return;  Vec3D vec3D = this.c.a(this.a);
/* 131 */     if (vec3D == null)
/*     */       return; 
/* 133 */     this.a.getControllerMove().a(vec3D.a, vec3D.b, vec3D.c, this.d);
/*     */   }
/*     */   
/*     */   private void i() {
/* 137 */     Vec3D vec3D = j();
/*     */ 
/*     */     
/* 140 */     int i = this.c.d();
/* 141 */     for (int j = this.c.e(); j < this.c.d(); j++) {
/* 142 */       if ((this.c.a(j)).b != (int)vec3D.b) {
/* 143 */         i = j;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*     */     
/* 149 */     float f = this.a.width * this.a.width; int k;
/* 150 */     for (k = this.c.e(); k < i; k++) {
/* 151 */       if (vec3D.distanceSquared(this.c.a(this.a, k)) < f) {
/* 152 */         this.c.c(k + 1);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 157 */     k = MathHelper.f(this.a.width);
/* 158 */     int m = (int)this.a.length + 1;
/* 159 */     int n = k;
/* 160 */     for (int i1 = i - 1; i1 >= this.c.e(); i1--) {
/* 161 */       if (a(vec3D, this.c.a(this.a, i1), k, m, n)) {
/* 162 */         this.c.c(i1);
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*     */     
/* 168 */     if (this.g - this.h > 100) {
/* 169 */       if (vec3D.distanceSquared(this.i) < 2.25D) h(); 
/* 170 */       this.h = this.g;
/* 171 */       this.i.a = vec3D.a;
/* 172 */       this.i.b = vec3D.b;
/* 173 */       this.i.c = vec3D.c;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean g() {
/* 178 */     return (this.c == null || this.c.b());
/*     */   }
/*     */   
/*     */   public void h() {
/* 182 */     this.c = null;
/*     */   }
/*     */   
/*     */   private Vec3D j() {
/* 186 */     return Vec3D.a(this.a.locX, k(), this.a.locZ);
/*     */   }
/*     */   
/*     */   private int k() {
/* 190 */     if (!this.a.M() || !this.m) return (int)(this.a.boundingBox.b + 0.5D);
/*     */     
/* 192 */     int i = (int)this.a.boundingBox.b;
/* 193 */     Block block = this.b.getType(MathHelper.floor(this.a.locX), i, MathHelper.floor(this.a.locZ));
/* 194 */     byte b = 0;
/* 195 */     while (block == Blocks.WATER || block == Blocks.STATIONARY_WATER) {
/* 196 */       i++;
/* 197 */       block = this.b.getType(MathHelper.floor(this.a.locX), i, MathHelper.floor(this.a.locZ));
/* 198 */       if (++b > 16) return (int)this.a.boundingBox.b; 
/*     */     } 
/* 200 */     return i;
/*     */   }
/*     */   
/*     */   private boolean l() {
/* 204 */     return (this.a.onGround || (this.m && m()) || (this.a.am() && this.a instanceof EntityZombie && this.a.vehicle instanceof EntityChicken));
/*     */   }
/*     */   
/*     */   private boolean m() {
/* 208 */     return (this.a.M() || this.a.P());
/*     */   }
/*     */   
/*     */   private void n() {
/* 212 */     if (this.b.i(MathHelper.floor(this.a.locX), (int)(this.a.boundingBox.b + 0.5D), MathHelper.floor(this.a.locZ)))
/*     */       return; 
/* 214 */     for (byte b = 0; b < this.c.d(); b++) {
/* 215 */       PathPoint pathPoint = this.c.a(b);
/* 216 */       if (this.b.i(pathPoint.a, pathPoint.b, pathPoint.c)) {
/* 217 */         this.c.b(b - 1);
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean a(Vec3D paramVec3D1, Vec3D paramVec3D2, int paramInt1, int paramInt2, int paramInt3) {
/* 225 */     int i = MathHelper.floor(paramVec3D1.a);
/* 226 */     int j = MathHelper.floor(paramVec3D1.c);
/*     */     
/* 228 */     double d1 = paramVec3D2.a - paramVec3D1.a;
/* 229 */     double d2 = paramVec3D2.c - paramVec3D1.c;
/* 230 */     double d3 = d1 * d1 + d2 * d2;
/* 231 */     if (d3 < 1.0E-8D) return false;
/*     */     
/* 233 */     double d4 = 1.0D / Math.sqrt(d3);
/* 234 */     d1 *= d4;
/* 235 */     d2 *= d4;
/*     */     
/* 237 */     paramInt1 += 2;
/* 238 */     paramInt3 += 2;
/* 239 */     if (!a(i, (int)paramVec3D1.b, j, paramInt1, paramInt2, paramInt3, paramVec3D1, d1, d2)) return false; 
/* 240 */     paramInt1 -= 2;
/* 241 */     paramInt3 -= 2;
/*     */     
/* 243 */     double d5 = 1.0D / Math.abs(d1);
/* 244 */     double d6 = 1.0D / Math.abs(d2);
/*     */     
/* 246 */     double d7 = (i * 1) - paramVec3D1.a;
/* 247 */     double d8 = (j * 1) - paramVec3D1.c;
/* 248 */     if (d1 >= 0.0D) d7++; 
/* 249 */     if (d2 >= 0.0D) d8++; 
/* 250 */     d7 /= d1;
/* 251 */     d8 /= d2;
/*     */     
/* 253 */     byte b1 = (d1 < 0.0D) ? -1 : 1;
/* 254 */     byte b2 = (d2 < 0.0D) ? -1 : 1;
/* 255 */     int k = MathHelper.floor(paramVec3D2.a);
/* 256 */     int m = MathHelper.floor(paramVec3D2.c);
/* 257 */     int n = k - i;
/* 258 */     int i1 = m - j;
/* 259 */     while (n * b1 > 0 || i1 * b2 > 0) {
/* 260 */       if (d7 < d8) {
/* 261 */         d7 += d5;
/* 262 */         i += b1;
/* 263 */         n = k - i;
/*     */       } else {
/* 265 */         d8 += d6;
/* 266 */         j += b2;
/* 267 */         i1 = m - j;
/*     */       } 
/*     */       
/* 270 */       if (!a(i, (int)paramVec3D1.b, j, paramInt1, paramInt2, paramInt3, paramVec3D1, d1, d2)) return false; 
/*     */     } 
/* 272 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean a(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, Vec3D paramVec3D, double paramDouble1, double paramDouble2) {
/* 277 */     int i = paramInt1 - paramInt4 / 2;
/* 278 */     int j = paramInt3 - paramInt6 / 2;
/*     */     
/* 280 */     if (!b(i, paramInt2, j, paramInt4, paramInt5, paramInt6, paramVec3D, paramDouble1, paramDouble2)) return false;
/*     */ 
/*     */     
/* 283 */     for (int k = i; k < i + paramInt4; k++) {
/* 284 */       for (int m = j; m < j + paramInt6; m++) {
/* 285 */         double d1 = k + 0.5D - paramVec3D.a;
/* 286 */         double d2 = m + 0.5D - paramVec3D.c;
/* 287 */         if (d1 * paramDouble1 + d2 * paramDouble2 >= 0.0D) {
/* 288 */           Block block = this.b.getType(k, paramInt2 - 1, m);
/* 289 */           Material material = block.getMaterial();
/* 290 */           if (material == Material.AIR) return false; 
/* 291 */           if (material == Material.WATER && !this.a.M()) return false; 
/* 292 */           if (material == Material.LAVA) return false; 
/*     */         } 
/*     */       } 
/*     */     } 
/* 296 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean b(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, Vec3D paramVec3D, double paramDouble1, double paramDouble2) {
/* 301 */     for (int i = paramInt1; i < paramInt1 + paramInt4; i++) {
/* 302 */       for (int j = paramInt2; j < paramInt2 + paramInt5; j++) {
/* 303 */         for (int k = paramInt3; k < paramInt3 + paramInt6; k++) {
/*     */           
/* 305 */           double d1 = i + 0.5D - paramVec3D.a;
/* 306 */           double d2 = k + 0.5D - paramVec3D.c;
/* 307 */           if (d1 * paramDouble1 + d2 * paramDouble2 >= 0.0D) {
/* 308 */             Block block = this.b.getType(i, j, k);
/* 309 */             if (!block.b(this.b, i, j, k)) return false; 
/*     */           } 
/*     */         } 
/*     */       } 
/* 313 */     }  return true;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\Navigation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
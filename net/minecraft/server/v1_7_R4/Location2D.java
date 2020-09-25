/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Random;
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
/*     */ class Location2D
/*     */ {
/*     */   double a;
/*     */   double b;
/*     */   
/*     */   Location2D() {}
/*     */   
/*     */   Location2D(double paramDouble1, double paramDouble2) {
/* 233 */     this.a = paramDouble1;
/* 234 */     this.b = paramDouble2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   double a(Location2D paramLocation2D) {
/* 243 */     double d1 = this.a - paramLocation2D.a;
/* 244 */     double d2 = this.b - paramLocation2D.b;
/*     */     
/* 246 */     return Math.sqrt(d1 * d1 + d2 * d2);
/*     */   }
/*     */   
/*     */   void a() {
/* 250 */     double d = b();
/* 251 */     this.a /= d;
/* 252 */     this.b /= d;
/*     */   }
/*     */   
/*     */   float b() {
/* 256 */     return MathHelper.sqrt(this.a * this.a + this.b * this.b);
/*     */   }
/*     */   
/*     */   public void b(Location2D paramLocation2D) {
/* 260 */     this.a -= paramLocation2D.a;
/* 261 */     this.b -= paramLocation2D.b;
/*     */   }
/*     */   
/*     */   public boolean a(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/* 265 */     boolean bool = false;
/*     */     
/* 267 */     if (this.a < paramDouble1) {
/* 268 */       this.a = paramDouble1;
/* 269 */       bool = true;
/* 270 */     } else if (this.a > paramDouble3) {
/* 271 */       this.a = paramDouble3;
/* 272 */       bool = true;
/*     */     } 
/*     */     
/* 275 */     if (this.b < paramDouble2) {
/* 276 */       this.b = paramDouble2;
/* 277 */       bool = true;
/* 278 */     } else if (this.b > paramDouble4) {
/* 279 */       this.b = paramDouble4;
/* 280 */       bool = true;
/*     */     } 
/*     */     
/* 283 */     return bool;
/*     */   }
/*     */   
/*     */   public int a(World paramWorld) {
/* 287 */     int i = MathHelper.floor(this.a);
/* 288 */     int j = MathHelper.floor(this.b);
/*     */     
/* 290 */     for (char c = 'Ā'; c > '\000'; c--) {
/* 291 */       if (paramWorld.getType(i, c, j).getMaterial() != Material.AIR) {
/* 292 */         return c + 1;
/*     */       }
/*     */     } 
/*     */     
/* 296 */     return 257;
/*     */   }
/*     */   
/*     */   public boolean b(World paramWorld) {
/* 300 */     int i = MathHelper.floor(this.a);
/* 301 */     int j = MathHelper.floor(this.b);
/*     */     
/* 303 */     char c = 'Ā'; if (c > '\000') {
/* 304 */       Material material = paramWorld.getType(i, c, j).getMaterial();
/*     */       
/* 306 */       return (!material.isLiquid() && material != Material.FIRE);
/*     */     } 
/*     */     
/* 309 */     return false;
/*     */   }
/*     */   
/*     */   public void a(Random paramRandom, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/* 313 */     this.a = MathHelper.a(paramRandom, paramDouble1, paramDouble3);
/* 314 */     this.b = MathHelper.a(paramRandom, paramDouble2, paramDouble4);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\Location2D.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
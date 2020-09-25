/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Pathfinder
/*     */ {
/*     */   private IBlockAccess a;
/*  13 */   private Path b = new Path();
/*  14 */   private IntHashMap c = new IntHashMap();
/*     */   
/*  16 */   private PathPoint[] d = new PathPoint[32];
/*     */   
/*     */   private boolean e;
/*     */   private boolean f;
/*     */   private boolean g;
/*     */   private boolean h;
/*     */   
/*     */   public Pathfinder(IBlockAccess paramIBlockAccess, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4) {
/*  24 */     this.a = paramIBlockAccess;
/*  25 */     this.e = paramBoolean1;
/*  26 */     this.f = paramBoolean2;
/*  27 */     this.g = paramBoolean3;
/*  28 */     this.h = paramBoolean4;
/*     */   }
/*     */   
/*     */   public PathEntity a(Entity paramEntity1, Entity paramEntity2, float paramFloat) {
/*  32 */     return a(paramEntity1, paramEntity2.locX, paramEntity2.boundingBox.b, paramEntity2.locZ, paramFloat);
/*     */   }
/*     */   
/*     */   public PathEntity a(Entity paramEntity, int paramInt1, int paramInt2, int paramInt3, float paramFloat) {
/*  36 */     return a(paramEntity, (paramInt1 + 0.5F), (paramInt2 + 0.5F), (paramInt3 + 0.5F), paramFloat);
/*     */   }
/*     */   
/*     */   private PathEntity a(Entity paramEntity, double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat) {
/*  40 */     this.b.a();
/*  41 */     this.c.c();
/*     */     
/*  43 */     boolean bool = this.g;
/*  44 */     int i = MathHelper.floor(paramEntity.boundingBox.b + 0.5D);
/*  45 */     if (this.h && paramEntity.M())
/*  46 */     { i = (int)paramEntity.boundingBox.b;
/*  47 */       Block block = this.a.getType(MathHelper.floor(paramEntity.locX), i, MathHelper.floor(paramEntity.locZ));
/*  48 */       while (block == Blocks.WATER || block == Blocks.STATIONARY_WATER) {
/*  49 */         i++;
/*  50 */         block = this.a.getType(MathHelper.floor(paramEntity.locX), i, MathHelper.floor(paramEntity.locZ));
/*     */       } 
/*  52 */       bool = this.g;
/*  53 */       this.g = false; }
/*  54 */     else { i = MathHelper.floor(paramEntity.boundingBox.b + 0.5D); }
/*     */     
/*  56 */     PathPoint pathPoint1 = a(MathHelper.floor(paramEntity.boundingBox.a), i, MathHelper.floor(paramEntity.boundingBox.c));
/*  57 */     PathPoint pathPoint2 = a(MathHelper.floor(paramDouble1 - (paramEntity.width / 2.0F)), MathHelper.floor(paramDouble2), MathHelper.floor(paramDouble3 - (paramEntity.width / 2.0F)));
/*     */     
/*  59 */     PathPoint pathPoint3 = new PathPoint(MathHelper.d(paramEntity.width + 1.0F), MathHelper.d(paramEntity.length + 1.0F), MathHelper.d(paramEntity.width + 1.0F));
/*  60 */     PathEntity pathEntity = a(paramEntity, pathPoint1, pathPoint2, pathPoint3, paramFloat);
/*     */     
/*  62 */     this.g = bool;
/*  63 */     return pathEntity;
/*     */   }
/*     */ 
/*     */   
/*     */   private PathEntity a(Entity paramEntity, PathPoint paramPathPoint1, PathPoint paramPathPoint2, PathPoint paramPathPoint3, float paramFloat) {
/*  68 */     paramPathPoint1.e = 0.0F;
/*  69 */     paramPathPoint1.f = paramPathPoint1.b(paramPathPoint2);
/*  70 */     paramPathPoint1.g = paramPathPoint1.f;
/*     */     
/*  72 */     this.b.a();
/*  73 */     this.b.a(paramPathPoint1);
/*     */     
/*  75 */     PathPoint pathPoint = paramPathPoint1;
/*     */     
/*  77 */     while (!this.b.e()) {
/*  78 */       PathPoint pathPoint1 = this.b.c();
/*     */       
/*  80 */       if (pathPoint1.equals(paramPathPoint2)) {
/*  81 */         return a(paramPathPoint1, paramPathPoint2);
/*     */       }
/*     */       
/*  84 */       if (pathPoint1.b(paramPathPoint2) < pathPoint.b(paramPathPoint2)) {
/*  85 */         pathPoint = pathPoint1;
/*     */       }
/*  87 */       pathPoint1.i = true;
/*     */       
/*  89 */       int i = b(paramEntity, pathPoint1, paramPathPoint3, paramPathPoint2, paramFloat);
/*  90 */       for (byte b = 0; b < i; b++) {
/*  91 */         PathPoint pathPoint2 = this.d[b];
/*     */         
/*  93 */         float f = pathPoint1.e + pathPoint1.b(pathPoint2);
/*  94 */         if (!pathPoint2.a() || f < pathPoint2.e) {
/*  95 */           pathPoint2.h = pathPoint1;
/*  96 */           pathPoint2.e = f;
/*  97 */           pathPoint2.f = pathPoint2.b(paramPathPoint2);
/*  98 */           if (pathPoint2.a()) {
/*  99 */             this.b.a(pathPoint2, pathPoint2.e + pathPoint2.f);
/*     */           } else {
/* 101 */             pathPoint2.g = pathPoint2.e + pathPoint2.f;
/* 102 */             this.b.a(pathPoint2);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 108 */     if (pathPoint == paramPathPoint1) return null; 
/* 109 */     return a(paramPathPoint1, pathPoint);
/*     */   }
/*     */   
/*     */   private int b(Entity paramEntity, PathPoint paramPathPoint1, PathPoint paramPathPoint2, PathPoint paramPathPoint3, float paramFloat) {
/* 113 */     byte b = 0;
/*     */     
/* 115 */     boolean bool = false;
/* 116 */     if (a(paramEntity, paramPathPoint1.a, paramPathPoint1.b + 1, paramPathPoint1.c, paramPathPoint2) == 1) bool = true;
/*     */     
/* 118 */     PathPoint pathPoint1 = a(paramEntity, paramPathPoint1.a, paramPathPoint1.b, paramPathPoint1.c + 1, paramPathPoint2, bool);
/* 119 */     PathPoint pathPoint2 = a(paramEntity, paramPathPoint1.a - 1, paramPathPoint1.b, paramPathPoint1.c, paramPathPoint2, bool);
/* 120 */     PathPoint pathPoint3 = a(paramEntity, paramPathPoint1.a + 1, paramPathPoint1.b, paramPathPoint1.c, paramPathPoint2, bool);
/* 121 */     PathPoint pathPoint4 = a(paramEntity, paramPathPoint1.a, paramPathPoint1.b, paramPathPoint1.c - 1, paramPathPoint2, bool);
/*     */     
/* 123 */     if (pathPoint1 != null && !pathPoint1.i && pathPoint1.a(paramPathPoint3) < paramFloat) this.d[b++] = pathPoint1; 
/* 124 */     if (pathPoint2 != null && !pathPoint2.i && pathPoint2.a(paramPathPoint3) < paramFloat) this.d[b++] = pathPoint2; 
/* 125 */     if (pathPoint3 != null && !pathPoint3.i && pathPoint3.a(paramPathPoint3) < paramFloat) this.d[b++] = pathPoint3; 
/* 126 */     if (pathPoint4 != null && !pathPoint4.i && pathPoint4.a(paramPathPoint3) < paramFloat) this.d[b++] = pathPoint4;
/*     */     
/* 128 */     return b;
/*     */   }
/*     */   
/*     */   private PathPoint a(Entity paramEntity, int paramInt1, int paramInt2, int paramInt3, PathPoint paramPathPoint, int paramInt4) {
/* 132 */     PathPoint pathPoint = null;
/* 133 */     int i = a(paramEntity, paramInt1, paramInt2, paramInt3, paramPathPoint);
/* 134 */     if (i == 2) return a(paramInt1, paramInt2, paramInt3); 
/* 135 */     if (i == 1) pathPoint = a(paramInt1, paramInt2, paramInt3); 
/* 136 */     if (pathPoint == null && paramInt4 > 0 && i != -3 && i != -4 && a(paramEntity, paramInt1, paramInt2 + paramInt4, paramInt3, paramPathPoint) == 1) {
/* 137 */       pathPoint = a(paramInt1, paramInt2 + paramInt4, paramInt3);
/* 138 */       paramInt2 += paramInt4;
/*     */     } 
/*     */     
/* 141 */     if (pathPoint != null) {
/* 142 */       byte b = 0;
/* 143 */       int j = 0;
/*     */       
/* 145 */       while (paramInt2 > 0) {
/* 146 */         j = a(paramEntity, paramInt1, paramInt2 - 1, paramInt3, paramPathPoint);
/* 147 */         if (this.g && j == -1) return null; 
/* 148 */         if (j != 1) {
/*     */           break;
/*     */         }
/* 151 */         if (b++ >= paramEntity.ax()) return null; 
/* 152 */         paramInt2--;
/* 153 */         if (paramInt2 > 0) pathPoint = a(paramInt1, paramInt2, paramInt3);
/*     */       
/*     */       } 
/* 156 */       if (j == -2) return null;
/*     */     
/*     */     } 
/* 159 */     return pathPoint;
/*     */   }
/*     */   
/*     */   private final PathPoint a(int paramInt1, int paramInt2, int paramInt3) {
/* 163 */     int i = PathPoint.a(paramInt1, paramInt2, paramInt3);
/* 164 */     PathPoint pathPoint = (PathPoint)this.c.get(i);
/* 165 */     if (pathPoint == null) {
/* 166 */       pathPoint = new PathPoint(paramInt1, paramInt2, paramInt3);
/* 167 */       this.c.a(i, pathPoint);
/*     */     } 
/* 169 */     return pathPoint;
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
/*     */   public int a(Entity paramEntity, int paramInt1, int paramInt2, int paramInt3, PathPoint paramPathPoint) {
/* 181 */     return a(paramEntity, paramInt1, paramInt2, paramInt3, paramPathPoint, this.g, this.f, this.e);
/*     */   }
/*     */   
/*     */   public static int a(Entity paramEntity, int paramInt1, int paramInt2, int paramInt3, PathPoint paramPathPoint, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3) {
/* 185 */     boolean bool = false;
/* 186 */     for (int i = paramInt1; i < paramInt1 + paramPathPoint.a; i++) {
/* 187 */       for (int j = paramInt2; j < paramInt2 + paramPathPoint.b; j++) {
/* 188 */         for (int k = paramInt3; k < paramInt3 + paramPathPoint.c; k++) {
/*     */           
/* 190 */           Block block = paramEntity.world.getType(i, j, k);
/* 191 */           if (block.getMaterial() != Material.AIR) {
/* 192 */             if (block == Blocks.TRAP_DOOR) { bool = true; }
/* 193 */             else if (block == Blocks.WATER || block == Blocks.STATIONARY_WATER)
/* 194 */             { if (paramBoolean1) return -1; 
/* 195 */               bool = true; }
/* 196 */             else if (!paramBoolean3 && block == Blocks.WOODEN_DOOR)
/* 197 */             { return 0; }
/*     */             
/* 199 */             int m = block.b();
/*     */             
/* 201 */             if (paramEntity.world.getType(i, j, k).b() == 9)
/* 202 */             { int n = MathHelper.floor(paramEntity.locX);
/* 203 */               int i1 = MathHelper.floor(paramEntity.locY);
/* 204 */               int i2 = MathHelper.floor(paramEntity.locZ);
/* 205 */               if (paramEntity.world.getType(n, i1, i2).b() != 9 && paramEntity.world.getType(n, i1 - 1, i2).b() != 9)
/*     */               {
/*     */ 
/*     */                 
/* 209 */                 return -3;
/*     */               
/*     */               } }
/*     */             
/* 213 */             else if (!block.b(paramEntity.world, i, j, k) && (
/* 214 */               !paramBoolean2 || block != Blocks.WOODEN_DOOR))
/*     */             
/* 216 */             { if (m == 11 || block == Blocks.FENCE_GATE || m == 32) return -3;
/*     */               
/* 218 */               if (block == Blocks.TRAP_DOOR) return -4; 
/* 219 */               Material material = block.getMaterial();
/* 220 */               if (material == Material.LAVA)
/* 221 */               { if (!paramEntity.P())
/* 222 */                   return -2;  }
/*     */               else
/* 224 */               { return 0; }  } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 228 */     }  return bool ? 2 : 1;
/*     */   }
/*     */ 
/*     */   
/*     */   private PathEntity a(PathPoint paramPathPoint1, PathPoint paramPathPoint2) {
/* 233 */     byte b = 1;
/* 234 */     PathPoint pathPoint = paramPathPoint2;
/* 235 */     while (pathPoint.h != null) {
/* 236 */       b++;
/* 237 */       pathPoint = pathPoint.h;
/*     */     } 
/*     */     
/* 240 */     PathPoint[] arrayOfPathPoint = new PathPoint[b];
/* 241 */     pathPoint = paramPathPoint2;
/* 242 */     arrayOfPathPoint[--b] = pathPoint;
/* 243 */     while (pathPoint.h != null) {
/* 244 */       pathPoint = pathPoint.h;
/* 245 */       arrayOfPathPoint[--b] = pathPoint;
/*     */     } 
/* 247 */     return new PathEntity(arrayOfPathPoint);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\Pathfinder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
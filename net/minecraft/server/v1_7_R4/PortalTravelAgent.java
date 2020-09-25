/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.entity.EntityPortalExitEvent;
/*     */ import org.bukkit.util.Vector;
/*     */ 
/*     */ public class PortalTravelAgent
/*     */ {
/*     */   private final WorldServer a;
/*     */   private final Random b;
/*  18 */   private final LongHashMap c = new LongHashMap();
/*  19 */   private final List d = new ArrayList();
/*     */   
/*     */   public PortalTravelAgent(WorldServer worldserver) {
/*  22 */     this.a = worldserver;
/*  23 */     this.b = new Random(worldserver.getSeed());
/*     */   }
/*     */   
/*     */   public void a(Entity entity, double d0, double d1, double d2, float f) {
/*  27 */     if (this.a.worldProvider.dimension != 1) {
/*  28 */       if (!b(entity, d0, d1, d2, f)) {
/*  29 */         a(entity);
/*  30 */         b(entity, d0, d1, d2, f);
/*     */       } 
/*     */     } else {
/*     */       
/*  34 */       ChunkCoordinates created = createEndPortal(d0, d1, d2);
/*  35 */       entity.setPositionRotation(created.x, created.y, created.z, entity.yaw, 0.0F);
/*  36 */       entity.motX = entity.motY = entity.motZ = 0.0D;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private ChunkCoordinates createEndPortal(double x, double y, double z) {
/*  42 */     int i = MathHelper.floor(x);
/*  43 */     int j = MathHelper.floor(y) - 1;
/*  44 */     int k = MathHelper.floor(z);
/*     */     
/*  46 */     byte b0 = 1;
/*  47 */     byte b1 = 0;
/*     */     
/*  49 */     for (int l = -2; l <= 2; l++) {
/*  50 */       for (int i1 = -2; i1 <= 2; i1++) {
/*  51 */         for (int j1 = -1; j1 < 3; j1++) {
/*  52 */           int k1 = i + i1 * b0 + l * b1;
/*  53 */           int l1 = j + j1;
/*  54 */           int i2 = k + i1 * b1 - l * b0;
/*  55 */           boolean flag = (j1 < 0);
/*     */           
/*  57 */           this.a.setTypeUpdate(k1, l1, i2, flag ? Blocks.OBSIDIAN : Blocks.AIR);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  63 */     return new ChunkCoordinates(i, j, k);
/*     */   }
/*     */ 
/*     */   
/*     */   private ChunkCoordinates findEndPortal(ChunkCoordinates portal) {
/*  68 */     int i = portal.x;
/*  69 */     int j = portal.y - 1;
/*  70 */     int k = portal.z;
/*  71 */     byte b0 = 1;
/*  72 */     byte b1 = 0;
/*     */     
/*  74 */     for (int l = -2; l <= 2; l++) {
/*  75 */       for (int i1 = -2; i1 <= 2; i1++) {
/*  76 */         for (int j1 = -1; j1 < 3; j1++) {
/*  77 */           int k1 = i + i1 * b0 + l * b1;
/*  78 */           int l1 = j + j1;
/*  79 */           int i2 = k + i1 * b1 - l * b0;
/*  80 */           boolean flag = (j1 < 0);
/*     */           
/*  82 */           if (this.a.getType(k1, l1, i2) != (flag ? Blocks.OBSIDIAN : Blocks.AIR)) {
/*  83 */             return null;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*  88 */     return new ChunkCoordinates(i, j, k);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean b(Entity entity, double d0, double d1, double d2, float f) {
/*  94 */     ChunkCoordinates found = findPortal(entity.locX, entity.locY, entity.locZ, 128);
/*  95 */     if (found == null) {
/*  96 */       return false;
/*     */     }
/*     */     
/*  99 */     Location exit = new Location((World)this.a.getWorld(), found.x, found.y, found.z, f, entity.pitch);
/* 100 */     Vector velocity = entity.getBukkitEntity().getVelocity();
/* 101 */     adjustExit(entity, exit, velocity);
/* 102 */     entity.setPositionRotation(exit.getX(), exit.getY(), exit.getZ(), exit.getYaw(), exit.getPitch());
/* 103 */     if (entity.motX != velocity.getX() || entity.motY != velocity.getY() || entity.motZ != velocity.getZ()) {
/* 104 */       entity.getBukkitEntity().setVelocity(velocity);
/*     */     }
/* 106 */     return true;
/*     */   }
/*     */   
/*     */   public ChunkCoordinates findPortal(double x, double y, double z, int short1) {
/* 110 */     if (this.a.getWorld().getEnvironment() == World.Environment.THE_END) {
/* 111 */       return findEndPortal(this.a.worldProvider.h());
/*     */     }
/*     */     
/* 114 */     double d3 = -1.0D;
/* 115 */     int i = 0;
/* 116 */     int j = 0;
/* 117 */     int k = 0;
/*     */     
/* 119 */     int l = MathHelper.floor(x);
/* 120 */     int i1 = MathHelper.floor(z);
/*     */     
/* 122 */     long j1 = ChunkCoordIntPair.a(l, i1);
/* 123 */     boolean flag = true;
/*     */ 
/*     */ 
/*     */     
/* 127 */     if (this.c.contains(j1)) {
/* 128 */       ChunkCoordinatesPortal chunkcoordinatesportal = (ChunkCoordinatesPortal)this.c.getEntry(j1);
/*     */       
/* 130 */       d3 = 0.0D;
/* 131 */       i = chunkcoordinatesportal.x;
/* 132 */       j = chunkcoordinatesportal.y;
/* 133 */       k = chunkcoordinatesportal.z;
/* 134 */       chunkcoordinatesportal.d = this.a.getTime();
/* 135 */       flag = false;
/*     */     } else {
/* 137 */       for (int k1 = l - short1; k1 <= l + short1; k1++) {
/* 138 */         double d5 = k1 + 0.5D - x;
/*     */         
/* 140 */         for (int l1 = i1 - short1; l1 <= i1 + short1; l1++) {
/* 141 */           double d6 = l1 + 0.5D - z;
/*     */           
/* 143 */           for (int i2 = this.a.S() - 1; i2 >= 0; i2--) {
/* 144 */             if (this.a.getType(k1, i2, l1) == Blocks.PORTAL) {
/* 145 */               while (this.a.getType(k1, i2 - 1, l1) == Blocks.PORTAL) {
/* 146 */                 i2--;
/*     */               }
/*     */               
/* 149 */               double d4 = i2 + 0.5D - y;
/* 150 */               double d7 = d5 * d5 + d4 * d4 + d6 * d6;
/*     */               
/* 152 */               if (d3 < 0.0D || d7 < d3) {
/* 153 */                 d3 = d7;
/* 154 */                 i = k1;
/* 155 */                 j = i2;
/* 156 */                 k = l1;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 164 */     if (d3 >= 0.0D) {
/* 165 */       if (flag) {
/* 166 */         this.c.put(j1, new ChunkCoordinatesPortal(this, i, j, k, this.a.getTime()));
/* 167 */         this.d.add(Long.valueOf(j1));
/*     */       } 
/*     */       
/* 170 */       return new ChunkCoordinates(i, j, k);
/*     */     } 
/* 172 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void adjustExit(Entity entity, Location position, Vector velocity) {
/* 177 */     Location from = position.clone();
/* 178 */     Vector before = velocity.clone();
/* 179 */     int i = position.getBlockX();
/* 180 */     int j = position.getBlockY();
/* 181 */     int k = position.getBlockZ();
/* 182 */     float f = position.getYaw();
/*     */     
/* 184 */     if (this.a.getWorld().getEnvironment() == World.Environment.THE_END) {
/*     */ 
/*     */       
/* 187 */       position.setPitch(0.0F);
/* 188 */       velocity.setX(0);
/* 189 */       velocity.setY(0);
/* 190 */       velocity.setZ(0);
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 196 */       double d8 = i + 0.5D;
/* 197 */       double d9 = j + 0.5D;
/*     */       
/* 199 */       double d4 = k + 0.5D;
/* 200 */       int j2 = -1;
/*     */       
/* 202 */       if (this.a.getType(i - 1, j, k) == Blocks.PORTAL) {
/* 203 */         j2 = 2;
/*     */       }
/*     */       
/* 206 */       if (this.a.getType(i + 1, j, k) == Blocks.PORTAL) {
/* 207 */         j2 = 0;
/*     */       }
/*     */       
/* 210 */       if (this.a.getType(i, j, k - 1) == Blocks.PORTAL) {
/* 211 */         j2 = 3;
/*     */       }
/*     */       
/* 214 */       if (this.a.getType(i, j, k + 1) == Blocks.PORTAL) {
/* 215 */         j2 = 1;
/*     */       }
/*     */       
/* 218 */       int k2 = entity.ay();
/*     */       
/* 220 */       if (j2 > -1) {
/* 221 */         int l2 = Direction.h[j2];
/* 222 */         int i3 = Direction.a[j2];
/* 223 */         int j3 = Direction.b[j2];
/* 224 */         int k3 = Direction.a[l2];
/* 225 */         int l3 = Direction.b[l2];
/* 226 */         boolean flag1 = (!this.a.isEmpty(i + i3 + k3, j, k + j3 + l3) || !this.a.isEmpty(i + i3 + k3, j + 1, k + j3 + l3));
/* 227 */         boolean flag2 = (!this.a.isEmpty(i + i3, j, k + j3) || !this.a.isEmpty(i + i3, j + 1, k + j3));
/*     */         
/* 229 */         if (flag1 && flag2) {
/* 230 */           j2 = Direction.f[j2];
/* 231 */           l2 = Direction.f[l2];
/* 232 */           i3 = Direction.a[j2];
/* 233 */           j3 = Direction.b[j2];
/* 234 */           k3 = Direction.a[l2];
/* 235 */           l3 = Direction.b[l2];
/* 236 */           int k1 = i - k3;
/* 237 */           d8 -= k3;
/* 238 */           int i4 = k - l3;
/*     */           
/* 240 */           d4 -= l3;
/* 241 */           flag1 = (!this.a.isEmpty(k1 + i3 + k3, j, i4 + j3 + l3) || !this.a.isEmpty(k1 + i3 + k3, j + 1, i4 + j3 + l3));
/* 242 */           flag2 = (!this.a.isEmpty(k1 + i3, j, i4 + j3) || !this.a.isEmpty(k1 + i3, j + 1, i4 + j3));
/*     */         } 
/*     */         
/* 245 */         float f1 = 0.5F;
/* 246 */         float f2 = 0.5F;
/*     */         
/* 248 */         if (!flag1 && flag2) {
/* 249 */           f1 = 1.0F;
/* 250 */         } else if (flag1 && !flag2) {
/* 251 */           f1 = 0.0F;
/* 252 */         } else if (flag1 && flag2) {
/* 253 */           f2 = 0.0F;
/*     */         } 
/*     */         
/* 256 */         d8 += (k3 * f1 + f2 * i3);
/* 257 */         d4 += (l3 * f1 + f2 * j3);
/* 258 */         float f3 = 0.0F;
/* 259 */         float f4 = 0.0F;
/* 260 */         float f5 = 0.0F;
/* 261 */         float f6 = 0.0F;
/*     */         
/* 263 */         if (j2 == k2) {
/* 264 */           f3 = 1.0F;
/* 265 */           f4 = 1.0F;
/* 266 */         } else if (j2 == Direction.f[k2]) {
/* 267 */           f3 = -1.0F;
/* 268 */           f4 = -1.0F;
/* 269 */         } else if (j2 == Direction.g[k2]) {
/* 270 */           f5 = 1.0F;
/* 271 */           f6 = -1.0F;
/*     */         } else {
/* 273 */           f5 = -1.0F;
/* 274 */           f6 = 1.0F;
/*     */         } 
/*     */ 
/*     */         
/* 278 */         double d10 = velocity.getX();
/* 279 */         double d11 = velocity.getZ();
/*     */ 
/*     */ 
/*     */         
/* 283 */         velocity.setX(d10 * f3 + d11 * f6);
/* 284 */         velocity.setZ(d10 * f5 + d11 * f4);
/* 285 */         f = f - (k2 * 90) + (j2 * 90);
/*     */       } else {
/*     */         
/* 288 */         velocity.setX(0);
/* 289 */         velocity.setY(0);
/* 290 */         velocity.setZ(0);
/*     */       } 
/*     */ 
/*     */       
/* 294 */       position.setX(d8);
/* 295 */       position.setY(d9);
/* 296 */       position.setZ(d4);
/* 297 */       position.setYaw(f);
/*     */     } 
/*     */     
/* 300 */     EntityPortalExitEvent event = new EntityPortalExitEvent((Entity)entity.getBukkitEntity(), from, position, before, velocity);
/* 301 */     this.a.getServer().getPluginManager().callEvent((Event)event);
/* 302 */     Location to = event.getTo();
/* 303 */     if (event.isCancelled() || to == null || !entity.isAlive()) {
/* 304 */       position.setX(from.getX());
/* 305 */       position.setY(from.getY());
/* 306 */       position.setZ(from.getZ());
/* 307 */       position.setYaw(from.getYaw());
/* 308 */       position.setPitch(from.getPitch());
/* 309 */       velocity.copy(before);
/*     */     } else {
/* 311 */       position.setX(to.getX());
/* 312 */       position.setY(to.getY());
/* 313 */       position.setZ(to.getZ());
/* 314 */       position.setYaw(to.getYaw());
/* 315 */       position.setPitch(to.getPitch());
/* 316 */       velocity.copy(event.getAfter());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean a(Entity entity) {
/* 323 */     return createPortal(entity.locX, entity.locY, entity.locZ, 16);
/*     */   }
/*     */   
/*     */   public boolean createPortal(double x, double y, double z, int b0) {
/* 327 */     if (this.a.getWorld().getEnvironment() == World.Environment.THE_END) {
/* 328 */       createEndPortal(x, y, z);
/* 329 */       return true;
/*     */     } 
/*     */     
/* 332 */     double d0 = -1.0D;
/*     */     
/* 334 */     int i = MathHelper.floor(x);
/* 335 */     int j = MathHelper.floor(y);
/* 336 */     int k = MathHelper.floor(z);
/*     */     
/* 338 */     int l = i;
/* 339 */     int i1 = j;
/* 340 */     int j1 = k;
/* 341 */     int k1 = 0;
/* 342 */     int l1 = this.b.nextInt(4);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     int i2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 360 */     for (i2 = i - b0; i2 <= i + b0; i2++) {
/* 361 */       double d1 = i2 + 0.5D - x;
/*     */       
/* 363 */       for (int m = k - b0; m <= k + b0; m++) {
/* 364 */         double d2 = m + 0.5D - z;
/*     */         
/*     */         int n;
/* 367 */         label175: for (n = this.a.S() - 1; n >= 0; n--) {
/* 368 */           if (this.a.isEmpty(i2, n, m)) {
/* 369 */             while (n > 0 && this.a.isEmpty(i2, n - 1, m)) {
/* 370 */               n--;
/*     */             }
/*     */             
/* 373 */             for (int i3 = l1; i3 < l1 + 4; i3++) {
/* 374 */               int l2 = i3 % 2;
/* 375 */               int k3 = 1 - l2;
/* 376 */               if (i3 % 4 >= 2) {
/* 377 */                 l2 = -l2;
/* 378 */                 k3 = -k3;
/*     */               } 
/*     */               
/* 381 */               for (int j3 = 0; j3 < 3; j3++) {
/* 382 */                 for (int i4 = 0; i4 < 4; i4++) {
/* 383 */                   for (int l3 = -1; l3 < 4; ) {
/* 384 */                     int k4 = i2 + (i4 - 1) * l2 + j3 * k3;
/* 385 */                     int j4 = n + l3;
/* 386 */                     int l4 = m + (i4 - 1) * k3 - j3 * l2;
/*     */                     
/* 388 */                     if (l3 >= 0 || this.a.getType(k4, j4, l4).getMaterial().isBuildable()) { if (l3 >= 0 && !this.a.isEmpty(k4, j4, l4))
/*     */                         continue label175;  l3++; }
/*     */                     
/*     */                     continue label175;
/*     */                   } 
/*     */                 } 
/*     */               } 
/* 395 */               double d3 = n + 0.5D - y;
/* 396 */               double d4 = d1 * d1 + d3 * d3 + d2 * d2;
/* 397 */               if (d0 < 0.0D || d4 < d0) {
/* 398 */                 d0 = d4;
/* 399 */                 l = i2;
/* 400 */                 i1 = n;
/* 401 */                 j1 = m;
/* 402 */                 k1 = i3 % 4;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 410 */     if (d0 < 0.0D) {
/* 411 */       for (i2 = i - b0; i2 <= i + b0; i2++) {
/* 412 */         double d1 = i2 + 0.5D - x;
/*     */         
/* 414 */         for (int m = k - b0; m <= k + b0; m++) {
/* 415 */           double d2 = m + 0.5D - z;
/*     */           
/*     */           int n;
/* 418 */           label172: for (n = this.a.S() - 1; n >= 0; n--) {
/* 419 */             if (this.a.isEmpty(i2, n, m)) {
/* 420 */               while (n > 0 && this.a.isEmpty(i2, n - 1, m)) {
/* 421 */                 n--;
/*     */               }
/*     */               
/* 424 */               for (int i3 = l1; i3 < l1 + 2; i3++) {
/* 425 */                 int l2 = i3 % 2;
/* 426 */                 int k3 = 1 - l2;
/*     */                 
/* 428 */                 for (int j3 = 0; j3 < 4; j3++) {
/* 429 */                   for (int i4 = -1; i4 < 4; ) {
/* 430 */                     int l3 = i2 + (j3 - 1) * l2;
/* 431 */                     int k4 = n + i4;
/* 432 */                     int j4 = m + (j3 - 1) * k3;
/* 433 */                     if (i4 >= 0 || this.a.getType(l3, k4, j4).getMaterial().isBuildable()) { if (i4 >= 0 && !this.a.isEmpty(l3, k4, j4))
/*     */                         continue label172;  i4++; }
/*     */                     
/*     */                     continue label172;
/*     */                   } 
/*     */                 } 
/* 439 */                 double d3 = n + 0.5D - y;
/* 440 */                 double d4 = d1 * d1 + d3 * d3 + d2 * d2;
/* 441 */                 if (d0 < 0.0D || d4 < d0) {
/* 442 */                   d0 = d4;
/* 443 */                   l = i2;
/* 444 */                   i1 = n;
/* 445 */                   j1 = m;
/* 446 */                   k1 = i3 % 2;
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 455 */     int i5 = l;
/* 456 */     int j5 = i1;
/*     */     
/* 458 */     int j2 = j1;
/* 459 */     int k5 = k1 % 2;
/* 460 */     int l5 = 1 - k5;
/*     */     
/* 462 */     if (k1 % 4 >= 2) {
/* 463 */       k5 = -k5;
/* 464 */       l5 = -l5;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 469 */     if (d0 < 0.0D) {
/* 470 */       if (i1 < 70) {
/* 471 */         i1 = 70;
/*     */       }
/*     */       
/* 474 */       if (i1 > this.a.S() - 10) {
/* 475 */         i1 = this.a.S() - 10;
/*     */       }
/*     */       
/* 478 */       j5 = i1;
/*     */       
/* 480 */       for (int m = -1; m <= 1; m++) {
/* 481 */         for (int i3 = 1; i3 < 3; i3++) {
/* 482 */           for (int l2 = -1; l2 < 3; l2++) {
/* 483 */             int k3 = i5 + (i3 - 1) * k5 + m * l5;
/* 484 */             int j3 = j5 + l2;
/* 485 */             int i4 = j2 + (i3 - 1) * l5 - m * k5;
/* 486 */             boolean flag = (l2 < 0);
/* 487 */             this.a.setTypeUpdate(k3, j3, i4, flag ? Blocks.OBSIDIAN : Blocks.AIR);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 493 */     for (int k2 = 0; k2 < 4; k2++) {
/* 494 */       int i3; for (i3 = 0; i3 < 4; i3++) {
/* 495 */         for (int l2 = -1; l2 < 4; l2++) {
/* 496 */           int k3 = i5 + (i3 - 1) * k5;
/* 497 */           int j3 = j5 + l2;
/* 498 */           int i4 = j2 + (i3 - 1) * l5;
/* 499 */           boolean flag = (i3 == 0 || i3 == 3 || l2 == -1 || l2 == 3);
/* 500 */           this.a.setTypeAndData(k3, j3, i4, flag ? Blocks.OBSIDIAN : Blocks.PORTAL, 0, 2);
/*     */         } 
/*     */       } 
/*     */       
/* 504 */       for (i3 = 0; i3 < 4; i3++) {
/* 505 */         for (int l2 = -1; l2 < 4; l2++) {
/* 506 */           int k3 = i5 + (i3 - 1) * k5;
/* 507 */           int j3 = j5 + l2;
/* 508 */           int i4 = j2 + (i3 - 1) * l5;
/* 509 */           this.a.applyPhysics(k3, j3, i4, this.a.getType(k3, j3, i4));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 514 */     return true;
/*     */   }
/*     */   
/*     */   public void a(long i) {
/* 518 */     if (i % 100L == 0L) {
/* 519 */       Iterator<Long> iterator = this.d.iterator();
/* 520 */       long j = i - 600L;
/*     */       
/* 522 */       while (iterator.hasNext()) {
/* 523 */         Long olong = iterator.next();
/* 524 */         ChunkCoordinatesPortal chunkcoordinatesportal = (ChunkCoordinatesPortal)this.c.getEntry(olong.longValue());
/*     */         
/* 526 */         if (chunkcoordinatesportal == null || chunkcoordinatesportal.d < j) {
/* 527 */           iterator.remove();
/* 528 */           this.c.remove(olong.longValue());
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PortalTravelAgent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package net.minecraft.server.v1_7_R4;
/*     */ import java.util.List;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.Vehicle;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.vehicle.VehicleDamageEvent;
/*     */ import org.bukkit.event.vehicle.VehicleDestroyEvent;
/*     */ import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;
/*     */ import org.bukkit.event.vehicle.VehicleMoveEvent;
/*     */ import org.bukkit.util.Vector;
/*     */ import org.spigotmc.ProtocolData;
/*     */ 
/*     */ public abstract class EntityMinecartAbstract extends Entity {
/*  18 */   private static final int[][][] matrix = new int[][][] { { { 0, 0, -1 }, { 0, 0, 1 } }, { { -1, 0, 0 }, { 1, 0, 0 } }, { { -1, -1, 0 }, { 1, 0, 0 } }, { { -1, 0, 0 }, { 1, -1, 0 } }, { { 0, 0, -1 }, { 0, -1, 1 } }, { { 0, -1, -1 }, { 0, 0, 1 } }, { { 0, 0, 1 }, { 1, 0, 0 } }, { { 0, 0, 1 }, { -1, 0, 0 } }, { { 0, 0, -1 }, { -1, 0, 0 } }, { { 0, 0, -1 }, { 1, 0, 0 } } };
/*     */   private boolean a;
/*     */   private String b;
/*     */   private int d;
/*     */   private double e;
/*     */   private double f;
/*     */   private double g;
/*     */   private double h;
/*     */   private double i;
/*     */   public boolean slowWhenEmpty = true;
/*  28 */   private double derailedX = 0.5D;
/*  29 */   private double derailedY = 0.5D;
/*  30 */   private double derailedZ = 0.5D;
/*  31 */   private double flyingX = 0.95D;
/*  32 */   private double flyingY = 0.95D;
/*  33 */   private double flyingZ = 0.95D;
/*  34 */   public double maxSpeed = 0.4D;
/*     */ 
/*     */   
/*     */   public EntityMinecartAbstract(World world) {
/*  38 */     super(world);
/*  39 */     this.k = true;
/*  40 */     a(0.98F, 0.7F);
/*  41 */     this.height = this.length / 2.0F;
/*     */   }
/*     */   
/*     */   public static EntityMinecartAbstract a(World world, double d0, double d1, double d2, int i) {
/*  45 */     switch (i) {
/*     */       case 1:
/*  47 */         return new EntityMinecartChest(world, d0, d1, d2);
/*     */       
/*     */       case 2:
/*  50 */         return new EntityMinecartFurnace(world, d0, d1, d2);
/*     */       
/*     */       case 3:
/*  53 */         return new EntityMinecartTNT(world, d0, d1, d2);
/*     */       
/*     */       case 4:
/*  56 */         return new EntityMinecartMobSpawner(world, d0, d1, d2);
/*     */       
/*     */       case 5:
/*  59 */         return new EntityMinecartHopper(world, d0, d1, d2);
/*     */       
/*     */       case 6:
/*  62 */         return new EntityMinecartCommandBlock(world, d0, d1, d2);
/*     */     } 
/*     */     
/*  65 */     return new EntityMinecartRideable(world, d0, d1, d2);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean g_() {
/*  70 */     return false;
/*     */   }
/*     */   
/*     */   protected void c() {
/*  74 */     this.datawatcher.a(17, new Integer(0));
/*  75 */     this.datawatcher.a(18, new Integer(1));
/*  76 */     this.datawatcher.a(19, new Float(0.0F));
/*  77 */     this.datawatcher.a(20, new ProtocolData.DualInt(0, 0));
/*  78 */     this.datawatcher.a(21, new Integer(6));
/*  79 */     this.datawatcher.a(22, Byte.valueOf((byte)0));
/*     */   }
/*     */   
/*     */   public AxisAlignedBB h(Entity entity) {
/*  83 */     return entity.S() ? entity.boundingBox : null;
/*     */   }
/*     */   
/*     */   public AxisAlignedBB J() {
/*  87 */     return null;
/*     */   }
/*     */   
/*     */   public boolean S() {
/*  91 */     return true;
/*     */   }
/*     */   
/*     */   public EntityMinecartAbstract(World world, double d0, double d1, double d2) {
/*  95 */     this(world);
/*  96 */     setPosition(d0, d1, d2);
/*  97 */     this.motX = 0.0D;
/*  98 */     this.motY = 0.0D;
/*  99 */     this.motZ = 0.0D;
/* 100 */     this.lastX = d0;
/* 101 */     this.lastY = d1;
/* 102 */     this.lastZ = d2;
/*     */     
/* 104 */     this.world.getServer().getPluginManager().callEvent((Event)new VehicleCreateEvent((Vehicle)getBukkitEntity()));
/*     */   }
/*     */   
/*     */   public double ae() {
/* 108 */     return this.length * 0.0D - 0.30000001192092896D;
/*     */   }
/*     */   
/*     */   public boolean damageEntity(DamageSource damagesource, float f) {
/* 112 */     if (!this.world.isStatic && !this.dead) {
/* 113 */       if (isInvulnerable()) {
/* 114 */         return false;
/*     */       }
/*     */       
/* 117 */       Vehicle vehicle = (Vehicle)getBukkitEntity();
/* 118 */       CraftEntity craftEntity = (damagesource.getEntity() == null) ? null : damagesource.getEntity().getBukkitEntity();
/*     */       
/* 120 */       VehicleDamageEvent event = new VehicleDamageEvent(vehicle, (Entity)craftEntity, f);
/* 121 */       this.world.getServer().getPluginManager().callEvent((Event)event);
/*     */       
/* 123 */       if (event.isCancelled()) {
/* 124 */         return true;
/*     */       }
/*     */       
/* 127 */       f = (float)event.getDamage();
/*     */ 
/*     */       
/* 130 */       j(-l());
/* 131 */       c(10);
/* 132 */       Q();
/* 133 */       setDamage(getDamage() + f * 10.0F);
/* 134 */       boolean flag = (damagesource.getEntity() instanceof EntityHuman && ((EntityHuman)damagesource.getEntity()).abilities.canInstantlyBuild);
/*     */       
/* 136 */       if (flag || getDamage() > 40.0F) {
/* 137 */         if (this.passenger != null) {
/* 138 */           this.passenger.mount(this);
/*     */         }
/*     */ 
/*     */         
/* 142 */         VehicleDestroyEvent destroyEvent = new VehicleDestroyEvent(vehicle, (Entity)craftEntity);
/* 143 */         this.world.getServer().getPluginManager().callEvent((Event)destroyEvent);
/*     */         
/* 145 */         if (destroyEvent.isCancelled()) {
/* 146 */           setDamage(40.0F);
/* 147 */           return true;
/*     */         } 
/*     */ 
/*     */         
/* 151 */         if (flag && !k_()) {
/* 152 */           die();
/*     */         } else {
/* 154 */           a(damagesource);
/*     */         } 
/*     */       } 
/*     */       
/* 158 */       return true;
/*     */     } 
/*     */     
/* 161 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(DamageSource damagesource) {
/* 166 */     die();
/* 167 */     ItemStack itemstack = new ItemStack(Items.MINECART, 1);
/*     */     
/* 169 */     if (this.b != null) {
/* 170 */       itemstack.c(this.b);
/*     */     }
/*     */     
/* 173 */     a(itemstack, 0.0F);
/*     */   }
/*     */   
/*     */   public boolean R() {
/* 177 */     return !this.dead;
/*     */   }
/*     */   
/*     */   public void die() {
/* 181 */     super.die();
/*     */   }
/*     */ 
/*     */   
/*     */   public void h() {
/* 186 */     double prevX = this.locX;
/* 187 */     double prevY = this.locY;
/* 188 */     double prevZ = this.locZ;
/* 189 */     float prevYaw = this.yaw;
/* 190 */     float prevPitch = this.pitch;
/*     */ 
/*     */     
/* 193 */     if (getType() > 0) {
/* 194 */       c(getType() - 1);
/*     */     }
/*     */     
/* 197 */     if (getDamage() > 0.0F) {
/* 198 */       setDamage(getDamage() - 1.0F);
/*     */     }
/*     */     
/* 201 */     if (this.locY < -64.0D) {
/* 202 */       G();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 207 */     if (!this.world.isStatic && this.world instanceof WorldServer) {
/* 208 */       this.world.methodProfiler.a("portal");
/* 209 */       MinecraftServer minecraftserver = ((WorldServer)this.world).getMinecraftServer();
/*     */       
/* 211 */       int i = D();
/* 212 */       if (this.an) {
/*     */         
/* 214 */         if (this.vehicle == null && this.ao++ >= i) {
/* 215 */           byte b0; this.ao = i;
/* 216 */           this.portalCooldown = ai();
/*     */ 
/*     */           
/* 219 */           if (this.world.worldProvider.dimension == -1) {
/* 220 */             b0 = 0;
/*     */           } else {
/* 222 */             b0 = -1;
/*     */           } 
/*     */           
/* 225 */           b(b0);
/*     */         } 
/*     */         
/* 228 */         this.an = false;
/*     */       } else {
/*     */         
/* 231 */         if (this.ao > 0) {
/* 232 */           this.ao -= 4;
/*     */         }
/*     */         
/* 235 */         if (this.ao < 0) {
/* 236 */           this.ao = 0;
/*     */         }
/*     */       } 
/*     */       
/* 240 */       if (this.portalCooldown > 0) {
/* 241 */         this.portalCooldown--;
/*     */       }
/*     */       
/* 244 */       this.world.methodProfiler.b();
/*     */     } 
/*     */     
/* 247 */     if (this.world.isStatic) {
/* 248 */       if (this.d > 0) {
/* 249 */         double d0 = this.locX + (this.e - this.locX) / this.d;
/* 250 */         double d1 = this.locY + (this.f - this.locY) / this.d;
/* 251 */         double d2 = this.locZ + (this.g - this.locZ) / this.d;
/* 252 */         double d3 = MathHelper.g(this.h - this.yaw);
/*     */         
/* 254 */         this.yaw = (float)(this.yaw + d3 / this.d);
/* 255 */         this.pitch = (float)(this.pitch + (this.i - this.pitch) / this.d);
/* 256 */         this.d--;
/* 257 */         setPosition(d0, d1, d2);
/* 258 */         b(this.yaw, this.pitch);
/*     */       } else {
/* 260 */         setPosition(this.locX, this.locY, this.locZ);
/* 261 */         b(this.yaw, this.pitch);
/*     */       } 
/*     */     } else {
/* 264 */       this.lastX = this.locX;
/* 265 */       this.lastY = this.locY;
/* 266 */       this.lastZ = this.locZ;
/* 267 */       this.motY -= 0.03999999910593033D;
/* 268 */       int j = MathHelper.floor(this.locX);
/*     */       
/* 270 */       int i = MathHelper.floor(this.locY);
/* 271 */       int k = MathHelper.floor(this.locZ);
/*     */       
/* 273 */       if (BlockMinecartTrackAbstract.b_(this.world, j, i - 1, k)) {
/* 274 */         i--;
/*     */       }
/*     */       
/* 277 */       double d4 = this.maxSpeed;
/* 278 */       double d5 = 0.0078125D;
/* 279 */       Block block = this.world.getType(j, i, k);
/*     */       
/* 281 */       if (BlockMinecartTrackAbstract.a(block)) {
/* 282 */         int l = this.world.getData(j, i, k);
/*     */         
/* 284 */         a(j, i, k, d4, d5, block, l);
/* 285 */         if (block == Blocks.ACTIVATOR_RAIL) {
/* 286 */           a(j, i, k, ((l & 0x8) != 0));
/*     */         }
/*     */       } else {
/* 289 */         b(d4);
/*     */       } 
/*     */       
/* 292 */       I();
/* 293 */       this.pitch = 0.0F;
/* 294 */       double d6 = this.lastX - this.locX;
/* 295 */       double d7 = this.lastZ - this.locZ;
/*     */       
/* 297 */       if (d6 * d6 + d7 * d7 > 0.001D) {
/* 298 */         this.yaw = (float)(Math.atan2(d7, d6) * 180.0D / Math.PI);
/* 299 */         if (this.a) {
/* 300 */           this.yaw += 180.0F;
/*     */         }
/*     */       } 
/*     */       
/* 304 */       double d8 = MathHelper.g(this.yaw - this.lastYaw);
/*     */       
/* 306 */       if (d8 < -170.0D || d8 >= 170.0D) {
/* 307 */         this.yaw += 180.0F;
/* 308 */         this.a = !this.a;
/*     */       } 
/*     */       
/* 311 */       b(this.yaw, this.pitch);
/*     */ 
/*     */       
/* 314 */       CraftWorld craftWorld = this.world.getWorld();
/* 315 */       Location from = new Location((World)craftWorld, prevX, prevY, prevZ, prevYaw, prevPitch);
/* 316 */       Location to = new Location((World)craftWorld, this.locX, this.locY, this.locZ, this.yaw, this.pitch);
/* 317 */       Vehicle vehicle = (Vehicle)getBukkitEntity();
/*     */       
/* 319 */       this.world.getServer().getPluginManager().callEvent((Event)new VehicleUpdateEvent(vehicle));
/*     */       
/* 321 */       if (!from.equals(to)) {
/* 322 */         this.world.getServer().getPluginManager().callEvent((Event)new VehicleMoveEvent(vehicle, from, to));
/*     */       }
/*     */ 
/*     */       
/* 326 */       List<Entity> list = this.world.getEntities(this, this.boundingBox.grow(0.20000000298023224D, 0.0D, 0.20000000298023224D));
/*     */       
/* 328 */       if (list != null && !list.isEmpty()) {
/* 329 */         for (int i1 = 0; i1 < list.size(); i1++) {
/* 330 */           Entity entity = list.get(i1);
/*     */           
/* 332 */           if (entity != this.passenger && entity.S() && entity instanceof EntityMinecartAbstract) {
/* 333 */             entity.collide(this);
/*     */           }
/*     */         } 
/*     */       }
/*     */       
/* 338 */       if (this.passenger != null && this.passenger.dead) {
/* 339 */         if (this.passenger.vehicle == this) {
/* 340 */           this.passenger.vehicle = null;
/*     */         }
/*     */         
/* 343 */         this.passenger = null;
/*     */       } 
/*     */ 
/*     */       
/* 347 */       if (this.world.spigotConfig.altHopperTicking && this instanceof EntityMinecartContainer) {
/* 348 */         int xi = MathHelper.floor(this.boundingBox.a) - 1;
/* 349 */         int yi = MathHelper.floor(this.boundingBox.b) - 1;
/* 350 */         int zi = MathHelper.floor(this.boundingBox.c) - 1;
/* 351 */         int xf = MathHelper.floor(this.boundingBox.d) + 1;
/* 352 */         int yf = MathHelper.floor(this.boundingBox.e) + 1;
/* 353 */         int zf = MathHelper.floor(this.boundingBox.f) + 1;
/* 354 */         for (int a = xi; a <= xf; a++) {
/* 355 */           for (int b = yi; b <= yf; b++) {
/* 356 */             for (int c = zi; c <= zf; c++) {
/* 357 */               TileEntity tileEntity = this.world.getTileEntity(a, b, c);
/* 358 */               if (tileEntity instanceof TileEntityHopper) {
/* 359 */                 ((TileEntityHopper)tileEntity).makeTick();
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(int i, int j, int k, boolean flag) {}
/*     */   
/*     */   protected void b(double d0) {
/* 372 */     if (this.motX < -d0) {
/* 373 */       this.motX = -d0;
/*     */     }
/*     */     
/* 376 */     if (this.motX > d0) {
/* 377 */       this.motX = d0;
/*     */     }
/*     */     
/* 380 */     if (this.motZ < -d0) {
/* 381 */       this.motZ = -d0;
/*     */     }
/*     */     
/* 384 */     if (this.motZ > d0) {
/* 385 */       this.motZ = d0;
/*     */     }
/*     */     
/* 388 */     if (this.onGround) {
/*     */       
/* 390 */       this.motX *= this.derailedX;
/* 391 */       this.motY *= this.derailedY;
/* 392 */       this.motZ *= this.derailedZ;
/*     */     } 
/*     */ 
/*     */     
/* 396 */     move(this.motX, this.motY, this.motZ);
/* 397 */     if (!this.onGround) {
/*     */       
/* 399 */       this.motX *= this.flyingX;
/* 400 */       this.motY *= this.flyingY;
/* 401 */       this.motZ *= this.flyingZ;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void a(int i, int j, int k, double d0, double d1, Block block, int l) {
/* 407 */     this.fallDistance = 0.0F;
/* 408 */     Vec3D vec3d = a(this.locX, this.locY, this.locZ);
/*     */     
/* 410 */     this.locY = j;
/* 411 */     boolean flag = false;
/* 412 */     boolean flag1 = false;
/*     */     
/* 414 */     if (block == Blocks.GOLDEN_RAIL) {
/* 415 */       flag = ((l & 0x8) != 0);
/* 416 */       flag1 = !flag;
/*     */     } 
/*     */     
/* 419 */     if (((BlockMinecartTrackAbstract)block).e()) {
/* 420 */       l &= 0x7;
/*     */     }
/*     */     
/* 423 */     if (l >= 2 && l <= 5) {
/* 424 */       this.locY = (j + 1);
/*     */     }
/*     */     
/* 427 */     if (l == 2) {
/* 428 */       this.motX -= d1;
/*     */     }
/*     */     
/* 431 */     if (l == 3) {
/* 432 */       this.motX += d1;
/*     */     }
/*     */     
/* 435 */     if (l == 4) {
/* 436 */       this.motZ += d1;
/*     */     }
/*     */     
/* 439 */     if (l == 5) {
/* 440 */       this.motZ -= d1;
/*     */     }
/*     */     
/* 443 */     int[][] aint = matrix[l];
/* 444 */     double d2 = (aint[1][0] - aint[0][0]);
/* 445 */     double d3 = (aint[1][2] - aint[0][2]);
/* 446 */     double d4 = Math.sqrt(d2 * d2 + d3 * d3);
/* 447 */     double d5 = this.motX * d2 + this.motZ * d3;
/*     */     
/* 449 */     if (d5 < 0.0D) {
/* 450 */       d2 = -d2;
/* 451 */       d3 = -d3;
/*     */     } 
/*     */     
/* 454 */     double d6 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);
/*     */     
/* 456 */     if (d6 > 2.0D) {
/* 457 */       d6 = 2.0D;
/*     */     }
/*     */     
/* 460 */     this.motX = d6 * d2 / d4;
/* 461 */     this.motZ = d6 * d3 / d4;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 467 */     if (this.passenger != null && this.passenger instanceof EntityLiving) {
/* 468 */       double d = ((EntityLiving)this.passenger).be;
/* 469 */       if (d > 0.0D) {
/* 470 */         double d14 = -Math.sin((this.passenger.yaw * 3.1415927F / 180.0F));
/* 471 */         double d15 = Math.cos((this.passenger.yaw * 3.1415927F / 180.0F));
/* 472 */         double d16 = this.motX * this.motX + this.motZ * this.motZ;
/* 473 */         if (d16 < 0.01D) {
/* 474 */           this.motX += d14 * 0.1D;
/* 475 */           this.motZ += d15 * 0.1D;
/* 476 */           flag1 = false;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 481 */     if (flag1) {
/* 482 */       double d = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);
/* 483 */       if (d < 0.03D) {
/* 484 */         this.motX *= 0.0D;
/* 485 */         this.motY *= 0.0D;
/* 486 */         this.motZ *= 0.0D;
/*     */       } else {
/* 488 */         this.motX *= 0.5D;
/* 489 */         this.motY *= 0.0D;
/* 490 */         this.motZ *= 0.5D;
/*     */       } 
/*     */     } 
/*     */     
/* 494 */     double d7 = 0.0D;
/* 495 */     double d8 = i + 0.5D + aint[0][0] * 0.5D;
/* 496 */     double d9 = k + 0.5D + aint[0][2] * 0.5D;
/* 497 */     double d10 = i + 0.5D + aint[1][0] * 0.5D;
/* 498 */     double d11 = k + 0.5D + aint[1][2] * 0.5D;
/*     */     
/* 500 */     d2 = d10 - d8;
/* 501 */     d3 = d11 - d9;
/*     */ 
/*     */ 
/*     */     
/* 505 */     if (d2 == 0.0D) {
/* 506 */       this.locX = i + 0.5D;
/* 507 */       d7 = this.locZ - k;
/* 508 */     } else if (d3 == 0.0D) {
/* 509 */       this.locZ = k + 0.5D;
/* 510 */       d7 = this.locX - i;
/*     */     } else {
/* 512 */       double d14 = this.locX - d8;
/* 513 */       double d15 = this.locZ - d9;
/* 514 */       d7 = (d14 * d2 + d15 * d3) * 2.0D;
/*     */     } 
/*     */     
/* 517 */     this.locX = d8 + d2 * d7;
/* 518 */     this.locZ = d9 + d3 * d7;
/* 519 */     setPosition(this.locX, this.locY + this.height, this.locZ);
/* 520 */     double d12 = this.motX;
/* 521 */     double d13 = this.motZ;
/* 522 */     if (this.passenger != null) {
/* 523 */       d12 *= 0.75D;
/* 524 */       d13 *= 0.75D;
/*     */     } 
/*     */     
/* 527 */     if (d12 < -d0) {
/* 528 */       d12 = -d0;
/*     */     }
/*     */     
/* 531 */     if (d12 > d0) {
/* 532 */       d12 = d0;
/*     */     }
/*     */     
/* 535 */     if (d13 < -d0) {
/* 536 */       d13 = -d0;
/*     */     }
/*     */     
/* 539 */     if (d13 > d0) {
/* 540 */       d13 = d0;
/*     */     }
/*     */     
/* 543 */     move(d12, 0.0D, d13);
/* 544 */     if (aint[0][1] != 0 && MathHelper.floor(this.locX) - i == aint[0][0] && MathHelper.floor(this.locZ) - k == aint[0][2]) {
/* 545 */       setPosition(this.locX, this.locY + aint[0][1], this.locZ);
/* 546 */     } else if (aint[1][1] != 0 && MathHelper.floor(this.locX) - i == aint[1][0] && MathHelper.floor(this.locZ) - k == aint[1][2]) {
/* 547 */       setPosition(this.locX, this.locY + aint[1][1], this.locZ);
/*     */     } 
/*     */     
/* 550 */     i();
/* 551 */     Vec3D vec3d1 = a(this.locX, this.locY, this.locZ);
/*     */     
/* 553 */     if (vec3d1 != null && vec3d != null) {
/* 554 */       double d14 = (vec3d.b - vec3d1.b) * 0.05D;
/*     */       
/* 556 */       d6 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);
/* 557 */       if (d6 > 0.0D) {
/* 558 */         this.motX = this.motX / d6 * (d6 + d14);
/* 559 */         this.motZ = this.motZ / d6 * (d6 + d14);
/*     */       } 
/*     */       
/* 562 */       setPosition(this.locX, vec3d1.b, this.locZ);
/*     */     } 
/*     */     
/* 565 */     int i1 = MathHelper.floor(this.locX);
/* 566 */     int j1 = MathHelper.floor(this.locZ);
/*     */     
/* 568 */     if (i1 != i || j1 != k) {
/* 569 */       d6 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);
/* 570 */       this.motX = d6 * (i1 - i);
/* 571 */       this.motZ = d6 * (j1 - k);
/*     */     } 
/*     */     
/* 574 */     if (flag) {
/* 575 */       double d15 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);
/*     */       
/* 577 */       if (d15 > 0.01D) {
/* 578 */         double d16 = 0.06D;
/*     */         
/* 580 */         this.motX += this.motX / d15 * d16;
/* 581 */         this.motZ += this.motZ / d15 * d16;
/* 582 */       } else if (l == 1) {
/* 583 */         if (this.world.getType(i - 1, j, k).r()) {
/* 584 */           this.motX = 0.02D;
/* 585 */         } else if (this.world.getType(i + 1, j, k).r()) {
/* 586 */           this.motX = -0.02D;
/*     */         } 
/* 588 */       } else if (l == 0) {
/* 589 */         if (this.world.getType(i, j, k - 1).r()) {
/* 590 */           this.motZ = 0.02D;
/* 591 */         } else if (this.world.getType(i, j, k + 1).r()) {
/* 592 */           this.motZ = -0.02D;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void i() {
/* 599 */     if (this.passenger != null || !this.slowWhenEmpty) {
/* 600 */       this.motX *= 0.996999979019165D;
/* 601 */       this.motY *= 0.0D;
/* 602 */       this.motZ *= 0.996999979019165D;
/*     */     } else {
/* 604 */       this.motX *= 0.9599999785423279D;
/* 605 */       this.motY *= 0.0D;
/* 606 */       this.motZ *= 0.9599999785423279D;
/*     */     } 
/*     */   }
/*     */   
/*     */   public Vec3D a(double d0, double d1, double d2) {
/* 611 */     int i = MathHelper.floor(d0);
/* 612 */     int j = MathHelper.floor(d1);
/* 613 */     int k = MathHelper.floor(d2);
/*     */     
/* 615 */     if (BlockMinecartTrackAbstract.b_(this.world, i, j - 1, k)) {
/* 616 */       j--;
/*     */     }
/*     */     
/* 619 */     Block block = this.world.getType(i, j, k);
/*     */     
/* 621 */     if (BlockMinecartTrackAbstract.a(block)) {
/* 622 */       int l = this.world.getData(i, j, k);
/*     */       
/* 624 */       d1 = j;
/* 625 */       if (((BlockMinecartTrackAbstract)block).e()) {
/* 626 */         l &= 0x7;
/*     */       }
/*     */       
/* 629 */       if (l >= 2 && l <= 5) {
/* 630 */         d1 = (j + 1);
/*     */       }
/*     */       
/* 633 */       int[][] aint = matrix[l];
/* 634 */       double d3 = 0.0D;
/* 635 */       double d4 = i + 0.5D + aint[0][0] * 0.5D;
/* 636 */       double d5 = j + 0.5D + aint[0][1] * 0.5D;
/* 637 */       double d6 = k + 0.5D + aint[0][2] * 0.5D;
/* 638 */       double d7 = i + 0.5D + aint[1][0] * 0.5D;
/* 639 */       double d8 = j + 0.5D + aint[1][1] * 0.5D;
/* 640 */       double d9 = k + 0.5D + aint[1][2] * 0.5D;
/* 641 */       double d10 = d7 - d4;
/* 642 */       double d11 = (d8 - d5) * 2.0D;
/* 643 */       double d12 = d9 - d6;
/*     */       
/* 645 */       if (d10 == 0.0D) {
/* 646 */         d0 = i + 0.5D;
/* 647 */         d3 = d2 - k;
/* 648 */       } else if (d12 == 0.0D) {
/* 649 */         d2 = k + 0.5D;
/* 650 */         d3 = d0 - i;
/*     */       } else {
/* 652 */         double d13 = d0 - d4;
/* 653 */         double d14 = d2 - d6;
/*     */         
/* 655 */         d3 = (d13 * d10 + d14 * d12) * 2.0D;
/*     */       } 
/*     */       
/* 658 */       d0 = d4 + d10 * d3;
/* 659 */       d1 = d5 + d11 * d3;
/* 660 */       d2 = d6 + d12 * d3;
/* 661 */       if (d11 < 0.0D) {
/* 662 */         d1++;
/*     */       }
/*     */       
/* 665 */       if (d11 > 0.0D) {
/* 666 */         d1 += 0.5D;
/*     */       }
/*     */       
/* 669 */       return Vec3D.a(d0, d1, d2);
/*     */     } 
/* 671 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void a(NBTTagCompound nbttagcompound) {
/* 676 */     if (nbttagcompound.getBoolean("CustomDisplayTile")) {
/* 677 */       k(nbttagcompound.getInt("DisplayTile"));
/* 678 */       l(nbttagcompound.getInt("DisplayData"));
/* 679 */       m(nbttagcompound.getInt("DisplayOffset"));
/*     */     } 
/*     */     
/* 682 */     if (nbttagcompound.hasKeyOfType("CustomName", 8) && nbttagcompound.getString("CustomName").length() > 0) {
/* 683 */       this.b = nbttagcompound.getString("CustomName");
/*     */     }
/*     */   }
/*     */   
/*     */   protected void b(NBTTagCompound nbttagcompound) {
/* 688 */     if (t()) {
/* 689 */       nbttagcompound.setBoolean("CustomDisplayTile", true);
/* 690 */       nbttagcompound.setInt("DisplayTile", (n().getMaterial() == Material.AIR) ? 0 : Block.getId(n()));
/* 691 */       nbttagcompound.setInt("DisplayData", p());
/* 692 */       nbttagcompound.setInt("DisplayOffset", r());
/*     */     } 
/*     */     
/* 695 */     if (this.b != null && this.b.length() > 0) {
/* 696 */       nbttagcompound.setString("CustomName", this.b);
/*     */     }
/*     */   }
/*     */   
/*     */   public void collide(Entity entity) {
/* 701 */     if (!this.world.isStatic && 
/* 702 */       entity != this.passenger) {
/*     */       
/* 704 */       Vehicle vehicle = (Vehicle)getBukkitEntity();
/* 705 */       CraftEntity craftEntity = (entity == null) ? null : entity.getBukkitEntity();
/*     */       
/* 707 */       VehicleEntityCollisionEvent collisionEvent = new VehicleEntityCollisionEvent(vehicle, (Entity)craftEntity);
/* 708 */       this.world.getServer().getPluginManager().callEvent((Event)collisionEvent);
/*     */       
/* 710 */       if (collisionEvent.isCancelled()) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 715 */       if (entity instanceof EntityLiving && !(entity instanceof EntityHuman) && !(entity instanceof EntityIronGolem) && m() == 0 && this.motX * this.motX + this.motZ * this.motZ > 0.01D && this.passenger == null && entity.vehicle == null) {
/* 716 */         entity.mount(this);
/*     */       }
/*     */       
/* 719 */       double d0 = entity.locX - this.locX;
/* 720 */       double d1 = entity.locZ - this.locZ;
/* 721 */       double d2 = d0 * d0 + d1 * d1;
/*     */ 
/*     */       
/* 724 */       if (d2 >= 9.999999747378752E-5D && !collisionEvent.isCollisionCancelled()) {
/* 725 */         d2 = MathHelper.sqrt(d2);
/* 726 */         d0 /= d2;
/* 727 */         d1 /= d2;
/* 728 */         double d3 = 1.0D / d2;
/*     */         
/* 730 */         if (d3 > 1.0D) {
/* 731 */           d3 = 1.0D;
/*     */         }
/*     */         
/* 734 */         d0 *= d3;
/* 735 */         d1 *= d3;
/* 736 */         d0 *= 0.10000000149011612D;
/* 737 */         d1 *= 0.10000000149011612D;
/* 738 */         d0 *= (1.0F - this.Y);
/* 739 */         d1 *= (1.0F - this.Y);
/* 740 */         d0 *= 0.5D;
/* 741 */         d1 *= 0.5D;
/* 742 */         if (entity instanceof EntityMinecartAbstract) {
/* 743 */           double d4 = entity.locX - this.locX;
/* 744 */           double d5 = entity.locZ - this.locZ;
/* 745 */           Vec3D vec3d = Vec3D.a(d4, 0.0D, d5).a();
/* 746 */           Vec3D vec3d1 = Vec3D.a(MathHelper.cos(this.yaw * 3.1415927F / 180.0F), 0.0D, MathHelper.sin(this.yaw * 3.1415927F / 180.0F)).a();
/* 747 */           double d6 = Math.abs(vec3d.b(vec3d1));
/*     */           
/* 749 */           if (d6 < 0.800000011920929D) {
/*     */             return;
/*     */           }
/*     */           
/* 753 */           double d7 = entity.motX + this.motX;
/* 754 */           double d8 = entity.motZ + this.motZ;
/*     */           
/* 756 */           if (((EntityMinecartAbstract)entity).m() == 2 && m() != 2) {
/* 757 */             this.motX *= 0.20000000298023224D;
/* 758 */             this.motZ *= 0.20000000298023224D;
/* 759 */             g(entity.motX - d0, 0.0D, entity.motZ - d1);
/* 760 */             entity.motX *= 0.949999988079071D;
/* 761 */             entity.motZ *= 0.949999988079071D;
/* 762 */           } else if (((EntityMinecartAbstract)entity).m() != 2 && m() == 2) {
/* 763 */             entity.motX *= 0.20000000298023224D;
/* 764 */             entity.motZ *= 0.20000000298023224D;
/* 765 */             entity.g(this.motX + d0, 0.0D, this.motZ + d1);
/* 766 */             this.motX *= 0.949999988079071D;
/* 767 */             this.motZ *= 0.949999988079071D;
/*     */           } else {
/* 769 */             d7 /= 2.0D;
/* 770 */             d8 /= 2.0D;
/* 771 */             this.motX *= 0.20000000298023224D;
/* 772 */             this.motZ *= 0.20000000298023224D;
/* 773 */             g(d7 - d0, 0.0D, d8 - d1);
/* 774 */             entity.motX *= 0.20000000298023224D;
/* 775 */             entity.motZ *= 0.20000000298023224D;
/* 776 */             entity.g(d7 + d0, 0.0D, d8 + d1);
/*     */           } 
/*     */         } else {
/* 779 */           g(-d0, 0.0D, -d1);
/* 780 */           entity.g(d0 / 4.0D, 0.0D, d1 / 4.0D);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDamage(float f) {
/* 788 */     this.datawatcher.watch(19, Float.valueOf(f));
/*     */   }
/*     */   
/*     */   public float getDamage() {
/* 792 */     return this.datawatcher.getFloat(19);
/*     */   }
/*     */   
/*     */   public void c(int i) {
/* 796 */     this.datawatcher.watch(17, Integer.valueOf(i));
/*     */   }
/*     */   
/*     */   public int getType() {
/* 800 */     return this.datawatcher.getInt(17);
/*     */   }
/*     */   
/*     */   public void j(int i) {
/* 804 */     this.datawatcher.watch(18, Integer.valueOf(i));
/*     */   }
/*     */   
/*     */   public int l() {
/* 808 */     return this.datawatcher.getInt(18);
/*     */   }
/*     */   
/*     */   public abstract int m();
/*     */   
/*     */   public Block n() {
/* 814 */     if (!t()) {
/* 815 */       return o();
/*     */     }
/* 817 */     int i = getDataWatcher().getInt(20) & 0xFFFF;
/*     */     
/* 819 */     return Block.getById(i);
/*     */   }
/*     */ 
/*     */   
/*     */   public Block o() {
/* 824 */     return Blocks.AIR;
/*     */   }
/*     */   
/*     */   public int p() {
/* 828 */     return !t() ? q() : (getDataWatcher().getInt(20) >> 16);
/*     */   }
/*     */   
/*     */   public int q() {
/* 832 */     return 0;
/*     */   }
/*     */   
/*     */   public int r() {
/* 836 */     return !t() ? s() : getDataWatcher().getInt(21);
/*     */   }
/*     */   
/*     */   public int s() {
/* 840 */     return 6;
/*     */   }
/*     */ 
/*     */   
/*     */   public void k(int i) {
/* 845 */     ProtocolData.DualInt val = this.datawatcher.getDualInt(20);
/* 846 */     val.value = Integer.valueOf(i & 0xFFFF | p() << 16).intValue();
/* 847 */     val.value2 = Integer.valueOf(i & 0xFFFF | p() << 12).intValue();
/* 848 */     getDataWatcher().watch(20, val);
/*     */     
/* 850 */     a(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void l(int i) {
/* 855 */     ProtocolData.DualInt val = this.datawatcher.getDualInt(20);
/* 856 */     val.value = Integer.valueOf(Block.getId(n()) & 0xFFFF | i << 16).intValue();
/* 857 */     val.value2 = Integer.valueOf(Block.getId(n()) & 0xFFFF | i << 12).intValue();
/* 858 */     getDataWatcher().watch(20, val);
/*     */     
/* 860 */     a(true);
/*     */   }
/*     */   
/*     */   public void m(int i) {
/* 864 */     getDataWatcher().watch(21, Integer.valueOf(i));
/* 865 */     a(true);
/*     */   }
/*     */   
/*     */   public boolean t() {
/* 869 */     return (getDataWatcher().getByte(22) == 1);
/*     */   }
/*     */   
/*     */   public void a(boolean flag) {
/* 873 */     getDataWatcher().watch(22, Byte.valueOf((byte)(flag ? 1 : 0)));
/*     */   }
/*     */   
/*     */   public void a(String s) {
/* 877 */     this.b = s;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 881 */     return (this.b != null) ? this.b : super.getName();
/*     */   }
/*     */   
/*     */   public boolean k_() {
/* 885 */     return (this.b != null);
/*     */   }
/*     */   
/*     */   public String u() {
/* 889 */     return this.b;
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector getFlyingVelocityMod() {
/* 894 */     return new Vector(this.flyingX, this.flyingY, this.flyingZ);
/*     */   }
/*     */   
/*     */   public void setFlyingVelocityMod(Vector flying) {
/* 898 */     this.flyingX = flying.getX();
/* 899 */     this.flyingY = flying.getY();
/* 900 */     this.flyingZ = flying.getZ();
/*     */   }
/*     */   
/*     */   public Vector getDerailedVelocityMod() {
/* 904 */     return new Vector(this.derailedX, this.derailedY, this.derailedZ);
/*     */   }
/*     */   
/*     */   public void setDerailedVelocityMod(Vector derailed) {
/* 908 */     this.derailedX = derailed.getX();
/* 909 */     this.derailedY = derailed.getY();
/* 910 */     this.derailedZ = derailed.getZ();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityMinecartAbstract.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
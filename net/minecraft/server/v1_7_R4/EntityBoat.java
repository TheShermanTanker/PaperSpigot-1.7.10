/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.Vehicle;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.vehicle.VehicleCreateEvent;
/*     */ import org.bukkit.event.vehicle.VehicleDamageEvent;
/*     */ import org.bukkit.event.vehicle.VehicleDestroyEvent;
/*     */ import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;
/*     */ import org.bukkit.event.vehicle.VehicleMoveEvent;
/*     */ import org.bukkit.event.vehicle.VehicleUpdateEvent;
/*     */ 
/*     */ public class EntityBoat
/*     */   extends Entity
/*     */ {
/*     */   private boolean a;
/*     */   private double b;
/*     */   private int c;
/*     */   private double d;
/*  27 */   public double maxSpeed = 0.4D; private double e; private double f; private double g; private double h;
/*  28 */   public double occupiedDeceleration = 0.2D;
/*  29 */   public double unoccupiedDeceleration = -1.0D;
/*     */   
/*     */   public boolean landBoats = false;
/*     */   
/*     */   public void collide(Entity entity) {
/*  34 */     CraftEntity craftEntity = (entity == null) ? null : entity.getBukkitEntity();
/*     */     
/*  36 */     VehicleEntityCollisionEvent event = new VehicleEntityCollisionEvent((Vehicle)getBukkitEntity(), (Entity)craftEntity);
/*  37 */     this.world.getServer().getPluginManager().callEvent((Event)event);
/*     */     
/*  39 */     if (event.isCancelled()) {
/*     */       return;
/*     */     }
/*     */     
/*  43 */     super.collide(entity);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityBoat(World world) {
/*  48 */     super(world);
/*  49 */     this.a = true;
/*  50 */     this.b = 0.07D;
/*  51 */     this.k = true;
/*  52 */     a(1.5F, 0.6F);
/*  53 */     this.height = this.length / 2.0F;
/*     */   }
/*     */   
/*     */   protected boolean g_() {
/*  57 */     return false;
/*     */   }
/*     */   
/*     */   protected void c() {
/*  61 */     this.datawatcher.a(17, new Integer(0));
/*  62 */     this.datawatcher.a(18, new Integer(1));
/*  63 */     this.datawatcher.a(19, new Float(0.0F));
/*     */   }
/*     */   
/*     */   public AxisAlignedBB h(Entity entity) {
/*  67 */     return entity.boundingBox;
/*     */   }
/*     */   
/*     */   public AxisAlignedBB J() {
/*  71 */     return this.boundingBox;
/*     */   }
/*     */   
/*     */   public boolean S() {
/*  75 */     return true;
/*     */   }
/*     */   
/*     */   public EntityBoat(World world, double d0, double d1, double d2) {
/*  79 */     this(world);
/*  80 */     setPosition(d0, d1 + this.height, d2);
/*  81 */     this.motX = 0.0D;
/*  82 */     this.motY = 0.0D;
/*  83 */     this.motZ = 0.0D;
/*  84 */     this.lastX = d0;
/*  85 */     this.lastY = d1;
/*  86 */     this.lastZ = d2;
/*     */     
/*  88 */     this.world.getServer().getPluginManager().callEvent((Event)new VehicleCreateEvent((Vehicle)getBukkitEntity()));
/*     */   }
/*     */   
/*     */   public double ae() {
/*  92 */     return this.length * 0.0D - 0.30000001192092896D;
/*     */   }
/*     */   
/*     */   public boolean damageEntity(DamageSource damagesource, float f) {
/*  96 */     if (isInvulnerable())
/*  97 */       return false; 
/*  98 */     if (!this.world.isStatic && !this.dead) {
/*     */       
/* 100 */       Vehicle vehicle = (Vehicle)getBukkitEntity();
/* 101 */       CraftEntity craftEntity = (damagesource.getEntity() == null) ? null : damagesource.getEntity().getBukkitEntity();
/*     */       
/* 103 */       VehicleDamageEvent event = new VehicleDamageEvent(vehicle, (Entity)craftEntity, f);
/* 104 */       this.world.getServer().getPluginManager().callEvent((Event)event);
/*     */       
/* 106 */       if (event.isCancelled()) {
/* 107 */         return true;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 112 */       c(-i());
/* 113 */       a(10);
/* 114 */       setDamage(getDamage() + f * 10.0F);
/* 115 */       Q();
/* 116 */       boolean flag = (damagesource.getEntity() instanceof EntityHuman && ((EntityHuman)damagesource.getEntity()).abilities.canInstantlyBuild);
/*     */       
/* 118 */       if (flag || getDamage() > 40.0F) {
/*     */         
/* 120 */         VehicleDestroyEvent destroyEvent = new VehicleDestroyEvent(vehicle, (Entity)craftEntity);
/* 121 */         this.world.getServer().getPluginManager().callEvent((Event)destroyEvent);
/*     */         
/* 123 */         if (destroyEvent.isCancelled()) {
/* 124 */           setDamage(40.0F);
/* 125 */           return true;
/*     */         } 
/*     */ 
/*     */         
/* 129 */         if (this.passenger != null) {
/* 130 */           this.passenger.mount(this);
/*     */         }
/*     */         
/* 133 */         if (!flag) {
/* 134 */           a(Items.BOAT, 1, 0.0F);
/*     */         }
/*     */         
/* 137 */         die();
/*     */       } 
/*     */       
/* 140 */       return true;
/*     */     } 
/* 142 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean R() {
/* 147 */     return !this.dead;
/*     */   }
/*     */ 
/*     */   
/*     */   public void h() {
/* 152 */     double prevX = this.locX;
/* 153 */     double prevY = this.locY;
/* 154 */     double prevZ = this.locZ;
/* 155 */     float prevYaw = this.yaw;
/* 156 */     float prevPitch = this.pitch;
/*     */ 
/*     */     
/* 159 */     super.h();
/* 160 */     if (f() > 0) {
/* 161 */       a(f() - 1);
/*     */     }
/*     */     
/* 164 */     if (getDamage() > 0.0F) {
/* 165 */       setDamage(getDamage() - 1.0F);
/*     */     }
/*     */     
/* 168 */     this.lastX = this.locX;
/* 169 */     this.lastY = this.locY;
/* 170 */     this.lastZ = this.locZ;
/* 171 */     byte b0 = 5;
/* 172 */     double d0 = 0.0D;
/*     */     
/* 174 */     for (int i = 0; i < b0; i++) {
/* 175 */       double d1 = this.boundingBox.b + (this.boundingBox.e - this.boundingBox.b) * (i + 0) / b0 - 0.125D;
/* 176 */       double d2 = this.boundingBox.b + (this.boundingBox.e - this.boundingBox.b) * (i + 1) / b0 - 0.125D;
/* 177 */       AxisAlignedBB axisalignedbb = AxisAlignedBB.a(this.boundingBox.a, d1, this.boundingBox.c, this.boundingBox.d, d2, this.boundingBox.f);
/*     */       
/* 179 */       if (this.world.b(axisalignedbb, Material.WATER)) {
/* 180 */         d0 += 1.0D / b0;
/*     */       }
/*     */     } 
/*     */     
/* 184 */     double d3 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 189 */     if (d3 > 0.26249999999999996D) {
/* 190 */       double d4 = Math.cos(this.yaw * Math.PI / 180.0D);
/* 191 */       double d5 = Math.sin(this.yaw * Math.PI / 180.0D);
/*     */       
/* 193 */       for (int j = 0; j < 1.0D + d3 * 60.0D; j++) {
/* 194 */         double d6 = (this.random.nextFloat() * 2.0F - 1.0F);
/* 195 */         double d7 = (this.random.nextInt(2) * 2 - 1) * 0.7D;
/*     */ 
/*     */ 
/*     */         
/* 199 */         if (this.random.nextBoolean()) {
/* 200 */           double d8 = this.locX - d4 * d6 * 0.8D + d5 * d7;
/* 201 */           double d9 = this.locZ - d5 * d6 * 0.8D - d4 * d7;
/* 202 */           this.world.addParticle("splash", d8, this.locY - 0.125D, d9, this.motX, this.motY, this.motZ);
/*     */         } else {
/* 204 */           double d8 = this.locX + d4 + d5 * d6 * 0.7D;
/* 205 */           double d9 = this.locZ + d5 - d4 * d6 * 0.7D;
/* 206 */           this.world.addParticle("splash", d8, this.locY - 0.125D, d9, this.motX, this.motY, this.motZ);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 214 */     if (this.world.isStatic && this.a) {
/* 215 */       if (this.c > 0) {
/* 216 */         double d4 = this.locX + (this.d - this.locX) / this.c;
/* 217 */         double d5 = this.locY + (this.e - this.locY) / this.c;
/* 218 */         double d10 = this.locZ + (this.f - this.locZ) / this.c;
/* 219 */         double d11 = MathHelper.g(this.g - this.yaw);
/* 220 */         this.yaw = (float)(this.yaw + d11 / this.c);
/* 221 */         this.pitch = (float)(this.pitch + (this.h - this.pitch) / this.c);
/* 222 */         this.c--;
/* 223 */         setPosition(d4, d5, d10);
/* 224 */         b(this.yaw, this.pitch);
/*     */       } else {
/* 226 */         double d4 = this.locX + this.motX;
/* 227 */         double d5 = this.locY + this.motY;
/* 228 */         double d10 = this.locZ + this.motZ;
/* 229 */         setPosition(d4, d5, d10);
/* 230 */         if (this.onGround) {
/* 231 */           this.motX *= 0.5D;
/* 232 */           this.motY *= 0.5D;
/* 233 */           this.motZ *= 0.5D;
/*     */         } 
/*     */         
/* 236 */         this.motX *= 0.9900000095367432D;
/* 237 */         this.motY *= 0.949999988079071D;
/* 238 */         this.motZ *= 0.9900000095367432D;
/*     */       } 
/*     */     } else {
/* 241 */       if (d0 < 1.0D) {
/* 242 */         double d = d0 * 2.0D - 1.0D;
/* 243 */         this.motY += 0.03999999910593033D * d;
/*     */       } else {
/* 245 */         if (this.motY < 0.0D) {
/* 246 */           this.motY /= 2.0D;
/*     */         }
/*     */         
/* 249 */         this.motY += 0.007000000216066837D;
/*     */       } 
/*     */       
/* 252 */       if (this.passenger != null && this.passenger instanceof EntityLiving) {
/* 253 */         EntityLiving entityliving = (EntityLiving)this.passenger;
/* 254 */         float f = this.passenger.yaw + -entityliving.bd * 90.0F;
/*     */         
/* 256 */         this.motX += -Math.sin((f * 3.1415927F / 180.0F)) * this.b * entityliving.be * 0.05000000074505806D;
/* 257 */         this.motZ += Math.cos((f * 3.1415927F / 180.0F)) * this.b * entityliving.be * 0.05000000074505806D;
/*     */       
/*     */       }
/* 260 */       else if (this.unoccupiedDeceleration >= 0.0D) {
/* 261 */         this.motX *= this.unoccupiedDeceleration;
/* 262 */         this.motZ *= this.unoccupiedDeceleration;
/*     */         
/* 264 */         if (this.motX <= 1.0E-5D) {
/* 265 */           this.motX = 0.0D;
/*     */         }
/* 267 */         if (this.motZ <= 1.0E-5D) {
/* 268 */           this.motZ = 0.0D;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 273 */       double d4 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);
/* 274 */       if (d4 > 0.35D) {
/* 275 */         double d = 0.35D / d4;
/* 276 */         this.motX *= d;
/* 277 */         this.motZ *= d;
/* 278 */         d4 = 0.35D;
/*     */       } 
/*     */       
/* 281 */       if (d4 > d3 && this.b < 0.35D) {
/* 282 */         this.b += (0.35D - this.b) / 35.0D;
/* 283 */         if (this.b > 0.35D) {
/* 284 */           this.b = 0.35D;
/*     */         }
/*     */       } else {
/* 287 */         this.b -= (this.b - 0.07D) / 35.0D;
/* 288 */         if (this.b < 0.07D) {
/* 289 */           this.b = 0.07D;
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 295 */       for (int k = 0; k < 4; k++) {
/* 296 */         int l = MathHelper.floor(this.locX + ((k % 2) - 0.5D) * 0.8D);
/*     */         
/* 298 */         int j = MathHelper.floor(this.locZ + ((k / 2) - 0.5D) * 0.8D);
/*     */         
/* 300 */         for (int i1 = 0; i1 < 2; i1++) {
/* 301 */           int j1 = MathHelper.floor(this.locY) + i1;
/* 302 */           Block block = this.world.getType(l, j1, j);
/*     */           
/* 304 */           if (block == Blocks.SNOW) {
/*     */             
/* 306 */             if (!CraftEventFactory.callEntityChangeBlockEvent(this, l, j1, j, Blocks.AIR, 0).isCancelled())
/*     */             
/*     */             { 
/*     */               
/* 310 */               this.world.setAir(l, j1, j);
/* 311 */               this.positionChanged = false; } 
/* 312 */           } else if (block == Blocks.WATER_LILY) {
/*     */             
/* 314 */             if (!CraftEventFactory.callEntityChangeBlockEvent(this, l, j1, j, Blocks.AIR, 0).isCancelled()) {
/*     */ 
/*     */ 
/*     */               
/* 318 */               this.world.setAir(l, j1, j, true);
/* 319 */               this.positionChanged = false;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 324 */       if (this.onGround && !this.landBoats) {
/* 325 */         this.motX *= 0.5D;
/* 326 */         this.motY *= 0.5D;
/* 327 */         this.motZ *= 0.5D;
/*     */       } 
/*     */       
/* 330 */       move(this.motX, this.motY, this.motZ);
/* 331 */       if (this.positionChanged && d3 > 0.2D) {
/* 332 */         if (!this.world.isStatic && !this.dead) {
/*     */           
/* 334 */           Vehicle vehicle1 = (Vehicle)getBukkitEntity();
/* 335 */           VehicleDestroyEvent destroyEvent = new VehicleDestroyEvent(vehicle1, null);
/* 336 */           this.world.getServer().getPluginManager().callEvent((Event)destroyEvent);
/* 337 */           if (!destroyEvent.isCancelled()) {
/* 338 */             die();
/*     */             
/* 340 */             breakNaturally();
/*     */           } 
/*     */         } 
/*     */       } else {
/*     */         
/* 345 */         this.motX *= 0.9900000095367432D;
/* 346 */         this.motY *= 0.949999988079071D;
/* 347 */         this.motZ *= 0.9900000095367432D;
/*     */       } 
/*     */       
/* 350 */       this.pitch = 0.0F;
/* 351 */       double d5 = this.yaw;
/* 352 */       double d10 = this.lastX - this.locX;
/* 353 */       double d11 = this.lastZ - this.locZ;
/* 354 */       if (d10 * d10 + d11 * d11 > 0.001D) {
/* 355 */         d5 = (float)(Math.atan2(d11, d10) * 180.0D / Math.PI);
/*     */       }
/*     */       
/* 358 */       double d12 = MathHelper.g(d5 - this.yaw);
/*     */       
/* 360 */       if (d12 > 20.0D) {
/* 361 */         d12 = 20.0D;
/*     */       }
/*     */       
/* 364 */       if (d12 < -20.0D) {
/* 365 */         d12 = -20.0D;
/*     */       }
/*     */       
/* 368 */       this.yaw = (float)(this.yaw + d12);
/* 369 */       b(this.yaw, this.pitch);
/*     */ 
/*     */       
/* 372 */       CraftServer craftServer = this.world.getServer();
/* 373 */       CraftWorld craftWorld = this.world.getWorld();
/*     */       
/* 375 */       Location from = new Location((World)craftWorld, prevX, prevY, prevZ, prevYaw, prevPitch);
/* 376 */       Location to = new Location((World)craftWorld, this.locX, this.locY, this.locZ, this.yaw, this.pitch);
/* 377 */       Vehicle vehicle = (Vehicle)getBukkitEntity();
/*     */       
/* 379 */       craftServer.getPluginManager().callEvent((Event)new VehicleUpdateEvent(vehicle));
/*     */       
/* 381 */       if (!from.equals(to)) {
/* 382 */         VehicleMoveEvent event = new VehicleMoveEvent(vehicle, from, to);
/* 383 */         craftServer.getPluginManager().callEvent((Event)event);
/*     */       } 
/*     */ 
/*     */       
/* 387 */       if (!this.world.isStatic) {
/* 388 */         List<Entity> list = this.world.getEntities(this, this.boundingBox.grow(0.20000000298023224D, 0.0D, 0.20000000298023224D));
/*     */         
/* 390 */         if (list != null && !list.isEmpty()) {
/* 391 */           for (int k1 = 0; k1 < list.size(); k1++) {
/* 392 */             Entity entity = list.get(k1);
/*     */             
/* 394 */             if (entity != this.passenger && entity.S() && entity instanceof EntityBoat) {
/* 395 */               entity.collide(this);
/*     */             }
/*     */           } 
/*     */         }
/*     */         
/* 400 */         if (this.passenger != null && this.passenger.dead) {
/* 401 */           this.passenger.vehicle = null;
/* 402 */           this.passenger = null;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void ac() {
/* 409 */     if (this.passenger != null) {
/* 410 */       double d0 = Math.cos(this.yaw * Math.PI / 180.0D) * 0.4D;
/* 411 */       double d1 = Math.sin(this.yaw * Math.PI / 180.0D) * 0.4D;
/*     */       
/* 413 */       this.passenger.setPosition(this.locX + d0, this.locY + ae() + this.passenger.ad(), this.locZ + d1);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void b(NBTTagCompound nbttagcompound) {}
/*     */   
/*     */   protected void a(NBTTagCompound nbttagcompound) {}
/*     */   
/*     */   public boolean c(EntityHuman entityhuman) {
/* 422 */     if (this.passenger != null && this.passenger instanceof EntityHuman && this.passenger != entityhuman) {
/* 423 */       return true;
/*     */     }
/* 425 */     if (!this.world.isStatic) {
/* 426 */       entityhuman.mount(this);
/*     */     }
/*     */     
/* 429 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void a(double d0, boolean flag) {
/* 434 */     int i = MathHelper.floor(this.locX);
/* 435 */     int j = MathHelper.floor(this.locY);
/* 436 */     int k = MathHelper.floor(this.locZ);
/*     */     
/* 438 */     if (flag) {
/* 439 */       if (this.fallDistance > 3.0F) {
/* 440 */         b(this.fallDistance);
/* 441 */         if (!this.world.isStatic && !this.dead) {
/*     */           
/* 443 */           Vehicle vehicle = (Vehicle)getBukkitEntity();
/* 444 */           VehicleDestroyEvent destroyEvent = new VehicleDestroyEvent(vehicle, null);
/* 445 */           this.world.getServer().getPluginManager().callEvent((Event)destroyEvent);
/* 446 */           if (!destroyEvent.isCancelled()) {
/* 447 */             die();
/*     */             
/* 449 */             breakNaturally();
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 454 */         this.fallDistance = 0.0F;
/*     */       } 
/* 456 */     } else if (this.world.getType(i, j - 1, k).getMaterial() != Material.WATER && d0 < 0.0D) {
/* 457 */       this.fallDistance = (float)(this.fallDistance - d0);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setDamage(float f) {
/* 462 */     this.datawatcher.watch(19, Float.valueOf(f));
/*     */   }
/*     */   
/*     */   public float getDamage() {
/* 466 */     return this.datawatcher.getFloat(19);
/*     */   }
/*     */   
/*     */   public void a(int i) {
/* 470 */     this.datawatcher.watch(17, Integer.valueOf(i));
/*     */   }
/*     */   
/*     */   public int f() {
/* 474 */     return this.datawatcher.getInt(17);
/*     */   }
/*     */   
/*     */   public void c(int i) {
/* 478 */     this.datawatcher.watch(18, Integer.valueOf(i));
/*     */   }
/*     */   
/*     */   public int i() {
/* 482 */     return this.datawatcher.getInt(18);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void breakNaturally() {
/* 489 */     if (this.world.paperSpigotConfig.boatsDropBoats) {
/* 490 */       a(Items.BOAT, 1, 0.0F);
/*     */     } else {
/* 492 */       int k; for (k = 0; k < 3; k++) {
/* 493 */         a(Item.getItemOf(Blocks.WOOD), 1, 0.0F);
/*     */       }
/*     */       
/* 496 */       for (k = 0; k < 2; k++)
/* 497 */         a(Items.STICK, 1, 0.0F); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityBoat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
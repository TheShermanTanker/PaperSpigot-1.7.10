/*      */ package net.minecraft.server.v1_7_R4;public abstract class Entity { private static final int CURRENT_LEVEL = 2; private static int entityCount;
/*      */   private int id;
/*      */   public double j;
/*      */   public boolean k;
/*      */   public Entity passenger;
/*      */   public Entity vehicle;
/*      */   public boolean attachedToPlayer;
/*      */   public World world;
/*      */   public double lastX;
/*      */   public double lastY;
/*      */   public double lastZ;
/*      */   public double locX;
/*      */   public double locY;
/*      */   public double locZ;
/*      */   public double motX;
/*      */   public double motY;
/*      */   public double motZ;
/*      */   public float yaw;
/*      */   public float pitch;
/*      */   public float lastYaw;
/*      */   public float lastPitch;
/*      */   public final AxisAlignedBB boundingBox;
/*      */   public boolean onGround;
/*      */   public boolean positionChanged;
/*      */   public boolean F;
/*      */   public boolean G;
/*      */   public boolean velocityChanged;
/*      */   protected boolean I;
/*      */   public boolean J;
/*      */   public boolean dead;
/*      */   public float height;
/*      */   public float width;
/*      */   public float length;
/*      */   public float O;
/*      */   public float P;
/*      */   public float Q;
/*      */   
/*      */   static boolean isLevelAtLeast(NBTTagCompound tag, int level) {
/*   39 */     return (tag.hasKey("Bukkit.updateLevel") && tag.getInt("Bukkit.updateLevel") >= level);
/*      */   }
/*      */   public float fallDistance; private int d; public double S; public double T; public double U; public float V; public float W; public boolean X; public float Y; public float Z; protected Random random; public int ticksLived; public int maxFireTicks; public int fireTicks; public boolean inWater; public int noDamageTicks; private boolean justCreated; protected boolean fireProof; protected DataWatcher datawatcher; private double g; private double h; public boolean ag; public int ah; public int ai; public int aj; public boolean ak; public boolean al; public int portalCooldown; protected boolean an; protected int ao; public int dimension; protected int aq; private boolean invulnerable; public UUID uniqueID; public EnumEntitySize as; public boolean valid; public ProjectileSource projectileSource;
/*      */   public void retrack() {
/*   43 */     EntityTracker entityTracker = ((WorldServer)this.world).getTracker();
/*   44 */     entityTracker.untrackEntity(this);
/*   45 */     entityTracker.track(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAddedToChunk() {
/*  107 */     return this.ag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean inUnloadedChunk = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean loadChunks = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  127 */   public CustomTimingsHandler tickTimer = SpigotTimings.getEntityTimings(this);
/*  128 */   public final byte activationType = ActivationRange.initializeEntityActivationType(this);
/*      */   public final boolean defaultActivationState;
/*  130 */   public long activatedTick = -2147483648L; public boolean fromMobSpawner; int numCollisions;
/*      */   protected CraftEntity bukkitEntity;
/*      */   
/*      */   public void inactiveTick() {}
/*      */   
/*      */   public int getId() {
/*  136 */     return this.id;
/*      */   }
/*      */   
/*      */   public void d(int i) {
/*  140 */     this.id = i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DataWatcher getDataWatcher() {
/*  176 */     return this.datawatcher;
/*      */   }
/*      */   
/*      */   public boolean equals(Object object) {
/*  180 */     return (object instanceof Entity) ? ((((Entity)object).id == this.id)) : false;
/*      */   }
/*      */   
/*      */   public int hashCode() {
/*  184 */     return this.id;
/*      */   }
/*      */   
/*      */   public void die() {
/*  188 */     this.dead = true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void a(float f, float f1) {
/*  194 */     if (f != this.width || f1 != this.length) {
/*  195 */       float f3 = this.width;
/*  196 */       this.width = f;
/*  197 */       this.length = f1;
/*  198 */       this.boundingBox.d = this.boundingBox.a + this.width;
/*  199 */       this.boundingBox.f = this.boundingBox.c + this.width;
/*  200 */       this.boundingBox.e = this.boundingBox.b + this.length;
/*  201 */       if (this.width > f3 && !this.justCreated && !this.world.isStatic) {
/*  202 */         move((f3 - this.width), 0.0D, (f3 - this.width));
/*      */       }
/*      */     } 
/*      */     
/*  206 */     float f2 = f % 2.0F;
/*  207 */     if (f2 < 0.375D) {
/*  208 */       this.as = EnumEntitySize.SIZE_1;
/*  209 */     } else if (f2 < 0.75D) {
/*  210 */       this.as = EnumEntitySize.SIZE_2;
/*  211 */     } else if (f2 < 1.0D) {
/*  212 */       this.as = EnumEntitySize.SIZE_3;
/*  213 */     } else if (f2 < 1.375D) {
/*  214 */       this.as = EnumEntitySize.SIZE_4;
/*  215 */     } else if (f2 < 1.75D) {
/*  216 */       this.as = EnumEntitySize.SIZE_5;
/*      */     } else {
/*  218 */       this.as = EnumEntitySize.SIZE_6;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void b(float f, float f1) {
/*  224 */     if (Float.isNaN(f)) {
/*  225 */       f = 0.0F;
/*      */     }
/*      */     
/*  228 */     if (f == Float.POSITIVE_INFINITY || f == Float.NEGATIVE_INFINITY) {
/*  229 */       if (this instanceof EntityPlayer) {
/*  230 */         this.world.getServer().getLogger().warning(((CraftPlayer)getBukkitEntity()).getName() + " was caught trying to crash the server with an invalid yaw");
/*  231 */         ((CraftPlayer)getBukkitEntity()).kickPlayer("Infinite yaw (Hacking?)");
/*      */       } 
/*  233 */       f = 0.0F;
/*      */     } 
/*      */ 
/*      */     
/*  237 */     if (Float.isNaN(f1)) {
/*  238 */       f1 = 0.0F;
/*      */     }
/*      */     
/*  241 */     if (f1 == Float.POSITIVE_INFINITY || f1 == Float.NEGATIVE_INFINITY) {
/*  242 */       if (this instanceof EntityPlayer) {
/*  243 */         this.world.getServer().getLogger().warning(((CraftPlayer)getBukkitEntity()).getName() + " was caught trying to crash the server with an invalid pitch");
/*  244 */         ((CraftPlayer)getBukkitEntity()).kickPlayer("Infinite pitch (Hacking?)");
/*      */       } 
/*  246 */       f1 = 0.0F;
/*      */     } 
/*      */ 
/*      */     
/*  250 */     this.yaw = f % 360.0F;
/*  251 */     this.pitch = f1 % 360.0F;
/*      */   }
/*      */   
/*      */   public void setPosition(double d0, double d1, double d2) {
/*  255 */     this.locX = d0;
/*  256 */     this.locY = d1;
/*  257 */     this.locZ = d2;
/*  258 */     float f = this.width / 2.0F;
/*  259 */     float f1 = this.length;
/*      */     
/*  261 */     this.boundingBox.b(d0 - f, d1 - this.height + this.V, d2 - f, d0 + f, d1 - this.height + this.V + f1, d2 + f);
/*      */   }
/*      */   
/*      */   public void h() {
/*  265 */     C();
/*      */   }
/*      */   
/*      */   public void C() {
/*  269 */     this.world.methodProfiler.a("entityBaseTick");
/*  270 */     if (this.vehicle != null && this.vehicle.dead) {
/*  271 */       this.vehicle = null;
/*      */     }
/*      */     
/*  274 */     this.O = this.P;
/*  275 */     this.lastX = this.locX;
/*  276 */     this.lastY = this.locY;
/*  277 */     this.lastZ = this.locZ;
/*  278 */     this.lastPitch = this.pitch;
/*  279 */     this.lastYaw = this.yaw;
/*      */ 
/*      */     
/*  282 */     if (!this.world.isStatic && this.world instanceof WorldServer) {
/*  283 */       this.world.methodProfiler.a("portal");
/*  284 */       MinecraftServer minecraftserver = ((WorldServer)this.world).getMinecraftServer();
/*      */       
/*  286 */       int i = D();
/*  287 */       if (this.an) {
/*      */         
/*  289 */         if (this.vehicle == null && this.ao++ >= i) {
/*  290 */           byte b0; this.ao = i;
/*  291 */           this.portalCooldown = ai();
/*      */ 
/*      */           
/*  294 */           if (this.world.worldProvider.dimension == -1) {
/*  295 */             b0 = 0;
/*      */           } else {
/*  297 */             b0 = -1;
/*      */           } 
/*      */           
/*  300 */           b(b0);
/*      */         } 
/*      */         
/*  303 */         this.an = false;
/*      */       } else {
/*      */         
/*  306 */         if (this.ao > 0) {
/*  307 */           this.ao -= 4;
/*      */         }
/*      */         
/*  310 */         if (this.ao < 0) {
/*  311 */           this.ao = 0;
/*      */         }
/*      */       } 
/*      */       
/*  315 */       if (this.portalCooldown > 0) {
/*  316 */         this.portalCooldown--;
/*      */       }
/*      */       
/*  319 */       this.world.methodProfiler.b();
/*      */     } 
/*      */     
/*  322 */     if (isSprinting() && !M()) {
/*  323 */       int j = MathHelper.floor(this.locX);
/*      */       
/*  325 */       int i = MathHelper.floor(this.locY - 0.20000000298023224D - this.height);
/*  326 */       int k = MathHelper.floor(this.locZ);
/*  327 */       Block block = this.world.getType(j, i, k);
/*      */       
/*  329 */       if (block.getMaterial() != Material.AIR) {
/*  330 */         this.world.addParticle("blockcrack_" + Block.getId(block) + "_" + this.world.getData(j, i, k), this.locX + (this.random.nextFloat() - 0.5D) * this.width, this.boundingBox.b + 0.1D, this.locZ + (this.random.nextFloat() - 0.5D) * this.width, -this.motX * 4.0D, 1.5D, -this.motZ * 4.0D);
/*      */       }
/*      */     } 
/*      */     
/*  334 */     N();
/*  335 */     if (this.world.isStatic) {
/*  336 */       this.fireTicks = 0;
/*  337 */     } else if (this.fireTicks > 0) {
/*  338 */       if (this.fireProof) {
/*  339 */         this.fireTicks -= 4;
/*  340 */         if (this.fireTicks < 0) {
/*  341 */           this.fireTicks = 0;
/*      */         }
/*      */       } else {
/*  344 */         if (this.fireTicks % 20 == 0) {
/*  345 */           damageEntity(DamageSource.BURN, 1.0F);
/*      */         }
/*      */         
/*  348 */         this.fireTicks--;
/*      */       } 
/*      */     } 
/*      */     
/*  352 */     if (P()) {
/*  353 */       E();
/*  354 */       this.fallDistance *= 0.5F;
/*      */     } 
/*      */     
/*  357 */     if (this.locY < -64.0D || paperNetherCheck()) {
/*  358 */       G();
/*      */     }
/*      */     
/*  361 */     if (!this.world.isStatic) {
/*  362 */       a(0, (this.fireTicks > 0));
/*      */     }
/*      */     
/*  365 */     this.justCreated = false;
/*  366 */     this.world.methodProfiler.b();
/*      */   }
/*      */   
/*      */   public int D() {
/*  370 */     return 0;
/*      */   }
/*      */   
/*      */   protected void E() {
/*  374 */     if (!this.fireProof) {
/*  375 */       damageEntity(DamageSource.LAVA, 4.0F);
/*      */ 
/*      */       
/*  378 */       if (this instanceof EntityLiving) {
/*  379 */         if (this.fireTicks <= 0) {
/*      */ 
/*      */           
/*  382 */           Block damager = null;
/*  383 */           CraftEntity craftEntity = getBukkitEntity();
/*  384 */           EntityCombustByBlockEvent entityCombustByBlockEvent = new EntityCombustByBlockEvent(damager, (org.bukkit.entity.Entity)craftEntity, 15);
/*  385 */           this.world.getServer().getPluginManager().callEvent((Event)entityCombustByBlockEvent);
/*      */           
/*  387 */           if (!entityCombustByBlockEvent.isCancelled()) {
/*  388 */             setOnFire(entityCombustByBlockEvent.getDuration());
/*      */           }
/*      */         } else {
/*      */           
/*  392 */           setOnFire(15);
/*      */         } 
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/*  398 */       setOnFire(15);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setOnFire(int i) {
/*  403 */     int j = i * 20;
/*      */     
/*  405 */     j = EnchantmentProtection.a(this, j);
/*  406 */     if (this.fireTicks < j) {
/*  407 */       this.fireTicks = j;
/*      */     }
/*      */   }
/*      */   
/*      */   public void extinguish() {
/*  412 */     this.fireTicks = 0;
/*      */   }
/*      */   
/*      */   protected void G() {
/*  416 */     die();
/*      */   }
/*      */   
/*      */   public boolean c(double d0, double d1, double d2) {
/*  420 */     AxisAlignedBB axisalignedbb = this.boundingBox.c(d0, d1, d2);
/*  421 */     List list = this.world.getCubes(this, axisalignedbb);
/*      */     
/*  423 */     return !list.isEmpty() ? false : (!this.world.containsLiquid(axisalignedbb));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void loadChunks() {
/*  430 */     for (int cx = (int)this.locX >> 4; cx <= (int)(this.locX + this.motX) >> 4; cx++) {
/*  431 */       for (int cz = (int)this.locZ >> 4; cz <= (int)(this.locZ + this.motZ) >> 4; cz++) {
/*  432 */         this.world.chunkProvider.getChunkAt(cx, cz);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public void move(double d0, double d1, double d2) {
/*  438 */     if (this.loadChunks) loadChunks();
/*      */ 
/*      */     
/*      */     try {
/*  442 */       I();
/*  443 */     } catch (Throwable throwable) {
/*  444 */       CrashReport crashreport = CrashReport.a(throwable, "Checking entity block collision");
/*  445 */       CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Entity being checked for collision");
/*      */       
/*  447 */       a(crashreportsystemdetails);
/*  448 */       throw new ReportedException(crashreport);
/*      */     } 
/*      */     
/*  451 */     if (d0 == 0.0D && d1 == 0.0D && d2 == 0.0D && this.vehicle == null && this.passenger == null) {
/*      */       return;
/*      */     }
/*      */     
/*  455 */     SpigotTimings.entityMoveTimer.startTiming();
/*  456 */     if (this.X) {
/*  457 */       this.boundingBox.d(d0, d1, d2);
/*  458 */       this.locX = (this.boundingBox.a + this.boundingBox.d) / 2.0D;
/*  459 */       this.locY = this.boundingBox.b + this.height - this.V;
/*  460 */       this.locZ = (this.boundingBox.c + this.boundingBox.f) / 2.0D;
/*      */     } else {
/*  462 */       this.world.methodProfiler.a("move");
/*  463 */       this.V *= 0.4F;
/*  464 */       double d3 = this.locX;
/*  465 */       double d4 = this.locY;
/*  466 */       double d5 = this.locZ;
/*      */       
/*  468 */       if (this.I) {
/*  469 */         this.I = false;
/*  470 */         d0 *= 0.25D;
/*  471 */         d1 *= 0.05000000074505806D;
/*  472 */         d2 *= 0.25D;
/*  473 */         this.motX = 0.0D;
/*  474 */         this.motY = 0.0D;
/*  475 */         this.motZ = 0.0D;
/*      */       } 
/*      */       
/*  478 */       double d6 = d0;
/*  479 */       double d7 = d1;
/*  480 */       double d8 = d2;
/*  481 */       AxisAlignedBB axisalignedbb = this.boundingBox.clone();
/*  482 */       boolean flag = (this.onGround && isSneaking() && this instanceof EntityHuman);
/*      */       
/*  484 */       if (flag) {
/*      */         double d9;
/*      */         
/*  487 */         for (d9 = 0.05D; d0 != 0.0D && this.world.getCubes(this, this.boundingBox.c(d0, -1.0D, 0.0D)).isEmpty(); d6 = d0) {
/*  488 */           if (d0 < d9 && d0 >= -d9) {
/*  489 */             d0 = 0.0D;
/*  490 */           } else if (d0 > 0.0D) {
/*  491 */             d0 -= d9;
/*      */           } else {
/*  493 */             d0 += d9;
/*      */           } 
/*      */         } 
/*      */         
/*  497 */         for (; d2 != 0.0D && this.world.getCubes(this, this.boundingBox.c(0.0D, -1.0D, d2)).isEmpty(); d8 = d2) {
/*  498 */           if (d2 < d9 && d2 >= -d9) {
/*  499 */             d2 = 0.0D;
/*  500 */           } else if (d2 > 0.0D) {
/*  501 */             d2 -= d9;
/*      */           } else {
/*  503 */             d2 += d9;
/*      */           } 
/*      */         } 
/*      */         
/*  507 */         while (d0 != 0.0D && d2 != 0.0D && this.world.getCubes(this, this.boundingBox.c(d0, -1.0D, d2)).isEmpty()) {
/*  508 */           if (d0 < d9 && d0 >= -d9) {
/*  509 */             d0 = 0.0D;
/*  510 */           } else if (d0 > 0.0D) {
/*  511 */             d0 -= d9;
/*      */           } else {
/*  513 */             d0 += d9;
/*      */           } 
/*      */           
/*  516 */           if (d2 < d9 && d2 >= -d9) {
/*  517 */             d2 = 0.0D;
/*  518 */           } else if (d2 > 0.0D) {
/*  519 */             d2 -= d9;
/*      */           } else {
/*  521 */             d2 += d9;
/*      */           } 
/*      */           
/*  524 */           d6 = d0;
/*  525 */           d8 = d2;
/*      */         } 
/*      */       } 
/*      */       
/*  529 */       List<AxisAlignedBB> list = this.world.getCubes(this, this.boundingBox.a(d0, d1, d2));
/*      */       
/*  531 */       for (int i = 0; i < list.size(); i++) {
/*  532 */         d1 = ((AxisAlignedBB)list.get(i)).b(this.boundingBox, d1);
/*      */       }
/*      */       
/*  535 */       this.boundingBox.d(0.0D, d1, 0.0D);
/*  536 */       if (!this.J && d7 != d1) {
/*  537 */         d2 = 0.0D;
/*  538 */         d1 = 0.0D;
/*  539 */         d0 = 0.0D;
/*      */       } 
/*      */       
/*  542 */       boolean flag1 = (this.onGround || (d7 != d1 && d7 < 0.0D));
/*      */       
/*      */       int j;
/*      */       
/*  546 */       for (j = 0; j < list.size(); j++) {
/*  547 */         d0 = ((AxisAlignedBB)list.get(j)).a(this.boundingBox, d0);
/*      */       }
/*      */       
/*  550 */       this.boundingBox.d(d0, 0.0D, 0.0D);
/*  551 */       if (!this.J && d6 != d0) {
/*  552 */         d2 = 0.0D;
/*  553 */         d1 = 0.0D;
/*  554 */         d0 = 0.0D;
/*      */       } 
/*      */       
/*  557 */       for (j = 0; j < list.size(); j++) {
/*  558 */         d2 = ((AxisAlignedBB)list.get(j)).c(this.boundingBox, d2);
/*      */       }
/*      */       
/*  561 */       this.boundingBox.d(0.0D, 0.0D, d2);
/*  562 */       if (!this.J && d8 != d2) {
/*  563 */         d2 = 0.0D;
/*  564 */         d1 = 0.0D;
/*  565 */         d0 = 0.0D;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  573 */       if (this.W > 0.0F && flag1 && (flag || this.V < 0.05F) && (d6 != d0 || d8 != d2)) {
/*  574 */         double d9 = d0;
/*  575 */         double d13 = d1;
/*  576 */         double d14 = d2;
/*  577 */         d0 = d6;
/*  578 */         d1 = this.W;
/*  579 */         d2 = d8;
/*  580 */         AxisAlignedBB axisalignedbb1 = this.boundingBox.clone();
/*      */         
/*  582 */         this.boundingBox.d(axisalignedbb);
/*  583 */         list = this.world.getCubes(this, this.boundingBox.a(d6, d1, d8));
/*      */         int k;
/*  585 */         for (k = 0; k < list.size(); k++) {
/*  586 */           d1 = ((AxisAlignedBB)list.get(k)).b(this.boundingBox, d1);
/*      */         }
/*      */         
/*  589 */         this.boundingBox.d(0.0D, d1, 0.0D);
/*  590 */         if (!this.J && d7 != d1) {
/*  591 */           d2 = 0.0D;
/*  592 */           d1 = 0.0D;
/*  593 */           d0 = 0.0D;
/*      */         } 
/*      */         
/*  596 */         for (k = 0; k < list.size(); k++) {
/*  597 */           d0 = ((AxisAlignedBB)list.get(k)).a(this.boundingBox, d0);
/*      */         }
/*      */         
/*  600 */         this.boundingBox.d(d0, 0.0D, 0.0D);
/*  601 */         if (!this.J && d6 != d0) {
/*  602 */           d2 = 0.0D;
/*  603 */           d1 = 0.0D;
/*  604 */           d0 = 0.0D;
/*      */         } 
/*      */         
/*  607 */         for (k = 0; k < list.size(); k++) {
/*  608 */           d2 = ((AxisAlignedBB)list.get(k)).c(this.boundingBox, d2);
/*      */         }
/*      */         
/*  611 */         this.boundingBox.d(0.0D, 0.0D, d2);
/*  612 */         if (!this.J && d8 != d2) {
/*  613 */           d2 = 0.0D;
/*  614 */           d1 = 0.0D;
/*  615 */           d0 = 0.0D;
/*      */         } 
/*      */         
/*  618 */         if (!this.J && d7 != d1) {
/*  619 */           d2 = 0.0D;
/*  620 */           d1 = 0.0D;
/*  621 */           d0 = 0.0D;
/*      */         } else {
/*  623 */           d1 = -this.W;
/*      */           
/*  625 */           for (k = 0; k < list.size(); k++) {
/*  626 */             d1 = ((AxisAlignedBB)list.get(k)).b(this.boundingBox, d1);
/*      */           }
/*      */           
/*  629 */           this.boundingBox.d(0.0D, d1, 0.0D);
/*      */         } 
/*      */         
/*  632 */         if (d9 * d9 + d14 * d14 >= d0 * d0 + d2 * d2) {
/*  633 */           d0 = d9;
/*  634 */           d1 = d13;
/*  635 */           d2 = d14;
/*  636 */           this.boundingBox.d(axisalignedbb1);
/*      */         } 
/*      */       } 
/*      */       
/*  640 */       this.world.methodProfiler.b();
/*  641 */       this.world.methodProfiler.a("rest");
/*  642 */       this.locX = (this.boundingBox.a + this.boundingBox.d) / 2.0D;
/*  643 */       this.locY = this.boundingBox.b + this.height - this.V;
/*  644 */       this.locZ = (this.boundingBox.c + this.boundingBox.f) / 2.0D;
/*  645 */       this.positionChanged = (d6 != d0 || d8 != d2);
/*  646 */       this.F = (d7 != d1);
/*  647 */       this.onGround = (d7 != d1 && d7 < 0.0D);
/*  648 */       this.G = (this.positionChanged || this.F);
/*  649 */       a(d1, this.onGround);
/*  650 */       if (d6 != d0) {
/*  651 */         this.motX = 0.0D;
/*      */       }
/*      */       
/*  654 */       if (d7 != d1) {
/*  655 */         this.motY = 0.0D;
/*      */       }
/*      */       
/*  658 */       if (d8 != d2) {
/*  659 */         this.motZ = 0.0D;
/*      */       }
/*      */       
/*  662 */       double d10 = this.locX - d3;
/*  663 */       double d11 = this.locY - d4;
/*  664 */       double d12 = this.locZ - d5;
/*      */ 
/*      */       
/*  667 */       if (this.positionChanged && getBukkitEntity() instanceof Vehicle) {
/*  668 */         Vehicle vehicle = (Vehicle)getBukkitEntity();
/*  669 */         Block block = this.world.getWorld().getBlockAt(MathHelper.floor(this.locX), MathHelper.floor(this.locY - this.height), MathHelper.floor(this.locZ));
/*      */         
/*  671 */         if (d6 > d0) {
/*  672 */           block = block.getRelative(BlockFace.EAST);
/*  673 */         } else if (d6 < d0) {
/*  674 */           block = block.getRelative(BlockFace.WEST);
/*  675 */         } else if (d8 > d2) {
/*  676 */           block = block.getRelative(BlockFace.SOUTH);
/*  677 */         } else if (d8 < d2) {
/*  678 */           block = block.getRelative(BlockFace.NORTH);
/*      */         } 
/*      */         
/*  681 */         VehicleBlockCollisionEvent event = new VehicleBlockCollisionEvent(vehicle, block);
/*  682 */         this.world.getServer().getPluginManager().callEvent((Event)event);
/*      */       } 
/*      */ 
/*      */       
/*  686 */       if (g_() && !flag && this.vehicle == null) {
/*  687 */         int l = MathHelper.floor(this.locX);
/*      */         
/*  689 */         int k = MathHelper.floor(this.locY - 0.20000000298023224D - this.height);
/*  690 */         int i1 = MathHelper.floor(this.locZ);
/*  691 */         Block block = this.world.getType(l, k, i1);
/*  692 */         int j1 = this.world.getType(l, k - 1, i1).b();
/*      */         
/*  694 */         if (j1 == 11 || j1 == 32 || j1 == 21) {
/*  695 */           block = this.world.getType(l, k - 1, i1);
/*      */         }
/*      */         
/*  698 */         if (block != Blocks.LADDER) {
/*  699 */           d11 = 0.0D;
/*      */         }
/*      */         
/*  702 */         this.P = (float)(this.P + MathHelper.sqrt(d10 * d10 + d12 * d12) * 0.6D);
/*  703 */         this.Q = (float)(this.Q + MathHelper.sqrt(d10 * d10 + d11 * d11 + d12 * d12) * 0.6D);
/*  704 */         if (this.Q > this.d && block.getMaterial() != Material.AIR) {
/*  705 */           this.d = (int)this.Q + 1;
/*  706 */           if (M()) {
/*  707 */             float f = MathHelper.sqrt(this.motX * this.motX * 0.20000000298023224D + this.motY * this.motY + this.motZ * this.motZ * 0.20000000298023224D) * 0.35F;
/*      */             
/*  709 */             if (f > 1.0F) {
/*  710 */               f = 1.0F;
/*      */             }
/*      */             
/*  713 */             makeSound(H(), f, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
/*      */           } 
/*      */           
/*  716 */           a(l, k, i1, block);
/*  717 */           block.b(this.world, l, k, i1, this);
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  734 */       boolean flag2 = L();
/*      */       
/*  736 */       if (this.world.e(this.boundingBox.shrink(0.001D, 0.001D, 0.001D))) {
/*  737 */         burn(1.0F);
/*  738 */         if (!flag2) {
/*  739 */           this.fireTicks++;
/*      */           
/*  741 */           if (this.fireTicks <= 0) {
/*  742 */             EntityCombustEvent event = new EntityCombustEvent((org.bukkit.entity.Entity)getBukkitEntity(), 8);
/*  743 */             this.world.getServer().getPluginManager().callEvent((Event)event);
/*      */             
/*  745 */             if (!event.isCancelled()) {
/*  746 */               setOnFire(event.getDuration());
/*      */             }
/*      */           } else {
/*      */             
/*  750 */             setOnFire(8);
/*      */           } 
/*      */         } 
/*  753 */       } else if (this.fireTicks <= 0) {
/*  754 */         this.fireTicks = -this.maxFireTicks;
/*      */       } 
/*      */       
/*  757 */       if (flag2 && this.fireTicks > 0) {
/*  758 */         makeSound("random.fizz", 0.7F, 1.6F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
/*  759 */         this.fireTicks = -this.maxFireTicks;
/*      */       } 
/*      */       
/*  762 */       this.world.methodProfiler.b();
/*      */     } 
/*  764 */     SpigotTimings.entityMoveTimer.stopTiming();
/*      */   }
/*      */   
/*      */   protected String H() {
/*  768 */     return "game.neutral.swim";
/*      */   }
/*      */   
/*      */   protected void I() {
/*  772 */     int i = MathHelper.floor(this.boundingBox.a + 0.001D);
/*  773 */     int j = MathHelper.floor(this.boundingBox.b + 0.001D);
/*  774 */     int k = MathHelper.floor(this.boundingBox.c + 0.001D);
/*  775 */     int l = MathHelper.floor(this.boundingBox.d - 0.001D);
/*  776 */     int i1 = MathHelper.floor(this.boundingBox.e - 0.001D);
/*  777 */     int j1 = MathHelper.floor(this.boundingBox.f - 0.001D);
/*      */     
/*  779 */     if (this.world.b(i, j, k, l, i1, j1)) {
/*  780 */       for (int k1 = i; k1 <= l; k1++) {
/*  781 */         for (int l1 = j; l1 <= i1; l1++) {
/*  782 */           for (int i2 = k; i2 <= j1; i2++) {
/*  783 */             Block block = this.world.getType(k1, l1, i2);
/*      */             
/*      */             try {
/*  786 */               block.a(this.world, k1, l1, i2, this);
/*  787 */             } catch (Throwable throwable) {
/*  788 */               CrashReport crashreport = CrashReport.a(throwable, "Colliding entity with block");
/*  789 */               CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Block being collided with");
/*      */               
/*  791 */               CrashReportSystemDetails.a(crashreportsystemdetails, k1, l1, i2, block, this.world.getData(k1, l1, i2));
/*  792 */               throw new ReportedException(crashreport);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   protected void a(int i, int j, int k, Block block) {
/*  801 */     StepSound stepsound = block.stepSound;
/*      */     
/*  803 */     if (this.world.getType(i, j + 1, k) == Blocks.SNOW) {
/*  804 */       stepsound = Blocks.SNOW.stepSound;
/*  805 */       makeSound(stepsound.getStepSound(), stepsound.getVolume1() * 0.15F, stepsound.getVolume2());
/*  806 */     } else if (!block.getMaterial().isLiquid()) {
/*  807 */       makeSound(stepsound.getStepSound(), stepsound.getVolume1() * 0.15F, stepsound.getVolume2());
/*      */     } 
/*      */   }
/*      */   
/*      */   public void makeSound(String s, float f, float f1) {
/*  812 */     this.world.makeSound(this, s, f, f1);
/*      */   }
/*      */   
/*      */   protected boolean g_() {
/*  816 */     return true;
/*      */   }
/*      */   
/*      */   protected void a(double d0, boolean flag) {
/*  820 */     if (flag) {
/*  821 */       if (this.fallDistance > 0.0F) {
/*  822 */         b(this.fallDistance);
/*  823 */         this.fallDistance = 0.0F;
/*      */       } 
/*  825 */     } else if (d0 < 0.0D) {
/*  826 */       this.fallDistance = (float)(this.fallDistance - d0);
/*      */     } 
/*      */   }
/*      */   
/*      */   public AxisAlignedBB J() {
/*  831 */     return null;
/*      */   }
/*      */   
/*      */   protected void burn(float i) {
/*  835 */     if (!this.fireProof) {
/*  836 */       damageEntity(DamageSource.FIRE, i);
/*      */     }
/*      */   }
/*      */   
/*      */   public final boolean isFireproof() {
/*  841 */     return this.fireProof;
/*      */   }
/*      */   
/*      */   protected void b(float f) {
/*  845 */     if (this.passenger != null) {
/*  846 */       this.passenger.b(f);
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean L() {
/*  851 */     return (this.inWater || this.world.isRainingAt(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ)) || this.world.isRainingAt(MathHelper.floor(this.locX), MathHelper.floor(this.locY + this.length), MathHelper.floor(this.locZ)));
/*      */   }
/*      */   
/*      */   public boolean M() {
/*  855 */     return this.inWater;
/*      */   }
/*      */   
/*      */   public boolean N() {
/*  859 */     if (this.world.a(this.boundingBox.grow(0.0D, -0.4000000059604645D, 0.0D).shrink(0.001D, 0.001D, 0.001D), Material.WATER, this)) {
/*  860 */       if (!this.inWater && !this.justCreated) {
/*  861 */         float f = MathHelper.sqrt(this.motX * this.motX * 0.20000000298023224D + this.motY * this.motY + this.motZ * this.motZ * 0.20000000298023224D) * 0.2F;
/*      */         
/*  863 */         if (f > 1.0F) {
/*  864 */           f = 1.0F;
/*      */         }
/*      */         
/*  867 */         makeSound(O(), f, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
/*  868 */         float f1 = MathHelper.floor(this.boundingBox.b);
/*      */ 
/*      */         
/*      */         int i;
/*      */ 
/*      */         
/*  874 */         for (i = 0; i < 1.0F + this.width * 20.0F; i++) {
/*  875 */           float f2 = (this.random.nextFloat() * 2.0F - 1.0F) * this.width;
/*  876 */           float f3 = (this.random.nextFloat() * 2.0F - 1.0F) * this.width;
/*  877 */           this.world.addParticle("bubble", this.locX + f2, (f1 + 1.0F), this.locZ + f3, this.motX, this.motY - (this.random.nextFloat() * 0.2F), this.motZ);
/*      */         } 
/*      */         
/*  880 */         for (i = 0; i < 1.0F + this.width * 20.0F; i++) {
/*  881 */           float f2 = (this.random.nextFloat() * 2.0F - 1.0F) * this.width;
/*  882 */           float f3 = (this.random.nextFloat() * 2.0F - 1.0F) * this.width;
/*  883 */           this.world.addParticle("splash", this.locX + f2, (f1 + 1.0F), this.locZ + f3, this.motX, this.motY, this.motZ);
/*      */         } 
/*      */       } 
/*      */       
/*  887 */       this.fallDistance = 0.0F;
/*  888 */       this.inWater = true;
/*  889 */       this.fireTicks = 0;
/*      */     } else {
/*  891 */       this.inWater = false;
/*      */     } 
/*      */     
/*  894 */     return this.inWater;
/*      */   }
/*      */   
/*      */   protected String O() {
/*  898 */     return "game.neutral.swim.splash";
/*      */   }
/*      */   
/*      */   public boolean a(Material material) {
/*  902 */     double d0 = this.locY + getHeadHeight();
/*  903 */     int i = MathHelper.floor(this.locX);
/*  904 */     int j = MathHelper.d(MathHelper.floor(d0));
/*  905 */     int k = MathHelper.floor(this.locZ);
/*  906 */     Block block = this.world.getType(i, j, k);
/*      */     
/*  908 */     if (block.getMaterial() == material) {
/*  909 */       float f = BlockFluids.b(this.world.getData(i, j, k)) - 0.11111111F;
/*  910 */       float f1 = (j + 1) - f;
/*      */       
/*  912 */       return (d0 < f1);
/*      */     } 
/*  914 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public float getHeadHeight() {
/*  919 */     return 0.0F;
/*      */   }
/*      */   
/*      */   public boolean P() {
/*  923 */     return this.world.a(this.boundingBox.grow(-0.10000000149011612D, -0.4000000059604645D, -0.10000000149011612D), Material.LAVA);
/*      */   }
/*      */   
/*      */   public void a(float f, float f1, float f2) {
/*  927 */     float f3 = f * f + f1 * f1;
/*      */     
/*  929 */     if (f3 >= 1.0E-4F) {
/*  930 */       f3 = MathHelper.c(f3);
/*  931 */       if (f3 < 1.0F) {
/*  932 */         f3 = 1.0F;
/*      */       }
/*      */       
/*  935 */       f3 = f2 / f3;
/*  936 */       f *= f3;
/*  937 */       f1 *= f3;
/*  938 */       float f4 = MathHelper.sin(this.yaw * 3.1415927F / 180.0F);
/*  939 */       float f5 = MathHelper.cos(this.yaw * 3.1415927F / 180.0F);
/*      */       
/*  941 */       this.motX += (f * f5 - f1 * f4);
/*  942 */       this.motZ += (f1 * f5 + f * f4);
/*      */     } 
/*      */   }
/*      */   
/*      */   public float d(float f) {
/*  947 */     int i = MathHelper.floor(this.locX);
/*  948 */     int j = MathHelper.floor(this.locZ);
/*      */     
/*  950 */     if (this.world.isLoaded(i, 0, j)) {
/*  951 */       double d0 = (this.boundingBox.e - this.boundingBox.b) * 0.66D;
/*  952 */       int k = MathHelper.floor(this.locY - this.height + d0);
/*      */       
/*  954 */       return this.world.n(i, k, j);
/*      */     } 
/*  956 */     return 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void spawnIn(World world) {
/*  962 */     if (world == null) {
/*  963 */       die();
/*  964 */       this.world = ((CraftWorld)Bukkit.getServer().getWorlds().get(0)).getHandle();
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  969 */     this.world = world;
/*      */   }
/*      */   
/*      */   public void setLocation(double d0, double d1, double d2, float f, float f1) {
/*  973 */     this.lastX = this.locX = d0;
/*  974 */     this.lastY = this.locY = d1;
/*  975 */     this.lastZ = this.locZ = d2;
/*  976 */     this.lastYaw = this.yaw = f;
/*  977 */     this.lastPitch = this.pitch = f1;
/*  978 */     this.V = 0.0F;
/*  979 */     double d3 = (this.lastYaw - f);
/*      */     
/*  981 */     if (d3 < -180.0D) {
/*  982 */       this.lastYaw += 360.0F;
/*      */     }
/*      */     
/*  985 */     if (d3 >= 180.0D) {
/*  986 */       this.lastYaw -= 360.0F;
/*      */     }
/*      */     
/*  989 */     setPosition(this.locX, this.locY, this.locZ);
/*  990 */     b(f, f1);
/*      */   }
/*      */   
/*      */   public void setPositionRotation(double d0, double d1, double d2, float f, float f1) {
/*  994 */     this.S = this.lastX = this.locX = d0;
/*  995 */     this.T = this.lastY = this.locY = d1 + this.height;
/*  996 */     this.U = this.lastZ = this.locZ = d2;
/*  997 */     this.yaw = f;
/*  998 */     this.pitch = f1;
/*  999 */     setPosition(this.locX, this.locY, this.locZ);
/*      */   }
/*      */   
/*      */   public float e(Entity entity) {
/* 1003 */     float f = (float)(this.locX - entity.locX);
/* 1004 */     float f1 = (float)(this.locY - entity.locY);
/* 1005 */     float f2 = (float)(this.locZ - entity.locZ);
/*      */     
/* 1007 */     return MathHelper.c(f * f + f1 * f1 + f2 * f2);
/*      */   }
/*      */   
/*      */   public double e(double d0, double d1, double d2) {
/* 1011 */     double d3 = this.locX - d0;
/* 1012 */     double d4 = this.locY - d1;
/* 1013 */     double d5 = this.locZ - d2;
/*      */     
/* 1015 */     return d3 * d3 + d4 * d4 + d5 * d5;
/*      */   }
/*      */   
/*      */   public double f(double d0, double d1, double d2) {
/* 1019 */     double d3 = this.locX - d0;
/* 1020 */     double d4 = this.locY - d1;
/* 1021 */     double d5 = this.locZ - d2;
/*      */     
/* 1023 */     return MathHelper.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
/*      */   }
/*      */   
/*      */   public double f(Entity entity) {
/* 1027 */     double d0 = this.locX - entity.locX;
/* 1028 */     double d1 = this.locY - entity.locY;
/* 1029 */     double d2 = this.locZ - entity.locZ;
/*      */     
/* 1031 */     return d0 * d0 + d1 * d1 + d2 * d2;
/*      */   }
/*      */   
/*      */   public void b_(EntityHuman entityhuman) {}
/*      */   
/* 1036 */   public Entity(World world) { this.numCollisions = 0; this.id = entityCount++; this.j = 1.0D; this.boundingBox = AxisAlignedBB.a(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D); this.J = true; this.width = 0.6F; this.length = 1.8F; this.d = 1; this.random = new Random(); this.maxFireTicks = 1; this.justCreated = true; this.uniqueID = new UUID(this.random.nextLong(), this.random.nextLong()); this.as = EnumEntitySize.SIZE_2; this.world = world; setPosition(0.0D, 0.0D, 0.0D); if (world != null) { this.dimension = world.worldProvider.dimension; this.defaultActivationState = ActivationRange.initializeEntityActivationState(this, world.spigotConfig); }
/*      */     else { this.defaultActivationState = false; }
/* 1038 */      this.datawatcher = new DataWatcher(this); this.datawatcher.a(0, Byte.valueOf((byte)0)); this.datawatcher.a(1, Short.valueOf((short)300)); c(); } public void collide(Entity entity) { if (entity.passenger != this && entity.vehicle != this) {
/* 1039 */       double d0 = entity.locX - this.locX;
/* 1040 */       double d1 = entity.locZ - this.locZ;
/* 1041 */       double d2 = MathHelper.a(d0, d1);
/*      */       
/* 1043 */       if (d2 >= 0.009999999776482582D) {
/* 1044 */         d2 = MathHelper.sqrt(d2);
/* 1045 */         d0 /= d2;
/* 1046 */         d1 /= d2;
/* 1047 */         double d3 = 1.0D / d2;
/*      */         
/* 1049 */         if (d3 > 1.0D) {
/* 1050 */           d3 = 1.0D;
/*      */         }
/*      */         
/* 1053 */         d0 *= d3;
/* 1054 */         d1 *= d3;
/* 1055 */         d0 *= 0.05000000074505806D;
/* 1056 */         d1 *= 0.05000000074505806D;
/* 1057 */         d0 *= (1.0F - this.Y);
/* 1058 */         d1 *= (1.0F - this.Y);
/* 1059 */         g(-d0, 0.0D, -d1);
/* 1060 */         entity.g(d0, 0.0D, d1);
/*      */       } 
/*      */     }  }
/*      */ 
/*      */   
/*      */   public void g(double d0, double d1, double d2) {
/* 1066 */     this.motX += d0;
/* 1067 */     this.motY += d1;
/* 1068 */     this.motZ += d2;
/* 1069 */     this.al = true;
/*      */   }
/*      */   
/*      */   protected void Q() {
/* 1073 */     this.velocityChanged = true;
/*      */   }
/*      */   
/*      */   public boolean damageEntity(DamageSource damagesource, float f) {
/* 1077 */     if (isInvulnerable()) {
/* 1078 */       return false;
/*      */     }
/* 1080 */     Q();
/* 1081 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean R() {
/* 1086 */     return false;
/*      */   }
/*      */   
/*      */   public boolean S() {
/* 1090 */     return false;
/*      */   }
/*      */   
/*      */   public void b(Entity entity, int i) {}
/*      */   
/*      */   public boolean c(NBTTagCompound nbttagcompound) {
/* 1096 */     String s = W();
/*      */     
/* 1098 */     if (!this.dead && s != null) {
/* 1099 */       nbttagcompound.setString("id", s);
/* 1100 */       e(nbttagcompound);
/* 1101 */       return true;
/*      */     } 
/* 1103 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean d(NBTTagCompound nbttagcompound) {
/* 1108 */     String s = W();
/*      */     
/* 1110 */     if (!this.dead && s != null && this.passenger == null) {
/* 1111 */       nbttagcompound.setString("id", s);
/* 1112 */       e(nbttagcompound);
/* 1113 */       return true;
/*      */     } 
/* 1115 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public void e(NBTTagCompound nbttagcompound) {
/*      */     try {
/* 1121 */       nbttagcompound.set("Pos", a(new double[] { this.locX, this.locY + this.V, this.locZ }));
/* 1122 */       nbttagcompound.set("Motion", a(new double[] { this.motX, this.motY, this.motZ }));
/*      */ 
/*      */ 
/*      */       
/* 1126 */       if (Float.isNaN(this.yaw)) {
/* 1127 */         this.yaw = 0.0F;
/*      */       }
/*      */       
/* 1130 */       if (Float.isNaN(this.pitch)) {
/* 1131 */         this.pitch = 0.0F;
/*      */       }
/*      */ 
/*      */       
/* 1135 */       nbttagcompound.set("Rotation", a(new float[] { this.yaw, this.pitch }));
/* 1136 */       nbttagcompound.setFloat("FallDistance", this.fallDistance);
/* 1137 */       nbttagcompound.setShort("Fire", (short)this.fireTicks);
/* 1138 */       nbttagcompound.setShort("Air", (short)getAirTicks());
/* 1139 */       nbttagcompound.setBoolean("OnGround", this.onGround);
/* 1140 */       nbttagcompound.setInt("Dimension", this.dimension);
/* 1141 */       nbttagcompound.setBoolean("Invulnerable", this.invulnerable);
/* 1142 */       nbttagcompound.setInt("PortalCooldown", this.portalCooldown);
/* 1143 */       nbttagcompound.setLong("UUIDMost", getUniqueID().getMostSignificantBits());
/* 1144 */       nbttagcompound.setLong("UUIDLeast", getUniqueID().getLeastSignificantBits());
/*      */       
/* 1146 */       nbttagcompound.setLong("WorldUUIDLeast", this.world.getDataManager().getUUID().getLeastSignificantBits());
/* 1147 */       nbttagcompound.setLong("WorldUUIDMost", this.world.getDataManager().getUUID().getMostSignificantBits());
/* 1148 */       nbttagcompound.setInt("Bukkit.updateLevel", 2);
/* 1149 */       nbttagcompound.setInt("Spigot.ticksLived", this.ticksLived);
/*      */       
/* 1151 */       b(nbttagcompound);
/* 1152 */       if (this.vehicle != null) {
/* 1153 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*      */         
/* 1155 */         if (this.vehicle.c(nbttagcompound1)) {
/* 1156 */           nbttagcompound.set("Riding", nbttagcompound1);
/*      */         }
/*      */       } 
/* 1159 */     } catch (Throwable throwable) {
/* 1160 */       CrashReport crashreport = CrashReport.a(throwable, "Saving entity NBT");
/* 1161 */       CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Entity being saved");
/*      */       
/* 1163 */       a(crashreportsystemdetails);
/* 1164 */       throw new ReportedException(crashreport);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void f(NBTTagCompound nbttagcompound) {
/*      */     try {
/* 1170 */       NBTTagList nbttaglist = nbttagcompound.getList("Pos", 6);
/* 1171 */       NBTTagList nbttaglist1 = nbttagcompound.getList("Motion", 6);
/* 1172 */       NBTTagList nbttaglist2 = nbttagcompound.getList("Rotation", 5);
/*      */       
/* 1174 */       this.motX = nbttaglist1.d(0);
/* 1175 */       this.motY = nbttaglist1.d(1);
/* 1176 */       this.motZ = nbttaglist1.d(2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1191 */       this.lastX = this.S = this.locX = nbttaglist.d(0);
/* 1192 */       this.lastY = this.T = this.locY = nbttaglist.d(1);
/* 1193 */       this.lastZ = this.U = this.locZ = nbttaglist.d(2);
/* 1194 */       this.lastYaw = this.yaw = nbttaglist2.e(0);
/* 1195 */       this.lastPitch = this.pitch = nbttaglist2.e(1);
/* 1196 */       this.fallDistance = nbttagcompound.getFloat("FallDistance");
/* 1197 */       this.fireTicks = nbttagcompound.getShort("Fire");
/* 1198 */       setAirTicks(nbttagcompound.getShort("Air"));
/* 1199 */       this.onGround = nbttagcompound.getBoolean("OnGround");
/* 1200 */       this.dimension = nbttagcompound.getInt("Dimension");
/* 1201 */       this.invulnerable = nbttagcompound.getBoolean("Invulnerable");
/* 1202 */       this.portalCooldown = nbttagcompound.getInt("PortalCooldown");
/* 1203 */       if (nbttagcompound.hasKeyOfType("UUIDMost", 4) && nbttagcompound.hasKeyOfType("UUIDLeast", 4)) {
/* 1204 */         this.uniqueID = new UUID(nbttagcompound.getLong("UUIDMost"), nbttagcompound.getLong("UUIDLeast"));
/*      */       }
/*      */       
/* 1207 */       setPosition(this.locX, this.locY, this.locZ);
/* 1208 */       b(this.yaw, this.pitch);
/* 1209 */       a(nbttagcompound);
/* 1210 */       if (V()) {
/* 1211 */         setPosition(this.locX, this.locY, this.locZ);
/*      */       }
/*      */ 
/*      */       
/* 1215 */       if (this instanceof EntityLiving) {
/* 1216 */         EntityLiving entity = (EntityLiving)this;
/*      */         
/* 1218 */         this.ticksLived = nbttagcompound.getInt("Spigot.ticksLived");
/*      */ 
/*      */         
/* 1221 */         if (entity instanceof EntityTameableAnimal && !isLevelAtLeast(nbttagcompound, 2) && !nbttagcompound.getBoolean("PersistenceRequired")) {
/* 1222 */           EntityInsentient entityinsentient = (EntityInsentient)entity;
/* 1223 */           entityinsentient.persistent = !entityinsentient.isTypeNotPersistent();
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1229 */       if (!(getBukkitEntity() instanceof Vehicle)) {
/* 1230 */         if (Math.abs(this.motX) > 10.0D) {
/* 1231 */           this.motX = 0.0D;
/*      */         }
/*      */         
/* 1234 */         if (Math.abs(this.motY) > 10.0D) {
/* 1235 */           this.motY = 0.0D;
/*      */         }
/*      */         
/* 1238 */         if (Math.abs(this.motZ) > 10.0D) {
/* 1239 */           this.motZ = 0.0D;
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1245 */       if (this instanceof EntityPlayer) {
/* 1246 */         CraftWorld craftWorld; Server server = Bukkit.getServer();
/* 1247 */         World bworld = null;
/*      */ 
/*      */         
/* 1250 */         String worldName = nbttagcompound.getString("World");
/*      */         
/* 1252 */         if (nbttagcompound.hasKey("WorldUUIDMost") && nbttagcompound.hasKey("WorldUUIDLeast")) {
/* 1253 */           UUID uid = new UUID(nbttagcompound.getLong("WorldUUIDMost"), nbttagcompound.getLong("WorldUUIDLeast"));
/* 1254 */           bworld = server.getWorld(uid);
/*      */         } else {
/* 1256 */           bworld = server.getWorld(worldName);
/*      */         } 
/*      */         
/* 1259 */         if (bworld == null) {
/* 1260 */           EntityPlayer entityPlayer = (EntityPlayer)this;
/* 1261 */           craftWorld = ((CraftServer)server).getServer().getWorldServer(entityPlayer.dimension).getWorld();
/*      */         } 
/*      */         
/* 1264 */         spawnIn((craftWorld == null) ? null : craftWorld.getHandle());
/*      */       }
/*      */     
/* 1267 */     } catch (Throwable throwable) {
/* 1268 */       CrashReport crashreport = CrashReport.a(throwable, "Loading entity NBT");
/* 1269 */       CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Entity being loaded");
/*      */       
/* 1271 */       a(crashreportsystemdetails);
/* 1272 */       throw new ReportedException(crashreport);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected boolean V() {
/* 1277 */     return true;
/*      */   }
/*      */   
/*      */   protected final String W() {
/* 1281 */     return EntityTypes.b(this);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void X() {}
/*      */ 
/*      */ 
/*      */   
/*      */   protected NBTTagList a(double... adouble) {
/* 1291 */     NBTTagList nbttaglist = new NBTTagList();
/* 1292 */     double[] adouble1 = adouble;
/* 1293 */     int i = adouble.length;
/*      */     
/* 1295 */     for (int j = 0; j < i; j++) {
/* 1296 */       double d0 = adouble1[j];
/*      */       
/* 1298 */       nbttaglist.add(new NBTTagDouble(d0));
/*      */     } 
/*      */     
/* 1301 */     return nbttaglist;
/*      */   }
/*      */   
/*      */   protected NBTTagList a(float... afloat) {
/* 1305 */     NBTTagList nbttaglist = new NBTTagList();
/* 1306 */     float[] afloat1 = afloat;
/* 1307 */     int i = afloat.length;
/*      */     
/* 1309 */     for (int j = 0; j < i; j++) {
/* 1310 */       float f = afloat1[j];
/*      */       
/* 1312 */       nbttaglist.add(new NBTTagFloat(f));
/*      */     } 
/*      */     
/* 1315 */     return nbttaglist;
/*      */   }
/*      */   
/*      */   public EntityItem a(Item item, int i) {
/* 1319 */     return a(item, i, 0.0F);
/*      */   }
/*      */   
/*      */   public EntityItem a(Item item, int i, float f) {
/* 1323 */     return a(new ItemStack(item, i, 0), f);
/*      */   }
/*      */   
/*      */   public EntityItem a(ItemStack itemstack, float f) {
/* 1327 */     if (itemstack.count != 0 && itemstack.getItem() != null) {
/*      */       
/* 1329 */       if (this instanceof EntityLiving && ((EntityLiving)this).drops != null) {
/* 1330 */         ((EntityLiving)this).drops.add(CraftItemStack.asBukkitCopy(itemstack));
/* 1331 */         return null;
/*      */       } 
/*      */ 
/*      */       
/* 1335 */       EntityItem entityitem = new EntityItem(this.world, this.locX, this.locY + f, this.locZ, itemstack);
/*      */       
/* 1337 */       entityitem.pickupDelay = 10;
/* 1338 */       this.world.addEntity(entityitem);
/* 1339 */       return entityitem;
/*      */     } 
/* 1341 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isAlive() {
/* 1346 */     return !this.dead;
/*      */   }
/*      */   
/*      */   public boolean inBlock() {
/* 1350 */     for (int i = 0; i < 8; i++) {
/* 1351 */       float f = (((i >> 0) % 2) - 0.5F) * this.width * 0.8F;
/* 1352 */       float f1 = (((i >> 1) % 2) - 0.5F) * 0.1F;
/* 1353 */       float f2 = (((i >> 2) % 2) - 0.5F) * this.width * 0.8F;
/* 1354 */       int j = MathHelper.floor(this.locX + f);
/* 1355 */       int k = MathHelper.floor(this.locY + getHeadHeight() + f1);
/* 1356 */       int l = MathHelper.floor(this.locZ + f2);
/*      */       
/* 1358 */       if (this.world.getType(j, k, l).r()) {
/* 1359 */         return true;
/*      */       }
/*      */     } 
/*      */     
/* 1363 */     return false;
/*      */   }
/*      */   
/*      */   public boolean c(EntityHuman entityhuman) {
/* 1367 */     return false;
/*      */   }
/*      */   
/*      */   public AxisAlignedBB h(Entity entity) {
/* 1371 */     return null;
/*      */   }
/*      */   
/*      */   public void ab() {
/* 1375 */     if (this.vehicle.dead) {
/* 1376 */       this.vehicle = null;
/*      */     } else {
/* 1378 */       this.motX = 0.0D;
/* 1379 */       this.motY = 0.0D;
/* 1380 */       this.motZ = 0.0D;
/* 1381 */       h();
/* 1382 */       if (this.vehicle != null) {
/* 1383 */         this.vehicle.ac();
/* 1384 */         this.h += (this.vehicle.yaw - this.vehicle.lastYaw);
/*      */         
/* 1386 */         for (this.g += (this.vehicle.pitch - this.vehicle.lastPitch); this.h >= 180.0D; this.h -= 360.0D);
/*      */ 
/*      */ 
/*      */         
/* 1390 */         while (this.h < -180.0D) {
/* 1391 */           this.h += 360.0D;
/*      */         }
/*      */         
/* 1394 */         while (this.g >= 180.0D) {
/* 1395 */           this.g -= 360.0D;
/*      */         }
/*      */         
/* 1398 */         while (this.g < -180.0D) {
/* 1399 */           this.g += 360.0D;
/*      */         }
/*      */         
/* 1402 */         double d0 = this.h * 0.5D;
/* 1403 */         double d1 = this.g * 0.5D;
/* 1404 */         float f = 10.0F;
/*      */         
/* 1406 */         if (d0 > f) {
/* 1407 */           d0 = f;
/*      */         }
/*      */         
/* 1410 */         if (d0 < -f) {
/* 1411 */           d0 = -f;
/*      */         }
/*      */         
/* 1414 */         if (d1 > f) {
/* 1415 */           d1 = f;
/*      */         }
/*      */         
/* 1418 */         if (d1 < -f) {
/* 1419 */           d1 = -f;
/*      */         }
/*      */         
/* 1422 */         this.h -= d0;
/* 1423 */         this.g -= d1;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void ac() {
/* 1429 */     if (this.passenger != null) {
/* 1430 */       this.passenger.setPosition(this.locX, this.locY + ae() + this.passenger.ad(), this.locZ);
/*      */     }
/*      */   }
/*      */   
/*      */   public double ad() {
/* 1435 */     return this.height;
/*      */   }
/*      */   
/*      */   public double ae() {
/* 1439 */     return this.length * 0.75D;
/*      */   }
/*      */ 
/*      */   
/*      */   public void mount(Entity entity) {
/* 1444 */     setPassengerOf(entity);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public CraftEntity getBukkitEntity() {
/* 1450 */     if (this.bukkitEntity == null) {
/* 1451 */       this.bukkitEntity = CraftEntity.getEntity(this.world.getServer(), this);
/*      */     }
/* 1453 */     return this.bukkitEntity;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPassengerOf(Entity entity) {
/* 1460 */     Entity originalVehicle = this.vehicle;
/* 1461 */     Entity originalPassenger = (this.vehicle == null) ? null : this.vehicle.passenger;
/* 1462 */     PluginManager pluginManager = Bukkit.getPluginManager();
/* 1463 */     getBukkitEntity();
/*      */     
/* 1465 */     this.g = 0.0D;
/* 1466 */     this.h = 0.0D;
/* 1467 */     if (entity == null) {
/* 1468 */       if (this.vehicle != null) {
/*      */         
/* 1470 */         if (this.bukkitEntity instanceof LivingEntity && this.vehicle.getBukkitEntity() instanceof Vehicle) {
/* 1471 */           VehicleExitEvent event = new VehicleExitEvent((Vehicle)this.vehicle.getBukkitEntity(), (LivingEntity)this.bukkitEntity);
/* 1472 */           pluginManager.callEvent((Event)event);
/*      */           
/* 1474 */           if (event.isCancelled() || this.vehicle != originalVehicle) {
/*      */             return;
/*      */           }
/*      */         } 
/*      */         
/* 1479 */         pluginManager.callEvent((Event)new EntityDismountEvent((org.bukkit.entity.Entity)getBukkitEntity(), (org.bukkit.entity.Entity)this.vehicle.getBukkitEntity()));
/*      */         
/* 1481 */         setPositionRotation(this.vehicle.locX, this.vehicle.boundingBox.b + this.vehicle.length, this.vehicle.locZ, this.yaw, this.pitch);
/* 1482 */         this.vehicle.passenger = null;
/*      */       } 
/*      */       
/* 1485 */       this.vehicle = null;
/*      */     } else {
/*      */       
/* 1488 */       if (this.bukkitEntity instanceof LivingEntity && entity.getBukkitEntity() instanceof Vehicle && entity.world.isChunkLoaded((int)entity.locX >> 4, (int)entity.locZ >> 4)) {
/*      */         
/* 1490 */         VehicleExitEvent exitEvent = null;
/* 1491 */         if (this.vehicle != null && this.vehicle.getBukkitEntity() instanceof Vehicle) {
/* 1492 */           exitEvent = new VehicleExitEvent((Vehicle)this.vehicle.getBukkitEntity(), (LivingEntity)this.bukkitEntity);
/* 1493 */           pluginManager.callEvent((Event)exitEvent);
/*      */           
/* 1495 */           if (exitEvent.isCancelled() || this.vehicle != originalVehicle || (this.vehicle != null && this.vehicle.passenger != originalPassenger)) {
/*      */             return;
/*      */           }
/*      */         } 
/*      */         
/* 1500 */         VehicleEnterEvent event = new VehicleEnterEvent((Vehicle)entity.getBukkitEntity(), (org.bukkit.entity.Entity)this.bukkitEntity);
/* 1501 */         pluginManager.callEvent((Event)event);
/*      */ 
/*      */         
/* 1504 */         if (event.isCancelled() || this.vehicle != originalVehicle || (this.vehicle != null && this.vehicle.passenger != originalPassenger)) {
/*      */           
/* 1506 */           if (exitEvent != null && this.vehicle == originalVehicle && this.vehicle != null && this.vehicle.passenger == originalPassenger) {
/* 1507 */             setPositionRotation(this.vehicle.locX, this.vehicle.boundingBox.b + this.vehicle.length, this.vehicle.locZ, this.yaw, this.pitch);
/* 1508 */             this.vehicle.passenger = null;
/* 1509 */             this.vehicle = null;
/*      */           } 
/*      */           
/*      */           return;
/*      */         } 
/*      */       } 
/*      */       
/* 1516 */       if (entity.world.isChunkLoaded((int)entity.locX >> 4, (int)entity.locZ >> 4)) {
/*      */         
/* 1518 */         EntityMountEvent event = new EntityMountEvent((org.bukkit.entity.Entity)getBukkitEntity(), (org.bukkit.entity.Entity)entity.getBukkitEntity());
/* 1519 */         pluginManager.callEvent((Event)event);
/* 1520 */         if (event.isCancelled()) {
/*      */           return;
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1527 */       if (this.vehicle != null) {
/* 1528 */         this.vehicle.passenger = null;
/*      */       }
/*      */       
/* 1531 */       if (entity != null) {
/* 1532 */         for (Entity entity1 = entity.vehicle; entity1 != null; entity1 = entity1.vehicle) {
/* 1533 */           if (entity1 == this) {
/*      */             return;
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/* 1539 */       this.vehicle = entity;
/* 1540 */       entity.passenger = this;
/*      */     } 
/*      */   }
/*      */   
/*      */   public float af() {
/* 1545 */     return 0.1F;
/*      */   }
/*      */   
/*      */   public Vec3D ag() {
/* 1549 */     return null;
/*      */   }
/*      */   
/*      */   public void ah() {
/* 1553 */     if (this.portalCooldown > 0) {
/* 1554 */       this.portalCooldown = ai();
/*      */     } else {
/* 1556 */       double d0 = this.lastX - this.locX;
/* 1557 */       double d1 = this.lastZ - this.locZ;
/*      */       
/* 1559 */       if (!this.world.isStatic && !this.an) {
/* 1560 */         this.aq = Direction.a(d0, d1);
/*      */       }
/*      */       
/* 1563 */       this.an = true;
/*      */     } 
/*      */   }
/*      */   
/*      */   public int ai() {
/* 1568 */     return 300;
/*      */   }
/*      */   
/*      */   public ItemStack[] getEquipment() {
/* 1572 */     return null;
/*      */   }
/*      */   
/*      */   public void setEquipment(int i, ItemStack itemstack) {}
/*      */   
/*      */   public boolean isBurning() {
/* 1578 */     boolean flag = (this.world != null && this.world.isStatic);
/*      */     
/* 1580 */     return (!this.fireProof && (this.fireTicks > 0 || (flag && g(0))));
/*      */   }
/*      */   
/*      */   public boolean am() {
/* 1584 */     return (this.vehicle != null);
/*      */   }
/*      */   
/*      */   public boolean isSneaking() {
/* 1588 */     return g(1);
/*      */   }
/*      */   
/*      */   public void setSneaking(boolean flag) {
/* 1592 */     a(1, flag);
/*      */   }
/*      */   
/*      */   public boolean isSprinting() {
/* 1596 */     return g(3);
/*      */   }
/*      */   
/*      */   public void setSprinting(boolean flag) {
/* 1600 */     a(3, flag);
/*      */   }
/*      */   
/*      */   public boolean isInvisible() {
/* 1604 */     return g(5);
/*      */   }
/*      */   
/*      */   public void setInvisible(boolean flag) {
/* 1608 */     a(5, flag);
/*      */   }
/*      */   
/*      */   public void e(boolean flag) {
/* 1612 */     a(4, flag);
/*      */   }
/*      */   
/*      */   protected boolean g(int i) {
/* 1616 */     return ((this.datawatcher.getByte(0) & 1 << i) != 0);
/*      */   }
/*      */   
/*      */   protected void a(int i, boolean flag) {
/* 1620 */     byte b0 = this.datawatcher.getByte(0);
/*      */     
/* 1622 */     if (flag) {
/* 1623 */       this.datawatcher.watch(0, Byte.valueOf((byte)(b0 | 1 << i)));
/*      */     } else {
/* 1625 */       this.datawatcher.watch(0, Byte.valueOf((byte)(b0 & (1 << i ^ 0xFFFFFFFF))));
/*      */     } 
/*      */   }
/*      */   
/*      */   public int getAirTicks() {
/* 1630 */     return this.datawatcher.getShort(1);
/*      */   }
/*      */   
/*      */   public void setAirTicks(int i) {
/* 1634 */     this.datawatcher.watch(1, Short.valueOf((short)i));
/*      */   }
/*      */ 
/*      */   
/*      */   public void a(EntityLightning entitylightning) {
/* 1639 */     CraftEntity craftEntity1 = getBukkitEntity();
/* 1640 */     CraftEntity craftEntity2 = entitylightning.getBukkitEntity();
/* 1641 */     PluginManager pluginManager = Bukkit.getPluginManager();
/*      */     
/* 1643 */     if (craftEntity1 instanceof Hanging) {
/* 1644 */       HangingBreakByEntityEvent hangingEvent = new HangingBreakByEntityEvent((Hanging)craftEntity1, (org.bukkit.entity.Entity)craftEntity2);
/* 1645 */       PaintingBreakByEntityEvent paintingEvent = null;
/*      */       
/* 1647 */       if (craftEntity1 instanceof Painting) {
/* 1648 */         paintingEvent = new PaintingBreakByEntityEvent((Painting)craftEntity1, (org.bukkit.entity.Entity)craftEntity2);
/*      */       }
/*      */       
/* 1651 */       pluginManager.callEvent((Event)hangingEvent);
/*      */       
/* 1653 */       if (paintingEvent != null) {
/* 1654 */         paintingEvent.setCancelled(hangingEvent.isCancelled());
/* 1655 */         pluginManager.callEvent((Event)paintingEvent);
/*      */       } 
/*      */       
/* 1658 */       if (hangingEvent.isCancelled() || (paintingEvent != null && paintingEvent.isCancelled())) {
/*      */         return;
/*      */       }
/*      */     } 
/*      */     
/* 1663 */     if (this.fireProof) {
/*      */       return;
/*      */     }
/* 1666 */     CraftEventFactory.entityDamage = entitylightning;
/* 1667 */     if (!damageEntity(DamageSource.FIRE, 5.0F)) {
/* 1668 */       CraftEventFactory.entityDamage = null;
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 1673 */     this.fireTicks++;
/* 1674 */     if (this.fireTicks == 0) {
/*      */       
/* 1676 */       EntityCombustByEntityEvent entityCombustEvent = new EntityCombustByEntityEvent((org.bukkit.entity.Entity)craftEntity2, (org.bukkit.entity.Entity)craftEntity1, 8);
/* 1677 */       pluginManager.callEvent((Event)entityCombustEvent);
/* 1678 */       if (!entityCombustEvent.isCancelled()) {
/* 1679 */         setOnFire(entityCombustEvent.getDuration());
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void a(EntityLiving entityliving) {}
/*      */   
/*      */   protected boolean j(double d0, double d1, double d2) {
/* 1688 */     int i = MathHelper.floor(d0);
/* 1689 */     int j = MathHelper.floor(d1);
/* 1690 */     int k = MathHelper.floor(d2);
/* 1691 */     double d3 = d0 - i;
/* 1692 */     double d4 = d1 - j;
/* 1693 */     double d5 = d2 - k;
/* 1694 */     List list = this.world.a(this.boundingBox);
/*      */     
/* 1696 */     if (list.isEmpty() && !this.world.q(i, j, k)) {
/* 1697 */       return false;
/*      */     }
/* 1699 */     boolean flag = !this.world.q(i - 1, j, k);
/* 1700 */     boolean flag1 = !this.world.q(i + 1, j, k);
/* 1701 */     boolean flag2 = !this.world.q(i, j - 1, k);
/* 1702 */     boolean flag3 = !this.world.q(i, j + 1, k);
/* 1703 */     boolean flag4 = !this.world.q(i, j, k - 1);
/* 1704 */     boolean flag5 = !this.world.q(i, j, k + 1);
/* 1705 */     byte b0 = 3;
/* 1706 */     double d6 = 9999.0D;
/*      */     
/* 1708 */     if (flag && d3 < d6) {
/* 1709 */       d6 = d3;
/* 1710 */       b0 = 0;
/*      */     } 
/*      */     
/* 1713 */     if (flag1 && 1.0D - d3 < d6) {
/* 1714 */       d6 = 1.0D - d3;
/* 1715 */       b0 = 1;
/*      */     } 
/*      */     
/* 1718 */     if (flag3 && 1.0D - d4 < d6) {
/* 1719 */       d6 = 1.0D - d4;
/* 1720 */       b0 = 3;
/*      */     } 
/*      */     
/* 1723 */     if (flag4 && d5 < d6) {
/* 1724 */       d6 = d5;
/* 1725 */       b0 = 4;
/*      */     } 
/*      */     
/* 1728 */     if (flag5 && 1.0D - d5 < d6) {
/* 1729 */       d6 = 1.0D - d5;
/* 1730 */       b0 = 5;
/*      */     } 
/*      */     
/* 1733 */     float f = this.random.nextFloat() * 0.2F + 0.1F;
/*      */     
/* 1735 */     if (b0 == 0) {
/* 1736 */       this.motX = -f;
/*      */     }
/*      */     
/* 1739 */     if (b0 == 1) {
/* 1740 */       this.motX = f;
/*      */     }
/*      */     
/* 1743 */     if (b0 == 2) {
/* 1744 */       this.motY = -f;
/*      */     }
/*      */     
/* 1747 */     if (b0 == 3) {
/* 1748 */       this.motY = f;
/*      */     }
/*      */     
/* 1751 */     if (b0 == 4) {
/* 1752 */       this.motZ = -f;
/*      */     }
/*      */     
/* 1755 */     if (b0 == 5) {
/* 1756 */       this.motZ = f;
/*      */     }
/*      */     
/* 1759 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public void as() {
/* 1764 */     this.I = true;
/* 1765 */     this.fallDistance = 0.0F;
/*      */   }
/*      */   
/*      */   public String getName() {
/* 1769 */     String s = EntityTypes.b(this);
/*      */     
/* 1771 */     if (s == null) {
/* 1772 */       s = "generic";
/*      */     }
/*      */     
/* 1775 */     return LocaleI18n.get("entity." + s + ".name");
/*      */   }
/*      */   
/*      */   public Entity[] at() {
/* 1779 */     return null;
/*      */   }
/*      */   
/*      */   public boolean i(Entity entity) {
/* 1783 */     return (this == entity);
/*      */   }
/*      */   
/*      */   public float getHeadRotation() {
/* 1787 */     return 0.0F;
/*      */   }
/*      */   
/*      */   public boolean av() {
/* 1791 */     return true;
/*      */   }
/*      */   
/*      */   public boolean j(Entity entity) {
/* 1795 */     return false;
/*      */   }
/*      */   
/*      */   public String toString() {
/* 1799 */     return String.format("%s['%s'/%d, l='%s', x=%.2f, y=%.2f, z=%.2f]", new Object[] { getClass().getSimpleName(), getName(), Integer.valueOf(this.id), (this.world == null) ? "~NULL~" : this.world.getWorldData().getName(), Double.valueOf(this.locX), Double.valueOf(this.locY), Double.valueOf(this.locZ) });
/*      */   }
/*      */   
/*      */   public boolean isInvulnerable() {
/* 1803 */     return this.invulnerable;
/*      */   }
/*      */   
/*      */   public void k(Entity entity) {
/* 1807 */     setPositionRotation(entity.locX, entity.locY, entity.locZ, entity.yaw, entity.pitch);
/*      */   }
/*      */   
/*      */   public void a(Entity entity, boolean flag) {
/* 1811 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*      */     
/* 1813 */     entity.e(nbttagcompound);
/* 1814 */     f(nbttagcompound);
/* 1815 */     this.portalCooldown = entity.portalCooldown;
/* 1816 */     this.aq = entity.aq;
/*      */   }
/*      */   
/*      */   public void b(int i) {
/* 1820 */     if (!this.world.isStatic && !this.dead) {
/* 1821 */       this.world.methodProfiler.a("changeDimension");
/* 1822 */       MinecraftServer minecraftserver = MinecraftServer.getServer();
/*      */ 
/*      */       
/* 1825 */       WorldServer exitWorld = null;
/* 1826 */       if (this.dimension < 10)
/*      */       {
/* 1828 */         for (WorldServer world : minecraftserver.worlds) {
/* 1829 */           if (world.dimension == i) {
/* 1830 */             exitWorld = world;
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/* 1835 */       Location enter = getBukkitEntity().getLocation();
/* 1836 */       Location exit = (exitWorld != null) ? minecraftserver.getPlayerList().calculateTarget(enter, minecraftserver.getWorldServer(i)) : null;
/* 1837 */       boolean useTravelAgent = (exitWorld != null && (this.dimension != 1 || exitWorld.dimension != 1));
/*      */       
/* 1839 */       TravelAgent agent = (exit != null) ? (TravelAgent)((CraftWorld)exit.getWorld()).getHandle().getTravelAgent() : CraftTravelAgent.DEFAULT;
/* 1840 */       EntityPortalEvent event = new EntityPortalEvent((org.bukkit.entity.Entity)getBukkitEntity(), enter, exit, agent);
/* 1841 */       event.useTravelAgent(useTravelAgent);
/* 1842 */       event.getEntity().getServer().getPluginManager().callEvent((Event)event);
/* 1843 */       if (event.isCancelled() || event.getTo() == null || event.getTo().getWorld() == null || !isAlive()) {
/*      */         return;
/*      */       }
/* 1846 */       exit = event.useTravelAgent() ? event.getPortalTravelAgent().findOrCreate(event.getTo()) : event.getTo();
/* 1847 */       teleportTo(exit, true);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void teleportTo(Location exit, boolean portal) {
/* 1853 */     WorldServer worldserver = ((CraftWorld)getBukkitEntity().getLocation().getWorld()).getHandle();
/* 1854 */     WorldServer worldserver1 = ((CraftWorld)exit.getWorld()).getHandle();
/* 1855 */     int i = worldserver1.dimension;
/*      */ 
/*      */     
/* 1858 */     this.dimension = i;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1866 */     this.world.kill(this);
/* 1867 */     this.dead = false;
/* 1868 */     this.world.methodProfiler.a("reposition");
/*      */ 
/*      */     
/* 1871 */     boolean before = worldserver1.chunkProviderServer.forceChunkLoad;
/* 1872 */     worldserver1.chunkProviderServer.forceChunkLoad = true;
/*      */     
/* 1874 */     worldserver1.chunkProviderServer.forceChunkLoad = before;
/*      */     
/* 1876 */     this.world.methodProfiler.c("reloading");
/* 1877 */     Entity entity = EntityTypes.createEntityByName(EntityTypes.b(this), worldserver1);
/*      */     
/* 1879 */     if (entity != null) {
/* 1880 */       entity.a(this, true);
/*      */       
/* 1882 */       exit.getBlock();
/* 1883 */       entity.setLocation(exit.getX(), exit.getY(), exit.getZ(), exit.getYaw(), exit.getPitch());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1893 */       worldserver1.addEntity(entity);
/*      */       
/* 1895 */       getBukkitEntity().setHandle(entity);
/* 1896 */       entity.bukkitEntity = getBukkitEntity();
/*      */     } 
/*      */ 
/*      */     
/* 1900 */     this.dead = true;
/* 1901 */     this.world.methodProfiler.b();
/* 1902 */     worldserver.i();
/* 1903 */     worldserver1.i();
/* 1904 */     this.world.methodProfiler.b();
/*      */   }
/*      */ 
/*      */   
/*      */   public float a(Explosion explosion, World world, int i, int j, int k, Block block) {
/* 1909 */     return block.a(this);
/*      */   }
/*      */   
/*      */   public boolean a(Explosion explosion, World world, int i, int j, int k, Block block, float f) {
/* 1913 */     return true;
/*      */   }
/*      */   
/*      */   public int ax() {
/* 1917 */     return 3;
/*      */   }
/*      */   
/*      */   public int ay() {
/* 1921 */     return this.aq;
/*      */   }
/*      */   
/*      */   public boolean az() {
/* 1925 */     return false;
/*      */   }
/*      */   
/*      */   public void a(CrashReportSystemDetails crashreportsystemdetails) {
/* 1929 */     crashreportsystemdetails.a("Entity Type", new CrashReportEntityType(this));
/* 1930 */     crashreportsystemdetails.a("Entity ID", Integer.valueOf(this.id));
/* 1931 */     crashreportsystemdetails.a("Entity Name", new CrashReportEntityName(this));
/* 1932 */     crashreportsystemdetails.a("Entity's Exact location", String.format("%.2f, %.2f, %.2f", new Object[] { Double.valueOf(this.locX), Double.valueOf(this.locY), Double.valueOf(this.locZ) }));
/* 1933 */     crashreportsystemdetails.a("Entity's Block location", CrashReportSystemDetails.a(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ)));
/* 1934 */     crashreportsystemdetails.a("Entity's Momentum", String.format("%.2f, %.2f, %.2f", new Object[] { Double.valueOf(this.motX), Double.valueOf(this.motY), Double.valueOf(this.motZ) }));
/*      */   }
/*      */   
/*      */   public UUID getUniqueID() {
/* 1938 */     return this.uniqueID;
/*      */   }
/*      */   
/*      */   public boolean aC() {
/* 1942 */     return true;
/*      */   }
/*      */   
/*      */   public IChatBaseComponent getScoreboardDisplayName() {
/* 1946 */     return new ChatComponentText(getName());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void i(int i) {}
/*      */ 
/*      */   
/*      */   private boolean paperNetherCheck() {
/* 1955 */     return (this.world.paperSpigotConfig.netherVoidTopDamage && this.world.getWorld().getEnvironment() == World.Environment.NETHER && this.locY >= 128.0D);
/*      */   }
/*      */   
/*      */   protected abstract void c();
/*      */   
/*      */   protected abstract void a(NBTTagCompound paramNBTTagCompound);
/*      */   
/*      */   protected abstract void b(NBTTagCompound paramNBTTagCompound); }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\Entity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
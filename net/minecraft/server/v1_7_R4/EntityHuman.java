/*      */ package net.minecraft.server.v1_7_R4;
/*      */ import java.util.Collection;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.UUID;
/*      */ import net.minecraft.util.com.mojang.authlib.GameProfile;
/*      */ import org.bukkit.OfflinePlayer;
/*      */ import org.bukkit.block.Block;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.entity.CraftHumanEntity;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.entity.CraftItem;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
/*      */ import org.bukkit.entity.Entity;
/*      */ import org.bukkit.entity.Player;
/*      */ import org.bukkit.event.Event;
/*      */ import org.bukkit.event.entity.EntityCombustByEntityEvent;
/*      */ import org.bukkit.event.player.PlayerBedEnterEvent;
/*      */ import org.bukkit.event.player.PlayerBedLeaveEvent;
/*      */ import org.bukkit.event.player.PlayerDropItemEvent;
/*      */ import org.bukkit.event.player.PlayerItemConsumeEvent;
/*      */ import org.bukkit.inventory.ItemStack;
/*      */ import org.bukkit.scoreboard.Team;
/*      */ import org.spigotmc.ProtocolData;
/*      */ 
/*      */ public abstract class EntityHuman extends EntityLiving implements ICommandListener {
/*   26 */   public PlayerInventory inventory = new PlayerInventory(this);
/*   27 */   private InventoryEnderChest enderChest = new InventoryEnderChest();
/*      */   public Container defaultContainer;
/*      */   public Container activeContainer;
/*   30 */   protected FoodMetaData foodData = new FoodMetaData(this);
/*      */   
/*      */   protected int bq;
/*      */   public float br;
/*      */   public float bs;
/*      */   public int bt;
/*      */   public double bu;
/*      */   public double bv;
/*      */   public double bw;
/*      */   public double bx;
/*      */   public double by;
/*      */   public double bz;
/*      */   public boolean sleeping;
/*      */   public boolean fauxSleeping;
/*   44 */   public String spawnWorld = ""; public boolean affectsSpawning = true; public ChunkCoordinates bB; public int sleepTicks; public float bC; public float bD; private ChunkCoordinates c;
/*      */   private boolean d;
/*      */   private ChunkCoordinates e;
/*      */   
/*      */   public CraftHumanEntity getBukkitEntity() {
/*   49 */     return (CraftHumanEntity)super.getBukkitEntity();
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
/*   60 */   public PlayerAbilities abilities = new PlayerAbilities();
/*   61 */   public int oldLevel = -1;
/*      */   public int expLevel;
/*      */   public int expTotal;
/*      */   public float exp;
/*      */   private ItemStack f;
/*      */   private int g;
/*   67 */   protected float bI = 0.1F;
/*   68 */   protected float bJ = 0.02F;
/*      */   private int h;
/*      */   private final GameProfile i;
/*      */   public EntityFishingHook hookedFish;
/*      */   
/*      */   public EntityHuman(World world, GameProfile gameprofile) {
/*   74 */     super(world);
/*   75 */     this.uniqueID = a(gameprofile);
/*   76 */     this.i = gameprofile;
/*   77 */     this.defaultContainer = new ContainerPlayer(this.inventory, !world.isStatic, this);
/*   78 */     this.activeContainer = this.defaultContainer;
/*   79 */     this.height = 1.62F;
/*   80 */     ChunkCoordinates chunkcoordinates = world.getSpawn();
/*      */     
/*   82 */     setPositionRotation(chunkcoordinates.x + 0.5D, (chunkcoordinates.y + 1), chunkcoordinates.z + 0.5D, 0.0F, 0.0F);
/*   83 */     this.aZ = 180.0F;
/*   84 */     this.maxFireTicks = 20;
/*      */   }
/*      */   
/*      */   protected void aD() {
/*   88 */     super.aD();
/*   89 */     getAttributeMap().b(GenericAttributes.e).setValue(1.0D);
/*      */   }
/*      */   
/*      */   protected void c() {
/*   93 */     super.c();
/*   94 */     this.datawatcher.a(16, new ProtocolData.DualByte((byte)0, (byte)0));
/*   95 */     this.datawatcher.a(17, Float.valueOf(0.0F));
/*   96 */     this.datawatcher.a(18, Integer.valueOf(0));
/*   97 */     this.datawatcher.a(10, new ProtocolData.HiddenByte((byte)0));
/*      */   }
/*      */   
/*      */   public boolean by() {
/*  101 */     return (this.f != null);
/*      */   }
/*      */   
/*      */   public void bA() {
/*  105 */     if (this.f != null) {
/*  106 */       this.f.b(this.world, this, this.g);
/*      */     }
/*      */     
/*  109 */     bB();
/*      */   }
/*      */   
/*      */   public void bB() {
/*  113 */     this.f = null;
/*  114 */     this.g = 0;
/*  115 */     if (!this.world.isStatic) {
/*  116 */       e(false);
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean isBlocking() {
/*  121 */     return (by() && this.f.getItem().d(this.f) == EnumAnimation.BLOCK);
/*      */   }
/*      */   
/*      */   public void h() {
/*  125 */     if (this.f != null) {
/*  126 */       ItemStack itemstack = this.inventory.getItemInHand();
/*      */       
/*  128 */       if (itemstack == this.f) {
/*  129 */         if (this.g <= 25 && this.g % 4 == 0) {
/*  130 */           c(itemstack, 5);
/*      */         }
/*      */         
/*  133 */         if (--this.g == 0 && !this.world.isStatic) {
/*  134 */           p();
/*      */         }
/*      */       } else {
/*  137 */         bB();
/*      */       } 
/*      */     } 
/*      */     
/*  141 */     if (this.bt > 0) {
/*  142 */       this.bt--;
/*      */     }
/*      */     
/*  145 */     if (isSleeping()) {
/*  146 */       this.sleepTicks++;
/*  147 */       if (this.sleepTicks > 100) {
/*  148 */         this.sleepTicks = 100;
/*      */       }
/*      */       
/*  151 */       if (!this.world.isStatic) {
/*  152 */         if (!j()) {
/*  153 */           a(true, true, false);
/*  154 */         } else if (this.world.w()) {
/*  155 */           a(false, true, true);
/*      */         } 
/*      */       }
/*  158 */     } else if (this.sleepTicks > 0) {
/*  159 */       this.sleepTicks++;
/*  160 */       if (this.sleepTicks >= 110) {
/*  161 */         this.sleepTicks = 0;
/*      */       }
/*      */     } 
/*      */     
/*  165 */     super.h();
/*  166 */     if (!this.world.isStatic && this.activeContainer != null && !this.activeContainer.a(this)) {
/*  167 */       closeInventory();
/*  168 */       this.activeContainer = this.defaultContainer;
/*      */     } 
/*      */     
/*  171 */     if (isBurning() && this.abilities.isInvulnerable) {
/*  172 */       extinguish();
/*      */     }
/*      */     
/*  175 */     this.bu = this.bx;
/*  176 */     this.bv = this.by;
/*  177 */     this.bw = this.bz;
/*  178 */     double d0 = this.locX - this.bx;
/*  179 */     double d1 = this.locY - this.by;
/*  180 */     double d2 = this.locZ - this.bz;
/*  181 */     double d3 = 10.0D;
/*      */     
/*  183 */     if (d0 > d3) {
/*  184 */       this.bu = this.bx = this.locX;
/*      */     }
/*      */     
/*  187 */     if (d2 > d3) {
/*  188 */       this.bw = this.bz = this.locZ;
/*      */     }
/*      */     
/*  191 */     if (d1 > d3) {
/*  192 */       this.bv = this.by = this.locY;
/*      */     }
/*      */     
/*  195 */     if (d0 < -d3) {
/*  196 */       this.bu = this.bx = this.locX;
/*      */     }
/*      */     
/*  199 */     if (d2 < -d3) {
/*  200 */       this.bw = this.bz = this.locZ;
/*      */     }
/*      */     
/*  203 */     if (d1 < -d3) {
/*  204 */       this.bv = this.by = this.locY;
/*      */     }
/*      */     
/*  207 */     this.bx += d0 * 0.25D;
/*  208 */     this.bz += d2 * 0.25D;
/*  209 */     this.by += d1 * 0.25D;
/*  210 */     if (this.vehicle == null) {
/*  211 */       this.e = null;
/*      */     }
/*      */     
/*  214 */     if (!this.world.isStatic) {
/*  215 */       this.foodData.a(this);
/*  216 */       a(StatisticList.g, 1);
/*      */     } 
/*      */   }
/*      */   
/*      */   public int D() {
/*  221 */     return this.abilities.isInvulnerable ? 0 : 80;
/*      */   }
/*      */   
/*      */   protected String H() {
/*  225 */     return "game.player.swim";
/*      */   }
/*      */   
/*      */   protected String O() {
/*  229 */     return "game.player.swim.splash";
/*      */   }
/*      */   
/*      */   public int ai() {
/*  233 */     return 10;
/*      */   }
/*      */   
/*      */   public void makeSound(String s, float f, float f1) {
/*  237 */     this.world.a(this, s, f, f1);
/*      */   }
/*      */   
/*      */   protected void c(ItemStack itemstack, int i) {
/*  241 */     if (itemstack.o() == EnumAnimation.DRINK) {
/*  242 */       makeSound("random.drink", 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
/*      */     }
/*      */     
/*  245 */     if (itemstack.o() == EnumAnimation.EAT) {
/*  246 */       for (int j = 0; j < i; j++) {
/*  247 */         Vec3D vec3d = Vec3D.a((this.random.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
/*      */         
/*  249 */         vec3d.a(-this.pitch * 3.1415927F / 180.0F);
/*  250 */         vec3d.b(-this.yaw * 3.1415927F / 180.0F);
/*  251 */         Vec3D vec3d1 = Vec3D.a((this.random.nextFloat() - 0.5D) * 0.3D, -this.random.nextFloat() * 0.6D - 0.3D, 0.6D);
/*      */         
/*  253 */         vec3d1.a(-this.pitch * 3.1415927F / 180.0F);
/*  254 */         vec3d1.b(-this.yaw * 3.1415927F / 180.0F);
/*  255 */         vec3d1 = vec3d1.add(this.locX, this.locY + getHeadHeight(), this.locZ);
/*  256 */         String s = "iconcrack_" + Item.getId(itemstack.getItem());
/*      */         
/*  258 */         if (itemstack.usesData()) {
/*  259 */           s = s + "_" + itemstack.getData();
/*      */         }
/*      */         
/*  262 */         this.world.addParticle(s, vec3d1.a, vec3d1.b, vec3d1.c, vec3d.a, vec3d.b + 0.05D, vec3d.c);
/*      */       } 
/*      */       
/*  265 */       makeSound("random.eat", 0.5F + 0.5F * this.random.nextInt(2), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void p() {
/*  270 */     if (this.f != null) {
/*  271 */       c(this.f, 16);
/*  272 */       int i = this.f.count;
/*      */ 
/*      */       
/*  275 */       ItemStack craftItem = CraftItemStack.asBukkitCopy(this.f);
/*  276 */       PlayerItemConsumeEvent event = new PlayerItemConsumeEvent((Player)getBukkitEntity(), craftItem);
/*  277 */       this.world.getServer().getPluginManager().callEvent((Event)event);
/*      */       
/*  279 */       if (event.isCancelled()) {
/*      */         
/*  281 */         if (this instanceof EntityPlayer) {
/*  282 */           ((EntityPlayer)this).playerConnection.sendPacket(new PacketPlayOutSetSlot(0, (this.activeContainer.getSlot(this.inventory, this.inventory.itemInHandIndex)).index, this.f));
/*      */           
/*  284 */           ((EntityPlayer)this).getBukkitEntity().updateInventory();
/*  285 */           ((EntityPlayer)this).getBukkitEntity().updateScaledHealth();
/*      */         } 
/*      */ 
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/*  292 */       if (!craftItem.equals(event.getItem())) {
/*  293 */         CraftItemStack.asNMSCopy(event.getItem()).b(this.world, this);
/*      */ 
/*      */         
/*  296 */         if (this instanceof EntityPlayer) {
/*  297 */           ((EntityPlayer)this).playerConnection.sendPacket(new PacketPlayOutSetSlot(0, (this.activeContainer.getSlot(this.inventory, this.inventory.itemInHandIndex)).index, this.f));
/*      */         }
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/*  303 */       ItemStack itemstack = this.f.b(this.world, this);
/*      */       
/*  305 */       if (itemstack != this.f || (itemstack != null && itemstack.count != i)) {
/*  306 */         this.inventory.items[this.inventory.itemInHandIndex] = itemstack;
/*  307 */         if (itemstack.count == 0) {
/*  308 */           this.inventory.items[this.inventory.itemInHandIndex] = null;
/*      */         }
/*      */       } 
/*      */       
/*  312 */       bB();
/*      */     } 
/*      */   }
/*      */   
/*      */   protected boolean bh() {
/*  317 */     return (getHealth() <= 0.0F || isSleeping());
/*      */   }
/*      */ 
/*      */   
/*      */   public void closeInventory() {
/*  322 */     this.activeContainer = this.defaultContainer;
/*      */   }
/*      */ 
/*      */   
/*      */   public void mount(Entity entity) {
/*  327 */     setPassengerOf(entity);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setPassengerOf(Entity entity) {
/*  332 */     if (this.vehicle != null && entity == null) {
/*  333 */       this.world.getServer().getPluginManager().callEvent((Event)new EntityDismountEvent((Entity)getBukkitEntity(), (Entity)this.vehicle.getBukkitEntity()));
/*      */       
/*  335 */       Entity originalVehicle = this.vehicle;
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
/*  348 */       super.setPassengerOf(entity);
/*  349 */       if (!this.world.isStatic && this.vehicle == null) {
/*  350 */         m(originalVehicle);
/*      */       }
/*      */     } else {
/*      */       
/*  354 */       super.setPassengerOf(entity);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void ab() {
/*  359 */     if (!this.world.isStatic && isSneaking()) {
/*  360 */       mount((Entity)null);
/*  361 */       setSneaking(false);
/*      */     } else {
/*  363 */       double d0 = this.locX;
/*  364 */       double d1 = this.locY;
/*  365 */       double d2 = this.locZ;
/*  366 */       float f = this.yaw;
/*  367 */       float f1 = this.pitch;
/*      */       
/*  369 */       super.ab();
/*  370 */       this.br = this.bs;
/*  371 */       this.bs = 0.0F;
/*  372 */       l(this.locX - d0, this.locY - d1, this.locZ - d2);
/*  373 */       if (this.vehicle instanceof EntityPig) {
/*  374 */         this.pitch = f1;
/*  375 */         this.yaw = f;
/*  376 */         this.aM = ((EntityPig)this.vehicle).aM;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void bq() {
/*  382 */     super.bq();
/*  383 */     bb();
/*      */   }
/*      */   
/*      */   public void e() {
/*  387 */     if (this.bq > 0) {
/*  388 */       this.bq--;
/*      */     }
/*      */     
/*  391 */     if (this.world.difficulty == EnumDifficulty.PEACEFUL && getHealth() < getMaxHealth() && this.world.getGameRules().getBoolean("naturalRegeneration") && this.ticksLived % 20 * 12 == 0)
/*      */     {
/*  393 */       heal(1.0F, EntityRegainHealthEvent.RegainReason.REGEN);
/*      */     }
/*      */     
/*  396 */     this.inventory.k();
/*  397 */     this.br = this.bs;
/*  398 */     super.e();
/*  399 */     AttributeInstance attributeinstance = getAttributeInstance(GenericAttributes.d);
/*      */     
/*  401 */     if (!this.world.isStatic) {
/*  402 */       attributeinstance.setValue(this.abilities.b());
/*      */     }
/*      */     
/*  405 */     this.aQ = this.bJ;
/*  406 */     if (isSprinting()) {
/*  407 */       this.aQ = (float)(this.aQ + this.bJ * 0.3D);
/*      */     }
/*      */     
/*  410 */     i((float)attributeinstance.getValue());
/*  411 */     float f = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
/*      */     
/*  413 */     float f1 = (float)TrigMath.atan(-this.motY * 0.20000000298023224D) * 15.0F;
/*      */     
/*  415 */     if (f > 0.1F) {
/*  416 */       f = 0.1F;
/*      */     }
/*      */     
/*  419 */     if (!this.onGround || getHealth() <= 0.0F) {
/*  420 */       f = 0.0F;
/*      */     }
/*      */     
/*  423 */     if (this.onGround || getHealth() <= 0.0F) {
/*  424 */       f1 = 0.0F;
/*      */     }
/*      */     
/*  427 */     this.bs += (f - this.bs) * 0.4F;
/*  428 */     this.aJ += (f1 - this.aJ) * 0.8F;
/*  429 */     if (getHealth() > 0.0F) {
/*  430 */       AxisAlignedBB axisalignedbb = null;
/*      */       
/*  432 */       if (this.vehicle != null && !this.vehicle.dead) {
/*  433 */         axisalignedbb = this.boundingBox.a(this.vehicle.boundingBox).grow(1.0D, 0.0D, 1.0D);
/*      */       } else {
/*  435 */         axisalignedbb = this.boundingBox.grow(1.0D, 0.5D, 1.0D);
/*      */       } 
/*      */       
/*  438 */       List<Entity> list = this.world.getEntities(this, axisalignedbb);
/*      */       
/*  440 */       if (list != null && S()) {
/*  441 */         for (int i = 0; i < list.size(); i++) {
/*  442 */           Entity entity = list.get(i);
/*      */           
/*  444 */           if (!entity.dead) {
/*  445 */             d(entity);
/*      */           }
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void d(Entity entity) {
/*  453 */     entity.b_(this);
/*      */   }
/*      */   
/*      */   public int getScore() {
/*  457 */     return this.datawatcher.getInt(18);
/*      */   }
/*      */   
/*      */   public void setScore(int i) {
/*  461 */     this.datawatcher.watch(18, Integer.valueOf(i));
/*      */   }
/*      */   
/*      */   public void addScore(int i) {
/*  465 */     int j = getScore();
/*      */     
/*  467 */     this.datawatcher.watch(18, Integer.valueOf(j + i));
/*      */   }
/*      */   
/*      */   public void die(DamageSource damagesource) {
/*  471 */     super.die(damagesource);
/*  472 */     a(0.2F, 0.2F);
/*  473 */     setPosition(this.locX, this.locY, this.locZ);
/*  474 */     this.motY = 0.10000000149011612D;
/*  475 */     if (getName().equals("Notch")) {
/*  476 */       a(new ItemStack(Items.APPLE, 1), true, false);
/*      */     }
/*      */     
/*  479 */     if (!this.world.getGameRules().getBoolean("keepInventory")) {
/*  480 */       this.inventory.m();
/*      */     }
/*      */     
/*  483 */     if (damagesource != null) {
/*  484 */       this.motX = (-MathHelper.cos((this.az + this.yaw) * 3.1415927F / 180.0F) * 0.1F);
/*  485 */       this.motZ = (-MathHelper.sin((this.az + this.yaw) * 3.1415927F / 180.0F) * 0.1F);
/*      */     } else {
/*  487 */       this.motX = this.motZ = 0.0D;
/*      */     } 
/*      */     
/*  490 */     this.height = 0.1F;
/*  491 */     a(StatisticList.v, 1);
/*      */   }
/*      */   
/*      */   protected String aT() {
/*  495 */     return "game.player.hurt";
/*      */   }
/*      */   
/*      */   protected String aU() {
/*  499 */     return "game.player.die";
/*      */   }
/*      */   
/*      */   public void b(Entity entity, int i) {
/*  503 */     addScore(i);
/*      */     
/*  505 */     Collection<ScoreboardScore> collection = this.world.getServer().getScoreboardManager().getScoreboardScores(IScoreboardCriteria.e, getName(), new ArrayList());
/*      */     
/*  507 */     if (entity instanceof EntityHuman) {
/*  508 */       a(StatisticList.y, 1);
/*      */       
/*  510 */       this.world.getServer().getScoreboardManager().getScoreboardScores(IScoreboardCriteria.d, getName(), collection);
/*      */     } else {
/*  512 */       a(StatisticList.w, 1);
/*      */     } 
/*      */     
/*  515 */     Iterator<ScoreboardScore> iterator = collection.iterator();
/*      */     
/*  517 */     while (iterator.hasNext()) {
/*  518 */       ScoreboardScore scoreboardscore = iterator.next();
/*      */       
/*  520 */       scoreboardscore.incrementScore();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public EntityItem a(boolean flag) {
/*  526 */     return a(this.inventory.splitStack(this.inventory.itemInHandIndex, (flag && this.inventory.getItemInHand() != null) ? (this.inventory.getItemInHand()).count : 1), false, true);
/*      */   }
/*      */   
/*      */   public EntityItem drop(ItemStack itemstack, boolean flag) {
/*  530 */     return a(itemstack, false, false);
/*      */   }
/*      */   
/*      */   public EntityItem a(ItemStack itemstack, boolean flag, boolean flag1) {
/*  534 */     if (itemstack == null)
/*  535 */       return null; 
/*  536 */     if (itemstack.count == 0) {
/*  537 */       return null;
/*      */     }
/*  539 */     EntityItem entityitem = new EntityItem(this.world, this.locX, this.locY - 0.30000001192092896D + getHeadHeight(), this.locZ, itemstack);
/*      */     
/*  541 */     entityitem.pickupDelay = 40;
/*  542 */     if (flag1) {
/*  543 */       entityitem.b(getName());
/*      */     }
/*      */     
/*  546 */     float f = 0.1F;
/*      */ 
/*      */     
/*  549 */     if (flag) {
/*  550 */       float f1 = this.random.nextFloat() * 0.5F;
/*  551 */       float f2 = this.random.nextFloat() * 3.1415927F * 2.0F;
/*      */       
/*  553 */       entityitem.motX = (-MathHelper.sin(f2) * f1);
/*  554 */       entityitem.motZ = (MathHelper.cos(f2) * f1);
/*  555 */       entityitem.motY = 0.20000000298023224D;
/*      */     } else {
/*  557 */       f = 0.3F;
/*  558 */       entityitem.motX = (-MathHelper.sin(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F) * f);
/*  559 */       entityitem.motZ = (MathHelper.cos(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F) * f);
/*  560 */       entityitem.motY = (-MathHelper.sin(this.pitch / 180.0F * 3.1415927F) * f + 0.1F);
/*  561 */       f = 0.02F;
/*  562 */       float f1 = this.random.nextFloat() * 3.1415927F * 2.0F;
/*  563 */       f *= this.random.nextFloat();
/*  564 */       entityitem.motX += Math.cos(f1) * f;
/*  565 */       entityitem.motY += ((this.random.nextFloat() - this.random.nextFloat()) * 0.1F);
/*  566 */       entityitem.motZ += Math.sin(f1) * f;
/*      */     } 
/*      */ 
/*      */     
/*  570 */     Player player = (Player)getBukkitEntity();
/*  571 */     CraftItem drop = new CraftItem(this.world.getServer(), entityitem);
/*      */     
/*  573 */     PlayerDropItemEvent event = new PlayerDropItemEvent(player, (Item)drop);
/*  574 */     this.world.getServer().getPluginManager().callEvent((Event)event);
/*      */     
/*  576 */     if (event.isCancelled()) {
/*  577 */       ItemStack cur = player.getInventory().getItemInHand();
/*  578 */       if (flag1 && (cur == null || cur.getAmount() == 0)) {
/*      */         
/*  580 */         player.getInventory().setItemInHand(drop.getItemStack());
/*  581 */       } else if (flag1 && cur.isSimilar(drop.getItemStack()) && drop.getItemStack().getAmount() == 1) {
/*      */         
/*  583 */         cur.setAmount(cur.getAmount() + 1);
/*  584 */         player.getInventory().setItemInHand(cur);
/*      */       } else {
/*      */         
/*  587 */         player.getInventory().addItem(new ItemStack[] { drop.getItemStack() });
/*      */       } 
/*  589 */       return null;
/*      */     } 
/*      */ 
/*      */     
/*  593 */     a(entityitem);
/*  594 */     a(StatisticList.s, 1);
/*  595 */     return entityitem;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void a(EntityItem entityitem) {
/*  600 */     this.world.addEntity(entityitem);
/*      */   }
/*      */   
/*      */   public float a(Block block, boolean flag) {
/*  604 */     float f = this.inventory.a(block);
/*      */     
/*  606 */     if (f > 1.0F) {
/*  607 */       int i = EnchantmentManager.getDigSpeedEnchantmentLevel(this);
/*  608 */       ItemStack itemstack = this.inventory.getItemInHand();
/*      */       
/*  610 */       if (i > 0 && itemstack != null) {
/*  611 */         float f1 = (i * i + 1);
/*      */         
/*  613 */         if (!itemstack.b(block) && f <= 1.0F) {
/*  614 */           f += f1 * 0.08F;
/*      */         } else {
/*  616 */           f += f1;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  621 */     if (hasEffect(MobEffectList.FASTER_DIG)) {
/*  622 */       f *= 1.0F + (getEffect(MobEffectList.FASTER_DIG).getAmplifier() + 1) * 0.2F;
/*      */     }
/*      */     
/*  625 */     if (hasEffect(MobEffectList.SLOWER_DIG)) {
/*  626 */       f *= 1.0F - (getEffect(MobEffectList.SLOWER_DIG).getAmplifier() + 1) * 0.2F;
/*      */     }
/*      */     
/*  629 */     if (a(Material.WATER) && !EnchantmentManager.hasWaterWorkerEnchantment(this)) {
/*  630 */       f /= 5.0F;
/*      */     }
/*      */     
/*  633 */     if (!this.onGround) {
/*  634 */       f /= 5.0F;
/*      */     }
/*      */     
/*  637 */     return f;
/*      */   }
/*      */   
/*      */   public boolean a(Block block) {
/*  641 */     return this.inventory.b(block);
/*      */   }
/*      */   
/*      */   public void a(NBTTagCompound nbttagcompound) {
/*  645 */     super.a(nbttagcompound);
/*  646 */     this.uniqueID = a(this.i);
/*  647 */     NBTTagList nbttaglist = nbttagcompound.getList("Inventory", 10);
/*      */     
/*  649 */     this.inventory.b(nbttaglist);
/*  650 */     this.inventory.itemInHandIndex = nbttagcompound.getInt("SelectedItemSlot");
/*  651 */     this.sleeping = nbttagcompound.getBoolean("Sleeping");
/*  652 */     this.sleepTicks = nbttagcompound.getShort("SleepTimer");
/*  653 */     this.exp = nbttagcompound.getFloat("XpP");
/*  654 */     this.expLevel = nbttagcompound.getInt("XpLevel");
/*  655 */     this.expTotal = nbttagcompound.getInt("XpTotal");
/*  656 */     setScore(nbttagcompound.getInt("Score"));
/*  657 */     if (this.sleeping) {
/*  658 */       this.bB = new ChunkCoordinates(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ));
/*  659 */       a(true, true, false);
/*      */     } 
/*      */ 
/*      */     
/*  663 */     this.spawnWorld = nbttagcompound.getString("SpawnWorld");
/*  664 */     if ("".equals(this.spawnWorld)) {
/*  665 */       this.spawnWorld = ((World)this.world.getServer().getWorlds().get(0)).getName();
/*      */     }
/*      */ 
/*      */     
/*  669 */     if (nbttagcompound.hasKeyOfType("SpawnX", 99) && nbttagcompound.hasKeyOfType("SpawnY", 99) && nbttagcompound.hasKeyOfType("SpawnZ", 99)) {
/*  670 */       this.c = new ChunkCoordinates(nbttagcompound.getInt("SpawnX"), nbttagcompound.getInt("SpawnY"), nbttagcompound.getInt("SpawnZ"));
/*  671 */       this.d = nbttagcompound.getBoolean("SpawnForced");
/*      */     } 
/*      */     
/*  674 */     this.foodData.a(nbttagcompound);
/*  675 */     this.abilities.b(nbttagcompound);
/*  676 */     if (nbttagcompound.hasKeyOfType("EnderItems", 9)) {
/*  677 */       NBTTagList nbttaglist1 = nbttagcompound.getList("EnderItems", 10);
/*      */       
/*  679 */       this.enderChest.a(nbttaglist1);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void b(NBTTagCompound nbttagcompound) {
/*  684 */     super.b(nbttagcompound);
/*  685 */     nbttagcompound.set("Inventory", this.inventory.a(new NBTTagList()));
/*  686 */     nbttagcompound.setInt("SelectedItemSlot", this.inventory.itemInHandIndex);
/*  687 */     nbttagcompound.setBoolean("Sleeping", this.sleeping);
/*  688 */     nbttagcompound.setShort("SleepTimer", (short)this.sleepTicks);
/*  689 */     nbttagcompound.setFloat("XpP", this.exp);
/*  690 */     nbttagcompound.setInt("XpLevel", this.expLevel);
/*  691 */     nbttagcompound.setInt("XpTotal", this.expTotal);
/*  692 */     nbttagcompound.setInt("Score", getScore());
/*  693 */     if (this.c != null) {
/*  694 */       nbttagcompound.setInt("SpawnX", this.c.x);
/*  695 */       nbttagcompound.setInt("SpawnY", this.c.y);
/*  696 */       nbttagcompound.setInt("SpawnZ", this.c.z);
/*  697 */       nbttagcompound.setBoolean("SpawnForced", this.d);
/*  698 */       nbttagcompound.setString("SpawnWorld", this.spawnWorld);
/*      */     } 
/*      */     
/*  701 */     this.foodData.b(nbttagcompound);
/*  702 */     this.abilities.a(nbttagcompound);
/*  703 */     nbttagcompound.set("EnderItems", this.enderChest.h());
/*      */   }
/*      */   
/*      */   public void openContainer(IInventory iinventory) {}
/*      */   
/*      */   public void openHopper(TileEntityHopper tileentityhopper) {}
/*      */   
/*      */   public void openMinecartHopper(EntityMinecartHopper entityminecarthopper) {}
/*      */   
/*      */   public void openHorseInventory(EntityHorse entityhorse, IInventory iinventory) {}
/*      */   
/*      */   public void startEnchanting(int i, int j, int k, String s) {}
/*      */   
/*      */   public void openAnvil(int i, int j, int k) {}
/*      */   
/*      */   public void startCrafting(int i, int j, int k) {}
/*      */   
/*      */   public float getHeadHeight() {
/*  721 */     return 0.12F;
/*      */   }
/*      */   
/*      */   protected void e_() {
/*  725 */     this.height = 1.62F;
/*      */   }
/*      */   
/*      */   public boolean damageEntity(DamageSource damagesource, float f) {
/*  729 */     if (isInvulnerable())
/*  730 */       return false; 
/*  731 */     if (this.abilities.isInvulnerable && !damagesource.ignoresInvulnerability()) {
/*  732 */       return false;
/*      */     }
/*  734 */     this.aU = 0;
/*  735 */     if (getHealth() <= 0.0F) {
/*  736 */       return false;
/*      */     }
/*  738 */     if (isSleeping() && !this.world.isStatic) {
/*  739 */       a(true, true, false);
/*      */     }
/*      */     
/*  742 */     if (damagesource.r()) {
/*  743 */       if (this.world.difficulty == EnumDifficulty.PEACEFUL) {
/*  744 */         return false;
/*      */       }
/*      */       
/*  747 */       if (this.world.difficulty == EnumDifficulty.EASY) {
/*  748 */         f = f / 2.0F + 1.0F;
/*      */       }
/*      */       
/*  751 */       if (this.world.difficulty == EnumDifficulty.HARD) {
/*  752 */         f = f * 3.0F / 2.0F;
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  759 */     Entity entity = damagesource.getEntity();
/*      */     
/*  761 */     if (entity instanceof EntityArrow && ((EntityArrow)entity).shooter != null) {
/*  762 */       entity = ((EntityArrow)entity).shooter;
/*      */     }
/*      */     
/*  765 */     a(StatisticList.u, Math.round(f * 10.0F));
/*  766 */     return super.damageEntity(damagesource, f);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean a(EntityHuman entityhuman) {
/*      */     Team team;
/*  776 */     if (entityhuman instanceof EntityPlayer) {
/*  777 */       EntityPlayer thatPlayer = (EntityPlayer)entityhuman;
/*  778 */       team = thatPlayer.getBukkitEntity().getScoreboard().getPlayerTeam((OfflinePlayer)thatPlayer.getBukkitEntity());
/*  779 */       if (team == null || team.allowFriendlyFire()) {
/*  780 */         return true;
/*      */       }
/*      */     } else {
/*      */       
/*  784 */       OfflinePlayer thisPlayer = entityhuman.world.getServer().getOfflinePlayer(entityhuman.getName());
/*  785 */       team = entityhuman.world.getServer().getScoreboardManager().getMainScoreboard().getPlayerTeam(thisPlayer);
/*  786 */       if (team == null || team.allowFriendlyFire()) {
/*  787 */         return true;
/*      */       }
/*      */     } 
/*      */     
/*  791 */     if (this instanceof EntityPlayer) {
/*  792 */       return !team.hasPlayer((OfflinePlayer)((EntityPlayer)this).getBukkitEntity());
/*      */     }
/*  794 */     return !team.hasPlayer(this.world.getServer().getOfflinePlayer(getName()));
/*      */   }
/*      */ 
/*      */   
/*      */   protected void damageArmor(float f) {
/*  799 */     this.inventory.a(f);
/*      */   }
/*      */   
/*      */   public int aV() {
/*  803 */     return this.inventory.l();
/*      */   }
/*      */   
/*      */   public float bE() {
/*  807 */     int i = 0;
/*  808 */     ItemStack[] aitemstack = this.inventory.armor;
/*  809 */     int j = aitemstack.length;
/*      */     
/*  811 */     for (int k = 0; k < j; k++) {
/*  812 */       ItemStack itemstack = aitemstack[k];
/*      */       
/*  814 */       if (itemstack != null) {
/*  815 */         i++;
/*      */       }
/*      */     } 
/*      */     
/*  819 */     return i / this.inventory.armor.length;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean d(DamageSource damagesource, float f) {
/*  825 */     return super.d(damagesource, f);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void openFurnace(TileEntityFurnace tileentityfurnace) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void openDispenser(TileEntityDispenser tileentitydispenser) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void a(TileEntity tileentity) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void a(CommandBlockListenerAbstract commandblocklistenerabstract) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void openBrewingStand(TileEntityBrewingStand tileentitybrewingstand) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void openBeacon(TileEntityBeacon tileentitybeacon) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void openTrade(IMerchant imerchant, String s) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void b(ItemStack itemstack) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean q(Entity entity) {
/*  867 */     ItemStack itemstack = bF();
/*  868 */     ItemStack itemstack1 = (itemstack != null) ? itemstack.cloneItemStack() : null;
/*      */     
/*  870 */     if (!entity.c(this)) {
/*  871 */       if (itemstack != null && entity instanceof EntityLiving) {
/*  872 */         if (this.abilities.canInstantlyBuild) {
/*  873 */           itemstack = itemstack1;
/*      */         }
/*      */         
/*  876 */         if (itemstack.a(this, (EntityLiving)entity)) {
/*      */           
/*  878 */           if (itemstack.count == 0 && !this.abilities.canInstantlyBuild) {
/*  879 */             bG();
/*      */           }
/*      */           
/*  882 */           return true;
/*      */         } 
/*      */       } 
/*      */       
/*  886 */       return false;
/*      */     } 
/*  888 */     if (itemstack != null && itemstack == bF()) {
/*  889 */       if (itemstack.count <= 0 && !this.abilities.canInstantlyBuild) {
/*  890 */         bG();
/*  891 */       } else if (itemstack.count < itemstack1.count && this.abilities.canInstantlyBuild) {
/*  892 */         itemstack.count = itemstack1.count;
/*      */       } 
/*      */     }
/*      */     
/*  896 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public ItemStack bF() {
/*  901 */     return this.inventory.getItemInHand();
/*      */   }
/*      */   
/*      */   public void bG() {
/*  905 */     this.inventory.setItem(this.inventory.itemInHandIndex, (ItemStack)null);
/*      */   }
/*      */   
/*      */   public double ad() {
/*  909 */     return (this.height - 0.5F);
/*      */   }
/*      */   
/*      */   public void attack(Entity entity) {
/*  913 */     if (entity.av() && 
/*  914 */       !entity.j(this)) {
/*  915 */       float f = (float)getAttributeInstance(GenericAttributes.e).getValue();
/*  916 */       int i = 0;
/*  917 */       float f1 = 0.0F;
/*      */       
/*  919 */       if (entity instanceof EntityLiving) {
/*  920 */         f1 = EnchantmentManager.a(this, (EntityLiving)entity);
/*  921 */         i += EnchantmentManager.getKnockbackEnchantmentLevel(this, (EntityLiving)entity);
/*      */       } 
/*      */       
/*  924 */       if (isSprinting()) {
/*  925 */         i++;
/*      */       }
/*      */       
/*  928 */       if (f > 0.0F || f1 > 0.0F) {
/*  929 */         boolean flag = (!this.world.paperSpigotConfig.disablePlayerCrits && this.fallDistance > 0.0F && !this.onGround && !h_() && !M() && !hasEffect(MobEffectList.BLINDNESS) && this.vehicle == null && entity instanceof EntityLiving);
/*      */         
/*  931 */         if (flag && f > 0.0F) {
/*  932 */           f *= 1.5F;
/*      */         }
/*      */         
/*  935 */         f += f1;
/*  936 */         boolean flag1 = false;
/*  937 */         int j = EnchantmentManager.getFireAspectEnchantmentLevel(this);
/*      */         
/*  939 */         if (entity instanceof EntityLiving && j > 0 && !entity.isBurning()) {
/*      */           
/*  941 */           EntityCombustByEntityEvent combustEvent = new EntityCombustByEntityEvent((Entity)getBukkitEntity(), (Entity)entity.getBukkitEntity(), 1);
/*  942 */           Bukkit.getPluginManager().callEvent((Event)combustEvent);
/*      */           
/*  944 */           if (!combustEvent.isCancelled()) {
/*  945 */             flag1 = true;
/*  946 */             entity.setOnFire(combustEvent.getDuration());
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/*  951 */         boolean flag2 = entity.damageEntity(DamageSource.playerAttack(this), f);
/*      */         
/*  953 */         if (flag2) {
/*  954 */           if (i > 0) {
/*  955 */             entity.g((-MathHelper.sin(this.yaw * 3.1415927F / 180.0F) * i * 0.5F), 0.1D, (MathHelper.cos(this.yaw * 3.1415927F / 180.0F) * i * 0.5F));
/*  956 */             this.motX *= 0.6D;
/*  957 */             this.motZ *= 0.6D;
/*  958 */             setSprinting(false);
/*      */           } 
/*      */           
/*  961 */           if (flag) {
/*  962 */             b(entity);
/*      */           }
/*      */           
/*  965 */           if (f1 > 0.0F) {
/*  966 */             c(entity);
/*      */           }
/*      */           
/*  969 */           if (f >= 18.0F) {
/*  970 */             a(AchievementList.F);
/*      */           }
/*      */           
/*  973 */           l(entity);
/*  974 */           if (entity instanceof EntityLiving) {
/*  975 */             EnchantmentManager.a((EntityLiving)entity, this);
/*      */           }
/*      */           
/*  978 */           EnchantmentManager.b(this, entity);
/*  979 */           ItemStack itemstack = bF();
/*  980 */           Object object = entity;
/*      */           
/*  982 */           if (entity instanceof EntityComplexPart) {
/*  983 */             IComplex icomplex = ((EntityComplexPart)entity).owner;
/*      */             
/*  985 */             if (icomplex != null && icomplex instanceof EntityLiving) {
/*  986 */               object = icomplex;
/*      */             }
/*      */           } 
/*      */           
/*  990 */           if (itemstack != null && object instanceof EntityLiving) {
/*  991 */             itemstack.a((EntityLiving)object, this);
/*      */             
/*  993 */             if (itemstack.count == 0) {
/*  994 */               bG();
/*      */             }
/*      */           } 
/*      */           
/*  998 */           if (entity instanceof EntityLiving) {
/*  999 */             a(StatisticList.t, Math.round(f * 10.0F));
/* 1000 */             if (j > 0) {
/*      */               
/* 1002 */               EntityCombustByEntityEvent combustEvent = new EntityCombustByEntityEvent((Entity)getBukkitEntity(), (Entity)entity.getBukkitEntity(), j * 4);
/* 1003 */               Bukkit.getPluginManager().callEvent((Event)combustEvent);
/*      */               
/* 1005 */               if (!combustEvent.isCancelled()) {
/* 1006 */                 entity.setOnFire(combustEvent.getDuration());
/*      */               }
/*      */             } 
/*      */           } 
/*      */ 
/*      */           
/* 1012 */           applyExhaustion(this.world.spigotConfig.combatExhaustion);
/* 1013 */         } else if (flag1) {
/* 1014 */           entity.extinguish();
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void b(Entity entity) {}
/*      */   
/*      */   public void c(Entity entity) {}
/*      */   
/*      */   public void die() {
/* 1026 */     super.die();
/* 1027 */     this.defaultContainer.b(this);
/* 1028 */     if (this.activeContainer != null) {
/* 1029 */       this.activeContainer.b(this);
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean inBlock() {
/* 1034 */     return (!this.sleeping && super.inBlock());
/*      */   }
/*      */   
/*      */   public GameProfile getProfile() {
/* 1038 */     return this.i;
/*      */   }
/*      */   
/*      */   public EnumBedResult a(int i, int j, int k) {
/* 1042 */     if (!this.world.isStatic) {
/* 1043 */       if (isSleeping() || !isAlive()) {
/* 1044 */         return EnumBedResult.OTHER_PROBLEM;
/*      */       }
/*      */       
/* 1047 */       if (!this.world.worldProvider.d()) {
/* 1048 */         return EnumBedResult.NOT_POSSIBLE_HERE;
/*      */       }
/*      */       
/* 1051 */       if (this.world.w()) {
/* 1052 */         return EnumBedResult.NOT_POSSIBLE_NOW;
/*      */       }
/*      */       
/* 1055 */       if (Math.abs(this.locX - i) > 3.0D || Math.abs(this.locY - j) > 2.0D || Math.abs(this.locZ - k) > 3.0D) {
/* 1056 */         return EnumBedResult.TOO_FAR_AWAY;
/*      */       }
/*      */       
/* 1059 */       double d0 = 8.0D;
/* 1060 */       double d1 = 5.0D;
/* 1061 */       List list = this.world.a(EntityMonster.class, AxisAlignedBB.a(i - d0, j - d1, k - d0, i + d0, j + d1, k + d0));
/*      */       
/* 1063 */       if (!list.isEmpty()) {
/* 1064 */         return EnumBedResult.NOT_SAFE;
/*      */       }
/*      */     } 
/*      */     
/* 1068 */     if (am()) {
/* 1069 */       mount((Entity)null);
/*      */     }
/*      */ 
/*      */     
/* 1073 */     if (getBukkitEntity() instanceof Player) {
/* 1074 */       Player player = (Player)getBukkitEntity();
/* 1075 */       Block bed = this.world.getWorld().getBlockAt(i, j, k);
/*      */       
/* 1077 */       PlayerBedEnterEvent event = new PlayerBedEnterEvent(player, bed);
/* 1078 */       this.world.getServer().getPluginManager().callEvent((Event)event);
/*      */       
/* 1080 */       if (event.isCancelled()) {
/* 1081 */         return EnumBedResult.OTHER_PROBLEM;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1086 */     a(0.2F, 0.2F);
/* 1087 */     this.height = 0.2F;
/* 1088 */     if (this.world.isLoaded(i, j, k)) {
/* 1089 */       int l = this.world.getData(i, j, k);
/* 1090 */       int i1 = BlockBed.l(l);
/* 1091 */       float f = 0.5F;
/* 1092 */       float f1 = 0.5F;
/*      */       
/* 1094 */       switch (i1) {
/*      */         case 0:
/* 1096 */           f1 = 0.9F;
/*      */           break;
/*      */         
/*      */         case 1:
/* 1100 */           f = 0.1F;
/*      */           break;
/*      */         
/*      */         case 2:
/* 1104 */           f1 = 0.1F;
/*      */           break;
/*      */         
/*      */         case 3:
/* 1108 */           f = 0.9F;
/*      */           break;
/*      */       } 
/* 1111 */       w(i1);
/* 1112 */       setPosition((i + f), (j + 0.9375F), (k + f1));
/*      */     } else {
/* 1114 */       setPosition((i + 0.5F), (j + 0.9375F), (k + 0.5F));
/*      */     } 
/*      */     
/* 1117 */     this.sleeping = true;
/* 1118 */     this.sleepTicks = 0;
/* 1119 */     this.bB = new ChunkCoordinates(i, j, k);
/* 1120 */     this.motX = this.motZ = this.motY = 0.0D;
/* 1121 */     if (!this.world.isStatic) {
/* 1122 */       this.world.everyoneSleeping();
/*      */     }
/*      */     
/* 1125 */     return EnumBedResult.OK;
/*      */   }
/*      */   
/*      */   private void w(int i) {
/* 1129 */     this.bC = 0.0F;
/* 1130 */     this.bD = 0.0F;
/* 1131 */     switch (i) {
/*      */       case 0:
/* 1133 */         this.bD = -1.8F;
/*      */         break;
/*      */       
/*      */       case 1:
/* 1137 */         this.bC = 1.8F;
/*      */         break;
/*      */       
/*      */       case 2:
/* 1141 */         this.bD = 1.8F;
/*      */         break;
/*      */       
/*      */       case 3:
/* 1145 */         this.bC = -1.8F;
/*      */         break;
/*      */     } 
/*      */   }
/*      */   public void a(boolean flag, boolean flag1, boolean flag2) {
/* 1150 */     a(0.6F, 1.8F);
/* 1151 */     e_();
/* 1152 */     ChunkCoordinates chunkcoordinates = this.bB;
/* 1153 */     ChunkCoordinates chunkcoordinates1 = this.bB;
/*      */     
/* 1155 */     if (chunkcoordinates != null && this.world.getType(chunkcoordinates.x, chunkcoordinates.y, chunkcoordinates.z) == Blocks.BED) {
/* 1156 */       BlockBed.a(this.world, chunkcoordinates.x, chunkcoordinates.y, chunkcoordinates.z, false);
/* 1157 */       chunkcoordinates1 = BlockBed.a(this.world, chunkcoordinates.x, chunkcoordinates.y, chunkcoordinates.z, 0);
/* 1158 */       if (chunkcoordinates1 == null) {
/* 1159 */         chunkcoordinates1 = new ChunkCoordinates(chunkcoordinates.x, chunkcoordinates.y + 1, chunkcoordinates.z);
/*      */       }
/*      */       
/* 1162 */       setPosition((chunkcoordinates1.x + 0.5F), (chunkcoordinates1.y + this.height + 0.1F), (chunkcoordinates1.z + 0.5F));
/*      */     } 
/*      */     
/* 1165 */     this.sleeping = false;
/* 1166 */     if (!this.world.isStatic && flag1) {
/* 1167 */       this.world.everyoneSleeping();
/*      */     }
/*      */ 
/*      */     
/* 1171 */     if (getBukkitEntity() instanceof Player) {
/* 1172 */       Block bed; Player player = (Player)getBukkitEntity();
/*      */ 
/*      */       
/* 1175 */       if (chunkcoordinates != null) {
/* 1176 */         bed = this.world.getWorld().getBlockAt(chunkcoordinates.x, chunkcoordinates.y, chunkcoordinates.z);
/*      */       } else {
/* 1178 */         bed = this.world.getWorld().getBlockAt(player.getLocation());
/*      */       } 
/*      */       
/* 1181 */       PlayerBedLeaveEvent event = new PlayerBedLeaveEvent(player, bed);
/* 1182 */       this.world.getServer().getPluginManager().callEvent((Event)event);
/*      */     } 
/*      */ 
/*      */     
/* 1186 */     if (flag) {
/* 1187 */       this.sleepTicks = 0;
/*      */     } else {
/* 1189 */       this.sleepTicks = 100;
/*      */     } 
/*      */     
/* 1192 */     if (flag2) {
/* 1193 */       setRespawnPosition(this.bB, false);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean j() {
/* 1198 */     return (this.world.getType(this.bB.x, this.bB.y, this.bB.z) == Blocks.BED);
/*      */   }
/*      */   
/*      */   public static ChunkCoordinates getBed(World world, ChunkCoordinates chunkcoordinates, boolean flag) {
/* 1202 */     IChunkProvider ichunkprovider = world.L();
/*      */     
/* 1204 */     ichunkprovider.getChunkAt(chunkcoordinates.x - 3 >> 4, chunkcoordinates.z - 3 >> 4);
/* 1205 */     ichunkprovider.getChunkAt(chunkcoordinates.x + 3 >> 4, chunkcoordinates.z - 3 >> 4);
/* 1206 */     ichunkprovider.getChunkAt(chunkcoordinates.x - 3 >> 4, chunkcoordinates.z + 3 >> 4);
/* 1207 */     ichunkprovider.getChunkAt(chunkcoordinates.x + 3 >> 4, chunkcoordinates.z + 3 >> 4);
/* 1208 */     if (world.getType(chunkcoordinates.x, chunkcoordinates.y, chunkcoordinates.z) == Blocks.BED) {
/* 1209 */       ChunkCoordinates chunkcoordinates1 = BlockBed.a(world, chunkcoordinates.x, chunkcoordinates.y, chunkcoordinates.z, 0);
/*      */       
/* 1211 */       return chunkcoordinates1;
/*      */     } 
/* 1213 */     Material material = world.getType(chunkcoordinates.x, chunkcoordinates.y, chunkcoordinates.z).getMaterial();
/* 1214 */     Material material1 = world.getType(chunkcoordinates.x, chunkcoordinates.y + 1, chunkcoordinates.z).getMaterial();
/* 1215 */     boolean flag1 = (!material.isBuildable() && !material.isLiquid());
/* 1216 */     boolean flag2 = (!material1.isBuildable() && !material1.isLiquid());
/*      */     
/* 1218 */     return (flag && flag1 && flag2) ? chunkcoordinates : null;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isSleeping() {
/* 1223 */     return this.sleeping;
/*      */   }
/*      */   
/*      */   public boolean isDeeplySleeping() {
/* 1227 */     return (this.sleeping && this.sleepTicks >= 100);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void b(int i, boolean flag, int version) {
/* 1232 */     ProtocolData.DualByte db = this.datawatcher.getDualByte(16);
/* 1233 */     byte b0 = (version >= 16) ? db.value2 : db.value;
/* 1234 */     if (flag) {
/* 1235 */       b0 = (byte)(b0 | 1 << i);
/*      */     } else {
/* 1237 */       b0 = (byte)(b0 & (1 << i ^ 0xFFFFFFFF));
/*      */     } 
/* 1239 */     if (version >= 16) {
/* 1240 */       db.value2 = b0;
/*      */     } else {
/* 1242 */       db.value = b0;
/*      */     } 
/* 1244 */     this.datawatcher.watch(16, db);
/*      */   }
/*      */ 
/*      */   
/*      */   public void b(IChatBaseComponent ichatbasecomponent) {}
/*      */   
/*      */   public ChunkCoordinates getBed() {
/* 1251 */     return this.c;
/*      */   }
/*      */   
/*      */   public boolean isRespawnForced() {
/* 1255 */     return this.d;
/*      */   }
/*      */   
/*      */   public void setRespawnPosition(ChunkCoordinates chunkcoordinates, boolean flag) {
/* 1259 */     if (chunkcoordinates != null) {
/* 1260 */       this.c = new ChunkCoordinates(chunkcoordinates);
/* 1261 */       this.d = flag;
/* 1262 */       this.spawnWorld = this.world.worldData.getName();
/*      */     } else {
/* 1264 */       this.c = null;
/* 1265 */       this.d = false;
/* 1266 */       this.spawnWorld = "";
/*      */     } 
/*      */   }
/*      */   
/*      */   public void a(Statistic statistic) {
/* 1271 */     a(statistic, 1);
/*      */   }
/*      */   
/*      */   public void a(Statistic statistic, int i) {}
/*      */   
/*      */   public void bj() {
/* 1277 */     super.bj();
/* 1278 */     a(StatisticList.r, 1);
/* 1279 */     if (isSprinting()) {
/* 1280 */       applyExhaustion(this.world.spigotConfig.sprintExhaustion);
/*      */     } else {
/* 1282 */       applyExhaustion(this.world.spigotConfig.walkExhaustion);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void e(float f, float f1) {
/* 1287 */     double d0 = this.locX;
/* 1288 */     double d1 = this.locY;
/* 1289 */     double d2 = this.locZ;
/*      */     
/* 1291 */     if (this.abilities.isFlying && this.vehicle == null) {
/* 1292 */       double d3 = this.motY;
/* 1293 */       float f2 = this.aQ;
/*      */       
/* 1295 */       this.aQ = this.abilities.a();
/* 1296 */       super.e(f, f1);
/* 1297 */       this.motY = d3 * 0.6D;
/* 1298 */       this.aQ = f2;
/*      */     } else {
/* 1300 */       super.e(f, f1);
/*      */     } 
/*      */     
/* 1303 */     checkMovement(this.locX - d0, this.locY - d1, this.locZ - d2);
/*      */   }
/*      */   
/*      */   public float bl() {
/* 1307 */     return (float)getAttributeInstance(GenericAttributes.d).getValue();
/*      */   }
/*      */   
/*      */   public void checkMovement(double d0, double d1, double d2) {
/* 1311 */     if (this.vehicle == null)
/*      */     {
/*      */       
/* 1314 */       if (a(Material.WATER)) {
/* 1315 */         int i = Math.round(MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2) * 100.0F);
/* 1316 */         if (i > 0) {
/* 1317 */           a(StatisticList.m, i);
/* 1318 */           applyExhaustion(this.world.paperSpigotConfig.playerSwimmingExhaustion * i * 0.01F);
/*      */         } 
/* 1320 */       } else if (M()) {
/* 1321 */         int i = Math.round(MathHelper.sqrt(d0 * d0 + d2 * d2) * 100.0F);
/* 1322 */         if (i > 0) {
/* 1323 */           a(StatisticList.i, i);
/* 1324 */           applyExhaustion(this.world.paperSpigotConfig.playerSwimmingExhaustion * i * 0.01F);
/*      */         } 
/* 1326 */       } else if (h_()) {
/* 1327 */         if (d1 > 0.0D) {
/* 1328 */           a(StatisticList.k, (int)Math.round(d1 * 100.0D));
/*      */         }
/* 1330 */       } else if (this.onGround) {
/* 1331 */         int i = Math.round(MathHelper.sqrt(d0 * d0 + d2 * d2) * 100.0F);
/* 1332 */         if (i > 0) {
/* 1333 */           a(StatisticList.h, i);
/* 1334 */           if (isSprinting()) {
/* 1335 */             applyExhaustion(0.099999994F * i * 0.01F);
/*      */           } else {
/* 1337 */             applyExhaustion(0.01F * i * 0.01F);
/*      */           } 
/*      */         } 
/*      */       } else {
/* 1341 */         int i = Math.round(MathHelper.sqrt(d0 * d0 + d2 * d2) * 100.0F);
/* 1342 */         if (i > 25) {
/* 1343 */           a(StatisticList.l, i);
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private void l(double d0, double d1, double d2) {
/* 1350 */     if (this.vehicle != null) {
/* 1351 */       int i = Math.round(MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2) * 100.0F);
/*      */       
/* 1353 */       if (i > 0) {
/* 1354 */         if (this.vehicle instanceof EntityMinecartAbstract) {
/* 1355 */           a(StatisticList.n, i);
/* 1356 */           if (this.e == null) {
/* 1357 */             this.e = new ChunkCoordinates(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ));
/* 1358 */           } else if (this.e.e(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ)) >= 1000000.0D) {
/* 1359 */             a(AchievementList.q, 1);
/*      */           } 
/* 1361 */         } else if (this.vehicle instanceof EntityBoat) {
/* 1362 */           a(StatisticList.o, i);
/* 1363 */         } else if (this.vehicle instanceof EntityPig) {
/* 1364 */           a(StatisticList.p, i);
/* 1365 */         } else if (this.vehicle instanceof EntityHorse) {
/* 1366 */           a(StatisticList.q, i);
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void b(float f) {
/* 1373 */     if (!this.abilities.canFly) {
/* 1374 */       if (f >= 2.0F) {
/* 1375 */         a(StatisticList.j, (int)Math.round(f * 100.0D));
/*      */       }
/*      */       
/* 1378 */       super.b(f);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected String o(int i) {
/* 1383 */     return (i > 4) ? "game.player.hurt.fall.big" : "game.player.hurt.fall.small";
/*      */   }
/*      */   
/*      */   public void a(EntityLiving entityliving) {
/* 1387 */     if (entityliving instanceof IMonster) {
/* 1388 */       a(AchievementList.s);
/*      */     }
/*      */     
/* 1391 */     int i = EntityTypes.a(entityliving);
/* 1392 */     MonsterEggInfo monsteregginfo = (MonsterEggInfo)EntityTypes.eggInfo.get(Integer.valueOf(i));
/*      */     
/* 1394 */     if (monsteregginfo != null) {
/* 1395 */       a(monsteregginfo.killEntityStatistic, 1);
/*      */     }
/*      */   }
/*      */   
/*      */   public void as() {
/* 1400 */     if (!this.abilities.isFlying) {
/* 1401 */       super.as();
/*      */     }
/*      */   }
/*      */   
/*      */   public ItemStack r(int i) {
/* 1406 */     return this.inventory.d(i);
/*      */   }
/*      */   
/*      */   public void giveExp(int i) {
/* 1410 */     addScore(i);
/* 1411 */     int j = Integer.MAX_VALUE - this.expTotal;
/*      */     
/* 1413 */     if (i > j) {
/* 1414 */       i = j;
/*      */     }
/*      */     
/* 1417 */     this.exp += i / getExpToLevel();
/*      */     
/* 1419 */     for (this.expTotal += i; this.exp >= 1.0F; this.exp /= getExpToLevel()) {
/* 1420 */       this.exp = (this.exp - 1.0F) * getExpToLevel();
/* 1421 */       levelDown(1);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void levelDown(int i) {
/* 1426 */     this.expLevel += i;
/* 1427 */     if (this.expLevel < 0) {
/* 1428 */       this.expLevel = 0;
/* 1429 */       this.exp = 0.0F;
/* 1430 */       this.expTotal = 0;
/*      */     } 
/*      */     
/* 1433 */     if (i > 0 && this.expLevel % 5 == 0 && this.h < this.ticksLived - 100.0F) {
/* 1434 */       float f = (this.expLevel > 30) ? 1.0F : (this.expLevel / 30.0F);
/*      */       
/* 1436 */       this.world.makeSound(this, "random.levelup", f * 0.75F, 1.0F);
/* 1437 */       this.h = this.ticksLived;
/*      */     } 
/*      */   }
/*      */   
/*      */   public int getExpToLevel() {
/* 1442 */     return (this.expLevel >= 30) ? (62 + (this.expLevel - 30) * 7) : ((this.expLevel >= 15) ? (17 + (this.expLevel - 15) * 3) : 17);
/*      */   }
/*      */   
/*      */   public void applyExhaustion(float f) {
/* 1446 */     if (!this.abilities.isInvulnerable && 
/* 1447 */       !this.world.isStatic) {
/* 1448 */       this.foodData.a(f);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public FoodMetaData getFoodData() {
/* 1454 */     return this.foodData;
/*      */   }
/*      */   
/*      */   public boolean g(boolean flag) {
/* 1458 */     return ((flag || this.foodData.c()) && !this.abilities.isInvulnerable);
/*      */   }
/*      */   
/*      */   public boolean bR() {
/* 1462 */     return (getHealth() > 0.0F && getHealth() < getMaxHealth());
/*      */   }
/*      */   
/*      */   public void a(ItemStack itemstack, int i) {
/* 1466 */     if (itemstack != this.f) {
/* 1467 */       this.f = itemstack;
/* 1468 */       this.g = i;
/* 1469 */       if (!this.world.isStatic) {
/* 1470 */         e(true);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean d(int i, int j, int k) {
/* 1476 */     if (this.abilities.mayBuild) {
/* 1477 */       return true;
/*      */     }
/* 1479 */     Block block = this.world.getType(i, j, k);
/*      */     
/* 1481 */     if (block.getMaterial() != Material.AIR) {
/* 1482 */       if (block.getMaterial().q()) {
/* 1483 */         return true;
/*      */       }
/*      */       
/* 1486 */       if (bF() != null) {
/* 1487 */         ItemStack itemstack = bF();
/*      */         
/* 1489 */         if (itemstack.b(block) || itemstack.a(block) > 1.0F) {
/* 1490 */           return true;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1495 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean a(int i, int j, int k, int l, ItemStack itemstack) {
/* 1500 */     return this.abilities.mayBuild ? true : ((itemstack != null) ? itemstack.z() : false);
/*      */   }
/*      */   
/*      */   protected int getExpValue(EntityHuman entityhuman) {
/* 1504 */     if (this.world.getGameRules().getBoolean("keepInventory")) {
/* 1505 */       return 0;
/*      */     }
/* 1507 */     int i = this.expLevel * 7;
/*      */     
/* 1509 */     return (i > 100) ? 100 : i;
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean alwaysGivesExp() {
/* 1514 */     return true;
/*      */   }
/*      */   
/*      */   public void copyTo(EntityHuman entityhuman, boolean flag) {
/* 1518 */     if (flag) {
/* 1519 */       this.inventory.b(entityhuman.inventory);
/* 1520 */       setHealth(entityhuman.getHealth());
/* 1521 */       this.foodData = entityhuman.foodData;
/* 1522 */       this.expLevel = entityhuman.expLevel;
/* 1523 */       this.expTotal = entityhuman.expTotal;
/* 1524 */       this.exp = entityhuman.exp;
/* 1525 */       setScore(entityhuman.getScore());
/* 1526 */       this.aq = entityhuman.aq;
/* 1527 */     } else if (this.world.getGameRules().getBoolean("keepInventory")) {
/* 1528 */       this.inventory.b(entityhuman.inventory);
/* 1529 */       this.expLevel = entityhuman.expLevel;
/* 1530 */       this.expTotal = entityhuman.expTotal;
/* 1531 */       this.exp = entityhuman.exp;
/* 1532 */       setScore(entityhuman.getScore());
/*      */     } 
/*      */     
/* 1535 */     this.enderChest = entityhuman.enderChest;
/*      */   }
/*      */   
/*      */   protected boolean g_() {
/* 1539 */     return !this.abilities.isFlying;
/*      */   }
/*      */   
/*      */   public void updateAbilities() {}
/*      */   
/*      */   public void a(EnumGamemode enumgamemode) {}
/*      */   
/*      */   public String getName() {
/* 1547 */     return this.i.getName();
/*      */   }
/*      */   
/*      */   public World getWorld() {
/* 1551 */     return this.world;
/*      */   }
/*      */   
/*      */   public InventoryEnderChest getEnderChest() {
/* 1555 */     return this.enderChest;
/*      */   }
/*      */   
/*      */   public ItemStack getEquipment(int i) {
/* 1559 */     return (i == 0) ? this.inventory.getItemInHand() : this.inventory.armor[i - 1];
/*      */   }
/*      */   
/*      */   public ItemStack be() {
/* 1563 */     return this.inventory.getItemInHand();
/*      */   }
/*      */   
/*      */   public void setEquipment(int i, ItemStack itemstack) {
/* 1567 */     this.inventory.armor[i] = itemstack;
/*      */   }
/*      */   
/*      */   public ItemStack[] getEquipment() {
/* 1571 */     return this.inventory.armor;
/*      */   }
/*      */   
/*      */   public boolean aC() {
/* 1575 */     return !this.abilities.isFlying;
/*      */   }
/*      */   
/*      */   public Scoreboard getScoreboard() {
/* 1579 */     return this.world.getScoreboard();
/*      */   }
/*      */   
/*      */   public ScoreboardTeamBase getScoreboardTeam() {
/* 1583 */     return getScoreboard().getPlayerTeam(getName());
/*      */   }
/*      */ 
/*      */   
/*      */   public IChatBaseComponent getScoreboardDisplayName() {
/* 1588 */     ChatComponentText chatcomponenttext = new ChatComponentText(ScoreboardTeam.getPlayerDisplayName(getScoreboardTeam(), getName()));
/*      */     
/* 1590 */     chatcomponenttext.getChatModifier().setChatClickable(new ChatClickable(EnumClickAction.SUGGEST_COMMAND, "/msg " + getName() + " "));
/* 1591 */     return chatcomponenttext;
/*      */   }
/*      */   
/*      */   public void setAbsorptionHearts(float f) {
/* 1595 */     if (f < 0.0F) {
/* 1596 */       f = 0.0F;
/*      */     }
/*      */     
/* 1599 */     getDataWatcher().watch(17, Float.valueOf(f));
/*      */   }
/*      */   
/*      */   public float getAbsorptionHearts() {
/* 1603 */     return getDataWatcher().getFloat(17);
/*      */   }
/*      */   
/*      */   public static UUID a(GameProfile gameprofile) {
/* 1607 */     UUID uuid = gameprofile.getId();
/*      */     
/* 1609 */     if (uuid == null) {
/* 1610 */       uuid = UUID.nameUUIDFromBytes(("OfflinePlayer:" + gameprofile.getName()).getBytes(Charsets.UTF_8));
/*      */     }
/*      */     
/* 1613 */     return uuid;
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityHuman.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
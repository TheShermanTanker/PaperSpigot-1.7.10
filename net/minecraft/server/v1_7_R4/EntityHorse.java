/*      */ package net.minecraft.server.v1_7_R4;
/*      */ 
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*      */ import org.bukkit.event.entity.EntityRegainHealthEvent;
/*      */ import org.bukkit.event.entity.HorseJumpEvent;
/*      */ 
/*      */ public class EntityHorse extends EntityAnimal implements IInventoryListener {
/*   10 */   private static final IEntitySelector bu = new EntitySelectorHorse();
/*   11 */   public static final IAttribute attributeJumpStrength = (new AttributeRanged("horse.jumpStrength", 0.7D, 0.0D, 2.0D)).a("Jump Strength").a(true);
/*   12 */   private static final String[] bw = new String[] { null, "textures/entity/horse/armor/horse_armor_iron.png", "textures/entity/horse/armor/horse_armor_gold.png", "textures/entity/horse/armor/horse_armor_diamond.png" };
/*   13 */   private static final String[] bx = new String[] { "", "meo", "goo", "dio" };
/*   14 */   private static final int[] by = new int[] { 0, 5, 7, 11 };
/*   15 */   private static final String[] bz = new String[] { "textures/entity/horse/horse_white.png", "textures/entity/horse/horse_creamy.png", "textures/entity/horse/horse_chestnut.png", "textures/entity/horse/horse_brown.png", "textures/entity/horse/horse_black.png", "textures/entity/horse/horse_gray.png", "textures/entity/horse/horse_darkbrown.png" };
/*   16 */   private static final String[] bA = new String[] { "hwh", "hcr", "hch", "hbr", "hbl", "hgr", "hdb" };
/*   17 */   private static final String[] bB = new String[] { null, "textures/entity/horse/horse_markings_white.png", "textures/entity/horse/horse_markings_whitefield.png", "textures/entity/horse/horse_markings_whitedots.png", "textures/entity/horse/horse_markings_blackdots.png" };
/*   18 */   private static final String[] bC = new String[] { "", "wo_", "wmo", "wdo", "bdo" };
/*      */   private int bD;
/*      */   private int bE;
/*      */   private int bF;
/*      */   public int bp;
/*      */   public int bq;
/*      */   protected boolean br;
/*      */   public InventoryHorseChest inventoryChest;
/*      */   private boolean bH;
/*      */   protected int bs;
/*      */   protected float bt;
/*      */   private boolean bI;
/*      */   private float bJ;
/*      */   private float bK;
/*      */   private float bL;
/*      */   private float bM;
/*      */   private float bN;
/*      */   private float bO;
/*      */   private int bP;
/*      */   private String bQ;
/*   38 */   private String[] bR = new String[3];
/*   39 */   public int maxDomestication = 100;
/*      */   
/*      */   public EntityHorse(World world) {
/*   42 */     super(world);
/*   43 */     a(1.4F, 1.6F);
/*   44 */     this.fireProof = false;
/*   45 */     setHasChest(false);
/*   46 */     getNavigation().a(true);
/*   47 */     this.goalSelector.a(0, new PathfinderGoalFloat(this));
/*   48 */     this.goalSelector.a(1, new PathfinderGoalPanic(this, 1.2D));
/*   49 */     this.goalSelector.a(1, new PathfinderGoalTame(this, 1.2D));
/*   50 */     this.goalSelector.a(2, new PathfinderGoalBreed(this, 1.0D));
/*   51 */     this.goalSelector.a(4, new PathfinderGoalFollowParent(this, 1.0D));
/*   52 */     this.goalSelector.a(6, new PathfinderGoalRandomStroll(this, 0.7D));
/*   53 */     this.goalSelector.a(7, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
/*   54 */     this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
/*   55 */     loadChest();
/*      */   }
/*      */   
/*      */   protected void c() {
/*   59 */     super.c();
/*   60 */     this.datawatcher.a(16, Integer.valueOf(0));
/*   61 */     this.datawatcher.a(19, Byte.valueOf((byte)0));
/*   62 */     this.datawatcher.a(20, Integer.valueOf(0));
/*   63 */     this.datawatcher.a(21, String.valueOf(""));
/*   64 */     this.datawatcher.a(22, Integer.valueOf(0));
/*      */   }
/*      */   
/*      */   public void setType(int i) {
/*   68 */     this.datawatcher.watch(19, Byte.valueOf((byte)i));
/*   69 */     cP();
/*      */   }
/*      */   
/*      */   public int getType() {
/*   73 */     return this.datawatcher.getByte(19);
/*      */   }
/*      */   
/*      */   public void setVariant(int i) {
/*   77 */     this.datawatcher.watch(20, Integer.valueOf(i));
/*   78 */     cP();
/*      */   }
/*      */   
/*      */   public int getVariant() {
/*   82 */     return this.datawatcher.getInt(20);
/*      */   }
/*      */   
/*      */   public String getName() {
/*   86 */     if (hasCustomName()) {
/*   87 */       return getCustomName();
/*      */     }
/*   89 */     int i = getType();
/*      */     
/*   91 */     switch (i) {
/*      */       
/*      */       default:
/*   94 */         return LocaleI18n.get("entity.horse.name");
/*      */       
/*      */       case 1:
/*   97 */         return LocaleI18n.get("entity.donkey.name");
/*      */       
/*      */       case 2:
/*  100 */         return LocaleI18n.get("entity.mule.name");
/*      */       
/*      */       case 3:
/*  103 */         return LocaleI18n.get("entity.zombiehorse.name");
/*      */       case 4:
/*      */         break;
/*  106 */     }  return LocaleI18n.get("entity.skeletonhorse.name");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean x(int i) {
/*  112 */     return ((this.datawatcher.getInt(16) & i) != 0);
/*      */   }
/*      */   
/*      */   private void b(int i, boolean flag) {
/*  116 */     int j = this.datawatcher.getInt(16);
/*      */     
/*  118 */     if (flag) {
/*  119 */       this.datawatcher.watch(16, Integer.valueOf(j | i));
/*      */     } else {
/*  121 */       this.datawatcher.watch(16, Integer.valueOf(j & (i ^ 0xFFFFFFFF)));
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean cb() {
/*  126 */     return !isBaby();
/*      */   }
/*      */   
/*      */   public boolean isTame() {
/*  130 */     return x(2);
/*      */   }
/*      */   
/*      */   public boolean cg() {
/*  134 */     return cb();
/*      */   }
/*      */   
/*      */   public String getOwnerUUID() {
/*  138 */     return this.datawatcher.getString(21);
/*      */   }
/*      */   
/*      */   public void setOwnerUUID(String s) {
/*  142 */     this.datawatcher.watch(21, s);
/*      */   }
/*      */   
/*      */   public float ci() {
/*  146 */     int i = getAge();
/*      */     
/*  148 */     return (i >= 0) ? 1.0F : (0.5F + (-24000 - i) / -24000.0F * 0.5F);
/*      */   }
/*      */   
/*      */   public void a(boolean flag) {
/*  152 */     if (flag) {
/*  153 */       a(ci());
/*      */     } else {
/*  155 */       a(1.0F);
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean cj() {
/*  160 */     return this.br;
/*      */   }
/*      */   
/*      */   public void setTame(boolean flag) {
/*  164 */     b(2, flag);
/*      */   }
/*      */   
/*      */   public void j(boolean flag) {
/*  168 */     this.br = flag;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean bM() {
/*  173 */     if (this.world.paperSpigotConfig.allowUndeadHorseLeashing) {
/*  174 */       return super.bM();
/*      */     }
/*  176 */     return (!cE() && super.bM());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void o(float f) {
/*  182 */     if (f > 6.0F && cm()) {
/*  183 */       o(false);
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean hasChest() {
/*  188 */     return x(8);
/*      */   }
/*      */   
/*      */   public int cl() {
/*  192 */     return this.datawatcher.getInt(22);
/*      */   }
/*      */   
/*      */   private int e(ItemStack itemstack) {
/*  196 */     if (itemstack == null) {
/*  197 */       return 0;
/*      */     }
/*  199 */     Item item = itemstack.getItem();
/*      */     
/*  201 */     return (item == Items.HORSE_ARMOR_IRON) ? 1 : ((item == Items.HORSE_ARMOR_GOLD) ? 2 : ((item == Items.HORSE_ARMOR_DIAMOND) ? 3 : 0));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean cm() {
/*  206 */     return x(32);
/*      */   }
/*      */   
/*      */   public boolean cn() {
/*  210 */     return x(64);
/*      */   }
/*      */   
/*      */   public boolean co() {
/*  214 */     return x(16);
/*      */   }
/*      */   
/*      */   public boolean cp() {
/*  218 */     return this.bH;
/*      */   }
/*      */   
/*      */   public void d(ItemStack itemstack) {
/*  222 */     this.datawatcher.watch(22, Integer.valueOf(e(itemstack)));
/*  223 */     cP();
/*      */   }
/*      */   
/*      */   public void k(boolean flag) {
/*  227 */     b(16, flag);
/*      */   }
/*      */   
/*      */   public void setHasChest(boolean flag) {
/*  231 */     b(8, flag);
/*      */   }
/*      */   
/*      */   public void m(boolean flag) {
/*  235 */     this.bH = flag;
/*      */   }
/*      */   
/*      */   public void n(boolean flag) {
/*  239 */     b(4, flag);
/*      */   }
/*      */   
/*      */   public int getTemper() {
/*  243 */     return this.bs;
/*      */   }
/*      */   
/*      */   public void setTemper(int i) {
/*  247 */     this.bs = i;
/*      */   }
/*      */   
/*      */   public int v(int i) {
/*  251 */     int j = MathHelper.a(getTemper() + i, 0, getMaxDomestication());
/*      */     
/*  253 */     setTemper(j);
/*  254 */     return j;
/*      */   }
/*      */   
/*      */   public boolean damageEntity(DamageSource damagesource, float f) {
/*  258 */     Entity entity = damagesource.getEntity();
/*      */     
/*  260 */     return (this.passenger != null && this.passenger.equals(entity)) ? false : super.damageEntity(damagesource, f);
/*      */   }
/*      */   
/*      */   public int aV() {
/*  264 */     return by[cl()];
/*      */   }
/*      */   
/*      */   public boolean S() {
/*  268 */     return (this.passenger == null);
/*      */   }
/*      */   
/*      */   public boolean cr() {
/*  272 */     int i = MathHelper.floor(this.locX);
/*  273 */     int j = MathHelper.floor(this.locZ);
/*      */     
/*  275 */     this.world.getBiome(i, j);
/*  276 */     return true;
/*      */   }
/*      */   
/*      */   public void cs() {
/*  280 */     if (!this.world.isStatic && hasChest()) {
/*  281 */       a(Item.getItemOf(Blocks.CHEST), 1);
/*  282 */       setHasChest(false);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void cL() {
/*  287 */     cS();
/*  288 */     this.world.makeSound(this, "eating", 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
/*      */   }
/*      */   
/*      */   protected void b(float f) {
/*  292 */     if (f > 1.0F) {
/*  293 */       makeSound("mob.horse.land", 0.4F, 1.0F);
/*      */     }
/*      */     
/*  296 */     int i = MathHelper.f(f * 0.5F - 3.0F);
/*      */     
/*  298 */     if (i > 0) {
/*  299 */       damageEntity(DamageSource.FALL, i);
/*  300 */       if (this.passenger != null) {
/*  301 */         this.passenger.damageEntity(DamageSource.FALL, i);
/*      */       }
/*      */       
/*  304 */       Block block = this.world.getType(MathHelper.floor(this.locX), MathHelper.floor(this.locY - 0.2D - this.lastYaw), MathHelper.floor(this.locZ));
/*      */       
/*  306 */       if (block.getMaterial() != Material.AIR) {
/*  307 */         StepSound stepsound = block.stepSound;
/*      */         
/*  309 */         this.world.makeSound(this, stepsound.getStepSound(), stepsound.getVolume1() * 0.5F, stepsound.getVolume2() * 0.75F);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private int cM() {
/*  315 */     int i = getType();
/*      */     
/*  317 */     return hasChest() ? 17 : 2;
/*      */   }
/*      */   
/*      */   public void loadChest() {
/*  321 */     InventoryHorseChest inventoryhorsechest = this.inventoryChest;
/*      */     
/*  323 */     this.inventoryChest = new InventoryHorseChest("HorseChest", cM(), this);
/*  324 */     this.inventoryChest.a(getName());
/*  325 */     if (inventoryhorsechest != null) {
/*  326 */       inventoryhorsechest.b(this);
/*  327 */       int i = Math.min(inventoryhorsechest.getSize(), this.inventoryChest.getSize());
/*      */       
/*  329 */       for (int j = 0; j < i; j++) {
/*  330 */         ItemStack itemstack = inventoryhorsechest.getItem(j);
/*      */         
/*  332 */         if (itemstack != null) {
/*  333 */           this.inventoryChest.setItem(j, itemstack.cloneItemStack());
/*      */         }
/*      */       } 
/*      */       
/*  337 */       inventoryhorsechest = null;
/*      */     } 
/*      */     
/*  340 */     this.inventoryChest.a(this);
/*  341 */     cO();
/*      */   }
/*      */   
/*      */   private void cO() {
/*  345 */     if (!this.world.isStatic) {
/*  346 */       n((this.inventoryChest.getItem(0) != null));
/*  347 */       if (cB()) {
/*  348 */         d(this.inventoryChest.getItem(1));
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public void a(InventorySubcontainer inventorysubcontainer) {
/*  354 */     int i = cl();
/*  355 */     boolean flag = cu();
/*      */     
/*  357 */     cO();
/*  358 */     if (this.ticksLived > 20) {
/*  359 */       if (i == 0 && i != cl()) {
/*  360 */         makeSound("mob.horse.armor", 0.5F, 1.0F);
/*  361 */       } else if (i != cl()) {
/*  362 */         makeSound("mob.horse.armor", 0.5F, 1.0F);
/*      */       } 
/*      */       
/*  365 */       if (!flag && cu()) {
/*  366 */         makeSound("mob.horse.leather", 0.5F, 1.0F);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean canSpawn() {
/*  372 */     cr();
/*  373 */     return super.canSpawn();
/*      */   }
/*      */   
/*      */   protected EntityHorse a(Entity entity, double d0) {
/*  377 */     double d1 = Double.MAX_VALUE;
/*  378 */     Entity entity1 = null;
/*  379 */     List list = this.world.getEntities(entity, entity.boundingBox.a(d0, d0, d0), bu);
/*  380 */     Iterator<Entity> iterator = list.iterator();
/*      */     
/*  382 */     while (iterator.hasNext()) {
/*  383 */       Entity entity2 = iterator.next();
/*  384 */       double d2 = entity2.e(entity.locX, entity.locY, entity.locZ);
/*      */       
/*  386 */       if (d2 < d1) {
/*  387 */         entity1 = entity2;
/*  388 */         d1 = d2;
/*      */       } 
/*      */     } 
/*      */     
/*  392 */     return (EntityHorse)entity1;
/*      */   }
/*      */   
/*      */   public double getJumpStrength() {
/*  396 */     return getAttributeInstance(attributeJumpStrength).getValue();
/*      */   }
/*      */   
/*      */   protected String aU() {
/*  400 */     cS();
/*  401 */     int i = getType();
/*      */     
/*  403 */     return (i == 3) ? "mob.horse.zombie.death" : ((i == 4) ? "mob.horse.skeleton.death" : ((i != 1 && i != 2) ? "mob.horse.death" : "mob.horse.donkey.death"));
/*      */   }
/*      */   
/*      */   protected Item getLoot() {
/*  407 */     boolean flag = (this.random.nextInt(4) == 0);
/*  408 */     int i = getType();
/*      */     
/*  410 */     return (i == 4) ? Items.BONE : ((i == 3) ? (flag ? Item.getById(0) : Items.ROTTEN_FLESH) : Items.LEATHER);
/*      */   }
/*      */   
/*      */   protected String aT() {
/*  414 */     cS();
/*  415 */     if (this.random.nextInt(3) == 0) {
/*  416 */       cU();
/*      */     }
/*      */     
/*  419 */     int i = getType();
/*      */     
/*  421 */     return (i == 3) ? "mob.horse.zombie.hit" : ((i == 4) ? "mob.horse.skeleton.hit" : ((i != 1 && i != 2) ? "mob.horse.hit" : "mob.horse.donkey.hit"));
/*      */   }
/*      */   
/*      */   public boolean cu() {
/*  425 */     return x(4);
/*      */   }
/*      */   
/*      */   protected String t() {
/*  429 */     cS();
/*  430 */     if (this.random.nextInt(10) == 0 && !bh()) {
/*  431 */       cU();
/*      */     }
/*      */     
/*  434 */     int i = getType();
/*      */     
/*  436 */     return (i == 3) ? "mob.horse.zombie.idle" : ((i == 4) ? "mob.horse.skeleton.idle" : ((i != 1 && i != 2) ? "mob.horse.idle" : "mob.horse.donkey.idle"));
/*      */   }
/*      */   
/*      */   protected String cv() {
/*  440 */     cS();
/*  441 */     cU();
/*  442 */     int i = getType();
/*      */     
/*  444 */     return (i != 3 && i != 4) ? ((i != 1 && i != 2) ? "mob.horse.angry" : "mob.horse.donkey.angry") : null;
/*      */   }
/*      */   
/*      */   protected void a(int i, int j, int k, Block block) {
/*  448 */     StepSound stepsound = block.stepSound;
/*      */     
/*  450 */     if (this.world.getType(i, j + 1, k) == Blocks.SNOW) {
/*  451 */       stepsound = Blocks.SNOW.stepSound;
/*      */     }
/*      */     
/*  454 */     if (!block.getMaterial().isLiquid()) {
/*  455 */       int l = getType();
/*      */       
/*  457 */       if (this.passenger != null && l != 1 && l != 2) {
/*  458 */         this.bP++;
/*  459 */         if (this.bP > 5 && this.bP % 3 == 0) {
/*  460 */           makeSound("mob.horse.gallop", stepsound.getVolume1() * 0.15F, stepsound.getVolume2());
/*  461 */           if (l == 0 && this.random.nextInt(10) == 0) {
/*  462 */             makeSound("mob.horse.breathe", stepsound.getVolume1() * 0.6F, stepsound.getVolume2());
/*      */           }
/*  464 */         } else if (this.bP <= 5) {
/*  465 */           makeSound("mob.horse.wood", stepsound.getVolume1() * 0.15F, stepsound.getVolume2());
/*      */         } 
/*  467 */       } else if (stepsound == Block.f) {
/*  468 */         makeSound("mob.horse.wood", stepsound.getVolume1() * 0.15F, stepsound.getVolume2());
/*      */       } else {
/*  470 */         makeSound("mob.horse.soft", stepsound.getVolume1() * 0.15F, stepsound.getVolume2());
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void aD() {
/*  476 */     super.aD();
/*  477 */     getAttributeMap().b(attributeJumpStrength);
/*  478 */     getAttributeInstance(GenericAttributes.maxHealth).setValue(53.0D);
/*  479 */     getAttributeInstance(GenericAttributes.d).setValue(0.22499999403953552D);
/*      */   }
/*      */   
/*      */   public int bB() {
/*  483 */     return 6;
/*      */   }
/*      */   
/*      */   public int getMaxDomestication() {
/*  487 */     return this.maxDomestication;
/*      */   }
/*      */   
/*      */   protected float bf() {
/*  491 */     return 0.8F;
/*      */   }
/*      */   
/*      */   public int q() {
/*  495 */     return 400;
/*      */   }
/*      */   
/*      */   private void cP() {
/*  499 */     this.bQ = null;
/*      */   }
/*      */   
/*      */   public void g(EntityHuman entityhuman) {
/*  503 */     if (!this.world.isStatic && (this.passenger == null || this.passenger == entityhuman) && isTame()) {
/*  504 */       this.inventoryChest.a(getName());
/*  505 */       entityhuman.openHorseInventory(this, this.inventoryChest);
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean a(EntityHuman entityhuman) {
/*  510 */     ItemStack itemstack = entityhuman.inventory.getItemInHand();
/*      */     
/*  512 */     if (itemstack != null && itemstack.getItem() == Items.MONSTER_EGG)
/*  513 */       return super.a(entityhuman); 
/*  514 */     if (!isTame() && cE())
/*  515 */       return false; 
/*  516 */     if (isTame() && cb() && entityhuman.isSneaking()) {
/*  517 */       g(entityhuman);
/*  518 */       return true;
/*  519 */     }  if (cg() && this.passenger != null) {
/*  520 */       return super.a(entityhuman);
/*      */     }
/*  522 */     if (itemstack != null) {
/*  523 */       boolean flag = false;
/*      */       
/*  525 */       if (cB()) {
/*  526 */         byte b0 = -1;
/*      */         
/*  528 */         if (itemstack.getItem() == Items.HORSE_ARMOR_IRON) {
/*  529 */           b0 = 1;
/*  530 */         } else if (itemstack.getItem() == Items.HORSE_ARMOR_GOLD) {
/*  531 */           b0 = 2;
/*  532 */         } else if (itemstack.getItem() == Items.HORSE_ARMOR_DIAMOND) {
/*  533 */           b0 = 3;
/*      */         } 
/*      */         
/*  536 */         if (b0 >= 0) {
/*  537 */           if (!isTame()) {
/*  538 */             cJ();
/*  539 */             return true;
/*      */           } 
/*      */           
/*  542 */           g(entityhuman);
/*  543 */           return true;
/*      */         } 
/*      */       } 
/*      */       
/*  547 */       if (!flag && !cE()) {
/*  548 */         float f = 0.0F;
/*  549 */         short short1 = 0;
/*  550 */         byte b1 = 0;
/*      */         
/*  552 */         if (itemstack.getItem() == Items.WHEAT) {
/*  553 */           f = 2.0F;
/*  554 */           short1 = 60;
/*  555 */           b1 = 3;
/*  556 */         } else if (itemstack.getItem() == Items.SUGAR) {
/*  557 */           f = 1.0F;
/*  558 */           short1 = 30;
/*  559 */           b1 = 3;
/*  560 */         } else if (itemstack.getItem() == Items.BREAD) {
/*  561 */           f = 7.0F;
/*  562 */           short1 = 180;
/*  563 */           b1 = 3;
/*  564 */         } else if (Block.a(itemstack.getItem()) == Blocks.HAY_BLOCK) {
/*  565 */           f = 20.0F;
/*  566 */           short1 = 180;
/*  567 */         } else if (itemstack.getItem() == Items.APPLE) {
/*  568 */           f = 3.0F;
/*  569 */           short1 = 60;
/*  570 */           b1 = 3;
/*  571 */         } else if (itemstack.getItem() == Items.CARROT_GOLDEN) {
/*  572 */           f = 4.0F;
/*  573 */           short1 = 60;
/*  574 */           b1 = 5;
/*  575 */           if (isTame() && getAge() == 0) {
/*  576 */             flag = true;
/*  577 */             f(entityhuman);
/*      */           } 
/*  579 */         } else if (itemstack.getItem() == Items.GOLDEN_APPLE) {
/*  580 */           f = 10.0F;
/*  581 */           short1 = 240;
/*  582 */           b1 = 10;
/*  583 */           if (isTame() && getAge() == 0) {
/*  584 */             flag = true;
/*  585 */             f(entityhuman);
/*      */           } 
/*      */         } 
/*      */         
/*  589 */         if (getHealth() < getMaxHealth() && f > 0.0F) {
/*  590 */           heal(f, EntityRegainHealthEvent.RegainReason.EATING);
/*  591 */           flag = true;
/*      */         } 
/*      */         
/*  594 */         if (!cb() && short1 > 0) {
/*  595 */           a(short1);
/*  596 */           flag = true;
/*      */         } 
/*      */         
/*  599 */         if (b1 > 0 && (flag || !isTame()) && b1 < getMaxDomestication()) {
/*  600 */           flag = true;
/*  601 */           v(b1);
/*      */         } 
/*      */         
/*  604 */         if (flag) {
/*  605 */           cL();
/*      */         }
/*      */       } 
/*      */       
/*  609 */       if (!isTame() && !flag) {
/*  610 */         if (itemstack != null && itemstack.a(entityhuman, this)) {
/*  611 */           return true;
/*      */         }
/*      */         
/*  614 */         cJ();
/*  615 */         return true;
/*      */       } 
/*      */       
/*  618 */       if (!flag && cC() && !hasChest() && itemstack.getItem() == Item.getItemOf(Blocks.CHEST)) {
/*  619 */         setHasChest(true);
/*  620 */         makeSound("mob.chickenplop", 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
/*  621 */         flag = true;
/*  622 */         loadChest();
/*      */       } 
/*      */       
/*  625 */       if (!flag && cg() && !cu() && itemstack.getItem() == Items.SADDLE) {
/*  626 */         g(entityhuman);
/*  627 */         return true;
/*      */       } 
/*      */       
/*  630 */       if (flag) {
/*  631 */         if (!entityhuman.abilities.canInstantlyBuild && --itemstack.count == 0) {
/*  632 */           entityhuman.inventory.setItem(entityhuman.inventory.itemInHandIndex, (ItemStack)null);
/*      */         }
/*      */         
/*  635 */         return true;
/*      */       } 
/*      */     } 
/*      */     
/*  639 */     if (cg() && this.passenger == null) {
/*  640 */       if (itemstack != null && itemstack.a(entityhuman, this)) {
/*  641 */         return true;
/*      */       }
/*  643 */       i(entityhuman);
/*  644 */       return true;
/*      */     } 
/*      */     
/*  647 */     return super.a(entityhuman);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void i(EntityHuman entityhuman) {
/*  653 */     entityhuman.yaw = this.yaw;
/*  654 */     entityhuman.pitch = this.pitch;
/*  655 */     o(false);
/*  656 */     p(false);
/*  657 */     if (!this.world.isStatic) {
/*  658 */       entityhuman.mount(this);
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean cB() {
/*  663 */     return (getType() == 0);
/*      */   }
/*      */   
/*      */   public boolean cC() {
/*  667 */     int i = getType();
/*      */     
/*  669 */     return (i == 2 || i == 1);
/*      */   }
/*      */   
/*      */   protected boolean bh() {
/*  673 */     return (this.passenger != null && cu()) ? true : ((cm() || cn()));
/*      */   }
/*      */   
/*      */   public boolean cE() {
/*  677 */     int i = getType();
/*      */     
/*  679 */     return (i == 3 || i == 4);
/*      */   }
/*      */   
/*      */   public boolean cF() {
/*  683 */     return (cE() || getType() == 2);
/*      */   }
/*      */   
/*      */   public boolean c(ItemStack itemstack) {
/*  687 */     return false;
/*      */   }
/*      */   
/*      */   private void cR() {
/*  691 */     this.bp = 1;
/*      */   }
/*      */   
/*      */   public void die(DamageSource damagesource) {
/*  695 */     super.die(damagesource);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void dropDeathLoot(boolean flag, int i) {
/*  705 */     super.dropDeathLoot(flag, i);
/*      */ 
/*      */     
/*  708 */     if (!this.world.isStatic) {
/*  709 */       dropChest();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void e() {
/*  715 */     if (this.random.nextInt(200) == 0) {
/*  716 */       cR();
/*      */     }
/*      */     
/*  719 */     super.e();
/*  720 */     if (!this.world.isStatic) {
/*  721 */       if (this.random.nextInt(900) == 0 && this.deathTicks == 0) {
/*  722 */         heal(1.0F, EntityRegainHealthEvent.RegainReason.REGEN);
/*      */       }
/*      */       
/*  725 */       if (!cm() && this.passenger == null && this.random.nextInt(300) == 0 && this.world.getType(MathHelper.floor(this.locX), MathHelper.floor(this.locY) - 1, MathHelper.floor(this.locZ)) == Blocks.GRASS) {
/*  726 */         o(true);
/*      */       }
/*      */       
/*  729 */       if (cm() && ++this.bD > 50) {
/*  730 */         this.bD = 0;
/*  731 */         o(false);
/*      */       } 
/*      */       
/*  734 */       if (co() && !cb() && !cm()) {
/*  735 */         EntityHorse entityhorse = a(this, 16.0D);
/*      */         
/*  737 */         if (entityhorse != null && f(entityhorse) > 4.0D) {
/*  738 */           PathEntity pathentity = this.world.findPath(this, entityhorse, 16.0F, true, false, false, true);
/*      */           
/*  740 */           setPathEntity(pathentity);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void h() {
/*  747 */     super.h();
/*  748 */     if (this.world.isStatic && this.datawatcher.a()) {
/*  749 */       this.datawatcher.e();
/*  750 */       cP();
/*      */     } 
/*      */     
/*  753 */     if (this.bE > 0 && ++this.bE > 30) {
/*  754 */       this.bE = 0;
/*  755 */       b(128, false);
/*      */     } 
/*      */     
/*  758 */     if (!this.world.isStatic && this.bF > 0 && ++this.bF > 20) {
/*  759 */       this.bF = 0;
/*  760 */       p(false);
/*      */     } 
/*      */     
/*  763 */     if (this.bp > 0 && ++this.bp > 8) {
/*  764 */       this.bp = 0;
/*      */     }
/*      */     
/*  767 */     if (this.bq > 0) {
/*  768 */       this.bq++;
/*  769 */       if (this.bq > 300) {
/*  770 */         this.bq = 0;
/*      */       }
/*      */     } 
/*      */     
/*  774 */     this.bK = this.bJ;
/*  775 */     if (cm()) {
/*  776 */       this.bJ += (1.0F - this.bJ) * 0.4F + 0.05F;
/*  777 */       if (this.bJ > 1.0F) {
/*  778 */         this.bJ = 1.0F;
/*      */       }
/*      */     } else {
/*  781 */       this.bJ += (0.0F - this.bJ) * 0.4F - 0.05F;
/*  782 */       if (this.bJ < 0.0F) {
/*  783 */         this.bJ = 0.0F;
/*      */       }
/*      */     } 
/*      */     
/*  787 */     this.bM = this.bL;
/*  788 */     if (cn()) {
/*  789 */       this.bK = this.bJ = 0.0F;
/*  790 */       this.bL += (1.0F - this.bL) * 0.4F + 0.05F;
/*  791 */       if (this.bL > 1.0F) {
/*  792 */         this.bL = 1.0F;
/*      */       }
/*      */     } else {
/*  795 */       this.bI = false;
/*  796 */       this.bL += (0.8F * this.bL * this.bL * this.bL - this.bL) * 0.6F - 0.05F;
/*  797 */       if (this.bL < 0.0F) {
/*  798 */         this.bL = 0.0F;
/*      */       }
/*      */     } 
/*      */     
/*  802 */     this.bO = this.bN;
/*  803 */     if (x(128)) {
/*  804 */       this.bN += (1.0F - this.bN) * 0.7F + 0.05F;
/*  805 */       if (this.bN > 1.0F) {
/*  806 */         this.bN = 1.0F;
/*      */       }
/*      */     } else {
/*  809 */       this.bN += (0.0F - this.bN) * 0.7F - 0.05F;
/*  810 */       if (this.bN < 0.0F) {
/*  811 */         this.bN = 0.0F;
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void cS() {
/*  817 */     if (!this.world.isStatic) {
/*  818 */       this.bE = 1;
/*  819 */       b(128, true);
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean cT() {
/*  824 */     return (this.passenger == null && this.vehicle == null && isTame() && cb() && !cF() && getHealth() >= getMaxHealth());
/*      */   }
/*      */   
/*      */   public void e(boolean flag) {
/*  828 */     b(32, flag);
/*      */   }
/*      */   
/*      */   public void o(boolean flag) {
/*  832 */     e(flag);
/*      */   }
/*      */   
/*      */   public void p(boolean flag) {
/*  836 */     if (flag) {
/*  837 */       o(false);
/*      */     }
/*      */     
/*  840 */     b(64, flag);
/*      */   }
/*      */   
/*      */   private void cU() {
/*  844 */     if (!this.world.isStatic) {
/*  845 */       this.bF = 1;
/*  846 */       p(true);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void cJ() {
/*  851 */     cU();
/*  852 */     String s = cv();
/*      */     
/*  854 */     if (s != null) {
/*  855 */       makeSound(s, bf(), bg());
/*      */     }
/*      */   }
/*      */   
/*      */   public void dropChest() {
/*  860 */     a(this, this.inventoryChest);
/*  861 */     cs();
/*      */   }
/*      */   
/*      */   private void a(Entity entity, InventoryHorseChest inventoryhorsechest) {
/*  865 */     if (inventoryhorsechest != null && !this.world.isStatic) {
/*  866 */       for (int i = 0; i < inventoryhorsechest.getSize(); i++) {
/*  867 */         ItemStack itemstack = inventoryhorsechest.getItem(i);
/*      */         
/*  869 */         if (itemstack != null) {
/*  870 */           a(itemstack, 0.0F);
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean h(EntityHuman entityhuman) {
/*  877 */     setOwnerUUID(entityhuman.getUniqueID().toString());
/*  878 */     setTame(true);
/*  879 */     return true;
/*      */   }
/*      */   
/*      */   public void e(float f, float f1) {
/*  883 */     if (this.passenger != null && this.passenger instanceof EntityLiving && cu()) {
/*  884 */       this.lastYaw = this.yaw = this.passenger.yaw;
/*  885 */       this.pitch = this.passenger.pitch * 0.5F;
/*  886 */       b(this.yaw, this.pitch);
/*  887 */       this.aO = this.aM = this.yaw;
/*  888 */       f = ((EntityLiving)this.passenger).bd * 0.5F;
/*  889 */       f1 = ((EntityLiving)this.passenger).be;
/*  890 */       if (f1 <= 0.0F) {
/*  891 */         f1 *= 0.25F;
/*  892 */         this.bP = 0;
/*      */       } 
/*      */       
/*  895 */       if (this.onGround && this.bt == 0.0F && cn() && !this.bI) {
/*  896 */         f = 0.0F;
/*  897 */         f1 = 0.0F;
/*      */       } 
/*      */       
/*  900 */       if (this.bt > 0.0F && !cj() && this.onGround) {
/*  901 */         this.motY = getJumpStrength() * this.bt;
/*  902 */         if (hasEffect(MobEffectList.JUMP)) {
/*  903 */           this.motY += ((getEffect(MobEffectList.JUMP).getAmplifier() + 1) * 0.1F);
/*      */         }
/*      */         
/*  906 */         j(true);
/*  907 */         this.al = true;
/*  908 */         if (f1 > 0.0F) {
/*  909 */           float f2 = MathHelper.sin(this.yaw * 3.1415927F / 180.0F);
/*  910 */           float f3 = MathHelper.cos(this.yaw * 3.1415927F / 180.0F);
/*      */           
/*  912 */           this.motX += (-0.4F * f2 * this.bt);
/*  913 */           this.motZ += (0.4F * f3 * this.bt);
/*  914 */           makeSound("mob.horse.jump", 0.4F, 1.0F);
/*      */         } 
/*      */         
/*  917 */         this.bt = 0.0F;
/*      */       } 
/*      */       
/*  920 */       this.W = 1.0F;
/*  921 */       this.aQ = bl() * 0.1F;
/*  922 */       if (!this.world.isStatic) {
/*  923 */         i((float)getAttributeInstance(GenericAttributes.d).getValue());
/*  924 */         super.e(f, f1);
/*      */       } 
/*      */       
/*  927 */       if (this.onGround) {
/*  928 */         this.bt = 0.0F;
/*  929 */         j(false);
/*      */       } 
/*      */       
/*  932 */       this.aE = this.aF;
/*  933 */       double d0 = this.locX - this.lastX;
/*  934 */       double d1 = this.locZ - this.lastZ;
/*  935 */       float f4 = MathHelper.sqrt(d0 * d0 + d1 * d1) * 4.0F;
/*      */       
/*  937 */       if (f4 > 1.0F) {
/*  938 */         f4 = 1.0F;
/*      */       }
/*      */       
/*  941 */       this.aF += (f4 - this.aF) * 0.4F;
/*  942 */       this.aG += this.aF;
/*      */     } else {
/*  944 */       this.W = 0.5F;
/*  945 */       this.aQ = 0.02F;
/*  946 */       super.e(f, f1);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void b(NBTTagCompound nbttagcompound) {
/*  951 */     super.b(nbttagcompound);
/*  952 */     nbttagcompound.setBoolean("EatingHaystack", cm());
/*  953 */     nbttagcompound.setBoolean("ChestedHorse", hasChest());
/*  954 */     nbttagcompound.setBoolean("HasReproduced", cp());
/*  955 */     nbttagcompound.setBoolean("Bred", co());
/*  956 */     nbttagcompound.setInt("Type", getType());
/*  957 */     nbttagcompound.setInt("Variant", getVariant());
/*  958 */     nbttagcompound.setInt("Temper", getTemper());
/*  959 */     nbttagcompound.setBoolean("Tame", isTame());
/*  960 */     nbttagcompound.setString("OwnerUUID", getOwnerUUID());
/*  961 */     nbttagcompound.setInt("Bukkit.MaxDomestication", this.maxDomestication);
/*  962 */     if (hasChest()) {
/*  963 */       NBTTagList nbttaglist = new NBTTagList();
/*      */       
/*  965 */       for (int i = 2; i < this.inventoryChest.getSize(); i++) {
/*  966 */         ItemStack itemstack = this.inventoryChest.getItem(i);
/*      */         
/*  968 */         if (itemstack != null) {
/*  969 */           NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*      */           
/*  971 */           nbttagcompound1.setByte("Slot", (byte)i);
/*  972 */           itemstack.save(nbttagcompound1);
/*  973 */           nbttaglist.add(nbttagcompound1);
/*      */         } 
/*      */       } 
/*      */       
/*  977 */       nbttagcompound.set("Items", nbttaglist);
/*      */     } 
/*      */     
/*  980 */     if (this.inventoryChest.getItem(1) != null) {
/*  981 */       nbttagcompound.set("ArmorItem", this.inventoryChest.getItem(1).save(new NBTTagCompound()));
/*      */     }
/*      */     
/*  984 */     if (this.inventoryChest.getItem(0) != null) {
/*  985 */       nbttagcompound.set("SaddleItem", this.inventoryChest.getItem(0).save(new NBTTagCompound()));
/*      */     }
/*      */   }
/*      */   
/*      */   public void a(NBTTagCompound nbttagcompound) {
/*  990 */     super.a(nbttagcompound);
/*  991 */     o(nbttagcompound.getBoolean("EatingHaystack"));
/*  992 */     k(nbttagcompound.getBoolean("Bred"));
/*  993 */     setHasChest(nbttagcompound.getBoolean("ChestedHorse"));
/*  994 */     m(nbttagcompound.getBoolean("HasReproduced"));
/*  995 */     setType(nbttagcompound.getInt("Type"));
/*  996 */     setVariant(nbttagcompound.getInt("Variant"));
/*  997 */     setTemper(nbttagcompound.getInt("Temper"));
/*  998 */     setTame(nbttagcompound.getBoolean("Tame"));
/*  999 */     if (nbttagcompound.hasKeyOfType("OwnerUUID", 8)) {
/* 1000 */       setOwnerUUID(nbttagcompound.getString("OwnerUUID"));
/*      */     
/*      */     }
/* 1003 */     else if (nbttagcompound.hasKey("OwnerName")) {
/* 1004 */       String owner = nbttagcompound.getString("OwnerName");
/* 1005 */       if (owner != null && !owner.isEmpty()) {
/* 1006 */         setOwnerUUID(NameReferencingFileConverter.a(owner));
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1011 */     if (nbttagcompound.hasKey("Bukkit.MaxDomestication")) {
/* 1012 */       this.maxDomestication = nbttagcompound.getInt("Bukkit.MaxDomestication");
/*      */     }
/*      */     
/* 1015 */     AttributeInstance attributeinstance = getAttributeMap().a("Speed");
/*      */     
/* 1017 */     if (attributeinstance != null) {
/* 1018 */       getAttributeInstance(GenericAttributes.d).setValue(attributeinstance.b() * 0.25D);
/*      */     }
/*      */     
/* 1021 */     if (hasChest()) {
/* 1022 */       NBTTagList nbttaglist = nbttagcompound.getList("Items", 10);
/*      */       
/* 1024 */       loadChest();
/*      */       
/* 1026 */       for (int i = 0; i < nbttaglist.size(); i++) {
/* 1027 */         NBTTagCompound nbttagcompound1 = nbttaglist.get(i);
/* 1028 */         int j = nbttagcompound1.getByte("Slot") & 0xFF;
/*      */         
/* 1030 */         if (j >= 2 && j < this.inventoryChest.getSize()) {
/* 1031 */           this.inventoryChest.setItem(j, ItemStack.createStack(nbttagcompound1));
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1038 */     if (nbttagcompound.hasKeyOfType("ArmorItem", 10)) {
/* 1039 */       ItemStack itemstack = ItemStack.createStack(nbttagcompound.getCompound("ArmorItem"));
/* 1040 */       if (itemstack != null && a(itemstack.getItem())) {
/* 1041 */         this.inventoryChest.setItem(1, itemstack);
/*      */       }
/*      */     } 
/*      */     
/* 1045 */     if (nbttagcompound.hasKeyOfType("SaddleItem", 10)) {
/* 1046 */       ItemStack itemstack = ItemStack.createStack(nbttagcompound.getCompound("SaddleItem"));
/* 1047 */       if (itemstack != null && itemstack.getItem() == Items.SADDLE) {
/* 1048 */         this.inventoryChest.setItem(0, itemstack);
/*      */       }
/* 1050 */     } else if (nbttagcompound.getBoolean("Saddle")) {
/* 1051 */       this.inventoryChest.setItem(0, new ItemStack(Items.SADDLE));
/*      */     } 
/*      */     
/* 1054 */     cO();
/*      */   }
/*      */   
/*      */   public boolean mate(EntityAnimal entityanimal) {
/* 1058 */     if (entityanimal == this)
/* 1059 */       return false; 
/* 1060 */     if (entityanimal.getClass() != getClass()) {
/* 1061 */       return false;
/*      */     }
/* 1063 */     EntityHorse entityhorse = (EntityHorse)entityanimal;
/*      */     
/* 1065 */     if (cT() && entityhorse.cT()) {
/* 1066 */       int i = getType();
/* 1067 */       int j = entityhorse.getType();
/*      */       
/* 1069 */       return (i == j || (i == 0 && j == 1) || (i == 1 && j == 0));
/*      */     } 
/* 1071 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public EntityAgeable createChild(EntityAgeable entityageable) {
/* 1077 */     EntityHorse entityhorse = (EntityHorse)entityageable;
/* 1078 */     EntityHorse entityhorse1 = new EntityHorse(this.world);
/* 1079 */     int i = getType();
/* 1080 */     int j = entityhorse.getType();
/* 1081 */     int k = 0;
/*      */     
/* 1083 */     if (i == j) {
/* 1084 */       k = i;
/* 1085 */     } else if ((i == 0 && j == 1) || (i == 1 && j == 0)) {
/* 1086 */       k = 2;
/*      */     } 
/*      */     
/* 1089 */     if (k == 0) {
/* 1090 */       int i1, l = this.random.nextInt(9);
/*      */ 
/*      */       
/* 1093 */       if (l < 4) {
/* 1094 */         i1 = getVariant() & 0xFF;
/* 1095 */       } else if (l < 8) {
/* 1096 */         i1 = entityhorse.getVariant() & 0xFF;
/*      */       } else {
/* 1098 */         i1 = this.random.nextInt(7);
/*      */       } 
/*      */       
/* 1101 */       int j1 = this.random.nextInt(5);
/*      */       
/* 1103 */       if (j1 < 2) {
/* 1104 */         i1 |= getVariant() & 0xFF00;
/* 1105 */       } else if (j1 < 4) {
/* 1106 */         i1 |= entityhorse.getVariant() & 0xFF00;
/*      */       } else {
/* 1108 */         i1 |= this.random.nextInt(5) << 8 & 0xFF00;
/*      */       } 
/*      */       
/* 1111 */       entityhorse1.setVariant(i1);
/*      */     } 
/*      */     
/* 1114 */     entityhorse1.setType(k);
/* 1115 */     double d0 = getAttributeInstance(GenericAttributes.maxHealth).b() + entityageable.getAttributeInstance(GenericAttributes.maxHealth).b() + cV();
/*      */     
/* 1117 */     entityhorse1.getAttributeInstance(GenericAttributes.maxHealth).setValue(d0 / 3.0D);
/* 1118 */     double d1 = getAttributeInstance(attributeJumpStrength).b() + entityageable.getAttributeInstance(attributeJumpStrength).b() + cW();
/*      */     
/* 1120 */     entityhorse1.getAttributeInstance(attributeJumpStrength).setValue(d1 / 3.0D);
/* 1121 */     double d2 = getAttributeInstance(GenericAttributes.d).b() + entityageable.getAttributeInstance(GenericAttributes.d).b() + cX();
/*      */     
/* 1123 */     entityhorse1.getAttributeInstance(GenericAttributes.d).setValue(d2 / 3.0D);
/* 1124 */     return entityhorse1;
/*      */   }
/*      */   public GroupDataEntity prepare(GroupDataEntity groupdataentity) {
/*      */     int j;
/* 1128 */     Object object = super.prepare(groupdataentity);
/* 1129 */     boolean flag = false;
/* 1130 */     int i = 0;
/*      */ 
/*      */     
/* 1133 */     if (object instanceof GroupDataHorse) {
/* 1134 */       j = ((GroupDataHorse)object).a;
/* 1135 */       i = ((GroupDataHorse)object).b & 0xFF | this.random.nextInt(5) << 8;
/*      */     } else {
/* 1137 */       if (this.random.nextInt(10) == 0) {
/* 1138 */         j = 1;
/*      */       } else {
/* 1140 */         int k = this.random.nextInt(7);
/* 1141 */         int l = this.random.nextInt(5);
/*      */         
/* 1143 */         j = 0;
/* 1144 */         i = k | l << 8;
/*      */       } 
/*      */       
/* 1147 */       object = new GroupDataHorse(j, i);
/*      */     } 
/*      */     
/* 1150 */     setType(j);
/* 1151 */     setVariant(i);
/* 1152 */     if (this.random.nextInt(5) == 0) {
/* 1153 */       setAge(-24000);
/*      */     }
/*      */     
/* 1156 */     if (j != 4 && j != 3) {
/* 1157 */       getAttributeInstance(GenericAttributes.maxHealth).setValue(cV());
/* 1158 */       if (j == 0) {
/* 1159 */         getAttributeInstance(GenericAttributes.d).setValue(cX());
/*      */       } else {
/* 1161 */         getAttributeInstance(GenericAttributes.d).setValue(0.17499999701976776D);
/*      */       } 
/*      */     } else {
/* 1164 */       getAttributeInstance(GenericAttributes.maxHealth).setValue(15.0D);
/* 1165 */       getAttributeInstance(GenericAttributes.d).setValue(0.20000000298023224D);
/*      */     } 
/*      */     
/* 1168 */     if (j != 2 && j != 1) {
/* 1169 */       getAttributeInstance(attributeJumpStrength).setValue(cW());
/*      */     } else {
/* 1171 */       getAttributeInstance(attributeJumpStrength).setValue(0.5D);
/*      */     } 
/*      */     
/* 1174 */     setHealth(getMaxHealth());
/* 1175 */     return (GroupDataEntity)object;
/*      */   }
/*      */   
/*      */   protected boolean bk() {
/* 1179 */     return true;
/*      */   }
/*      */   
/*      */   public void w(int i) {
/* 1183 */     if (cu()) {
/*      */       float power;
/* 1185 */       if (i < 0) {
/* 1186 */         i = 0;
/*      */       }
/*      */ 
/*      */       
/* 1190 */       if (i >= 90) {
/* 1191 */         power = 1.0F;
/*      */       } else {
/* 1193 */         power = 0.4F + 0.4F * i / 90.0F;
/*      */       } 
/*      */       
/* 1196 */       HorseJumpEvent event = CraftEventFactory.callHorseJumpEvent(this, power);
/* 1197 */       if (!event.isCancelled()) {
/* 1198 */         this.bI = true;
/* 1199 */         cU();
/* 1200 */         this.bt = event.getPower();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void ac() {
/* 1207 */     super.ac();
/* 1208 */     if (this.bM > 0.0F) {
/* 1209 */       float f = MathHelper.sin(this.aM * 3.1415927F / 180.0F);
/* 1210 */       float f1 = MathHelper.cos(this.aM * 3.1415927F / 180.0F);
/* 1211 */       float f2 = 0.7F * this.bM;
/* 1212 */       float f3 = 0.15F * this.bM;
/*      */       
/* 1214 */       this.passenger.setPosition(this.locX + (f2 * f), this.locY + ad() + this.passenger.ad() + f3, this.locZ - (f2 * f1));
/* 1215 */       if (this.passenger instanceof EntityLiving) {
/* 1216 */         ((EntityLiving)this.passenger).aM = this.aM;
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private float cV() {
/* 1222 */     return 15.0F + this.random.nextInt(8) + this.random.nextInt(9);
/*      */   }
/*      */   
/*      */   private double cW() {
/* 1226 */     return 0.4000000059604645D + this.random.nextDouble() * 0.2D + this.random.nextDouble() * 0.2D + this.random.nextDouble() * 0.2D;
/*      */   }
/*      */   
/*      */   private double cX() {
/* 1230 */     return (0.44999998807907104D + this.random.nextDouble() * 0.3D + this.random.nextDouble() * 0.3D + this.random.nextDouble() * 0.3D) * 0.25D;
/*      */   }
/*      */   
/*      */   public static boolean a(Item item) {
/* 1234 */     return (item == Items.HORSE_ARMOR_IRON || item == Items.HORSE_ARMOR_GOLD || item == Items.HORSE_ARMOR_DIAMOND);
/*      */   }
/*      */   
/*      */   public boolean h_() {
/* 1238 */     return false;
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityHorse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
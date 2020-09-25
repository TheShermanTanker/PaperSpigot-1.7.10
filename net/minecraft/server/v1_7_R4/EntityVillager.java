/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ public class EntityVillager
/*     */   extends EntityAgeable
/*     */   implements IMerchant, NPC
/*     */ {
/*     */   private int profession;
/*     */   private boolean br;
/*     */   private boolean bs;
/*     */   Village village;
/*     */   private EntityHuman tradingPlayer;
/*     */   private MerchantRecipeList bu;
/*     */   private int bv;
/*     */   private boolean bw;
/*     */   private int riches;
/*     */   private String by;
/*     */   private boolean bz;
/*     */   private float bA;
/*     */   
/*     */   public EntityVillager(World paramWorld) {
/*  50 */     this(paramWorld, 0);
/*     */   }
/*     */   
/*     */   public EntityVillager(World paramWorld, int paramInt) {
/*  54 */     super(paramWorld);
/*  55 */     setProfession(paramInt);
/*  56 */     a(0.6F, 1.8F);
/*     */     
/*  58 */     getNavigation().b(true);
/*  59 */     getNavigation().a(true);
/*     */     
/*  61 */     this.goalSelector.a(0, new PathfinderGoalFloat(this));
/*  62 */     this.goalSelector.a(1, new PathfinderGoalAvoidPlayer(this, EntityZombie.class, 8.0F, 0.6D, 0.6D));
/*  63 */     this.goalSelector.a(1, new PathfinderGoalTradeWithPlayer(this));
/*  64 */     this.goalSelector.a(1, new PathfinderGoalLookAtTradingPlayer(this));
/*  65 */     this.goalSelector.a(2, new PathfinderGoalMoveIndoors(this));
/*  66 */     this.goalSelector.a(3, new PathfinderGoalRestrictOpenDoor(this));
/*  67 */     this.goalSelector.a(4, new PathfinderGoalOpenDoor(this, true));
/*  68 */     this.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, 0.6D));
/*  69 */     this.goalSelector.a(6, new PathfinderGoalMakeLove(this));
/*  70 */     this.goalSelector.a(7, new PathfinderGoalTakeFlower(this));
/*  71 */     this.goalSelector.a(8, new PathfinderGoalPlay(this, 0.32D));
/*  72 */     this.goalSelector.a(9, new PathfinderGoalInteract(this, EntityHuman.class, 3.0F, 1.0F));
/*  73 */     this.goalSelector.a(9, new PathfinderGoalInteract(this, EntityVillager.class, 5.0F, 0.02F));
/*  74 */     this.goalSelector.a(9, new PathfinderGoalRandomStroll(this, 0.6D));
/*  75 */     this.goalSelector.a(10, new PathfinderGoalLookAtPlayer(this, EntityInsentient.class, 8.0F));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void aD() {
/*  80 */     super.aD();
/*     */     
/*  82 */     getAttributeInstance(GenericAttributes.d).setValue(0.5D);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean bk() {
/*  87 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void bp() {
/*  92 */     if (--this.profession <= 0) {
/*  93 */       this.world.villages.a(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ));
/*  94 */       this.profession = 70 + this.random.nextInt(50);
/*     */       
/*  96 */       this.village = this.world.villages.getClosestVillage(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ), 32);
/*  97 */       if (this.village == null) { bX(); }
/*     */       else
/*  99 */       { ChunkCoordinates chunkCoordinates = this.village.getCenter();
/* 100 */         a(chunkCoordinates.x, chunkCoordinates.y, chunkCoordinates.z, (int)(this.village.getSize() * 0.6F));
/*     */         
/* 102 */         if (this.bz) {
/* 103 */           this.bz = false;
/* 104 */           this.village.b(5);
/*     */         }  }
/*     */     
/*     */     } 
/* 108 */     if (!cc() && this.bv > 0) {
/* 109 */       this.bv--;
/* 110 */       if (this.bv <= 0) {
/* 111 */         if (this.bw) {
/*     */ 
/*     */           
/* 114 */           if (this.bu.size() > 1) {
/* 115 */             for (MerchantRecipe merchantRecipe : this.bu) {
/* 116 */               if (merchantRecipe.g()) {
/* 117 */                 merchantRecipe.a(this.random.nextInt(6) + this.random.nextInt(6) + 2);
/*     */               }
/*     */             } 
/*     */           }
/*     */           
/* 122 */           t(1);
/* 123 */           this.bw = false;
/*     */           
/* 125 */           if (this.village != null && this.by != null) {
/* 126 */             this.world.broadcastEntityEffect(this, (byte)14);
/* 127 */             this.village.a(this.by, 1);
/*     */           } 
/*     */         } 
/* 130 */         addEffect(new MobEffect(MobEffectList.REGENERATION.id, 200, 0));
/*     */       } 
/*     */     } 
/*     */     
/* 134 */     super.bp();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean a(EntityHuman paramEntityHuman) {
/* 140 */     ItemStack itemStack = paramEntityHuman.inventory.getItemInHand();
/* 141 */     boolean bool = (itemStack != null && itemStack.getItem() == Items.MONSTER_EGG) ? true : false;
/*     */     
/* 143 */     if (!bool && isAlive() && !cc() && !isBaby()) {
/* 144 */       if (!this.world.isStatic) {
/*     */         
/* 146 */         a_(paramEntityHuman);
/* 147 */         paramEntityHuman.openTrade(this, getCustomName());
/*     */       } 
/* 149 */       return true;
/*     */     } 
/* 151 */     return super.a(paramEntityHuman);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void c() {
/* 156 */     super.c();
/* 157 */     this.datawatcher.a(16, Integer.valueOf(0));
/*     */   }
/*     */ 
/*     */   
/*     */   public void b(NBTTagCompound paramNBTTagCompound) {
/* 162 */     super.b(paramNBTTagCompound);
/* 163 */     paramNBTTagCompound.setInt("Profession", getProfession());
/* 164 */     paramNBTTagCompound.setInt("Riches", this.riches);
/* 165 */     if (this.bu != null) {
/* 166 */       paramNBTTagCompound.set("Offers", this.bu.a());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(NBTTagCompound paramNBTTagCompound) {
/* 172 */     super.a(paramNBTTagCompound);
/* 173 */     setProfession(paramNBTTagCompound.getInt("Profession"));
/* 174 */     this.riches = paramNBTTagCompound.getInt("Riches");
/* 175 */     if (paramNBTTagCompound.hasKeyOfType("Offers", 10)) {
/* 176 */       NBTTagCompound nBTTagCompound = paramNBTTagCompound.getCompound("Offers");
/* 177 */       this.bu = new MerchantRecipeList(nBTTagCompound);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isTypeNotPersistent() {
/* 183 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected String t() {
/* 188 */     if (cc()) {
/* 189 */       return "mob.villager.haggle";
/*     */     }
/* 191 */     return "mob.villager.idle";
/*     */   }
/*     */ 
/*     */   
/*     */   protected String aT() {
/* 196 */     return "mob.villager.hit";
/*     */   }
/*     */ 
/*     */   
/*     */   protected String aU() {
/* 201 */     return "mob.villager.death";
/*     */   }
/*     */   
/*     */   public void setProfession(int paramInt) {
/* 205 */     this.datawatcher.watch(16, Integer.valueOf(paramInt));
/*     */   }
/*     */   
/*     */   public int getProfession() {
/* 209 */     return this.datawatcher.getInt(16);
/*     */   }
/*     */   
/*     */   public boolean ca() {
/* 213 */     return this.br;
/*     */   }
/*     */   
/*     */   public void i(boolean paramBoolean) {
/* 217 */     this.br = paramBoolean;
/*     */   }
/*     */   
/*     */   public void j(boolean paramBoolean) {
/* 221 */     this.bs = paramBoolean;
/*     */   }
/*     */   
/*     */   public boolean cb() {
/* 225 */     return this.bs;
/*     */   }
/*     */ 
/*     */   
/*     */   public void b(EntityLiving paramEntityLiving) {
/* 230 */     super.b(paramEntityLiving);
/* 231 */     if (this.village != null && paramEntityLiving != null) {
/* 232 */       this.village.a(paramEntityLiving);
/*     */       
/* 234 */       if (paramEntityLiving instanceof EntityHuman) {
/* 235 */         byte b = -1;
/* 236 */         if (isBaby()) {
/* 237 */           b = -3;
/*     */         }
/* 239 */         this.village.a(paramEntityLiving.getName(), b);
/* 240 */         if (isAlive()) {
/* 241 */           this.world.broadcastEntityEffect(this, (byte)13);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void die(DamageSource paramDamageSource) {
/* 250 */     if (this.village != null) {
/* 251 */       Entity entity = paramDamageSource.getEntity();
/* 252 */       if (entity != null) {
/* 253 */         if (entity instanceof EntityHuman) {
/* 254 */           this.village.a(entity.getName(), -2);
/* 255 */         } else if (entity instanceof IMonster) {
/* 256 */           this.village.h();
/*     */         } 
/* 258 */       } else if (entity == null) {
/*     */ 
/*     */         
/* 261 */         EntityHuman entityHuman = this.world.findNearbyPlayer(this, 16.0D);
/* 262 */         if (entityHuman != null) {
/* 263 */           this.village.h();
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 268 */     super.die(paramDamageSource);
/*     */   }
/*     */ 
/*     */   
/*     */   public void a_(EntityHuman paramEntityHuman) {
/* 273 */     this.tradingPlayer = paramEntityHuman;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityHuman b() {
/* 278 */     return this.tradingPlayer;
/*     */   }
/*     */   
/*     */   public boolean cc() {
/* 282 */     return (this.tradingPlayer != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(MerchantRecipe paramMerchantRecipe) {
/* 287 */     paramMerchantRecipe.f();
/* 288 */     this.a_ = -q();
/* 289 */     makeSound("mob.villager.yes", bf(), bg());
/*     */ 
/*     */     
/* 292 */     if (paramMerchantRecipe.a((MerchantRecipe)this.bu.get(this.bu.size() - 1))) {
/* 293 */       this.bv = 40;
/* 294 */       this.bw = true;
/* 295 */       if (this.tradingPlayer != null) {
/* 296 */         this.by = this.tradingPlayer.getName();
/*     */       } else {
/* 298 */         this.by = null;
/*     */       } 
/*     */     } 
/* 301 */     if (paramMerchantRecipe.getBuyItem1().getItem() == Items.EMERALD) {
/* 302 */       this.riches += (paramMerchantRecipe.getBuyItem1()).count;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void a_(ItemStack paramItemStack) {
/* 308 */     if (!this.world.isStatic && this.a_ > -q() + 20) {
/* 309 */       this.a_ = -q();
/* 310 */       if (paramItemStack != null) {
/* 311 */         makeSound("mob.villager.yes", bf(), bg());
/*     */       } else {
/* 313 */         makeSound("mob.villager.no", bf(), bg());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public MerchantRecipeList getOffers(EntityHuman paramEntityHuman) {
/* 320 */     if (this.bu == null) {
/* 321 */       t(1);
/*     */     }
/* 323 */     return this.bu;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private float p(float paramFloat) {
/* 329 */     float f = paramFloat + this.bA;
/* 330 */     if (f > 0.9F) {
/* 331 */       return 0.9F - f - 0.9F;
/*     */     }
/* 333 */     return f;
/*     */   }
/*     */   
/*     */   private void t(int paramInt) {
/*     */     Item[] arrayOfItem;
/* 338 */     if (this.bu != null) {
/* 339 */       this.bA = MathHelper.c(this.bu.size()) * 0.2F;
/*     */     } else {
/* 341 */       this.bA = 0.0F;
/*     */     } 
/*     */     
/* 344 */     MerchantRecipeList merchantRecipeList = new MerchantRecipeList();
/* 345 */     switch (getProfession()) {
/*     */       case 0:
/* 347 */         a(merchantRecipeList, Items.WHEAT, this.random, p(0.9F));
/* 348 */         a(merchantRecipeList, Item.getItemOf(Blocks.WOOL), this.random, p(0.5F));
/* 349 */         a(merchantRecipeList, Items.RAW_CHICKEN, this.random, p(0.5F));
/* 350 */         a(merchantRecipeList, Items.COOKED_FISH, this.random, p(0.4F));
/* 351 */         b(merchantRecipeList, Items.BREAD, this.random, p(0.9F));
/* 352 */         b(merchantRecipeList, Items.MELON, this.random, p(0.3F));
/* 353 */         b(merchantRecipeList, Items.APPLE, this.random, p(0.3F));
/* 354 */         b(merchantRecipeList, Items.COOKIE, this.random, p(0.3F));
/* 355 */         b(merchantRecipeList, Items.SHEARS, this.random, p(0.3F));
/* 356 */         b(merchantRecipeList, Items.FLINT_AND_STEEL, this.random, p(0.3F));
/* 357 */         b(merchantRecipeList, Items.COOKED_CHICKEN, this.random, p(0.3F));
/* 358 */         b(merchantRecipeList, Items.ARROW, this.random, p(0.5F));
/* 359 */         if (this.random.nextFloat() < p(0.5F)) {
/* 360 */           merchantRecipeList.add((E)new MerchantRecipe(new ItemStack(Blocks.GRAVEL, 10), new ItemStack(Items.EMERALD), new ItemStack(Items.FLINT, 4 + this.random.nextInt(2), 0)));
/*     */         }
/*     */         break;
/*     */       case 4:
/* 364 */         a(merchantRecipeList, Items.COAL, this.random, p(0.7F));
/* 365 */         a(merchantRecipeList, Items.PORK, this.random, p(0.5F));
/* 366 */         a(merchantRecipeList, Items.RAW_BEEF, this.random, p(0.5F));
/* 367 */         b(merchantRecipeList, Items.SADDLE, this.random, p(0.1F));
/* 368 */         b(merchantRecipeList, Items.LEATHER_CHESTPLATE, this.random, p(0.3F));
/* 369 */         b(merchantRecipeList, Items.LEATHER_BOOTS, this.random, p(0.3F));
/* 370 */         b(merchantRecipeList, Items.LEATHER_HELMET, this.random, p(0.3F));
/* 371 */         b(merchantRecipeList, Items.LEATHER_LEGGINGS, this.random, p(0.3F));
/* 372 */         b(merchantRecipeList, Items.GRILLED_PORK, this.random, p(0.3F));
/* 373 */         b(merchantRecipeList, Items.COOKED_BEEF, this.random, p(0.3F));
/*     */         break;
/*     */       case 3:
/* 376 */         a(merchantRecipeList, Items.COAL, this.random, p(0.7F));
/* 377 */         a(merchantRecipeList, Items.IRON_INGOT, this.random, p(0.5F));
/* 378 */         a(merchantRecipeList, Items.GOLD_INGOT, this.random, p(0.5F));
/* 379 */         a(merchantRecipeList, Items.DIAMOND, this.random, p(0.5F));
/*     */         
/* 381 */         b(merchantRecipeList, Items.IRON_SWORD, this.random, p(0.5F));
/* 382 */         b(merchantRecipeList, Items.DIAMOND_SWORD, this.random, p(0.5F));
/* 383 */         b(merchantRecipeList, Items.IRON_AXE, this.random, p(0.3F));
/* 384 */         b(merchantRecipeList, Items.DIAMOND_AXE, this.random, p(0.3F));
/* 385 */         b(merchantRecipeList, Items.IRON_PICKAXE, this.random, p(0.5F));
/* 386 */         b(merchantRecipeList, Items.DIAMOND_PICKAXE, this.random, p(0.5F));
/* 387 */         b(merchantRecipeList, Items.IRON_SPADE, this.random, p(0.2F));
/* 388 */         b(merchantRecipeList, Items.DIAMOND_SPADE, this.random, p(0.2F));
/* 389 */         b(merchantRecipeList, Items.IRON_HOE, this.random, p(0.2F));
/* 390 */         b(merchantRecipeList, Items.DIAMOND_HOE, this.random, p(0.2F));
/* 391 */         b(merchantRecipeList, Items.IRON_BOOTS, this.random, p(0.2F));
/* 392 */         b(merchantRecipeList, Items.DIAMOND_BOOTS, this.random, p(0.2F));
/* 393 */         b(merchantRecipeList, Items.IRON_HELMET, this.random, p(0.2F));
/* 394 */         b(merchantRecipeList, Items.DIAMOND_HELMET, this.random, p(0.2F));
/* 395 */         b(merchantRecipeList, Items.IRON_CHESTPLATE, this.random, p(0.2F));
/* 396 */         b(merchantRecipeList, Items.DIAMOND_CHESTPLATE, this.random, p(0.2F));
/* 397 */         b(merchantRecipeList, Items.IRON_LEGGINGS, this.random, p(0.2F));
/* 398 */         b(merchantRecipeList, Items.DIAMOND_LEGGINGS, this.random, p(0.2F));
/* 399 */         b(merchantRecipeList, Items.CHAINMAIL_BOOTS, this.random, p(0.1F));
/* 400 */         b(merchantRecipeList, Items.CHAINMAIL_HELMET, this.random, p(0.1F));
/* 401 */         b(merchantRecipeList, Items.CHAINMAIL_CHESTPLATE, this.random, p(0.1F));
/* 402 */         b(merchantRecipeList, Items.CHAINMAIL_LEGGINGS, this.random, p(0.1F));
/*     */         break;
/*     */       case 1:
/* 405 */         a(merchantRecipeList, Items.PAPER, this.random, p(0.8F));
/* 406 */         a(merchantRecipeList, Items.BOOK, this.random, p(0.8F));
/* 407 */         a(merchantRecipeList, Items.WRITTEN_BOOK, this.random, p(0.3F));
/* 408 */         b(merchantRecipeList, Item.getItemOf(Blocks.BOOKSHELF), this.random, p(0.8F));
/* 409 */         b(merchantRecipeList, Item.getItemOf(Blocks.GLASS), this.random, p(0.2F));
/* 410 */         b(merchantRecipeList, Items.COMPASS, this.random, p(0.2F));
/* 411 */         b(merchantRecipeList, Items.WATCH, this.random, p(0.2F));
/*     */         
/* 413 */         if (this.random.nextFloat() < p(0.07F)) {
/* 414 */           Enchantment enchantment = Enchantment.c[this.random.nextInt(Enchantment.c.length)];
/* 415 */           int i = MathHelper.nextInt(this.random, enchantment.getStartLevel(), enchantment.getMaxLevel());
/* 416 */           ItemStack itemStack = Items.ENCHANTED_BOOK.a(new EnchantmentInstance(enchantment, i));
/* 417 */           int j = 2 + this.random.nextInt(5 + i * 10) + 3 * i;
/*     */           
/* 419 */           merchantRecipeList.add((E)new MerchantRecipe(new ItemStack(Items.BOOK), new ItemStack(Items.EMERALD, j), itemStack));
/*     */         } 
/*     */         break;
/*     */       case 2:
/* 423 */         b(merchantRecipeList, Items.EYE_OF_ENDER, this.random, p(0.3F));
/* 424 */         b(merchantRecipeList, Items.EXP_BOTTLE, this.random, p(0.2F));
/* 425 */         b(merchantRecipeList, Items.REDSTONE, this.random, p(0.4F));
/* 426 */         b(merchantRecipeList, Item.getItemOf(Blocks.GLOWSTONE), this.random, p(0.3F));
/*     */         
/* 428 */         arrayOfItem = new Item[] { Items.IRON_SWORD, Items.DIAMOND_SWORD, Items.IRON_CHESTPLATE, Items.DIAMOND_CHESTPLATE, Items.IRON_AXE, Items.DIAMOND_AXE, Items.IRON_PICKAXE, Items.DIAMOND_PICKAXE };
/*     */         
/* 430 */         for (Item item : arrayOfItem) {
/* 431 */           if (this.random.nextFloat() < p(0.05F)) {
/* 432 */             merchantRecipeList.add((E)new MerchantRecipe(new ItemStack(item, 1, 0), new ItemStack(Items.EMERALD, 2 + this.random.nextInt(3), 0), EnchantmentManager.a(this.random, new ItemStack(item, 1, 0), 5 + this.random.nextInt(15))));
/*     */           }
/*     */         } 
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 439 */     if (merchantRecipeList.isEmpty()) {
/* 440 */       a(merchantRecipeList, Items.GOLD_INGOT, this.random, 1.0F);
/*     */     }
/*     */ 
/*     */     
/* 444 */     Collections.shuffle(merchantRecipeList);
/*     */     
/* 446 */     if (this.bu == null) {
/* 447 */       this.bu = new MerchantRecipeList();
/*     */     }
/* 449 */     for (byte b = 0; b < paramInt && b < merchantRecipeList.size(); b++) {
/* 450 */       this.bu.a((MerchantRecipe)merchantRecipeList.get(b));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 458 */   private static final Map bB = new HashMap<Object, Object>();
/* 459 */   private static final Map bC = new HashMap<Object, Object>();
/*     */   static {
/* 461 */     bB.put(Items.COAL, new Tuple(Integer.valueOf(16), Integer.valueOf(24)));
/* 462 */     bB.put(Items.IRON_INGOT, new Tuple(Integer.valueOf(8), Integer.valueOf(10)));
/* 463 */     bB.put(Items.GOLD_INGOT, new Tuple(Integer.valueOf(8), Integer.valueOf(10)));
/* 464 */     bB.put(Items.DIAMOND, new Tuple(Integer.valueOf(4), Integer.valueOf(6)));
/* 465 */     bB.put(Items.PAPER, new Tuple(Integer.valueOf(24), Integer.valueOf(36)));
/* 466 */     bB.put(Items.BOOK, new Tuple(Integer.valueOf(11), Integer.valueOf(13)));
/* 467 */     bB.put(Items.WRITTEN_BOOK, new Tuple(Integer.valueOf(1), Integer.valueOf(1)));
/* 468 */     bB.put(Items.ENDER_PEARL, new Tuple(Integer.valueOf(3), Integer.valueOf(4)));
/* 469 */     bB.put(Items.EYE_OF_ENDER, new Tuple(Integer.valueOf(2), Integer.valueOf(3)));
/* 470 */     bB.put(Items.PORK, new Tuple(Integer.valueOf(14), Integer.valueOf(18)));
/* 471 */     bB.put(Items.RAW_BEEF, new Tuple(Integer.valueOf(14), Integer.valueOf(18)));
/* 472 */     bB.put(Items.RAW_CHICKEN, new Tuple(Integer.valueOf(14), Integer.valueOf(18)));
/* 473 */     bB.put(Items.COOKED_FISH, new Tuple(Integer.valueOf(9), Integer.valueOf(13)));
/* 474 */     bB.put(Items.SEEDS, new Tuple(Integer.valueOf(34), Integer.valueOf(48)));
/* 475 */     bB.put(Items.MELON_SEEDS, new Tuple(Integer.valueOf(30), Integer.valueOf(38)));
/* 476 */     bB.put(Items.PUMPKIN_SEEDS, new Tuple(Integer.valueOf(30), Integer.valueOf(38)));
/* 477 */     bB.put(Items.WHEAT, new Tuple(Integer.valueOf(18), Integer.valueOf(22)));
/* 478 */     bB.put(Item.getItemOf(Blocks.WOOL), new Tuple(Integer.valueOf(14), Integer.valueOf(22)));
/* 479 */     bB.put(Items.ROTTEN_FLESH, new Tuple(Integer.valueOf(36), Integer.valueOf(64)));
/*     */     
/* 481 */     bC.put(Items.FLINT_AND_STEEL, new Tuple(Integer.valueOf(3), Integer.valueOf(4)));
/* 482 */     bC.put(Items.SHEARS, new Tuple(Integer.valueOf(3), Integer.valueOf(4)));
/* 483 */     bC.put(Items.IRON_SWORD, new Tuple(Integer.valueOf(7), Integer.valueOf(11)));
/* 484 */     bC.put(Items.DIAMOND_SWORD, new Tuple(Integer.valueOf(12), Integer.valueOf(14)));
/* 485 */     bC.put(Items.IRON_AXE, new Tuple(Integer.valueOf(6), Integer.valueOf(8)));
/* 486 */     bC.put(Items.DIAMOND_AXE, new Tuple(Integer.valueOf(9), Integer.valueOf(12)));
/* 487 */     bC.put(Items.IRON_PICKAXE, new Tuple(Integer.valueOf(7), Integer.valueOf(9)));
/* 488 */     bC.put(Items.DIAMOND_PICKAXE, new Tuple(Integer.valueOf(10), Integer.valueOf(12)));
/* 489 */     bC.put(Items.IRON_SPADE, new Tuple(Integer.valueOf(4), Integer.valueOf(6)));
/* 490 */     bC.put(Items.DIAMOND_SPADE, new Tuple(Integer.valueOf(7), Integer.valueOf(8)));
/* 491 */     bC.put(Items.IRON_HOE, new Tuple(Integer.valueOf(4), Integer.valueOf(6)));
/* 492 */     bC.put(Items.DIAMOND_HOE, new Tuple(Integer.valueOf(7), Integer.valueOf(8)));
/* 493 */     bC.put(Items.IRON_BOOTS, new Tuple(Integer.valueOf(4), Integer.valueOf(6)));
/* 494 */     bC.put(Items.DIAMOND_BOOTS, new Tuple(Integer.valueOf(7), Integer.valueOf(8)));
/* 495 */     bC.put(Items.IRON_HELMET, new Tuple(Integer.valueOf(4), Integer.valueOf(6)));
/* 496 */     bC.put(Items.DIAMOND_HELMET, new Tuple(Integer.valueOf(7), Integer.valueOf(8)));
/* 497 */     bC.put(Items.IRON_CHESTPLATE, new Tuple(Integer.valueOf(10), Integer.valueOf(14)));
/* 498 */     bC.put(Items.DIAMOND_CHESTPLATE, new Tuple(Integer.valueOf(16), Integer.valueOf(19)));
/* 499 */     bC.put(Items.IRON_LEGGINGS, new Tuple(Integer.valueOf(8), Integer.valueOf(10)));
/* 500 */     bC.put(Items.DIAMOND_LEGGINGS, new Tuple(Integer.valueOf(11), Integer.valueOf(14)));
/* 501 */     bC.put(Items.CHAINMAIL_BOOTS, new Tuple(Integer.valueOf(5), Integer.valueOf(7)));
/* 502 */     bC.put(Items.CHAINMAIL_HELMET, new Tuple(Integer.valueOf(5), Integer.valueOf(7)));
/* 503 */     bC.put(Items.CHAINMAIL_CHESTPLATE, new Tuple(Integer.valueOf(11), Integer.valueOf(15)));
/* 504 */     bC.put(Items.CHAINMAIL_LEGGINGS, new Tuple(Integer.valueOf(9), Integer.valueOf(11)));
/* 505 */     bC.put(Items.BREAD, new Tuple(Integer.valueOf(-4), Integer.valueOf(-2)));
/* 506 */     bC.put(Items.MELON, new Tuple(Integer.valueOf(-8), Integer.valueOf(-4)));
/* 507 */     bC.put(Items.APPLE, new Tuple(Integer.valueOf(-8), Integer.valueOf(-4)));
/* 508 */     bC.put(Items.COOKIE, new Tuple(Integer.valueOf(-10), Integer.valueOf(-7)));
/* 509 */     bC.put(Item.getItemOf(Blocks.GLASS), new Tuple(Integer.valueOf(-5), Integer.valueOf(-3)));
/* 510 */     bC.put(Item.getItemOf(Blocks.BOOKSHELF), new Tuple(Integer.valueOf(3), Integer.valueOf(4)));
/* 511 */     bC.put(Items.LEATHER_CHESTPLATE, new Tuple(Integer.valueOf(4), Integer.valueOf(5)));
/* 512 */     bC.put(Items.LEATHER_BOOTS, new Tuple(Integer.valueOf(2), Integer.valueOf(4)));
/* 513 */     bC.put(Items.LEATHER_HELMET, new Tuple(Integer.valueOf(2), Integer.valueOf(4)));
/* 514 */     bC.put(Items.LEATHER_LEGGINGS, new Tuple(Integer.valueOf(2), Integer.valueOf(4)));
/* 515 */     bC.put(Items.SADDLE, new Tuple(Integer.valueOf(6), Integer.valueOf(8)));
/* 516 */     bC.put(Items.EXP_BOTTLE, new Tuple(Integer.valueOf(-4), Integer.valueOf(-1)));
/* 517 */     bC.put(Items.REDSTONE, new Tuple(Integer.valueOf(-4), Integer.valueOf(-1)));
/* 518 */     bC.put(Items.COMPASS, new Tuple(Integer.valueOf(10), Integer.valueOf(12)));
/* 519 */     bC.put(Items.WATCH, new Tuple(Integer.valueOf(10), Integer.valueOf(12)));
/* 520 */     bC.put(Item.getItemOf(Blocks.GLOWSTONE), new Tuple(Integer.valueOf(-3), Integer.valueOf(-1)));
/* 521 */     bC.put(Items.GRILLED_PORK, new Tuple(Integer.valueOf(-7), Integer.valueOf(-5)));
/* 522 */     bC.put(Items.COOKED_BEEF, new Tuple(Integer.valueOf(-7), Integer.valueOf(-5)));
/* 523 */     bC.put(Items.COOKED_CHICKEN, new Tuple(Integer.valueOf(-8), Integer.valueOf(-6)));
/* 524 */     bC.put(Items.EYE_OF_ENDER, new Tuple(Integer.valueOf(7), Integer.valueOf(11)));
/* 525 */     bC.put(Items.ARROW, new Tuple(Integer.valueOf(-12), Integer.valueOf(-8)));
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
/*     */   private static void a(MerchantRecipeList paramMerchantRecipeList, Item paramItem, Random paramRandom, float paramFloat) {
/* 537 */     if (paramRandom.nextFloat() < paramFloat) {
/* 538 */       paramMerchantRecipeList.add((E)new MerchantRecipe(a(paramItem, paramRandom), Items.EMERALD));
/*     */     }
/*     */   }
/*     */   
/*     */   private static ItemStack a(Item paramItem, Random paramRandom) {
/* 543 */     return new ItemStack(paramItem, b(paramItem, paramRandom), 0);
/*     */   }
/*     */   
/*     */   private static int b(Item paramItem, Random paramRandom) {
/* 547 */     Tuple tuple = (Tuple)bB.get(paramItem);
/* 548 */     if (tuple == null) {
/* 549 */       return 1;
/*     */     }
/* 551 */     if (((Integer)tuple.a()).intValue() >= ((Integer)tuple.b()).intValue()) {
/* 552 */       return ((Integer)tuple.a()).intValue();
/*     */     }
/* 554 */     return ((Integer)tuple.a()).intValue() + paramRandom.nextInt(((Integer)tuple.b()).intValue() - ((Integer)tuple.a()).intValue());
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
/*     */   private static void b(MerchantRecipeList paramMerchantRecipeList, Item paramItem, Random paramRandom, float paramFloat) {
/* 567 */     if (paramRandom.nextFloat() < paramFloat) {
/* 568 */       ItemStack itemStack1, itemStack2; int i = c(paramItem, paramRandom);
/*     */ 
/*     */       
/* 571 */       if (i < 0) {
/* 572 */         itemStack1 = new ItemStack(Items.EMERALD, 1, 0);
/* 573 */         itemStack2 = new ItemStack(paramItem, -i, 0);
/*     */       } else {
/* 575 */         itemStack1 = new ItemStack(Items.EMERALD, i, 0);
/* 576 */         itemStack2 = new ItemStack(paramItem, 1, 0);
/*     */       } 
/* 578 */       paramMerchantRecipeList.add((E)new MerchantRecipe(itemStack1, itemStack2));
/*     */     } 
/*     */   }
/*     */   
/*     */   private static int c(Item paramItem, Random paramRandom) {
/* 583 */     Tuple tuple = (Tuple)bC.get(paramItem);
/* 584 */     if (tuple == null) {
/* 585 */       return 1;
/*     */     }
/* 587 */     if (((Integer)tuple.a()).intValue() >= ((Integer)tuple.b()).intValue()) {
/* 588 */       return ((Integer)tuple.a()).intValue();
/*     */     }
/* 590 */     return ((Integer)tuple.a()).intValue() + paramRandom.nextInt(((Integer)tuple.b()).intValue() - ((Integer)tuple.a()).intValue());
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
/*     */   public GroupDataEntity prepare(GroupDataEntity paramGroupDataEntity) {
/* 617 */     paramGroupDataEntity = super.prepare(paramGroupDataEntity);
/*     */     
/* 619 */     setProfession(this.world.random.nextInt(5));
/*     */     
/* 621 */     return paramGroupDataEntity;
/*     */   }
/*     */   
/*     */   public void cd() {
/* 625 */     this.bz = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityVillager b(EntityAgeable paramEntityAgeable) {
/* 630 */     EntityVillager entityVillager = new EntityVillager(this.world);
/* 631 */     entityVillager.prepare((GroupDataEntity)null);
/* 632 */     return entityVillager;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean bM() {
/* 637 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityVillager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
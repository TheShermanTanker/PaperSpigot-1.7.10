/*     */ package net.minecraft.server.v1_7_R4;
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.util.com.google.common.collect.HashMultimap;
/*     */ import net.minecraft.util.com.google.common.collect.Multimap;
/*     */ import net.minecraft.util.com.mojang.authlib.GameProfile;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.TreeType;
/*     */ import org.bukkit.block.BlockState;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.util.CraftMagicNumbers;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.block.BlockPlaceEvent;
/*     */ import org.bukkit.event.player.PlayerItemDamageEvent;
/*     */ import org.bukkit.event.world.StructureGrowEvent;
/*     */ 
/*     */ public final class ItemStack {
/*  23 */   public static final DecimalFormat a = new DecimalFormat("#.###");
/*     */   public int count;
/*     */   public int c;
/*     */   private Item item;
/*     */   public NBTTagCompound tag;
/*     */   private int damage;
/*     */   private EntityItemFrame g;
/*     */   
/*     */   public ItemStack(Block block) {
/*  32 */     this(block, 1);
/*     */   }
/*     */   
/*     */   public ItemStack(Block block, int i) {
/*  36 */     this(block, i, 0);
/*     */   }
/*     */   
/*     */   public ItemStack(Block block, int i, int j) {
/*  40 */     this(Item.getItemOf(block), i, j);
/*     */   }
/*     */   
/*     */   public ItemStack(Item item) {
/*  44 */     this(item, 1);
/*     */   }
/*     */   
/*     */   public ItemStack(Item item, int i) {
/*  48 */     this(item, i, 0);
/*     */   }
/*     */   
/*     */   public ItemStack(Item item, int i, int j) {
/*  52 */     this.item = item;
/*  53 */     this.count = i;
/*     */     
/*  55 */     setData(j);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ItemStack createStack(NBTTagCompound nbttagcompound) {
/*  64 */     ItemStack itemstack = new ItemStack();
/*     */     
/*  66 */     itemstack.c(nbttagcompound);
/*  67 */     return (itemstack.getItem() != null) ? itemstack : null;
/*     */   }
/*     */   
/*     */   private ItemStack() {}
/*     */   
/*     */   public ItemStack a(int i) {
/*  73 */     ItemStack itemstack = new ItemStack(this.item, i, this.damage);
/*     */     
/*  75 */     if (this.tag != null) {
/*  76 */       itemstack.tag = (NBTTagCompound)this.tag.clone();
/*     */     }
/*     */     
/*  79 */     this.count -= i;
/*  80 */     return itemstack;
/*     */   }
/*     */   
/*     */   public Item getItem() {
/*  84 */     return this.item;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean placeItem(EntityHuman entityhuman, World world, int i, int j, int k, int l, float f, float f1, float f2) {
/*  89 */     int data = getData();
/*  90 */     int count = this.count;
/*     */     
/*  92 */     if (!(getItem() instanceof ItemBucket)) {
/*  93 */       world.captureBlockStates = true;
/*     */       
/*  95 */       if (getItem() instanceof ItemDye && getData() == 15) {
/*  96 */         Block block = world.getType(i, j, k);
/*  97 */         if (block == Blocks.SAPLING || block instanceof BlockMushroom) {
/*  98 */           world.captureTreeGeneration = true;
/*     */         }
/*     */       } 
/*     */     } 
/* 102 */     boolean flag = getItem().interactWith(this, entityhuman, world, i, j, k, l, f, f1, f2);
/* 103 */     int newData = getData();
/* 104 */     int newCount = this.count;
/* 105 */     this.count = count;
/* 106 */     setData(data);
/* 107 */     world.captureBlockStates = false;
/* 108 */     if (flag && world.captureTreeGeneration && world.capturedBlockStates.size() > 0) {
/* 109 */       world.captureTreeGeneration = false;
/* 110 */       Location location = new Location((World)world.getWorld(), i, j, k);
/* 111 */       TreeType treeType = BlockSapling.treeType;
/* 112 */       BlockSapling.treeType = null;
/* 113 */       List<BlockState> blocks = (List<BlockState>)world.capturedBlockStates.clone();
/* 114 */       world.capturedBlockStates.clear();
/* 115 */       StructureGrowEvent event = null;
/* 116 */       if (treeType != null) {
/* 117 */         event = new StructureGrowEvent(location, treeType, false, (Player)entityhuman.getBukkitEntity(), blocks);
/* 118 */         Bukkit.getPluginManager().callEvent((Event)event);
/*     */       } 
/* 120 */       if (event == null || !event.isCancelled()) {
/*     */         
/* 122 */         if (this.count == count && getData() == data) {
/* 123 */           setData(newData);
/* 124 */           this.count = newCount;
/*     */         } 
/* 126 */         for (BlockState blockstate : blocks) {
/* 127 */           blockstate.update(true);
/*     */         }
/*     */       } 
/*     */       
/* 131 */       return flag;
/*     */     } 
/* 133 */     world.captureTreeGeneration = false;
/*     */     
/* 135 */     if (flag) {
/* 136 */       BlockPlaceEvent placeEvent = null;
/* 137 */       List<BlockState> blocks = (List<BlockState>)world.capturedBlockStates.clone();
/* 138 */       world.capturedBlockStates.clear();
/* 139 */       if (blocks.size() > 1) {
/* 140 */         BlockMultiPlaceEvent blockMultiPlaceEvent = CraftEventFactory.callBlockMultiPlaceEvent(world, entityhuman, blocks, i, j, k);
/* 141 */       } else if (blocks.size() == 1) {
/* 142 */         placeEvent = CraftEventFactory.callBlockPlaceEvent(world, entityhuman, blocks.get(0), i, j, k);
/*     */       } 
/*     */       
/* 145 */       if (placeEvent != null && (placeEvent.isCancelled() || !placeEvent.canBuild())) {
/* 146 */         flag = false;
/*     */         
/* 148 */         for (BlockState blockstate : blocks) {
/* 149 */           blockstate.update(true, false);
/*     */         }
/*     */       } else {
/*     */         
/* 153 */         if (this.count == count && getData() == data) {
/* 154 */           setData(newData);
/* 155 */           this.count = newCount;
/*     */         } 
/* 157 */         for (BlockState blockstate : blocks) {
/* 158 */           int x = blockstate.getX();
/* 159 */           int y = blockstate.getY();
/* 160 */           int z = blockstate.getZ();
/* 161 */           int updateFlag = ((CraftBlockState)blockstate).getFlag();
/* 162 */           Material mat = blockstate.getType();
/* 163 */           Block oldBlock = CraftMagicNumbers.getBlock(mat);
/* 164 */           Block block = world.getType(x, y, z);
/*     */           
/* 166 */           if (block != null && !(block instanceof BlockContainer)) {
/* 167 */             block.onPlace(world, x, y, z);
/*     */           }
/*     */           
/* 170 */           world.notifyAndUpdatePhysics(x, y, z, null, oldBlock, block, updateFlag);
/*     */         } 
/* 172 */         entityhuman.a(StatisticList.USE_ITEM_COUNT[Item.getId(this.item)], 1);
/*     */       } 
/*     */     } 
/* 175 */     world.capturedBlockStates.clear();
/*     */ 
/*     */     
/* 178 */     return flag;
/*     */   }
/*     */   
/*     */   public float a(Block block) {
/* 182 */     return getItem().getDestroySpeed(this, block);
/*     */   }
/*     */   
/*     */   public ItemStack a(World world, EntityHuman entityhuman) {
/* 186 */     return getItem().a(this, world, entityhuman);
/*     */   }
/*     */   
/*     */   public ItemStack b(World world, EntityHuman entityhuman) {
/* 190 */     return getItem().b(this, world, entityhuman);
/*     */   }
/*     */   
/*     */   public NBTTagCompound save(NBTTagCompound nbttagcompound) {
/* 194 */     nbttagcompound.setShort("id", (short)Item.getId(this.item));
/* 195 */     nbttagcompound.setByte("Count", (byte)this.count);
/* 196 */     nbttagcompound.setShort("Damage", (short)this.damage);
/* 197 */     if (this.tag != null) {
/* 198 */       nbttagcompound.set("tag", this.tag.clone());
/*     */     }
/*     */     
/* 201 */     return nbttagcompound;
/*     */   }
/*     */   
/*     */   public void c(NBTTagCompound nbttagcompound) {
/* 205 */     this.item = Item.getById(nbttagcompound.getShort("id"));
/* 206 */     this.count = nbttagcompound.getByte("Count");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 213 */     setData(nbttagcompound.getShort("Damage"));
/*     */ 
/*     */     
/* 216 */     if (nbttagcompound.hasKeyOfType("tag", 10)) {
/*     */       
/* 218 */       this.tag = (NBTTagCompound)nbttagcompound.getCompound("tag").clone();
/* 219 */       validateSkullSkin();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void validateSkullSkin() {
/* 226 */     if (this.item == Items.SKULL && getData() == 3) {
/*     */       String owner;
/*     */       
/* 229 */       if (this.tag.hasKeyOfType("SkullOwner", 8)) {
/*     */         
/* 231 */         owner = this.tag.getString("SkullOwner");
/* 232 */       } else if (this.tag.hasKeyOfType("SkullOwner", 10)) {
/*     */         
/* 234 */         GameProfile profile = GameProfileSerializer.deserialize(this.tag.getCompound("SkullOwner"));
/* 235 */         if (profile == null || !profile.getProperties().isEmpty()) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/* 240 */         owner = profile.getName();
/*     */       } else {
/*     */         return;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 247 */       final String finalOwner = owner;
/* 248 */       TileEntitySkull.executor.execute(new Runnable()
/*     */           {
/*     */ 
/*     */             
/*     */             public void run()
/*     */             {
/* 254 */               final GameProfile profile = (GameProfile)TileEntitySkull.skinCache.getUnchecked(finalOwner.toLowerCase());
/* 255 */               if (profile != null)
/*     */               {
/* 257 */                 (MinecraftServer.getServer()).processQueue.add(new Runnable()
/*     */                     {
/*     */                       
/*     */                       public void run()
/*     */                       {
/* 262 */                         NBTTagCompound nbtProfile = new NBTTagCompound();
/* 263 */                         GameProfileSerializer.serialize(nbtProfile, profile);
/* 264 */                         ItemStack.this.tag.set("SkullOwner", nbtProfile);
/*     */                       }
/*     */                     });
/*     */               }
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxStackSize() {
/* 275 */     return getItem().getMaxStackSize();
/*     */   }
/*     */   
/*     */   public boolean isStackable() {
/* 279 */     return (getMaxStackSize() > 1 && (!g() || !i()));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean g() {
/* 284 */     if (this.item.getMaxDurability() <= 0)
/*     */     {
/* 286 */       return false;
/*     */     }
/* 288 */     return (!hasTag() || !getTag().getBoolean("Unbreakable"));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean usesData() {
/* 293 */     return this.item.n();
/*     */   }
/*     */   
/*     */   public boolean i() {
/* 297 */     return (g() && this.damage > 0);
/*     */   }
/*     */   
/*     */   public int j() {
/* 301 */     return this.damage;
/*     */   }
/*     */   
/*     */   public int getData() {
/* 305 */     return this.damage;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setData(int i) {
/* 311 */     if (i == 32767) {
/* 312 */       this.damage = i;
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 317 */     if (CraftMagicNumbers.getBlock(CraftMagicNumbers.getId(getItem())) != Blocks.AIR)
/*     */     {
/* 319 */       if (!usesData() && !getItem().usesDurability()) {
/* 320 */         i = 0;
/*     */       }
/*     */     }
/*     */ 
/*     */     
/* 325 */     if (CraftMagicNumbers.getBlock(CraftMagicNumbers.getId(getItem())) == Blocks.DOUBLE_PLANT && (i > 5 || i < 0)) {
/* 326 */       i = 0;
/*     */     }
/*     */ 
/*     */     
/* 330 */     this.damage = i;
/* 331 */     if (this.damage < -1) {
/* 332 */       this.damage = 0;
/*     */     }
/*     */   }
/*     */   
/*     */   public int l() {
/* 337 */     return this.item.getMaxDurability();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDamaged(int i, Random random) {
/* 342 */     return isDamaged(i, random, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDamaged(int i, Random random, EntityLiving entityliving) {
/* 347 */     if (!g()) {
/* 348 */       return false;
/*     */     }
/* 350 */     if (i > 0) {
/* 351 */       int j = EnchantmentManager.getEnchantmentLevel(Enchantment.DURABILITY.id, this);
/* 352 */       int k = 0;
/*     */       
/* 354 */       for (int l = 0; j > 0 && l < i; l++) {
/* 355 */         if (EnchantmentDurability.a(this, j, random)) {
/* 356 */           k++;
/*     */         }
/*     */       } 
/*     */       
/* 360 */       i -= k;
/*     */       
/* 362 */       if (entityliving instanceof EntityPlayer) {
/* 363 */         CraftItemStack item = CraftItemStack.asCraftMirror(this);
/* 364 */         PlayerItemDamageEvent event = new PlayerItemDamageEvent((Player)entityliving.getBukkitEntity(), (org.bukkit.inventory.ItemStack)item, i);
/* 365 */         Bukkit.getServer().getPluginManager().callEvent((Event)event);
/* 366 */         if (event.isCancelled()) return false; 
/* 367 */         i = event.getDamage();
/*     */       } 
/*     */       
/* 370 */       if (i <= 0) {
/* 371 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 375 */     this.damage += i;
/* 376 */     return (this.damage > l());
/*     */   }
/*     */ 
/*     */   
/*     */   public void damage(int i, EntityLiving entityliving) {
/* 381 */     if ((!(entityliving instanceof EntityHuman) || !((EntityHuman)entityliving).abilities.canInstantlyBuild) && 
/* 382 */       g() && 
/* 383 */       isDamaged(i, entityliving.aI(), entityliving)) {
/* 384 */       entityliving.a(this);
/* 385 */       this.count--;
/* 386 */       if (entityliving instanceof EntityHuman) {
/* 387 */         EntityHuman entityhuman = (EntityHuman)entityliving;
/*     */         
/* 389 */         entityhuman.a(StatisticList.BREAK_ITEM_COUNT[Item.getId(this.item)], 1);
/* 390 */         if (this.count == 0 && getItem() instanceof ItemBow) {
/* 391 */           entityhuman.bG();
/*     */         }
/*     */       } 
/*     */       
/* 395 */       if (this.count < 0) {
/* 396 */         this.count = 0;
/*     */       }
/*     */ 
/*     */       
/* 400 */       if (this.count == 0 && entityliving instanceof EntityHuman) {
/* 401 */         CraftEventFactory.callPlayerItemBreakEvent((EntityHuman)entityliving, this);
/*     */       }
/*     */ 
/*     */       
/* 405 */       this.damage = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(EntityLiving entityliving, EntityHuman entityhuman) {
/* 412 */     boolean flag = this.item.a(this, entityliving, entityhuman);
/*     */     
/* 414 */     if (flag) {
/* 415 */       entityhuman.a(StatisticList.USE_ITEM_COUNT[Item.getId(this.item)], 1);
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(World world, Block block, int i, int j, int k, EntityHuman entityhuman) {
/* 420 */     boolean flag = this.item.a(this, world, block, i, j, k, entityhuman);
/*     */     
/* 422 */     if (flag) {
/* 423 */       entityhuman.a(StatisticList.USE_ITEM_COUNT[Item.getId(this.item)], 1);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean b(Block block) {
/* 428 */     return this.item.canDestroySpecialBlock(block);
/*     */   }
/*     */   
/*     */   public boolean a(EntityHuman entityhuman, EntityLiving entityliving) {
/* 432 */     return this.item.a(this, entityhuman, entityliving);
/*     */   }
/*     */   
/*     */   public ItemStack cloneItemStack() {
/* 436 */     ItemStack itemstack = new ItemStack(this.item, this.count, this.damage);
/*     */     
/* 438 */     if (this.tag != null) {
/* 439 */       itemstack.tag = (NBTTagCompound)this.tag.clone();
/*     */     }
/*     */     
/* 442 */     return itemstack;
/*     */   }
/*     */   
/*     */   public static boolean equals(ItemStack itemstack, ItemStack itemstack1) {
/* 446 */     return (itemstack == null && itemstack1 == null) ? true : ((itemstack != null && itemstack1 != null) ? ((itemstack.tag == null && itemstack1.tag != null) ? false : ((itemstack.tag == null || itemstack.tag.equals(itemstack1.tag)))) : false);
/*     */   }
/*     */   
/*     */   public static boolean matches(ItemStack itemstack, ItemStack itemstack1) {
/* 450 */     return (itemstack == null && itemstack1 == null) ? true : ((itemstack != null && itemstack1 != null) ? itemstack.d(itemstack1) : false);
/*     */   }
/*     */   
/*     */   private boolean d(ItemStack itemstack) {
/* 454 */     return (this.count != itemstack.count) ? false : ((this.item != itemstack.item) ? false : ((this.damage != itemstack.damage) ? false : ((this.tag == null && itemstack.tag != null) ? false : ((this.tag == null || this.tag.equals(itemstack.tag))))));
/*     */   }
/*     */   
/*     */   public boolean doMaterialsMatch(ItemStack itemstack) {
/* 458 */     return (this.item == itemstack.item && this.damage == itemstack.damage);
/*     */   }
/*     */   
/*     */   public String a() {
/* 462 */     return this.item.a(this);
/*     */   }
/*     */   
/*     */   public static ItemStack b(ItemStack itemstack) {
/* 466 */     return (itemstack == null) ? null : itemstack.cloneItemStack();
/*     */   }
/*     */   
/*     */   public String toString() {
/* 470 */     return this.count + "x" + this.item.getName() + "@" + this.damage;
/*     */   }
/*     */   
/*     */   public void a(World world, Entity entity, int i, boolean flag) {
/* 474 */     if (this.c > 0) {
/* 475 */       this.c--;
/*     */     }
/*     */     
/* 478 */     this.item.a(this, world, entity, i, flag);
/*     */   }
/*     */   
/*     */   public void a(World world, EntityHuman entityhuman, int i) {
/* 482 */     entityhuman.a(StatisticList.CRAFT_BLOCK_COUNT[Item.getId(this.item)], i);
/* 483 */     this.item.d(this, world, entityhuman);
/*     */   }
/*     */   
/*     */   public int n() {
/* 487 */     return getItem().d_(this);
/*     */   }
/*     */   
/*     */   public EnumAnimation o() {
/* 491 */     return getItem().d(this);
/*     */   }
/*     */   
/*     */   public void b(World world, EntityHuman entityhuman, int i) {
/* 495 */     getItem().a(this, world, entityhuman, i);
/*     */   }
/*     */   
/*     */   public boolean hasTag() {
/* 499 */     return (this.tag != null);
/*     */   }
/*     */   
/*     */   public NBTTagCompound getTag() {
/* 503 */     return this.tag;
/*     */   }
/*     */   
/*     */   public NBTTagList getEnchantments() {
/* 507 */     return (this.tag == null) ? null : this.tag.getList("ench", 10);
/*     */   }
/*     */   
/*     */   public void setTag(NBTTagCompound nbttagcompound) {
/* 511 */     this.tag = nbttagcompound;
/* 512 */     validateSkullSkin();
/*     */   }
/*     */   
/*     */   public String getName() {
/* 516 */     String s = getItem().n(this);
/*     */     
/* 518 */     if (this.tag != null && this.tag.hasKeyOfType("display", 10)) {
/* 519 */       NBTTagCompound nbttagcompound = this.tag.getCompound("display");
/*     */       
/* 521 */       if (nbttagcompound.hasKeyOfType("Name", 8)) {
/* 522 */         s = nbttagcompound.getString("Name");
/*     */       }
/*     */     } 
/*     */     
/* 526 */     return s;
/*     */   }
/*     */   
/*     */   public ItemStack c(String s) {
/* 530 */     if (this.tag == null) {
/* 531 */       this.tag = new NBTTagCompound();
/*     */     }
/*     */     
/* 534 */     if (!this.tag.hasKeyOfType("display", 10)) {
/* 535 */       this.tag.set("display", new NBTTagCompound());
/*     */     }
/*     */     
/* 538 */     this.tag.getCompound("display").setString("Name", s);
/* 539 */     return this;
/*     */   }
/*     */   
/*     */   public void t() {
/* 543 */     if (this.tag != null && 
/* 544 */       this.tag.hasKeyOfType("display", 10)) {
/* 545 */       NBTTagCompound nbttagcompound = this.tag.getCompound("display");
/*     */       
/* 547 */       nbttagcompound.remove("Name");
/* 548 */       if (nbttagcompound.isEmpty()) {
/* 549 */         this.tag.remove("display");
/* 550 */         if (this.tag.isEmpty()) {
/* 551 */           setTag((NBTTagCompound)null);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasName() {
/* 559 */     return (this.tag == null) ? false : (!this.tag.hasKeyOfType("display", 10) ? false : this.tag.getCompound("display").hasKeyOfType("Name", 8));
/*     */   }
/*     */   
/*     */   public EnumItemRarity w() {
/* 563 */     return getItem().f(this);
/*     */   }
/*     */   
/*     */   public boolean x() {
/* 567 */     return !getItem().e_(this) ? false : (!hasEnchantments());
/*     */   }
/*     */   
/*     */   public void addEnchantment(Enchantment enchantment, int i) {
/* 571 */     if (this.tag == null) {
/* 572 */       setTag(new NBTTagCompound());
/*     */     }
/*     */     
/* 575 */     if (!this.tag.hasKeyOfType("ench", 9)) {
/* 576 */       this.tag.set("ench", new NBTTagList());
/*     */     }
/*     */     
/* 579 */     NBTTagList nbttaglist = this.tag.getList("ench", 10);
/* 580 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*     */     
/* 582 */     nbttagcompound.setShort("id", (short)enchantment.id);
/* 583 */     nbttagcompound.setShort("lvl", (short)(byte)i);
/* 584 */     nbttaglist.add(nbttagcompound);
/*     */   }
/*     */   
/*     */   public boolean hasEnchantments() {
/* 588 */     return (this.tag != null && this.tag.hasKeyOfType("ench", 9));
/*     */   }
/*     */   
/*     */   public void a(String s, NBTBase nbtbase) {
/* 592 */     if (this.tag == null) {
/* 593 */       setTag(new NBTTagCompound());
/*     */     }
/*     */     
/* 596 */     this.tag.set(s, nbtbase);
/*     */   }
/*     */   
/*     */   public boolean z() {
/* 600 */     return getItem().v();
/*     */   }
/*     */   
/*     */   public boolean A() {
/* 604 */     return (this.g != null);
/*     */   }
/*     */   
/*     */   public void a(EntityItemFrame entityitemframe) {
/* 608 */     this.g = entityitemframe;
/*     */   }
/*     */   
/*     */   public EntityItemFrame B() {
/* 612 */     return this.g;
/*     */   }
/*     */   
/*     */   public int getRepairCost() {
/* 616 */     return (hasTag() && this.tag.hasKeyOfType("RepairCost", 3)) ? this.tag.getInt("RepairCost") : 0;
/*     */   }
/*     */   
/*     */   public void setRepairCost(int i) {
/* 620 */     if (!hasTag()) {
/* 621 */       this.tag = new NBTTagCompound();
/*     */     }
/*     */     
/* 624 */     this.tag.setInt("RepairCost", i);
/*     */   }
/*     */ 
/*     */   
/*     */   public Multimap D() {
/*     */     Object object;
/* 630 */     if (hasTag() && this.tag.hasKeyOfType("AttributeModifiers", 9)) {
/* 631 */       object = HashMultimap.create();
/* 632 */       NBTTagList nbttaglist = this.tag.getList("AttributeModifiers", 10);
/*     */       
/* 634 */       for (int i = 0; i < nbttaglist.size(); i++) {
/* 635 */         NBTTagCompound nbttagcompound = nbttaglist.get(i);
/* 636 */         AttributeModifier attributemodifier = GenericAttributes.a(nbttagcompound);
/*     */         
/* 638 */         if (attributemodifier.a().getLeastSignificantBits() != 0L && attributemodifier.a().getMostSignificantBits() != 0L) {
/* 639 */           ((Multimap)object).put(nbttagcompound.getString("AttributeName"), attributemodifier);
/*     */         }
/*     */       } 
/*     */     } else {
/* 643 */       object = getItem().k();
/*     */     } 
/*     */     
/* 646 */     return (Multimap)object;
/*     */   }
/*     */   
/*     */   public void setItem(Item item) {
/* 650 */     this.item = item;
/* 651 */     setData(getData());
/*     */   }
/*     */   
/*     */   public IChatBaseComponent E() {
/* 655 */     IChatBaseComponent ichatbasecomponent = (new ChatComponentText("[")).a(getName()).a("]");
/*     */     
/* 657 */     if (this.item != null) {
/* 658 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/*     */       
/* 660 */       save(nbttagcompound);
/* 661 */       ichatbasecomponent.getChatModifier().a(new ChatHoverable(EnumHoverAction.SHOW_ITEM, new ChatComponentText(nbttagcompound.toString())));
/* 662 */       ichatbasecomponent.getChatModifier().setColor((w()).e);
/*     */     } 
/*     */     
/* 665 */     return ichatbasecomponent;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ItemStack.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
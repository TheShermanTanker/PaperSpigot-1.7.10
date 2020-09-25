/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.entity.EntityUnleashEvent;
/*     */ 
/*     */ 
/*     */ public abstract class EntityInsentient
/*     */   extends EntityLiving
/*     */ {
/*     */   public int a_;
/*     */   protected int b;
/*     */   private ControllerLook lookController;
/*     */   private ControllerMove moveController;
/*     */   private ControllerJump bm;
/*     */   private EntityAIBodyControl bn;
/*     */   private Navigation navigation;
/*     */   protected final PathfinderGoalSelector goalSelector;
/*     */   protected final PathfinderGoalSelector targetSelector;
/*  25 */   private WeakReference<EntityLiving> goalTarget = new WeakReference<EntityLiving>(null);
/*     */   private EntitySenses bq;
/*  27 */   private ItemStack[] equipment = new ItemStack[5];
/*  28 */   public float[] dropChances = new float[5];
/*     */   public boolean canPickUpLoot;
/*  30 */   public boolean persistent = !isTypeNotPersistent();
/*     */   protected float f;
/*     */   private Entity bu;
/*     */   protected int g;
/*     */   private boolean bv;
/*     */   private Entity bw;
/*     */   private NBTTagCompound bx;
/*     */   public PathfinderGoalFloat goalFloat;
/*     */   
/*     */   public EntityInsentient(World world) {
/*  40 */     super(world);
/*  41 */     this.goalSelector = new PathfinderGoalSelector((world != null && world.methodProfiler != null) ? world.methodProfiler : null);
/*  42 */     this.targetSelector = new PathfinderGoalSelector((world != null && world.methodProfiler != null) ? world.methodProfiler : null);
/*  43 */     this.lookController = new ControllerLook(this);
/*  44 */     this.moveController = new ControllerMove(this);
/*  45 */     this.bm = new ControllerJump(this);
/*  46 */     this.bn = new EntityAIBodyControl(this);
/*  47 */     this.navigation = new Navigation(this, world);
/*  48 */     this.bq = new EntitySenses(this);
/*     */     
/*  50 */     for (int i = 0; i < this.dropChances.length; i++) {
/*  51 */       this.dropChances[i] = 0.085F;
/*     */     }
/*     */   }
/*     */   
/*     */   protected void aD() {
/*  56 */     super.aD();
/*  57 */     getAttributeMap().b(GenericAttributes.b).setValue(16.0D);
/*     */   }
/*     */   
/*     */   public ControllerLook getControllerLook() {
/*  61 */     return this.lookController;
/*     */   }
/*     */   
/*     */   public ControllerMove getControllerMove() {
/*  65 */     return this.moveController;
/*     */   }
/*     */   
/*     */   public ControllerJump getControllerJump() {
/*  69 */     return this.bm;
/*     */   }
/*     */   
/*     */   public Navigation getNavigation() {
/*  73 */     return this.navigation;
/*     */   }
/*     */   
/*     */   public EntitySenses getEntitySenses() {
/*  77 */     return this.bq;
/*     */   }
/*     */   
/*     */   public EntityLiving getGoalTarget() {
/*  81 */     return this.goalTarget.get();
/*     */   }
/*     */   
/*     */   public void setGoalTarget(EntityLiving entityliving) {
/*  85 */     this.goalTarget = new WeakReference<EntityLiving>(entityliving);
/*     */   }
/*     */   
/*     */   public boolean a(Class<EntityCreeper> oclass) {
/*  89 */     return (EntityCreeper.class != oclass && EntityGhast.class != oclass);
/*     */   }
/*     */   
/*     */   public void p() {}
/*     */   
/*     */   protected void c() {
/*  95 */     super.c();
/*  96 */     this.datawatcher.a(11, Byte.valueOf((byte)0));
/*  97 */     this.datawatcher.a(10, "");
/*     */     
/*  99 */     this.datawatcher.a(3, Byte.valueOf((byte)0));
/* 100 */     this.datawatcher.a(2, "");
/*     */   }
/*     */ 
/*     */   
/*     */   public int q() {
/* 105 */     return 80;
/*     */   }
/*     */   
/*     */   public void r() {
/* 109 */     String s = t();
/*     */     
/* 111 */     if (s != null) {
/* 112 */       makeSound(s, bf(), bg());
/*     */     }
/*     */   }
/*     */   
/*     */   public void C() {
/* 117 */     super.C();
/* 118 */     this.world.methodProfiler.a("mobBaseTick");
/* 119 */     if (isAlive() && this.random.nextInt(1000) < this.a_++) {
/* 120 */       this.a_ = -q();
/* 121 */       r();
/*     */     } 
/*     */     
/* 124 */     this.world.methodProfiler.b();
/*     */   }
/*     */   
/*     */   protected int getExpValue(EntityHuman entityhuman) {
/* 128 */     if (this.b > 0) {
/* 129 */       int i = this.b;
/* 130 */       ItemStack[] aitemstack = getEquipment();
/*     */       
/* 132 */       for (int j = 0; j < aitemstack.length; j++) {
/* 133 */         if (aitemstack[j] != null && this.dropChances[j] <= 1.0F) {
/* 134 */           i += 1 + this.random.nextInt(3);
/*     */         }
/*     */       } 
/*     */       
/* 138 */       return i;
/*     */     } 
/* 140 */     return this.b;
/*     */   }
/*     */ 
/*     */   
/*     */   public void s() {
/* 145 */     for (int i = 0; i < 20; i++) {
/* 146 */       double d0 = this.random.nextGaussian() * 0.02D;
/* 147 */       double d1 = this.random.nextGaussian() * 0.02D;
/* 148 */       double d2 = this.random.nextGaussian() * 0.02D;
/* 149 */       double d3 = 10.0D;
/*     */       
/* 151 */       this.world.addParticle("explode", this.locX + (this.random.nextFloat() * this.width * 2.0F) - this.width - d0 * d3, this.locY + (this.random.nextFloat() * this.length) - d1 * d3, this.locZ + (this.random.nextFloat() * this.width * 2.0F) - this.width - d2 * d3, d0, d1, d2);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void h() {
/* 156 */     super.h();
/* 157 */     if (!this.world.isStatic) {
/* 158 */       bL();
/*     */     }
/*     */   }
/*     */   
/*     */   protected float f(float f, float f1) {
/* 163 */     if (bk()) {
/* 164 */       this.bn.a();
/* 165 */       return f1;
/*     */     } 
/* 167 */     return super.f(f, f1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected String t() {
/* 172 */     return null;
/*     */   }
/*     */   
/*     */   protected Item getLoot() {
/* 176 */     return Item.getById(0);
/*     */   }
/*     */   
/*     */   protected void dropDeathLoot(boolean flag, int i) {
/* 180 */     Item item = getLoot();
/*     */     
/* 182 */     if (item != null) {
/* 183 */       int j = this.random.nextInt(3);
/*     */       
/* 185 */       if (i > 0) {
/* 186 */         j += this.random.nextInt(i + 1);
/*     */       }
/*     */       
/* 189 */       for (int k = 0; k < j; k++) {
/* 190 */         a(item, 1);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/* 196 */     super.b(nbttagcompound);
/* 197 */     nbttagcompound.setBoolean("CanPickUpLoot", bJ());
/* 198 */     nbttagcompound.setBoolean("PersistenceRequired", this.persistent);
/* 199 */     NBTTagList nbttaglist = new NBTTagList();
/*     */ 
/*     */ 
/*     */     
/* 203 */     for (int i = 0; i < this.equipment.length; i++) {
/* 204 */       NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 205 */       if (this.equipment[i] != null) {
/* 206 */         this.equipment[i].save(nbttagcompound1);
/*     */       }
/*     */       
/* 209 */       nbttaglist.add(nbttagcompound1);
/*     */     } 
/*     */     
/* 212 */     nbttagcompound.set("Equipment", nbttaglist);
/* 213 */     NBTTagList nbttaglist1 = new NBTTagList();
/*     */     
/* 215 */     for (int j = 0; j < this.dropChances.length; j++) {
/* 216 */       nbttaglist1.add(new NBTTagFloat(this.dropChances[j]));
/*     */     }
/*     */     
/* 219 */     nbttagcompound.set("DropChances", nbttaglist1);
/* 220 */     nbttagcompound.setString("CustomName", getCustomName());
/* 221 */     nbttagcompound.setBoolean("CustomNameVisible", getCustomNameVisible());
/* 222 */     nbttagcompound.setBoolean("Leashed", this.bv);
/* 223 */     if (this.bw != null) {
/* 224 */       NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 225 */       if (this.bw instanceof EntityLiving) {
/* 226 */         nbttagcompound1.setLong("UUIDMost", this.bw.getUniqueID().getMostSignificantBits());
/* 227 */         nbttagcompound1.setLong("UUIDLeast", this.bw.getUniqueID().getLeastSignificantBits());
/* 228 */       } else if (this.bw instanceof EntityHanging) {
/* 229 */         EntityHanging entityhanging = (EntityHanging)this.bw;
/*     */         
/* 231 */         nbttagcompound1.setInt("X", entityhanging.x);
/* 232 */         nbttagcompound1.setInt("Y", entityhanging.y);
/* 233 */         nbttagcompound1.setInt("Z", entityhanging.z);
/*     */       } 
/*     */       
/* 236 */       nbttagcompound.set("Leash", nbttagcompound1);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/* 241 */     super.a(nbttagcompound);
/*     */ 
/*     */     
/* 244 */     boolean data = nbttagcompound.getBoolean("CanPickUpLoot");
/* 245 */     if (isLevelAtLeast(nbttagcompound, 1) || data) {
/* 246 */       this.canPickUpLoot = data;
/*     */     }
/*     */     
/* 249 */     data = nbttagcompound.getBoolean("PersistenceRequired");
/* 250 */     if (isLevelAtLeast(nbttagcompound, 1) || data) {
/* 251 */       this.persistent = data;
/*     */     }
/*     */ 
/*     */     
/* 255 */     if (nbttagcompound.hasKeyOfType("CustomName", 8) && nbttagcompound.getString("CustomName").length() > 0) {
/* 256 */       setCustomName(nbttagcompound.getString("CustomName"));
/*     */     }
/*     */     
/* 259 */     setCustomNameVisible(nbttagcompound.getBoolean("CustomNameVisible"));
/*     */ 
/*     */ 
/*     */     
/* 263 */     if (nbttagcompound.hasKeyOfType("Equipment", 9)) {
/* 264 */       NBTTagList nbttaglist = nbttagcompound.getList("Equipment", 10);
/*     */       
/* 266 */       for (int i = 0; i < this.equipment.length; i++) {
/* 267 */         this.equipment[i] = ItemStack.createStack(nbttaglist.get(i));
/*     */       }
/*     */     } 
/*     */     
/* 271 */     if (nbttagcompound.hasKeyOfType("DropChances", 9)) {
/* 272 */       NBTTagList nbttaglist = nbttagcompound.getList("DropChances", 5);
/*     */       
/* 274 */       for (int i = 0; i < nbttaglist.size(); i++) {
/* 275 */         this.dropChances[i] = nbttaglist.e(i);
/*     */       }
/*     */     } 
/*     */     
/* 279 */     this.bv = nbttagcompound.getBoolean("Leashed");
/* 280 */     if (this.bv && nbttagcompound.hasKeyOfType("Leash", 10)) {
/* 281 */       this.bx = nbttagcompound.getCompound("Leash");
/*     */     }
/*     */   }
/*     */   
/*     */   public void n(float f) {
/* 286 */     this.be = f;
/*     */   }
/*     */   
/*     */   public void i(float f) {
/* 290 */     super.i(f);
/* 291 */     n(f);
/*     */   }
/*     */   
/*     */   public void e() {
/* 295 */     super.e();
/* 296 */     this.world.methodProfiler.a("looting");
/* 297 */     if (!this.world.isStatic && bJ() && !this.aT && this.world.getGameRules().getBoolean("mobGriefing")) {
/* 298 */       List list = this.world.a(EntityItem.class, this.boundingBox.grow(1.0D, 0.0D, 1.0D));
/* 299 */       Iterator<EntityItem> iterator = list.iterator();
/*     */       
/* 301 */       while (iterator.hasNext()) {
/* 302 */         EntityItem entityitem = iterator.next();
/*     */         
/* 304 */         if (!entityitem.dead && entityitem.getItemStack() != null) {
/* 305 */           ItemStack itemstack = entityitem.getItemStack();
/* 306 */           int i = b(itemstack);
/*     */           
/* 308 */           if (i > -1) {
/* 309 */             boolean flag = true;
/* 310 */             ItemStack itemstack1 = getEquipment(i);
/*     */             
/* 312 */             if (itemstack1 != null) {
/* 313 */               if (i == 0) {
/* 314 */                 if (itemstack.getItem() instanceof ItemSword && !(itemstack1.getItem() instanceof ItemSword)) {
/* 315 */                   flag = true;
/* 316 */                 } else if (itemstack.getItem() instanceof ItemSword && itemstack1.getItem() instanceof ItemSword) {
/* 317 */                   ItemSword itemsword = (ItemSword)itemstack.getItem();
/* 318 */                   ItemSword itemsword1 = (ItemSword)itemstack1.getItem();
/*     */                   
/* 320 */                   if (itemsword.i() == itemsword1.i()) {
/* 321 */                     flag = (itemstack.getData() > itemstack1.getData() || (itemstack.hasTag() && !itemstack1.hasTag()));
/*     */                   } else {
/* 323 */                     flag = (itemsword.i() > itemsword1.i());
/*     */                   } 
/*     */                 } else {
/* 326 */                   flag = false;
/*     */                 } 
/* 328 */               } else if (itemstack.getItem() instanceof ItemArmor && !(itemstack1.getItem() instanceof ItemArmor)) {
/* 329 */                 flag = true;
/* 330 */               } else if (itemstack.getItem() instanceof ItemArmor && itemstack1.getItem() instanceof ItemArmor) {
/* 331 */                 ItemArmor itemarmor = (ItemArmor)itemstack.getItem();
/* 332 */                 ItemArmor itemarmor1 = (ItemArmor)itemstack1.getItem();
/*     */                 
/* 334 */                 if (itemarmor.c == itemarmor1.c) {
/* 335 */                   flag = (itemstack.getData() > itemstack1.getData() || (itemstack.hasTag() && !itemstack1.hasTag()));
/*     */                 } else {
/* 337 */                   flag = (itemarmor.c > itemarmor1.c);
/*     */                 } 
/*     */               } else {
/* 340 */                 flag = false;
/*     */               } 
/*     */             }
/*     */             
/* 344 */             if (flag) {
/* 345 */               if (itemstack1 != null && this.random.nextFloat() - 0.1F < this.dropChances[i]) {
/* 346 */                 a(itemstack1, 0.0F);
/*     */               }
/*     */               
/* 349 */               if (itemstack.getItem() == Items.DIAMOND && entityitem.j() != null) {
/* 350 */                 EntityHuman entityhuman = this.world.a(entityitem.j());
/*     */                 
/* 352 */                 if (entityhuman != null) {
/* 353 */                   entityhuman.a(AchievementList.x);
/*     */                 }
/*     */               } 
/*     */               
/* 357 */               setEquipment(i, itemstack);
/* 358 */               this.dropChances[i] = 2.0F;
/* 359 */               this.persistent = true;
/* 360 */               receive(entityitem, 1);
/* 361 */               entityitem.die();
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 368 */     this.world.methodProfiler.b();
/*     */   }
/*     */   
/*     */   protected boolean bk() {
/* 372 */     return false;
/*     */   }
/*     */   
/*     */   protected boolean isTypeNotPersistent() {
/* 376 */     return true;
/*     */   }
/*     */   
/*     */   protected void w() {
/* 380 */     if (this.persistent) {
/* 381 */       this.aU = 0;
/*     */     } else {
/* 383 */       EntityHuman entityhuman = this.world.findNearbyPlayerWhoAffectsSpawning(this, -1.0D);
/*     */       
/* 385 */       if (entityhuman != null) {
/* 386 */         double d0 = entityhuman.locX - this.locX;
/* 387 */         double d1 = entityhuman.locY - this.locY;
/* 388 */         double d2 = entityhuman.locZ - this.locZ;
/* 389 */         double d3 = d0 * d0 + d1 * d1 + d2 * d2;
/*     */         
/* 391 */         if (d3 > this.world.paperSpigotConfig.hardDespawnDistance.intValue()) {
/* 392 */           die();
/*     */         }
/*     */         
/* 395 */         if (this.aU > 600 && this.random.nextInt(800) == 0 && d3 > this.world.paperSpigotConfig.softDespawnDistance.intValue()) {
/* 396 */           die();
/* 397 */         } else if (d3 < this.world.paperSpigotConfig.softDespawnDistance.intValue()) {
/* 398 */           this.aU = 0;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void bn() {
/* 405 */     this.aU++;
/* 406 */     this.world.methodProfiler.a("checkDespawn");
/* 407 */     w();
/* 408 */     this.world.methodProfiler.b();
/*     */     
/* 410 */     if (this.fromMobSpawner) {
/*     */ 
/*     */       
/* 413 */       if (this.goalFloat != null) {
/* 414 */         if (this.goalFloat.a()) this.goalFloat.e(); 
/* 415 */         this.bm.b();
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 421 */     this.world.methodProfiler.a("sensing");
/* 422 */     this.bq.a();
/* 423 */     this.world.methodProfiler.b();
/* 424 */     this.world.methodProfiler.a("targetSelector");
/* 425 */     this.targetSelector.a();
/* 426 */     this.world.methodProfiler.b();
/* 427 */     this.world.methodProfiler.a("goalSelector");
/* 428 */     this.goalSelector.a();
/* 429 */     this.world.methodProfiler.b();
/* 430 */     this.world.methodProfiler.a("navigation");
/* 431 */     this.navigation.f();
/* 432 */     this.world.methodProfiler.b();
/* 433 */     this.world.methodProfiler.a("mob tick");
/* 434 */     bp();
/* 435 */     this.world.methodProfiler.b();
/* 436 */     this.world.methodProfiler.a("controls");
/* 437 */     this.world.methodProfiler.a("move");
/* 438 */     this.moveController.c();
/* 439 */     this.world.methodProfiler.c("look");
/* 440 */     this.lookController.a();
/* 441 */     this.world.methodProfiler.c("jump");
/* 442 */     this.bm.b();
/* 443 */     this.world.methodProfiler.b();
/* 444 */     this.world.methodProfiler.b();
/*     */   }
/*     */   
/*     */   protected void bq() {
/* 448 */     super.bq();
/* 449 */     this.bd = 0.0F;
/* 450 */     this.be = 0.0F;
/* 451 */     w();
/* 452 */     float f = 8.0F;
/*     */     
/* 454 */     if (this.random.nextFloat() < 0.02F) {
/* 455 */       EntityHuman entityhuman = this.world.findNearbyPlayer(this, f);
/*     */       
/* 457 */       if (entityhuman != null) {
/* 458 */         this.bu = entityhuman;
/* 459 */         this.g = 10 + this.random.nextInt(20);
/*     */       } else {
/* 461 */         this.bf = (this.random.nextFloat() - 0.5F) * 20.0F;
/*     */       } 
/*     */     } 
/*     */     
/* 465 */     if (this.bu != null) {
/* 466 */       a(this.bu, 10.0F, x());
/* 467 */       if (this.g-- <= 0 || this.bu.dead || this.bu.f(this) > (f * f)) {
/* 468 */         this.bu = null;
/*     */       }
/*     */     } else {
/* 471 */       if (this.random.nextFloat() < 0.05F) {
/* 472 */         this.bf = (this.random.nextFloat() - 0.5F) * 20.0F;
/*     */       }
/*     */       
/* 475 */       this.yaw += this.bf;
/* 476 */       this.pitch = this.f;
/*     */     } 
/*     */     
/* 479 */     boolean flag = M();
/* 480 */     boolean flag1 = P();
/*     */     
/* 482 */     if (flag || flag1) {
/* 483 */       this.bc = (this.random.nextFloat() < 0.8F);
/*     */     }
/*     */   }
/*     */   
/*     */   public int x() {
/* 488 */     return 40;
/*     */   }
/*     */   
/*     */   public void a(Entity entity, float f, float f1) {
/* 492 */     double d2, d0 = entity.locX - this.locX;
/* 493 */     double d1 = entity.locZ - this.locZ;
/*     */ 
/*     */     
/* 496 */     if (entity instanceof EntityLiving) {
/* 497 */       EntityLiving entityliving = (EntityLiving)entity;
/*     */       
/* 499 */       d2 = entityliving.locY + entityliving.getHeadHeight() - this.locY + getHeadHeight();
/*     */     } else {
/* 501 */       d2 = (entity.boundingBox.b + entity.boundingBox.e) / 2.0D - this.locY + getHeadHeight();
/*     */     } 
/*     */     
/* 504 */     double d3 = MathHelper.sqrt(d0 * d0 + d1 * d1);
/* 505 */     float f2 = (float)(Math.atan2(d1, d0) * 180.0D / 3.1415927410125732D) - 90.0F;
/* 506 */     float f3 = (float)-(Math.atan2(d2, d3) * 180.0D / 3.1415927410125732D);
/*     */     
/* 508 */     this.pitch = b(this.pitch, f3, f1);
/* 509 */     this.yaw = b(this.yaw, f2, f);
/*     */   }
/*     */   
/*     */   private float b(float f, float f1, float f2) {
/* 513 */     float f3 = MathHelper.g(f1 - f);
/*     */     
/* 515 */     if (f3 > f2) {
/* 516 */       f3 = f2;
/*     */     }
/*     */     
/* 519 */     if (f3 < -f2) {
/* 520 */       f3 = -f2;
/*     */     }
/*     */     
/* 523 */     return f + f3;
/*     */   }
/*     */   
/*     */   public boolean canSpawn() {
/* 527 */     return (this.world.b(this.boundingBox) && this.world.getCubes(this, this.boundingBox).isEmpty() && !this.world.containsLiquid(this.boundingBox));
/*     */   }
/*     */   
/*     */   public int bB() {
/* 531 */     return 4;
/*     */   }
/*     */   
/*     */   public int ax() {
/* 535 */     if (getGoalTarget() == null) {
/* 536 */       return 3;
/*     */     }
/* 538 */     int i = (int)(getHealth() - getMaxHealth() * 0.33F);
/*     */     
/* 540 */     i -= (3 - this.world.difficulty.a()) * 4;
/* 541 */     if (i < 0) {
/* 542 */       i = 0;
/*     */     }
/*     */     
/* 545 */     return i + 3;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack be() {
/* 550 */     return this.equipment[0];
/*     */   }
/*     */   
/*     */   public ItemStack getEquipment(int i) {
/* 554 */     return this.equipment[i];
/*     */   }
/*     */   
/*     */   public ItemStack r(int i) {
/* 558 */     return this.equipment[i + 1];
/*     */   }
/*     */   
/*     */   public void setEquipment(int i, ItemStack itemstack) {
/* 562 */     this.equipment[i] = itemstack;
/*     */   }
/*     */   
/*     */   public ItemStack[] getEquipment() {
/* 566 */     return this.equipment;
/*     */   }
/*     */   
/*     */   protected void dropEquipment(boolean flag, int i) {
/* 570 */     for (int j = 0; j < (getEquipment()).length; j++) {
/* 571 */       ItemStack itemstack = getEquipment(j);
/* 572 */       boolean flag1 = (this.dropChances[j] > 1.0F);
/*     */       
/* 574 */       if (itemstack != null && (flag || flag1) && this.random.nextFloat() - i * 0.01F < this.dropChances[j]) {
/* 575 */         if (!flag1 && itemstack.g()) {
/* 576 */           int k = Math.max(itemstack.l() - 25, 1);
/* 577 */           int l = itemstack.l() - this.random.nextInt(this.random.nextInt(k) + 1);
/*     */           
/* 579 */           if (l > k) {
/* 580 */             l = k;
/*     */           }
/*     */           
/* 583 */           if (l < 1) {
/* 584 */             l = 1;
/*     */           }
/*     */           
/* 587 */           itemstack.setData(l);
/*     */         } 
/*     */         
/* 590 */         a(itemstack, 0.0F);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void bC() {
/* 596 */     if (this.random.nextFloat() < 0.15F * this.world.b(this.locX, this.locY, this.locZ)) {
/* 597 */       int i = this.random.nextInt(2);
/* 598 */       float f = (this.world.difficulty == EnumDifficulty.HARD) ? 0.1F : 0.25F;
/*     */       
/* 600 */       if (this.random.nextFloat() < 0.095F) {
/* 601 */         i++;
/*     */       }
/*     */       
/* 604 */       if (this.random.nextFloat() < 0.095F) {
/* 605 */         i++;
/*     */       }
/*     */       
/* 608 */       if (this.random.nextFloat() < 0.095F) {
/* 609 */         i++;
/*     */       }
/*     */       
/* 612 */       for (int j = 3; j >= 0; j--) {
/* 613 */         ItemStack itemstack = r(j);
/*     */         
/* 615 */         if (j < 3 && this.random.nextFloat() < f) {
/*     */           break;
/*     */         }
/*     */         
/* 619 */         if (itemstack == null) {
/* 620 */           Item item = a(j + 1, i);
/*     */           
/* 622 */           if (item != null) {
/* 623 */             setEquipment(j + 1, new ItemStack(item));
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int b(ItemStack itemstack) {
/* 631 */     if (itemstack.getItem() != Item.getItemOf(Blocks.PUMPKIN) && itemstack.getItem() != Items.SKULL) {
/* 632 */       if (itemstack.getItem() instanceof ItemArmor) {
/* 633 */         switch (((ItemArmor)itemstack.getItem()).b) {
/*     */           case 0:
/* 635 */             return 4;
/*     */           
/*     */           case 1:
/* 638 */             return 3;
/*     */           
/*     */           case 2:
/* 641 */             return 2;
/*     */           
/*     */           case 3:
/* 644 */             return 1;
/*     */         } 
/*     */       
/*     */       }
/* 648 */       return 0;
/*     */     } 
/* 650 */     return 4;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Item a(int i, int j) {
/* 655 */     switch (i) {
/*     */       case 4:
/* 657 */         if (j == 0)
/* 658 */           return Items.LEATHER_HELMET; 
/* 659 */         if (j == 1)
/* 660 */           return Items.GOLD_HELMET; 
/* 661 */         if (j == 2)
/* 662 */           return Items.CHAINMAIL_HELMET; 
/* 663 */         if (j == 3)
/* 664 */           return Items.IRON_HELMET; 
/* 665 */         if (j == 4) {
/* 666 */           return Items.DIAMOND_HELMET;
/*     */         }
/*     */       
/*     */       case 3:
/* 670 */         if (j == 0)
/* 671 */           return Items.LEATHER_CHESTPLATE; 
/* 672 */         if (j == 1)
/* 673 */           return Items.GOLD_CHESTPLATE; 
/* 674 */         if (j == 2)
/* 675 */           return Items.CHAINMAIL_CHESTPLATE; 
/* 676 */         if (j == 3)
/* 677 */           return Items.IRON_CHESTPLATE; 
/* 678 */         if (j == 4) {
/* 679 */           return Items.DIAMOND_CHESTPLATE;
/*     */         }
/*     */       
/*     */       case 2:
/* 683 */         if (j == 0)
/* 684 */           return Items.LEATHER_LEGGINGS; 
/* 685 */         if (j == 1)
/* 686 */           return Items.GOLD_LEGGINGS; 
/* 687 */         if (j == 2)
/* 688 */           return Items.CHAINMAIL_LEGGINGS; 
/* 689 */         if (j == 3)
/* 690 */           return Items.IRON_LEGGINGS; 
/* 691 */         if (j == 4) {
/* 692 */           return Items.DIAMOND_LEGGINGS;
/*     */         }
/*     */       
/*     */       case 1:
/* 696 */         if (j == 0)
/* 697 */           return Items.LEATHER_BOOTS; 
/* 698 */         if (j == 1)
/* 699 */           return Items.GOLD_BOOTS; 
/* 700 */         if (j == 2)
/* 701 */           return Items.CHAINMAIL_BOOTS; 
/* 702 */         if (j == 3)
/* 703 */           return Items.IRON_BOOTS; 
/* 704 */         if (j == 4) {
/* 705 */           return Items.DIAMOND_BOOTS;
/*     */         }
/*     */         break;
/*     */     } 
/* 709 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void bD() {
/* 714 */     float f = this.world.b(this.locX, this.locY, this.locZ);
/*     */     
/* 716 */     if (be() != null && this.random.nextFloat() < 0.25F * f) {
/* 717 */       EnchantmentManager.a(this.random, be(), (int)(5.0F + f * this.random.nextInt(18)));
/*     */     }
/*     */     
/* 720 */     for (int i = 0; i < 4; i++) {
/* 721 */       ItemStack itemstack = r(i);
/*     */       
/* 723 */       if (itemstack != null && this.random.nextFloat() < 0.5F * f) {
/* 724 */         EnchantmentManager.a(this.random, itemstack, (int)(5.0F + f * this.random.nextInt(18)));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public GroupDataEntity prepare(GroupDataEntity groupdataentity) {
/* 730 */     getAttributeInstance(GenericAttributes.b).a(new AttributeModifier("Random spawn bonus", this.random.nextGaussian() * 0.05D, 1));
/* 731 */     return groupdataentity;
/*     */   }
/*     */   
/*     */   public boolean bE() {
/* 735 */     return false;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 739 */     return hasCustomName() ? getCustomName() : super.getName();
/*     */   }
/*     */   
/*     */   public void bF() {
/* 743 */     this.persistent = true;
/*     */   }
/*     */   
/*     */   public void setCustomName(String s) {
/* 747 */     this.datawatcher.watch(10, s);
/* 748 */     this.datawatcher.watch(2, s);
/*     */   }
/*     */   
/*     */   public String getCustomName() {
/* 752 */     return this.datawatcher.getString(10);
/*     */   }
/*     */   
/*     */   public boolean hasCustomName() {
/* 756 */     return (this.datawatcher.getString(10).length() > 0);
/*     */   }
/*     */   
/*     */   public void setCustomNameVisible(boolean flag) {
/* 760 */     this.datawatcher.watch(11, Byte.valueOf((byte)(flag ? 1 : 0)));
/* 761 */     this.datawatcher.watch(3, Byte.valueOf((byte)(flag ? 1 : 0)));
/*     */   }
/*     */   
/*     */   public boolean getCustomNameVisible() {
/* 765 */     return (this.datawatcher.getByte(11) == 1);
/*     */   }
/*     */   
/*     */   public void a(int i, float f) {
/* 769 */     this.dropChances[i] = f;
/*     */   }
/*     */   
/*     */   public boolean bJ() {
/* 773 */     return this.canPickUpLoot;
/*     */   }
/*     */   
/*     */   public void h(boolean flag) {
/* 777 */     this.canPickUpLoot = flag;
/*     */   }
/*     */   
/*     */   public boolean isPersistent() {
/* 781 */     return this.persistent;
/*     */   }
/*     */   
/*     */   public final boolean c(EntityHuman entityhuman) {
/* 785 */     if (bN() && getLeashHolder() == entityhuman) {
/*     */       
/* 787 */       if (CraftEventFactory.callPlayerUnleashEntityEvent(this, entityhuman).isCancelled()) {
/* 788 */         ((EntityPlayer)entityhuman).playerConnection.sendPacket(new PacketPlayOutAttachEntity(1, this, getLeashHolder()));
/* 789 */         return false;
/*     */       } 
/*     */       
/* 792 */       unleash(true, !entityhuman.abilities.canInstantlyBuild);
/* 793 */       return true;
/*     */     } 
/* 795 */     ItemStack itemstack = entityhuman.inventory.getItemInHand();
/*     */     
/* 797 */     if (itemstack != null && itemstack.getItem() == Items.LEASH && bM()) {
/* 798 */       if (!(this instanceof EntityTameableAnimal) || !((EntityTameableAnimal)this).isTamed()) {
/*     */         
/* 800 */         if (CraftEventFactory.callPlayerLeashEntityEvent(this, entityhuman, entityhuman).isCancelled()) {
/* 801 */           ((EntityPlayer)entityhuman).playerConnection.sendPacket(new PacketPlayOutAttachEntity(1, this, getLeashHolder()));
/* 802 */           return false;
/*     */         } 
/*     */         
/* 805 */         setLeashHolder(entityhuman, true);
/* 806 */         itemstack.count--;
/* 807 */         return true;
/*     */       } 
/*     */       
/* 810 */       if (((EntityTameableAnimal)this).e(entityhuman)) {
/*     */         
/* 812 */         if (CraftEventFactory.callPlayerLeashEntityEvent(this, entityhuman, entityhuman).isCancelled()) {
/* 813 */           ((EntityPlayer)entityhuman).playerConnection.sendPacket(new PacketPlayOutAttachEntity(1, this, getLeashHolder()));
/* 814 */           return false;
/*     */         } 
/*     */         
/* 817 */         setLeashHolder(entityhuman, true);
/* 818 */         itemstack.count--;
/* 819 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 823 */     return a(entityhuman) ? true : super.c(entityhuman);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean a(EntityHuman entityhuman) {
/* 828 */     return false;
/*     */   }
/*     */   
/*     */   protected void bL() {
/* 832 */     if (this.bx != null) {
/* 833 */       bP();
/*     */     }
/*     */     
/* 836 */     if (this.bv && (
/* 837 */       this.bw == null || this.bw.dead)) {
/* 838 */       this.world.getServer().getPluginManager().callEvent((Event)new EntityUnleashEvent((Entity)getBukkitEntity(), EntityUnleashEvent.UnleashReason.HOLDER_GONE));
/* 839 */       unleash(true, true);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void unleash(boolean flag, boolean flag1) {
/* 845 */     if (this.bv) {
/* 846 */       this.bv = false;
/* 847 */       this.bw = null;
/* 848 */       if (!this.world.isStatic && flag1) {
/* 849 */         a(Items.LEASH, 1);
/*     */       }
/*     */       
/* 852 */       if (!this.world.isStatic && flag && this.world instanceof WorldServer) {
/* 853 */         ((WorldServer)this.world).getTracker().a(this, new PacketPlayOutAttachEntity(1, this, (Entity)null));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean bM() {
/* 859 */     return (!bN() && !(this instanceof IMonster));
/*     */   }
/*     */   
/*     */   public boolean bN() {
/* 863 */     return this.bv;
/*     */   }
/*     */   
/*     */   public Entity getLeashHolder() {
/* 867 */     return this.bw;
/*     */   }
/*     */   
/*     */   public void setLeashHolder(Entity entity, boolean flag) {
/* 871 */     this.bv = true;
/* 872 */     this.bw = entity;
/* 873 */     if (!this.world.isStatic && flag && this.world instanceof WorldServer) {
/* 874 */       ((WorldServer)this.world).getTracker().a(this, new PacketPlayOutAttachEntity(1, this, this.bw));
/*     */     }
/*     */   }
/*     */   
/*     */   private void bP() {
/* 879 */     if (this.bv && this.bx != null) {
/* 880 */       if (this.bx.hasKeyOfType("UUIDMost", 4) && this.bx.hasKeyOfType("UUIDLeast", 4)) {
/* 881 */         UUID uuid = new UUID(this.bx.getLong("UUIDMost"), this.bx.getLong("UUIDLeast"));
/* 882 */         List list = this.world.a(EntityLiving.class, this.boundingBox.grow(10.0D, 10.0D, 10.0D));
/* 883 */         Iterator<EntityLiving> iterator = list.iterator();
/*     */         
/* 885 */         while (iterator.hasNext()) {
/* 886 */           EntityLiving entityliving = iterator.next();
/*     */           
/* 888 */           if (entityliving.getUniqueID().equals(uuid)) {
/* 889 */             this.bw = entityliving;
/*     */             break;
/*     */           } 
/*     */         } 
/* 893 */       } else if (this.bx.hasKeyOfType("X", 99) && this.bx.hasKeyOfType("Y", 99) && this.bx.hasKeyOfType("Z", 99)) {
/* 894 */         int i = this.bx.getInt("X");
/* 895 */         int j = this.bx.getInt("Y");
/* 896 */         int k = this.bx.getInt("Z");
/* 897 */         EntityLeash entityleash = EntityLeash.b(this.world, i, j, k);
/*     */         
/* 899 */         if (entityleash == null) {
/* 900 */           entityleash = EntityLeash.a(this.world, i, j, k);
/*     */         }
/*     */         
/* 903 */         this.bw = entityleash;
/*     */       } else {
/* 905 */         this.world.getServer().getPluginManager().callEvent((Event)new EntityUnleashEvent((Entity)getBukkitEntity(), EntityUnleashEvent.UnleashReason.UNKNOWN));
/* 906 */         unleash(false, true);
/*     */       } 
/*     */     }
/*     */     
/* 910 */     this.bx = null;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityInsentient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package net.minecraft.server.v1_7_R4;
/*     */ import java.util.Iterator;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*     */ import org.bukkit.entity.Item;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.player.PlayerPickupItemEvent;
/*     */ 
/*     */ public class EntityItem extends Entity {
/*  12 */   private static final Logger d = LogManager.getLogger();
/*     */   public int age;
/*     */   public int pickupDelay;
/*     */   private int e;
/*     */   private String f;
/*     */   private String g;
/*     */   public float c;
/*  19 */   private int lastTick = MinecraftServer.currentTick;
/*     */   
/*     */   public EntityItem(World world, double d0, double d1, double d2) {
/*  22 */     super(world);
/*  23 */     this.e = 5;
/*  24 */     this.c = (float)(Math.random() * Math.PI * 2.0D);
/*  25 */     a(0.25F, 0.25F);
/*  26 */     this.height = this.length / 2.0F;
/*  27 */     setPosition(d0, d1, d2);
/*  28 */     this.yaw = (float)(Math.random() * 360.0D);
/*  29 */     this.motX = (float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D);
/*  30 */     this.motY = 0.20000000298023224D;
/*  31 */     this.motZ = (float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D);
/*     */   }
/*     */   
/*     */   public EntityItem(World world, double d0, double d1, double d2, ItemStack itemstack) {
/*  35 */     this(world, d0, d1, d2);
/*     */     
/*  37 */     if (itemstack == null || itemstack.getItem() == null) {
/*     */       return;
/*     */     }
/*     */     
/*  41 */     setItemStack(itemstack);
/*     */   }
/*     */   
/*     */   protected boolean g_() {
/*  45 */     return false;
/*     */   }
/*     */   
/*     */   public EntityItem(World world) {
/*  49 */     super(world);
/*  50 */     this.e = 5;
/*  51 */     this.c = (float)(Math.random() * Math.PI * 2.0D);
/*  52 */     a(0.25F, 0.25F);
/*  53 */     this.height = this.length / 2.0F;
/*     */   }
/*     */   
/*     */   protected void c() {
/*  57 */     getDataWatcher().add(10, 5);
/*     */   }
/*     */   
/*     */   public void h() {
/*  61 */     if (getItemStack() == null) {
/*  62 */       die();
/*     */     } else {
/*  64 */       super.h();
/*     */       
/*  66 */       int elapsedTicks = MinecraftServer.currentTick - this.lastTick;
/*  67 */       this.pickupDelay -= elapsedTicks;
/*  68 */       this.age += elapsedTicks;
/*  69 */       this.lastTick = MinecraftServer.currentTick;
/*     */ 
/*     */       
/*  72 */       this.lastX = this.locX;
/*  73 */       this.lastY = this.locY;
/*  74 */       this.lastZ = this.locZ;
/*  75 */       this.motY -= 0.03999999910593033D;
/*  76 */       this.X = j(this.locX, (this.boundingBox.b + this.boundingBox.e) / 2.0D, this.locZ);
/*  77 */       move(this.motX, this.motY, this.motZ);
/*  78 */       boolean flag = ((int)this.lastX != (int)this.locX || (int)this.lastY != (int)this.locY || (int)this.lastZ != (int)this.locZ);
/*     */       
/*  80 */       if (flag || this.ticksLived % 25 == 0) {
/*  81 */         if (this.world.getType(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ)).getMaterial() == Material.LAVA) {
/*  82 */           this.motY = 0.20000000298023224D;
/*  83 */           this.motX = ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
/*  84 */           this.motZ = ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
/*  85 */           makeSound("random.fizz", 0.4F, 2.0F + this.random.nextFloat() * 0.4F);
/*     */         } 
/*     */         
/*  88 */         if (!this.world.isStatic) {
/*  89 */           k();
/*     */         }
/*     */       } 
/*     */       
/*  93 */       float f = 0.98F;
/*     */       
/*  95 */       if (this.onGround) {
/*  96 */         f = (this.world.getType(MathHelper.floor(this.locX), MathHelper.floor(this.boundingBox.b) - 1, MathHelper.floor(this.locZ))).frictionFactor * 0.98F;
/*     */       }
/*     */       
/*  99 */       this.motX *= f;
/* 100 */       this.motY *= 0.9800000190734863D;
/* 101 */       this.motZ *= f;
/* 102 */       if (this.onGround) {
/* 103 */         this.motY *= -0.5D;
/*     */       }
/*     */ 
/*     */       
/* 107 */       if (this.world.spigotConfig.altHopperTicking) {
/* 108 */         int xi = MathHelper.floor(this.boundingBox.a);
/* 109 */         int yi = MathHelper.floor(this.boundingBox.b) - 1;
/* 110 */         int zi = MathHelper.floor(this.boundingBox.c);
/* 111 */         int xf = MathHelper.floor(this.boundingBox.d);
/* 112 */         int yf = MathHelper.floor(this.boundingBox.e) - 1;
/* 113 */         int zf = MathHelper.floor(this.boundingBox.f);
/* 114 */         for (int a = xi; a <= xf; a++) {
/* 115 */           for (int c = zi; c <= zf; c++) {
/* 116 */             for (int b = yi; b <= yf; b++) {
/* 117 */               TileEntity tileEntity = this.world.getTileEntity(a, b, c);
/* 118 */               if (tileEntity instanceof TileEntityHopper) {
/* 119 */                 ((TileEntityHopper)tileEntity).makeTick();
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 128 */       if (!this.world.isStatic && this.age >= this.world.spigotConfig.itemDespawnRate) {
/*     */         
/* 130 */         if (CraftEventFactory.callItemDespawnEvent(this).isCancelled()) {
/* 131 */           this.age = 0;
/*     */           
/*     */           return;
/*     */         } 
/* 135 */         die();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void inactiveTick() {
/* 144 */     int elapsedTicks = MinecraftServer.currentTick - this.lastTick;
/* 145 */     this.pickupDelay -= elapsedTicks;
/* 146 */     this.age += elapsedTicks;
/* 147 */     this.lastTick = MinecraftServer.currentTick;
/*     */ 
/*     */     
/* 150 */     if (!this.world.isStatic && this.age >= this.world.spigotConfig.itemDespawnRate) {
/*     */       
/* 152 */       if (CraftEventFactory.callItemDespawnEvent(this).isCancelled()) {
/* 153 */         this.age = 0;
/*     */         
/*     */         return;
/*     */       } 
/* 157 */       die();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void k() {
/* 164 */     double radius = this.world.spigotConfig.itemMerge;
/* 165 */     Iterator<EntityItem> iterator = this.world.a(EntityItem.class, this.boundingBox.grow(radius, radius, radius)).iterator();
/*     */ 
/*     */     
/* 168 */     while (iterator.hasNext()) {
/* 169 */       EntityItem entityitem = iterator.next();
/*     */       
/* 171 */       a(entityitem);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean a(EntityItem entityitem) {
/* 176 */     if (entityitem == this)
/* 177 */       return false; 
/* 178 */     if (entityitem.isAlive() && isAlive()) {
/* 179 */       ItemStack itemstack = getItemStack();
/* 180 */       ItemStack itemstack1 = entityitem.getItemStack();
/*     */       
/* 182 */       if (itemstack1.getItem() != itemstack.getItem())
/* 183 */         return false; 
/* 184 */       if ((itemstack1.hasTag() ^ itemstack.hasTag()) != 0)
/* 185 */         return false; 
/* 186 */       if (itemstack1.hasTag() && !itemstack1.getTag().equals(itemstack.getTag()))
/* 187 */         return false; 
/* 188 */       if (itemstack1.getItem() == null)
/* 189 */         return false; 
/* 190 */       if (itemstack1.getItem().n() && itemstack1.getData() != itemstack.getData())
/* 191 */         return false; 
/* 192 */       if (itemstack1.count < itemstack.count)
/* 193 */         return entityitem.a(this); 
/* 194 */       if (itemstack1.count + itemstack.count > itemstack1.getMaxStackSize()) {
/* 195 */         return false;
/*     */       }
/*     */       
/* 198 */       itemstack.count += itemstack1.count;
/* 199 */       this.pickupDelay = Math.max(entityitem.pickupDelay, this.pickupDelay);
/* 200 */       this.age = Math.min(entityitem.age, this.age);
/* 201 */       setItemStack(itemstack);
/* 202 */       entityitem.die();
/*     */       
/* 204 */       return true;
/*     */     } 
/*     */     
/* 207 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void e() {
/* 212 */     this.age = 4800;
/*     */   }
/*     */   
/*     */   public boolean N() {
/* 216 */     return this.world.a(this.boundingBox, Material.WATER, this);
/*     */   }
/*     */   
/*     */   protected void burn(int i) {
/* 220 */     damageEntity(DamageSource.FIRE, i);
/*     */   }
/*     */   
/*     */   public boolean damageEntity(DamageSource damagesource, float f) {
/* 224 */     if (isInvulnerable())
/* 225 */       return false; 
/* 226 */     if (getItemStack() != null && getItemStack().getItem() == Items.NETHER_STAR && damagesource.isExplosion()) {
/* 227 */       return false;
/*     */     }
/* 229 */     Q();
/* 230 */     this.e = (int)(this.e - f);
/* 231 */     if (this.e <= 0) {
/* 232 */       die();
/*     */     }
/*     */     
/* 235 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/* 240 */     nbttagcompound.setShort("Health", (short)(byte)this.e);
/* 241 */     nbttagcompound.setShort("Age", (short)this.age);
/* 242 */     if (j() != null) {
/* 243 */       nbttagcompound.setString("Thrower", this.f);
/*     */     }
/*     */     
/* 246 */     if (i() != null) {
/* 247 */       nbttagcompound.setString("Owner", this.g);
/*     */     }
/*     */     
/* 250 */     if (getItemStack() != null) {
/* 251 */       nbttagcompound.set("Item", getItemStack().save(new NBTTagCompound()));
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/* 256 */     this.e = nbttagcompound.getShort("Health") & 0xFF;
/* 257 */     this.age = nbttagcompound.getShort("Age");
/* 258 */     if (nbttagcompound.hasKey("Owner")) {
/* 259 */       this.g = nbttagcompound.getString("Owner");
/*     */     }
/*     */     
/* 262 */     if (nbttagcompound.hasKey("Thrower")) {
/* 263 */       this.f = nbttagcompound.getString("Thrower");
/*     */     }
/*     */     
/* 266 */     NBTTagCompound nbttagcompound1 = nbttagcompound.getCompound("Item");
/*     */ 
/*     */     
/* 269 */     if (nbttagcompound1 != null) {
/* 270 */       ItemStack itemstack = ItemStack.createStack(nbttagcompound1);
/* 271 */       if (itemstack != null) {
/* 272 */         setItemStack(itemstack);
/*     */       } else {
/* 274 */         die();
/*     */       } 
/*     */     } else {
/* 277 */       die();
/*     */     } 
/*     */     
/* 280 */     if (getItemStack() == null) {
/* 281 */       die();
/*     */     }
/*     */   }
/*     */   
/*     */   public void b_(EntityHuman entityhuman) {
/* 286 */     if (!this.world.isStatic) {
/* 287 */       ItemStack itemstack = getItemStack();
/* 288 */       int i = itemstack.count;
/*     */ 
/*     */       
/* 291 */       int canHold = entityhuman.inventory.canHold(itemstack);
/* 292 */       int remaining = itemstack.count - canHold;
/*     */       
/* 294 */       if (this.pickupDelay <= 0 && canHold > 0) {
/* 295 */         itemstack.count = canHold;
/* 296 */         PlayerPickupItemEvent event = new PlayerPickupItemEvent((Player)entityhuman.getBukkitEntity(), (Item)getBukkitEntity(), remaining);
/*     */         
/* 298 */         this.world.getServer().getPluginManager().callEvent((Event)event);
/* 299 */         itemstack.count = canHold + remaining;
/*     */         
/* 301 */         if (event.isCancelled()) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/* 306 */         this.pickupDelay = 0;
/*     */       } 
/*     */ 
/*     */       
/* 310 */       if (this.pickupDelay == 0 && (this.g == null || 6000 - this.age <= 200 || this.g.equals(entityhuman.getName())) && entityhuman.inventory.pickup(itemstack)) {
/* 311 */         if (itemstack.getItem() == Item.getItemOf(Blocks.LOG)) {
/* 312 */           entityhuman.a(AchievementList.g);
/*     */         }
/*     */         
/* 315 */         if (itemstack.getItem() == Item.getItemOf(Blocks.LOG2)) {
/* 316 */           entityhuman.a(AchievementList.g);
/*     */         }
/*     */         
/* 319 */         if (itemstack.getItem() == Items.LEATHER) {
/* 320 */           entityhuman.a(AchievementList.t);
/*     */         }
/*     */         
/* 323 */         if (itemstack.getItem() == Items.DIAMOND) {
/* 324 */           entityhuman.a(AchievementList.w);
/*     */         }
/*     */         
/* 327 */         if (itemstack.getItem() == Items.BLAZE_ROD) {
/* 328 */           entityhuman.a(AchievementList.A);
/*     */         }
/*     */         
/* 331 */         if (itemstack.getItem() == Items.DIAMOND && j() != null) {
/* 332 */           EntityHuman entityhuman1 = this.world.a(j());
/*     */           
/* 334 */           if (entityhuman1 != null && entityhuman1 != entityhuman) {
/* 335 */             entityhuman1.a(AchievementList.x);
/*     */           }
/*     */         } 
/*     */         
/* 339 */         this.world.makeSound(entityhuman, "random.pop", 0.2F, ((this.random.nextFloat() - this.random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
/* 340 */         entityhuman.receive(this, i);
/* 341 */         if (itemstack.count <= 0) {
/* 342 */           die();
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getName() {
/* 349 */     return LocaleI18n.get("item." + getItemStack().a());
/*     */   }
/*     */   
/*     */   public boolean av() {
/* 353 */     return false;
/*     */   }
/*     */   
/*     */   public void b(int i) {
/* 357 */     super.b(i);
/* 358 */     if (!this.world.isStatic) {
/* 359 */       k();
/*     */     }
/*     */   }
/*     */   
/*     */   public ItemStack getItemStack() {
/* 364 */     ItemStack itemstack = getDataWatcher().getItemStack(10);
/*     */     
/* 366 */     return (itemstack == null) ? new ItemStack(Blocks.STONE) : itemstack;
/*     */   }
/*     */   
/*     */   public void setItemStack(ItemStack itemstack) {
/* 370 */     getDataWatcher().watch(10, itemstack);
/* 371 */     getDataWatcher().update(10);
/*     */   }
/*     */   
/*     */   public String i() {
/* 375 */     return this.g;
/*     */   }
/*     */   
/*     */   public void a(String s) {
/* 379 */     this.g = s;
/*     */   }
/*     */   
/*     */   public String j() {
/* 383 */     return this.f;
/*     */   }
/*     */   
/*     */   public void b(String s) {
/* 387 */     this.f = s;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
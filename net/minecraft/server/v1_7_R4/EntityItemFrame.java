/*     */ package net.minecraft.server.v1_7_R4;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*     */ 
/*     */ public class EntityItemFrame extends EntityHanging {
/*   5 */   private float e = 1.0F;
/*     */   
/*     */   public EntityItemFrame(World world) {
/*   8 */     super(world);
/*     */   }
/*     */   
/*     */   public EntityItemFrame(World world, int i, int j, int k, int l) {
/*  12 */     super(world, i, j, k, l);
/*  13 */     setDirection(l);
/*     */   }
/*     */   
/*     */   protected void c() {
/*  17 */     getDataWatcher().add(2, 5);
/*  18 */     getDataWatcher().a(3, Byte.valueOf((byte)0));
/*     */     
/*  20 */     getDataWatcher().add(8, 5);
/*  21 */     getDataWatcher().a(9, Byte.valueOf((byte)0));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean damageEntity(DamageSource damagesource, float f) {
/*  26 */     if (isInvulnerable())
/*  27 */       return false; 
/*  28 */     if (getItem() != null) {
/*  29 */       if (!this.world.isStatic) {
/*     */         
/*  31 */         if (CraftEventFactory.handleNonLivingEntityDamageEvent(this, damagesource, f, false) || this.dead) {
/*  32 */           return true;
/*     */         }
/*     */ 
/*     */         
/*  36 */         b(damagesource.getEntity(), false);
/*  37 */         setItem((ItemStack)null);
/*     */       } 
/*     */       
/*  40 */       return true;
/*     */     } 
/*  42 */     return super.damageEntity(damagesource, f);
/*     */   }
/*     */ 
/*     */   
/*     */   public int f() {
/*  47 */     return 9;
/*     */   }
/*     */   
/*     */   public int i() {
/*  51 */     return 9;
/*     */   }
/*     */   
/*     */   public void b(Entity entity) {
/*  55 */     b(entity, true);
/*     */   }
/*     */   
/*     */   public void b(Entity entity, boolean flag) {
/*  59 */     ItemStack itemstack = getItem();
/*     */     
/*  61 */     if (entity instanceof EntityHuman) {
/*  62 */       EntityHuman entityhuman = (EntityHuman)entity;
/*     */       
/*  64 */       if (entityhuman.abilities.canInstantlyBuild) {
/*  65 */         b(itemstack);
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*  70 */     if (flag) {
/*  71 */       a(new ItemStack(Items.ITEM_FRAME), 0.0F);
/*     */     }
/*     */     
/*  74 */     if (itemstack != null && this.random.nextFloat() < this.e) {
/*  75 */       itemstack = itemstack.cloneItemStack();
/*  76 */       b(itemstack);
/*  77 */       a(itemstack, 0.0F);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void b(ItemStack itemstack) {
/*  82 */     if (itemstack != null) {
/*  83 */       if (itemstack.getItem() == Items.MAP) {
/*  84 */         WorldMap worldmap = ((ItemWorldMap)itemstack.getItem()).getSavedMap(itemstack, this.world);
/*     */         
/*  86 */         worldmap.decorations.remove("frame-" + getId());
/*     */       } 
/*     */       
/*  89 */       itemstack.a((EntityItemFrame)null);
/*     */     } 
/*     */   }
/*     */   
/*     */   public ItemStack getItem() {
/*  94 */     return getDataWatcher().getItemStack(2);
/*     */   }
/*     */   
/*     */   public void setItem(ItemStack itemstack) {
/*  98 */     if (itemstack != null) {
/*  99 */       itemstack = itemstack.cloneItemStack();
/* 100 */       itemstack.count = 1;
/* 101 */       itemstack.a(this);
/*     */     } 
/*     */     
/* 104 */     getDataWatcher().watch(2, itemstack);
/* 105 */     getDataWatcher().update(2);
/*     */     
/* 107 */     getDataWatcher().watch(8, itemstack);
/* 108 */     getDataWatcher().update(8);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRotation() {
/* 113 */     return getDataWatcher().getByte(3);
/*     */   }
/*     */   
/*     */   public void setRotation(int i) {
/* 117 */     getDataWatcher().watch(3, Byte.valueOf((byte)(i % 4)));
/* 118 */     getDataWatcher().watch(9, Byte.valueOf((byte)(i % 4 * 2)));
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/* 122 */     if (getItem() != null) {
/* 123 */       nbttagcompound.set("Item", getItem().save(new NBTTagCompound()));
/* 124 */       nbttagcompound.setByte("ItemRotation", (byte)getRotation());
/* 125 */       nbttagcompound.setFloat("ItemDropChance", this.e);
/*     */     } 
/*     */     
/* 128 */     super.b(nbttagcompound);
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/* 132 */     NBTTagCompound nbttagcompound1 = nbttagcompound.getCompound("Item");
/*     */     
/* 134 */     if (nbttagcompound1 != null && !nbttagcompound1.isEmpty()) {
/* 135 */       setItem(ItemStack.createStack(nbttagcompound1));
/* 136 */       setRotation(nbttagcompound.getByte("ItemRotation"));
/* 137 */       if (nbttagcompound.hasKeyOfType("ItemDropChance", 99)) {
/* 138 */         this.e = nbttagcompound.getFloat("ItemDropChance");
/*     */       }
/*     */     } 
/*     */     
/* 142 */     super.a(nbttagcompound);
/*     */   }
/*     */   
/*     */   public boolean c(EntityHuman entityhuman) {
/* 146 */     if (getItem() == null) {
/* 147 */       ItemStack itemstack = entityhuman.be();
/*     */       
/* 149 */       if (itemstack != null && !this.world.isStatic) {
/* 150 */         setItem(itemstack);
/* 151 */         if (!entityhuman.abilities.canInstantlyBuild && --itemstack.count <= 0) {
/* 152 */           entityhuman.inventory.setItem(entityhuman.inventory.itemInHandIndex, (ItemStack)null);
/*     */         }
/*     */       } 
/* 155 */     } else if (!this.world.isStatic) {
/* 156 */       setRotation(getRotation() + 1);
/*     */     } 
/*     */     
/* 159 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityItemFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
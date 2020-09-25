/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.entity.CraftLivingEntity;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*     */ import org.bukkit.entity.LivingEntity;
/*     */ import org.bukkit.event.entity.PotionSplashEvent;
/*     */ 
/*     */ 
/*     */ public class EntityPotion
/*     */   extends EntityProjectile
/*     */ {
/*     */   public ItemStack item;
/*     */   
/*     */   public EntityPotion(World world) {
/*  18 */     super(world);
/*     */   }
/*     */   
/*     */   public EntityPotion(World world, EntityLiving entityliving, int i) {
/*  22 */     this(world, entityliving, new ItemStack(Items.POTION, 1, i));
/*     */   }
/*     */   
/*     */   public EntityPotion(World world, EntityLiving entityliving, ItemStack itemstack) {
/*  26 */     super(world, entityliving);
/*  27 */     this.item = itemstack;
/*     */   }
/*     */   
/*     */   public EntityPotion(World world, double d0, double d1, double d2, ItemStack itemstack) {
/*  31 */     super(world, d0, d1, d2);
/*  32 */     this.item = itemstack;
/*     */   }
/*     */   
/*     */   protected float i() {
/*  36 */     return 0.05F;
/*     */   }
/*     */   
/*     */   protected float e() {
/*  40 */     return 0.5F;
/*     */   }
/*     */   
/*     */   protected float f() {
/*  44 */     return -20.0F;
/*     */   }
/*     */   
/*     */   public void setPotionValue(int i) {
/*  48 */     if (this.item == null) {
/*  49 */       this.item = new ItemStack(Items.POTION, 1, 0);
/*     */     }
/*     */     
/*  52 */     this.item.setData(i);
/*     */   }
/*     */   
/*     */   public int getPotionValue() {
/*  56 */     if (this.item == null) {
/*  57 */       this.item = new ItemStack(Items.POTION, 1, 0);
/*     */     }
/*     */     
/*  60 */     return this.item.getData();
/*     */   }
/*     */   
/*     */   protected void a(MovingObjectPosition movingobjectposition) {
/*  64 */     if (!this.world.isStatic) {
/*  65 */       List list = Items.POTION.g(this.item);
/*     */ 
/*     */       
/*  68 */       AxisAlignedBB axisalignedbb = this.boundingBox.grow(4.0D, 2.0D, 4.0D);
/*  69 */       List list1 = this.world.a(EntityLiving.class, axisalignedbb);
/*     */       
/*  71 */       if (list1 != null) {
/*  72 */         Iterator<EntityLiving> iterator = list1.iterator();
/*     */ 
/*     */         
/*  75 */         HashMap<LivingEntity, Double> affected = new HashMap<LivingEntity, Double>();
/*     */         
/*  77 */         while (iterator.hasNext()) {
/*  78 */           EntityLiving entityliving = iterator.next();
/*  79 */           double d0 = f(entityliving);
/*     */           
/*  81 */           if (d0 < 16.0D) {
/*  82 */             double d1 = 1.0D - Math.sqrt(d0) / 4.0D;
/*     */             
/*  84 */             if (entityliving == movingobjectposition.entity) {
/*  85 */               d1 = 1.0D;
/*     */             }
/*     */ 
/*     */             
/*  89 */             affected.put((LivingEntity)entityliving.getBukkitEntity(), Double.valueOf(d1));
/*     */           } 
/*     */         } 
/*     */         
/*  93 */         PotionSplashEvent event = CraftEventFactory.callPotionSplashEvent(this, affected);
/*  94 */         if (!event.isCancelled() && list != null && !list.isEmpty()) {
/*  95 */           for (LivingEntity victim : event.getAffectedEntities()) {
/*  96 */             if (!(victim instanceof CraftLivingEntity)) {
/*     */               continue;
/*     */             }
/*     */             
/* 100 */             EntityLiving entityliving = ((CraftLivingEntity)victim).getHandle();
/* 101 */             double d1 = event.getIntensity(victim);
/*     */ 
/*     */             
/* 104 */             Iterator<MobEffect> iterator1 = list.iterator();
/*     */             
/* 106 */             while (iterator1.hasNext()) {
/* 107 */               MobEffect mobeffect = iterator1.next();
/* 108 */               int i = mobeffect.getEffectId();
/*     */ 
/*     */               
/* 111 */               if (!this.world.pvpMode && getShooter() instanceof EntityPlayer && entityliving instanceof EntityPlayer && entityliving != getShooter())
/*     */               {
/* 113 */                 if (i == 2 || i == 4 || i == 7 || i == 15 || i == 17 || i == 18 || i == 19) {
/*     */                   continue;
/*     */                 }
/*     */               }
/* 117 */               if (MobEffectList.byId[i].isInstant()) {
/*     */                 
/* 119 */                 MobEffectList.byId[i].applyInstantEffect(getShooter(), entityliving, mobeffect.getAmplifier(), d1, this); continue;
/*     */               } 
/* 121 */               int j = (int)(d1 * mobeffect.getDuration() + 0.5D);
/*     */               
/* 123 */               if (j > 20) {
/* 124 */                 entityliving.addEffect(new MobEffect(i, j, mobeffect.getAmplifier()));
/*     */               }
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 133 */       this.world.triggerEffect(2002, (int)Math.round(this.locX), (int)Math.round(this.locY), (int)Math.round(this.locZ), getPotionValue());
/* 134 */       die();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/* 139 */     super.a(nbttagcompound);
/* 140 */     if (nbttagcompound.hasKeyOfType("Potion", 10)) {
/* 141 */       this.item = ItemStack.createStack(nbttagcompound.getCompound("Potion"));
/*     */     } else {
/* 143 */       setPotionValue(nbttagcompound.getInt("potionValue"));
/*     */     } 
/*     */     
/* 146 */     if (this.item == null) {
/* 147 */       die();
/*     */     }
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/* 152 */     super.b(nbttagcompound);
/* 153 */     if (this.item != null)
/* 154 */       nbttagcompound.set("Potion", this.item.save(new NBTTagCompound())); 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityPotion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
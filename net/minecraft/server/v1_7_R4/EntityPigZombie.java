/*     */ package net.minecraft.server.v1_7_R4;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.entity.EntityTargetEvent;
/*     */ 
/*     */ public class EntityPigZombie extends EntityZombie {
/*  10 */   private static final UUID bq = UUID.fromString("49455A49-7EC5-45BA-B886-3B90B23A1718");
/*  11 */   private static final AttributeModifier br = (new AttributeModifier(bq, "Attacking speed boost", 0.45D, 0)).a(false);
/*     */   public int angerLevel;
/*     */   private int soundDelay;
/*     */   private Entity bu;
/*     */   
/*     */   public EntityPigZombie(World world) {
/*  17 */     super(world);
/*  18 */     this.fireProof = true;
/*     */   }
/*     */   
/*     */   protected void aD() {
/*  22 */     super.aD();
/*  23 */     getAttributeInstance(bp).setValue(0.0D);
/*  24 */     getAttributeInstance(GenericAttributes.d).setValue(0.5D);
/*  25 */     getAttributeInstance(GenericAttributes.e).setValue(5.0D);
/*     */   }
/*     */   
/*     */   protected boolean bk() {
/*  29 */     return false;
/*     */   }
/*     */   
/*     */   public void h() {
/*  33 */     if (this.bu != this.target && !this.world.isStatic) {
/*  34 */       AttributeInstance attributeinstance = getAttributeInstance(GenericAttributes.d);
/*     */       
/*  36 */       attributeinstance.b(br);
/*  37 */       if (this.target != null) {
/*  38 */         attributeinstance.a(br);
/*     */       }
/*     */     } 
/*     */     
/*  42 */     this.bu = this.target;
/*  43 */     if (this.soundDelay > 0 && --this.soundDelay == 0) {
/*  44 */       makeSound("mob.zombiepig.zpigangry", bf() * 2.0F, ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) * 1.8F);
/*     */     }
/*     */     
/*  47 */     super.h();
/*     */   }
/*     */   
/*     */   public boolean canSpawn() {
/*  51 */     return (this.world.difficulty != EnumDifficulty.PEACEFUL && this.world.b(this.boundingBox) && this.world.getCubes(this, this.boundingBox).isEmpty() && !this.world.containsLiquid(this.boundingBox));
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/*  55 */     super.b(nbttagcompound);
/*  56 */     nbttagcompound.setShort("Anger", (short)this.angerLevel);
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/*  60 */     super.a(nbttagcompound);
/*  61 */     this.angerLevel = nbttagcompound.getShort("Anger");
/*     */   }
/*     */   
/*     */   protected Entity findTarget() {
/*  65 */     return (this.angerLevel == 0) ? null : super.findTarget();
/*     */   }
/*     */   
/*     */   public boolean damageEntity(DamageSource damagesource, float f) {
/*  69 */     if (isInvulnerable()) {
/*  70 */       return false;
/*     */     }
/*  72 */     Entity entity = damagesource.getEntity();
/*     */     
/*  74 */     if (entity instanceof EntityHuman) {
/*  75 */       List<Entity> list = this.world.getEntities(this, this.boundingBox.grow(32.0D, 32.0D, 32.0D));
/*     */       
/*  77 */       for (int i = 0; i < list.size(); i++) {
/*  78 */         Entity entity1 = list.get(i);
/*     */         
/*  80 */         if (entity1 instanceof EntityPigZombie) {
/*  81 */           EntityPigZombie entitypigzombie = (EntityPigZombie)entity1;
/*     */           
/*  83 */           entitypigzombie.c(entity, EntityTargetEvent.TargetReason.PIG_ZOMBIE_TARGET);
/*     */         } 
/*     */       } 
/*     */       
/*  87 */       c(entity, EntityTargetEvent.TargetReason.TARGET_ATTACKED_ENTITY);
/*     */     } 
/*     */     
/*  90 */     return super.damageEntity(damagesource, f);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void c(Entity entity, EntityTargetEvent.TargetReason reason) {
/*  96 */     EntityTargetEvent event = new EntityTargetEvent((Entity)getBukkitEntity(), (Entity)entity.getBukkitEntity(), reason);
/*  97 */     this.world.getServer().getPluginManager().callEvent((Event)event);
/*     */     
/*  99 */     if (event.isCancelled()) {
/*     */       return;
/*     */     }
/*     */     
/* 103 */     if (event.getTarget() == null) {
/* 104 */       this.target = null;
/*     */       return;
/*     */     } 
/* 107 */     entity = ((CraftEntity)event.getTarget()).getHandle();
/*     */ 
/*     */     
/* 110 */     this.target = entity;
/* 111 */     this.angerLevel = 400 + this.random.nextInt(400);
/* 112 */     this.soundDelay = this.random.nextInt(40);
/*     */   }
/*     */   
/*     */   protected String t() {
/* 116 */     return "mob.zombiepig.zpig";
/*     */   }
/*     */   
/*     */   protected String aT() {
/* 120 */     return "mob.zombiepig.zpighurt";
/*     */   }
/*     */   
/*     */   protected String aU() {
/* 124 */     return "mob.zombiepig.zpigdeath";
/*     */   }
/*     */   
/*     */   protected void dropDeathLoot(boolean flag, int i) {
/* 128 */     int j = this.random.nextInt(2 + i);
/*     */     
/*     */     int k;
/*     */     
/* 132 */     for (k = 0; k < j; k++) {
/* 133 */       a(Items.ROTTEN_FLESH, 1);
/*     */     }
/*     */     
/* 136 */     j = this.random.nextInt(2 + i);
/*     */     
/* 138 */     for (k = 0; k < j; k++) {
/* 139 */       a(Items.GOLD_NUGGET, 1);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean a(EntityHuman entityhuman) {
/* 144 */     return false;
/*     */   }
/*     */   
/*     */   protected void getRareDrop(int i) {
/* 148 */     a(Items.GOLD_INGOT, 1);
/*     */   }
/*     */   
/*     */   protected void bC() {
/* 152 */     setEquipment(0, new ItemStack(Items.GOLD_SWORD));
/*     */   }
/*     */   
/*     */   public GroupDataEntity prepare(GroupDataEntity groupdataentity) {
/* 156 */     super.prepare(groupdataentity);
/* 157 */     setVillager(false);
/* 158 */     return groupdataentity;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityPigZombie.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
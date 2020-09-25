/*     */ package net.minecraft.server.v1_7_R4;
/*     */ import org.spigotmc.ProtocolData;
/*     */ 
/*     */ public abstract class EntityAgeable extends EntityCreature {
/*   5 */   private float bp = -1.0F;
/*     */   
/*     */   private float bq;
/*     */   
/*     */   public boolean ageLocked = false;
/*     */ 
/*     */   
/*     */   public void inactiveTick() {
/*  13 */     super.inactiveTick();
/*  14 */     if (this.world.isStatic || this.ageLocked) {
/*     */       
/*  16 */       a(isBaby());
/*     */     } else {
/*     */       
/*  19 */       int i = getAge();
/*     */       
/*  21 */       if (i < 0) {
/*     */         
/*  23 */         i++;
/*  24 */         setAge(i);
/*  25 */       } else if (i > 0) {
/*     */         
/*  27 */         i--;
/*  28 */         setAge(i);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityAgeable(World world) {
/*  35 */     super(world);
/*     */   }
/*     */   
/*     */   public abstract EntityAgeable createChild(EntityAgeable paramEntityAgeable);
/*     */   
/*     */   public boolean a(EntityHuman entityhuman) {
/*  41 */     ItemStack itemstack = entityhuman.inventory.getItemInHand();
/*     */     
/*  43 */     if (itemstack != null && itemstack.getItem() == Items.MONSTER_EGG) {
/*  44 */       if (!this.world.isStatic) {
/*  45 */         Class oclass = EntityTypes.a(itemstack.getData());
/*     */         
/*  47 */         if (oclass != null && oclass.isAssignableFrom(getClass())) {
/*  48 */           EntityAgeable entityageable = createChild(this);
/*     */           
/*  50 */           if (entityageable != null) {
/*  51 */             entityageable.setAge(-24000);
/*  52 */             entityageable.setPositionRotation(this.locX, this.locY, this.locZ, 0.0F, 0.0F);
/*  53 */             this.world.addEntity(entityageable, CreatureSpawnEvent.SpawnReason.SPAWNER_EGG);
/*  54 */             if (itemstack.hasName()) {
/*  55 */               entityageable.setCustomName(itemstack.getName());
/*     */             }
/*     */             
/*  58 */             if (!entityhuman.abilities.canInstantlyBuild) {
/*  59 */               itemstack.count--;
/*  60 */               if (itemstack.count == 0) {
/*  61 */                 entityhuman.inventory.setItem(entityhuman.inventory.itemInHandIndex, (ItemStack)null);
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  68 */       return true;
/*     */     } 
/*  70 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void c() {
/*  75 */     super.c();
/*  76 */     this.datawatcher.a(12, new ProtocolData.IntByte(0, (byte)0));
/*     */   }
/*     */   
/*     */   public int getAge() {
/*  80 */     return (this.datawatcher.getIntByte(12)).value;
/*     */   }
/*     */   
/*     */   public void a(int i) {
/*  84 */     int j = getAge();
/*     */     
/*  86 */     j += i * 20;
/*  87 */     if (j > 0) {
/*  88 */       j = 0;
/*     */     }
/*     */     
/*  91 */     setAge(j);
/*     */   }
/*     */   
/*     */   public void setAge(int i) {
/*  95 */     this.datawatcher.watch(12, new ProtocolData.IntByte(i, (byte)((i < 0) ? -1 : ((i >= 6000) ? 1 : 0))));
/*  96 */     a(isBaby());
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/* 100 */     super.b(nbttagcompound);
/* 101 */     nbttagcompound.setInt("Age", getAge());
/* 102 */     nbttagcompound.setBoolean("AgeLocked", this.ageLocked);
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/* 106 */     super.a(nbttagcompound);
/* 107 */     setAge(nbttagcompound.getInt("Age"));
/* 108 */     this.ageLocked = nbttagcompound.getBoolean("AgeLocked");
/*     */   }
/*     */   
/*     */   public void e() {
/* 112 */     super.e();
/* 113 */     if (this.world.isStatic || this.ageLocked) {
/* 114 */       a(isBaby());
/*     */     } else {
/* 116 */       int i = getAge();
/*     */       
/* 118 */       if (i < 0) {
/* 119 */         i++;
/* 120 */         setAge(i);
/* 121 */       } else if (i > 0) {
/* 122 */         i--;
/* 123 */         setAge(i);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isBaby() {
/* 129 */     return (getAge() < 0);
/*     */   }
/*     */   
/*     */   public void a(boolean flag) {
/* 133 */     a(flag ? 0.5F : 1.0F);
/*     */   }
/*     */   
/*     */   protected final void a(float f, float f1) {
/* 137 */     boolean flag = (this.bp > 0.0F);
/*     */     
/* 139 */     this.bp = f;
/* 140 */     this.bq = f1;
/* 141 */     if (!flag) {
/* 142 */       a(1.0F);
/*     */     }
/*     */   }
/*     */   
/*     */   protected final void a(float f) {
/* 147 */     super.a(this.bp * f, this.bq * f);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityAgeable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
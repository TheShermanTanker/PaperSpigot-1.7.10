/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.List;
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
/*     */ public abstract class EntityAnimal
/*     */   extends EntityAgeable
/*     */   implements IAnimal
/*     */ {
/*     */   private int love;
/*     */   private int bq;
/*     */   private EntityHuman br;
/*     */   
/*     */   public EntityAnimal(World paramWorld) {
/*  26 */     super(paramWorld);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void bp() {
/*  31 */     if (getAge() != 0) this.love = 0; 
/*  32 */     super.bp();
/*     */   }
/*     */ 
/*     */   
/*     */   public void e() {
/*  37 */     super.e();
/*     */     
/*  39 */     if (getAge() != 0) this.love = 0;
/*     */     
/*  41 */     if (this.love > 0) {
/*  42 */       this.love--;
/*  43 */       String str = "heart";
/*  44 */       if (this.love % 10 == 0) {
/*  45 */         double d1 = this.random.nextGaussian() * 0.02D;
/*  46 */         double d2 = this.random.nextGaussian() * 0.02D;
/*  47 */         double d3 = this.random.nextGaussian() * 0.02D;
/*  48 */         this.world.addParticle(str, this.locX + (this.random.nextFloat() * this.width * 2.0F) - this.width, this.locY + 0.5D + (this.random.nextFloat() * this.length), this.locZ + (this.random.nextFloat() * this.width * 2.0F) - this.width, d1, d2, d3);
/*     */       } 
/*     */     } else {
/*  51 */       this.bq = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void a(Entity paramEntity, float paramFloat) {
/*  57 */     if (paramEntity instanceof EntityHuman) {
/*  58 */       if (paramFloat < 3.0F) {
/*  59 */         double d1 = paramEntity.locX - this.locX;
/*  60 */         double d2 = paramEntity.locZ - this.locZ;
/*  61 */         this.yaw = (float)(Math.atan2(d2, d1) * 180.0D / 3.1415927410125732D) - 90.0F;
/*     */         
/*  63 */         this.bn = true;
/*     */       } 
/*     */       
/*  66 */       EntityHuman entityHuman = (EntityHuman)paramEntity;
/*  67 */       if (entityHuman.bF() == null || !c(entityHuman.bF())) {
/*  68 */         this.target = null;
/*     */       }
/*  70 */     } else if (paramEntity instanceof EntityAnimal) {
/*  71 */       EntityAnimal entityAnimal = (EntityAnimal)paramEntity;
/*  72 */       if (getAge() > 0 && entityAnimal.getAge() < 0)
/*  73 */       { if (paramFloat < 2.5D) {
/*  74 */           this.bn = true;
/*     */         } }
/*  76 */       else if (this.love > 0 && entityAnimal.love > 0)
/*  77 */       { if (entityAnimal.target == null) entityAnimal.target = this;
/*     */         
/*  79 */         if (entityAnimal.target == this && paramFloat < 3.5D)
/*  80 */         { entityAnimal.love++;
/*  81 */           this.love++;
/*  82 */           this.bq++;
/*  83 */           if (this.bq % 4 == 0) {
/*  84 */             this.world.addParticle("heart", this.locX + (this.random.nextFloat() * this.width * 2.0F) - this.width, this.locY + 0.5D + (this.random.nextFloat() * this.length), this.locZ + (this.random.nextFloat() * this.width * 2.0F) - this.width, 0.0D, 0.0D, 0.0D);
/*     */           }
/*     */           
/*  87 */           if (this.bq == 60) b((EntityAnimal)paramEntity);  }
/*  88 */         else { this.bq = 0; }
/*     */          }
/*  90 */       else { this.bq = 0;
/*  91 */         this.target = null; }
/*     */     
/*     */     } 
/*     */   }
/*     */   
/*     */   private void b(EntityAnimal paramEntityAnimal) {
/*  97 */     EntityAgeable entityAgeable = createChild(paramEntityAnimal);
/*  98 */     if (entityAgeable != null) {
/*  99 */       if (this.br == null && paramEntityAnimal.cd() != null) {
/* 100 */         this.br = paramEntityAnimal.cd();
/*     */       }
/*     */       
/* 103 */       if (this.br != null) {
/* 104 */         this.br.a(StatisticList.x);
/*     */         
/* 106 */         if (this instanceof EntityCow) {
/* 107 */           this.br.a(AchievementList.H);
/*     */         }
/*     */       } 
/*     */       
/* 111 */       setAge(6000);
/* 112 */       paramEntityAnimal.setAge(6000);
/* 113 */       this.love = 0;
/* 114 */       this.bq = 0;
/* 115 */       this.target = null;
/* 116 */       paramEntityAnimal.target = null;
/* 117 */       paramEntityAnimal.bq = 0;
/* 118 */       paramEntityAnimal.love = 0;
/* 119 */       entityAgeable.setAge(-24000);
/* 120 */       entityAgeable.setPositionRotation(this.locX, this.locY, this.locZ, this.yaw, this.pitch);
/* 121 */       for (byte b = 0; b < 7; b++) {
/* 122 */         double d1 = this.random.nextGaussian() * 0.02D;
/* 123 */         double d2 = this.random.nextGaussian() * 0.02D;
/* 124 */         double d3 = this.random.nextGaussian() * 0.02D;
/* 125 */         this.world.addParticle("heart", this.locX + (this.random.nextFloat() * this.width * 2.0F) - this.width, this.locY + 0.5D + (this.random.nextFloat() * this.length), this.locZ + (this.random.nextFloat() * this.width * 2.0F) - this.width, d1, d2, d3);
/*     */       } 
/* 127 */       this.world.addEntity(entityAgeable);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean damageEntity(DamageSource paramDamageSource, float paramFloat) {
/* 133 */     if (isInvulnerable()) return false; 
/* 134 */     this.bo = 60;
/*     */     
/* 136 */     if (!bk()) {
/* 137 */       AttributeInstance attributeInstance = getAttributeInstance(GenericAttributes.d);
/* 138 */       if (attributeInstance.a(h) == null) {
/* 139 */         attributeInstance.a(i);
/*     */       }
/*     */     } 
/*     */     
/* 143 */     this.target = null;
/* 144 */     this.love = 0;
/* 145 */     return super.damageEntity(paramDamageSource, paramFloat);
/*     */   }
/*     */ 
/*     */   
/*     */   public float a(int paramInt1, int paramInt2, int paramInt3) {
/* 150 */     if (this.world.getType(paramInt1, paramInt2 - 1, paramInt3) == Blocks.GRASS) return 10.0F; 
/* 151 */     return this.world.n(paramInt1, paramInt2, paramInt3) - 0.5F;
/*     */   }
/*     */ 
/*     */   
/*     */   public void b(NBTTagCompound paramNBTTagCompound) {
/* 156 */     super.b(paramNBTTagCompound);
/* 157 */     paramNBTTagCompound.setInt("InLove", this.love);
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(NBTTagCompound paramNBTTagCompound) {
/* 162 */     super.a(paramNBTTagCompound);
/* 163 */     this.love = paramNBTTagCompound.getInt("InLove");
/*     */   }
/*     */ 
/*     */   
/*     */   protected Entity findTarget() {
/* 168 */     if (this.bo > 0) return null;
/*     */     
/* 170 */     float f = 8.0F;
/* 171 */     if (this.love > 0) {
/* 172 */       List<EntityAnimal> list = this.world.a(getClass(), this.boundingBox.grow(f, f, f));
/* 173 */       for (byte b = 0; b < list.size(); b++) {
/* 174 */         EntityAnimal entityAnimal = list.get(b);
/* 175 */         if (entityAnimal != this && entityAnimal.love > 0) {
/* 176 */           return entityAnimal;
/*     */         }
/*     */       }
/*     */     
/* 180 */     } else if (getAge() == 0) {
/* 181 */       List<EntityHuman> list = this.world.a(EntityHuman.class, this.boundingBox.grow(f, f, f));
/* 182 */       for (byte b = 0; b < list.size(); b++) {
/* 183 */         EntityHuman entityHuman = list.get(b);
/* 184 */         if (entityHuman.bF() != null && c(entityHuman.bF())) {
/* 185 */           return entityHuman;
/*     */         }
/*     */       } 
/* 188 */     } else if (getAge() > 0) {
/* 189 */       List<EntityAnimal> list = this.world.a(getClass(), this.boundingBox.grow(f, f, f));
/* 190 */       for (byte b = 0; b < list.size(); b++) {
/* 191 */         EntityAnimal entityAnimal = list.get(b);
/* 192 */         if (entityAnimal != this && entityAnimal.getAge() < 0) {
/* 193 */           return entityAnimal;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 198 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canSpawn() {
/* 203 */     int i = MathHelper.floor(this.locX);
/* 204 */     int j = MathHelper.floor(this.boundingBox.b);
/* 205 */     int k = MathHelper.floor(this.locZ);
/* 206 */     return (this.world.getType(i, j - 1, k) == Blocks.GRASS && this.world.j(i, j, k) > 8 && super.canSpawn());
/*     */   }
/*     */ 
/*     */   
/*     */   public int q() {
/* 211 */     return 120;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isTypeNotPersistent() {
/* 216 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getExpValue(EntityHuman paramEntityHuman) {
/* 221 */     return 1 + this.world.random.nextInt(3);
/*     */   }
/*     */   
/*     */   public boolean c(ItemStack paramItemStack) {
/* 225 */     return (paramItemStack.getItem() == Items.WHEAT);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean a(EntityHuman paramEntityHuman) {
/* 230 */     ItemStack itemStack = paramEntityHuman.inventory.getItemInHand();
/* 231 */     if (itemStack != null && c(itemStack) && getAge() == 0 && this.love <= 0) {
/* 232 */       if (!paramEntityHuman.abilities.canInstantlyBuild) {
/* 233 */         itemStack.count--;
/* 234 */         if (itemStack.count <= 0) {
/* 235 */           paramEntityHuman.inventory.setItem(paramEntityHuman.inventory.itemInHandIndex, null);
/*     */         }
/*     */       } 
/* 238 */       f(paramEntityHuman);
/* 239 */       return true;
/*     */     } 
/* 241 */     return super.a(paramEntityHuman);
/*     */   }
/*     */   
/*     */   public void f(EntityHuman paramEntityHuman) {
/* 245 */     this.love = 600;
/* 246 */     this.br = paramEntityHuman;
/*     */     
/* 248 */     this.target = null;
/* 249 */     this.world.broadcastEntityEffect(this, (byte)18);
/*     */   }
/*     */   
/*     */   public EntityHuman cd() {
/* 253 */     return this.br;
/*     */   }
/*     */   
/*     */   public boolean ce() {
/* 257 */     return (this.love > 0);
/*     */   }
/*     */   
/*     */   public void cf() {
/* 261 */     this.love = 0;
/*     */   }
/*     */   
/*     */   public boolean mate(EntityAnimal paramEntityAnimal) {
/* 265 */     if (paramEntityAnimal == this) return false; 
/* 266 */     if (paramEntityAnimal.getClass() != getClass()) return false; 
/* 267 */     return (ce() && paramEntityAnimal.ce());
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityAnimal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityLightning
/*     */   extends EntityWeather
/*     */ {
/*     */   private int lifeTicks;
/*     */   public long a;
/*     */   private int c;
/*     */   public boolean isEffect = false;
/*     */   public boolean isSilent = false;
/*     */   
/*     */   public EntityLightning(World world, double d0, double d1, double d2) {
/*  19 */     this(world, d0, d1, d2, false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityLightning(World world, double d0, double d1, double d2, boolean isEffect) {
/*  25 */     super(world);
/*     */ 
/*     */     
/*  28 */     this.isEffect = isEffect;
/*     */     
/*  30 */     setPositionRotation(d0, d1, d2, 0.0F, 0.0F);
/*  31 */     this.lifeTicks = 2;
/*  32 */     this.a = this.random.nextLong();
/*  33 */     this.c = this.random.nextInt(3) + 1;
/*     */ 
/*     */     
/*  36 */     if (!isEffect && !world.isStatic && world.getGameRules().getBoolean("doFireTick") && (world.difficulty == EnumDifficulty.NORMAL || world.difficulty == EnumDifficulty.HARD) && world.areChunksLoaded(MathHelper.floor(d0), MathHelper.floor(d1), MathHelper.floor(d2), 10)) {
/*  37 */       int i = MathHelper.floor(d0);
/*  38 */       int j = MathHelper.floor(d1);
/*  39 */       int k = MathHelper.floor(d2);
/*     */       
/*  41 */       if (world.getType(i, j, k).getMaterial() == Material.AIR && Blocks.FIRE.canPlace(world, i, j, k))
/*     */       {
/*  43 */         if (!CraftEventFactory.callBlockIgniteEvent(world, i, j, k, this).isCancelled()) {
/*  44 */           world.setTypeUpdate(i, j, k, Blocks.FIRE);
/*     */         }
/*     */       }
/*     */ 
/*     */       
/*  49 */       for (i = 0; i < 4; i++) {
/*  50 */         j = MathHelper.floor(d0) + this.random.nextInt(3) - 1;
/*  51 */         k = MathHelper.floor(d1) + this.random.nextInt(3) - 1;
/*  52 */         int l = MathHelper.floor(d2) + this.random.nextInt(3) - 1;
/*     */         
/*  54 */         if (world.getType(j, k, l).getMaterial() == Material.AIR && Blocks.FIRE.canPlace(world, j, k, l))
/*     */         {
/*  56 */           if (!CraftEventFactory.callBlockIgniteEvent(world, j, k, l, this).isCancelled()) {
/*  57 */             world.setTypeUpdate(j, k, l, Blocks.FIRE);
/*     */           }
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityLightning(World world, double d0, double d1, double d2, boolean isEffect, boolean isSilent) {
/*  68 */     this(world, d0, d1, d2, isEffect);
/*  69 */     this.isSilent = isSilent;
/*     */   }
/*     */ 
/*     */   
/*     */   public void h() {
/*  74 */     super.h();
/*  75 */     if (!this.isSilent && this.lifeTicks == 2) {
/*     */ 
/*     */       
/*  78 */       float pitch = 0.8F + this.random.nextFloat() * 0.2F;
/*  79 */       for (EntityPlayer player : this.world.players) {
/*  80 */         int viewDistance = player.viewDistance * 16;
/*  81 */         double deltaX = this.locX - player.locX;
/*  82 */         double deltaZ = this.locZ - player.locZ;
/*  83 */         double distanceSquared = deltaX * deltaX + deltaZ * deltaZ;
/*  84 */         if (distanceSquared > (viewDistance * viewDistance)) {
/*  85 */           double deltaLength = Math.sqrt(distanceSquared);
/*  86 */           double relativeX = player.locX + deltaX / deltaLength * viewDistance;
/*  87 */           double relativeZ = player.locZ + deltaZ / deltaLength * viewDistance;
/*  88 */           player.playerConnection.sendPacket(new PacketPlayOutNamedSoundEffect("ambient.weather.thunder", relativeX, this.locY, relativeZ, 10000.0F, pitch)); continue;
/*     */         } 
/*  90 */         player.playerConnection.sendPacket(new PacketPlayOutNamedSoundEffect("ambient.weather.thunder", this.locX, this.locY, this.locZ, 10000.0F, pitch));
/*     */       } 
/*     */ 
/*     */       
/*  94 */       this.world.makeSound(this.locX, this.locY, this.locZ, "random.explode", 2.0F, 0.5F + this.random.nextFloat() * 0.2F);
/*     */     } 
/*     */     
/*  97 */     this.lifeTicks--;
/*  98 */     if (this.lifeTicks < 0) {
/*  99 */       if (this.c == 0) {
/* 100 */         die();
/* 101 */       } else if (this.lifeTicks < -this.random.nextInt(10)) {
/* 102 */         this.c--;
/* 103 */         this.lifeTicks = 1;
/* 104 */         this.a = this.random.nextLong();
/*     */         
/* 106 */         if (!this.isEffect && !this.world.isStatic && this.world.getGameRules().getBoolean("doFireTick") && this.world.areChunksLoaded(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ), 10)) {
/* 107 */           int i = MathHelper.floor(this.locX);
/* 108 */           int j = MathHelper.floor(this.locY);
/* 109 */           int k = MathHelper.floor(this.locZ);
/*     */           
/* 111 */           if (this.world.getType(i, j, k).getMaterial() == Material.AIR && Blocks.FIRE.canPlace(this.world, i, j, k))
/*     */           {
/* 113 */             if (!CraftEventFactory.callBlockIgniteEvent(this.world, i, j, k, this).isCancelled()) {
/* 114 */               this.world.setTypeUpdate(i, j, k, Blocks.FIRE);
/*     */             }
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 122 */     if (this.lifeTicks >= 0 && !this.isEffect)
/* 123 */       if (this.world.isStatic) {
/* 124 */         this.world.q = 2;
/*     */       } else {
/* 126 */         double d0 = 3.0D;
/* 127 */         List<Entity> list = this.world.getEntities(this, AxisAlignedBB.a(this.locX - d0, this.locY - d0, this.locZ - d0, this.locX + d0, this.locY + 6.0D + d0, this.locZ + d0));
/*     */         
/* 129 */         for (int l = 0; l < list.size(); l++) {
/* 130 */           Entity entity = list.get(l);
/*     */           
/* 132 */           entity.a(this);
/*     */         } 
/*     */       }  
/*     */   }
/*     */   
/*     */   protected void c() {}
/*     */   
/*     */   protected void a(NBTTagCompound nbttagcompound) {}
/*     */   
/*     */   protected void b(NBTTagCompound nbttagcompound) {}
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityLightning.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
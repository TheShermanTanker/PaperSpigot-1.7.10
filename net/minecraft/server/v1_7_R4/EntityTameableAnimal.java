/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.UUID;
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
/*     */ public abstract class EntityTameableAnimal
/*     */   extends EntityAnimal
/*     */   implements EntityOwnable
/*     */ {
/*  20 */   protected PathfinderGoalSit bp = new PathfinderGoalSit(this);
/*     */   
/*     */   public EntityTameableAnimal(World paramWorld) {
/*  23 */     super(paramWorld);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void c() {
/*  28 */     super.c();
/*  29 */     this.datawatcher.a(16, Byte.valueOf((byte)0));
/*  30 */     this.datawatcher.a(17, "");
/*     */   }
/*     */ 
/*     */   
/*     */   public void b(NBTTagCompound paramNBTTagCompound) {
/*  35 */     super.b(paramNBTTagCompound);
/*  36 */     if (getOwnerUUID() == null) {
/*  37 */       paramNBTTagCompound.setString("OwnerUUID", "");
/*     */     } else {
/*  39 */       paramNBTTagCompound.setString("OwnerUUID", getOwnerUUID());
/*     */     } 
/*  41 */     paramNBTTagCompound.setBoolean("Sitting", isSitting());
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(NBTTagCompound paramNBTTagCompound) {
/*  46 */     super.a(paramNBTTagCompound);
/*  47 */     String str = "";
/*  48 */     if (paramNBTTagCompound.hasKeyOfType("OwnerUUID", 8)) {
/*  49 */       str = paramNBTTagCompound.getString("OwnerUUID");
/*     */     } else {
/*  51 */       String str1 = paramNBTTagCompound.getString("Owner");
/*  52 */       str = NameReferencingFileConverter.a(str1);
/*     */     } 
/*  54 */     if (str.length() > 0) {
/*  55 */       setOwnerUUID(str);
/*  56 */       setTamed(true);
/*     */     } 
/*  58 */     this.bp.setSitting(paramNBTTagCompound.getBoolean("Sitting"));
/*  59 */     setSitting(paramNBTTagCompound.getBoolean("Sitting"));
/*     */   }
/*     */   
/*     */   protected void i(boolean paramBoolean) {
/*  63 */     String str = "heart";
/*  64 */     if (!paramBoolean) {
/*  65 */       str = "smoke";
/*     */     }
/*  67 */     for (byte b = 0; b < 7; b++) {
/*  68 */       double d1 = this.random.nextGaussian() * 0.02D;
/*  69 */       double d2 = this.random.nextGaussian() * 0.02D;
/*  70 */       double d3 = this.random.nextGaussian() * 0.02D;
/*  71 */       this.world.addParticle(str, this.locX + (this.random.nextFloat() * this.width * 2.0F) - this.width, this.locY + 0.5D + (this.random.nextFloat() * this.length), this.locZ + (this.random.nextFloat() * this.width * 2.0F) - this.width, d1, d2, d3);
/*     */     } 
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
/*     */   public boolean isTamed() {
/*  87 */     return ((this.datawatcher.getByte(16) & 0x4) != 0);
/*     */   }
/*     */   
/*     */   public void setTamed(boolean paramBoolean) {
/*  91 */     byte b = this.datawatcher.getByte(16);
/*  92 */     if (paramBoolean) {
/*  93 */       this.datawatcher.watch(16, Byte.valueOf((byte)(b | 0x4)));
/*     */     } else {
/*  95 */       this.datawatcher.watch(16, Byte.valueOf((byte)(b & 0xFFFFFFFB)));
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isSitting() {
/* 100 */     return ((this.datawatcher.getByte(16) & 0x1) != 0);
/*     */   }
/*     */   
/*     */   public void setSitting(boolean paramBoolean) {
/* 104 */     byte b = this.datawatcher.getByte(16);
/* 105 */     if (paramBoolean) {
/* 106 */       this.datawatcher.watch(16, Byte.valueOf((byte)(b | 0x1)));
/*     */     } else {
/* 108 */       this.datawatcher.watch(16, Byte.valueOf((byte)(b & 0xFFFFFFFE)));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getOwnerUUID() {
/* 114 */     return this.datawatcher.getString(17);
/*     */   }
/*     */   
/*     */   public void setOwnerUUID(String paramString) {
/* 118 */     this.datawatcher.watch(17, paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityLiving getOwner() {
/*     */     try {
/* 124 */       UUID uUID = UUID.fromString(getOwnerUUID());
/* 125 */       if (uUID == null) {
/* 126 */         return null;
/*     */       }
/* 128 */       return this.world.a(uUID);
/* 129 */     } catch (IllegalArgumentException illegalArgumentException) {
/* 130 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean e(EntityLiving paramEntityLiving) {
/* 135 */     return (paramEntityLiving == getOwner());
/*     */   }
/*     */   
/*     */   public PathfinderGoalSit getGoalSit() {
/* 139 */     return this.bp;
/*     */   }
/*     */   
/*     */   public boolean a(EntityLiving paramEntityLiving1, EntityLiving paramEntityLiving2) {
/* 143 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public ScoreboardTeamBase getScoreboardTeam() {
/* 148 */     if (isTamed()) {
/* 149 */       EntityLiving entityLiving = getOwner();
/* 150 */       if (entityLiving != null) {
/* 151 */         return entityLiving.getScoreboardTeam();
/*     */       }
/*     */     } 
/* 154 */     return super.getScoreboardTeam();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean c(EntityLiving paramEntityLiving) {
/* 159 */     if (isTamed()) {
/* 160 */       EntityLiving entityLiving = getOwner();
/* 161 */       if (paramEntityLiving == entityLiving) {
/* 162 */         return true;
/*     */       }
/* 164 */       if (entityLiving != null) {
/* 165 */         return entityLiving.c(paramEntityLiving);
/*     */       }
/*     */     } 
/* 168 */     return super.c(paramEntityLiving);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityTameableAnimal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
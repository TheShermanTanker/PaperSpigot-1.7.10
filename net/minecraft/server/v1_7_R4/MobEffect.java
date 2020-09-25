/*     */ package net.minecraft.server.v1_7_R4;
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
/*     */ public class MobEffect
/*     */ {
/*     */   private int effectId;
/*     */   private int duration;
/*     */   private int amplification;
/*     */   private boolean splash;
/*     */   private boolean ambient;
/*     */   
/*     */   public MobEffect(int paramInt1, int paramInt2) {
/*  23 */     this(paramInt1, paramInt2, 0);
/*     */   }
/*     */   
/*     */   public MobEffect(int paramInt1, int paramInt2, int paramInt3) {
/*  27 */     this(paramInt1, paramInt2, paramInt3, false);
/*     */   }
/*     */   
/*     */   public MobEffect(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean) {
/*  31 */     this.effectId = paramInt1;
/*  32 */     this.duration = paramInt2;
/*  33 */     this.amplification = paramInt3;
/*  34 */     this.ambient = paramBoolean;
/*     */   }
/*     */   
/*     */   public MobEffect(MobEffect paramMobEffect) {
/*  38 */     this.effectId = paramMobEffect.effectId;
/*  39 */     this.duration = paramMobEffect.duration;
/*  40 */     this.amplification = paramMobEffect.amplification;
/*     */   }
/*     */   
/*     */   public void a(MobEffect paramMobEffect) {
/*  44 */     if (this.effectId != paramMobEffect.effectId) {
/*  45 */       System.err.println("This method should only be called for matching effects!");
/*     */     }
/*  47 */     if (paramMobEffect.amplification > this.amplification) {
/*  48 */       this.amplification = paramMobEffect.amplification;
/*  49 */       this.duration = paramMobEffect.duration;
/*  50 */     } else if (paramMobEffect.amplification == this.amplification && this.duration < paramMobEffect.duration) {
/*  51 */       this.duration = paramMobEffect.duration;
/*  52 */     } else if (!paramMobEffect.ambient && this.ambient) {
/*  53 */       this.ambient = paramMobEffect.ambient;
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getEffectId() {
/*  58 */     return this.effectId;
/*     */   }
/*     */   
/*     */   public int getDuration() {
/*  62 */     return this.duration;
/*     */   }
/*     */   
/*     */   public int getAmplifier() {
/*  66 */     return this.amplification;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSplash(boolean paramBoolean) {
/*  74 */     this.splash = paramBoolean;
/*     */   }
/*     */   
/*     */   public boolean isAmbient() {
/*  78 */     return this.ambient;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean tick(EntityLiving paramEntityLiving) {
/*  88 */     if (this.duration > 0) {
/*  89 */       if (MobEffectList.byId[this.effectId].a(this.duration, this.amplification)) {
/*  90 */         b(paramEntityLiving);
/*     */       }
/*  92 */       h();
/*     */     } 
/*  94 */     return (this.duration > 0);
/*     */   }
/*     */   
/*     */   private int h() {
/*  98 */     return --this.duration;
/*     */   }
/*     */   
/*     */   public void b(EntityLiving paramEntityLiving) {
/* 102 */     if (this.duration > 0) {
/* 103 */       MobEffectList.byId[this.effectId].tick(paramEntityLiving, this.amplification);
/*     */     }
/*     */   }
/*     */   
/*     */   public String f() {
/* 108 */     return MobEffectList.byId[this.effectId].a();
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 113 */     return this.effectId;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 118 */     String str = "";
/* 119 */     if (getAmplifier() > 0) {
/* 120 */       str = f() + " x " + (getAmplifier() + 1) + ", Duration: " + getDuration();
/*     */     } else {
/* 122 */       str = f() + ", Duration: " + getDuration();
/*     */     } 
/* 124 */     if (this.splash) {
/* 125 */       str = str + ", Splash: true";
/*     */     }
/* 127 */     if (MobEffectList.byId[this.effectId].i()) {
/* 128 */       return "(" + str + ")";
/*     */     }
/* 130 */     return str;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 135 */     if (!(paramObject instanceof MobEffect)) {
/* 136 */       return false;
/*     */     }
/* 138 */     MobEffect mobEffect = (MobEffect)paramObject;
/* 139 */     return (this.effectId == mobEffect.effectId && this.amplification == mobEffect.amplification && this.duration == mobEffect.duration && this.splash == mobEffect.splash && this.ambient == mobEffect.ambient);
/*     */   }
/*     */   
/*     */   public NBTTagCompound a(NBTTagCompound paramNBTTagCompound) {
/* 143 */     paramNBTTagCompound.setByte("Id", (byte)getEffectId());
/* 144 */     paramNBTTagCompound.setByte("Amplifier", (byte)getAmplifier());
/* 145 */     paramNBTTagCompound.setInt("Duration", getDuration());
/* 146 */     paramNBTTagCompound.setBoolean("Ambient", isAmbient());
/* 147 */     return paramNBTTagCompound;
/*     */   }
/*     */   
/*     */   public static MobEffect b(NBTTagCompound paramNBTTagCompound) {
/* 151 */     byte b1 = paramNBTTagCompound.getByte("Id");
/* 152 */     if (b1 < 0 || b1 >= MobEffectList.byId.length || MobEffectList.byId[b1] == null) {
/* 153 */       return null;
/*     */     }
/* 155 */     byte b2 = paramNBTTagCompound.getByte("Amplifier");
/* 156 */     int i = paramNBTTagCompound.getInt("Duration");
/* 157 */     boolean bool = paramNBTTagCompound.getBoolean("Ambient");
/* 158 */     return new MobEffect(b1, i, b2, bool);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\MobEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
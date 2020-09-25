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
/*     */ public class EntityMinecartTNT
/*     */   extends EntityMinecartAbstract
/*     */ {
/*  16 */   private int fuse = -1;
/*     */   
/*     */   public EntityMinecartTNT(World paramWorld) {
/*  19 */     super(paramWorld);
/*     */   }
/*     */   
/*     */   public EntityMinecartTNT(World paramWorld, double paramDouble1, double paramDouble2, double paramDouble3) {
/*  23 */     super(paramWorld, paramDouble1, paramDouble2, paramDouble3);
/*     */   }
/*     */ 
/*     */   
/*     */   public int m() {
/*  28 */     return 3;
/*     */   }
/*     */ 
/*     */   
/*     */   public Block o() {
/*  33 */     return Blocks.TNT;
/*     */   }
/*     */ 
/*     */   
/*     */   public void h() {
/*  38 */     super.h();
/*     */     
/*  40 */     if (this.fuse > 0) {
/*  41 */       this.fuse--;
/*  42 */       this.world.addParticle("smoke", this.locX, this.locY + 0.5D, this.locZ, 0.0D, 0.0D, 0.0D);
/*  43 */     } else if (this.fuse == 0) {
/*  44 */       c(this.motX * this.motX + this.motZ * this.motZ);
/*     */     } 
/*     */     
/*  47 */     if (this.positionChanged) {
/*  48 */       double d = this.motX * this.motX + this.motZ * this.motZ;
/*     */       
/*  50 */       if (d >= 0.009999999776482582D) {
/*  51 */         c(d);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(DamageSource paramDamageSource) {
/*  58 */     super.a(paramDamageSource);
/*     */     
/*  60 */     double d = this.motX * this.motX + this.motZ * this.motZ;
/*     */     
/*  62 */     if (!paramDamageSource.isExplosion()) {
/*  63 */       a(new ItemStack(Blocks.TNT, 1), 0.0F);
/*     */     }
/*     */     
/*  66 */     if (paramDamageSource.o() || paramDamageSource.isExplosion() || d >= 0.009999999776482582D) {
/*  67 */       c(d);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void c(double paramDouble) {
/*  72 */     if (!this.world.isStatic) {
/*  73 */       double d = Math.sqrt(paramDouble);
/*  74 */       if (d > 5.0D) d = 5.0D; 
/*  75 */       this.world.explode(this, this.locX, this.locY, this.locZ, (float)(4.0D + this.random.nextDouble() * 1.5D * d), true);
/*  76 */       die();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void b(float paramFloat) {
/*  82 */     if (paramFloat >= 3.0F) {
/*  83 */       float f = paramFloat / 10.0F;
/*  84 */       c((f * f));
/*     */     } 
/*     */     
/*  87 */     super.b(paramFloat);
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean) {
/*  92 */     if (paramBoolean && this.fuse < 0) {
/*  93 */       e();
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
/*     */   public void e() {
/* 107 */     this.fuse = 80;
/*     */     
/* 109 */     if (!this.world.isStatic) {
/* 110 */       this.world.broadcastEntityEffect(this, (byte)10);
/* 111 */       this.world.makeSound(this, "game.tnt.primed", 1.0F, 1.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean v() {
/* 120 */     return (this.fuse > -1);
/*     */   }
/*     */ 
/*     */   
/*     */   public float a(Explosion paramExplosion, World paramWorld, int paramInt1, int paramInt2, int paramInt3, Block paramBlock) {
/* 125 */     if (v() && (BlockMinecartTrackAbstract.a(paramBlock) || BlockMinecartTrackAbstract.b_(paramWorld, paramInt1, paramInt2 + 1, paramInt3))) {
/* 126 */       return 0.0F;
/*     */     }
/*     */     
/* 129 */     return super.a(paramExplosion, paramWorld, paramInt1, paramInt2, paramInt3, paramBlock);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean a(Explosion paramExplosion, World paramWorld, int paramInt1, int paramInt2, int paramInt3, Block paramBlock, float paramFloat) {
/* 134 */     if (v() && (BlockMinecartTrackAbstract.a(paramBlock) || BlockMinecartTrackAbstract.b_(paramWorld, paramInt1, paramInt2 + 1, paramInt3))) return false;
/*     */     
/* 136 */     return super.a(paramExplosion, paramWorld, paramInt1, paramInt2, paramInt3, paramBlock, paramFloat);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void a(NBTTagCompound paramNBTTagCompound) {
/* 141 */     super.a(paramNBTTagCompound);
/* 142 */     if (paramNBTTagCompound.hasKeyOfType("TNTFuse", 99)) this.fuse = paramNBTTagCompound.getInt("TNTFuse");
/*     */   
/*     */   }
/*     */   
/*     */   protected void b(NBTTagCompound paramNBTTagCompound) {
/* 147 */     super.b(paramNBTTagCompound);
/* 148 */     paramNBTTagCompound.setInt("TNTFuse", this.fuse);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityMinecartTNT.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
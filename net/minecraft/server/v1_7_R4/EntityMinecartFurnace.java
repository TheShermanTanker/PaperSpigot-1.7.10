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
/*     */ public class EntityMinecartFurnace
/*     */   extends EntityMinecartAbstract
/*     */ {
/*     */   private int c;
/*     */   public double a;
/*     */   public double b;
/*     */   
/*     */   public EntityMinecartFurnace(World paramWorld) {
/*  22 */     super(paramWorld);
/*     */   }
/*     */   
/*     */   public EntityMinecartFurnace(World paramWorld, double paramDouble1, double paramDouble2, double paramDouble3) {
/*  26 */     super(paramWorld, paramDouble1, paramDouble2, paramDouble3);
/*     */   }
/*     */ 
/*     */   
/*     */   public int m() {
/*  31 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void c() {
/*  36 */     super.c();
/*  37 */     this.datawatcher.a(16, new Byte((byte)0));
/*     */   }
/*     */ 
/*     */   
/*     */   public void h() {
/*  42 */     super.h();
/*     */     
/*  44 */     if (this.c > 0) {
/*  45 */       this.c--;
/*     */     }
/*  47 */     if (this.c <= 0) {
/*  48 */       this.a = this.b = 0.0D;
/*     */     }
/*  50 */     f((this.c > 0));
/*     */     
/*  52 */     if (e() && this.random.nextInt(4) == 0) {
/*  53 */       this.world.addParticle("largesmoke", this.locX, this.locY + 0.8D, this.locZ, 0.0D, 0.0D, 0.0D);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(DamageSource paramDamageSource) {
/*  59 */     super.a(paramDamageSource);
/*     */     
/*  61 */     if (!paramDamageSource.isExplosion()) {
/*  62 */       a(new ItemStack(Blocks.FURNACE, 1), 0.0F);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void a(int paramInt1, int paramInt2, int paramInt3, double paramDouble1, double paramDouble2, Block paramBlock, int paramInt4) {
/*  68 */     super.a(paramInt1, paramInt2, paramInt3, paramDouble1, paramDouble2, paramBlock, paramInt4);
/*     */     
/*  70 */     double d = this.a * this.a + this.b * this.b;
/*  71 */     if (d > 1.0E-4D && this.motX * this.motX + this.motZ * this.motZ > 0.001D) {
/*  72 */       d = MathHelper.sqrt(d);
/*  73 */       this.a /= d;
/*  74 */       this.b /= d;
/*     */       
/*  76 */       if (this.a * this.motX + this.b * this.motZ < 0.0D) {
/*  77 */         this.a = 0.0D;
/*  78 */         this.b = 0.0D;
/*     */       } else {
/*  80 */         this.a = this.motX;
/*  81 */         this.b = this.motZ;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void i() {
/*  88 */     double d = this.a * this.a + this.b * this.b;
/*     */     
/*  90 */     if (d > 1.0E-4D) {
/*  91 */       d = MathHelper.sqrt(d);
/*  92 */       this.a /= d;
/*  93 */       this.b /= d;
/*  94 */       double d1 = 0.05D;
/*  95 */       this.motX *= 0.800000011920929D;
/*  96 */       this.motY *= 0.0D;
/*  97 */       this.motZ *= 0.800000011920929D;
/*  98 */       this.motX += this.a * d1;
/*  99 */       this.motZ += this.b * d1;
/*     */     } else {
/* 101 */       this.motX *= 0.9800000190734863D;
/* 102 */       this.motY *= 0.0D;
/* 103 */       this.motZ *= 0.9800000190734863D;
/*     */     } 
/*     */     
/* 106 */     super.i();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean c(EntityHuman paramEntityHuman) {
/* 111 */     ItemStack itemStack = paramEntityHuman.inventory.getItemInHand();
/* 112 */     if (itemStack != null && itemStack.getItem() == Items.COAL) {
/* 113 */       if (!paramEntityHuman.abilities.canInstantlyBuild && --itemStack.count == 0) paramEntityHuman.inventory.setItem(paramEntityHuman.inventory.itemInHandIndex, null); 
/* 114 */       this.c += 3600;
/*     */     } 
/*     */     
/* 117 */     this.a = this.locX - paramEntityHuman.locX;
/* 118 */     this.b = this.locZ - paramEntityHuman.locZ;
/*     */     
/* 120 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void b(NBTTagCompound paramNBTTagCompound) {
/* 125 */     super.b(paramNBTTagCompound);
/* 126 */     paramNBTTagCompound.setDouble("PushX", this.a);
/* 127 */     paramNBTTagCompound.setDouble("PushZ", this.b);
/* 128 */     paramNBTTagCompound.setShort("Fuel", (short)this.c);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void a(NBTTagCompound paramNBTTagCompound) {
/* 133 */     super.a(paramNBTTagCompound);
/* 134 */     this.a = paramNBTTagCompound.getDouble("PushX");
/* 135 */     this.b = paramNBTTagCompound.getDouble("PushZ");
/* 136 */     this.c = paramNBTTagCompound.getShort("Fuel");
/*     */   }
/*     */   
/*     */   protected boolean e() {
/* 140 */     return ((this.datawatcher.getByte(16) & 0x1) != 0);
/*     */   }
/*     */   
/*     */   protected void f(boolean paramBoolean) {
/* 144 */     if (paramBoolean) {
/* 145 */       this.datawatcher.watch(16, Byte.valueOf((byte)(this.datawatcher.getByte(16) | 0x1)));
/*     */     } else {
/* 147 */       this.datawatcher.watch(16, Byte.valueOf((byte)(this.datawatcher.getByte(16) & 0xFFFFFFFE)));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Block o() {
/* 153 */     return Blocks.BURNING_FURNACE;
/*     */   }
/*     */ 
/*     */   
/*     */   public int q() {
/* 158 */     return 2;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityMinecartFurnace.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
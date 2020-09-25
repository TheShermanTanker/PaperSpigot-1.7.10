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
/*     */ public class PathfinderGoalPassengerCarrotStick
/*     */   extends PathfinderGoal
/*     */ {
/*     */   private final EntityInsentient a;
/*     */   private final float b;
/*     */   private float c;
/*     */   private boolean d;
/*     */   private int e;
/*     */   private int f;
/*     */   
/*     */   public PathfinderGoalPassengerCarrotStick(EntityInsentient paramEntityInsentient, float paramFloat) {
/*  25 */     this.a = paramEntityInsentient;
/*  26 */     this.b = paramFloat;
/*  27 */     a(7);
/*     */   }
/*     */ 
/*     */   
/*     */   public void c() {
/*  32 */     this.c = 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public void d() {
/*  37 */     this.d = false;
/*  38 */     this.c = 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean a() {
/*  43 */     return (this.a.isAlive() && this.a.passenger != null && this.a.passenger instanceof EntityHuman && (this.d || this.a.bE()));
/*     */   }
/*     */ 
/*     */   
/*     */   public void e() {
/*  48 */     EntityHuman entityHuman = (EntityHuman)this.a.passenger;
/*  49 */     EntityCreature entityCreature = (EntityCreature)this.a;
/*     */     
/*  51 */     float f1 = MathHelper.g(entityHuman.yaw - this.a.yaw) * 0.5F;
/*  52 */     if (f1 > 5.0F) f1 = 5.0F; 
/*  53 */     if (f1 < -5.0F) f1 = -5.0F;
/*     */     
/*  55 */     this.a.yaw = MathHelper.g(this.a.yaw + f1);
/*  56 */     if (this.c < this.b) this.c += (this.b - this.c) * 0.01F; 
/*  57 */     if (this.c > this.b) this.c = this.b;
/*     */     
/*  59 */     int i = MathHelper.floor(this.a.locX);
/*  60 */     int j = MathHelper.floor(this.a.locY);
/*  61 */     int k = MathHelper.floor(this.a.locZ);
/*  62 */     float f2 = this.c;
/*  63 */     if (this.d) {
/*  64 */       if (this.e++ > this.f) {
/*  65 */         this.d = false;
/*     */       }
/*  67 */       f2 += f2 * 1.15F * MathHelper.sin(this.e / this.f * 3.1415927F);
/*     */     } 
/*     */     
/*  70 */     float f3 = 0.91F;
/*  71 */     if (this.a.onGround) {
/*  72 */       f3 = (this.a.world.getType(MathHelper.d(i), MathHelper.d(j) - 1, MathHelper.d(k))).frictionFactor * 0.91F;
/*     */     }
/*  74 */     float f4 = 0.16277136F / f3 * f3 * f3;
/*  75 */     float f5 = MathHelper.sin(entityCreature.yaw * 3.1415927F / 180.0F);
/*  76 */     float f6 = MathHelper.cos(entityCreature.yaw * 3.1415927F / 180.0F);
/*  77 */     float f7 = entityCreature.bl() * f4;
/*  78 */     float f8 = Math.max(f2, 1.0F);
/*  79 */     f8 = f7 / f8;
/*  80 */     float f9 = f2 * f8;
/*  81 */     float f10 = -(f9 * f5);
/*  82 */     float f11 = f9 * f6;
/*     */     
/*  84 */     if (MathHelper.abs(f10) > MathHelper.abs(f11)) {
/*  85 */       if (f10 < 0.0F) f10 -= this.a.width / 2.0F; 
/*  86 */       if (f10 > 0.0F) f10 += this.a.width / 2.0F; 
/*  87 */       f11 = 0.0F;
/*     */     } else {
/*  89 */       f10 = 0.0F;
/*  90 */       if (f11 < 0.0F) f11 -= this.a.width / 2.0F; 
/*  91 */       if (f11 > 0.0F) f11 += this.a.width / 2.0F;
/*     */     
/*     */     } 
/*  94 */     int m = MathHelper.floor(this.a.locX + f10);
/*  95 */     int n = MathHelper.floor(this.a.locZ + f11);
/*     */     
/*  97 */     PathPoint pathPoint = new PathPoint(MathHelper.d(this.a.width + 1.0F), MathHelper.d(this.a.length + entityHuman.length + 1.0F), MathHelper.d(this.a.width + 1.0F));
/*     */     
/*  99 */     if (i != m || k != n) {
/*     */ 
/*     */       
/* 102 */       Block block = this.a.world.getType(i, j, k);
/* 103 */       boolean bool = (!a(block) && (block.getMaterial() != Material.AIR || !a(this.a.world.getType(i, j - 1, k)))) ? true : false;
/*     */       
/* 105 */       if (bool && Pathfinder.a(this.a, m, j, n, pathPoint, false, false, true) == 0 && Pathfinder.a(this.a, i, j + 1, k, pathPoint, false, false, true) == 1 && Pathfinder.a(this.a, m, j + 1, n, pathPoint, false, false, true) == 1)
/*     */       {
/* 107 */         entityCreature.getControllerJump().a();
/*     */       }
/*     */     } 
/*     */     
/* 111 */     if (!entityHuman.abilities.canInstantlyBuild && this.c >= this.b * 0.5F && this.a.aI().nextFloat() < 0.006F && !this.d) {
/* 112 */       ItemStack itemStack = entityHuman.be();
/*     */       
/* 114 */       if (itemStack != null && itemStack.getItem() == Items.CARROT_STICK) {
/* 115 */         itemStack.damage(1, entityHuman);
/*     */         
/* 117 */         if (itemStack.count == 0) {
/* 118 */           ItemStack itemStack1 = new ItemStack(Items.FISHING_ROD);
/* 119 */           itemStack1.setTag(itemStack.tag);
/* 120 */           entityHuman.inventory.items[entityHuman.inventory.itemInHandIndex] = itemStack1;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 125 */     this.a.e(0.0F, f2);
/*     */   }
/*     */   
/*     */   private boolean a(Block paramBlock) {
/* 129 */     return (paramBlock.b() == 10 || paramBlock instanceof BlockStepAbstract);
/*     */   }
/*     */   
/*     */   public boolean f() {
/* 133 */     return this.d;
/*     */   }
/*     */   
/*     */   public void g() {
/* 137 */     this.d = true;
/* 138 */     this.e = 0;
/* 139 */     this.f = this.a.aI().nextInt(841) + 140;
/*     */   }
/*     */   
/*     */   public boolean h() {
/* 143 */     return (!f() && this.c > this.b * 0.3F);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PathfinderGoalPassengerCarrotStick.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
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
/*     */ public class PathfinderGoalJumpOnBlock
/*     */   extends PathfinderGoal
/*     */ {
/*     */   private final EntityOcelot a;
/*     */   private final double b;
/*     */   private int c;
/*     */   private int d;
/*     */   private int e;
/*     */   private int f;
/*     */   private int g;
/*     */   private int h;
/*     */   
/*     */   public PathfinderGoalJumpOnBlock(EntityOcelot paramEntityOcelot, double paramDouble) {
/*  26 */     this.a = paramEntityOcelot;
/*  27 */     this.b = paramDouble;
/*  28 */     a(5);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean a() {
/*  33 */     return (this.a.isTamed() && !this.a.isSitting() && this.a.aI().nextDouble() <= 0.006500000134110451D && f());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean b() {
/*  38 */     return (this.c <= this.e && this.d <= 60 && a(this.a.world, this.f, this.g, this.h));
/*     */   }
/*     */ 
/*     */   
/*     */   public void c() {
/*  43 */     this.a.getNavigation().a(this.f + 0.5D, (this.g + 1), this.h + 0.5D, this.b);
/*  44 */     this.c = 0;
/*  45 */     this.d = 0;
/*  46 */     this.e = this.a.aI().nextInt(this.a.aI().nextInt(1200) + 1200) + 1200;
/*  47 */     this.a.getGoalSit().setSitting(false);
/*     */   }
/*     */ 
/*     */   
/*     */   public void d() {
/*  52 */     this.a.setSitting(false);
/*     */   }
/*     */ 
/*     */   
/*     */   public void e() {
/*  57 */     this.c++;
/*  58 */     this.a.getGoalSit().setSitting(false);
/*  59 */     if (this.a.e(this.f, (this.g + 1), this.h) > 1.0D) {
/*  60 */       this.a.setSitting(false);
/*  61 */       this.a.getNavigation().a(this.f + 0.5D, (this.g + 1), this.h + 0.5D, this.b);
/*  62 */       this.d++;
/*  63 */     } else if (!this.a.isSitting()) {
/*  64 */       this.a.setSitting(true);
/*     */     } else {
/*  66 */       this.d--;
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean f() {
/*  71 */     int i = (int)this.a.locY;
/*  72 */     double d = 2.147483647E9D;
/*     */     
/*  74 */     for (int j = (int)this.a.locX - 8; j < this.a.locX + 8.0D; j++) {
/*  75 */       for (int k = (int)this.a.locZ - 8; k < this.a.locZ + 8.0D; k++) {
/*  76 */         if (a(this.a.world, j, i, k) && this.a.world.isEmpty(j, i + 1, k)) {
/*  77 */           double d1 = this.a.e(j, i, k);
/*     */           
/*  79 */           if (d1 < d) {
/*  80 */             this.f = j;
/*  81 */             this.g = i;
/*  82 */             this.h = k;
/*  83 */             d = d1;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  89 */     return (d < 2.147483647E9D);
/*     */   }
/*     */   
/*     */   private boolean a(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/*  93 */     Block block = paramWorld.getType(paramInt1, paramInt2, paramInt3);
/*  94 */     int i = paramWorld.getData(paramInt1, paramInt2, paramInt3);
/*     */     
/*  96 */     if (block == Blocks.CHEST) {
/*  97 */       TileEntityChest tileEntityChest = (TileEntityChest)paramWorld.getTileEntity(paramInt1, paramInt2, paramInt3);
/*     */       
/*  99 */       if (tileEntityChest.o < 1)
/* 100 */         return true; 
/*     */     } else {
/* 102 */       if (block == Blocks.BURNING_FURNACE)
/* 103 */         return true; 
/* 104 */       if (block == Blocks.BED && !BlockBed.b(i)) {
/* 105 */         return true;
/*     */       }
/*     */     } 
/* 108 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PathfinderGoalJumpOnBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
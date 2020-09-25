/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class PathfinderGoalTempt
/*    */   extends PathfinderGoal
/*    */ {
/*    */   private EntityCreature a;
/*    */   private double b;
/*    */   private double c;
/*    */   private double d;
/*    */   private double e;
/*    */   private double f;
/*    */   private double g;
/*    */   private EntityHuman h;
/*    */   private int i;
/*    */   private boolean j;
/*    */   private Item k;
/*    */   private boolean l;
/*    */   private boolean m;
/*    */   
/*    */   public PathfinderGoalTempt(EntityCreature paramEntityCreature, double paramDouble, Item paramItem, boolean paramBoolean) {
/* 21 */     this.a = paramEntityCreature;
/* 22 */     this.b = paramDouble;
/* 23 */     this.k = paramItem;
/* 24 */     this.l = paramBoolean;
/* 25 */     a(3);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean a() {
/* 30 */     if (this.i > 0) {
/* 31 */       this.i--;
/* 32 */       return false;
/*    */     } 
/* 34 */     this.h = this.a.world.findNearbyPlayer(this.a, 10.0D);
/* 35 */     if (this.h == null) return false; 
/* 36 */     ItemStack itemStack = this.h.bF();
/* 37 */     if (itemStack == null) return false; 
/* 38 */     if (itemStack.getItem() != this.k) return false; 
/* 39 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean b() {
/* 44 */     if (this.l) {
/* 45 */       if (this.a.f(this.h) < 36.0D) {
/* 46 */         if (this.h.e(this.c, this.d, this.e) > 0.010000000000000002D) return false; 
/* 47 */         if (Math.abs(this.h.pitch - this.f) > 5.0D || Math.abs(this.h.yaw - this.g) > 5.0D) return false; 
/*    */       } else {
/* 49 */         this.c = this.h.locX;
/* 50 */         this.d = this.h.locY;
/* 51 */         this.e = this.h.locZ;
/*    */       } 
/* 53 */       this.f = this.h.pitch;
/* 54 */       this.g = this.h.yaw;
/*    */     } 
/* 56 */     return a();
/*    */   }
/*    */ 
/*    */   
/*    */   public void c() {
/* 61 */     this.c = this.h.locX;
/* 62 */     this.d = this.h.locY;
/* 63 */     this.e = this.h.locZ;
/* 64 */     this.j = true;
/* 65 */     this.m = this.a.getNavigation().a();
/* 66 */     this.a.getNavigation().a(false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void d() {
/* 71 */     this.h = null;
/* 72 */     this.a.getNavigation().h();
/* 73 */     this.i = 100;
/* 74 */     this.j = false;
/* 75 */     this.a.getNavigation().a(this.m);
/*    */   }
/*    */ 
/*    */   
/*    */   public void e() {
/* 80 */     this.a.getControllerLook().a(this.h, 30.0F, this.a.x());
/* 81 */     if (this.a.f(this.h) < 6.25D) { this.a.getNavigation().h(); }
/* 82 */     else { this.a.getNavigation().a(this.h, this.b); }
/*    */   
/*    */   }
/*    */   public boolean f() {
/* 86 */     return this.j;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PathfinderGoalTempt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
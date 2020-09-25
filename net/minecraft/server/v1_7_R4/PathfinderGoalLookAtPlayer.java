/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PathfinderGoalLookAtPlayer
/*    */   extends PathfinderGoal
/*    */ {
/*    */   private EntityInsentient b;
/*    */   protected Entity a;
/*    */   private float c;
/*    */   private int d;
/*    */   private float e;
/*    */   private Class f;
/*    */   
/*    */   public PathfinderGoalLookAtPlayer(EntityInsentient paramEntityInsentient, Class paramClass, float paramFloat) {
/* 17 */     this.b = paramEntityInsentient;
/* 18 */     this.f = paramClass;
/* 19 */     this.c = paramFloat;
/* 20 */     this.e = 0.02F;
/* 21 */     a(2);
/*    */   }
/*    */   
/*    */   public PathfinderGoalLookAtPlayer(EntityInsentient paramEntityInsentient, Class paramClass, float paramFloat1, float paramFloat2) {
/* 25 */     this.b = paramEntityInsentient;
/* 26 */     this.f = paramClass;
/* 27 */     this.c = paramFloat1;
/* 28 */     this.e = paramFloat2;
/* 29 */     a(2);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean a() {
/* 34 */     if (this.b.aI().nextFloat() >= this.e) return false;
/*    */     
/* 36 */     if (this.b.getGoalTarget() != null)
/* 37 */       this.a = this.b.getGoalTarget(); 
/* 38 */     if (this.f == EntityHuman.class) {
/* 39 */       this.a = this.b.world.findNearbyPlayer(this.b, this.c);
/*    */     } else {
/* 41 */       this.a = this.b.world.a(this.f, this.b.boundingBox.grow(this.c, 3.0D, this.c), this.b);
/*    */     } 
/*    */     
/* 44 */     return (this.a != null);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean b() {
/* 49 */     if (!this.a.isAlive()) return false; 
/* 50 */     if (this.b.f(this.a) > (this.c * this.c)) return false; 
/* 51 */     return (this.d > 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void c() {
/* 56 */     this.d = 40 + this.b.aI().nextInt(40);
/*    */   }
/*    */ 
/*    */   
/*    */   public void d() {
/* 61 */     this.a = null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void e() {
/* 66 */     this.b.getControllerLook().a(this.a.locX, this.a.locY + this.a.getHeadHeight(), this.a.locZ, 10.0F, this.b.x());
/* 67 */     this.d--;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PathfinderGoalLookAtPlayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
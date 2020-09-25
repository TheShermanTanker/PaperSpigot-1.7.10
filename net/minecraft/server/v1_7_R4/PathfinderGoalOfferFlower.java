/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PathfinderGoalOfferFlower
/*    */   extends PathfinderGoal
/*    */ {
/*    */   private EntityIronGolem a;
/*    */   private EntityVillager b;
/*    */   private int c;
/*    */   
/*    */   public PathfinderGoalOfferFlower(EntityIronGolem paramEntityIronGolem) {
/* 15 */     this.a = paramEntityIronGolem;
/* 16 */     a(3);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean a() {
/* 21 */     if (!this.a.world.w()) return false; 
/* 22 */     if (this.a.aI().nextInt(8000) != 0) return false; 
/* 23 */     this.b = (EntityVillager)this.a.world.a(EntityVillager.class, this.a.boundingBox.grow(6.0D, 2.0D, 6.0D), this.a);
/* 24 */     return (this.b != null);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean b() {
/* 29 */     return (this.c > 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void c() {
/* 34 */     this.c = 400;
/* 35 */     this.a.a(true);
/*    */   }
/*    */ 
/*    */   
/*    */   public void d() {
/* 40 */     this.a.a(false);
/* 41 */     this.b = null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void e() {
/* 46 */     this.a.getControllerLook().a(this.b, 30.0F, 30.0F);
/* 47 */     this.c--;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PathfinderGoalOfferFlower.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PathfinderGoalTakeFlower
/*    */   extends PathfinderGoal
/*    */ {
/*    */   private EntityVillager a;
/*    */   private EntityIronGolem b;
/*    */   private int c;
/*    */   private boolean d;
/*    */   
/*    */   public PathfinderGoalTakeFlower(EntityVillager paramEntityVillager) {
/* 17 */     this.a = paramEntityVillager;
/* 18 */     a(3);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean a() {
/* 23 */     if (this.a.getAge() >= 0) return false; 
/* 24 */     if (!this.a.world.w()) return false;
/*    */     
/* 26 */     List list = this.a.world.a(EntityIronGolem.class, this.a.boundingBox.grow(6.0D, 2.0D, 6.0D));
/* 27 */     if (list.isEmpty()) return false;
/*    */     
/* 29 */     for (EntityIronGolem entityIronGolem : list) {
/* 30 */       if (entityIronGolem.cb() > 0) {
/* 31 */         this.b = entityIronGolem;
/*    */         break;
/*    */       } 
/*    */     } 
/* 35 */     return (this.b != null);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean b() {
/* 40 */     return (this.b.cb() > 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void c() {
/* 45 */     this.c = this.a.aI().nextInt(320);
/* 46 */     this.d = false;
/* 47 */     this.b.getNavigation().h();
/*    */   }
/*    */ 
/*    */   
/*    */   public void d() {
/* 52 */     this.b = null;
/* 53 */     this.a.getNavigation().h();
/*    */   }
/*    */ 
/*    */   
/*    */   public void e() {
/* 58 */     this.a.getControllerLook().a(this.b, 30.0F, 30.0F);
/* 59 */     if (this.b.cb() == this.c) {
/* 60 */       this.a.getNavigation().a(this.b, 0.5D);
/* 61 */       this.d = true;
/*    */     } 
/*    */     
/* 64 */     if (this.d && 
/* 65 */       this.a.f(this.b) < 4.0D) {
/* 66 */       this.b.a(false);
/* 67 */       this.a.getNavigation().h();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PathfinderGoalTakeFlower.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
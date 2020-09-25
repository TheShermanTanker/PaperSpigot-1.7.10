/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PathfinderGoalTradeWithPlayer
/*    */   extends PathfinderGoal
/*    */ {
/*    */   private EntityVillager a;
/*    */   
/*    */   public PathfinderGoalTradeWithPlayer(EntityVillager paramEntityVillager) {
/* 13 */     this.a = paramEntityVillager;
/* 14 */     a(5);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean a() {
/* 19 */     if (!this.a.isAlive()) return false; 
/* 20 */     if (this.a.M()) return false; 
/* 21 */     if (!this.a.onGround) return false; 
/* 22 */     if (this.a.velocityChanged) return false;
/*    */     
/* 24 */     EntityHuman entityHuman = this.a.b();
/* 25 */     if (entityHuman == null)
/*    */     {
/* 27 */       return false;
/*    */     }
/*    */     
/* 30 */     if (this.a.f(entityHuman) > 16.0D)
/*    */     {
/* 32 */       return false;
/*    */     }
/*    */     
/* 35 */     if (!(entityHuman.activeContainer instanceof Container))
/*    */     {
/* 37 */       return false;
/*    */     }
/*    */     
/* 40 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void c() {
/* 45 */     this.a.getNavigation().h();
/*    */   }
/*    */ 
/*    */   
/*    */   public void d() {
/* 50 */     this.a.a_((EntityHuman)null);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PathfinderGoalTradeWithPlayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
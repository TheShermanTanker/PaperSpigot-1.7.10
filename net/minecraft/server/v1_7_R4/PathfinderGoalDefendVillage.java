/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PathfinderGoalDefendVillage
/*    */   extends PathfinderGoalTarget
/*    */ {
/*    */   EntityIronGolem a;
/*    */   EntityLiving b;
/*    */   
/*    */   public PathfinderGoalDefendVillage(EntityIronGolem paramEntityIronGolem) {
/* 12 */     super(paramEntityIronGolem, false, true);
/* 13 */     this.a = paramEntityIronGolem;
/* 14 */     a(1);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean a() {
/* 19 */     Village village = this.a.bZ();
/* 20 */     if (village == null) return false; 
/* 21 */     this.b = village.b(this.a);
/* 22 */     if (!a(this.b, false)) {
/*    */       
/* 24 */       if (this.c.aI().nextInt(20) == 0) {
/* 25 */         this.b = village.c(this.a);
/* 26 */         return a(this.b, false);
/*    */       } 
/* 28 */       return false;
/*    */     } 
/* 30 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void c() {
/* 36 */     this.a.setGoalTarget(this.b);
/* 37 */     super.c();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PathfinderGoalDefendVillage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PathfinderGoalBeg
/*    */   extends PathfinderGoal
/*    */ {
/*    */   private EntityWolf a;
/*    */   private EntityHuman b;
/*    */   private World c;
/*    */   private float d;
/*    */   private int e;
/*    */   
/*    */   public PathfinderGoalBeg(EntityWolf paramEntityWolf, float paramFloat) {
/* 18 */     this.a = paramEntityWolf;
/* 19 */     this.c = paramEntityWolf.world;
/* 20 */     this.d = paramFloat;
/* 21 */     a(2);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean a() {
/* 26 */     this.b = this.c.findNearbyPlayer(this.a, this.d);
/* 27 */     if (this.b == null) return false; 
/* 28 */     return a(this.b);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean b() {
/* 33 */     if (!this.b.isAlive()) return false; 
/* 34 */     if (this.a.f(this.b) > (this.d * this.d)) return false; 
/* 35 */     return (this.e > 0 && a(this.b));
/*    */   }
/*    */ 
/*    */   
/*    */   public void c() {
/* 40 */     this.a.m(true);
/* 41 */     this.e = 40 + this.a.aI().nextInt(40);
/*    */   }
/*    */ 
/*    */   
/*    */   public void d() {
/* 46 */     this.a.m(false);
/* 47 */     this.b = null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void e() {
/* 52 */     this.a.getControllerLook().a(this.b.locX, this.b.locY + this.b.getHeadHeight(), this.b.locZ, 10.0F, this.a.x());
/* 53 */     this.e--;
/*    */   }
/*    */   
/*    */   private boolean a(EntityHuman paramEntityHuman) {
/* 57 */     ItemStack itemStack = paramEntityHuman.inventory.getItemInHand();
/* 58 */     if (itemStack == null) return false; 
/* 59 */     if (!this.a.isTamed() && itemStack.getItem() == Items.BONE) return true; 
/* 60 */     return this.a.c(itemStack);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PathfinderGoalBeg.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
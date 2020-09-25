/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*    */ 
/*    */ public class PathfinderGoalBreakDoor extends PathfinderGoalDoorInteract {
/*  6 */   private int j = -1; private int i;
/*    */   
/*    */   public PathfinderGoalBreakDoor(EntityInsentient entityinsentient) {
/*  9 */     super(entityinsentient);
/*    */   }
/*    */   
/*    */   public boolean a() {
/* 13 */     return !super.a() ? false : (!this.a.world.getGameRules().getBoolean("mobGriefing") ? false : (!this.e.f(this.a.world, this.b, this.c, this.d)));
/*    */   }
/*    */   
/*    */   public void c() {
/* 17 */     super.c();
/* 18 */     this.i = 0;
/*    */   }
/*    */   
/*    */   public boolean b() {
/* 22 */     double d0 = this.a.e(this.b, this.c, this.d);
/*    */     
/* 24 */     return (this.i <= 240 && !this.e.f(this.a.world, this.b, this.c, this.d) && d0 < 4.0D);
/*    */   }
/*    */   
/*    */   public void d() {
/* 28 */     super.d();
/* 29 */     this.a.world.d(this.a.getId(), this.b, this.c, this.d, -1);
/*    */   }
/*    */   
/*    */   public void e() {
/* 33 */     super.e();
/* 34 */     if (this.a.aI().nextInt(20) == 0) {
/* 35 */       this.a.world.triggerEffect(1010, this.b, this.c, this.d, 0);
/*    */     }
/*    */     
/* 38 */     this.i++;
/* 39 */     int i = (int)(this.i / 240.0F * 10.0F);
/*    */     
/* 41 */     if (i != this.j) {
/* 42 */       this.a.world.d(this.a.getId(), this.b, this.c, this.d, i);
/* 43 */       this.j = i;
/*    */     } 
/*    */     
/* 46 */     if (this.i == 240 && this.a.world.difficulty == EnumDifficulty.HARD) {
/*    */       
/* 48 */       if (CraftEventFactory.callEntityBreakDoorEvent(this.a, this.b, this.c, this.d).isCancelled()) {
/* 49 */         c();
/*    */         
/*    */         return;
/*    */       } 
/*    */       
/* 54 */       this.a.world.setAir(this.b, this.c, this.d);
/* 55 */       this.a.world.triggerEffect(1012, this.b, this.c, this.d, 0);
/* 56 */       this.a.world.triggerEffect(2001, this.b, this.c, this.d, Block.getId(this.e));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PathfinderGoalBreakDoor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
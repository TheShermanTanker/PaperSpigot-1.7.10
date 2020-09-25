/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.lang.ref.WeakReference;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ 
/*    */ public class PathfinderGoalNearestAttackableTarget
/*    */   extends PathfinderGoalTarget {
/*    */   private final Class a;
/*    */   private final int b;
/*    */   private final DistanceComparator e;
/*    */   private final IEntitySelector f;
/* 13 */   private WeakReference<EntityLiving> g = new WeakReference<EntityLiving>(null);
/*    */   
/*    */   public PathfinderGoalNearestAttackableTarget(EntityCreature entitycreature, Class oclass, int i, boolean flag) {
/* 16 */     this(entitycreature, oclass, i, flag, false);
/*    */   }
/*    */   
/*    */   public PathfinderGoalNearestAttackableTarget(EntityCreature entitycreature, Class oclass, int i, boolean flag, boolean flag1) {
/* 20 */     this(entitycreature, oclass, i, flag, flag1, (IEntitySelector)null);
/*    */   }
/*    */   
/*    */   public PathfinderGoalNearestAttackableTarget(EntityCreature entitycreature, Class oclass, int i, boolean flag, boolean flag1, IEntitySelector ientityselector) {
/* 24 */     super(entitycreature, flag, flag1);
/* 25 */     this.a = oclass;
/* 26 */     this.b = i;
/* 27 */     this.e = new DistanceComparator(entitycreature);
/* 28 */     a(1);
/* 29 */     this.f = new EntitySelectorNearestAttackableTarget(this, ientityselector);
/*    */   }
/*    */   
/*    */   public boolean a() {
/* 33 */     if (this.b > 0 && this.c.aI().nextInt(this.b) != 0) {
/* 34 */       return false;
/*    */     }
/* 36 */     double d0 = f();
/* 37 */     List<?> list = this.c.world.a(this.a, this.c.boundingBox.grow(d0, 4.0D, d0), this.f);
/*    */     
/* 39 */     Collections.sort(list, this.e);
/* 40 */     if (list.isEmpty()) {
/* 41 */       return false;
/*    */     }
/* 43 */     this.g = new WeakReference<EntityLiving>((EntityLiving)list.get(0));
/* 44 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void c() {
/* 50 */     this.c.setGoalTarget(this.g.get());
/* 51 */     super.c();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PathfinderGoalNearestAttackableTarget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
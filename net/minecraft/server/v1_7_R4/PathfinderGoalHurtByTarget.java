/*    */ package net.minecraft.server.v1_7_R4;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.entity.CraftLivingEntity;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*    */ import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
/*    */ 
/*    */ public class PathfinderGoalHurtByTarget extends PathfinderGoalTarget {
/*    */   boolean a;
/*    */   
/*    */   public PathfinderGoalHurtByTarget(EntityCreature entitycreature, boolean flag) {
/* 12 */     super(entitycreature, false);
/* 13 */     this.a = flag;
/* 14 */     a(1);
/*    */   }
/*    */   private int b;
/*    */   public boolean a() {
/* 18 */     int i = this.c.aK();
/*    */     
/* 20 */     return (i != this.b && a(this.c.getLastDamager(), false));
/*    */   }
/*    */   
/*    */   public void c() {
/* 24 */     this.c.setGoalTarget(this.c.getLastDamager());
/* 25 */     this.b = this.c.aK();
/* 26 */     if (this.a) {
/* 27 */       double d0 = f();
/* 28 */       List list = this.c.world.a(this.c.getClass(), AxisAlignedBB.a(this.c.locX, this.c.locY, this.c.locZ, this.c.locX + 1.0D, this.c.locY + 1.0D, this.c.locZ + 1.0D).grow(d0, 10.0D, d0));
/* 29 */       Iterator<EntityCreature> iterator = list.iterator();
/*    */       
/* 31 */       while (iterator.hasNext()) {
/* 32 */         EntityCreature entitycreature = iterator.next();
/*    */         
/* 34 */         if (this.c != entitycreature && entitycreature.getGoalTarget() == null && !entitycreature.c(this.c.getLastDamager())) {
/*    */           
/* 36 */           EntityTargetLivingEntityEvent event = CraftEventFactory.callEntityTargetLivingEvent(entitycreature, this.c.getLastDamager(), EntityTargetEvent.TargetReason.TARGET_ATTACKED_NEARBY_ENTITY);
/* 37 */           if (event.isCancelled()) {
/*    */             continue;
/*    */           }
/* 40 */           entitycreature.setGoalTarget((event.getTarget() == null) ? null : ((CraftLivingEntity)event.getTarget()).getHandle());
/*    */         } 
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 46 */     super.c();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PathfinderGoalHurtByTarget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
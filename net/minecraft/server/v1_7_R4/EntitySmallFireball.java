/*    */ package net.minecraft.server.v1_7_R4;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.event.entity.EntityCombustByEntityEvent;
/*    */ 
/*    */ public class EntitySmallFireball extends EntityFireball {
/*    */   public EntitySmallFireball(World world) {
/*  8 */     super(world);
/*  9 */     a(0.3125F, 0.3125F);
/*    */   }
/*    */   
/*    */   public EntitySmallFireball(World world, EntityLiving entityliving, double d0, double d1, double d2) {
/* 13 */     super(world, entityliving, d0, d1, d2);
/* 14 */     a(0.3125F, 0.3125F);
/*    */   }
/*    */   
/*    */   public EntitySmallFireball(World world, double d0, double d1, double d2, double d3, double d4, double d5) {
/* 18 */     super(world, d0, d1, d2, d3, d4, d5);
/* 19 */     a(0.3125F, 0.3125F);
/*    */   }
/*    */   
/*    */   protected void a(MovingObjectPosition movingobjectposition) {
/* 23 */     if (!this.world.isStatic) {
/* 24 */       if (movingobjectposition.entity != null) {
/* 25 */         if (!movingobjectposition.entity.isFireproof() && movingobjectposition.entity.damageEntity(DamageSource.fireball(this, this.shooter), 5.0F)) {
/*    */           
/* 27 */           EntityCombustByEntityEvent event = new EntityCombustByEntityEvent((Entity)getBukkitEntity(), (Entity)movingobjectposition.entity.getBukkitEntity(), 5);
/* 28 */           movingobjectposition.entity.world.getServer().getPluginManager().callEvent((Event)event);
/*    */           
/* 30 */           if (!event.isCancelled()) {
/* 31 */             movingobjectposition.entity.setOnFire(event.getDuration());
/*    */           }
/*    */         } 
/*    */       } else {
/*    */         
/* 36 */         int i = movingobjectposition.b;
/* 37 */         int j = movingobjectposition.c;
/* 38 */         int k = movingobjectposition.d;
/*    */         
/* 40 */         switch (movingobjectposition.face) {
/*    */           case 0:
/* 42 */             j--;
/*    */             break;
/*    */           
/*    */           case 1:
/* 46 */             j++;
/*    */             break;
/*    */           
/*    */           case 2:
/* 50 */             k--;
/*    */             break;
/*    */           
/*    */           case 3:
/* 54 */             k++;
/*    */             break;
/*    */           
/*    */           case 4:
/* 58 */             i--;
/*    */             break;
/*    */           
/*    */           case 5:
/* 62 */             i++;
/*    */             break;
/*    */         } 
/* 65 */         if (this.world.isEmpty(i, j, k))
/*    */         {
/* 67 */           if (!CraftEventFactory.callBlockIgniteEvent(this.world, i, j, k, this).isCancelled()) {
/* 68 */             this.world.setTypeUpdate(i, j, k, Blocks.FIRE);
/*    */           }
/*    */         }
/*    */       } 
/*    */ 
/*    */       
/* 74 */       die();
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean R() {
/* 79 */     return false;
/*    */   }
/*    */   
/*    */   public boolean damageEntity(DamageSource damagesource, float f) {
/* 83 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntitySmallFireball.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
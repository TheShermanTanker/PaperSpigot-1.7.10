/*    */ package net.minecraft.server.v1_7_R4;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity;
/*    */ import org.bukkit.entity.Explosive;
/*    */ import org.bukkit.event.entity.ExplosionPrimeEvent;
/*    */ 
/*    */ public class EntityLargeFireball extends EntityFireball {
/*  7 */   public int yield = 1;
/*    */   
/*    */   public EntityLargeFireball(World world) {
/* 10 */     super(world);
/*    */   }
/*    */   
/*    */   public EntityLargeFireball(World world, EntityLiving entityliving, double d0, double d1, double d2) {
/* 14 */     super(world, entityliving, d0, d1, d2);
/*    */   }
/*    */   
/*    */   protected void a(MovingObjectPosition movingobjectposition) {
/* 18 */     if (!this.world.isStatic) {
/* 19 */       if (movingobjectposition.entity != null) {
/* 20 */         movingobjectposition.entity.damageEntity(DamageSource.fireball(this, this.shooter), 6.0F);
/*    */       }
/*    */ 
/*    */       
/* 24 */       ExplosionPrimeEvent event = new ExplosionPrimeEvent((Explosive)CraftEntity.getEntity(this.world.getServer(), this));
/* 25 */       this.world.getServer().getPluginManager().callEvent((Event)event);
/*    */       
/* 27 */       if (!event.isCancelled())
/*    */       {
/* 29 */         this.world.createExplosion(this, this.locX, this.locY, this.locZ, event.getRadius(), event.getFire(), this.world.getGameRules().getBoolean("mobGriefing"));
/*    */       }
/*    */ 
/*    */       
/* 33 */       die();
/*    */     } 
/*    */   }
/*    */   
/*    */   public void b(NBTTagCompound nbttagcompound) {
/* 38 */     super.b(nbttagcompound);
/* 39 */     nbttagcompound.setInt("ExplosionPower", this.yield);
/*    */   }
/*    */   
/*    */   public void a(NBTTagCompound nbttagcompound) {
/* 43 */     super.a(nbttagcompound);
/* 44 */     if (nbttagcompound.hasKeyOfType("ExplosionPower", 99))
/*    */     {
/* 46 */       this.bukkitYield = (this.yield = nbttagcompound.getInt("ExplosionPower"));
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityLargeFireball.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package net.minecraft.server.v1_7_R4;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.entity.Egg;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.EntityType;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.entity.CreatureSpawnEvent;
/*    */ import org.bukkit.event.player.PlayerEggThrowEvent;
/*    */ 
/*    */ public class EntityEgg extends EntityProjectile {
/*    */   public EntityEgg(World world) {
/* 13 */     super(world);
/*    */   }
/*    */   
/*    */   public EntityEgg(World world, EntityLiving entityliving) {
/* 17 */     super(world, entityliving);
/*    */   }
/*    */   
/*    */   public EntityEgg(World world, double d0, double d1, double d2) {
/* 21 */     super(world, d0, d1, d2);
/*    */   }
/*    */   
/*    */   protected void a(MovingObjectPosition movingobjectposition) {
/* 25 */     if (movingobjectposition.entity != null) {
/* 26 */       movingobjectposition.entity.damageEntity(DamageSource.projectile(this, getShooter()), 0.0F);
/*    */     }
/*    */ 
/*    */     
/* 30 */     boolean hatching = (!this.world.isStatic && this.random.nextInt(8) == 0);
/* 31 */     int numHatching = (this.random.nextInt(32) == 0) ? 4 : 1;
/* 32 */     if (!hatching) {
/* 33 */       numHatching = 0;
/*    */     }
/*    */     
/* 36 */     EntityType hatchingType = EntityType.CHICKEN;
/*    */     
/* 38 */     Entity shooter = getShooter();
/* 39 */     if (shooter instanceof EntityPlayer) {
/* 40 */       Player player = (shooter == null) ? null : (Player)shooter.getBukkitEntity();
/*    */       
/* 42 */       PlayerEggThrowEvent event = new PlayerEggThrowEvent(player, (Egg)getBukkitEntity(), hatching, (byte)numHatching, hatchingType);
/* 43 */       this.world.getServer().getPluginManager().callEvent((Event)event);
/*    */       
/* 45 */       hatching = event.isHatching();
/* 46 */       numHatching = event.getNumHatches();
/* 47 */       hatchingType = event.getHatchingType();
/*    */     } 
/*    */     
/* 50 */     if (hatching) {
/* 51 */       for (int k = 0; k < numHatching; k++) {
/* 52 */         Entity entity = this.world.getWorld().spawn(new Location((World)this.world.getWorld(), this.locX, this.locY, this.locZ, this.yaw, 0.0F), hatchingType.getEntityClass(), CreatureSpawnEvent.SpawnReason.EGG);
/* 53 */         if (entity instanceof Ageable) {
/* 54 */           ((Ageable)entity).setBaby();
/*    */         }
/*    */       } 
/*    */     }
/*    */ 
/*    */     
/* 60 */     for (int j = 0; j < 8; j++) {
/* 61 */       this.world.addParticle("snowballpoof", this.locX, this.locY, this.locZ, 0.0D, 0.0D, 0.0D);
/*    */     }
/*    */     
/* 64 */     if (!this.world.isStatic)
/* 65 */       die(); 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityEgg.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
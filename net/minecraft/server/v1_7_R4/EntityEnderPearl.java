/*    */ package net.minecraft.server.v1_7_R4;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.player.PlayerTeleportEvent;
/*    */ 
/*    */ public class EntityEnderPearl extends EntityProjectile {
/*    */   public EntityEnderPearl(World world) {
/* 12 */     super(world);
/* 13 */     this.loadChunks = world.paperSpigotConfig.loadUnloadedEnderPearls;
/*    */   }
/*    */   
/*    */   public EntityEnderPearl(World world, EntityLiving entityliving) {
/* 17 */     super(world, entityliving);
/* 18 */     this.loadChunks = world.paperSpigotConfig.loadUnloadedEnderPearls;
/*    */   }
/*    */   
/*    */   protected void a(MovingObjectPosition movingobjectposition) {
/* 22 */     if (movingobjectposition.entity != null) {
/* 23 */       movingobjectposition.entity.damageEntity(DamageSource.projectile(this, getShooter()), 0.0F);
/*    */     }
/*    */ 
/*    */     
/* 27 */     if (this.inUnloadedChunk && this.world.paperSpigotConfig.removeUnloadedEnderPearls) {
/* 28 */       die();
/*    */     }
/*    */ 
/*    */     
/* 32 */     for (int i = 0; i < 32; i++) {
/* 33 */       this.world.addParticle("portal", this.locX, this.locY + this.random.nextDouble() * 2.0D, this.locZ, this.random.nextGaussian(), 0.0D, this.random.nextGaussian());
/*    */     }
/*    */     
/* 36 */     if (!this.world.isStatic) {
/* 37 */       if (getShooter() != null && getShooter() instanceof EntityPlayer) {
/* 38 */         EntityPlayer entityplayer = (EntityPlayer)getShooter();
/*    */         
/* 40 */         if (entityplayer.playerConnection.b().isConnected() && entityplayer.world == this.world) {
/*    */           
/* 42 */           CraftPlayer player = entityplayer.getBukkitEntity();
/* 43 */           Location location = getBukkitEntity().getLocation();
/* 44 */           location.setPitch(player.getLocation().getPitch());
/* 45 */           location.setYaw(player.getLocation().getYaw());
/*    */           
/* 47 */           PlayerTeleportEvent teleEvent = new PlayerTeleportEvent((Player)player, player.getLocation(), location, PlayerTeleportEvent.TeleportCause.ENDER_PEARL);
/* 48 */           Bukkit.getPluginManager().callEvent((Event)teleEvent);
/*    */           
/* 50 */           if (!teleEvent.isCancelled() && !entityplayer.playerConnection.isDisconnected()) {
/* 51 */             if (getShooter().am()) {
/* 52 */               getShooter().mount((Entity)null);
/*    */             }
/*    */             
/* 55 */             entityplayer.playerConnection.teleport(teleEvent.getTo());
/* 56 */             (getShooter()).fallDistance = 0.0F;
/* 57 */             CraftEventFactory.entityDamage = this;
/* 58 */             getShooter().damageEntity(DamageSource.FALL, 5.0F);
/* 59 */             CraftEventFactory.entityDamage = null;
/*    */           } 
/*    */         } 
/*    */       } 
/*    */ 
/*    */       
/* 65 */       die();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityEnderPearl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
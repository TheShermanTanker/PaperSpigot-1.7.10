/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.entity.ExplosionPrimeEvent;
/*    */ 
/*    */ public class EntityEnderCrystal
/*    */   extends Entity {
/*    */   public int a;
/*    */   public int b;
/*    */   
/*    */   public EntityEnderCrystal(World world) {
/* 14 */     super(world);
/* 15 */     this.k = true;
/* 16 */     a(2.0F, 2.0F);
/* 17 */     this.height = this.length / 2.0F;
/* 18 */     this.b = 5;
/* 19 */     this.a = this.random.nextInt(100000);
/*    */   }
/*    */   
/*    */   protected boolean g_() {
/* 23 */     return false;
/*    */   }
/*    */   
/*    */   protected void c() {
/* 27 */     this.datawatcher.a(8, Integer.valueOf(this.b));
/*    */   }
/*    */   
/*    */   public void h() {
/* 31 */     this.lastX = this.locX;
/* 32 */     this.lastY = this.locY;
/* 33 */     this.lastZ = this.locZ;
/* 34 */     this.a++;
/* 35 */     this.datawatcher.watch(8, Integer.valueOf(this.b));
/* 36 */     int i = MathHelper.floor(this.locX);
/* 37 */     int j = MathHelper.floor(this.locY);
/* 38 */     int k = MathHelper.floor(this.locZ);
/*    */     
/* 40 */     if (this.world.worldProvider instanceof WorldProviderTheEnd && this.world.getType(i, j, k) != Blocks.FIRE)
/*    */     {
/* 42 */       if (!CraftEventFactory.callBlockIgniteEvent(this.world, i, j, k, this).isCancelled()) {
/* 43 */         this.world.setTypeUpdate(i, j, k, Blocks.FIRE);
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected void b(NBTTagCompound nbttagcompound) {}
/*    */   
/*    */   protected void a(NBTTagCompound nbttagcompound) {}
/*    */   
/*    */   public boolean R() {
/* 54 */     return true;
/*    */   }
/*    */   
/*    */   public boolean damageEntity(DamageSource damagesource, float f) {
/* 58 */     if (isInvulnerable()) {
/* 59 */       return false;
/*    */     }
/* 61 */     if (!this.dead && !this.world.isStatic) {
/*    */       
/* 63 */       if (CraftEventFactory.handleNonLivingEntityDamageEvent(this, damagesource, f)) {
/* 64 */         return false;
/*    */       }
/*    */ 
/*    */       
/* 68 */       this.b = 0;
/* 69 */       if (this.b <= 0) {
/* 70 */         die();
/* 71 */         if (!this.world.isStatic) {
/*    */           
/* 73 */           ExplosionPrimeEvent event = new ExplosionPrimeEvent((Entity)getBukkitEntity(), 6.0F, false);
/* 74 */           this.world.getServer().getPluginManager().callEvent((Event)event);
/* 75 */           if (event.isCancelled()) {
/* 76 */             this.dead = false;
/* 77 */             return false;
/*    */           } 
/* 79 */           this.world.createExplosion(this, this.locX, this.locY, this.locZ, event.getRadius(), event.getFire(), true);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 85 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityEnderCrystal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
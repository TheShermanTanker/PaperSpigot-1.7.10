/*    */ package net.minecraft.server.v1_7_R4;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.player.PlayerShearEntityEvent;
/*    */ 
/*    */ public class EntityMushroomCow extends EntityCow {
/*    */   public EntityMushroomCow(World world) {
/*  8 */     super(world);
/*  9 */     a(0.9F, 1.3F);
/*    */   }
/*    */   
/*    */   public boolean a(EntityHuman entityhuman) {
/* 13 */     ItemStack itemstack = entityhuman.inventory.getItemInHand();
/*    */     
/* 15 */     if (itemstack != null && itemstack.getItem() == Items.BOWL && getAge() >= 0) {
/* 16 */       if (itemstack.count == 1) {
/* 17 */         entityhuman.inventory.setItem(entityhuman.inventory.itemInHandIndex, new ItemStack(Items.MUSHROOM_SOUP));
/* 18 */         return true;
/*    */       } 
/*    */       
/* 21 */       if (entityhuman.inventory.pickup(new ItemStack(Items.MUSHROOM_SOUP)) && !entityhuman.abilities.canInstantlyBuild) {
/* 22 */         entityhuman.inventory.splitStack(entityhuman.inventory.itemInHandIndex, 1);
/* 23 */         return true;
/*    */       } 
/*    */     } 
/*    */     
/* 27 */     if (itemstack != null && itemstack.getItem() == Items.SHEARS && getAge() >= 0) {
/*    */       
/* 29 */       PlayerShearEntityEvent event = new PlayerShearEntityEvent((Player)entityhuman.getBukkitEntity(), (Entity)getBukkitEntity());
/* 30 */       this.world.getServer().getPluginManager().callEvent((Event)event);
/*    */       
/* 32 */       if (event.isCancelled()) {
/* 33 */         return false;
/*    */       }
/*    */ 
/*    */       
/* 37 */       die();
/* 38 */       this.world.addParticle("largeexplode", this.locX, this.locY + (this.length / 2.0F), this.locZ, 0.0D, 0.0D, 0.0D);
/* 39 */       if (!this.world.isStatic) {
/* 40 */         EntityCow entitycow = new EntityCow(this.world);
/*    */         
/* 42 */         entitycow.setPositionRotation(this.locX, this.locY, this.locZ, this.yaw, this.pitch);
/* 43 */         entitycow.setHealth(getHealth());
/* 44 */         entitycow.aM = this.aM;
/* 45 */         this.world.addEntity(entitycow);
/*    */         
/* 47 */         for (int i = 0; i < 5; i++) {
/* 48 */           this.world.addEntity(new EntityItem(this.world, this.locX, this.locY + this.length, this.locZ, new ItemStack(Blocks.RED_MUSHROOM)));
/*    */         }
/*    */         
/* 51 */         itemstack.damage(1, entityhuman);
/* 52 */         makeSound("mob.sheep.shear", 1.0F, 1.0F);
/*    */       } 
/*    */       
/* 55 */       return true;
/*    */     } 
/* 57 */     return super.a(entityhuman);
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityMushroomCow c(EntityAgeable entityageable) {
/* 62 */     return new EntityMushroomCow(this.world);
/*    */   }
/*    */   
/*    */   public EntityCow b(EntityAgeable entityageable) {
/* 66 */     return c(entityageable);
/*    */   }
/*    */   
/*    */   public EntityAgeable createChild(EntityAgeable entityageable) {
/* 70 */     return c(entityageable);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityMushroomCow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
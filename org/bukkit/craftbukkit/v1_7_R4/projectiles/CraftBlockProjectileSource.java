/*     */ package org.bukkit.craftbukkit.v1_7_R4.projectiles;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.server.v1_7_R4.BlockDispenser;
/*     */ import net.minecraft.server.v1_7_R4.Entity;
/*     */ import net.minecraft.server.v1_7_R4.EntityArrow;
/*     */ import net.minecraft.server.v1_7_R4.EntityEgg;
/*     */ import net.minecraft.server.v1_7_R4.EntityEnderPearl;
/*     */ import net.minecraft.server.v1_7_R4.EntityFireball;
/*     */ import net.minecraft.server.v1_7_R4.EntityLargeFireball;
/*     */ import net.minecraft.server.v1_7_R4.EntityPotion;
/*     */ import net.minecraft.server.v1_7_R4.EntityProjectile;
/*     */ import net.minecraft.server.v1_7_R4.EntitySmallFireball;
/*     */ import net.minecraft.server.v1_7_R4.EntitySnowball;
/*     */ import net.minecraft.server.v1_7_R4.EntityThrownExpBottle;
/*     */ import net.minecraft.server.v1_7_R4.EntityWitherSkull;
/*     */ import net.minecraft.server.v1_7_R4.EnumFacing;
/*     */ import net.minecraft.server.v1_7_R4.IPosition;
/*     */ import net.minecraft.server.v1_7_R4.IProjectile;
/*     */ import net.minecraft.server.v1_7_R4.ISourceBlock;
/*     */ import net.minecraft.server.v1_7_R4.MathHelper;
/*     */ import net.minecraft.server.v1_7_R4.SourceBlock;
/*     */ import net.minecraft.server.v1_7_R4.TileEntityDispenser;
/*     */ import net.minecraft.server.v1_7_R4.World;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
/*     */ import org.bukkit.entity.Arrow;
/*     */ import org.bukkit.entity.Egg;
/*     */ import org.bukkit.entity.EnderPearl;
/*     */ import org.bukkit.entity.Fireball;
/*     */ import org.bukkit.entity.Projectile;
/*     */ import org.bukkit.entity.SmallFireball;
/*     */ import org.bukkit.entity.Snowball;
/*     */ import org.bukkit.entity.ThrownExpBottle;
/*     */ import org.bukkit.entity.ThrownPotion;
/*     */ import org.bukkit.entity.WitherSkull;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.projectiles.BlockProjectileSource;
/*     */ import org.bukkit.projectiles.ProjectileSource;
/*     */ import org.bukkit.util.Vector;
/*     */ 
/*     */ public class CraftBlockProjectileSource implements BlockProjectileSource {
/*     */   public CraftBlockProjectileSource(TileEntityDispenser dispenserBlock) {
/*  46 */     this.dispenserBlock = dispenserBlock;
/*     */   }
/*     */ 
/*     */   
/*     */   public Block getBlock() {
/*  51 */     return this.dispenserBlock.getWorld().getWorld().getBlockAt(this.dispenserBlock.x, this.dispenserBlock.y, this.dispenserBlock.z);
/*     */   }
/*     */   private final TileEntityDispenser dispenserBlock;
/*     */   
/*     */   public <T extends Projectile> T launchProjectile(Class<? extends T> projectile) {
/*  56 */     return launchProjectile(projectile, null);
/*     */   }
/*     */   
/*     */   public <T extends Projectile> T launchProjectile(Class<? extends T> projectile, Vector velocity) {
/*     */     EntityLargeFireball entityLargeFireball;
/*  61 */     Validate.isTrue((getBlock().getType() == Material.DISPENSER), "Block is no longer dispenser");
/*     */     
/*  63 */     SourceBlock isourceblock = new SourceBlock(this.dispenserBlock.getWorld(), this.dispenserBlock.x, this.dispenserBlock.y, this.dispenserBlock.z);
/*     */     
/*  65 */     IPosition iposition = BlockDispenser.a((ISourceBlock)isourceblock);
/*  66 */     EnumFacing enumfacing = BlockDispenser.b(isourceblock.h());
/*  67 */     World world = this.dispenserBlock.getWorld();
/*  68 */     Entity launch = null;
/*     */     
/*  70 */     if (Snowball.class.isAssignableFrom(projectile)) {
/*  71 */       EntitySnowball entitySnowball = new EntitySnowball(world, iposition.getX(), iposition.getY(), iposition.getZ());
/*  72 */     } else if (Egg.class.isAssignableFrom(projectile)) {
/*  73 */       EntityEgg entityEgg = new EntityEgg(world, iposition.getX(), iposition.getY(), iposition.getZ());
/*  74 */     } else if (EnderPearl.class.isAssignableFrom(projectile)) {
/*  75 */       EntityEnderPearl entityEnderPearl = new EntityEnderPearl(world);
/*  76 */       entityEnderPearl.setPosition(iposition.getX(), iposition.getY(), iposition.getZ());
/*  77 */     } else if (ThrownExpBottle.class.isAssignableFrom(projectile)) {
/*  78 */       EntityThrownExpBottle entityThrownExpBottle = new EntityThrownExpBottle(world, iposition.getX(), iposition.getY(), iposition.getZ());
/*  79 */     } else if (ThrownPotion.class.isAssignableFrom(projectile)) {
/*  80 */       EntityPotion entityPotion = new EntityPotion(world, iposition.getX(), iposition.getY(), iposition.getZ(), CraftItemStack.asNMSCopy(new ItemStack(Material.POTION, 1)));
/*  81 */     } else if (Arrow.class.isAssignableFrom(projectile)) {
/*  82 */       EntityArrow entityArrow = new EntityArrow(world, iposition.getX(), iposition.getY(), iposition.getZ());
/*  83 */       entityArrow.fromPlayer = 1;
/*  84 */       entityArrow.projectileSource = (ProjectileSource)this;
/*  85 */     } else if (Fireball.class.isAssignableFrom(projectile)) {
/*  86 */       double d0 = iposition.getX() + (enumfacing.getAdjacentX() * 0.3F);
/*  87 */       double d1 = iposition.getY() + (enumfacing.getAdjacentY() * 0.3F);
/*  88 */       double d2 = iposition.getZ() + (enumfacing.getAdjacentZ() * 0.3F);
/*  89 */       Random random = world.random;
/*  90 */       double d3 = random.nextGaussian() * 0.05D + enumfacing.getAdjacentX();
/*  91 */       double d4 = random.nextGaussian() * 0.05D + enumfacing.getAdjacentY();
/*  92 */       double d5 = random.nextGaussian() * 0.05D + enumfacing.getAdjacentZ();
/*     */       
/*  94 */       if (SmallFireball.class.isAssignableFrom(projectile)) {
/*  95 */         EntitySmallFireball entitySmallFireball = new EntitySmallFireball(world, d0, d1, d2, d3, d4, d5);
/*  96 */       } else if (WitherSkull.class.isAssignableFrom(projectile)) {
/*  97 */         EntityWitherSkull entityWitherSkull = new EntityWitherSkull(world);
/*  98 */         entityWitherSkull.setPosition(d0, d1, d2);
/*  99 */         double d6 = MathHelper.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
/*     */         
/* 101 */         ((EntityFireball)entityWitherSkull).dirX = d3 / d6 * 0.1D;
/* 102 */         ((EntityFireball)entityWitherSkull).dirY = d4 / d6 * 0.1D;
/* 103 */         ((EntityFireball)entityWitherSkull).dirZ = d5 / d6 * 0.1D;
/*     */       } else {
/* 105 */         entityLargeFireball = new EntityLargeFireball(world);
/* 106 */         entityLargeFireball.setPosition(d0, d1, d2);
/* 107 */         double d6 = MathHelper.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
/*     */         
/* 109 */         ((EntityFireball)entityLargeFireball).dirX = d3 / d6 * 0.1D;
/* 110 */         ((EntityFireball)entityLargeFireball).dirY = d4 / d6 * 0.1D;
/* 111 */         ((EntityFireball)entityLargeFireball).dirZ = d5 / d6 * 0.1D;
/*     */       } 
/*     */       
/* 114 */       ((EntityFireball)entityLargeFireball).projectileSource = (ProjectileSource)this;
/*     */     } 
/*     */     
/* 117 */     Validate.notNull(entityLargeFireball, "Projectile not supported");
/*     */     
/* 119 */     if (entityLargeFireball instanceof IProjectile) {
/* 120 */       if (entityLargeFireball instanceof EntityProjectile) {
/* 121 */         ((EntityProjectile)entityLargeFireball).projectileSource = (ProjectileSource)this;
/*     */       }
/*     */       
/* 124 */       float a = 6.0F;
/* 125 */       float b = 1.1F;
/* 126 */       if (entityLargeFireball instanceof EntityPotion || entityLargeFireball instanceof ThrownExpBottle) {
/*     */         
/* 128 */         a *= 0.5F;
/* 129 */         b *= 1.25F;
/*     */       } 
/*     */       
/* 132 */       ((IProjectile)entityLargeFireball).shoot(enumfacing.getAdjacentX(), (enumfacing.getAdjacentY() + 0.1F), enumfacing.getAdjacentZ(), b, a);
/*     */     } 
/*     */     
/* 135 */     if (velocity != null) {
/* 136 */       ((Projectile)entityLargeFireball.getBukkitEntity()).setVelocity(velocity);
/*     */     }
/*     */     
/* 139 */     world.addEntity((Entity)entityLargeFireball);
/* 140 */     return (T)entityLargeFireball.getBukkitEntity();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\projectiles\CraftBlockProjectileSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
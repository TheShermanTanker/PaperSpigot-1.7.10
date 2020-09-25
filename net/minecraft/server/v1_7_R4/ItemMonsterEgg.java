/*     */ package net.minecraft.server.v1_7_R4;
/*     */ import org.bukkit.event.entity.CreatureSpawnEvent;
/*     */ 
/*     */ public class ItemMonsterEgg extends Item {
/*     */   public ItemMonsterEgg() {
/*   6 */     a(true);
/*   7 */     a(CreativeModeTab.f);
/*     */   }
/*     */   
/*     */   public String n(ItemStack itemstack) {
/*  11 */     String s = ("" + LocaleI18n.get(getName() + ".name")).trim();
/*  12 */     String s1 = EntityTypes.b(itemstack.getData());
/*     */     
/*  14 */     if (s1 != null) {
/*  15 */       s = s + " " + LocaleI18n.get("entity." + s1 + ".name");
/*     */     }
/*     */     
/*  18 */     return s;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean interactWith(ItemStack itemstack, EntityHuman entityhuman, World world, int i, int j, int k, int l, float f, float f1, float f2) {
/*  23 */     if (world.isStatic || itemstack.getData() == 48 || itemstack.getData() == 49 || itemstack.getData() == 63 || itemstack.getData() == 64) {
/*  24 */       return true;
/*     */     }
/*  26 */     Block block = world.getType(i, j, k);
/*     */     
/*  28 */     i += Facing.b[l];
/*  29 */     j += Facing.c[l];
/*  30 */     k += Facing.d[l];
/*  31 */     double d0 = 0.0D;
/*     */     
/*  33 */     if (l == 1 && block.b() == 11) {
/*  34 */       d0 = 0.5D;
/*     */     }
/*     */     
/*  37 */     Entity entity = a(world, itemstack.getData(), i + 0.5D, j + d0, k + 0.5D);
/*     */     
/*  39 */     if (entity != null) {
/*  40 */       if (entity instanceof EntityLiving && itemstack.hasName()) {
/*  41 */         ((EntityInsentient)entity).setCustomName(itemstack.getName());
/*     */       }
/*     */       
/*  44 */       if (!entityhuman.abilities.canInstantlyBuild) {
/*  45 */         itemstack.count--;
/*     */       }
/*     */     } 
/*     */     
/*  49 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack a(ItemStack itemstack, World world, EntityHuman entityhuman) {
/*  54 */     if (world.isStatic) {
/*  55 */       return itemstack;
/*     */     }
/*  57 */     MovingObjectPosition movingobjectposition = a(world, entityhuman, true);
/*     */     
/*  59 */     if (movingobjectposition == null) {
/*  60 */       return itemstack;
/*     */     }
/*  62 */     if (movingobjectposition.type == EnumMovingObjectType.BLOCK) {
/*  63 */       int i = movingobjectposition.b;
/*  64 */       int j = movingobjectposition.c;
/*  65 */       int k = movingobjectposition.d;
/*     */       
/*  67 */       if (!world.a(entityhuman, i, j, k)) {
/*  68 */         return itemstack;
/*     */       }
/*     */       
/*  71 */       if (!entityhuman.a(i, j, k, movingobjectposition.face, itemstack)) {
/*  72 */         return itemstack;
/*     */       }
/*     */       
/*  75 */       if (world.getType(i, j, k) instanceof BlockFluids) {
/*  76 */         Entity entity = a(world, itemstack.getData(), i, j, k);
/*     */         
/*  78 */         if (entity != null) {
/*  79 */           if (entity instanceof EntityLiving && itemstack.hasName()) {
/*  80 */             ((EntityInsentient)entity).setCustomName(itemstack.getName());
/*     */           }
/*     */           
/*  83 */           if (!entityhuman.abilities.canInstantlyBuild) {
/*  84 */             itemstack.count--;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  90 */     return itemstack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Entity a(World world, int i, double d0, double d1, double d2) {
/*  97 */     return spawnCreature(world, i, d0, d1, d2, CreatureSpawnEvent.SpawnReason.SPAWNER_EGG);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Entity spawnCreature(World world, int i, double d0, double d1, double d2, CreatureSpawnEvent.SpawnReason spawnReason) {
/* 102 */     if (!EntityTypes.eggInfo.containsKey(Integer.valueOf(i))) {
/* 103 */       return null;
/*     */     }
/* 105 */     Entity entity = null;
/*     */     
/* 107 */     for (int j = 0; j < 1; j++) {
/* 108 */       entity = EntityTypes.a(i, world);
/* 109 */       if (entity != null && entity instanceof EntityLiving) {
/* 110 */         EntityInsentient entityinsentient = (EntityInsentient)entity;
/*     */         
/* 112 */         entity.setPositionRotation(d0, d1, d2, MathHelper.g(world.random.nextFloat() * 360.0F), 0.0F);
/* 113 */         entityinsentient.aO = entityinsentient.yaw;
/* 114 */         entityinsentient.aM = entityinsentient.yaw;
/* 115 */         entityinsentient.prepare((GroupDataEntity)null);
/* 116 */         world.addEntity(entity, spawnReason);
/* 117 */         entityinsentient.r();
/*     */       } 
/*     */     } 
/*     */     
/* 121 */     return entity;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ItemMonsterEgg.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
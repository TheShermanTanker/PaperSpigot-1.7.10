/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
/*     */ import org.bukkit.event.player.PlayerBucketEmptyEvent;
/*     */ import org.bukkit.event.player.PlayerBucketFillEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.github.paperspigot.PaperSpigotConfig;
/*     */ 
/*     */ 
/*     */ public class ItemBucket
/*     */   extends Item
/*     */ {
/*     */   private Block a;
/*     */   
/*     */   public ItemBucket(Block block) {
/*  18 */     if ((block == Blocks.LAVA && PaperSpigotConfig.stackableLavaBuckets) || (block == Blocks.WATER && PaperSpigotConfig.stackableWaterBuckets)) {
/*     */       
/*  20 */       this.maxStackSize = Material.BUCKET.getMaxStackSize();
/*     */     } else {
/*  22 */       this.maxStackSize = 1;
/*     */     } 
/*     */     
/*  25 */     this.a = block;
/*  26 */     a(CreativeModeTab.f);
/*     */   }
/*     */   
/*     */   public ItemStack a(ItemStack itemstack, World world, EntityHuman entityhuman) {
/*  30 */     boolean flag = (this.a == Blocks.AIR);
/*  31 */     MovingObjectPosition movingobjectposition = a(world, entityhuman, flag);
/*     */     
/*  33 */     if (movingobjectposition == null) {
/*  34 */       return itemstack;
/*     */     }
/*  36 */     if (movingobjectposition.type == EnumMovingObjectType.BLOCK) {
/*  37 */       int i = movingobjectposition.b;
/*  38 */       int j = movingobjectposition.c;
/*  39 */       int k = movingobjectposition.d;
/*     */       
/*  41 */       if (!world.a(entityhuman, i, j, k)) {
/*  42 */         return itemstack;
/*     */       }
/*     */       
/*  45 */       if (flag) {
/*  46 */         if (!entityhuman.a(i, j, k, movingobjectposition.face, itemstack)) {
/*  47 */           return itemstack;
/*     */         }
/*     */         
/*  50 */         Material material = world.getType(i, j, k).getMaterial();
/*  51 */         int l = world.getData(i, j, k);
/*     */         
/*  53 */         if (material == Material.WATER && l == 0) {
/*     */           
/*  55 */           PlayerBucketFillEvent event = CraftEventFactory.callPlayerBucketFillEvent(entityhuman, i, j, k, -1, itemstack, Items.WATER_BUCKET);
/*     */           
/*  57 */           if (event.isCancelled()) {
/*  58 */             return itemstack;
/*     */           }
/*     */           
/*  61 */           world.setAir(i, j, k);
/*  62 */           return a(itemstack, entityhuman, Items.WATER_BUCKET, event.getItemStack());
/*     */         } 
/*     */         
/*  65 */         if (material == Material.LAVA && l == 0) {
/*     */           
/*  67 */           PlayerBucketFillEvent event = CraftEventFactory.callPlayerBucketFillEvent(entityhuman, i, j, k, -1, itemstack, Items.LAVA_BUCKET);
/*     */           
/*  69 */           if (event.isCancelled()) {
/*  70 */             return itemstack;
/*     */           }
/*     */           
/*  73 */           world.setAir(i, j, k);
/*  74 */           return a(itemstack, entityhuman, Items.LAVA_BUCKET, event.getItemStack());
/*     */         } 
/*     */       } else {
/*  77 */         if (this.a == Blocks.AIR) {
/*     */           
/*  79 */           PlayerBucketEmptyEvent playerBucketEmptyEvent = CraftEventFactory.callPlayerBucketEmptyEvent(entityhuman, i, j, k, movingobjectposition.face, itemstack);
/*     */           
/*  81 */           if (playerBucketEmptyEvent.isCancelled()) {
/*  82 */             return itemstack;
/*     */           }
/*     */           
/*  85 */           return CraftItemStack.asNMSCopy(playerBucketEmptyEvent.getItemStack());
/*     */         } 
/*     */         
/*  88 */         int clickedX = i, clickedY = j, clickedZ = k;
/*     */ 
/*     */         
/*  91 */         if (movingobjectposition.face == 0) {
/*  92 */           j--;
/*     */         }
/*     */         
/*  95 */         if (movingobjectposition.face == 1) {
/*  96 */           j++;
/*     */         }
/*     */         
/*  99 */         if (movingobjectposition.face == 2) {
/* 100 */           k--;
/*     */         }
/*     */         
/* 103 */         if (movingobjectposition.face == 3) {
/* 104 */           k++;
/*     */         }
/*     */         
/* 107 */         if (movingobjectposition.face == 4) {
/* 108 */           i--;
/*     */         }
/*     */         
/* 111 */         if (movingobjectposition.face == 5) {
/* 112 */           i++;
/*     */         }
/*     */         
/* 115 */         if (!entityhuman.a(i, j, k, movingobjectposition.face, itemstack)) {
/* 116 */           return itemstack;
/*     */         }
/*     */ 
/*     */         
/* 120 */         PlayerBucketEmptyEvent event = CraftEventFactory.callPlayerBucketEmptyEvent(entityhuman, clickedX, clickedY, clickedZ, movingobjectposition.face, itemstack);
/*     */         
/* 122 */         if (event.isCancelled()) {
/* 123 */           return itemstack;
/*     */         }
/*     */ 
/*     */         
/* 127 */         if (a(world, i, j, k) && !entityhuman.abilities.canInstantlyBuild) {
/*     */           
/* 129 */           if ((this == Items.LAVA_BUCKET && PaperSpigotConfig.stackableLavaBuckets) || (this == Items.WATER_BUCKET && PaperSpigotConfig.stackableWaterBuckets)) {
/*     */             
/* 131 */             itemstack.count--;
/* 132 */             if (itemstack.count <= 0) {
/* 133 */               return CraftItemStack.asNMSCopy(event.getItemStack());
/*     */             }
/* 135 */             if (!entityhuman.inventory.pickup(CraftItemStack.asNMSCopy(event.getItemStack()))) {
/* 136 */               entityhuman.drop(CraftItemStack.asNMSCopy(event.getItemStack()), false);
/*     */             }
/* 138 */             return itemstack;
/*     */           } 
/*     */           
/* 141 */           return CraftItemStack.asNMSCopy(event.getItemStack());
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 146 */     return itemstack;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private ItemStack a(ItemStack itemstack, EntityHuman entityhuman, Item item, ItemStack result) {
/* 152 */     if (entityhuman.abilities.canInstantlyBuild)
/* 153 */       return itemstack; 
/* 154 */     if (--itemstack.count <= 0) {
/* 155 */       return CraftItemStack.asNMSCopy(result);
/*     */     }
/* 157 */     if (!entityhuman.inventory.pickup(CraftItemStack.asNMSCopy(result))) {
/* 158 */       entityhuman.drop(CraftItemStack.asNMSCopy(result), false);
/*     */     }
/*     */     
/* 161 */     return itemstack;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean a(World world, int i, int j, int k) {
/* 166 */     if (this.a == Blocks.AIR) {
/* 167 */       return false;
/*     */     }
/* 169 */     Material material = world.getType(i, j, k).getMaterial();
/* 170 */     boolean flag = !material.isBuildable();
/*     */     
/* 172 */     if (!world.isEmpty(i, j, k) && !flag) {
/* 173 */       return false;
/*     */     }
/* 175 */     if (world.worldProvider.f && this.a == Blocks.WATER) {
/* 176 */       world.makeSound((i + 0.5F), (j + 0.5F), (k + 0.5F), "random.fizz", 0.5F, 2.6F + (world.random.nextFloat() - world.random.nextFloat()) * 0.8F);
/*     */       
/* 178 */       for (int l = 0; l < 8; l++) {
/* 179 */         world.addParticle("largesmoke", i + Math.random(), j + Math.random(), k + Math.random(), 0.0D, 0.0D, 0.0D);
/*     */       }
/*     */     } else {
/* 182 */       if (!world.isStatic && flag && !material.isLiquid()) {
/* 183 */         world.setAir(i, j, k, true);
/*     */       }
/*     */       
/* 186 */       world.setTypeAndData(i, j, k, this.a, 0, 3);
/*     */     } 
/*     */     
/* 189 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ItemBucket.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
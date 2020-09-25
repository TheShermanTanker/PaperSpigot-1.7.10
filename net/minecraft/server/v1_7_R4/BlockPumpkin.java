/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.util.BlockStateListPopulator;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.block.BlockRedstoneEvent;
/*    */ import org.bukkit.event.entity.CreatureSpawnEvent;
/*    */ 
/*    */ public class BlockPumpkin extends BlockDirectional {
/*    */   private boolean a;
/*    */   
/*    */   protected BlockPumpkin(boolean flag) {
/* 14 */     super(Material.PUMPKIN);
/* 15 */     a(true);
/* 16 */     this.a = flag;
/* 17 */     a(CreativeModeTab.b);
/*    */   }
/*    */   
/*    */   public void onPlace(World world, int i, int j, int k) {
/* 21 */     super.onPlace(world, i, j, k);
/* 22 */     if (world.getType(i, j - 1, k) == Blocks.SNOW_BLOCK && world.getType(i, j - 2, k) == Blocks.SNOW_BLOCK) {
/* 23 */       if (!world.isStatic) {
/*    */         
/* 25 */         BlockStateListPopulator blockList = new BlockStateListPopulator((World)world.getWorld());
/*    */         
/* 27 */         blockList.setTypeId(i, j, k, 0);
/* 28 */         blockList.setTypeId(i, j - 1, k, 0);
/* 29 */         blockList.setTypeId(i, j - 2, k, 0);
/* 30 */         EntitySnowman entitysnowman = new EntitySnowman(world);
/*    */         
/* 32 */         entitysnowman.setPositionRotation(i + 0.5D, j - 1.95D, k + 0.5D, 0.0F, 0.0F);
/* 33 */         if (world.addEntity(entitysnowman, CreatureSpawnEvent.SpawnReason.BUILD_SNOWMAN)) {
/* 34 */           blockList.updateList();
/*    */         }
/*    */       } 
/*    */ 
/*    */       
/* 39 */       for (int l = 0; l < 120; l++) {
/* 40 */         world.addParticle("snowshovel", i + world.random.nextDouble(), (j - 2) + world.random.nextDouble() * 2.5D, k + world.random.nextDouble(), 0.0D, 0.0D, 0.0D);
/*    */       }
/* 42 */     } else if (world.getType(i, j - 1, k) == Blocks.IRON_BLOCK && world.getType(i, j - 2, k) == Blocks.IRON_BLOCK) {
/* 43 */       boolean flag = (world.getType(i - 1, j - 1, k) == Blocks.IRON_BLOCK && world.getType(i + 1, j - 1, k) == Blocks.IRON_BLOCK);
/* 44 */       boolean flag1 = (world.getType(i, j - 1, k - 1) == Blocks.IRON_BLOCK && world.getType(i, j - 1, k + 1) == Blocks.IRON_BLOCK);
/*    */       
/* 46 */       if (flag || flag1) {
/*    */         
/* 48 */         BlockStateListPopulator blockList = new BlockStateListPopulator((World)world.getWorld());
/*    */         
/* 50 */         blockList.setTypeId(i, j, k, 0);
/* 51 */         blockList.setTypeId(i, j - 1, k, 0);
/* 52 */         blockList.setTypeId(i, j - 2, k, 0);
/* 53 */         if (flag) {
/* 54 */           blockList.setTypeId(i - 1, j - 1, k, 0);
/* 55 */           blockList.setTypeId(i + 1, j - 1, k, 0);
/*    */         } else {
/* 57 */           blockList.setTypeId(i, j - 1, k - 1, 0);
/* 58 */           blockList.setTypeId(i, j - 1, k + 1, 0);
/*    */         } 
/*    */         
/* 61 */         EntityIronGolem entityirongolem = new EntityIronGolem(world);
/*    */         
/* 63 */         entityirongolem.setPlayerCreated(true);
/* 64 */         entityirongolem.setPositionRotation(i + 0.5D, j - 1.95D, k + 0.5D, 0.0F, 0.0F);
/* 65 */         if (world.addEntity(entityirongolem, CreatureSpawnEvent.SpawnReason.BUILD_IRONGOLEM)) {
/* 66 */           for (int i1 = 0; i1 < 120; i1++) {
/* 67 */             world.addParticle("snowballpoof", i + world.random.nextDouble(), (j - 2) + world.random.nextDouble() * 3.9D, k + world.random.nextDouble(), 0.0D, 0.0D, 0.0D);
/*    */           }
/*    */           
/* 70 */           blockList.updateList();
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canPlace(World world, int i, int j, int k) {
/* 78 */     return ((world.getType(i, j, k)).material.isReplaceable() && World.a(world, i, j - 1, k));
/*    */   }
/*    */   
/*    */   public void postPlace(World world, int i, int j, int k, EntityLiving entityliving, ItemStack itemstack) {
/* 82 */     int l = MathHelper.floor((entityliving.yaw * 4.0F / 360.0F) + 2.5D) & 0x3;
/*    */     
/* 84 */     world.setData(i, j, k, l, 2);
/*    */   }
/*    */ 
/*    */   
/*    */   public void doPhysics(World world, int i, int j, int k, Block block) {
/* 89 */     if (block != null && block.isPowerSource()) {
/* 90 */       Block bukkitBlock = world.getWorld().getBlockAt(i, j, k);
/* 91 */       int power = bukkitBlock.getBlockPower();
/*    */       
/* 93 */       BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(bukkitBlock, power, power);
/* 94 */       world.getServer().getPluginManager().callEvent((Event)eventRedstone);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockPumpkin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
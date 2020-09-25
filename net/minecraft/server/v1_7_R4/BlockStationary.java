/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*    */ 
/*    */ public class BlockStationary
/*    */   extends BlockFluids
/*    */ {
/*    */   protected BlockStationary(Material material) {
/* 10 */     super(material);
/* 11 */     a(false);
/* 12 */     if (material == Material.LAVA) {
/* 13 */       a(true);
/*    */     }
/*    */   }
/*    */   
/*    */   public void doPhysics(World world, int i, int j, int k, Block block) {
/* 18 */     super.doPhysics(world, i, j, k, block);
/* 19 */     if (world.getType(i, j, k) == this) {
/* 20 */       n(world, i, j, k);
/*    */     }
/*    */   }
/*    */   
/*    */   private void n(World world, int i, int j, int k) {
/* 25 */     int l = world.getData(i, j, k);
/*    */     
/* 27 */     world.setTypeAndData(i, j, k, Block.getById(Block.getId(this) - 1), l, 2);
/* 28 */     world.a(i, j, k, Block.getById(Block.getId(this) - 1), a(world));
/*    */   }
/*    */   
/*    */   public void a(World world, int i, int j, int k, Random random) {
/* 32 */     if (this.material == Material.LAVA) {
/* 33 */       int l = random.nextInt(3);
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 38 */       int x = i;
/* 39 */       int y = j;
/* 40 */       int z = k;
/*    */       
/*    */       int i1;
/* 43 */       for (i1 = 0; i1 < l; i1++) {
/* 44 */         i += random.nextInt(3) - 1;
/* 45 */         j++;
/* 46 */         k += random.nextInt(3) - 1;
/* 47 */         Block block = world.getType(i, j, k);
/*    */         
/* 49 */         if (block.material == Material.AIR) {
/* 50 */           if (o(world, i - 1, j, k) || o(world, i + 1, j, k) || o(world, i, j, k - 1) || o(world, i, j, k + 1) || o(world, i, j - 1, k) || o(world, i, j + 1, k))
/*    */           {
/* 52 */             if (world.getType(i, j, k) == Blocks.FIRE || 
/* 53 */               !CraftEventFactory.callBlockIgniteEvent(world, i, j, k, x, y, z).isCancelled()) {
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */               
/* 59 */               world.setTypeUpdate(i, j, k, Blocks.FIRE);
/*    */               return;
/*    */             }  } 
/* 62 */         } else if (block.material.isSolid()) {
/*    */           return;
/*    */         } 
/*    */       } 
/*    */       
/* 67 */       if (l == 0) {
/* 68 */         i1 = i;
/* 69 */         int j1 = k;
/*    */         
/* 71 */         for (int k1 = 0; k1 < 3; k1++) {
/* 72 */           i = i1 + random.nextInt(3) - 1;
/* 73 */           k = j1 + random.nextInt(3) - 1;
/* 74 */           if (world.isEmpty(i, j + 1, k) && o(world, i, j, k))
/*    */           {
/* 76 */             if (world.getType(i, j + 1, k) == Blocks.FIRE || 
/* 77 */               !CraftEventFactory.callBlockIgniteEvent(world, i, j + 1, k, x, y, z).isCancelled())
/*    */             {
/*    */ 
/*    */ 
/*    */ 
/*    */               
/* 83 */               world.setTypeUpdate(i, j + 1, k, Blocks.FIRE); } 
/*    */           }
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   private boolean o(World world, int i, int j, int k) {
/* 91 */     return world.getType(i, j, k).getMaterial().isBurnable();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockStationary.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.World;
/*    */ 
/*    */ public class BlockFalling
/*    */   extends Block {
/*    */   public BlockFalling() {
/* 10 */     super(Material.SAND);
/* 11 */     a(CreativeModeTab.b);
/*    */   }
/*    */   public static boolean instaFall;
/*    */   public BlockFalling(Material material) {
/* 15 */     super(material);
/*    */   }
/*    */   
/*    */   public void onPlace(World world, int i, int j, int k) {
/* 19 */     world.a(i, j, k, this, a(world));
/*    */   }
/*    */   
/*    */   public void doPhysics(World world, int i, int j, int k, Block block) {
/* 23 */     world.a(i, j, k, this, a(world));
/*    */   }
/*    */   
/*    */   public void a(World world, int i, int j, int k, Random random) {
/* 27 */     if (!world.isStatic) {
/* 28 */       m(world, i, j, k);
/*    */     }
/*    */   }
/*    */   
/*    */   private void m(World world, int i, int j, int k) {
/* 33 */     if (canFall(world, i, j - 1, k) && j >= 0) {
/* 34 */       byte b0 = 32;
/*    */       
/* 36 */       if (!instaFall && world.b(i - b0, j - b0, k - b0, i + b0, j + b0, k + b0)) {
/* 37 */         if (!world.isStatic) {
/*    */           
/* 39 */           Location loc = new Location((World)world.getWorld(), (i + 0.5F), (j + 0.5F), (k + 0.5F));
/* 40 */           EntityFallingBlock entityfallingblock = new EntityFallingBlock(loc, world, (i + 0.5F), (j + 0.5F), (k + 0.5F), this, world.getData(i, j, k));
/*    */ 
/*    */           
/* 43 */           a(entityfallingblock);
/* 44 */           world.addEntity(entityfallingblock);
/*    */         } 
/*    */       } else {
/* 47 */         world.setAir(i, j, k);
/*    */         
/* 49 */         while (canFall(world, i, j - 1, k) && j > 0) {
/* 50 */           j--;
/*    */         }
/*    */         
/* 53 */         if (j > 0) {
/* 54 */           world.setTypeUpdate(i, j, k, this);
/*    */         }
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   protected void a(EntityFallingBlock entityfallingblock) {}
/*    */   
/*    */   public int a(World world) {
/* 63 */     return 2;
/*    */   }
/*    */   
/*    */   public static boolean canFall(World world, int i, int j, int k) {
/* 67 */     Block block = world.getType(i, j, k);
/*    */     
/* 69 */     if (block.material == Material.AIR)
/* 70 */       return true; 
/* 71 */     if (block == Blocks.FIRE) {
/* 72 */       return true;
/*    */     }
/* 74 */     Material material = block.material;
/*    */     
/* 76 */     return (material == Material.WATER) ? true : ((material == Material.LAVA));
/*    */   }
/*    */   
/*    */   public void a(World world, int i, int j, int k, int l) {}
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockFalling.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
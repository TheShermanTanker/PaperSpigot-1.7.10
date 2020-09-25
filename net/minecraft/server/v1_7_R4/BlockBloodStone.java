/*    */ package net.minecraft.server.v1_7_R4;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.block.BlockRedstoneEvent;
/*    */ 
/*    */ public class BlockBloodStone extends Block {
/*    */   public BlockBloodStone() {
/*  8 */     super(Material.STONE);
/*  9 */     a(CreativeModeTab.b);
/*    */   }
/*    */   
/*    */   public MaterialMapColor f(int i) {
/* 13 */     return MaterialMapColor.K;
/*    */   }
/*    */ 
/*    */   
/*    */   public void doPhysics(World world, int i, int j, int k, int l) {
/* 18 */     if (Block.getById(l) != null && Block.getById(l).isPowerSource()) {
/* 19 */       Block block = world.getWorld().getBlockAt(i, j, k);
/* 20 */       int power = block.getBlockPower();
/*    */       
/* 22 */       BlockRedstoneEvent event = new BlockRedstoneEvent(block, power, power);
/* 23 */       world.getServer().getPluginManager().callEvent((Event)event);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockBloodStone.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
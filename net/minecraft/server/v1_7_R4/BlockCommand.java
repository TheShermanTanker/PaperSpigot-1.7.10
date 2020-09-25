/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.block.BlockRedstoneEvent;
/*    */ 
/*    */ public class BlockCommand extends BlockContainer {
/*    */   public BlockCommand() {
/* 10 */     super(Material.ORE);
/*    */   }
/*    */   
/*    */   public TileEntity a(World world, int i) {
/* 14 */     return new TileEntityCommand();
/*    */   }
/*    */   
/*    */   public void doPhysics(World world, int i, int j, int k, Block block) {
/* 18 */     if (!world.isStatic) {
/* 19 */       boolean flag = world.isBlockIndirectlyPowered(i, j, k);
/* 20 */       int l = world.getData(i, j, k);
/* 21 */       boolean flag1 = ((l & 0x1) != 0);
/*    */ 
/*    */       
/* 24 */       Block bukkitBlock = world.getWorld().getBlockAt(i, j, k);
/* 25 */       int old = flag1 ? 15 : 0;
/* 26 */       int current = flag ? 15 : 0;
/*    */       
/* 28 */       BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(bukkitBlock, old, current);
/* 29 */       world.getServer().getPluginManager().callEvent((Event)eventRedstone);
/*    */ 
/*    */       
/* 32 */       if (eventRedstone.getNewCurrent() > 0 && eventRedstone.getOldCurrent() <= 0) {
/* 33 */         world.setData(i, j, k, l | 0x1, 4);
/* 34 */         world.a(i, j, k, this, a(world));
/* 35 */       } else if (eventRedstone.getNewCurrent() <= 0 && eventRedstone.getOldCurrent() > 0) {
/* 36 */         world.setData(i, j, k, l & 0xFFFFFFFE, 4);
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public void a(World world, int i, int j, int k, Random random) {
/* 42 */     TileEntity tileentity = world.getTileEntity(i, j, k);
/*    */     
/* 44 */     if (tileentity != null && tileentity instanceof TileEntityCommand) {
/* 45 */       CommandBlockListenerAbstract commandblocklistenerabstract = ((TileEntityCommand)tileentity).getCommandBlock();
/*    */       
/* 47 */       commandblocklistenerabstract.a(world);
/* 48 */       world.updateAdjacentComparators(i, j, k, this);
/*    */     } 
/*    */   }
/*    */   
/*    */   public int a(World world) {
/* 53 */     return 1;
/*    */   }
/*    */   
/*    */   public boolean interact(World world, int i, int j, int k, EntityHuman entityhuman, int l, float f, float f1, float f2) {
/* 57 */     TileEntityCommand tileentitycommand = (TileEntityCommand)world.getTileEntity(i, j, k);
/*    */     
/* 59 */     if (tileentitycommand != null) {
/* 60 */       entityhuman.a(tileentitycommand);
/*    */     }
/*    */     
/* 63 */     return true;
/*    */   }
/*    */   
/*    */   public boolean isComplexRedstone() {
/* 67 */     return true;
/*    */   }
/*    */   
/*    */   public int g(World world, int i, int j, int k, int l) {
/* 71 */     TileEntity tileentity = world.getTileEntity(i, j, k);
/*    */     
/* 73 */     return (tileentity != null && tileentity instanceof TileEntityCommand) ? ((TileEntityCommand)tileentity).getCommandBlock().g() : 0;
/*    */   }
/*    */   
/*    */   public void postPlace(World world, int i, int j, int k, EntityLiving entityliving, ItemStack itemstack) {
/* 77 */     TileEntityCommand tileentitycommand = (TileEntityCommand)world.getTileEntity(i, j, k);
/*    */     
/* 79 */     if (itemstack.hasName()) {
/* 80 */       tileentitycommand.getCommandBlock().setName(itemstack.getName());
/*    */     }
/*    */   }
/*    */   
/*    */   public int a(Random random) {
/* 85 */     return 0;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
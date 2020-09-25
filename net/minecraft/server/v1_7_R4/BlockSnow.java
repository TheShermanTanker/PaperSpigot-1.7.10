/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*    */ 
/*    */ public class BlockSnow extends Block {
/*    */   protected BlockSnow() {
/*  8 */     super(Material.PACKED_ICE);
/*  9 */     a(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
/* 10 */     a(true);
/* 11 */     a(CreativeModeTab.c);
/* 12 */     b(0);
/*    */   }
/*    */   
/*    */   public AxisAlignedBB a(World world, int i, int j, int k) {
/* 16 */     int l = world.getData(i, j, k) & 0x7;
/* 17 */     float f = 0.125F;
/*    */     
/* 19 */     return AxisAlignedBB.a(i + this.minX, j + this.minY, k + this.minZ, i + this.maxX, (j + l * f), k + this.maxZ);
/*    */   }
/*    */   
/*    */   public boolean c() {
/* 23 */     return false;
/*    */   }
/*    */   
/*    */   public boolean d() {
/* 27 */     return false;
/*    */   }
/*    */   
/*    */   public void g() {
/* 31 */     b(0);
/*    */   }
/*    */   
/*    */   public void updateShape(IBlockAccess iblockaccess, int i, int j, int k) {
/* 35 */     b(iblockaccess.getData(i, j, k));
/*    */   }
/*    */   
/*    */   protected void b(int i) {
/* 39 */     int j = i & 0x7;
/* 40 */     float f = (2 * (1 + j)) / 16.0F;
/*    */     
/* 42 */     a(0.0F, 0.0F, 0.0F, 1.0F, f, 1.0F);
/*    */   }
/*    */   
/*    */   public boolean canPlace(World world, int i, int j, int k) {
/* 46 */     Block block = world.getType(i, j - 1, k);
/*    */     
/* 48 */     return (block != Blocks.ICE && block != Blocks.PACKED_ICE) ? ((block.getMaterial() == Material.LEAVES) ? true : ((block == this && (world.getData(i, j - 1, k) & 0x7) == 7) ? true : ((block.c() && block.material.isSolid())))) : false;
/*    */   }
/*    */   
/*    */   public void doPhysics(World world, int i, int j, int k, Block block) {
/* 52 */     m(world, i, j, k);
/*    */   }
/*    */   
/*    */   private boolean m(World world, int i, int j, int k) {
/* 56 */     if (!canPlace(world, i, j, k)) {
/* 57 */       b(world, i, j, k, world.getData(i, j, k), 0);
/* 58 */       world.setAir(i, j, k);
/* 59 */       return false;
/*    */     } 
/* 61 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(World world, EntityHuman entityhuman, int i, int j, int k, int l) {
/* 66 */     int i1 = l & 0x7;
/*    */     
/* 68 */     a(world, i, j, k, new ItemStack(Items.SNOW_BALL, i1 + 1, 0));
/* 69 */     world.setAir(i, j, k);
/* 70 */     entityhuman.a(StatisticList.MINE_BLOCK_COUNT[Block.getId(this)], 1);
/*    */   }
/*    */   
/*    */   public Item getDropType(int i, Random random, int j) {
/* 74 */     return Items.SNOW_BALL;
/*    */   }
/*    */   
/*    */   public int a(Random random) {
/* 78 */     return 0;
/*    */   }
/*    */   
/*    */   public void a(World world, int i, int j, int k, Random random) {
/* 82 */     if (world.b(EnumSkyBlock.BLOCK, i, j, k) > 11) {
/*    */       
/* 84 */       if (CraftEventFactory.callBlockFadeEvent(world.getWorld().getBlockAt(i, j, k), Blocks.AIR).isCancelled()) {
/*    */         return;
/*    */       }
/*    */ 
/*    */       
/* 89 */       b(world, i, j, k, world.getData(i, j, k), 0);
/* 90 */       world.setAir(i, j, k);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockSnow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
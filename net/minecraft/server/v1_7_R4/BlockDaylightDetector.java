/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Random;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*    */ 
/*    */ public class BlockDaylightDetector extends BlockContainer {
/*  7 */   private IIcon[] a = new IIcon[2];
/*    */   
/*    */   public BlockDaylightDetector() {
/* 10 */     super(Material.WOOD);
/* 11 */     a(0.0F, 0.0F, 0.0F, 1.0F, 0.375F, 1.0F);
/* 12 */     a(CreativeModeTab.d);
/*    */   }
/*    */   
/*    */   public void updateShape(IBlockAccess iblockaccess, int i, int j, int k) {
/* 16 */     a(0.0F, 0.0F, 0.0F, 1.0F, 0.375F, 1.0F);
/*    */   }
/*    */   
/*    */   public int b(IBlockAccess iblockaccess, int i, int j, int k, int l) {
/* 20 */     return iblockaccess.getData(i, j, k);
/*    */   }
/*    */   
/*    */   public void a(World world, int i, int j, int k, Random random) {}
/*    */   
/*    */   public void doPhysics(World world, int i, int j, int k, Block block) {}
/*    */   
/*    */   public void onPlace(World world, int i, int j, int k) {}
/*    */   
/*    */   public void e(World world, int i, int j, int k) {
/* 30 */     if (!world.worldProvider.g) {
/* 31 */       int l = world.getData(i, j, k);
/* 32 */       int i1 = world.b(EnumSkyBlock.SKY, i, j, k) - world.j;
/* 33 */       float f = world.d(1.0F);
/*    */       
/* 35 */       if (f < 3.1415927F) {
/* 36 */         f += (0.0F - f) * 0.2F;
/*    */       } else {
/* 38 */         f += (6.2831855F - f) * 0.2F;
/*    */       } 
/*    */ 
/*    */       
/* 42 */       if (world.paperSpigotConfig.invertedDaylightDetectors) {
/* 43 */         i1 = Math.round(i1 * MathHelper.cos(f) * -1.0F + 15.0F);
/* 44 */         if (i1 < 10) {
/* 45 */           i1 = 0;
/*    */         }
/*    */         
/* 48 */         if (i1 > 9) {
/* 49 */           i1 = 15;
/*    */         }
/*    */       } else {
/* 52 */         i1 = Math.round(i1 * MathHelper.cos(f));
/* 53 */         if (i1 < 0) {
/* 54 */           i1 = 0;
/*    */         }
/*    */         
/* 57 */         if (i1 > 15) {
/* 58 */           i1 = 15;
/*    */         }
/*    */       } 
/*    */ 
/*    */       
/* 63 */       if (l != i1) {
/* 64 */         i1 = CraftEventFactory.callRedstoneChange(world, i, j, k, l, i1).getNewCurrent();
/* 65 */         world.setData(i, j, k, i1, 3);
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean d() {
/* 71 */     return false;
/*    */   }
/*    */   
/*    */   public boolean c() {
/* 75 */     return false;
/*    */   }
/*    */   
/*    */   public boolean isPowerSource() {
/* 79 */     return true;
/*    */   }
/*    */   
/*    */   public TileEntity a(World world, int i) {
/* 83 */     return new TileEntityLightDetector();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockDaylightDetector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class BlockAnvil
/*    */   extends BlockFalling {
/*  5 */   public static final String[] a = new String[] { "intact", "slightlyDamaged", "veryDamaged" };
/*  6 */   private static final String[] N = new String[] { "anvil_top_damaged_0", "anvil_top_damaged_1", "anvil_top_damaged_2" };
/*    */   
/*    */   protected BlockAnvil() {
/*  9 */     super(Material.HEAVY);
/* 10 */     g(0);
/* 11 */     a(CreativeModeTab.c);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AxisAlignedBB a(World world, int i, int j, int k) {
/* 18 */     updateShape(world, i, j, k);
/* 19 */     return super.a(world, i, j, k);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean d() {
/* 24 */     return false;
/*    */   }
/*    */   
/*    */   public boolean c() {
/* 28 */     return false;
/*    */   }
/*    */   
/*    */   public void postPlace(World world, int i, int j, int k, EntityLiving entityliving, ItemStack itemstack) {
/* 32 */     int l = MathHelper.floor((entityliving.yaw * 4.0F / 360.0F) + 0.5D) & 0x3;
/* 33 */     int i1 = world.getData(i, j, k) >> 2;
/*    */     
/* 35 */     l++;
/* 36 */     l %= 4;
/* 37 */     if (l == 0) {
/* 38 */       world.setData(i, j, k, 0x2 | i1 << 2, 2);
/*    */     }
/*    */     
/* 41 */     if (l == 1) {
/* 42 */       world.setData(i, j, k, 0x3 | i1 << 2, 2);
/*    */     }
/*    */     
/* 45 */     if (l == 2) {
/* 46 */       world.setData(i, j, k, 0x0 | i1 << 2, 2);
/*    */     }
/*    */     
/* 49 */     if (l == 3) {
/* 50 */       world.setData(i, j, k, 0x1 | i1 << 2, 2);
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean interact(World world, int i, int j, int k, EntityHuman entityhuman, int l, float f, float f1, float f2) {
/* 55 */     if (world.isStatic) {
/* 56 */       return true;
/*    */     }
/* 58 */     entityhuman.openAnvil(i, j, k);
/* 59 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int b() {
/* 64 */     return 35;
/*    */   }
/*    */   
/*    */   public int getDropData(int i) {
/* 68 */     return i >> 2;
/*    */   }
/*    */   
/*    */   public void updateShape(IBlockAccess iblockaccess, int i, int j, int k) {
/* 72 */     int l = iblockaccess.getData(i, j, k) & 0x3;
/*    */     
/* 74 */     if (l != 3 && l != 1) {
/* 75 */       a(0.125F, 0.0F, 0.0F, 0.875F, 1.0F, 1.0F);
/*    */     } else {
/* 77 */       a(0.0F, 0.0F, 0.125F, 1.0F, 1.0F, 0.875F);
/*    */     } 
/*    */   }
/*    */   
/*    */   protected void a(EntityFallingBlock entityfallingblock) {
/* 82 */     entityfallingblock.a(true);
/*    */   }
/*    */   
/*    */   public void a(World world, int i, int j, int k, int l) {
/* 86 */     world.triggerEffect(1022, i, j, k, 0);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockAnvil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.block.BlockRedstoneEvent;
/*     */ 
/*     */ public class BlockMinecartDetector extends BlockMinecartTrackAbstract {
/*     */   public BlockMinecartDetector() {
/*  11 */     super(true);
/*  12 */     a(true);
/*     */   }
/*     */   
/*     */   public int a(World world) {
/*  16 */     return 20;
/*     */   }
/*     */   
/*     */   public boolean isPowerSource() {
/*  20 */     return true;
/*     */   }
/*     */   
/*     */   public void a(World world, int i, int j, int k, Entity entity) {
/*  24 */     if (!world.isStatic) {
/*  25 */       int l = world.getData(i, j, k);
/*     */       
/*  27 */       if ((l & 0x8) == 0) {
/*  28 */         a(world, i, j, k, l);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void a(World world, int i, int j, int k, Random random) {
/*  34 */     if (!world.isStatic) {
/*  35 */       int l = world.getData(i, j, k);
/*     */       
/*  37 */       if ((l & 0x8) != 0) {
/*  38 */         a(world, i, j, k, l);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public int b(IBlockAccess iblockaccess, int i, int j, int k, int l) {
/*  44 */     return ((iblockaccess.getData(i, j, k) & 0x8) != 0) ? 15 : 0;
/*     */   }
/*     */   
/*     */   public int c(IBlockAccess iblockaccess, int i, int j, int k, int l) {
/*  48 */     return ((iblockaccess.getData(i, j, k) & 0x8) == 0) ? 0 : ((l == 1) ? 15 : 0);
/*     */   }
/*     */   
/*     */   private void a(World world, int i, int j, int k, int l) {
/*  52 */     boolean flag = ((l & 0x8) != 0);
/*  53 */     boolean flag1 = false;
/*  54 */     float f = 0.125F;
/*  55 */     List list = world.a(EntityMinecartAbstract.class, AxisAlignedBB.a((i + f), j, (k + f), ((i + 1) - f), ((j + 1) - f), ((k + 1) - f)));
/*     */     
/*  57 */     if (!list.isEmpty()) {
/*  58 */       flag1 = true;
/*     */     }
/*     */ 
/*     */     
/*  62 */     if (flag != flag1) {
/*  63 */       Block block = world.getWorld().getBlockAt(i, j, k);
/*     */       
/*  65 */       BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, flag ? 15 : 0, flag1 ? 15 : 0);
/*  66 */       world.getServer().getPluginManager().callEvent((Event)eventRedstone);
/*     */       
/*  68 */       flag1 = (eventRedstone.getNewCurrent() > 0);
/*     */     } 
/*     */ 
/*     */     
/*  72 */     if (flag1 && !flag) {
/*  73 */       world.setData(i, j, k, l | 0x8, 3);
/*  74 */       world.applyPhysics(i, j, k, this);
/*  75 */       world.applyPhysics(i, j - 1, k, this);
/*  76 */       world.c(i, j, k, i, j, k);
/*     */     } 
/*     */     
/*  79 */     if (!flag1 && flag) {
/*  80 */       world.setData(i, j, k, l & 0x7, 3);
/*  81 */       world.applyPhysics(i, j, k, this);
/*  82 */       world.applyPhysics(i, j - 1, k, this);
/*  83 */       world.c(i, j, k, i, j, k);
/*     */     } 
/*     */     
/*  86 */     if (flag1) {
/*  87 */       world.a(i, j, k, this, a(world));
/*     */     }
/*     */     
/*  90 */     world.updateAdjacentComparators(i, j, k, this);
/*     */   }
/*     */   
/*     */   public void onPlace(World world, int i, int j, int k) {
/*  94 */     super.onPlace(world, i, j, k);
/*  95 */     a(world, i, j, k, world.getData(i, j, k));
/*     */   }
/*     */   
/*     */   public boolean isComplexRedstone() {
/*  99 */     return true;
/*     */   }
/*     */   
/*     */   public int g(World world, int i, int j, int k, int l) {
/* 103 */     if ((world.getData(i, j, k) & 0x8) > 0) {
/* 104 */       float f = 0.125F;
/* 105 */       List<EntityMinecartCommandBlock> list = world.a(EntityMinecartCommandBlock.class, AxisAlignedBB.a((i + f), j, (k + f), ((i + 1) - f), ((j + 1) - f), ((k + 1) - f)));
/*     */       
/* 107 */       if (list.size() > 0) {
/* 108 */         return ((EntityMinecartCommandBlock)list.get(0)).getCommandBlock().g();
/*     */       }
/*     */       
/* 111 */       List<IInventory> list1 = world.a(EntityMinecartAbstract.class, AxisAlignedBB.a((i + f), j, (k + f), ((i + 1) - f), ((j + 1) - f), ((k + 1) - f)), IEntitySelector.c);
/*     */       
/* 113 */       if (list1.size() > 0) {
/* 114 */         return Container.b(list1.get(0));
/*     */       }
/*     */     } 
/*     */     
/* 118 */     return 0;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockMinecartDetector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
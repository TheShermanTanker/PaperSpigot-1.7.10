/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import java.util.WeakHashMap;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.block.BlockRedstoneEvent;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ 
/*     */ public class BlockRedstoneTorch extends BlockTorch {
/*  14 */   private static Map b = new WeakHashMap<Object, Object>(); private boolean isOn;
/*     */   
/*     */   private boolean a(World world, int i, int j, int k, boolean flag) {
/*  17 */     if (!b.containsKey(world)) {
/*  18 */       b.put(world, new ArrayList());
/*     */     }
/*     */     
/*  21 */     List<RedstoneUpdateInfo> list = (List)b.get(world);
/*     */     
/*  23 */     if (flag) {
/*  24 */       list.add(new RedstoneUpdateInfo(i, j, k, world.getTime()));
/*     */     }
/*     */     
/*  27 */     int l = 0;
/*     */     
/*  29 */     for (int i1 = 0; i1 < list.size(); i1++) {
/*  30 */       RedstoneUpdateInfo redstoneupdateinfo = list.get(i1);
/*     */ 
/*     */       
/*  33 */       l++;
/*  34 */       if (redstoneupdateinfo.a == i && redstoneupdateinfo.b == j && redstoneupdateinfo.c == k && l >= 8) {
/*  35 */         return true;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  40 */     return false;
/*     */   }
/*     */   
/*     */   protected BlockRedstoneTorch(boolean flag) {
/*  44 */     this.isOn = flag;
/*  45 */     a(true);
/*  46 */     a((CreativeModeTab)null);
/*     */   }
/*     */   
/*     */   public int a(World world) {
/*  50 */     return 2;
/*     */   }
/*     */   
/*     */   public void onPlace(World world, int i, int j, int k) {
/*  54 */     if (world.getData(i, j, k) == 0) {
/*  55 */       super.onPlace(world, i, j, k);
/*     */     }
/*     */     
/*  58 */     if (this.isOn) {
/*  59 */       world.applyPhysics(i, j - 1, k, this);
/*  60 */       world.applyPhysics(i, j + 1, k, this);
/*  61 */       world.applyPhysics(i - 1, j, k, this);
/*  62 */       world.applyPhysics(i + 1, j, k, this);
/*  63 */       world.applyPhysics(i, j, k - 1, this);
/*  64 */       world.applyPhysics(i, j, k + 1, this);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void remove(World world, int i, int j, int k, Block block, int l) {
/*  69 */     if (this.isOn) {
/*  70 */       world.applyPhysics(i, j - 1, k, this);
/*  71 */       world.applyPhysics(i, j + 1, k, this);
/*  72 */       world.applyPhysics(i - 1, j, k, this);
/*  73 */       world.applyPhysics(i + 1, j, k, this);
/*  74 */       world.applyPhysics(i, j, k - 1, this);
/*  75 */       world.applyPhysics(i, j, k + 1, this);
/*     */     } 
/*     */   }
/*     */   
/*     */   public int b(IBlockAccess iblockaccess, int i, int j, int k, int l) {
/*  80 */     if (!this.isOn) {
/*  81 */       return 0;
/*     */     }
/*  83 */     int i1 = iblockaccess.getData(i, j, k);
/*     */     
/*  85 */     return (i1 == 5 && l == 1) ? 0 : ((i1 == 3 && l == 3) ? 0 : ((i1 == 4 && l == 2) ? 0 : ((i1 == 1 && l == 5) ? 0 : ((i1 == 2 && l == 4) ? 0 : 15))));
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean m(World world, int i, int j, int k) {
/*  90 */     int l = world.getData(i, j, k);
/*     */     
/*  92 */     return (l == 5 && world.isBlockFacePowered(i, j - 1, k, 0)) ? true : ((l == 3 && world.isBlockFacePowered(i, j, k - 1, 2)) ? true : ((l == 4 && world.isBlockFacePowered(i, j, k + 1, 3)) ? true : ((l == 1 && world.isBlockFacePowered(i - 1, j, k, 4)) ? true : ((l == 2 && world.isBlockFacePowered(i + 1, j, k, 5))))));
/*     */   }
/*     */   
/*     */   public void a(World world, int i, int j, int k, Random random) {
/*  96 */     boolean flag = m(world, i, j, k);
/*  97 */     List list = (List)b.get(world);
/*     */     
/*  99 */     while (list != null && !list.isEmpty() && world.getTime() - ((RedstoneUpdateInfo)list.get(0)).d > 60L) {
/* 100 */       list.remove(0);
/*     */     }
/*     */ 
/*     */     
/* 104 */     PluginManager manager = world.getServer().getPluginManager();
/* 105 */     Block block = world.getWorld().getBlockAt(i, j, k);
/* 106 */     int oldCurrent = this.isOn ? 15 : 0;
/*     */     
/* 108 */     BlockRedstoneEvent event = new BlockRedstoneEvent(block, oldCurrent, oldCurrent);
/*     */ 
/*     */     
/* 111 */     if (this.isOn) {
/* 112 */       if (flag) {
/*     */         
/* 114 */         if (oldCurrent != 0) {
/* 115 */           event.setNewCurrent(0);
/* 116 */           manager.callEvent((Event)event);
/* 117 */           if (event.getNewCurrent() != 0) {
/*     */             return;
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/* 123 */         world.setTypeAndData(i, j, k, Blocks.REDSTONE_TORCH_OFF, world.getData(i, j, k), 3);
/* 124 */         if (a(world, i, j, k, true)) {
/* 125 */           world.makeSound((i + 0.5F), (j + 0.5F), (k + 0.5F), "random.fizz", 0.5F, 2.6F + (world.random.nextFloat() - world.random.nextFloat()) * 0.8F);
/*     */           
/* 127 */           for (int l = 0; l < 5; l++) {
/* 128 */             double d0 = i + random.nextDouble() * 0.6D + 0.2D;
/* 129 */             double d1 = j + random.nextDouble() * 0.6D + 0.2D;
/* 130 */             double d2 = k + random.nextDouble() * 0.6D + 0.2D;
/*     */             
/* 132 */             world.addParticle("smoke", d0, d1, d2, 0.0D, 0.0D, 0.0D);
/*     */           } 
/*     */         } 
/*     */       } 
/* 136 */     } else if (!flag && !a(world, i, j, k, false)) {
/*     */       
/* 138 */       if (oldCurrent != 15) {
/* 139 */         event.setNewCurrent(15);
/* 140 */         manager.callEvent((Event)event);
/* 141 */         if (event.getNewCurrent() != 15) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 147 */       world.setTypeAndData(i, j, k, Blocks.REDSTONE_TORCH_ON, world.getData(i, j, k), 3);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void doPhysics(World world, int i, int j, int k, Block block) {
/* 152 */     if (!b(world, i, j, k, block)) {
/* 153 */       boolean flag = m(world, i, j, k);
/*     */       
/* 155 */       if ((this.isOn && flag) || (!this.isOn && !flag)) {
/* 156 */         world.a(i, j, k, this, a(world));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public int c(IBlockAccess iblockaccess, int i, int j, int k, int l) {
/* 162 */     return (l == 0) ? b(iblockaccess, i, j, k, l) : 0;
/*     */   }
/*     */   
/*     */   public Item getDropType(int i, Random random, int j) {
/* 166 */     return Item.getItemOf(Blocks.REDSTONE_TORCH_ON);
/*     */   }
/*     */   
/*     */   public boolean isPowerSource() {
/* 170 */     return true;
/*     */   }
/*     */   
/*     */   public boolean c(Block block) {
/* 174 */     return (block == Blocks.REDSTONE_TORCH_OFF || block == Blocks.REDSTONE_TORCH_ON);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockRedstoneTorch.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
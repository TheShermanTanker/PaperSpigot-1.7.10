/*     */ package net.minecraft.server.v1_7_R4;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
/*     */ import org.bukkit.event.entity.EntityInteractEvent;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ 
/*     */ public class BlockTripwire extends Block {
/*     */   public BlockTripwire() {
/*  12 */     super(Material.ORIENTABLE);
/*  13 */     a(0.0F, 0.0F, 0.0F, 1.0F, 0.15625F, 1.0F);
/*  14 */     a(true);
/*     */   }
/*     */   
/*     */   public int a(World world) {
/*  18 */     return 10;
/*     */   }
/*     */   
/*     */   public AxisAlignedBB a(World world, int i, int j, int k) {
/*  22 */     return null;
/*     */   }
/*     */   
/*     */   public boolean c() {
/*  26 */     return false;
/*     */   }
/*     */   
/*     */   public boolean d() {
/*  30 */     return false;
/*     */   }
/*     */   
/*     */   public int b() {
/*  34 */     return 30;
/*     */   }
/*     */   
/*     */   public Item getDropType(int i, Random random, int j) {
/*  38 */     return Items.STRING;
/*     */   }
/*     */   
/*     */   public void doPhysics(World world, int i, int j, int k, Block block) {
/*  42 */     int l = world.getData(i, j, k);
/*  43 */     boolean flag = ((l & 0x2) == 2);
/*  44 */     boolean flag1 = !World.a(world, i, j - 1, k);
/*     */     
/*  46 */     if (flag != flag1) {
/*  47 */       b(world, i, j, k, l, 0);
/*  48 */       world.setAir(i, j, k);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void updateShape(IBlockAccess iblockaccess, int i, int j, int k) {
/*  53 */     int l = iblockaccess.getData(i, j, k);
/*  54 */     boolean flag = ((l & 0x4) == 4);
/*  55 */     boolean flag1 = ((l & 0x2) == 2);
/*     */     
/*  57 */     if (!flag1) {
/*  58 */       a(0.0F, 0.0F, 0.0F, 1.0F, 0.09375F, 1.0F);
/*  59 */     } else if (!flag) {
/*  60 */       a(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
/*     */     } else {
/*  62 */       a(0.0F, 0.0625F, 0.0F, 1.0F, 0.15625F, 1.0F);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onPlace(World world, int i, int j, int k) {
/*  67 */     int l = World.a(world, i, j - 1, k) ? 0 : 2;
/*     */     
/*  69 */     world.setData(i, j, k, l, 3);
/*  70 */     a(world, i, j, k, l);
/*     */   }
/*     */   
/*     */   public void remove(World world, int i, int j, int k, Block block, int l) {
/*  74 */     a(world, i, j, k, l | 0x1);
/*     */   }
/*     */   
/*     */   public void a(World world, int i, int j, int k, int l, EntityHuman entityhuman) {
/*  78 */     if (!world.isStatic && 
/*  79 */       entityhuman.bF() != null && entityhuman.bF().getItem() == Items.SHEARS) {
/*  80 */       world.setData(i, j, k, l | 0x8, 4);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void a(World world, int i, int j, int k, int l) {
/*  86 */     int i1 = 0;
/*     */     
/*  88 */     while (i1 < 2) {
/*  89 */       int j1 = 1;
/*     */ 
/*     */       
/*  92 */       while (j1 < 42) {
/*  93 */         int k1 = i + Direction.a[i1] * j1;
/*  94 */         int l1 = k + Direction.b[i1] * j1;
/*  95 */         Block block = world.getType(k1, j, l1);
/*     */         
/*  97 */         if (block == Blocks.TRIPWIRE_SOURCE) {
/*  98 */           int i2 = world.getData(k1, j, l1) & 0x3;
/*     */           
/* 100 */           if (i2 == Direction.f[i1])
/* 101 */             Blocks.TRIPWIRE_SOURCE.a(world, k1, j, l1, false, world.getData(k1, j, l1), true, j1, l);  break;
/*     */         } 
/* 103 */         if (block == Blocks.TRIPWIRE) {
/* 104 */           j1++;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 109 */       i1++;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(World world, int i, int j, int k, Entity entity) {
/* 116 */     if (!world.isStatic && (
/* 117 */       world.getData(i, j, k) & 0x1) != 1) {
/* 118 */       e(world, i, j, k);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(World world, int i, int j, int k, Random random) {
/* 124 */     if (!world.isStatic && (
/* 125 */       world.getData(i, j, k) & 0x1) == 1) {
/* 126 */       e(world, i, j, k);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void e(World world, int i, int j, int k) {
/* 132 */     int l = world.getData(i, j, k);
/* 133 */     boolean flag = ((l & 0x1) == 1);
/* 134 */     boolean flag1 = false;
/* 135 */     List list = world.getEntities((Entity)null, AxisAlignedBB.a(i + this.minX, j + this.minY, k + this.minZ, i + this.maxX, j + this.maxY, k + this.maxZ));
/*     */     
/* 137 */     if (!list.isEmpty()) {
/* 138 */       Iterator<Entity> iterator = list.iterator();
/*     */       
/* 140 */       while (iterator.hasNext()) {
/* 141 */         Entity entity = iterator.next();
/*     */         
/* 143 */         if (!entity.az()) {
/* 144 */           flag1 = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 151 */     if (flag != flag1 && flag1 && (world.getData(i, j, k) & 0x4) == 4) {
/* 152 */       CraftWorld craftWorld = world.getWorld();
/* 153 */       PluginManager manager = world.getServer().getPluginManager();
/* 154 */       Block block = craftWorld.getBlockAt(i, j, k);
/* 155 */       boolean allowed = false;
/*     */ 
/*     */       
/* 158 */       for (Object object : list) {
/* 159 */         if (object != null) {
/*     */           EntityInteractEvent entityInteractEvent;
/*     */           
/* 162 */           if (object instanceof EntityHuman) {
/* 163 */             PlayerInteractEvent playerInteractEvent = CraftEventFactory.callPlayerInteractEvent((EntityHuman)object, Action.PHYSICAL, i, j, k, -1, null);
/* 164 */           } else if (object instanceof Entity) {
/* 165 */             entityInteractEvent = new EntityInteractEvent((Entity)((Entity)object).getBukkitEntity(), block);
/* 166 */             manager.callEvent((Event)entityInteractEvent);
/*     */           } else {
/*     */             continue;
/*     */           } 
/*     */           
/* 171 */           if (!entityInteractEvent.isCancelled()) {
/* 172 */             allowed = true;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/* 178 */       if (!allowed) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 184 */     if (flag1 && !flag) {
/* 185 */       l |= 0x1;
/*     */     }
/*     */     
/* 188 */     if (!flag1 && flag) {
/* 189 */       l &= 0xFFFFFFFE;
/*     */     }
/*     */     
/* 192 */     if (flag1 != flag) {
/* 193 */       world.setData(i, j, k, l, 3);
/* 194 */       a(world, i, j, k, l);
/*     */     } 
/*     */     
/* 197 */     if (flag1)
/* 198 */       world.a(i, j, k, this, a(world)); 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockTripwire.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
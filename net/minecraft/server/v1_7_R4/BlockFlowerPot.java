/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ public class BlockFlowerPot
/*     */   extends BlockContainer {
/*     */   public BlockFlowerPot() {
/*   8 */     super(Material.ORIENTABLE);
/*   9 */     g();
/*     */   }
/*     */   
/*     */   public void g() {
/*  13 */     float f = 0.375F;
/*  14 */     float f1 = f / 2.0F;
/*     */     
/*  16 */     a(0.5F - f1, 0.0F, 0.5F - f1, 0.5F + f1, f, 0.5F + f1);
/*     */   }
/*     */   
/*     */   public boolean c() {
/*  20 */     return false;
/*     */   }
/*     */   
/*     */   public int b() {
/*  24 */     return 33;
/*     */   }
/*     */   
/*     */   public boolean d() {
/*  28 */     return false;
/*     */   }
/*     */   
/*     */   public boolean interact(World world, int i, int j, int k, EntityHuman entityhuman, int l, float f, float f1, float f2) {
/*  32 */     ItemStack itemstack = entityhuman.inventory.getItemInHand();
/*     */     
/*  34 */     if (itemstack != null && itemstack.getItem() instanceof ItemBlock) {
/*  35 */       TileEntityFlowerPot tileentityflowerpot = e(world, i, j, k);
/*     */       
/*  37 */       if (tileentityflowerpot != null) {
/*  38 */         if (tileentityflowerpot.a() != null) {
/*  39 */           return false;
/*     */         }
/*  41 */         Block block = Block.a(itemstack.getItem());
/*     */         
/*  43 */         if (!a(block, itemstack.getData())) {
/*  44 */           return false;
/*     */         }
/*  46 */         tileentityflowerpot.a(itemstack.getItem(), itemstack.getData());
/*  47 */         tileentityflowerpot.update();
/*  48 */         if (!world.setData(i, j, k, itemstack.getData(), 2)) {
/*  49 */           world.notify(i, j, k);
/*     */         }
/*     */         
/*  52 */         if (!entityhuman.abilities.canInstantlyBuild && --itemstack.count <= 0) {
/*  53 */           entityhuman.inventory.setItem(entityhuman.inventory.itemInHandIndex, (ItemStack)null);
/*     */         }
/*     */         
/*  56 */         return true;
/*     */       } 
/*     */ 
/*     */       
/*  60 */       return false;
/*     */     } 
/*     */     
/*  63 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean a(Block block, int i) {
/*  68 */     return (block != Blocks.YELLOW_FLOWER && block != Blocks.RED_ROSE && block != Blocks.CACTUS && block != Blocks.BROWN_MUSHROOM && block != Blocks.RED_MUSHROOM && block != Blocks.SAPLING && block != Blocks.DEAD_BUSH) ? ((block == Blocks.LONG_GRASS && i == 2)) : true;
/*     */   }
/*     */   
/*     */   public int getDropData(World world, int i, int j, int k) {
/*  72 */     TileEntityFlowerPot tileentityflowerpot = e(world, i, j, k);
/*     */     
/*  74 */     return (tileentityflowerpot != null && tileentityflowerpot.a() != null) ? tileentityflowerpot.b() : 0;
/*     */   }
/*     */   
/*     */   public boolean canPlace(World world, int i, int j, int k) {
/*  78 */     return (super.canPlace(world, i, j, k) && World.a(world, i, j - 1, k));
/*     */   }
/*     */   
/*     */   public void doPhysics(World world, int i, int j, int k, Block block) {
/*  82 */     if (!World.a(world, i, j - 1, k)) {
/*  83 */       b(world, i, j, k, world.getData(i, j, k), 0);
/*  84 */       world.setAir(i, j, k);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void remove(World world, int i, int j, int k, Block block, int l) {
/*  89 */     TileEntityFlowerPot tileentityflowerpot = e(world, i, j, k);
/*     */     
/*  91 */     if (tileentityflowerpot != null && tileentityflowerpot.a() != null) {
/*  92 */       a(world, i, j, k, new ItemStack(tileentityflowerpot.a(), 1, tileentityflowerpot.b()));
/*  93 */       tileentityflowerpot.a((Item)null, 0);
/*     */     } 
/*     */     
/*  96 */     super.remove(world, i, j, k, block, l);
/*     */   }
/*     */   
/*     */   public void a(World world, int i, int j, int k, int l, EntityHuman entityhuman) {
/* 100 */     super.a(world, i, j, k, l, entityhuman);
/* 101 */     if (entityhuman.abilities.canInstantlyBuild) {
/* 102 */       TileEntityFlowerPot tileentityflowerpot = e(world, i, j, k);
/*     */       
/* 104 */       if (tileentityflowerpot != null) {
/* 105 */         tileentityflowerpot.a(Item.getById(0), 0);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public Item getDropType(int i, Random random, int j) {
/* 111 */     return Items.FLOWER_POT;
/*     */   }
/*     */   
/*     */   private TileEntityFlowerPot e(World world, int i, int j, int k) {
/* 115 */     TileEntity tileentity = world.getTileEntity(i, j, k);
/*     */     
/* 117 */     return (tileentity != null && tileentity instanceof TileEntityFlowerPot) ? (TileEntityFlowerPot)tileentity : null;
/*     */   }
/*     */   
/*     */   public TileEntity a(World world, int i) {
/* 121 */     Object object = null;
/* 122 */     byte b0 = 0;
/*     */     
/* 124 */     switch (i) {
/*     */       case 1:
/* 126 */         object = Blocks.RED_ROSE;
/* 127 */         b0 = 0;
/*     */         break;
/*     */       
/*     */       case 2:
/* 131 */         object = Blocks.YELLOW_FLOWER;
/*     */         break;
/*     */       
/*     */       case 3:
/* 135 */         object = Blocks.SAPLING;
/* 136 */         b0 = 0;
/*     */         break;
/*     */       
/*     */       case 4:
/* 140 */         object = Blocks.SAPLING;
/* 141 */         b0 = 1;
/*     */         break;
/*     */       
/*     */       case 5:
/* 145 */         object = Blocks.SAPLING;
/* 146 */         b0 = 2;
/*     */         break;
/*     */       
/*     */       case 6:
/* 150 */         object = Blocks.SAPLING;
/* 151 */         b0 = 3;
/*     */         break;
/*     */       
/*     */       case 7:
/* 155 */         object = Blocks.RED_MUSHROOM;
/*     */         break;
/*     */       
/*     */       case 8:
/* 159 */         object = Blocks.BROWN_MUSHROOM;
/*     */         break;
/*     */       
/*     */       case 9:
/* 163 */         object = Blocks.CACTUS;
/*     */         break;
/*     */       
/*     */       case 10:
/* 167 */         object = Blocks.DEAD_BUSH;
/*     */         break;
/*     */       
/*     */       case 11:
/* 171 */         object = Blocks.LONG_GRASS;
/* 172 */         b0 = 2;
/*     */         break;
/*     */       
/*     */       case 12:
/* 176 */         object = Blocks.SAPLING;
/* 177 */         b0 = 4;
/*     */         break;
/*     */       
/*     */       case 13:
/* 181 */         object = Blocks.SAPLING;
/* 182 */         b0 = 5;
/*     */         break;
/*     */     } 
/* 185 */     return new TileEntityFlowerPot(Item.getItemOf((Block)object), b0);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockFlowerPot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
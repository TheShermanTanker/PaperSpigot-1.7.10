/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.Random;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.util.BlockStateListPopulator;
/*     */ import org.bukkit.event.entity.CreatureSpawnEvent;
/*     */ 
/*     */ 
/*     */ public class BlockSkull
/*     */   extends BlockContainer
/*     */ {
/*     */   protected BlockSkull() {
/*  14 */     super(Material.ORIENTABLE);
/*  15 */     a(0.25F, 0.0F, 0.25F, 0.75F, 0.5F, 0.75F);
/*     */   }
/*     */   
/*     */   public int b() {
/*  19 */     return -1;
/*     */   }
/*     */   
/*     */   public boolean c() {
/*  23 */     return false;
/*     */   }
/*     */   
/*     */   public boolean d() {
/*  27 */     return false;
/*     */   }
/*     */   
/*     */   public void updateShape(IBlockAccess iblockaccess, int i, int j, int k) {
/*  31 */     int l = iblockaccess.getData(i, j, k) & 0x7;
/*     */     
/*  33 */     switch (l) {
/*     */       
/*     */       default:
/*  36 */         a(0.25F, 0.0F, 0.25F, 0.75F, 0.5F, 0.75F);
/*     */         return;
/*     */       
/*     */       case 2:
/*  40 */         a(0.25F, 0.25F, 0.5F, 0.75F, 0.75F, 1.0F);
/*     */         return;
/*     */       
/*     */       case 3:
/*  44 */         a(0.25F, 0.25F, 0.0F, 0.75F, 0.75F, 0.5F);
/*     */         return;
/*     */       
/*     */       case 4:
/*  48 */         a(0.5F, 0.25F, 0.25F, 1.0F, 0.75F, 0.75F); return;
/*     */       case 5:
/*     */         break;
/*     */     } 
/*  52 */     a(0.0F, 0.25F, 0.25F, 0.5F, 0.75F, 0.75F);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB a(World world, int i, int j, int k) {
/*  57 */     updateShape(world, i, j, k);
/*  58 */     return super.a(world, i, j, k);
/*     */   }
/*     */   
/*     */   public void postPlace(World world, int i, int j, int k, EntityLiving entityliving, ItemStack itemstack) {
/*  62 */     int l = MathHelper.floor((entityliving.yaw * 4.0F / 360.0F) + 2.5D) & 0x3;
/*     */     
/*  64 */     world.setData(i, j, k, l, 2);
/*     */   }
/*     */   
/*     */   public TileEntity a(World world, int i) {
/*  68 */     return new TileEntitySkull();
/*     */   }
/*     */   
/*     */   public int getDropData(World world, int i, int j, int k) {
/*  72 */     TileEntity tileentity = world.getTileEntity(i, j, k);
/*     */     
/*  74 */     return (tileentity != null && tileentity instanceof TileEntitySkull) ? ((TileEntitySkull)tileentity).getSkullType() : super.getDropData(world, i, j, k);
/*     */   }
/*     */   
/*     */   public int getDropData(int i) {
/*  78 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public void dropNaturally(World world, int i, int j, int k, int l, float f, int i1) {
/*  83 */     if (world.random.nextFloat() < f) {
/*  84 */       ItemStack itemstack = new ItemStack(Items.SKULL, 1, getDropData(world, i, j, k));
/*     */ 
/*     */       
/*  87 */       TileEntity tileEntity = world.getTileEntity(i, j, k);
/*  88 */       if (!(tileEntity instanceof TileEntitySkull)) {
/*     */         return;
/*     */       }
/*  91 */       TileEntitySkull tileentityskull = (TileEntitySkull)tileEntity;
/*     */ 
/*     */       
/*  94 */       if (tileentityskull.getSkullType() == 3 && tileentityskull.getGameProfile() != null) {
/*  95 */         itemstack.setTag(new NBTTagCompound());
/*  96 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/*     */         
/*  98 */         GameProfileSerializer.serialize(nbttagcompound, tileentityskull.getGameProfile());
/*  99 */         itemstack.getTag().set("SkullOwner", nbttagcompound);
/*     */       } 
/*     */       
/* 102 */       a(world, i, j, k, itemstack);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(World world, int i, int j, int k, int l, EntityHuman entityhuman) {
/* 108 */     if (entityhuman.abilities.canInstantlyBuild) {
/* 109 */       l |= 0x8;
/* 110 */       world.setData(i, j, k, l, 4);
/*     */     } 
/*     */     
/* 113 */     super.a(world, i, j, k, l, entityhuman);
/*     */   }
/*     */   
/*     */   public void remove(World world, int i, int j, int k, Block block, int l) {
/* 117 */     if (!world.isStatic)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 136 */       super.remove(world, i, j, k, block, l);
/*     */     }
/*     */   }
/*     */   
/*     */   public Item getDropType(int i, Random random, int j) {
/* 141 */     return Items.SKULL;
/*     */   }
/*     */   
/*     */   public void a(World world, int i, int j, int k, TileEntitySkull tileentityskull) {
/* 145 */     if (tileentityskull.getSkullType() == 1 && j >= 2 && world.difficulty != EnumDifficulty.PEACEFUL && !world.isStatic) {
/*     */       int l;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 152 */       for (l = -2; l <= 0; l++) {
/* 153 */         if (world.getType(i, j - 1, k + l) == Blocks.SOUL_SAND && world.getType(i, j - 1, k + l + 1) == Blocks.SOUL_SAND && world.getType(i, j - 2, k + l + 1) == Blocks.SOUL_SAND && world.getType(i, j - 1, k + l + 2) == Blocks.SOUL_SAND && a(world, i, j, k + l, 1) && a(world, i, j, k + l + 1, 1) && a(world, i, j, k + l + 2, 1)) {
/*     */           
/* 155 */           BlockStateListPopulator blockList = new BlockStateListPopulator((World)world.getWorld());
/*     */           
/* 157 */           world.setData(i, j, k + l, 8, 2);
/* 158 */           world.setData(i, j, k + l + 1, 8, 2);
/* 159 */           world.setData(i, j, k + l + 2, 8, 2);
/*     */           
/* 161 */           blockList.setTypeAndData(i, j, k + l, getById(0), 0, 2);
/* 162 */           blockList.setTypeAndData(i, j, k + l + 1, getById(0), 0, 2);
/* 163 */           blockList.setTypeAndData(i, j, k + l + 2, getById(0), 0, 2);
/* 164 */           blockList.setTypeAndData(i, j - 1, k + l, getById(0), 0, 2);
/* 165 */           blockList.setTypeAndData(i, j - 1, k + l + 1, getById(0), 0, 2);
/* 166 */           blockList.setTypeAndData(i, j - 1, k + l + 2, getById(0), 0, 2);
/* 167 */           blockList.setTypeAndData(i, j - 2, k + l + 1, getById(0), 0, 2);
/*     */           
/* 169 */           if (!world.isStatic) {
/* 170 */             EntityWither entitywither = new EntityWither(world);
/* 171 */             entitywither.setPositionRotation(i + 0.5D, j - 1.45D, (k + l) + 1.5D, 90.0F, 0.0F);
/* 172 */             entitywither.aM = 90.0F;
/* 173 */             entitywither.bZ();
/*     */             
/* 175 */             if (world.addEntity(entitywither, CreatureSpawnEvent.SpawnReason.BUILD_WITHER)) {
/* 176 */               if (!world.isStatic) {
/* 177 */                 Iterator<EntityHuman> iterator = world.a(EntityHuman.class, entitywither.boundingBox.grow(50.0D, 50.0D, 50.0D)).iterator();
/*     */                 
/* 179 */                 while (iterator.hasNext()) {
/* 180 */                   EntityHuman entityhuman = iterator.next();
/* 181 */                   entityhuman.a(AchievementList.I);
/*     */                 } 
/*     */               } 
/*     */               
/* 185 */               blockList.updateList();
/*     */             } 
/*     */           } 
/*     */           
/* 189 */           for (int i1 = 0; i1 < 120; i1++) {
/* 190 */             world.addParticle("snowballpoof", i + world.random.nextDouble(), (j - 2) + world.random.nextDouble() * 3.9D, (k + l + 1) + world.random.nextDouble(), 0.0D, 0.0D, 0.0D);
/*     */           }
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/*     */       
/* 197 */       for (l = -2; l <= 0; l++) {
/* 198 */         if (world.getType(i + l, j - 1, k) == Blocks.SOUL_SAND && world.getType(i + l + 1, j - 1, k) == Blocks.SOUL_SAND && world.getType(i + l + 1, j - 2, k) == Blocks.SOUL_SAND && world.getType(i + l + 2, j - 1, k) == Blocks.SOUL_SAND && a(world, i + l, j, k, 1) && a(world, i + l + 1, j, k, 1) && a(world, i + l + 2, j, k, 1)) {
/*     */           
/* 200 */           BlockStateListPopulator blockList = new BlockStateListPopulator((World)world.getWorld());
/*     */           
/* 202 */           world.setData(i + l, j, k, 8, 2);
/* 203 */           world.setData(i + l + 1, j, k, 8, 2);
/* 204 */           world.setData(i + l + 2, j, k, 8, 2);
/*     */           
/* 206 */           blockList.setTypeAndData(i + l, j, k, getById(0), 0, 2);
/* 207 */           blockList.setTypeAndData(i + l + 1, j, k, getById(0), 0, 2);
/* 208 */           blockList.setTypeAndData(i + l + 2, j, k, getById(0), 0, 2);
/* 209 */           blockList.setTypeAndData(i + l, j - 1, k, getById(0), 0, 2);
/* 210 */           blockList.setTypeAndData(i + l + 1, j - 1, k, getById(0), 0, 2);
/* 211 */           blockList.setTypeAndData(i + l + 2, j - 1, k, getById(0), 0, 2);
/* 212 */           blockList.setTypeAndData(i + l + 1, j - 2, k, getById(0), 0, 2);
/* 213 */           if (!world.isStatic) {
/* 214 */             EntityWither entitywither = new EntityWither(world);
/* 215 */             entitywither.setPositionRotation((i + l) + 1.5D, j - 1.45D, k + 0.5D, 0.0F, 0.0F);
/* 216 */             entitywither.bZ();
/*     */             
/* 218 */             if (world.addEntity(entitywither, CreatureSpawnEvent.SpawnReason.BUILD_WITHER)) {
/* 219 */               if (!world.isStatic) {
/* 220 */                 Iterator<EntityHuman> iterator = world.a(EntityHuman.class, entitywither.boundingBox.grow(50.0D, 50.0D, 50.0D)).iterator();
/*     */                 
/* 222 */                 while (iterator.hasNext()) {
/* 223 */                   EntityHuman entityhuman = iterator.next();
/* 224 */                   entityhuman.a(AchievementList.I);
/*     */                 } 
/*     */               } 
/* 227 */               blockList.updateList();
/*     */             } 
/*     */           } 
/*     */           
/* 231 */           for (int i1 = 0; i1 < 120; i1++) {
/* 232 */             world.addParticle("snowballpoof", (i + l + 1) + world.random.nextDouble(), (j - 2) + world.random.nextDouble() * 3.9D, k + world.random.nextDouble(), 0.0D, 0.0D, 0.0D);
/*     */           }
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean a(World world, int i, int j, int k, int l) {
/* 243 */     if (world.getType(i, j, k) != this) {
/* 244 */       return false;
/*     */     }
/* 246 */     TileEntity tileentity = world.getTileEntity(i, j, k);
/*     */     
/* 248 */     return (tileentity != null && tileentity instanceof TileEntitySkull) ? ((((TileEntitySkull)tileentity).getSkullType() == l)) : false;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\BlockSkull.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
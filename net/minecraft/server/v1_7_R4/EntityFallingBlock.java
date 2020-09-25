/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*     */ 
/*     */ public class EntityFallingBlock
/*     */   extends Entity {
/*     */   public Block id;
/*     */   public int data;
/*     */   public int ticksLived;
/*     */   public boolean dropItem;
/*     */   private boolean f;
/*     */   private boolean hurtEntities;
/*     */   private int fallHurtMax;
/*     */   private float fallHurtAmount;
/*     */   public NBTTagCompound tileEntityData;
/*     */   public Location sourceLoc;
/*     */   
/*     */   public EntityFallingBlock(World world) {
/*  23 */     this((Location)null, world);
/*     */   }
/*     */   
/*     */   public EntityFallingBlock(Location loc, World world) {
/*  27 */     super(world);
/*  28 */     this.sourceLoc = loc;
/*     */     
/*  30 */     this.dropItem = true;
/*  31 */     this.fallHurtMax = 40;
/*  32 */     this.fallHurtAmount = 2.0F;
/*  33 */     this.loadChunks = world.paperSpigotConfig.loadUnloadedFallingBlocks;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityFallingBlock(Location loc, World world, double d0, double d1, double d2, Block block) {
/*  38 */     this(loc, world, d0, d1, d2, block, 0);
/*     */   }
/*     */   
/*     */   public EntityFallingBlock(Location loc, World world, double d0, double d1, double d2, Block block, int i) {
/*  42 */     super(world);
/*  43 */     this.sourceLoc = loc;
/*     */     
/*  45 */     this.dropItem = true;
/*  46 */     this.fallHurtMax = 40;
/*  47 */     this.fallHurtAmount = 2.0F;
/*  48 */     this.id = block;
/*  49 */     this.data = i;
/*  50 */     this.k = true;
/*  51 */     a(0.98F, 0.98F);
/*  52 */     this.height = this.length / 2.0F;
/*  53 */     setPosition(d0, d1, d2);
/*  54 */     this.motX = 0.0D;
/*  55 */     this.motY = 0.0D;
/*  56 */     this.motZ = 0.0D;
/*  57 */     this.lastX = d0;
/*  58 */     this.lastY = d1;
/*  59 */     this.lastZ = d2;
/*  60 */     this.loadChunks = world.paperSpigotConfig.loadUnloadedFallingBlocks;
/*     */   }
/*     */   
/*     */   protected boolean g_() {
/*  64 */     return false;
/*     */   }
/*     */   
/*     */   protected void c() {}
/*     */   
/*     */   public boolean R() {
/*  70 */     return !this.dead;
/*     */   }
/*     */   
/*     */   public void h() {
/*  74 */     if (this.id.getMaterial() == Material.AIR) {
/*  75 */       die();
/*     */     } else {
/*  77 */       this.lastX = this.locX;
/*  78 */       this.lastY = this.locY;
/*  79 */       this.lastZ = this.locZ;
/*  80 */       this.ticksLived++;
/*  81 */       this.motY -= 0.03999999910593033D;
/*  82 */       move(this.motX, this.motY, this.motZ);
/*     */       
/*  84 */       if (this.inUnloadedChunk && this.world.paperSpigotConfig.removeUnloadedFallingBlocks) {
/*  85 */         die();
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*  90 */       if (this.world.paperSpigotConfig.fallingBlockHeightNerf != 0.0D && this.locY > this.world.paperSpigotConfig.fallingBlockHeightNerf) {
/*  91 */         if (this.dropItem) {
/*  92 */           a(new ItemStack(this.id, 1, this.id.getDropData(this.data)), 0.0F);
/*     */         }
/*     */         
/*  95 */         die();
/*     */       } 
/*     */ 
/*     */       
/*  99 */       this.motX *= 0.9800000190734863D;
/* 100 */       this.motY *= 0.9800000190734863D;
/* 101 */       this.motZ *= 0.9800000190734863D;
/* 102 */       if (!this.world.isStatic) {
/* 103 */         int i = MathHelper.floor(this.locX);
/* 104 */         int j = MathHelper.floor(this.locY);
/* 105 */         int k = MathHelper.floor(this.locZ);
/*     */         
/* 107 */         if (this.ticksLived == 1) {
/*     */           
/* 109 */           if (this.ticksLived != 1 || this.world.getType(i, j, k) != this.id || this.world.getData(i, j, k) != this.data || CraftEventFactory.callEntityChangeBlockEvent(this, i, j, k, Blocks.AIR, 0).isCancelled()) {
/* 110 */             die();
/*     */             
/*     */             return;
/*     */           } 
/* 114 */           this.world.setAir(i, j, k);
/* 115 */           this.world.spigotConfig.antiXrayInstance.updateNearbyBlocks(this.world, i, j, k);
/*     */         } 
/*     */         
/* 118 */         if (this.onGround) {
/* 119 */           this.motX *= 0.699999988079071D;
/* 120 */           this.motZ *= 0.699999988079071D;
/* 121 */           this.motY *= -0.5D;
/* 122 */           if (this.world.getType(i, j, k) != Blocks.PISTON_MOVING) {
/* 123 */             die();
/*     */             
/* 125 */             if (!this.f && this.world.mayPlace(this.id, i, j, k, true, 1, (Entity)null, (ItemStack)null) && !BlockFalling.canFall(this.world, i, j - 1, k) && i >= -30000000 && k >= -30000000 && i < 30000000 && k < 30000000 && j > 0 && j < 256 && (this.world.getType(i, j, k) != this.id || this.world.getData(i, j, k) != this.data)) {
/* 126 */               if (CraftEventFactory.callEntityChangeBlockEvent(this, i, j, k, this.id, this.data).isCancelled()) {
/*     */                 return;
/*     */               }
/* 129 */               this.world.setTypeAndData(i, j, k, this.id, this.data, 3);
/*     */               
/* 131 */               this.world.spigotConfig.antiXrayInstance.updateNearbyBlocks(this.world, i, j, k);
/*     */               
/* 133 */               if (this.id instanceof BlockFalling) {
/* 134 */                 ((BlockFalling)this.id).a(this.world, i, j, k, this.data);
/*     */               }
/*     */               
/* 137 */               if (this.tileEntityData != null && this.id instanceof IContainer) {
/* 138 */                 TileEntity tileentity = this.world.getTileEntity(i, j, k);
/*     */                 
/* 140 */                 if (tileentity != null) {
/* 141 */                   NBTTagCompound nbttagcompound = new NBTTagCompound();
/*     */                   
/* 143 */                   tileentity.b(nbttagcompound);
/* 144 */                   Iterator<String> iterator = this.tileEntityData.c().iterator();
/*     */                   
/* 146 */                   while (iterator.hasNext()) {
/* 147 */                     String s = iterator.next();
/* 148 */                     NBTBase nbtbase = this.tileEntityData.get(s);
/*     */                     
/* 150 */                     if (!s.equals("x") && !s.equals("y") && !s.equals("z")) {
/* 151 */                       nbttagcompound.set(s, nbtbase.clone());
/*     */                     }
/*     */                   } 
/*     */                   
/* 155 */                   tileentity.a(nbttagcompound);
/* 156 */                   tileentity.update();
/*     */                 } 
/*     */               } 
/* 159 */             } else if (this.dropItem && !this.f) {
/* 160 */               a(new ItemStack(this.id, 1, this.id.getDropData(this.data)), 0.0F);
/*     */             } 
/*     */           } 
/* 163 */         } else if ((this.ticksLived > 100 && !this.world.isStatic && (j < 1 || j > 256)) || this.ticksLived > 600) {
/* 164 */           if (this.dropItem) {
/* 165 */             a(new ItemStack(this.id, 1, this.id.getDropData(this.data)), 0.0F);
/*     */           }
/*     */           
/* 168 */           die();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void b(float f) {
/* 175 */     if (this.hurtEntities) {
/* 176 */       int i = MathHelper.f(f - 1.0F);
/*     */       
/* 178 */       if (i > 0) {
/* 179 */         ArrayList arraylist = new ArrayList(this.world.getEntities(this, this.boundingBox));
/* 180 */         boolean flag = (this.id == Blocks.ANVIL);
/* 181 */         DamageSource damagesource = flag ? DamageSource.ANVIL : DamageSource.FALLING_BLOCK;
/* 182 */         Iterator<Entity> iterator = arraylist.iterator();
/*     */         
/* 184 */         while (iterator.hasNext()) {
/* 185 */           Entity entity = iterator.next();
/*     */           
/* 187 */           CraftEventFactory.entityDamage = this;
/* 188 */           entity.damageEntity(damagesource, Math.min(MathHelper.d(i * this.fallHurtAmount), this.fallHurtMax));
/* 189 */           CraftEventFactory.entityDamage = null;
/*     */         } 
/*     */         
/* 192 */         if (flag && this.random.nextFloat() < 0.05000000074505806D + i * 0.05D) {
/* 193 */           int j = this.data >> 2;
/* 194 */           int k = this.data & 0x3;
/*     */           
/* 196 */           j++;
/* 197 */           if (j > 2) {
/* 198 */             this.f = true;
/*     */           } else {
/* 200 */             this.data = k | j << 2;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void b(NBTTagCompound nbttagcompound) {
/* 208 */     nbttagcompound.setByte("Tile", (byte)Block.getId(this.id));
/* 209 */     nbttagcompound.setInt("TileID", Block.getId(this.id));
/* 210 */     nbttagcompound.setByte("Data", (byte)this.data);
/* 211 */     nbttagcompound.setByte("Time", (byte)this.ticksLived);
/* 212 */     nbttagcompound.setBoolean("DropItem", this.dropItem);
/* 213 */     nbttagcompound.setBoolean("HurtEntities", this.hurtEntities);
/* 214 */     nbttagcompound.setFloat("FallHurtAmount", this.fallHurtAmount);
/* 215 */     nbttagcompound.setInt("FallHurtMax", this.fallHurtMax);
/* 216 */     if (this.tileEntityData != null) {
/* 217 */       nbttagcompound.set("TileEntityData", this.tileEntityData);
/*     */     }
/*     */     
/* 220 */     if (this.sourceLoc != null) {
/* 221 */       nbttagcompound.setInt("SourceLoc_x", this.sourceLoc.getBlockX());
/* 222 */       nbttagcompound.setInt("SourceLoc_y", this.sourceLoc.getBlockY());
/* 223 */       nbttagcompound.setInt("SourceLoc_z", this.sourceLoc.getBlockZ());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void a(NBTTagCompound nbttagcompound) {
/* 229 */     if (nbttagcompound.hasKeyOfType("TileID", 99)) {
/* 230 */       this.id = Block.getById(nbttagcompound.getInt("TileID"));
/*     */     } else {
/* 232 */       this.id = Block.getById(nbttagcompound.getByte("Tile") & 0xFF);
/*     */     } 
/*     */     
/* 235 */     this.data = nbttagcompound.getByte("Data") & 0xFF;
/* 236 */     this.ticksLived = nbttagcompound.getByte("Time") & 0xFF;
/* 237 */     if (nbttagcompound.hasKeyOfType("HurtEntities", 99)) {
/* 238 */       this.hurtEntities = nbttagcompound.getBoolean("HurtEntities");
/* 239 */       this.fallHurtAmount = nbttagcompound.getFloat("FallHurtAmount");
/* 240 */       this.fallHurtMax = nbttagcompound.getInt("FallHurtMax");
/* 241 */     } else if (this.id == Blocks.ANVIL) {
/* 242 */       this.hurtEntities = true;
/*     */     } 
/*     */     
/* 245 */     if (nbttagcompound.hasKeyOfType("DropItem", 99)) {
/* 246 */       this.dropItem = nbttagcompound.getBoolean("DropItem");
/*     */     }
/*     */     
/* 249 */     if (nbttagcompound.hasKeyOfType("TileEntityData", 10)) {
/* 250 */       this.tileEntityData = nbttagcompound.getCompound("TileEntityData");
/*     */     }
/*     */     
/* 253 */     if (this.id.getMaterial() == Material.AIR) {
/* 254 */       this.id = Blocks.SAND;
/*     */     }
/*     */     
/* 257 */     if (nbttagcompound.hasKey("SourceLoc_x")) {
/* 258 */       int srcX = nbttagcompound.getInt("SourceLoc_x");
/* 259 */       int srcY = nbttagcompound.getInt("SourceLoc_y");
/* 260 */       int srcZ = nbttagcompound.getInt("SourceLoc_z");
/* 261 */       this.sourceLoc = new Location((World)this.world.getWorld(), srcX, srcY, srcZ);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(boolean flag) {
/* 267 */     this.hurtEntities = flag;
/*     */   }
/*     */   
/*     */   public void a(CrashReportSystemDetails crashreportsystemdetails) {
/* 271 */     super.a(crashreportsystemdetails);
/* 272 */     crashreportsystemdetails.a("Immitating block ID", Integer.valueOf(Block.getId(this.id)));
/* 273 */     crashreportsystemdetails.a("Immitating block data", Integer.valueOf(this.data));
/*     */   }
/*     */   
/*     */   public Block f() {
/* 277 */     return this.id;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityFallingBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
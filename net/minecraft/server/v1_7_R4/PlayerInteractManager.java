/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.block.Action;
/*     */ import org.bukkit.event.block.BlockBreakEvent;
/*     */ import org.bukkit.event.block.BlockDamageEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ 
/*     */ public class PlayerInteractManager {
/*     */   public World world;
/*     */   public EntityPlayer player;
/*     */   private EnumGamemode gamemode;
/*     */   private boolean d;
/*     */   private int lastDigTick;
/*     */   private int f;
/*     */   private int g;
/*     */   private int h;
/*     */   private int currentTick;
/*     */   private boolean j;
/*     */   private int k;
/*     */   private int l;
/*     */   private int m;
/*     */   private int n;
/*     */   private int o;
/*     */   
/*     */   public PlayerInteractManager(World world) {
/*  30 */     this.gamemode = EnumGamemode.NONE;
/*  31 */     this.o = -1;
/*  32 */     this.world = world;
/*     */   }
/*     */   
/*     */   public void setGameMode(EnumGamemode enumgamemode) {
/*  36 */     this.gamemode = enumgamemode;
/*  37 */     enumgamemode.a(this.player.abilities);
/*  38 */     this.player.updateAbilities();
/*     */   }
/*     */   
/*     */   public EnumGamemode getGameMode() {
/*  42 */     return this.gamemode;
/*     */   }
/*     */   
/*     */   public boolean isCreative() {
/*  46 */     return this.gamemode.d();
/*     */   }
/*     */   
/*     */   public void b(EnumGamemode enumgamemode) {
/*  50 */     if (this.gamemode == EnumGamemode.NONE) {
/*  51 */       this.gamemode = enumgamemode;
/*     */     }
/*     */     
/*  54 */     setGameMode(this.gamemode);
/*     */   }
/*     */   
/*     */   public void a() {
/*  58 */     this.currentTick = MinecraftServer.currentTick;
/*     */ 
/*     */ 
/*     */     
/*  62 */     if (this.j) {
/*  63 */       int j = this.currentTick - this.n;
/*  64 */       Block block = this.world.getType(this.k, this.l, this.m);
/*     */       
/*  66 */       if (block.getMaterial() == Material.AIR) {
/*  67 */         this.j = false;
/*     */       } else {
/*  69 */         float f = block.getDamage(this.player, this.player.world, this.k, this.l, this.m) * (j + 1);
/*  70 */         int i = (int)(f * 10.0F);
/*  71 */         if (i != this.o) {
/*  72 */           this.world.d(this.player.getId(), this.k, this.l, this.m, i);
/*  73 */           this.o = i;
/*     */         } 
/*     */         
/*  76 */         if (f >= 1.0F) {
/*  77 */           this.j = false;
/*  78 */           breakBlock(this.k, this.l, this.m);
/*     */         } 
/*     */       } 
/*  81 */     } else if (this.d) {
/*  82 */       Block block1 = this.world.getType(this.f, this.g, this.h);
/*     */       
/*  84 */       if (block1.getMaterial() == Material.AIR) {
/*  85 */         this.world.d(this.player.getId(), this.f, this.g, this.h, -1);
/*  86 */         this.o = -1;
/*  87 */         this.d = false;
/*     */       } else {
/*  89 */         int k = this.currentTick - this.lastDigTick;
/*     */         
/*  91 */         float f = block1.getDamage(this.player, this.player.world, this.f, this.g, this.h) * (k + 1);
/*  92 */         int i = (int)(f * 10.0F);
/*  93 */         if (i != this.o) {
/*  94 */           this.world.d(this.player.getId(), this.f, this.g, this.h, i);
/*  95 */           this.o = i;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void dig(int i, int j, int k, int l) {
/* 103 */     PlayerInteractEvent event = CraftEventFactory.callPlayerInteractEvent(this.player, Action.LEFT_CLICK_BLOCK, i, j, k, l, this.player.inventory.getItemInHand());
/* 104 */     if (!this.gamemode.isAdventure() || this.player.d(i, j, k)) {
/* 105 */       if (event.isCancelled()) {
/*     */         
/* 107 */         this.player.playerConnection.sendPacket(new PacketPlayOutBlockChange(i, j, k, this.world));
/*     */         
/* 109 */         TileEntity tileentity = this.world.getTileEntity(i, j, k);
/* 110 */         if (tileentity != null) {
/* 111 */           this.player.playerConnection.sendPacket(tileentity.getUpdatePacket());
/*     */         }
/*     */         
/*     */         return;
/*     */       } 
/* 116 */       if (isCreative()) {
/* 117 */         if (!this.world.douseFire((EntityHuman)null, i, j, k, l)) {
/* 118 */           breakBlock(i, j, k);
/*     */         }
/*     */       } else {
/*     */         
/* 122 */         this.lastDigTick = this.currentTick;
/* 123 */         float f = 1.0F;
/* 124 */         Block block = this.world.getType(i, j, k);
/*     */         
/* 126 */         if (event.useInteractedBlock() == Event.Result.DENY) {
/*     */           
/* 128 */           if (block == Blocks.WOODEN_DOOR) {
/*     */             
/* 130 */             boolean bottom = ((this.world.getData(i, j, k) & 0x8) == 0);
/* 131 */             this.player.playerConnection.sendPacket(new PacketPlayOutBlockChange(i, j, k, this.world));
/* 132 */             this.player.playerConnection.sendPacket(new PacketPlayOutBlockChange(i, j + (bottom ? 1 : -1), k, this.world));
/* 133 */           } else if (block == Blocks.TRAP_DOOR) {
/* 134 */             this.player.playerConnection.sendPacket(new PacketPlayOutBlockChange(i, j, k, this.world));
/*     */           } 
/* 136 */         } else if (block.getMaterial() != Material.AIR) {
/* 137 */           block.attack(this.world, i, j, k, this.player);
/* 138 */           f = block.getDamage(this.player, this.player.world, i, j, k);
/*     */           
/* 140 */           this.world.douseFire((EntityHuman)null, i, j, k, l);
/*     */         } 
/*     */         
/* 143 */         if (event.useItemInHand() == Event.Result.DENY) {
/*     */           
/* 145 */           if (f > 1.0F) {
/* 146 */             this.player.playerConnection.sendPacket(new PacketPlayOutBlockChange(i, j, k, this.world));
/*     */           }
/*     */           return;
/*     */         } 
/* 150 */         BlockDamageEvent blockEvent = CraftEventFactory.callBlockDamageEvent(this.player, i, j, k, this.player.inventory.getItemInHand(), (f >= 1.0F));
/*     */         
/* 152 */         if (blockEvent.isCancelled()) {
/*     */           
/* 154 */           this.player.playerConnection.sendPacket(new PacketPlayOutBlockChange(i, j, k, this.world));
/*     */           
/*     */           return;
/*     */         } 
/* 158 */         if (blockEvent.getInstaBreak()) {
/* 159 */           f = 2.0F;
/*     */         }
/*     */ 
/*     */         
/* 163 */         if (block.getMaterial() != Material.AIR && f >= 1.0F) {
/* 164 */           breakBlock(i, j, k);
/*     */         } else {
/* 166 */           this.d = true;
/* 167 */           this.f = i;
/* 168 */           this.g = j;
/* 169 */           this.h = k;
/* 170 */           int i1 = (int)(f * 10.0F);
/*     */           
/* 172 */           this.world.d(this.player.getId(), i, j, k, i1);
/* 173 */           this.o = i1;
/*     */         } 
/*     */       } 
/* 176 */       this.world.spigotConfig.antiXrayInstance.updateNearbyBlocks(this.world, i, j, k);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void a(int i, int j, int k) {
/* 181 */     if (i == this.f && j == this.g && k == this.h) {
/* 182 */       this.currentTick = MinecraftServer.currentTick;
/* 183 */       int l = this.currentTick - this.lastDigTick;
/* 184 */       Block block = this.world.getType(i, j, k);
/*     */       
/* 186 */       if (block.getMaterial() != Material.AIR) {
/* 187 */         float f = block.getDamage(this.player, this.player.world, i, j, k) * (l + 1);
/*     */         
/* 189 */         if (f >= 0.7F) {
/* 190 */           this.d = false;
/* 191 */           this.world.d(this.player.getId(), i, j, k, -1);
/* 192 */           breakBlock(i, j, k);
/* 193 */         } else if (!this.j) {
/* 194 */           this.d = false;
/* 195 */           this.j = true;
/* 196 */           this.k = i;
/* 197 */           this.l = j;
/* 198 */           this.m = k;
/* 199 */           this.n = this.lastDigTick;
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       
/* 204 */       this.player.playerConnection.sendPacket(new PacketPlayOutBlockChange(i, j, k, this.world));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void c(int i, int j, int k) {
/* 210 */     this.d = false;
/* 211 */     this.world.d(this.player.getId(), this.f, this.g, this.h, -1);
/*     */   }
/*     */   
/*     */   private boolean d(int i, int j, int k) {
/* 215 */     Block block = this.world.getType(i, j, k);
/* 216 */     int l = this.world.getData(i, j, k);
/*     */     
/* 218 */     block.a(this.world, i, j, k, l, this.player);
/* 219 */     boolean flag = this.world.setAir(i, j, k);
/*     */     
/* 221 */     if (flag) {
/* 222 */       block.postBreak(this.world, i, j, k, l);
/*     */     }
/*     */     
/* 225 */     return flag;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean breakBlock(int i, int j, int k) {
/* 230 */     BlockBreakEvent event = null;
/*     */     
/* 232 */     if (this.player instanceof EntityPlayer) {
/* 233 */       Block block1 = this.world.getWorld().getBlockAt(i, j, k);
/*     */ 
/*     */       
/* 236 */       if (this.world.getTileEntity(i, j, k) == null) {
/* 237 */         PacketPlayOutBlockChange packet = new PacketPlayOutBlockChange(i, j, k, this.world);
/* 238 */         packet.block = Blocks.AIR;
/* 239 */         packet.data = 0;
/* 240 */         this.player.playerConnection.sendPacket(packet);
/*     */       } 
/*     */       
/* 243 */       event = new BlockBreakEvent(block1, (Player)this.player.getBukkitEntity());
/*     */ 
/*     */       
/* 246 */       event.setCancelled((this.gamemode.isAdventure() && !this.player.d(i, j, k)));
/*     */ 
/*     */       
/* 249 */       event.setCancelled((event.isCancelled() || (this.gamemode.d() && this.player.be() != null && this.player.be().getItem() instanceof ItemSword)));
/*     */ 
/*     */       
/* 252 */       Block nmsBlock = this.world.getType(i, j, k);
/*     */       
/* 254 */       if (nmsBlock != null && !event.isCancelled() && !isCreative() && this.player.a(nmsBlock))
/*     */       {
/* 256 */         if (!nmsBlock.E() || !EnchantmentManager.hasSilkTouchEnchantment(this.player)) {
/* 257 */           int data = block1.getData();
/* 258 */           int bonusLevel = EnchantmentManager.getBonusBlockLootEnchantmentLevel(this.player);
/*     */           
/* 260 */           event.setExpToDrop(nmsBlock.getExpDrop(this.world, data, bonusLevel));
/*     */         } 
/*     */       }
/*     */       
/* 264 */       this.world.getServer().getPluginManager().callEvent((Event)event);
/*     */       
/* 266 */       if (event.isCancelled()) {
/*     */         
/* 268 */         this.player.playerConnection.sendPacket(new PacketPlayOutBlockChange(i, j, k, this.world));
/*     */         
/* 270 */         TileEntity tileentity = this.world.getTileEntity(i, j, k);
/* 271 */         if (tileentity != null) {
/* 272 */           this.player.playerConnection.sendPacket(tileentity.getUpdatePacket());
/*     */         }
/* 274 */         return false;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 284 */     Block block = this.world.getType(i, j, k);
/* 285 */     if (block == Blocks.AIR) return false; 
/* 286 */     int l = this.world.getData(i, j, k);
/*     */ 
/*     */     
/* 289 */     if (block == Blocks.SKULL && !isCreative()) {
/* 290 */       block.dropNaturally(this.world, i, j, k, l, 1.0F, 0);
/* 291 */       return d(i, j, k);
/*     */     } 
/*     */ 
/*     */     
/* 295 */     this.world.a(this.player, 2001, i, j, k, Block.getId(block) + (this.world.getData(i, j, k) << 12));
/* 296 */     boolean flag = d(i, j, k);
/*     */     
/* 298 */     if (isCreative()) {
/* 299 */       this.player.playerConnection.sendPacket(new PacketPlayOutBlockChange(i, j, k, this.world));
/*     */     } else {
/* 301 */       ItemStack itemstack = this.player.bF();
/* 302 */       boolean flag1 = this.player.a(block);
/*     */       
/* 304 */       if (itemstack != null) {
/* 305 */         itemstack.a(this.world, block, i, j, k, this.player);
/* 306 */         if (itemstack.count == 0) {
/* 307 */           this.player.bG();
/*     */         }
/*     */       } 
/*     */       
/* 311 */       if (flag && flag1) {
/* 312 */         block.a(this.world, this.player, i, j, k, l);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 317 */     if (flag && event != null) {
/* 318 */       block.dropExperience(this.world, i, j, k, event.getExpToDrop());
/*     */     }
/*     */ 
/*     */     
/* 322 */     return flag;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean useItem(EntityHuman entityhuman, World world, ItemStack itemstack) {
/* 327 */     int i = itemstack.count;
/* 328 */     int j = itemstack.getData();
/* 329 */     ItemStack itemstack1 = itemstack.a(world, entityhuman);
/*     */ 
/*     */     
/* 332 */     if (itemstack1 != null && itemstack1.getItem() == Items.WRITTEN_BOOK)
/*     */     {
/* 334 */       this.player.playerConnection.sendPacket(new PacketPlayOutCustomPayload("MC|BOpen", new byte[0]));
/*     */     }
/*     */ 
/*     */     
/* 338 */     if (itemstack1 == itemstack && (itemstack1 == null || (itemstack1.count == i && itemstack1.n() <= 0 && itemstack1.getData() == j))) {
/* 339 */       return false;
/*     */     }
/* 341 */     entityhuman.inventory.items[entityhuman.inventory.itemInHandIndex] = itemstack1;
/* 342 */     if (isCreative()) {
/* 343 */       itemstack1.count = i;
/* 344 */       if (itemstack1.g()) {
/* 345 */         itemstack1.setData(j);
/*     */       }
/*     */     } 
/*     */     
/* 349 */     if (itemstack1.count == 0) {
/* 350 */       entityhuman.inventory.items[entityhuman.inventory.itemInHandIndex] = null;
/*     */     }
/*     */     
/* 353 */     if (!entityhuman.by()) {
/* 354 */       ((EntityPlayer)entityhuman).updateInventory(entityhuman.defaultContainer);
/*     */     }
/*     */     
/* 357 */     return true;
/*     */   }
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
/*     */ 
/*     */   
/*     */   public boolean interact(EntityHuman entityhuman, World world, ItemStack itemstack, int i, int j, int k, int l, float f, float f1, float f2) {
/* 379 */     Block block = world.getType(i, j, k);
/* 380 */     boolean result = false;
/* 381 */     if (block != Blocks.AIR) {
/* 382 */       PlayerInteractEvent event = CraftEventFactory.callPlayerInteractEvent(entityhuman, Action.RIGHT_CLICK_BLOCK, i, j, k, l, itemstack);
/* 383 */       if (event.useInteractedBlock() == Event.Result.DENY) {
/*     */         
/* 385 */         if (block == Blocks.WOODEN_DOOR) {
/* 386 */           boolean bottom = ((world.getData(i, j, k) & 0x8) == 0);
/* 387 */           ((EntityPlayer)entityhuman).playerConnection.sendPacket(new PacketPlayOutBlockChange(i, j + (bottom ? 1 : -1), k, world));
/*     */         } 
/* 389 */         result = (event.useItemInHand() != Event.Result.ALLOW);
/* 390 */       } else if (!entityhuman.isSneaking() || itemstack == null) {
/* 391 */         result = block.interact(world, i, j, k, entityhuman, l, f, f1, f2);
/*     */       } 
/*     */       
/* 394 */       if (itemstack != null && !result) {
/* 395 */         int j1 = itemstack.getData();
/* 396 */         int k1 = itemstack.count;
/*     */         
/* 398 */         result = itemstack.placeItem(entityhuman, world, i, j, k, l, f, f1, f2);
/*     */ 
/*     */         
/* 401 */         if (isCreative()) {
/* 402 */           itemstack.setData(j1);
/* 403 */           itemstack.count = k1;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 408 */       if (itemstack != null && ((!result && event.useItemInHand() != Event.Result.DENY) || event.useItemInHand() == Event.Result.ALLOW)) {
/* 409 */         useItem(entityhuman, world, itemstack);
/*     */       }
/*     */     } 
/* 412 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(WorldServer worldserver) {
/* 417 */     this.world = worldserver;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PlayerInteractManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
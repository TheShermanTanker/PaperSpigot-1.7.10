/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.entity.CraftHumanEntity;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.inventory.FurnaceBurnEvent;
/*     */ import org.bukkit.event.inventory.FurnaceSmeltEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ public class TileEntityFurnace
/*     */   extends TileEntity implements IWorldInventory {
/*  15 */   private static final int[] k = new int[] { 0 };
/*  16 */   private static final int[] l = new int[] { 2, 1 };
/*  17 */   private static final int[] m = new int[] { 1 };
/*  18 */   private ItemStack[] items = new ItemStack[3];
/*     */   
/*     */   public int burnTime;
/*     */   
/*     */   public int ticksForCurrentFuel;
/*     */   public int cookTime;
/*     */   private String o;
/*  25 */   private int lastTick = MinecraftServer.currentTick;
/*  26 */   private int maxStack = 64;
/*  27 */   public List<HumanEntity> transaction = new ArrayList<HumanEntity>();
/*     */   
/*     */   public ItemStack[] getContents() {
/*  30 */     return this.items;
/*     */   }
/*     */   
/*     */   public void onOpen(CraftHumanEntity who) {
/*  34 */     this.transaction.add(who);
/*     */   }
/*     */   
/*     */   public void onClose(CraftHumanEntity who) {
/*  38 */     this.transaction.remove(who);
/*     */   }
/*     */   
/*     */   public List<HumanEntity> getViewers() {
/*  42 */     return this.transaction;
/*     */   }
/*     */   
/*     */   public void setMaxStackSize(int size) {
/*  46 */     this.maxStack = size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSize() {
/*  53 */     return this.items.length;
/*     */   }
/*     */   
/*     */   public ItemStack getItem(int i) {
/*  57 */     return this.items[i];
/*     */   }
/*     */   
/*     */   public ItemStack splitStack(int i, int j) {
/*  61 */     if (this.items[i] != null) {
/*     */ 
/*     */       
/*  64 */       if ((this.items[i]).count <= j) {
/*  65 */         ItemStack itemStack = this.items[i];
/*  66 */         this.items[i] = null;
/*  67 */         return itemStack;
/*     */       } 
/*  69 */       ItemStack itemstack = this.items[i].a(j);
/*  70 */       if ((this.items[i]).count == 0) {
/*  71 */         this.items[i] = null;
/*     */       }
/*     */       
/*  74 */       return itemstack;
/*     */     } 
/*     */     
/*  77 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack splitWithoutUpdate(int i) {
/*  82 */     if (this.items[i] != null) {
/*  83 */       ItemStack itemstack = this.items[i];
/*     */       
/*  85 */       this.items[i] = null;
/*  86 */       return itemstack;
/*     */     } 
/*  88 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setItem(int i, ItemStack itemstack) {
/*  93 */     this.items[i] = itemstack;
/*  94 */     if (itemstack != null && itemstack.count > getMaxStackSize()) {
/*  95 */       itemstack.count = getMaxStackSize();
/*     */     }
/*     */   }
/*     */   
/*     */   public String getInventoryName() {
/* 100 */     return k_() ? this.o : "container.furnace";
/*     */   }
/*     */   
/*     */   public boolean k_() {
/* 104 */     return (this.o != null && this.o.length() > 0);
/*     */   }
/*     */   
/*     */   public void a(String s) {
/* 108 */     this.o = s;
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/* 112 */     super.a(nbttagcompound);
/* 113 */     NBTTagList nbttaglist = nbttagcompound.getList("Items", 10);
/*     */     
/* 115 */     this.items = new ItemStack[getSize()];
/*     */     
/* 117 */     for (int i = 0; i < nbttaglist.size(); i++) {
/* 118 */       NBTTagCompound nbttagcompound1 = nbttaglist.get(i);
/* 119 */       byte b0 = nbttagcompound1.getByte("Slot");
/*     */       
/* 121 */       if (b0 >= 0 && b0 < this.items.length) {
/* 122 */         this.items[b0] = ItemStack.createStack(nbttagcompound1);
/*     */       }
/*     */     } 
/*     */     
/* 126 */     this.burnTime = nbttagcompound.getShort("BurnTime");
/* 127 */     this.cookTime = nbttagcompound.getShort("CookTime");
/* 128 */     this.ticksForCurrentFuel = fuelTime(this.items[1]);
/* 129 */     if (nbttagcompound.hasKeyOfType("CustomName", 8)) {
/* 130 */       this.o = nbttagcompound.getString("CustomName");
/*     */     }
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/* 135 */     super.b(nbttagcompound);
/* 136 */     nbttagcompound.setShort("BurnTime", (short)this.burnTime);
/* 137 */     nbttagcompound.setShort("CookTime", (short)this.cookTime);
/* 138 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/* 140 */     for (int i = 0; i < this.items.length; i++) {
/* 141 */       if (this.items[i] != null) {
/* 142 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*     */         
/* 144 */         nbttagcompound1.setByte("Slot", (byte)i);
/* 145 */         this.items[i].save(nbttagcompound1);
/* 146 */         nbttaglist.add(nbttagcompound1);
/*     */       } 
/*     */     } 
/*     */     
/* 150 */     nbttagcompound.set("Items", nbttaglist);
/* 151 */     if (k_()) {
/* 152 */       nbttagcompound.setString("CustomName", this.o);
/*     */     }
/*     */   }
/*     */   
/*     */   public int getMaxStackSize() {
/* 157 */     return this.maxStack;
/*     */   }
/*     */   
/*     */   public boolean isBurning() {
/* 161 */     return (this.burnTime > 0);
/*     */   }
/*     */   
/*     */   public void h() {
/* 165 */     boolean flag = (this.burnTime > 0);
/* 166 */     boolean flag1 = false;
/*     */ 
/*     */     
/* 169 */     int elapsedTicks = MinecraftServer.currentTick - this.lastTick;
/* 170 */     this.lastTick = MinecraftServer.currentTick;
/*     */ 
/*     */     
/* 173 */     if (isBurning() && canBurn()) {
/* 174 */       this.cookTime += elapsedTicks;
/* 175 */       if (this.cookTime >= 200) {
/* 176 */         this.cookTime %= 200;
/* 177 */         burn();
/* 178 */         flag1 = true;
/*     */       } 
/*     */     } else {
/* 181 */       this.cookTime = 0;
/*     */     } 
/*     */ 
/*     */     
/* 185 */     if (this.burnTime > 0) {
/* 186 */       this.burnTime -= elapsedTicks;
/*     */     }
/*     */     
/* 189 */     if (!this.world.isStatic) {
/* 190 */       if (this.burnTime != 0 || (this.items[1] != null && this.items[0] != null))
/*     */       {
/* 192 */         if (this.burnTime <= 0 && canBurn()) {
/* 193 */           CraftItemStack fuel = CraftItemStack.asCraftMirror(this.items[1]);
/*     */           
/* 195 */           FurnaceBurnEvent furnaceBurnEvent = new FurnaceBurnEvent(this.world.getWorld().getBlockAt(this.x, this.y, this.z), (ItemStack)fuel, fuelTime(this.items[1]));
/* 196 */           this.world.getServer().getPluginManager().callEvent((Event)furnaceBurnEvent);
/*     */           
/* 198 */           if (furnaceBurnEvent.isCancelled()) {
/*     */             return;
/*     */           }
/*     */           
/* 202 */           this.ticksForCurrentFuel = furnaceBurnEvent.getBurnTime();
/* 203 */           this.burnTime += this.ticksForCurrentFuel;
/* 204 */           if (this.burnTime > 0 && furnaceBurnEvent.isBurning()) {
/*     */             
/* 206 */             flag1 = true;
/* 207 */             if (this.items[1] != null) {
/* 208 */               (this.items[1]).count--;
/* 209 */               if ((this.items[1]).count == 0) {
/* 210 */                 Item item = this.items[1].getItem().t();
/*     */                 
/* 212 */                 this.items[1] = (item != null) ? new ItemStack(item) : null;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }
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
/* 232 */       if (flag != ((this.burnTime > 0))) {
/* 233 */         flag1 = true;
/* 234 */         BlockFurnace.a((this.burnTime > 0), this.world, this.x, this.y, this.z);
/*     */       } 
/*     */     } 
/*     */     
/* 238 */     if (flag1) {
/* 239 */       update();
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean canBurn() {
/* 244 */     if (this.items[0] == null) {
/* 245 */       return false;
/*     */     }
/* 247 */     ItemStack itemstack = RecipesFurnace.getInstance().getResult(this.items[0]);
/*     */ 
/*     */     
/* 250 */     return (itemstack == null) ? false : ((this.items[2] == null) ? true : (!this.items[2].doMaterialsMatch(itemstack) ? false : (((this.items[2]).count + itemstack.count <= getMaxStackSize() && (this.items[2]).count < this.items[2].getMaxStackSize()) ? true : (((this.items[2]).count + itemstack.count <= itemstack.getMaxStackSize())))));
/*     */   }
/*     */ 
/*     */   
/*     */   public void burn() {
/* 255 */     if (canBurn()) {
/* 256 */       ItemStack itemstack = RecipesFurnace.getInstance().getResult(this.items[0]);
/*     */ 
/*     */       
/* 259 */       CraftItemStack source = CraftItemStack.asCraftMirror(this.items[0]);
/* 260 */       ItemStack result = CraftItemStack.asBukkitCopy(itemstack);
/*     */       
/* 262 */       FurnaceSmeltEvent furnaceSmeltEvent = new FurnaceSmeltEvent(this.world.getWorld().getBlockAt(this.x, this.y, this.z), (ItemStack)source, result);
/* 263 */       this.world.getServer().getPluginManager().callEvent((Event)furnaceSmeltEvent);
/*     */       
/* 265 */       if (furnaceSmeltEvent.isCancelled()) {
/*     */         return;
/*     */       }
/*     */       
/* 269 */       result = furnaceSmeltEvent.getResult();
/* 270 */       itemstack = CraftItemStack.asNMSCopy(result);
/*     */       
/* 272 */       if (itemstack != null) {
/* 273 */         if (this.items[2] == null) {
/* 274 */           this.items[2] = itemstack;
/* 275 */         } else if (CraftItemStack.asCraftMirror(this.items[2]).isSimilar(result)) {
/* 276 */           (this.items[2]).count += itemstack.count;
/*     */         } else {
/*     */           return;
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/* 283 */       (this.items[0]).count--;
/* 284 */       if ((this.items[0]).count <= 0) {
/* 285 */         this.items[0] = null;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int fuelTime(ItemStack itemstack) {
/* 291 */     if (itemstack == null) {
/* 292 */       return 0;
/*     */     }
/* 294 */     Item item = itemstack.getItem();
/*     */     
/* 296 */     if (item instanceof ItemBlock && Block.a(item) != Blocks.AIR) {
/* 297 */       Block block = Block.a(item);
/*     */       
/* 299 */       if (block == Blocks.WOOD_STEP) {
/* 300 */         return 150;
/*     */       }
/*     */       
/* 303 */       if (block.getMaterial() == Material.WOOD) {
/* 304 */         return 300;
/*     */       }
/*     */       
/* 307 */       if (block == Blocks.COAL_BLOCK) {
/* 308 */         return 16000;
/*     */       }
/*     */     } 
/*     */     
/* 312 */     return (item instanceof ItemTool && ((ItemTool)item).j().equals("WOOD")) ? 200 : ((item instanceof ItemSword && ((ItemSword)item).j().equals("WOOD")) ? 200 : ((item instanceof ItemHoe && ((ItemHoe)item).i().equals("WOOD")) ? 200 : ((item == Items.STICK) ? 100 : ((item == Items.COAL) ? 1600 : ((item == Items.LAVA_BUCKET) ? 20000 : ((item == Item.getItemOf(Blocks.SAPLING)) ? 100 : ((item == Items.BLAZE_ROD) ? 2400 : 0)))))));
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isFuel(ItemStack itemstack) {
/* 317 */     return (fuelTime(itemstack) > 0);
/*     */   }
/*     */   
/*     */   public boolean a(EntityHuman entityhuman) {
/* 321 */     return (this.world.getTileEntity(this.x, this.y, this.z) != this) ? false : ((entityhuman.e(this.x + 0.5D, this.y + 0.5D, this.z + 0.5D) <= 64.0D));
/*     */   }
/*     */   
/*     */   public void startOpen() {}
/*     */   
/*     */   public void closeContainer() {}
/*     */   
/*     */   public boolean b(int i, ItemStack itemstack) {
/* 329 */     return (i == 2) ? false : ((i == 1) ? isFuel(itemstack) : true);
/*     */   }
/*     */   
/*     */   public int[] getSlotsForFace(int i) {
/* 333 */     return (i == 0) ? l : ((i == 1) ? k : m);
/*     */   }
/*     */   
/*     */   public boolean canPlaceItemThroughFace(int i, ItemStack itemstack, int j) {
/* 337 */     return b(i, itemstack);
/*     */   }
/*     */   
/*     */   public boolean canTakeItemThroughFace(int i, ItemStack itemstack, int j) {
/* 341 */     return (j != 0 || i != 1 || itemstack.getItem() == Items.BUCKET);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\TileEntityFurnace.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
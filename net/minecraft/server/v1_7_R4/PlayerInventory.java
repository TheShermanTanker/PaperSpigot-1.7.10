/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.entity.CraftHumanEntity;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.inventory.InventoryHolder;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PlayerInventory
/*     */   implements IInventory
/*     */ {
/*  14 */   public ItemStack[] items = new ItemStack[36];
/*  15 */   public ItemStack[] armor = new ItemStack[4];
/*     */   
/*     */   public int itemInHandIndex;
/*     */   
/*     */   public EntityHuman player;
/*     */   private ItemStack g;
/*     */   public boolean e;
/*  22 */   public List<HumanEntity> transaction = new ArrayList<HumanEntity>();
/*  23 */   private int maxStack = 64;
/*     */   
/*     */   public ItemStack[] getContents() {
/*  26 */     return this.items;
/*     */   }
/*     */   
/*     */   public ItemStack[] getArmorContents() {
/*  30 */     return this.armor;
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
/*     */   public InventoryHolder getOwner() {
/*  46 */     return (InventoryHolder)this.player.getBukkitEntity();
/*     */   }
/*     */   
/*     */   public void setMaxStackSize(int size) {
/*  50 */     this.maxStack = size;
/*     */   }
/*     */ 
/*     */   
/*     */   public PlayerInventory(EntityHuman entityhuman) {
/*  55 */     this.player = entityhuman;
/*     */   }
/*     */   
/*     */   public ItemStack getItemInHand() {
/*  59 */     return (this.itemInHandIndex < 9 && this.itemInHandIndex >= 0) ? this.items[this.itemInHandIndex] : null;
/*     */   }
/*     */   
/*     */   public static int getHotbarSize() {
/*  63 */     return 9;
/*     */   }
/*     */   
/*     */   private int c(Item item) {
/*  67 */     for (int i = 0; i < this.items.length; i++) {
/*  68 */       if (this.items[i] != null && this.items[i].getItem() == item) {
/*  69 */         return i;
/*     */       }
/*     */     } 
/*     */     
/*  73 */     return -1;
/*     */   }
/*     */   
/*     */   private int firstPartial(ItemStack itemstack) {
/*  77 */     for (int i = 0; i < this.items.length; i++) {
/*  78 */       if (this.items[i] != null && this.items[i].getItem() == itemstack.getItem() && this.items[i].isStackable() && (this.items[i]).count < this.items[i].getMaxStackSize() && (this.items[i]).count < getMaxStackSize() && (!this.items[i].usesData() || this.items[i].getData() == itemstack.getData()) && ItemStack.equals(this.items[i], itemstack)) {
/*  79 */         return i;
/*     */       }
/*     */     } 
/*     */     
/*  83 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int canHold(ItemStack itemstack) {
/*  88 */     int remains = itemstack.count;
/*  89 */     for (int i = 0; i < this.items.length; i++) {
/*  90 */       if (this.items[i] == null) return itemstack.count;
/*     */ 
/*     */       
/*  93 */       if (this.items[i] != null && this.items[i].getItem() == itemstack.getItem() && this.items[i].isStackable() && (this.items[i]).count < this.items[i].getMaxStackSize() && (this.items[i]).count < getMaxStackSize() && (!this.items[i].usesData() || this.items[i].getData() == itemstack.getData()) && ItemStack.equals(this.items[i], itemstack)) {
/*  94 */         remains -= ((this.items[i].getMaxStackSize() < getMaxStackSize()) ? this.items[i].getMaxStackSize() : getMaxStackSize()) - (this.items[i]).count;
/*     */       }
/*  96 */       if (remains <= 0) return itemstack.count; 
/*     */     } 
/*  98 */     return itemstack.count - remains;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getFirstEmptySlotIndex() {
/* 103 */     for (int i = 0; i < this.items.length; i++) {
/* 104 */       if (this.items[i] == null) {
/* 105 */         return i;
/*     */       }
/*     */     } 
/*     */     
/* 109 */     return -1;
/*     */   }
/*     */   
/*     */   public int a(Item item, int i) {
/* 113 */     int j = 0;
/*     */ 
/*     */     
/*     */     int k;
/*     */     
/* 118 */     for (k = 0; k < this.items.length; k++) {
/* 119 */       ItemStack itemstack = this.items[k];
/* 120 */       if (itemstack != null && (item == null || itemstack.getItem() == item) && (i <= -1 || itemstack.getData() == i)) {
/* 121 */         j += itemstack.count;
/* 122 */         this.items[k] = null;
/*     */       } 
/*     */     } 
/*     */     
/* 126 */     for (k = 0; k < this.armor.length; k++) {
/* 127 */       ItemStack itemstack = this.armor[k];
/* 128 */       if (itemstack != null && (item == null || itemstack.getItem() == item) && (i <= -1 || itemstack.getData() == i)) {
/* 129 */         j += itemstack.count;
/* 130 */         this.armor[k] = null;
/*     */       } 
/*     */     } 
/*     */     
/* 134 */     if (this.g != null) {
/* 135 */       if (item != null && this.g.getItem() != item) {
/* 136 */         return j;
/*     */       }
/*     */       
/* 139 */       if (i > -1 && this.g.getData() != i) {
/* 140 */         return j;
/*     */       }
/*     */       
/* 143 */       j += this.g.count;
/* 144 */       setCarried((ItemStack)null);
/*     */     } 
/*     */     
/* 147 */     return j;
/*     */   }
/*     */   
/*     */   private int e(ItemStack itemstack) {
/* 151 */     Item item = itemstack.getItem();
/* 152 */     int i = itemstack.count;
/*     */ 
/*     */     
/* 155 */     if (itemstack.getMaxStackSize() == 1) {
/* 156 */       int m = getFirstEmptySlotIndex();
/* 157 */       if (m < 0) {
/* 158 */         return i;
/*     */       }
/* 160 */       if (this.items[m] == null) {
/* 161 */         this.items[m] = ItemStack.b(itemstack);
/*     */       }
/*     */       
/* 164 */       return 0;
/*     */     } 
/*     */     
/* 167 */     int j = firstPartial(itemstack);
/* 168 */     if (j < 0) {
/* 169 */       j = getFirstEmptySlotIndex();
/*     */     }
/*     */     
/* 172 */     if (j < 0) {
/* 173 */       return i;
/*     */     }
/* 175 */     if (this.items[j] == null) {
/* 176 */       this.items[j] = new ItemStack(item, 0, itemstack.getData());
/* 177 */       if (itemstack.hasTag()) {
/* 178 */         this.items[j].setTag((NBTTagCompound)itemstack.getTag().clone());
/*     */       }
/*     */     } 
/*     */     
/* 182 */     int k = i;
/*     */     
/* 184 */     if (i > this.items[j].getMaxStackSize() - (this.items[j]).count) {
/* 185 */       k = this.items[j].getMaxStackSize() - (this.items[j]).count;
/*     */     }
/*     */     
/* 188 */     if (k > getMaxStackSize() - (this.items[j]).count) {
/* 189 */       k = getMaxStackSize() - (this.items[j]).count;
/*     */     }
/*     */     
/* 192 */     if (k == 0) {
/* 193 */       return i;
/*     */     }
/* 195 */     i -= k;
/* 196 */     (this.items[j]).count += k;
/* 197 */     (this.items[j]).c = 5;
/* 198 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void k() {
/* 205 */     for (int i = 0; i < this.items.length; i++) {
/* 206 */       if (this.items[i] != null) {
/* 207 */         this.items[i].a(this.player.world, this.player, i, (this.itemInHandIndex == i));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean a(Item item) {
/* 213 */     int i = c(item);
/*     */     
/* 215 */     if (i < 0) {
/* 216 */       return false;
/*     */     }
/* 218 */     if (--(this.items[i]).count <= 0) {
/* 219 */       this.items[i] = null;
/*     */     }
/*     */     
/* 222 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean b(Item item) {
/* 227 */     int i = c(item);
/*     */     
/* 229 */     return (i >= 0);
/*     */   }
/*     */   
/*     */   public boolean pickup(ItemStack itemstack) {
/* 233 */     if (itemstack != null && itemstack.count != 0 && itemstack.getItem() != null) {
/*     */       try {
/*     */         int i;
/*     */         
/* 237 */         if (itemstack.i()) {
/* 238 */           i = getFirstEmptySlotIndex();
/* 239 */           if (i >= 0) {
/* 240 */             this.items[i] = ItemStack.b(itemstack);
/* 241 */             (this.items[i]).c = 5;
/* 242 */             itemstack.count = 0;
/* 243 */             return true;
/* 244 */           }  if (this.player.abilities.canInstantlyBuild) {
/* 245 */             itemstack.count = 0;
/* 246 */             return true;
/*     */           } 
/* 248 */           return false;
/*     */         } 
/*     */         
/*     */         do {
/* 252 */           i = itemstack.count;
/* 253 */           itemstack.count = e(itemstack);
/* 254 */         } while (itemstack.count > 0 && itemstack.count < i);
/*     */         
/* 256 */         if (itemstack.count == i && this.player.abilities.canInstantlyBuild) {
/* 257 */           itemstack.count = 0;
/* 258 */           return true;
/*     */         } 
/* 260 */         return (itemstack.count < i);
/*     */       
/*     */       }
/* 263 */       catch (Throwable throwable) {
/* 264 */         CrashReport crashreport = CrashReport.a(throwable, "Adding item to inventory");
/* 265 */         CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Item being added");
/*     */         
/* 267 */         crashreportsystemdetails.a("Item ID", Integer.valueOf(Item.getId(itemstack.getItem())));
/* 268 */         crashreportsystemdetails.a("Item data", Integer.valueOf(itemstack.getData()));
/* 269 */         crashreportsystemdetails.a("Item name", new CrashReportItemName(this, itemstack));
/* 270 */         throw new ReportedException(crashreport);
/*     */       } 
/*     */     }
/* 273 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack splitStack(int i, int j) {
/* 278 */     ItemStack[] aitemstack = this.items;
/*     */     
/* 280 */     if (i >= this.items.length) {
/* 281 */       aitemstack = this.armor;
/* 282 */       i -= this.items.length;
/*     */     } 
/*     */     
/* 285 */     if (aitemstack[i] != null) {
/*     */ 
/*     */       
/* 288 */       if ((aitemstack[i]).count <= j) {
/* 289 */         ItemStack itemStack = aitemstack[i];
/* 290 */         aitemstack[i] = null;
/* 291 */         return itemStack;
/*     */       } 
/* 293 */       ItemStack itemstack = aitemstack[i].a(j);
/* 294 */       if ((aitemstack[i]).count == 0) {
/* 295 */         aitemstack[i] = null;
/*     */       }
/*     */       
/* 298 */       return itemstack;
/*     */     } 
/*     */     
/* 301 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack splitWithoutUpdate(int i) {
/* 306 */     ItemStack[] aitemstack = this.items;
/*     */     
/* 308 */     if (i >= this.items.length) {
/* 309 */       aitemstack = this.armor;
/* 310 */       i -= this.items.length;
/*     */     } 
/*     */     
/* 313 */     if (aitemstack[i] != null) {
/* 314 */       ItemStack itemstack = aitemstack[i];
/*     */       
/* 316 */       aitemstack[i] = null;
/* 317 */       return itemstack;
/*     */     } 
/* 319 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setItem(int i, ItemStack itemstack) {
/* 324 */     ItemStack[] aitemstack = this.items;
/*     */     
/* 326 */     if (i >= aitemstack.length) {
/* 327 */       i -= aitemstack.length;
/* 328 */       aitemstack = this.armor;
/*     */     } 
/*     */     
/* 331 */     aitemstack[i] = itemstack;
/*     */   }
/*     */   
/*     */   public float a(Block block) {
/* 335 */     float f = 1.0F;
/*     */     
/* 337 */     if (this.items[this.itemInHandIndex] != null) {
/* 338 */       f *= this.items[this.itemInHandIndex].a(block);
/*     */     }
/*     */     
/* 341 */     return f;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagList a(NBTTagList nbttaglist) {
/*     */     int i;
/* 348 */     for (i = 0; i < this.items.length; i++) {
/* 349 */       if (this.items[i] != null) {
/* 350 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 351 */         nbttagcompound.setByte("Slot", (byte)i);
/* 352 */         this.items[i].save(nbttagcompound);
/* 353 */         nbttaglist.add(nbttagcompound);
/*     */       } 
/*     */     } 
/*     */     
/* 357 */     for (i = 0; i < this.armor.length; i++) {
/* 358 */       if (this.armor[i] != null) {
/* 359 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 360 */         nbttagcompound.setByte("Slot", (byte)(i + 100));
/* 361 */         this.armor[i].save(nbttagcompound);
/* 362 */         nbttaglist.add(nbttagcompound);
/*     */       } 
/*     */     } 
/*     */     
/* 366 */     return nbttaglist;
/*     */   }
/*     */   
/*     */   public void b(NBTTagList nbttaglist) {
/* 370 */     this.items = new ItemStack[36];
/* 371 */     this.armor = new ItemStack[4];
/*     */     
/* 373 */     for (int i = 0; i < nbttaglist.size(); i++) {
/* 374 */       NBTTagCompound nbttagcompound = nbttaglist.get(i);
/* 375 */       int j = nbttagcompound.getByte("Slot") & 0xFF;
/* 376 */       ItemStack itemstack = ItemStack.createStack(nbttagcompound);
/*     */       
/* 378 */       if (itemstack != null) {
/* 379 */         if (j >= 0 && j < this.items.length) {
/* 380 */           this.items[j] = itemstack;
/*     */         }
/*     */         
/* 383 */         if (j >= 100 && j < this.armor.length + 100) {
/* 384 */           this.armor[j - 100] = itemstack;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getSize() {
/* 391 */     return this.items.length + 4;
/*     */   }
/*     */   
/*     */   public ItemStack getItem(int i) {
/* 395 */     ItemStack[] aitemstack = this.items;
/*     */     
/* 397 */     if (i >= aitemstack.length) {
/* 398 */       i -= aitemstack.length;
/* 399 */       aitemstack = this.armor;
/*     */     } 
/*     */     
/* 402 */     return aitemstack[i];
/*     */   }
/*     */   
/*     */   public String getInventoryName() {
/* 406 */     return "container.inventory";
/*     */   }
/*     */   
/*     */   public boolean k_() {
/* 410 */     return false;
/*     */   }
/*     */   
/*     */   public int getMaxStackSize() {
/* 414 */     return this.maxStack;
/*     */   }
/*     */   
/*     */   public boolean b(Block block) {
/* 418 */     if (block.getMaterial().isAlwaysDestroyable()) {
/* 419 */       return true;
/*     */     }
/* 421 */     ItemStack itemstack = getItem(this.itemInHandIndex);
/*     */     
/* 423 */     return (itemstack != null) ? itemstack.b(block) : false;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack d(int i) {
/* 428 */     return this.armor[i];
/*     */   }
/*     */   
/*     */   public int l() {
/* 432 */     int i = 0;
/*     */     
/* 434 */     for (int j = 0; j < this.armor.length; j++) {
/* 435 */       if (this.armor[j] != null && this.armor[j].getItem() instanceof ItemArmor) {
/* 436 */         int k = ((ItemArmor)this.armor[j].getItem()).c;
/*     */         
/* 438 */         i += k;
/*     */       } 
/*     */     } 
/*     */     
/* 442 */     return i;
/*     */   }
/*     */   
/*     */   public void a(float f) {
/* 446 */     f /= 4.0F;
/* 447 */     if (f < 1.0F) {
/* 448 */       f = 1.0F;
/*     */     }
/*     */     
/* 451 */     for (int i = 0; i < this.armor.length; i++) {
/* 452 */       if (this.armor[i] != null && this.armor[i].getItem() instanceof ItemArmor) {
/* 453 */         this.armor[i].damage((int)f, this.player);
/* 454 */         if ((this.armor[i]).count == 0) {
/* 455 */           this.armor[i] = null;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void m() {
/*     */     int i;
/* 464 */     for (i = 0; i < this.items.length; i++) {
/* 465 */       if (this.items[i] != null) {
/* 466 */         this.player.a(this.items[i], true, false);
/* 467 */         this.items[i] = null;
/*     */       } 
/*     */     } 
/*     */     
/* 471 */     for (i = 0; i < this.armor.length; i++) {
/* 472 */       if (this.armor[i] != null) {
/* 473 */         this.player.a(this.armor[i], true, false);
/* 474 */         this.armor[i] = null;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void update() {
/* 480 */     this.e = true;
/*     */   }
/*     */   
/*     */   public void setCarried(ItemStack itemstack) {
/* 484 */     this.g = itemstack;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getCarried() {
/* 489 */     if (this.g != null && this.g.count == 0) {
/* 490 */       setCarried(null);
/*     */     }
/*     */     
/* 493 */     return this.g;
/*     */   }
/*     */   
/*     */   public boolean a(EntityHuman entityhuman) {
/* 497 */     return this.player.dead ? false : ((entityhuman.f(this.player) <= 64.0D));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean c(ItemStack itemstack) {
/*     */     int i;
/* 503 */     for (i = 0; i < this.armor.length; i++) {
/* 504 */       if (this.armor[i] != null && this.armor[i].doMaterialsMatch(itemstack)) {
/* 505 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 509 */     for (i = 0; i < this.items.length; i++) {
/* 510 */       if (this.items[i] != null && this.items[i].doMaterialsMatch(itemstack)) {
/* 511 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 515 */     return false;
/*     */   }
/*     */   
/*     */   public void startOpen() {}
/*     */   
/*     */   public void closeContainer() {}
/*     */   
/*     */   public boolean b(int i, ItemStack itemstack) {
/* 523 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void b(PlayerInventory playerinventory) {
/*     */     int i;
/* 529 */     for (i = 0; i < this.items.length; i++) {
/* 530 */       this.items[i] = ItemStack.b(playerinventory.items[i]);
/*     */     }
/*     */     
/* 533 */     for (i = 0; i < this.armor.length; i++) {
/* 534 */       this.armor[i] = ItemStack.b(playerinventory.armor[i]);
/*     */     }
/*     */     
/* 537 */     this.itemInHandIndex = playerinventory.itemInHandIndex;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PlayerInventory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
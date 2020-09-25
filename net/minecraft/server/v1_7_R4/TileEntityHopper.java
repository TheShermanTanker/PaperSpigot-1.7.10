/*     */ package net.minecraft.server.v1_7_R4;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.entity.CraftHumanEntity;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventoryDoubleChest;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.entity.Item;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.inventory.InventoryMoveItemEvent;
/*     */ import org.bukkit.event.inventory.InventoryPickupItemEvent;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ public class TileEntityHopper extends TileEntity implements IHopper {
/*  16 */   private ItemStack[] a = new ItemStack[5];
/*     */   private String i;
/*  18 */   private int j = -1;
/*     */ 
/*     */   
/*  21 */   private long nextTick = -1L;
/*  22 */   private long lastTick = -1L;
/*     */ 
/*     */   
/*     */   public void makeTick() {
/*  26 */     if (!j()) {
/*  27 */       c(0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void scheduleHopperTick() {
/*  33 */     if (this.world != null && this.world.spigotConfig.altHopperTicking) {
/*  34 */       makeTick();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void convertToScheduling() {
/*  42 */     c(this.j);
/*     */   }
/*     */ 
/*     */   
/*     */   public void convertToPolling() {
/*     */     long cooldownDiff;
/*  48 */     if (this.lastTick == this.world.getTime()) {
/*  49 */       cooldownDiff = this.nextTick - this.world.getTime();
/*     */     } else {
/*  51 */       cooldownDiff = this.nextTick - this.world.getTime() + 1L;
/*     */     } 
/*  53 */     c((int)Math.max(0L, Math.min(cooldownDiff, 2147483647L)));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  58 */   public List<HumanEntity> transaction = new ArrayList<HumanEntity>();
/*  59 */   private int maxStack = 64;
/*     */   
/*     */   public ItemStack[] getContents() {
/*  62 */     return this.a;
/*     */   }
/*     */   
/*     */   public void onOpen(CraftHumanEntity who) {
/*  66 */     this.transaction.add(who);
/*     */   }
/*     */   
/*     */   public void onClose(CraftHumanEntity who) {
/*  70 */     this.transaction.remove(who);
/*     */   }
/*     */   
/*     */   public List<HumanEntity> getViewers() {
/*  74 */     return this.transaction;
/*     */   }
/*     */   
/*     */   public void setMaxStackSize(int size) {
/*  78 */     this.maxStack = size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/*  85 */     super.a(nbttagcompound);
/*  86 */     NBTTagList nbttaglist = nbttagcompound.getList("Items", 10);
/*     */     
/*  88 */     this.a = new ItemStack[getSize()];
/*  89 */     if (nbttagcompound.hasKeyOfType("CustomName", 8)) {
/*  90 */       this.i = nbttagcompound.getString("CustomName");
/*     */     }
/*     */     
/*  93 */     this.j = nbttagcompound.getInt("TransferCooldown");
/*     */     
/*  95 */     for (int i = 0; i < nbttaglist.size(); i++) {
/*  96 */       NBTTagCompound nbttagcompound1 = nbttaglist.get(i);
/*  97 */       byte b0 = nbttagcompound1.getByte("Slot");
/*     */       
/*  99 */       if (b0 >= 0 && b0 < this.a.length) {
/* 100 */         this.a[b0] = ItemStack.createStack(nbttagcompound1);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/* 106 */     super.b(nbttagcompound);
/* 107 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/* 109 */     for (int i = 0; i < this.a.length; i++) {
/* 110 */       if (this.a[i] != null) {
/* 111 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*     */         
/* 113 */         nbttagcompound1.setByte("Slot", (byte)i);
/* 114 */         this.a[i].save(nbttagcompound1);
/* 115 */         nbttaglist.add(nbttagcompound1);
/*     */       } 
/*     */     } 
/*     */     
/* 119 */     nbttagcompound.set("Items", nbttaglist);
/*     */     
/* 121 */     if (this.world != null && this.world.spigotConfig.altHopperTicking) {
/*     */       long cooldownDiff;
/* 123 */       if (this.lastTick == this.world.getTime()) {
/* 124 */         cooldownDiff = this.nextTick - this.world.getTime();
/*     */       } else {
/* 126 */         cooldownDiff = this.nextTick - this.world.getTime() + 1L;
/*     */       } 
/* 128 */       nbttagcompound.setInt("TransferCooldown", (int)Math.max(0L, Math.min(cooldownDiff, 2147483647L)));
/*     */     } else {
/*     */       
/* 131 */       nbttagcompound.setInt("TransferCooldown", this.j);
/*     */     } 
/*     */     
/* 134 */     if (k_()) {
/* 135 */       nbttagcompound.setString("CustomName", this.i);
/*     */     }
/*     */   }
/*     */   
/*     */   public void update() {
/* 140 */     super.update();
/*     */     
/* 142 */     scheduleHopperTick();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSize() {
/* 147 */     return this.a.length;
/*     */   }
/*     */   
/*     */   public ItemStack getItem(int i) {
/* 151 */     return this.a[i];
/*     */   }
/*     */   
/*     */   public ItemStack splitStack(int i, int j) {
/* 155 */     if (this.a[i] != null) {
/*     */ 
/*     */       
/* 158 */       if ((this.a[i]).count <= j) {
/* 159 */         ItemStack itemStack = this.a[i];
/* 160 */         this.a[i] = null;
/* 161 */         return itemStack;
/*     */       } 
/* 163 */       ItemStack itemstack = this.a[i].a(j);
/* 164 */       if ((this.a[i]).count == 0) {
/* 165 */         this.a[i] = null;
/*     */       }
/*     */       
/* 168 */       return itemstack;
/*     */     } 
/*     */     
/* 171 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack splitWithoutUpdate(int i) {
/* 176 */     if (this.a[i] != null) {
/* 177 */       ItemStack itemstack = this.a[i];
/*     */       
/* 179 */       this.a[i] = null;
/* 180 */       return itemstack;
/*     */     } 
/* 182 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setItem(int i, ItemStack itemstack) {
/* 187 */     this.a[i] = itemstack;
/* 188 */     if (itemstack != null && itemstack.count > getMaxStackSize()) {
/* 189 */       itemstack.count = getMaxStackSize();
/*     */     }
/*     */   }
/*     */   
/*     */   public String getInventoryName() {
/* 194 */     return k_() ? this.i : "container.hopper";
/*     */   }
/*     */   
/*     */   public boolean k_() {
/* 198 */     return (this.i != null && this.i.length() > 0);
/*     */   }
/*     */   
/*     */   public void a(String s) {
/* 202 */     this.i = s;
/*     */   }
/*     */   
/*     */   public int getMaxStackSize() {
/* 206 */     return this.maxStack;
/*     */   }
/*     */   
/*     */   public boolean a(EntityHuman entityhuman) {
/* 210 */     return (this.world.getTileEntity(this.x, this.y, this.z) != this) ? false : ((entityhuman.e(this.x + 0.5D, this.y + 0.5D, this.z + 0.5D) <= 64.0D));
/*     */   }
/*     */   
/*     */   public void startOpen() {}
/*     */   
/*     */   public void closeContainer() {}
/*     */   
/*     */   public boolean b(int i, ItemStack itemstack) {
/* 218 */     return true;
/*     */   }
/*     */   
/*     */   public void h() {
/* 222 */     if (this.world != null && !this.world.isStatic)
/*     */     {
/* 224 */       if (this.world.spigotConfig.altHopperTicking) {
/* 225 */         this.lastTick = this.world.getTime();
/* 226 */         if (this.nextTick == this.world.getTime())
/*     */         {
/* 228 */           i();
/*     */         }
/*     */       } else {
/* 231 */         this.j--;
/* 232 */         if (!j()) {
/* 233 */           c(0);
/* 234 */           i();
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean i() {
/* 242 */     if (this.world != null && !this.world.isStatic) {
/* 243 */       if (!j() && BlockHopper.c(p())) {
/* 244 */         boolean flag = false;
/*     */         
/* 246 */         if (!k()) {
/* 247 */           flag = y();
/*     */         }
/*     */         
/* 250 */         if (!l()) {
/* 251 */           flag = (suckInItems(this) || flag);
/*     */         }
/*     */         
/* 254 */         if (flag) {
/* 255 */           c(this.world.spigotConfig.hopperTransfer);
/* 256 */           update();
/* 257 */           return true;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 262 */       if (!this.world.spigotConfig.altHopperTicking && !j())
/*     */       {
/* 264 */         c(this.world.spigotConfig.hopperCheck);
/*     */       }
/*     */       
/* 267 */       return false;
/*     */     } 
/* 269 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean k() {
/* 274 */     ItemStack[] aitemstack = this.a;
/* 275 */     int i = aitemstack.length;
/*     */     
/* 277 */     for (int j = 0; j < i; j++) {
/* 278 */       ItemStack itemstack = aitemstack[j];
/*     */       
/* 280 */       if (itemstack != null) {
/* 281 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 285 */     return true;
/*     */   }
/*     */   
/*     */   private boolean l() {
/* 289 */     ItemStack[] aitemstack = this.a;
/* 290 */     int i = aitemstack.length;
/*     */     
/* 292 */     for (int j = 0; j < i; j++) {
/* 293 */       ItemStack itemstack = aitemstack[j];
/*     */       
/* 295 */       if (itemstack == null || itemstack.count != itemstack.getMaxStackSize()) {
/* 296 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 300 */     return true;
/*     */   }
/*     */   
/*     */   private boolean y() {
/* 304 */     IInventory iinventory = z();
/*     */     
/* 306 */     if (iinventory == null) {
/* 307 */       return false;
/*     */     }
/* 309 */     int i = Facing.OPPOSITE_FACING[BlockHopper.b(p())];
/*     */     
/* 311 */     if (a(iinventory, i)) {
/* 312 */       return false;
/*     */     }
/* 314 */     for (int j = 0; j < getSize(); j++) {
/* 315 */       if (getItem(j) != null) {
/* 316 */         Inventory destinationInventory; ItemStack itemstack = getItem(j).cloneItemStack();
/*     */         
/* 318 */         CraftItemStack oitemstack = CraftItemStack.asCraftMirror(splitStack(j, this.world.spigotConfig.hopperAmount));
/*     */ 
/*     */ 
/*     */         
/* 322 */         if (iinventory instanceof InventoryLargeChest) {
/* 323 */           CraftInventoryDoubleChest craftInventoryDoubleChest = new CraftInventoryDoubleChest((InventoryLargeChest)iinventory);
/*     */         } else {
/* 325 */           destinationInventory = iinventory.getOwner().getInventory();
/*     */         } 
/*     */         
/* 328 */         InventoryMoveItemEvent event = new InventoryMoveItemEvent(getOwner().getInventory(), (ItemStack)oitemstack.clone(), destinationInventory, true);
/* 329 */         getWorld().getServer().getPluginManager().callEvent((Event)event);
/* 330 */         if (event.isCancelled()) {
/* 331 */           setItem(j, itemstack);
/* 332 */           c(this.world.spigotConfig.hopperTransfer);
/* 333 */           return false;
/*     */         } 
/* 335 */         int origCount = event.getItem().getAmount();
/* 336 */         ItemStack itemstack1 = addItem(iinventory, CraftItemStack.asNMSCopy(event.getItem()), i);
/* 337 */         if (itemstack1 == null || itemstack1.count == 0) {
/* 338 */           if (event.getItem().equals(oitemstack)) {
/* 339 */             iinventory.update();
/*     */           } else {
/* 341 */             setItem(j, itemstack);
/*     */           } 
/*     */           
/* 344 */           return true;
/*     */         } 
/* 346 */         itemstack.count -= origCount - itemstack1.count;
/* 347 */         setItem(j, itemstack);
/*     */       } 
/*     */     } 
/*     */     
/* 351 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean a(IInventory iinventory, int i) {
/* 357 */     if (iinventory instanceof IWorldInventory && i > -1) {
/* 358 */       IWorldInventory iworldinventory = (IWorldInventory)iinventory;
/* 359 */       int[] aint = iworldinventory.getSlotsForFace(i);
/*     */       
/* 361 */       for (int j = 0; j < aint.length; j++) {
/* 362 */         ItemStack itemstack = iworldinventory.getItem(aint[j]);
/*     */         
/* 364 */         if (itemstack == null || itemstack.count != itemstack.getMaxStackSize()) {
/* 365 */           return false;
/*     */         }
/*     */       } 
/*     */     } else {
/* 369 */       int k = iinventory.getSize();
/*     */       
/* 371 */       for (int l = 0; l < k; l++) {
/* 372 */         ItemStack itemstack1 = iinventory.getItem(l);
/*     */         
/* 374 */         if (itemstack1 == null || itemstack1.count != itemstack1.getMaxStackSize()) {
/* 375 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 380 */     return true;
/*     */   }
/*     */   
/*     */   private static boolean b(IInventory iinventory, int i) {
/* 384 */     if (iinventory instanceof IWorldInventory && i > -1) {
/* 385 */       IWorldInventory iworldinventory = (IWorldInventory)iinventory;
/* 386 */       int[] aint = iworldinventory.getSlotsForFace(i);
/*     */       
/* 388 */       for (int j = 0; j < aint.length; j++) {
/* 389 */         if (iworldinventory.getItem(aint[j]) != null) {
/* 390 */           return false;
/*     */         }
/*     */       } 
/*     */     } else {
/* 394 */       int k = iinventory.getSize();
/*     */       
/* 396 */       for (int l = 0; l < k; l++) {
/* 397 */         if (iinventory.getItem(l) != null) {
/* 398 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 403 */     return true;
/*     */   }
/*     */   
/*     */   public static boolean suckInItems(IHopper ihopper) {
/* 407 */     IInventory iinventory = getSourceInventory(ihopper);
/*     */     
/* 409 */     if (iinventory != null) {
/* 410 */       byte b0 = 0;
/*     */       
/* 412 */       if (b(iinventory, b0)) {
/* 413 */         return false;
/*     */       }
/*     */       
/* 416 */       if (iinventory instanceof IWorldInventory && b0 > -1) {
/* 417 */         IWorldInventory iworldinventory = (IWorldInventory)iinventory;
/* 418 */         int[] aint = iworldinventory.getSlotsForFace(b0);
/*     */         
/* 420 */         for (int i = 0; i < aint.length; i++) {
/* 421 */           if (tryTakeInItemFromSlot(ihopper, iinventory, aint[i], b0)) {
/* 422 */             return true;
/*     */           }
/*     */         } 
/*     */       } else {
/* 426 */         int j = iinventory.getSize();
/*     */         
/* 428 */         for (int k = 0; k < j; k++) {
/* 429 */           if (tryTakeInItemFromSlot(ihopper, iinventory, k, b0)) {
/* 430 */             return true;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } else {
/* 435 */       EntityItem entityitem = getEntityItemAt(ihopper.getWorld(), ihopper.x(), ihopper.aD() + 1.0D, ihopper.aE());
/*     */       
/* 437 */       if (entityitem != null) {
/* 438 */         return addEntityItem(ihopper, entityitem);
/*     */       }
/*     */     } 
/*     */     
/* 442 */     return false;
/*     */   }
/*     */   
/*     */   private static boolean tryTakeInItemFromSlot(IHopper ihopper, IInventory iinventory, int i, int j) {
/* 446 */     ItemStack itemstack = iinventory.getItem(i);
/*     */     
/* 448 */     if (itemstack != null && canTakeItemFromInventory(iinventory, itemstack, i, j)) {
/* 449 */       Inventory sourceInventory; ItemStack itemstack1 = itemstack.cloneItemStack();
/*     */       
/* 451 */       CraftItemStack oitemstack = CraftItemStack.asCraftMirror(iinventory.splitStack(i, (ihopper.getWorld()).spigotConfig.hopperAmount));
/*     */ 
/*     */ 
/*     */       
/* 455 */       if (iinventory instanceof InventoryLargeChest) {
/* 456 */         CraftInventoryDoubleChest craftInventoryDoubleChest = new CraftInventoryDoubleChest((InventoryLargeChest)iinventory);
/*     */       } else {
/* 458 */         sourceInventory = iinventory.getOwner().getInventory();
/*     */       } 
/*     */       
/* 461 */       InventoryMoveItemEvent event = new InventoryMoveItemEvent(sourceInventory, (ItemStack)oitemstack.clone(), ihopper.getOwner().getInventory(), false);
/*     */       
/* 463 */       ihopper.getWorld().getServer().getPluginManager().callEvent((Event)event);
/* 464 */       if (event.isCancelled()) {
/* 465 */         iinventory.setItem(i, itemstack1);
/*     */         
/* 467 */         if (ihopper instanceof TileEntityHopper) {
/* 468 */           ((TileEntityHopper)ihopper).c((ihopper.getWorld()).spigotConfig.hopperTransfer);
/* 469 */         } else if (ihopper instanceof EntityMinecartHopper) {
/* 470 */           ((EntityMinecartHopper)ihopper).l((ihopper.getWorld()).spigotConfig.hopperTransfer / 2);
/*     */         } 
/*     */         
/* 473 */         return false;
/*     */       } 
/* 475 */       int origCount = event.getItem().getAmount();
/* 476 */       ItemStack itemstack2 = addItem(ihopper, CraftItemStack.asNMSCopy(event.getItem()), -1);
/*     */       
/* 478 */       if (itemstack2 == null || itemstack2.count == 0) {
/* 479 */         if (event.getItem().equals(oitemstack)) {
/* 480 */           iinventory.update();
/*     */         } else {
/* 482 */           iinventory.setItem(i, itemstack1);
/*     */         } 
/*     */ 
/*     */         
/* 486 */         return true;
/*     */       } 
/* 488 */       itemstack1.count -= origCount - itemstack2.count;
/*     */       
/* 490 */       iinventory.setItem(i, itemstack1);
/*     */     } 
/*     */     
/* 493 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean addEntityItem(IInventory iinventory, EntityItem entityitem) {
/* 497 */     boolean flag = false;
/*     */     
/* 499 */     if (entityitem == null) {
/* 500 */       return false;
/*     */     }
/*     */     
/* 503 */     InventoryPickupItemEvent event = new InventoryPickupItemEvent(iinventory.getOwner().getInventory(), (Item)entityitem.getBukkitEntity());
/* 504 */     entityitem.world.getServer().getPluginManager().callEvent((Event)event);
/* 505 */     if (event.isCancelled()) {
/* 506 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 510 */     ItemStack itemstack = entityitem.getItemStack().cloneItemStack();
/* 511 */     ItemStack itemstack1 = addItem(iinventory, itemstack, -1);
/*     */     
/* 513 */     if (itemstack1 != null && itemstack1.count != 0) {
/* 514 */       entityitem.setItemStack(itemstack1);
/*     */     } else {
/* 516 */       flag = true;
/* 517 */       entityitem.die();
/*     */     } 
/*     */     
/* 520 */     return flag;
/*     */   }
/*     */ 
/*     */   
/*     */   public static ItemStack addItem(IInventory iinventory, ItemStack itemstack, int i) {
/* 525 */     if (iinventory instanceof IWorldInventory && i > -1) {
/* 526 */       IWorldInventory iworldinventory = (IWorldInventory)iinventory;
/* 527 */       int[] aint = iworldinventory.getSlotsForFace(i);
/*     */       
/* 529 */       for (int j = 0; j < aint.length && itemstack != null && itemstack.count > 0; j++) {
/* 530 */         itemstack = tryMoveInItem(iinventory, itemstack, aint[j], i);
/*     */       }
/*     */     } else {
/* 533 */       int k = iinventory.getSize();
/*     */       
/* 535 */       for (int l = 0; l < k && itemstack != null && itemstack.count > 0; l++) {
/* 536 */         itemstack = tryMoveInItem(iinventory, itemstack, l, i);
/*     */       }
/*     */     } 
/*     */     
/* 540 */     if (itemstack != null && itemstack.count == 0) {
/* 541 */       itemstack = null;
/*     */     }
/*     */     
/* 544 */     return itemstack;
/*     */   }
/*     */   
/*     */   private static boolean canPlaceItemInInventory(IInventory iinventory, ItemStack itemstack, int i, int j) {
/* 548 */     return !iinventory.b(i, itemstack) ? false : ((!(iinventory instanceof IWorldInventory) || ((IWorldInventory)iinventory).canPlaceItemThroughFace(i, itemstack, j)));
/*     */   }
/*     */   
/*     */   private static boolean canTakeItemFromInventory(IInventory iinventory, ItemStack itemstack, int i, int j) {
/* 552 */     return (!(iinventory instanceof IWorldInventory) || ((IWorldInventory)iinventory).canTakeItemThroughFace(i, itemstack, j));
/*     */   }
/*     */   
/*     */   private static ItemStack tryMoveInItem(IInventory iinventory, ItemStack itemstack, int i, int j) {
/* 556 */     ItemStack itemstack1 = iinventory.getItem(i);
/*     */     
/* 558 */     if (canPlaceItemInInventory(iinventory, itemstack, i, j)) {
/* 559 */       boolean flag = false;
/*     */       
/* 561 */       if (itemstack1 == null) {
/* 562 */         iinventory.setItem(i, itemstack);
/* 563 */         itemstack = null;
/* 564 */         flag = true;
/* 565 */       } else if (canMergeItems(itemstack1, itemstack)) {
/* 566 */         int k = itemstack.getMaxStackSize() - itemstack1.count;
/* 567 */         int l = Math.min(itemstack.count, k);
/*     */         
/* 569 */         itemstack.count -= l;
/* 570 */         itemstack1.count += l;
/* 571 */         flag = (l > 0);
/*     */       } 
/*     */       
/* 574 */       if (flag) {
/* 575 */         if (iinventory instanceof TileEntityHopper) {
/* 576 */           ((TileEntityHopper)iinventory).c(((TileEntityHopper)iinventory).world.spigotConfig.hopperTransfer);
/* 577 */           iinventory.update();
/*     */         } 
/*     */         
/* 580 */         iinventory.update();
/*     */       } 
/*     */     } 
/*     */     
/* 584 */     return itemstack;
/*     */   }
/*     */   
/*     */   private IInventory z() {
/* 588 */     int i = BlockHopper.b(p());
/*     */     
/* 590 */     return getInventoryAt(getWorld(), (this.x + Facing.b[i]), (this.y + Facing.c[i]), (this.z + Facing.d[i]));
/*     */   }
/*     */   
/*     */   public static IInventory getSourceInventory(IHopper ihopper) {
/* 594 */     return getInventoryAt(ihopper.getWorld(), ihopper.x(), ihopper.aD() + 1.0D, ihopper.aE());
/*     */   }
/*     */   
/*     */   public static EntityItem getEntityItemAt(World world, double d0, double d1, double d2) {
/* 598 */     List<EntityItem> list = world.a(EntityItem.class, AxisAlignedBB.a(d0, d1, d2, d0 + 1.0D, d1 + 1.0D, d2 + 1.0D), IEntitySelector.a);
/*     */     
/* 600 */     return (list.size() > 0) ? list.get(0) : null;
/*     */   }
/*     */   
/*     */   public static IInventory getInventoryAt(World world, double d0, double d1, double d2) {
/* 604 */     IInventory iinventory = null;
/* 605 */     int i = MathHelper.floor(d0);
/* 606 */     int j = MathHelper.floor(d1);
/* 607 */     int k = MathHelper.floor(d2);
/* 608 */     if (!world.isLoaded(i, j, k)) return null; 
/* 609 */     TileEntity tileentity = world.getTileEntity(i, j, k);
/*     */     
/* 611 */     if (tileentity != null && tileentity instanceof IInventory) {
/* 612 */       iinventory = (IInventory)tileentity;
/* 613 */       if (iinventory instanceof TileEntityChest) {
/* 614 */         Block block = world.getType(i, j, k);
/*     */         
/* 616 */         if (block instanceof BlockChest) {
/* 617 */           iinventory = ((BlockChest)block).m(world, i, j, k);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 622 */     if (iinventory == null) {
/* 623 */       List<IInventory> list = world.getEntities((Entity)null, AxisAlignedBB.a(d0, d1, d2, d0 + 1.0D, d1 + 1.0D, d2 + 1.0D), IEntitySelector.c);
/*     */       
/* 625 */       if (list != null && list.size() > 0) {
/* 626 */         iinventory = list.get(world.random.nextInt(list.size()));
/*     */       }
/*     */     } 
/*     */     
/* 630 */     return iinventory;
/*     */   }
/*     */   
/*     */   private static boolean canMergeItems(ItemStack itemstack, ItemStack itemstack1) {
/* 634 */     return (itemstack.getItem() != itemstack1.getItem()) ? false : ((itemstack.getData() != itemstack1.getData()) ? false : ((itemstack.count > itemstack.getMaxStackSize()) ? false : ItemStack.equals(itemstack, itemstack1)));
/*     */   }
/*     */   
/*     */   public double x() {
/* 638 */     return this.x;
/*     */   }
/*     */   
/*     */   public double aD() {
/* 642 */     return this.y;
/*     */   }
/*     */   
/*     */   public double aE() {
/* 646 */     return this.z;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void c(int i) {
/* 652 */     if (this.world != null && this.world.spigotConfig.altHopperTicking) {
/* 653 */       if (i <= 0) {
/* 654 */         i = 1;
/*     */       }
/* 656 */       if (this.lastTick == this.world.getTime()) {
/* 657 */         this.nextTick = this.world.getTime() + i;
/*     */       } else {
/* 659 */         this.nextTick = this.world.getTime() + i - 1L;
/*     */       } 
/*     */     } else {
/* 662 */       this.j = i;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean j() {
/* 669 */     if (this.world != null && this.world.spigotConfig.altHopperTicking) {
/* 670 */       if (this.lastTick == this.world.getTime()) {
/* 671 */         return (this.nextTick > this.world.getTime());
/*     */       }
/* 673 */       return (this.nextTick >= this.world.getTime());
/*     */     } 
/*     */     
/* 676 */     return (this.j > 0);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\TileEntityHopper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
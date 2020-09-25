/*     */ package org.bukkit.craftbukkit.v1_7_R4.inventory;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import net.minecraft.server.v1_7_R4.IInventory;
/*     */ import net.minecraft.server.v1_7_R4.ItemStack;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.event.inventory.InventoryType;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.InventoryHolder;
/*     */ import org.bukkit.inventory.ItemStack;
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
/*     */ public class CraftInventory
/*     */   implements Inventory
/*     */ {
/*     */   protected final IInventory inventory;
/*     */   
/*     */   public CraftInventory(IInventory inventory) {
/*  33 */     this.inventory = inventory;
/*     */   }
/*     */   
/*     */   public IInventory getInventory() {
/*  37 */     return this.inventory;
/*     */   }
/*     */   
/*     */   public int getSize() {
/*  41 */     return getInventory().getSize();
/*     */   }
/*     */   
/*     */   public String getName() {
/*  45 */     return getInventory().getInventoryName();
/*     */   }
/*     */   
/*     */   public ItemStack getItem(int index) {
/*  49 */     ItemStack item = getInventory().getItem(index);
/*  50 */     return (item == null) ? null : CraftItemStack.asCraftMirror(item);
/*     */   }
/*     */   
/*     */   public ItemStack[] getContents() {
/*  54 */     ItemStack[] items = new ItemStack[getSize()];
/*  55 */     ItemStack[] mcItems = getInventory().getContents();
/*     */     
/*  57 */     int size = Math.min(items.length, mcItems.length);
/*  58 */     for (int i = 0; i < size; i++) {
/*  59 */       items[i] = (mcItems[i] == null) ? null : CraftItemStack.asCraftMirror(mcItems[i]);
/*     */     }
/*     */     
/*  62 */     return items;
/*     */   }
/*     */   
/*     */   public void setContents(ItemStack[] items) {
/*  66 */     if ((getInventory().getContents()).length < items.length) {
/*  67 */       throw new IllegalArgumentException("Invalid inventory size; expected " + (getInventory().getContents()).length + " or less");
/*     */     }
/*     */     
/*  70 */     ItemStack[] mcItems = getInventory().getContents();
/*     */     
/*  72 */     for (int i = 0; i < mcItems.length; i++) {
/*  73 */       if (i >= items.length) {
/*  74 */         mcItems[i] = null;
/*     */       } else {
/*  76 */         mcItems[i] = CraftItemStack.asNMSCopy(items[i]);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setItem(int index, ItemStack item) {
/*  82 */     getInventory().setItem(index, (item == null || item.getTypeId() == 0) ? null : CraftItemStack.asNMSCopy(item));
/*     */   }
/*     */   
/*     */   public boolean contains(int materialId) {
/*  86 */     for (ItemStack item : getContents()) {
/*  87 */       if (item != null && item.getTypeId() == materialId) {
/*  88 */         return true;
/*     */       }
/*     */     } 
/*  91 */     return false;
/*     */   }
/*     */   
/*     */   public boolean contains(Material material) {
/*  95 */     Validate.notNull(material, "Material cannot be null");
/*  96 */     return contains(material.getId());
/*     */   }
/*     */   
/*     */   public boolean contains(ItemStack item) {
/* 100 */     if (item == null) {
/* 101 */       return false;
/*     */     }
/* 103 */     for (ItemStack i : getContents()) {
/* 104 */       if (item.equals(i)) {
/* 105 */         return true;
/*     */       }
/*     */     } 
/* 108 */     return false;
/*     */   }
/*     */   
/*     */   public boolean contains(int materialId, int amount) {
/* 112 */     if (amount <= 0) {
/* 113 */       return true;
/*     */     }
/* 115 */     for (ItemStack item : getContents()) {
/* 116 */       if (item != null && item.getTypeId() == materialId && (
/* 117 */         amount -= item.getAmount()) <= 0) {
/* 118 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 122 */     return false;
/*     */   }
/*     */   
/*     */   public boolean contains(Material material, int amount) {
/* 126 */     Validate.notNull(material, "Material cannot be null");
/* 127 */     return contains(material.getId(), amount);
/*     */   }
/*     */   
/*     */   public boolean contains(ItemStack item, int amount) {
/* 131 */     if (item == null) {
/* 132 */       return false;
/*     */     }
/* 134 */     if (amount <= 0) {
/* 135 */       return true;
/*     */     }
/* 137 */     for (ItemStack i : getContents()) {
/* 138 */       if (item.equals(i) && --amount <= 0) {
/* 139 */         return true;
/*     */       }
/*     */     } 
/* 142 */     return false;
/*     */   }
/*     */   
/*     */   public boolean containsAtLeast(ItemStack item, int amount) {
/* 146 */     if (item == null) {
/* 147 */       return false;
/*     */     }
/* 149 */     if (amount <= 0) {
/* 150 */       return true;
/*     */     }
/* 152 */     for (ItemStack i : getContents()) {
/* 153 */       if (item.isSimilar(i) && (amount -= i.getAmount()) <= 0) {
/* 154 */         return true;
/*     */       }
/*     */     } 
/* 157 */     return false;
/*     */   }
/*     */   
/*     */   public HashMap<Integer, ItemStack> all(int materialId) {
/* 161 */     HashMap<Integer, ItemStack> slots = new HashMap<Integer, ItemStack>();
/*     */     
/* 163 */     ItemStack[] inventory = getContents();
/* 164 */     for (int i = 0; i < inventory.length; i++) {
/* 165 */       ItemStack item = inventory[i];
/* 166 */       if (item != null && item.getTypeId() == materialId) {
/* 167 */         slots.put(Integer.valueOf(i), item);
/*     */       }
/*     */     } 
/* 170 */     return slots;
/*     */   }
/*     */   
/*     */   public HashMap<Integer, ItemStack> all(Material material) {
/* 174 */     Validate.notNull(material, "Material cannot be null");
/* 175 */     return all(material.getId());
/*     */   }
/*     */   
/*     */   public HashMap<Integer, ItemStack> all(ItemStack item) {
/* 179 */     HashMap<Integer, ItemStack> slots = new HashMap<Integer, ItemStack>();
/* 180 */     if (item != null) {
/* 181 */       ItemStack[] inventory = getContents();
/* 182 */       for (int i = 0; i < inventory.length; i++) {
/* 183 */         if (item.equals(inventory[i])) {
/* 184 */           slots.put(Integer.valueOf(i), inventory[i]);
/*     */         }
/*     */       } 
/*     */     } 
/* 188 */     return slots;
/*     */   }
/*     */   
/*     */   public int first(int materialId) {
/* 192 */     ItemStack[] inventory = getContents();
/* 193 */     for (int i = 0; i < inventory.length; i++) {
/* 194 */       ItemStack item = inventory[i];
/* 195 */       if (item != null && item.getTypeId() == materialId) {
/* 196 */         return i;
/*     */       }
/*     */     } 
/* 199 */     return -1;
/*     */   }
/*     */   
/*     */   public int first(Material material) {
/* 203 */     Validate.notNull(material, "Material cannot be null");
/* 204 */     return first(material.getId());
/*     */   }
/*     */   
/*     */   public int first(ItemStack item) {
/* 208 */     return first(item, true);
/*     */   }
/*     */   
/*     */   private int first(ItemStack item, boolean withAmount) {
/* 212 */     if (item == null) {
/* 213 */       return -1;
/*     */     }
/* 215 */     ItemStack[] inventory = getContents();
/* 216 */     for (int i = 0; i < inventory.length; i++) {
/* 217 */       if (inventory[i] != null)
/*     */       {
/* 219 */         if (withAmount ? item.equals(inventory[i]) : item.isSimilar(inventory[i]))
/* 220 */           return i; 
/*     */       }
/*     */     } 
/* 223 */     return -1;
/*     */   }
/*     */   
/*     */   public int firstEmpty() {
/* 227 */     ItemStack[] inventory = getContents();
/* 228 */     for (int i = 0; i < inventory.length; i++) {
/* 229 */       if (inventory[i] == null) {
/* 230 */         return i;
/*     */       }
/*     */     } 
/* 233 */     return -1;
/*     */   }
/*     */   
/*     */   public int firstPartial(int materialId) {
/* 237 */     ItemStack[] inventory = getContents();
/* 238 */     for (int i = 0; i < inventory.length; i++) {
/* 239 */       ItemStack item = inventory[i];
/* 240 */       if (item != null && item.getTypeId() == materialId && item.getAmount() < item.getMaxStackSize()) {
/* 241 */         return i;
/*     */       }
/*     */     } 
/* 244 */     return -1;
/*     */   }
/*     */   
/*     */   public int firstPartial(Material material) {
/* 248 */     Validate.notNull(material, "Material cannot be null");
/* 249 */     return firstPartial(material.getId());
/*     */   }
/*     */   
/*     */   private int firstPartial(ItemStack item) {
/* 253 */     ItemStack[] inventory = getContents();
/* 254 */     ItemStack filteredItem = CraftItemStack.asCraftCopy(item);
/* 255 */     if (item == null) {
/* 256 */       return -1;
/*     */     }
/* 258 */     for (int i = 0; i < inventory.length; i++) {
/* 259 */       ItemStack cItem = inventory[i];
/* 260 */       if (cItem != null && cItem.getAmount() < cItem.getMaxStackSize() && cItem.isSimilar(filteredItem)) {
/* 261 */         return i;
/*     */       }
/*     */     } 
/* 264 */     return -1;
/*     */   }
/*     */   
/*     */   public HashMap<Integer, ItemStack> addItem(ItemStack... items) {
/* 268 */     Validate.noNullElements((Object[])items, "Item cannot be null");
/* 269 */     HashMap<Integer, ItemStack> leftover = new HashMap<Integer, ItemStack>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 277 */     for (int i = 0; i < items.length; i++) {
/* 278 */       ItemStack item = items[i];
/*     */       
/*     */       while (true) {
/* 281 */         int firstPartial = firstPartial(item);
/*     */ 
/*     */         
/* 284 */         if (firstPartial == -1) {
/*     */           
/* 286 */           int firstFree = firstEmpty();
/*     */           
/* 288 */           if (firstFree == -1) {
/*     */             
/* 290 */             leftover.put(Integer.valueOf(i), item);
/*     */             
/*     */             break;
/*     */           } 
/* 294 */           if (item.getAmount() > getMaxItemStack()) {
/* 295 */             CraftItemStack stack = CraftItemStack.asCraftCopy(item);
/* 296 */             stack.setAmount(getMaxItemStack());
/* 297 */             setItem(firstFree, stack);
/* 298 */             item.setAmount(item.getAmount() - getMaxItemStack());
/*     */             continue;
/*     */           } 
/* 301 */           setItem(firstFree, item);
/*     */ 
/*     */           
/*     */           break;
/*     */         } 
/*     */         
/* 307 */         ItemStack partialItem = getItem(firstPartial);
/*     */         
/* 309 */         int amount = item.getAmount();
/* 310 */         int partialAmount = partialItem.getAmount();
/* 311 */         int maxAmount = partialItem.getMaxStackSize();
/*     */ 
/*     */         
/* 314 */         if (amount + partialAmount <= maxAmount) {
/* 315 */           partialItem.setAmount(amount + partialAmount);
/*     */           
/*     */           break;
/*     */         } 
/*     */         
/* 320 */         partialItem.setAmount(maxAmount);
/* 321 */         item.setAmount(amount + partialAmount - maxAmount);
/*     */       } 
/*     */     } 
/*     */     
/* 325 */     return leftover;
/*     */   }
/*     */   
/*     */   public HashMap<Integer, ItemStack> removeItem(ItemStack... items) {
/* 329 */     Validate.notNull(items, "Items cannot be null");
/* 330 */     HashMap<Integer, ItemStack> leftover = new HashMap<Integer, ItemStack>();
/*     */ 
/*     */ 
/*     */     
/* 334 */     for (int i = 0; i < items.length; i++) {
/* 335 */       ItemStack item = items[i];
/* 336 */       int toDelete = item.getAmount();
/*     */       
/*     */       do {
/* 339 */         int first = first(item, false);
/*     */ 
/*     */         
/* 342 */         if (first == -1) {
/* 343 */           item.setAmount(toDelete);
/* 344 */           leftover.put(Integer.valueOf(i), item);
/*     */           break;
/*     */         } 
/* 347 */         ItemStack itemStack = getItem(first);
/* 348 */         int amount = itemStack.getAmount();
/*     */         
/* 350 */         if (amount <= toDelete) {
/* 351 */           toDelete -= amount;
/*     */           
/* 353 */           clear(first);
/*     */         } else {
/*     */           
/* 356 */           itemStack.setAmount(amount - toDelete);
/* 357 */           setItem(first, itemStack);
/* 358 */           toDelete = 0;
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 363 */       while (toDelete > 0);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 368 */     return leftover;
/*     */   }
/*     */   
/*     */   private int getMaxItemStack() {
/* 372 */     return getInventory().getMaxStackSize();
/*     */   }
/*     */   
/*     */   public void remove(int materialId) {
/* 376 */     ItemStack[] items = getContents();
/* 377 */     for (int i = 0; i < items.length; i++) {
/* 378 */       if (items[i] != null && items[i].getTypeId() == materialId) {
/* 379 */         clear(i);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void remove(Material material) {
/* 385 */     Validate.notNull(material, "Material cannot be null");
/* 386 */     remove(material.getId());
/*     */   }
/*     */   
/*     */   public void remove(ItemStack item) {
/* 390 */     ItemStack[] items = getContents();
/* 391 */     for (int i = 0; i < items.length; i++) {
/* 392 */       if (items[i] != null && items[i].equals(item)) {
/* 393 */         clear(i);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void clear(int index) {
/* 399 */     setItem(index, null);
/*     */   }
/*     */   
/*     */   public void clear() {
/* 403 */     for (int i = 0; i < getSize(); i++) {
/* 404 */       clear(i);
/*     */     }
/*     */   }
/*     */   
/*     */   public ListIterator<ItemStack> iterator() {
/* 409 */     return new InventoryIterator(this);
/*     */   }
/*     */   
/*     */   public ListIterator<ItemStack> iterator(int index) {
/* 413 */     if (index < 0) {
/* 414 */       index += getSize() + 1;
/*     */     }
/* 416 */     return new InventoryIterator(this, index);
/*     */   }
/*     */   
/*     */   public List<HumanEntity> getViewers() {
/* 420 */     return this.inventory.getViewers();
/*     */   }
/*     */   
/*     */   public String getTitle() {
/* 424 */     return this.inventory.getInventoryName();
/*     */   }
/*     */ 
/*     */   
/*     */   public InventoryType getType() {
/* 429 */     if (this.inventory instanceof net.minecraft.server.v1_7_R4.InventoryCrafting)
/* 430 */       return (this.inventory.getSize() >= 9) ? InventoryType.WORKBENCH : InventoryType.CRAFTING; 
/* 431 */     if (this.inventory instanceof net.minecraft.server.v1_7_R4.PlayerInventory)
/* 432 */       return InventoryType.PLAYER; 
/* 433 */     if (this.inventory instanceof net.minecraft.server.v1_7_R4.TileEntityDropper)
/* 434 */       return InventoryType.DROPPER; 
/* 435 */     if (this.inventory instanceof net.minecraft.server.v1_7_R4.TileEntityDispenser)
/* 436 */       return InventoryType.DISPENSER; 
/* 437 */     if (this.inventory instanceof net.minecraft.server.v1_7_R4.TileEntityFurnace)
/* 438 */       return InventoryType.FURNACE; 
/* 439 */     if (this.inventory instanceof net.minecraft.server.v1_7_R4.ContainerEnchantTableInventory)
/* 440 */       return InventoryType.ENCHANTING; 
/* 441 */     if (this.inventory instanceof net.minecraft.server.v1_7_R4.TileEntityBrewingStand)
/* 442 */       return InventoryType.BREWING; 
/* 443 */     if (this.inventory instanceof CraftInventoryCustom.MinecraftInventory)
/* 444 */       return ((CraftInventoryCustom.MinecraftInventory)this.inventory).getType(); 
/* 445 */     if (this.inventory instanceof net.minecraft.server.v1_7_R4.InventoryEnderChest)
/* 446 */       return InventoryType.ENDER_CHEST; 
/* 447 */     if (this.inventory instanceof net.minecraft.server.v1_7_R4.InventoryMerchant)
/* 448 */       return InventoryType.MERCHANT; 
/* 449 */     if (this.inventory instanceof net.minecraft.server.v1_7_R4.TileEntityBeacon)
/* 450 */       return InventoryType.BEACON; 
/* 451 */     if (this.inventory instanceof net.minecraft.server.v1_7_R4.ContainerAnvilInventory)
/* 452 */       return InventoryType.ANVIL; 
/* 453 */     if (this.inventory instanceof net.minecraft.server.v1_7_R4.IHopper) {
/* 454 */       return InventoryType.HOPPER;
/*     */     }
/* 456 */     return InventoryType.CHEST;
/*     */   }
/*     */ 
/*     */   
/*     */   public InventoryHolder getHolder() {
/* 461 */     return this.inventory.getOwner();
/*     */   }
/*     */   
/*     */   public int getMaxStackSize() {
/* 465 */     return this.inventory.getMaxStackSize();
/*     */   }
/*     */   
/*     */   public void setMaxStackSize(int size) {
/* 469 */     this.inventory.setMaxStackSize(size);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 474 */     return this.inventory.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 479 */     return (obj instanceof CraftInventory && ((CraftInventory)obj).inventory.equals(this.inventory));
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\inventory\CraftInventory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
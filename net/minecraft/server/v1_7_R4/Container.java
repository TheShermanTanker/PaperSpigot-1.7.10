/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.entity.CraftHumanEntity;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventory;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.inventory.InventoryDragEvent;
/*     */ import org.bukkit.event.inventory.InventoryType;
/*     */ import org.bukkit.inventory.InventoryView;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ public abstract class Container
/*     */ {
/*  22 */   public List b = new ArrayList();
/*  23 */   public List c = new ArrayList();
/*     */   public int windowId;
/*  25 */   private int dragType = -1;
/*     */   public int g;
/*  27 */   private final Set h = new HashSet();
/*  28 */   protected List listeners = new ArrayList();
/*  29 */   private Set i = new HashSet();
/*     */   public boolean checkReachable = true;
/*     */   
/*     */   public abstract InventoryView getBukkitView();
/*     */   
/*     */   public void transferTo(Container other, CraftHumanEntity player) {
/*  35 */     InventoryView source = getBukkitView(), destination = other.getBukkitView();
/*  36 */     ((CraftInventory)source.getTopInventory()).getInventory().onClose(player);
/*  37 */     ((CraftInventory)source.getBottomInventory()).getInventory().onClose(player);
/*  38 */     ((CraftInventory)destination.getTopInventory()).getInventory().onOpen(player);
/*  39 */     ((CraftInventory)destination.getBottomInventory()).getInventory().onOpen(player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Slot a(Slot slot) {
/*  46 */     slot.rawSlotIndex = this.c.size();
/*  47 */     this.c.add(slot);
/*  48 */     this.b.add(null);
/*  49 */     return slot;
/*     */   }
/*     */   
/*     */   public void addSlotListener(ICrafting icrafting) {
/*  53 */     if (this.listeners.contains(icrafting)) {
/*  54 */       throw new IllegalArgumentException("Listener already listening");
/*     */     }
/*  56 */     this.listeners.add(icrafting);
/*  57 */     icrafting.a(this, a());
/*  58 */     b();
/*     */   }
/*     */ 
/*     */   
/*     */   public List a() {
/*  63 */     ArrayList<ItemStack> arraylist = new ArrayList();
/*     */     
/*  65 */     for (int i = 0; i < this.c.size(); i++) {
/*  66 */       arraylist.add(((Slot)this.c.get(i)).getItem());
/*     */     }
/*     */     
/*  69 */     return arraylist;
/*     */   }
/*     */   
/*     */   public void b() {
/*  73 */     for (int i = 0; i < this.c.size(); i++) {
/*  74 */       ItemStack itemstack = ((Slot)this.c.get(i)).getItem();
/*  75 */       ItemStack itemstack1 = this.b.get(i);
/*     */       
/*  77 */       if (!ItemStack.matches(itemstack1, itemstack)) {
/*  78 */         itemstack1 = (itemstack == null) ? null : itemstack.cloneItemStack();
/*  79 */         this.b.set(i, itemstack1);
/*     */         
/*  81 */         for (int j = 0; j < this.listeners.size(); j++) {
/*  82 */           ((ICrafting)this.listeners.get(j)).a(this, i, itemstack1);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean a(EntityHuman entityhuman, int i) {
/*  89 */     return false;
/*     */   }
/*     */   
/*     */   public Slot getSlot(IInventory iinventory, int i) {
/*  93 */     for (int j = 0; j < this.c.size(); j++) {
/*  94 */       Slot slot = this.c.get(j);
/*     */       
/*  96 */       if (slot.a(iinventory, i)) {
/*  97 */         return slot;
/*     */       }
/*     */     } 
/*     */     
/* 101 */     return null;
/*     */   }
/*     */   
/*     */   public Slot getSlot(int i) {
/* 105 */     return this.c.get(i);
/*     */   }
/*     */   
/*     */   public ItemStack b(EntityHuman entityhuman, int i) {
/* 109 */     Slot slot = this.c.get(i);
/*     */     
/* 111 */     return (slot != null) ? slot.getItem() : null;
/*     */   }
/*     */   
/*     */   public ItemStack clickItem(int i, int j, int k, EntityHuman entityhuman) {
/* 115 */     ItemStack itemstack = null;
/* 116 */     PlayerInventory playerinventory = entityhuman.inventory;
/*     */ 
/*     */ 
/*     */     
/* 120 */     if (k == 5) {
/* 121 */       int i1 = this.g;
/*     */       
/* 123 */       this.g = c(j);
/* 124 */       if ((i1 != 1 || this.g != 2) && i1 != this.g) {
/* 125 */         d();
/* 126 */       } else if (playerinventory.getCarried() == null) {
/* 127 */         d();
/* 128 */       } else if (this.g == 0) {
/* 129 */         this.dragType = b(j);
/* 130 */         if (d(this.dragType)) {
/* 131 */           this.g = 1;
/* 132 */           this.h.clear();
/*     */         } else {
/* 134 */           d();
/*     */         } 
/* 136 */       } else if (this.g == 1) {
/* 137 */         Slot slot = this.c.get(i);
/*     */         
/* 139 */         if (slot != null && a(slot, playerinventory.getCarried(), true) && slot.isAllowed(playerinventory.getCarried()) && (playerinventory.getCarried()).count > this.h.size() && b(slot)) {
/* 140 */           this.h.add(slot);
/*     */         }
/* 142 */       } else if (this.g == 2) {
/* 143 */         if (!this.h.isEmpty()) {
/* 144 */           ItemStack itemstack1 = playerinventory.getCarried().cloneItemStack();
/* 145 */           int l = (playerinventory.getCarried()).count;
/* 146 */           Iterator<Slot> iterator = this.h.iterator();
/*     */           
/* 148 */           Map<Integer, ItemStack> draggedSlots = new HashMap<Integer, ItemStack>();
/* 149 */           while (iterator.hasNext()) {
/* 150 */             Slot slot1 = iterator.next();
/*     */             
/* 152 */             if (slot1 != null && a(slot1, playerinventory.getCarried(), true) && slot1.isAllowed(playerinventory.getCarried()) && (playerinventory.getCarried()).count >= this.h.size() && b(slot1)) {
/* 153 */               ItemStack itemstack2 = itemstack1.cloneItemStack();
/* 154 */               int j1 = slot1.hasItem() ? (slot1.getItem()).count : 0;
/*     */               
/* 156 */               a(this.h, this.dragType, itemstack2, j1);
/* 157 */               if (itemstack2.count > itemstack2.getMaxStackSize()) {
/* 158 */                 itemstack2.count = itemstack2.getMaxStackSize();
/*     */               }
/*     */               
/* 161 */               if (itemstack2.count > slot1.getMaxStackSize()) {
/* 162 */                 itemstack2.count = slot1.getMaxStackSize();
/*     */               }
/*     */               
/* 165 */               l -= itemstack2.count - j1;
/* 166 */               draggedSlots.put(Integer.valueOf(slot1.rawSlotIndex), itemstack2);
/*     */             } 
/*     */           } 
/*     */ 
/*     */           
/* 171 */           InventoryView view = getBukkitView();
/* 172 */           CraftItemStack craftItemStack = CraftItemStack.asCraftMirror(itemstack1);
/* 173 */           craftItemStack.setAmount(l);
/* 174 */           Map<Integer, ItemStack> eventmap = new HashMap<Integer, ItemStack>();
/* 175 */           for (Map.Entry<Integer, ItemStack> ditem : draggedSlots.entrySet()) {
/* 176 */             eventmap.put(ditem.getKey(), CraftItemStack.asBukkitCopy(ditem.getValue()));
/*     */           }
/*     */ 
/*     */           
/* 180 */           ItemStack oldCursor = playerinventory.getCarried();
/* 181 */           playerinventory.setCarried(CraftItemStack.asNMSCopy((ItemStack)craftItemStack));
/*     */           
/* 183 */           InventoryDragEvent event = new InventoryDragEvent(view, (craftItemStack.getType() != Material.AIR) ? (ItemStack)craftItemStack : null, CraftItemStack.asBukkitCopy(oldCursor), (this.dragType == 1), eventmap);
/* 184 */           entityhuman.world.getServer().getPluginManager().callEvent((Event)event);
/*     */ 
/*     */           
/* 187 */           boolean needsUpdate = (event.getResult() != Event.Result.DEFAULT);
/*     */           
/* 189 */           if (event.getResult() != Event.Result.DENY) {
/* 190 */             for (Map.Entry<Integer, ItemStack> dslot : draggedSlots.entrySet()) {
/* 191 */               view.setItem(((Integer)dslot.getKey()).intValue(), CraftItemStack.asBukkitCopy(dslot.getValue()));
/*     */             }
/*     */ 
/*     */             
/* 195 */             if (playerinventory.getCarried() != null) {
/* 196 */               playerinventory.setCarried(CraftItemStack.asNMSCopy(event.getCursor()));
/* 197 */               needsUpdate = true;
/*     */             } 
/*     */           } else {
/*     */             
/* 201 */             playerinventory.setCarried(oldCursor);
/*     */           } 
/*     */           
/* 204 */           if (needsUpdate && entityhuman instanceof EntityPlayer) {
/* 205 */             ((EntityPlayer)entityhuman).updateInventory(this);
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/* 210 */         d();
/*     */       } else {
/* 212 */         d();
/*     */       } 
/* 214 */     } else if (this.g != 0) {
/* 215 */       d();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 221 */     else if ((k == 0 || k == 1) && (j == 0 || j == 1)) {
/* 222 */       if (i == -999) {
/* 223 */         if (playerinventory.getCarried() != null && i == -999) {
/* 224 */           if (j == 0) {
/* 225 */             entityhuman.drop(playerinventory.getCarried(), true);
/* 226 */             playerinventory.setCarried((ItemStack)null);
/*     */           } 
/*     */           
/* 229 */           if (j == 1) {
/*     */             
/* 231 */             ItemStack itemstack4 = playerinventory.getCarried();
/* 232 */             if (itemstack4.count > 0) {
/* 233 */               entityhuman.drop(itemstack4.a(1), true);
/*     */             }
/*     */             
/* 236 */             if (itemstack4.count == 0)
/*     */             {
/* 238 */               playerinventory.setCarried((ItemStack)null);
/*     */             }
/*     */           } 
/*     */         } 
/* 242 */       } else if (k == 1) {
/* 243 */         if (i < 0) {
/* 244 */           return null;
/*     */         }
/*     */         
/* 247 */         Slot slot2 = this.c.get(i);
/* 248 */         if (slot2 != null && slot2.isAllowed(entityhuman)) {
/* 249 */           ItemStack itemstack1 = b(entityhuman, i);
/* 250 */           if (itemstack1 != null) {
/* 251 */             Item item = itemstack1.getItem();
/*     */             
/* 253 */             itemstack = itemstack1.cloneItemStack();
/* 254 */             if (slot2.getItem() != null && slot2.getItem().getItem() == item) {
/* 255 */               a(i, j, true, entityhuman);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } else {
/* 260 */         if (i < 0) {
/* 261 */           return null;
/*     */         }
/*     */         
/* 264 */         Slot slot2 = this.c.get(i);
/* 265 */         if (slot2 != null) {
/* 266 */           ItemStack itemstack1 = slot2.getItem();
/* 267 */           ItemStack itemstack4 = playerinventory.getCarried();
/*     */           
/* 269 */           if (itemstack1 != null) {
/* 270 */             itemstack = itemstack1.cloneItemStack();
/*     */           }
/*     */           
/* 273 */           if (itemstack1 == null) {
/* 274 */             if (itemstack4 != null && slot2.isAllowed(itemstack4)) {
/* 275 */               int k1 = (j == 0) ? itemstack4.count : 1;
/* 276 */               if (k1 > slot2.getMaxStackSize()) {
/* 277 */                 k1 = slot2.getMaxStackSize();
/*     */               }
/*     */               
/* 280 */               if (itemstack4.count >= k1) {
/* 281 */                 slot2.set(itemstack4.a(k1));
/*     */               }
/*     */               
/* 284 */               if (itemstack4.count == 0) {
/* 285 */                 playerinventory.setCarried((ItemStack)null);
/*     */               }
/* 287 */               else if (entityhuman instanceof EntityPlayer) {
/* 288 */                 ((EntityPlayer)entityhuman).playerConnection.sendPacket(new PacketPlayOutSetSlot(-1, -1, entityhuman.inventory.getCarried()));
/*     */               }
/*     */             
/*     */             } 
/* 292 */           } else if (slot2.isAllowed(entityhuman)) {
/* 293 */             if (itemstack4 == null) {
/* 294 */               int k1 = (j == 0) ? itemstack1.count : ((itemstack1.count + 1) / 2);
/* 295 */               ItemStack itemstack3 = slot2.a(k1);
/* 296 */               playerinventory.setCarried(itemstack3);
/* 297 */               if (itemstack1.count == 0) {
/* 298 */                 slot2.set((ItemStack)null);
/*     */               }
/*     */               
/* 301 */               slot2.a(entityhuman, playerinventory.getCarried());
/* 302 */             } else if (slot2.isAllowed(itemstack4)) {
/* 303 */               if (itemstack1.getItem() == itemstack4.getItem() && itemstack1.getData() == itemstack4.getData() && ItemStack.equals(itemstack1, itemstack4)) {
/* 304 */                 int k1 = (j == 0) ? itemstack4.count : 1;
/* 305 */                 if (k1 > slot2.getMaxStackSize() - itemstack1.count) {
/* 306 */                   k1 = slot2.getMaxStackSize() - itemstack1.count;
/*     */                 }
/*     */                 
/* 309 */                 if (k1 > itemstack4.getMaxStackSize() - itemstack1.count) {
/* 310 */                   k1 = itemstack4.getMaxStackSize() - itemstack1.count;
/*     */                 }
/*     */                 
/* 313 */                 itemstack4.a(k1);
/* 314 */                 if (itemstack4.count == 0) {
/* 315 */                   playerinventory.setCarried((ItemStack)null);
/*     */                 }
/* 317 */                 else if (entityhuman instanceof EntityPlayer) {
/* 318 */                   ((EntityPlayer)entityhuman).playerConnection.sendPacket(new PacketPlayOutSetSlot(-1, -1, entityhuman.inventory.getCarried()));
/*     */                 } 
/*     */ 
/*     */                 
/* 322 */                 itemstack1.count += k1;
/* 323 */               } else if (itemstack4.count <= slot2.getMaxStackSize()) {
/* 324 */                 slot2.set(itemstack4);
/* 325 */                 playerinventory.setCarried(itemstack1);
/*     */               } 
/* 327 */             } else if (itemstack1.getItem() == itemstack4.getItem() && itemstack4.getMaxStackSize() > 1 && (!itemstack1.usesData() || itemstack1.getData() == itemstack4.getData()) && ItemStack.equals(itemstack1, itemstack4)) {
/* 328 */               int k1 = itemstack1.count;
/*     */               
/* 330 */               int maxStack = Math.min(itemstack4.getMaxStackSize(), slot2.getMaxStackSize());
/* 331 */               if (k1 > 0 && k1 + itemstack4.count <= maxStack) {
/*     */                 
/* 333 */                 itemstack4.count += k1;
/* 334 */                 itemstack1 = slot2.a(k1);
/* 335 */                 if (itemstack1.count == 0) {
/* 336 */                   slot2.set((ItemStack)null);
/*     */                 }
/*     */                 
/* 339 */                 slot2.a(entityhuman, playerinventory.getCarried());
/*     */               }
/* 341 */               else if (entityhuman instanceof EntityPlayer) {
/* 342 */                 ((EntityPlayer)entityhuman).playerConnection.sendPacket(new PacketPlayOutSetSlot(-1, -1, entityhuman.inventory.getCarried()));
/*     */               } 
/*     */             } 
/*     */           } 
/*     */ 
/*     */           
/* 348 */           slot2.f();
/*     */           
/* 350 */           if (entityhuman instanceof EntityPlayer && slot2.getMaxStackSize() != 64) {
/* 351 */             ((EntityPlayer)entityhuman).playerConnection.sendPacket(new PacketPlayOutSetSlot(this.windowId, slot2.rawSlotIndex, slot2.getItem()));
/*     */             
/* 353 */             if (getBukkitView().getType() == InventoryType.WORKBENCH || getBukkitView().getType() == InventoryType.CRAFTING) {
/* 354 */               ((EntityPlayer)entityhuman).playerConnection.sendPacket(new PacketPlayOutSetSlot(this.windowId, 0, getSlot(0).getItem()));
/*     */             }
/*     */           }
/*     */         
/*     */         } 
/*     */       } 
/* 360 */     } else if (k == 2 && j >= 0 && j < 9) {
/* 361 */       Slot slot2 = this.c.get(i);
/* 362 */       if (slot2.isAllowed(entityhuman)) {
/* 363 */         int m; ItemStack itemstack1 = playerinventory.getItem(j);
/* 364 */         boolean flag = (itemstack1 == null || (slot2.inventory == playerinventory && slot2.isAllowed(itemstack1)));
/*     */         
/* 366 */         int k1 = -1;
/* 367 */         if (!flag) {
/* 368 */           k1 = playerinventory.getFirstEmptySlotIndex();
/* 369 */           m = flag | ((k1 > -1) ? 1 : 0);
/*     */         } 
/*     */         
/* 372 */         if (slot2.hasItem() && m != 0) {
/* 373 */           ItemStack itemstack3 = slot2.getItem();
/* 374 */           playerinventory.setItem(j, itemstack3.cloneItemStack());
/* 375 */           if ((slot2.inventory != playerinventory || !slot2.isAllowed(itemstack1)) && itemstack1 != null) {
/* 376 */             if (k1 > -1) {
/* 377 */               playerinventory.pickup(itemstack1);
/* 378 */               slot2.a(itemstack3.count);
/* 379 */               slot2.set((ItemStack)null);
/* 380 */               slot2.a(entityhuman, itemstack3);
/*     */             } 
/*     */           } else {
/* 383 */             slot2.a(itemstack3.count);
/* 384 */             slot2.set(itemstack1);
/* 385 */             slot2.a(entityhuman, itemstack3);
/*     */           } 
/* 387 */         } else if (!slot2.hasItem() && itemstack1 != null && slot2.isAllowed(itemstack1)) {
/* 388 */           playerinventory.setItem(j, (ItemStack)null);
/* 389 */           slot2.set(itemstack1);
/*     */         } 
/*     */       } 
/* 392 */     } else if (k == 3 && entityhuman.abilities.canInstantlyBuild && playerinventory.getCarried() == null && i >= 0) {
/* 393 */       Slot slot2 = this.c.get(i);
/* 394 */       if (slot2 != null && slot2.hasItem()) {
/* 395 */         ItemStack itemstack1 = slot2.getItem().cloneItemStack();
/* 396 */         itemstack1.count = itemstack1.getMaxStackSize();
/* 397 */         playerinventory.setCarried(itemstack1);
/*     */       } 
/* 399 */     } else if (k == 4 && playerinventory.getCarried() == null && i >= 0) {
/* 400 */       Slot slot2 = this.c.get(i);
/* 401 */       if (slot2 != null && slot2.hasItem() && slot2.isAllowed(entityhuman)) {
/* 402 */         ItemStack itemstack1 = slot2.a((j == 0) ? 1 : (slot2.getItem()).count);
/* 403 */         slot2.a(entityhuman, itemstack1);
/* 404 */         entityhuman.drop(itemstack1, true);
/*     */       } 
/* 406 */     } else if (k == 6 && i >= 0) {
/* 407 */       Slot slot2 = this.c.get(i);
/* 408 */       ItemStack itemstack1 = playerinventory.getCarried();
/* 409 */       if (itemstack1 != null && (slot2 == null || !slot2.hasItem() || !slot2.isAllowed(entityhuman))) {
/* 410 */         int l = (j == 0) ? 0 : (this.c.size() - 1);
/* 411 */         int k1 = (j == 0) ? 1 : -1;
/*     */         
/* 413 */         for (int l1 = 0; l1 < 2; l1++) {
/* 414 */           int i2; for (i2 = l; i2 >= 0 && i2 < this.c.size() && itemstack1.count < itemstack1.getMaxStackSize(); i2 += k1) {
/* 415 */             Slot slot3 = this.c.get(i2);
/*     */             
/* 417 */             if (slot3.hasItem() && a(slot3, itemstack1, true) && slot3.isAllowed(entityhuman) && a(itemstack1, slot3) && (l1 != 0 || (slot3.getItem()).count != slot3.getItem().getMaxStackSize())) {
/* 418 */               int j2 = Math.min(itemstack1.getMaxStackSize() - itemstack1.count, (slot3.getItem()).count);
/* 419 */               ItemStack itemstack5 = slot3.a(j2);
/*     */               
/* 421 */               itemstack1.count += j2;
/* 422 */               if (itemstack5.count <= 0) {
/* 423 */                 slot3.set((ItemStack)null);
/*     */               }
/*     */               
/* 426 */               slot3.a(entityhuman, itemstack5);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 432 */       b();
/*     */     } 
/*     */ 
/*     */     
/* 436 */     return itemstack;
/*     */   }
/*     */   
/*     */   public boolean a(ItemStack itemstack, Slot slot) {
/* 440 */     return true;
/*     */   }
/*     */   
/*     */   protected void a(int i, int j, boolean flag, EntityHuman entityhuman) {
/* 444 */     clickItem(i, j, 1, entityhuman);
/*     */   }
/*     */   
/*     */   public void b(EntityHuman entityhuman) {
/* 448 */     PlayerInventory playerinventory = entityhuman.inventory;
/*     */     
/* 450 */     if (playerinventory.getCarried() != null) {
/* 451 */       entityhuman.drop(playerinventory.getCarried(), false);
/* 452 */       playerinventory.setCarried((ItemStack)null);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void a(IInventory iinventory) {
/* 457 */     b();
/*     */   }
/*     */   
/*     */   public void setItem(int i, ItemStack itemstack) {
/* 461 */     getSlot(i).set(itemstack);
/*     */   }
/*     */   
/*     */   public boolean c(EntityHuman entityhuman) {
/* 465 */     return !this.i.contains(entityhuman);
/*     */   }
/*     */   
/*     */   public void a(EntityHuman entityhuman, boolean flag) {
/* 469 */     if (flag) {
/* 470 */       this.i.remove(entityhuman);
/*     */     } else {
/* 472 */       this.i.add(entityhuman);
/*     */     } 
/*     */   }
/*     */   
/*     */   public abstract boolean a(EntityHuman paramEntityHuman);
/*     */   
/*     */   protected boolean a(ItemStack itemstack, int i, int j, boolean flag) {
/* 479 */     boolean flag1 = false;
/* 480 */     int k = i;
/*     */     
/* 482 */     if (flag) {
/* 483 */       k = j - 1;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 489 */     if (itemstack.isStackable()) {
/* 490 */       while (itemstack.count > 0 && ((!flag && k < j) || (flag && k >= i))) {
/* 491 */         Slot slot = this.c.get(k);
/* 492 */         ItemStack itemstack1 = slot.getItem();
/* 493 */         if (itemstack1 != null && itemstack1.getItem() == itemstack.getItem() && (!itemstack.usesData() || itemstack.getData() == itemstack1.getData()) && ItemStack.equals(itemstack, itemstack1)) {
/* 494 */           int l = itemstack1.count + itemstack.count;
/*     */ 
/*     */           
/* 497 */           int maxStack = Math.min(itemstack.getMaxStackSize(), slot.getMaxStackSize());
/* 498 */           if (l <= maxStack) {
/* 499 */             itemstack.count = 0;
/* 500 */             itemstack1.count = l;
/* 501 */             slot.f();
/* 502 */             flag1 = true;
/* 503 */           } else if (itemstack1.count < maxStack) {
/* 504 */             itemstack.count -= maxStack - itemstack1.count;
/* 505 */             itemstack1.count = maxStack;
/* 506 */             slot.f();
/* 507 */             flag1 = true;
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 512 */         if (flag) {
/* 513 */           k--; continue;
/*     */         } 
/* 515 */         k++;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 520 */     if (itemstack.count > 0) {
/* 521 */       if (flag) {
/* 522 */         k = j - 1;
/*     */       } else {
/* 524 */         k = i;
/*     */       } 
/*     */       
/* 527 */       while ((!flag && k < j) || (flag && k >= i)) {
/* 528 */         Slot slot = this.c.get(k);
/* 529 */         ItemStack itemstack1 = slot.getItem();
/* 530 */         if (itemstack1 == null) {
/* 531 */           slot.set(itemstack.cloneItemStack());
/* 532 */           slot.f();
/* 533 */           itemstack.count = 0;
/* 534 */           flag1 = true;
/*     */           
/*     */           break;
/*     */         } 
/* 538 */         if (flag) {
/* 539 */           k--; continue;
/*     */         } 
/* 541 */         k++;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 546 */     return flag1;
/*     */   }
/*     */   
/*     */   public static int b(int i) {
/* 550 */     return i >> 2 & 0x3;
/*     */   }
/*     */   
/*     */   public static int c(int i) {
/* 554 */     return i & 0x3;
/*     */   }
/*     */   
/*     */   public static boolean d(int i) {
/* 558 */     return (i == 0 || i == 1);
/*     */   }
/*     */   
/*     */   protected void d() {
/* 562 */     this.g = 0;
/* 563 */     this.h.clear();
/*     */   }
/*     */   public static boolean a(Slot slot, ItemStack itemstack, boolean flag) {
/*     */     int i;
/* 567 */     boolean flag1 = (slot == null || !slot.hasItem());
/*     */     
/* 569 */     if (slot != null && slot.hasItem() && itemstack != null && itemstack.doMaterialsMatch(slot.getItem()) && ItemStack.equals(slot.getItem(), itemstack)) {
/* 570 */       int j = flag ? 0 : itemstack.count;
/*     */       
/* 572 */       i = flag1 | (((slot.getItem()).count + j <= itemstack.getMaxStackSize()) ? 1 : 0);
/*     */     } 
/*     */     
/* 575 */     return i;
/*     */   }
/*     */   
/*     */   public static void a(Set set, int i, ItemStack itemstack, int j) {
/* 579 */     switch (i) {
/*     */       case 0:
/* 581 */         itemstack.count = MathHelper.d(itemstack.count / set.size());
/*     */         break;
/*     */       
/*     */       case 1:
/* 585 */         itemstack.count = 1;
/*     */         break;
/*     */     } 
/* 588 */     itemstack.count += j;
/*     */   }
/*     */   
/*     */   public boolean b(Slot slot) {
/* 592 */     return true;
/*     */   }
/*     */   
/*     */   public static int b(IInventory iinventory) {
/* 596 */     if (iinventory == null) {
/* 597 */       return 0;
/*     */     }
/* 599 */     int i = 0;
/* 600 */     float f = 0.0F;
/*     */     
/* 602 */     for (int j = 0; j < iinventory.getSize(); j++) {
/* 603 */       ItemStack itemstack = iinventory.getItem(j);
/*     */       
/* 605 */       if (itemstack != null) {
/* 606 */         f += itemstack.count / Math.min(iinventory.getMaxStackSize(), itemstack.getMaxStackSize());
/* 607 */         i++;
/*     */       } 
/*     */     } 
/*     */     
/* 611 */     f /= iinventory.getSize();
/* 612 */     return MathHelper.d(f * 14.0F) + ((i > 0) ? 1 : 0);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\Container.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
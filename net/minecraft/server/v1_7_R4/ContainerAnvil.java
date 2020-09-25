/*     */ package net.minecraft.server.v1_7_R4;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import net.minecraft.util.org.apache.commons.lang3.StringUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventoryAnvil;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventoryView;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.InventoryView;
/*     */ 
/*     */ public class ContainerAnvil extends Container {
/*  14 */   private static final Logger f = LogManager.getLogger();
/*  15 */   private IInventory g = new InventoryCraftResult();
/*  16 */   private IInventory h = new ContainerAnvilInventory(this, "Repair", true, 2);
/*     */   
/*     */   private World i;
/*     */   private int j;
/*     */   private int k;
/*     */   private int l;
/*     */   public int a;
/*     */   private int m;
/*     */   private String n;
/*     */   private final EntityHuman o;
/*  26 */   private CraftInventoryView bukkitEntity = null;
/*     */   
/*     */   private PlayerInventory player;
/*     */   
/*     */   public ContainerAnvil(PlayerInventory playerinventory, World world, int i, int j, int k, EntityHuman entityhuman) {
/*  31 */     this.player = playerinventory;
/*  32 */     this.i = world;
/*  33 */     this.j = i;
/*  34 */     this.k = j;
/*  35 */     this.l = k;
/*  36 */     this.o = entityhuman;
/*  37 */     a(new Slot(this.h, 0, 27, 47));
/*  38 */     a(new Slot(this.h, 1, 76, 47));
/*  39 */     a(new SlotAnvilResult(this, this.g, 2, 134, 47, world, i, j, k));
/*     */     
/*     */     int l;
/*     */     
/*  43 */     for (l = 0; l < 3; l++) {
/*  44 */       for (int i1 = 0; i1 < 9; i1++) {
/*  45 */         a(new Slot(playerinventory, i1 + l * 9 + 9, 8 + i1 * 18, 84 + l * 18));
/*     */       }
/*     */     } 
/*     */     
/*  49 */     for (l = 0; l < 9; l++) {
/*  50 */       a(new Slot(playerinventory, l, 8 + l * 18, 142));
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(IInventory iinventory) {
/*  55 */     super.a(iinventory);
/*  56 */     if (iinventory == this.h) {
/*  57 */       e();
/*     */     }
/*     */   }
/*     */   
/*     */   public void e() {
/*  62 */     ItemStack itemstack = this.h.getItem(0);
/*     */     
/*  64 */     this.a = 0;
/*  65 */     int i = 0;
/*  66 */     byte b0 = 0;
/*  67 */     int j = 0;
/*     */     
/*  69 */     if (itemstack == null) {
/*  70 */       this.g.setItem(0, (ItemStack)null);
/*  71 */       this.a = 0;
/*     */     } else {
/*  73 */       ItemStack itemstack1 = itemstack.cloneItemStack();
/*  74 */       ItemStack itemstack2 = this.h.getItem(1);
/*  75 */       Map<Integer, Integer> map = EnchantmentManager.a(itemstack1);
/*  76 */       boolean flag = false;
/*  77 */       int k = b0 + itemstack.getRepairCost() + ((itemstack2 == null) ? 0 : itemstack2.getRepairCost());
/*     */       
/*  79 */       this.m = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  88 */       if (itemstack2 != null) {
/*  89 */         flag = (itemstack2.getItem() == Items.ENCHANTED_BOOK && Items.ENCHANTED_BOOK.g(itemstack2).size() > 0);
/*  90 */         if (itemstack1.g() && itemstack1.getItem().a(itemstack, itemstack2)) {
/*  91 */           int m = Math.min(itemstack1.j(), itemstack1.l() / 4);
/*  92 */           if (m <= 0) {
/*  93 */             this.g.setItem(0, (ItemStack)null);
/*  94 */             this.a = 0;
/*     */             return;
/*     */           } 
/*     */           int i1;
/*  98 */           for (i1 = 0; m > 0 && i1 < itemstack2.count; i1++) {
/*  99 */             int j1 = itemstack1.j() - m;
/* 100 */             itemstack1.setData(j1);
/* 101 */             i += Math.max(1, m / 100) + map.size();
/* 102 */             m = Math.min(itemstack1.j(), itemstack1.l() / 4);
/*     */           } 
/*     */           
/* 105 */           this.m = i1;
/*     */         } else {
/* 107 */           if (!flag && (itemstack1.getItem() != itemstack2.getItem() || !itemstack1.g())) {
/* 108 */             this.g.setItem(0, (ItemStack)null);
/* 109 */             this.a = 0;
/*     */             
/*     */             return;
/*     */           } 
/* 113 */           if (itemstack1.g() && !flag) {
/* 114 */             int m = itemstack.l() - itemstack.j();
/* 115 */             int i1 = itemstack2.l() - itemstack2.j();
/* 116 */             int j1 = i1 + itemstack1.l() * 12 / 100;
/* 117 */             int i2 = m + j1;
/*     */             
/* 119 */             int k1 = itemstack1.l() - i2;
/* 120 */             if (k1 < 0) {
/* 121 */               k1 = 0;
/*     */             }
/*     */             
/* 124 */             if (k1 < itemstack1.getData()) {
/* 125 */               itemstack1.setData(k1);
/* 126 */               i += Math.max(1, j1 / 100);
/*     */             } 
/*     */           } 
/*     */           
/* 130 */           Map map1 = EnchantmentManager.a(itemstack2);
/*     */           
/* 132 */           Iterator<Integer> iterator1 = map1.keySet().iterator();
/*     */           
/* 134 */           while (iterator1.hasNext()) {
/* 135 */             int j2, j1 = ((Integer)iterator1.next()).intValue();
/* 136 */             Enchantment enchantment = Enchantment.byId[j1];
/* 137 */             int k1 = map.containsKey(Integer.valueOf(j1)) ? ((Integer)map.get(Integer.valueOf(j1))).intValue() : 0;
/* 138 */             int l1 = ((Integer)map1.get(Integer.valueOf(j1))).intValue();
/*     */ 
/*     */             
/* 141 */             if (k1 == l1) {
/*     */               
/* 143 */               j2 = ++l1;
/*     */             } else {
/* 145 */               j2 = Math.max(l1, k1);
/*     */             } 
/*     */             
/* 148 */             l1 = j2;
/* 149 */             int k2 = l1 - k1;
/* 150 */             boolean flag1 = enchantment.canEnchant(itemstack);
/*     */             
/* 152 */             if (this.o.abilities.canInstantlyBuild || itemstack.getItem() == Items.ENCHANTED_BOOK) {
/* 153 */               flag1 = true;
/*     */             }
/*     */             
/* 156 */             Iterator<Integer> iterator2 = map.keySet().iterator();
/*     */             
/* 158 */             while (iterator2.hasNext()) {
/* 159 */               int l2 = ((Integer)iterator2.next()).intValue();
/*     */               
/* 161 */               if (l2 != j1 && !enchantment.a(Enchantment.byId[l2])) {
/* 162 */                 flag1 = false;
/* 163 */                 i += k2;
/*     */               } 
/*     */             } 
/*     */             
/* 167 */             if (flag1) {
/* 168 */               if (l1 > enchantment.getMaxLevel()) {
/* 169 */                 l1 = enchantment.getMaxLevel();
/*     */               }
/*     */               
/* 172 */               map.put(Integer.valueOf(j1), Integer.valueOf(l1));
/* 173 */               int i3 = 0;
/*     */               
/* 175 */               switch (enchantment.getRandomWeight()) {
/*     */                 case 1:
/* 177 */                   i3 = 8;
/*     */                   break;
/*     */                 
/*     */                 case 2:
/* 181 */                   i3 = 4;
/*     */                   break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/*     */                 case 5:
/* 193 */                   i3 = 2;
/*     */                   break;
/*     */                 
/*     */                 case 10:
/* 197 */                   i3 = 1;
/*     */                   break;
/*     */               } 
/* 200 */               if (flag) {
/* 201 */                 i3 = Math.max(1, i3 / 2);
/*     */               }
/*     */               
/* 204 */               i += i3 * k2;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 210 */       if (StringUtils.isBlank(this.n)) {
/* 211 */         if (itemstack.hasName()) {
/* 212 */           j = itemstack.g() ? 7 : (itemstack.count * 5);
/* 213 */           i += j;
/* 214 */           itemstack1.t();
/*     */         } 
/* 216 */       } else if (!this.n.equals(itemstack.getName())) {
/* 217 */         j = itemstack.g() ? 7 : (itemstack.count * 5);
/* 218 */         i += j;
/* 219 */         if (itemstack.hasName()) {
/* 220 */           k += j / 2;
/*     */         }
/*     */         
/* 223 */         itemstack1.c(this.n);
/*     */       } 
/*     */       
/* 226 */       int l = 0;
/*     */       
/* 228 */       for (Iterator<Integer> iterator = map.keySet().iterator(); iterator.hasNext(); k += l + k1 * l1) {
/* 229 */         int j1 = ((Integer)iterator.next()).intValue();
/* 230 */         Enchantment enchantment = Enchantment.byId[j1];
/* 231 */         int k1 = ((Integer)map.get(Integer.valueOf(j1))).intValue();
/* 232 */         int l1 = 0;
/* 233 */         l++;
/* 234 */         switch (enchantment.getRandomWeight()) {
/*     */           case 1:
/* 236 */             l1 = 8;
/*     */             break;
/*     */           
/*     */           case 2:
/* 240 */             l1 = 4;
/*     */             break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           case 5:
/* 252 */             l1 = 2;
/*     */             break;
/*     */           
/*     */           case 10:
/* 256 */             l1 = 1;
/*     */             break;
/*     */         } 
/* 259 */         if (flag) {
/* 260 */           l1 = Math.max(1, l1 / 2);
/*     */         }
/*     */       } 
/*     */       
/* 264 */       if (flag) {
/* 265 */         k = Math.max(1, k / 2);
/*     */       }
/*     */       
/* 268 */       this.a = k + i;
/* 269 */       if (i <= 0) {
/* 270 */         itemstack1 = null;
/*     */       }
/*     */       
/* 273 */       if (j == i && j > 0 && this.a >= 40) {
/* 274 */         this.a = 39;
/*     */       }
/*     */       
/* 277 */       if (this.a >= 40 && !this.o.abilities.canInstantlyBuild) {
/* 278 */         itemstack1 = null;
/*     */       }
/*     */       
/* 281 */       if (itemstack1 != null) {
/* 282 */         int i1 = itemstack1.getRepairCost();
/* 283 */         if (itemstack2 != null && i1 < itemstack2.getRepairCost()) {
/* 284 */           i1 = itemstack2.getRepairCost();
/*     */         }
/*     */         
/* 287 */         if (itemstack1.hasName()) {
/* 288 */           i1 -= 9;
/*     */         }
/*     */         
/* 291 */         if (i1 < 0) {
/* 292 */           i1 = 0;
/*     */         }
/*     */         
/* 295 */         i1 += 2;
/* 296 */         itemstack1.setRepairCost(i1);
/* 297 */         EnchantmentManager.a(map, itemstack1);
/*     */       } 
/*     */       
/* 300 */       this.g.setItem(0, itemstack1);
/* 301 */       b();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addSlotListener(ICrafting icrafting) {
/* 306 */     super.addSlotListener(icrafting);
/* 307 */     icrafting.setContainerData(this, 0, this.a);
/*     */   }
/*     */   
/*     */   public void b(EntityHuman entityhuman) {
/* 311 */     super.b(entityhuman);
/* 312 */     if (!this.i.isStatic) {
/* 313 */       for (int i = 0; i < this.h.getSize(); i++) {
/* 314 */         ItemStack itemstack = this.h.splitWithoutUpdate(i);
/*     */         
/* 316 */         if (itemstack != null) {
/* 317 */           entityhuman.drop(itemstack, false);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean a(EntityHuman entityhuman) {
/* 324 */     if (!this.checkReachable) return true; 
/* 325 */     return (this.i.getType(this.j, this.k, this.l) != Blocks.ANVIL) ? false : ((entityhuman.e(this.j + 0.5D, this.k + 0.5D, this.l + 0.5D) <= 64.0D));
/*     */   }
/*     */   
/*     */   public ItemStack b(EntityHuman entityhuman, int i) {
/* 329 */     ItemStack itemstack = null;
/* 330 */     Slot slot = this.c.get(i);
/*     */     
/* 332 */     if (slot != null && slot.hasItem()) {
/* 333 */       ItemStack itemstack1 = slot.getItem();
/*     */       
/* 335 */       itemstack = itemstack1.cloneItemStack();
/* 336 */       if (i == 2) {
/* 337 */         if (!a(itemstack1, 3, 39, true)) {
/* 338 */           return null;
/*     */         }
/*     */         
/* 341 */         slot.a(itemstack1, itemstack);
/* 342 */       } else if (i != 0 && i != 1) {
/* 343 */         if (i >= 3 && i < 39 && !a(itemstack1, 0, 2, false)) {
/* 344 */           return null;
/*     */         }
/* 346 */       } else if (!a(itemstack1, 3, 39, false)) {
/* 347 */         return null;
/*     */       } 
/*     */       
/* 350 */       if (itemstack1.count == 0) {
/* 351 */         slot.set((ItemStack)null);
/*     */       } else {
/* 353 */         slot.f();
/*     */       } 
/*     */       
/* 356 */       if (itemstack1.count == itemstack.count) {
/* 357 */         return null;
/*     */       }
/*     */       
/* 360 */       slot.a(entityhuman, itemstack1);
/*     */     } 
/*     */     
/* 363 */     return itemstack;
/*     */   }
/*     */   
/*     */   public void a(String s) {
/* 367 */     this.n = s;
/* 368 */     if (getSlot(2).hasItem()) {
/* 369 */       ItemStack itemstack = getSlot(2).getItem();
/*     */       
/* 371 */       if (StringUtils.isBlank(s)) {
/* 372 */         itemstack.t();
/*     */       } else {
/* 374 */         itemstack.c(this.n);
/*     */       } 
/*     */     } 
/*     */     
/* 378 */     e();
/*     */   }
/*     */   
/*     */   static IInventory a(ContainerAnvil containeranvil) {
/* 382 */     return containeranvil.h;
/*     */   }
/*     */   
/*     */   static int b(ContainerAnvil containeranvil) {
/* 386 */     return containeranvil.m;
/*     */   }
/*     */ 
/*     */   
/*     */   public CraftInventoryView getBukkitView() {
/* 391 */     if (this.bukkitEntity != null) {
/* 392 */       return this.bukkitEntity;
/*     */     }
/*     */     
/* 395 */     CraftInventoryAnvil craftInventoryAnvil = new CraftInventoryAnvil(this.h, this.g);
/* 396 */     this.bukkitEntity = new CraftInventoryView((HumanEntity)this.player.player.getBukkitEntity(), (Inventory)craftInventoryAnvil, this);
/* 397 */     return this.bukkitEntity;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ContainerAnvil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
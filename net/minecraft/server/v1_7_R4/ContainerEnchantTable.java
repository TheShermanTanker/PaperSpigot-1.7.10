/*     */ package net.minecraft.server.v1_7_R4;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventoryEnchanting;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventoryView;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.enchantment.EnchantItemEvent;
/*     */ import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.InventoryView;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ public class ContainerEnchantTable extends Container {
/*  20 */   public ContainerEnchantTableInventory enchantSlots = new ContainerEnchantTableInventory(this, "Enchant", true, 1);
/*     */   private World world;
/*     */   private int x;
/*     */   private int y;
/*     */   private int z;
/*  25 */   private Random l = new Random();
/*     */   public long f;
/*  27 */   public int[] costs = new int[3];
/*     */   
/*  29 */   private CraftInventoryView bukkitEntity = null;
/*     */   
/*     */   private Player player;
/*     */   
/*     */   public ContainerEnchantTable(PlayerInventory playerinventory, World world, int i, int j, int k) {
/*  34 */     this.world = world;
/*  35 */     this.x = i;
/*  36 */     this.y = j;
/*  37 */     this.z = k;
/*  38 */     a(new SlotEnchant(this, this.enchantSlots, 0, 25, 47));
/*     */     
/*     */     int l;
/*     */     
/*  42 */     for (l = 0; l < 3; l++) {
/*  43 */       for (int i1 = 0; i1 < 9; i1++) {
/*  44 */         a(new Slot(playerinventory, i1 + l * 9 + 9, 8 + i1 * 18, 84 + l * 18));
/*     */       }
/*     */     } 
/*     */     
/*  48 */     for (l = 0; l < 9; l++) {
/*  49 */       a(new Slot(playerinventory, l, 8 + l * 18, 142));
/*     */     }
/*     */ 
/*     */     
/*  53 */     this.player = (Player)playerinventory.player.getBukkitEntity();
/*  54 */     this.enchantSlots.player = this.player;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addSlotListener(ICrafting icrafting) {
/*  59 */     super.addSlotListener(icrafting);
/*  60 */     icrafting.setContainerData(this, 0, this.costs[0]);
/*  61 */     icrafting.setContainerData(this, 1, this.costs[1]);
/*  62 */     icrafting.setContainerData(this, 2, this.costs[2]);
/*     */   }
/*     */   
/*     */   public void b() {
/*  66 */     super.b();
/*     */     
/*  68 */     for (int i = 0; i < this.listeners.size(); i++) {
/*  69 */       ICrafting icrafting = this.listeners.get(i);
/*     */       
/*  71 */       icrafting.setContainerData(this, 0, this.costs[0]);
/*  72 */       icrafting.setContainerData(this, 1, this.costs[1]);
/*  73 */       icrafting.setContainerData(this, 2, this.costs[2]);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void a(IInventory iinventory) {
/*  78 */     if (iinventory == this.enchantSlots) {
/*  79 */       ItemStack itemstack = iinventory.getItem(0);
/*     */ 
/*     */       
/*  82 */       if (itemstack != null) {
/*  83 */         this.f = this.l.nextLong();
/*  84 */         if (!this.world.isStatic) {
/*  85 */           int i = 0;
/*     */           
/*     */           int j;
/*     */           
/*  89 */           for (j = -1; j <= 1; j++) {
/*  90 */             for (int k = -1; k <= 1; k++) {
/*  91 */               if ((j != 0 || k != 0) && this.world.isEmpty(this.x + k, this.y, this.z + j) && this.world.isEmpty(this.x + k, this.y + 1, this.z + j)) {
/*  92 */                 if (this.world.getType(this.x + k * 2, this.y, this.z + j * 2) == Blocks.BOOKSHELF) {
/*  93 */                   i++;
/*     */                 }
/*     */                 
/*  96 */                 if (this.world.getType(this.x + k * 2, this.y + 1, this.z + j * 2) == Blocks.BOOKSHELF) {
/*  97 */                   i++;
/*     */                 }
/*     */                 
/* 100 */                 if (k != 0 && j != 0) {
/* 101 */                   if (this.world.getType(this.x + k * 2, this.y, this.z + j) == Blocks.BOOKSHELF) {
/* 102 */                     i++;
/*     */                   }
/*     */                   
/* 105 */                   if (this.world.getType(this.x + k * 2, this.y + 1, this.z + j) == Blocks.BOOKSHELF) {
/* 106 */                     i++;
/*     */                   }
/*     */                   
/* 109 */                   if (this.world.getType(this.x + k, this.y, this.z + j * 2) == Blocks.BOOKSHELF) {
/* 110 */                     i++;
/*     */                   }
/*     */                   
/* 113 */                   if (this.world.getType(this.x + k, this.y + 1, this.z + j * 2) == Blocks.BOOKSHELF) {
/* 114 */                     i++;
/*     */                   }
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/* 121 */           for (j = 0; j < 3; j++) {
/* 122 */             this.costs[j] = EnchantmentManager.a(this.l, j, i, itemstack);
/*     */           }
/*     */ 
/*     */           
/* 126 */           CraftItemStack item = CraftItemStack.asCraftMirror(itemstack);
/* 127 */           PrepareItemEnchantEvent event = new PrepareItemEnchantEvent(this.player, (InventoryView)getBukkitView(), this.world.getWorld().getBlockAt(this.x, this.y, this.z), (ItemStack)item, this.costs, i);
/* 128 */           event.setCancelled(!itemstack.x());
/* 129 */           this.world.getServer().getPluginManager().callEvent((Event)event);
/*     */           
/* 131 */           if (event.isCancelled()) {
/* 132 */             for (i = 0; i < 3; i++) {
/* 133 */               this.costs[i] = 0;
/*     */             }
/*     */             
/*     */             return;
/*     */           } 
/*     */           
/* 139 */           b();
/*     */         } 
/*     */       } else {
/* 142 */         for (int i = 0; i < 3; i++) {
/* 143 */           this.costs[i] = 0;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean a(EntityHuman entityhuman, int i) {
/* 150 */     ItemStack itemstack = this.enchantSlots.getItem(0);
/*     */     
/* 152 */     if (this.costs[i] > 0 && itemstack != null && (entityhuman.expLevel >= this.costs[i] || entityhuman.abilities.canInstantlyBuild)) {
/* 153 */       if (!this.world.isStatic) {
/* 154 */         List list = EnchantmentManager.b(this.l, itemstack, this.costs[i]);
/*     */         
/* 156 */         if (list == null) {
/* 157 */           list = new ArrayList();
/*     */         }
/*     */ 
/*     */         
/* 161 */         boolean flag = (itemstack.getItem() == Items.BOOK);
/*     */         
/* 163 */         if (list != null) {
/*     */           
/* 165 */           Map<Enchantment, Integer> enchants = new HashMap<Enchantment, Integer>();
/* 166 */           for (Object obj : list) {
/* 167 */             EnchantmentInstance instance = (EnchantmentInstance)obj;
/* 168 */             enchants.put(Enchantment.getById(instance.enchantment.id), Integer.valueOf(instance.level));
/*     */           } 
/* 170 */           CraftItemStack item = CraftItemStack.asCraftMirror(itemstack);
/*     */           
/* 172 */           EnchantItemEvent event = new EnchantItemEvent((Player)entityhuman.getBukkitEntity(), (InventoryView)getBukkitView(), this.world.getWorld().getBlockAt(this.x, this.y, this.z), (ItemStack)item, this.costs[i], enchants, i);
/* 173 */           this.world.getServer().getPluginManager().callEvent((Event)event);
/*     */           
/* 175 */           int level = event.getExpLevelCost();
/* 176 */           if (event.isCancelled() || (level > entityhuman.expLevel && !entityhuman.abilities.canInstantlyBuild) || event.getEnchantsToAdd().isEmpty()) {
/* 177 */             return false;
/*     */           }
/*     */           
/* 180 */           if (flag) {
/* 181 */             itemstack.setItem(Items.ENCHANTED_BOOK);
/*     */           }
/*     */           
/* 184 */           for (Map.Entry<Enchantment, Integer> entry : (Iterable<Map.Entry<Enchantment, Integer>>)event.getEnchantsToAdd().entrySet()) {
/*     */             try {
/* 186 */               if (flag) {
/* 187 */                 int enchantId = ((Enchantment)entry.getKey()).getId();
/* 188 */                 if (Enchantment.byId[enchantId] == null) {
/*     */                   continue;
/*     */                 }
/*     */                 
/* 192 */                 EnchantmentInstance enchantment = new EnchantmentInstance(enchantId, ((Integer)entry.getValue()).intValue());
/* 193 */                 Items.ENCHANTED_BOOK.a(itemstack, enchantment); continue;
/*     */               } 
/* 195 */               item.addUnsafeEnchantment(entry.getKey(), ((Integer)entry.getValue()).intValue());
/*     */             }
/* 197 */             catch (IllegalArgumentException e) {}
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 202 */           entityhuman.levelDown(-level);
/*     */ 
/*     */           
/* 205 */           a(this.enchantSlots);
/*     */         } 
/*     */       } 
/*     */       
/* 209 */       return true;
/*     */     } 
/* 211 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void b(EntityHuman entityhuman) {
/* 216 */     super.b(entityhuman);
/* 217 */     if (!this.world.isStatic) {
/* 218 */       ItemStack itemstack = this.enchantSlots.splitWithoutUpdate(0);
/*     */       
/* 220 */       if (itemstack != null) {
/* 221 */         entityhuman.drop(itemstack, false);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean a(EntityHuman entityhuman) {
/* 227 */     if (!this.checkReachable) return true; 
/* 228 */     return (this.world.getType(this.x, this.y, this.z) != Blocks.ENCHANTMENT_TABLE) ? false : ((entityhuman.e(this.x + 0.5D, this.y + 0.5D, this.z + 0.5D) <= 64.0D));
/*     */   }
/*     */   
/*     */   public ItemStack b(EntityHuman entityhuman, int i) {
/* 232 */     ItemStack itemstack = null;
/* 233 */     Slot slot = this.c.get(i);
/*     */     
/* 235 */     if (slot != null && slot.hasItem()) {
/* 236 */       ItemStack itemstack1 = slot.getItem();
/*     */       
/* 238 */       itemstack = itemstack1.cloneItemStack();
/* 239 */       if (i == 0) {
/* 240 */         if (!a(itemstack1, 1, 37, true)) {
/* 241 */           return null;
/*     */         }
/*     */       } else {
/* 244 */         if (((Slot)this.c.get(0)).hasItem() || !((Slot)this.c.get(0)).isAllowed(itemstack1)) {
/* 245 */           return null;
/*     */         }
/*     */         
/* 248 */         if (itemstack1.hasTag() && itemstack1.count == 1) {
/* 249 */           ((Slot)this.c.get(0)).set(itemstack1.cloneItemStack());
/* 250 */           itemstack1.count = 0;
/* 251 */         } else if (itemstack1.count >= 1) {
/*     */           
/* 253 */           ItemStack clone = itemstack1.cloneItemStack();
/* 254 */           clone.count = 1;
/* 255 */           ((Slot)this.c.get(0)).set(clone);
/*     */           
/* 257 */           itemstack1.count--;
/*     */         } 
/*     */       } 
/*     */       
/* 261 */       if (itemstack1.count == 0) {
/* 262 */         slot.set((ItemStack)null);
/*     */       } else {
/* 264 */         slot.f();
/*     */       } 
/*     */       
/* 267 */       if (itemstack1.count == itemstack.count) {
/* 268 */         return null;
/*     */       }
/*     */       
/* 271 */       slot.a(entityhuman, itemstack1);
/*     */     } 
/*     */     
/* 274 */     return itemstack;
/*     */   }
/*     */ 
/*     */   
/*     */   public CraftInventoryView getBukkitView() {
/* 279 */     if (this.bukkitEntity != null) {
/* 280 */       return this.bukkitEntity;
/*     */     }
/*     */     
/* 283 */     CraftInventoryEnchanting inventory = new CraftInventoryEnchanting(this.enchantSlots);
/* 284 */     this.bukkitEntity = new CraftInventoryView((HumanEntity)this.player, (Inventory)inventory, this);
/* 285 */     return this.bukkitEntity;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ContainerEnchantTable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
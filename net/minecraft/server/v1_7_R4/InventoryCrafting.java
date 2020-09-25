/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.entity.CraftHumanEntity;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.event.inventory.InventoryType;
/*     */ import org.bukkit.inventory.InventoryHolder;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InventoryCrafting
/*     */   implements IInventory
/*     */ {
/*     */   private ItemStack[] items;
/*     */   private int b;
/*     */   private Container c;
/*  18 */   public List<HumanEntity> transaction = new ArrayList<HumanEntity>();
/*     */   public IRecipe currentRecipe;
/*     */   public IInventory resultInventory;
/*     */   private EntityHuman owner;
/*  22 */   private int maxStack = 64;
/*     */   
/*     */   public ItemStack[] getContents() {
/*  25 */     return this.items;
/*     */   }
/*     */   
/*     */   public void onOpen(CraftHumanEntity who) {
/*  29 */     this.transaction.add(who);
/*     */   }
/*     */   
/*     */   public InventoryType getInvType() {
/*  33 */     return (this.items.length == 4) ? InventoryType.CRAFTING : InventoryType.WORKBENCH;
/*     */   }
/*     */   
/*     */   public void onClose(CraftHumanEntity who) {
/*  37 */     this.transaction.remove(who);
/*     */   }
/*     */   
/*     */   public List<HumanEntity> getViewers() {
/*  41 */     return this.transaction;
/*     */   }
/*     */   
/*     */   public InventoryHolder getOwner() {
/*  45 */     return (InventoryHolder)this.owner.getBukkitEntity();
/*     */   }
/*     */   
/*     */   public void setMaxStackSize(int size) {
/*  49 */     this.maxStack = size;
/*  50 */     this.resultInventory.setMaxStackSize(size);
/*     */   }
/*     */   
/*     */   public InventoryCrafting(Container container, int i, int j, EntityHuman player) {
/*  54 */     this(container, i, j);
/*  55 */     this.owner = player;
/*     */   }
/*     */ 
/*     */   
/*     */   public InventoryCrafting(Container container, int i, int j) {
/*  60 */     int k = i * j;
/*     */     
/*  62 */     this.items = new ItemStack[k];
/*  63 */     this.c = container;
/*  64 */     this.b = i;
/*     */   }
/*     */   
/*     */   public int getSize() {
/*  68 */     return this.items.length;
/*     */   }
/*     */   
/*     */   public ItemStack getItem(int i) {
/*  72 */     return (i >= getSize()) ? null : this.items[i];
/*     */   }
/*     */   
/*     */   public ItemStack b(int i, int j) {
/*  76 */     if (i >= 0 && i < this.b) {
/*  77 */       int k = i + j * this.b;
/*     */       
/*  79 */       return getItem(k);
/*     */     } 
/*  81 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getInventoryName() {
/*  86 */     return "container.crafting";
/*     */   }
/*     */   
/*     */   public boolean k_() {
/*  90 */     return false;
/*     */   }
/*     */   
/*     */   public ItemStack splitWithoutUpdate(int i) {
/*  94 */     if (this.items[i] != null) {
/*  95 */       ItemStack itemstack = this.items[i];
/*     */       
/*  97 */       this.items[i] = null;
/*  98 */       return itemstack;
/*     */     } 
/* 100 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack splitStack(int i, int j) {
/* 105 */     if (this.items[i] != null) {
/*     */ 
/*     */       
/* 108 */       if ((this.items[i]).count <= j) {
/* 109 */         ItemStack itemStack = this.items[i];
/* 110 */         this.items[i] = null;
/* 111 */         this.c.a(this);
/* 112 */         return itemStack;
/*     */       } 
/* 114 */       ItemStack itemstack = this.items[i].a(j);
/* 115 */       if ((this.items[i]).count == 0) {
/* 116 */         this.items[i] = null;
/*     */       }
/*     */       
/* 119 */       this.c.a(this);
/* 120 */       return itemstack;
/*     */     } 
/*     */     
/* 123 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setItem(int i, ItemStack itemstack) {
/* 128 */     this.items[i] = itemstack;
/* 129 */     this.c.a(this);
/*     */   }
/*     */   
/*     */   public int getMaxStackSize() {
/* 133 */     return this.maxStack;
/*     */   }
/*     */   
/*     */   public void update() {}
/*     */   
/*     */   public boolean a(EntityHuman entityhuman) {
/* 139 */     return true;
/*     */   }
/*     */   
/*     */   public void startOpen() {}
/*     */   
/*     */   public void closeContainer() {}
/*     */   
/*     */   public boolean b(int i, ItemStack itemstack) {
/* 147 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\InventoryCrafting.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
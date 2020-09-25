/*     */ package org.bukkit.craftbukkit.v1_7_R4.inventory;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.server.v1_7_R4.EntityHuman;
/*     */ import net.minecraft.server.v1_7_R4.IInventory;
/*     */ import net.minecraft.server.v1_7_R4.ItemStack;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.entity.CraftHumanEntity;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.event.inventory.InventoryType;
/*     */ import org.bukkit.inventory.InventoryHolder;
/*     */ 
/*     */ public class CraftInventoryCustom
/*     */   extends CraftInventory
/*     */ {
/*     */   public CraftInventoryCustom(InventoryHolder owner, InventoryType type) {
/*  18 */     super(new MinecraftInventory(owner, type));
/*     */   }
/*     */   
/*     */   public CraftInventoryCustom(InventoryHolder owner, InventoryType type, String title) {
/*  22 */     super(new MinecraftInventory(owner, type, title));
/*     */   }
/*     */   
/*     */   public CraftInventoryCustom(InventoryHolder owner, int size) {
/*  26 */     super(new MinecraftInventory(owner, size));
/*     */   }
/*     */   
/*     */   public CraftInventoryCustom(InventoryHolder owner, int size, String title) {
/*  30 */     super(new MinecraftInventory(owner, size, title));
/*     */   }
/*     */   
/*     */   static class MinecraftInventory implements IInventory {
/*     */     private final ItemStack[] items;
/*  35 */     private int maxStack = 64;
/*     */     private final List<HumanEntity> viewers;
/*     */     private final String title;
/*     */     private InventoryType type;
/*     */     private final InventoryHolder owner;
/*     */     
/*     */     public MinecraftInventory(InventoryHolder owner, InventoryType type) {
/*  42 */       this(owner, type.getDefaultSize(), type.getDefaultTitle());
/*  43 */       this.type = type;
/*     */     }
/*     */     
/*     */     public MinecraftInventory(InventoryHolder owner, InventoryType type, String title) {
/*  47 */       this(owner, type.getDefaultSize(), title);
/*  48 */       this.type = type;
/*     */     }
/*     */     
/*     */     public MinecraftInventory(InventoryHolder owner, int size) {
/*  52 */       this(owner, size, "Chest");
/*     */     }
/*     */     
/*     */     public MinecraftInventory(InventoryHolder owner, int size, String title) {
/*  56 */       Validate.notNull(title, "Title cannot be null");
/*  57 */       Validate.isTrue((title.length() <= 32), "Title cannot be longer than 32 characters");
/*  58 */       this.items = new ItemStack[size];
/*  59 */       this.title = title;
/*  60 */       this.viewers = new ArrayList<HumanEntity>();
/*  61 */       this.owner = owner;
/*  62 */       this.type = InventoryType.CHEST;
/*     */     }
/*     */     
/*     */     public int getSize() {
/*  66 */       return this.items.length;
/*     */     }
/*     */     
/*     */     public ItemStack getItem(int i) {
/*  70 */       return this.items[i];
/*     */     }
/*     */     
/*     */     public ItemStack splitStack(int i, int j) {
/*  74 */       ItemStack result, stack = getItem(i);
/*     */       
/*  76 */       if (stack == null) return null; 
/*  77 */       if (stack.count <= j) {
/*  78 */         setItem(i, null);
/*  79 */         result = stack;
/*     */       } else {
/*  81 */         result = CraftItemStack.copyNMSStack(stack, j);
/*  82 */         stack.count -= j;
/*     */       } 
/*  84 */       update();
/*  85 */       return result;
/*     */     }
/*     */     
/*     */     public ItemStack splitWithoutUpdate(int i) {
/*  89 */       ItemStack result, stack = getItem(i);
/*     */       
/*  91 */       if (stack == null) return null; 
/*  92 */       if (stack.count <= 1) {
/*  93 */         setItem(i, null);
/*  94 */         result = stack;
/*     */       } else {
/*  96 */         result = CraftItemStack.copyNMSStack(stack, 1);
/*  97 */         stack.count--;
/*     */       } 
/*  99 */       return result;
/*     */     }
/*     */     
/*     */     public void setItem(int i, ItemStack itemstack) {
/* 103 */       this.items[i] = itemstack;
/* 104 */       if (itemstack != null && getMaxStackSize() > 0 && itemstack.count > getMaxStackSize()) {
/* 105 */         itemstack.count = getMaxStackSize();
/*     */       }
/*     */     }
/*     */     
/*     */     public String getInventoryName() {
/* 110 */       return this.title;
/*     */     }
/*     */     
/*     */     public int getMaxStackSize() {
/* 114 */       return this.maxStack;
/*     */     }
/*     */     
/*     */     public void setMaxStackSize(int size) {
/* 118 */       this.maxStack = size;
/*     */     }
/*     */     
/*     */     public void update() {}
/*     */     
/*     */     public boolean a(EntityHuman entityhuman) {
/* 124 */       return true;
/*     */     }
/*     */     
/*     */     public ItemStack[] getContents() {
/* 128 */       return this.items;
/*     */     }
/*     */     
/*     */     public void onOpen(CraftHumanEntity who) {
/* 132 */       this.viewers.add(who);
/*     */     }
/*     */     
/*     */     public void onClose(CraftHumanEntity who) {
/* 136 */       this.viewers.remove(who);
/*     */     }
/*     */     
/*     */     public List<HumanEntity> getViewers() {
/* 140 */       return this.viewers;
/*     */     }
/*     */     
/*     */     public InventoryType getType() {
/* 144 */       return this.type;
/*     */     }
/*     */     
/*     */     public void closeContainer() {}
/*     */     
/*     */     public InventoryHolder getOwner() {
/* 150 */       return this.owner;
/*     */     }
/*     */     
/*     */     public void startOpen() {}
/*     */     
/*     */     public boolean k_() {
/* 156 */       return false;
/*     */     }
/*     */     
/*     */     public boolean b(int i, ItemStack itemstack) {
/* 160 */       return true;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\inventory\CraftInventoryCustom.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
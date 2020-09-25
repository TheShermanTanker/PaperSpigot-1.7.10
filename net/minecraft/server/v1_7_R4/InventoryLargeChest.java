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
/*     */ public class InventoryLargeChest
/*     */   implements IInventory
/*     */ {
/*     */   private String a;
/*     */   public IInventory left;
/*     */   public IInventory right;
/*  17 */   public List<HumanEntity> transaction = new ArrayList<HumanEntity>();
/*     */   
/*     */   public ItemStack[] getContents() {
/*  20 */     ItemStack[] result = new ItemStack[getSize()];
/*  21 */     for (int i = 0; i < result.length; i++) {
/*  22 */       result[i] = getItem(i);
/*     */     }
/*  24 */     return result;
/*     */   }
/*     */   
/*     */   public void onOpen(CraftHumanEntity who) {
/*  28 */     this.left.onOpen(who);
/*  29 */     this.right.onOpen(who);
/*  30 */     this.transaction.add(who);
/*     */   }
/*     */   
/*     */   public void onClose(CraftHumanEntity who) {
/*  34 */     this.left.onClose(who);
/*  35 */     this.right.onClose(who);
/*  36 */     this.transaction.remove(who);
/*     */   }
/*     */   
/*     */   public List<HumanEntity> getViewers() {
/*  40 */     return this.transaction;
/*     */   }
/*     */   
/*     */   public InventoryHolder getOwner() {
/*  44 */     return null;
/*     */   }
/*     */   
/*     */   public void setMaxStackSize(int size) {
/*  48 */     this.left.setMaxStackSize(size);
/*  49 */     this.right.setMaxStackSize(size);
/*     */   }
/*     */ 
/*     */   
/*     */   public InventoryLargeChest(String s, IInventory iinventory, IInventory iinventory1) {
/*  54 */     this.a = s;
/*  55 */     if (iinventory == null) {
/*  56 */       iinventory = iinventory1;
/*     */     }
/*     */     
/*  59 */     if (iinventory1 == null) {
/*  60 */       iinventory1 = iinventory;
/*     */     }
/*     */     
/*  63 */     this.left = iinventory;
/*  64 */     this.right = iinventory1;
/*     */   }
/*     */   
/*     */   public int getSize() {
/*  68 */     return this.left.getSize() + this.right.getSize();
/*     */   }
/*     */   
/*     */   public boolean a(IInventory iinventory) {
/*  72 */     return (this.left == iinventory || this.right == iinventory);
/*     */   }
/*     */   
/*     */   public String getInventoryName() {
/*  76 */     return this.left.k_() ? this.left.getInventoryName() : (this.right.k_() ? this.right.getInventoryName() : this.a);
/*     */   }
/*     */   
/*     */   public boolean k_() {
/*  80 */     return (this.left.k_() || this.right.k_());
/*     */   }
/*     */   
/*     */   public ItemStack getItem(int i) {
/*  84 */     return (i >= this.left.getSize()) ? this.right.getItem(i - this.left.getSize()) : this.left.getItem(i);
/*     */   }
/*     */   
/*     */   public ItemStack splitStack(int i, int j) {
/*  88 */     return (i >= this.left.getSize()) ? this.right.splitStack(i - this.left.getSize(), j) : this.left.splitStack(i, j);
/*     */   }
/*     */   
/*     */   public ItemStack splitWithoutUpdate(int i) {
/*  92 */     return (i >= this.left.getSize()) ? this.right.splitWithoutUpdate(i - this.left.getSize()) : this.left.splitWithoutUpdate(i);
/*     */   }
/*     */   
/*     */   public void setItem(int i, ItemStack itemstack) {
/*  96 */     if (i >= this.left.getSize()) {
/*  97 */       this.right.setItem(i - this.left.getSize(), itemstack);
/*     */     } else {
/*  99 */       this.left.setItem(i, itemstack);
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getMaxStackSize() {
/* 104 */     return Math.min(this.left.getMaxStackSize(), this.right.getMaxStackSize());
/*     */   }
/*     */   
/*     */   public void update() {
/* 108 */     this.left.update();
/* 109 */     this.right.update();
/*     */   }
/*     */   
/*     */   public boolean a(EntityHuman entityhuman) {
/* 113 */     return (this.left.a(entityhuman) && this.right.a(entityhuman));
/*     */   }
/*     */   
/*     */   public void startOpen() {
/* 117 */     this.left.startOpen();
/* 118 */     this.right.startOpen();
/*     */   }
/*     */   
/*     */   public void closeContainer() {
/* 122 */     this.left.closeContainer();
/* 123 */     this.right.closeContainer();
/*     */   }
/*     */   
/*     */   public boolean b(int i, ItemStack itemstack) {
/* 127 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\InventoryLargeChest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
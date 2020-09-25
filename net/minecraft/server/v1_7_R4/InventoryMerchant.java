/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.entity.CraftHumanEntity;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.inventory.InventoryHolder;
/*     */ 
/*     */ public class InventoryMerchant
/*     */   implements IInventory {
/*     */   private final IMerchant merchant;
/*  12 */   private ItemStack[] itemsInSlots = new ItemStack[3];
/*     */   
/*     */   private final EntityHuman player;
/*     */   
/*     */   private MerchantRecipe recipe;
/*     */   private int e;
/*  18 */   public List<HumanEntity> transaction = new ArrayList<HumanEntity>();
/*  19 */   private int maxStack = 64;
/*     */   
/*     */   public ItemStack[] getContents() {
/*  22 */     return this.itemsInSlots;
/*     */   }
/*     */   
/*     */   public void onOpen(CraftHumanEntity who) {
/*  26 */     this.transaction.add(who);
/*     */   }
/*     */   
/*     */   public void onClose(CraftHumanEntity who) {
/*  30 */     this.transaction.remove(who);
/*     */   }
/*     */   
/*     */   public List<HumanEntity> getViewers() {
/*  34 */     return this.transaction;
/*     */   }
/*     */   
/*     */   public void setMaxStackSize(int i) {
/*  38 */     this.maxStack = i;
/*     */   }
/*     */   
/*     */   public InventoryHolder getOwner() {
/*  42 */     return (InventoryHolder)this.player.getBukkitEntity();
/*     */   }
/*     */ 
/*     */   
/*     */   public InventoryMerchant(EntityHuman entityhuman, IMerchant imerchant) {
/*  47 */     this.player = entityhuman;
/*  48 */     this.merchant = imerchant;
/*     */   }
/*     */   
/*     */   public int getSize() {
/*  52 */     return this.itemsInSlots.length;
/*     */   }
/*     */   
/*     */   public ItemStack getItem(int i) {
/*  56 */     return this.itemsInSlots[i];
/*     */   }
/*     */   
/*     */   public ItemStack splitStack(int i, int j) {
/*  60 */     if (this.itemsInSlots[i] != null) {
/*     */ 
/*     */       
/*  63 */       if (i == 2) {
/*  64 */         ItemStack itemStack = this.itemsInSlots[i];
/*  65 */         this.itemsInSlots[i] = null;
/*  66 */         return itemStack;
/*  67 */       }  if ((this.itemsInSlots[i]).count <= j) {
/*  68 */         ItemStack itemStack = this.itemsInSlots[i];
/*  69 */         this.itemsInSlots[i] = null;
/*  70 */         if (d(i)) {
/*  71 */           h();
/*     */         }
/*     */         
/*  74 */         return itemStack;
/*     */       } 
/*  76 */       ItemStack itemstack = this.itemsInSlots[i].a(j);
/*  77 */       if ((this.itemsInSlots[i]).count == 0) {
/*  78 */         this.itemsInSlots[i] = null;
/*     */       }
/*     */       
/*  81 */       if (d(i)) {
/*  82 */         h();
/*     */       }
/*     */       
/*  85 */       return itemstack;
/*     */     } 
/*     */     
/*  88 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean d(int i) {
/*  93 */     return (i == 0 || i == 1);
/*     */   }
/*     */   
/*     */   public ItemStack splitWithoutUpdate(int i) {
/*  97 */     if (this.itemsInSlots[i] != null) {
/*  98 */       ItemStack itemstack = this.itemsInSlots[i];
/*     */       
/* 100 */       this.itemsInSlots[i] = null;
/* 101 */       return itemstack;
/*     */     } 
/* 103 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setItem(int i, ItemStack itemstack) {
/* 108 */     this.itemsInSlots[i] = itemstack;
/* 109 */     if (itemstack != null && itemstack.count > getMaxStackSize()) {
/* 110 */       itemstack.count = getMaxStackSize();
/*     */     }
/*     */     
/* 113 */     if (d(i)) {
/* 114 */       h();
/*     */     }
/*     */   }
/*     */   
/*     */   public String getInventoryName() {
/* 119 */     return "mob.villager";
/*     */   }
/*     */   
/*     */   public boolean k_() {
/* 123 */     return false;
/*     */   }
/*     */   
/*     */   public int getMaxStackSize() {
/* 127 */     return this.maxStack;
/*     */   }
/*     */   
/*     */   public boolean a(EntityHuman entityhuman) {
/* 131 */     return (this.merchant.b() == entityhuman);
/*     */   }
/*     */   
/*     */   public void startOpen() {}
/*     */   
/*     */   public void closeContainer() {}
/*     */   
/*     */   public boolean b(int i, ItemStack itemstack) {
/* 139 */     return true;
/*     */   }
/*     */   
/*     */   public void update() {
/* 143 */     h();
/*     */   }
/*     */   
/*     */   public void h() {
/* 147 */     this.recipe = null;
/* 148 */     ItemStack itemstack = this.itemsInSlots[0];
/* 149 */     ItemStack itemstack1 = this.itemsInSlots[1];
/*     */     
/* 151 */     if (itemstack == null) {
/* 152 */       itemstack = itemstack1;
/* 153 */       itemstack1 = null;
/*     */     } 
/*     */     
/* 156 */     if (itemstack == null) {
/* 157 */       setItem(2, (ItemStack)null);
/*     */     } else {
/* 159 */       MerchantRecipeList merchantrecipelist = this.merchant.getOffers(this.player);
/*     */       
/* 161 */       if (merchantrecipelist != null) {
/* 162 */         MerchantRecipe merchantrecipe = merchantrecipelist.a(itemstack, itemstack1, this.e);
/*     */         
/* 164 */         if (merchantrecipe != null && !merchantrecipe.g()) {
/* 165 */           this.recipe = merchantrecipe;
/* 166 */           setItem(2, merchantrecipe.getBuyItem3().cloneItemStack());
/* 167 */         } else if (itemstack1 != null) {
/* 168 */           merchantrecipe = merchantrecipelist.a(itemstack1, itemstack, this.e);
/* 169 */           if (merchantrecipe != null && !merchantrecipe.g()) {
/* 170 */             this.recipe = merchantrecipe;
/* 171 */             setItem(2, merchantrecipe.getBuyItem3().cloneItemStack());
/*     */           } else {
/* 173 */             setItem(2, (ItemStack)null);
/*     */           } 
/*     */         } else {
/* 176 */           setItem(2, (ItemStack)null);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 181 */     this.merchant.a_(getItem(2));
/*     */   }
/*     */   
/*     */   public MerchantRecipe getRecipe() {
/* 185 */     return this.recipe;
/*     */   }
/*     */   
/*     */   public void c(int i) {
/* 189 */     this.e = i;
/* 190 */     h();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\InventoryMerchant.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class MerchantRecipe
/*    */ {
/*    */   private ItemStack buyingItem1;
/*    */   private ItemStack buyingItem2;
/*    */   private ItemStack sellingItem;
/*    */   public int uses;
/*    */   public int maxUses;
/*    */   
/*    */   public MerchantRecipe(NBTTagCompound nbttagcompound) {
/* 12 */     a(nbttagcompound);
/*    */   }
/*    */   
/*    */   public MerchantRecipe(ItemStack itemstack, ItemStack itemstack1, ItemStack itemstack2) {
/* 16 */     this.buyingItem1 = itemstack;
/* 17 */     this.buyingItem2 = itemstack1;
/* 18 */     this.sellingItem = itemstack2;
/* 19 */     this.maxUses = 7;
/*    */   }
/*    */   
/*    */   public MerchantRecipe(ItemStack itemstack, ItemStack itemstack1) {
/* 23 */     this(itemstack, (ItemStack)null, itemstack1);
/*    */   }
/*    */   
/*    */   public MerchantRecipe(ItemStack itemstack, Item item) {
/* 27 */     this(itemstack, new ItemStack(item));
/*    */   }
/*    */   
/*    */   public ItemStack getBuyItem1() {
/* 31 */     return this.buyingItem1;
/*    */   }
/*    */   
/*    */   public ItemStack getBuyItem2() {
/* 35 */     return this.buyingItem2;
/*    */   }
/*    */   
/*    */   public boolean hasSecondItem() {
/* 39 */     return (this.buyingItem2 != null);
/*    */   }
/*    */   
/*    */   public ItemStack getBuyItem3() {
/* 43 */     return this.sellingItem;
/*    */   }
/*    */   
/*    */   public boolean a(MerchantRecipe merchantrecipe) {
/* 47 */     return (this.buyingItem1.getItem() == merchantrecipe.buyingItem1.getItem() && this.sellingItem.getItem() == merchantrecipe.sellingItem.getItem()) ? (((this.buyingItem2 == null && merchantrecipe.buyingItem2 == null) || (this.buyingItem2 != null && merchantrecipe.buyingItem2 != null && this.buyingItem2.getItem() == merchantrecipe.buyingItem2.getItem()))) : false;
/*    */   }
/*    */   
/*    */   public boolean b(MerchantRecipe merchantrecipe) {
/* 51 */     return (a(merchantrecipe) && (this.buyingItem1.count < merchantrecipe.buyingItem1.count || (this.buyingItem2 != null && this.buyingItem2.count < merchantrecipe.buyingItem2.count)));
/*    */   }
/*    */   
/*    */   public void f() {
/* 55 */     this.uses++;
/*    */   }
/*    */   
/*    */   public void a(int i) {
/* 59 */     this.maxUses += i;
/*    */   }
/*    */   
/*    */   public boolean g() {
/* 63 */     return (this.uses >= this.maxUses);
/*    */   }
/*    */   
/*    */   public void a(NBTTagCompound nbttagcompound) {
/* 67 */     NBTTagCompound nbttagcompound1 = nbttagcompound.getCompound("buy");
/*    */     
/* 69 */     this.buyingItem1 = ItemStack.createStack(nbttagcompound1);
/* 70 */     NBTTagCompound nbttagcompound2 = nbttagcompound.getCompound("sell");
/*    */     
/* 72 */     this.sellingItem = ItemStack.createStack(nbttagcompound2);
/* 73 */     if (nbttagcompound.hasKeyOfType("buyB", 10)) {
/* 74 */       this.buyingItem2 = ItemStack.createStack(nbttagcompound.getCompound("buyB"));
/*    */     }
/*    */     
/* 77 */     if (nbttagcompound.hasKeyOfType("uses", 99)) {
/* 78 */       this.uses = nbttagcompound.getInt("uses");
/*    */     }
/*    */     
/* 81 */     if (nbttagcompound.hasKeyOfType("maxUses", 99)) {
/* 82 */       this.maxUses = nbttagcompound.getInt("maxUses");
/*    */     } else {
/* 84 */       this.maxUses = 7;
/*    */     } 
/*    */   }
/*    */   
/*    */   public NBTTagCompound i() {
/* 89 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*    */     
/* 91 */     nbttagcompound.set("buy", this.buyingItem1.save(new NBTTagCompound()));
/* 92 */     nbttagcompound.set("sell", this.sellingItem.save(new NBTTagCompound()));
/* 93 */     if (this.buyingItem2 != null) {
/* 94 */       nbttagcompound.set("buyB", this.buyingItem2.save(new NBTTagCompound()));
/*    */     }
/*    */     
/* 97 */     nbttagcompound.setInt("uses", this.uses);
/* 98 */     nbttagcompound.setInt("maxUses", this.maxUses);
/* 99 */     return nbttagcompound;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\MerchantRecipe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
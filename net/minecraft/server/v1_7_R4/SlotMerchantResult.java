/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SlotMerchantResult
/*    */   extends Slot
/*    */ {
/*    */   private final InventoryMerchant a;
/*    */   private EntityHuman b;
/*    */   private int c;
/*    */   private final IMerchant d;
/*    */   
/*    */   public SlotMerchantResult(EntityHuman paramEntityHuman, IMerchant paramIMerchant, InventoryMerchant paramInventoryMerchant, int paramInt1, int paramInt2, int paramInt3) {
/* 15 */     super(paramInventoryMerchant, paramInt1, paramInt2, paramInt3);
/* 16 */     this.b = paramEntityHuman;
/* 17 */     this.d = paramIMerchant;
/* 18 */     this.a = paramInventoryMerchant;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isAllowed(ItemStack paramItemStack) {
/* 23 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack a(int paramInt) {
/* 28 */     if (hasItem()) {
/* 29 */       this.c += Math.min(paramInt, (getItem()).count);
/*    */     }
/* 31 */     return super.a(paramInt);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void a(ItemStack paramItemStack, int paramInt) {
/* 36 */     this.c += paramInt;
/* 37 */     b(paramItemStack);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void b(ItemStack paramItemStack) {
/* 42 */     paramItemStack.a(this.b.world, this.b, this.c);
/* 43 */     this.c = 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(EntityHuman paramEntityHuman, ItemStack paramItemStack) {
/* 48 */     b(paramItemStack);
/*    */     
/* 50 */     MerchantRecipe merchantRecipe = this.a.getRecipe();
/* 51 */     if (merchantRecipe != null) {
/*    */       
/* 53 */       ItemStack itemStack1 = this.a.getItem(0);
/* 54 */       ItemStack itemStack2 = this.a.getItem(1);
/*    */ 
/*    */       
/* 57 */       if (a(merchantRecipe, itemStack1, itemStack2) || a(merchantRecipe, itemStack2, itemStack1)) {
/* 58 */         this.d.a(merchantRecipe);
/*    */         
/* 60 */         if (itemStack1 != null && itemStack1.count <= 0) {
/* 61 */           itemStack1 = null;
/*    */         }
/* 63 */         if (itemStack2 != null && itemStack2.count <= 0) {
/* 64 */           itemStack2 = null;
/*    */         }
/* 66 */         this.a.setItem(0, itemStack1);
/* 67 */         this.a.setItem(1, itemStack2);
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   private boolean a(MerchantRecipe paramMerchantRecipe, ItemStack paramItemStack1, ItemStack paramItemStack2) {
/* 73 */     ItemStack itemStack1 = paramMerchantRecipe.getBuyItem1();
/* 74 */     ItemStack itemStack2 = paramMerchantRecipe.getBuyItem2();
/*    */     
/* 76 */     if (paramItemStack1 != null && paramItemStack1.getItem() == itemStack1.getItem()) {
/* 77 */       if (itemStack2 != null && paramItemStack2 != null && itemStack2.getItem() == paramItemStack2.getItem()) {
/* 78 */         paramItemStack1.count -= itemStack1.count;
/* 79 */         paramItemStack2.count -= itemStack2.count;
/* 80 */         return true;
/* 81 */       }  if (itemStack2 == null && paramItemStack2 == null) {
/* 82 */         paramItemStack1.count -= itemStack1.count;
/* 83 */         return true;
/*    */       } 
/*    */     } 
/* 86 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\SlotMerchantResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SlotResult
/*    */   extends Slot
/*    */ {
/*    */   private final IInventory a;
/*    */   private EntityHuman b;
/*    */   private int c;
/*    */   
/*    */   public SlotResult(EntityHuman paramEntityHuman, IInventory paramIInventory1, IInventory paramIInventory2, int paramInt1, int paramInt2, int paramInt3) {
/* 15 */     super(paramIInventory2, paramInt1, paramInt2, paramInt3);
/* 16 */     this.b = paramEntityHuman;
/* 17 */     this.a = paramIInventory1;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isAllowed(ItemStack paramItemStack) {
/* 22 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack a(int paramInt) {
/* 27 */     if (hasItem()) {
/* 28 */       this.c += Math.min(paramInt, (getItem()).count);
/*    */     }
/* 30 */     return super.a(paramInt);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void a(ItemStack paramItemStack, int paramInt) {
/* 35 */     this.c += paramInt;
/* 36 */     b(paramItemStack);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void b(ItemStack paramItemStack) {
/* 41 */     paramItemStack.a(this.b.world, this.b, this.c);
/* 42 */     this.c = 0;
/*    */     
/* 44 */     if (paramItemStack.getItem() == Item.getItemOf(Blocks.WORKBENCH)) this.b.a(AchievementList.h, 1); 
/* 45 */     if (paramItemStack.getItem() instanceof ItemPickaxe) this.b.a(AchievementList.i, 1); 
/* 46 */     if (paramItemStack.getItem() == Item.getItemOf(Blocks.FURNACE)) this.b.a(AchievementList.j, 1); 
/* 47 */     if (paramItemStack.getItem() instanceof ItemHoe) this.b.a(AchievementList.l, 1); 
/* 48 */     if (paramItemStack.getItem() == Items.BREAD) this.b.a(AchievementList.m, 1); 
/* 49 */     if (paramItemStack.getItem() == Items.CAKE) this.b.a(AchievementList.n, 1); 
/* 50 */     if (paramItemStack.getItem() instanceof ItemPickaxe && ((ItemPickaxe)paramItemStack.getItem()).i() != EnumToolMaterial.WOOD) this.b.a(AchievementList.o, 1); 
/* 51 */     if (paramItemStack.getItem() instanceof ItemSword) this.b.a(AchievementList.r, 1); 
/* 52 */     if (paramItemStack.getItem() == Item.getItemOf(Blocks.ENCHANTMENT_TABLE)) this.b.a(AchievementList.E, 1); 
/* 53 */     if (paramItemStack.getItem() == Item.getItemOf(Blocks.BOOKSHELF)) this.b.a(AchievementList.G, 1);
/*    */   
/*    */   }
/*    */   
/*    */   public void a(EntityHuman paramEntityHuman, ItemStack paramItemStack) {
/* 58 */     b(paramItemStack);
/*    */     
/* 60 */     for (byte b = 0; b < this.a.getSize(); b++) {
/* 61 */       ItemStack itemStack = this.a.getItem(b);
/* 62 */       if (itemStack != null) {
/* 63 */         this.a.splitStack(b, 1);
/*    */         
/* 65 */         if (itemStack.getItem().u()) {
/* 66 */           ItemStack itemStack1 = new ItemStack(itemStack.getItem().t());
/*    */ 
/*    */           
/* 69 */           if (!itemStack.getItem().l(itemStack) || !this.b.inventory.pickup(itemStack1))
/*    */           {
/*    */ 
/*    */ 
/*    */             
/* 74 */             if (this.a.getItem(b) == null) {
/* 75 */               this.a.setItem(b, itemStack1);
/*    */             } else {
/*    */               
/* 78 */               this.b.drop(itemStack1, false);
/*    */             } 
/*    */           }
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\SlotResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
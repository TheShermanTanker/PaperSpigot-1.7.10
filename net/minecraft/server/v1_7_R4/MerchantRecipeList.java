/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ public class MerchantRecipeList
/*    */   extends ArrayList {
/*    */   public MerchantRecipeList() {}
/*    */   
/*    */   public MerchantRecipeList(NBTTagCompound nbttagcompound) {
/* 10 */     a(nbttagcompound);
/*    */   }
/*    */   
/*    */   public MerchantRecipe a(ItemStack itemstack, ItemStack itemstack1, int i) {
/* 14 */     if (i > 0 && i < size()) {
/* 15 */       MerchantRecipe merchantrecipe = (MerchantRecipe)get(i);
/*    */       
/* 17 */       return (itemstack.getItem() == merchantrecipe.getBuyItem1().getItem() && ((itemstack1 == null && !merchantrecipe.hasSecondItem()) || (merchantrecipe.hasSecondItem() && itemstack1 != null && merchantrecipe.getBuyItem2().getItem() == itemstack1.getItem())) && itemstack.count >= (merchantrecipe.getBuyItem1()).count && (!merchantrecipe.hasSecondItem() || itemstack1.count >= (merchantrecipe.getBuyItem2()).count)) ? merchantrecipe : null;
/*    */     } 
/* 19 */     for (int j = 0; j < size(); j++) {
/* 20 */       MerchantRecipe merchantrecipe1 = (MerchantRecipe)get(j);
/*    */       
/* 22 */       if (itemstack.getItem() == merchantrecipe1.getBuyItem1().getItem() && itemstack.count >= (merchantrecipe1.getBuyItem1()).count && ((!merchantrecipe1.hasSecondItem() && itemstack1 == null) || (merchantrecipe1.hasSecondItem() && itemstack1 != null && merchantrecipe1.getBuyItem2().getItem() == itemstack1.getItem() && itemstack1.count >= (merchantrecipe1.getBuyItem2()).count))) {
/* 23 */         return merchantrecipe1;
/*    */       }
/*    */     } 
/*    */     
/* 27 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(MerchantRecipe merchantrecipe) {
/* 32 */     for (int i = 0; i < size(); i++) {
/* 33 */       MerchantRecipe merchantrecipe1 = (MerchantRecipe)get(i);
/*    */       
/* 35 */       if (merchantrecipe.a(merchantrecipe1)) {
/* 36 */         if (merchantrecipe.b(merchantrecipe1)) {
/* 37 */           set(i, (E)merchantrecipe);
/*    */         }
/*    */         
/*    */         return;
/*    */       } 
/*    */     } 
/*    */     
/* 44 */     add((E)merchantrecipe);
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer packetdataserializer) {
/* 48 */     packetdataserializer.writeByte((byte)(size() & 0xFF));
/*    */     
/* 50 */     for (int i = 0; i < size(); i++) {
/* 51 */       MerchantRecipe merchantrecipe = (MerchantRecipe)get(i);
/*    */       
/* 53 */       packetdataserializer.a(merchantrecipe.getBuyItem1());
/* 54 */       packetdataserializer.a(merchantrecipe.getBuyItem3());
/* 55 */       ItemStack itemstack = merchantrecipe.getBuyItem2();
/*    */       
/* 57 */       packetdataserializer.writeBoolean((itemstack != null));
/* 58 */       if (itemstack != null) {
/* 59 */         packetdataserializer.a(itemstack);
/*    */       }
/*    */       
/* 62 */       packetdataserializer.writeBoolean(merchantrecipe.g());
/*    */       
/* 64 */       if (packetdataserializer.version >= 28) {
/*    */         
/* 66 */         packetdataserializer.writeInt(merchantrecipe.uses);
/* 67 */         packetdataserializer.writeInt(merchantrecipe.maxUses);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(NBTTagCompound nbttagcompound) {
/* 74 */     NBTTagList nbttaglist = nbttagcompound.getList("Recipes", 10);
/*    */     
/* 76 */     for (int i = 0; i < nbttaglist.size(); i++) {
/* 77 */       NBTTagCompound nbttagcompound1 = nbttaglist.get(i);
/*    */       
/* 79 */       add((E)new MerchantRecipe(nbttagcompound1));
/*    */     } 
/*    */   }
/*    */   
/*    */   public NBTTagCompound a() {
/* 84 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 85 */     NBTTagList nbttaglist = new NBTTagList();
/*    */     
/* 87 */     for (int i = 0; i < size(); i++) {
/* 88 */       MerchantRecipe merchantrecipe = (MerchantRecipe)get(i);
/*    */       
/* 90 */       nbttaglist.add(merchantrecipe.i());
/*    */     } 
/*    */     
/* 93 */     nbttagcompound.set("Recipes", nbttaglist);
/* 94 */     return nbttagcompound;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\MerchantRecipeList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
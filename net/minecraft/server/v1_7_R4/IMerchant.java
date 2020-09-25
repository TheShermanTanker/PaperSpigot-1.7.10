package net.minecraft.server.v1_7_R4;

public interface IMerchant {
  void a_(EntityHuman paramEntityHuman);
  
  EntityHuman b();
  
  MerchantRecipeList getOffers(EntityHuman paramEntityHuman);
  
  void a(MerchantRecipe paramMerchantRecipe);
  
  void a_(ItemStack paramItemStack);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\IMerchant.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
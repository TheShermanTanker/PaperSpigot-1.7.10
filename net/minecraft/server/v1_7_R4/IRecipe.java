package net.minecraft.server.v1_7_R4;

import java.util.List;
import org.bukkit.inventory.Recipe;

public interface IRecipe {
  boolean a(InventoryCrafting paramInventoryCrafting, World paramWorld);
  
  ItemStack a(InventoryCrafting paramInventoryCrafting);
  
  int a();
  
  ItemStack b();
  
  Recipe toBukkitRecipe();
  
  List<ItemStack> getIngredients();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\IRecipe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
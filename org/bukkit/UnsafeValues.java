package org.bukkit;

import java.util.List;
import org.bukkit.inventory.ItemStack;

@Deprecated
public interface UnsafeValues {
  Material getMaterialFromInternalName(String paramString);
  
  List<String> tabCompleteInternalMaterialName(String paramString, List<String> paramList);
  
  ItemStack modifyItemStack(ItemStack paramItemStack, String paramString);
  
  Statistic getStatisticFromInternalName(String paramString);
  
  Achievement getAchievementFromInternalName(String paramString);
  
  List<String> tabCompleteInternalStatisticOrAchievementName(String paramString, List<String> paramList);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\UnsafeValues.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
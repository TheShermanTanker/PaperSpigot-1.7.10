package net.minecraft.server.v1_7_R4;

import java.util.List;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.InventoryHolder;

public interface IInventory {
  public static final int MAX_STACK = 64;
  
  int getSize();
  
  ItemStack getItem(int paramInt);
  
  ItemStack splitStack(int paramInt1, int paramInt2);
  
  ItemStack splitWithoutUpdate(int paramInt);
  
  void setItem(int paramInt, ItemStack paramItemStack);
  
  String getInventoryName();
  
  boolean k_();
  
  int getMaxStackSize();
  
  void update();
  
  boolean a(EntityHuman paramEntityHuman);
  
  void startOpen();
  
  void closeContainer();
  
  boolean b(int paramInt, ItemStack paramItemStack);
  
  ItemStack[] getContents();
  
  void onOpen(CraftHumanEntity paramCraftHumanEntity);
  
  void onClose(CraftHumanEntity paramCraftHumanEntity);
  
  List<HumanEntity> getViewers();
  
  InventoryHolder getOwner();
  
  void setMaxStackSize(int paramInt);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\IInventory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
package org.bukkit.inventory;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;

public interface Inventory extends Iterable<ItemStack> {
  int getSize();
  
  int getMaxStackSize();
  
  void setMaxStackSize(int paramInt);
  
  String getName();
  
  ItemStack getItem(int paramInt);
  
  void setItem(int paramInt, ItemStack paramItemStack);
  
  HashMap<Integer, ItemStack> addItem(ItemStack... paramVarArgs) throws IllegalArgumentException;
  
  HashMap<Integer, ItemStack> removeItem(ItemStack... paramVarArgs) throws IllegalArgumentException;
  
  ItemStack[] getContents();
  
  void setContents(ItemStack[] paramArrayOfItemStack) throws IllegalArgumentException;
  
  @Deprecated
  boolean contains(int paramInt);
  
  boolean contains(Material paramMaterial) throws IllegalArgumentException;
  
  boolean contains(ItemStack paramItemStack);
  
  @Deprecated
  boolean contains(int paramInt1, int paramInt2);
  
  boolean contains(Material paramMaterial, int paramInt) throws IllegalArgumentException;
  
  boolean contains(ItemStack paramItemStack, int paramInt);
  
  boolean containsAtLeast(ItemStack paramItemStack, int paramInt);
  
  @Deprecated
  HashMap<Integer, ? extends ItemStack> all(int paramInt);
  
  HashMap<Integer, ? extends ItemStack> all(Material paramMaterial) throws IllegalArgumentException;
  
  HashMap<Integer, ? extends ItemStack> all(ItemStack paramItemStack);
  
  @Deprecated
  int first(int paramInt);
  
  int first(Material paramMaterial) throws IllegalArgumentException;
  
  int first(ItemStack paramItemStack);
  
  int firstEmpty();
  
  @Deprecated
  void remove(int paramInt);
  
  void remove(Material paramMaterial) throws IllegalArgumentException;
  
  void remove(ItemStack paramItemStack);
  
  void clear(int paramInt);
  
  void clear();
  
  List<HumanEntity> getViewers();
  
  String getTitle();
  
  InventoryType getType();
  
  InventoryHolder getHolder();
  
  ListIterator<ItemStack> iterator();
  
  ListIterator<ItemStack> iterator(int paramInt);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\inventory\Inventory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
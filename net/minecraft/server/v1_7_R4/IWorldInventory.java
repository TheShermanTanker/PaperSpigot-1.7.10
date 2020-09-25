package net.minecraft.server.v1_7_R4;

public interface IWorldInventory extends IInventory {
  int[] getSlotsForFace(int paramInt);
  
  boolean canPlaceItemThroughFace(int paramInt1, ItemStack paramItemStack, int paramInt2);
  
  boolean canTakeItemThroughFace(int paramInt1, ItemStack paramItemStack, int paramInt2);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\IWorldInventory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
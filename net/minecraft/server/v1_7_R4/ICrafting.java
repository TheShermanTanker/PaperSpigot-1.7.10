package net.minecraft.server.v1_7_R4;

import java.util.List;

public interface ICrafting {
  void a(Container paramContainer, List paramList);
  
  void a(Container paramContainer, int paramInt, ItemStack paramItemStack);
  
  void setContainerData(Container paramContainer, int paramInt1, int paramInt2);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ICrafting.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
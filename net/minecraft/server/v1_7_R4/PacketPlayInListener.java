package net.minecraft.server.v1_7_R4;

public interface PacketPlayInListener extends PacketListener {
  void a(PacketPlayInArmAnimation paramPacketPlayInArmAnimation);
  
  void a(PacketPlayInChat paramPacketPlayInChat);
  
  void a(PacketPlayInTabComplete paramPacketPlayInTabComplete);
  
  void a(PacketPlayInClientCommand paramPacketPlayInClientCommand);
  
  void a(PacketPlayInSettings paramPacketPlayInSettings);
  
  void a(PacketPlayInTransaction paramPacketPlayInTransaction);
  
  void a(PacketPlayInEnchantItem paramPacketPlayInEnchantItem);
  
  void a(PacketPlayInWindowClick paramPacketPlayInWindowClick);
  
  void a(PacketPlayInCloseWindow paramPacketPlayInCloseWindow);
  
  void a(PacketPlayInCustomPayload paramPacketPlayInCustomPayload);
  
  void a(PacketPlayInUseEntity paramPacketPlayInUseEntity);
  
  void a(PacketPlayInKeepAlive paramPacketPlayInKeepAlive);
  
  void a(PacketPlayInFlying paramPacketPlayInFlying);
  
  void a(PacketPlayInAbilities paramPacketPlayInAbilities);
  
  void a(PacketPlayInBlockDig paramPacketPlayInBlockDig);
  
  void a(PacketPlayInEntityAction paramPacketPlayInEntityAction);
  
  void a(PacketPlayInSteerVehicle paramPacketPlayInSteerVehicle);
  
  void a(PacketPlayInHeldItemSlot paramPacketPlayInHeldItemSlot);
  
  void a(PacketPlayInSetCreativeSlot paramPacketPlayInSetCreativeSlot);
  
  void a(PacketPlayInUpdateSign paramPacketPlayInUpdateSign);
  
  void a(PacketPlayInBlockPlace paramPacketPlayInBlockPlace);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayInListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
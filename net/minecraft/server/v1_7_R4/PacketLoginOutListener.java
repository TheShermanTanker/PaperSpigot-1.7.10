package net.minecraft.server.v1_7_R4;

public interface PacketLoginOutListener extends PacketListener {
  void a(PacketLoginOutEncryptionBegin paramPacketLoginOutEncryptionBegin);
  
  void a(PacketLoginOutSuccess paramPacketLoginOutSuccess);
  
  void a(PacketLoginOutDisconnect paramPacketLoginOutDisconnect);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketLoginOutListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
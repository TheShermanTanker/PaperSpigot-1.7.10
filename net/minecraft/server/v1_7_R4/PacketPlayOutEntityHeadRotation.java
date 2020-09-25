/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class PacketPlayOutEntityHeadRotation
/*    */   extends Packet {
/*    */   private int a;
/*    */   private byte b;
/*    */   
/*    */   public PacketPlayOutEntityHeadRotation() {}
/*    */   
/*    */   public PacketPlayOutEntityHeadRotation(Entity entity, byte b0) {
/* 11 */     this.a = entity.getId();
/* 12 */     this.b = b0;
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer packetdataserializer) {
/* 16 */     this.a = packetdataserializer.readInt();
/* 17 */     this.b = packetdataserializer.readByte();
/*    */   }
/*    */ 
/*    */   
/*    */   public void b(PacketDataSerializer packetdataserializer) {
/* 22 */     if (packetdataserializer.version < 16) {
/*    */       
/* 24 */       packetdataserializer.writeInt(this.a);
/*    */     } else {
/*    */       
/* 27 */       packetdataserializer.b(this.a);
/*    */     } 
/*    */     
/* 30 */     packetdataserializer.writeByte(this.b);
/*    */   }
/*    */   
/*    */   public void a(PacketPlayOutListener packetplayoutlistener) {
/* 34 */     packetplayoutlistener.a(this);
/*    */   }
/*    */   
/*    */   public String b() {
/* 38 */     return String.format("id=%d, rot=%d", new Object[] { Integer.valueOf(this.a), Byte.valueOf(this.b) });
/*    */   }
/*    */   
/*    */   public void handle(PacketListener packetlistener) {
/* 42 */     a((PacketPlayOutListener)packetlistener);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayOutEntityHeadRotation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
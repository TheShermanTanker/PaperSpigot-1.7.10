/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayInPositionLook
/*    */   extends PacketPlayInFlying
/*    */ {
/*    */   public void a(PacketDataSerializer packetdataserializer) {
/* 11 */     this.x = packetdataserializer.readDouble();
/*    */     
/* 13 */     if (packetdataserializer.version < 16) {
/*    */       
/* 15 */       this.y = packetdataserializer.readDouble();
/* 16 */       this.stance = packetdataserializer.readDouble();
/*    */     } else {
/*    */       
/* 19 */       this.y = packetdataserializer.readDouble();
/* 20 */       this.stance = this.y + 1.62D;
/*    */     } 
/*    */     
/* 23 */     this.z = packetdataserializer.readDouble();
/* 24 */     this.yaw = packetdataserializer.readFloat();
/* 25 */     this.pitch = packetdataserializer.readFloat();
/* 26 */     super.a(packetdataserializer);
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer packetdataserializer) {
/* 30 */     packetdataserializer.writeDouble(this.x);
/* 31 */     packetdataserializer.writeDouble(this.y);
/* 32 */     packetdataserializer.writeDouble(this.stance);
/* 33 */     packetdataserializer.writeDouble(this.z);
/* 34 */     packetdataserializer.writeFloat(this.yaw);
/* 35 */     packetdataserializer.writeFloat(this.pitch);
/* 36 */     super.b(packetdataserializer);
/*    */   }
/*    */   
/*    */   public void handle(PacketListener packetlistener) {
/* 40 */     a((PacketPlayInListener)packetlistener);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayInPositionLook.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
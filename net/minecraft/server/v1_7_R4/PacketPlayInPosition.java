/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayInPosition
/*    */   extends PacketPlayInFlying
/*    */ {
/*    */   public void a(PacketDataSerializer packetdataserializer) {
/* 10 */     this.x = packetdataserializer.readDouble();
/*    */     
/* 12 */     if (packetdataserializer.version < 16) {
/*    */       
/* 14 */       this.y = packetdataserializer.readDouble();
/* 15 */       this.stance = packetdataserializer.readDouble();
/*    */     } else {
/*    */       
/* 18 */       this.y = packetdataserializer.readDouble();
/* 19 */       this.stance = this.y + 1.62D;
/*    */     } 
/*    */     
/* 22 */     this.z = packetdataserializer.readDouble();
/* 23 */     super.a(packetdataserializer);
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer packetdataserializer) {
/* 27 */     packetdataserializer.writeDouble(this.x);
/* 28 */     packetdataserializer.writeDouble(this.y);
/* 29 */     packetdataserializer.writeDouble(this.stance);
/* 30 */     packetdataserializer.writeDouble(this.z);
/* 31 */     super.b(packetdataserializer);
/*    */   }
/*    */   
/*    */   public void handle(PacketListener packetlistener) {
/* 35 */     a((PacketPlayInListener)packetlistener);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayInPosition.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
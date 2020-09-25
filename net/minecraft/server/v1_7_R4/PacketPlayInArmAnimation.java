/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayInArmAnimation
/*    */   extends Packet
/*    */ {
/*    */   private int a;
/*    */   private int b;
/*    */   
/*    */   public void a(PacketDataSerializer packetdataserializer) {
/* 12 */     if (packetdataserializer.version < 16) {
/*    */       
/* 14 */       this.a = packetdataserializer.readInt();
/* 15 */       this.b = packetdataserializer.readByte();
/*    */     } else {
/* 17 */       this.b = 1;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void b(PacketDataSerializer packetdataserializer) {
/* 23 */     packetdataserializer.writeInt(this.a);
/* 24 */     packetdataserializer.writeByte(this.b);
/*    */   }
/*    */   
/*    */   public void a(PacketPlayInListener packetplayinlistener) {
/* 28 */     packetplayinlistener.a(this);
/*    */   }
/*    */   
/*    */   public String b() {
/* 32 */     return String.format("id=%d, type=%d", new Object[] { Integer.valueOf(this.a), Integer.valueOf(this.b) });
/*    */   }
/*    */   
/*    */   public int d() {
/* 36 */     return this.b;
/*    */   }
/*    */   
/*    */   public void handle(PacketListener packetlistener) {
/* 40 */     a((PacketPlayInListener)packetlistener);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayInArmAnimation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
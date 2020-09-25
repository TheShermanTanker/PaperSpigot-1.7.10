/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ public class PacketPlayInKeepAlive
/*    */   extends Packet
/*    */ {
/*    */   private int a;
/*    */   
/*    */   public void a(PacketPlayInListener packetplayinlistener) {
/* 10 */     packetplayinlistener.a(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(PacketDataSerializer packetdataserializer) {
/* 15 */     if (packetdataserializer.version < 16) {
/*    */       
/* 17 */       this.a = packetdataserializer.readInt();
/*    */     } else {
/*    */       
/* 20 */       this.a = packetdataserializer.a();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void b(PacketDataSerializer packetdataserializer) {
/* 26 */     packetdataserializer.writeInt(this.a);
/*    */   }
/*    */   
/*    */   public boolean a() {
/* 30 */     return true;
/*    */   }
/*    */   
/*    */   public int c() {
/* 34 */     return this.a;
/*    */   }
/*    */   
/*    */   public void handle(PacketListener packetlistener) {
/* 38 */     a((PacketPlayInListener)packetlistener);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayInKeepAlive.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
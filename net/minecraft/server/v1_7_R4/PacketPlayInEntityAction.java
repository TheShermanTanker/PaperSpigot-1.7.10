/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayInEntityAction
/*    */   extends Packet
/*    */ {
/*    */   private int a;
/*    */   private int animation;
/*    */   private int c;
/*    */   
/*    */   public void a(PacketDataSerializer packetdataserializer) {
/* 13 */     if (packetdataserializer.version < 16) {
/*    */       
/* 15 */       this.a = packetdataserializer.readInt();
/* 16 */       this.animation = packetdataserializer.readByte();
/* 17 */       this.c = packetdataserializer.readInt();
/*    */     } else {
/*    */       
/* 20 */       this.a = packetdataserializer.a();
/* 21 */       this.animation = packetdataserializer.readUnsignedByte() + 1;
/* 22 */       this.c = packetdataserializer.a();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void b(PacketDataSerializer packetdataserializer) {
/* 28 */     packetdataserializer.writeInt(this.a);
/* 29 */     packetdataserializer.writeByte(this.animation);
/* 30 */     packetdataserializer.writeInt(this.c);
/*    */   }
/*    */   
/*    */   public void a(PacketPlayInListener packetplayinlistener) {
/* 34 */     packetplayinlistener.a(this);
/*    */   }
/*    */   
/*    */   public int d() {
/* 38 */     return this.animation;
/*    */   }
/*    */   
/*    */   public int e() {
/* 42 */     return this.c;
/*    */   }
/*    */   
/*    */   public void handle(PacketListener packetlistener) {
/* 46 */     a((PacketPlayInListener)packetlistener);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayInEntityAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
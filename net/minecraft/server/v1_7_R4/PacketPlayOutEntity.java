/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class PacketPlayOutEntity
/*    */   extends Packet {
/*    */   protected int a;
/*    */   protected byte b;
/*    */   protected byte c;
/*    */   protected byte d;
/*    */   protected byte e;
/*    */   protected byte f;
/*    */   protected boolean g;
/*    */   
/*    */   public PacketPlayOutEntity() {}
/*    */   
/*    */   public PacketPlayOutEntity(int i) {
/* 16 */     this.a = i;
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer packetdataserializer) {
/* 20 */     this.a = packetdataserializer.readInt();
/*    */   }
/*    */ 
/*    */   
/*    */   public void b(PacketDataSerializer packetdataserializer) {
/* 25 */     if (packetdataserializer.version < 16) {
/*    */       
/* 27 */       packetdataserializer.writeInt(this.a);
/*    */     } else {
/*    */       
/* 30 */       packetdataserializer.b(this.a);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(PacketPlayOutListener packetplayoutlistener) {
/* 36 */     packetplayoutlistener.a(this);
/*    */   }
/*    */   
/*    */   public String b() {
/* 40 */     return String.format("id=%d", new Object[] { Integer.valueOf(this.a) });
/*    */   }
/*    */   
/*    */   public String toString() {
/* 44 */     return "Entity_" + super.toString();
/*    */   }
/*    */   
/*    */   public void handle(PacketListener packetlistener) {
/* 48 */     a((PacketPlayOutListener)packetlistener);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayOutEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
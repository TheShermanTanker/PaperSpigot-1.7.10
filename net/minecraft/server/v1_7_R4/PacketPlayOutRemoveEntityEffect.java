/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class PacketPlayOutRemoveEntityEffect
/*    */   extends Packet {
/*    */   private int a;
/*    */   private int b;
/*    */   
/*    */   public PacketPlayOutRemoveEntityEffect() {}
/*    */   
/*    */   public PacketPlayOutRemoveEntityEffect(int i, MobEffect mobeffect) {
/* 11 */     this.a = i;
/* 12 */     this.b = mobeffect.getEffectId();
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer packetdataserializer) {
/* 16 */     this.a = packetdataserializer.readInt();
/* 17 */     this.b = packetdataserializer.readUnsignedByte();
/*    */   }
/*    */ 
/*    */   
/*    */   public void b(PacketDataSerializer packetdataserializer) {
/* 22 */     if (packetdataserializer.version < 16) {
/*    */       
/* 24 */       packetdataserializer.writeInt(this.a);
/*    */     } else {
/* 26 */       packetdataserializer.b(this.a);
/*    */     } 
/*    */     
/* 29 */     packetdataserializer.writeByte(this.b);
/*    */   }
/*    */   
/*    */   public void a(PacketPlayOutListener packetplayoutlistener) {
/* 33 */     packetplayoutlistener.a(this);
/*    */   }
/*    */   
/*    */   public void handle(PacketListener packetlistener) {
/* 37 */     a((PacketPlayOutListener)packetlistener);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayOutRemoveEntityEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
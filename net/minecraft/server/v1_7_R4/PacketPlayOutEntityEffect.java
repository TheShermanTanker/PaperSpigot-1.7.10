/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class PacketPlayOutEntityEffect
/*    */   extends Packet {
/*    */   private int a;
/*    */   private byte b;
/*    */   private byte c;
/*    */   private short d;
/*    */   
/*    */   public PacketPlayOutEntityEffect() {}
/*    */   
/*    */   public PacketPlayOutEntityEffect(int i, MobEffect mobeffect) {
/* 13 */     this.a = i;
/* 14 */     this.b = (byte)(mobeffect.getEffectId() & 0xFF);
/* 15 */     this.c = (byte)(mobeffect.getAmplifier() & 0xFF);
/* 16 */     if (mobeffect.getDuration() > 32767) {
/* 17 */       this.d = Short.MAX_VALUE;
/*    */     } else {
/* 19 */       this.d = (short)mobeffect.getDuration();
/*    */     } 
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer packetdataserializer) {
/* 24 */     this.a = packetdataserializer.readInt();
/* 25 */     this.b = packetdataserializer.readByte();
/* 26 */     this.c = packetdataserializer.readByte();
/* 27 */     this.d = packetdataserializer.readShort();
/*    */   }
/*    */ 
/*    */   
/*    */   public void b(PacketDataSerializer packetdataserializer) {
/* 32 */     if (packetdataserializer.version < 16) {
/*    */       
/* 34 */       packetdataserializer.writeInt(this.a);
/* 35 */       packetdataserializer.writeByte(this.b);
/* 36 */       packetdataserializer.writeByte(this.c);
/* 37 */       packetdataserializer.writeShort(this.d);
/*    */     } else {
/*    */       
/* 40 */       packetdataserializer.b(this.a);
/* 41 */       packetdataserializer.writeByte(this.b);
/* 42 */       packetdataserializer.writeByte(this.c);
/* 43 */       packetdataserializer.b(this.d);
/* 44 */       packetdataserializer.writeBoolean(false);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(PacketPlayOutListener packetplayoutlistener) {
/* 50 */     packetplayoutlistener.a(this);
/*    */   }
/*    */   
/*    */   public void handle(PacketListener packetlistener) {
/* 54 */     a((PacketPlayOutListener)packetlistener);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayOutEntityEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
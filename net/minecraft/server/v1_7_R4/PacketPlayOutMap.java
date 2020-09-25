/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ 
/*    */ public class PacketPlayOutMap
/*    */   extends Packet
/*    */ {
/*    */   private int a;
/*    */   private byte[] b;
/*    */   private byte scale;
/*    */   
/*    */   public PacketPlayOutMap() {}
/*    */   
/*    */   public PacketPlayOutMap(int i, byte[] abyte, byte scale) {
/* 15 */     this.scale = scale;
/*    */     
/* 17 */     this.a = i;
/* 18 */     this.b = abyte;
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer packetdataserializer) {
/* 22 */     this.a = packetdataserializer.a();
/* 23 */     this.b = new byte[packetdataserializer.readUnsignedShort()];
/* 24 */     packetdataserializer.readBytes(this.b);
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer packetdataserializer) {
/* 28 */     packetdataserializer.b(this.a);
/* 29 */     if (packetdataserializer.version < 27) {
/*    */       
/* 31 */       packetdataserializer.writeShort(this.b.length);
/* 32 */       packetdataserializer.writeBytes(this.b);
/*    */     } else {
/* 34 */       packetdataserializer.writeByte(this.scale);
/* 35 */       if (this.b[0] == 1) {
/* 36 */         int count = (this.b.length - 1) / 3;
/* 37 */         packetdataserializer.b(count);
/* 38 */         for (int i = 0; i < count; i++) {
/* 39 */           packetdataserializer.writeByte(this.b[1 + i * 3]);
/* 40 */           packetdataserializer.writeByte(this.b[2 + i * 3]);
/* 41 */           packetdataserializer.writeByte(this.b[3 + i * 3]);
/*    */         } 
/*    */       } else {
/* 44 */         packetdataserializer.b(0);
/*    */       } 
/*    */       
/* 47 */       if (this.b[0] == 0) {
/* 48 */         packetdataserializer.writeByte(1);
/* 49 */         int rows = this.b.length - 3;
/* 50 */         packetdataserializer.writeByte(rows);
/* 51 */         packetdataserializer.writeByte(this.b[1]);
/* 52 */         packetdataserializer.writeByte(this.b[2]);
/* 53 */         a(packetdataserializer, Arrays.copyOfRange(this.b, 3, this.b.length));
/*    */       } else {
/* 55 */         packetdataserializer.writeByte(0);
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public void a(PacketPlayOutListener packetplayoutlistener) {
/* 61 */     packetplayoutlistener.a(this);
/*    */   }
/*    */   
/*    */   public String b() {
/* 65 */     return String.format("id=%d, length=%d", new Object[] { Integer.valueOf(this.a), Integer.valueOf(this.b.length) });
/*    */   }
/*    */   
/*    */   public void handle(PacketListener packetlistener) {
/* 69 */     a((PacketPlayOutListener)packetlistener);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayOutMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayInUpdateSign
/*    */   extends Packet
/*    */ {
/*    */   private int a;
/*    */   private int b;
/*    */   private int c;
/*    */   private String[] d;
/*    */   
/*    */   public void a(PacketDataSerializer packetdataserializer) throws IOException {
/* 16 */     if (packetdataserializer.version < 16) {
/*    */       
/* 18 */       this.a = packetdataserializer.readInt();
/* 19 */       this.b = packetdataserializer.readShort();
/* 20 */       this.c = packetdataserializer.readInt();
/*    */     } else {
/*    */       
/* 23 */       long position = packetdataserializer.readLong();
/* 24 */       this.a = packetdataserializer.readPositionX(position);
/* 25 */       this.b = packetdataserializer.readPositionY(position);
/* 26 */       this.c = packetdataserializer.readPositionZ(position);
/*    */     } 
/*    */     
/* 29 */     this.d = new String[4];
/*    */     
/* 31 */     for (int i = 0; i < 4; i++) {
/*    */       
/* 33 */       if (packetdataserializer.version < 21) {
/*    */         
/* 35 */         this.d[i] = packetdataserializer.c(15);
/*    */       } else {
/*    */         
/* 38 */         this.d[i] = ChatSerializer.a(packetdataserializer.c(32767)).c();
/*    */       } 
/* 40 */       if (this.d[i].length() > 15) {
/* 41 */         this.d[i] = this.d[i].substring(0, 15);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void b(PacketDataSerializer packetdataserializer) throws IOException {
/* 48 */     packetdataserializer.writeInt(this.a);
/* 49 */     packetdataserializer.writeShort(this.b);
/* 50 */     packetdataserializer.writeInt(this.c);
/*    */     
/* 52 */     for (int i = 0; i < 4; i++) {
/* 53 */       packetdataserializer.a(this.d[i]);
/*    */     }
/*    */   }
/*    */   
/*    */   public void a(PacketPlayInListener packetplayinlistener) {
/* 58 */     packetplayinlistener.a(this);
/*    */   }
/*    */   
/*    */   public int c() {
/* 62 */     return this.a;
/*    */   }
/*    */   
/*    */   public int d() {
/* 66 */     return this.b;
/*    */   }
/*    */   
/*    */   public int e() {
/* 70 */     return this.c;
/*    */   }
/*    */   
/*    */   public String[] f() {
/* 74 */     return this.d;
/*    */   }
/*    */   
/*    */   public void handle(PacketListener packetlistener) {
/* 78 */     a((PacketPlayInListener)packetlistener);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayInUpdateSign.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
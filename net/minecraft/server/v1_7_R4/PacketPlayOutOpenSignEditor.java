/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class PacketPlayOutOpenSignEditor
/*    */   extends Packet {
/*    */   private int a;
/*    */   private int b;
/*    */   private int c;
/*    */   
/*    */   public PacketPlayOutOpenSignEditor() {}
/*    */   
/*    */   public PacketPlayOutOpenSignEditor(int i, int j, int k) {
/* 12 */     this.a = i;
/* 13 */     this.b = j;
/* 14 */     this.c = k;
/*    */   }
/*    */   
/*    */   public void a(PacketPlayOutListener packetplayoutlistener) {
/* 18 */     packetplayoutlistener.a(this);
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer packetdataserializer) {
/* 22 */     this.a = packetdataserializer.readInt();
/* 23 */     this.b = packetdataserializer.readInt();
/* 24 */     this.c = packetdataserializer.readInt();
/*    */   }
/*    */ 
/*    */   
/*    */   public void b(PacketDataSerializer packetdataserializer) {
/* 29 */     if (packetdataserializer.version < 16) {
/*    */       
/* 31 */       packetdataserializer.writeInt(this.a);
/* 32 */       packetdataserializer.writeInt(this.b);
/* 33 */       packetdataserializer.writeInt(this.c);
/*    */     } else {
/*    */       
/* 36 */       packetdataserializer.writePosition(this.a, this.b, this.c);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(PacketListener packetlistener) {
/* 42 */     a((PacketPlayOutListener)packetlistener);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayOutOpenSignEditor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
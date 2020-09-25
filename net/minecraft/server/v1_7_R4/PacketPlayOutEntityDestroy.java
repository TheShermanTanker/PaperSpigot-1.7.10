/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class PacketPlayOutEntityDestroy
/*    */   extends Packet {
/*    */   private int[] a;
/*    */   
/*    */   public PacketPlayOutEntityDestroy() {}
/*    */   
/*    */   public PacketPlayOutEntityDestroy(int... aint) {
/* 10 */     this.a = aint;
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer packetdataserializer) {
/* 14 */     this.a = new int[packetdataserializer.readByte()];
/*    */     
/* 16 */     for (int i = 0; i < this.a.length; i++) {
/* 17 */       this.a[i] = packetdataserializer.readInt();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void b(PacketDataSerializer packetdataserializer) {
/* 23 */     if (packetdataserializer.version < 16) {
/*    */       
/* 25 */       packetdataserializer.writeByte(this.a.length);
/*    */       
/* 27 */       for (int i = 0; i < this.a.length; i++)
/*    */       {
/* 29 */         packetdataserializer.writeInt(this.a[i]);
/*    */       }
/*    */     } else {
/* 32 */       packetdataserializer.b(this.a.length);
/* 33 */       for (int i : this.a) {
/* 34 */         packetdataserializer.b(i);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(PacketPlayOutListener packetplayoutlistener) {
/* 41 */     packetplayoutlistener.a(this);
/*    */   }
/*    */   
/*    */   public String b() {
/* 45 */     StringBuilder stringbuilder = new StringBuilder();
/*    */     
/* 47 */     for (int i = 0; i < this.a.length; i++) {
/* 48 */       if (i > 0) {
/* 49 */         stringbuilder.append(", ");
/*    */       }
/*    */       
/* 52 */       stringbuilder.append(this.a[i]);
/*    */     } 
/*    */     
/* 55 */     return String.format("entities=%d[%s]", new Object[] { Integer.valueOf(this.a.length), stringbuilder });
/*    */   }
/*    */   
/*    */   public void handle(PacketListener packetlistener) {
/* 59 */     a((PacketPlayOutListener)packetlistener);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayOutEntityDestroy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
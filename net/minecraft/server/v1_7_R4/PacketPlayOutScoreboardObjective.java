/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ public class PacketPlayOutScoreboardObjective
/*    */   extends Packet {
/*    */   private String a;
/*    */   private String b;
/*    */   private int c;
/*    */   
/*    */   public PacketPlayOutScoreboardObjective() {}
/*    */   
/*    */   public PacketPlayOutScoreboardObjective(ScoreboardObjective scoreboardobjective, int i) {
/* 14 */     this.a = scoreboardobjective.getName();
/* 15 */     this.b = scoreboardobjective.getDisplayName();
/* 16 */     this.c = i;
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer packetdataserializer) throws IOException {
/* 20 */     this.a = packetdataserializer.c(16);
/* 21 */     this.b = packetdataserializer.c(32);
/* 22 */     this.c = packetdataserializer.readByte();
/*    */   }
/*    */ 
/*    */   
/*    */   public void b(PacketDataSerializer packetdataserializer) throws IOException {
/* 27 */     if (packetdataserializer.version < 16) {
/*    */       
/* 29 */       packetdataserializer.a(this.a);
/* 30 */       packetdataserializer.a(this.b);
/* 31 */       packetdataserializer.writeByte(this.c);
/*    */     } else {
/*    */       
/* 34 */       packetdataserializer.a(this.a);
/* 35 */       packetdataserializer.writeByte(this.c);
/* 36 */       if (this.c == 0 || this.c == 2) {
/* 37 */         packetdataserializer.a(this.b);
/* 38 */         packetdataserializer.a("integer");
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(PacketPlayOutListener packetplayoutlistener) {
/* 45 */     packetplayoutlistener.a(this);
/*    */   }
/*    */   
/*    */   public void handle(PacketListener packetlistener) {
/* 49 */     a((PacketPlayOutListener)packetlistener);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayOutScoreboardObjective.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
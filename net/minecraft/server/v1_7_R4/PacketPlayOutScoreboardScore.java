/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ public class PacketPlayOutScoreboardScore
/*    */   extends Packet {
/*  7 */   private String a = "";
/*  8 */   private String b = "";
/*    */   private int c;
/*    */   private int d;
/*    */   
/*    */   public PacketPlayOutScoreboardScore() {}
/*    */   
/*    */   public PacketPlayOutScoreboardScore(ScoreboardScore scoreboardscore, int i) {
/* 15 */     this.a = scoreboardscore.getPlayerName();
/* 16 */     this.b = scoreboardscore.getObjective().getName();
/* 17 */     this.c = scoreboardscore.getScore();
/* 18 */     this.d = i;
/*    */   }
/*    */   
/*    */   public PacketPlayOutScoreboardScore(String s) {
/* 22 */     this.a = s;
/* 23 */     this.b = "";
/* 24 */     this.c = 0;
/* 25 */     this.d = 1;
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer packetdataserializer) throws IOException {
/* 29 */     this.a = packetdataserializer.c(16);
/* 30 */     this.d = packetdataserializer.readByte();
/* 31 */     if (this.d != 1) {
/* 32 */       this.b = packetdataserializer.c(16);
/* 33 */       this.c = packetdataserializer.readInt();
/*    */     } 
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer packetdataserializer) throws IOException {
/* 38 */     packetdataserializer.a(this.a);
/* 39 */     packetdataserializer.writeByte(this.d);
/*    */     
/* 41 */     if (packetdataserializer.version < 16) {
/*    */       
/* 43 */       if (this.d != 1) {
/*    */         
/* 45 */         packetdataserializer.a(this.b);
/* 46 */         packetdataserializer.writeInt(this.c);
/*    */       } 
/*    */     } else {
/*    */       
/* 50 */       packetdataserializer.a(this.b);
/* 51 */       if (this.d != 1)
/*    */       {
/* 53 */         packetdataserializer.b(this.c);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(PacketPlayOutListener packetplayoutlistener) {
/* 60 */     packetplayoutlistener.a(this);
/*    */   }
/*    */   
/*    */   public void handle(PacketListener packetlistener) {
/* 64 */     a((PacketPlayOutListener)packetlistener);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayOutScoreboardScore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayOutScoreboardDisplayObjective
/*    */   extends Packet
/*    */ {
/*    */   private int a;
/*    */   private String b;
/*    */   
/*    */   public PacketPlayOutScoreboardDisplayObjective() {}
/*    */   
/*    */   public PacketPlayOutScoreboardDisplayObjective(int paramInt, ScoreboardObjective paramScoreboardObjective) {
/* 17 */     this.a = paramInt;
/*    */     
/* 19 */     if (paramScoreboardObjective == null) {
/* 20 */       this.b = "";
/*    */     } else {
/* 22 */       this.b = paramScoreboardObjective.getName();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) {
/* 28 */     this.a = paramPacketDataSerializer.readByte();
/* 29 */     this.b = paramPacketDataSerializer.c(16);
/*    */   }
/*    */ 
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) {
/* 34 */     paramPacketDataSerializer.writeByte(this.a);
/* 35 */     paramPacketDataSerializer.a(this.b);
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(PacketPlayOutListener paramPacketPlayOutListener) {
/* 40 */     paramPacketPlayOutListener.a(this);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayOutScoreboardDisplayObjective.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
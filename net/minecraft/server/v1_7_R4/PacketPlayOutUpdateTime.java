/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayOutUpdateTime
/*    */   extends Packet
/*    */ {
/*    */   private long a;
/*    */   private long b;
/*    */   
/*    */   public PacketPlayOutUpdateTime() {}
/*    */   
/*    */   public PacketPlayOutUpdateTime(long paramLong1, long paramLong2, boolean paramBoolean) {
/* 16 */     this.a = paramLong1;
/* 17 */     this.b = paramLong2;
/*    */     
/* 19 */     if (!paramBoolean) {
/* 20 */       this.b = -this.b;
/* 21 */       if (this.b == 0L) {
/* 22 */         this.b = -1L;
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) {
/* 29 */     this.a = paramPacketDataSerializer.readLong();
/* 30 */     this.b = paramPacketDataSerializer.readLong();
/*    */   }
/*    */ 
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) {
/* 35 */     paramPacketDataSerializer.writeLong(this.a);
/* 36 */     paramPacketDataSerializer.writeLong(this.b);
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(PacketPlayOutListener paramPacketPlayOutListener) {
/* 41 */     paramPacketPlayOutListener.a(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public String b() {
/* 46 */     return String.format("time=%d,dtime=%d", new Object[] { Long.valueOf(this.a), Long.valueOf(this.b) });
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayOutUpdateTime.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
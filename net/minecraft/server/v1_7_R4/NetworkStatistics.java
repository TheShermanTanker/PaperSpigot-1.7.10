/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ import org.apache.logging.log4j.Marker;
/*    */ import org.apache.logging.log4j.MarkerManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NetworkStatistics
/*    */ {
/* 13 */   private static final Logger a = LogManager.getLogger();
/* 14 */   private static final Marker b = MarkerManager.getMarker("NETSTAT_MARKER", NetworkManager.c);
/*    */ 
/*    */ 
/*    */   
/* 18 */   private PacketStatisticsTracker c = new PacketStatisticsTracker();
/* 19 */   private PacketStatisticsTracker d = new PacketStatisticsTracker();
/*    */ 
/*    */   
/*    */   public void a(int paramInt, long paramLong) {
/* 23 */     this.c.a(paramInt, paramLong);
/*    */   }
/*    */   
/*    */   public void b(int paramInt, long paramLong) {
/* 27 */     this.d.a(paramInt, paramLong);
/*    */   }
/*    */   
/*    */   public long a() {
/* 31 */     return this.c.a();
/*    */   }
/*    */   
/*    */   public long b() {
/* 35 */     return this.d.a();
/*    */   }
/*    */   
/*    */   public long c() {
/* 39 */     return this.c.b();
/*    */   }
/*    */   
/*    */   public long d() {
/* 43 */     return this.d.b();
/*    */   }
/*    */   
/*    */   public PacketStatistics e() {
/* 47 */     return this.c.c();
/*    */   }
/*    */   
/*    */   public PacketStatistics f() {
/* 51 */     return this.c.d();
/*    */   }
/*    */   
/*    */   public PacketStatistics g() {
/* 55 */     return this.d.c();
/*    */   }
/*    */   
/*    */   public PacketStatistics h() {
/* 59 */     return this.d.d();
/*    */   }
/*    */   
/*    */   public PacketStatistics a(int paramInt) {
/* 63 */     return this.c.a(paramInt);
/*    */   }
/*    */   
/*    */   public PacketStatistics b(int paramInt) {
/* 67 */     return this.d.a(paramInt);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\NetworkStatistics.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
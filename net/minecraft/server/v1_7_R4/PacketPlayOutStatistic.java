/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Map;
/*    */ import net.minecraft.util.com.google.common.collect.Maps;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayOutStatistic
/*    */   extends Packet
/*    */ {
/*    */   private Map a;
/*    */   
/*    */   public PacketPlayOutStatistic() {}
/*    */   
/*    */   public PacketPlayOutStatistic(Map paramMap) {
/* 21 */     this.a = paramMap;
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(PacketPlayOutListener paramPacketPlayOutListener) {
/* 26 */     paramPacketPlayOutListener.a(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) {
/* 31 */     int i = paramPacketDataSerializer.a();
/* 32 */     this.a = Maps.newHashMap();
/*    */     
/* 34 */     for (byte b = 0; b < i; b++) {
/* 35 */       Statistic statistic = StatisticList.getStatistic(paramPacketDataSerializer.c(32767));
/* 36 */       int j = paramPacketDataSerializer.a();
/*    */       
/* 38 */       if (statistic != null) this.a.put(statistic, Integer.valueOf(j));
/*    */     
/*    */     } 
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) {
/* 44 */     paramPacketDataSerializer.b(this.a.size());
/*    */     
/* 46 */     for (Map.Entry entry : this.a.entrySet()) {
/* 47 */       paramPacketDataSerializer.a(((Statistic)entry.getKey()).name);
/* 48 */       paramPacketDataSerializer.b(((Integer)entry.getValue()).intValue());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public String b() {
/* 54 */     return String.format("count=%d", new Object[] { Integer.valueOf(this.a.size()) });
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayOutStatistic.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
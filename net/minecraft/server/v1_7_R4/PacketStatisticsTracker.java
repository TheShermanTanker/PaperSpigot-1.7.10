/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.concurrent.atomic.AtomicReference;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class PacketStatisticsTracker
/*     */ {
/*  76 */   private AtomicReference[] a = new AtomicReference[100];
/*     */   
/*     */   public PacketStatisticsTracker() {
/*  79 */     for (byte b = 0; b < 100; b++)
/*  80 */       this.a[b] = new AtomicReference<PackStatisticData>(new PackStatisticData(0L, 0, 0.0D, null)); 
/*     */   }
/*     */   public void a(int paramInt, long paramLong) {
/*     */     try {
/*     */       PackStatisticData packStatisticData1;
/*     */       PackStatisticData packStatisticData2;
/*  86 */       if (paramInt < 0 || paramInt >= 100) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/*     */       do {
/*  92 */         packStatisticData1 = this.a[paramInt].get();
/*  93 */         packStatisticData2 = packStatisticData1.a(paramLong);
/*     */       }
/*  95 */       while (!this.a[paramInt].compareAndSet(packStatisticData1, packStatisticData2));
/*     */     }
/*  97 */     catch (Exception exception) {
/*  98 */       if (NetworkStatistics.i().isDebugEnabled()) {
/*  99 */         NetworkStatistics.i().debug(NetworkStatistics.j(), "NetStat failed with packetId: " + paramInt, exception);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public long a() {
/* 105 */     long l = 0L;
/* 106 */     for (byte b = 0; b < 100; b++) {
/* 107 */       l += ((PackStatisticData)this.a[b].get()).a();
/*     */     }
/* 109 */     return l;
/*     */   }
/*     */   
/*     */   public long b() {
/* 113 */     long l = 0L;
/* 114 */     for (byte b = 0; b < 100; b++) {
/* 115 */       l += ((PackStatisticData)this.a[b].get()).b();
/*     */     }
/* 117 */     return l;
/*     */   }
/*     */   
/*     */   public PacketStatistics c() {
/* 121 */     byte b = -1;
/* 122 */     PackStatisticData packStatisticData = new PackStatisticData(-1L, -1, 0.0D, null);
/* 123 */     for (byte b1 = 0; b1 < 100; b1++) {
/* 124 */       PackStatisticData packStatisticData1 = this.a[b1].get();
/* 125 */       if (PackStatisticData.a(packStatisticData1) > PackStatisticData.a(packStatisticData)) {
/* 126 */         b = b1;
/* 127 */         packStatisticData = packStatisticData1;
/*     */       } 
/*     */     } 
/* 130 */     return new PacketStatistics(b, packStatisticData);
/*     */   }
/*     */   
/*     */   public PacketStatistics d() {
/* 134 */     byte b = -1;
/* 135 */     PackStatisticData packStatisticData = new PackStatisticData(-1L, -1, 0.0D, null);
/* 136 */     for (byte b1 = 0; b1 < 100; b1++) {
/* 137 */       PackStatisticData packStatisticData1 = this.a[b1].get();
/* 138 */       if (PackStatisticData.b(packStatisticData1) > PackStatisticData.b(packStatisticData)) {
/* 139 */         b = b1;
/* 140 */         packStatisticData = packStatisticData1;
/*     */       } 
/*     */     } 
/* 143 */     return new PacketStatistics(b, packStatisticData);
/*     */   }
/*     */   
/*     */   public PacketStatistics a(int paramInt) {
/* 147 */     if (paramInt < 0 || paramInt >= 100) {
/* 148 */       return null;
/*     */     }
/*     */     
/* 151 */     return new PacketStatistics(paramInt, this.a[paramInt].get());
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketStatisticsTracker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
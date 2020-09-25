/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.concurrent.Callable;
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
/*     */ class CrashReportAABBPoolSize
/*     */   implements Callable
/*     */ {
/*     */   CrashReportAABBPoolSize(CrashReport paramCrashReport) {}
/*     */   
/*     */   public String a() {
/* 103 */     byte b1 = 0;
/* 104 */     int i = 56 * b1;
/* 105 */     int j = i / 1024 / 1024;
/* 106 */     byte b2 = 0;
/* 107 */     int k = 56 * b2;
/* 108 */     int m = k / 1024 / 1024;
/*     */     
/* 110 */     return b1 + " (" + i + " bytes; " + j + " MB) allocated, " + b2 + " (" + k + " bytes; " + m + " MB) used";
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\CrashReportAABBPoolSize.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.concurrent.Callable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class CrashReportMemory
/*    */   implements Callable
/*    */ {
/*    */   CrashReportMemory(CrashReport paramCrashReport) {}
/*    */   
/*    */   public String a() {
/* 66 */     Runtime runtime = Runtime.getRuntime();
/* 67 */     long l1 = runtime.maxMemory();
/* 68 */     long l2 = runtime.totalMemory();
/* 69 */     long l3 = runtime.freeMemory();
/* 70 */     long l4 = l1 / 1024L / 1024L;
/* 71 */     long l5 = l2 / 1024L / 1024L;
/* 72 */     long l6 = l3 / 1024L / 1024L;
/*    */     
/* 74 */     return l3 + " bytes (" + l6 + " MB) / " + l2 + " bytes (" + l5 + " MB) up to " + l1 + " bytes (" + l4 + " MB)";
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\CrashReportMemory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
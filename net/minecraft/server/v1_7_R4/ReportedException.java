/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class ReportedException
/*    */   extends RuntimeException
/*    */ {
/*    */   private final CrashReport a;
/*    */   
/*    */   public ReportedException(CrashReport paramCrashReport) {
/*  9 */     this.a = paramCrashReport;
/*    */   }
/*    */   
/*    */   public CrashReport a() {
/* 13 */     return this.a;
/*    */   }
/*    */ 
/*    */   
/*    */   public Throwable getCause() {
/* 18 */     return this.a.b();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getMessage() {
/* 23 */     return this.a.a();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ReportedException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */